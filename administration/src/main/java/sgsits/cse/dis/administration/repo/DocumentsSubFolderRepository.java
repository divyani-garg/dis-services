package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.DocumentsSubFolder;

@Repository("documentsSubFolderRepository")
public interface DocumentsSubFolderRepository extends JpaRepository<DocumentsSubFolder, Long>{

	List<DocumentsSubFolder> findBySectionIdAndFolderId(long section, long folder);
	boolean existsBySubFolderNameAndFolderIdAndSectionId(String subFolderName, long folderId, long sectionId);

}
