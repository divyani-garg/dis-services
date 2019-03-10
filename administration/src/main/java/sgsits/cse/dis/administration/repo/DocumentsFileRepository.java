package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.DocumentsFile;

@Repository
public interface DocumentsFileRepository extends JpaRepository<DocumentsFile, String> {

	List<DocumentsFile> findBySectionIdAndFolderId(long section, long folder);
	List<DocumentsFile> findBySectionIdAndFolderIdAndSubFolderId(long section, long folder, long subfolder);

}
