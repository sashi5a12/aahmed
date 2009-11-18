package com.netpace.vzdn.model;

public class MyNotifications implements Comparable<MyNotifications>{
	private VzdnEventNotifications notification;
	private Boolean isOptOut;
	
	public MyNotifications(VzdnEventNotifications notification, Boolean isOptOut){
		this.notification = notification;
		this.isOptOut = isOptOut;
	}
	
	public MyNotifications(){}

	public VzdnEventNotifications getNotification() {
		return notification;
	}

	public void setNotification(VzdnEventNotifications notification) {
		this.notification = notification;
	}

	public Boolean getIsOptOut() {
		return isOptOut;
	}

	public void setIsOptOut(Boolean isOptOut) {
		this.isOptOut = isOptOut;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((isOptOut == null) ? 0 : isOptOut.hashCode());
		result = prime * result
				+ ((notification == null) ? 0 : notification.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MyNotifications other = (MyNotifications) obj;
		if (isOptOut == null) {
			if (other.isOptOut != null)
				return false;
		} else if (!isOptOut.equals(other.isOptOut))
			return false;
		if (notification == null) {
			if (other.notification != null)
				return false;
		} else if (!notification.equals(other.notification))
			return false;
		return true;
	}
	
	public int compareTo(MyNotifications myNotification) {	    
		return this.notification.compareTo(myNotification.getNotification());
	}
	
}
