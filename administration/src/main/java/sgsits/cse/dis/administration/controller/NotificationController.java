package sgsits.cse.dis.administration.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.administration.constants.RestAPI;
import sgsits.cse.dis.administration.feign.UserClient;
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.StaffNotification;
import sgsits.cse.dis.administration.model.StudentNotification;
import sgsits.cse.dis.administration.repo.NotificationRepository;
import sgsits.cse.dis.administration.model.Notification;
import sgsits.cse.dis.administration.repo.StaffNotificationRepository;
import sgsits.cse.dis.administration.repo.StudentNotificationRepository;
import sgsits.cse.dis.administration.response.NotificationResponse;
import sgsits.cse.dis.administration.response.ResponseMessage;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Notification")
public class NotificationController {
	
	JwtResolver jwtResolver = new JwtResolver();
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	StudentNotificationRepository studentNotificationRepository;
	
	@Autowired
	StaffNotificationRepository staffNotificationRepository;
	
	@Autowired
	UserClient userClient;
	
	@ApiOperation(value="Show Staff's notification", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_STAFF_NOTIFICATION, method = RequestMethod.GET)
	public List<NotificationResponse> getStaffNotification(HttpServletRequest request) {
		
		String id = Long.toString(jwtResolver.getIdFromJwtToken(request.getHeader("Authorization")));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {		
			List<StaffNotification> staffNotifications;
			List<Long> notificationId = new ArrayList<Long>();
			
			staffNotifications = staffNotificationRepository.findByUserIdContainingAndStatus(id,0);
			for(StaffNotification staffNotification : staffNotifications) {
				notificationId.add(staffNotification.getNotificationId());
			}
			
			staffNotifications = staffNotificationRepository.findByUserIdContainingAndStatus(id,1);
			for(StaffNotification staffNotification : staffNotifications) {
				notificationId.add(staffNotification.getNotificationId());
			}
			
			List<Notification> notifications = new ArrayList<Notification>();
			for(Long notiId : notificationId) {
				notifications.add(notificationRepository.findByNotificationId(notiId));
			}
			
			List<NotificationResponse> notificationResponses = new ArrayList<NotificationResponse>();
			for(Notification temp : notifications) {
				NotificationResponse notificationResponse = new NotificationResponse();
				notificationResponse.setContent(temp.getContent());
				notificationResponse.setOrigin(temp.getOrigin());
				notificationResponse.setLink(temp.getLink());
				notificationResponses.add(notificationResponse);		
			}
			
			return notificationResponses;
			
		}
		
		
		return null;

	}
	
	
	@ApiOperation(value="Show Students's notification", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_STUDENT_NOTIFICATION, method = RequestMethod.GET)
	public List<NotificationResponse> getStudentNotification(HttpServletRequest request){
		
		String id = Long.toString(jwtResolver.getIdFromJwtToken(request.getHeader("Authorization")));
		if (jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			
			List<StudentNotification> studentNotifications;
			List<Long> notificationId = new ArrayList<Long>();
			
			studentNotifications=studentNotificationRepository.findByUserIdContaining(id);
			for(StudentNotification studentNotification: studentNotifications) {
				notificationId.add(studentNotification.getNotificationId());
			}	
			
			
			studentNotifications=studentNotificationRepository.findByCourseAndAdmissionYearAndUserId(userClient.getCourse(Long.parseLong(id)),userClient.getAdmissionYear(Long.parseLong(id)),"ALL");
			for(StudentNotification studentNotification: studentNotifications) {
				notificationId.add(studentNotification.getNotificationId());
			}	
			
			List<Notification> notifications = new ArrayList<Notification>();
			for(Long notiId : notificationId) {
				notifications.add(notificationRepository.findByNotificationId(notiId));
			}
			
			List<NotificationResponse> notificationResponses = new ArrayList<NotificationResponse>();
			for(Notification temp : notifications) {
				NotificationResponse notificationResponse = new NotificationResponse();
				notificationResponse.setContent(temp.getContent());
				notificationResponse.setOrigin(temp.getOrigin());
				notificationResponse.setLink(temp.getLink());
				notificationResponses.add(notificationResponse);		
			}
			
			return notificationResponses;
			
		}
		
		return null;
	}
	
	
	@ApiOperation(value="update notification status", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = RestAPI.UPDATE_NOTIFICATION_STATUS, method = RequestMethod.POST)
	public ResponseEntity<?> updateNotificationStatus(HttpServletRequest request) {
		String id = Long.toString(jwtResolver.getIdFromJwtToken(request.getHeader("Authorization")));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			List<StaffNotification> staffNotifications = new ArrayList<>();
			staffNotifications = staffNotificationRepository.findByUserIdContainingAndStatus(id,0);
			for(StaffNotification staffNotification:staffNotifications) {
				staffNotification.setStatus(1);
			}
			
			List<StaffNotification> test = staffNotificationRepository.saveAll(staffNotifications);

			if (test.size()>0)
				return new ResponseEntity<>(new ResponseMessage(+test.size()+" Records updated successfully!"),HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("No records to be update"),HttpStatus.OK);
		}
		return null;
	}
	
}
