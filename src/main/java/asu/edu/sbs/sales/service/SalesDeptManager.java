package asu.edu.sbs.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.HrDBConnectionManager;
import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;

@Service
public class SalesDeptManager {

	@Autowired
	private SalesDBConnectionManager salesdbconnection;
	
	public void  saveNewEmployeeRequest(String UserName,String firstName,String lastName ,String emailId,String department) throws Exception
	{
			System.out.println("\n In Sales Db connection manager");
			salesdbconnection.saveNewEmployeeRequest( UserName, firstName, lastName , emailId, department);
	}
	
	public void deleteEmployeeRequest(String UserName)  throws Exception
	{
		salesdbconnection.deleteEmployeeRequest(UserName);
	}
	
	public void addNewHrEmployee(SignUpEmployee employee)  throws Exception
	{
		salesdbconnection.addNewSalesEmployee(employee);
	}
	
	public void deleteHrEmployee(String username) throws Exception
	{
		salesdbconnection.deleteSalesEmployee(username);
	}
	
	public void updateDepartmentOfEmployee(String UserName,String department)  throws Exception
	{
		salesdbconnection.updateDepartmentOfEmployee(UserName, department);
	}
	
	public void insertDeleteRequesttoCM (String userName,String department, boolean deleteApprove) throws Exception 
	{
		salesdbconnection.insertDeleteRequesttoCM(userName,department,deleteApprove);
	}
	
	public int getDeleteApprovalStatus (String userName,String department) throws Exception 
	{
		return salesdbconnection.getDeleteApprovalStatus(userName,department);
	}
	
}
