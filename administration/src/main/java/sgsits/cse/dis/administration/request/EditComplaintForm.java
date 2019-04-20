package sgsits.cse.dis.administration.request;

import javax.validation.constraints.NotBlank;

public class EditComplaintForm {

	@NotBlank
	private long id;
	
	@NotBlank
	private String type;
	
	@NotBlank
	private String status;
	
	@NotBlank
	private String remarks;
	
	@NotBlank
	private Long assignedTo;

	public long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

	public String getRemarks() {
		return remarks;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}
}
