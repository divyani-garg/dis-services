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
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.model.User;
import sgsits.cse.dis.user.repo.StaffRepository;
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
	@Autowired
	StaffRepository staffRepository;
	
	@ApiOperation(value = "get User Type", response = String.class, httpMethod = "GET", produces = "text/plain")
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
	
	@ApiOperation(value = "get Admission Year", response = Integer.class, httpMethod = "GET", produces = "text/plain")
	@RequestMapping(value = "/getAdmissionYear", method = RequestMethod.GET)
	public int getAdmissionYear(@RequestParam("id") long id)
	{
		Optional<StudentProfile> stud = studentRepository.findByUserId(id);
		int year = stud.get().getAdmissionYear();
		return year;
	}
	
	@ApiOperation(value = "get User Name", response = String.class, httpMethod = "GET", produces = "text/plain")
	@RequestMapping(value = "/getUserName", method = RequestMethod.GET)
	public String getUserName(@RequestParam("id") long id)
	{	
		Optional<StaffProfile> staffDetails = staffRepository.findByUserId(id);
		String staffName = staffDetails.get().getName();
		return staffName;
	}
}