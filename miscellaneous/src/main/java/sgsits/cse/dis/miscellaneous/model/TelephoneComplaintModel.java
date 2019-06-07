package sgsits.cse.dis.miscellaneous.model;

public class TelephoneComplaintModel {
	private long id;
	private String createdDate;
	private String details;
	private long extensionNo;
	private String dateOfResolution;
	
	public TelephoneComplaintModel(int i, String createdDate, String details, int j,
			String dateOfResolution) {
		//super();
		this.id = i;
		this.createdDate = createdDate;
		this.details = details;
		this.extensionNo = j;
		this.dateOfResolution = dateOfResolution;
	}
	
	public TelephoneComplaintModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getDetails() {
		return details;
	}
	public Long getExtensionNo() {
		return extensionNo;
	}
	public String getDateOfResolution() {
		return dateOfResolution;
	}

}
