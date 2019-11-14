package sgsits.cse.dis.administration.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "faculty_resource_request")
public class FacultyResourceRequest {

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
	
	@Column(name = "resource_category")
	private String resourceCategory;

	@Column(name = "details")
	private String details;

	@Column(name = "status")
	private String status;

	@Column(name = "priority")
	private String priority;

	@Column(name = "date_of_resolution")
	private String dateOfResolution;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "deadline_to_resolve")
	private String deadlineToResolve;
	

	public FacultyResourceRequest(String resourceCategory, String details, String priority, String deadlineToResolve) {
		super();
		this.resourceCategory = resourceCategory;
		this.details = details;
		this.priority = priority;
		this.deadlineToResolve = deadlineToResolve;
	}

	public FacultyResourceRequest() { }

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

	public String getResourceCategory() {
		return resourceCategory;
	}

	public void setResourceCategory(String resourceCategory) {
		this.resourceCategory = resourceCategory;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDateOfResolution() {
		return dateOfResolution;
	}

	public void setDateOfResolution(String dateOfResolution) {
		this.dateOfResolution = dateOfResolution;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDeadlineToResolve() {
		return deadlineToResolve;
	}

	public void setDeadlineToResolve(String deadlineToResolve) {
		this.deadlineToResolve = deadlineToResolve;
	}
	
	
}
