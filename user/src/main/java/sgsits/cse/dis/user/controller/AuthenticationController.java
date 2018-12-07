package sgsits.cse.dis.user.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sgsits.cse.dis.user.model.Authentication;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.repo.StaffRepository;
import sgsits.cse.dis.user.repo.StudentRepository;
import sgsits.cse.dis.user.security.MD5;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*") // enables cross origin request
@RestController
@RequestMapping(value = "/dis")
@Api(value = "Authentication Resource")
public class AuthenticationController {

	@Autowired
	StudentRepository studRepo;
	@Autowired
	StaffRepository staffRepo;
	@Autowired
	EmailController email;

	String pattern = "MM-dd-yyyy";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	@ApiOperation(value = "login", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody Authentication auth) {
		Optional<StaffProfile> staff = staffRepo.findByEmail(auth.getUsername());
		if (staff.isPresent()) {
			String md5_pass = MD5.getHash(auth.getPassword());
			if (staff.get().getPassword().equals(md5_pass)) {
				// set session
				// update last login
				return "staff home page";
			} else
				return "You have entered incorrect password";
		} else {
			Optional<StudentProfile> student = studRepo.findByEnrollmentId(auth.getUsername());
			if (student.isPresent()) {
				String md5_pass = MD5.getHash(auth.getPassword());
				if (student.get().getPassword().equals(md5_pass)) {
					// set session
					// update last login
					return "student home page";
				} else
					return "You have entered incorrect password";
			} else {
				return "You are not registered with the system. Please signup first";
			}
		}
	}

	@ApiOperation(value = "signup", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@RequestBody Authentication auth) {
		Optional<StudentProfile> student = studRepo.findByEnrollmentId(auth.getUsername());
		if (student.isPresent()) {
			StudentProfile stud = student.get();
			if (!stud.isEnabled()) {
				if (stud.getActivationlink() == null) {
					if (stud.getEmail().equals(auth.getEmail())) {
						if (stud.getMobileNo() == auth.getMobileNo()) {
							String enteredDate = simpleDateFormat.format(auth.getDob());
							String storedDate = simpleDateFormat.format(stud.getDob());
							if (storedDate.equals(enteredDate)) {
								String md5_passwd = MD5.getHash(auth.getPassword());
								stud.setPassword(md5_passwd);
								studRepo.save(stud);
								// send activation link
								// activate account via email
								return "You are successfully registered, activation link has been sent to your registered email account";
							} else
								return "You have enetered wrong DOB";
						} else
							return "You have enetered wrong mobile no";
					} else
						return "You have entered wrong email id";
				} else // what if activation link expire
					return "You are already registered with the system, please activate your account to continue";
			} else
				return "You are already registered with the system, please login to continue";
		} else
			return "You are not registered with the system, contact administration";
	}
	
	//register new staff; by HOD or incharge only;  
	@ApiOperation(value = "staff-sign-up", response = Object.class, httpMethod = "POST")
	@RequestMapping(value = "/staff-signup", method = RequestMethod.POST)
	public String StaffSignUp(@RequestBody StaffProfile staffPro)
	{
		Optional<StaffProfile> staff = staffRepo.findByEmail(staffPro.getEmail());
		if(!staff.isPresent())
		{
			String md5_passwd = MD5.getHash(staffPro.getPassword());
			staffPro.setPassword(md5_passwd);
			staffPro.setEnabled(true);
			staffRepo.save(staffPro);
		}
		else
			return "Email id is already registered with the system";
		return "";
	}

	@ApiOperation(value = "forgot-password", response = Object.class, httpMethod = "POST")
	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public String ForgotPassword(@RequestBody StudentProfile studPro, HttpServletRequest request) {
		Optional<StudentProfile> list = studRepo.findByEmail(studPro.getEmail());
		if (list.isPresent()) {
			StudentProfile studentProfile = list.get();
			studentProfile.setResetToken(UUID.randomUUID().toString());
			studentProfile.setResetTokenExpiry(LocalDate.now().plusDays(1));
			studRepo.save(studentProfile);
			String appUrl = request.getScheme() + "://" + request.getServerName();

			// Email message
			email.sendSimpleEmail(studentProfile.getEmail(), "DIS Password Reset Request",
					"To reset your password, click the link below:\n" + appUrl + "/dis/reset-password?resetToken="
							+ studentProfile.getResetToken());
			return "A password reset link has been sent to registered email address.";

		} else {
			if (list.isPresent()) {
				// faculty code
				return "";
			} else
				return "We didn't find an account for this e-mail address.";
		}
	}

	// Display form to reset password
	@ApiOperation(value = "reset-password", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/reset-password/{resetToken}", method = RequestMethod.GET)
	public ResponseEntity<String> displayResetPassword(@PathVariable("resetToken") String resetToken) {
		System.out.println("reset");
		Optional<StudentProfile> studentProfile = studRepo.findByResetToken(resetToken);
		System.out.println(resetToken);
		
		if (studentProfile.isPresent()) {
			System.out.println("valid");
			return new ResponseEntity<>("Valid", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This is an invalid password reset link.", HttpStatus.OK);
		}
	}

	// Process reset password form
	@ApiOperation(value = "reset-password-process", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/reset-password-process", method = RequestMethod.POST)
	public String processResetPassword(@RequestParam Map<String, String> requestParams) {
		// Find the user associated with the reset token Optional<StudentProfile>
		Optional<StudentProfile> stud = studRepo.findByResetToken(requestParams.get("resetToken"));
		// This should always be non-null but we check just in case
		if (stud.isPresent()) {
			StudentProfile resetStud = stud.get();
			// set new password
			resetStud.setPassword(MD5.getHash(requestParams.get("password")));
			// Set the reset token to null so it cannot be used again
			resetStud.setResetToken(null);
			// save student
			studRepo.save(resetStud);
			return "You have successfully reset your password. You may now login.";
		} else {
			// faculty code
			return "";
		}
	}
}