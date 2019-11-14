package sgsits.cse.dis.academics.model.response;

public class SubjectCodeAndYearResponse {
	
	private String subjectCode;
	private String year;
	private String subjectName;
	
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public String getYear() {
		return year;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	

}
