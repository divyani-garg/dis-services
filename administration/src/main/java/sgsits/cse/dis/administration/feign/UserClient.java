package sgsits.cse.dis.administration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user")
public interface UserClient {
	
	@RequestMapping(value = "/getUser/type", method = RequestMethod.GET)
	String getUserType(@RequestParam("id") long id);

}
