package sgsits.cse.dis.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.presentation.FacultyBriefData;
import sgsits.cse.dis.user.repo.StaffRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dis/faculty")
@Api(value = "Faculty Resource")
public class FacultyController {
	
	@Autowired
	StaffRepository staffRepository;
	
	@ApiOperation(value = "FacultyBriefData", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/brief", method = RequestMethod.GET)
	public List<FacultyBriefData> getFacultyBreifData()
	{
		List<StaffProfile> facultyData = staffRepository.findByClasssOrClasssOrderByCurrentDesignation("I","II");
		
		List<FacultyBriefData> facultyBriefData = new ArrayList<>();   
		
		for(StaffProfile faculty : facultyData)
		{
			FacultyBriefData fbd = new FacultyBriefData();
			fbd.setName(faculty.getName());
			fbd.setNameAcronym(faculty.getNameAcronym());
			fbd.setCurrentDesignation(faculty.getCurrentDesignation());
			fbd.setEmail(faculty.getEmail());
			fbd.setProfilePicture(faculty.getProfilePicture());
			facultyBriefData.add(fbd);
		}
		
		return facultyBriefData;
	}
}
