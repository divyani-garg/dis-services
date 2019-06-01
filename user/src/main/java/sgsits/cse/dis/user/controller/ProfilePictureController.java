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
import org.springframework.web.bind.annotation.PathVariable;
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
import sgsits.cse.dis.user.repo.StaffRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Profile Picture Resource")
public class ProfilePictureController {

	@Autowired
	StaffRepository staffRepository;

	JwtResolver jwtResolver = new JwtResolver();

	@ApiOperation(value = "AddProfilePicture", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addProfilePicture", method = RequestMethod.POST)
	public ResponseEntity<?> addProfilePicture(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws IOException {
	//	long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
	//	Optional<StaffProfile> staffProfile = staffRepository.findByUserId(id);
		long id = 2;
	//	if (staffProfile.isPresent()) {
			Optional<StaffProfile> profile = staffRepository.findByUserId(id);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			profile.get().setModifiedBy(id);
			profile.get().setModifiedDate(simpleDateFormat.format(new Date()));
			profile.get().setProfilePicture(file.getBytes());
			staffRepository.save(profile.get());
			return new ResponseEntity<>(new ResponseMessage("Profile Picture Updated Successfully!"), HttpStatus.OK);
		//} else
		//	return new ResponseEntity<>(new ResponseMessage("You are not allowed to update!"), HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "GetProfilePicture", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getProfilePicture", method = RequestMethod.GET)
	public ProfilePicture getProfilePicture(HttpServletRequest request)
	{
		long id =2;
		Optional<StaffProfile> profile = staffRepository.findByUserId(id);
		ProfilePicture picture = new ProfilePicture();
		picture.setProfilePicture(profile.get().getProfilePicture());
		return picture;
	}
	@ApiOperation(value = "DeleteProfilePicture", response = Object.class, httpMethod = "DELETE", produces = "application/json")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProfilePicture(@PathVariable long id) {

		return new ResponseEntity<>("Profile Picture has been deleted!", HttpStatus.OK);
	}

}
