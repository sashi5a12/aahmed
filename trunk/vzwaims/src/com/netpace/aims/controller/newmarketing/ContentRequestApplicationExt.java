package com.netpace.aims.controller.newmarketing;

import java.io.Serializable;
import java.util.Date;

/** @author Ahson Imtiaz */
public class ContentRequestApplicationExt implements Serializable
{

    private Long crequestId;
    private Long applicationId;
    private String appTitle;
    private String programName;
    private Date projectStartDate;
    private Date dateSubmitted;
    private String status;
    private String userStatus;

    /**
     * @return Returns the userStatus.
     */
    public String getUserStatus()
    {
        return userStatus;
    }
    /**
     * @param userStatus The userStatus to set.
     */
    public void setUserStatus(String userStatus)
    {
        this.userStatus = userStatus;
    }
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
     * @return Returns the crequestId.
     */
    public Long getCrequestId()
    {
        return crequestId;
    }
    /**
     * @param crequestId The crequestId to set.
     */
    public void setCrequestId(Long crequestId)
    {
        this.crequestId = crequestId;
    }
    /**
     * @return Returns the dateSubmitted.
     */
    public Date getDateSubmitted()
    {
        return dateSubmitted;
    }
    /**
     * @param dateSubmitted The dateSubmitted to set.
     */
    public void setDateSubmitted(Date dateSubmitted)
    {
        this.dateSubmitted = dateSubmitted;
    }
    /**
     * @return Returns the projectStartDate.
     */
    public Date getProjectStartDate()
    {
        return projectStartDate;
    }
    /**
     * @param projectStartDate The projectStartDate to set.
     */
    public void setProjectStartDate(Date projectStartDate)
    {
        this.projectStartDate = projectStartDate;
    }
    /**
     * @return Returns the status.
     */
    public String getStatus()
    {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
}
