package sgsits.cse.dis.administration.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.administration.constants.RestAPI;
import sgsits.cse.dis.administration.request.NotificationForm;
import sgsits.cse.dis.administration.request.StaffNotificationForm;
import sgsits.cse.dis.administration.response.ResponseMessage;
import sgsits.cse.dis.administration.feign.AcademicsClient;
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.DocumentsFile;
import sgsits.cse.dis.administration.model.StudentNotification;
import sgsits.cse.dis.administration.model.Notification;
import sgsits.cse.dis.administration.repo.DocumentsFileRepository;
import sgsits.cse.dis.administration.repo.NotificationRepository;
import sgsits.cse.dis.administration.repo.StaffNotificationRepository;
import sgsits.cse.dis.administration.repo.StudentNotificationRepository;
import sgsits.cse.dis.administration.service.CurrentYearAndSession;
import sgsits.cse.dis.administration.request.DataForm;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Administration Client Resource")
public class AdministrationClientController {

	@Autowired
	DocumentsFileRepository documentsFileRepository;
	
	@Autowired
	StudentNotificationRepository studentNotificationRepository;
	
	@Autowired
	StaffNotificationRepository staffNotificationRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	AcademicsClient academicsClient;
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	JwtResolver jwtResolver = new JwtResolver();

	@RequestMapping(value = "/fileDownloadLink", method = RequestMethod.GET)
	public String getFileDownloadLink(@RequestParam("id") String id) {
		Optional<DocumentsFile> file = documentsFileRepository.findById(id);
		String link = file.get().getDownloadURL();
		if (link != null)
			return link;
		return null;
	}
	
	@ApiOperation(value="notify students", response = Object.class, httpMethod = "POST",produces = "application/json")
	@RequestMapping(value = RestAPI.NOTIFY_STUDENTS, method = RequestMethod.POST)
	public ResponseEntity<?> notifyStudents(@RequestBody NotificationForm notificationForm,HttpServletRequest request){
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			CurrentYearAndSession currentYearAndSession = new CurrentYearAndSession();
			Notification notification =new Notification();
//			StudentNotification studentNotification = new StudentNotification();
//			
//			int admissionYear = currentYearAndSession.getAdmissionYearFromYear(Integer.parseInt(year));
		
			List<Notification> count = notificationRepository.findAll();
			notification.setNotificationId(count.size()+1l);
			notification.setContent(notificationForm.getContent());
			notification.setOrigin(notificationForm.getOrigin());
			notification.setLink(notificationForm.getLink());
			notification.setCreatedDate(simpleDateFormat.format(new Date()));
			notification.setCreatedBy(id);
//			notification.setModifiedBy(null);
//			notification.setModifiedDate(null);
			Notification test = notificationRepository.save(notification);
//
			if (test!=null)
				return new ResponseEntity<>(new ResponseMessage(" Records updated successfully!"),HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("No records to be update"),HttpStatus.OK);
//			studentNotification.setNotificationId(notificationForm.getNotificationId());
//			studentNotification.setUserId(userId);
//			studentNotification.setCourse(academicsClient.getCoursename(course));
//			studentNotification.setAdmissionYear(admissionYear);
//			studentNotification.setCreatedBy(id);
//			studentNotification.setCreatedDate(simpleDateFormat.format(new Date()));
//			
//			studentNotificationRepository.save(studentNotification);
				
		}		
			return null;
		
	}
	
//	
//	@ApiOperation(value="notify students", response = Object.class, httpMethod = "GET",produces = "application/json")
//	@RequestMapping(value = RestAPI.NOTIFY_STUDENTS, method = RequestMethod.POST)
//	public ResponseEntity<?> notifyStudents(HttpServletRequest request,@PathVariable("content") String content,@PathVariable("origin") String origin ,@PathVariable("link") String link,@PathVariable("year") int year,@PathVariable("course") String course,@PathVariable("userId") String userId){
//		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
//		if(jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
//				CurrentYearAndSession currentYearAndSession = new CurrentYearAndSession();
//				Notification notification =new Notification();
//				StudentNotification studentNotification = new StudentNotification();
//				
//				int admissionYear = currentYearAndSession.getAdmissionYearFromYear(year);
//				
//				notification.setNotificationId(8l);
//				notification.setContent(content);
//				notification.setOrigin(origin);
//				notification.setLink(link);
//				notification.setCreatedDate(simpleDateFormat.format(new Date()));
//				notification.setCreatedBy(id);
//				notificationRepository.save(notification);
//				
//				studentNotification.setNotificationId(8l);
//				studentNotification.setUserId(userId);
//				studentNotification.setCourse(academicsClient.getCoursename(course));
//				studentNotification.setAdmissionYear(admissionYear);
//				studentNotification.setCreatedBy(id);
//				studentNotification.setCreatedDate(simpleDateFormat.format(new Date()));
//				
//				studentNotificationRepository.save(studentNotification);
//		}	
//			
//		return null;
//		
//	}
	
	
//	@ApiOperation(value="notify students", response = Object.class, httpMethod = "GET",produces = "application/json")
//	@RequestMapping(value = RestAPI.NOTIFY_STUDENTS, method = RequestMethod.POST)
//	public ResponseEntity<?> notifyStudents(@RequestBody Map<NotificationForm, DataForm> json,HttpServletRequest request){
//		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
//		CurrentYearAndSession currentYearAndSession = new CurrentYearAndSession();
//		Notification notification =new Notification();
//		StudentNotification studentNotification = new StudentNotification();
//		NotificationForm notificationForm =new NotificationForm();
//		DataForm dataForm = new DataForm();
//		
//		dataForm = json.get("Data");
//		
//		
//		
//		
//		int admissionYear = currentYearAndSession.getAdmissionYearFromYear(dataForm.getYear());
//		
//		notification.setNotificationId(notificationForm.getNotificationId());
//		notification.setContent(notificationForm.getContent());
//		notification.setOrigin(notificationForm.getOrigin());
//		notification.setLink(notificationForm.getLink());
//		notification.setCreatedDate(simpleDateFormat.format(new Date()));
//		notification.setCreatedBy(id);
//		notificationRepository.save(notification);
//		
//		studentNotification.setNotificationId(notificationForm.getNotificationId());
//		studentNotification.setUserId(dataForm.getUserId());
//		studentNotification.setCourse(academicsClient.getCoursename(dataForm.getCourse()));
//		studentNotification.setAdmissionYear(admissionYear);
//		studentNotification.setCreatedBy(id);
//		studentNotification.setCreatedDate(simpleDateFormat.format(new Date()));
//		
//		studentNotificationRepository.save(studentNotification);
//			
//			
//		return null;
//		
//	}
	
	

}