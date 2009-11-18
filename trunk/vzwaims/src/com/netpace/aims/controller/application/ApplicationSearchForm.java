package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/**
 * @struts.form	name="ApplicationSearchForm"
 */
public class ApplicationSearchForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(ApplicationSearchForm.class.getName());

    protected java.lang.String title;
    protected java.lang.Long aimsAllianceId;
    protected java.lang.Long aimsPlatformId;
    protected java.util.Collection allPlatforms;
    protected java.lang.Long aimsLifecyclePhaseId;
    protected java.util.Collection allPhases;
    protected java.lang.Long aimsContractId;
    protected java.util.Collection allContracts;
    protected java.lang.Long aimsAppCategoryId;
    protected java.util.Collection allCategories;
    protected java.lang.Long aimsAppSubCategoryId;
    protected java.util.Collection allSubCategories;
    private String[] listAvailableDevices;
    private java.util.Collection availableDevices;
    private String[] listSelectedDevices;
    private java.util.Collection selectedDevices;
    protected java.lang.String brewAppType;

    protected java.lang.String task;

    public java.lang.String getTitle()
    {
        return this.title;
    }

    public void setTitle(java.lang.String title)
    {
        this.title = title;
    }

    public java.lang.Long getAimsAllianceId()
    {
        return this.aimsAllianceId;
    }

    public void setAimsAllianceId(java.lang.Long aimsAllianceId)
    {
        this.aimsAllianceId = aimsAllianceId;
    }

    public java.lang.Long getAimsPlatformId()
    {
        return this.aimsPlatformId;
    }

    public void setAimsPlatformId(java.lang.Long aimsPlatformId)
    {
        this.aimsPlatformId = aimsPlatformId;
    }

    public java.util.Collection getAllPlatforms()
    {
        return this.allPlatforms;
    }

    public void setAllPlatforms(java.util.Collection allPlatforms)
    {
        this.allPlatforms = allPlatforms;
    }

    public java.lang.Long getAimsLifecyclePhaseId()
    {
        return this.aimsLifecyclePhaseId;
    }

    public void setAimsLifecyclePhaseId(java.lang.Long aimsLifecyclePhaseId)
    {
        this.aimsLifecyclePhaseId = aimsLifecyclePhaseId;
    }

    public java.util.Collection getAllPhases()
    {
        return this.allPhases;
    }

    public void setAllPhases(java.util.Collection allPhases)
    {
        this.allPhases = allPhases;
    }

    public java.lang.Long getAimsContractId()
    {
        return this.aimsContractId;
    }

    public void setAimsContractId(java.lang.Long aimsContractId)
    {
        this.aimsContractId = aimsContractId;
    }

    public java.util.Collection getAllContracts()
    {
        return this.allContracts;
    }

    public void setAllContracts(java.util.Collection allContracts)
    {
        this.allContracts = allContracts;
    }

    public java.lang.Long getAimsAppCategoryId()
    {
        return this.aimsAppCategoryId;
    }

    public void setAimsAppCategoryId(java.lang.Long aimsAppCategoryId)
    {
        this.aimsAppCategoryId = aimsAppCategoryId;
    }

    public java.util.Collection getAllCategories()
    {
        return this.allCategories;
    }

    public void setAllCategories(java.util.Collection allCategories)
    {
        this.allCategories = allCategories;
    }

    public java.lang.Long getAimsAppSubCategoryId()
    {
        return this.aimsAppSubCategoryId;
    }

    public void setAimsAppSubCategoryId(java.lang.Long aimsAppSubCategoryId)
    {
        this.aimsAppSubCategoryId = aimsAppSubCategoryId;
    }

    public java.util.Collection getAllSubCategories()
    {
        return this.allSubCategories;
    }

    public void setAllSubCategories(java.util.Collection allSubCategories)
    {
        this.allSubCategories = allSubCategories;
    }

    public String[] getListAvailableDevices()
    {
        return this.listAvailableDevices;
    }

    public void setListAvailableDevices(String[] listAvailableDevices)
    {
        this.listAvailableDevices = listAvailableDevices;
    }

    public java.util.Collection getAvailableDevices()
    {
        return this.availableDevices;
    }

    public void setAvailableDevices(java.util.Collection availableDevices)
    {
        this.availableDevices = availableDevices;
    }

    public String[] getListSelectedDevices()
    {
        return this.listSelectedDevices;
    }

    public void setListSelectedDevices(String[] listSelectedDevices)
    {
        this.listSelectedDevices = listSelectedDevices;
    }

    public java.util.Collection getSelectedDevices()
    {
        return this.selectedDevices;
    }

    public void setSelectedDevices(java.util.Collection selectedDevices)
    {
        this.selectedDevices = selectedDevices;
    }

    public java.lang.String getTask()
    {
        return this.task;
    }

    public void setTask(java.lang.String task)
    {
        this.task = task;
    }

    public java.lang.String getBrewAppType()
    {
        return brewAppType;
    }

    public void setBrewAppType(java.lang.String string)
    {
        brewAppType = string;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        try
        {
            this.allCategories = AimsApplicationsManager.getCategories();
            this.allSubCategories = AimsApplicationsManager.getSubCategories();
            this.availableDevices = AimsDevicesManager.getDevices();
            this.allPlatforms = AimsDevicesManager.getPlatforms();
            this.allContracts = AimsApplicationsManager.getContracts();
            this.allPhases = AimsApplicationsManager.getPhases(((AimsUser) (request.getSession().getAttribute(AimsConstants.AIMS_USER))).getUserType());
            request.setAttribute("ApplicationSearchForm", this);

        }
        catch (Exception ex)
        {
            log.debug("Exception in	RESET: " + ex);

        }

    }

}
