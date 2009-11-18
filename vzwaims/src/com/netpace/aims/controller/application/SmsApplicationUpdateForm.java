package com.netpace.aims.controller.application;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.util.Utility;

/**
 * @struts.form	name="SmsApplicationUpdateForm"
 */
public class SmsApplicationUpdateForm extends ApplicationUpdateForm
{

    static Logger log = Logger.getLogger(SmsApplicationUpdateForm.class.getName());

    private java.lang.Long smsAppsId;
    private java.lang.String campaignClassification;
    private java.lang.String campaignStartDate;
    private java.lang.String campaignEndDate;
    private java.lang.String carrierSupport;
    private java.lang.String wholesalePrice;
    private java.lang.String retailPrice;
    private java.lang.String avgMsgsPerSec;
    private java.lang.String peakMsgsPerSec;
    private java.lang.String ifVzwDirectConn;
    private java.lang.String connType;
    private java.lang.String contentProvider;
    private java.lang.String campaignDesc;
    private java.lang.String campaignPromoInfo;
    private java.lang.String shortCodesReqd;

    private FormFile messageFlow;
    private java.lang.String messageFlowFileName;
    private java.lang.Long messageFlowTempFileId;

    public java.lang.Long getSmsAppsId()
    {
        return this.smsAppsId;
    }

    public void setSmsAppsId(java.lang.Long smsAppsId)
    {
        this.smsAppsId = smsAppsId;
    }

    public java.lang.String getCampaignClassification()
    {
        return this.campaignClassification;
    }

    public void setCampaignClassification(java.lang.String campaignClassification)
    {
        this.campaignClassification = campaignClassification;
    }

    public java.lang.String getCampaignStartDate()
    {
        return this.campaignStartDate;
    }

    public void setCampaignStartDate(java.lang.String campaignStartDate)
    {
        this.campaignStartDate = campaignStartDate;
    }

    public java.lang.String getCampaignEndDate()
    {
        return this.campaignEndDate;
    }

    public void setCampaignEndDate(java.lang.String campaignEndDate)
    {
        this.campaignEndDate = campaignEndDate;
    }

    public java.lang.String getCarrierSupport()
    {
        return this.carrierSupport;
    }

    public void setCarrierSupport(java.lang.String carrierSupport)
    {
        this.carrierSupport = carrierSupport;
    }

    public java.lang.String getWholesalePrice()
    {
        return this.wholesalePrice;
    }

    public void setWholesalePrice(java.lang.String wholesalePrice)
    {
        this.wholesalePrice = wholesalePrice;
    }

    public java.lang.String getRetailPrice()
    {
        return this.retailPrice;
    }

    public void setRetailPrice(java.lang.String retailPrice)
    {
        this.retailPrice = retailPrice;
    }

    public java.lang.String getAvgMsgsPerSec()
    {
        return this.avgMsgsPerSec;
    }

    public void setAvgMsgsPerSec(java.lang.String avgMsgsPerSec)
    {
        this.avgMsgsPerSec = avgMsgsPerSec;
    }

    public java.lang.String getPeakMsgsPerSec()
    {
        return this.peakMsgsPerSec;
    }

    public void setPeakMsgsPerSec(java.lang.String peakMsgsPerSec)
    {
        this.peakMsgsPerSec = peakMsgsPerSec;
    }

    public java.lang.String getIfVzwDirectConn()
    {
        return this.ifVzwDirectConn;
    }

    public void setIfVzwDirectConn(java.lang.String ifVzwDirectConn)
    {
        this.ifVzwDirectConn = ifVzwDirectConn;
    }

    public java.lang.String getConnType()
    {
        return this.connType;
    }

    public void setConnType(java.lang.String connType)
    {
        this.connType = connType;
    }

    public java.lang.String getContentProvider()
    {
        return this.contentProvider;
    }

    public void setContentProvider(java.lang.String contentProvider)
    {
        this.contentProvider = contentProvider;
    }

    public java.lang.String getCampaignDesc()
    {
        return this.campaignDesc;
    }

    public void setCampaignDesc(java.lang.String campaignDesc)
    {
        this.campaignDesc = campaignDesc;
    }

    public java.lang.String getCampaignPromoInfo()
    {
        return this.campaignPromoInfo;
    }

    public void setCampaignPromoInfo(java.lang.String campaignPromoInfo)
    {
        this.campaignPromoInfo = campaignPromoInfo;
    }

    public java.lang.String getShortCodesReqd()
    {
        return this.shortCodesReqd;
    }

    public void setShortCodesReqd(java.lang.String shortCodesReqd)
    {
        this.shortCodesReqd = shortCodesReqd;
    }

    public FormFile getMessageFlow()
    {
        return this.messageFlow;
    }

    public void setMessageFlow(FormFile messageFlow)
    {
        this.messageFlow = messageFlow;
    }

    public java.lang.String getMessageFlowFileName()
    {
        return this.messageFlowFileName;
    }

    public void setMessageFlowFileName(java.lang.String messageFlowFileName)
    {
        this.messageFlowFileName = messageFlowFileName;
    }

    public java.lang.Long getMessageFlowTempFileId()
    {
        return this.messageFlowTempFileId;
    }

