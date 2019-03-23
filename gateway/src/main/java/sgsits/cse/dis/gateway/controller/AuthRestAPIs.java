package sgsits.cse.dis.gateway.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sgsits.cse.dis.gateway.feign.UserClient;
import sgsits.cse.dis.gateway.message.request.LoginForm;
import sgsits.cse.dis.gateway.message.request.SignUpForm;
import sgsits.cse.dis.gateway.message.response.JwtResponse;
import sgsits.cse.dis.gateway.message.response.ResponseMessage;
import sgsits.cse.dis.gateway.model.User;
import sgsits.cse.dis.gateway.repo.TaskRepository;
import sgsits.cse.dis.gateway.repo.UserRepository;
import sgsits.cse.dis.gateway.security.jwt.JwtProvider;
import sgsits.cse.dis.gateway.service.UserPrinciple;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/dis")
public class AuthRestAPIs {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	EmailController email;
	@Autowired
	UserClient userClient;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
		Optional<User> u = userRepository.findByUsername(loginRequest.getUsername());
		if (u.get().isEnabled()) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generateJwtToken(authentication);
			UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
			// Update Last Login
			Optional<User> user = userRepository.findByUsername(userPrincipal.getUsername());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			user.get().setLastLogin(simpleDateFormat.format(new Date()));
			userRepository.save(user.get());
			return ResponseEntity.ok(new JwtResponse(jwt, userPrincipal.getAuthorities()));
		} else
			return new ResponseEntity<>(new ResponseMessage("Your Account is not yet activated! Please activate first."), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest, HttpServletRequest request)
			throws SQLException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"), HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"), HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByMobileNo(signUpRequest.getMobileNo())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Mobile Number is already in use!"), HttpStatus.BAD_REQUEST);
		}

		if (userClient.findUser(signUpRequest)) { // separate message for incorrect detail
			User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getDob(),
					signUpRequest.getMobileNo(), encoder.encode(signUpRequest.getPassword()));
			user.setCreatedBy(signUpRequest.getUsername());
			user.setCreatedDate(simpleDateFormat.format(new Date()));
			user.setUserType(userClient.findUserType(signUpRequest));
			user.setActivationToken(UUID.randomUUID().toString());
			user.setActivationTokenExpiry(simpleDateFormat.format(DateUtils.addDays(new Date(), 3)));
			userRepository.save(user);
			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();
			// Email message
			email.sendSimpleEmail(user.getEmail(), "DIS Account Activation Request", "To activate your account, click the link below:\n" + appUrl + "/dis/activation?token="+ user.getActivationToken());
			return new ResponseEntity<>(new ResponseMessage("Registration successfull! An email has been sent to your registered email address. Please verify to continue!"), HttpStatus.OK);
		} else
			return new ResponseEntity<>(new ResponseMessage("You are not registered with the System! Please contact administration."), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/preActivation", method = RequestMethod.POST)
	public ResponseEntity<?> preActivation(@RequestParam("email") String recepientemail, HttpServletRequest request) {
		Optional<User> userlist = userRepository.findByEmail(recepientemail);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (userlist.isPresent()) {
			User u = userlist.get();
			u.setActivationToken(UUID.randomUUID().toString());
			u.setActivationTokenExpiry(simpleDateFormat.format(DateUtils.addDays(new Date(), 3)));
			u.setModifiedBy(u.getId());
			u.setModifiedDate(simpleDateFormat.format(new Date()));
			userRepository.save(u);
			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();
			// Email message
			email.sendSimpleEmail(u.getEmail(), "DIS Account Activation Request", "To activate your account, click the link below:\n" + appUrl + "/dis/activation?token="+ u.getActivationToken());
			return new ResponseEntity<>(new ResponseMessage("An Account Activation link has been sent to registered email address!"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ResponseMessage("We didn't find an account for this e-mail address!"), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/activation", method = RequestMethod.GET)
	public ModelAndView activateAccount(@RequestParam("token") String token) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Optional<User> user = userRepository.findUserByActivationToken(token);
		ModelAndView modelAndView = new ModelAndView();
		if (user.isPresent()) { // Token found in DB
			if (simpleDateFormat.parse(user.get().getActivationTokenExpiry()).after(new Date())) {
				user.get().setEnabled(true);
				user.get().setActivationToken(null);
				user.get().setActivationTokenExpiry(null);
				user.get().setModifiedBy(user.get().getId());
				user.get().setModifiedDate(simpleDateFormat.format(new Date()));
				if (userClient.updateEmailAndUserId(user.get().getMobileNo())) {
					userRepository.save(user.get());
					modelAndView.addObject("token", token);
					modelAndView.setViewName("redirect:http://localhost:4200");
				}
			} else {
				modelAndView.addObject("errorMessage", "Oops!  Your account activation link has expired.");
				modelAndView.setViewName("redirect:http://localhost:4200/activate-account");
			}
		} else { // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid account activation link.");
			modelAndView.setViewName("redirect:http://localhost:4200/activate-account");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<?> forgotPassword(@RequestParam("email") String recepientemail, HttpServletRequest request) {
		Optional<User> userlist = userRepository.findByEmail(recepientemail);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (userlist.isPresent()) {
			User u = userlist.get();
			u.setResetToken(UUID.randomUUID().toString());
			u.setResetTokenExpiry(simpleDateFormat.format(DateUtils.addDays(new Date(), 1)));
			u.setModifiedBy(u.getId());
			u.setModifiedDate(simpleDateFormat.format(new Date()));
			userRepository.save(u);
			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();

			// Email message
			email.sendSimpleEmail(u.getEmail(), "DIS Password Reset Request",
					"To reset your password, click the link below:\n" + appUrl + "/dis/resetPassword?resetToken="
							+ u.getResetToken());
			return new ResponseEntity<>(
					new ResponseMessage("A password reset link has been sent to registered email address!"),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(new ResponseMessage("We didn't find an account for this e-mail address!"),
				HttpStatus.BAD_REQUEST);
	}

	// Display form to reset password
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(@RequestParam("resetToken") String token) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Optional<User> user = userRepository.findUserByResetToken(token);
		ModelAndView modelAndView = new ModelAndView();
		if (user.isPresent()) { // Token found in DB
			if (simpleDateFormat.parse(user.get().getResetTokenExpiry()).after(new Date())) {
				modelAndView.addObject("resetToken", token);
				modelAndView.setViewName("redirect:http://localhost:4200/reset-password");
			} else {
				modelAndView.addObject("errorMessage", "Oops!  Your password reset link has expired.");
				modelAndView.setViewName("redirect:http://localhost:4200/forgot-password");
			}
		} else { // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("redirect:http://localhost:4200/forgot-password");
		}
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/processResetPassword", method = RequestMethod.POST)
	public ResponseEntity<?> setNewPassword(@RequestBody Map<String, String> requestParams, RedirectAttributes redir, HttpServletResponse response) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// Find the user associated with the reset token
		Optional<User> user = userRepository.findUserByResetToken(requestParams.get("resetToken"));
		ModelAndView modelAndView = new ModelAndView();
		// This should always be non-null but we check just in case
		if (user.isPresent()) {
			User resetUser = user.get();
			// Set new password
			resetUser.setPassword(encoder.encode(requestParams.get("password")));
			// Set the reset token to null so it cannot be used again
			resetUser.setResetToken(null);
			resetUser.setResetTokenExpiry(null);
			// Update modified date
			resetUser.setModifiedBy(resetUser.getId());
			resetUser.setModifiedDate(simpleDateFormat.format(new Date()));
			// Save user
			userRepository.save(resetUser);
			// In order to set a model attribute on a redirect, we must use RedirectAttributes
			// redir.addFlashAttribute("successMessage", "You have successfully reset your password. You may now login.");
			//modelAndView.addObject("successMessage", "You have successfully reset your password. You may now login.");
			//modelAndView.setViewName("redirect:http://localhost:4200");
			return new ResponseEntity<>(new ResponseMessage("You have successfully reset your password. You may now login."),
					HttpStatus.OK);
			//return modelAndView;
		} else {
			//modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			//modelAndView.setViewName("redirect:http://localhost:4200/forgot-password");
			return new ResponseEntity<>(new ResponseMessage("Oops!  This is an invalid password reset link."),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getUserType")
	public String getuserType(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return jwtProvider.getUserTypeFromJwtToken(authHeader.replace("Bearer ", ""));
		}
		return null;
	}
}