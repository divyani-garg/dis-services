package sgsits.cse.dis.administration.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "academics")
public interface AcademicsClient {
	
	@RequestMapping(value = "/getCourseName", method = RequestMethod.GET)
	public String getCoursename(@RequestParam("id") String id);
	

}
