package sgsits.cse.dis.user.message.request;

import java.util.Date;

public class StudentLeaveApplicationForm {
	
	private Date from;
	private Date to;
	private String subject;
	private String details;
	
	public Date getFrom() {
		return from;
	}
	public Date getTo() {
		return to;
	}
	public String getSubject() {
		return subject;
	}
	public String getDetails() {
		return details;
	}
}
