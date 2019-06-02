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
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@ApiOperation(value = "Search Book", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchBook/{keyword}", method = RequestMethod.GET)
	public List<BookResponse> searchBook(@PathVariable String keyword) {
		List<Books> books = booksRepository.findAll();
		if (!books.isEmpty()) {
			String keyw=keyword.toLowerCase();
			//System.out.println(keyw);
			List<BookResponse> result = new ArrayList<>();
			for (Books book : books) {
				if(book.getBookName()!=null)
				{
					String book_name=book.getBookName().toLowerCase();
					if(book.getAuthor()!=null)
					{
						String author_name=book.getAuthor().toLowerCase();
						if(book_name.contains(keyw) || author_name.contains(keyw))
						{
							//System.out.println(book_name + " "+ author_name);
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
					}
					else
					{
						if(book_name.contains(keyw))
						{
							//System.out.println(book_name + " "+ author_name);
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
					}
				}
				else
				{
					if(book.getAuthor()!=null)
					{
						String author_name=book.getAuthor().toLowerCase();
						if(author_name.contains(keyw))
						{
							//System.out.println(book_name + " "+ author_name);
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
					}
				}
				
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

//	@ApiOperation(value = "Search Book", response = Object.class, httpMethod = "GET", produces = "application/json")
//	@RequestMapping(value = RestAPI.SEARCH_BOOK, method = RequestMethod.GET)
//	public List<BookResponse> searchBook(@RequestParam("name") String name) {
//		return null;
//	}

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
				res.setGuide2(the.getGuide2());
				res.setGuide3(the.getGuide3());
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
				int serial=0;
				if(thesisForm.getSerialNo()!=null) {
					ThesisBE thesis=new ThesisBE();
					thesis.setCreatedBy(id);
					thesis.setCreatedDate(simpleDateFormat.format(new Date()));
					thesis.setSerialNo(thesisForm.getSerialNo());
					thesis.setTitle(thesisForm.getTitle());
					thesis.setSession(thesisForm.getSession());
					thesis.setYear(thesisForm.getYear());
					thesis.setSubmittedBy(thesisForm.getSubmittedBy());
					thesis.setGuide(thesisForm.getGuide());
					thesis.setGuide2(thesisForm.getGuide2());
					thesis.setGuide3(thesisForm.getGuide3());
					thesis.setMediaAvailable(thesisForm.getMediaAvailable());
					thesis.setStatus(thesisForm.getStatus());
					thesis.setStatus(thesisForm.getStatus());
					
					ThesisBE test=thesisBERepository.save(thesis);
					
					if(test!=null) {
						return new ResponseEntity<>(new ResponseMessage("BE Thesis has been added successfully!"),HttpStatus.OK);
					}
					else {
						return new ResponseEntity<>(new ResponseMessage("Unable to add BE Thesis, please try again later!"),HttpStatus.BAD_REQUEST);
					}
				}
				else {
					return new ResponseEntity<>(new ResponseMessage("Please enter the serial number!"),HttpStatus.BAD_REQUEST);
				}
				
			}
			else {
				return new ResponseEntity<>(new ResponseMessage("BE Thesis is already available with Thesis no. "+thesisForm.getSerialNo()+" !"),HttpStatus.BAD_REQUEST);
			}
		}
		else {
			return new ResponseEntity<>(new ResponseMessage("You will need to provide administrator permission to add this BE Thesis!"),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value= "Edit BE Thesis", response=Object.class, httpMethod= "PUT", produces= "application/json")
	@RequestMapping(value= "/editThesisBE", method= RequestMethod.PUT)
	public ResponseEntity<?> editThesisBE(@RequestBody ThesisForm thesisForm, HttpServletRequest request){
		long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
		if(!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
			if(thesisBERepository.existsBySerialNo(thesisForm.getSerialNo())) {
				Optional<ThesisBE> thesisBE = thesisBERepository.findBySerialNo(thesisForm.getSerialNo());
						thesisBE.get().setModifiedBy(id);
						thesisBE.get().setModifiedDate(simpleDateFormat.format(new Date()));
						thesisBE.get().setSerialNo(thesisForm.getSerialNo());
						thesisBE.get().setTitle(thesisForm.getTitle());
						thesisBE.get().setYear(thesisForm.getYear());
						thesisBE.get().setSession(thesisForm.getSession());
						thesisBE.get().setSubmittedBy(thesisForm.getSubmittedBy());
						thesisBE.get().setGuide(thesisForm.getGuide());
						thesisBE.get().setGuide2(thesisForm.getGuide2());
						thesisBE.get().setGuide3(thesisForm.getGuide3());
						thesisBE.get().setMediaAvailable(thesisForm.getMediaAvailable());
						thesisBE.get().setRemarks(thesisForm.getRemarks());
						thesisBE.get().setStatus(thesisForm.getStatus());
						
						ThesisBE test=thesisBERepository.save(thesisBE.get());
						if(test!= null)
							return new ResponseEntity<>(new ResponseMessage("BE Thesis has been updated successfully!"),HttpStatus.OK);
						else
							return new ResponseEntity<>(new ResponseMessage("Unable to update BE Thesis, Please try again later!"),HttpStatus.BAD_REQUEST);
						
						
			}
			else
				return new ResponseEntity<>(new ResponseMessage("BE Thesis is not available with Serial No. "+thesisForm.getSerialNo()+"! You will need to add the BE Thesis first!"),HttpStatus.BAD_REQUEST);
			
		}
		else
			return new ResponseEntity<>(new ResponseMessage("You will need to provide administrator permission to update this BE Thesis!"),HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Search BE Thesis (by Title)", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchBEThesis/byTitle/{keyword}", method = RequestMethod.GET)
	public List<ThesisResponse> searchBEThesisByTitle(@PathVariable String keyword) {
		List<ThesisBE> thesis = thesisBERepository.findAll();
		String keyw=keyword.toLowerCase();
		if (thesis != null) {
			List<ThesisResponse> result = new ArrayList<>();
			for (ThesisBE the : thesis) {
				if(the.getTitle()!=null)
				{
					String title=the.getTitle().toLowerCase();
					if(title.contains(keyw))
					{
						ThesisResponse res = new ThesisResponse();
						res.setId(the.getId());
						res.setSerialNo(the.getSerialNo());
						res.setTitle(the.getTitle());
						res.setYear(the.getYear());
						res.setSession(the.getSession());
						res.setSubmittedBy(the.getSubmittedBy());
						res.setGuide(the.getGuide());
						res.setGuide2(the.getGuide2());
						res.setGuide3(the.getGuide3());
						res.setMediaAvailable(the.getMediaAvailable());
						res.setStatus(the.getStatus());
						res.setRemarks(the.getRemarks());
						result.add(res);
					}
				}
			}
			return result;
		}
		return null;
	}

	@ApiOperation(value = "Search BE Thesis (by SubmittedBy)", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchBEThesis/bySubmittedBy/{keyword}", method = RequestMethod.GET)
	public List<ThesisResponse> searchBEThesisBySubmittedBy(@PathVariable String keyword) {
		List<ThesisBE> thesis = thesisBERepository.findAll();
		String keyw=keyword.toLowerCase();
		if (thesis != null) {
			List<ThesisResponse> result = new ArrayList<>();
			for (ThesisBE the : thesis) {
				if(the.getSubmittedBy()!=null)
				{
					String submittedBy=the.getSubmittedBy().toLowerCase();
					if(submittedBy.contains(keyw))
					{
						ThesisResponse res = new ThesisResponse();
						res.setId(the.getId());
						res.setSerialNo(the.getSerialNo());
						res.setTitle(the.getTitle());
						res.setYear(the.getYear());
						res.setSession(the.getSession());
						res.setSubmittedBy(the.getSubmittedBy());
						res.setGuide(the.getGuide());
						res.setGuide2(the.getGuide2());
						res.setGuide3(the.getGuide3());
						res.setMediaAvailable(the.getMediaAvailable());
						res.setStatus(the.getStatus());
						res.setRemarks(the.getRemarks());
						result.add(res);
					}
				}
			}
			return result;
		}
		return null;
	}
	
	@ApiOperation(value = "Search BE Thesis (by Guide)", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/searchBEThesis/byGuide/{keyword}", method = RequestMethod.GET)
	public List<ThesisResponse> searchBEThesisByGuide(@PathVariable String keyword) {
		List<ThesisBE> thesis = thesisBERepository.findAll();
		String keyw=keyword.toLowerCase();
		if (thesis != null) {
			List<ThesisResponse> result = new ArrayList<>();
			for (ThesisBE the : thesis) {
				if(the.getGuide()!=null)
				{
					String guide=the.getGuide().toLowerCase();
					if(the.getGuide2()!=null)
					{
						String guide2=the.getGuide2().toLowerCase();
						if(the.getGuide3()!=null)
						{
							String guide3=the.getGuide3().toLowerCase();
							if(guide.contains(keyw)||guide2.contains(keyw)||guide3.contains(keyw))
							{
								ThesisResponse res = new ThesisResponse();
								res.setId(the.getId());
								res.setSerialNo(the.getSerialNo());
								res.setTitle(the.getTitle());
								res.setYear(the.getYear());
								res.setSession(the.getSession());
								res.setSubmittedBy(the.getSubmittedBy());
								res.setGuide(the.getGuide());
								res.setGuide2(the.getGuide2());
								res.setGuide3(the.getGuide3());
								res.setMediaAvailable(the.getMediaAvailable());
								res.setStatus(the.getStatus());
								res.setRemarks(the.getRemarks());
								result.add(res);
							}
						}
						else
						{
							if(guide.contains(keyw)||guide2.contains(keyw))
							{
								ThesisResponse res = new ThesisResponse();
								res.setId(the.getId());
								res.setSerialNo(the.getSerialNo());
								res.setTitle(the.getTitle());
								res.setYear(the.getYear());
								res.setSession(the.getSession());
								res.setSubmittedBy(the.getSubmittedBy());
								res.setGuide(the.getGuide());
								res.setGuide2(the.getGuide2());
								res.setGuide3(the.getGuide3());
								res.setMediaAvailable(the.getMediaAvailable());
								res.setStatus(the.getStatus());
								res.setRemarks(the.getRemarks());
								result.add(res);
							}
						}
						
					}
					else
					{
						if(the.getGuide3()!=null)
						{
							String guide3=the.getGuide3().toLowerCase();
							if(guide.contains(keyw)||guide3.contains(keyw))
							{
								ThesisResponse res = new ThesisResponse();
								res.setId(the.getId());
								res.setSerialNo(the.getSerialNo());
								res.setTitle(the.getTitle());
								res.setYear(the.getYear());
								res.setSession(the.getSession());
								res.setSubmittedBy(the.getSubmittedBy());
								res.setGuide(the.getGuide());
								res.setGuide2(the.getGuide2());
								res.setGuide3(the.getGuide3());
								res.setMediaAvailable(the.getMediaAvailable());
								res.setStatus(the.getStatus());
								res.setRemarks(the.getRemarks());
								result.add(res);
							}
						}
						else
						{
							if(guide.contains(keyw))
							{
								ThesisResponse res = new ThesisResponse();
								res.setId(the.getId());
								res.setSerialNo(the.getSerialNo());
								res.setTitle(the.getTitle());
								res.setYear(the.getYear());
								res.setSession(the.getSession());
								res.setSubmittedBy(the.getSubmittedBy());
								res.setGuide(the.getGuide());
								res.setGuide2(the.getGuide2());
								res.setGuide3(the.getGuide3());
								res.setMediaAvailable(the.getMediaAvailable());
								res.setStatus(the.getStatus());
								res.setRemarks(the.getRemarks());
								result.add(res);
							}
						}
					}
				}
				else
				{
					if(the.getGuide2()!=null)
					{
						String guide2=the.getGuide2().toLowerCase();
						if(the.getGuide3()!=null)
						{
							String guide3=the.getGuide3().toLowerCase();
							if(guide2.contains(keyw)||guide3.contains(keyw))
							{
								ThesisResponse res = new ThesisResponse();
								res.setId(the.getId());
								res.setSerialNo(the.getSerialNo());
								res.setTitle(the.getTitle());
								res.setYear(the.getYear());
								res.setSession(the.getSession());
								res.setSubmittedBy(the.getSubmittedBy());
								res.setGuide(the.getGuide());
								res.setGuide2(the.getGuide2());
								res.setGuide3(the.getGuide3());
								res.setMediaAvailable(the.getMediaAvailable());
								res.setStatus(the.getStatus());
								res.setRemarks(the.getRemarks());
								result.add(res);
							}
						}
						else
						{
							if(guide2.contains(keyw))
							{
								ThesisResponse res = new ThesisResponse();
								res.setId(the.getId());
								res.setSerialNo(the.getSerialNo());
								res.setTitle(the.getTitle());
								res.setYear(the.getYear());
								res.setSession(the.getSession());
								res.setSubmittedBy(the.getSubmittedBy());
								res.setGuide(the.getGuide());
								res.setGuide2(the.getGuide2());
								res.setGuide3(the.getGuide3());
								res.setMediaAvailable(the.getMediaAvailable());
								res.setStatus(the.getStatus());
								res.setRemarks(the.getRemarks());
								result.add(res);
							}
						}
						
					}
					else
					{
						if(the.getGuide3()!=null)
						{
							String guide3=the.getGuide3().toLowerCase();
							if(guide3.contains(keyw))
							{
								ThesisResponse res = new ThesisResponse();
								res.setId(the.getId());
								res.setSerialNo(the.getSerialNo());
								res.setTitle(the.getTitle());
								res.setYear(the.getYear());
								res.setSession(the.getSession());
								res.setSubmittedBy(the.getSubmittedBy());
								res.setGuide(the.getGuide());
								res.setGuide2(the.getGuide2());
								res.setGuide3(the.getGuide3());
								res.setMediaAvailable(the.getMediaAvailable());
								res.setStatus(the.getStatus());
								res.setRemarks(the.getRemarks());
								result.add(res);
							}
						}
					}
				}
			}
			
			return result;
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
				res.setGuide2(the.getGuide2());
				res.setGuide3(the.getGuide3());
				res.setMediaAvailable(the.getMediaAvailable());
				res.setStatus(the.getStatus());
				res.setRemarks(the.getRemarks());
				result.add(res);
			}
			return result;
		}
		return null;
	}




@ApiOperation(value = "Add ME Thesis", response = Object.class, httpMethod = "POST", produces = "application/json")
@RequestMapping(value = "/addThesisME", method = RequestMethod.POST)
public ResponseEntity<?> addThesisME(@RequestBody ThesisForm thesisForm, HttpServletRequest request) {
	long id = jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
	if (!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
		if(!thesisMERepository.existsBySerialNo(thesisForm.getSerialNo())) {
			int serial=0;
			if(thesisForm.getSerialNo()!=null) {
				ThesisME thesis=new ThesisME();
				thesis.setCreatedBy(id);
				thesis.setCreatedDate(simpleDateFormat.format(new Date()));
				thesis.setSerialNo(thesisForm.getSerialNo());
				thesis.setTitle(thesisForm.getTitle());
				thesis.setSession(thesisForm.getSession());
				thesis.setYear(thesisForm.getYear());
				thesis.setSubmittedBy(thesisForm.getSubmittedBy());
				thesis.setGuide(thesisForm.getGuide());
				thesis.setGuide2(thesisForm.getGuide2());
				thesis.setGuide3(thesisForm.getGuide3());
				thesis.setMediaAvailable(thesisForm.getMediaAvailable());
				thesis.setStatus(thesisForm.getStatus());
				thesis.setStatus(thesisForm.getStatus());
				
				ThesisME test=thesisMERepository.save(thesis);
				
				if(test!=null) {
					return new ResponseEntity<>(new ResponseMessage("ME Thesis has been added successfully!"),HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(new ResponseMessage("Unable to add ME Thesis, please try again later!"),HttpStatus.BAD_REQUEST);
				}
			}
			else {
				return new ResponseEntity<>(new ResponseMessage("Please enter the serial number!"),HttpStatus.BAD_REQUEST);
			}
			
		}
		else {
			return new ResponseEntity<>(new ResponseMessage("ME Thesis is already available with Thesis no. "+thesisForm.getSerialNo()+" !"),HttpStatus.BAD_REQUEST);
		}
	}
	else {
		return new ResponseEntity<>(new ResponseMessage("You will need to provide administrator permission to add this BE Thesis!"),HttpStatus.BAD_REQUEST);
	}
}

@ApiOperation(value= "Edit ME Thesis", response=Object.class, httpMethod= "PUT", produces= "application/json")
@RequestMapping(value= "/editThesisME", method= RequestMethod.PUT)
public ResponseEntity<?> editThesisME(@RequestBody ThesisForm thesisForm, HttpServletRequest request){
	long id=jwtResolver.getIdFromJwtToken(request.getHeader("Authorization"));
	if(!jwtResolver.getUserTypeFromJwtToken(request.getHeader("Authorization")).equals("student")) {
		if(thesisMERepository.existsBySerialNo(thesisForm.getSerialNo())) {
			Optional<ThesisME> thesisME = thesisMERepository.findBySerialNo(thesisForm.getSerialNo());
					thesisME.get().setModifiedBy(id);
					thesisME.get().setModifiedDate(simpleDateFormat.format(new Date()));
					thesisME.get().setSerialNo(thesisForm.getSerialNo());
					thesisME.get().setTitle(thesisForm.getTitle());
					thesisME.get().setYear(thesisForm.getYear());
					thesisME.get().setSession(thesisForm.getSession());
					thesisME.get().setSubmittedBy(thesisForm.getSubmittedBy());
					thesisME.get().setGuide(thesisForm.getGuide());
					thesisME.get().setGuide2(thesisForm.getGuide2());
					thesisME.get().setGuide3(thesisForm.getGuide3());
					thesisME.get().setMediaAvailable(thesisForm.getMediaAvailable());
					thesisME.get().setRemarks(thesisForm.getRemarks());
					thesisME.get().setStatus(thesisForm.getStatus());
					
					ThesisME test=thesisMERepository.save(thesisME.get());
					if(test!= null)
						return new ResponseEntity<>(new ResponseMessage("ME Thesis has been updated successfully!"),HttpStatus.OK);
					else
						return new ResponseEntity<>(new ResponseMessage("Unable to update ME Thesis, Please try again later!"),HttpStatus.BAD_REQUEST);
					
					
		}
		else
			return new ResponseEntity<>(new ResponseMessage("ME Thesis is not available with Serial No. "+thesisForm.getSerialNo()+"! You will need to add the ME Thesis first!"),HttpStatus.BAD_REQUEST);
		
	}
	else
		return new ResponseEntity<>(new ResponseMessage("You will need to provide administrator permission to update this ME Thesis!"),HttpStatus.BAD_REQUEST);
}

@ApiOperation(value = "Search ME Thesis (by Title)", response = Object.class, httpMethod = "GET", produces = "application/json")
@RequestMapping(value = "/searchMEThesis/byTitle/{keyword}", method = RequestMethod.GET)
public List<ThesisResponse> searchMEThesisByTitle(@PathVariable String keyword) {
	List<ThesisME> thesis = thesisMERepository.findAll();
	String keyw=keyword.toLowerCase();
	if (thesis != null) {
		List<ThesisResponse> result = new ArrayList<>();
		for (ThesisME the : thesis) {
			if(the.getTitle()!=null)
			{
				String title=the.getTitle().toLowerCase();
				if(title.contains(keyw))
				{
					ThesisResponse res = new ThesisResponse();
					res.setId(the.getId());
					res.setSerialNo(the.getSerialNo());
					res.setTitle(the.getTitle());
					res.setYear(the.getYear());
					res.setSession(the.getSession());
					res.setSubmittedBy(the.getSubmittedBy());
					res.setGuide(the.getGuide());
					res.setGuide2(the.getGuide2());
					res.setGuide3(the.getGuide3());
					res.setMediaAvailable(the.getMediaAvailable());
					res.setStatus(the.getStatus());
					res.setRemarks(the.getRemarks());
					result.add(res);
				}
			}
		}
		return result;
	}
	return null;
}

@ApiOperation(value = "Search ME Thesis (by SubmittedBy)", response = Object.class, httpMethod = "GET", produces = "application/json")
@RequestMapping(value = "/searchMEThesis/bySubmittedBy/{keyword}", method = RequestMethod.GET)
public List<ThesisResponse> searchMEThesisBySubmittedBy(@PathVariable String keyword) {
	List<ThesisME> thesis = thesisMERepository.findAll();
	String keyw=keyword.toLowerCase();
	if (thesis != null) {
		List<ThesisResponse> result = new ArrayList<>();
		for (ThesisME the : thesis) {
			if(the.getSubmittedBy()!=null)
			{
				String submittedBy=the.getSubmittedBy().toLowerCase();
				if(submittedBy.contains(keyw))
				{
					ThesisResponse res = new ThesisResponse();
					res.setId(the.getId());
					res.setSerialNo(the.getSerialNo());
					res.setTitle(the.getTitle());
					res.setYear(the.getYear());
					res.setSession(the.getSession());
					res.setSubmittedBy(the.getSubmittedBy());
					res.setGuide(the.getGuide());
					res.setGuide2(the.getGuide2());
					res.setGuide3(the.getGuide3());
					res.setMediaAvailable(the.getMediaAvailable());
					res.setStatus(the.getStatus());
					res.setRemarks(the.getRemarks());
					result.add(res);
				}
			}
		}
		return result;
	}
	return null;
}

@ApiOperation(value = "Search ME Thesis (by Guide)", response = Object.class, httpMethod = "GET", produces = "application/json")
@RequestMapping(value = "/searchMEThesis/byGuide/{keyword}", method = RequestMethod.GET)
public List<ThesisResponse> searchMEThesisByGuide(@PathVariable String keyword) {
	List<ThesisME> thesis = thesisMERepository.findAll();
	String keyw=keyword.toLowerCase();
	if (thesis != null) {
		List<ThesisResponse> result = new ArrayList<>();
		for (ThesisME the : thesis) {
			if(the.getGuide()!=null)
			{
				String guide=the.getGuide().toLowerCase();
				if(the.getGuide2()!=null)
				{
					String guide2=the.getGuide2().toLowerCase();
					if(the.getGuide3()!=null)
					{
						String guide3=the.getGuide3().toLowerCase();
						if(guide.contains(keyw)||guide2.contains(keyw)||guide3.contains(keyw))
						{
							ThesisResponse res = new ThesisResponse();
							res.setId(the.getId());
							res.setSerialNo(the.getSerialNo());
							res.setTitle(the.getTitle());
							res.setYear(the.getYear());
							res.setSession(the.getSession());
							res.setSubmittedBy(the.getSubmittedBy());
							res.setGuide(the.getGuide());
							res.setGuide2(the.getGuide2());
							res.setGuide3(the.getGuide3());
							res.setMediaAvailable(the.getMediaAvailable());
							res.setStatus(the.getStatus());
							res.setRemarks(the.getRemarks());
							result.add(res);
						}
					}
					else
					{
						if(guide.contains(keyw)||guide2.contains(keyw))
						{
							ThesisResponse res = new ThesisResponse();
							res.setId(the.getId());
							res.setSerialNo(the.getSerialNo());
							res.setTitle(the.getTitle());
							res.setYear(the.getYear());
							res.setSession(the.getSession());
							res.setSubmittedBy(the.getSubmittedBy());
							res.setGuide(the.getGuide());
							res.setGuide2(the.getGuide2());
							res.setGuide3(the.getGuide3());
							res.setMediaAvailable(the.getMediaAvailable());
							res.setStatus(the.getStatus());
							res.setRemarks(the.getRemarks());
							result.add(res);
						}
					}
					
				}
				else
				{
					if(the.getGuide3()!=null)
					{
						String guide3=the.getGuide3().toLowerCase();
						if(guide.contains(keyw)||guide3.contains(keyw))
						{
							ThesisResponse res = new ThesisResponse();
							res.setId(the.getId());
							res.setSerialNo(the.getSerialNo());
							res.setTitle(the.getTitle());
							res.setYear(the.getYear());
							res.setSession(the.getSession());
							res.setSubmittedBy(the.getSubmittedBy());
							res.setGuide(the.getGuide());
							res.setGuide2(the.getGuide2());
							res.setGuide3(the.getGuide3());
							res.setMediaAvailable(the.getMediaAvailable());
							res.setStatus(the.getStatus());
							res.setRemarks(the.getRemarks());
							result.add(res);
						}
					}
					else
					{
						if(guide.contains(keyw))
						{
							ThesisResponse res = new ThesisResponse();
							res.setId(the.getId());
							res.setSerialNo(the.getSerialNo());
							res.setTitle(the.getTitle());
							res.setYear(the.getYear());
							res.setSession(the.getSession());
							res.setSubmittedBy(the.getSubmittedBy());
							res.setGuide(the.getGuide());
							res.setGuide2(the.getGuide2());
							res.setGuide3(the.getGuide3());
							res.setMediaAvailable(the.getMediaAvailable());
							res.setStatus(the.getStatus());
							res.setRemarks(the.getRemarks());
							result.add(res);
						}
					}
				}
			}
			else
			{
				if(the.getGuide2()!=null)
				{
					String guide2=the.getGuide2().toLowerCase();
					if(the.getGuide3()!=null)
					{
						String guide3=the.getGuide3().toLowerCase();
						if(guide2.contains(keyw)||guide3.contains(keyw))
						{
							ThesisResponse res = new ThesisResponse();
							res.setId(the.getId());
							res.setSerialNo(the.getSerialNo());
							res.setTitle(the.getTitle());
							res.setYear(the.getYear());
							res.setSession(the.getSession());
							res.setSubmittedBy(the.getSubmittedBy());
							res.setGuide(the.getGuide());
							res.setGuide2(the.getGuide2());
							res.setGuide3(the.getGuide3());
							res.setMediaAvailable(the.getMediaAvailable());
							res.setStatus(the.getStatus());
							res.setRemarks(the.getRemarks());
							result.add(res);
						}
					}
					else
					{
						if(guide2.contains(keyw))
						{
							ThesisResponse res = new ThesisResponse();
							res.setId(the.getId());
							res.setSerialNo(the.getSerialNo());
							res.setTitle(the.getTitle());
							res.setYear(the.getYear());
							res.setSession(the.getSession());
							res.setSubmittedBy(the.getSubmittedBy());
							res.setGuide(the.getGuide());
							res.setGuide2(the.getGuide2());
							res.setGuide3(the.getGuide3());
							res.setMediaAvailable(the.getMediaAvailable());
							res.setStatus(the.getStatus());
							res.setRemarks(the.getRemarks());
							result.add(res);
						}
					}
					
				}
				else
				{
					if(the.getGuide3()!=null)
					{
						String guide3=the.getGuide3().toLowerCase();
						if(guide3.contains(keyw))
						{
							ThesisResponse res = new ThesisResponse();
							res.setId(the.getId());
							res.setSerialNo(the.getSerialNo());
							res.setTitle(the.getTitle());
							res.setYear(the.getYear());
							res.setSession(the.getSession());
							res.setSubmittedBy(the.getSubmittedBy());
							res.setGuide(the.getGuide());
							res.setGuide2(the.getGuide2());
							res.setGuide3(the.getGuide3());
							res.setMediaAvailable(the.getMediaAvailable());
							res.setStatus(the.getStatus());
							res.setRemarks(the.getRemarks());
							result.add(res);
						}
					}
				}
			}
		}
		
		return result;
	}
	return null;
}



}