package sgsits.cse.dis.administration.request;

public class SubFolderForm {
	
	private String subFolderName;
	private long folderId;
	private long sectionId;
	
	public String getSubFolderName() {
		return subFolderName;
	}
	public void setSubFolderName(String subFolderName) {
		this.subFolderName = subFolderName;
	}
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
	public long getSectionId() {
		return sectionId;
	}
	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}
}