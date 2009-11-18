package com.netpace.aims.controller.system;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="EmailMessageForm"
 */

public class EmailMessageForm extends BaseValidatorForm
{

    private java.lang.String createdBy;
    private java.lang.String lastUpdatedBy;
    private String task;
    private String emailTitle;
    private String emailText;
    private String emailDesc;
    private String emailCategory;
    private String emailMessageId;
    private String lastEmailSentDate;
    private String totalEmailSent;
    private String createdDate;
    private String lastUpdatedDate;
    private String[] emailCategoryList;
    private String replaceableValue;

    public java.lang.String getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedDate()
    {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

    public java.lang.String getLastUpdatedBy()
    {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(java.lang.String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDate()
    {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate)
    {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getTask()
    {
        return task;
    }

    public void setTask(String task)
    {
        this.task = task;
    }
    public String getEmailTitle()
    {
        return emailTitle;
    }
    public void setEmailTitle(String emailTitle)
    {
        this.emailTitle = emailTitle;
    }
    public String getEmailText()
    {
        return emailText;
    }
    public void setEmailText(String emailText)
    {
        this.emailText = emailText;
    }
    public String getEmailDesc()
    {
        return emailDesc;
    }
    public void setEmailDesc(String emailDesc)
    {
        this.emailDesc = emailDesc;
    }
    public String getEmailCategory()
    {
        return emailCategory;
    }
    public void setEmailCategory(String emailCategory)
    {
        this.emailCategory = emailCategory;
    }
    public String getEmailMessageId()
    {
        return emailMessageId;
    }
    public void setEmailMessageId(String emailMessageId)
    {
        this.emailMessageId = emailMessageId;
    }
    public String getLastEmailSentDate()
    {
        return lastEmailSentDate;
    }
    public void setLastEmailSentDate(String lastEmailSentDate)
    {
        this.lastEmailSentDate = lastEmailSentDate;
    }
    public String getTotalEmailSent()
    {
        return totalEmailSent;
    }
    public void setTotalEmailSent(String totalEmailSent)
    {
        this.totalEmailSent = totalEmailSent;
    }
    public String[] getEmailCategoryList()
    {
        return emailCategoryList;
    }
    public void setEmailCategoryList(String[] emailCategoryList)
    {
        this.emailCategoryList = emailCategoryList;
    }
    public String getReplaceableValue()
    {
        return replaceableValue;
    }
    public void setReplaceableValue(String replaceableValue)
    {
        this.replaceableValue = replaceableValue;
    }

}
