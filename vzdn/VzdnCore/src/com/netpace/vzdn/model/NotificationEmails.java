package com.netpace.vzdn.model;

import java.util.List;

public class NotificationEmails {
	private VzdnEventNotifications notification;
	private List<String> emails;
	private VzdnEmailMessages emailMessage;
	
	public VzdnEmailMessages getEmailMessage() {
		return emailMessage;
	}
	public void setEmailMessage(VzdnEmailMessages emailMessage) {
		this.emailMessage = emailMessage;
	}
	public VzdnEventNotifications getNotification() {
		return notification;
	}
	public void setNotification(VzdnEventNotifications notification) {
		this.notification = notification;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
}
