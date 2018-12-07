package sgsits.cse.dis.user.model;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_profile")
public class StudentProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "enrollment_id", nullable = false, unique = true)
	private String enrollmentId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "reset_token")
	private String resetToken;

	@Column(name = "reset_token_expiry")
	private LocalDate resetTokenExpiry;

	@Column(name = "activation_link")
	private String activationlink;

	@Column(name = "activation_link_expiry")
	private LocalDate activationLinkExpiry;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "admission_year")
	private int admissionYear;

	@Column(name = "course_id")
	private String courseId;

	@Column(name = "mobile_no")
	private long mobileNo;

	@Column(name = "email")
	private String email;

	@Column(name = "dob")
	private Date dob;

	@Column(name = "father_name")
	private String fatherName;

	@Column(name = "father_contact")
	private long fatherContact;

	@Column(name = "father_email")
	private String fatherEmail;

	@Column(name = "mother_name")
	private String motherName;

	@Column(name = "mother_contact")
	private long motherContact;

	@Column(name = "mother_email")
	private String motherEmail;

	@Column(name = "category")
	private String category;

	@Column(name = "gender")
	private String gender;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "last_login")
	private Date lastLogin;

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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getAdmissionYear() {
		return admissionYear;
	}

	public void setAdmissionYear(int admissionYear) {
		this.admissionYear = admissionYear;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
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

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public long getFatherContact() {
		return fatherContact;
	}

	public void setFatherContact(long fatherContact) {
		this.fatherContact = fatherContact;
	}

	public String getFatherEmail() {
		return fatherEmail;
	}

	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public long getMotherContact() {
		return motherContact;
	}

	public void setMotherContact(long motherContact) {
		this.motherContact = motherContact;
	}

	public String getMotherEmail() {
		return motherEmail;
	}

	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public LocalDate getResetTokenExpiry() {
		return resetTokenExpiry;
	}

	public void setResetTokenExpiry(LocalDate localDate) {
		this.resetTokenExpiry = localDate;
	}

	public String getActivationlink() {
		return activationlink;
	}

	public void setActivationlink(String activationlink) {
		this.activationlink = activationlink;
	}

	public LocalDate getActivationLinkExpiry() {
		return activationLinkExpiry;
	}

	public void setActivationLinkExpiry(LocalDate activationLinkExpiry) {
		this.activationLinkExpiry = activationLinkExpiry;
	}
}