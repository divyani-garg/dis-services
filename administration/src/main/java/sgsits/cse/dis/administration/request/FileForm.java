package sgsits.cse.dis.administration.request;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {
	
	private MultipartFile[] file;
	private Long sectionId;
	private Long folderId;
	private Long subFolderId;
	
	public MultipartFile[] getFile() {
		return file;
	}
	public void setFile(MultipartFile[] file) {
		this.file = file;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	public Long getSubFolderId() {
		return subFolderId;
	}
	public void setSubFolderId(Long subFolderId) {
		this.subFolderId = subFolderId;
	}
}
