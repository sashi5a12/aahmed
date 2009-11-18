package com.netpace.vzdn.model;

//import com.netpace.aims.model.core.AimsUser;
import com.netpace.vzdn.model.VzdnUsers;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class VzdnNotifOptOutRecipients extends BaseValueObject implements Serializable {

    /** nullable persistent field */
    private java.lang.Long notificationId;

    /** nullable persistent field */
    private java.lang.Long userId;

    /** identifier field */
    private VzdnUsers aimsUser;

    /** full constructor */
    public VzdnNotifOptOutRecipients(java.lang.Long notificationId, java.lang.Long userId, VzdnUsers aimsUser) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.aimsUser = aimsUser;
    }

    /** default constructor */
    public VzdnNotifOptOutRecipients() {
    }

    /** minimal constructor */
    public VzdnNotifOptOutRecipients(VzdnUsers aimsUser) {
        this.aimsUser = aimsUser;
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

    public VzdnUsers getAimsUser() {
        return this.aimsUser;
    }

    public void setAimsUser(VzdnUsers aimsUser) {
        this.aimsUser = aimsUser;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("aimsUser", getAimsUser())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof VzdnNotifOptOutRecipients) ) return false;
        VzdnNotifOptOutRecipients castOther = (VzdnNotifOptOutRecipients) other;
        return new EqualsBuilder()
            .append(this.getAimsUser(), castOther.getAimsUser())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAimsUser())
            .toHashCode();
    }

}
