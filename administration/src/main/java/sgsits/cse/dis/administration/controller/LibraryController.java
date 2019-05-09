package sgsits.cse.dis.administration.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import sgsits.cse.dis.administration.constants.RestAPI;
import sgsits.cse.dis.administration.jwt.JwtResolver;
import sgsits.cse.dis.administration.model.Books;
import sgsits.cse.dis.administration.model.ThesisBE;
import sgsits.cse.dis.administration.model.ThesisME;
import sgsits.cse.dis.administration.repo.BooksRepository;
import sgsits.cse.dis.administration.repo.ThesisBERepository;
import sgsits.cse.dis.administration.repo.ThesisMERepository;
import sgsits.cse.dis.administration.request.BookForm;
import sgsits.cse.dis.administration.request.ThesisForm;
import sgsits.cse.dis.administration.response.BookResponse;
import sgsits.cse.dis.administration.response.ResponseMessage;
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

	JwtResolver jwtResolver = new JwtResolver();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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

	@ApiOperation(value = "Add Book", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public ResponseEntity<?> addBook(@RequestBody BookForm bookForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			int serial = 0, bookNo = 0; // value is null
			if (bookForm.getSerialNo() != null) {
				serial = 1; // value is not null // as such no use
				if (booksRepository.existsBySerialNo(bookForm.getSerialNo())) {
					serial = 2; // serial no. exists in the database
				} else
					serial = 3; // serial no. not exists in the database
			}
			if (bookForm.getBookNo() != null) {
				bookNo = 1;
				if (booksRepository.existsByBookNo(bookForm.getBookNo())) {
					bookNo = 2;
				} else
					bookNo = 3;
			}
			// serial & bookNo
			// 1. 0 & 3 null and not available
			// 2. 3 & 0 not available and null
			// 3. 3 & 3 both available
			if ((serial == 0 && bookNo == 3) || (serial == 3 && bookNo == 0) || (serial == 3 && bookNo == 3)) {
				Books books = new Books();
				books.setCreatedBy(id);
				books.setCreatedDate(simpleDateFormat.format(new Date()));
				books.setSerialNo(bookForm.getSerialNo());
				books.setBookNo(bookForm.getBookNo());
				books.setBookName(bookForm.getBookName());
				books.setAuthor(bookForm.getAuthor());
				books.setType(bookForm.getType());
				books.setRegisteredOn(bookForm.getRegisteredOn());
				books.setVersion(bookForm.getVersion());
				books.setStatus(bookForm.getStatus());
				books.setRemarks(bookForm.getRemarks());
				Books test = booksRepository.save(books);
				if (test != null)
					return new ResponseEntity<>(new ResponseMessage("Book has been added successfully!"),
							HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to add book, Please try again later!"),
							HttpStatus.BAD_REQUEST);
			}

			if (serial == 2)
				return new ResponseEntity<>(
						new ResponseMessage("Book is already available with serial No " + bookForm.getSerialNo() + "!"),
						HttpStatus.BAD_REQUEST);
			if (bookNo == 2)
				return new ResponseEntity<>(
						new ResponseMessage("Book is already available with Book No " + bookForm.getBookNo() + "!"),
						HttpStatus.BAD_REQUEST);
			if (serial == 0 && bookNo == 0)
				return new ResponseEntity<>(
						new ResponseMessage("Please enter either of the Serial Number or Book Number!"),
						HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to add this book!"),
					HttpStatus.BAD_REQUEST);
		return null;
	}

	@ApiOperation(value = "Edit Book", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/editBook", method = RequestMethod.PUT)
	public ResponseEntity<?> editBook(@RequestBody BookForm bookForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			if (booksRepository.existsBySerialNoOrBookNo(bookForm.getSerialNo(), bookForm.getBookNo())) {
				Optional<Books> book = booksRepository.findBySerialNoOrBookNo(bookForm.getSerialNo(),
						bookForm.getBookNo());
				book.get().setModifiedBy(id);
				book.get().setModifiedDate(simpleDateFormat.format(new Date()));
				book.get().setSerialNo(bookForm.getSerialNo());
				book.get().setBookNo(bookForm.getBookNo());
				book.get().setBookName(bookForm.getBookName());
				book.get().setAuthor(bookForm.getAuthor());
				book.get().setType(bookForm.getType());
				book.get().setRegisteredOn(bookForm.getRegisteredOn());
				book.get().setVersion(bookForm.getVersion());
				book.get().setStatus(bookForm.getStatus());
				book.get().setRemarks(bookForm.getRemarks());
				Books test = booksRepository.save(book.get());
				if (test != null)
					return new ResponseEntity<>(new ResponseMessage("Book has been updated successfully!"),
							HttpStatus.OK);
				else
					return new ResponseEntity<>(new ResponseMessage("Unable to update book, Please try again later!"),
							HttpStatus.BAD_REQUEST);
			} else
				return new ResponseEntity<>(
						new ResponseMessage("Book is not available with Serial No " + bookForm.getSerialNo() + " Or "
								+ "Book No " + bookForm.getBookNo() + "! You will need to add book first!"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(
					new ResponseMessage("You will need to provide administrator permission to update this book!"),
					HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Search Book", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = RestAPI.SEARCH_BOOK, method = RequestMethod.GET)
	public List<BookResponse> searchBook(@RequestParam("name") String name) {
		return null;
	}

	@ApiOperation(value = "Get BE Thesis Count", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getThesisBECount", method = RequestMethod.GET)
	public long getThesisBECount() {
		long count = thesisBERepository.count();
		return count;
	}

	@ApiOperation(value = "Get BE Thesis List", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getThesisBEList", method = RequestMethod.GET)
	public List<ThesisResponse> getThesisBEList() {
		List<ThesisBE> thesis = thesisBERepository.findAll();
		if (thesis != null) {
			List<ThesisResponse> result = new ArrayList<>();
			for (ThesisBE the : thesis) {
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

	@ApiOperation(value = "Add BE Thesis", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addThesisBE", method = RequestMethod.POST)
	public ResponseEntity<?> addThesisBE(@RequestBody ThesisForm thesisForm, HttpServletRequest request) {
		long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			if(!thesisBERepository.existsBySerialNo(thesisForm.getSerialNo())) {
				
			}
		}
		return null;
	}
	
	@ApiOperation(value = "Get ME Thesis Count", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getThesisMECount", method = RequestMethod.GET)
	public long getThesisMECount() {
		long count = thesisMERepository.count();
		return count;
	}

	@ApiOperation(value = "Get ME Thesis List", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/getThesisMEList", method = RequestMethod.GET)
	public List<ThesisResponse> getThesisMEList() {
		List<ThesisME> thesis = thesisMERepository.findAll();
		if (thesis != null) {
			List<ThesisResponse> result = new ArrayList<>();
			for (ThesisME the : thesis) {
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
