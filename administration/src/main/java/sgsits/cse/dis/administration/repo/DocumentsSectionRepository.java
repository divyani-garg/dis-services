package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.DocumentsSection;

@Repository("documentsSectionRepository")
public interface DocumentsSectionRepository extends JpaRepository<DocumentsSection, Long>{

	boolean existsBySectionName(String sectionName);

}
