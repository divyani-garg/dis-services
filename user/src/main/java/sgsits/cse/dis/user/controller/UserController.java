package sgsits.cse.dis.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.model.User;
import sgsits.cse.dis.user.repo.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/getUser")
@Api(value = "User Resource")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@ApiOperation(value = "get User Details", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public String getUserType(@RequestParam("id") long id)
	{	
		Optional<User> user = userRepository.findById(id);
		String type = user.get().getUserType();
		return type;
	}
	
	
}