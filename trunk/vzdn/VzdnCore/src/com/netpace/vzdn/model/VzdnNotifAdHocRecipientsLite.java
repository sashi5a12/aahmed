package com.netpace.vzdn.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class VzdnNotifAdHocRecipientsLite extends BaseValueObject implements Serializable {

    /** identifier field */
    private java.lang.Long notificationId;

    /** identifier field */
    private java.lang.String emailAddress;

    /** full constructor */
    public VzdnNotifAdHocRecipientsLite(java.lang.Long notificationId, java.lang.String emailAddress) {
        this.notificationId = notificationId;
        this.emailAddress = emailAddress;
    }

    /** default constructor */
    public VzdnNotifAdHocRecipientsLite() {
    }

    public java.lang.Long getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(java.lang.Long notificationId) {
        this.notificationId = notificationId;
    }

    public java.lang.String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(java.lang.String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("notificationId", getNotificationId())
            .append("emailAddress", getEmailAddress())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof VzdnNotifAdHocRecipientsLite) ) return false;
        VzdnNotifAdHocRecipientsLite castOther = (VzdnNotifAdHocRecipientsLite) other;
        return new EqualsBuilder()
            .append(this.getNotificationId(), castOther.getNotificationId())
            .append(this.getEmailAddress(), castOther.getEmailAddress())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNotificationId())
            .append(getEmailAddress())
            .toHashCode();
    }

}
