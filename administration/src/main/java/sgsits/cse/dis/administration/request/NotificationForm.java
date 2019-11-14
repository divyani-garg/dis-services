package sgsits.cse.dis.administration.request;

import javax.validation.constraints.NotBlank;

public class NotificationForm {
	
	@NotBlank
	private String content;
	
	@NotBlank
	private String origin;
	
	private String link;

	
	public String getContent() {
		return content;
	}
	public String getOrigin() {
		return origin;
	}
	public String getLink() {
		return link;
	}
	
	
}
