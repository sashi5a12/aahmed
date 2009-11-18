package com.netpace.aims.controller.newmarketing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.TempFile;

/**
 * @struts.form name="MarketingContentUpdateForm"
 */
public class MarketingContentUpdateForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(MarketingContentUpdateForm.class.getName());

    private java.lang.String tempForRadioIssue; //This property has been added only as a workaround for an issue with Special Characters and Radio Buttons
    private java.lang.String task;
    private java.lang.String appSubmitType;

    private java.lang.Long creativeContentId;
    private java.lang.String contentTitle;
    private java.lang.String contentDescription;
    private java.lang.String applicationTitle;
    private java.lang.String contentUsagePermission;
    private java.lang.String status;
    private java.lang.Long userId;
    private java.lang.String approvalDate;
    private java.lang.String expiryDate;
    private java.lang.String evaluationComments;

    //Files
    private FormFile publisherLogo;
    private FormFile appTitleGraphic;
    private FormFile splashScreen;
    private FormFile activeScreen;
    private FormFile screenJpeg1;
    private FormFile screenJpeg2;
    private FormFile screenJpeg3;
    private FormFile screenJpeg4;
    private FormFile screenJpeg5;
    private FormFile videoFile;
    private FormFile appLogoBwSmall;
    private FormFile appLogoBwLarge;
    private FormFile appLogoClrsmall;
    private FormFile appLogoClrlarge;

    private java.lang.String publisherLogoFileName;
    private java.lang.String appTitleGraphicFileName;
    private java.lang.String splashScreenFileName;
    private java.lang.String activeScreenFileName;
    private java.lang.String screenJpeg1FileName;
    private java.lang.String screenJpeg2FileName;
    private java.lang.String screenJpeg3FileName;
    private java.lang.String screenJpeg4FileName;
    private java.lang.String screenJpeg5FileName;
    private java.lang.String videoFileFileName;
    private java.lang.String appLogoBwSmallFileName;
    private java.lang.String appLogoBwLargeFileName;
    private java.lang.String appLogoClrsmallFileName;
    private java.lang.String appLogoClrlargeFileName;

    private java.lang.Long publisherLogoTempFileId;
    private java.lang.Long appTitleGraphicTempFileId;
    private java.lang.Long splashScreenTempFileId;
    private java.lang.Long activeScreenTempFileId;
    private java.lang.Long screenJpeg1TempFileId;
    private java.lang.Long screenJpeg2TempFileId;
    private java.lang.Long screenJpeg3TempFileId;
    private java.lang.Long screenJpeg4TempFileId;
    private java.lang.Long screenJpeg5TempFileId;
    private java.lang.Long videoFileTempFileId;
    private java.lang.Long appLogoBwSmallTempFileId;
    private java.lang.Long appLogoBwLargeTempFileId;
    private java.lang.Long appLogoClrsmallTempFileId;
    private java.lang.Long appLogoClrlargeTempFileId;

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        request.setAttribute("MarketingContentUpdateForm", this);
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();

        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String sessionId = session.getId();
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);

        //Save Files to AIMS_TEMP_FILES table
        saveTempMarketingFiles(currUserId, sessionId, fileSize, errors);

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {

            //Check to see if data is compatible with DB fields
            validateToDBFields(currUserType, errors);

            if (this.isBlankString(this.contentTitle))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.contentTitle.required"));

            if (this.isBlankString(this.contentDescription))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.contentDescription.required"));

            //Only For Alliance Users
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                //Start of File Type Validations

                if (!(this.isBlankString(this.publisherLogoFileName)))
                    if (!(this.isValidFileType(this.publisherLogoFileName, AimsConstants.FILE_TYPE_EPS_ONLY)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.publisherLogo.file.type"));

                if (!(this.isBlankString(this.appTitleGraphicFileName)))
                    if (!(this.isValidFileType(this.appTitleGraphicFileName, AimsConstants.FILE_TYPE_EPS_ONLY)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appTitleGraphic.file.type"));

                if (!(this.isBlankString(this.splashScreenFileName)))
                    if (!(this.isValidFileType(this.splashScreenFileName, AimsConstants.FILE_TYPE_EPS)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.splashScreen.file.type"));

                if (!(this.isBlankString(this.activeScreenFileName)))
                    if (!(this.isValidFileType(this.activeScreenFileName, AimsConstants.FILE_TYPE_EPS)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.activeScreen.file.type"));

                if (!(this.isBlankString(this.screenJpeg1FileName)))
                    if (!(this.isValidFileType(this.screenJpeg1FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg1.file.type"));

                if (!(this.isBlankString(this.screenJpeg2FileName)))
                    if (!(this.isValidFileType(this.screenJpeg2FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg2.file.type"));

                if (!(this.isBlankString(this.screenJpeg3FileName)))
                    if (!(this.isValidFileType(this.screenJpeg3FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg3.file.type"));

                if (!(this.isBlankString(this.screenJpeg4FileName)))
                    if (!(this.isValidFileType(this.screenJpeg4FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg4.file.type"));

                if (!(this.isBlankString(this.screenJpeg5FileName)))
                    if (!(this.isValidFileType(this.screenJpeg5FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg5.file.type"));

                if (!(this.isBlankString(this.videoFileFileName)))
                    if (!(this.isValidFileType(this.videoFileFileName, AimsConstants.FILE_TYPE_VIDEO_CLIP)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.videoFile.file.type"));

                if (!(this.isBlankString(this.appLogoBwSmallFileName)))
                    if (!(this.isValidFileType(this.appLogoBwSmallFileName, AimsConstants.FILE_TYPE_IMAGE)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appLogoBwSmall.file.type"));

                if (!(this.isBlankString(this.appLogoBwLargeFileName)))
                    if (!(this.isValidFileType(this.appLogoBwLargeFileName, AimsConstants.FILE_TYPE_IMAGE)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appLogoBwLarge.file.type"));

                if (!(this.isBlankString(this.appLogoClrsmallFileName)))
                    if (!(this.isValidFileType(this.appLogoClrsmallFileName, AimsConstants.FILE_TYPE_IMAGE)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appLogoClrsmall.file.type"));

                if (!(this.isBlankString(this.appLogoClrlargeFileName)))
                    if (!(this.isValidFileType(this.appLogoClrlargeFileName, AimsConstants.FILE_TYPE_IMAGE)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appLogoClrlarge.file.type"));

                //End of File Type Validations
            }
        }

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {

            if (this.isBlankString(this.contentUsagePermission))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.contentUsagePermission.required"));

            //The following properties will be validated for an can only be updated by an Alliance user and not the Verizon user
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                //Start   of Files (NULL) Validation
                if (((this.splashScreen == null) || !(this.splashScreen.getFileSize() > 0)) && (this.isBlankString(this.splashScreenFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.splashScreen.required"));

                if (((this.screenJpeg1 == null) || !(this.screenJpeg1.getFileSize() > 0)) && (this.isBlankString(this.screenJpeg1FileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg1.required"));
                //End of Files (NULL) Validation

            }

            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                if (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                {}
            }
        }

        return errors;
    }

    public void validateToDBFields(String currUserType, ActionErrors errors)
    {
        if ((this.contentTitle != null) && (this.contentTitle.length() > 40))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.contentTitle.length"));

        if ((this.applicationTitle != null) && (this.applicationTitle.length() > 150))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.applicationTitle.length"));

        if ((this.contentDescription != null) && (this.contentDescription.length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.contentDescription.length"));

        if ((this.evaluationComments != null) && (this.evaluationComments.length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.evaluationComments.length"));

        if (currUserType.equals(AimsConstants.VZW_USERTYPE))
        {
            if (!this.isBlankString(this.expiryDate))
                if (!this.isDate(this.expiryDate))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.expiryDate.date", this.expiryDate));
        }
    }

    public void saveTempMarketingFiles(String currUserId, String sessionId, String fileSize, ActionErrors errors)
    {
        TempFile tempFile = null;

        try
        {

            if ((this.publisherLogo != null) && (this.publisherLogo.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.publisherLogo.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.publisherLogo.fileSize.exceeded"));
                this.publisherLogo.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.publisherLogo, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.publisherLogoTempFileId = tempFile.getFileId();
                    this.publisherLogoFileName = tempFile.getFileName();
                }
            }

            if ((this.appTitleGraphic != null)
                && (this.appTitleGraphic.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.appTitleGraphic.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appTitleGraphic.fileSize.exceeded"));
                this.appTitleGraphic.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appTitleGraphic, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appTitleGraphicTempFileId = tempFile.getFileId();
                    this.appTitleGraphicFileName = tempFile.getFileName();
                }
            }

            if ((this.splashScreen != null) && (this.splashScreen.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.splashScreen.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.splashScreen.fileSize.exceeded"));
                this.splashScreen.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.splashScreen, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.splashScreenTempFileId = tempFile.getFileId();
                    this.splashScreenFileName = tempFile.getFileName();
                }
            }

            if ((this.activeScreen != null) && (this.activeScreen.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.activeScreen.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.activeScreen.fileSize.exceeded"));
                this.activeScreen.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.activeScreen, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.activeScreenTempFileId = tempFile.getFileId();
                    this.activeScreenFileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg1 != null) && (this.screenJpeg1.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg1.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg1.fileSize.exceeded"));
                this.screenJpeg1.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg1, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpeg1TempFileId = tempFile.getFileId();
                    this.screenJpeg1FileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg2 != null) && (this.screenJpeg2.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg2.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg2.fileSize.exceeded"));
                this.screenJpeg2.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg2, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpeg2TempFileId = tempFile.getFileId();
                    this.screenJpeg2FileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg3 != null) && (this.screenJpeg3.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg3.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg3.fileSize.exceeded"));
                this.screenJpeg3.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg3, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpeg3TempFileId = tempFile.getFileId();
                    this.screenJpeg3FileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg4 != null) && (this.screenJpeg4.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg4.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg4.fileSize.exceeded"));
                this.screenJpeg4.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg4, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpeg4TempFileId = tempFile.getFileId();
                    this.screenJpeg4FileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg5 != null) && (this.screenJpeg5.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg5.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.screenJpeg5.fileSize.exceeded"));
                this.screenJpeg5.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg5, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpeg5TempFileId = tempFile.getFileId();
                    this.screenJpeg5FileName = tempFile.getFileName();
                }
            }

            if ((this.videoFile != null) && (this.videoFile.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.videoFile.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.videoFile.fileSize.exceeded"));
                this.videoFile.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.videoFile, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.videoFileTempFileId = tempFile.getFileId();
                    this.videoFileFileName = tempFile.getFileName();
                }
            }

            if ((this.appLogoBwSmall != null)
                && (this.appLogoBwSmall.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.appLogoBwSmall.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appLogoBwSmall.fileSize.exceeded"));
                this.appLogoBwSmall.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appLogoBwSmall, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appLogoBwSmallTempFileId = tempFile.getFileId();
                    this.appLogoBwSmallFileName = tempFile.getFileName();
                }
            }

            if ((this.appLogoBwLarge != null)
                && (this.appLogoBwLarge.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.appLogoBwLarge.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appLogoBwLarge.fileSize.exceeded"));
                this.appLogoBwLarge.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appLogoBwLarge, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appLogoBwLargeTempFileId = tempFile.getFileId();
                    this.appLogoBwLargeFileName = tempFile.getFileName();
                }
            }

            if ((this.appLogoClrsmall != null)
                && (this.appLogoClrsmall.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.appLogoClrsmall.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appLogoClrsmall.fileSize.exceeded"));
                this.appLogoClrsmall.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appLogoClrsmall, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appLogoClrsmallTempFileId = tempFile.getFileId();
                    this.appLogoClrsmallFileName = tempFile.getFileName();
                }
            }

            if ((this.appLogoClrlarge != null)
                && (this.appLogoClrlarge.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.appLogoClrlarge.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.mkt.content.appLogoClrlarge.fileSize.exceeded"));
                this.appLogoClrlarge.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appLogoClrlarge, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appLogoClrlargeTempFileId = tempFile.getFileId();
                    this.appLogoClrlargeFileName = tempFile.getFileName();
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempMarketingFiles() of MarketingContentUpdateForm");
        }
        finally
        {
            log.debug("Finally called IN saveTempMarketingFiles() of MarketingContentUpdateForm");
        }

    }

    /**
     * @return
     */
    public FormFile getActiveScreen()
    {
        return activeScreen;
    }

    /**
     * @return
     */
    public java.lang.String getActiveScreenFileName()
    {
        return activeScreenFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getActiveScreenTempFileId()
    {
        return activeScreenTempFileId;
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
    public FormFile getAppLogoBwLarge()
    {
        return appLogoBwLarge;
    }

    /**
     * @return
     */
    public java.lang.String getAppLogoBwLargeFileName()
    {
        return appLogoBwLargeFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getAppLogoBwLargeTempFileId()
    {
        return appLogoBwLargeTempFileId;
    }

    /**
     * @return
     */
    public FormFile getAppLogoBwSmall()
    {
        return appLogoBwSmall;
    }

    /**
     * @return
     */
    public java.lang.String getAppLogoBwSmallFileName()
    {
        return appLogoBwSmallFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getAppLogoBwSmallTempFileId()
    {
        return appLogoBwSmallTempFileId;
    }

    /**
     * @return
     */
    public FormFile getAppLogoClrlarge()
    {
        return appLogoClrlarge;
    }

    /**
     * @return
     */
    public java.lang.String getAppLogoClrlargeFileName()
    {
        return appLogoClrlargeFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getAppLogoClrlargeTempFileId()
    {
        return appLogoClrlargeTempFileId;
    }

    /**
     * @return
     */
    public FormFile getAppLogoClrsmall()
    {
        return appLogoClrsmall;
    }

    /**
     * @return
     */
    public java.lang.String getAppLogoClrsmallFileName()
    {
        return appLogoClrsmallFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getAppLogoClrsmallTempFileId()
    {
        return appLogoClrsmallTempFileId;
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
    public java.lang.String getAppSubmitType()
    {
        return appSubmitType;
    }

    /**
     * @return
     */
    public FormFile getAppTitleGraphic()
    {
        return appTitleGraphic;
    }

    /**
     * @return
     */
    public java.lang.String getAppTitleGraphicFileName()
    {
        return appTitleGraphicFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getAppTitleGraphicTempFileId()
    {
        return appTitleGraphicTempFileId;
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
    public java.lang.String getContentUsagePermission()
    {
        return contentUsagePermission;
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
    public FormFile getPublisherLogo()
    {
        return publisherLogo;
    }

    /**
     * @return
     */
    public java.lang.String getPublisherLogoFileName()
    {
        return publisherLogoFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getPublisherLogoTempFileId()
    {
        return publisherLogoTempFileId;
    }

    /**
     * @return
     */
    public FormFile getScreenJpeg1()
    {
        return screenJpeg1;
    }

    /**
     * @return
     */
    public java.lang.String getScreenJpeg1FileName()
    {
        return screenJpeg1FileName;
    }

    /**
     * @return
     */
    public java.lang.Long getScreenJpeg1TempFileId()
    {
        return screenJpeg1TempFileId;
    }

    /**
     * @return
     */
    public FormFile getScreenJpeg2()
    {
        return screenJpeg2;
    }

    /**
     * @return
     */
    public java.lang.String getScreenJpeg2FileName()
    {
        return screenJpeg2FileName;
    }

    /**
     * @return
     */
    public java.lang.Long getScreenJpeg2TempFileId()
    {
        return screenJpeg2TempFileId;
    }

    /**
     * @return
     */
    public FormFile getScreenJpeg3()
    {
        return screenJpeg3;
    }

    /**
     * @return
     */
    public java.lang.String getScreenJpeg3FileName()
    {
        return screenJpeg3FileName;
    }

    /**
     * @return
     */
    public java.lang.Long getScreenJpeg3TempFileId()
    {
        return screenJpeg3TempFileId;
    }

    /**
     * @return
     */
    public FormFile getScreenJpeg4()
    {
        return screenJpeg4;
    }

    /**
     * @return
     */
    public java.lang.String getScreenJpeg4FileName()
    {
        return screenJpeg4FileName;
    }

    /**
     * @return
     */
    public java.lang.Long getScreenJpeg4TempFileId()
    {
        return screenJpeg4TempFileId;
    }

    /**
     * @return
     */
    public FormFile getScreenJpeg5()
    {
        return screenJpeg5;
    }

    /**
     * @return
     */
    public java.lang.String getScreenJpeg5FileName()
    {
        return screenJpeg5FileName;
    }

    /**
     * @return
     */
    public java.lang.Long getScreenJpeg5TempFileId()
    {
        return screenJpeg5TempFileId;
    }

    /**
     * @return
     */
    public FormFile getSplashScreen()
    {
        return splashScreen;
    }

    /**
     * @return
     */
    public java.lang.String getSplashScreenFileName()
    {
        return splashScreenFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getSplashScreenTempFileId()
    {
        return splashScreenTempFileId;
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
    public java.lang.String getTask()
    {
        return task;
    }

    /**
     * @return
     */
    public java.lang.String getTempForRadioIssue()
    {
        return tempForRadioIssue;
    }

    /**
     * @return
     */
    public java.lang.Long getUserId()
    {
        return userId;
    }

    /**
     * @return
     */
    public FormFile getVideoFile()
    {
        return videoFile;
    }

    /**
     * @return
     */
    public java.lang.String getVideoFileFileName()
    {
        return videoFileFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getVideoFileTempFileId()
    {
        return videoFileTempFileId;
    }

    /**
     * @param file
     */
    public void setActiveScreen(FormFile file)
    {
        activeScreen = file;
    }

    /**
     * @param string
     */
    public void setActiveScreenFileName(java.lang.String string)
    {
        activeScreenFileName = string;
    }

    /**
     * @param long1
     */
    public void setActiveScreenTempFileId(java.lang.Long long1)
    {
        activeScreenTempFileId = long1;
    }

    /**
     * @param string
     */
    public void setApplicationTitle(java.lang.String string)
    {
        applicationTitle = string;
    }

    /**
     * @param file
     */
    public void setAppLogoBwLarge(FormFile file)
    {
        appLogoBwLarge = file;
    }

    /**
     * @param string
     */
    public void setAppLogoBwLargeFileName(java.lang.String string)
    {
        appLogoBwLargeFileName = string;
    }

    /**
     * @param long1
     */
    public void setAppLogoBwLargeTempFileId(java.lang.Long long1)
    {
        appLogoBwLargeTempFileId = long1;
    }

    /**
     * @param file
     */
    public void setAppLogoBwSmall(FormFile file)
    {
        appLogoBwSmall = file;
    }

    /**
     * @param string
     */
    public void setAppLogoBwSmallFileName(java.lang.String string)
    {
        appLogoBwSmallFileName = string;
    }

    /**
     * @param long1
     */
    public void setAppLogoBwSmallTempFileId(java.lang.Long long1)
    {
        appLogoBwSmallTempFileId = long1;
    }

    /**
     * @param file
     */
    public void setAppLogoClrlarge(FormFile file)
    {
        appLogoClrlarge = file;
    }

    /**
     * @param string
     */
    public void setAppLogoClrlargeFileName(java.lang.String string)
    {
        appLogoClrlargeFileName = string;
    }

    /**
     * @param long1
     */
    public void setAppLogoClrlargeTempFileId(java.lang.Long long1)
    {
        appLogoClrlargeTempFileId = long1;
    }

    /**
     * @param file
     */
    public void setAppLogoClrsmall(FormFile file)
    {
        appLogoClrsmall = file;
    }

    /**
     * @param string
     */
    public void setAppLogoClrsmallFileName(java.lang.String string)
    {
        appLogoClrsmallFileName = string;
    }

    /**
     * @param long1
     */
    public void setAppLogoClrsmallTempFileId(java.lang.Long long1)
    {
        appLogoClrsmallTempFileId = long1;
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
    public void setAppSubmitType(java.lang.String string)
    {
        appSubmitType = string;
    }

    /**
     * @param file
     */
    public void setAppTitleGraphic(FormFile file)
    {
        appTitleGraphic = file;
    }

    /**
     * @param string
     */
    public void setAppTitleGraphicFileName(java.lang.String string)
    {
        appTitleGraphicFileName = string;
    }

    /**
     * @param long1
     */
    public void setAppTitleGraphicTempFileId(java.lang.Long long1)
    {
        appTitleGraphicTempFileId = long1;
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
     * @param string
     */
    public void setContentUsagePermission(java.lang.String string)
    {
        contentUsagePermission = string;
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
     * @param file
     */
    public void setPublisherLogo(FormFile file)
    {
        publisherLogo = file;
    }

    /**
     * @param string
     */
    public void setPublisherLogoFileName(java.lang.String string)
    {
        publisherLogoFileName = string;
    }

    /**
     * @param long1
     */
    public void setPublisherLogoTempFileId(java.lang.Long long1)
    {
        publisherLogoTempFileId = long1;
    }

    /**
     * @param file
     */
    public void setScreenJpeg1(FormFile file)
    {
        screenJpeg1 = file;
    }

    /**
     * @param string
     */
    public void setScreenJpeg1FileName(java.lang.String string)
    {
        screenJpeg1FileName = string;
    }

    /**
     * @param long1
     */
    public void setScreenJpeg1TempFileId(java.lang.Long long1)
    {
        screenJpeg1TempFileId = long1;
    }

    /**
     * @param file
     */
    public void setScreenJpeg2(FormFile file)
    {
        screenJpeg2 = file;
    }

    /**
     * @param string
     */
    public void setScreenJpeg2FileName(java.lang.String string)
    {
        screenJpeg2FileName = string;
    }

    /**
     * @param long1
     */
    public void setScreenJpeg2TempFileId(java.lang.Long long1)
    {
        screenJpeg2TempFileId = long1;
    }

    /**
     * @param file
     */
    public void setScreenJpeg3(FormFile file)
    {
        screenJpeg3 = file;
    }

    /**
     * @param string
     */
    public void setScreenJpeg3FileName(java.lang.String string)
    {
        screenJpeg3FileName = string;
    }

    /**
     * @param long1
     */
    public void setScreenJpeg3TempFileId(java.lang.Long long1)
    {
        screenJpeg3TempFileId = long1;
    }

    /**
     * @param file
     */
    public void setScreenJpeg4(FormFile file)
    {
        screenJpeg4 = file;
    }

    /**
     * @param string
     */
    public void setScreenJpeg4FileName(java.lang.String string)
    {
        screenJpeg4FileName = string;
    }

    /**
     * @param long1
     */
    public void setScreenJpeg4TempFileId(java.lang.Long long1)
    {
        screenJpeg4TempFileId = long1;
    }

    /**
     * @param file
     */
    public void setScreenJpeg5(FormFile file)
    {
        screenJpeg5 = file;
    }

    /**
     * @param string
     */
    public void setScreenJpeg5FileName(java.lang.String string)
    {
        screenJpeg5FileName = string;
    }

    /**
     * @param long1
     */
    public void setScreenJpeg5TempFileId(java.lang.Long long1)
    {
        screenJpeg5TempFileId = long1;
    }

    /**
     * @param file
     */
    public void setSplashScreen(FormFile file)
    {
        splashScreen = file;
    }

    /**
     * @param string
     */
    public void setSplashScreenFileName(java.lang.String string)
    {
        splashScreenFileName = string;
    }

    /**
     * @param long1
     */
    public void setSplashScreenTempFileId(java.lang.Long long1)
    {
        splashScreenTempFileId = long1;
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
    public void setTask(java.lang.String string)
    {
        task = string;
    }

    /**
     * @param string
     */
    public void setTempForRadioIssue(java.lang.String string)
    {
        tempForRadioIssue = string;
    }

    /**
     * @param long1
     */
    public void setUserId(java.lang.Long long1)
    {
        userId = long1;
    }

    /**
     * @param file
     */
    public void setVideoFile(FormFile file)
    {
        videoFile = file;
    }

    /**
     * @param string
     */
    public void setVideoFileFileName(java.lang.String string)
    {
        videoFileFileName = string;
    }

    /**
     * @param long1
     */
    public void setVideoFileTempFileId(java.lang.Long long1)
    {
        videoFileTempFileId = long1;
    }

    /**
     * @return
     */
    public java.lang.String getEvaluationComments()
    {
        return evaluationComments;
    }

    /**
     * @param string
     */
    public void setEvaluationComments(java.lang.String string)
    {
        evaluationComments = string;
    }

}
