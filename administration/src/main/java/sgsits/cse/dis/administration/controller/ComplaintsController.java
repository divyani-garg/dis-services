package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import sgsits.cse.dis.administration.feign.InfrastructureClient;
import sgsits.cse.dis.administration.feign.UserClient;
import sgsits.cse.dis.administration.jwt.JwtResolver;

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
import sgsits.cse.dis.administration.request.CleanlinessComplaintForm;
import sgsits.cse.dis.administration.response.ResponseMessage;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/complaint")
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
	@Autowired
	UserClient userClient;
	@Autowired
	InfrastructureClient infrastructureClient;

	JwtResolver jwtResolver = new JwtResolver();

	// Get Complaints

	@ApiOperation(value = "Get My Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getMyComplaints", method = RequestMethod.GET)
	public <T, U> Object[] getMyComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		List<Object> complaints = new ArrayList<>();

		Collections.addAll(complaints, cleanlinessComplaintRepository.findByCreatedBy(id));
		Collections.addAll(complaints, leComplaintRepository.findByCreatedBy(id));
		Collections.addAll(complaints, otherComplaintRepository.findByCreatedBy(id));

		if (user_type.equals("student")) {
			Collections.addAll(complaints, facultyComplaintRepository.findByCreatedBy(id));
		}

		if (user_type.equals("faculty")) {
			Collections.addAll(complaints, studentComplaintRepository.findByCreatedBy(id));
		}

		return complaints.toArray();
	}

	@ApiOperation(value = "Get Remaining Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingComplaints", method = RequestMethod.GET)
	public <T, U> Object[] getRemainingComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		List<Object> complaints = new ArrayList<>();
		if (user_type.equals("head")) {
			Collections.addAll(complaints, facultyComplaintRepository.findByStatusNot("Resolved"));
			Collections.addAll(complaints, studentComplaintRepository.findByStatusNot("Resolved"));
			Collections.addAll(complaints, otherComplaintRepository.findByStatusNot("Resolved"));
		} else {
			List<String> location = infrastructureClient.findInchargeOf(id);
			for (String loc : location) {
				Collections.addAll(complaints,
						cleanlinessComplaintRepository.findByLocationAndStatusNot(loc, "Resolved"));
				Collections.addAll(complaints, cwnComplaintRepository.findByLocationAndStatusNot(loc, "Resolved"));
				Collections.addAll(complaints, eccwComplaintRepository.findByLocationAndStatusNot(loc, "Resolved"));
				Collections.addAll(complaints, emrsComplaintRepository.findByLocationAndStatusNot(loc, "Resolved"));
				Collections.addAll(complaints, leComplaintRepository.findByLabAndStatusNot(loc, "Resolved"));
				Collections.addAll(complaints,
						telephoneComplaintRepository.findByLocationAndStatusNot(loc, "Resolved"));
				Collections.addAll(complaints, otherComplaintRepository.findByAssignedToAndStatusNot(id, "Resolved"));
			}
		}
		return complaints.toArray();
	}

	@ApiOperation(value = "Get Resolved Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedComplaints", method = RequestMethod.GET)
	public <T, U> Object[] getResolvedComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		List<Object> complaints = new ArrayList<>();
		if (user_type.equals("head")) {
			Collections.addAll(complaints, facultyComplaintRepository.findByStatus("Resolved"));
			Collections.addAll(complaints, studentComplaintRepository.findByStatus("Resolved"));
			Collections.addAll(complaints, otherComplaintRepository.findByStatus("Resolved"));
		} else {
			List<String> location = infrastructureClient.findInchargeOf(id);
			for (String loc : location) {
				Collections.addAll(complaints, cleanlinessComplaintRepository.findByLocationAndStatus(loc, "Resolved"));
				Collections.addAll(complaints, cwnComplaintRepository.findByLocationAndStatus(loc, "Resolved"));
				Collections.addAll(complaints, eccwComplaintRepository.findByLocationAndStatus(loc, "Resolved"));
				Collections.addAll(complaints, emrsComplaintRepository.findByLocationAndStatus(loc, "Resolved"));
				Collections.addAll(complaints, leComplaintRepository.findByLabAndStatus(loc, "Resolved"));
				Collections.addAll(complaints, telephoneComplaintRepository.findByLocationAndStatus(loc, "Resolved"));
				Collections.addAll(complaints, otherComplaintRepository.findByAssignedToAndStatus(id, "Resolved"));
			}
		}
		return complaints.toArray();
	}

	@ApiOperation(value = "Get Total Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalComplaints", method = RequestMethod.GET)
	public <T, U> Object[] getTotalComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		List<Object> complaints = new ArrayList<>();
		if (user_type.equals("head")) {
			Collections.addAll(complaints, facultyComplaintRepository.findAll());
			Collections.addAll(complaints, studentComplaintRepository.findAll());
			Collections.addAll(complaints, otherComplaintRepository.findAll());
		} else {
			List<String> location = infrastructureClient.findInchargeOf(id);
			for (String loc : location) {
				Collections.addAll(complaints, cleanlinessComplaintRepository.findByLocation(loc));
				Collections.addAll(complaints, cwnComplaintRepository.findByLocation(loc));
				Collections.addAll(complaints, eccwComplaintRepository.findByLocation(loc));
				Collections.addAll(complaints, emrsComplaintRepository.findByLocation(loc));
				Collections.addAll(complaints, leComplaintRepository.findByLab(loc));
				Collections.addAll(complaints, telephoneComplaintRepository.findByLocation(loc));
				Collections.addAll(complaints, otherComplaintRepository.findByAssignedTo(id));
			}
		}
		return complaints.toArray();
	}

	// Add Complaints //create notification for all

	@ApiOperation(value = "Add Cleanliness Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addCleanliness", method = RequestMethod.POST)
	public ResponseEntity<?> addCleanlinessComplaint(@RequestBody CleanlinessComplaintForm cleanlinessComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		if (!cleanlinessComplaintRepository.existsByCreatedByAndLocationAndStatusNot(id, cleanlinessComplaintForm.getLocation(), "Resolved")) {
			CleanlinessComplaints cleanlinessComplaints = new CleanlinessComplaints(cleanlinessComplaintForm.getDetails(), cleanlinessComplaintForm.getLevelOfDust(), cleanlinessComplaintForm.getLocation());
			cleanlinessComplaints.setCreatedBy(id);
			cleanlinessComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
			cleanlinessComplaints.setStatus("Not Assigned");
			CleanlinessComplaints test = cleanlinessComplaintRepository.save(cleanlinessComplaints);
			if (test != null)
				return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
						HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to record Complaint, Please try again later!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(new ResponseMessage("Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add CWN Maintenance Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addCWN", method = RequestMethod.POST)
	public ResponseEntity<String> addCWNComplaint(@RequestBody CWNComplaints cwnComplaints,
			HttpServletRequest request) {
		cwnComplaintRepository.save(cwnComplaints);
		return null;
	}

	@ApiOperation(value = "Add Engineering Cell / Central Workshop Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addECCW", method = RequestMethod.POST)
	public ResponseEntity<String> addECCWComplaint(@RequestBody ECCWComplaints eccwComplaints,
			HttpServletRequest request) {
		eccwComplaintRepository.save(eccwComplaints);
		return null;
	}

	@ApiOperation(value = "Add Electrical Maintenance and Repairs Section Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addEMRS", method = RequestMethod.POST)
	public ResponseEntity<String> addEMRSComplaint(@RequestBody EMRSComplaints emrsComplaints,
			HttpServletRequest request) {
		emrsComplaintRepository.save(emrsComplaints);
		return null;
	}

	@ApiOperation(value = "Add Faculty Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addFaculty", method = RequestMethod.POST)
	public ResponseEntity<String> addFacultyComplaint(@RequestBody FacultyComplaints facultyComplaints,
			HttpServletRequest request) {
		facultyComplaintRepository.save(facultyComplaints);
		return null;
	}

	@ApiOperation(value = "Add Lab Equipment Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addLE", method = RequestMethod.POST)
	public ResponseEntity<String> addLEComplaint(@RequestBody LEComplaints leComplaints, HttpServletRequest request) {
		leComplaintRepository.save(leComplaints);
		return null;
	}

	@ApiOperation(value = "Add Other Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addOther", method = RequestMethod.POST)
	public ResponseEntity<String> addOtherComplaint(@RequestBody OtherComplaints otherComplaints,
			HttpServletRequest request) {
		otherComplaintRepository.save(otherComplaints);
		return null;
	}

	@ApiOperation(value = "Add Student Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public ResponseEntity<String> addStudentComplaint(@RequestBody StudentComplaints studentComplaints,
			HttpServletRequest request) {
		studentComplaintRepository.save(studentComplaints);
		return null;
	}

	@ApiOperation(value = "Add Telephone Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addTelephone", method = RequestMethod.POST)
	public ResponseEntity<String> addTelephoneComplaint(@RequestBody TelephoneComplaints telephoneComplaints,
			HttpServletRequest request) {
		telephoneComplaintRepository.save(telephoneComplaints);
		return null;
	}

}