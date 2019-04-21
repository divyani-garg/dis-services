package sgsits.cse.dis.user.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.model.FirstYearBEStudent;
import sgsits.cse.dis.user.repo.FirstYearBEStudentRepository;
import sgsits.cse.dis.user.service.CsvUtils;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "First Year BE Student Resource")
public class BEFirstYearController {
	
	@Autowired
	FirstYearBEStudentRepository firstYearBEStudentRepository; 
	
	JwtResolver jwtResolver = new JwtResolver();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	
	 @PostMapping(value = "/uploadBEFirstYearData", consumes = "multipart/form-data")
	    public void uploadMultipart(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
		 //long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		 long id = 3;
		 	List<FirstYearBEStudent> beStudents = CsvUtils.read(FirstYearBEStudent.class, file.getInputStream());
		 	for(FirstYearBEStudent student : beStudents){
		 		student.setCreatedBy(id);
		 		student.setCreatedDate(simpleDateFormat.format(new Date()));
		 		//student.setDob(simpleDateFormat1.format(student.getDob()));
		 		firstYearBEStudentRepository.save(student);
		 	}
	        //firstYearBEStudentRepository.saveAll(CsvUtils.read(FirstYearBEStudent.class, file.getInputStream()));
	    }

}
