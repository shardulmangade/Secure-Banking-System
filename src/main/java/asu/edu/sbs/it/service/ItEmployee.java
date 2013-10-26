package asu.edu.sbs.it.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.ItDBConnectionManager;
import asu.edu.sbs.domain.User;

@Service
public class ItEmployee {

	@Autowired
	private ItDBConnectionManager itdbconnection;
	
	public List<User> getAllPendingUserRequests()
	{
		return itdbconnection.getAllPendingUserRequests();		
	}
	public void deleteEmployeeRequest(String UserName)  throws Exception
	{
			itdbconnection.deleteItPendingRequest(UserName);
	}
}
