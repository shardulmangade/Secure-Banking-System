package asu.edu.sbs.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.SignUpEmployee;
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
			message.append("Dear User,\n\nYour One Time Password is: \""+otp.getPassword()+"\"(without the quotes). Expiration Time: "+otp.getExpirationTime() +" and its valid for only one login.");

			if(emailSender.sendNotificationEmail(user.getEmail(), "SunDevilBank:Your One Time Password", message.toString()) == SUCCESS)
			{
				logger.info("An OTP was sent to "+user.getEmail());
				return SUCCESS;
			}
		}
		return FAILURE;
	}
	
	public int sendPassword(SignUpEmployee user, String password)
	{
		if(user.getEmailId() != null && !user.getEmailId().equals(""))
		{
			StringBuilder message = new StringBuilder();
			message.append("Dear User,\n\nYour temporary password is: "+   password +".\n Please change the password once you log in\n");
			emailSender.sendNotificationEmail(user.getEmailId(), "SunDevilBank:Your temporary Password", message.toString());
			logger.info("Temporary password was sent to "+user.getEmailId());
			return SUCCESS;
		}
		return FAILURE;
	}
}
