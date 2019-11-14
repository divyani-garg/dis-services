package sgsits.cse.dis.user.message.response;

public class StudentBasicProfileResponse {
	
	private long userId;
	private String enrollmentId;
	private String name;
	private String courseYearSession;
	private Long mobileNo;
	private String email;
	private String dob;
	private String fatherName;
	private Long fatherContact;
	private String fatherEmail;
	private String motherName;
	private Long motherContact;
	private String motherEmail;
	private String category;
	private String gender;
	private String bloodGroup;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourseYearSession() {
		return courseYearSession;
	}
	public void setCourseYearSession(String courseYearSession) {
		this.courseYearSession = courseYearSession;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public Long getFatherContact() {
		return fatherContact;
	}
	public void setFatherContact(Long fatherContact) {
		this.fatherContact = fatherContact;
	}
	public String getFatherEmail() {
		return fatherEmail;
	}
	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public Long getMotherContact() {
		return motherContact;
	}
	public void setMotherContact(Long motherContact) {
		this.motherContact = motherContact;
	}
	public String getMotherEmail() {
		return motherEmail;
	}
	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
}
