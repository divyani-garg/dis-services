package sgsits.cse.dis.academics.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user")
public interface UserClient {
	
	@RequestMapping(value = "/getAdmissionYear", method = RequestMethod.GET)
	int getAdmissionYear(@RequestParam("id") long id);

	@RequestMapping(value = "/getCourse", method = RequestMethod.GET)
	public String getCourse(@RequestParam("id") long id);
	
	@RequestMapping(value = "/getStudentName", method = RequestMethod.GET)
	public String getStudentName(@RequestParam("id") long id);
	
	@RequestMapping(value = "/getAcronymName", method = RequestMethod.GET)
	public String getAcronymName(@RequestParam("id") long id);
}
