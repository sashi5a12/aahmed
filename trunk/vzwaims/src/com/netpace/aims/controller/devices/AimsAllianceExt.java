package com.netpace.aims.controller.devices;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Ahson Imtiaz */
public class AimsAllianceExt extends com.netpace.aims.model.BaseValueObject implements Serializable {

    /** identifier field */
    private java.lang.Long allianceId;

    /** nullable persistent field */
    private java.lang.String companyName;

    /** default constructor */
    public AimsAllianceExt() {
    }

    public java.lang.Long getAllianceId() {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.Long allianceId) {
        this.allianceId = allianceId;
    }

    public java.lang.String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("allianceId", getAllianceId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AimsAllianceExt) ) return false;
        AimsAllianceExt castOther = (AimsAllianceExt) other;
        return new EqualsBuilder()
            .append(this.getAllianceId(), castOther.getAllianceId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAllianceId())
            .toHashCode();
    }

}
