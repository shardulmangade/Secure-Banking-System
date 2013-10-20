package asu.edu.sbs.hr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.HrDBConnectionManager;
import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.domain.User;

@Service
public class HrDeptManager {

	@Autowired
	private HrDBConnectionManager hrdbconnection;
	
	public void  saveNewEmployeeRequest(String UserName,String firstName,String lastName ,String emailId,String department) throws Exception
	{
			System.out.println("\n In HrDb connection manager");
			hrdbconnection.saveNewEmployeeRequest( UserName, firstName, lastName , emailId, department);
	}
	
	public void deleteEmployeeRequest(String UserName)  throws Exception
	{
			hrdbconnection.deleteEmployeeRequest(UserName);
	}
	
	public void addNewHrEmployee(String UserName,String firstName,String lastName ,String emailId,String department)  throws Exception
	{
			hrdbconnection.addNewHrEmployee(UserName, firstName, lastName , emailId, department);
	}
	
	public void updateDepartmentOfEmployee(String UserName,String department)  throws Exception
	{
			hrdbconnection.updateDepartmentOfEmployee(UserName, department);
	}
	
}
