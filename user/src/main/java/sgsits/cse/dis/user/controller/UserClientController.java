package sgsits.cse.dis.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.message.request.SignUpForm;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.model.User;
import sgsits.cse.dis.user.repo.StaffRepository;
import sgsits.cse.dis.user.repo.StudentRepository;
import sgsits.cse.dis.user.repo.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "User Resource")
public class UserClientController {
	
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
	
	@ApiOperation(value = "get Course", response = String.class, httpMethod = "GET", produces = "text/plain")
	@RequestMapping(value = "/getCourse", method = RequestMethod.GET)
	public String getCourse(@RequestParam("id") long id)
	{
		Optional<StudentProfile> stud = studentRepository.findByUserId(id);
		String course = stud.get().getCourseId();
		return course;
	}
	
	@ApiOperation(value = "get Student Name", response = String.class, httpMethod = "GET", produces = "text/plain")
	@RequestMapping(value = "/getStudentName", method = RequestMethod.GET)
	public String getStudentName(@RequestParam("id") long id)
	{
		Optional<StudentProfile> stud = studentRepository.findByUserId(id);
		String name = stud.get().getFullName();
		return name;
	}
	
	@ApiOperation(value = "Find User", response = Boolean.class, httpMethod = "POST", produces = "text/plain")
	@RequestMapping(value = "/findUser", method = RequestMethod.POST)
	public boolean findUser(@RequestBody SignUpForm signup)
	{
		boolean exist = false;
		exist = staffRepository.existsByEmailAndMobileNoAndDob(signup.getEmail(),signup.getMobileNo(),signup.getDob());
		if(exist == false)
		{
			exist = studentRepository.existsByEnrollmentIdAndMobileNoAndDob(signup.getUsername(),signup.getMobileNo(),signup.getDob());
		}
		return exist;
	}
	
	@ApiOperation(value = "Find User Type", response = Boolean.class, httpMethod = "POST", produces = "text/plain")
	@RequestMapping(value = "/findUserIype", method = RequestMethod.POST)
	public String findUserType(@RequestBody SignUpForm signup)
	{
		String type = null;
		Optional<StaffProfile> staff = staffRepository.findByEmailAndMobileNoAndDob(signup.getEmail(),signup.getMobileNo(),signup.getDob());
		if(staff.isPresent()){
			if(staff.get().getClasss().equals("I") || staff.get().getClasss().equals("II")){
				type = "faculty";
			}
			if(staff.get().getClasss().equals("III")){
				type = "staff";
			}
			if(staff.get().getClasss().equals("IV")){
				type = "attendent";
			}
		}
		else{
			Optional<StudentProfile> stud = studentRepository.findByEnrollmentIdAndMobileNoAndDob(signup.getUsername(),signup.getMobileNo(),signup.getDob());
			if(stud.isPresent()){
				type = "student";
			}
		}
		return type;
	}
	
	@ApiOperation(value = "Update email and user Id", response = Boolean.class, httpMethod = "GET", produces = "text/plain")
	@RequestMapping(value = "/updateEmailAndUserId", method = RequestMethod.GET)
	public boolean updateEmailAndUserId(@RequestParam("mobileNo") long mobileNo)
	{
		Optional<User> user = userRepository.findByMobileNo(mobileNo);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(user.get().getUserType().equals("student")) {
			Optional<StudentProfile> stud = studentRepository.findByMobileNo(mobileNo);
			stud.get().setUserId(user.get().getId());
			stud.get().setEmail(user.get().getEmail());
			stud.get().setModifiedBy(user.get().getId());
			stud.get().setModifiedDate(simpleDateFormat.format(new Date()));
			studentRepository.save(stud.get());
			return true;
		}
		else {
			Optional<StaffProfile> staff = staffRepository.findByMobileNo(mobileNo);
			staff.get().setUserId(user.get().getId());
			staff.get().setEmail(user.get().getEmail());
			staff.get().setModifiedBy(user.get().getId());
			staff.get().setModifiedDate(simpleDateFormat.format(new Date()));
			staffRepository.save(staff.get());
			return true;
		}			
	}
	
	@ApiOperation(value = "Get Acronym Name", response = Object.class, httpMethod = "GET", produces = "text/plain")
	@RequestMapping(value = "/getAcronymName", method = RequestMethod.GET)
	public String getAcronymName(@RequestParam("id") long id)
	{
		Optional<StaffProfile> staff = staffRepository.findByUserId(id);
		if(staff!=null)
			if(staff.get().getNameAcronym()!=null)
				return staff.get().getNameAcronym();
		return null;
	}

}
