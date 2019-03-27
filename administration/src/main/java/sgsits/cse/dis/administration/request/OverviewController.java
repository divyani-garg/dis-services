package sgsits.cse.dis.administration.request;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.administration.model.OverviewDetails;
import sgsits.cse.dis.administration.repo.OverviewDetailsRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Overview Details Resource")
public class OverviewController {
	
	@Autowired
	OverviewDetailsRepository overviewDetailsRepository;	
	
	@ApiOperation(value = "Get Overview Details", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getOverviewDetails", method = RequestMethod.GET)
	public List<OverviewDetails> getOverviewDetails() {
		long id = overviewDetailsRepository.findMax();
		Optional<OverviewDetails> details = overviewDetailsRepository.findById(id);
		String session = details.get().getSession();
		List<OverviewDetails> overviewDetails = overviewDetailsRepository.findBySession(session);
		if(!overviewDetails.isEmpty()){
			return overviewDetails;
		}
		return null;
	}
	
}
