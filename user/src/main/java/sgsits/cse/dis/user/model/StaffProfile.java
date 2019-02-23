package sgsits.cse.dis.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "staff_basic_profile")
public class StaffProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name = "employee_id", unique = true)
	private String employeeId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "name_acronym",unique = true)
	private String nameAcronym;
	
	@Lob
	@Column(name = "profile_picture")
	private byte[] profilePicture;
	
	@Column(name = "current_designation")
	private String currentDesignation;
	
	@Column(name = "class")
	private String classs;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "dob")
	private Date dob;
	
	@Column(name = "pan_number")
	private String panNumber;
	
	@Column(name = "aadhar_number")
	private String aadharNumber;
	
	@Column(name = "blood_group")
	private String bloodGroup;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "mother_name")
	private String motherName;
	
	@Column(name = "father_name")
	private String fatherName;
	
	@Column(name = "mobile_no")
	private long mobileNo;
	
	@Column(name = "alternate_mobile_no")
	private long alternateMobileNo;
	
	@Column(name = "joining_date")
	private Date joiningDate;
	
	@Column(name = "area_of_specialization")
	private String areaOfSpecialization;
	
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentDesignation() {
		return currentDesignation;
	}

	public void setCurrentDesignation(String currentDesignation) {
		this.currentDesignation = currentDesignation;
	}

	public String getClasss() {
		return classs;
	}

	public void setClasss(String classs) {
		this.classs = classs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getAlternateMobileNo() {
		return alternateMobileNo;
	}

	public void setAlternateMobileNo(long alternateMobileNo) {
		this.alternateMobileNo = alternateMobileNo;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getAreaOfSpecialization() {
		return areaOfSpecialization;
	}

	public void setAreaOfSpecialization(String areaOfSpecialization) {
		this.areaOfSpecialization = areaOfSpecialization;
	}

	public String getNameAcronym() {
		return nameAcronym;
	}

	public void setNameAcronym(String nameAcronym) {
		this.nameAcronym = nameAcronym;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}
}
