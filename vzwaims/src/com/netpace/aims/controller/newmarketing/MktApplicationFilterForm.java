package com.netpace.aims.controller.newmarketing;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="MktApplicationFilterForm"
 * @author Ahson Imtiaz.
 */

public class MktApplicationFilterForm extends BaseValidatorForm
{

    private String filterText;
    private String filterField;
    private Long[] appsIds;
    private Integer pageNo;
    private String task;
    private Long applicationId;

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
     * @return Returns the filterField.
     */
    public String getFilterField()
    {
        return filterField;
    }
    /**
     * @param filterField The filterField to set.
     */
    public void setFilterField(String filterField)
    {
        this.filterField = filterField;
    }
    /**
     * @return Returns the filterText.
     */
    public String getFilterText()
    {
        return filterText;
    }
    /**
     * @param filterText The filterText to set.
     */
    public void setFilterText(String filterText)
    {
        this.filterText = filterText;
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
}
