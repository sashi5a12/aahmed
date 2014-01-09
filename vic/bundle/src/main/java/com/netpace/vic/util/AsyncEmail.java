package com.netpace.vic.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netpace.vic.dto.EmailLog;
import com.netpace.vic.service.EmailLogService;

public class AsyncEmail implements Runnable {

	MailConfig config = null;
	EmailLogService emailLogService = null;
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());	
	
	public AsyncEmail(MailConfig config) {
		this.config = config;
		BundleContext bundleContext = FrameworkUtil.getBundle(AsyncEmail.class).getBundleContext();
        ServiceReference factoryRef = bundleContext.getServiceReference(EmailLogService.class.getName());
        emailLogService = (EmailLogService) bundleContext.getService(factoryRef);
 	}
	
	public void run() {
		 
	      Properties properties = System.getProperties();  
	      properties.put("mail.smtp.host", config.getMailServer());
	      properties.put("mail.transport.protocol", "smtp");
	      javax.mail.Session session = javax.mail.Session.getInstance(properties, null);
	       
	      try{  
	    	  	  
	    	 MimeMessage message = new MimeMessage(session);  
	         message.setFrom(new InternetAddress(config.getFrom()));  
	         message.setSubject(config.getSubject());
	         
	         
	         String toAddresses[] = config.getTo().split(",");
	         for (String email : toAddresses) {
	        	 message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	         } 
	         
	         Multipart multipart = new MimeMultipart("alternative");
	         MimeBodyPart textPart = new MimeBodyPart();
	         textPart.setText(config.getPlainContent());
	         MimeBodyPart htmlPart = new MimeBodyPart();
	         htmlPart.setContent(config.getHtmlContent(), "text/html");
	         multipart.addBodyPart(textPart);
	         multipart.addBodyPart(htmlPart);
	         message.setContent(multipart);
	         
             //message.setContent(config.getHtmlContent(), "text/html");
	         
	         // Send message  
	         Transport.send(message);  
	         saveLog(config,false);	  
	      }catch (MessagingException mex) {
	    	 LOGGER.error(mex.toString(),mex);
	    	 saveLog(config,true);
	      }  

	}
	
	private void saveLog(MailConfig mailConfig, boolean exceptionOccured){
		EmailLog emailLog = new EmailLog();
	    emailLog.setStatus(exceptionOccured == true ? "Failed":"Sent");
	    emailLog.setSubject(mailConfig.getSubject());
	    emailLog.setToAddresses(mailConfig.getTo());
	    Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
	    emailLog.setCreatedDate(currentTime);
	    emailLog.setCreatedBy("System");
	    emailLogService.saveEmailLog(emailLog);
	}
	

}
