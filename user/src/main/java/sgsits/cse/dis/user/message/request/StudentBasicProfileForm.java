package sgsits.cse.dis.user.message.request;

public class StudentBasicProfileForm {
	
	private long userId;
	private String motherName;
	private long motherContact;
	private String motherEmail;
	private String fatherName;
	private long fatherContact;
	private String fatherEmail;
	private String bloodGroup;
	private long mobileNo;
	
	public long getUserId() {
		return userId;
	}
	public String getMotherName() {
		return motherName;
	}
	public long getMotherContact() {
		return motherContact;
	}
	public String getMotherEmail() {
		return motherEmail;
	}
	public String getFatherName() {
		return fatherName;
	}
	public long getFatherContact() {
		return fatherContact;
	}
	public String getFatherEmail() {
		return fatherEmail;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public long getMobileNo() {
		return mobileNo;
	}
}
