package sgsits.cse.dis.user.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.model.User;
import sgsits.cse.dis.user.repo.StudentRepository;
import sgsits.cse.dis.user.repo.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "User Resource")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	StudentRepository studentRepository;
	
	@ApiOperation(value = "get User Details", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getUserType", method = RequestMethod.GET)
	public String getUserType(@RequestParam("id") long id)
	{	
		Optional<User> user = userRepository.findById(id);
		String type = user.get().getUserType();
		return type;
	}
	
	public String verifySignUp()
	{
		return null;
	}
	
	@ApiOperation(value = "get Admission Year", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getAdmissionYear", method = RequestMethod.GET)
	public int getAdmissionYear(@RequestParam("id") long id)
	{
		Optional<StudentProfile> stud = studentRepository.findByUserId(id);
		int year = stud.get().getAdmissionYear();
		return year;
	}
}