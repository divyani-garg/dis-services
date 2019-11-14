package sgsits.cse.dis.administration.request;

public class BookForm {
	private Long serialNo;
	private Long bookNo;
	private String bookName;
	private String author;
	private String type;
	private String registeredOn;
	private String version;
	private String status;
	private String remarks;
	public Long getSerialNo() {
		return serialNo;
	}
	public Long getBookNo() {
		return bookNo;
	}
	public String getBookName() {
		return bookName;
	}
	public String getAuthor() {
		return author;
	}
	public String getType() {
		return type;
	}
	public String getRegisteredOn() {
		return registeredOn;
	}
	public String getVersion() {
		return version;
	}
	public String getStatus() {
		return status;
	}
	public String getRemarks() {
		return remarks;
	}
}
