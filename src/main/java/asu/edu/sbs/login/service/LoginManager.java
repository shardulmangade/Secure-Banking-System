package asu.edu.sbs.login.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.LoginDBConnectionManager;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.email.EmailNotificationManager;

@Service
public class LoginManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	private LoginDBConnectionManager loginDBConnection;
	
	@Autowired
	private EmailNotificationManager emailManager;

	public String getRole(String username)
	{
		return loginDBConnection.getRole(username);
	}

	public boolean validateOTP(String username, String inputOTP)
	{
		OneTimePassword storedOTP = loginDBConnection.getOTP(username);
		if(storedOTP.getPassword().equals(inputOTP))
			return true;
		else
			return false;
	}
	public boolean checkForvalidOTP(String username)
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

	public int insertNewOTP(String username)
	{
		//Generate new OTP
		OneTimePassword otp = new OneTimePassword();

		if(loginDBConnection.updateOTP(username, otp) == SUCCESS)
		{
			//TODO: Fetch user email from database
			User user = new User();
			
			user.setEmail("ramkumar007@gmail.com");
			emailManager.sendOTP(user, otp);
							
			return SUCCESS;
		}

		return FAILURE;
	}
	
	public int deleteOTP(String username)
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

	public int resendOTP(String username)
	{
		OneTimePassword storedOTP = loginDBConnection.getOTP(username);
		//TODO: Send the user OTP
		return SUCCESS;
	}
}
