package com.netpace.vzdn.model;

public class VzdnNotifAdHocRecipients implements java.io.Serializable {

	private Integer adHocId;
	private VzdnEventNotifications notification;
	private String emailAddress;

	/** default constructor */
	public VzdnNotifAdHocRecipients() {
	}
	
	public Integer getAdHocId() {
		return adHocId;
	}

	public void setAdHocId(Integer adHocId) {
		this.adHocId = adHocId;
	}

	/** minimal constructor */
	public VzdnNotifAdHocRecipients(VzdnEventNotifications notification) {
		this.notification = notification;
	}

	public VzdnEventNotifications getNotification() {
		return notification;
	}

	public void setNotification(VzdnEventNotifications notification) {
		this.notification = notification;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
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
		final VzdnNotifAdHocRecipients other = (VzdnNotifAdHocRecipients) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		return true;
	}

}