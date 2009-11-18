package com.netpace.vzdn.model;

import java.util.HashSet;
import java.util.Set;

public class VzdnEventNotifications implements java.io.Serializable, Comparable<VzdnEventNotifications> {

	private Integer notificationId;
	private VzdnEmailMessages message;
	private VzdnEvents event;
	private String notificationTitle;
	private String media;
	private String status;
	private String fromAddress;
	private Set<VzdnUsers> optOutRecipients = new HashSet<VzdnUsers>(0);
	private Set<VzdnNotifAdHocRecipients> adHocRecipients = new HashSet<VzdnNotifAdHocRecipients>(0);
	private Set<VzdnSysRoles> roles = new HashSet<VzdnSysRoles>(0);

	/** default constructor */
	public VzdnEventNotifications() {
	}

	public Integer getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public VzdnEmailMessages getMessage() {
		return this.message;
	}

	public void setMessage(VzdnEmailMessages message) {
		this.message = message;
	}

	public VzdnEvents getEvent() {
		return this.event;
	}

	public void setEvent(VzdnEvents event) {
		this.event = event;
	}

	public String getNotificationTitle() {
		return this.notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public String getMedia() {
		return this.media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFromAddress() {
		return this.fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public Set<VzdnUsers> getOptOutRecipients() {
		return this.optOutRecipients;
	}

	public void setOptOutRecipients(Set<VzdnUsers> optOutRecipients) {
		this.optOutRecipients = optOutRecipients;
	}

	public Set<VzdnNotifAdHocRecipients> getAdHocRecipients() {
		return this.adHocRecipients;
	}

	public void setAdHocRecipients(Set<VzdnNotifAdHocRecipients> adHocRecipients) {
		this.adHocRecipients = adHocRecipients;
	}

	public Set<VzdnSysRoles> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<VzdnSysRoles> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((notificationId == null) ? 0 : notificationId.hashCode());
		result = prime
				* result
				+ ((notificationTitle == null) ? 0 : notificationTitle
						.hashCode());
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
		final VzdnEventNotifications other = (VzdnEventNotifications) obj;
		if (notificationId == null) {
			if (other.notificationId != null)
				return false;
		} else if (!notificationId.equals(other.notificationId))
			return false;
		if (notificationTitle == null) {
			if (other.notificationTitle != null)
				return false;
		} else if (!notificationTitle.equals(other.notificationTitle))
			return false;
		return true;
	}
	
	public int compareTo(VzdnEventNotifications compareWith){
		return this.notificationId.compareTo(compareWith.getNotificationId());
	}

}