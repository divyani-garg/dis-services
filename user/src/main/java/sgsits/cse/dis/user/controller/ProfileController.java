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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import sgsits.cse.dis.administration.model.Books;
//import sgsits.cse.dis.administration.response.BookResponse;
import sgsits.cse.dis.user.feign.AcademicsClient;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.message.request.ProfileForm;
import sgsits.cse.dis.user.message.request.StaffBasicProfileForm;
import sgsits.cse.dis.user.message.request.StudentBasicProfileForm;
import sgsits.cse.dis.user.message.request.UserAddressForm;
import sgsits.cse.dis.user.message.request.UserCompetitiveExamsForm;
import sgsits.cse.dis.user.message.request.UserCulturalActivityAchievementsForm;
import sgsits.cse.dis.user.message.request.UserInternshipForm;
import sgsits.cse.dis.user.message.request.UserProjectForm;
import sgsits.cse.dis.user.message.response.ResponseMessage;
import sgsits.cse.dis.user.message.response.StaffBasicProfileResponse;
import sgsits.cse.dis.user.message.response.StudentBasicProfileResponse;
import sgsits.cse.dis.user.message.response.UserResearchWorkResponse;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.model.UserAddress;
import sgsits.cse.dis.user.model.UserCompetitiveExams;
import sgsits.cse.dis.user.model.UserCulturalActivityAchievements;
import sgsits.cse.dis.user.model.UserInternship;
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
import sgsits.cse.dis.user.message.request.UserQualificationForm;
import sgsits.cse.dis.user.message.request.UserResearchWorkForm;
import sgsits.cse.dis.user.message.request.UserTechnicalActivityForm;
import sgsits.cse.dis.user.message.request.UserWorkExperienceForm;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Profile Resource")
public class ProfileController {

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
	
