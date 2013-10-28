package asu.edu.sbs.hr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.HrDBConnectionManager;
import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class HrDeptManager {

	@Autowired
	private HrDBConnectionManager hrdbconnection;
	@Autowired
	private LoginDBConnectionManager loginmanager;
	
	public void  saveNewEmployeeRequest(String UserName,String firstName,String lastName ,String emailId,String department) throws Exception
	{
			System.out.println("\n In HrDb connection manager");
			hrdbconnection.saveNewEmployeeRequest( UserName, firstName, lastName , emailId, department);
	}
	
	public void deleteEmployeeRequest(String UserName)  throws Exception
	{
			hrdbconnection.deleteEmployeeRequest(UserName);
	}
	
	public void addNewHrEmployee(SignUpEmployee employee)  throws Exception
	{
			hrdbconnection.addNewHrEmployee(employee);
	}
	
	
	public int insertValidUser(User user, String password, String createdBy) throws BankStorageException
	{		
			return (loginmanager.insertValidUser(user, password, createdBy));	
	}
	
	public void deleteHrEmployee(String username) throws Exception
	{
		hrdbconnection.deleteHrEmployee(username);
	}
	
	public void updateDepartmentOfEmployee(String UserName,String department)  throws Exception
	{
			hrdbconnection.updateDepartmentOfEmployee(UserName, department);
	}
	
	public void insertDeleteRequesttoCM (String userName,String department, boolean deleteApprove) throws Exception 
	{
		hrdbconnection.insertDeleteRequesttoCM(userName,department,deleteApprove);
	}
	
	public int getDeleteApprovalStatus (String userName,String department) throws Exception 
	{
		return hrdbconnection.getDeleteApprovalStatus(userName,department);
	}

	public void updateUserRole(String role,String olddepartment, String department, String username,String changedby) throws BankStorageException, BankAccessException {
		// TODO Auto-generated method stub
		loginmanager.updateUserRole(role,olddepartment, department,username ,changedby);
	}

	public String getRoleTobechanged(String department, String role) {
		
		return (loginmanager.getRoleTobechanged( department,  role));
	}
	
	
}
