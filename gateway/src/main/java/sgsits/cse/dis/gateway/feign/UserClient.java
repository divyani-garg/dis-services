package sgsits.cse.dis.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user")
public interface UserClient {
	
	@RequestMapping(value = "/getAdmissionYear", method = RequestMethod.GET)
	int getAdmissionYear(@RequestParam("id") long id);
	
}
