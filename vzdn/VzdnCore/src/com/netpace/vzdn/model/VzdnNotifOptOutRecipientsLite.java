package com.netpace.vzdn.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class VzdnNotifOptOutRecipientsLite extends BaseValueObject implements Serializable {

    /** identifier field */
    private java.lang.Long notificationId;

    /** identifier field */
    private java.lang.Long userId;

    /** full constructor */
    public VzdnNotifOptOutRecipientsLite(java.lang.Long notificationId, java.lang.Long userId) {
        this.notificationId = notificationId;
        this.userId = userId;
    }

    /** default constructor */
    public VzdnNotifOptOutRecipientsLite() {
    }

    public java.lang.Long getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(java.lang.Long notificationId) {
        this.notificationId = notificationId;
    }

    public java.lang.Long getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.Long userId) {
        this.userId = userId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("notificationId", getNotificationId())
            .append("userId", getUserId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof VzdnNotifOptOutRecipientsLite) ) return false;
        VzdnNotifOptOutRecipientsLite castOther = (VzdnNotifOptOutRecipientsLite) other;
        return new EqualsBuilder()
            .append(this.getNotificationId(), castOther.getNotificationId())
            .append(this.getUserId(), castOther.getUserId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNotificationId())
            .append(getUserId())
            .toHashCode();
    }

}
