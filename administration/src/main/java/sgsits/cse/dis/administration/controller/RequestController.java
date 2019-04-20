package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.FacultyResourceRequest;
import sgsits.cse.dis.administration.repo.FacultyResourceRequestRepository;
import sgsits.cse.dis.administration.request.FacultyResourceRequestForm;
import sgsits.cse.dis.administration.response.ResponseMessage;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Request Resource")
public class RequestController {

	@Autowired
	FacultyResourceRequestRepository facultyResourceRequestRepository;

	JwtResolver jwtResolver = new JwtResolver();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@ApiOperation(value = "Add Faculty Resource Request", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addFacultyResourceRequest", method = RequestMethod.POST)
	public ResponseEntity<?> addFacultyResourceRequest(
			@RequestBody FacultyResourceRequestForm facultyResourceRequestForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			if (!facultyResourceRequestRepository.existsByCreatedByAndResourceAndDetailsAndStatusNot(id,
					facultyResourceRequestForm.getResource(), facultyResourceRequestForm.getDetails(), "Resolved")) {
				FacultyResourceRequest facultyResourceRequest = new FacultyResourceRequest(
						facultyResourceRequestForm.getResource(), facultyResourceRequestForm.getDetails());
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

}
