package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import sgsits.cse.dis.administration.model.CWNComplaints;
import sgsits.cse.dis.administration.model.CleanlinessComplaints;
import sgsits.cse.dis.administration.model.ECCWComplaints;
import sgsits.cse.dis.administration.model.EMRSComplaints;
import sgsits.cse.dis.administration.model.FacultyComplaints;
import sgsits.cse.dis.administration.model.LEComplaints;
import sgsits.cse.dis.administration.model.OtherComplaints;
import sgsits.cse.dis.administration.model.StudentComplaints;
import sgsits.cse.dis.administration.model.TelephoneComplaints;
import sgsits.cse.dis.administration.repo.CWNComplaintRepository;
import sgsits.cse.dis.administration.repo.CleanlinessComplaintRepository;
import sgsits.cse.dis.administration.repo.ECCWComplaintRepository;
import sgsits.cse.dis.administration.repo.EMRSComplaintRepository;
import sgsits.cse.dis.administration.repo.FacultyComplaintRepository;
import sgsits.cse.dis.administration.repo.LEComplaintRepository;
import sgsits.cse.dis.administration.repo.OtherComplaintRepository;
import sgsits.cse.dis.administration.repo.StudentComplaintRepository;
import sgsits.cse.dis.administration.repo.TelephoneComplaintRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dis/complaint") 
@Api(value = "Complaints Resource")
public class ComplaintsController {
	
	@Autowired
	CleanlinessComplaintRepository cleanlinessComplaintRepository;
	@Autowired
	CWNComplaintRepository cwnComplaintRepository; 
	@Autowired
	ECCWComplaintRepository eccwComplaintRepository;
	@Autowired
	EMRSComplaintRepository emrsComplaintRepository;
	@Autowired
	FacultyComplaintRepository facultyComplaintRepository;
	@Autowired
	LEComplaintRepository leComplaintRepository;
	@Autowired
	OtherComplaintRepository otherComplaintRepository;
	@Autowired
	StudentComplaintRepository studentComplaintRepository;
	@Autowired
	TelephoneComplaintRepository telephoneComplaintRepository;
	
	@ApiOperation(value = "Add Cleanliness Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addCleanliness", method = RequestMethod.POST)
	public ResponseEntity<String> addCleanlinessComplaint(@RequestBody CleanlinessComplaints cleanlinessComplaints)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		cleanlinessComplaints.setCreatedBy(simpleDateFormat.format(new Date()));
		cleanlinessComplaintRepository.save(cleanlinessComplaints);
		return null;
	}
	
	@ApiOperation(value = "Get Cleanliness Complaint", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getCleanliness", method = RequestMethod.GET)
	public List<CleanlinessComplaints> getCleanlinessComplaint()
	{
		return null;
	}
	
	@ApiOperation(value = "Add CWN Maintenance Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addCWN", method = RequestMethod.POST)
	public ResponseEntity<String> addCWNComplaint(@RequestBody CWNComplaints cwnComplaints)
	{
		cwnComplaintRepository.save(cwnComplaints);
		return null;
	}
	
	@ApiOperation(value = "Get CWN Maintenance Complaint", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getCWN", method = RequestMethod.GET)
	public List<CWNComplaints> getCWNComplaint()
	{
		return null;
	}
	
	@ApiOperation(value = "Add Engineering Cell / Central Workshop Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addECCW", method = RequestMethod.POST)
	public ResponseEntity<String> addECCWComplaint(@RequestBody ECCWComplaints eccwComplaints)
	{
		eccwComplaintRepository.save(eccwComplaints);
		return null;
	}
	
	@ApiOperation(value = "Get Engineering Cell / Central Workshop Complaint", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getECCW", method = RequestMethod.GET)
	public List<ECCWComplaints> getECCWComplaint()
	{
		return null;
	}
	
	@ApiOperation(value = "Add Electrical Maintenance and Repairs Section Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addEMRS", method = RequestMethod.POST)
	public ResponseEntity<String> addEMRSComplaint(@RequestBody EMRSComplaints emrsComplaints)
	{
		emrsComplaintRepository.save(emrsComplaints);
		return null;
	}
	
	@ApiOperation(value = "Get Electrical Maintenance and Repairs Section Complaint", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getEMRS", method = RequestMethod.GET)
	public List<EMRSComplaints> getEMRSComplaint()
	{
		return null;
	}
	
	@ApiOperation(value = "Add Faculty Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addFaculty", method = RequestMethod.POST)
	public ResponseEntity<String> addFacultyComplaint(@RequestBody FacultyComplaints facultyComplaints)
	{
		facultyComplaintRepository.save(facultyComplaints);
		return null;
	}
	
	@ApiOperation(value = "Get Faculty Complaint", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getFaculty", method = RequestMethod.GET)
	public List<FacultyComplaints> getFacultyComplaint()
	{
		return null;
	}
	
	@ApiOperation(value = "Add Lab Equipment Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addLE", method = RequestMethod.POST)
	public ResponseEntity<String> addLEComplaint(@RequestBody LEComplaints leComplaints)
	{
		leComplaintRepository.save(leComplaints);
		return null;
	}
	
	@ApiOperation(value = "Get Lab Equipment Complaint", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getLE", method = RequestMethod.GET)
	public List<LEComplaints> getLEComplaint()
	{
		return null;
	}
	
	@ApiOperation(value = "Add Other Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addOther", method = RequestMethod.POST)
	public ResponseEntity<String> addOtherComplaint(@RequestBody OtherComplaints otherComplaints)
	{
		otherComplaintRepository.save(otherComplaints);
		return null;
	}
	
	@ApiOperation(value = "Get Other Complaint", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getOther", method = RequestMethod.GET)
	public List<OtherComplaints> getOtherComplaint()
	{
		return null;
	}
	
	@ApiOperation(value = "Add Student Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public ResponseEntity<String> addStudentComplaint(@RequestBody StudentComplaints studentComplaints)
	{
		studentComplaintRepository.save(studentComplaints);
		return null;
	}

	@ApiOperation(value = "Get Student Complaint", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getStudent", method = RequestMethod.GET)
	public List<StudentComplaints> getStudentComplaint()
	{
		return null;
	}
	
	@ApiOperation(value = "Add Telephone Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addTelephone", method = RequestMethod.POST)
	public ResponseEntity<String> addTelephoneComplaint(@RequestBody TelephoneComplaints telephoneComplaints)
	{
		telephoneComplaintRepository.save(telephoneComplaints);
		return null;
	}
	
	@ApiOperation(value = "Get Telephone Complaint", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTelephone", method = RequestMethod.GET)
	public List<TelephoneComplaints> getTelephoneComplaint()
	{
		return null;
	}
}