package sgsits.cse.dis.infrastructure.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "infrastructure")
public class Infrastructure {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date")
	private Instant createdDate;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "modified_date")
	private Instant modifiedDate;
	
	@Column(name = "resource_id")
	private String resourceId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "area")
	private String area;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "faculty_incharge")
	private String facultyIncharge;
	
	@Column(name = "lab_technician_incharge")
	private String labTechnicianIncharge;
	
	@Column(name = "attendant")
	private String attendant;
	
	@Column(name = "no_of_tables")
	private int noofTables;
	
	@Column(name = "no_of_computer_tables")
	private int noofComputerTables;
	
	@Column(name = "no_of_chairs")
	private int noofChairs;
	
	@Column(name = "no_of_almirah")
	private int noofAlmirah;
	
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
	public Instant getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Instant getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFacultyIncharge() {
		return facultyIncharge;
	}
	public void setFacultyIncharge(String facultyIncharge) {
		this.facultyIncharge = facultyIncharge;
	}
	public String getLabTechnicianIncharge() {
		return labTechnicianIncharge;
	}
	public void setLabTechnicianIncharge(String labTechnicianIncharge) {
		this.labTechnicianIncharge = labTechnicianIncharge;
	}
	public String getAttendant() {
		return attendant;
	}
	public void setAttendant(String attendant) {
		this.attendant = attendant;
	}
	public int getNoofTables() {
		return noofTables;
	}
	public void setNoofTables(int noofTables) {
		this.noofTables = noofTables;
	}
	public int getNoofComputerTables() {
		return noofComputerTables;
	}
	public void setNoofComputerTables(int noofComputerTables) {
		this.noofComputerTables = noofComputerTables;
	}
	public int getNoofChairs() {
		return noofChairs;
	}
	public void setNoofChairs(int noofChairs) {
		this.noofChairs = noofChairs;
	}
	public int getNoofAlmirah() {
		return noofAlmirah;
	}
	public void setNoofAlmirah(int noofAlmirah) {
		this.noofAlmirah = noofAlmirah;
	}
}
