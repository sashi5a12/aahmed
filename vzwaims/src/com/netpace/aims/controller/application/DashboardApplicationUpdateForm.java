package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.application.DashboardApplicationManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.util.SafeHTML;

/**
 * @struts.form name="DashboardApplicationUpdateForm"
 */
public class DashboardApplicationUpdateForm extends BaseValidatorForm {

    private static final Logger log = Logger.getLogger(DashboardApplicationUpdateForm.class.getName());

    private Long appsId;
    private String submittedDate;
    private Long aimsUserAppCreatedById;
    private Long aimsLifecyclePhaseId;
    private String applicationStatus;
    private Long aimsAllianceId;
    private String allianceName;
    private String task;
    private String originalTask;
    private String appSubmitType;
    private String currentPage;
    private String setupURL;
    private String updateURL;

    private String title;
    private String channelVersion;
    private String channelType;
    private String language;
    private String channelSize;
    private String shortDesc;
    private String longDesc;
    private String vendorProductDisplay;
    private String vzwRetailPrice;
    private Long aimsAppCategoryId;
    private java.util.Collection allCategories;
    private Long aimsAppSubCategoryId;
    private java.util.Collection allSubCategories;
    private String[] listSelectedDevices;
    private String[] listAvailableDevices;
    private java.util.Collection availableDevices;
    private java.util.Collection selectedDevices;
    private java.lang.String selectedDevicesAlertMessage;

    private String channelDeployments;
    private Long aimsContactId;
    private java.util.Collection allContacts;
    private String contactFirstName;
    private String contactLastName;
    private String contactTitle;
    private String contactEmail;
    private String contactPhone;
    private String contactMobile;
    private String developerName;
    private String publisherName;
    private String sellingPoints;
    private String plannedDevStartDate;
    private String isNewContentZipFileUploaded;

    private FormFile clrPubLogo;
    private FormFile appTitleName;
    private FormFile splashScreenEps;
    private FormFile activeScreenEps;
    private FormFile screenJpeg;
    private FormFile screenJpeg2;
    private FormFile screenJpeg3;
    private FormFile screenJpeg4;
    private FormFile screenJpeg5;
    private FormFile faqDoc;
    private FormFile userGuide;
    private FormFile contentZipFile;
    private FormFile companyLogo;
    private FormFile titleImage;

    private String clrPubLogoFileName;
    private String appTitleNameFileName;
    private String splashScreenEpsFileName;
    private String activeScreenEpsFileName;
    private String screenJpegFileName;
    private String screenJpeg2FileName;
    private String screenJpeg3FileName;
    private String screenJpeg4FileName;
    private String screenJpeg5FileName;
    private String faqDocFileName;
    private String userGuideFileName;
    private String contentZipFileFileName;
    private String companyLogoFileName;
    private String titleImageFileName;

    private Long clrPubLogoTempFileId;
    private Long appTitleNameTempFileId;
    private Long splashScreenEpsTempFileId;
    private Long activeScreenEpsTempFileId;
    private Long screenJpegTempFileId;
    private Long screenJpeg2TempFileId;
    private Long screenJpeg3TempFileId;
    private Long screenJpeg4TempFileId;
    private Long screenJpeg5TempFileId;
    private Long faqDocTempFileId;
    private Long userGuideTempFileId;
    private Long contentZipFileTempFileId;
    private Long companyLogoTempFileId;
    private Long titleImageTempFileId;

    private String productDescription;
    private String usingApplication;
    private String tipsAndTricks;
    private String faq;
    private String troubleshooting;
    private String devCompanyDisclaimer;
    private String additionalInformation;

    private int usingApplicationLen;
    private int tipsAndTricksLen;
    private int faqLen;
    private int troubleshootingLen;
    private int devCompanyDisclaimerLen;
    private int additionalInformationLen;

    private String initialApprovalNotes;
    private String initialApprovalAction;
    private String isContentZipFileLock;
    private String channelId;
    private String contentZipFileNotes;
    private String contentZipFileAction;
    private String moveToProduction;
    private String remove;

    private String journalType;
    private String journalText;
    private String journalCombinedText;

    private java.lang.String[] errorMessages;

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        log.debug("DashboardApplicationUpdateForm.reset: Start");
        log.debug("DashboardApplicationUpdateForm.reset: End");
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

        ActionErrors errors = new ActionErrors();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String sessionId = session.getId();

