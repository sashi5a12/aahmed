package com.netpace.aims.model.reports;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class AimsPerfReport extends com.netpace.aims.model.BaseValueObject implements Serializable {

    /** identifier field */
    private java.lang.Long deviceId;

    /** persistent field */
    private java.lang.String departmentName;

    /** nullable persistent field */
    private java.lang.String employeeName;

    /** nullable persistent field */
    private java.lang.String reportDate;


    /** persistent field */
    private List verizonEmployees;

    /** persistent field */
    private List verizonEmpAlliances;


    /** default constructor */
    public AimsPerfReport() {
    }


    public java.lang.Long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(java.lang.Long deviceId) {
        this.deviceId = deviceId;
    }

    public java.lang.String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(java.lang.String departmentName) {
        this.departmentName = departmentName;
    }

    public java.lang.String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(java.lang.String employeeName) {
        this.employeeName = employeeName;
    }

    public java.lang.String getReportDate() {
        return this.reportDate;
    }

    public void setReportDate(java.lang.String reportDate) {
        this.reportDate = reportDate;
    }

   
    public java.util.List getVerizonEmployees() {
        return this.verizonEmployees;
    }

    public void setVerizonEmployees(java.util.List verizonEmployees) {
        this.verizonEmployees = verizonEmployees;
    }


    public java.util.List getVerizonEmpAlliances() {
        return this.verizonEmpAlliances;
    }

    public void setVerizonEmpAlliances(java.util.List verizonEmpAlliances) {
        this.verizonEmpAlliances = verizonEmpAlliances;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("deviceId", getDeviceId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AimsPerfReport) ) return false;
        AimsPerfReport castOther = (AimsPerfReport) other;
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
