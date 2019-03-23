package sgsits.cse.dis.administration.request;

import javax.validation.constraints.NotBlank;

public class LEComplaintForm {
	
	@NotBlank
	private String lab;
	
	@NotBlank
	private String systemNo;
	
	@NotBlank
	private String details;

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
