package sgsits.cse.dis.user.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import sgsits.cse.dis.user.message.response.StaffBasicProfileResponse;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.repo.StaffRepository;

@Controller
@RequestMapping
@CrossOrigin(origins = "*")
@Api(value = "Profile Resource")
public class TestController {
	
	@Autowired
	StaffRepository staffRepository;
	
//	public static  List<StaffProfile> staffProfile;
//
//	public void assignData() {
//		staffProfile = staffRepository.findAll();	
//	}
//	
	@RequestMapping(value = "/getTestData", method = RequestMethod.GET)
	public java.util.List<StaffBasicProfileResponse> generateCollection() 
	{
			
//		new TestController().assignData();
		
		 List<StaffProfile> staffProfile  = staffRepository.findAll();
			
		java.util.List<StaffBasicProfileResponse> vect = new  ArrayList<>();
		
		
		
//		vect.add( new StaffBasicProfileResponse(
//				sf.getUserId(),
//				sf.getEmployeeId(),
//				sf.getName(),
//				sf.getNameAcronym(),
//				sf.getCurrentDesignation(),
//				sf.getClasss(),
//     			sf.getType(),
//				sf.getEmail(),
//				sf.getDob(),
//				sf.getBloodGroup(),
//				sf.getGender(),
//				sf.getMotherName(),
//				sf.getFatherName(),
//				sf.getMobileNo(),
//				sf.getAlternateMobileNo(),
//				sf.getAreaOfSpecialization()));

			for(StaffProfile sf : staffProfile) {
				StaffBasicProfileResponse staffBasicProfileResponse = new StaffBasicProfileResponse();
				
				staffBasicProfileResponse.setUserId(sf.getUserId());
				staffBasicProfileResponse.setEmployeeId(sf.getEmployeeId());
				staffBasicProfileResponse.setName(sf.getName());
				staffBasicProfileResponse.setNameAcronym(sf.getNameAcronym());
				staffBasicProfileResponse.setCurrentDesignation(sf.getCurrentDesignation());
				staffBasicProfileResponse.setEmail(sf.getEmail());
				staffBasicProfileResponse.setDob(sf.getDob());
				staffBasicProfileResponse.setBloodGroup(sf.getBloodGroup());
				staffBasicProfileResponse.setGender(sf.getGender());
				staffBasicProfileResponse.setMotherName(sf.getMotherName());
				staffBasicProfileResponse.setFatherName(sf.getFatherName());
				staffBasicProfileResponse.setMobileNo(sf.getMobileNo());
				staffBasicProfileResponse.setAlternateMobileNo(sf.getAlternateMobileNo());
				staffBasicProfileResponse.setAreaOfSpecialization(sf.getAreaOfSpecialization());
			}
		
		
				//vect.add(new StaffBasicProfileResponse(3l,"23","ABC","DEF","HOD","abc@gmail.com","18-02-18","o+","M","HIJ","klm",789456123l,147852369l,"AI"));
		
		return vect;
	}
}
