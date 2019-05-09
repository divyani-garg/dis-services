package sgsits.cse.dis.administration.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.Books;

@Repository("booksRepository")
public interface BooksRepository extends JpaRepository<Books, Long>{
	
	boolean existsBySerialNo(Long serialNo);

	boolean existsByBookNo(Long bookNo);

	Optional<Books> findBySerialNoOrBookNo(Long serialNo, Long bookNo);

	boolean existsBySerialNoOrBookNo(Long serialNo, Long bookNo);

}
