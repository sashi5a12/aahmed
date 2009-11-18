package com.netpace.aims.controller.devices;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="DevicesEditForm"
 */
public class DevicesEditForm extends BaseValidatorForm
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

    /** persistent field */
    private String[] platform;

    private String[] allplatforms;
    private String markAs;

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

    public String[] getPlatform()
    {
        return this.platform;
    }

    public void setPlatform(String[] platform)
    {
        this.platform = platform;
    }

    public String[] getAllplatforms()
    {
        return this.allplatforms;
    }

    public void setAllplatforms(String[] allplatforms)
    {
        this.allplatforms = allplatforms;
    }
    public String getMarkAs()
    {
        return markAs;
    }
    public void setMarkAs(String markAs)
    {
        this.markAs = markAs;
    }

}
