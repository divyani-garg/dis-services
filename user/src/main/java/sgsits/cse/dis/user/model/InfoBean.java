package sgsits.cse.dis.user.model;

public class InfoBean {
	
	
		public String getEmployeeId() {
		return employeeId;
	}

	public InfoBean(String employeeId, String name, String email, String gender, String motherName,
				String fatherName) {
			super();
			this.employeeId = employeeId;
			this.name = name;
			this.email = email;
			this.gender = gender;
			this.motherName = motherName;
			this.fatherName = fatherName;
		}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}


		private String employeeId;
		
		private String name;	
		
		private String email;
		
		private String gender;
		
		private String motherName;
		
		private String fatherName;
		
		

	}


