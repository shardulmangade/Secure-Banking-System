package asu.edu.sbs.login.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.email.EmailNotificationManager;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class LoginManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	private LoginDBConnectionManager loginDBConnection;

	@Autowired
	private EmailNotificationManager emailManager;

	public String getRole(String username) throws BankStorageException
	{
		return loginDBConnection.getRole(username);
	}

	public boolean validateOTP(String username, String inputOTP) throws BankStorageException
	{
		OneTimePassword storedOTP = loginDBConnection.getOTP(username);
		if(storedOTP.getPassword().equals(inputOTP))
			return true;
		else
			return false;
	}
	public boolean checkForvalidOTP(String username) throws BankStorageException
	{
		OneTimePassword storedOTP = loginDBConnection.getOTP(username);
		if(storedOTP != null)
		{
			Date nowTime = new Date();
			long difference = (nowTime.getTime() - storedOTP.getExpirationTime().getTime())/(1000);

			if(difference < 0)
				return true;
		}
		return false;
	}

	public int insertNewOTP(String username) throws BankStorageException
	{
		//Generate new OTP
		OneTimePassword otp = new OneTimePassword();

		if(loginDBConnection.updateOTP(username, otp) == SUCCESS)
		{
			//Fetch user from database
			User user = loginDBConnection.getUser(username);

			//Send OTP to user
			if(user != null)
				emailManager.sendOTP(user, otp);

			return SUCCESS;
		}

		return FAILURE;
	}

	public int deleteOTP(String username) throws BankStorageException
	{
		//Generate new OTP
		OneTimePassword otp = new OneTimePassword();
		otp.setExpirationTime(null);
		otp.setPassword(null);

		if(loginDBConnection.updateOTP(username, otp) == SUCCESS)
		{
			return SUCCESS;
		}

		return FAILURE;
	}

	
	public int changePassword(String username, String newPassword) throws BankStorageException
	{
		Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		String hashedPassword = passwordEncoder.encodePassword(newPassword, null);
		return loginDBConnection.changePassword(username, hashedPassword);
	}
	
//	public int resendOTP(String username) throws BankStorageException
//	{
//		OneTimePassword storedOTP = loginDBConnection.getOTP(username);
//		//Fetch user from database
//		User user = loginDBConnection.getUser(username);
//
//		//Send OTP to user
//		if(user != null)
//		{
//			return emailManager.sendOTP(user, storedOTP); 
//		}
//		
//		return FAILURE;
//	}
}
