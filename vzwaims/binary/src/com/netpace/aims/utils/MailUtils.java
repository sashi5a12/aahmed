package com.netpace.aims.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.netpace.aims.utils.ConfigEnvProperties;

public class MailUtils{

    public static boolean propertiesLoaded=false;
    public static String redirectionEmailAddress = "";
    public static String emailRedirection = "";

    /**
     * Sends the Email
     * @param mailTo The TO field addresses, comma separated for multiple recipients
     * @param mailFrom The FROM field of email
     * @param subject The SUBJECT of the email
     * @param cc The CC field of Email, comma separated for multiple recipients
     * @param body The BODY field of email
     * @param boolean true if the email operation was successful
     */
	public static final String EMAIL_EXCEPTION_ADMIN = "vzwtech@netpace.com";
	
    public static boolean sendMailWithHandledExceptions(final String mailTo, final String mailFrom, final String subject, final String cc, final String body)
    {
        boolean returnValue = false;
        try
        {
            returnValue = sendMailMain(mailTo, mailFrom, subject, cc, null, body);
        }
        catch (Exception ex)
        {}

        return returnValue;
    }

    private static boolean sendMailMain(
        final String pMailTo,
        final String mailFrom,
        final String subject,
        final String cc,
        final String bcc,
        final String pBody)
        throws SendFailedException, MessagingException
    {
        try
        {
        	final String mailTo;
        	final String body;

        	//Properties should load one time.
			if (propertiesLoaded==false){
				ConfigEnvProperties conf=ConfigEnvProperties.getInstance();        		
				redirectionEmailAddress=conf.getProperty("mailUtils.redirectionEmailAddress");
				emailRedirection=conf.getProperty("mailUtils.emailRedirection");
				propertiesLoaded=true;
			}
			if (BinaryUtility.isNullOrEmpty(redirectionEmailAddress) || BinaryUtility.isNullOrEmpty(emailRedirection)){
				throw new MessagingException("Email redirection setting is not defined in envProps.properties file.");
			}
        	
        	if("N".equalsIgnoreCase(emailRedirection)){
        		mailTo=pMailTo;
            	body=pBody;
        	}
        	else {
        		body = "--------------------------\nThis is a REDIRECTED Email. This email is intended for: "+pMailTo+"\n--------------------------\n\n"+pBody;
        		mailTo=redirectionEmailAddress;
        	}        	

            try
            {
                Properties properties = System.getProperties();

                properties.put("mail.smtp.host", "mail.netpace.com");
                properties.put("mail.transport.protocol", "smtp");

                javax.mail.Session session = javax.mail.Session.getInstance(properties, null);

                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(mailFrom));

                if (mailTo != null)
                    message.addRecipients(javax.mail.Message.RecipientType.TO, mailTo);
                if (cc != null)
                    message.addRecipients(javax.mail.Message.RecipientType.CC, cc);
                if (bcc != null)
                    message.addRecipients(javax.mail.Message.RecipientType.BCC, bcc);

                message.setSubject(subject);
                message.setSentDate(new Date());
                message.setText(body);
                Transport.send(message);

            }
            catch (Exception ex)
            { 
            	ex.printStackTrace();
            }
            return true;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

}