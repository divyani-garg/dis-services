package sgsits.cse.dis.user.message.request;

public class SignUpForm {
    private String username;
    private String email;    
    private String dob;
    private long mobileNo;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

	public String getDob() {
		return dob;
	}

	public long getMobileNo() {
		return mobileNo;
	}
}