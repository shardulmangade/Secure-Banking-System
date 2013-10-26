package asu.edu.sbs.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Service
public class EmailNotificationSender {

	private static final Logger logger = LoggerFactory
			.getLogger(EmailNotificationSender.class);

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
	
	@Autowired
	private JavaMailSender mailSender;

	public int sendNotificationEmail(String emailaddress, String subject, String msgText) {    	
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(new InternetAddress(emailaddress));
			helper.setSubject(subject);
			
			//Adding tail to the message text
			msgText += "\n\n------------------------------------------------------------------------------------------------------------\nThis message was sent from SunDevilBank. Please don't reply to this message; it's automated and not monitored for responses.";
			
			helper.setText(msgText);
			mailSender.send(message);
			logger.debug("Send email to " + emailaddress + " with subject \"" + subject + "\"");
			return SUCCESS;
		} catch (MessagingException ex) {
			logger.error("Notification email could not be sent.", ex);
		}
		catch(Exception e) {
			logger.error("Error in sending email",e);
		}
		return FAILURE;
	}
}
