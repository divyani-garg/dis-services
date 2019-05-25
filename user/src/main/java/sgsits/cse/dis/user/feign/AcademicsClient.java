package sgsits.cse.dis.user.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sgsits.cse.dis.user.message.response.SubjectListResponse;

@FeignClient(name = "academics")
public interface AcademicsClient {

	@RequestMapping(value = "/subjectListService", method = RequestMethod.GET)
	List<SubjectListResponse> getSubjectList(@RequestParam("id") long id);
	
	@RequestMapping(value = "/getCourseName", method = RequestMethod.GET)
	String getCoursename(@RequestParam("id") String id);
	
}
