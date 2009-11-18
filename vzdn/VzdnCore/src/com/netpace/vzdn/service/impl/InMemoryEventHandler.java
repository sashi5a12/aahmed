package com.netpace.vzdn.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netpace.vzdn.dao.IEventsDao;
import com.netpace.vzdn.dao.INotificationDAO;
import com.netpace.vzdn.dao.IPlaceHoldersDao;
import com.netpace.vzdn.dao.ISysRoleDao;
import com.netpace.vzdn.dao.impl.EventsDAO;
import com.netpace.vzdn.dao.impl.NotificationDAO;
import com.netpace.vzdn.dao.impl.PlaceHoldersDAO;
import com.netpace.vzdn.dao.impl.SysRoleDAO;
import com.netpace.vzdn.model.NotificationEmails;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEventLite;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnPlaceHolders;
import com.netpace.vzdn.service.EmailSelector;
import com.netpace.vzdn.util.MailUtils;
import com.netpace.vzdn.util.VzdnNotificationConstants;

public class InMemoryEventHandler extends BaseEventHandler {
	private IPlaceHoldersDao<VzdnPlaceHolders, Integer> placeHolderDao = new PlaceHoldersDAO();
	private IEventsDao<VzdnEvents, Integer> eventsDao = new EventsDAO();
	private INotificationDAO<VzdnEventNotifications, Integer> notificationsDao = new NotificationDAO();
	private ISysRoleDao roleDao = new SysRoleDAO();	
	private EmailSelectorImpl emailselector = new EmailSelectorImpl(eventsDao,notificationsDao,roleDao);
	
	private static Logger log = Logger.getLogger(InMemoryEventHandler.class);

	public void handleEvent(VzdnEventObject  eventObject) {
		try 
		{	
			List<NotificationEmails> notificationEmails = emailselector.getAciveEmailsForEvent((VzdnEventLite)eventObject.getEvent());			
			List<VzdnPlaceHolders> placeHolders = placeHolderDao.getPlaceHoldersOnEventId(((VzdnEventLite)eventObject.getEvent()).getEventId().intValue());
			log.debug("Starting sending emails ::");
			for (NotificationEmails notifEmail : notificationEmails) 
			{
				log.debug("Sending Emails to all users in notification" + notifEmail.getNotification().getNotificationTitle());
				VzdnEmailMessages emailMessage = notifEmail.getEmailMessage();
				String message_title = emailMessage.getEmailTitle();
				String message_body = emailMessage.getEmailText();
				
				message_body = replacePlaceHolders(message_body, placeHolders, eventObject);
				
				String message_id = emailMessage.getEmailMessageId().toString();
				Collection attachments = new ArrayList();
				attachments = GenericEventHandler.getAttachments(new Long(new Integer(message_id).toString()));
				
				System.out.println("ApplicationEventHandler MessageTitle : " + message_title);
				System.out.println("ApplicationEventHandler MessageText : "	+ message_body);				

				List<String> emails = notifEmail.getEmails();
				
				int no = 1;
				for (String email_address : emails)
				{
					System.out.println("\n\n\n AN  EMAIL  NEEDS  TO  BE  SENT !!!\n\n\n");
					System.out.println("\n\n\n "+ no++ + ". Sending Email to : " + email_address + " !!!\n\n\n");
					log.debug("Sending email to address:" + email_address);
					try 
					{
						MailUtils.sendMail(email_address,VzdnNotificationConstants.FROM_ADDRESS,message_title, null, message_body, attachments);
					} 
					catch (SendFailedException sfe) {
						log.error("Send Failed Exception Exception Raised :: " + sfe.getMessage());
						log.error(sfe.getStackTrace());
						System.out.println("SendFailedException occured in ApplicationEventHandler ");						
						sfe.printStackTrace();
					} catch (MessagingException me) {
						log.debug("Messaging Exception Raised :: " + me.getMessage());
						log.error(me.getStackTrace());
						System.out.println("MessagingException occured in ApplicationEventHandler ");
						me.printStackTrace();						
					}
				}
			}
			log.debug("Email Sending Finished ::");
		} catch (Exception exp) {
			log.error(exp);
			exp.printStackTrace();
		}
	}
	
	private String replacePlaceHolders(String emailBody, List<VzdnPlaceHolders> eventPlaceHolders, VzdnEventObject  eventObject){		
		for(VzdnPlaceHolders placeHolder : eventPlaceHolders){
			String replace = placeHolder.getPlaceHolderDisplayName() ;
			String with = (String)eventObject.getProperty(replace);
			String withCurly = "{" + replace + "}";
			emailBody = StringUtils.replace(emailBody,withCurly, with);
		}
		return emailBody;
		
	}
}
