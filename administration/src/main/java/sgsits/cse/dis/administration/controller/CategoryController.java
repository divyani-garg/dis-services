package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.administration.constants.RestAPI;
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.ResourceRequestCategory;
import sgsits.cse.dis.administration.repo.ResourceRequestCategoryRepository;
import sgsits.cse.dis.administration.response.ResourceRequestCategoryResopnse;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Request Category")
public class CategoryController {
	
	@Autowired
	ResourceRequestCategoryRepository resourceRequestCategoryRepository;
	
	JwtResolver jwtResolver = new JwtResolver();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@ApiOperation(value="Show Resource Request Categories", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_RESOURCE_CATEGORY, method = RequestMethod.GET)
	public List<String> getResourceCategoryList(HttpServletRequest request){	
	 	if (jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("head")) {
	 		List<String> resourceRequestCategoryResopnses = new ArrayList<>();
	 		List<ResourceRequestCategory> resourceRequestCategories;
	 		resourceRequestCategories =  resourceRequestCategoryRepository.findAll();
	 		for(ResourceRequestCategory requestCategory: resourceRequestCategories) {
	 			ResourceRequestCategoryResopnse temp =new ResourceRequestCategoryResopnse();
	 			temp.setCategory(requestCategory.getCategory());
	 			if(!resourceRequestCategoryResopnses.contains(temp.getCategory()))
	 				resourceRequestCategoryResopnses.add(temp.getCategory());
 		}
	 			
	 		
	 		return resourceRequestCategoryResopnses;
	 	}
	 	else
	 		return null;
	}

}
