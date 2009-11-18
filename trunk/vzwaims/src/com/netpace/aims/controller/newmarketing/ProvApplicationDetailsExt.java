package com.netpace.aims.controller.newmarketing;

import java.io.Serializable;

/** @author Ahson Imtiaz */
public class ProvApplicationDetailsExt implements Serializable
{

    private String developerName;
    private Long applicationId;
    private String appTitle;
    private String shortDesc;
    private String longDesc;
    private String deckName;
    private String[] devicesNames;
    private String singleMultiplayer;
    private String screenName1;
    private String screenName2;
    private String screenName3;
    private String screenName4;
    private String screenName5;
    private String hiResPublisherLogoName;
    private String splashScreenName;
    private String activeScreenName;
    private String appTitleGraphicsName;
    private String version;
    private String networkUsage;

    /**
     * @return Returns the devicesNames.
     */
    public String[] getDevicesNames()
    {
        return devicesNames;
    }
    /**
     * @param devicesNames The devicesNames to set.
     */
    public void setDevicesNames(String[] devicesNames)
    {
        this.devicesNames = devicesNames;
    }
    /**
     * @return Returns the networkUsage.
     */
    public String getNetworkUsage()
    {
        return networkUsage;
    }
    /**
     * @param networkUsage The networkUsage to set.
     */
    public void setNetworkUsage(String networkUsage)
    {
        this.networkUsage = networkUsage;
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
    // Class Ends
    /**
     * @return Returns the activeScreenName.
     */
    public String getActiveScreenName()
    {
        return activeScreenName;
    }
    /**
     * @param activeScreenName The activeScreenName to set.
     */
    public void setActiveScreenName(String activeScreenName)
    {
        this.activeScreenName = activeScreenName;
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
     * @return Returns the appTitleGraphicsName.
     */
    public String getAppTitleGraphicsName()
    {
        return appTitleGraphicsName;
    }
    /**
     * @param appTitleGraphicsName The appTitleGraphicsName to set.
     */
    public void setAppTitleGraphicsName(String appTitleGraphicsName)
    {
        this.appTitleGraphicsName = appTitleGraphicsName;
    }
    /**
     * @return Returns the deckName.
     */
    public String getDeckName()
    {
        return deckName;
    }
    /**
     * @param deckName The deckName to set.
     */
    public void setDeckName(String deckName)
    {
        this.deckName = deckName;
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
     * @return Returns the hiResPublisherLogoName.
     */
    public String getHiResPublisherLogoName()
    {
        return hiResPublisherLogoName;
    }
    /**
     * @param hiResPublisherLogoName The hiResPublisherLogoName to set.
     */
    public void setHiResPublisherLogoName(String hiResPublisherLogoName)
    {
        this.hiResPublisherLogoName = hiResPublisherLogoName;
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
     * @return Returns the screenName1.
     */
    public String getScreenName1()
    {
        return screenName1;
    }
    /**
     * @param screenName1 The screenName1 to set.
     */
    public void setScreenName1(String screenName1)
    {
        this.screenName1 = screenName1;
    }
    /**
     * @return Returns the screenName2.
     */
    public String getScreenName2()
    {
        return screenName2;
    }
    /**
     * @param screenName2 The screenName2 to set.
     */
    public void setScreenName2(String screenName2)
    {
        this.screenName2 = screenName2;
    }
    /**
     * @return Returns the screenName3.
     */
    public String getScreenName3()
    {
        return screenName3;
    }
    /**
     * @param screenName3 The screenName3 to set.
     */
    public void setScreenName3(String screenName3)
    {
        this.screenName3 = screenName3;
    }
    /**
     * @return Returns the screenName4.
     */
    public String getScreenName4()
    {
        return screenName4;
    }
    /**
     * @param screenName4 The screenName4 to set.
     */
    public void setScreenName4(String screenName4)
    {
        this.screenName4 = screenName4;
    }
    /**
     * @return Returns the screenName5.
     */
    public String getScreenName5()
    {
        return screenName5;
    }
    /**
     * @param screenName5 The screenName5 to set.
     */
    public void setScreenName5(String screenName5)
    {
        this.screenName5 = screenName5;
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
     * @return Returns the singleMultiplayer.
     */
    public String getSingleMultiplayer()
    {
        return singleMultiplayer;
    }
    /**
     * @param singleMultiplayer The singleMultiplayer to set.
     */
    public void setSingleMultiplayer(String singleMultiplayer)
    {
        this.singleMultiplayer = singleMultiplayer;
    }
    /**
     * @return Returns the splashScreenName.
     */
    public String getSplashScreenName()
    {
        return splashScreenName;
    }
    /**
     * @param splashScreenName The splashScreenName to set.
     */
    public void setSplashScreenName(String splashScreenName)
    {
        this.splashScreenName = splashScreenName;
    }

    /**
     * @return Returns the gameplay mode associated with application.
     */
    public String getGamePlayMode()
    {
        if (singleMultiplayer == null)
            return "Unkown";
        else if (singleMultiplayer.equals("S"))
            return "Single Player";
        else if (singleMultiplayer.equals("M"))
            return "Multi-Player";
        else if (singleMultiplayer.equals("B"))
            return "Both";
        else if (singleMultiplayer.equals("N"))
            return "N/A";
        else
            return "Unkown";
    }

    /**
     * @return Returns the Name associated with Network Code.
     */
    public String getNetworkMode()
    {
        if (networkUsage == null)
            return "Unkown";
        else if (networkUsage.equals("S"))
            return "Against Server";
        else if (networkUsage.equals("P"))
            return "Against Player";
        else if (networkUsage.equals("N"))
            return "No Network Usage";
        else
            return "Unkown";
    }

    public String getDevicesNameList()
    {
        StringBuffer sbNames = new StringBuffer();
        if (devicesNames != null && devicesNames.length > 0)
            for (int iCount = 0; iCount < devicesNames.length; iCount++)
            {
                if (iCount != 0)
                    sbNames.append(", ");
                sbNames.append(devicesNames[iCount]);
            }
        return sbNames.toString();
    }

    // End Application
}
