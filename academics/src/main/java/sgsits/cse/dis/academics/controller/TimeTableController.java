package sgsits.cse.dis.academics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.academics.model.SemesterTimeTable;
import sgsits.cse.dis.academics.repo.SemesterTimeTableRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dis/timetable")
@Api(value = "Time Table Resource")
public class TimeTableController {
	
	@Autowired 
	SemesterTimeTableRepository semesterTimeTableRepository;
	
	@ApiOperation(value = "studentTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public List<SemesterTimeTable> getStudentTimeTable()
	{
		String session = "July 2018 - Dec 2018";
		String year = "III";
		String semester = "A";
		List<SemesterTimeTable> semtimetable = semesterTimeTableRepository.findBySessionAndYearAndSemester(session,year,semester);		
		return semtimetable;
	}
	
	@ApiOperation(value = "facultyTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/faculty", method = RequestMethod.GET)
	public List<SemesterTimeTable> getFacultyTimeTable()
	{
		String session = "July 2018 - Dec 2018";
		String faculty = "SN";
		List<SemesterTimeTable> facultytimetable = semesterTimeTableRepository.findByFaculty1OrFaculty2OrFaculty3AndSession(faculty, faculty, faculty, session);
		return facultytimetable;
	}
	
	@ApiOperation(value = "staffTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/staff", method = RequestMethod.GET)
	public List<SemesterTimeTable> getStaffTimeTable()
	{
		String session = "July 2018 - Dec 2018";
		String lt = "PS";
		List<SemesterTimeTable> stafftimetable = semesterTimeTableRepository.findByLabTechnicianAndSession(lt, session); 
		return stafftimetable;
	}
	
	@ApiOperation(value = "locationTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/{location}", method = RequestMethod.GET)
	public List<SemesterTimeTable> getLabTimeTable(@PathVariable("location") String location)
	{
		String session = "July 2018 - Dec 2018";
		List<SemesterTimeTable> venuetimetable = semesterTimeTableRepository.findByLocationAndSession(location, session);
		return venuetimetable;
	}

}
