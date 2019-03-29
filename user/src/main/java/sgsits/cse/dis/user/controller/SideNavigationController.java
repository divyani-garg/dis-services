package sgsits.cse.dis.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.repo.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Side Navigation Resource")
public class SideNavigationController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserClientController userClientController;
	
	JwtResolver jwtResolver = new JwtResolver();
	
	@ApiOperation(value = "Get Side Navigation Details", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getSideNavigation", method = RequestMethod.GET)
	public void getSideNavigation(HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		String user_type = userClientController.getUserType(id);
		
	}

}
