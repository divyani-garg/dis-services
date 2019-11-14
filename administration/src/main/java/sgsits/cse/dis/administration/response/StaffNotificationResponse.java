package sgsits.cse.dis.administration.response;

public class StaffNotificationResponse {

	private Long notificationId;
	
	private Long userId;
	
	private int status;
	
	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

		
}
