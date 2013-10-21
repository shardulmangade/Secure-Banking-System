package asu.edu.sbs.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.CorporateDBConnectionManager;
import asu.edu.sbs.domain.SignUpEmployee;

@Service
public class CorporateManager {

	@Autowired
	private CorporateDBConnectionManager corporateDbconnection;
	
	public void  saveNewEmployeeRequest(SignUpEmployee employee) throws Exception
	{
			System.out.println("\nIn corpoarte manager to sign up");
			employee.setPassword("random password"); //random passsword currently. Use OTP once Ashwin is done with it
			corporateDbconnection.saveNewEmployeeRequest(employee);
	}
	
	public void deleteEmployeeRequest(String UserName)  throws Exception
	{
			corporateDbconnection.deleteEmployeeRequest(UserName);
	}
	
	public void updateDepartmentOfEmployee(String UserName,String department)  throws Exception
	{
			corporateDbconnection.updateDepartmentOfEmployee(UserName, department);
	}
}
