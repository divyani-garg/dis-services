package sgsits.cse.dis.user.feign;

public class SubjectListResponse {
	
	private long id;
	private String subjectCode;
	private String subjectName;
	private String subjectAcronym;
	private String downloadLink;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectAcronym() {
		return subjectAcronym;
	}
	public void setSubjectAcronym(String subjectAcronym) {
		this.subjectAcronym = subjectAcronym;
	}
	public String getDownloadLink() {
		return downloadLink;
	}
	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}
}
