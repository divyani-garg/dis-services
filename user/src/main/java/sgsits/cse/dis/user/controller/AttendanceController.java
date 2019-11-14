package sgsits.cse.dis.user.controller;

import java.text.ParseException;
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
import sgsits.cse.dis.user.message.response.ResponseMessage;
import sgsits.cse.dis.user.feign.AcademicsClient;
import sgsits.cse.dis.user.feign.SubjectListResponse;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.message.request.StudentLeaveApplicationForm;
import sgsits.cse.dis.user.message.response.StudentAttendancePercentage;
import sgsits.cse.dis.user.model.StudentAttendance;
import sgsits.cse.dis.user.model.StudentLeave;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.repo.StudentAttendanceRepository;
import sgsits.cse.dis.user.repo.StudentLeaveRepository;
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
	@Autowired
	StudentLeaveRepository studentLeaveRepository;

	JwtResolver jwtResolver = new JwtResolver();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

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

				List<StudentAttendance> theory = studentAttendanceRepository
						.findByEnrollmentIdAndSubjectCodeAndClassType(enrollment, percentList.get(i).getSubjectCode(),
								'T');
				int totalTheoryLecture = 0;
				for (StudentAttendance at : theory) {
					totalTheoryLecture = totalTheoryLecture + at.getLectureCount();
				}

				List<StudentAttendance> practical = studentAttendanceRepository
						.findByEnrollmentIdAndSubjectCodeAndClassType(enrollment, percentList.get(i).getSubjectCode(),
								'P');
				int totalPracticalLecture = 0;
				for (StudentAttendance at : practical) {
					totalPracticalLecture = totalPracticalLecture + at.getLectureCount();
				}

				percentList.get(i).setTotaltheorylecture(totalTheoryLecture);
				percentList.get(i).setTotalpracticallecture(totalPracticalLecture);

				if (totalTheoryLecture != 0) {
					List<StudentAttendance> attendance = studentAttendanceRepository
							.findByEnrollmentIdAndSubjectCodeAndClassTypeAndAttendanceNot(enrollment,
									percentList.get(i).getSubjectCode(), 'T', 0);
					int attendedTheory = 0;
					for (StudentAttendance at : attendance) {
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
					for (StudentAttendance at : attendance) {
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
	public List<StudentAttendance> getStudentAttendance(@PathVariable("subjectCode") String subjectCode,
			HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		Optional<StudentProfile> student = studentRepository.findByUserId(id);
		if (student.isPresent()) {
			String enrollment = student.get().getEnrollmentId();
			List<StudentAttendance> attendance = studentAttendanceRepository
					.findByEnrollmentIdAndSubjectCode(enrollment, subjectCode);
			return attendance;
		}
		return null;
	}

	@ApiOperation(value = "studentLeaveAccount", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/studentLeaveAccount", method = RequestMethod.GET)
	public Optional<StudentLeave> getLeaveAccount(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		Optional<StudentLeave> leaves = studentLeaveRepository.findById(id);
		return leaves;
	}

	@ApiOperation(value = "studentLeaveApplication", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/studentLeaveApplication", method = RequestMethod.POST)
	public ResponseEntity<?> applyForLeave(@RequestBody StudentLeaveApplicationForm leaveApplicationForm,
			HttpServletRequest request) throws ParseException {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!studentLeaveRepository.existsByCreatedByAndFrom(id,
				simpleDateFormat1.format(leaveApplicationForm.getFrom()))) {
			StudentLeave leave = new StudentLeave();
			leave.setCreatedBy(id);
			leave.setCreatedDate(simpleDateFormat.format(new Date()));
			leave.setFrom(simpleDateFormat1.format(leaveApplicationForm.getFrom()));
			leave.setTo(simpleDateFormat1.format(leaveApplicationForm.getTo()));
			leave.setSubject(leaveApplicationForm.getSubject());
			leave.setDetails(leaveApplicationForm.getDetails());
			leave.setStatus("Applied");
			StudentLeave test = studentLeaveRepository.save(leave);
			if (test != null)
				return new ResponseEntity<>(
						new ResponseMessage("Your Leave Application has been submitted successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(
						new ResponseMessage("Unable to record leave application, Please try again later!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(new ResponseMessage(
					"You have already applied for the leave on this days, You will be informed of the action taken on your application!"),
					HttpStatus.BAD_REQUEST);
	}
}