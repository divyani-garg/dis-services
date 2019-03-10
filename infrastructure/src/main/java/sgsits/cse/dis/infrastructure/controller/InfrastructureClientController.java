package sgsits.cse.dis.infrastructure.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.infrastructure.model.Infrastructure;
import sgsits.cse.dis.infrastructure.repo.InfrastructureRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Infrastructure Client Resource")
public class InfrastructureClientController {
	
	@Autowired
	InfrastructureRepository infrastructureRepository;
	
	@ApiOperation(value = "findIncharge", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/findIncharge", method = RequestMethod.GET)
	public List<String> findInchargeOf(@RequestParam("id") long id) {
		List<Infrastructure> infrastructure = infrastructureRepository.findByInchargeOrAssociateInchargeOrStaff(id, id,
				id);
		List<String> incharge = new ArrayList<String>();
		for (Infrastructure infra : infrastructure)
			incharge.add(infra.getName());
		return incharge;
	}
}
