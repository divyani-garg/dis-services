package sgsits.cse.dis.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.feign.AcademicsClient;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.message.request.StaffBasicProfileForm;
import sgsits.cse.dis.user.message.request.StudentBasicProfileForm;
import sgsits.cse.dis.user.message.response.FacultyBriefData;
import sgsits.cse.dis.user.message.response.FacultyData;
import sgsits.cse.dis.user.message.response.ResponseMessage;
import sgsits.cse.dis.user.message.response.StaffBasicProfileResponse;
import sgsits.cse.dis.user.message.response.StudentBasicProfileResponse;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.model.UserAddress;
import sgsits.cse.dis.user.model.UserCompetitiveExams;
import sgsits.cse.dis.user.model.UserCulturalActivityAchievements;
import sgsits.cse.dis.user.model.UserIntenship;
import sgsits.cse.dis.user.model.UserProjects;
import sgsits.cse.dis.user.model.UserQualification;
import sgsits.cse.dis.user.model.UserResearchWork;
import sgsits.cse.dis.user.model.UserTechnicalActivity;
import sgsits.cse.dis.user.model.UserWorkExperience;
import sgsits.cse.dis.user.repo.StaffRepository;
import sgsits.cse.dis.user.repo.StudentRepository;
import sgsits.cse.dis.user.repo.UserAddressRepository;
import sgsits.cse.dis.user.repo.UserCompetitiveExamsRepository;
import sgsits.cse.dis.user.repo.UserCulturalActivityAchievementsRepository;
import sgsits.cse.dis.user.repo.UserInternshipRepository;
import sgsits.cse.dis.user.repo.UserProjectsRepository;
import sgsits.cse.dis.user.repo.UserQualificationRepository;
import sgsits.cse.dis.user.repo.UserResearchWorkRepository;
import sgsits.cse.dis.user.repo.UserTechnicalActivityRepository;
import sgsits.cse.dis.user.repo.UserWorkExperienceRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "User Resource")
public class UserController {

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
	@Autowired
	UserTechnicalActivityRepository userTechnicalActivityRepository;
	@Autowired
	UserCulturalActivityAchievementsRepository userCulturalActivityAchievementsRepository;
	@Autowired
	UserCompetitiveExamsRepository userCompetitiveExamsRepository;
	@Autowired
	UserProjectsRepository userProjectsRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	AcademicsClient academicsClient;

	JwtResolver jwtResolver = new JwtResolver();

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

