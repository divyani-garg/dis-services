package sgsits.cse.dis.academics.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.academics.constants.RestAPI;
import sgsits.cse.dis.academics.feign.UserClient;
import sgsits.cse.dis.academics.jwt.JwtResolver;
import sgsits.cse.dis.academics.model.ExtraClassTimeTable;
import sgsits.cse.dis.academics.model.SemesterTimeTable;
import sgsits.cse.dis.academics.model.response.SemesterTimeTableResponse;
import sgsits.cse.dis.academics.repo.ExtraClassTimeTableRepository;
import sgsits.cse.dis.academics.repo.SemesterTimeTableRepository;
import sgsits.cse.dis.academics.services.CurrentYearAndSession;
import sgsits.cse.dis.academics.services.DateService;
import sgsits.cse.dis.academics.services.FormatTimeTable;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Time Table Resource")
public class TimeTableController {

	@Autowired
	SemesterTimeTableRepository semesterTimeTableRepository;
	@Autowired
	ExtraClassTimeTableRepository extraClassTimeTableRepository;
	@Autowired
	UserClient userClient;
	@Autowired
	AcademicsClientController academicsClientController;

	JwtResolver jwtResolver = new JwtResolver();
	CurrentYearAndSession currentYearAndSession = new CurrentYearAndSession();
	FormatTimeTable formatTimeTable = new FormatTimeTable();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@ApiOperation(value = "studentTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.STUDENT_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTableResponse> getStudentTimeTable(HttpServletRequest request) throws ParseException {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization")); //check if id is null
		int adYear = userClient.getAdmissionYear(id);
		String course = userClient.getCourse(id);
		String courseName = academicsClientController.getCoursename(course);
		// Get current year, semester and session
		String semester = currentYearAndSession.getCurrentSemester();
		String year = currentYearAndSession.getCurrentYear(adYear);
		String session = currentYearAndSession.getCurrentSession();
		// get Time Table
		List<SemesterTimeTable> semtimetable = new ArrayList<>();
		// for BE students
		if (courseName.equals("BE")) {
			semtimetable = semesterTimeTableRepository.findByCourseIdAndSessionAndYearAndSemester(course, session, year,
					semester);
		}
		// for ME students
		String ta = userClient.getStudentName(id); //giving full name
		if(ta.contains(" ")){
	        ta = ta.substring(0, ta.indexOf(" ")); //for getting first name only
	     }
		if (courseName.equals("ME")) {
			if (year.equals("I")) // for I year
				semtimetable = semesterTimeTableRepository.findByCourseIdAndSessionAndYearAndSemester(course, session,
						year, semester);
			else { // for II Year, TA Timetable
				semtimetable = semesterTimeTableRepository.findBySessionAndSemesterAndTa(session, semester, ta);
			}
		}

		List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(semtimetable);
		// get dates in current week
		DateService ds = new DateService();
		List<String> dates = ds.getCurrentWeekDates();
		// for each date check if there is any extra class
		for (String date : dates) {
			if (courseName.equals("ME") && year.equals("II")) {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findBySessionAndSemesterAndTaAndDate(session, semester, ta, date);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			} else {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findByCourseIdAndSessionAndYearAndSemesterAndDate(course, session, year, semester, date);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
		}
		return result;
	}

	@ApiOperation(value = "facultyTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.FACULTY_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTableResponse> getFacultyTimeTable(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization")); 
		// Get current semester and session
		String semester = currentYearAndSession.getCurrentSemester();
		String session = currentYearAndSession.getCurrentSession();
		String faculty = userClient.getAcronymName(id);
		if (faculty != null) {
			List<SemesterTimeTable> facultytimetable = semesterTimeTableRepository
					.findBySessionAndSemesterAndFaculty1OrFaculty2OrFaculty3(session, semester, faculty, faculty,
							faculty);
			List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(facultytimetable);
			// get dates in current week
			DateService ds = new DateService();
			List<String> dates = ds.getCurrentWeekDates();
			// for each date check if there is any extra class
			for (String date : dates) {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findBySessionAndSemesterAndDateAndFaculty1OrFaculty2OrFaculty3(session, semester, date,
								faculty, faculty, faculty);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "staffTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.STAFF_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTableResponse> getStaffTimeTable(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		// Get current semester and session
		String semester = currentYearAndSession.getCurrentSemester();
		String session = currentYearAndSession.getCurrentSession();
		String lt = userClient.getAcronymName(id);
		if (lt != null) {
			List<SemesterTimeTable> stafftimetable = semesterTimeTableRepository
					.findByLabTechnicianAndSessionAndSemester(lt, session, semester);
			List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(stafftimetable);
			// get dates in current week
			DateService ds = new DateService();
			List<String> dates = ds.getCurrentWeekDates();
			// for each date check if there is any extra class
			for (String date : dates) {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findBySessionAndSemesterAndDateAndLabTechnician(session, semester, date, lt);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "venueTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.VENUE_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTableResponse> getLabTimeTable(@PathVariable("location") String location) {
		if (location != null) {
			// Get current semester and session
			String session = currentYearAndSession.getCurrentSession();
			String semester = currentYearAndSession.getCurrentSemester();
			List<SemesterTimeTable> venuetimetable = semesterTimeTableRepository.findByLocationAndSessionAndSemester(location,
					session, semester);
			List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(venuetimetable);
			// get dates in current week
			DateService ds = new DateService();
			List<String> dates = ds.getCurrentWeekDates();
			// for each date check if there is any extra class
			for (String date : dates) {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findByLocationAndSessionAndSemesterAndDate(location, session, semester, date);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "BEyearTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.BE_YEAR_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTableResponse> getBEYearWiseTimeTable(@PathVariable("year") String year) {
		if (year != null) {
			// Get current semester and session
			String semester = currentYearAndSession.getCurrentSemester();
			String session = currentYearAndSession.getCurrentSession();
			List<SemesterTimeTable> semtimetable = semesterTimeTableRepository
					.findByCourseIdAndSessionAndYearAndSemester("C1", session, year, semester);
			List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(semtimetable);
			// get dates in current week
			DateService ds = new DateService();
			List<String> dates = ds.getCurrentWeekDates();
			// for each date check if there is any extra class
			for (String date : dates) {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findByCourseIdAndSessionAndYearAndSemesterAndDate("C1", session, year, semester, date);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "MEyearTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.ME_YEAR_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTableResponse> getMEYearWiseTimeTable(@PathVariable("year") String year) {
		if (year != null) {
			// Get current semester and session
			String semester = currentYearAndSession.getCurrentSemester();
			String session = currentYearAndSession.getCurrentSession();
			List<SemesterTimeTable> semtimetable = semesterTimeTableRepository
					.findByCourseIdAndSessionAndYearAndSemester("C2", session, year, semester);
			List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(semtimetable);
			// get dates in current week
			DateService ds = new DateService();
			List<String> dates = ds.getCurrentWeekDates();
			// for each date check if there is any extra class
			for (String date : dates) {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findByCourseIdAndSessionAndYearAndSemesterAndDate("C2", session, year, semester, date);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "TATimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.TA_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTableResponse> getTATimeTable(@PathVariable("taid") Long taid) {
		if (taid != null) {
			// Get current semester and session
			String semester = currentYearAndSession.getCurrentSemester();
			String session = currentYearAndSession.getCurrentSession();
			String ta = userClient.getStudentName(taid);
			if(ta.contains(" ")){
		        ta = ta.substring(0, ta.indexOf(" ")); //for getting first name only
		     }
			List<SemesterTimeTable> semtimetable = semesterTimeTableRepository.findBySessionAndSemesterAndTa(session,
					semester, ta);
			List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(semtimetable);
			// get dates in current week
			DateService ds = new DateService();
			List<String> dates = ds.getCurrentWeekDates();
			// for each date check if there is any extra class
			for (String date : dates) {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findBySessionAndSemesterAndTaAndDate(session, semester, ta, date);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "facultyWiseTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.FACULTY_WISE_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTableResponse> getFacultyWiseTimeTable(@PathVariable("facultyid") Long id) // user id
	{
		// Get current semester and session
		String semester = currentYearAndSession.getCurrentSemester();
		String session = currentYearAndSession.getCurrentSession();
		String faculty = userClient.getAcronymName(id);
		if (faculty != null) {
			List<SemesterTimeTable> facultytimetable = semesterTimeTableRepository
					.findBySessionAndSemesterAndFaculty1OrFaculty2OrFaculty3(session, semester, faculty, faculty,
							faculty);
			List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(facultytimetable);
			// get dates in current week
			DateService ds = new DateService();
			List<String> dates = ds.getCurrentWeekDates();
			// for each date check if there is any extra class
			for (String date : dates) {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findBySessionAndSemesterAndDateAndFaculty1OrFaculty2OrFaculty3(session, semester, date,
								faculty, faculty, faculty);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "staffWiseTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.STAFF_WISE_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTableResponse> getStaffWiseTimeTable(@PathVariable("staffid") Long id) // user id
	{
		// Get current semester and session
		String semester = currentYearAndSession.getCurrentSemester();
		String session = currentYearAndSession.getCurrentSession();
		String lt = userClient.getAcronymName(id);
		if (lt != null) {
			List<SemesterTimeTable> stafftimetable = semesterTimeTableRepository
					.findByLabTechnicianAndSessionAndSemester(lt, session, semester);
			List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(stafftimetable);
			// get dates in current week
			DateService ds = new DateService();
			List<String> dates = ds.getCurrentWeekDates();
			// for each date check if there is any extra class
			for (String date : dates) {
				List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
						.findBySessionAndSemesterAndDateAndLabTechnician(session, semester, date, lt);
				if (!extraClassTimeTable.isEmpty())
					result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
			return result;
		}
		return null;
	}
}
