package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import sgsits.cse.dis.administration.constants.RestAPI;
import sgsits.cse.dis.administration.feign.InfrastructureClient;
import sgsits.cse.dis.administration.feign.UserClient;
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.AddComplaintPermission;
import sgsits.cse.dis.administration.model.CWNComplaints;
import sgsits.cse.dis.administration.model.CleanlinessComplaints;
import sgsits.cse.dis.administration.model.ECCWComplaints;
import sgsits.cse.dis.administration.model.EMRSComplaints;
import sgsits.cse.dis.administration.model.FacultyComplaints;
import sgsits.cse.dis.administration.model.LEComplaints;
import sgsits.cse.dis.administration.model.OtherComplaints;
import sgsits.cse.dis.administration.model.StudentComplaints;
import sgsits.cse.dis.administration.model.TelephoneComplaints;
import sgsits.cse.dis.administration.repo.AddComplaintPermissionRepository;
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
import sgsits.cse.dis.administration.request.ECCWComplaintForm;
import sgsits.cse.dis.administration.request.EMRSComplaintForm;
import sgsits.cse.dis.administration.request.EditComplaintForm;
import sgsits.cse.dis.administration.request.FacultyComplaintForm;
import sgsits.cse.dis.administration.request.LEComplaintForm;
import sgsits.cse.dis.administration.request.OtherComplaintForm;
import sgsits.cse.dis.administration.request.StudentComplaintForm;
import sgsits.cse.dis.administration.request.TelephoneComplaintForm;
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
	@Autowired
	AddComplaintPermissionRepository addComplaintPermissionRepository;

	JwtResolver jwtResolver = new JwtResolver();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	// Get My Complaints

	@ApiOperation(value = "Get My Cleanliness Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_MY_CLEANLINESS_COMPLAINTS, method = RequestMethod.GET)
	public List<CleanlinessComplaints> getMyCleanlinessComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		return cleanlinessComplaintRepository.findByCreatedBy(id);
	}

	@ApiOperation(value = "Get My Lab Equipment Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_MY_LE_COMPLAINTS, method = RequestMethod.GET)
	public List<LEComplaints> getMyLEComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		return leComplaintRepository.findByCreatedBy(id);
	}

	@ApiOperation(value = "Get My Other Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_MY_OTHER_COMPLAINTS, method = RequestMethod.GET)
	public List<OtherComplaints> getMyOtherComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		return otherComplaintRepository.findByCreatedBy(id);
	}

	@ApiOperation(value = "Get My Faculty Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_MY_FACULTY_COMPLAINTS, method = RequestMethod.GET)
	public List<FacultyComplaints> getMyFacultyComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("student")) {
			return facultyComplaintRepository.findByCreatedBy(id);
		}
		return null;
	}

	@ApiOperation(value = "Get My Student Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_MY_STUDENT_COMPLAINTS, method = RequestMethod.GET)
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
	@RequestMapping(value = RestAPI.GET_REMAINING_CLEANLINESS_COMPLAINTS, method = RequestMethod.GET)
	public List<CleanlinessComplaints> getRemainingCleanlinessComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cleanlinessComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining LE Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_REMAINING_LE_COMPLAINTS, method = RequestMethod.GET)
	public List<LEComplaints> getRemainingLEComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return leComplaintRepository.findByLabInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining Other Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_REMAINING_OTHER_COMPLAINTS, method = RequestMethod.GET)
	public List<OtherComplaints> getRemainingOtherComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return otherComplaintRepository.findByStatusNot("Resolved");
		}
		return otherComplaintRepository.findByAssignedToAndStatusNot(id, "Resolved");
	}

	@ApiOperation(value = "Get Remaining Faculty Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_REMAINING_FACULTY_COMPLAINTS, method = RequestMethod.GET)
	public List<FacultyComplaints> getRemainingFacultyComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return facultyComplaintRepository.findByStatusNot("Resolved");
		}
		return null;
	}

	@ApiOperation(value = "Get Remaining Student Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_REMAINING_STUDENT_COMPLAINTS, method = RequestMethod.GET)
	public List<StudentComplaints> getRemainingStudentComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return studentComplaintRepository.findByStatusNot("Resolved");
		}
		return null;
	}

	@ApiOperation(value = "Get Remaining CWN Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_REMAINING_CWN_COMPLAINTS, method = RequestMethod.GET)
	public List<CWNComplaints> getRemainingCWNComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cwnComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining ECCW Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_REMAINING_ECCW_COMPLAINTS, method = RequestMethod.GET)
	public List<ECCWComplaints> getRemainingECCWComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return eccwComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining EMRS Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_REMAINING_EMRS_COMPLAINTS, method = RequestMethod.GET)
	public List<EMRSComplaints> getRemainingEMRSComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return emrsComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Remaining Telephone Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_REMAINING_TELEPHONE_COMPLAINTS, method = RequestMethod.GET)
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
	@RequestMapping(value = RestAPI.GET_RESOLVED_CLEANLINESS_COMPLAINTS, method = RequestMethod.GET)
	public List<CleanlinessComplaints> getResolvedCleanlinessComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cleanlinessComplaintRepository.findByLocationInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved LE Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_RESOLVED_LE_COMPLAINTS, method = RequestMethod.GET)
	public List<LEComplaints> getResolvedLEComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return leComplaintRepository.findByLabInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved Other Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_RESOLVED_OTHER_COMPLAINTS, method = RequestMethod.GET)
	public List<OtherComplaints> getResolvedOtherComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return otherComplaintRepository.findByStatus("Resolved");
		}
		return otherComplaintRepository.findByAssignedToAndStatus(id, "Resolved");
	}

	@ApiOperation(value = "Get Resolved Faculty Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_RESOLVED_FACULTY_COMPLAINTS, method = RequestMethod.GET)
	public List<FacultyComplaints> getResolvedFacultyComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return facultyComplaintRepository.findByStatus("Resolved");
		}
		return null;
	}

	@ApiOperation(value = "Get Resolved Student Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_RESOLVED_STUDENT_COMPLAINTS, method = RequestMethod.GET)
	public List<StudentComplaints> getResolvedStudentComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return studentComplaintRepository.findByStatus("Resolved");
		}
		return null;
	}

	@ApiOperation(value = "Get Resolved CWN Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_RESOLVED_CWN_COMPLAINTS, method = RequestMethod.GET)
	public List<CWNComplaints> getResolvedCWNComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cwnComplaintRepository.findByLocationInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved ECCW Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_RESOLVED_ECCW_COMPLAINTS, method = RequestMethod.GET)
	public List<ECCWComplaints> getResolvedECCWComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return eccwComplaintRepository.findByLocationInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved EMRS Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_RESOLVED_EMRS_COMPLAINTS, method = RequestMethod.GET)
	public List<EMRSComplaints> getResolvedEMRSComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return emrsComplaintRepository.findByLocationInAndStatus(location, "Resolved");
		else
			return null;
	}

	@ApiOperation(value = "Get Resolved Telephone Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_RESOLVED_TELEPHONE_COMPLAINTS, method = RequestMethod.GET)
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
	@RequestMapping(value = RestAPI.GET_TOTAL_CLEANLINESS_COMPLAINTS, method = RequestMethod.GET)
	public List<CleanlinessComplaints> getTotalCleanlinessComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cleanlinessComplaintRepository.findByLocationIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total LE Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_TOTAL_LE_COMPLAINTS, method = RequestMethod.GET)
	public List<LEComplaints> getTotalLEComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return leComplaintRepository.findByLabIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total Other Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_TOTAL_OTHER_COMPLAINTS, method = RequestMethod.GET)
	public List<OtherComplaints> getTotalOtherComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return otherComplaintRepository.findAll();
		}
		return otherComplaintRepository.findByAssignedTo(id);
	}

	@ApiOperation(value = "Get Total Faculty Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_TOTAL_FACULTY_COMPLAINTS, method = RequestMethod.GET)
	public List<FacultyComplaints> getTotalFacultyComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return facultyComplaintRepository.findAll();
		}
		return null;
	}

	@ApiOperation(value = "Get Total Student Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_TOTAL_STUDENT_COMPLAINTS, method = RequestMethod.GET)
	public List<StudentComplaints> getTotalStudentComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClient.getUserType(id);
		if (user_type.equals("head")) {
			return studentComplaintRepository.findAll();
		}
		return null;
	}

	@ApiOperation(value = "Get Total CWN Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_TOTAL_CWN_COMPLAINTS, method = RequestMethod.GET)
	public List<CWNComplaints> getTotalCWNComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return cwnComplaintRepository.findByLocationIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total ECCW Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_TOTAL_ECCW_COMPLAINTS, method = RequestMethod.GET)
	public List<ECCWComplaints> getTotalECCWComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return eccwComplaintRepository.findByLocationIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total EMRS Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_TOTAL_EMRS_COMPLAINTS, method = RequestMethod.GET)
	public List<EMRSComplaints> getTotalEMRSComplaints(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<String> location = infrastructureClient.findInchargeOf(id);
		if (location != null)
			return emrsComplaintRepository.findByLocationIn(location);
		else
			return null;
	}

	@ApiOperation(value = "Get Total Telephone Complaints", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_TOTAL_TELEPHONE_COMPLAINTS, method = RequestMethod.GET)
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
	@RequestMapping(value = RestAPI.GET_REMAINING_COMPLAINTS_COUNT, method = RequestMethod.GET)
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
	@RequestMapping(value = RestAPI.GET_RESOLVED_COMPLAINTS_COUNT, method = RequestMethod.GET)
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
	@RequestMapping(value = RestAPI.GET_TOTAL_COMPLAINTS_COUNT, method = RequestMethod.GET)
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
	@RequestMapping(value = RestAPI.GET_MY_COMPLAINTS_COUNT, method = RequestMethod.GET)
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

	// Add Complaint Permission
	
	@ApiOperation(value = "Add Complaint Permission", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.ADD_COMPLAINT_PERMISSION, method = RequestMethod.GET)
	public List<String> addComplaintPermission(HttpServletRequest request) {
		String user = jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization"));
		List<AddComplaintPermission> permissions = addComplaintPermissionRepository.findByUser(user);
		List<String> result = new ArrayList<>();
		for(AddComplaintPermission permission : permissions) {
			result.add(permission.getPermission());
		}
		return result;
	}
	
	// Add Complaints //create notification for all

	@ApiOperation(value = "Add Cleanliness Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = RestAPI.ADD_CLEANLINESS_COMPLAINTS, method = RequestMethod.POST)
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
	@RequestMapping(value = RestAPI.ADD_LE_COMPLAINTS, method = RequestMethod.POST)
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
	@RequestMapping(value = RestAPI.ADD_OTHER_COMPLAINTS, method = RequestMethod.POST)
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
	@RequestMapping(value = RestAPI.ADD_STUDENT_COMPLAINTS, method = RequestMethod.POST)
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
	@RequestMapping(value = RestAPI.ADD_FACULTY_COMPLAINTS, method = RequestMethod.POST)
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
	@RequestMapping(value = RestAPI.ADD_CWN_COMPLAINTS, method = RequestMethod.POST)
	public ResponseEntity<?> addCWNComplaint(@RequestBody List<CWNComplaintForm> cwnComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			long formId = cwnComplaintRepository.maxOfFormId();
			int size = cwnComplaintForm.size();
			int count = 0;
			List<CWNComplaints> complaintList = new ArrayList<>();
			for (CWNComplaintForm complaint : cwnComplaintForm) {
				if (!cwnComplaintRepository.existsByLocationAndDetailsAndStatusNot(complaint.getLocation(),
						complaint.getDetails(), "Resolved")) {
					CWNComplaints cwnComplaints = new CWNComplaints(complaint.getDetails(), complaint.getLocation());
					cwnComplaints.setCreatedBy(id);
					cwnComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
					cwnComplaints.setType("CWN");
					cwnComplaints.setStatus("Not Assigned");
					cwnComplaints.setFormId(formId + 1);
					complaintList.add(cwnComplaints);
					count++;
				}
			}
			if (count == size) {
				List<CWNComplaints> test = cwnComplaintRepository.saveAll(complaintList);
				if (test != null)
					return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
							HttpStatus.OK);
				else
					return new ResponseEntity<>(
							new ResponseMessage("Unable to record Complaint, Please try again later!"),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new ResponseMessage(
						"One of Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add Engineering Cell / Central Workshop Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = RestAPI.ADD_ECCW_COMPLAINTS, method = RequestMethod.POST)
	public ResponseEntity<?> addECCWComplaint(@RequestBody List<ECCWComplaintForm> eccwComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			long formId = eccwComplaintRepository.maxOfFormId();
			int size = eccwComplaintForm.size();
			int count = 0;
			List<ECCWComplaints> complaintList = new ArrayList<>();
			for (ECCWComplaintForm complaint : eccwComplaintForm) {
				if (!eccwComplaintRepository.existsByLocationAndDetailsAndStatusNot(complaint.getLocation(),
						complaint.getDetails(), "Resolved")) {
					ECCWComplaints eccwComplaints = new ECCWComplaints(complaint.getDetails(), complaint.getLocation());
					eccwComplaints.setCreatedBy(id);
					eccwComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
					eccwComplaints.setType("ECCW");
					eccwComplaints.setStatus("Not Assigned");
					eccwComplaints.setFormId(formId + 1);
					complaintList.add(eccwComplaints);
					count++;
				}
			}
			if (count == size) {
				List<ECCWComplaints> test = eccwComplaintRepository.saveAll(complaintList);
				if (test != null)
					return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
							HttpStatus.OK);
				else
					return new ResponseEntity<>(
							new ResponseMessage("Unable to record Complaint, Please try again later!"),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new ResponseMessage(
						"One of Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add Electrical Maintenance and Repairs Section Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = RestAPI.ADD_EMRS_COMPLAINTS, method = RequestMethod.POST)
	public ResponseEntity<?> addEMRSComplaint(@RequestBody List<EMRSComplaintForm> emrsComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			long formId = emrsComplaintRepository.maxOfFormId();
			int size = emrsComplaintForm.size();
			int count = 0;
			List<EMRSComplaints> complaintList = new ArrayList<>();
			for (EMRSComplaintForm complaint : emrsComplaintForm) {
				if (!emrsComplaintRepository.existsByLocationAndDetailsAndStatusNot(complaint.getLocation(),
						complaint.getDetails(), "Resolved")) {
					EMRSComplaints emrsComplaints = new EMRSComplaints(complaint.getDetails(), complaint.getLocation());
					emrsComplaints.setCreatedBy(id);
					emrsComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
					emrsComplaints.setType("ECCW");
					emrsComplaints.setStatus("Not Assigned");
					emrsComplaints.setFormId(formId + 1);
					complaintList.add(emrsComplaints);
					count++;
				}
			}
			if (count == size) {
				List<EMRSComplaints> test = emrsComplaintRepository.saveAll(complaintList);
				if (test != null)
					return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
							HttpStatus.OK);
				else
					return new ResponseEntity<>(
							new ResponseMessage("Unable to record Complaint, Please try again later!"),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new ResponseMessage(
						"One of Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add Telephone Complaint", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = RestAPI.ADD_TELEPHONE_COMPLAINTS, method = RequestMethod.POST)
	public ResponseEntity<?> addTelephoneComplaint(@RequestBody List<TelephoneComplaintForm> telephoneComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			long formId = telephoneComplaintRepository.maxOfFormId();
			int size = telephoneComplaintForm.size();
			int count = 0;
			List<TelephoneComplaints> complaintList = new ArrayList<>();
			for (TelephoneComplaintForm complaint : telephoneComplaintForm) {
				if (!telephoneComplaintRepository.existsByExtensionNoAndLocationAndDetailsAndStatusNot(
						complaint.getExtensionNo(), complaint.getLocation(), complaint.getDetails(), "Resolved")) {
					TelephoneComplaints telephoneComplaints = new TelephoneComplaints(complaint.getExtensionNo(),
							complaint.getDetails(), complaint.getLocation());
					telephoneComplaints.setCreatedBy(id);
					telephoneComplaints.setCreatedDate(simpleDateFormat.format(new Date()));
					telephoneComplaints.setType("TELEPHONE");
					telephoneComplaints.setStatus("Not Assigned");
					telephoneComplaints.setFormId(formId + 1);
					complaintList.add(telephoneComplaints);
					count++;
				}
			}
			if (count == size) {
				List<TelephoneComplaints> test = telephoneComplaintRepository.saveAll(complaintList);
				if (test != null)
					return new ResponseEntity<>(new ResponseMessage("Your Complaint has been registered successfully!"),
							HttpStatus.OK);
				else
					return new ResponseEntity<>(
							new ResponseMessage("Unable to record Complaint, Please try again later!"),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new ResponseMessage(
						"One of Your Complaint is already registered, You will be informed of the action taken on your complaint!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this complaint!"),
					HttpStatus.BAD_REQUEST);
	}

	// Edit Complaints

	@ApiOperation(value = "Edit Complaint", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = RestAPI.EDIT_COMPLAINTS, method = RequestMethod.PUT)
	public ResponseEntity<?> editComplaint(@RequestBody EditComplaintForm editComplaintForm,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		
		switch (editComplaintForm.getType()) {
		
		case "CL":
			Optional<CleanlinessComplaints> cc = cleanlinessComplaintRepository.findById(editComplaintForm.getId());
			if (cc.isPresent()) {
				cc.get().setModifiedBy(id);
				cc.get().setModifiedDate(simpleDateFormat.format(new Date()));
				cc.get().setStatus(editComplaintForm.getStatus());
				cc.get().setRemarks(editComplaintForm.getRemarks());
				if(editComplaintForm.getStatus().equals("Resolved"))
					cc.get().setDateOfResolution(simpleDateFormat.format(new Date()));
				cleanlinessComplaintRepository.save(cc.get());
				return new ResponseEntity<>(new ResponseMessage("Complaint has been updated successfully!"), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>( new ResponseMessage("Unable to find Complaint, Please try again later!"), HttpStatus.BAD_REQUEST);
		
		case "LE":
			Optional<LEComplaints> lec = leComplaintRepository.findById(editComplaintForm.getId());
			if (lec.isPresent()) {
				lec.get().setModifiedBy(id);
				lec.get().setModifiedDate(simpleDateFormat.format(new Date()));
				lec.get().setStatus(editComplaintForm.getStatus());
				lec.get().setRemarks(editComplaintForm.getRemarks());
				if(editComplaintForm.getStatus().equals("Resolved"))
					lec.get().setDateOfResolution(simpleDateFormat.format(new Date()));
				leComplaintRepository.save(lec.get());
				return new ResponseEntity<>(new ResponseMessage("Complaint has been updated successfully!"), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>( new ResponseMessage("Unable to find Complaint, Please try again later!"), HttpStatus.BAD_REQUEST);
			
		case "STUDENT":
			Optional<StudentComplaints> sc = studentComplaintRepository.findById(editComplaintForm.getId());
			if (sc.isPresent()) {
				sc.get().setModifiedBy(id);
				sc.get().setModifiedDate(simpleDateFormat.format(new Date()));
				sc.get().setStatus(editComplaintForm.getStatus());
				sc.get().setRemarks(editComplaintForm.getRemarks());
				if(editComplaintForm.getStatus().equals("Resolved"))
					sc.get().setDateOfResolution(simpleDateFormat.format(new Date()));
				studentComplaintRepository.save(sc.get());
				return new ResponseEntity<>(new ResponseMessage("Complaint has been updated successfully!"), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>( new ResponseMessage("Unable to find Complaint, Please try again later!"), HttpStatus.BAD_REQUEST);
			
		case "FACULTY":
			Optional<FacultyComplaints> fc = facultyComplaintRepository.findById(editComplaintForm.getId());
			if (fc.isPresent()) {
				fc.get().setModifiedBy(id);
				fc.get().setModifiedDate(simpleDateFormat.format(new Date()));
				fc.get().setStatus(editComplaintForm.getStatus());
				fc.get().setRemarks(editComplaintForm.getRemarks());
				if(editComplaintForm.getStatus().equals("Resolved"))
					fc.get().setDateOfResolution(simpleDateFormat.format(new Date()));
				facultyComplaintRepository.save(fc.get());
				return new ResponseEntity<>(new ResponseMessage("Complaint has been updated successfully!"), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>( new ResponseMessage("Unable to find Complaint, Please try again later!"), HttpStatus.BAD_REQUEST);

		case "OTHER":
			Optional<OtherComplaints> other = otherComplaintRepository.findById(editComplaintForm.getId());
			if (other.isPresent()) {
				other.get().setModifiedBy(id);
				other.get().setModifiedDate(simpleDateFormat.format(new Date()));
				other.get().setStatus(editComplaintForm.getStatus());
				other.get().setRemarks(editComplaintForm.getRemarks());
				other.get().setAssignedTo(editComplaintForm.getAssignedTo());
				if(editComplaintForm.getStatus().equals("Resolved"))
					other.get().setDateOfResolution(simpleDateFormat.format(new Date()));
				otherComplaintRepository.save(other.get());
				return new ResponseEntity<>(new ResponseMessage("Complaint has been updated successfully!"), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>( new ResponseMessage("Unable to find Complaint, Please try again later!"), HttpStatus.BAD_REQUEST);

		case "CWN":
			Optional<CWNComplaints> cwn = cwnComplaintRepository.findById(editComplaintForm.getId());
			if (cwn.isPresent()) {
				cwn.get().setModifiedBy(id);
				cwn.get().setModifiedDate(simpleDateFormat.format(new Date()));
				cwn.get().setStatus(editComplaintForm.getStatus());
				cwn.get().setRemarks(editComplaintForm.getRemarks());
				if(editComplaintForm.getStatus().equals("Resolved"))
					cwn.get().setDateOfResolution(simpleDateFormat.format(new Date()));
				cwnComplaintRepository.save(cwn.get());
				return new ResponseEntity<>(new ResponseMessage("Complaint has been updated successfully!"), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>( new ResponseMessage("Unable to find Complaint, Please try again later!"), HttpStatus.BAD_REQUEST);
			
		case "ECCW":
			Optional<ECCWComplaints> eccw = eccwComplaintRepository.findById(editComplaintForm.getId());
			if (eccw.isPresent()) {
				eccw.get().setModifiedBy(id);
				eccw.get().setModifiedDate(simpleDateFormat.format(new Date()));
				eccw.get().setStatus(editComplaintForm.getStatus());
				eccw.get().setRemarks(editComplaintForm.getRemarks());
				if(editComplaintForm.getStatus().equals("Resolved"))
					eccw.get().setDateOfResolution(simpleDateFormat.format(new Date()));
				eccwComplaintRepository.save(eccw.get());
				return new ResponseEntity<>(new ResponseMessage("Complaint has been updated successfully!"), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>( new ResponseMessage("Unable to find Complaint, Please try again later!"), HttpStatus.BAD_REQUEST);

		case "EMRS":
			Optional<EMRSComplaints> emrs = emrsComplaintRepository.findById(editComplaintForm.getId());
			if (emrs.isPresent()) {
				emrs.get().setModifiedBy(id);
				emrs.get().setModifiedDate(simpleDateFormat.format(new Date()));
				emrs.get().setStatus(editComplaintForm.getStatus());
				emrs.get().setRemarks(editComplaintForm.getRemarks());
				if(editComplaintForm.getStatus().equals("Resolved"))
					emrs.get().setDateOfResolution(simpleDateFormat.format(new Date()));
				emrsComplaintRepository.save(emrs.get());
				return new ResponseEntity<>(new ResponseMessage("Complaint has been updated successfully!"), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>( new ResponseMessage("Unable to find Complaint, Please try again later!"), HttpStatus.BAD_REQUEST);

		case "TELEPHONE":
			Optional<TelephoneComplaints> tc = telephoneComplaintRepository.findById(editComplaintForm.getId());
			if (tc.isPresent()) {
				tc.get().setModifiedBy(id);
				tc.get().setModifiedDate(simpleDateFormat.format(new Date()));
				tc.get().setStatus(editComplaintForm.getStatus());
				tc.get().setRemarks(editComplaintForm.getRemarks());
				if(editComplaintForm.getStatus().equals("Resolved"))
					tc.get().setDateOfResolution(simpleDateFormat.format(new Date()));
				telephoneComplaintRepository.save(tc.get());
				return new ResponseEntity<>(new ResponseMessage("Complaint has been updated successfully!"), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>( new ResponseMessage("Unable to find Complaint, Please try again later!"), HttpStatus.BAD_REQUEST);
		
		}
		return null;
	}

}