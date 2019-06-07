package sgsits.cse.dis.user.message.request;

public class StaffDutyForm {
	
	private long userId;
	private String insideOutside;
	private String fromDate;
	private String toDate;
	private String dutyDescription;
	private String place;
	
	public long getUserId() {
		return userId;
	}
	public String getInsideOutside() {
		return insideOutside;
	}
	public String getFromDate() {
		return fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public String getDutyDescription() {
		return dutyDescription;
	}
	public String getPlace() {
		return place;
	}
	
}