    public void setMessageFlowTempFileId(java.lang.Long messageFlowTempFileId)
    {
        this.messageFlowTempFileId = messageFlowTempFileId;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        try
        {
            log.debug("RESET called	in SMSApplicationUpdateForm");
            super.reset(mapping, request, AimsConstants.SMS_PLATFORM_ID);
        }
        catch (Exception ex)
        {
            log.debug("Exception in	RESET: " + ex);
        }
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String sessionId = session.getId();
        Date campStartDate = null;
        Date campEndDate = null;

        super.saveTempFiles(request, sessionId, currUserId, errors, AimsConstants.SMS_PLATFORM_ID); //Calling Parent	Class	method
        saveTempSmsFiles(request, sessionId, currUserId, errors);

        super.validate(mapping, request, errors, AimsConstants.SMS_PLATFORM_ID); //Calling	Parent Class method

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {
            //Common to	Save and Submit

            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                //Float
                if (!this.isBlankString(this.wholesalePrice))
                    if (!this.isDecimal(this.wholesalePrice))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.wholesalePrice.long", this.wholesalePrice));

                if (!this.isBlankString(this.retailPrice))
                    if (!this.isDecimal(this.retailPrice))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.retailPrice.long", this.retailPrice));

                if (!this.isBlankString(this.avgMsgsPerSec))
                    if (!this.isDecimal(this.avgMsgsPerSec))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.avgMsgsPerSec.long", this.avgMsgsPerSec));

                if (!this.isBlankString(this.peakMsgsPerSec))
                    if (!this.isDecimal(this.peakMsgsPerSec))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.peakMsgsPerSec.long", this.peakMsgsPerSec));

                //Date
                if (!this.isBlankString(this.campaignStartDate))
                    if (!this.isDate(this.campaignStartDate))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignStartDate.date", this.campaignStartDate));
                    else
                        campStartDate = Utility.convertToDate(this.campaignStartDate, AimsConstants.DATE_FORMAT);

                if (!this.isBlankString(this.campaignEndDate))
                    if (!this.isDate(this.campaignEndDate))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignEndDate.date", this.campaignEndDate));
                    else
                        campEndDate = Utility.convertToDate(this.campaignEndDate, AimsConstants.DATE_FORMAT);

                //Start	of File	Type Validations
                if (!(this.isBlankString(this.messageFlowFileName)))
                    if (!(this.isValidFileType(this.messageFlowFileName, AimsConstants.FILE_TYPE_DOC)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.messageFlow.file.type"));

                //End	of File	Type Validations

            }
        }

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                if (this.isBlankString(this.campaignClassification))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignClassification.required"));
                else if (this.campaignClassification.equals("P"))
                {
                    if (this.isBlankString(this.wholesalePrice))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.wholesalePrice.required"));
                    if (this.isBlankString(this.retailPrice))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.retailPrice.required"));
                }

                if (this.isBlankString(this.ifVzwDirectConn))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.ifVzwDirectConn.required"));
                else if ((this.isBlankString(this.connType)) && (this.ifVzwDirectConn.equals("Y")))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.connType.required"));

                if (this.isBlankString(this.carrierSupport))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.carrierSupport.required"));
                if (this.isBlankString(this.contentProvider))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.contentProvider.required"));
                /*DUPLICATE://
                if (this.isBlankString(this.campaignDesc))
                	errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.sms.app.campaignDesc.required"));
                */
                if (this.isBlankString(this.campaignPromoInfo))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignPromoInfo.required"));
                if (this.isBlankString(this.shortCodesReqd))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.shortCodesReqd.required"));

                if (this.isBlankString(this.avgMsgsPerSec))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.avgMsgsPerSec.required"));
                if (this.isBlankString(this.peakMsgsPerSec))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.peakMsgsPerSec.required"));

                //Start	of Dates Section
                if (this.isBlankString(this.campaignStartDate))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignStartDate.required"));
                else
                {
                    if (!this.isDate(this.campaignStartDate))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignStartDate.date", this.campaignStartDate));
                    else
                        campStartDate = Utility.convertToDate(this.campaignStartDate, AimsConstants.DATE_FORMAT);
                }

                if (this.isBlankString(this.campaignEndDate))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignEndDate.required"));
                else
                {
                    if (!this.isDate(this.campaignEndDate))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignEndDate.date", this.campaignEndDate));
                    else
                        campEndDate = Utility.convertToDate(this.campaignEndDate, AimsConstants.DATE_FORMAT);
                }

                if (campStartDate != null)
                    if (campStartDate.compareTo(new Date()) < 0)
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignStartDate.smaller"));
                if (campEndDate != null)
                    if (campEndDate.compareTo(campStartDate) < 0)
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.campaignEndDate.smaller"));

                //End	of Dates Section

                if (((this.messageFlow == null) || !(this.messageFlow.getFileSize() > 0)) && (this.isBlankString(this.messageFlowFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.messageFlow.required"));

            }
        }

        return errors;

    }

    public void saveTempSmsFiles(HttpServletRequest request, String sessionId, String currUserId, ActionErrors errors)
    {
        log.debug("\n\nIn	saveTempSmsFiles of	SmsApplicationUpdateForm:	\n\n");
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        TempFile tempFile = null;

        try
        {

            if ((this.messageFlow != null) && (this.messageFlow.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.messageFlow.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.sms.app.messageFlow.fileSize.exceeded"));
                this.messageFlow.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.messageFlow, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.messageFlowTempFileId = tempFile.getFileId();
                    this.messageFlowFileName = tempFile.getFileName();
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving	temp files IN	saveTempFiles()	of SmsApplicationUpdateForm");
        }
        finally
        {
            log.debug("Finally called	IN saveTempFiles() of	SmsApplicationUpdateForm");
        }

    }

}
