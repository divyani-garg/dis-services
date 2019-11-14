package sgsits.cse.dis.administration.response;


public class StudentNotificationResponse {

	private Long notificationId;
	
	private String userId;
	
	private String course;
	
	private int admissionYear;

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getAdmissionYear() {
		return admissionYear;
	}

	public void setAdmissionYear(int admissionYear) {
		this.admissionYear = admissionYear;
	}
	
	

}
