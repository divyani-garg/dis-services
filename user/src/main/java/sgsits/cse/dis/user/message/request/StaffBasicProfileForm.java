package sgsits.cse.dis.user.message.request;

public class StaffBasicProfileForm {
	
	private long userId;
	private String dob;
	private String motherName;
	private String fatherName;
	private String bloodGroup;
	private Long mobileNo;
	private Long alternateMobileNo;
	private String areaOfSpecialization;

	public long getUserId() {
		return userId;
	}
	public String getDob() {
		return dob;
	}
	public String getMotherName() {
		return motherName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public Long getMobileNo() {
		return mobileNo;
	}
	public Long getAlternateMobileNo() {
		return alternateMobileNo;
	}
	public String getAreaOfSpecialization() {
		return areaOfSpecialization;
	}
}
