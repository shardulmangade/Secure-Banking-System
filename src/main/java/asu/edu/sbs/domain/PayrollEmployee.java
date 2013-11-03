package asu.edu.sbs.domain;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class PayrollEmployee {

	@NotBlank
	private String userName;
	
	private String createdBy;
	
	public void setCreatedBy(String createdby)
	{
		createdBy=createdby;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	private String role;
	
	private String department;
		
				
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
