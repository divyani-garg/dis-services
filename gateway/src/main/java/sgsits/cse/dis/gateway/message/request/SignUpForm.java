package sgsits.cse.dis.gateway.message.request;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
    
    private Date dob;
   
    private long mobileNo;
      
    @NotBlank
    @Size(min = 6, max = 40)
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

	public Date getDob() {
		return dob;
	}

	public long getMobileNo() {
		return mobileNo;
	}
}