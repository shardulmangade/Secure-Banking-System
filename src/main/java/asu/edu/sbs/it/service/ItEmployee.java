package asu.edu.sbs.it.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.ItDBConnectionManager;
import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.domain.IBankRoles;
import asu.edu.sbs.domain.IDepartments;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankAccessException;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class ItEmployee {

	@Autowired
	private ItDBConnectionManager itdbconnection;
	@Autowired
	private LoginDBConnectionManager logindbconnection;
	
	public List<User> getAllPendingUserRequests()
	{
		return itdbconnection.getAllPendingUserRequests();		
	}
	public void deleteItPendingRequest(String UserName)  throws Exception
	{
			itdbconnection.deleteItPendingRequest(UserName);
	}
	public void insertValidUser(User user, String firstTimePassword, String insertedbyUsername)throws BankStorageException, BankAccessException
	{
		logindbconnection.insertValidUser(user, firstTimePassword, insertedbyUsername);		
	}
	public void insertCustomerAccNo(String UserName,String accountNo,Double balance,String createdBy)throws Exception
	{
		itdbconnection.insertCustomerAccNo(UserName, accountNo, balance, createdBy);		
	}
	public User getPendingUserRequest(String userName)throws BankStorageException
	{
		return itdbconnection.getPendingUserRequest(userName);		
	}
	public void deleteUser(String username, String name) throws BankStorageException, BankAccessException 
	{
		// TODO Auto-generated method stub
		itdbconnection.deleteCustomerAccNo(username);
		User duser= logindbconnection.getUser(username);
		logindbconnection.updateUserLoginRights(false, username, name);
	}	
	
}
