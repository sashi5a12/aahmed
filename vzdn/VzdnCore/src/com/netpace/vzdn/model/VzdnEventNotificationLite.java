package com.netpace.vzdn.model;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class VzdnEventNotificationLite extends BaseValueObject implements Serializable {

    /** identifier field */
    private java.lang.Long notificationId;

    /** nullable persistent field */
    private java.lang.String notificationTitle;

    /** nullable persistent field */
    private java.lang.String media;

    /** nullable persistent field */
    private java.lang.String status;

    /** nullable persistent field */
    private java.lang.String fromAddress;

    /** nullable persistent field */
    private java.lang.String includeVzwAccountManager;

    /** nullable persistent field */
    private java.lang.String includeVzwAppsContact;

    /** nullable persistent field */
    private java.lang.Long eventId;

    /** nullable persistent field */
    private java.lang.Long messageId;

    /** persistent field */
    private Set notificationRoles;

    /** persistent field */
    private Set notifAdHocRecipients;

    /** full constructor */
    public VzdnEventNotificationLite(java.lang.String notificationTitle, java.lang.String media, java.lang.String status, java.lang.String fromAddress, java.lang.String includeVzwAccountManager, java.lang.String includeVzwAppsContact, java.lang.Long eventId, java.lang.Long messageId, Set notificationRoles, Set notifAdHocRecipients) {
        this.notificationTitle = notificationTitle;
        this.media = media;
        this.status = status;
        this.fromAddress = fromAddress;
        this.includeVzwAccountManager = includeVzwAccountManager;
        this.includeVzwAppsContact = includeVzwAppsContact;
        this.eventId = eventId;
        this.messageId = messageId;
        this.notificationRoles = notificationRoles;
        this.notifAdHocRecipients = notifAdHocRecipients;
    }

    /** default constructor */
    public VzdnEventNotificationLite() {
    }

    /** minimal constructor */
    public VzdnEventNotificationLite(Set notificationRoles, Set notifAdHocRecipients) {
        this.notificationRoles = notificationRoles;
        this.notifAdHocRecipients = notifAdHocRecipients;
    }

    public java.lang.Long getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(java.lang.Long notificationId) {
        this.notificationId = notificationId;
    }

    public java.lang.String getNotificationTitle() {
        return this.notificationTitle;
    }

    public void setNotificationTitle(java.lang.String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public java.lang.String getMedia() {
        return this.media;
    }

    public void setMedia(java.lang.String media) {
        this.media = media;
    }

    public java.lang.String getStatus() {
        return this.status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    public java.lang.String getFromAddress() {
        return this.fromAddress;
    }

    public void setFromAddress(java.lang.String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public java.lang.String getIncludeVzwAccountManager() {
        return this.includeVzwAccountManager;
    }

    public void setIncludeVzwAccountManager(java.lang.String includeVzwAccountManager) {
        this.includeVzwAccountManager = includeVzwAccountManager;
    }

    public java.lang.String getIncludeVzwAppsContact() {
        return this.includeVzwAppsContact;
    }

    public void setIncludeVzwAppsContact(java.lang.String includeVzwAppsContact) {
        this.includeVzwAppsContact = includeVzwAppsContact;
    }

    public java.lang.Long getEventId() {
        return this.eventId;
    }

    public void setEventId(java.lang.Long eventId) {
        this.eventId = eventId;
    }

    public java.lang.Long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(java.lang.Long messageId) {
        this.messageId = messageId;
    }

    public java.util.Set getNotificationRoles() {
        return this.notificationRoles;
    }

    public void setNotificationRoles(java.util.Set notificationRoles) {
        this.notificationRoles = notificationRoles;
    }

    public java.util.Set getNotifAdHocRecipients() {
        return this.notifAdHocRecipients;
    }

    public void setNotifAdHocRecipients(java.util.Set notifAdHocRecipients) {
        this.notifAdHocRecipients = notifAdHocRecipients;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("notificationId", getNotificationId())
            .append("notificationTitle", getNotificationTitle())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof VzdnEventNotificationLite) ) return false;
        VzdnEventNotificationLite castOther = (VzdnEventNotificationLite) other;
        return new EqualsBuilder()
            .append(this.getNotificationId(), castOther.getNotificationId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNotificationId())
            .toHashCode();
    }

}
