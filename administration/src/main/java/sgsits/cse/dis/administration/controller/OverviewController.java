package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.administration.constants.RestAPI;
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.OverviewDetails;
import sgsits.cse.dis.administration.repo.OverviewDetailsRepository;
import sgsits.cse.dis.administration.service.CurrentYearAndSession;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Overview Details Resource")
public class OverviewController {

	@Autowired
	OverviewDetailsRepository overviewDetailsRepository;

	JwtResolver jwtResolver = new JwtResolver();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	CurrentYearAndSession currentYearAndSession = new CurrentYearAndSession();

	@ApiOperation(value = "Get Overview Details", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_OVERVIEW_DETAILS, method = RequestMethod.GET)
	public List<OverviewDetails> getOverviewDetails() {
		long id = overviewDetailsRepository.findMax();
		Optional<OverviewDetails> details = overviewDetailsRepository.findById(id);
		String session = details.get().getSession();
		List<OverviewDetails> overviewDetails = overviewDetailsRepository.findBySession(session);
		if (!overviewDetails.isEmpty()) {
			return overviewDetails;
		}
		return null;
	}

	@ApiOperation(value = "Edit Overview Details", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = RestAPI.EDIT_OVERVIEW_DETAILS, method = RequestMethod.PUT)
	public void EditOverviewDetails(@RequestBody List<OverviewDetails> overview, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String session = currentYearAndSession.getCurrentSession();
		List<OverviewDetails> list = new ArrayList<>();
		int size = overview.size();
		int count = 0;
		for (OverviewDetails od : overview) {
			if (!overviewDetailsRepository.existsBySessionAndHeading1AndHeading2AndHeading3AndContent(session,
					od.getHeading1(), od.getHeading2(), od.getHeading3(), od.getContent())) {
				if (!overviewDetailsRepository.existsBySessionAndHeading1AndHeading2AndContentNot(session, od.getHeading1(),
						od.getHeading2(), od.getContent())) {
					OverviewDetails overviewDetails = new OverviewDetails();
					overviewDetails.setCreatedBy(id);
					overviewDetails.setCreatedDate(simpleDateFormat.format(new Date()));
					overviewDetails.setHeading1(od.getHeading1());
					overviewDetails.setHeading2(od.getHeading2());
					overviewDetails.setHeading3(od.getHeading3());
					overviewDetails.setSession(session);
					overviewDetails.setContent(od.getContent());
					overviewDetails.setStatus(od.getStatus());
					list.add(overviewDetails);
					count++;
				}
				else {
					//OverviewDetails overviewDetails = overviewDetailsRepository.findBy
				}
			}
		}
	}

}
