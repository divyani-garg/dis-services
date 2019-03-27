package sgsits.cse.dis.academics.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "administration")
public interface AdministrationClient {
	
	@RequestMapping(value = "/fileDownloadLink", method = RequestMethod.GET)
	public String getFileDownloadLink(@RequestParam("id") String id);
}
