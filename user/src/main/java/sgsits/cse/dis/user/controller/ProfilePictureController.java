package sgsits.cse.dis.user.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.message.response.ProfilePicture;
import sgsits.cse.dis.user.message.response.ResponseMessage;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.repo.StaffRepository;
import sgsits.cse.dis.user.repo.StudentRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Profile Picture Resource")
public class ProfilePictureController {

	@Autowired
	StaffRepository staffRepository;
	@Autowired
	StudentRepository studentRepository;

	JwtResolver jwtResolver = new JwtResolver();

	@ApiOperation(value = "AddProfilePicture", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addProfilePicture", method = RequestMethod.POST)
	public ResponseEntity<?> addProfilePicture(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws IOException {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			Optional<StaffProfile> staffProfile = staffRepository.findByUserId(id);
			if (staffProfile.isPresent()) {
				Optional<StaffProfile> profile = staffRepository.findByUserId(id);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				profile.get().setModifiedBy(id);
				profile.get().setModifiedDate(simpleDateFormat.format(new Date()));
				profile.get().setProfilePicture(file.getBytes());
				staffRepository.save(profile.get());
				return new ResponseEntity<>(new ResponseMessage("Profile Picture Updated Successfully!"),
						HttpStatus.OK);
			} else
				return new ResponseEntity<>(new ResponseMessage("You are not allowed to update!"),
						HttpStatus.BAD_REQUEST);
		} else {
			Optional<StudentProfile> studentprofile = studentRepository.findByUserId(id);
			if (studentprofile.isPresent()) {
				Optional<StudentProfile> profile = studentRepository.findByUserId(id);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				profile.get().setModifiedBy(id);
				profile.get().setModifiedDate(simpleDateFormat.format(new Date()));
				profile.get().setProfilePicture(file.getBytes());
				studentRepository.save(profile.get());
				return new ResponseEntity<>(new ResponseMessage("Profile Picture Updated Successfully!"),
						HttpStatus.OK);
			} else
				return new ResponseEntity<>(new ResponseMessage("You are not allowed to update!"),
						HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "GetProfilePicture", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getProfilePicture", method = RequestMethod.GET)
	public ProfilePicture getProfilePicture(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			Optional<StaffProfile> profile = staffRepository.findByUserId(id);
			profile = staffRepository.findByUserId(id);
			ProfilePicture picture = new ProfilePicture();
			picture.setProfilePicture(profile.get().getProfilePicture());
			return picture;
		} else {
			Optional<StudentProfile> profile = studentRepository.findByUserId(id);
			ProfilePicture picture = new ProfilePicture();
			picture.setProfilePicture(profile.get().getProfilePicture());
			return picture;
		}
	}

}