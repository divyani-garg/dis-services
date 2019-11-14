package sgsits.cse.dis.administration.response;

public class ThesisResponse {

	private Long id;
	private Long serialNo;
	private String title;
	private String year;
	private String session;
	private String submittedBy;
	private String guide;
	private String coguide;
	private String mediaAvailable;
	private String remarks;
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public String getCoguide() {
		return coguide;
	}
	public void setCoguide(String coguide) {
		this.coguide = coguide;
	}
	public String getMediaAvailable() {
		return mediaAvailable;
	}
	public void setMediaAvailable(String mediaAvailable) {
		this.mediaAvailable = mediaAvailable;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
