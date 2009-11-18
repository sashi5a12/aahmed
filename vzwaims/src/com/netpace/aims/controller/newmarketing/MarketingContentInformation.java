package com.netpace.aims.controller.newmarketing;

import org.apache.log4j.Logger;

public class MarketingContentInformation
{

    static Logger log = Logger.getLogger(MarketingContentInformation.class.getName());

    private java.lang.Long creativeContentId;
    private java.lang.String contentTitle;
    private java.lang.String contentDescription;
    private java.lang.String applicationTitle;
    private java.lang.String status;
    private java.lang.String submittedDate;
    private java.lang.String approvalDate;
    private java.lang.String expiryDate;
    private java.lang.String companyName;
    private java.lang.Long allianceId;
    private java.lang.String urlSetupAction;
    private boolean allowCreate;
    private boolean allowEdit;
    private boolean allowDelete;

    /**
     * @return
     */
    public boolean isAllowCreate()
    {
        return allowCreate;
    }

    /**
     * @return
     */
    public boolean isAllowDelete()
    {
        return allowDelete;
    }

    /**
     * @return
     */
    public boolean isAllowEdit()
    {
        return allowEdit;
    }

    /**
     * @return
     */
    public java.lang.String getApplicationTitle()
    {
        return applicationTitle;
    }

    /**
     * @return
     */
    public java.lang.String getApprovalDate()
    {
        return approvalDate;
    }

    /**
     * @return
     */
    public java.lang.String getContentDescription()
    {
        return contentDescription;
    }

    /**
     * @return
     */
    public java.lang.String getContentTitle()
    {
        return contentTitle;
    }

    /**
     * @return
     */
    public java.lang.Long getCreativeContentId()
    {
        return creativeContentId;
    }

    /**
     * @return
     */
    public java.lang.String getExpiryDate()
    {
        return expiryDate;
    }

    /**
     * @return
     */
    public java.lang.String getStatus()
    {
        return status;
    }

    /**
     * @return
     */
    public java.lang.String getSubmittedDate()
    {
        return submittedDate;
    }

    /**
     * @return
     */
    public java.lang.String getUrlSetupAction()
    {
        return urlSetupAction;
    }

    /**
     * @param b
     */
    public void setAllowCreate(boolean b)
    {
        allowCreate = b;
    }

    /**
     * @param b
     */
    public void setAllowDelete(boolean b)
    {
        allowDelete = b;
    }

    /**
     * @param b
     */
    public void setAllowEdit(boolean b)
    {
        allowEdit = b;
    }

    /**
     * @param string
     */
    public void setApplicationTitle(java.lang.String string)
    {
        applicationTitle = string;
    }

    /**
     * @param string
     */
    public void setApprovalDate(java.lang.String string)
    {
        approvalDate = string;
    }

    /**
     * @param string
     */
    public void setContentDescription(java.lang.String string)
    {
        contentDescription = string;
    }

    /**
     * @param string
     */
    public void setContentTitle(java.lang.String string)
    {
        contentTitle = string;
    }

    /**
     * @param long1
     */
    public void setCreativeContentId(java.lang.Long long1)
    {
        creativeContentId = long1;
    }

    /**
     * @param string
     */
    public void setExpiryDate(java.lang.String string)
    {
        expiryDate = string;
    }

    /**
     * @param string
     */
    public void setStatus(java.lang.String string)
    {
        status = string;
    }

    /**
     * @param string
     */
    public void setSubmittedDate(java.lang.String string)
    {
        submittedDate = string;
    }

    /**
     * @param string
     */
    public void setUrlSetupAction(java.lang.String string)
    {
        urlSetupAction = string;
    }

    /**
     * @return
     */
    public java.lang.String getCompanyName()
    {
        return companyName;
    }

    /**
     * @param string
     */
    public void setCompanyName(java.lang.String string)
    {
        companyName = string;
    }

    /**
     * @return
     */
    public java.lang.Long getAllianceId()
    {
        return allianceId;
    }

    /**
     * @param long1
     */
    public void setAllianceId(java.lang.Long long1)
    {
        allianceId = long1;
    }

}
