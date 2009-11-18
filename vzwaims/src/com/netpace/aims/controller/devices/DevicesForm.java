package com.netpace.aims.controller.devices;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.bo.core.AimsTypeManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.AimsConstants;

/**
 * @struts.form name="DevicesForm"
 */
public class DevicesForm extends BaseValidatorForm
{

    /** identifier field */
    private java.lang.String deviceId;

    /** persistent field */
    private java.lang.String deviceModel;

    /** nullable persistent field */
    private java.lang.String status;

    /** nullable persistent field */
    private java.lang.String deviceMfgBy;

    /** nullable persistent field */
    private java.lang.String mfgWebSiteUrl;

    /** nullable persistent field */
    private java.lang.String deviceAlert;

    /** nullable persistent field */
    private java.lang.String deviceAlertMessage;

    /** nullable persistent field */
    private java.lang.String lbsSupported;

    /** nullable persistent field */
    private java.lang.String createdBy;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** nullable persistent field */
    private java.lang.String lastUpdatedBy;

    /** nullable persistent field */
    private java.util.Date lastUpdatedDate;

    /** nullable persistent field */
    private java.lang.Long pid1Id;

    /** nullable persistent field */
    private java.lang.String primaryPID1;

    /** nullable persistent field */
    private java.lang.String secondaryPID11;

    /** nullable persistent field */
    private java.lang.String secondaryPID12;

    /** nullable persistent field */
    private java.lang.String secondaryPID13;

    private java.util.Collection addedPlatforms;

    private String[] platform;

    private String[] allplatforms;
    private String taskPerform;


    private java.lang.Long assetType;
    private java.util.Collection allDeviceAssetTypes;

    private java.lang.String mportalDeviceName;


    public java.lang.String getDeviceId()
    {
        return this.deviceId;
    }

    public void setDeviceId(java.lang.String deviceId)
    {
        this.deviceId = deviceId;
    }

    public java.lang.String getDeviceModel()
    {
        return this.deviceModel;
    }

    /**
     * @struts.validator type="required"
     */
    public void setDeviceModel(java.lang.String deviceModel)
    {
        this.deviceModel = deviceModel;
    }

    public java.lang.String getStatus()
    {
        return this.status;
    }

    public void setStatus(java.lang.String status)
    {
        this.status = status;
    }

    public java.lang.String getDeviceMfgBy()
    {
        return this.deviceMfgBy;
    }

    public void setDeviceMfgBy(java.lang.String deviceMfgBy)
    {
        this.deviceMfgBy = deviceMfgBy;
    }

    public java.lang.String getMfgWebSiteUrl()
    {
        return this.mfgWebSiteUrl;
    }

    public void setMfgWebSiteUrl(java.lang.String mfgWebSiteUrl)
    {
        this.mfgWebSiteUrl = mfgWebSiteUrl;
    }

