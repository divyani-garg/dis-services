package sgsits.cse.dis.user.message.request;

public class UserQualificationForm {
	private Long userId;
	private String degreeCertificate;
	private int yearOfPassing;
	private String collegeSchool;
	private String universityBoard;
	private String percentageCgpa;
	private String specialization;
	
	public Long getUserId() {
		return userId;
	}
	public String getDegreeCertificate() {
		return degreeCertificate;
	}
	public int getYearOfPassing() {
		return yearOfPassing;
	}
	public String getCollegeSchool() {
		return collegeSchool;
	}
	public String getUniversityBoard() {
		return universityBoard;
	}
	public String getPercentageCgpa() {
		return percentageCgpa;
	}
	public String getSpecialization() {
		return specialization;
	}
}
