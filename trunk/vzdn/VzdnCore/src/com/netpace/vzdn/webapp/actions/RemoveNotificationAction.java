package com.netpace.vzdn.webapp.actions;

import org.apache.log4j.Logger;

import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.service.NotificationService;

/**
 * @author Ikramullah Khan Action class dealing with Notifications Management
 *          
 */
public class RemoveNotificationAction extends BaseAction {

	private static Logger log = Logger.getLogger(RemoveNotificationAction.class);

	private static final long serialVersionUID = 1;
	private NotificationService notificationService;	
	
	private VzdnEventNotifications notification;
	
	
	public RemoveNotificationAction() {
		
	}
	
	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
}