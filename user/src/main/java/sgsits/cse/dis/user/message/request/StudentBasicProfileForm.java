package sgsits.cse.dis.user.message.request;

public class StudentBasicProfileForm {
	
	private long userId;
	private Long motherContact;
	private String motherEmail;
	private Long fatherContact;
	private String fatherEmail;
	private String bloodGroup;

	private Long mobileNo;
	
	public long getUserId() {
		return userId;
	}
	public Long getMotherContact() {
		return motherContact;
	}
	public String getMotherEmail() {
		return motherEmail;
	}
	public Long getFatherContact() {
		return fatherContact;
	}
	public String getFatherEmail() {
		return fatherEmail;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public Long getMobileNo() {
		return mobileNo;
	}
}
