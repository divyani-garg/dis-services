package sgsits.cse.dis.user.controller;
/*package sgsits.cse.dis.controller;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sgsits.cse.dis.model.StudentProfile;
import sgsits.cse.dis.security.MD5;
import sgsits.cse.dis.service.EmailService;
import sgsits.cse.dis.service.StudentService;
 
@RestController
public class PasswordController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String email,
			HttpServletRequest request) {
		Optional<StudentProfile> optional = studentService.findByEmail(email);
		if (!optional.isPresent()) {
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
		} else {
			StudentProfile studentProfile = optional.get();
			studentProfile.setResetToken(UUID.randomUUID().toString());
			studentService.save(studentProfile);
			String appUrl = request.getScheme() + "://" + request.getServerName();

			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("dis.sgsits@gmail.com");
			passwordResetEmail.setTo(studentProfile.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + "/reset?token="
					+ studentProfile.getResetToken());
			emailService.sendEmail(passwordResetEmail);

			// Add success message to view
			modelAndView.addObject("successMessage", "A password reset link has been sent to " + email);
		}
		modelAndView.setViewName("forgotPassword");
		return modelAndView;
	}

	// Display form to reset password
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

		Optional<StudentProfile> studentProfile = studentService.findByResetToken(token);

		if (studentProfile.isPresent()) { // Token found in DB
			modelAndView.addObject("resetToken", token);
		} else { // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}

		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams,
			RedirectAttributes redir) {

		// Find the user associated with the reset token
		Optional<StudentProfile> studentProfile = studentService.findByResetToken(requestParams.get("token"));

		// This should always be non-null but we check just in case
		if (studentProfile.isPresent()) {

			StudentProfile resetUser = studentProfile.get();

			// Set new password
			resetUser.setPassword(MD5.getHash(requestParams.get("password")));

			// Set the reset token to null so it cannot be used again
			resetUser.setResetToken(null);

			// Save user
			studentService.save(resetUser);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

			modelAndView.setViewName("redirect:login");
			return modelAndView;

		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");
		}

		return modelAndView;
	}

	// Going to reset page without a token redirects to login page
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:login");
	}
}*/