package sgsits.cse.dis.administration.request;

import javax.validation.constraints.NotBlank;

public class TelephoneComplaintForm {
	
	@NotBlank
	private int extensionNo; 
	
	@NotBlank
	private String location;
	
	@NotBlank
	private String details;

	public String getLocation() {
		return location;
	}

	public String getDetails() {
		return details;
	}

	public int getExtensionNo() {
		return extensionNo;
	}
}
