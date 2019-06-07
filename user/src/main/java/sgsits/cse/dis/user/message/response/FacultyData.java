package sgsits.cse.dis.user.message.response;

public class FacultyData {
	private Long userId;
	private String userType;
	private String name;
	private String nameAcronym;
	private byte[] profilePicture;
	private String currentDesignation;
	private String email;
	private Long mobileNo;
	private Long alternateMobileNo;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameAcronym() {
		return nameAcronym;
	}
	public void setNameAcronym(String nameAcronym) {
		this.nameAcronym = nameAcronym;
	}
	public byte[] getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getCurrentDesignation() {
		return currentDesignation;
	}
	public void setCurrentDesignation(String currentDesignation) {
		this.currentDesignation = currentDesignation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Long getAlternateMobileNo() {
		return alternateMobileNo;
	}
	public void setAlternateMobileNo(Long alternateMobileNo) {
		this.alternateMobileNo = alternateMobileNo;
	}
}
