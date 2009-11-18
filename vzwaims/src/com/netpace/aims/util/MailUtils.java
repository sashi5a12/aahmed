package com.netpace.aims.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import oracle.sql.CLOB;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsEmailExceptionLog;

import com.netpace.aims.util.StringFuncs;

public class MailUtils
{
    /**
     * Sends the Email
     * @param mailTo The TO field addresses, comma separated for multiple recipients
     * @param mailFrom The FROM field of email
     * @param subject The SUBJECT of the email
     * @param cc The CC field of Email, comma separated for multiple recipients
     * @param body The BODY field of email
     * @param boolean true if the email operation was successful
     */
    
    public static boolean propertiesLoaded=false;
    public static String redirectionEmailAddress = "";
    public static String emailRedirection = "";

    public static boolean sendMail(final String mailTo, final String mailFrom, final String subject, final String cc, final String body)
        throws SendFailedException, MessagingException
    {
        return sendMailMain(mailTo, mailFrom, subject, cc, null, body, null);
    }

    public static boolean sendMail(final String mailTo, final String mailFrom, final String subject, final String cc, final String bcc, final String body)
        throws SendFailedException, MessagingException
    {
        return sendMailMain(mailTo, mailFrom, subject, cc, bcc, body, null);
    }

    public static boolean sendMail(
        final String mailTo,
        final String mailFrom,
        final String subject,
        final String cc,
        final String body,
        final Collection attachments)
        throws SendFailedException, MessagingException
    {
        return sendMailMain(mailTo, mailFrom, subject, cc, null, body, attachments);
    }

