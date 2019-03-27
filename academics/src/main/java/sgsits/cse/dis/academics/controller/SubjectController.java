package sgsits.cse.dis.academics.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.academics.constants.RestAPI;
import sgsits.cse.dis.academics.feign.AdministrationClient;
import sgsits.cse.dis.academics.feign.UserClient;
import sgsits.cse.dis.academics.jwt.JwtResolver;
import sgsits.cse.dis.academics.model.Scheme;
import sgsits.cse.dis.academics.model.response.SubjectListResponse;
import sgsits.cse.dis.academics.repo.SchemeRepository;
import sgsits.cse.dis.academics.services.CurrentYearAndSession;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Subject Resource")
public class SubjectController {

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
				//String link = administrationClient.getFileDownloadLink(subject.getSyllabusPdf());
				//if(link != null)
				//	slr.setDownloadLink(link);
				subjectListResponses.add(slr);
			}
		}
		return subjectListResponses;
	}
}
