package sgsits.cse.dis.infrastructure.model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cleanliness")
public class Cleanliness {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date")
	private Instant createdDate;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "modified_date")
	private Instant modifiedDate;
	
	@Column(name = "complaint_id")
	private String complaintId;
	
	@Column(name = "cleaned_by_id")
	private String cleanedById;
	
	@Column(name = "report_by_id")
	private String reportById;
	
	@Column(name = "date_of_complaint")
	private Date dateOfComplaint;
	
	@Column(name = "date_of_resolution")
	private Date dateOfResolution;
	
	@Column(name = "name_of_area")
	private String nameOfArea;
	
	@Column(name = "details")
	private String details;
	
	@Column(name = "level_of_dust")
	private int levelOfDust;
	
	@Column(name = "status")
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Instant getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getCleanedById() {
		return cleanedById;
	}

	public void setCleanedById(String cleanedById) {
		this.cleanedById = cleanedById;
	}

	public String getReportById() {
		return reportById;
	}

	public void setReportById(String reportById) {
		this.reportById = reportById;
	}

	public Date getDateOfComplaint() {
		return dateOfComplaint;
	}

	public void setDateOfComplaint(Date dateOfComplaint) {
		this.dateOfComplaint = dateOfComplaint;
	}

	public Date getDateOfResolution() {
		return dateOfResolution;
	}

	public void setDateOfResolution(Date dateOfResolution) {
		this.dateOfResolution = dateOfResolution;
	}

	public String getNameOfArea() {
		return nameOfArea;
	}

	public void setNameOfArea(String nameOfArea) {
		this.nameOfArea = nameOfArea;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getLevelOfDust() {
		return levelOfDust;
	}

	public void setLevelOfDust(int levelOfDust) {
		this.levelOfDust = levelOfDust;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
