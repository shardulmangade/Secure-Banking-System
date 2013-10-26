package asu.edu.sbs.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.ItDBConnectionManager;
import asu.edu.sbs.domain.SignUpEmployee;

@Service
public class ItDeptManager {

	@Autowired
	private ItDBConnectionManager itdbconnection;
	
	public void deleteItEmployee(String username) throws Exception
	{
		itdbconnection.deleteItEmployee(username);
	}
	
	public void updateDepartmentOfEmployee(String UserName,String department)  throws Exception
	{
		itdbconnection.updateDepartmentOfEmployee(UserName, department);
	}
	
	public void insertDeleteRequesttoCM (String userName,String department, boolean deleteApprove) throws Exception 
	{
		itdbconnection.insertDeleteRequesttoCM(userName,department,deleteApprove);
	}
	
	public int getDeleteApprovalStatus (String userName,String department) throws Exception 
	{
		return itdbconnection.getDeleteApprovalStatus(userName,department);
	}
	
}
