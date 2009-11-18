package com.netpace.aims.controller.newmarketing;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="MktAppSearchForm"
 * @author Ahson Imtiaz.
 */

public class MktAppSearchForm extends BaseValidatorForm
{

    private String appTitle;
    private String companyName;
    private Long deckId;
    private String singleMultiplayer;
    private Long[] deviceIds;
    private Integer pageNo;
    private String task;
    // Used by list page
    private Long[] appsIds;

    /**
     * @return Returns the appsIds.
     */
    public Long[] getAppsIds()
    {
        return appsIds;
    }
    /**
     * @param appsIds The appsIds to set.
     */
    public void setAppsIds(Long[] appsIds)
    {
        this.appsIds = appsIds;
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
     * @return Returns the companyName.
     */
    public String getCompanyName()
    {
        return companyName;
    }
    /**
     * @param companyName The companyName to set.
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    /**
     * @return Returns the deckId.
     */
    public Long getDeckId()
    {
        return deckId;
    }
    /**
     * @param deckId The deckId to set.
     */
    public void setDeckId(Long deckId)
    {
        this.deckId = deckId;
    }
    /**
     * @return Returns the deviceIds.
     */
    public Long[] getDeviceIds()
    {
        return deviceIds;
    }
    /**
     * @param deviceIds The deviceIds to set.
     */
    public void setDeviceIds(Long[] deviceIds)
    {
        this.deviceIds = deviceIds;
    }
    /**
     * @return Returns the pageNo.
     */
    public Integer getPageNo()
    {
        return pageNo;
    }
    /**
     * @param pageNo The pageNo to set.
     */
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
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
     * @return Returns the task.
     */
    public String getTask()
    {
        return task;
    }
    /**
     * @param task The task to set.
     */
    public void setTask(String task)
    {
        this.task = task;
    }
}
