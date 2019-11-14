package sgsits.cse.dis.user.message.request;

public class UserAddressForm {
	private long userId;
	private String addressLine1;
	private String addressLine2;
	private String state;
	private String city;
	private String country;
	private int pincode;
	private String type;
	
	public long getUserId() {
		return userId;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public String getState() {
		return state;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public int getPincode() {
		return pincode;
	}
	public String getType() {
		return type;
	}
}
