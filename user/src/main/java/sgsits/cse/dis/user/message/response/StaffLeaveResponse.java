package sgsits.cse.dis.user.message.response;

public class StaffLeaveResponse {

	private Long userId;
	private String createdDate;
	private String Name;
	private String toDate;
	private String fromDate;
	private String subject;
	private String details;
	private String remarks;
	private String typeOfLeave;
	private String halfdayFullday;
	
	public Long getUserId() {
		return userId;
	}
	public String getName() {
		return Name;
	}
	public String getToDate() {
		return toDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public String getSubject() {
		return subject;
	}
	public String getDetails() {
		return details;
	}
	public String getRemarks() {
		return remarks;
	}
	public String getTypeOfLeave() {
		return typeOfLeave;
	}
	public String getHalfdayFullday() {
		return halfdayFullday;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setName(String name) {
		Name = name;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setTypeOfLeave(String typeOfLeave) {
		this.typeOfLeave = typeOfLeave;
	}
	public void setHalfdayFullday(String halfdayFullday) {
		this.halfdayFullday = halfdayFullday;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}

}
