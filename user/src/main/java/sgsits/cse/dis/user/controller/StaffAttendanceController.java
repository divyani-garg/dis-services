package sgsits.cse.dis.user.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.message.request.StaffLeaveForm;
import sgsits.cse.dis.user.message.request.UserQualificationForm;
import sgsits.cse.dis.user.message.response.ResponseMessage;
import sgsits.cse.dis.user.message.response.StaffLeaveResponse;
import sgsits.cse.dis.user.model.StaffLeave;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.User;
import sgsits.cse.dis.user.model.UserQualification;
import sgsits.cse.dis.user.repo.StaffLeaveRepository;
import sgsits.cse.dis.user.repo.StaffRepository;
import sgsits.cse.dis.user.repo.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Staff Attendance Controller")
public class StaffAttendanceController {
	
	@Autowired
	StaffLeaveRepository staffLeaveRepository;
	
	@Autowired
	StaffRepository staffRepository;
	
	
	JwtResolver jwtResolver = new JwtResolver();
	
	@ApiOperation(value = "Apply for Leave", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/applyForLeave", method = RequestMethod.POST)
	public ResponseEntity<?> applyForLeave(@RequestBody StaffLeaveForm staffLeaveForm, HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("faculty") || jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("head") || jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("staff"))
		{
			StaffLeave staffLeave = new StaffLeave();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			staffLeave.setCreatedBy(id);
			staffLeave.setCreatedDate(simpleDateFormat.format(new Date()));
			staffLeave.setToDate(staffLeaveForm.getToDate());
			staffLeave.setFromDate(staffLeaveForm.getFromDate());
			staffLeave.setSubject(staffLeaveForm.getSubject());
			staffLeave.setDetails(staffLeaveForm.getDetails());
			staffLeave.setRemarks(staffLeaveForm.getRemarks());
			staffLeave.setStatus("Applied");
			staffLeave.setTypeOfLeave(staffLeaveForm.getTypeOfLeave());
			staffLeave.setHalfdayFullday(staffLeaveForm.getHalfdayFullday());
		
			StaffLeave test=staffLeaveRepository.save(staffLeave);
			if(test!=null)
					return new ResponseEntity<>(new ResponseMessage("Applied for leave Successfully!"), HttpStatus.OK);
			else
					return new ResponseEntity<>(new ResponseMessage("Unable to apply for leave, please try again later!"), HttpStatus.BAD_REQUEST);
		}
		else
					return new ResponseEntity<>(new ResponseMessage("You are not allowed to apply for leave!"), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Leave Account for Staff", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/leaveAccountForStaff", method = RequestMethod.GET)
	public List<StaffLeave> leaveAccountForStaff(HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		
		if(jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("staff") || jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("faculty"))
		{
			List<StaffLeave> staffLeaves = staffLeaveRepository.findByCreatedBy(id);
			 return staffLeaves;
		}
		else
			return null;
					
	}
	
	@ApiOperation(value = "Leave Account for Head", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/leaveAccountForHead", method = RequestMethod.GET)
	public List<StaffLeave> leaveAccountForHead(HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("head"))
		{
			List<StaffLeave> staffLeaves = staffLeaveRepository.findAll();
			 return staffLeaves;
		}
		else
			return null;
	}

	@ApiOperation(value = "Leave Account By Name", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/leaveAccount/ByName/{keyword}", method = RequestMethod.GET)
	public List<StaffLeaveResponse> leaveAccountByName(@PathVariable String keyword,HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("head"))
		{
			List<StaffLeave> staffLeave = staffLeaveRepository.findAll();
			if(staffLeave!=null)
			{
				String name;
				List<StaffLeaveResponse> result = new ArrayList<>();
				List<StaffProfile> staff = staffRepository.findByName(keyword);
				name = staff.get(0).getName();
				//System.out.println(name);
				for(StaffLeave leave : staffLeave)
				{
					if(staff.get(0).getUserId() == leave.getCreatedBy())
					{
						StaffLeaveResponse res = new StaffLeaveResponse();
						res.setUserId(leave.getCreatedBy());
						res.setCreatedDate(leave.getCreatedDate());
						res.setName(name);
						res.setToDate(leave.getToDate());
						res.setFromDate(leave.getFromDate());
						res.setSubject(leave.getSubject());
						res.setDetails(leave.getDetails());
						res.setRemarks(leave.getRemarks());
						res.setTypeOfLeave(leave.getTypeOfLeave());
						res.setHalfdayFullday(leave.getHalfdayFullday());
						
						result.add(res);
					}
					
				}
				return result;
			}
				return null;			
		}
		else
			return null;
					
	}
	
	@ApiOperation(value = "Leave Account By Type of Leave", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/leaveAccount/ByTypeOfLeave/{keyword}", method = RequestMethod.GET)
	public List<StaffLeaveResponse> leaveAccountByTypeOfLeave(@PathVariable String keyword,HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("head"))
		{
			List<StaffLeave> staffLeave = staffLeaveRepository.findAll();
			if(staffLeave!=null)
			{
				String name;
				List<StaffLeaveResponse> result = new ArrayList<>();
				for(StaffLeave leave : staffLeave)
				{
					if(leave.getTypeOfLeave().equalsIgnoreCase(keyword))
					{
						StaffLeaveResponse res = new StaffLeaveResponse();
						res.setUserId(leave.getCreatedBy());
						res.setCreatedDate(leave.getCreatedDate());
						Optional<StaffProfile> staff = staffRepository.findByUserId(leave.getCreatedBy());
						name = staff.get().getName();
						res.setName(name);
						res.setToDate(leave.getToDate());
						res.setFromDate(leave.getFromDate());
						res.setSubject(leave.getSubject());
						res.setDetails(leave.getDetails());
						res.setRemarks(leave.getRemarks());
						res.setTypeOfLeave(leave.getTypeOfLeave());
						res.setHalfdayFullday(leave.getHalfdayFullday());
						
						result.add(res);
					}
					
				}
				return result;
			}
				return null;			
		}
		else
			return null;
					
	}
	
	@ApiOperation(value = "Leave Account By Status", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/leaveAccount/ByStatus/{keyword}", method = RequestMethod.GET)
	public List<StaffLeaveResponse> leaveAccountByStatus(@PathVariable String keyword,HttpServletRequest request)
	{
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("head"))
		{
			List<StaffLeave> staffLeave = staffLeaveRepository.findAll();
			if(staffLeave!=null)
			{
				String name;
				List<StaffLeaveResponse> result = new ArrayList<>();
				for(StaffLeave leave : staffLeave)
				{
					if(leave.getStatus().equalsIgnoreCase(keyword))
					{
						StaffLeaveResponse res = new StaffLeaveResponse();
						res.setUserId(leave.getCreatedBy());
						res.setCreatedDate(leave.getCreatedDate());
						Optional<StaffProfile> staff = staffRepository.findByUserId(leave.getCreatedBy());
						name = staff.get().getName();
						res.setName(name);
						res.setToDate(leave.getToDate());
						res.setFromDate(leave.getFromDate());
						res.setSubject(leave.getSubject());
						res.setDetails(leave.getDetails());
						res.setRemarks(leave.getRemarks());
						res.setTypeOfLeave(leave.getTypeOfLeave());
						res.setHalfdayFullday(leave.getHalfdayFullday());
						
						result.add(res);
					}
					
				}
				return result;
			}
				return null;			
		}
		else
			return null;
					
	}
	
	
}

	
