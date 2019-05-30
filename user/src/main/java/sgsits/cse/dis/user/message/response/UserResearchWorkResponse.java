package sgsits.cse.dis.user.message.response;

public class UserResearchWorkResponse {
	
	private String title;
	private String category;
	private String subCategory;
	private String journalConferenceName;
	private String publisher;
	private int yearOfPublication;
	private String pdf;
	private String authorOne;
	private String authorTwo;
	private String otherAuthors;
	private int noOfAuthors;
	
	public String getOtherAuthors() {
		return otherAuthors;
	}
	public void setOtherAuthors(String otherAuthors) {
		this.otherAuthors = otherAuthors;
	}
	public String getTitle() {
		return title;
	}
	public String getCategory() {
		return category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public String getJournalConferenceName() {
		return journalConferenceName;
	}
	public String getPublisher() {
		return publisher;
	}
	public int getYearOfPublication() {
		return yearOfPublication;
	}
	public String getPdf() {
		return pdf;
	}
	public String getAuthorOne() {
		return authorOne;
	}
	public String getAuthorTwo() {
		return authorTwo;
	}
	public int getNoOfAuthors() {
		return noOfAuthors;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public void setJournalConferenceName(String journalConferenceName) {
		this.journalConferenceName = journalConferenceName;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public void setYearOfPublication(int yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	public void setAuthorOne(String authorOne) {
		this.authorOne = authorOne;
	}
	public void setAuthorTwo(String authorTwo) {
		this.authorTwo = authorTwo;
	}
	public void setNoOfAuthors(int noOfAuthors) {
		this.noOfAuthors = noOfAuthors;
	}

}
