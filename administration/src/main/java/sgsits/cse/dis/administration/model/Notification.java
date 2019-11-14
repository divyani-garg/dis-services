package sgsits.cse.dis.administration.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;

	@Column(name = "created_by", nullable = false)
	private Long createdBy;

	@Column(name = "created_date", nullable = false)
	private String createdDate;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "modified_date")
	private String modifiedDate;

	@Column(name = "notification_id")
	private Long notificationId;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "origin")
	private String origin;
		
	@Column(name = "link")
	private String link;
	
	
	public Notification() {
		super();
	}


	public Notification(Long notificationId, String content, String origin, String link) {
		super();
		this.notificationId = notificationId;
		this.content = content;
		this.origin = origin;
		this.link = link;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Long getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	public Long getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public Long getNotificationId() {
		return notificationId;
	}


	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}
	
	
	
	

}