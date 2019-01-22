package sgsits.cse.dis.user.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sgsits.cse.dis.user.model.Scheme;

@FeignClient(name = "academics")
public interface AcademicsClient {

	@RequestMapping(value = "/student/subjectList", method = RequestMethod.GET)
	List<Scheme> getSubjectList();
	
}
