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
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		int adYear = userClient.getAdmissionYear(id);
		String course = userClient.getCourse(id);
		String courseName = academicsClientController.getCoursename(course);

		// Get current year, semester and session
		String semester = currentYearAndSession.getCurrentSemester(adYear);
		String year = currentYearAndSession.getCurrentYear(adYear);
		String session = currentYearAndSession.getCurrentSession(adYear);

		// get Time Table
		List<SemesterTimeTable> semtimetable = new ArrayList<>();

		// for BE students
		if (courseName.equals("BE")) {
			semtimetable = semesterTimeTableRepository.findByCourseIdAndSessionAndYearAndSemester(course, session, year,
					semester);
		}

		// for ME students
		if (courseName.equals("ME")) {
			if (year.equals("I")) //for I year
				semtimetable = semesterTimeTableRepository.findByCourseIdAndSessionAndYearAndSemester(course, session,
						year, semester);
			else { //for II Year, TA Timetable
				String ta = userClient.getStudentName(id);
				semtimetable = semesterTimeTableRepository.findBySessionAndSemesterAndTa(session, semester, ta);
			}
		}

		List<SemesterTimeTableResponse> result = formatTimeTable.formatTT(semtimetable);

		// get dates in current week
		DateService ds = new DateService();
		List<String> dates = ds.getCurrentWeekDates();

		for (String date : dates) {
			System.out.println(date);
			List<ExtraClassTimeTable> extraClassTimeTable = extraClassTimeTableRepository
					.findBySessionAndYearAndSemesterAndDate(session, year, semester, simpleDateFormat.parse(date));
			if (!extraClassTimeTable.isEmpty()) {
				System.out.println(extraClassTimeTable.get(0).getSubjectCode());
				result = formatTimeTable.formatExtraTT(extraClassTimeTable, result);
			}
		}
		return result;
	}

	@ApiOperation(value = "facultyTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.FACULTY_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTable> getFacultyTimeTable() {
		String session = "2018 - 2019";
		String faculty = "SS";
		String semester = "B";
		List<SemesterTimeTable> facultytimetable = semesterTimeTableRepository
				.findBySessionAndSemesterAndFaculty1OrFaculty2OrFaculty3(session, semester, faculty, faculty, faculty);
		return facultytimetable;
	}

	@ApiOperation(value = "staffTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.STAFF_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTable> getStaffTimeTable() {
		String session = "July 2018 - Dec 2018";
		String lt = "NS";
		String semester = "B";
		List<SemesterTimeTable> stafftimetable = semesterTimeTableRepository
				.findByLabTechnicianAndSessionAndSemester(lt, session, semester);
		return stafftimetable;
	}

	@ApiOperation(value = "venueTimeTable", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.VENUE_TIMETABLE, method = RequestMethod.GET)
	public List<SemesterTimeTable> getLabTimeTable(@PathVariable("location") String location) {
		String session = "July 2018 - Dec 2018";
		List<SemesterTimeTable> venuetimetable = semesterTimeTableRepository.findByLocationAndSession(location,
				session);
		return venuetimetable;
	}

}
