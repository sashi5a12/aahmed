package com.netpace.aims.bo.application;

import org.apache.log4j.Logger;

public class ApplicationInformation
{

    static Logger log = Logger.getLogger(ApplicationInformation.class.getName());

    private java.lang.Long appsId;
    private java.lang.String companyName;
    private java.lang.Long platformId;
    private java.lang.String platformName;
    private java.lang.String title;
    private java.lang.String categoryName;
    private java.util.Date createdDate;
    private java.lang.Long phaseId;
    private java.lang.String phaseName;
    private java.lang.String urlSetupAction;
    private java.lang.String version;
    private java.lang.String ifOnHold;
    private java.lang.Long allianceId;
    private java.lang.String brewAppType;
    private boolean isLbs;
    private boolean allowCreate;
    private boolean allowEdit;
    private boolean allowDelete;

    public java.lang.Long getAppsId()
    {
        return this.appsId;
    }

    public void setAppsId(java.lang.Long appsId)
    {
        this.appsId = appsId;
    }

    public java.lang.String getCompanyName()
    {
        return this.companyName;
    }

    public void setCompanyName(java.lang.String companyName)
    {
        this.companyName = companyName;
    }

    public java.lang.Long getPlatformId()
    {
        return this.platformId;
    }

    public void setPlatformId(java.lang.Long platformId)
    {
        this.platformId = platformId;
    }

    public java.lang.String getPlatformName()
    {
        return this.platformName;
    }

    public void setPlatformName(java.lang.String platformName)
    {
        this.platformName = platformName;
    }

    public java.lang.String getTitle()
    {
        return this.title;
    }

    public void setTitle(java.lang.String title)
    {
        this.title = title;
    }

    public java.lang.String getCategoryName()
    {
        return this.categoryName;
    }

    public void setCategoryName(java.lang.String categoryName)
    {
        this.categoryName = categoryName;
    }

    public java.util.Date getCreatedDate()
    {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public java.lang.Long getPhaseId()
    {
        return this.phaseId;
    }

    public void setPhaseId(java.lang.Long phaseId)
    {
        this.phaseId = phaseId;
    }

    public java.lang.String getPhaseName()
    {
        return this.phaseName;
    }

    public void setPhaseName(java.lang.String phaseName)
    {
        this.phaseName = phaseName;
    }

    public java.lang.String getUrlSetupAction()
    {
        return this.urlSetupAction;
    }

    public void setUrlSetupAction(java.lang.String urlSetupAction)
    {
        this.urlSetupAction = urlSetupAction;
    }

    public java.lang.String getVersion()
    {
        return this.version;
    }

    public void setVersion(java.lang.String version)
    {
        this.version = version;
    }

    public java.lang.String getIfOnHold()
    {
        return this.ifOnHold;
    }

    public void setIfOnHold(java.lang.String ifOnHold)
    {
        this.ifOnHold = ifOnHold;
    }

    public java.lang.Long getAllianceId()
    {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.Long allianceId)
    {
        this.allianceId = allianceId;
    }

    public boolean getAllowCreate()
    {
        return this.allowCreate;
    }

    public void setAllowCreate(boolean allowCreate)
    {
        this.allowCreate = allowCreate;
    }

    public boolean getAllowEdit()
    {
        return this.allowEdit;
    }

    public void setAllowEdit(boolean allowEdit)
    {
        this.allowEdit = allowEdit;
    }

    public boolean getAllowDelete()
    {
        return this.allowDelete;
    }

    public void setAllowDelete(boolean allowDelete)
    {
        this.allowDelete = allowDelete;
    }

    /**
     * @return
     */
    public java.lang.String getBrewAppType()
    {
        return brewAppType;
    }

    /**
     * @param string
     */
    public void setBrewAppType(java.lang.String string)
    {
        brewAppType = string;
    }

    /**
     * @return
     */
    public boolean getIsLbs()
    {
        return isLbs;
    }

    /**
     * @param b
     */
    public void setIsLbs(boolean b)
    {
        isLbs = b;
    }

}
