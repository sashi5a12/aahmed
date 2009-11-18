package com.netpace.aims.controller.newmarketing;

import java.io.Serializable;
import java.util.Date;

/** @author Ahson Imtiaz */
public class MarketApplicationExt implements Serializable
{

    private Long applicationId;
    private String appTitle;
    private String developerName;
    private String deckPlacement;
    private Date launchDate;
    private String version;
    private String status;
    private String userStatus;
    // Fields mentioned below are only used by pdf generator action class
    private String shortDesc;
    private String longDesc;
    private String[] deviceName;
    private Object[] blobImages;
    private String contentUsagePermission;

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
     * @return Returns the version.
     */
    public String getVersion()
    {
        return version;
    }
    /**
     * @param version The version to set.
     */
    public void setVersion(String version)
    {
        this.version = version;
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
     * @return Returns the deckPlacement.
     */
    public String getDeckPlacement()
    {
        return deckPlacement;
    }
    /**
     * @param deckPlacement The deckPlacement to set.
     */
    public void setDeckPlacement(String deckPlacement)
    {
        this.deckPlacement = deckPlacement;
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
     * @return Returns the launchDate.
     */
    public Date getLaunchDate()
    {
        return launchDate;
    }
    /**
     * @param launchDate The launchDate to set.
     */
    public void setLaunchDate(Date launchDate)
    {
        this.launchDate = launchDate;
    }
    /**
     * @return Returns the blobImages.
     */
    public Object[] getBlobImages()
    {
        return blobImages;
    }
    /**
     * @param blobImages The blobImages to set.
     */
    public void setBlobImages(Object[] blobImages)
    {
        this.blobImages = blobImages;
    }
    /**
     * @return Returns the deviceName.
     */
    public String[] getDeviceName()
    {
        return deviceName;
    }
    /**
     * @param deviceName The deviceName to set.
     */
    public void setDeviceName(String[] deviceName)
    {
        this.deviceName = deviceName;
    }
    /**
     * @return Returns the longDesc.
     */
    public String getLongDesc()
    {
        return longDesc;
    }
    /**
     * @param longDesc The longDesc to set.
     */
    public void setLongDesc(String longDesc)
    {
        this.longDesc = longDesc;
    }
    /**
     * @return Returns the shortDesc.
     */
    public String getShortDesc()
    {
        return shortDesc;
    }
    /**
     * @param shortDesc The shortDesc to set.
     */
    public void setShortDesc(String shortDesc)
    {
        this.shortDesc = shortDesc;
    }

    /**
     * @return All devices names separated by comma.
     */
    public String getDevicesName()
    {
        StringBuffer sbName = new StringBuffer();

        for (int iCount = 0; iCount < deviceName.length; iCount++)
        {
            if (sbName.length() > 0)
                sbName.append(", ");
            sbName.append(deviceName[iCount]);
        }

        return sbName.toString();
    }
    public String getContentUsagePermission()
    {
        return contentUsagePermission;
    }
    public void setContentUsagePermission(String contentUsagePermission)
    {
        this.contentUsagePermission = contentUsagePermission;
    }

    /**
     * @return
     */
    public String getUserStatus()
    {
        return userStatus;
    }

    /**
     * @param string
     */
    public void setUserStatus(String string)
    {
        userStatus = string;
    }

}
