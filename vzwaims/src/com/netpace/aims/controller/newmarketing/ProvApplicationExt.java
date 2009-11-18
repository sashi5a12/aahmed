package com.netpace.aims.controller.newmarketing;

import java.io.Serializable;
import java.util.Date;

/** @author Ahson Imtiaz */
public class ProvApplicationExt implements Serializable
{

    private Long applicationId;
    private String appTitle;
    private String developerName;
    private Long cRequestId;
    private Date approvalDate;
    private Date expiryDate;
    private String programName;

    /**
     * @return Returns the programName.
     */
    public String getProgramName()
    {
        return programName;
    }
    /**
     * @param programName The programName to set.
     */
    public void setProgramName(String programName)
    {
        this.programName = programName;
    }
    /**
     * @return Returns the applicationId.
     */
    public Long getApplicationId()
    {
        return applicationId;
    }
    /**
     * @param applicationId The applicationId to set.
     */
    public void setApplicationId(Long applicationId)
    {
        this.applicationId = applicationId;
    }
    /**
     * @return Returns the approvalDate.
     */
    public Date getApprovalDate()
    {
        return approvalDate;
    }
    /**
     * @param approvalDate The approvalDate to set.
     */
    public void setApprovalDate(Date approvalDate)
    {
        this.approvalDate = approvalDate;
    }
    /**
     * @return Returns the appTitle.
     */
    public String getAppTitle()
    {
        return appTitle;
    }
    /**
     * @param appTitle The appTitle to set.
     */
    public void setAppTitle(String appTitle)
    {
        this.appTitle = appTitle;
    }
    /**
     * @return Returns the cRequestId.
     */
    public Long getCRequestId()
    {
        return cRequestId;
    }
    /**
     * @param requestId The cRequestId to set.
     */
    public void setCRequestId(Long requestId)
    {
        cRequestId = requestId;
    }
    /**
     * @return Returns the developerName.
     */
    public String getDeveloperName()
    {
        return developerName;
    }
    /**
     * @param developerName The developerName to set.
     */
    public void setDeveloperName(String developerName)
    {
        this.developerName = developerName;
    }
    /**
     * @return Returns the expiryDate.
     */
    public Date getExpiryDate()
    {
        return expiryDate;
    }
    /**
     * @param expiryDate The expiryDate to set.
     */
    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    /**
     * Method returns the status of provisioned contents either Active or Expired.
     * */
    public String getExpiryStatus()
    {
        if (expiryDate == null)
            return "Active";
        Date dte = new Date();
        if (dte.compareTo(expiryDate) <= 0)
            return "Active";
        else
            return "Expired";
    }

    /**
     * Method returns the result of status for provisioned contents
     * */
    public boolean getIsActive()
    {
        if (expiryDate == null)
            return true;
        Date dte = new Date();
        if (dte.compareTo(expiryDate) <= 0)
            return true;
        else
            return false;
    }
    // Class Ends
}
