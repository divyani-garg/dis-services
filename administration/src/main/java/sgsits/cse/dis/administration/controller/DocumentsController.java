package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.administration.constants.RestAPI;
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.DocumentsFile;
import sgsits.cse.dis.administration.model.Documents;
import sgsits.cse.dis.administration.repo.DocumentsFileRepository;
import sgsits.cse.dis.administration.repo.DocumentsRepository;
import sgsits.cse.dis.administration.request.DocumentsForm;
import sgsits.cse.dis.administration.request.FileForm;
import sgsits.cse.dis.administration.response.DocumentsFileResponse;
import sgsits.cse.dis.administration.response.DocumentsResponse;
import sgsits.cse.dis.administration.response.ResponseMessage;
import sgsits.cse.dis.administration.service.DBFileStorageService;
import sgsits.cse.dis.administration.service.FileNameValidation;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Documents Resource")
public class DocumentsController {

	@Autowired
	DocumentsRepository documentsRepository;
	@Autowired
	DocumentsFileRepository documentsFileRepository;
	@Autowired
	private DBFileStorageService DBFileStorageService;

	JwtResolver jwtResolver = new JwtResolver();
	FileNameValidation fileNameValidation = new FileNameValidation();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@ApiOperation(value = "Get Structure", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_DOCUMENT_STRUCTURE, method = RequestMethod.GET)
	public List<DocumentsResponse> getStructure(HttpServletRequest request) {
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			List<Documents> documents = documentsRepository.findAll();
			List<DocumentsResponse> result = new ArrayList<>();
			for (Documents doc : documents) {
				DocumentsResponse res = new DocumentsResponse();
				res.setId(doc.getId());
				res.setName(doc.getName());
				res.setParent(doc.getParent());
				res.setStatus(doc.getStatus());
				result.add(res);
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "Get Files", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.GET_FILES, method = RequestMethod.GET)
	public List<DocumentsFile> getFilesInFolder(@RequestParam("parent") long parent, HttpServletRequest request) {
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			List<DocumentsFile> files = documentsFileRepository.findByParent(parent);
			List<DocumentsFileResponse> result = new ArrayList<>();
			for (DocumentsFile file : files) {
				DocumentsFileResponse res = new DocumentsFileResponse();
				res.setId(file.getId());
				res.setFileName(file.getFileName());
				res.setFileType(file.getFileType());
				res.setParent(file.getParent());
				res.setDownloadURL(file.getDownloadURL());
				res.setStatus(file.getStatus());
				result.add(res);
			}
			return files;
		}
		return null;
	}

	@ApiOperation(value = "Add new Section or Folder", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = RestAPI.ADD_NEW_FOLDER, method = RequestMethod.POST)
	public ResponseEntity<?> addSection(@RequestBody DocumentsForm documentsForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			if (!documentsRepository.existsByNameAndParent(documentsForm.getName(), documentsForm.getParent())) {
				if (!documentsForm.getName().isEmpty()
						&& fileNameValidation.filenameIsValidOrNot(documentsForm.getName())) {
					Documents doc = new Documents();
					doc.setCreatedBy(id);
					doc.setCreatedDate(simpleDateFormat.format(new Date()));
					doc.setName(documentsForm.getName());
					doc.setParent(documentsForm.getParent());
					doc = documentsRepository.save(doc);
					if (doc != null)
						return new ResponseEntity<>(
								new ResponseMessage("Folder '" + documentsForm.getName() + "' Added Successfully!"),
								HttpStatus.OK);
					else
						return new ResponseEntity<>(
								new ResponseMessage("Unable to add new Folder, Please try again later!"),
								HttpStatus.BAD_REQUEST);
				} else
					return new ResponseEntity<>(
							new ResponseMessage(
									"The folder name you specified is not valid! Specify a different name."),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(
						new ResponseMessage(
								"Destination already contains a folder named '" + documentsForm.getName() + "'!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this folder!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Upload File", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = RestAPI.UPLOAD_FILE, method = RequestMethod.POST)
	public ResponseEntity<?> addFile(@RequestBody FileForm fileForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			for (MultipartFile file : fileForm.getFile()) {
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				if (fileNameValidation.filenameIsValidOrNot(fileName)) {
					if (!documentsFileRepository.existsByFileNameAndParent(fileName, fileForm.getParent())) {
						DocumentsFile dbFile = DBFileStorageService.storeFile(file, fileForm.getParent());
						String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
								.path("/downloadFile/").path(dbFile.getId()).toUriString();
						dbFile.setDownloadURL(fileDownloadUri);
						dbFile.setCreatedBy(id);
						dbFile.setCreatedDate(simpleDateFormat.format(new Date()));
						dbFile = documentsFileRepository.save(dbFile);
						if (dbFile == null)
							return new ResponseEntity<>(
									new ResponseMessage("Unable to add new File, Please try again later!"),
									HttpStatus.BAD_REQUEST);
					} else
						return new ResponseEntity<>(
								new ResponseMessage("Destination already contains a file named '" + fileName + "'!"),
								HttpStatus.BAD_REQUEST);
				} else
					return new ResponseEntity<>(
							new ResponseMessage("The file name you specified is not valid! Specify a different name."),
							HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(new ResponseMessage("File(s) Added Successfully!"), HttpStatus.OK);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this folder!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Download file", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.DOWNLOAD_FILE, method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
		// Load file from database
		DocumentsFile dbFile = DBFileStorageService.getFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}

}
