package sgsits.cse.dis.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_placement")
public class UserPlacement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;
	
	@Column(name = "created_by", nullable = false)
	private Long createdBy;

	@Column(name = "created_date", nullable = false)
	private String createdDate;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "modified_date")
	private String modifiedDate;
	
	@Column(name = "enrollment_id")
	private String enrollmentId;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "joining_location")
	private String joiningLocation;

	@Column(name = "joining_status")
	private String joiningStatus;

	@Column(name = "package")
	private double joiningPackage;
	
	@Column(name = "campus_type")
	private String campusType;

	@Column(name = "user_id")
	private long userId;
	
	public long getUserId() {
		return userId;
	}

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

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getJoiningLocation() {
		return joiningLocation;
	}

	public String getJoiningStatus() {
		return joiningStatus;
	}

	public double getJoiningPackage() {
		return joiningPackage;
	}

	public String getCampusType() {
		return campusType;
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

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setJoiningLocation(String joiningLocation) {
		this.joiningLocation = joiningLocation;
	}

	public void setJoiningStatus(String joiningStatus) {
		this.joiningStatus = joiningStatus;
	}

	public void setJoiningPackage(double joiningPackage) {
		this.joiningPackage = joiningPackage;
	}

	public void setCampusType(String campusType) {
		this.campusType = campusType;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
