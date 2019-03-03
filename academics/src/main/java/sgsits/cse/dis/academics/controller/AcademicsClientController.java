package sgsits.cse.dis.academics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import sgsits.cse.dis.academics.model.Courses;
import sgsits.cse.dis.academics.repo.CoursesRepository;

@Api(value = "Academics Client Request Resource")
@RestController
public class AcademicsClientController {
	
	@Autowired 
	CoursesRepository coursesRepository;
	
	@RequestMapping(value = "/getCourseName", method = RequestMethod.GET)
	public String getCoursename(@RequestParam("id") String id)
	{
		List<Courses> course = coursesRepository.findByCourseId(id); 
		return course.get(0).getName();
	}

}
