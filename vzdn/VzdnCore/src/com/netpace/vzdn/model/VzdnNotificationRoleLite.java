package com.netpace.vzdn.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class VzdnNotificationRoleLite extends BaseValueObject implements Serializable {

    /** identifier field */
    private java.lang.Long notificationId;

    /** identifier field */
    private java.lang.Long roleId;

    /** full constructor */
    public VzdnNotificationRoleLite(java.lang.Long notificationId, java.lang.Long roleId) {
        this.notificationId = notificationId;
        this.roleId = roleId;
    }

    /** default constructor */
    public VzdnNotificationRoleLite() {
    }

    public java.lang.Long getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(java.lang.Long notificationId) {
        this.notificationId = notificationId;
    }

    public java.lang.Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(java.lang.Long roleId) {
        this.roleId = roleId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("notificationId", getNotificationId())
            .append("roleId", getRoleId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof VzdnNotificationRoleLite) ) return false;
        VzdnNotificationRoleLite castOther = (VzdnNotificationRoleLite) other;
        return new EqualsBuilder()
            .append(this.getNotificationId(), castOther.getNotificationId())
            .append(this.getRoleId(), castOther.getRoleId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNotificationId())
            .append(getRoleId())
            .toHashCode();
    }

}
