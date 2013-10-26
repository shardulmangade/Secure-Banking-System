package asu.edu.sbs.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class EmailNotificationManager{

	private static final Logger logger = LoggerFactory
			.getLogger(EmailNotificationManager.class);
	
	@Autowired
	private EmailNotificationSender emailSender;
	

//	public void sendAccountDeactivationEmail(IUser user, String adminid) {
//		if(user.getEmail()!= null && !user.getEmail().equals(""))
//		{
//			emailSender.sendNotificationEmail(user.getEmail(), emailMessages.getProperty("email.account_deactivation_subject"), emailMessages.getProperty("email.account_deactivation_msg"));
//			logger.info("The admin "+adminid+" sent a deactivation email to "+user.getUserName());
//		}	
//	}
	

}
