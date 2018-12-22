package sgsits.cse.dis.academics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.academics.model.SemesterTimeTable;
import sgsits.cse.dis.academics.repo.SemesterTimeTableRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student") 
@Api(value = "Time Table Resource")
public class TimeTableController {
	
	@Autowired 
	SemesterTimeTableRepository semesterTimeTableRepository;
	
	@ApiOperation(value = "studentTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/student_timetable", method = RequestMethod.GET)
	public List<SemesterTimeTable> getStudentTimeTable()
	{
		String session = "July 2018 - Dec 2018";
		String year = "II";
		String semester = "A";
		List<SemesterTimeTable> semtimetable = semesterTimeTableRepository.findBySessionAndYearAndSemester(session,year,semester);		
		return semtimetable;
	}
	
	public List<SemesterTimeTable> getFacultyTimeTable()
	{
		String faculty;
		//List<SemesterTimeTable> facultytimetable = semesterTimeTableRepository.findBySessionAndYearAndSemester();
		//return facultytimetable;
		return null;
	}
}
