package asu.edu.sbs.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class SalesDeptManager {

	@Autowired
	private SalesDBConnectionManager salesdbconnection;
	@Autowired
	private LoginDBConnectionManager loginmanager;
		
	public void  saveNewEmployeeRequest(String UserName,String firstName,String lastName ,String emailId,String department) throws Exception
	{
			System.out.println("\n In SalesDb connection manager");
			salesdbconnection.saveNewEmployeeRequest( UserName, firstName, lastName , emailId, department);
	}
	
	public void  saveNewMerchantRequest(String UserName,String MerchantName ,String emailId,String department) throws Exception
	{
			System.out.println("\n In SalesDb merchant connection manager");
			salesdbconnection.saveNewMerchantRequest( UserName, MerchantName , emailId, department);
	}
	
	public int insertValidUser(User user, String password, String createdBy) throws BankStorageException
	{		
			return (loginmanager.insertValidUser(user, password, createdBy));	
	}
	
	public void deleteEmployeeRequest(String UserName)  throws Exception
	{
		salesdbconnection.deleteEmployeeRequest(UserName);
	}
	
	public void addNewSalesEmployee(SignUpEmployee employee)  throws Exception
	{
		salesdbconnection.addNewSalesEmployee(employee);
	}
	
	public void deleteSalesEmployee(String username) throws Exception
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
