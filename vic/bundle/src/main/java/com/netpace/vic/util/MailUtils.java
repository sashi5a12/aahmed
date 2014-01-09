package com.netpace.vic.util;

import com.netpace.vic.dto.UserApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtils {
        protected final static Logger LOGGER = LoggerFactory.getLogger(MailUtils.class);

	public static void sendEmailToVerizon(UserApplication userApplication){
		MailConfig mailConfig = new MailConfig();
		mailConfig.setTo(VICConstants.VERIZON_MAIL_TO);
		mailConfig.setSubject(VICConstants.VERIZON_EMAIL_SUBJECT);
		String htmlContent = DTOConverter.getHTMLContent(userApplication);
		mailConfig.setHtmlContent(htmlContent);
		String textContent = DTOConverter.getPlainTextContent(userApplication);
		mailConfig.setPlainContent(textContent);       
		Thread asynEmail = new Thread(new AsyncEmail(mailConfig));
		asynEmail.start();
	}
	
	public static void sendEmailToDeveloper(UserApplication userApplication){
		MailConfig mailConfig = new MailConfig();
		mailConfig.setTo(userApplication.getContactEmail());
		mailConfig.setSubject(VICConstants.DEV_EMAIL_SUBJECT);
		mailConfig.setHtmlContent(VICConstants.DEV_EMAIL_CONTENT_HTML);
		mailConfig.setPlainContent(VICConstants.DEV_EMAIL_CONTENT_TEXT);
		Thread asynEmail = new Thread(new AsyncEmail(mailConfig));
		asynEmail.start();
	}
	
}
