package sgsits.cse.dis.administration.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import sgsits.cse.dis.administration.model.DocumentsFile;
import sgsits.cse.dis.administration.repo.DocumentsFileRepository;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Administration Client Resource")
public class AdministrationClientController {

	@Autowired
	DocumentsFileRepository documentsFileRepository;

	@RequestMapping(value = "/fileDownloadLink", method = RequestMethod.GET)
	public String getFileDownloadLink(@RequestParam("id") String id) {
		Optional<DocumentsFile> file = documentsFileRepository.findById(id);
		String link = file.get().getDownloadURL();
		if (link != null)
			return link;
		return null;
	}

}