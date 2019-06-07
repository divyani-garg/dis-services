package sgsits.cse.dis.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "staff_duties")
public class StaffDuty {
	
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
	
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "inside_outside")
	private String insideOutside;
	
	@Column(name = "from_date")
	private String fromDate;
	
	@Column(name = "to_date")
	private String toDate;
	
	@Column(name = "duty_description")
	private String dutyDescription;
	
	@Column(name = "place")
	private String place;

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

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setInsideOutside(String where) {
		this.insideOutside = where;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public void setDutyDescription(String dutyDescription) {
		this.dutyDescription = dutyDescription;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	
	

}
