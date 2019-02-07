package sgsits.cse.dis.academics.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.academics.model.ExtraClassTimeTable;
import sgsits.cse.dis.academics.model.SemesterTimeTable;
import sgsits.cse.dis.academics.model.presentation.SemesterTimeTablePresentation;
import sgsits.cse.dis.academics.repo.ExtraClassTimeTableRepository;
import sgsits.cse.dis.academics.repo.SemesterTimeTableRepository;
import sgsits.cse.dis.academics.services.DateService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/timetable")
@Api(value = "Time Table Resource")
public class TimeTableController {

	@Autowired
	SemesterTimeTableRepository semesterTimeTableRepository;

	@Autowired
	ExtraClassTimeTableRepository extraClassTimeTableRepository;

	@ApiOperation(value = "studentTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public List<SemesterTimeTablePresentation> getStudentTimeTable() throws ParseException {
		String session = "July 2018 - Dec 2018";
		String year = "III";
		String semester = "A";
		List<SemesterTimeTable> semtimetable = semesterTimeTableRepository.findBySessionAndYearAndSemester(session,
				year, semester);

		// String string = "2019-01-16";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Date date = simpleDateFormat.parse(string);

		DateService ds = new DateService();
		List<String> dates = ds.getCurrentWeekDates();
		
		List<SemesterTimeTablePresentation> result = new ArrayList<SemesterTimeTablePresentation>();

		for (SemesterTimeTable semTT : semtimetable) {
			SemesterTimeTablePresentation sem = new SemesterTimeTablePresentation();
			sem.setSubjectCode(semTT.getSubjectCode());
			sem.setTo(semTT.getTo());
			sem.setFrom(semTT.getFrom());
			sem.setDay(semTT.getDay());
			sem.setType(semTT.getType());
			sem.setFaculty1(semTT.getFaculty1());
			sem.setFaculty2(semTT.getFaculty2());
			sem.setFaculty3(semTT.getFaculty3());
			sem.setLabTechnician(semTT.getLabTechnician());
			sem.setTa(semTT.getTa());
			sem.setBatch(semTT.getBatch());
			sem.setLocation(semTT.getLocation());
			sem.setColor("original");
			result.add(sem);
		}

		for (String date : dates) {
			List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
					.findBySessionAndYearAndSemesterAndDate(session, year, semester, simpleDateFormat.parse(date));

			if(extraClassTimeTable!=null)
			for (SemesterTimeTablePresentation semTT : result) {
				for (ExtraClassTimeTable extraClass : extraClassTimeTable) {
					if (semTT.getTo().equals(extraClass.getTo()) && semTT.getFrom().equals(extraClass.getFrom())
							&& semTT.getDay().equals(extraClass.getDay())) {
						semTT.setSubjectCode(extraClass.getSubjectCode());
						semTT.setTo(extraClass.getTo());
						semTT.setFrom(extraClass.getFrom());
						semTT.setDay(extraClass.getDay());
						semTT.setType(extraClass.getType());
						semTT.setFaculty1(extraClass.getFaculty1());
						semTT.setFaculty2(extraClass.getFaculty2());
						semTT.setFaculty3(extraClass.getFaculty3());
						semTT.setLabTechnician(extraClass.getLabTechnician());
						semTT.setTa(extraClass.getTa());
						semTT.setBatch(extraClass.getBatch());
						semTT.setLocation(extraClass.getLocation());
						semTT.setColor("change");
					}
				}
			}
		}
		return result;
	}

	@ApiOperation(value = "facultyTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/faculty", method = RequestMethod.GET)
	public List<SemesterTimeTable> getFacultyTimeTable() {
		String session = "July 2018 - Dec 2018";
		String faculty = "SN";
		List<SemesterTimeTable> facultytimetable = semesterTimeTableRepository
				.findByFaculty1OrFaculty2OrFaculty3AndSession(faculty, faculty, faculty, session);
		return facultytimetable;
	}

	@ApiOperation(value = "staffTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/staff", method = RequestMethod.GET)
	public List<SemesterTimeTable> getStaffTimeTable() {
		String session = "July 2018 - Dec 2018";
		String lt = "PS";
		List<SemesterTimeTable> stafftimetable = semesterTimeTableRepository.findByLabTechnicianAndSession(lt, session);
		return stafftimetable;
	}

	@ApiOperation(value = "locationTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/{location}", method = RequestMethod.GET)
	public List<SemesterTimeTable> getLabTimeTable(@PathVariable("location") String location) {
		String session = "July 2018 - Dec 2018";
		List<SemesterTimeTable> venuetimetable = semesterTimeTableRepository.findByLocationAndSession(location,
				session);
		return venuetimetable;
	}

}
