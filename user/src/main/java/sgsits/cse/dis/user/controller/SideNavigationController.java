package sgsits.cse.dis.user.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.model.StaffProfile;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.model.User;
import sgsits.cse.dis.user.repo.StaffRepository;
import sgsits.cse.dis.user.repo.StudentRepository;
import sgsits.cse.dis.user.repo.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Side Navigation Resource")
public class SideNavigationController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserClientController userClientController;
	@Autowired
	StaffRepository staffRepository;
	@Autowired
	StudentRepository studentRepository;

	JwtResolver jwtResolver = new JwtResolver();

	@ApiOperation(value = "Get Side Navigation Details", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getSideNavigation", method = RequestMethod.GET)
	public List<String> getSideNavigation(HttpServletRequest request) throws ParseException {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			List<String> result = new ArrayList<>();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEE, d MMM yyyy, K:mm aa");
			Date lastLogin =  simpleDateFormat.parse(user.get().getLastLogin());
			String date = simpleDateFormat1.format(lastLogin);
				
			String name = null;
			String sid = null;
			String user_type = userClientController.getUserType(id);
			if (user_type.equals("student")) {
				Optional<StudentProfile> student = studentRepository.findByUserId(id);
				name = student.get().getFullName();
				sid = student.get().getEnrollmentId();
			} else {
				Optional<StaffProfile> staff = staffRepository.findByUserId(id);
				name = staff.get().getName();
				sid = staff.get().getEmployeeId(); 
			}
			result.add(name);
			result.add(date);
			result.add(sid);
			return result;
		}
		return null;
	}

}
