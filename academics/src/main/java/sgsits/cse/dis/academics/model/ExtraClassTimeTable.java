package sgsits.cse.dis.academics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "extra_class_time_table")
public class ExtraClassTimeTable {

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

	@Column(name = "course_id", nullable = false)
	private String courseId;

	@Column(name = "session", nullable = false)
	private String session;

	@Column(name = "year", nullable = false)
	private String year;

	@Column(name = "semester", nullable = false)
	private String semester;

	@Column(name = "subject_code", nullable = false)
	private String subjectCode;

	@Column(name = "from_time", nullable = false)
	private String from;

	@Column(name = "to_time", nullable = false)
	private String to;
	
	@Column(name = "day", nullable = false)
	private String day;
	
	@Column(name = "date", nullable = false)
	private String date;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "faculty_1")
	private String faculty1;

	@Column(name = "faculty_2")
	private String faculty2;

	@Column(name = "faculty_3")
	private String faculty3;

	@Column(name = "lab_technician")
	private String labTechnician;

	@Column(name = "ta")
	private String ta;

	@Column(name = "batch")
	private String batch;

	@Column(name = "location", nullable = false)
	private String location;
	
	@Column(name = "which")
	private String which;

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

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFaculty1() {
		return faculty1;
	}

	public void setFaculty1(String faculty1) {
		this.faculty1 = faculty1;
	}

	public String getFaculty2() {
		return faculty2;
	}

	public void setFaculty2(String faculty2) {
		this.faculty2 = faculty2;
	}

	public String getFaculty3() {
		return faculty3;
	}

	public void setFaculty3(String faculty3) {
		this.faculty3 = faculty3;
	}

	public String getLabTechnician() {
		return labTechnician;
	}

	public void setLabTechnician(String labTechnician) {
		this.labTechnician = labTechnician;
	}

	public String getTa() {
		return ta;
	}

	public void setTa(String ta) {
		this.ta = ta;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWhich() {
		return which;
	}

	public void setWhich(String which) {
		this.which = which;
	}

}
