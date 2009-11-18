package com.netpace.vzdn.webapp.actions;

import java.util.Hashtable;
import org.apache.log4j.Logger;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.security.VzdnSecurityException;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.EventsService;
import com.netpace.vzdn.service.ISysRoleService;
import com.netpace.vzdn.service.NotificationService;
import com.netpace.vzdn.service.PlaceHolderService;
import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;

/**
 * @author Ikramullah Khan Action class dealing with Notifications Management
 *          
 */
public class DeleteNotificationAction extends BaseAction{

	
	
	private static Logger log = Logger.getLogger(DeleteNotificationAction.class);

	private static final long serialVersionUID = 1;
	private NotificationService notificationService;	
	private ISysRoleService roleService;
	private EventsService eventService;
	private PlaceHolderService palceHodlerService;
	private VzdnEventNotifications notification;
	
	public DeleteNotificationAction() {
		
	}
	
	public String deleteNotification()  throws VzdnSecurityException, NotLoggedInException{	
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
	
		try
		{
			
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_NOTIFICATIONS);
			
			Integer emailId = Integer.valueOf(getServletRequest().getParameter("id"));
			notification = notificationService.getNotificationByEmailId(emailId);
			//notification = notificationService.getNotificationById(id);
			notificationService.removeNotification(notification);
			getSession().setAttribute("successMessage", getText("notifications.delete.successmsg"));
			return SUCCESS;
		}
		
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)		
			log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
		
			se.printStackTrace();
			throw se;
		}
		
		catch(Exception exp){
			log.error("Some problem removing notification \n" + exp);
			return INPUT;
		}
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public ISysRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(ISysRoleService roleService) {
		this.roleService = roleService;
	}

	public EventsService getEventService() {
		return eventService;
	}

	public void setEventService(EventsService eventService) {
		this.eventService = eventService;
	}

	public PlaceHolderService getPalceHodlerService() {
		return palceHodlerService;
	}

	public void setPalceHodlerService(PlaceHolderService palceHodlerService) {
		this.palceHodlerService = palceHodlerService;
	}

	public VzdnEventNotifications getNotification() {
		return notification;
	}

	public void setNotification(VzdnEventNotifications notification) {
		this.notification = notification;
	}
	
	
	
	
}