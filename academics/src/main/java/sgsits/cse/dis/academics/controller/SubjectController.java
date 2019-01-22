package sgsits.cse.dis.academics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.academics.model.Scheme;
import sgsits.cse.dis.academics.repo.SchemeRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
@Api(value = "Subject Resource")
public class SubjectController {

	@Autowired
	SchemeRepository schemeRepository;

	@ApiOperation(value = "subjectList", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/subjectList", method = RequestMethod.GET)
	public List<Scheme> getSubjectList() {
		String session = "2017-2018";
		String year = "III";
		String semester = "A";
		List<Scheme> subjectList = schemeRepository.findBySessionAndYearAndSemester(session, year, semester);
		try {
			for (int i = 0; i < subjectList.size(); i++) {
				if (subjectList.get(i).getSyllabusPdf() == null) {
					subjectList.remove(i);
					i--;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return subjectList;
	}
}
