package asu.edu.sbs.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import asu.edu.sbs.domain.User;
import asu.edu.sbs.login.service.OneTimePassword;

@Service
public class EmailNotificationManager{

	private static final Logger logger = LoggerFactory
			.getLogger(EmailNotificationManager.class);

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

//	@Autowired
//	private EmailNotificationSender emailSender;
	
	@Autowired
	private JavaMailSender mailSender;


	public int sendOTP(User user, OneTimePassword otp)
	{
		if(user.getEmail() != null && !user.getEmail().equals(""))
		{
			String name = "User";
			if(user.getUsername() != null)
				name = user.getUsername();
			
			StringBuilder message = new StringBuilder();
			message.append("Dear "+name+",\n\nYour One Time Password is: \""+otp.getPassword()+"\"(without the quotes). Expiration Time: "+otp.getExpirationTime() +" and its valid for only one login.");

			EmailNotificationSender emailSender = new EmailNotificationSender(mailSender, user.getEmail(), "SunDevilBank:Your One Time Password", message.toString());
			Thread emailThread = new Thread(emailSender);
			emailThread.start();
			
			logger.info("An OTP was sent to "+user.getEmail());

		}
		return SUCCESS;
	}

	public int sendPassword(User user, String password)
	{
		if(user.getEmail() != null && !user.getEmail().equals(""))
		{
			String name = "User";
			if(user.getUsername() != null)
				name = user.getUsername();
			
			StringBuilder message = new StringBuilder();
			message.append("Dear "+name+"\n\nYour temporary password is: \""+   password +"\"(without the quotes).\n Please change the password once you log in\n");
			
//			emailSender.sendNotificationEmail(user.getEmail(), "SunDevilBank:Your temporary Password", message.toString());
			EmailNotificationSender emailSender = new EmailNotificationSender(mailSender, user.getEmail(), "SunDevilBank:Your temporary Password", message.toString());
			Thread emailThread = new Thread(emailSender);
			emailThread.start();
			
			logger.info("Temporary password was sent to "+user.getEmail());
	
		}
		return SUCCESS;
	}

	public int sendPasswordCustomer(User user, String password)
	{
		if(user.getEmail() != null && !user.getEmail().equals(""))
		{
			String name = "User";
			if(user.getUsername() != null)
				name = user.getUsername();
			
			StringBuilder message = new StringBuilder();
			message.append("Dear "+name+",\n\nYour temporary password is: \""+   password +"\"(without the quotes).\n Please change the password once you log in\n");
			
//			emailSender.sendNotificationEmail(user.getEmail(), "SunDevilBank:Your temporary Password", message.toString());
			EmailNotificationSender emailSender = new EmailNotificationSender(mailSender, user.getEmail(), "SunDevilBank:Your temporary Password", message.toString());
			Thread emailThread = new Thread(emailSender);
			emailThread.start();
			logger.info("Temporary password was sent to "+user.getEmail());
			
		}
		return SUCCESS;
	}

	//	public int sendOTP(User user, OneTimePassword otp)
	//	{
	//		if(user.getEmail() != null && !user.getEmail().equals(""))
	//		{
	//			StringBuilder message = new StringBuilder();
	//			message.append("Dear User,\n\nYour One Time Password is: \""+otp.getPassword()+"\"(without the quotes). Expiration Time: "+otp.getExpirationTime() +" and its valid for only one login.");
	//
	//			if(emailSender.sendNotificationEmail(user.getEmail(), "SunDevilBank:Your One Time Password", message.toString()) == SUCCESS)
	//			{
	//				logger.info("An OTP was sent to "+user.getEmail());
	//				return SUCCESS;
	//			}
	//		}
	//		return FAILURE;
	//	}
	//	
	//	public int sendPassword(User user, String password)
	//	{
	//		if(user.getEmail() != null && !user.getEmail().equals(""))
	//		{
	//			StringBuilder message = new StringBuilder();
	//			message.append("Dear User,\n\nYour temporary password is: "+   password +".\n Please change the password once you log in\n");
	//			emailSender.sendNotificationEmail(user.getEmail(), "SunDevilBank:Your temporary Password", message.toString());
	//			logger.info("Temporary password was sent to "+user.getEmail());
	//			return SUCCESS;
	//		}
	//		return FAILURE;
	//	}
	//	
	//	public int sendPasswordCustomer(User user, String password)
	//	{
	//		if(user.getEmail() != null && !user.getEmail().equals(""))
	//		{
	//			StringBuilder message = new StringBuilder();
	//			message.append("Dear User,\n\nYour temporary password is: "+   password +".\n Please change the password once you log in\n");
	//			emailSender.sendNotificationEmail(user.getEmail(), "SunDevilBank:Your temporary Password", message.toString());
	//			logger.info("Temporary password was sent to "+user.getEmail());
	//			return SUCCESS;
	//		}
	//		return FAILURE;
	//	}
}
