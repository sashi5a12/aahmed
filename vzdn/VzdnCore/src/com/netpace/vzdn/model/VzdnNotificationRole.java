package com.netpace.vzdn.model;

import com.netpace.vzdn.model.VzdnSysRoles;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class VzdnNotificationRole extends BaseValueObject implements Serializable {

    /** nullable persistent field */
    private java.lang.Long notificationId;

    /** nullable persistent field */
    private java.lang.Long roleId;

    /** identifier field */
    private VzdnSysRoles aimsSysRole;

    /** full constructor */
    public VzdnNotificationRole(java.lang.Long notificationId, java.lang.Long roleId, VzdnSysRoles aimsSysRole) {
        this.notificationId = notificationId;
        this.roleId = roleId;
        this.aimsSysRole = aimsSysRole;
    }

    /** default constructor */
    public VzdnNotificationRole() {
    }

    /** minimal constructor */
    public VzdnNotificationRole(VzdnSysRoles aimsSysRole) {
        this.aimsSysRole = aimsSysRole;
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

    public VzdnSysRoles getAimsSysRole() {
        return this.aimsSysRole;
    }

    public void setAimsSysRole(VzdnSysRoles aimsSysRole) {
        this.aimsSysRole = aimsSysRole;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("aimsSysRole", getAimsSysRole())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof VzdnNotificationRole) ) return false;
        VzdnNotificationRole castOther = (VzdnNotificationRole) other;
        return new EqualsBuilder()
            .append(this.getAimsSysRole(), castOther.getAimsSysRole())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAimsSysRole())
            .toHashCode();
    }

}
