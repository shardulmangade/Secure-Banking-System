package asu.edu.sbs.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.ItDBConnectionManager;
import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class ItDeptManager {

	@Autowired
	private ItDBConnectionManager itdbconnection;
	@Autowired
	private LoginDBConnectionManager loginmanager;
	
	public void deleteItEmployee(String username) throws Exception
	{
		itdbconnection.deleteItEmployee(username);
	}
	
	public void updateDepartmentOfEmployee(String UserName,String department)  throws Exception
	{
		itdbconnection.updateDepartmentOfEmployee(UserName, department);
	}
	
	public int insertValidUser(User user, String password, String createdBy) throws BankStorageException, BankAccessException
	{		
			return (loginmanager.insertValidUser(user, password, createdBy));	
	}	
	
	public void insertDeleteRequesttoCM (String userName,String department, boolean deleteApprove) throws Exception 
	{
		itdbconnection.insertDeleteRequesttoCM(userName,department,deleteApprove);
	}
	
	public int getDeleteApprovalStatus (String userName,String department) throws Exception 
	{
		return itdbconnection.getDeleteApprovalStatus(userName,department);
	}


	public void updateUserRole(String role,String olddepartment, String department, String username,String changedby) throws BankStorageException, BankAccessException {
		// TODO Auto-generated method stub
		loginmanager.updateUserRole(role,olddepartment, department,username ,changedby);
	}

	public String getRoleTobechanged(String department, String role) {
		
		return (loginmanager.getRoleTobechanged( department,  role));
	}
	
	
}
