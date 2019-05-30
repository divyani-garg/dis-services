package sgsits.cse.dis.user.message.request;

public class UserResearchWorkForm {
 
	private Long userId;
	private String title;
	private String category;
	private String subcategory;
	private String journalConferenceName;
	private String publisher;
	private int noOfAuthors;
	private String authorOne;
	private String authorTwo;
	private String otherAuthors;
	private String guideName;
	private int yearOfPublication; 
	
	public Long getUserId() {
		return userId;
	}
	public String getTitle() {
		return title;
	}
	public String getCategory() {
		return category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public String getJournalConferenceName() {
		return journalConferenceName;
	}
	public String getPublisher() {
		return publisher;
	}
	public int getNoOfAuthors() {
		return noOfAuthors;
	}
	public String getAuthorOne() {
		return authorOne;
	}
	public String getAuthorTwo() {
		return authorTwo;
	}
	public String getOtherAuthors() {
		return otherAuthors;
	}
	public String getGuideName() {
		return guideName;
	}
	public int getYearOfPublication() {
		return yearOfPublication;
	}
	
}