        if (!"paging".equalsIgnoreCase(this.appSubmitType)){
            this.setErrorMessages(null);
            validateToDBFields(currUserType,errors,request);
            if (!errors.isEmpty()){
                this.setCurrentPage("page1");
                DashboardApplicationHelper.prePopulateForm(this);
                return errors;
            }
        }
        saveTempFiles(request, sessionId, currUserId, errors);

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))) {

            if (this.isBlankString(this.title))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelTitle.required"));

            if (!this.isBlankString(this.title) && this.title.indexOf('_') != -1){
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.validate.underscore", "Channel Title"));
            }

            if (this.isBlankString(this.channelVersion))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelVersion.required"));

            if (this.isBlankString(this.shortDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appShortDesc.required"));

            if (this.isBlankString(this.longDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appLongDesc.required"));

            if (!this.isDropDownSelected(this.aimsAppSubCategoryId))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelSubCategory.required"));

            if(!isBlankString(clrPubLogoFileName) && !isValidFileType(clrPubLogoFileName, AimsConstants.FILE_TYPE_EPS_ONLY))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appClrPubLogo.file.type"));

            if(!isBlankString(appTitleNameFileName) && !isValidFileType(appTitleNameFileName, AimsConstants.FILE_TYPE_EPS_ONLY))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appAppTitleName.file.type"));

            if(!isBlankString(splashScreenEpsFileName) && !isValidFileType(splashScreenEpsFileName, AimsConstants.FILE_TYPE_EPS_ONLY))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appSplashScreen.file.type"));

            if(!isBlankString(activeScreenEpsFileName) && !isValidFileType(activeScreenEpsFileName, AimsConstants.FILE_TYPE_EPS_ONLY))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appActiveScreen.file.type"));

            if(!isBlankString(screenJpegFileName) && !isValidFileType(screenJpegFileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appScreenShot.file.type"));

            if(!isBlankString(screenJpeg2FileName) && !isValidFileType(screenJpeg2FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appScreenShot.file.type"));

            if(!isBlankString(screenJpeg3FileName) && !isValidFileType(screenJpeg3FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appScreenShot.file.type"));

            if(!isBlankString(screenJpeg4FileName) && !isValidFileType(screenJpeg4FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appScreenShot.file.type"));

            if(!isBlankString(screenJpeg5FileName) && !isValidFileType(screenJpeg5FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appScreenShot.file.type"));

            if(!isBlankString(faqDocFileName) && !isValidFileType(faqDocFileName, AimsConstants.FILE_TYPE_DOC))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.manage.app.appFAQ.file.type"));

            if(!isBlankString(userGuideFileName) && !isValidFileType(userGuideFileName, AimsConstants.FILE_TYPE_DOC))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.userGuide.file.type"));

            if(!isBlankString(contentZipFileFileName) && !isValidFileType(contentZipFileFileName, AimsConstants.FILE_TYPE_ZIP))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.contentZipFile.file.type"));

            if (!(this.isBlankString(this.companyLogoFileName)))
                if (!(this.isValidFileType(this.companyLogoFileName, AimsConstants.FILE_TYPE_JPEG)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.companyLogo.file.type"));

            if (!(this.isBlankString(this.titleImageFileName)))
                if (!(this.isValidFileType(this.titleImageFileName, AimsConstants.FILE_TYPE_JPEG)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.titleImage.file.type"));

            // Email Validation
            if (!this.isDropDownSelected(this.aimsContactId)) {
                if (!this.isBlankString(this.contactEmail))
                    if (!this.isValidEmail(this.contactEmail))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactEmail.email", this.contactEmail));
            }

        }

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))) {

            if (isBlankString(this.channelType))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelType.required"));

            if (isBlankString(this.language))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.language.required"));

            if ((this.listSelectedDevices == null) || (!(this.listSelectedDevices.length > 0)))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.listSelectedDevices.required"));

            if (((this.splashScreenEps == null) || !(this.splashScreenEps.getFileSize() > 0)) && (this.isBlankString(this.splashScreenEpsFileName)))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appSplashScreen.required"));

            if (((this.screenJpeg == null) || !(this.screenJpeg.getFileSize() > 0)) && (this.isBlankString(this.screenJpegFileName)))
                if (((this.screenJpeg2 == null) || !(this.screenJpeg2.getFileSize() > 0)) && (this.isBlankString(this.screenJpeg2FileName)))
                    if (((this.screenJpeg3 == null) || !(this.screenJpeg3.getFileSize() > 0)) && (this.isBlankString(this.screenJpeg3FileName)))
                        if (((this.screenJpeg4 == null) || !(this.screenJpeg4.getFileSize() > 0)) && (this.isBlankString(this.screenJpeg4FileName)))
                            if (((this.screenJpeg5 == null) || !(this.screenJpeg5.getFileSize() > 0)) && (this.isBlankString(this.screenJpeg5FileName)))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.appScreenShot.required"));

            // Contacts
            if (!this.isDropDownSelected(this.aimsContactId)) {
                if (this.isBlankString(this.contactFirstName))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactFirstName.required"));
                if (this.isBlankString(this.contactLastName))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactLastName.required"));
                if (this.isBlankString(this.contactTitle))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactTitle.required"));
                if (this.isBlankString(this.contactEmail))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactEmail.required"));
            }

            if (this.isBlankString(this.sellingPoints))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.sellingPoints.required"));

            if (this.isBlankString(this.productDescription))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashobard.app.productDescription.required"));

            if (this.isBlankString(this.devCompanyDisclaimer))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashobard.app.devCompanyDisclaimer.required"));

            if (aimsLifecyclePhaseId!=null
                    && !aimsLifecyclePhaseId.equals(AimsConstants.SUBMISSION_ID)
                    && !aimsLifecyclePhaseId.equals(AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL)){
                if (!isBlankString(channelId) && isNumber(channelId)){
                    if (Integer.parseInt(this.channelId)<=0 || Integer.parseInt(this.channelId)>65000){
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.validate.channelId"));
                    }
                    else {
                        try {
                            if (DashboardApplicationManager.isChannelIdExist(new Integer(channelId), appsId)){
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelId.duplicate"));
                            }
                        } catch (HibernateException e) {
                            log.error(e,e);
                        }
                    }
                }
                else if (!isBlankString(channelId) && !isNumber(channelId)){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelId.numeric"));
                }
            }
        }

        if ("paging".equalsIgnoreCase(this.appSubmitType) && "submitpage4".equalsIgnoreCase(this.task)){
            if (this.isBlankString(this.journalText))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.journalText.required"));
            if (this.isBlankString(this.journalType))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.journalType.required"));
        }

        if (!errors.isEmpty()){
            this.setCurrentPage("page1");
            DashboardApplicationHelper.prePopulateForm(this);
        }
        return errors;
    }

    public void saveTempFiles(HttpServletRequest request, String sessionId, String currUserId, ActionErrors errors) {
        log.debug("Dashboard.saveTempFiles Start:");
        TempFile tempFile = null;
        HttpSession session = request.getSession();
        String imageFileSize = (String) session.getAttribute(AimsConstants.IMAGE_FILE_SIZE);
        String docFileSize = (String) session.getAttribute(AimsConstants.DOC_FILE_SIZE);
        String epsFileSize = (String) session.getAttribute(AimsConstants.EPS_FILE_SIZE);
        String jpegFileSize = (String) session.getAttribute(AimsConstants.JPEG_FILE_SIZE);
        String flashFileSize = (String) session.getAttribute(AimsConstants.FLASH_FILE_SIZE);
        String presentationFileSize = (String) session.getAttribute(AimsConstants.PRESENTATION_FILE_SIZE);
        String screenShotFileSize = (String) session.getAttribute(AimsConstants.SCREEN_SHOT_FILE_SIZE);
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        String userGuideImageSize="30720";

        log.debug("Image File Size: " + imageFileSize);
        log.debug("Doc File Size: " + docFileSize);
        log.debug("File Size: "+fileSize);


        try {
            if (clrPubLogo != null && clrPubLogo.getFileSize() > 0 && !isValidFileSize(fileSize, clrPubLogo.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appClrPubLogo.fileSize.exceeded"));
                clrPubLogo.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(clrPubLogo, sessionId, currUserId);
                if (tempFile != null) {
                    clrPubLogoTempFileId = tempFile.getFileId();
                    clrPubLogoFileName = tempFile.getFileName();
                }
            }
            if (appTitleName != null && appTitleName.getFileSize() > 0 && !isValidFileSize(fileSize, appTitleName.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appAppTitleName.fileSize.exceeded"));
                appTitleName.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(appTitleName, sessionId, currUserId);
                if (tempFile != null) {
                    appTitleNameTempFileId = tempFile.getFileId();
                    appTitleNameFileName = tempFile.getFileName();
                }
            }
            if (splashScreenEps != null && splashScreenEps.getFileSize() > 0 && !isValidFileSize(fileSize, splashScreenEps.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appSplashScreen.fileSize.exceeded"));
                splashScreenEps.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(splashScreenEps, sessionId, currUserId);
                if (tempFile != null) {
                    splashScreenEpsTempFileId = tempFile.getFileId();
                    splashScreenEpsFileName = tempFile.getFileName();
                }
            }
            if (activeScreenEps != null && activeScreenEps.getFileSize() > 0 && !isValidFileSize(fileSize, activeScreenEps.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appActiveScreen.fileSize.exceeded"));
                activeScreenEps.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(activeScreenEps, sessionId, currUserId);
                if (tempFile != null) {
                    activeScreenEpsTempFileId = tempFile.getFileId();
                    activeScreenEpsFileName = tempFile.getFileName();
                }
            }
            if (screenJpeg != null && screenJpeg.getFileSize() > 0 && !isValidFileSize(fileSize, screenJpeg.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE",new ActionMessage("error.dashboard.app.appScreenShot.fileSize.exceeded"));
                screenJpeg.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(screenJpeg, sessionId, currUserId);
                if (tempFile != null) {
                    screenJpegTempFileId = tempFile.getFileId();
                    screenJpegFileName = tempFile.getFileName();
                }
            }
            if (screenJpeg2 != null && screenJpeg2.getFileSize() > 0 && !isValidFileSize(fileSize, screenJpeg2.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE",new ActionMessage("error.dashboard.app.appScreenShot.fileSize.exceeded"));
                screenJpeg2.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(screenJpeg2, sessionId, currUserId);
                if (tempFile != null) {
                    screenJpeg2TempFileId = tempFile.getFileId();
                    screenJpeg2FileName = tempFile.getFileName();
                }
            }
            if (screenJpeg3 != null && screenJpeg3.getFileSize() > 0 && !isValidFileSize(fileSize, screenJpeg3.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appScreenShot.fileSize.exceeded"));
                screenJpeg3.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(screenJpeg3, sessionId, currUserId);
                if (tempFile != null) {
                    screenJpeg3TempFileId = tempFile.getFileId();
                    screenJpeg3FileName = tempFile.getFileName();
                }
            }
            if (screenJpeg4 != null && screenJpeg4.getFileSize() > 0 && !isValidFileSize(fileSize, screenJpeg4.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appScreenShot.fileSize.exceeded"));
                screenJpeg4.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(screenJpeg4, sessionId, currUserId);
                if (tempFile != null) {
                    screenJpeg4TempFileId = tempFile.getFileId();
                    screenJpeg4FileName = tempFile.getFileName();
                }
            }
            if (screenJpeg5 != null && screenJpeg5.getFileSize() > 0 && !isValidFileSize(fileSize, screenJpeg5.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.appScreenShot.fileSize.exceeded"));
                screenJpeg5.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(screenJpeg5, sessionId, currUserId);
                if (tempFile != null) {
                    screenJpeg5TempFileId = tempFile.getFileId();
                    screenJpeg5FileName = tempFile.getFileName();
                }
            }
            if (faqDoc != null && faqDoc.getFileSize() > 0 && !isValidFileSize(fileSize, faqDoc.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.manage.app.appFAO.fileSize.exceeded"));
                faqDoc.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(faqDoc, sessionId, currUserId);
                if (tempFile != null) {
                    faqDocTempFileId = tempFile.getFileId();
                    faqDocFileName = tempFile.getFileName();
                }
            }
            if (userGuide != null && userGuide.getFileSize() > 0 && !isValidFileSize(fileSize, userGuide.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.manage.app.appUserGuide.fileSize.exceeded"));
                userGuide.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(userGuide, sessionId, currUserId);
                if (tempFile != null) {
                    userGuideTempFileId = tempFile.getFileId();
                    userGuideFileName = tempFile.getFileName();
                }
            }
            if (contentZipFile != null && contentZipFile.getFileSize() > 0 && !isValidFileSize(fileSize, contentZipFile.getFileSize())) {
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.dashboard.app.contentZipFile.fileSize.exceeded"));
                contentZipFile.destroy();
            } else {
                tempFile = TempFilesManager.saveTempFile(contentZipFile, sessionId, currUserId);
                if (tempFile != null) {
                    contentZipFileTempFileId = tempFile.getFileId();
                    contentZipFileFileName = tempFile.getFileName();
                    if (isValidFileType(contentZipFileFileName, AimsConstants.FILE_TYPE_ZIP)){
                        //set that new file was uploaded.
                        isNewContentZipFileUploaded="Y";
                    }
                }
            }

            if ((this.companyLogo != null) && (this.companyLogo.getFileSize() > 0)&& !(this.isValidFileSize(userGuideImageSize, this.companyLogo.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.companyLogo.fileSize.exceeded"));
                this.companyLogo.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.companyLogo, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.companyLogoTempFileId = tempFile.getFileId();
                    this.companyLogoFileName = tempFile.getFileName();
                }
            }

            if ((this.titleImage != null) && (this.titleImage.getFileSize() > 0)&& !(this.isValidFileSize(userGuideImageSize, this.titleImage.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.titleImage.fileSize.exceeded"));
                this.titleImage.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.titleImage, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.titleImageTempFileId = tempFile.getFileId();
                    this.titleImageFileName = tempFile.getFileName();
                }
            }


        } catch (Exception ex) {
            log.error(ex,ex);
            log.debug("Error while saving temp files IN saveTempFiles() of DashboardApplicationUpdateForm");
        } finally {
            log.debug("Finally called IN saveTempFiles() of DashboardApplicationUpdateForm");
        }
        log.debug("Dashboard.saveTempFiles End:");
    }

    public void validateToDBFields(String currUserType, ActionErrors errors,HttpServletRequest request) {
        if (!isBlankString(this.title) && (this.title.trim().length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelTitle.length"));

        if (!isBlankString(this.channelVersion) && (this.channelVersion.trim().length() > 30))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelVersion.length"));

        if (!isBlankString(this.channelSize) && (this.channelSize.trim().length() > 30))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelSize.length"));

        if (!isBlankString(this.shortDesc) && (this.shortDesc.trim().length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appShortDesc.length"));

        if (!isBlankString(this.longDesc) && (this.longDesc.trim().length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appLongDesc.length"));

        if ((this.clrPubLogo != null) && (this.clrPubLogo.getFileSize() > 0) && this.clrPubLogo.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.clrPubLogo.length"));
            this.clrPubLogo.destroy();
        }

        if ((this.appTitleName != null) && (this.appTitleName.getFileSize() > 0) && this.appTitleName.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.appTitleName.length"));
            this.appTitleName.destroy();
        }

        if ((this.splashScreenEps != null) && (this.splashScreenEps.getFileSize() > 0) && this.splashScreenEps.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.splashScreenEps.length"));
            this.splashScreenEps.destroy();
        }

        if ((this.activeScreenEps != null) && (this.activeScreenEps.getFileSize() > 0) && this.activeScreenEps.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.activeScreenEps.length"));
            this.activeScreenEps.destroy();
        }

        if ((this.screenJpeg != null) && (this.screenJpeg.getFileSize() > 0) && this.screenJpeg.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.screenJpeg.length"));
            this.screenJpeg.destroy();
        }

        if ((this.screenJpeg2 != null) && (this.screenJpeg2.getFileSize() > 0) && this.screenJpeg2.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.screenJpeg.length"));
            this.screenJpeg2.destroy();
        }

        if ((this.screenJpeg3 != null) && (this.screenJpeg3.getFileSize() > 0) && this.screenJpeg3.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.screenJpeg.length"));
            this.screenJpeg3.destroy();
        }

        if ((this.screenJpeg4 != null) && (this.screenJpeg4.getFileSize() > 0) && this.screenJpeg4.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.screenJpeg.length"));
            this.screenJpeg4.destroy();
        }

        if ((this.screenJpeg5 != null) && (this.screenJpeg5.getFileSize() > 0) && this.screenJpeg5.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.screenJpeg.length"));
            this.screenJpeg5.destroy();
        }

        if ((this.faqDoc != null) && (this.faqDoc.getFileSize() > 0) && this.faqDoc.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.faqDoc.length"));
            this.faqDoc.destroy();
        }

        if (((this.userGuide != null) && (this.userGuide.getFileSize() > 0)) && this.userGuide.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.userGuide.length"));
            this.userGuide.destroy();
        }

        if ((this.contentZipFile != null) && (this.contentZipFile.getFileSize() > 0) && this.contentZipFile.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.contentZipFile.length"));
            this.contentZipFile.destroy();
        }

        if (!isBlankString(this.channelDeployments) && (this.channelDeployments.trim().length() > 1000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.channelDeployments.length"));

        if (!isBlankString(this.vendorProductDisplay) && (this.vendorProductDisplay.trim().length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.vendorProductDisplay.length"));

        if (!this.isBlankString(this.vzwRetailPrice))
            if (!this.isDecimalWithinRange(this.vzwRetailPrice, 0, 9999999.99))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.vzwRetailPrice.number"));

        if (!this.isDropDownSelected(this.aimsContactId)) {
            if (!isBlankString(this.contactFirstName) && (this.contactFirstName.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.contactFirstName.length"));

            if (!isBlankString(this.contactLastName) && (this.contactLastName.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.contactLastName.length"));

            if (!isBlankString(this.contactTitle) && (this.contactTitle.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.contactTitle.length"));

            if (!isBlankString(this.contactEmail) && (this.contactEmail.trim().length() > 100))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.contactEmail.length"));

            if (!isBlankString(this.contactPhone) && (this.contactPhone.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.contactPhone.length"));

            if (!isBlankString(this.contactMobile) && (this.contactMobile.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.contactMobile.length"));
        }

        if (!isBlankString(this.developerName) && (this.developerName.trim().length() > 150))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.developerName.length"));

        if (!isBlankString(this.publisherName) && (this.publisherName.trim().length() > 150))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.publisherName.length"));

        if (!isBlankString(this.sellingPoints) && (this.sellingPoints.trim().length() > 2000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.sellingPoints.length"));

        if (!this.isBlankString(this.plannedDevStartDate))
            if (!this.isDate(this.plannedDevStartDate))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.plannedDevStartDate.date", this.plannedDevStartDate));

        if (!this.isBlankString(this.productDescription)&& this.productDescription.length() > 1000)
             errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.productDescription.length"));

        if (this.usingApplicationLen > AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.usingApplication.length"));

        if (this.tipsAndTricksLen > AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.tipsAndTricks.length"));

        if (this.faqLen > AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.faq.length"));

        if (this.troubleshootingLen > AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.troubleshooting.length"));

        if (this.devCompanyDisclaimerLen > AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.devCompanyDisclaimer.length"));

        if (this.additionalInformationLen > AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.additionalInformation.length"));

        if (!isBlankString(this.initialApprovalNotes) && (this.initialApprovalNotes.trim().length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.initialApprovalNotes.length"));

        if (!isBlankString(this.contentZipFileNotes) && (this.contentZipFileNotes.trim().length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.contentZipFileNotes.length"));

    }

    public Long getAppsId() {
        return appsId;
    }

    public void setAppsId(Long appsId) {
        this.appsId = appsId;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Long getAimsUserAppCreatedById() {
        return aimsUserAppCreatedById;
    }

    public void setAimsUserAppCreatedById(Long aimsUserAppCreatedById) {
        this.aimsUserAppCreatedById = aimsUserAppCreatedById;
    }

    public Long getAimsLifecyclePhaseId() {
        return aimsLifecyclePhaseId;
    }

    public void setAimsLifecyclePhaseId(Long aimsLifecyclePhaseId) {
        this.aimsLifecyclePhaseId = aimsLifecyclePhaseId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Long getAimsAllianceId() {
        return aimsAllianceId;
    }

    public void setAimsAllianceId(Long aimsAllianceId) {
        this.aimsAllianceId = aimsAllianceId;
    }

    public String getAllianceName() {
        return allianceName;
    }

    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getOriginalTask() {
        return originalTask;
    }

    public void setOriginalTask(String originalTask) {
        this.originalTask = originalTask;
    }

    public String getAppSubmitType() {
        return appSubmitType;
    }

    public void setAppSubmitType(String appSubmitType) {
        this.appSubmitType = appSubmitType;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getSetupURL() {
        return setupURL;
    }

    public void setSetupURL(String setupURL) {
        this.setupURL = setupURL;
    }

    public String getUpdateURL() {
        return updateURL;
    }

    public void setUpdateURL(String updateURL) {
        this.updateURL = updateURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = StringUtils.trim(title);
    }

    public String getChannelVersion() {
        return channelVersion;
    }

    public void setChannelVersion(String channelVersion) {
        this.channelVersion = StringUtils.trim(channelVersion);
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getChannelSize() {
        return channelSize;
    }

    public void setChannelSize(String channelSize) {
        this.channelSize = StringUtils.trim(channelSize);
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = StringUtils.trim(shortDesc);
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = StringUtils.trim(longDesc);
    }

    public String getVendorProductDisplay() {
        return vendorProductDisplay;
    }

    public void setVendorProductDisplay(String vendorProductDisplay) {
        this.vendorProductDisplay = StringUtils.trim(vendorProductDisplay);
    }

    public String getVzwRetailPrice() {
        return vzwRetailPrice;
    }

    public void setVzwRetailPrice(String vzwRetailPrice) {
        this.vzwRetailPrice = StringUtils.trim(vzwRetailPrice);
    }

    public Long getAimsAppCategoryId() {
        return aimsAppCategoryId;
    }

    public void setAimsAppCategoryId(Long aimsAppCategoryId) {
        this.aimsAppCategoryId = aimsAppCategoryId;
    }

    public java.util.Collection getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(java.util.Collection allCategories) {
        this.allCategories = allCategories;
    }

    public Long getAimsAppSubCategoryId() {
        return aimsAppSubCategoryId;
    }

    public void setAimsAppSubCategoryId(Long aimsAppSubCategoryId) {
        this.aimsAppSubCategoryId = aimsAppSubCategoryId;
    }

    public java.util.Collection getAllSubCategories() {
        return allSubCategories;
    }

    public void setAllSubCategories(java.util.Collection allSubCategories) {
        this.allSubCategories = allSubCategories;
    }

    public String[] getListAvailableDevices() {
        return listAvailableDevices;
    }

    public void setListAvailableDevices(String[] listAvailableDevices) {
        this.listAvailableDevices = listAvailableDevices;
    }

    public java.util.Collection getSelectedDevices() {
        return selectedDevices;
    }

    public void setSelectedDevices(java.util.Collection selectedDevices) {
        this.selectedDevices = selectedDevices;
    }

    public String[] getListSelectedDevices() {
        return listSelectedDevices;
    }

    public void setListSelectedDevices(String[] listSelectedDevices) {
        this.listSelectedDevices = listSelectedDevices;
    }

    public java.util.Collection getAvailableDevices() {
        return availableDevices;
    }

    public void setAvailableDevices(java.util.Collection availableDevices) {
        this.availableDevices = availableDevices;
    }

    public java.lang.String getSelectedDevicesAlertMessage() {
        return selectedDevicesAlertMessage;
    }

    public void setSelectedDevicesAlertMessage(
            java.lang.String selectedDevicesAlertMessage) {
        this.selectedDevicesAlertMessage = selectedDevicesAlertMessage;
    }

    public String getChannelDeployments() {
        return channelDeployments;
    }

    public void setChannelDeployments(String channelDeployments) {
        this.channelDeployments = StringUtils.trim(channelDeployments);
    }

    public Long getAimsContactId() {
        return aimsContactId;
    }

    public void setAimsContactId(Long aimsContactId) {
        this.aimsContactId = aimsContactId;
    }

    public java.util.Collection getAllContacts() {
        return allContacts;
    }

    public void setAllContacts(java.util.Collection allContacts) {
        this.allContacts = allContacts;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = StringUtils.trim(contactFirstName);
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = StringUtils.trim(contactLastName);
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = StringUtils.trim(contactTitle);
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = StringUtils.trim(contactEmail);
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = StringUtils.trim(contactPhone);
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = StringUtils.trim(contactMobile);
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = StringUtils.trim(developerName);
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = StringUtils.trim(publisherName);
    }

    public String getSellingPoints() {
        return sellingPoints;
    }

    public void setSellingPoints(String sellingPoints) {
        this.sellingPoints = StringUtils.trim(sellingPoints);
    }

    public String getPlannedDevStartDate() {
        return plannedDevStartDate;
    }

    public void setPlannedDevStartDate(String plannedDevStartDate) {
        this.plannedDevStartDate = StringUtils.trim(plannedDevStartDate);
    }

    public String getIsNewContentZipFileUploaded() {
        return isNewContentZipFileUploaded;
    }

    public void setIsNewContentZipFileUploaded(String isNewContentZipFileUploaded) {
        this.isNewContentZipFileUploaded = isNewContentZipFileUploaded;
    }

    public FormFile getClrPubLogo() {
        return clrPubLogo;
    }

    public void setClrPubLogo(FormFile clrPubLogo) {
        this.clrPubLogo = clrPubLogo;
    }

    public FormFile getAppTitleName() {
        return appTitleName;
    }

    public void setAppTitleName(FormFile appTitleName) {
        this.appTitleName = appTitleName;
    }

    public FormFile getSplashScreenEps() {
        return splashScreenEps;
    }

    public void setSplashScreenEps(FormFile splashScreenEps) {
        this.splashScreenEps = splashScreenEps;
    }

    public FormFile getActiveScreenEps() {
        return activeScreenEps;
    }

    public void setActiveScreenEps(FormFile activeScreenEps) {
        this.activeScreenEps = activeScreenEps;
    }

    public FormFile getScreenJpeg() {
        return screenJpeg;
    }

    public void setScreenJpeg(FormFile screenJpeg) {
        this.screenJpeg = screenJpeg;
    }

    public FormFile getScreenJpeg2() {
        return screenJpeg2;
    }

    public void setScreenJpeg2(FormFile screenJpeg2) {
        this.screenJpeg2 = screenJpeg2;
    }

    public FormFile getScreenJpeg3() {
        return screenJpeg3;
    }

    public void setScreenJpeg3(FormFile screenJpeg3) {
        this.screenJpeg3 = screenJpeg3;
    }

    public FormFile getScreenJpeg4() {
        return screenJpeg4;
    }

    public void setScreenJpeg4(FormFile screenJpeg4) {
        this.screenJpeg4 = screenJpeg4;
    }

    public FormFile getScreenJpeg5() {
        return screenJpeg5;
    }

    public void setScreenJpeg5(FormFile screenJpeg5) {
        this.screenJpeg5 = screenJpeg5;
    }

    public FormFile getFaqDoc() {
        return faqDoc;
    }

    public void setFaqDoc(FormFile faqDoc) {
        this.faqDoc = faqDoc;
    }

    public FormFile getUserGuide() {
        return userGuide;
    }

    public void setUserGuide(FormFile userGuide) {
        this.userGuide = userGuide;
    }

    public FormFile getContentZipFile() {
        return contentZipFile;
    }

    public void setContentZipFile(FormFile contentZipFile) {
        this.contentZipFile = contentZipFile;
    }

    public FormFile getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(FormFile companyLogo) {
        this.companyLogo = companyLogo;
    }

    public FormFile getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(FormFile titleImage) {
        this.titleImage = titleImage;
    }

    public String getClrPubLogoFileName() {
        return clrPubLogoFileName;
    }

    public void setClrPubLogoFileName(String clrPubLogoFileName) {
        this.clrPubLogoFileName = clrPubLogoFileName;
    }

    public String getAppTitleNameFileName() {
        return appTitleNameFileName;
    }

    public void setAppTitleNameFileName(String appTitleNameFileName) {
        this.appTitleNameFileName = appTitleNameFileName;
    }

    public String getSplashScreenEpsFileName() {
        return splashScreenEpsFileName;
    }

    public void setSplashScreenEpsFileName(String splashScreenEpsFileName) {
        this.splashScreenEpsFileName = splashScreenEpsFileName;
    }

    public String getActiveScreenEpsFileName() {
        return activeScreenEpsFileName;
    }

    public void setActiveScreenEpsFileName(String activeScreenEpsFileName) {
        this.activeScreenEpsFileName = activeScreenEpsFileName;
    }

    public String getScreenJpegFileName() {
        return screenJpegFileName;
    }

    public void setScreenJpegFileName(String screenJpegFileName) {
        this.screenJpegFileName = screenJpegFileName;
    }

    public String getScreenJpeg2FileName() {
        return screenJpeg2FileName;
    }

    public void setScreenJpeg2FileName(String screenJpeg2FileName) {
        this.screenJpeg2FileName = screenJpeg2FileName;
    }

    public String getScreenJpeg3FileName() {
        return screenJpeg3FileName;
    }

    public void setScreenJpeg3FileName(String screenJpeg3FileName) {
        this.screenJpeg3FileName = screenJpeg3FileName;
    }

    public String getScreenJpeg4FileName() {
        return screenJpeg4FileName;
    }

    public void setScreenJpeg4FileName(String screenJpeg4FileName) {
        this.screenJpeg4FileName = screenJpeg4FileName;
    }

    public String getScreenJpeg5FileName() {
        return screenJpeg5FileName;
    }

    public void setScreenJpeg5FileName(String screenJpeg5FileName) {
        this.screenJpeg5FileName = screenJpeg5FileName;
    }

    public String getFaqDocFileName() {
        return faqDocFileName;
    }

    public void setFaqDocFileName(String faqDocFileName) {
        this.faqDocFileName = faqDocFileName;
    }

    public String getUserGuideFileName() {
        return userGuideFileName;
    }

    public void setUserGuideFileName(String userGuideFileName) {
        this.userGuideFileName = userGuideFileName;
    }

    public String getContentZipFileFileName() {
        return contentZipFileFileName;
    }

    public void setContentZipFileFileName(String contentZipFileFileName) {
        this.contentZipFileFileName = contentZipFileFileName;
    }

    public String getCompanyLogoFileName() {
        return companyLogoFileName;
    }

    public void setCompanyLogoFileName(String companyLogoFileName) {
        this.companyLogoFileName = companyLogoFileName;
    }

    public String getTitleImageFileName() {
        return titleImageFileName;
    }

    public void setTitleImageFileName(String titleImageFileName) {
        this.titleImageFileName = titleImageFileName;
    }

    public Long getClrPubLogoTempFileId() {
        return clrPubLogoTempFileId;
    }

    public void setClrPubLogoTempFileId(Long clrPubLogoTempFileId) {
        this.clrPubLogoTempFileId = clrPubLogoTempFileId;
    }

    public Long getAppTitleNameTempFileId() {
        return appTitleNameTempFileId;
    }

    public void setAppTitleNameTempFileId(Long appTitleNameTempFileId) {
        this.appTitleNameTempFileId = appTitleNameTempFileId;
    }

    public Long getSplashScreenEpsTempFileId() {
        return splashScreenEpsTempFileId;
    }

    public void setSplashScreenEpsTempFileId(Long splashScreenEpsTempFileId) {
        this.splashScreenEpsTempFileId = splashScreenEpsTempFileId;
    }

    public Long getActiveScreenEpsTempFileId() {
        return activeScreenEpsTempFileId;
    }

    public void setActiveScreenEpsTempFileId(Long activeScreenEpsTempFileId) {
        this.activeScreenEpsTempFileId = activeScreenEpsTempFileId;
    }

    public Long getScreenJpegTempFileId() {
        return screenJpegTempFileId;
    }

    public void setScreenJpegTempFileId(Long screenJpegTempFileId) {
        this.screenJpegTempFileId = screenJpegTempFileId;
    }

    public Long getScreenJpeg2TempFileId() {
        return screenJpeg2TempFileId;
    }

    public void setScreenJpeg2TempFileId(Long screenJpeg2TempFileId) {
        this.screenJpeg2TempFileId = screenJpeg2TempFileId;
    }

    public Long getScreenJpeg3TempFileId() {
        return screenJpeg3TempFileId;
    }

    public void setScreenJpeg3TempFileId(Long screenJpeg3TempFileId) {
        this.screenJpeg3TempFileId = screenJpeg3TempFileId;
    }

    public Long getScreenJpeg4TempFileId() {
        return screenJpeg4TempFileId;
    }

    public void setScreenJpeg4TempFileId(Long screenJpeg4TempFileId) {
        this.screenJpeg4TempFileId = screenJpeg4TempFileId;
    }

    public Long getScreenJpeg5TempFileId() {
        return screenJpeg5TempFileId;
    }

    public void setScreenJpeg5TempFileId(Long screenJpeg5TempFileId) {
        this.screenJpeg5TempFileId = screenJpeg5TempFileId;
    }

    public Long getFaqDocTempFileId() {
        return faqDocTempFileId;
    }

    public void setFaqDocTempFileId(Long faqDocTempFileId) {
        this.faqDocTempFileId = faqDocTempFileId;
    }

    public Long getUserGuideTempFileId() {
        return userGuideTempFileId;
    }

    public void setUserGuideTempFileId(Long userGuideTempFileId) {
        this.userGuideTempFileId = userGuideTempFileId;
    }

    public Long getContentZipFileTempFileId() {
        return contentZipFileTempFileId;
    }

    public void setContentZipFileTempFileId(Long contentZipFileTempFileId) {
        this.contentZipFileTempFileId = contentZipFileTempFileId;
    }

    public Long getCompanyLogoTempFileId() {
        return companyLogoTempFileId;
    }

    public void setCompanyLogoTempFileId(Long companyLogoTempFileId) {
        this.companyLogoTempFileId = companyLogoTempFileId;
    }

    public Long getTitleImageTempFileId() {
        return titleImageTempFileId;
    }

    public void setTitleImageTempFileId(Long titleImageTempFileId) {
        this.titleImageTempFileId = titleImageTempFileId;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getUsingApplication() {
        return usingApplication;
    }

    public void setUsingApplication(String usingApplication) {
        this.usingApplication = SafeHTML.makeSafe(usingApplication);
    }

    public String getTipsAndTricks() {
        return tipsAndTricks;
    }

    public void setTipsAndTricks(String tipsAndTricks) {
        this.tipsAndTricks = SafeHTML.makeSafe(tipsAndTricks);
    }

    public String getFaq() {
        return faq;
    }

    public void setFaq(String faq) {
        this.faq = SafeHTML.makeSafe(faq);
    }

    public String getTroubleshooting() {
        return troubleshooting;
    }

    public void setTroubleshooting(String troubleshooting) {
        this.troubleshooting = SafeHTML.makeSafe(troubleshooting);
    }

    public String getDevCompanyDisclaimer() {
        return devCompanyDisclaimer;
    }

    public void setDevCompanyDisclaimer(String devCompanyDisclaimer) {
        this.devCompanyDisclaimer = SafeHTML.makeSafe(devCompanyDisclaimer);
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = SafeHTML.makeSafe(additionalInformation);
    }

    public int getUsingApplicationLen() {
        return usingApplicationLen;
    }

    public void setUsingApplicationLen(int usingApplicationLen) {
        this.usingApplicationLen = usingApplicationLen;
    }

    public int getTipsAndTricksLen() {
        return tipsAndTricksLen;
    }

    public void setTipsAndTricksLen(int tipsAndTricksLen) {
        this.tipsAndTricksLen = tipsAndTricksLen;
    }

    public int getFaqLen() {
        return faqLen;
    }

    public void setFaqLen(int faqLen) {
        this.faqLen = faqLen;
    }

    public int getTroubleshootingLen() {
        return troubleshootingLen;
    }

    public void setTroubleshootingLen(int troubleshootingLen) {
        this.troubleshootingLen = troubleshootingLen;
    }

    public int getDevCompanyDisclaimerLen() {
        return devCompanyDisclaimerLen;
    }

    public void setDevCompanyDisclaimerLen(int devCompanyDisclaimerLen) {
        this.devCompanyDisclaimerLen = devCompanyDisclaimerLen;
    }

    public int getAdditionalInformationLen() {
        return additionalInformationLen;
    }

    public void setAdditionalInformationLen(int additionalInformationLen) {
        this.additionalInformationLen = additionalInformationLen;
    }

    public String getInitialApprovalNotes() {
        return initialApprovalNotes;
    }

    public void setInitialApprovalNotes(String initialApprovalNotes) {
        this.initialApprovalNotes = StringUtils.trim(initialApprovalNotes);
    }

    public String getInitialApprovalAction() {
        return initialApprovalAction;
    }

    public void setInitialApprovalAction(String initialApprovalAction) {
        this.initialApprovalAction = initialApprovalAction;
    }

    public String getIsContentZipFileLock() {
        return isContentZipFileLock;
    }

    public void setIsContentZipFileLock(String isContentZipFileLock) {
        this.isContentZipFileLock = isContentZipFileLock;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getContentZipFileNotes() {
        return contentZipFileNotes;
    }

    public void setContentZipFileNotes(String contentZipFileNotes) {
        this.contentZipFileNotes = StringUtils.trim(contentZipFileNotes);
    }

    public String getContentZipFileAction() {
        return contentZipFileAction;
    }

    public void setContentZipFileAction(String contentZipFileAction) {
        this.contentZipFileAction = contentZipFileAction;
    }

    public String getMoveToProduction() {
        return moveToProduction;
    }

    public void setMoveToProduction(String moveToProduction) {
        this.moveToProduction = moveToProduction;
    }

    public String getRemove() {
        return remove;
    }

    public void setRemove(String remove) {
        this.remove = remove;
    }

    public String getJournalType() {
        return journalType;
    }

    public void setJournalType(String journalType) {
        this.journalType = journalType;
    }

    public String getJournalText() {
        return journalText;
    }

    public void setJournalText(String journalText) {
        this.journalText = StringUtils.trim(journalText);
    }

    public String getJournalCombinedText() {
        return journalCombinedText;
    }

    public void setJournalCombinedText(String journalCombinedText) {
        this.journalCombinedText = journalCombinedText;
    }

    public java.lang.String[] getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(java.lang.String[] errorMessages) {
        this.errorMessages = errorMessages;
    }

}
