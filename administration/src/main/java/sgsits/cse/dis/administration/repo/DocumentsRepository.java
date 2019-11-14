package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.Documents;

@Repository("documentsRepository")
public interface DocumentsRepository extends JpaRepository<Documents, Long>{

	boolean existsByNameAndParent(String name, long parent);

}
