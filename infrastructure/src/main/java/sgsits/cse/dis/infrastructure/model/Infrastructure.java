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
	private Long createdBy;
	
	@Column(name = "created_date")
	private Instant createdDate;
	
	@Column(name = "modified_by")
	private Long modifiedBy;
	
	@Column(name = "modified_date")
	private Instant modifiedDate;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "name_acronym")
	private String nameAcronym;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "area")
	private String area;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "incharge")
	private Long incharge;
	
	@Column(name = "associate_incharge")
	private Long associateIncharge;
	
	@Column(name = "staff")
	private Long staff;
	
	@Column(name = "attendent")
	private Long attendent;
	
	@Column(name = "no_of_tables")
	private Integer noofTables;
	
	@Column(name = "no_of_computer_tables")
	private Integer noofComputerTables;
	
	@Column(name = "no_of_chairs")
	private Integer noofChairs;
	
	@Column(name = "no_of_almirah")
	private Integer noofAlmirah;

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

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Instant getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameAcronym() {
		return nameAcronym;
	}

	public void setNameAcronym(String nameAcronym) {
		this.nameAcronym = nameAcronym;
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

	public Long getIncharge() {
		return incharge;
	}

	public void setIncharge(Long incharge) {
		this.incharge = incharge;
	}

	public Long getAssociateIncharge() {
		return associateIncharge;
	}

	public void setAssociateIncharge(Long associateIncharge) {
		this.associateIncharge = associateIncharge;
	}

	public Long getStaff() {
		return staff;
	}

	public void setStaff(Long staff) {
		this.staff = staff;
	}

	public Long getAttendent() {
		return attendent;
	}

	public void setAttendent(Long attendent) {
		this.attendent = attendent;
	}

	public Integer getNoofTables() {
		return noofTables;
	}

	public void setNoofTables(Integer noofTables) {
		this.noofTables = noofTables;
	}

	public Integer getNoofComputerTables() {
		return noofComputerTables;
	}

	public void setNoofComputerTables(Integer noofComputerTables) {
		this.noofComputerTables = noofComputerTables;
	}

	public Integer getNoofChairs() {
		return noofChairs;
	}

	public void setNoofChairs(Integer noofChairs) {
		this.noofChairs = noofChairs;
	}

	public Integer getNoofAlmirah() {
		return noofAlmirah;
	}

	public void setNoofAlmirah(Integer noofAlmirah) {
		this.noofAlmirah = noofAlmirah;
	}
}