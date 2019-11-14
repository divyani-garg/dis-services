package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
import sgsits.cse.dis.administration.constants.RestAPI;
import sgsits.cse.dis.administration.feign.UserClient;
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.FacultyResourceRequest;
import sgsits.cse.dis.administration.repo.FacultyResourceRequestRepository;
import sgsits.cse.dis.administration.request.FacultyResourceRequestForm;
import sgsits.cse.dis.administration.response.FacultyResourceRequestResponse;
import sgsits.cse.dis.administration.response.ResponseMessage;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Request Resource")
public class RequestController {

	@Autowired
	FacultyResourceRequestRepository facultyResourceRequestRepository;
	
	@Autowired
	UserClient userClient;

	JwtResolver jwtResolver = new JwtResolver();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@ApiOperation(value = "Add Faculty Resource Request", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = RestAPI.ADD_FACULTY_RESOURCE_REQUEST, method = RequestMethod.POST)
	
	public ResponseEntity<?> addFacultyResourceRequest(@RequestBody FacultyResourceRequestForm facultyResourceRequestForm, HttpServletRequest request) {
		
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			if (!facultyResourceRequestRepository.existsByCreatedByAndResourceCategoryAndDetailsAndStatusNot(id, facultyResourceRequestForm.getResourceCategory(), facultyResourceRequestForm.getDetails(), "Resolved")) {
				
				FacultyResourceRequest facultyResourceRequest = new FacultyResourceRequest(facultyResourceRequestForm.getResourceCategory(), facultyResourceRequestForm.getDetails(),facultyResourceRequestForm.getPriority(),facultyResourceRequestForm.getDeadlineOfResolution());
				facultyResourceRequest.setCreatedBy(id);
				facultyResourceRequest.setCreatedDate(simpleDateFormat.format(new Date()));
				facultyResourceRequest.setStatus("Not Assigned");
				FacultyResourceRequest test = facultyResourceRequestRepository.save(facultyResourceRequest);
				
				if (test != null)
					return new ResponseEntity<>(new ResponseMessage("Your Request has been registered successfully!"),
							HttpStatus.OK);
				else
					return new ResponseEntity<>(
							new ResponseMessage("Unable to record Request, Please try again later!"),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new ResponseMessage(
						"Your Request is already registered, You will be informed of the action taken on your request!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this request!"),
					HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value="Show Faculty Resource Request", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_FACULTY_RESOURCE_REQUEST, method = RequestMethod.GET)
	public List<FacultyResourceRequestResponse> getFacultyResourceRequest(@PathVariable String keyword,HttpServletRequest request){
		
		 	long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		 	
		 	if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
		 		if(jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("staff") || jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("faculty")) {
		 			if(facultyResourceRequestRepository.existsByCreatedBy(id)) {
		 				
		 				List<FacultyResourceRequestResponse> facultyResourceRequestResponses = new ArrayList<>();
		 				List<FacultyResourceRequest> facultyResourceRequests;
		 				if (keyword.equals("Resolved"))
			 				facultyResourceRequests = facultyResourceRequestRepository.findByCreatedByAndStatus(id,"Resolved");
			 			else
			 				facultyResourceRequests = facultyResourceRequestRepository.findByCreatedByAndStatusNot(id,"Resolved");
		 				
		 				for(FacultyResourceRequest facultyResourceRequest: facultyResourceRequests)
		 				{
		 					FacultyResourceRequestResponse temp = new FacultyResourceRequestResponse();
		 					temp.setCreatedById(facultyResourceRequest.getCreatedBy());
		 					temp.setCreatedByName(null);
		 					temp.setCreatedByDesignation(null);
		 					temp.setCreatedDate(facultyResourceRequest.getCreatedDate());
		 					temp.setResourceCategory(facultyResourceRequest.getResourceCategory());
		 					temp.setDetails(facultyResourceRequest.getDetails());
		 					temp.setStatus(facultyResourceRequest.getStatus());
		 					temp.setDateOfResolution(facultyResourceRequest.getDateOfResolution());
		 					temp.setRemarks(facultyResourceRequest.getRemarks());
		 					
		 					facultyResourceRequestResponses.add(temp);
		 				}
		 				return facultyResourceRequestResponses;
		 			}
		 			else
		 				return null;
		 		}
		 		else {
		 			List<FacultyResourceRequestResponse> facultyResourceRequestResponses = new ArrayList<>();
		 			List<FacultyResourceRequest> facultyResourceRequests;
		 			if (keyword.equals("Resolved"))
		 				facultyResourceRequests = facultyResourceRequestRepository.findByStatus("Resolved");
		 			else
		 				facultyResourceRequests = facultyResourceRequestRepository.findByStatusNot("Resolved");
	 				for(FacultyResourceRequest facultyResourceRequest: facultyResourceRequests)
	 				{
	 					FacultyResourceRequestResponse temp = new FacultyResourceRequestResponse();
	 					temp.setCreatedById(facultyResourceRequest.getCreatedBy());
	 					String userName = userClient.getUserName(facultyResourceRequest.getCreatedBy());
	 					String userDesignation = userClient.getUserCurrentDesignation(facultyResourceRequest.getCreatedBy());
	 					temp.setCreatedByName(userName);
	 					temp.setCreatedByDesignation(userDesignation);
	 					temp.setCreatedDate(facultyResourceRequest.getCreatedDate());
	 					temp.setResourceCategory(facultyResourceRequest.getResourceCategory());
	 					temp.setDetails(facultyResourceRequest.getDetails());
	 					temp.setStatus(facultyResourceRequest.getStatus());
	 					temp.setDateOfResolution(facultyResourceRequest.getDateOfResolution());
	 					temp.setRemarks(facultyResourceRequest.getRemarks());
	 					
	 					facultyResourceRequestResponses.add(temp);
	 				}
	 				return facultyResourceRequestResponses;
		 			
		 		}
		 	}
		
		 	return null;
	}
	
	
	@ApiOperation(value="Search Faculty Resource Request", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.SEARCH_FACULTY_RESOURCE_REQUEST, method = RequestMethod.GET)  
	public long searchFacultyResourceRequest(@PathVariable String keyword,HttpServletRequest request){
		
		long id = userClient.getUserId(keyword);
		return id;
	}
}
	