package sgsits.cse.dis.user.model.presentation;

public class FacultyBriefData {
	
	private String name;
	private String nameAcronym;
	private byte[] profilePicture;
	private String currentDesignation;
	private String email;
	
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
}
