package asu.edu.sbs.transaction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.HrDBConnectionManager;
import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.db.TransactionDBManager;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.Transaction;
import asu.edu.sbs.domain.User;

@Service
public class TransactionServiceForManager {

	@Autowired
	private TransactionDBManager transDBManager;
	
	
	
	public List<Transaction> getManagerTransactions() throws Exception
	{
		//transactionDBManager = new TransactionDBManager();
		return transDBManager.getRegEmpTransactions(); 
	}
	
	public void  saveNewEmployeeRequest(String UserName,String firstName,String lastName ,String emailId,String department) throws Exception
	{
			System.out.println("\n In Transaction DB connection manager");
			transDBManager.saveNewEmployeeRequest( UserName, firstName, lastName , emailId, department);
	}
	
	public void deleteEmployeeRequest(String UserName)  throws Exception
	{
			transDBManager.deleteEmployeeRequest(UserName);
	}
	
	public void addNewHrEmployee(SignUpEmployee employee)  throws Exception
	{
			transDBManager.addNewTransEmployee(employee);
	}
	
	public void deleteHrEmployee(String username) throws Exception
	{
			transDBManager.deleteTransEmployee(username);
	}
	
	public void updateDepartmentOfEmployee(String UserName,String department)  throws Exception
	{
		transDBManager.updateDepartmentOfEmployee(UserName, department);
	}
	
	public void insertDeleteRequesttoCM (String userName,String department, boolean deleteApprove) throws Exception 
	{
		transDBManager.insertDeleteRequesttoCM(userName,department,deleteApprove);
	}
	
	public int getDeleteApprovalStatus (String userName,String department) throws Exception 
	{
		return transDBManager.getDeleteApprovalStatus(userName,department);
	}
	
}
