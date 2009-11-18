package com.netpace.vzdn.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEmailMessagesAtt;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnNotifAdHocRecipients;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.util.UniqueConstraintException;

/**
 * @author Ikramullah Khan
 * Service interface related to Notifications
 */
public interface NotificationService {
	
	public List<VzdnEventNotifications> search(VzdnEventNotifications criteria);
	
	public List<VzdnEventNotifications> getAllNotifications();
	
	public void createNewNotification(VzdnEventNotifications notification);

	public void updateNotification(VzdnEventNotifications notification);
	
	public void removeNotification(VzdnEventNotifications notification) throws Exception;

	public VzdnEventNotifications getNotificationById(Integer notificationId);

	/*public void saveEventNotification(VzdnEmailMessages notificationForm, Integer eventId, int[] roleIds, String csvEmails, VzdnUsers aimsUser) 
	throws UniqueConstraintException, HibernateException, Exception;*/

	
	public void saveEventNotification(VzdnEmailMessages notificationForm, Integer eventId, int[] roleIds, String csvEmails, VzdnUsers aimsUser, VzdnEmailMessagesAtt emailAttachment) 
	throws UniqueConstraintException, HibernateException, Exception;
	
	public void updateEventNotification(VzdnEmailMessages notificationForm, Integer eventId, Integer notificationId, int[] roleIds, String csvEmails, VzdnUsers aimsUser, VzdnEmailMessagesAtt emailAttachment)
	throws UniqueConstraintException, HibernateException, Exception;
	
	public List<Integer> getRolesOnNotifId(Integer id) throws Exception;
	public String getAdHocRecipientsOnNotifId(Integer id) throws Exception;
	public VzdnEvents getEventOnNotificationId(Integer id) throws Exception;
	
	public VzdnEmailMessages getEmailMessdageOnNotificationId(Integer notificationId) throws Exception;
	public VzdnEventNotifications getNotificationByEmailId(Integer emailId) throws Exception;
	
	public List<VzdnEmailMessages> getEmailsMessagesOnNotificationList(List<VzdnEventNotifications> notifications)throws Exception;
	public VzdnEmailMessages getEmailMessageOnNotification(VzdnEventNotifications notification) throws Exception;	
	
	public void removeAttachmentOnEmailId(Integer emailId) throws Exception;
	
	
}
