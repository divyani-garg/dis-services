package sgsits.cse.dis.administration.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.administration.model.Books;
import sgsits.cse.dis.administration.model.ThesisBE;
import sgsits.cse.dis.administration.model.ThesisME;
import sgsits.cse.dis.administration.repo.BooksRepository;
import sgsits.cse.dis.administration.repo.ThesisBERepository;
import sgsits.cse.dis.administration.repo.ThesisMERepository;
import sgsits.cse.dis.administration.response.BookResponse;
import sgsits.cse.dis.administration.response.ThesisResponse;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Library Resource")
public class LibraryController {

	@Autowired
	BooksRepository booksRepository;
	@Autowired
	ThesisBERepository thesisBERepository;
	@Autowired
	ThesisMERepository thesisMERepository;

	@ApiOperation(value = "Get Book Count", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getBookCount", method = RequestMethod.GET)
	public long getBookCount() {
		long count = booksRepository.count();
		return count;
	}

	@ApiOperation(value = "Get Book List", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getBookList", method = RequestMethod.GET)
	public List<BookResponse> getBookList() {
		List<Books> books = booksRepository.findAll();
		if (!books.isEmpty()) {
			List<BookResponse> result = new ArrayList<>();
			for (Books book : books) {
				BookResponse res = new BookResponse();
				res.setId(book.getId());
				res.setSerialNo(book.getSerialNo());
				res.setBookNo(book.getBookNo());
				res.setBookName(book.getBookName());
				res.setAuthor(book.getAuthor());
				res.setType(book.getType());
				res.setVersion(book.getVersion());
				res.setStatus(book.getStatus());
				res.setRemarks(book.getRemarks());
				result.add(res);
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "Search Book", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchBook", method = RequestMethod.GET)
	public List<BookResponse> searchBook(@RequestParam("name") String name) {
		return null;
	}
	
	@ApiOperation(value = "Get BE Thesis Count", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getThesisBECount", method = RequestMethod.GET)
	public long getThesisBECount(){
		long count = thesisBERepository.count();
		return count;
	}
	
	@ApiOperation(value = "Get BE Thesis List", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getThesisBEList", method = RequestMethod.GET)
	public List<ThesisResponse> getThesisBEList(){
		List<ThesisBE> thesis = thesisBERepository.findAll();
		if(thesis!=null) {
			List<ThesisResponse> result = new ArrayList<>();
			for(ThesisBE the : thesis) {
				ThesisResponse res = new ThesisResponse();
				res.setId(the.getId());
				res.setSerialNo(the.getSerialNo());
				res.setTitle(the.getTitle());
				res.setYear(the.getYear());
				res.setSession(the.getSession());
				res.setSubmittedBy(the.getSubmittedBy());
				res.setGuide(the.getGuide());
				res.setCoguide(the.getCoguide());
				res.setMediaAvailable(the.getMediaAvailable());
				res.setStatus(the.getStatus());
				res.setRemarks(the.getRemarks());
				result.add(res);
			}
			return result;
		}
		return null;
	}
	
	@ApiOperation(value = "Get ME Thesis Count", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getThesisMECount", method = RequestMethod.GET)
	public long getThesisMECount(){
		long count = thesisMERepository.count();
		return count;
	}
	
	@ApiOperation(value = "Get ME Thesis List", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getThesisMEList", method = RequestMethod.GET)
	public List<ThesisResponse> getThesisMEList(){
		List<ThesisME> thesis = thesisMERepository.findAll();
		if(thesis!=null) {
			List<ThesisResponse> result = new ArrayList<>();
			for(ThesisME the : thesis) {
				ThesisResponse res = new ThesisResponse();
				res.setId(the.getId());
				res.setSerialNo(the.getSerialNo());
				res.setTitle(the.getTitle());
				res.setYear(the.getYear());
				res.setSession(the.getSession());
				res.setSubmittedBy(the.getSubmittedBy());
				res.setGuide(the.getGuide());
				res.setCoguide(the.getCoguide());
				res.setMediaAvailable(the.getMediaAvailable());
				res.setStatus(the.getStatus());
				res.setRemarks(the.getRemarks());
				result.add(res);
			}
			return result;
		}
		return null;
	}
	
}
