package sgsits.cse.dis.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_research_work")
public class UserResearchWork {
	
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
	private Long userId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "subcategory")
	private String subcategory;
	
	@Column(name = "journal_conference_name")
	private String journalConferenceName;
	
	@Column(name = "publisher")
	private String publisher;
	
	@Column(name = "no_of_authors")
	private int noOfAuthors;
	
	@Column(name = "author_one")
	private String authorOne;
	
	@Column(name = "author_two")
	private String authortwo;
	
	@Column(name = "other_authors")
	private String otherAuthors;
	
	@Column(name = "year_of_publication")
	private int yearOfPublication;

	@Column(name = "pdf")
	private String pdf;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getJournalConferenceName() {
		return journalConferenceName;
	}

	public void setJournalConferenceName(String journalConferenceName) {
		this.journalConferenceName = journalConferenceName;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setNoOfAuthors(int noOfAuthors) {
		this.noOfAuthors = noOfAuthors;
	}

	public void setAuthorOne(String authorOne) {
		this.authorOne = authorOne;
	}

	public void setAuthorTwo(String authortwo) {
		this.authortwo = authortwo;
	}

	public void setOtherAuthors(String otherAuthors) {
		this.otherAuthors = otherAuthors;
	}

	public int getNoOfAuthors() {
		return noOfAuthors;
	}

	public String getAuthorOne() {
		return authorOne;
	}

	public String getAuthorTwo() {
		return authortwo;
	}

	public String getOtherAuthors() {
		return otherAuthors;
	}

	public int getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(int yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

	public String getAuthortwo() {
		return authortwo;
	}

	public String getPdf() {
		return pdf;
	}

	public void setAuthortwo(String authortwo) {
		this.authortwo = authortwo;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	
	
}