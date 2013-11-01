package asu.edu.sbs.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class SignUpMerchantEmployee {

	@NotBlank
	private String MerchantName;
	@NotBlank
	private String userName;
	
	@Email @NotBlank
	private String emailId;
	
	private String department;
	
	private String password;
	
	@NotBlank @Size(min = 10, max= 10)
	private String ssn;
	
	private String role;
	
	public String getMerchantName() {
		return MerchantName;
	}

	public void setMerchantName(String MerchantName) {
		this.MerchantName = MerchantName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