	@ApiOperation(value = "User Address", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userAddress", method = RequestMethod.GET)
	public List<UserAddress> getUserAddress(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserAddress> address = userAddressRepository.findByUserId(id);
		return address;
	}

	/*
	 * @ApiOperation(value = "Edit User Address", response = Object.class,
	 * httpMethod = "POST", produces = "application/json")
	 * 
	 * @RequestMapping(value = "/editUserAddress", method = RequestMethod.POST)
	 * public ResponseEntity<?> editUserAddress(@RequestBody List<UserAddressForm>
	 * userAddressForm, HttpServletRequest request) { long id =
	 * jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
	 * if(userAddressForm.get(0).getUserId() == id){ List<UserAddress> address =
	 * userAddressRepository.findByUserId(id);
	 * 
	 * return new ResponseEntity<>(new
	 * ResponseMessage("Profile Updated Successfully!"), HttpStatus.OK); } else
	 * return new ResponseEntity<>(new
	 * ResponseMessage("You are not allowed to update!"), HttpStatus.BAD_REQUEST); }
	 */

	@ApiOperation(value = "User Qualification", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userQualification", method = RequestMethod.GET)
	public List<UserQualification> getUserQualification(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserQualification> qualification = userQualificationRepository.findByUserId(id);
		// marksheet
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
		// pdf
		return research;
	}

	@ApiOperation(value = "User Internship", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userInternship", method = RequestMethod.GET)
	public List<UserIntenship> getUserInternship(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserIntenship> internship = userInternshipRepository.findByUserId(id);
		// certificate
		return internship;
	}

	@ApiOperation(value = "User Technical Acitvity", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userTechnicalActivity", method = RequestMethod.GET)
	public List<UserTechnicalActivity> getUserTechnicalActivity(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserTechnicalActivity> technical = userTechnicalActivityRepository.findByUserId(id);
		return technical;
	}

	@ApiOperation(value = "User Cultural Activity Achievements", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userCulturalActivityAchievements", method = RequestMethod.GET)
	public List<UserCulturalActivityAchievements> getUserCulturalActivityAchievements(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserCulturalActivityAchievements> cultural = userCulturalActivityAchievementsRepository.findByUserId(id);
		// certificate
		return cultural;
	}

	@ApiOperation(value = "User Competitive Exams", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userCompetitiveExams", method = RequestMethod.GET)
	public List<UserCompetitiveExams> getUserCompetitiveExams(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserCompetitiveExams> exams = userCompetitiveExamsRepository.findByUserId(id);
		// score card
		return exams;
	}

	@ApiOperation(value = "User Project", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userProject", method = RequestMethod.GET)
	public List<UserProjects> getUserProject(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<UserProjects> projects = userProjectsRepository.findByUserId(id);
		return projects;
	}

	@ApiOperation(value = "Staff Basic Profile Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/staffBasicProfile", method = RequestMethod.GET)
	public StaffBasicProfileResponse getStaffBasicProfile(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		Optional<StaffProfile> staffProfile = staffRepository.findByUserId(id);
		StaffBasicProfileResponse sbpr = new StaffBasicProfileResponse();
		sbpr.setUserId(staffProfile.get().getUserId());
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
		return sbpr;
	}

	@ApiOperation(value = "Edit Staff Basic Profile Data", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/editStaffBasicProfile", method = RequestMethod.POST)
	public ResponseEntity<?> editStaffBasicProfile(@Valid @RequestBody StaffBasicProfileForm staffBasicProfileForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (staffBasicProfileForm.getUserId() == id) {
			Optional<StaffProfile> staffProfile = staffRepository.findByUserId(id);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			staffProfile.get().setModifiedBy(id);
			staffProfile.get().setModifiedDate(simpleDateFormat.format(new Date()));
			staffProfile.get().setDob(staffBasicProfileForm.getDob());
			staffProfile.get().setAlternateMobileNo(staffBasicProfileForm.getAlternateMobileNo());
			staffProfile.get().setAreaOfSpecialization(staffBasicProfileForm.getAreaOfSpecialization());
			staffProfile.get().setBloodGroup(staffBasicProfileForm.getBloodGroup());
			staffProfile.get().setFatherName(staffBasicProfileForm.getFatherName());
			staffProfile.get().setMobileNo(staffBasicProfileForm.getMobileNo());
			staffProfile.get().setMotherName(staffBasicProfileForm.getMotherName());
			staffRepository.save(staffProfile.get());
			return new ResponseEntity<>(new ResponseMessage("Profile Updated Successfully!"), HttpStatus.OK);
		} else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update!"), HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Staff Duties", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/staffDuties", method = RequestMethod.GET)
	public void getStaffDuties(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));

	}

	@ApiOperation(value = "Student Basic Profile Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/studentBasicProfile", method = RequestMethod.GET)
	public StudentBasicProfileResponse getStudentBasicProfile(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		Optional<StudentProfile> studentProfile = studentRepository.findByUserId(id);
		StudentBasicProfileResponse sbpr = new StudentBasicProfileResponse();
		sbpr.setUserId(studentProfile.get().getUserId());
		sbpr.setEnrollmentId(studentProfile.get().getEnrollmentId());
		sbpr.setName(studentProfile.get().getFullName());
		sbpr.setMobileNo(studentProfile.get().getMobileNo());
		sbpr.setEmail(studentProfile.get().getEmail());
		sbpr.setDob(studentProfile.get().getDob());
		sbpr.setFatherName(studentProfile.get().getFatherName());
		sbpr.setFatherContact(studentProfile.get().getFatherContact());
		sbpr.setFatherEmail(studentProfile.get().getFatherEmail());
		sbpr.setMotherName(studentProfile.get().getMotherName());
		sbpr.setMotherContact(studentProfile.get().getMotherContact());
		sbpr.setMotherEmail(studentProfile.get().getMotherEmail());
		sbpr.setCategory(studentProfile.get().getCategory());
		sbpr.setGender(studentProfile.get().getGender());
		sbpr.setBloodGroup(studentProfile.get().getBloodGroup());
		String courseYearSession = academicsClient.getCoursename(studentProfile.get().getCourseId()) + "-"+ studentProfile.get().getAdmissionYear() + " Batch";
		sbpr.setCourseYearSession(courseYearSession);
		return sbpr;
	}

	@ApiOperation(value = "Edit Student Basic Profile Data", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/editStudentBasicProfile", method = RequestMethod.POST)
	public ResponseEntity<?> editStudentBasicProfile(
			@Valid @RequestBody StudentBasicProfileForm studentBasicProfileForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (studentBasicProfileForm.getUserId() == id) {
			Optional<StudentProfile> studentProfile = studentRepository.findByUserId(id);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			studentProfile.get().setModifiedBy(id);
			studentProfile.get().setModifiedDate(simpleDateFormat.format(new Date()));
			if (studentBasicProfileForm.getMotherContact() != 0)
				studentProfile.get().setMotherContact(studentBasicProfileForm.getMotherContact());
			if (studentBasicProfileForm.getMotherEmail() != null)
				studentProfile.get().setMotherEmail(studentBasicProfileForm.getMotherEmail());
			if (studentBasicProfileForm.getFatherContact() != 0)
				studentProfile.get().setFatherContact(studentBasicProfileForm.getFatherContact());
			if (studentBasicProfileForm.getFatherEmail() != null)
				studentProfile.get().setFatherEmail(studentBasicProfileForm.getFatherEmail());
			if (studentBasicProfileForm.getBloodGroup() != null)
				studentProfile.get().setBloodGroup(studentBasicProfileForm.getBloodGroup());
			if (studentBasicProfileForm.getMobileNo() != 0)
				studentProfile.get().setMobileNo(studentBasicProfileForm.getMobileNo());
			studentRepository.save(studentProfile.get());
			return new ResponseEntity<>(new ResponseMessage("Profile Updated Successfully!"), HttpStatus.OK);
		} else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update!"), HttpStatus.BAD_REQUEST);
	}

	public void getStudentPlacement(HttpServletRequest request) {

	}

	public void getUGProject(HttpServletRequest request) {

	}

	public void getPGProject(HttpServletRequest request) {

	}

}