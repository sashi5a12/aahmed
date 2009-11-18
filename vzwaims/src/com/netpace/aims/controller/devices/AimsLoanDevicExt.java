package com.netpace.aims.controller.devices;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Ahson Imtiaz */
public class AimsLoanDevicExt extends com.netpace.aims.model.BaseValueObject implements Serializable {

    /** identifier field */
    private java.lang.Long deviceId;

    /** nullable persistent field */
    private java.lang.String deviceModel;

    /** nullable persistent field */
    private java.lang.String deviceMfgBy;

    /** nullable persistent field */
    private java.lang.String status;

    /** default constructor */
    public AimsLoanDevicExt() {
    }


    public java.lang.Long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(java.lang.Long deviceId) {
        this.deviceId = deviceId;
    }

    public java.lang.String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(java.lang.String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public java.lang.String getDeviceMfgBy() {
        return this.deviceMfgBy;
    }

    public void setDeviceMfgBy(java.lang.String deviceMfgBy) {
        this.deviceMfgBy = deviceMfgBy;
    }

    public java.lang.String getStatus() {
        return this.status;
    }

    public void setStatus(java.lang.String strInput) {
        this.status = strInput;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("loanDeviceId", getDeviceId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AimsLoanDevicExt) ) return false;
        AimsLoanDevicExt castOther = (AimsLoanDevicExt) other;
        return new EqualsBuilder()
            .append(this.getDeviceId(), castOther.getDeviceId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getDeviceId())
            .toHashCode();
    }

}
