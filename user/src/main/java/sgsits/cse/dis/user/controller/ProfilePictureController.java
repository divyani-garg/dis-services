package sgsits.cse.dis.user.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.user.model.ProfilePicture;
import sgsits.cse.dis.user.repo.ProfilePictureRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dis/profilepicture")
@Api(value = "Profile Picture Resource")
public class ProfilePictureController {

	@Autowired
	ProfilePictureRepository profilePictureRepository;

	@ApiOperation(value = "AddProfilePicture", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> addProfilePicture(@RequestParam("file") MultipartFile file) {
		// get username from session
		String id = "0801CS17ME09";
		if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"))
			if (!file.isEmpty()) {
				try {
					String uploadsDir = "E:/ME Project/dis-services/profile picture/";
					// String realPathtoUploads =
					// request.getServletContext().getRealPath(uploadsDir);
					if (!new File(uploadsDir).exists()) {
						new File(uploadsDir).mkdir();
					}

					// String orgName = file.getOriginalFilename();
					String filename = id + ".jpeg";
					String filePath = uploadsDir + filename;
					File dest = new File(filePath);
					file.transferTo(dest);

					if (dest.exists())
						// set audit
						profilePictureRepository.save(new ProfilePicture(filename, filePath, file.getContentType()));

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		return null;
	}

	@ApiOperation(value = "DeleteProfilePicture", response = Object.class, httpMethod = "DELETE", produces = "application/json")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProfilePicture(@PathVariable long id) {
		
		return new ResponseEntity<>("Profile Picture has been deleted!", HttpStatus.OK);
	}

}
