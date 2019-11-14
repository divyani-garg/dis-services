package sgsits.cse.dis.academics.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.academics.constants.RestAPI;
import sgsits.cse.dis.academics.feign.AdministrationClient;
import sgsits.cse.dis.academics.feign.UserClient;
import sgsits.cse.dis.academics.jwt.JwtResolver;
import sgsits.cse.dis.academics.model.Courses;
import sgsits.cse.dis.academics.model.Scheme;
import sgsits.cse.dis.academics.model.response.SubjectCodeAndYearResponse;
import sgsits.cse.dis.academics.model.response.SubjectListResponse;
import sgsits.cse.dis.academics.repo.CoursesRepository;
import sgsits.cse.dis.academics.repo.SchemeRepository;
import sgsits.cse.dis.academics.request.CourseAndYearForm;
import sgsits.cse.dis.academics.services.CurrentYearAndSession;
//import sgsits.cse.dis.administration.request.BookForm;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Subject Resource")
public class SubjectController {

	@Autowired
	CoursesRepository coursesRepository;
	@Autowired
	SchemeRepository schemeRepository;
	@Autowired
	UserClient userClient;
	@Autowired
	AdministrationClient administrationClient;

	JwtResolver jwtResolver = new JwtResolver();
	CurrentYearAndSession currentYearAndSession = new CurrentYearAndSession();

	@ApiOperation(value = "subjectList", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.SUBJECT_LIST, method = RequestMethod.GET)
	public List<SubjectListResponse> getSubjectList(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		List<SubjectListResponse> subjectListResponses = getSubjectListService(id);
		return subjectListResponses;
	}

	@RequestMapping(value = RestAPI.SUBJECT_LIST_SERVICE, method = RequestMethod.GET)
	public List<SubjectListResponse> getSubjectListService(long id) {
		int adYear = userClient.getAdmissionYear(id);
		String course = userClient.getCourse(id);

		// Get current year, semester and session
		String semester = currentYearAndSession.getCurrentSemester();
		String year = currentYearAndSession.getCurrentYear(adYear);
		String session = currentYearAndSession.getBESession(adYear);
		List<Scheme> subjectList = schemeRepository.findByCourseIdAndSessionAndYearAndSemester(course, session, year,
				semester);

		List<SubjectListResponse> subjectListResponses = new ArrayList<>();

		for (Scheme subject : subjectList) {
			if (subject.getSubjectCode().startsWith("CO")) {
				SubjectListResponse slr = new SubjectListResponse();
				slr.setId(subject.getId());
				slr.setSubjectCode(subject.getSubjectCode());
				slr.setSubjectName(subject.getSubjectName());
				slr.setSubjectAcronym(subject.getSubjectAcronym());
				// String link =
				// administrationClient.getFileDownloadLink(subject.getSyllabusPdf());
				// if(link != null)
				// slr.setDownloadLink(link);
				subjectListResponses.add(slr);
			}
		}
		return subjectListResponses;
	}

	@ApiOperation(value = "subjectCodeAndYear", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.SUBJECT_CODE_YEAR_SERVICE, method = RequestMethod.GET)
	public List<SubjectCodeAndYearResponse> getSubjectCodeAndYear(@RequestBody CourseAndYearForm courseAndYearForm,
			HttpServletRequest request) {
		Optional<Courses> courseId = coursesRepository.findByName(courseAndYearForm.getCourse());
		// System.out.println(courseId.get().getCourseId());
		if (courseAndYearForm.getYear1() == null && courseAndYearForm.getYear2() == null
				&& courseAndYearForm.getYear3() == null) // All three null for ME
		{
			String semester = currentYearAndSession.getCurrentSemester();
			String session1 = currentYearAndSession.getSessionFromYear("1", 2);
			List<Scheme> subjectList = schemeRepository
					.findByCourseIdAndSessionAndYearAndSemester(courseId.get().getCourseId(), session1, "I", semester);
			List<SubjectCodeAndYearResponse> subjects = new ArrayList<>();
			for (Scheme subject : subjectList) {
				SubjectCodeAndYearResponse subjectDetails = new SubjectCodeAndYearResponse();
				subjectDetails.setSubjectName(subject.getSubjectName());
				subjectDetails.setSubjectCode(subject.getSubjectCode());
				subjectDetails.setYear(subject.getYear());
				subjects.add(subjectDetails);
			}

			return subjects;
		} else {
			String semester = currentYearAndSession.getCurrentSemester();
			String session1 = currentYearAndSession.getSessionFromYear(courseAndYearForm.getYear1(), 4);
			String session2 = currentYearAndSession.getSessionFromYear(courseAndYearForm.getYear2(), 4);
			String session3 = currentYearAndSession.getSessionFromYear(courseAndYearForm.getYear3(), 4);
			List<SubjectCodeAndYearResponse> subjects = new ArrayList<>();
			if (courseAndYearForm.getYear1() != null) {
				List<Scheme> subjectList = schemeRepository.findByCourseIdAndSessionAndYearAndSemester(
						courseId.get().getCourseId(), session1, "II", semester);
				for (Scheme subject : subjectList) {
					SubjectCodeAndYearResponse subjectDetails = new SubjectCodeAndYearResponse();
					subjectDetails.setSubjectName(subject.getSubjectName());
					subjectDetails.setSubjectCode(subject.getSubjectCode());
					subjectDetails.setYear(subject.getYear());
					subjects.add(subjectDetails);
				}
			}
			if (courseAndYearForm.getYear2() != null) {
				List<Scheme> subjectList = schemeRepository.findByCourseIdAndSessionAndYearAndSemester(
						courseId.get().getCourseId(), session2, "III", semester);
				for (Scheme subject : subjectList) {
					SubjectCodeAndYearResponse subjectDetails = new SubjectCodeAndYearResponse();
					subjectDetails.setSubjectName(subject.getSubjectName());
					subjectDetails.setSubjectCode(subject.getSubjectCode());
					subjectDetails.setYear(subject.getYear());
					subjects.add(subjectDetails);
				}
			}
			if (courseAndYearForm.getYear3() != null) {
				List<Scheme> subjectList = schemeRepository.findByCourseIdAndSessionAndYearAndSemester(
						courseId.get().getCourseId(), session3, "IV", semester);
				for (Scheme subject : subjectList) {
					SubjectCodeAndYearResponse subjectDetails = new SubjectCodeAndYearResponse();
					subjectDetails.setSubjectName(subject.getSubjectName());
					subjectDetails.setSubjectCode(subject.getSubjectCode());
					subjectDetails.setYear(subject.getYear());
					subjects.add(subjectDetails);
				}
			}

			return subjects;
		}
	}
}
