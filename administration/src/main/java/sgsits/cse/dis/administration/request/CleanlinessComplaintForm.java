package sgsits.cse.dis.administration.request;

import javax.validation.constraints.NotBlank;

public class CleanlinessComplaintForm {

	@NotBlank
	private String location;
	
	@NotBlank
	private String details;
	
	@NotBlank
	private int levelOfDust;
	
	public String getLocation() {
		return location;
	}
	public String getDetails() {
		return details;
	}
	public int getLevelOfDust() {
		return levelOfDust;
	}
}
