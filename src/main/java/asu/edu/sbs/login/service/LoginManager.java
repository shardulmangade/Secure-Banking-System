package asu.edu.sbs.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.LoginDBConnectionManager;

@Service
public class LoginManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	private LoginDBConnectionManager loginDBConnection;

	public String getOTP(String username)
	{
//		return loginDBConnection.getOTP(username);
		return "123";
	}
	
	public String getRole(String username)
	{
		return loginDBConnection.getRole(username);
	}
	
	public boolean validateOTP(String username, String inputOTP)
	{
		OneTimePassword storedOTP = loginDBConnection.getOTP(username);
//		if(inputOTP.equals(storedOTP))
			return true;
		
//		return false;
	}

	public int insertNewOTP(String username)
	{
		//Generate new OTP
		OneTimePassword otp = new OneTimePassword();
		
		if(loginDBConnection.updateOTP(username, otp) == SUCCESS)
		{
			//TODO: Send the user an email
			return SUCCESS;
		}

		return FAILURE;
	}
	
	public int resendOTP(String username)
	{
		OneTimePassword storedOTP = loginDBConnection.getOTP(username);
		//TODO: Send the user OTP
		return SUCCESS;
	}
}
