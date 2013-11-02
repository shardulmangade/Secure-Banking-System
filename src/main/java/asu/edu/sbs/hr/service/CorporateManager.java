package asu.edu.sbs.hr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.CorporateDBConnectionManager;
import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.email.EmailNotificationManager;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankStorageException;
import asu.edu.sbs.login.service.OneTimePassword;

@Service
public class CorporateManager {

	@Autowired
	private CorporateDBConnectionManager corporateDbconnection;
	@Autowired
	private EmailNotificationManager enManager;
	@Autowired
	private LoginDBConnectionManager loginDbConnectionManager;

	public void  saveNewEmployeeRequest(User employee, String createdBy) throws Exception{	
		System.out.println("\nIn corpoarte manager to sign up");
		//generate otp
		OneTimePassword otpInstance = new OneTimePassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword(otpInstance.getPassword(), null);//change this
		//employee.set(hashedPass);
		System.out.println(hashedPass);
		loginDbConnectionManager.insertValidUser(employee, otpInstance.getPassword(), createdBy);
		//corporateDbconnection.saveNewEmployeeRequest(employee);
		//send email

		//TO BE CHNAGED BY APURV LATER
		//enManager.sendPassword(employee, otpInstance.getPassword());
	}

	public void deleteEmployeeRequest(String UserName)  throws Exception
	{
		corporateDbconnection.deleteEmployeeRequest(UserName);
	}

	public void updateDepartmentOfEmployee(String UserName,String department)  throws Exception
	{
		corporateDbconnection.updateDepartmentOfEmployee(UserName, department);
	}

	public List<SignUpEmployee> getAllPendingUserRequests() {

		return corporateDbconnection.getAllPendingUserRequests();
	}

	public void deleteEmployee(String username) throws Exception{

		corporateDbconnection.deleteEmployee(username);
	}

	public String getRoleTobechanged(String department, String role) {

		return (loginDbConnectionManager.getRoleTobechanged( department,  role));
	}

	public int insertValidUser(User user, String password, String createdBy) throws BankStorageException, BankAccessException
	{		
		user.setRole(loginDbConnectionManager.getRoleTobechanged(user.getDepartment(), user.getRole()));
		return (loginDbConnectionManager.insertValidUser(user, password, createdBy));	
	}
	public void updateUserRole(String role, String department, String username,String changedby) throws BankStorageException, BankAccessException {
		// TODO Auto-generated method stub
		loginDbConnectionManager.updateUserRole(role,department,username ,changedby);
	}
}
