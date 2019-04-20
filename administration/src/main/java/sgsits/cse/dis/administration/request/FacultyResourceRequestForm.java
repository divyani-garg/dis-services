package sgsits.cse.dis.administration.request;

import javax.validation.constraints.NotBlank;

public class FacultyResourceRequestForm {

	@NotBlank
	private String resource;
	
	@NotBlank
	private String details;

	public String getResource() {
		return resource;
	}

	public String getDetails() {
		return details;
	}
}