    private static boolean sendMailMain(
        final String pMailTo,
        final String mailFrom,
        final String subject,
        final String cc,
        final String bcc,
        final String pBody,
        final Collection attachments)
        throws SendFailedException, MessagingException
    {
        try
        {
        	final String mailTo;
        	final String body;

        	//Properties should load one time.
        	try {
				if (propertiesLoaded==false){
					ConfigEnvProperties conf=ConfigEnvProperties.getInstance();        		
					redirectionEmailAddress=conf.getProperty("mailUtils.redirectionEmailAddress");
					emailRedirection=conf.getProperty("mailUtils.emailRedirection");
					propertiesLoaded=true;
				}
				if (StringFuncs.isNullOrEmpty(redirectionEmailAddress) || StringFuncs.isNullOrEmpty(emailRedirection)){
					throw new MessagingException("Email redirection setting is not defined in envProps.properties file.");
				}
			}
        	catch (Exception e) {
				MailUtils.logException(pMailTo, mailFrom, subject, cc, bcc, pBody, attachments, e.getMessage(), MiscUtils.getExceptionStackTraceInfo(e));        		
				throw new MessagingException();
			}
        	
        	if("N".equalsIgnoreCase(emailRedirection)){
        		mailTo=pMailTo;
            	body=pBody;
        	}
        	else {
        		body = "--------------------------\nThis is a REDIRECTED Email. This email is intended for: "+pMailTo+"\n--------------------------\n\n"+pBody;
        		mailTo=redirectionEmailAddress;
        	}
        	
            new Thread()
            {
                Object[] resourceValues = null;
                InputStream inputStream = null;

                public void run()
                {
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

                        if ((attachments == null) || (attachments.size() < 1))
                        {
                            message.setText(body);
                        }
                        else
                        {
                            Multipart multipart = new MimeMultipart();
                            MimeBodyPart bodyPart = new MimeBodyPart();
                            bodyPart.setText(body);
                            multipart.addBodyPart(bodyPart);
                            for (Iterator iter = attachments.iterator(); iter.hasNext();)
                            {
                                resourceValues = (Object[]) iter.next();
                                inputStream = ((Blob) resourceValues[0]).getBinaryStream();
                                int lengthOfFile = (int) (((Blob) resourceValues[0]).length());
                                byte[] bytes = new byte[(int) lengthOfFile];

                                // Read in the bytes
                                int offset = 0;
                                int numRead = 0;
                                while (offset < bytes.length && (numRead = inputStream.read(bytes, offset, bytes.length - offset)) >= 0)
                                    offset += numRead;

                                // Ensure all the bytes have been read in
                                if (offset < bytes.length)
                                {
                                    throw new IOException("Could not completely read file " + (String) resourceValues[1]);
                                }

                                MimeBodyPart filePart = new MimeBodyPart();
                                filePart.setDataHandler(
                                    new DataHandler(new ByteArrayDataSource(bytes, (String) resourceValues[1], (String) resourceValues[2])));
                                filePart.setFileName((String) resourceValues[1]);
                                multipart.addBodyPart(filePart);
                            }
                            message.setContent(multipart);
                        }

                        Transport.send(message);

                    }
                    catch (Exception ex)
                    {
                        logException(mailTo, mailFrom, subject, cc, bcc, body, attachments, ex.getMessage(), MiscUtils.getExceptionStackTraceInfo(ex));
                    }
                    finally
                    {
                        try
                        {
                            if (inputStream != null)
                                inputStream.close();
                        }
                        catch (Exception eex)
                        {
                            System.out.println("COULD NOT Input Stream Closed in try");
                        }
                    }
                }
            }
            .start();

            return true;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

    private static void logException(
        final String mailTo,
        final String mailFrom,
        final String subject,
        final String cc,
        final String bcc,
        final String body,
        final Collection attachments,
        final String exceptionMessage,
        final String exceptionTrace)
    {
        Session session = null;
        Transaction tx = null;
        Long attachmentSize = new Long(0);
        long bytesWrote = 0;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            if (attachments != null)
                attachmentSize = new Long(attachments.size());

            AimsEmailExceptionLog exceptionLog = new AimsEmailExceptionLog();
            exceptionLog.setEmailFrom(mailFrom);
            exceptionLog.setEmailSubject(subject);
            exceptionLog.setEmailAttachments(attachmentSize);
            exceptionLog.setCreatedDate(new Date());
            exceptionLog.setEmailTo(Hibernate.createClob(" "));
            exceptionLog.setEmailCc(Hibernate.createClob(" "));
            exceptionLog.setEmailBcc(Hibernate.createClob(" "));
            exceptionLog.setEmailBody(Hibernate.createClob(" "));
            exceptionLog.setExceptionMessage(Hibernate.createClob(" "));
            exceptionLog.setExceptionTrace(Hibernate.createClob(" "));

            session.saveOrUpdate(exceptionLog);
            session.flush();
            session.refresh(exceptionLog, LockMode.UPGRADE);

            //Start of updating CLOBS
            bytesWrote =
                LobUtils.writeToOraClob((CLOB) exceptionLog.getEmailTo(), new ByteArrayInputStream(StringFuncs.NullValueReplacement(mailTo).getBytes()));
            bytesWrote = LobUtils.writeToOraClob((CLOB) exceptionLog.getEmailCc(), new ByteArrayInputStream(StringFuncs.NullValueReplacement(cc).getBytes()));
            bytesWrote = LobUtils.writeToOraClob((CLOB) exceptionLog.getEmailBcc(), new ByteArrayInputStream(StringFuncs.NullValueReplacement(bcc).getBytes()));
            bytesWrote =
                LobUtils.writeToOraClob((CLOB) exceptionLog.getEmailBody(), new ByteArrayInputStream(StringFuncs.NullValueReplacement(body).getBytes()));
            bytesWrote =
                LobUtils.writeToOraClob(
                    (CLOB) exceptionLog.getExceptionMessage(),
                    new ByteArrayInputStream(StringFuncs.NullValueReplacement(exceptionMessage).getBytes()));
            bytesWrote =
                LobUtils.writeToOraClob(
                    (CLOB) exceptionLog.getExceptionTrace(),
                    new ByteArrayInputStream(StringFuncs.NullValueReplacement(exceptionTrace).getBytes()));
            //End of updating CLOBS
            
            session.flush();
            tx.commit();
        }

        catch (Exception ex)
        {
            System.out.println("Exception in logException");
            try
            {
                if (tx != null)
                    tx.rollback();
            }
            catch (Exception ignore)
            {}
            ex.printStackTrace();
        }

        finally
        {
            try
            {
                session.close();
            }
            catch (Exception ignore)
            {}
        }

    }

}