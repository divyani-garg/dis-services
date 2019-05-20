package sgsits.cse.dis.user.message.request;

public class UserResearchWorkForm {
 
	private Long userId;
	private String title;
	private String category;
	private String subcategory;
	private String journalConferenceName;
	private String publisher;
	private String coAuthors;
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
	public String getCoAuthors() {
		return coAuthors;
	}
	public String getGuideName() {
		return guideName;
	}
	public int getYearOfPublication() {
		return yearOfPublication;
	}
	
}
