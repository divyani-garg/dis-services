package sgsits.cse.dis.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.message.response.FacultyBriefData;
import sgsits.cse.dis.user.message.response.FacultyData;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.repo.StaffRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Staff(Teaching & Non-Teaching Both) Resource")
public class StaffController {
	
	@Autowired
	StaffRepository staffRepository;
	
	JwtResolver jwtResolver = new JwtResolver();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@ApiOperation(value = "Faculty Brief Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/facultyBrief", method = RequestMethod.GET)
	public List<FacultyBriefData> getFacultyBreifData() {
		List<StaffProfile> facultyData = staffRepository.findByClasssOrClasssOrderByCurrentDesignation("I", "II");

		List<FacultyBriefData> facultyBriefData = new ArrayList<>();

		for (StaffProfile faculty : facultyData) {
			FacultyBriefData fbd = new FacultyBriefData();
			fbd.setId(faculty.getId());
			fbd.setName(faculty.getName());
			fbd.setNameAcronym(faculty.getNameAcronym());
			fbd.setCurrentDesignation(faculty.getCurrentDesignation());
			fbd.setEmail(faculty.getEmail());
			// fbd.setProfilePicture(faculty.getProfilePicture());
			facultyBriefData.add(fbd);
		}
		return facultyBriefData;
	}

	@ApiOperation(value = "Staff Brief Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/staffBrief", method = RequestMethod.GET)
	public List<FacultyBriefData> getStaffBriefData() {
		List<StaffProfile> facultyData = staffRepository.findByClasssOrClasssOrderByCurrentDesignation("III", "IV");
		List<FacultyBriefData> facultyBriefData = new ArrayList<>();
		for (StaffProfile faculty : facultyData) {
			FacultyBriefData fbd = new FacultyBriefData();
			fbd.setId(faculty.getId());
			fbd.setName(faculty.getName());
			fbd.setNameAcronym(faculty.getNameAcronym());
			fbd.setCurrentDesignation(faculty.getCurrentDesignation());
			fbd.setEmail(faculty.getEmail());
			// fbd.setProfilePicture(faculty.getProfilePicture());
			facultyBriefData.add(fbd);
		}
		return facultyBriefData;
	}

	@ApiOperation(value = "Faculty Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/facultyData", method = RequestMethod.GET)
	public List<FacultyData> getFacultyData() {
		List<StaffProfile> Data = staffRepository.findByClasssOrClasssOrderByCurrentDesignation("I", "II");
		List<FacultyData> facultyData = new ArrayList<>();
		for (StaffProfile faculty : Data) {
			FacultyData fd = new FacultyData();
			fd.setId(faculty.getId());
			fd.setName(faculty.getName());
			fd.setNameAcronym(faculty.getNameAcronym());
			fd.setCurrentDesignation(faculty.getCurrentDesignation());
			fd.setEmail(faculty.getEmail());
			fd.setMobileNo(faculty.getMobileNo());
			fd.setAlternateMobileNo(faculty.getAlternateMobileNo());
			// fbd.setProfilePicture(faculty.getProfilePicture());
			facultyData.add(fd);
		}
		return facultyData;
	}

	@ApiOperation(value = "Staff Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/staffData", method = RequestMethod.GET)
	public List<FacultyData> getStaffData() {
		List<StaffProfile> Data = staffRepository.findByClasssOrClasssOrderByCurrentDesignation("III", "IV");
		List<FacultyData> staffData = new ArrayList<>();
		for (StaffProfile faculty : Data) {
			FacultyData fd = new FacultyData();
			fd.setId(faculty.getUserId());
			fd.setName(faculty.getName());
			fd.setNameAcronym(faculty.getNameAcronym());
			fd.setCurrentDesignation(faculty.getCurrentDesignation());
			fd.setEmail(faculty.getEmail());
			fd.setMobileNo(faculty.getMobileNo());
			fd.setAlternateMobileNo(faculty.getAlternateMobileNo());
			// fbd.setProfilePicture(faculty.getProfilePicture());
			staffData.add(fd);
		}
		return staffData;
	}

	/*@ApiOperation(value = "Add New Staff", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addNewStaff", method = RequestMethod.POST)
	public void addNewStaff(@RequestBody AddNewStaffForm addNewStaffForm, HttpServletRequest request) {
		
	}*/
	
}
