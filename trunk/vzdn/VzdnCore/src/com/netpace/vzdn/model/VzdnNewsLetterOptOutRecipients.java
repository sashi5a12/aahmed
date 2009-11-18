package com.netpace.vzdn.model;

//import com.netpace.aims.model.core.AimsUser;
import com.netpace.vzdn.model.VzdnUsers;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class VzdnNewsLetterOptOutRecipients extends BaseValueObject implements Serializable {


	private Integer optOutId;
	
    /** identifier field */
    private VzdnUsers vzdnUser;
    
    private String lastUpdateBy;
    
    private Timestamp lastUpdateDate;
    

    /** full constructor */
    public VzdnNewsLetterOptOutRecipients(java.lang.Integer optOutId, VzdnUsers vzdnUser) {
    	this.optOutId = optOutId;        
        this.vzdnUser = vzdnUser;
    }
    
    public VzdnNewsLetterOptOutRecipients(VzdnUsers vzdnUser, String updateBy, Timestamp updateTime) {        
        this.vzdnUser = vzdnUser;
        this.lastUpdateBy = updateBy;
        this.lastUpdateDate = updateTime;       
    }

    /** default constructor */
    public VzdnNewsLetterOptOutRecipients() {
    }

    /** minimal constructor */
    public VzdnNewsLetterOptOutRecipients(VzdnUsers vzdnUser) {
        this.vzdnUser = vzdnUser;
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("aimsUser", getVzdnUser())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof VzdnNewsLetterOptOutRecipients) ) return false;
        VzdnNewsLetterOptOutRecipients castOther = (VzdnNewsLetterOptOutRecipients) other;
        return new EqualsBuilder()
            .append(this.getVzdnUser(), castOther.getVzdnUser())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getVzdnUser())
            .toHashCode();
    }

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getOptOutId() {
		return optOutId;
	}

	public void setOptOutId(Integer optOutId) {
		this.optOutId = optOutId;
	}

	public VzdnUsers getVzdnUser() {
		return vzdnUser;
	}

	public void setVzdnUser(VzdnUsers vzdnUser) {
		this.vzdnUser = vzdnUser;
	}
}
