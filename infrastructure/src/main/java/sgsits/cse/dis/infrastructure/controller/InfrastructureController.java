package sgsits.cse.dis.infrastructure.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.infrastructure.model.Infrastructure;
import sgsits.cse.dis.infrastructure.repo.InfrastructureRepository;
import sgsits.cse.dis.infrastructure.service.InfrastructureService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/infra") 
@Api(value = "Infrastructure Resource")
public class InfrastructureController {
	
	@Autowired
	InfrastructureService infrastructureService;
	
	@Autowired
	InfrastructureRepository infrastructureRepository;
	
	@ApiOperation(value = "listInfrastructure", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/listInfrastructure", method = RequestMethod.GET)
	public List<Infrastructure> getAllInfrastructure()
	{	
		return infrastructureService.findAll();
	}
	
	@ApiOperation(value = "addInfrastructure", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addInfrastructure", method = RequestMethod.POST)
	public String addInfrastructure(@RequestBody Infrastructure infrastructure)
	{
		infrastructure.setCreatedDate(java.time.Clock.systemUTC().instant());
		infrastructureService.save(infrastructure);
		return "redirect:/addInfrastructure?success";
	}

	@ApiOperation(value = "deleteInfrastructure", response = Object.class, httpMethod = "DELETE", produces = "application/json")
	@RequestMapping(value = "/deleteInfrastructure/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteInfrastructure(@PathVariable long id)
	{
		infrastructureService.delete(id);
		return new ResponseEntity<>("Infrastructure has been deleted!", HttpStatus.OK);
	}
	
	@ApiOperation(value = "updateInfrastructure", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/updateInfrastructure/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateInfrastructure(@RequestBody Infrastructure infrastructure, @PathVariable long id)
	{
		Optional<Infrastructure> infra = infrastructureRepository.findById(id);
		if (!infra.isPresent())
			return ResponseEntity.notFound().build();
		infrastructure.setId(id);
		infrastructure.setModifiedDate(java.time.Clock.systemUTC().instant());
		infrastructureRepository.save(infrastructure);
		return ResponseEntity.noContent().build();
		//return new ResponseEntity<>("Infrastructure has been Updated!", HttpStatus.OK);
	}
	
	@ApiOperation(value = "findInfrastructure", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/findInfrastructure/{name}", method = RequestMethod.GET)
	public List<Infrastructure> findByName(@PathVariable String name)
	{
		return infrastructureService.findByName(name);
	}

	@ApiOperation(value = "findIncharge", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/findIncharge", method = RequestMethod.GET)
	public List<String> findInchargeOf(@RequestParam("id") long id)
	{
		List<Infrastructure> infrastructure = infrastructureRepository.findByInchargeOrAssociateInchargeOrStaff(id,id,id);
		List<String> incharge = new ArrayList<String>();
		for(Infrastructure infra : infrastructure)
			incharge.add(infra.getName());
		return incharge;
	}
	
}