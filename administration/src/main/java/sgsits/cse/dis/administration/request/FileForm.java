package sgsits.cse.dis.administration.request;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {
	
	private MultipartFile[] file;
	private Long parent;
	
	public MultipartFile[] getFile() {
		return file;
	}

	public Long getParent() {
		return parent;
	}
}
