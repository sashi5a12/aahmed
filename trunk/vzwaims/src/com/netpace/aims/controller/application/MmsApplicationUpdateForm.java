package com.netpace.aims.controller.application;

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

/**
 * @struts.form	name="MmsApplicationUpdateForm"
 */
public class MmsApplicationUpdateForm extends ApplicationUpdateForm
{

    static Logger log = Logger.getLogger(MmsApplicationUpdateForm.class.getName());

    private java.lang.Long mmsAppsId;
    private java.lang.String contentProvider;
    private java.lang.String programPromoInfo;
    private java.lang.String targetedLaunchDate;
    private java.lang.String expectedMsgTraffic;

    private FormFile sampleContent;
    private String sampleContentFileName;
    private java.lang.Long sampleContentTempFileId;

    public java.lang.Long getMmsAppsId()
    {
        return this.mmsAppsId;
    }

    public void setMmsAppsId(java.lang.Long mmsAppsId)
    {
        this.mmsAppsId = mmsAppsId;
    }

    public java.lang.String getContentProvider()
    {
        return this.contentProvider;
    }

    public void setContentProvider(java.lang.String contentProvider)
    {
        this.contentProvider = contentProvider;
    }

    public java.lang.String getProgramPromoInfo()
    {
        return this.programPromoInfo;
    }

    public void setProgramPromoInfo(java.lang.String programPromoInfo)
    {
        this.programPromoInfo = programPromoInfo;
    }

    public java.lang.String getTargetedLaunchDate()
    {
        return this.targetedLaunchDate;
    }

    public void setTargetedLaunchDate(java.lang.String targetedLaunchDate)
    {
        this.targetedLaunchDate = targetedLaunchDate;
    }

    public java.lang.String getExpectedMsgTraffic()
    {
        return this.expectedMsgTraffic;
    }

    public void setExpectedMsgTraffic(java.lang.String expectedMsgTraffic)
    {
        this.expectedMsgTraffic = expectedMsgTraffic;
    }

    public FormFile getSampleContent()
    {
        return this.sampleContent;
    }

    public void setSampleContent(FormFile sampleContent)
    {
        this.sampleContent = sampleContent;
    }

    public java.lang.String getSampleContentFileName()
    {
        return this.sampleContentFileName;
    }

    public void setSampleContentFileName(java.lang.String sampleContentFileName)
    {
        this.sampleContentFileName = sampleContentFileName;
    }

    public java.lang.Long getSampleContentTempFileId()
    {
        return this.sampleContentTempFileId;
    }

    public void setSampleContentTempFileId(java.lang.Long sampleContentTempFileId)
    {
        this.sampleContentTempFileId = sampleContentTempFileId;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        Long currentUserAllianceId = ((AimsUser) (request.getSession().getAttribute(AimsConstants.AIMS_USER))).getAimsAllianc();

        try
        {
            super.reset(mapping, request, AimsConstants.MMS_PLATFORM_ID);
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

        super.saveTempFiles(request, sessionId, currUserId, errors, AimsConstants.MMS_PLATFORM_ID); //Calling Parent Class method
        saveTempMmsFiles(request, sessionId, currUserId, errors);

        super.validate(mapping, request, errors, AimsConstants.MMS_PLATFORM_ID); //Calling Parent Class method

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                //Long
                if (!this.isBlankString(this.expectedMsgTraffic))
                    if (!this.isLong(this.expectedMsgTraffic))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mms.app.expectedMsgTraffic.long", this.expectedMsgTraffic));

                //Date
                if (!this.isBlankString(this.targetedLaunchDate))
                    if (!this.isDate(this.targetedLaunchDate))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mms.app.targetedLaunchDate.date", this.targetedLaunchDate));

                //Start	of File	Type Validations
                if (!(this.isBlankString(this.sampleContentFileName)))
                    if (!(this.isValidFileType(this.sampleContentFileName, AimsConstants.FILE_TYPE_DOC)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mms.app.sampleContentFile.file.type"));

                //End of File	Type Validations
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
                if (this.isBlankString(this.contentProvider))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mms.app.contentProvider.required"));
                if (this.isBlankString(this.programPromoInfo))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mms.app.programPromoInfo.required"));
                if (this.isBlankString(this.targetedLaunchDate))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mms.app.targetedLaunchDate.required"));
                if (this.isBlankString(this.expectedMsgTraffic))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mms.app.expectedMsgTraffic.required"));

                //Files (NULL) Validation
                if (((this.sampleContent == null) || !(this.sampleContent.getFileSize() > 0)) && (this.isBlankString(this.sampleContentFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mms.app.sampleContentFile.required"));
            }
        }

        return errors;

    }

    public void saveTempMmsFiles(HttpServletRequest request, String sessionId, String currUserId, ActionErrors errors)
    {
        log.debug("\n\nIn saveTempMmsFiles of MmsApplicationUpdateForm: \n\n");
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        TempFile tempFile = null;

        try
        {
            if ((this.sampleContent != null) && (this.sampleContent.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.sampleContent.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mms.app.sampleContentFile.fileSize.exceeded"));
                this.sampleContent.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.sampleContent, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.sampleContentTempFileId = tempFile.getFileId();
                    this.sampleContentFileName = tempFile.getFileName();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempMmsFiles() of MmsApplicationUpdateForm");
        }
        finally
        {
            log.debug("Finally called IN saveTempMmsFiles() of MmsApplicationUpdateForm");
        }

    }

}
