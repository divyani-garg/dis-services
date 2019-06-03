package sgsits.cse.dis.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "staff_leave")
public class StaffLeave {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_date")
	private String createdDate;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "modified_date")
	private String modifiedDate;
	
	@Column(name = "to_date")
	private String toDate;

	@Column(name = "from_date")
	private String fromDate;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "details")
	private String details;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "type_of_leave")
	private String typeOfLeave;
	
	@Column(name = "halfday_fullday")
	private String halfdayFullday;

	public long getId() {
		return id;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getToDate() {
		return toDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getSubject() {
		return subject;
	}

	public String getDetails() {
		return details;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getStatus() {
		return status;
	}

	public String getTypeOfLeave() {
		return typeOfLeave;
	}

	public String getHalfdayFullday() {
		return halfdayFullday;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTypeOfLeave(String typeOfLeave) {
		this.typeOfLeave = typeOfLeave;
	}

	public void setHalfdayFullday(String halfdayFullday) {
		this.halfdayFullday = halfdayFullday;
	}
	
	
}
