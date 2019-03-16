package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.DocumentsFolder;

@Repository("documentsFolderRepository")
public interface DocumentsFolderRepository extends JpaRepository<DocumentsFolder, Long>{

	List<DocumentsFolder> findBySectionId(long section);

	boolean existsByFolderNameAndSectionId(String folderName, long sectionId);

}
