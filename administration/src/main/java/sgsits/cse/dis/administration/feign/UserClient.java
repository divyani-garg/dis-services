package sgsits.cse.dis.administration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user")
public interface UserClient {
	
	@RequestMapping(value = "/getUserType", method = RequestMethod.GET)
	String getUserType(@RequestParam("id") long id);
	
	@RequestMapping(value = "/getUserName", method = RequestMethod.GET)
	String getUserName(@RequestParam("id") long id);
	
	@RequestMapping(value = "/getUserCurrentDesignation", method = RequestMethod.GET)
	String getUserCurrentDesignation(@RequestParam("id") long id);

	@RequestMapping(value = "/getUserId", method = RequestMethod.GET)
	long getUserId(@RequestParam("keyword") String name);

	@RequestMapping(value = "/getAdmissionYear", method = RequestMethod.GET)
	int getAdmissionYear(@RequestParam("id") long id);

	@RequestMapping(value = "/getCourse", method = RequestMethod.GET)
	String getCourse(@RequestParam("id") long id);

	
}
