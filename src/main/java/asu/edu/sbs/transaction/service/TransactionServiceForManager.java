package asu.edu.sbs.transaction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.HrDBConnectionManager;
import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.db.TransactionDBManager;
import asu.edu.sbs.domain.SignUpEmployee;
import asu.edu.sbs.domain.Transaction;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class TransactionServiceForManager {

	@Autowired
	private TransactionDBManager transDBManager;
	
	@Autowired
	private LoginDBConnectionManager loginmanager;
	
	public List<Transaction> getManagerTransactions() throws Exception
	{
		//transactionDBManager = new TransactionDBManager();
		return transDBManager.getTransactionsForManager(); 
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
	
	public void addNewTransEmployee(SignUpEmployee employee)  throws Exception
	{
			transDBManager.addNewTransEmployee(employee);
	}
	
	public void deleteTransEmployee(String username) throws Exception
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
	
	public String getRoleTobechanged(String department, String role) {
		
		return (loginmanager.getRoleTobechanged( department,  role));
	}
	
	public void updateUserRole(String role,String olddepartment, String department, String username,String changedby) throws BankStorageException, BankAccessException {
		// TODO Auto-generated method stub
		loginmanager.updateUserRole(role,olddepartment, department,username ,changedby);
	}
	
	public int insertValidUser(User user, String password, String createdBy) throws BankStorageException, BankAccessException
	{		
			return (loginmanager.insertValidUser(user, password, createdBy));	
	}
	
}
