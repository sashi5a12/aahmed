package com.netpace.aims.bo.application;

import org.apache.log4j.Logger;

public class WapRollbackAppInformation
{

    static Logger log = Logger.getLogger(WapRollbackAppInformation.class.getName());

    private java.lang.Long appsId;
    private java.lang.String companyName;
    private java.lang.String title;
    private java.util.Date createdDate;
    private java.lang.Long phaseId;
    private java.lang.String phaseName;
    private java.lang.String urlSetupAction;
    private java.lang.Long allianceId;

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

    public java.lang.String getTitle()
    {
        return this.title;
    }

    public void setTitle(java.lang.String title)
    {
        this.title = title;
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

    public java.lang.Long getAllianceId()
    {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.Long allianceId)
    {
        this.allianceId = allianceId;
    }

}