	@ApiOperation(value = "User Address", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userAddress", method = RequestMethod.GET)
	public List<UserAddress> getUserAddress(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));	
		}
		else
		{
			id=profileForm.getId();
		}
		List<UserAddress> address = userAddressRepository.findByUserId(id);
		return address;
		
	}

	
	  @ApiOperation(value = "Edit User Address", response = Object.class, httpMethod = "PUT", produces = "application/json") 
	  @RequestMapping(value = "/editUserAddress", method = RequestMethod.PUT)
	  public ResponseEntity<?> editUserAddress(@RequestBody List<UserAddressForm> userAddressForm, HttpServletRequest request) { 
	  long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
	  if(userAddressForm.get(0).getUserId() == id) { 
		 List<UserAddress> address = userAddressRepository.findByUserId(id);
		 int flag=0;
		 for(UserAddress userAddress : address)
		 {
			 for(UserAddressForm form : userAddressForm)
			 {
				 if(userAddress.getType().equals(form.getType()))
				 {
					 Optional<UserAddress> optional = userAddressRepository.findByUserIdAndType(id,userAddress.getType());
					 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					 optional.get().setModifiedDate(simpleDateFormat.format(new Date()));
					 optional.get().setModifiedBy(id);
					 optional.get().setAddressLine1(form.getAddressLine1());
					 optional.get().setAddressLine2(form.getAddressLine2());
					 optional.get().setState(form.getCity());
					 optional.get().setCity(form.getCity());
					 optional.get().setCountry(form.getCountry());
					 optional.get().setPincode(form.getPincode());
					 UserAddress test = userAddressRepository.save(optional.get());
					 if(test!=null)
						 flag=1;
					 
				 }
				 if(!userAddressRepository.existsByUserIdAndType(id, form.getType()))
				 {
					 UserAddress newAddress = new UserAddress();
					 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					 newAddress.setCreatedDate(simpleDateFormat.format(new Date()));
					 newAddress.setCreatedBy(id);
					 newAddress.setUserId(id);
					 newAddress.setAddressLine1(form.getAddressLine1());
					 newAddress.setAddressLine2(form.getAddressLine2());
					 newAddress.setState(form.getState());
					 newAddress.setCity(form.getCity());
					 newAddress.setCountry(form.getCountry());
					 newAddress.setPincode(form.getPincode());
					 newAddress.setType(form.getType());
					 UserAddress test = userAddressRepository.save(newAddress);
					 if(test!=null)
						 flag=1;
				 }
			 }
		 }
		 if(flag==1)
			 return new ResponseEntity<>(new ResponseMessage("Address Updated Successfully!"), HttpStatus.OK); 
		 else
			 return new ResponseEntity<>(new ResponseMessage("Unable to update Address, please try again later!"), HttpStatus.BAD_REQUEST); 
	  } 
	  else
	   return new ResponseEntity<>(new ResponseMessage("You are not allowed to update!"), HttpStatus.BAD_REQUEST); 
	 }
	 

	@ApiOperation(value = "User Qualification", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userQualification", method = RequestMethod.GET)
	public List<UserQualification> getUserQualification(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		}
		else
		{
			id=profileForm.getId();
		}
		List<UserQualification> qualification = userQualificationRepository.findByUserId(id);
		// marksheet
		return qualification;
	}
	
	@ApiOperation(value = "Add User Qualification", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addUserQualification", method = RequestMethod.POST)
	public ResponseEntity<?> addUserQualification(@RequestBody UserQualificationForm userQualificationForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userQualificationForm.getUserId()==id)
		{
			UserQualification userQualification = new UserQualification();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			userQualification.setCreatedDate(simpleDateFormat.format(new Date()));
			userQualification.setUserId(id);
			userQualification.setCreatedBy(id);
			userQualification.setDegreeCertificate(userQualificationForm.getDegreeCertificate());
			userQualification.setYearOfPassing(userQualificationForm.getYearOfPassing());
			userQualification.setCollegeSchool(userQualificationForm.getCollegeSchool());
			userQualification.setUniversityBoard(userQualificationForm.getUniversityBoard());
			userQualification.setPercentageCgpa(userQualificationForm.getPercentageCgpa());
			userQualification.setSpecialization(userQualificationForm.getSpecialization());
			//marksheet
			UserQualification test = userQualificationRepository.save(userQualification);
			if(test!=null)
				return new ResponseEntity<>(new ResponseMessage("User Qualification added successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to add User Qualification, please try again later!"), HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to add User Qualification!"), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Edit User Qualification", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editUserQualification", method = RequestMethod.PUT)
	public ResponseEntity<?> editUserQualification(@RequestBody UserQualificationForm userQualificationForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userQualificationForm.getUserId()==id)
		{
			if(userQualificationRepository.existsByUserIdAndDegreeCertificate(id,userQualificationForm.getDegreeCertificate()))
			{
				Optional<UserQualification> userQualification = userQualificationRepository.findByUserIdAndDegreeCertificate(id,userQualificationForm.getDegreeCertificate());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				userQualification.get().setModifiedDate(simpleDateFormat.format(new Date()));
				userQualification.get().setUserId(id);
				userQualification.get().setModifiedBy(id);
				userQualification.get().setDegreeCertificate(userQualificationForm.getDegreeCertificate());
				userQualification.get().setYearOfPassing(userQualificationForm.getYearOfPassing());
				userQualification.get().setCollegeSchool(userQualificationForm.getCollegeSchool());
				userQualification.get().setUniversityBoard(userQualificationForm.getUniversityBoard());
				userQualification.get().setPercentageCgpa(userQualificationForm.getPercentageCgpa());
				userQualification.get().setSpecialization(userQualificationForm.getSpecialization());
				//marksheet
				UserQualification test = userQualificationRepository.save(userQualification.get());
				if(test!=null)
					return new ResponseEntity<>(new ResponseMessage("User Qualification updated successfully!"), HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to update User Qualification, please try again later!"), HttpStatus.BAD_REQUEST);
			}
			else
				return new ResponseEntity<>(new ResponseMessage("User Qualification not found, you need to add it first!"), HttpStatus.BAD_REQUEST);
				
			
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update User Qualification!"), HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "User Work Experience", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userWorkExperience", method = RequestMethod.GET)
	public List<UserWorkExperience> getUserWorkExperience(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));	
		}
		else {
			id=profileForm.getId();
		}
		List<UserWorkExperience> experience = userWorkExperienceRepository.findByUserId(id);
		return experience;
	}
	
	@ApiOperation(value = "Add User Work Experience", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addUserWorkExperience", method = RequestMethod.POST)
	public ResponseEntity<?> addUserWorkExperience(@RequestBody UserWorkExperienceForm userWorkExperienceForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userWorkExperienceForm.getUserId()==id)
		{
			UserWorkExperience userWorkExperience = new UserWorkExperience();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			userWorkExperience.setCreatedDate(simpleDateFormat.format(new Date()));
			userWorkExperience.setUserId(id);
			userWorkExperience.setCreatedBy(id);
			userWorkExperience.setOrganizationName(userWorkExperienceForm.getOrganizationName());
			userWorkExperience.setDesignation(userWorkExperienceForm.getDesignation());
			//to be reviewed
			userWorkExperience.setDateOfJoining(userWorkExperienceForm.getDateOfJoining());
			userWorkExperience.setDateOfLeaving(userWorkExperienceForm.getDateOfLeaving());
			userWorkExperience.setPayscale(userWorkExperienceForm.getPayscale());
			userWorkExperience.setCountry(userWorkExperienceForm.getCountry());
			userWorkExperience.setState(userWorkExperienceForm.getState());
			userWorkExperience.setCity(userWorkExperienceForm.getCity());
			UserWorkExperience test = userWorkExperienceRepository.save(userWorkExperience);
			if(test!=null)
				return new ResponseEntity<>(new ResponseMessage("User Work Experience added successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to add User Work Experience, please try again later!"), HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to add User Work Experience!"), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Edit User Work Experience", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editUserWorkExperience", method = RequestMethod.PUT)
	public ResponseEntity<?> editUserWorkExperience(@RequestBody UserWorkExperienceForm userWorkExperienceForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userWorkExperienceForm.getUserId()==id)
		{
			if(userWorkExperienceRepository.existsByUserIdAndOrganizationName(id,userWorkExperienceForm.getOrganizationName()))
			{
				Optional<UserWorkExperience> userWorkExperience = userWorkExperienceRepository.findByUserIdAndOrganizationName(id,userWorkExperienceForm.getOrganizationName());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				userWorkExperience.get().setModifiedDate(simpleDateFormat.format(new Date()));
				userWorkExperience.get().setUserId(id);
				userWorkExperience.get().setModifiedBy(id);
				userWorkExperience.get().setOrganizationName(userWorkExperienceForm.getOrganizationName());
				userWorkExperience.get().setDesignation(userWorkExperienceForm.getDesignation());
				userWorkExperience.get().setDateOfJoining(userWorkExperienceForm.getDateOfJoining());
				userWorkExperience.get().setDateOfLeaving(userWorkExperienceForm.getDateOfLeaving());
				userWorkExperience.get().setPayscale(userWorkExperienceForm.getPayscale());
				userWorkExperience.get().setCountry(userWorkExperienceForm.getCountry());
				userWorkExperience.get().setState(userWorkExperienceForm.getState());
				userWorkExperience.get().setCity(userWorkExperienceForm.getCity());
				UserWorkExperience test = userWorkExperienceRepository.save(userWorkExperience.get());
				if(test!=null)
					return new ResponseEntity<>(new ResponseMessage("User Work Experience updated successfully!"), HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to update User Work Experience, please try again later!"), HttpStatus.BAD_REQUEST);
			}
			else
				return new ResponseEntity<>(new ResponseMessage("User Work Experience not found, you need to add it first!"), HttpStatus.BAD_REQUEST);
				
			
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update User Work Experience!"), HttpStatus.BAD_REQUEST);
	}
	
	

	@ApiOperation(value = "User Research Work", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userResearchWork", method = RequestMethod.GET)
	public List<UserResearchWork> geUserResearchWork(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		}
		else {
			id=profileForm.getId();
		}
		List<UserResearchWork> research = userResearchWorkRepository.findByUserId(id);
		// pdf
		return research;
	}
	
	@ApiOperation(value = "Show Research Paper", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/showResearchPaper", method = RequestMethod.GET) //From Library
	public List<UserResearchWorkResponse> showResearchPaper() {
		List<UserResearchWork> researchWorks = userResearchWorkRepository.findAll();
		if (!researchWorks.isEmpty()) {
			List<UserResearchWorkResponse> result = new ArrayList<>();
			for (UserResearchWork researchWork : researchWorks) {
				UserResearchWorkResponse res = new UserResearchWorkResponse();
				res.setTitle(researchWork.getTitle());
				res.setCategory(researchWork.getCategory());
				res.setSubCategory(researchWork.getSubcategory());
				res.setJournalConferenceName(researchWork.getJournalConferenceName());
				res.setPublisher(researchWork.getPublisher());
				res.setYearOfPublication(researchWork.getYearOfPublication());
				res.setPdf(researchWork.getPdf());
				res.setAuthorOne(researchWork.getAuthorOne());
				res.setAuthorTwo(researchWork.getAuthorTwo());
				res.setOtherAuthors(researchWork.getOtherAuthors());
				res.setNoOfAuthors(researchWork.getNoOfAuthors());
				result.add(res);
			}
			return result;
		}
		return null;
	}
	
	@ApiOperation(value = "Search Research Paper By Keyword", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchResearchPaper/byTitle/{keyword}", method = RequestMethod.GET) //From Library
	public List<UserResearchWorkResponse> searchResearchPaperByTitle(@PathVariable String keyword) {
		List<UserResearchWork> researchWorks = userResearchWorkRepository.findAll();
		if (!researchWorks.isEmpty()) {
			String keyw=keyword.toLowerCase();
			List<UserResearchWorkResponse> result = new ArrayList<>();
			for (UserResearchWork researchWork : researchWorks) {
				if(researchWork.getTitle()!=null)
				{
					String title=researchWork.getTitle().toLowerCase();
					if(title.contains(keyw))
					{
						UserResearchWorkResponse res = new UserResearchWorkResponse();
						res.setTitle(researchWork.getTitle());
						res.setCategory(researchWork.getCategory());
						res.setSubCategory(researchWork.getSubcategory());
						res.setJournalConferenceName(researchWork.getJournalConferenceName());
						res.setPublisher(researchWork.getPublisher());
						res.setYearOfPublication(researchWork.getYearOfPublication());
						res.setPdf(researchWork.getPdf());
						res.setAuthorOne(researchWork.getAuthorOne());
						res.setAuthorTwo(researchWork.getAuthorTwo());
						res.setOtherAuthors(researchWork.getOtherAuthors());
						res.setNoOfAuthors(researchWork.getNoOfAuthors());
						result.add(res);
					}
				}
			}
			return result;
		}
		return null;
	}
	
	@ApiOperation(value = "Search Research Paper By Category", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchResearchPaper/byCategory/{keyword}", method = RequestMethod.GET) //From Library
	public List<UserResearchWorkResponse> searchResearchPaperByCategory(@PathVariable String keyword) {
		List<UserResearchWork> researchWorks = userResearchWorkRepository.findAll();
		if (!researchWorks.isEmpty()) {
			List<UserResearchWorkResponse> result = new ArrayList<>();
			for (UserResearchWork researchWork : researchWorks) {
				if(researchWork.getCategory()!=null)
				{
					if(researchWork.getCategory().equalsIgnoreCase(keyword))
					{
						UserResearchWorkResponse res = new UserResearchWorkResponse();
						res.setTitle(researchWork.getTitle());
						res.setCategory(researchWork.getCategory());
						res.setSubCategory(researchWork.getSubcategory());
						res.setJournalConferenceName(researchWork.getJournalConferenceName());
						res.setPublisher(researchWork.getPublisher());
						res.setYearOfPublication(researchWork.getYearOfPublication());
						res.setPdf(researchWork.getPdf());
						res.setAuthorOne(researchWork.getAuthorOne());
						res.setAuthorTwo(researchWork.getAuthorTwo());
						res.setOtherAuthors(researchWork.getOtherAuthors());
						res.setNoOfAuthors(researchWork.getNoOfAuthors());
						result.add(res);
					}
				}
			}
			return result;
		}
		return null;
	}
	
	@ApiOperation(value = "Search Research Paper By Subcategory", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchResearchPaper/bySubcategory/{keyword}", method = RequestMethod.GET) //From Library
	public List<UserResearchWorkResponse> searchResearchPaperBySubcategory(@PathVariable String keyword) {
		List<UserResearchWork> researchWorks = userResearchWorkRepository.findAll();
		if (!researchWorks.isEmpty()) {
			List<UserResearchWorkResponse> result = new ArrayList<>();
			for (UserResearchWork researchWork : researchWorks) {
				if(researchWork.getSubcategory()!=null)
				{
					if(researchWork.getSubcategory().equalsIgnoreCase(keyword))
					{
						UserResearchWorkResponse res = new UserResearchWorkResponse();
						res.setTitle(researchWork.getTitle());
						res.setCategory(researchWork.getCategory());
						res.setSubCategory(researchWork.getSubcategory());
						res.setJournalConferenceName(researchWork.getJournalConferenceName());
						res.setPublisher(researchWork.getPublisher());
						res.setYearOfPublication(researchWork.getYearOfPublication());
						res.setPdf(researchWork.getPdf());
						res.setAuthorOne(researchWork.getAuthorOne());
						res.setAuthorTwo(researchWork.getAuthorTwo());
						res.setOtherAuthors(researchWork.getOtherAuthors());
						res.setNoOfAuthors(researchWork.getNoOfAuthors());
						result.add(res);
					}
				}
			}
			return result;
		}
		return null;
	}
	
	@ApiOperation(value = "Search Research Paper By Publisher", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchResearchPaper/byPublisher/{keyword}", method = RequestMethod.GET) //From Library
	public List<UserResearchWorkResponse> searchResearchPaperByPublisher(@PathVariable String keyword) {
		List<UserResearchWork> researchWorks = userResearchWorkRepository.findAll();
		if (!researchWorks.isEmpty()) {
			List<UserResearchWorkResponse> result = new ArrayList<>();
			for (UserResearchWork researchWork : researchWorks) {
				if(researchWork.getPublisher()!=null)
				{
					if(researchWork.getPublisher().toLowerCase().contains(keyword.toLowerCase()))
					{
						UserResearchWorkResponse res = new UserResearchWorkResponse();
						res.setTitle(researchWork.getTitle());
						res.setCategory(researchWork.getCategory());
						res.setSubCategory(researchWork.getSubcategory());
						res.setJournalConferenceName(researchWork.getJournalConferenceName());
						res.setPublisher(researchWork.getPublisher());
						res.setYearOfPublication(researchWork.getYearOfPublication());
						res.setPdf(researchWork.getPdf());
						res.setAuthorOne(researchWork.getAuthorOne());
						res.setAuthorTwo(researchWork.getAuthorTwo());
						res.setOtherAuthors(researchWork.getOtherAuthors());
						res.setNoOfAuthors(researchWork.getNoOfAuthors());
						result.add(res);
					}
				}
			}
			return result;
		}
		return null;
	}
	
	@ApiOperation(value = "Search Research Paper By Author", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchResearchPaper/byAuthor/{keyword}", method = RequestMethod.GET) //From Library
	public List<UserResearchWorkResponse> searchResearchPaperByAuthor(@PathVariable String keyword) {
		List<UserResearchWork> researchWorks = userResearchWorkRepository.findAll();
		if (!researchWorks.isEmpty()) {
			List<UserResearchWorkResponse> result = new ArrayList<>();
			String keyw=keyword.toLowerCase();
			for (UserResearchWork researchWork : researchWorks) {
				if(researchWork.getAuthorOne()!=null)
				{
					String authorOne=researchWork.getAuthorOne().toLowerCase();
					if(researchWork.getAuthorTwo()!=null)
					{
						String authorTwo=researchWork.getAuthorTwo().toLowerCase();
						if(researchWork.getOtherAuthors()!=null)
						{
							String otherAuthors=researchWork.getOtherAuthors().toLowerCase();
							if(authorOne.contains(keyw)||authorTwo.contains(keyw)||otherAuthors.contains(keyw))
							{
								UserResearchWorkResponse res = new UserResearchWorkResponse();
								res.setTitle(researchWork.getTitle());
								res.setCategory(researchWork.getCategory());
								res.setSubCategory(researchWork.getSubcategory());
								res.setJournalConferenceName(researchWork.getJournalConferenceName());
								res.setPublisher(researchWork.getPublisher());
								res.setYearOfPublication(researchWork.getYearOfPublication());
								res.setPdf(researchWork.getPdf());
								res.setAuthorOne(researchWork.getAuthorOne());
								res.setAuthorTwo(researchWork.getAuthorTwo());
								res.setOtherAuthors(researchWork.getOtherAuthors());
								res.setNoOfAuthors(researchWork.getNoOfAuthors());
								result.add(res);
							}
						}
						else
						{
							if(authorOne.contains(keyw)||authorTwo.contains(keyw))
							{
								UserResearchWorkResponse res = new UserResearchWorkResponse();
								res.setTitle(researchWork.getTitle());
								res.setCategory(researchWork.getCategory());
								res.setSubCategory(researchWork.getSubcategory());
								res.setJournalConferenceName(researchWork.getJournalConferenceName());
								res.setPublisher(researchWork.getPublisher());
								res.setYearOfPublication(researchWork.getYearOfPublication());
								res.setPdf(researchWork.getPdf());
								res.setAuthorOne(researchWork.getAuthorOne());
								res.setAuthorTwo(researchWork.getAuthorTwo());
								res.setOtherAuthors(researchWork.getOtherAuthors());
								res.setNoOfAuthors(researchWork.getNoOfAuthors());
								result.add(res);
							}
						}
					}
					else
					{
						if(researchWork.getOtherAuthors()!=null)
						{
							String otherAuthors=researchWork.getOtherAuthors().toLowerCase();
							if(authorOne.contains(keyw)||otherAuthors.contains(keyw))
							{
								UserResearchWorkResponse res = new UserResearchWorkResponse();
								res.setTitle(researchWork.getTitle());
								res.setCategory(researchWork.getCategory());
								res.setSubCategory(researchWork.getSubcategory());
								res.setJournalConferenceName(researchWork.getJournalConferenceName());
								res.setPublisher(researchWork.getPublisher());
								res.setYearOfPublication(researchWork.getYearOfPublication());
								res.setPdf(researchWork.getPdf());
								res.setAuthorOne(researchWork.getAuthorOne());
								res.setAuthorTwo(researchWork.getAuthorTwo());
								res.setOtherAuthors(researchWork.getOtherAuthors());
								res.setNoOfAuthors(researchWork.getNoOfAuthors());
								result.add(res);
							}
						}
						else
						{
							if(authorOne.contains(keyw))
							{
								UserResearchWorkResponse res = new UserResearchWorkResponse();
								res.setTitle(researchWork.getTitle());
								res.setCategory(researchWork.getCategory());
								res.setSubCategory(researchWork.getSubcategory());
								res.setJournalConferenceName(researchWork.getJournalConferenceName());
								res.setPublisher(researchWork.getPublisher());
								res.setYearOfPublication(researchWork.getYearOfPublication());
								res.setPdf(researchWork.getPdf());
								res.setAuthorOne(researchWork.getAuthorOne());
								res.setAuthorTwo(researchWork.getAuthorTwo());
								res.setOtherAuthors(researchWork.getOtherAuthors());
								res.setNoOfAuthors(researchWork.getNoOfAuthors());
								result.add(res);
							}
						}
					}
				}
				else
				{
					if(researchWork.getAuthorTwo()!=null)
					{
						String authorTwo=researchWork.getAuthorTwo().toLowerCase();
						if(researchWork.getOtherAuthors()!=null)
						{
							String otherAuthors=researchWork.getOtherAuthors().toLowerCase();
							if(authorTwo.contains(keyw)||otherAuthors.contains(keyw))
							{
								UserResearchWorkResponse res = new UserResearchWorkResponse();
								res.setTitle(researchWork.getTitle());
								res.setCategory(researchWork.getCategory());
								res.setSubCategory(researchWork.getSubcategory());
								res.setJournalConferenceName(researchWork.getJournalConferenceName());
								res.setPublisher(researchWork.getPublisher());
								res.setYearOfPublication(researchWork.getYearOfPublication());
								res.setPdf(researchWork.getPdf());
								res.setAuthorOne(researchWork.getAuthorOne());
								res.setAuthorTwo(researchWork.getAuthorTwo());
								res.setOtherAuthors(researchWork.getOtherAuthors());
								res.setNoOfAuthors(researchWork.getNoOfAuthors());
								result.add(res);
							}
						}
						else
						{
							if(authorTwo.contains(keyw))
							{
								UserResearchWorkResponse res = new UserResearchWorkResponse();
								res.setTitle(researchWork.getTitle());
								res.setCategory(researchWork.getCategory());
								res.setSubCategory(researchWork.getSubcategory());
								res.setJournalConferenceName(researchWork.getJournalConferenceName());
								res.setPublisher(researchWork.getPublisher());
								res.setYearOfPublication(researchWork.getYearOfPublication());
								res.setPdf(researchWork.getPdf());
								res.setAuthorOne(researchWork.getAuthorOne());
								res.setAuthorTwo(researchWork.getAuthorTwo());
								res.setOtherAuthors(researchWork.getOtherAuthors());
								res.setNoOfAuthors(researchWork.getNoOfAuthors());
								result.add(res);
							}
						}
					}
					else
					{
						if(researchWork.getOtherAuthors()!=null)
						{
							String otherAuthors=researchWork.getOtherAuthors().toLowerCase();
							if(otherAuthors.contains(keyw))
							{
								UserResearchWorkResponse res = new UserResearchWorkResponse();
								res.setTitle(researchWork.getTitle());
								res.setCategory(researchWork.getCategory());
								res.setSubCategory(researchWork.getSubcategory());
								res.setJournalConferenceName(researchWork.getJournalConferenceName());
								res.setPublisher(researchWork.getPublisher());
								res.setYearOfPublication(researchWork.getYearOfPublication());
								res.setPdf(researchWork.getPdf());
								res.setAuthorOne(researchWork.getAuthorOne());
								res.setAuthorTwo(researchWork.getAuthorTwo());
								res.setOtherAuthors(researchWork.getOtherAuthors());
								res.setNoOfAuthors(researchWork.getNoOfAuthors());
								result.add(res);
							}
						}
					}
				}
			}
			return result;
		}
		return null;
	}
	
	
	@ApiOperation(value = "Add User Research Work", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addUserResearchWork", method = RequestMethod.POST)
	public ResponseEntity<?> addUserResearchWork(@RequestBody UserResearchWorkForm userResearchWorkForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userResearchWorkForm.getUserId()==id)
		{
			UserResearchWork userResearchWork = new UserResearchWork();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			userResearchWork.setCreatedDate(simpleDateFormat.format(new Date()));
			userResearchWork.setUserId(id);
			userResearchWork.setCreatedBy(id);
			userResearchWork.setTitle(userResearchWorkForm.getTitle());
			userResearchWork.setCategory(userResearchWorkForm.getCategory());
			userResearchWork.setSubcategory(userResearchWorkForm.getSubcategory());
			userResearchWork.setJournalConferenceName(userResearchWorkForm.getJournalConferenceName());
			userResearchWork.setPublisher(userResearchWorkForm.getPublisher());
			userResearchWork.setNoOfAuthors(userResearchWorkForm.getNoOfAuthors());
			userResearchWork.setAuthorOne(userResearchWorkForm.getAuthorOne());
			userResearchWork.setAuthorTwo(userResearchWorkForm.getAuthorTwo());
			userResearchWork.setOtherAuthors(userResearchWorkForm.getOtherAuthors());
			userResearchWork.setYearOfPublication(userResearchWorkForm.getYearOfPublication());
			UserResearchWork test = userResearchWorkRepository.save(userResearchWork);
			if(test!=null)
				return new ResponseEntity<>(new ResponseMessage("User research Work added successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to add User Research Work, please try again later!"), HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to add User Research Work!"), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Edit User Research Work", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editUserResearchWork", method = RequestMethod.PUT)
	public ResponseEntity<?> editUserResearchWork(@RequestBody UserResearchWorkForm userResearchWorkForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userResearchWorkForm.getUserId()==id)
		{
			if(userResearchWorkRepository.existsByUserIdAndTitle(id,userResearchWorkForm.getTitle()))
			{
				Optional<UserResearchWork> userResearchWork = userResearchWorkRepository.findByUserIdAndTitle(id,userResearchWorkForm.getTitle());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				userResearchWork.get().setModifiedDate(simpleDateFormat.format(new Date()));
				userResearchWork.get().setUserId(id);
				userResearchWork.get().setModifiedBy(id);
				userResearchWork.get().setTitle(userResearchWorkForm.getTitle());
				userResearchWork.get().setCategory(userResearchWorkForm.getCategory());
				userResearchWork.get().setSubcategory(userResearchWorkForm.getSubcategory());
				userResearchWork.get().setJournalConferenceName(userResearchWorkForm.getJournalConferenceName());
				userResearchWork.get().setPublisher(userResearchWorkForm.getPublisher());
				userResearchWork.get().setNoOfAuthors(userResearchWorkForm.getNoOfAuthors());
				userResearchWork.get().setAuthorOne(userResearchWorkForm.getAuthorOne());
				userResearchWork.get().setAuthorTwo(userResearchWorkForm.getAuthorTwo());
				userResearchWork.get().setOtherAuthors(userResearchWorkForm.getOtherAuthors());
				userResearchWork.get().setYearOfPublication(userResearchWorkForm.getYearOfPublication());
				UserResearchWork test = userResearchWorkRepository.save(userResearchWork.get());
				if(test!=null)
					return new ResponseEntity<>(new ResponseMessage("User Research Work updated successfully!"), HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to update User Research Work, please try again later!"), HttpStatus.BAD_REQUEST);
			}
			else
				return new ResponseEntity<>(new ResponseMessage("User Research Work not found, you need to add it first!"), HttpStatus.BAD_REQUEST);
				
			
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update User Research Work!"), HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "User Internship", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userInternship", method = RequestMethod.GET)
	public List<UserInternship> getUserInternship(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		}
		else {
			id=profileForm.getId();
		}
		List<UserInternship> internship = userInternshipRepository.findByUserId(id);
		// certificate
		return internship;
	}
	
	@ApiOperation(value = "Add User Internship", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addUserInternship", method = RequestMethod.POST)
	public ResponseEntity<?> addUserInternship(@RequestBody UserInternshipForm userInternshipForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userInternshipForm.getUserId()==id)
		{
			UserInternship userInternship = new UserInternship();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			userInternship.setCreatedDate(simpleDateFormat.format(new Date()));
			userInternship.setUserId(id);
			userInternship.setCreatedBy(id);
			userInternship.setSubject(userInternshipForm.getSubject());
			userInternship.setCompanyName(userInternshipForm.getCompanyName());
			userInternship.setCountry(userInternshipForm.getCountry());
			userInternship.setState(userInternshipForm.getState());
			userInternship.setCity(userInternshipForm.getCity());
			userInternship.setStartDate(userInternshipForm.getStartDate());
			userInternship.setEndDate(userInternshipForm.getEndDate());
			UserInternship test = userInternshipRepository.save(userInternship);
			if(test!=null)
				return new ResponseEntity<>(new ResponseMessage("User Internship added successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to add User Internship, please try again later!"), HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to add User Internship!"), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Edit User Internship", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editUserInternship", method = RequestMethod.PUT)
	public ResponseEntity<?> editUserInternship(@RequestBody UserInternshipForm userInternshipForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userInternshipForm.getUserId()==id)
		{
			if(userInternshipRepository.existsByUserIdAndCompanyName(id,userInternshipForm.getCompanyName()))
			{
				Optional<UserInternship> userInternship = userInternshipRepository.findByUserIdAndCompanyName(id,userInternshipForm.getCompanyName());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				userInternship.get().setModifiedDate(simpleDateFormat.format(new Date()));
				userInternship.get().setUserId(id);
				userInternship.get().setModifiedBy(id);
				userInternship.get().setSubject(userInternshipForm.getSubject());
				userInternship.get().setCompanyName(userInternshipForm.getCompanyName());
				userInternship.get().setCountry(userInternshipForm.getCountry());
				userInternship.get().setState(userInternshipForm.getState());
				userInternship.get().setCity(userInternshipForm.getCity());
				userInternship.get().setStartDate(userInternshipForm.getStartDate());
				userInternship.get().setEndDate(userInternshipForm.getEndDate());
				UserInternship test = userInternshipRepository.save(userInternship.get());
				if(test!=null)
					return new ResponseEntity<>(new ResponseMessage("User Internship updated successfully!"), HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to update User Internship, please try again later!"), HttpStatus.BAD_REQUEST);
			}
			else
				return new ResponseEntity<>(new ResponseMessage("User Internship not found, you need to add it first!"), HttpStatus.BAD_REQUEST);
				
			
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update User Internship!"), HttpStatus.BAD_REQUEST);
	}
	

	@ApiOperation(value = "User Technical Acitvity", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userTechnicalActivity", method = RequestMethod.GET)
	public List<UserTechnicalActivity> getUserTechnicalActivity(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		}
		else {
			id=profileForm.getId();
		}
		List<UserTechnicalActivity> technical = userTechnicalActivityRepository.findByUserId(id);
		return technical;
	}
	
	@ApiOperation(value = "Add User Technical Activity", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addUserTechnicalActivity", method = RequestMethod.POST)
	public ResponseEntity<?> addUserTechnicalActivity(@RequestBody UserTechnicalActivityForm userTechnicalActivityForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userTechnicalActivityForm.getUserId()==id)
		{
			UserTechnicalActivity userTechnicalActivity = new UserTechnicalActivity();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			userTechnicalActivity.setCreatedDate(simpleDateFormat.format(new Date()));
			userTechnicalActivity.setUserId(id);
			userTechnicalActivity.setCreatedBy(id);
			userTechnicalActivity.setSubject(userTechnicalActivityForm.getSubject());
			userTechnicalActivity.setType(userTechnicalActivityForm.getType());
			userTechnicalActivity.setFrom(userTechnicalActivityForm.getFrom());
			userTechnicalActivity.setTo(userTechnicalActivityForm.getTo());
			userTechnicalActivity.setNameOfCoordinator(userTechnicalActivityForm.getNameOfCoordinator());
			userTechnicalActivity.setAttendedOrganized(userTechnicalActivityForm.getAttendedOrganized());
			userTechnicalActivity.setPlace(userTechnicalActivityForm.getPlace());
			UserTechnicalActivity test = userTechnicalActivityRepository.save(userTechnicalActivity);
			if(test!=null)
				return new ResponseEntity<>(new ResponseMessage("User Technical Activity added successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to add User Technical Activity, please try again later!"), HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to add User Technical Activity!"), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Edit User Technical Activity", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editUserTechnicalActivity", method = RequestMethod.PUT)
	public ResponseEntity<?> editUserTechnicalActivity(@RequestBody UserTechnicalActivityForm userTechnicalActivityForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userTechnicalActivityForm.getUserId()==id)
		{
			if(userTechnicalActivityRepository.existsByUserIdAndSubject(id,userTechnicalActivityForm.getSubject()))
			{
				Optional<UserTechnicalActivity> userTechnicalActivity = userTechnicalActivityRepository.findByUserIdAndSubject(id,userTechnicalActivityForm.getSubject());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				userTechnicalActivity.get().setModifiedDate(simpleDateFormat.format(new Date()));
				userTechnicalActivity.get().setUserId(id);
				userTechnicalActivity.get().setModifiedBy(id);
				userTechnicalActivity.get().setSubject(userTechnicalActivityForm.getSubject());
				userTechnicalActivity.get().setType(userTechnicalActivityForm.getType());
				userTechnicalActivity.get().setFrom(userTechnicalActivityForm.getFrom());
				userTechnicalActivity.get().setTo(userTechnicalActivityForm.getTo());
				userTechnicalActivity.get().setNameOfCoordinator(userTechnicalActivityForm.getNameOfCoordinator());
				userTechnicalActivity.get().setAttendedOrganized(userTechnicalActivityForm.getAttendedOrganized());
				userTechnicalActivity.get().setPlace(userTechnicalActivityForm.getPlace());
				UserTechnicalActivity test = userTechnicalActivityRepository.save(userTechnicalActivity.get());
				if(test!=null)
					return new ResponseEntity<>(new ResponseMessage("User Technical Activity updated successfully!"), HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to update User Technical Activity, please try again later!"), HttpStatus.BAD_REQUEST);
			}
			else
				return new ResponseEntity<>(new ResponseMessage("User Technical Activity not found, you need to add it first!"), HttpStatus.BAD_REQUEST);
				
			
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update User Technical Activity!"), HttpStatus.BAD_REQUEST);
	}
	
	
	

	@ApiOperation(value = "User Cultural Activity Achievements", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userCulturalActivityAchievements", method = RequestMethod.GET)
	public List<UserCulturalActivityAchievements> getUserCulturalActivityAchievements(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		}
		else {
			id=profileForm.getId();
		}
		List<UserCulturalActivityAchievements> cultural = userCulturalActivityAchievementsRepository.findByUserId(id);
		// certificate
		return cultural;
	}
	
	@ApiOperation(value = "Add User Cultural Activity Achievements", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addUserCulturalActivityAchievements", method = RequestMethod.POST)
	public ResponseEntity<?> addUserCulturalActivityAchievements(@RequestBody UserCulturalActivityAchievementsForm userCulturalActivityAchievementsForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userCulturalActivityAchievementsForm.getUserId()==id)
		{
			UserCulturalActivityAchievements userCulturalActivityAchievements = new UserCulturalActivityAchievements();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			userCulturalActivityAchievements.setCreatedDate(simpleDateFormat.format(new Date()));
			userCulturalActivityAchievements.setUserId(id);
			userCulturalActivityAchievements.setCreatedBy(id);
			userCulturalActivityAchievements.setType(userCulturalActivityAchievementsForm.getType());
			userCulturalActivityAchievements.setNameOfActivity(userCulturalActivityAchievementsForm.getNameOfActivity());
			userCulturalActivityAchievements.setAchievement(userCulturalActivityAchievementsForm.getAchievement());
			userCulturalActivityAchievements.setDate(userCulturalActivityAchievementsForm.getDate());
			userCulturalActivityAchievements.setPlace(userCulturalActivityAchievementsForm.getPlace());
			UserCulturalActivityAchievements test = userCulturalActivityAchievementsRepository.save(userCulturalActivityAchievements);
			if(test!=null)
				return new ResponseEntity<>(new ResponseMessage("User Cultural Activity Achievement added successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to add User Cultural Activity Achievement, please try again later!"), HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to add User Cultural Activity Achievement!"), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Edit User Cultural Activity Achievements", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editUserCulturalActivityAchievements", method = RequestMethod.PUT)
	public ResponseEntity<?> editUserCulturalActivityAchievements(@RequestBody UserCulturalActivityAchievementsForm userCulturalActivityAchievementsForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userCulturalActivityAchievementsForm.getUserId()==id)
		{
			if(userCulturalActivityAchievementsRepository.existsByUserIdAndNameOfActivity(id,userCulturalActivityAchievementsForm.getNameOfActivity()))
			{
				Optional<UserCulturalActivityAchievements> userCulturalActivityAchievements = userCulturalActivityAchievementsRepository.findByUserIdAndNameOfActivity(id,userCulturalActivityAchievementsForm.getNameOfActivity());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				userCulturalActivityAchievements.get().setModifiedDate(simpleDateFormat.format(new Date()));
				userCulturalActivityAchievements.get().setUserId(id);
				userCulturalActivityAchievements.get().setModifiedBy(id);
				userCulturalActivityAchievements.get().setType(userCulturalActivityAchievementsForm.getType());
				userCulturalActivityAchievements.get().setNameOfActivity(userCulturalActivityAchievementsForm.getNameOfActivity());
				userCulturalActivityAchievements.get().setAchievement(userCulturalActivityAchievementsForm.getAchievement());
				userCulturalActivityAchievements.get().setDate(userCulturalActivityAchievementsForm.getDate());
				userCulturalActivityAchievements.get().setPlace(userCulturalActivityAchievementsForm.getPlace());
				UserCulturalActivityAchievements test = userCulturalActivityAchievementsRepository.save(userCulturalActivityAchievements.get());
				if(test!=null)
					return new ResponseEntity<>(new ResponseMessage("User Cultural Activity Achievement updated successfully!"), HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to update User Cultural Activity Achievement, please try again later!"), HttpStatus.BAD_REQUEST);
			}
			else
				return new ResponseEntity<>(new ResponseMessage("User Cultural Activity Achievement not found, you need to add it first!"), HttpStatus.BAD_REQUEST);
				
			
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update User Cultural Activity Achievement!"), HttpStatus.BAD_REQUEST);
	}
	
	

	@ApiOperation(value = "User Competitive Exams", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userCompetitiveExams", method = RequestMethod.GET)
	public List<UserCompetitiveExams> getUserCompetitiveExams(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		}
		else {
			id=profileForm.getId();
		} 
		List<UserCompetitiveExams> exams = userCompetitiveExamsRepository.findByUserId(id);
		// score card
		return exams;
	}
	
	@ApiOperation(value = "Add User Competitive Exams", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addUserCompetitiveExams", method = RequestMethod.POST)
	public ResponseEntity<?> addUserCompetitiveExams(@RequestBody UserCompetitiveExamsForm userCompetitiveExamsForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userCompetitiveExamsForm.getUserId()==id)
		{
			UserCompetitiveExams userCompetitiveExams = new UserCompetitiveExams();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			userCompetitiveExams.setCreatedDate(simpleDateFormat.format(new Date()));
			userCompetitiveExams.setUserId(id);
			userCompetitiveExams.setCreatedBy(id);
			userCompetitiveExams.setNameOfExam(userCompetitiveExamsForm.getNameOfExam());
			userCompetitiveExams.setScore(userCompetitiveExamsForm.getScore());
			userCompetitiveExams.setRank(userCompetitiveExamsForm.getRank());
			userCompetitiveExams.setRegistrationNo(userCompetitiveExamsForm.getRegisterationNo());
			userCompetitiveExams.setYear(userCompetitiveExamsForm.getYear());
			UserCompetitiveExams test = userCompetitiveExamsRepository.save(userCompetitiveExams);
			if(test!=null)
				return new ResponseEntity<>(new ResponseMessage("User Competitive Exam added successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to add User Competitive Exam, please try again later!"), HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to add User Competitive Exam!"), HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Edit User Competitive Exams", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editUserCompetitiveExams", method = RequestMethod.PUT)
	public ResponseEntity<?> editUserCompetitiveExams(@RequestBody UserCompetitiveExamsForm userCompetitiveExamsForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userCompetitiveExamsForm.getUserId()==id)
		{
			if(userCompetitiveExamsRepository.existsByUserIdAndNameOfExam(id,userCompetitiveExamsForm.getNameOfExam()))
			{
				Optional<UserCompetitiveExams> userCompetitiveExams = userCompetitiveExamsRepository.findByUserIdAndNameOfExam(id,userCompetitiveExamsForm.getNameOfExam());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				userCompetitiveExams.get().setModifiedDate(simpleDateFormat.format(new Date()));
				userCompetitiveExams.get().setUserId(id);
				userCompetitiveExams.get().setModifiedBy(id);
				userCompetitiveExams.get().setNameOfExam(userCompetitiveExamsForm.getNameOfExam());
				userCompetitiveExams.get().setScore(userCompetitiveExamsForm.getScore());
				userCompetitiveExams.get().setRank(userCompetitiveExamsForm.getRank());
				userCompetitiveExams.get().setRegistrationNo(userCompetitiveExamsForm.getRegisterationNo());
				userCompetitiveExams.get().setYear(userCompetitiveExamsForm.getYear());
				UserCompetitiveExams test = userCompetitiveExamsRepository.save(userCompetitiveExams.get());
				if(test!=null)
					return new ResponseEntity<>(new ResponseMessage("User Competitive Exam details updated successfully!"), HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to update User Competitive Exam details, please try again later!"), HttpStatus.BAD_REQUEST);
			}
			else
				return new ResponseEntity<>(new ResponseMessage("User Competitive Exam not found, you need to add it first!"), HttpStatus.BAD_REQUEST);
				
			
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update User Competitive Exam details!"), HttpStatus.BAD_REQUEST);
	}
	
	
	@ApiOperation(value = "User Project", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/userProject", method = RequestMethod.GET)
	public List<UserProjects> getUserProject(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		}
		else {
			id=profileForm.getId();
		} 
		List<UserProjects> projects = userProjectsRepository.findByUserId(id);
		return projects;
	}
	
	@ApiOperation(value = "Add User Project", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addUserProject", method = RequestMethod.POST)
	public ResponseEntity<?> addUserProject(@RequestBody UserProjectForm userProjectForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userProjectForm.getUserId()==id)
		{
			UserProjects userProject = new UserProjects();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			userProject.setCreatedDate(simpleDateFormat.format(new Date()));
			userProject.setUserId(id);
			userProject.setCreatedBy(id);
			userProject.setTitle(userProjectForm.getTitle());
			userProject.setFrom(userProjectForm.getFrom());
			userProject.setTo(userProjectForm.getTo());
			userProject.setDescription(userProjectForm.getDescription());
			userProject.setOtherCreators(userProjectForm.getOtherCreators());
			userProject.setRole(userProjectForm.getRole());
			userProject.setGuide(userProjectForm.getGuide());
			UserProjects test = userProjectsRepository.save(userProject);
			if(test!=null)
				return new ResponseEntity<>(new ResponseMessage("User Project added successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to add User Project, please try again later!"), HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to add User Project!"), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Edit User Project", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editUserProject", method = RequestMethod.PUT)
	public ResponseEntity<?> editUserProject(@RequestBody UserProjectForm userProjectForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(userProjectForm.getUserId()==id)
		{
			if(userProjectsRepository.existsByUserIdAndTitle(id,userProjectForm.getTitle()))
			{
				Optional<UserProjects> userProject = userProjectsRepository.findByUserIdAndTitle(id,userProjectForm.getTitle());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				userProject.get().setModifiedDate(simpleDateFormat.format(new Date()));
				userProject.get().setUserId(id);
				userProject.get().setModifiedBy(id);
				userProject.get().setTitle(userProjectForm.getTitle());
				userProject.get().setFrom(userProjectForm.getFrom());
				userProject.get().setTo(userProjectForm.getTo());
				userProject.get().setDescription(userProjectForm.getDescription());
				userProject.get().setOtherCreators(userProjectForm.getOtherCreators());
				userProject.get().setRole(userProjectForm.getRole());
				userProject.get().setGuide(userProjectForm.getGuide());
				UserProjects test = userProjectsRepository.save(userProject.get());
				if(test!=null)
					return new ResponseEntity<>(new ResponseMessage("User Project details updated successfully!"), HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to update User Project details, please try again later!"), HttpStatus.BAD_REQUEST);
			}
			else
				return new ResponseEntity<>(new ResponseMessage("User Project not found, you need to add it first!"), HttpStatus.BAD_REQUEST);
				
			
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You are not allowed to update User Project details!"), HttpStatus.BAD_REQUEST);
	}
	

	@ApiOperation(value = "Staff Basic Profile Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/staffBasicProfile", method = RequestMethod.GET)
	public StaffBasicProfileResponse getStaffBasicProfile(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		}
		else {
			id=profileForm.getId();
		} 
		Optional<StaffProfile> staffProfile = staffRepository.findByUserId(id);
		if(staffProfile.isPresent()) {
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
		else
			return null;
	}

	@ApiOperation(value = "Edit Staff Basic Profile Data", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editStaffBasicProfile", method = RequestMethod.PUT)
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
//		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));

	}

	@ApiOperation(value = "Student Basic Profile Data", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/studentBasicProfile", method = RequestMethod.GET)
	public StudentBasicProfileResponse getStudentBasicProfile(@RequestBody ProfileForm profileForm,HttpServletRequest request) {
		
		long id;
		if(profileForm.getId()==null) {
			id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		}
		else {
			id=profileForm.getId();
		}
		
		Optional<StudentProfile> studentProfile = studentRepository.findByUserId(id);
		if(studentProfile.isPresent()) {
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
		else
			return null;
		
	}

	@ApiOperation(value = "Edit Student Basic Profile Data", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editStudentBasicProfile", method = RequestMethod.PUT)
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