package com.netpace.vzdn.service;

import java.util.List;


import com.netpace.vzdn.model.NotificationEmails;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEventLite;

public interface EmailSelector {
	public List<NotificationEmails> getAciveEmailsForEvent(VzdnEventLite event)throws Exception;	
	public List<String> getActiveEmailsForNotification(VzdnEventNotifications notification)throws Exception;	
	public List<String> getAdhocEmailsForNotification(VzdnEventNotifications notification)throws Exception;	
	public List<String> getRegularEmailsForNotification(VzdnEventNotifications notification)throws Exception;	
}
