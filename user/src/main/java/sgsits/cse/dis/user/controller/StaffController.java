package sgsits.cse.dis.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.message.response.FacultyBriefData;
import sgsits.cse.dis.user.message.response.FacultyData;
import sgsits.cse.dis.user.message.response.StaffBasicProfileResponse;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.UserAddress;
import sgsits.cse.dis.user.model.UserIntenship;
import sgsits.cse.dis.user.model.UserQualification;
import sgsits.cse.dis.user.model.UserResearchWork;
import sgsits.cse.dis.user.model.UserWorkExperience;
import sgsits.cse.dis.user.repo.StaffRepository;
import sgsits.cse.dis.user.repo.UserAddressRepository;
import sgsits.cse.dis.user.repo.UserInternshipRepository;
import sgsits.cse.dis.user.repo.UserQualificationRepository;
import sgsits.cse.dis.user.repo.UserResearchWorkRepository;
import sgsits.cse.dis.user.repo.UserWorkExperienceRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Staff Resource")
public class StaffController {

	@Autowired
	StaffRepository staffRepository;
	@Autowired
	UserAddressRepository userAddressRepository;
	@Autowired
	UserQualificationRepository userQualificationRepository;
	@Autowired
	UserWorkExperienceRepository userWorkExperienceRepository;
	@Autowired
	UserResearchWorkRepository userResearchWorkRepository;
	@Autowired
	UserInternshipRepository userInternshipRepository;

	JwtResolver jwtResolver = new JwtResolver();
	
	@ApiOperation(value = "Faculty Brief Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/facultyBrief", method = RequestMethod.GET)
	public List<FacultyBriefData> getFacultyBreifData() {
		List<StaffProfile> facultyData = staffRepository.findByClasssOrClasssOrderByCurrentDesignation("I", "II");
		
		List<FacultyBriefData> facultyBriefData = new ArrayList<>();
		
		for (StaffProfile faculty : facultyData) {
			FacultyBriefData fbd = new FacultyBriefData();
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
	public List<FacultyData> getFacultyData(){
		List<StaffProfile> Data = staffRepository.findByClasssOrClasssOrderByCurrentDesignation("I", "II");
		List<FacultyData> facultyData = new ArrayList<>();
		for (StaffProfile faculty : Data) {
			FacultyData fd = new FacultyData();
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

	@ApiOperation(value = "User Qualification", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userQualification", method = RequestMethod.GET)
	public List<UserQualification> getUserQualification(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserQualification> qualification = userQualificationRepository.findByUserId(id);
		//marksheet
		return qualification;
	}

	@ApiOperation(value = "User Work Experience", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userWorkExperience", method = RequestMethod.GET)
	public List<UserWorkExperience> getUserWorkExperience(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserWorkExperience> experience = userWorkExperienceRepository.findByUserId(id);
		return experience;
	}

	@ApiOperation(value = "User Research Work", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userResearchWork", method = RequestMethod.GET)
	public List<UserResearchWork> geUserResearchWork(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserResearchWork> research = userResearchWorkRepository.findByUserId(id); 
		return research;
	}

	@ApiOperation(value = "User Internship", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userInternship", method = RequestMethod.GET)
	public List<UserIntenship> getUserInternship(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserIntenship> internship = userInternshipRepository.findByUserId(id);
		return internship;
	}

	@ApiOperation(value = "User Technical Acitvity", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userTechnicalActivity", method = RequestMethod.GET)
	public void getUserTechnicalActivity(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		
	}

	@ApiOperation(value = "User Cultural Activity Achievements", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userCulturalActivityAchievements", method = RequestMethod.GET)
	public void getUserCulturalActivityAchievements(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
	}

	@ApiOperation(value = "User Competitive Exams", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userCompetitiveExams", method = RequestMethod.GET)
	public void getUserCompetitiveExams(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
	}

	@ApiOperation(value = "User Project", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userProject", method = RequestMethod.GET)
	public void getUserProject(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
	}

	@ApiOperation(value = "Staff Basic Profile Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/staffBasicProfile", method = RequestMethod.GET)
	public StaffBasicProfileResponse getStaffBasicProfile(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		Optional<StaffProfile> staffProfile = staffRepository.findByUserId(id);
		List<UserAddress> address = userAddressRepository.findByUserId(id);
		StaffBasicProfileResponse sbpr = new StaffBasicProfileResponse();
	
		sbpr.setEmployeeId(staffProfile.get().getEmployeeId());
		sbpr.setName(staffProfile.get().getName());
		sbpr.setNameAcronym(staffProfile.get().getNameAcronym());
		sbpr.setCurrentDesignation(staffProfile.get().getCurrentDesignation());
		sbpr.setEmail(staffProfile.get().getEmail());
		sbpr.setDob(staffProfile.get().getDob());
		sbpr.setBloodGroup(staffProfile.get().getBloodGroup());
		sbpr.setGender(staffProfile.get().getGender());
		sbpr.setMotherName(staffProfile.get().getMotherName());
		sbpr.setFatherName(staffProfile.get().getFatherName());
		sbpr.setMobileNo(staffProfile.get().getMobileNo());
		sbpr.setAlternateMobileNo(staffProfile.get().getAlternateMobileNo());
		sbpr.setAreaOfSpecialization(staffProfile.get().getAreaOfSpecialization());

		for (UserAddress add : address) {
			if (add.getType().equals("Permanent")) {
				sbpr.setPermanentAddress(add.getType() + " Address: " + add.getAddressLine1() +","+ add.getAddressLine2()
						+","+ add.getCity() +","+ add.getState() +","+ add.getCountry() +"-"+ add.getPincode());
			}
			if (add.getType().equals("Present")) {
				sbpr.setPresentAddress(add.getType() + "Address: " + add.getAddressLine1() +","+ add.getAddressLine2()
				+","+ add.getCity() +","+ add.getState() +","+ add.getCountry() +"-"+ add.getPincode());
			}
		}
		return sbpr;
	}

	public void getStaffDuties(HttpServletRequest request) {

	}

	public void getStudentBasicProfile(HttpServletRequest request) {

	}

	public void getStudentPlacement(HttpServletRequest request) {

	}

	public void getUGProject(HttpServletRequest request) {

	}

	public void getPGProject(HttpServletRequest request) {

	}

}