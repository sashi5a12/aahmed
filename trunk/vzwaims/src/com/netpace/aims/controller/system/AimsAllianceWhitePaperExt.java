package com.netpace.aims.controller.system;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AimsAllianceWhitePaperExt implements Serializable
{

    /** identifier field */
    private java.lang.Long whitePaperId;

    /** nullable persistent field */
    private java.lang.String whitePaperName;

    /** nullable persistent field */
    private java.lang.String whitePaperDesc;

    /** nullable persistent field */
    private java.util.Date submittedDate;

    /** nullable persistent field */
    private java.lang.String status;

    /** nullable persistent field */
    private java.lang.String allianceName;

    /** nullable persistent field */
    private java.lang.String whitePaperFilename;

    /** nullable persistent field */
    private java.lang.String submittedByName;
    private String whitePaperFileType;

    /* Public Getter and Setter Functions Starts*/

    /*  */
    public void setWhitePaperId(Long lngTempId)
    {
        this.whitePaperId = lngTempId;
    }

    public Long getWhitePaperId()
    {
        return this.whitePaperId;
    }

    /*  */
    public void setStatus(String strInput)
    {
        this.status = strInput;
    }

    public String getStatus()
    {
        return this.status;
    }

    /*  */

    public String getWhitePaperStatusDesc()
    {
        if (this.status.equalsIgnoreCase("S"))
            return "Submitted";
        else if (this.status.equalsIgnoreCase("A"))
            return "Active";
        else if (this.status.equalsIgnoreCase("H"))
            return "Hot";
        else if (this.status.equalsIgnoreCase("O"))
            return "On Hold";

        return "Unknown";
    }

    /*  */
    public void setWhitePaperName(String strInput)
    {
        this.whitePaperName = strInput;
    }

    public String getWhitePaperName()
    {
        return this.whitePaperName;
    }

    /*  */
    public void setAllianceName(String strInput)
    {
        this.allianceName = strInput;
    }

    public String getAllianceName()
    {
        return this.allianceName;
    }

    /* */
    public java.util.Date getSubmittedDate()
    {
        return this.submittedDate;
    }

    public void setSubmittedDate(java.util.Date submittedDate)
    {
        this.submittedDate = submittedDate;
    }

    /* */
    public void setSubmittedByName(String strInput)
    {
        this.submittedByName = strInput;
    }

    public String getSubmittedByName()
    {
        return this.submittedByName;
    }

    /* */
    public java.lang.String getWhitePaperDesc()
    {
        return this.whitePaperDesc;
    }

    public void setWhitePaperDesc(java.lang.String whitePaperDesc)
    {
        this.whitePaperDesc = whitePaperDesc;
    }

    /* */
    public void setWhitePaperFilename(java.lang.String whitePaperFilename)
    {
        this.whitePaperFilename = whitePaperFilename;
    }

    public java.lang.String getWhitePaperFilename()
    {
        return this.whitePaperFilename;
    }

    /* */
    public String toString()
    {
        return new ToStringBuilder(this).append("whitePaperId", getWhitePaperId()).toString();
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof AimsAllianceWhitePaperExt))
            return false;
        AimsAllianceWhitePaperExt castOther = (AimsAllianceWhitePaperExt) other;
        return new EqualsBuilder().append(this.getWhitePaperId(), castOther.getWhitePaperId()).isEquals();
    }

    public int hashCode()
    {
        return new HashCodeBuilder().append(getWhitePaperId()).toHashCode();
    }
    public String getWhitePaperFileType()
    {
        return whitePaperFileType;
    }
    public void setWhitePaperFileType(String whitePaperFileType)
    {
        this.whitePaperFileType = whitePaperFileType;
    }
}