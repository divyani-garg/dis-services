package sgsits.cse.dis.administration.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "library_thesis_be")
public class ThesisBE {
	
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
	
	@Column(name = "serial_no")
	private Long serialNo;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "session")
	private String session;
	
	@Column(name = "submitted_by")
	private String submittedBy;
	
	@Column(name = "guide")
	private String guide;
	
	@Column(name = "coguide")
	private String coguide;
	
	@Column(name = "media_available")
	private String mediaAvailable;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "status")
	private String status;

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

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public String getCoguide() {
		return coguide;
	}

	public void setCoguide(String coguide) {
		this.coguide = coguide;
	}

	public String getMediaAvailable() {
		return mediaAvailable;
	}

	public void setMediaAvailable(String mediaAvailable) {
		this.mediaAvailable = mediaAvailable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
