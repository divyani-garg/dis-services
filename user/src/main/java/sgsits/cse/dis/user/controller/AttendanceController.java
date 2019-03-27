package sgsits.cse.dis.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.feign.AcademicsClient;
import sgsits.cse.dis.user.feign.SubjectListResponse;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.message.response.StudentAttendancePercentage;
import sgsits.cse.dis.user.model.StudentAttendance;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.repo.StudentAttendanceRepository;
import sgsits.cse.dis.user.repo.StudentRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Student Attendance Resource")
public class AttendanceController {

	@Autowired
	StudentAttendanceRepository studentAttendanceRepository;
	@Autowired
	AcademicsClient academicsClient;
	@Autowired
	StudentRepository studentRepository;

	JwtResolver jwtResolver = new JwtResolver();

	@ApiOperation(value = "studentAttendancePercentage", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/studentAttendancePercentage", method = RequestMethod.GET)
	public List<StudentAttendancePercentage> getAttendancePercentage(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		Optional<StudentProfile> student = studentRepository.findByUserId(id);
		if (student.isPresent()) {
			String enrollment = student.get().getEnrollmentId();
			List<StudentAttendancePercentage> percentList = new ArrayList<StudentAttendancePercentage>();
			// get subject list from academics service
			List<SubjectListResponse> subjects = academicsClient.getSubjectList(id);
			ArrayList<String> subjectList = new ArrayList<String>();
			for (SubjectListResponse sub : subjects) {
				subjectList.add(sub.getSubjectCode());
			}
			for (int i = 0; i < subjectList.size(); i++) {
				StudentAttendancePercentage sap = new StudentAttendancePercentage();
				sap.setEnrollmentId(enrollment);
				sap.setSubjectCode(subjectList.get(i));
				percentList.add(sap);
			}
			for (int i = 0; i < percentList.size(); i++) {
				
				List<StudentAttendance> theory = studentAttendanceRepository.findByEnrollmentIdAndSubjectCodeAndClassType(
						enrollment, percentList.get(i).getSubjectCode(), 'T');
				int totalTheoryLecture = 0;
				for(StudentAttendance at : theory){
					totalTheoryLecture = totalTheoryLecture + at.getLectureCount();
				}
				
				List<StudentAttendance> practical =  studentAttendanceRepository.findByEnrollmentIdAndSubjectCodeAndClassType(
						enrollment, percentList.get(i).getSubjectCode(), 'P');
				int totalPracticalLecture = 0;
				for(StudentAttendance at : practical){
					totalPracticalLecture = totalPracticalLecture + at.getLectureCount();
				}
				
				percentList.get(i).setTotaltheorylecture(totalTheoryLecture);
				percentList.get(i).setTotalpracticallecture(totalPracticalLecture);
				
				if (totalTheoryLecture != 0) {
					List<StudentAttendance> attendance = studentAttendanceRepository
							.findByEnrollmentIdAndSubjectCodeAndClassTypeAndAttendanceNot(enrollment,
									percentList.get(i).getSubjectCode(), 'T', 0);
					int attendedTheory = 0;
					for(StudentAttendance at : attendance){
						attendedTheory = attendedTheory + at.getAttendance();
					}
					percentList.get(i).setTheoryPercent((attendedTheory * 100) / totalTheoryLecture);
				} else {
					percentList.get(i).setTheoryPercent(0);
				}
				
				if (totalPracticalLecture != 0) {
					List<StudentAttendance> attendance = studentAttendanceRepository
							.findByEnrollmentIdAndSubjectCodeAndClassTypeAndAttendanceNot(enrollment,
									percentList.get(i).getSubjectCode(), 'P', 0);
					int attendedPractical = 0;
					for(StudentAttendance at : attendance){
						attendedPractical = attendedPractical + at.getAttendance();
					}
					percentList.get(i).setPracticalPercent((attendedPractical * 100) / totalPracticalLecture);
				} else {
					percentList.get(i).setPracticalPercent(0);
				}
				
			}
			return percentList;
		}
		return null;
	}

	@ApiOperation(value = "studentAttendanceDetail", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/studentAttendance/{subjectCode}", method = RequestMethod.GET)
	public List<StudentAttendance> getStudentAttendance(@PathVariable("subjectCode") String subjectCode, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		Optional<StudentProfile> student = studentRepository.findByUserId(id);
		if (student.isPresent()) {
			String enrollment = student.get().getEnrollmentId();
			List<StudentAttendance> attendance = studentAttendanceRepository.findByEnrollmentIdAndSubjectCode(enrollment, subjectCode);
		return attendance;
		}
		return null;
	}
	
	public void getLeaveAccount()
	{
		
	}
	
	public void applyForLeave()
	{
		
	}
}