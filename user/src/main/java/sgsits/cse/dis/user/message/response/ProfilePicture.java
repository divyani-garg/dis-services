package sgsits.cse.dis.user.message.response;

import javax.persistence.Column;
import javax.persistence.Lob;

public class ProfilePicture {

	@Lob
	@Column(name = "profile_picture")
	private byte[] profilePicture;

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

}
