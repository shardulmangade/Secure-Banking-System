package asu.edu.sbs.it.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.ItDBConnectionManager;
import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.domain.User;
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
	public void deleteEmployeeRequest(String UserName)  throws Exception
	{
			itdbconnection.deleteItPendingRequest(UserName);
	}
	public void insertValidUser(User user, String firstTimePassword, String insertedbyUsername)throws BankStorageException
	{
		logindbconnection.insertValidUser(user, firstTimePassword, insertedbyUsername);		
	}
	public User getUser(String userName)throws BankStorageException
	{
		return logindbconnection.getUser(userName);		
	}	
	
}
