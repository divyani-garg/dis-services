package sgsits.cse.dis.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.feign.AcademicsClient;
import sgsits.cse.dis.user.model.Scheme;
import sgsits.cse.dis.user.model.StudentAttendance;
import sgsits.cse.dis.user.model.StudentAttendancePercentage;
import sgsits.cse.dis.user.repo.StudentAttendanceRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dis/attendance")
@Api(value = "Student Attendance Resource")
public class AttendanceController {
	
	@Autowired
	StudentAttendanceRepository studentAttendanceRepository;
	
	@Autowired
	AcademicsClient academicsClient;

	@ApiOperation(value = "studentAttendancePercentage", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/studentPercentage", method = RequestMethod.GET)
	public List<StudentAttendancePercentage> getAttendancePercentage()
	{
		//get enrollment from session
		String enrollment = "0801CS161022";

		List<StudentAttendancePercentage> percentList = new ArrayList<StudentAttendancePercentage>();
		
		//get subject list from academics service
		List<Scheme> subjects = academicsClient.getSubjectList();
		
		ArrayList<String> subjectList = new ArrayList<String>();
	
		for(Scheme sub : subjects)
		{
			subjectList.add(sub.getSubjectCode());
		}
		
		for(int i = 0; i < subjectList.size() ; i++)
		{
			StudentAttendancePercentage sap = new StudentAttendancePercentage();
			sap.setEnrollmentId(enrollment);
			sap.setSubjectCode(subjectList.get(i));
			percentList.add(sap);
		}

		for(int i = 0 ; i < percentList.size() ; i++)
		{
			int totalTheoryLecture = studentAttendanceRepository.countByEnrollmentIdAndSubjectCodeAndClassType(enrollment, percentList.get(i).getSubjectCode(), 'T');
			int totalPracticalLecture = studentAttendanceRepository.countByEnrollmentIdAndSubjectCodeAndClassType(enrollment, percentList.get(i).getSubjectCode(), 'P');
			percentList.get(i).setTotaltheorylecture(totalTheoryLecture);
			percentList.get(i).setTotalpracticallecture(totalPracticalLecture);
			
			if(totalTheoryLecture != 0 )
			{
				int attendedTheory = studentAttendanceRepository.countByEnrollmentIdAndSubjectCodeAndClassTypeAndAttendance(enrollment, percentList.get(i).getSubjectCode(), 'T', 'P');
				percentList.get(i).setTheoryPercent((attendedTheory*100)/totalTheoryLecture);
			}
			else
			{
				percentList.get(i).setTheoryPercent(0);
			}
			if(totalPracticalLecture != 0)
			{
				int attendedPractical = studentAttendanceRepository.countByEnrollmentIdAndSubjectCodeAndClassTypeAndAttendance(enrollment, percentList.get(i).getSubjectCode(), 'P', 'P');
				percentList.get(i).setPracticalPercent((attendedPractical*100)/totalPracticalLecture);
			}
			else
			{
				percentList.get(i).setPracticalPercent(0);
			}
		}						
		return percentList;
	}
	
	@ApiOperation(value = "studentAttendanceDetail", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/studentAttendance/{subjectCode}", method = RequestMethod.GET)
	public List<StudentAttendance> getStudentAttendance(@PathVariable("subjectCode") String subjectCode)
	{
		//get enrollment from session
		String enrollment = "0801CS161022";
		List<StudentAttendance> attendance = studentAttendanceRepository.findByEnrollmentIdAndSubjectCode(enrollment, subjectCode);
		return attendance;
	}
}