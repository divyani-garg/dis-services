package sgsits.cse.dis.user.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_competitive_exams")
public class UserCompetitiveExams {
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

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "name_of_exam")
	private String nameOfExam;
	
	@Column(name = "score")
	private Double score;
	
	@Column(name = "rank")
	private Long rank;
	
	@Column(name = "registration_no")
	private String registrationNo;
	
	@Column(name = "year")
	private int year;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNameOfExam() {
		return nameOfExam;
	}

	public void setNameOfExam(String nameOfExam) {
		this.nameOfExam = nameOfExam;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
