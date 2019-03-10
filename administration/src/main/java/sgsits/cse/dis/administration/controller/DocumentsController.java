package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.DocumentsFile;
import sgsits.cse.dis.administration.model.DocumentsFolder;
import sgsits.cse.dis.administration.model.DocumentsSection;
import sgsits.cse.dis.administration.model.DocumentsSubFolder;
import sgsits.cse.dis.administration.repo.DocumentsFileRepository;
import sgsits.cse.dis.administration.repo.DocumentsFolderRepository;
import sgsits.cse.dis.administration.repo.DocumentsSectionRepository;
import sgsits.cse.dis.administration.repo.DocumentsSubFolderRepository;
import sgsits.cse.dis.administration.request.FolderForm;
import sgsits.cse.dis.administration.request.SectionForm;
import sgsits.cse.dis.administration.response.ResponseMessage;
import sgsits.cse.dis.administration.service.FileNameValidation;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Documents Resource")
public class DocumentsController {

	@Autowired
	DocumentsSectionRepository documentsSectionRepository;
	@Autowired
	DocumentsFolderRepository documentsFolderRepository;
	@Autowired
	DocumentsSubFolderRepository documentsSubFolderRepository;
	@Autowired
	DocumentsFileRepository documentsFileRepository;

	JwtResolver jwtResolver = new JwtResolver();
	FileNameValidation fileNameValidation = new FileNameValidation();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@ApiOperation(value = "Get Section Name", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getSectionName", method = RequestMethod.GET)
	public List<DocumentsSection> getSectionName() {
		List<DocumentsSection> sections = documentsSectionRepository.findAll();
		return sections;
	}

	@ApiOperation(value = "Get Folders In Section", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getFoldersInSection", method = RequestMethod.GET)
	public List<DocumentsFolder> getFoldersInSection(@RequestParam("sectionId") long section) {
		List<DocumentsFolder> folders = documentsFolderRepository.findBySectionId(section);
		return folders;
	}

	@ApiOperation(value = "Get Sub Folders In Folder", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getSubFoldersInFolder", method = RequestMethod.GET)
	public List<DocumentsSubFolder> getSubFoldersInFolder(@RequestParam Map<String, String> id) {
		long section = Long.parseLong(id.get("sectionId"));
		long folder = Long.parseLong(id.get("folderId"));
		List<DocumentsSubFolder> subFolders = documentsSubFolderRepository.findBySectionIdAndFolderId(section, folder);
		return subFolders;
	}

	@ApiOperation(value = "Get Files In Folder", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getFilesInFolder", method = RequestMethod.GET)
	public List<DocumentsFile> getFilesInFolder(@RequestParam Map<String, Long> id) {
		long section = id.get("sectionId");
		long folder = id.get("folderId");
		List<DocumentsFile> files = documentsFileRepository.findBySectionIdAndFolderId(section, folder);
		return files;
	}

	@ApiOperation(value = "Get Files In Sub Folder", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getFilesInSubFolder", method = RequestMethod.GET)
	public List<DocumentsFile> getFilesInSubFolder(@RequestParam Map<String, Long> id) {
		long section = id.get("sectionId");
		long folder = id.get("folderId");
		long subfolder = id.get("subfolderId");
		List<DocumentsFile> files = documentsFileRepository.findBySectionIdAndFolderIdAndSubFolderId(section, folder,
				subfolder);
		return files;
	}

	@ApiOperation(value = "Add new Section", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addSection", method = RequestMethod.POST)
	public ResponseEntity<?> addSection(@RequestBody SectionForm sectionForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			if (!documentsSectionRepository.existsBySectionName(sectionForm.getSectionName())) {
				if(!sectionForm.getSectionName().isEmpty() && fileNameValidation.filenameIsValidOrNot(sectionForm.getSectionName())) {
					DocumentsSection section = new DocumentsSection();
					section.setCreatedBy(id);
					section.setCreatedDate(simpleDateFormat.format(new Date()));
					section.setSectionName(sectionForm.getSectionName());
					section = documentsSectionRepository.save(section);
					if (section != null)
						return new ResponseEntity<>(new ResponseMessage("Section '" + sectionForm.getSectionName() + "' Added Successfully!"), HttpStatus.OK);
					else
						return new ResponseEntity<>(new ResponseMessage("Unable to add new Section, Please try again later!"), HttpStatus.BAD_REQUEST);
				}
				else
					return new ResponseEntity<>(new ResponseMessage("The section name you specified is not valid! Specify a different name."), HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(new ResponseMessage("Destnation already contains a section named '" + sectionForm.getSectionName() + "'!"), HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(new ResponseMessage("You will need to provide administrator permission to add this section!"), HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Add new Folder In Section", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addFolderInSection", method = RequestMethod.POST)
	public ResponseEntity<?> addFolderInSection(@RequestBody FolderForm folderForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			DocumentsFolder folder = new DocumentsFolder();
			folder.setCreatedBy(id);
			folder.setCreatedDate(simpleDateFormat.format(new Date()));
			folder.setFolderName(folderForm.getFolderName());
			folder.setSectionId(folderForm.getSectionId());
			folder = documentsFolderRepository.save(folder);
			if (folder != null)
				return new ResponseEntity<>(new ResponseMessage("Folder Added Successfully!"), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseMessage("Unable to add new Folder, Please try again later!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(new ResponseMessage("Unable to add new Folder!"), HttpStatus.BAD_REQUEST);
	}

}
