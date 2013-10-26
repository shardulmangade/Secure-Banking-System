package asu.edu.sbs.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.User;
import asu.edu.sbs.login.service.OneTimePassword;

@Service
public class EmailNotificationManager{

	private static final Logger logger = LoggerFactory
			.getLogger(EmailNotificationManager.class);
	
	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
	
	@Autowired
	private EmailNotificationSender emailSender;
	

	public int sendOTP(User user, OneTimePassword otp)
	{
		if(user.getEmail() != null && !user.getEmail().equals(""))
		{
			StringBuilder message = new StringBuilder();
			message.append("Dear User,\n\nYour One Time Password is: "+otp.getPassword()+". Expiration Time: "+otp.getExpirationTime());
			emailSender.sendNotificationEmail(user.getEmail(), "SunDevilBank:Your One Time Password", message.toString());
			logger.info("An OTP was sent to "+user.getEmail());
			return SUCCESS;
		}
		return FAILURE;
	}
}
