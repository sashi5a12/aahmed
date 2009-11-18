package com.netpace.aims.model.reports;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class AimsEmployee extends com.netpace.aims.model.BaseValueObject implements Serializable {

    /** identifier field */
    private int employeeId;

    /** nullable persistent field */
    private java.lang.String employeeName;


    /** default constructor */
    public AimsEmployee() {
    }


    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public java.lang.String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(java.lang.String employeeName) {
        this.employeeName = employeeName;
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("employeeId", getEmployeeId())
			.append("employeeName", getEmployeeName())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AimsEmployee) ) return false;
        AimsEmployee castOther = (AimsEmployee) other;
        return new EqualsBuilder()
            .append(this.getEmployeeId(), castOther.getEmployeeId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getEmployeeId())
            .toHashCode();
    }

}

