package com.netpace.aims.controller.newmarketing;

import java.io.Serializable;
import java.util.ArrayList;

/** @author Ahson Imtiaz */
public class AppContactsDetailsExt implements Serializable
{

    private Long applicationId;
    private String appTitle;
    private String developerName;
    private ArrayList allianceUsers;
    private String status;
    private String userStatus;
    private String allianceUserNameToApprove;
    private String allianceUserIdToApprove;
    private String contentUsagePersmission;

    /**
     * @return Returns the allianceUserNameToApprove.
     */
    public String getAllianceUserNameToApprove()
    {
        return allianceUserNameToApprove;
    }
    /**
     * @param allianceUserNameToApprove The allianceUserNameToApprove to set.
     */
    public void setAllianceUserNameToApprove(String allianceUserNameToApprove)
    {
        this.allianceUserNameToApprove = allianceUserNameToApprove;
    }
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
    /**
     * @return Returns the allianceUsers.
     */
    public ArrayList getAllianceUsers()
    {
        return allianceUsers;
    }
    /**
     * @param allianceUsers The allianceUsers to set.
     */
    public void setAllianceUsers(ArrayList allianceUsers)
    {
        this.allianceUsers = allianceUsers;
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
    public String getContentUsagePersmission()
    {
        return contentUsagePersmission;
    }
    public void setContentUsagePersmission(String contentUsagePersmission)
    {
        this.contentUsagePersmission = contentUsagePersmission;
    }

    /**
     * @return
     */
    public String getAllianceUserIdToApprove()
    {
        return allianceUserIdToApprove;
    }

    /**
     * @param string
     */
    public void setAllianceUserIdToApprove(String string)
    {
        allianceUserIdToApprove = string;
    }

}