    public java.lang.String getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy)
    {
        this.createdBy = createdBy;
    }

    public java.util.Date getCreatedDate()
    {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate)
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

    public java.util.Date getLastUpdatedDate()
    {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(java.util.Date lastUpdatedDate)
    {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        this.taskPerform = request.getParameter("task");
        try {
            this.allDeviceAssetTypes = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_DEVICE_ASSET_TYPE);
        }
        catch (HibernateException he) {
            he.printStackTrace();
        }
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        Collection aimsPlatforms = null;
        boolean vzAppZonePlatformSelected = false;

        try
        {
            aimsPlatforms = AimsDevicesManager.getPlatforms();
        }
        catch (HibernateException ex)
        {}

        if (this.isBlankString(this.deviceModel))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceForm.deviceModel"));

        }
        if (this.platform == null || this.platform.length < 1 || "-1".equals(this.platform[0]))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceForm.vzwPlatform"));
        }
        else
        {
            Collection collection = new ArrayList();
            AimsPlatform aimsPlatform = null;

            for (int i = 0; this.platform.length > i; i++)
            {
                aimsPlatform = new AimsPlatform();
                aimsPlatform.setPlatformId(Utility.convertToLong(platform[i]));
                aimsPlatform.setPlatformName(VOLookupFactory.getInstance().getAimsPlatform(Utility.convertToLong(platform[i])).getPlatformName());
                collection.add(aimsPlatform);

                if(Utility.convertToLong(platform[i]).equals(AimsConstants.VZAPPZONE_PLATFORM_ID)) {
                    vzAppZonePlatformSelected = true;
                }

            }
            setAddedPlatforms(collection);

            //if vzappzone platform is selected, then assetType and mPortal Device name is required
            if(vzAppZonePlatformSelected) {
                if(!isDropDownSelected(this.assetType)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceForm.assetType.required"));
                }
                if(isBlankString(this.mportalDeviceName)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceForm.mportalDeviceName.required"));
                }
            }
        }
        if (((this.deviceAlert == null) || (this.deviceAlert.equals("Y"))) && ((this.deviceAlertMessage == null) || (this.deviceAlertMessage.length() < 1)))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceForm.alertMessage"));
        }

        if (this.lbsSupported == null)
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceForm.lbsSupported"));
        }

        if (isBlankString(this.primaryPID1)){
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceForm.primaryPID1"));
        }
        request.setAttribute("AimsPlatforms", MiscUtils.filterPlatformCollection(aimsPlatforms, getAddedPlatforms()));

        return errors;
    }

    public String[] getAllplatforms()
    {
        return allplatforms;
    }

    public void setAllplatforms(String[] allplatforms)
    {
        this.allplatforms = allplatforms;
    }

    public String[] getPlatform()
    {
        return platform;
    }

    public void setPlatform(String[] platform)
    {
        this.platform = platform;
    }

    public java.util.Collection getAddedPlatforms()
    {
        return addedPlatforms;
    }

    public void setAddedPlatforms(java.util.Collection addedPlatforms)
    {
        this.addedPlatforms = addedPlatforms;
    }

    public String getTaskPerform()
    {
        return taskPerform;
    }

    public void setTaskPerform(String taskPerform)
    {
        this.taskPerform = taskPerform;
    }

    public java.lang.String getDeviceAlert()
    {
        return deviceAlert;
    }

    public java.lang.String getDeviceAlertMessage()
    {
        return deviceAlertMessage;
    }

    public void setDeviceAlert(java.lang.String string)
    {
        deviceAlert = string;
    }

    public void setDeviceAlertMessage(java.lang.String string)
    {
        deviceAlertMessage = string;
    }

    public java.lang.String getLbsSupported()
    {
        return lbsSupported;
    }

    public void setLbsSupported(java.lang.String string)
    {
        lbsSupported = string;
    }

    public java.lang.Long getPid1Id() {
        return pid1Id;
    }

    public void setPid1Id(java.lang.Long pid1Id) {
        this.pid1Id = pid1Id;
    }

    public java.lang.String getPrimaryPID1() {
        return primaryPID1;
    }

    public void setPrimaryPID1(java.lang.String primaryPID1) {
        this.primaryPID1 = primaryPID1;
    }

    public java.lang.String getSecondaryPID11() {
        return secondaryPID11;
    }

    public void setSecondaryPID11(java.lang.String secondaryPID11) {
        this.secondaryPID11 = secondaryPID11;
    }

    public java.lang.String getSecondaryPID12() {
        return secondaryPID12;
    }

    public void setSecondaryPID12(java.lang.String secondaryPID12) {
        this.secondaryPID12 = secondaryPID12;
    }

    public java.lang.String getSecondaryPID13() {
        return secondaryPID13;
    }

    public void setSecondaryPID13(java.lang.String secondaryPID13) {
        this.secondaryPID13 = secondaryPID13;
    }

    public Long getAssetType() {
        return assetType;
    }

    public void setAssetType(Long assetType) {
        this.assetType = assetType;
    }

    public Collection getAllDeviceAssetTypes() {
        return allDeviceAssetTypes;
    }

    public void setAllDeviceAssetTypes(Collection allDeviceAssetTypes) {
        this.allDeviceAssetTypes = allDeviceAssetTypes;
    }

    public String getMportalDeviceName() {
        return mportalDeviceName;
    }

    public void setMportalDeviceName(String mportalDeviceName) {
        this.mportalDeviceName = mportalDeviceName;
    }

}
