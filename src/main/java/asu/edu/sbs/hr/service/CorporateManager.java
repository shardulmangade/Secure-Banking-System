package asu.edu.sbs.hr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.CorporateDBConnectionManager;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.email.EmailNotificationManager;
import asu.edu.sbs.login.service.OneTimePassword;

@Service
public class CorporateManager {

	@Autowired
	private CorporateDBConnectionManager corporateDbconnection;
	@Autowired
	private EmailNotificationManager enManager;
	
	public void  saveNewEmployeeRequest(SignUpEmployee employee) throws Exception
	{
			System.out.println("\nIn corpoarte manager to sign up");
			//generate otp
			OneTimePassword otpInstance = new OneTimePassword();
			String hashedPassword = otpInstance.getHashedOTP();
			employee.setPassword(otpInstance.getPassword());
			corporateDbconnection.saveNewEmployeeRequest(employee);
			//send email
			enManager.sendPassword(employee);
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
}
