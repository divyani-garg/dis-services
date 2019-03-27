package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
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
import sgsits.cse.dis.administration.request.CWNComplaintForm;
import sgsits.cse.dis.administration.request.CleanlinessComplaintForm;
import sgsits.cse.dis.administration.request.FacultyComplaintForm;
import sgsits.cse.dis.administration.request.LEComplaintForm;
import sgsits.cse.dis.administration.request.OtherComplaintForm;
import sgsits.cse.dis.administration.request.StudentComplaintForm;
import sgsits.cse.dis.administration.response.ResponseMessage;

@CrossOrigin(origins = "*")
@RestController
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
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	// Get My Complaints

	@ApiOperation(value = "Get My Cleanliness Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getMyCleanlinessComplaints", method = RequestMethod.GET)
	public List<CleanlinessComplaints> getMyCleanlinessComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		return cleanlinessComplaintRepository.findByCreatedBy(id);
	}

	@ApiOperation(value = "Get My Lab Equipment Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getMyLEComplaints", method = RequestMethod.GET)
	public List<LEComplaints> getMyLEComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		return leComplaintRepository.findByCreatedBy(id);
	}

	@ApiOperation(value = "Get My Other Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getMyOtherComplaints", method = RequestMethod.GET)
	public List<OtherComplaints> getMyOtherComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		return otherComplaintRepository.findByCreatedBy(id);
	}

	@ApiOperation(value = "Get My Faculty Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getMyFacultyComplaints", method = RequestMethod.GET)
	public List<FacultyComplaints> getMyFacultyComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("student")) {
			return facultyComplaintRepository.findByCreatedBy(id);
		}
		return null;
	}

	@ApiOperation(value = "Get My Student Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getMyStudentComplaints", method = RequestMethod.GET)
	public List<StudentComplaints> getMyStudentComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("faculty")) {
			return studentComplaintRepository.findByCreatedBy(id);
		}
		return null;
	}

	// Get Remaining Complaints

	@ApiOperation(value = "Get Remaining Cleanliness Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingCleanlinessComplaints", method = RequestMethod.GET)
	public List<CleanlinessComplaints> getRemainingCleanlinessComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cleanlinessComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining LE Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingLEComplaints", method = RequestMethod.GET)
	public List<LEComplaints> getRemainingLEComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return leComplaintRepository.findByLabInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining Other Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingOtherComplaints", method = RequestMethod.GET)
	public List<OtherComplaints> getRemainingOtherComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return otherComplaintRepository.findByStatusNot("Resolved");
		}
		return otherComplaintRepository.findByAssignedToAndStatusNot(id, "Resolved");
	}

	@ApiOperation(value = "Get Remaining Faculty Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingFacultyComplaints", method = RequestMethod.GET)
	public List<FacultyComplaints> getRemainingFacultyComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return facultyComplaintRepository.findByStatusNot("Resolved");
		}
		return null;
	}

	@ApiOperation(value = "Get Remaining Student Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingStudentComplaints", method = RequestMethod.GET)
	public List<StudentComplaints> getRemainingStudentComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return studentComplaintRepository.findByStatusNot("Resolved");
		}
		return null;
	}

	@ApiOperation(value = "Get Remaining CWN Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingCWNComplaints", method = RequestMethod.GET)
	public List<CWNComplaints> getRemainingCWNComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cwnComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining ECCW Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingECCWComplaints", method = RequestMethod.GET)
	public List<ECCWComplaints> getRemainingECCWComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return eccwComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining EMRS Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingEMRSComplaints", method = RequestMethod.GET)
	public List<EMRSComplaints> getRemainingEMRSComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return emrsComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining Telephone Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingTelephoneComplaints", method = RequestMethod.GET)
	public List<TelephoneComplaints> getRemainingTelephoneComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return telephoneComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	// Get Resolved Complaints

	@ApiOperation(value = "Get Resolved Cleanliness Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedCleanlinessComplaints", method = RequestMethod.GET)
	public List<CleanlinessComplaints> getResolvedCleanlinessComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cleanlinessComplaintRepository.findByLocationInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved LE Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedLEComplaints", method = RequestMethod.GET)
	public List<LEComplaints> getResolvedLEComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return leComplaintRepository.findByLabInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved Other Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedOtherComplaints", method = RequestMethod.GET)
	public List<OtherComplaints> getResolvedOtherComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return otherComplaintRepository.findByStatus("Resolved");
		}
		return otherComplaintRepository.findByAssignedToAndStatus(id, "Resolved");
	}

	@ApiOperation(value = "Get Resolved Faculty Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedFacultyComplaints", method = RequestMethod.GET)
	public List<FacultyComplaints> getResolvedFacultyComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return facultyComplaintRepository.findByStatus("Resolved");
		}
		return null;
	}

	@ApiOperation(value = "Get Resolved Student Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedStudentComplaints", method = RequestMethod.GET)
	public List<StudentComplaints> getResolvedStudentComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return studentComplaintRepository.findByStatus("Resolved");
		}
		return null;
	}

	@ApiOperation(value = "Get Resolved CWN Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedCWNComplaints", method = RequestMethod.GET)
	public List<CWNComplaints> getResolvedCWNComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cwnComplaintRepository.findByLocationInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved ECCW Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedECCWComplaints", method = RequestMethod.GET)
	public List<ECCWComplaints> getResolvedECCWComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return eccwComplaintRepository.findByLocationInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved EMRS Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedEMRSComplaints", method = RequestMethod.GET)
	public List<EMRSComplaints> getResolvedEMRSComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return emrsComplaintRepository.findByLocationInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved Telephone Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedTelephoneComplaints", method = RequestMethod.GET)
	public List<TelephoneComplaints> getResolvedTelephoneComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return telephoneComplaintRepository.findByLocationInAndStatus(location, "Resolved");
		else
			return null;
	}

	// Get Total Complaints

	@ApiOperation(value = "Get Total Cleanliness Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalCleanlinessComplaints", method = RequestMethod.GET)
	public List<CleanlinessComplaints> getTotalCleanlinessComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cleanlinessComplaintRepository.findByLocationIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total LE Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalLEComplaints", method = RequestMethod.GET)
	public List<LEComplaints> getTotalLEComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return leComplaintRepository.findByLabIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total Other Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalOtherComplaints", method = RequestMethod.GET)
	public List<OtherComplaints> getTotalOtherComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return otherComplaintRepository.findAll();
		}
		return otherComplaintRepository.findByAssignedTo(id);
	}

	@ApiOperation(value = "Get Total Faculty Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalFacultyComplaints", method = RequestMethod.GET)
	public List<FacultyComplaints> getTotalFacultyComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return facultyComplaintRepository.findAll();
		}
		return null;
	}

	@ApiOperation(value = "Get Total Student Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalStudentComplaints", method = RequestMethod.GET)
	public List<StudentComplaints> getTotalStudentComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return studentComplaintRepository.findAll();
		}
		return null;
	}

	@ApiOperation(value = "Get Total CWN Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalCWNComplaints", method = RequestMethod.GET)
	public List<CWNComplaints> getTotalCWNComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cwnComplaintRepository.findByLocationIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total ECCW Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalECCWComplaints", method = RequestMethod.GET)
	public List<ECCWComplaints> getTotalECCWComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return eccwComplaintRepository.findByLocationIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total EMRS Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalEMRSComplaints", method = RequestMethod.GET)
	public List<EMRSComplaints> getTotalEMRSComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return emrsComplaintRepository.findByLocationIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total Telephone Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalTelephoneComplaints", method = RequestMethod.GET)
	public List<TelephoneComplaints> getTotalTelephoneComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return telephoneComplaintRepository.findByLocationIn(location);
		else
			return null;
	}

	// count complaints

	@ApiOperation(value = "Get Remaining Complaints Count", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getRemainingComplaintsCount", method = RequestMethod.GET)
	public long getRemainingComplaintsCount(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		long count = 0;
		if (user_type.equals("head")) {
			count = count + facultyComplaintRepository.countByStatusNot("Resolved");
			count = count + studentComplaintRepository.countByStatusNot("Resolved");
			count = count + otherComplaintRepository.countByStatusNot("Resolved");
		} else {
			List<String> location = infrastructureClient.findInchargeOf(id);
			for (String loc : location) {
				count = count + cleanlinessComplaintRepository.countByLocationAndStatusNot(loc, "Resolved");
				count = count + cwnComplaintRepository.countByLocationAndStatusNot(loc, "Resolved");
				count = count + eccwComplaintRepository.countByLocationAndStatusNot(loc, "Resolved");
				count = count + emrsComplaintRepository.countByLocationAndStatusNot(loc, "Resolved");
				count = count + leComplaintRepository.countByLabAndStatusNot(loc, "Resolved");
				count = count + telephoneComplaintRepository.countByLocationAndStatusNot(loc, "Resolved");
				count = count + otherComplaintRepository.countByAssignedToAndStatusNot(id, "Resolved");
			}
		}
		return count;
	}

	@ApiOperation(value = "Get Resolved Complaints Count", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getResolvedComplaintsCount", method = RequestMethod.GET)
	public long getResolvedComplaintsCount(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		long count = 0;
		if (user_type.equals("head")) {
			count = count + facultyComplaintRepository.countByStatus("Resolved");
			count = count + studentComplaintRepository.countByStatus("Resolved");
			count = count + otherComplaintRepository.countByStatus("Resolved");
		} else {
			List<String> location = infrastructureClient.findInchargeOf(id);
			for (String loc : location) {
				count = count + cleanlinessComplaintRepository.countByLocationAndStatus(loc, "Resolved");
				count = count + cwnComplaintRepository.countByLocationAndStatus(loc, "Resolved");
				count = count + eccwComplaintRepository.countByLocationAndStatus(loc, "Resolved");
				count = count + emrsComplaintRepository.countByLocationAndStatus(loc, "Resolved");
				count = count + leComplaintRepository.countByLabAndStatus(loc, "Resolved");
				count = count + telephoneComplaintRepository.countByLocationAndStatus(loc, "Resolved");
				count = count + otherComplaintRepository.countByAssignedToAndStatus(id, "Resolved");
			}
		}
		return count;
	}

	@ApiOperation(value = "Get Total Complaints Count", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getTotalComplaintsCount", method = RequestMethod.GET)
	public long getTotalComplaintsCount(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		long count = 0;
		if (user_type.equals("head")) {
			count = count + facultyComplaintRepository.count();
			count = count + studentComplaintRepository.count();
			count = count + otherComplaintRepository.count();
		} else {
			List<String> location = infrastructureClient.findInchargeOf(id);
			for (String loc : location) {
				count = count + cleanlinessComplaintRepository.countByLocation(loc);
				count = count + cwnComplaintRepository.countByLocation(loc);
				count = count + eccwComplaintRepository.countByLocation(loc);
				count = count + emrsComplaintRepository.countByLocation(loc);
				count = count + leComplaintRepository.countByLab(loc);
				count = count + telephoneComplaintRepository.countByLocation(loc);
				count = count + otherComplaintRepository.countByAssignedTo(id);
			}
		}
		return count;
	}

	@ApiOperation(value = "Get My Complaints Count", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getMyComplaintsCount", method = RequestMethod.GET)
	public long getMyComplaintsCount(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		long count = 0;
		count = count + cleanlinessComplaintRepository.countByCreatedBy(id);
		count = count + leComplaintRepository.countByCreatedBy(id);
		count = count + otherComplaintRepository.countByCreatedBy(id);
		if (user_type.equals("student")) {
			count = count + facultyComplaintRepository.countByCreatedBy(id);
		}
		if (user_type.equals("faculty")) {
			count = count + studentComplaintRepository.countByCreatedBy(id);
		}
		return count;
	}

	// Add Complaints //create notification for all

	@ApiOperation(value = "Add Cleanliness Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addCleanlinessComplaint", method = RequestMethod.POST)
	public ResponseEntity<?> addCleanlinessComplaint(@RequestBody CleanlinessComplaintForm cleanlinessComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!cleanlinessComplaintRepository.existsByCreatedByAndLocationAndStatusNot(id,
				cleanlinessComplaintForm.getLocation(), "Resolved")) {
			CleanlinessComplaints cleanlinessComplaints = new CleanlinessComplaints(
					cleanlinessComplaintForm.getDetails(), cleanlinessComplaintForm.getLevelOfDust(),
					cleanlinessComplaintForm.getLocation());
			cleanlinessComplaints.setCreatedBy(id);
			cleanlinessComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
			cleanlinessComplaints.setStatus("Not Assigned");
			cleanlinessComplaints.setType("CL");
			CleanlinessComplaints test = cleanlinessComplaintRepository.save(cleanlinessComplaints);
			if (test != null)
				return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
						HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to record Complaint, Please try again later!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(new ResponseMessage(
					"Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add Lab Equipment Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addLEComplaint", method = RequestMethod.POST)
	public ResponseEntity<?> addLEComplaint(@RequestBody LEComplaintForm leComplaintForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!leComplaintRepository.existsByCreatedByAndLabAndSystemNoAndStatusNot(id, leComplaintForm.getLab(),
				leComplaintForm.getSystemNo(), "Resolved")) {
			LEComplaints leComplaints = new LEComplaints(leComplaintForm.getLab(), leComplaintForm.getSystemNo(),
					leComplaintForm.getDetails());
			leComplaints.setCreatedBy(id);
			leComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
			leComplaints.setType("LE");
			leComplaints.setStatus("Not Assigned");
			LEComplaints test = leComplaintRepository.save(leComplaints);
			if (test != null)
				return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
						HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to record Complaint, Please try again later!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(new ResponseMessage(
					"Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add Other Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addOtherComplaint", method = RequestMethod.POST)
	public ResponseEntity<?> addOtherComplaint(@RequestBody OtherComplaintForm otherComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!otherComplaintRepository.existsByCreatedByAndDetailsAndStatusNot(id, otherComplaintForm.getDetails(),
				"Resolved")) {
			OtherComplaints otherComplaints = new OtherComplaints(otherComplaintForm.getDetails());
			otherComplaints.setCreatedBy(id);
			otherComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
			otherComplaints.setType("OTHER");
			otherComplaints.setStatus("Not Assigned");
			OtherComplaints test = otherComplaintRepository.save(otherComplaints);
			if (test != null)
				return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
						HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to record Complaint, Please try again later!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(new ResponseMessage(
					"Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add Student Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addStudentComplaint", method = RequestMethod.POST)
	public ResponseEntity<?> addStudentComplaint(@RequestBody StudentComplaintForm studentComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("faculty")) {
			boolean check1 = studentComplaintRepository
					.existsByCreatedByAndStudentRollNoAndStudentNameAndYearAndStatusNot(id,
							studentComplaintForm.getStudentRollNo(), studentComplaintForm.getStudentName(),
							studentComplaintForm.getYear(), "Resolved");
			boolean check2 = studentComplaintRepository.existsByCreatedByAndStudentRollNoAndYearAndStatusNot(id,
					studentComplaintForm.getStudentRollNo(), studentComplaintForm.getYear(), "Resolved");
			boolean check3 = studentComplaintRepository.existsByCreatedByAndStudentNameAndYearAndStatusNot(id,
					studentComplaintForm.getStudentName(), studentComplaintForm.getYear(), "Resolved");
			if (!(check1 || check2 || check3)) {

				StudentComplaints studentComplaints = new StudentComplaints();
				if (studentComplaintForm.getStudentName().isEmpty() || studentComplaintForm.getStudentName() == null)
					studentComplaints = new StudentComplaints(studentComplaintForm.getStudentRollNo(), null,
							studentComplaintForm.getCourse(), studentComplaintForm.getYear(),
							studentComplaintForm.getDetails());
				else if (studentComplaintForm.getStudentRollNo().isEmpty()
						|| studentComplaintForm.getStudentRollNo() == null)
					studentComplaints = new StudentComplaints(null, studentComplaintForm.getStudentName(),
							studentComplaintForm.getCourse(), studentComplaintForm.getYear(),
							studentComplaintForm.getDetails());
				else
					studentComplaints = new StudentComplaints(studentComplaintForm.getStudentRollNo(),
							studentComplaintForm.getStudentName(), studentComplaintForm.getCourse(),
							studentComplaintForm.getYear(), studentComplaintForm.getDetails());

				studentComplaints.setCreatedBy(id);
				studentComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
				studentComplaints.setType("STUDENT");
				studentComplaints.setStatus("Not Assigned");
				StudentComplaints test = studentComplaintRepository.save(studentComplaints);
				if (test != null)
					return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
							HttpStatus.OK);
				else
					return new ResponseEntity<>(
							new ResponseMessage("Unable to record Complaint, Please try again later!"),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new ResponseMessage(
						"Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add Faculty Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addFacultyComplaint", method = RequestMethod.POST)
	public ResponseEntity<?> addFacultyComplaint(@RequestBody FacultyComplaintForm facultyComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			if (!facultyComplaintRepository.existsByCreatedByAndFacultyNameAndStatusNot(id,
					facultyComplaintForm.getFacultyName(), "Resolved")) {
				FacultyComplaints facultyComplaints = new FacultyComplaints(facultyComplaintForm.getFacultyName(),
						facultyComplaintForm.getDetails());
				facultyComplaints.setCreatedBy(id);
				facultyComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
				facultyComplaints.setType("FACULTY");
				facultyComplaints.setStatus("Not Assigned");
				FacultyComplaints test = facultyComplaintRepository.save(facultyComplaints);
				if (test != null)
					return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
							HttpStatus.OK);
				else
					return new ResponseEntity<>(
							new ResponseMessage("Unable to record Complaint, Please try again later!"),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new ResponseMessage(
						"Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add CWN Maintenance Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addCWNComplaint", method = RequestMethod.POST)
	public ResponseEntity<?> addCWNComplaint(@RequestBody List<CWNComplaintForm> cwnComplaintForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		long formId = cwnComplaintRepository.maxOfFormId();
		for(CWNComplaintForm complaint : cwnComplaintForm)
		{
			if(!cwnComplaintRepository.existsByLocationAndDetailsAndStatusNot(complaint.getLocation(),complaint.getDetails(),"Resolved")) {
				CWNComplaints cwnComplaints = new CWNComplaints(complaint.getDetails(), complaint.getLocation());
				cwnComplaints.setCreatedBy(id);
				cwnComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
				cwnComplaints.setType("CWN");
				cwnComplaints.setStatus("Not Assigned");
				cwnComplaints.setFormId(formId+1);
				cwnComplaintRepository.save(cwnComplaints);
			}
		}
		return null;
	}

	@ApiOperation(value = "Add Engineering Cell / Central Workshop Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addECCWComplaint", method = RequestMethod.POST)
	public ResponseEntity<String> addECCWComplaint(@RequestBody ECCWComplaints eccwComplaints, HttpServletRequest request) {
		eccwComplaintRepository.save(eccwComplaints);
		return null;
	}

	@ApiOperation(value = "Add Electrical Maintenance and Repairs Section Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addEMRSComplaint", method = RequestMethod.POST)
	public ResponseEntity<String> addEMRSComplaint(@RequestBody EMRSComplaints emrsComplaints, HttpServletRequest request) {
		emrsComplaintRepository.save(emrsComplaints);
		return null;
	}

	@ApiOperation(value = "Add Telephone Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addTelephoneComplaint", method = RequestMethod.POST)
	public ResponseEntity<String> addTelephoneComplaint(@RequestBody TelephoneComplaints telephoneComplaints, HttpServletRequest request) {
		telephoneComplaintRepository.save(telephoneComplaints);
		return null;
	}

}