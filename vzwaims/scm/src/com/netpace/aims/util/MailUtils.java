package com.netpace.aims.util;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

    public static boolean sendMailWithHandledExceptions(final String mailTo, final String mailFrom, final String subject, final String cc, final String body)
    {
        boolean returnValue = false;
        try
        {
            returnValue = sendMailMain(mailTo, mailFrom, subject, cc, null, body, null);
        }
        catch (Exception ex)
        {}

        return returnValue;
    }

    private static boolean sendMailMain(
        final String mailTo,
        final String mailFrom,
        final String subject,
        final String cc,
        final String bcc,
        final String body,
        final Collection attachments)
        throws SendFailedException, MessagingException
    {
        try
        {
            InputStream inputStream = null;

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
                    //Not handling attachments for now
                }

                Transport.send(message);

            }
            catch (Exception ex)
            {}
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

            return true;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

}