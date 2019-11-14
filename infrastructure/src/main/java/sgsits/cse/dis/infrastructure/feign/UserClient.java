package sgsits.cse.dis.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user")
public interface UserClient {
	
	@RequestMapping(value = "/getUserName", method = RequestMethod.GET)
	String getUserName(@RequestParam("id") long id);
	
	@RequestMapping(value = "/getUserType", method = RequestMethod.GET)
	String getUserType(@RequestParam("id") long id);

}

