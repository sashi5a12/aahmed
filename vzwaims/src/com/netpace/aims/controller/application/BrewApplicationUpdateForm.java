package com.netpace.aims.controller.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.netpace.aims.bo.application.BrewApplicationManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.core.AimsTypeManager;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.bo.system.AimsDeckManager;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.model.masters.AimsDevicePid;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.SafeHTML;

/**
 * @struts.form name="BrewApplicationUpdateForm"
 */
public class BrewApplicationUpdateForm extends ApplicationUpdateForm
{

    static Logger log = Logger.getLogger(BrewApplicationUpdateForm.class.getName());

    private java.lang.Long brewAppsId;
    private java.lang.String appSize;



    private FormFile progGuide;
    private FormFile appTitleName;
    private FormFile companyLogo;
    private FormFile titleShot;

    private FormFile highResSplash;
    private FormFile highResActive;
    private FormFile splashScreen;
    private FormFile smallSplash;
    private FormFile activeScreen1;
    private FormFile activeScreen2;
    private FormFile smlActiveScreen;
    private FormFile appActiionFlash;
    private FormFile appGifAction;
    private FormFile mediaStore;
    private FormFile flashDemoMovie;
    private FormFile dashboardScrImg;

    private java.lang.String progGuideFileName;
    private java.lang.String appTitleNameFileName;
    private java.lang.String companyLogoFileName;
    private java.lang.String titleShotFileName;

    private java.lang.String highResActiveFileName;
    private java.lang.String highResSplashFileName;
    private java.lang.String splashScreenFileName;
    private java.lang.String activeScreen1FileName;
    private java.lang.String activeScreen2FileName;
    private java.lang.String smallSplashFileName;
    private java.lang.String smlActiveScreenFileName;
    private java.lang.String mediaStoreFileName;
    private java.lang.String appGifActionFileName;
    private java.lang.String appActiionFlashFileName;
    private java.lang.String userGuidePdfFileName;
    private java.lang.String flashDemoMovieFileName;
    private java.lang.String dashboardScrImgFileName;

    private java.lang.Long progGuideTempFileId;
    private java.lang.Long appTitleNameTempFileId;
    private java.lang.Long companyLogoTempFileId;
    private java.lang.Long titleShotTempFileId;

    private java.lang.Long highResSplashTempFileId;
    private java.lang.Long highResActiveTempFileId;
    private java.lang.Long splashScreenTempFileId;
    private java.lang.Long smallSplashTempFileId;
    private java.lang.Long activeScreen1TempFileId;
    private java.lang.Long activeScreen2TempFileId;
    private java.lang.Long smlActiveScreenTempFileId;
    private java.lang.Long appActiionFlashTempFileId;
    private java.lang.Long appGifActionTempFileId;
    private java.lang.Long mediaStoreTempFileId;
    private java.lang.Long flashDemoMovieTempFileId;
    private java.lang.Long dashboardScrImgTempFileId;


    private String[] listSelectedDevices;
    private String[] listAvailableDevices;
    private java.util.Collection availableDevices;
    private java.util.Collection selectedDevices;

    private java.lang.String developerName;
    private java.lang.String publisherName;
    private java.lang.String sellingPoints;
    private java.lang.String plannedDevStartDate;
    private java.lang.String plannedEntryIntoNstl;
    private java.lang.String plannedCompletionByNstl;
    private java.lang.String plannedEntryIntoVzw;
    private java.lang.String plannedCompletionByVzw;
    private java.lang.String prizes;
    private java.lang.String ugcChat;
    private java.lang.String chatPrize;
    private java.lang.String anticipatedDaps;
    private java.lang.String networkUse;
    private java.lang.String singleMultiPlayer;
    private java.lang.String evaluation;
    private java.lang.String deckLaunchDate;
    private java.lang.String deckPlacement;
    private String appType;
    private String appTypeFullName;
    private java.lang.Long conceptId;
    private java.lang.String conceptTitle;
    private java.lang.String conceptEvaluationDate;
    private java.util.Collection deckList;
    private java.lang.String isConcept;
    private java.lang.String selectedDevicesAlertMessage;
    private java.lang.String isLbs;
    private java.lang.String lbsClientId;
    private java.lang.String lbsSecretKey;
    private java.lang.String lbsAppType;

    private java.lang.Long lbsAutodeskPhaseId;
    private java.lang.String lbsAutodeskPhaseName;

    private java.lang.String disclaimerText;
    private java.lang.String productDescription;
    private java.lang.String multiPlayer;
    private java.lang.String usingApplication;
    private java.lang.String tipsAndTricks;
    private java.lang.String faq;
    private java.lang.String troubleshooting;
    private java.lang.String devCompanyDisclaimer;
    private java.lang.String additionalInformation;

    private int usingApplicationLen;
    private int tipsAndTricksLen;
    private int faqLen;
    private int troubleshootingLen;
    private int devCompanyDisclaimerLen;
    private int additionalInformationLen;
    private int airTimeLen;

    private java.util.Collection allGeoServices;
    private String[] listSelectedGeoServices;
    private String[] listAvailableGeoServices;

    private java.util.Collection allBrewContentRatings;
    private java.lang.Long contentRating;

    private List history;

    private List allAggregators;
    private String shortCode;
    private String keyword;
    private Long aggregator;

    private java.lang.String[] errorMessages;

    public java.lang.Long getBrewAppsId()
    {
        return this.brewAppsId;
    }

    public void setBrewAppsId(java.lang.Long brewAppsId)
    {
        this.brewAppsId = brewAppsId;
    }

    public java.lang.String getAppSize()
    {
        return this.appSize;
    }

    public void setAppSize(java.lang.String appSize)
    {
        this.appSize = appSize;
    }

    public FormFile getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(FormFile companyLogo) {
        this.companyLogo = companyLogo;
    }

    public FormFile getTitleShot() {
        return titleShot;
    }

    public void setTitleShot(FormFile titleShot) {
        this.titleShot = titleShot;
    }

    public java.lang.String getCompanyLogoFileName() {
        return companyLogoFileName;
    }

    public void setCompanyLogoFileName(java.lang.String companyLogoFileName) {
        this.companyLogoFileName = companyLogoFileName;
    }

    public java.lang.String getTitleShotFileName() {
        return titleShotFileName;
    }

    public void setTitleShotFileName(java.lang.String titleShotFileName) {
        this.titleShotFileName = titleShotFileName;
    }

    public String[] getListSelectedDevices()
    {
        return this.listSelectedDevices;
    }

    public void setListSelectedDevices(String[] listSelectedDevices)
    {
        this.listSelectedDevices = listSelectedDevices;
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

    public java.util.Collection getSelectedDevices()
    {
        return this.selectedDevices;
    }

    public void setSelectedDevices(java.util.Collection selectedDevices)
    {
        this.selectedDevices = selectedDevices;
    }

    public java.lang.Long getCompanyLogoTempFileId() {
        return companyLogoTempFileId;
    }

    public void setCompanyLogoTempFileId(java.lang.Long companyLogoTempFileId) {
        this.companyLogoTempFileId = companyLogoTempFileId;
    }

    public java.lang.Long getTitleShotTempFileId() {
        return titleShotTempFileId;
    }

    public void setTitleShotTempFileId(java.lang.Long titleShotTempFileId) {
        this.titleShotTempFileId = titleShotTempFileId;
    }

    public java.lang.String getDeveloperName()
    {
        return this.developerName;
    }

    public void setDeveloperName(java.lang.String developerName)
    {
        this.developerName = developerName;
    }

    public java.lang.String getPublisherName()
    {
        return this.publisherName;
    }

    public void setPublisherName(java.lang.String publisherName)
    {
        this.publisherName = publisherName;
    }

    public java.lang.String getSellingPoints()
    {
        return this.sellingPoints;
    }

    public void setSellingPoints(java.lang.String sellingPoints)
    {
        this.sellingPoints = sellingPoints;
    }

    public java.lang.String getPlannedDevStartDate()
    {
        return this.plannedDevStartDate;
    }

    public void setPlannedDevStartDate(java.lang.String plannedDevStartDate)
    {
        this.plannedDevStartDate = plannedDevStartDate;
    }

    public java.lang.String getPlannedEntryIntoNstl()
    {
        return this.plannedEntryIntoNstl;
    }

    public void setPlannedEntryIntoNstl(java.lang.String plannedEntryIntoNstl)
    {
        this.plannedEntryIntoNstl = plannedEntryIntoNstl;
    }

    public java.lang.String getPlannedCompletionByNstl()
    {
        return this.plannedCompletionByNstl;
    }

    public void setPlannedCompletionByNstl(java.lang.String plannedCompletionByNstl)
    {
        this.plannedCompletionByNstl = plannedCompletionByNstl;
    }

    public java.lang.String getPlannedEntryIntoVzw()
    {
        return this.plannedEntryIntoVzw;
    }

    public void setPlannedEntryIntoVzw(java.lang.String plannedEntryIntoVzw)
    {
        this.plannedEntryIntoVzw = plannedEntryIntoVzw;
    }

    public java.lang.String getPlannedCompletionByVzw()
    {
        return this.plannedCompletionByVzw;
    }

    public void setPlannedCompletionByVzw(java.lang.String plannedCompletionByVzw)
    {
        this.plannedCompletionByVzw = plannedCompletionByVzw;
    }

    public java.lang.String getAnticipatedDaps()
    {
        return this.anticipatedDaps;
    }

    public void setAnticipatedDaps(java.lang.String anticipatedDaps)
    {
        this.anticipatedDaps = anticipatedDaps;
    }

    public java.lang.String getNetworkUse()
    {
        return this.networkUse;
    }

    public void setNetworkUse(java.lang.String networkUse)
    {
        this.networkUse = networkUse;
    }

    public java.lang.String getSingleMultiPlayer()
    {
        return this.singleMultiPlayer;
    }

    public void setSingleMultiPlayer(java.lang.String singleMultiPlayer)
    {
        this.singleMultiPlayer = singleMultiPlayer;
    }

    public java.lang.String getEvaluation()
    {
        return this.evaluation;
    }

    public void setEvaluation(java.lang.String evaluation)
    {
        this.evaluation = evaluation;
    }

    public java.lang.String getDeckLaunchDate()
    {
        return this.deckLaunchDate;
    }

    public void setDeckLaunchDate(java.lang.String deckLaunchDate)
    {
        this.deckLaunchDate = deckLaunchDate;
    }

    public java.lang.String getDeckPlacement()
    {
        return this.deckPlacement;
    }

    public void setDeckPlacement(java.lang.String deckPlacement)
    {
        this.deckPlacement = deckPlacement;
    }

    public String getAppType()
    {
        return appType;
    }
    public void setAppType(String appType)
    {
        this.appType = appType;
    }

    public FormFile getAppTitleName()
    {
        return this.appTitleName;
    }

    public void setAppTitleName(FormFile fFile)
    {
        this.appTitleName = fFile;
    }

    public java.lang.String getAppTitleNameFileName()
    {
        return this.appTitleNameFileName;
    }

    public void setAppTitleNameFileName(java.lang.String strFileName)
    {
        this.appTitleNameFileName = strFileName;
    }

    public java.lang.Long getAppTitleNameTempFileId()
    {
        return this.appTitleNameTempFileId;
    }

    public void setAppTitleNameTempFileId(java.lang.Long lngTempFileId)
    {
        this.appTitleNameTempFileId = lngTempFileId;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        log.debug("\n\nIn Reset of BrewApplicationUpdateForm: \n\n");
        try
        {
            super.reset(mapping, request, AimsConstants.BREW_PLATFORM_ID);
            this.deckList = AimsDeckManager.getDeckList();
            this.availableDevices = AimsDevicesManager.getDevices(AimsConstants.BREW_PLATFORM_ID, true);
            if (this.availableDevices !=null && this.availableDevices.size()>0){
                List list=new ArrayList();
                Iterator itr=this.availableDevices.iterator();
                while(itr.hasNext()){
                    AimsDevic device=(AimsDevic)itr.next();
                    if (device.getAimsDevicePids()!=null && device.getAimsDevicePids().size()>0){
                        AimsDevicePid pid=(AimsDevicePid)device.getAimsDevicePids().iterator().next();
                        if (pid !=null && pid.getDevicePidId()!=null){
                            device.setDeviceModel(device.getDeviceModel()+" ("+pid.getPid()+")");
                        }
                    }
                    list.add(device);
                }
                this.availableDevices=list;
            }
            this.listSelectedDevices = (String[]) request.getSession().getAttribute(ManageApplicationsConstants.SESSION_SELECTED_DEVICES_LIST);
            this.allGeoServices = BrewApplicationManager.getGeoServices();
            this.allBrewContentRatings = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_BREW_CONTENT_RATING_ID);
            this.allAggregators=BrewApplicationManager.getAggregators();

        }
        catch (Exception ex)
        {
            log.debug("Exception in RESET: " + ex);
        }

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

        ActionErrors errors = new ActionErrors();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String sessionId = session.getId();
        String taskName = (String) session.getAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME);
        Date validationDateForZonAutomation=(Date)session.getAttribute(AimsConstants.VALIDATION_DATE_FOR_ZON_AUTOMATION);
        Date appSubmitDate =null;
        boolean messagingDetailValidation=false;

        if (!super.isBlankString(this.submittedDate)){
            appSubmitDate= Utility.convertToDate(this.submittedDate, "MM/dd/yyyy");
        }

        if (this.listSelectedDevices != null)
            session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_DEVICES_LIST, this.listSelectedDevices);

        super.saveTempFiles(request, sessionId, currUserId, errors, AimsConstants.BREW_PLATFORM_ID); //Calling Parent Class method
        saveTempBrewFiles(request, sessionId, currUserId, errors);
        super.validate(mapping, request, errors, AimsConstants.BREW_PLATFORM_ID); //Calling Parent Class method

        if (!"paging".equalsIgnoreCase(this.appSubmitType)){
            this.setErrorMessages(null);
        }

        if (appSubmitDate !=null && appSubmitDate.before(validationDateForZonAutomation)){
            request.setAttribute("zonAutoBeforeLive", "true");
        }

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {

            //Start of File Type Validations

            //Validating for *.swf only for new uploaded files and not for old ones (which might be *.avi)
            if ((this.flashDemoTempFileId != null) && (this.flashDemoTempFileId.longValue() != 0))
                if (!(this.isBlankString(this.flashDemoFileName)))
                    if (!(this.isValidFileType(this.flashDemoFileName, AimsConstants.FILE_TYPE_FLASH_ONLY)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appFlashOnlyDemo.file.type"));

            if (!(this.isBlankString(this.progGuideFileName)))
                if (!(this.isValidFileTypeWithName(this.progGuideFileName, AimsConstants.FILE_TYPE_BREW_PROG_GUIDE)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appProgGuide.file.type"));

            if (!(this.isBlankString(this.appTitleNameFileName)))
                if (!(this.isValidFileType(this.appTitleNameFileName, AimsConstants.FILE_TYPE_EPS_ONLY)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appAppTitleName.file.type"));

            if (!(this.isBlankString(this.highResSplashFileName)))
                if (!(this.isValidFileType(this.highResSplashFileName, AimsConstants.FILE_TYPE_EPS_ONLY)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.highResSplash.file.type"));

            if (!(this.isBlankString(this.highResActiveFileName)))
                if (!(this.isValidFileType(this.highResActiveFileName, AimsConstants.FILE_TYPE_EPS_ONLY)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.highResActive.file.type"));

            try {

                if (!(this.isBlankString(this.splashScreenFileName)))
                    if (!(this.isValidFileType(this.splashScreenFileName, AimsConstants.FILE_TYPE_JPEG_PNG)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.splashScreen.file.type"));
                else if ((this.splashScreen != null) && (this.splashScreen.getFileSize() > 0) && !(this.isImageWidthHeightEqual(this.splashScreen.getInputStream(), 150, 200))){
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.splashScreen.widthHeight.exceeded"));
                        this.splashScreen.destroy();
                }

                if (!(this.isBlankString(this.smallSplashFileName)))
                    if (!(this.isValidFileType(this.smallSplashFileName, AimsConstants.FILE_TYPE_JPEG)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.smallSplash.file.type"));
                else if ((this.smallSplash != null)&& (this.smallSplash.getFileSize() > 0) && !(this.isImageWidthHeightEqual(this.smallSplash.getInputStream(), 75, 100))){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.smallSplash.widthHeight.exceeded"));
                    this.smallSplash.destroy();
                }

                if (!(this.isBlankString(this.activeScreen1FileName)))
                    if (!(this.isValidFileType(this.activeScreen1FileName, AimsConstants.FILE_TYPE_JPEG_PNG)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.activeScreen1.file.type"));
                else if ((this.activeScreen1 != null) && (this.activeScreen1.getFileSize() > 0) && !(this.isImageWidthHeightEqual(this.activeScreen1.getInputStream(), 150, 200))){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.activeScreen1.widthHeight.exceeded"));
                    this.activeScreen1.destroy();
                }

                if (!(this.isBlankString(this.activeScreen2FileName)))
                    if (!(this.isValidFileType(this.activeScreen2FileName, AimsConstants.FILE_TYPE_JPEG_PNG)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.activeScreen2.file.type"));
                else if ((this.activeScreen2 != null) && (this.activeScreen2.getFileSize() > 0) && !(this.isImageWidthHeightEqual(this.activeScreen2.getInputStream(), 150, 200))){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.activeScreen2.widthHeight.exceeded"));
                    this.activeScreen2.destroy();
                }

                if (!(this.isBlankString(this.smlActiveScreenFileName)))
                    if (!(this.isValidFileType(this.smlActiveScreenFileName, AimsConstants.FILE_TYPE_JPEG)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.smlActiveScreen.file.type"));
                else if ((this.smlActiveScreen != null) && (this.smlActiveScreen.getFileSize() > 0) && !(this.isImageWidthHeightEqual(this.smlActiveScreen.getInputStream(), 75, 100))){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.smlActiveScreen.widthHeight.exceeded"));
                    this.smlActiveScreen.destroy();
                }

                if (!(this.isBlankString(this.appActiionFlashFileName)))
                    if (!(this.isValidFileType(this.appActiionFlashFileName, AimsConstants.FILE_TYPE_FLASH_ONLY)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appActiionFlash.file.type"));

                if (!(this.isBlankString(this.appGifActionFileName)))
                    if (!(this.isValidFileType(this.appGifActionFileName, AimsConstants.FILE_TYPE_GIF_ONLY)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appGifAction.file.type"));
                else if ((this.appGifAction != null) && (this.appGifAction.getFileSize() > 0) && !(this.isValidImageWidthHeight(this.appGifAction.getInputStream(), 150, 200))){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appGifAction.widthHeight.exceeded"));
                    this.appGifAction.destroy();
                }

                if (!(this.isBlankString(this.mediaStoreFileName)))
                    if (!(this.isValidFileType(this.mediaStoreFileName, AimsConstants.FILE_TYPE_JPEG)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.mediaStore.file.type"));
                else if ((this.mediaStore != null) && (this.mediaStore.getFileSize() > 0) && !(this.isImageWidthHeightEqual(this.mediaStore.getInputStream(), 80, 80))){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.mediaStore.widthHeight.exceeded"));
                    this.mediaStore.destroy();
                }

                if (!(this.isBlankString(this.flashDemoMovieFileName)))
                    if (!(this.isValidFileType(this.flashDemoMovieFileName, AimsConstants.FILE_TYPE_FLASH_ONLY)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.flashDemoMovie.file.type"));

                if (!(this.isBlankString(this.dashboardScrImgFileName)))
                    if (!(this.isValidFileType(this.dashboardScrImgFileName, AimsConstants.FILE_TYPE_JPEG)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.dashboardScrImg.file.type"));
                else if ((this.dashboardScrImg != null) && (this.dashboardScrImg.getFileSize() > 0) && !(this.isImageWidthHeightEqual(this.dashboardScrImg.getInputStream(), 228, 179))){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.dashboardScrImg.widthHeight.exceeded"));
                    this.dashboardScrImg.destroy();
                }

            }
            catch (IOException e1) {
                log.error(e1,e1);
            }

            if (!(this.isBlankString(this.companyLogoFileName)))
                if (!(this.isValidFileType(this.companyLogoFileName, AimsConstants.FILE_TYPE_JPEG)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.companyLogo.file.type"));

            if (!(this.isBlankString(this.titleShotFileName)))
                if (!(this.isValidFileType(this.titleShotFileName, AimsConstants.FILE_TYPE_JPEG)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.titleShot.file.type"));
            //End of File Type Validations

            try {
                Long appId=null;

                //Cloned application
                if ("create".equals(this.task) && this.appsId != null && this.appsId.longValue() > 0){
                    appId=null;
                }
                else {
                    appId=this.appsId;
                }
                if (!this.isBlankString(this.keyword) && BrewApplicationManager.isKeywordExists(this.keyword,appId)){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.keyword.duplicate"));
                }

            } catch (HibernateException e) {
                log.error(e,e);
            }
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {

                //Start of Check for Length of Text Areas
                if (!this.isBlankString(this.appDeployments))
                    if (this.appDeployments.length() > 1000)
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appDeployments.length"));

                //End  of Check for Length

                // Start of Dates
                if (!this.isBlankString(this.plannedDevStartDate))
                    if (!this.isDate(this.plannedDevStartDate))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.plannedDevStartDate.date", this.plannedDevStartDate));

                if (!this.isBlankString(this.plannedEntryIntoNstl))
                    if (!this.isDate(this.plannedEntryIntoNstl))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.plannedEntryIntoNstl.date", this.plannedEntryIntoNstl));

                if (!this.isBlankString(this.plannedCompletionByNstl))
                    if (!this.isDate(this.plannedCompletionByNstl))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.plannedCompletionByNstl.date", this.plannedCompletionByNstl));
                // End of Dates

            }

            if (!this.isBlankString(this.sellingPoints))
                if (this.sellingPoints.length() > 2000)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.sellingPoints.length"));

            if (!this.isBlankString(this.anticipatedDaps))
                if (this.anticipatedDaps.length() > 500)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.anticipatedDaps.length"));

            if (!this.isBlankString(this.shortCode) || !this.isBlankString(this.keyword) || isDropDownSelected(this.aggregator)){
            	
                //VALIDATION: application was submitted after validation date or old application is in SUBMITTED State.
                if (this.isBlankString(this.shortCode)){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.shortcode.required"));
                }
                if (this.isBlankString(this.keyword)){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.keyword.required"));
                }
                if (!isDropDownSelected(this.aggregator)){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.aggregator.required"));
                }
                messagingDetailValidation=true;
            }
            
            if (!this.isBlankString(this.productDescription)&& this.productDescription.length() > 1000)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.productDescription.length"));

            if (this.usingApplicationLen > AimsConstants.BREW_USER_GUIDE_FIELD_LEN)
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.usingApplication.length"));

            if (this.tipsAndTricksLen > AimsConstants.BREW_USER_GUIDE_FIELD_LEN)
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.tipsAndTricks.length"));

            if (this.faqLen > AimsConstants.BREW_USER_GUIDE_FIELD_LEN)
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.faq.length"));

            if (this.troubleshootingLen > AimsConstants.BREW_USER_GUIDE_FIELD_LEN)
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.troubleshooting.length"));

            if (this.devCompanyDisclaimerLen > AimsConstants.BREW_USER_GUIDE_FIELD_LEN)
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.devCompanyDisclaimer.length"));

            if (this.additionalInformationLen > AimsConstants.BREW_USER_GUIDE_FIELD_LEN)
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.additionalInformation.length"));

        }

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {

            //Start of Files (NULL) Validation
            if (((this.screenJpeg == null) || !(this.screenJpeg.getFileSize() > 0)) && (this.isBlankString(this.screenJpegFileName)))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.required"));

            if (appSubmitDate == null || appSubmitDate.after(validationDateForZonAutomation)
                    || this.aimsLifecyclePhaseId.longValue() == AimsConstants.SUBMISSION_ID.longValue()){

                //VALIDATION: application was submitted after validation date or old application is in SUBMITTED State.
                if (((this.highResSplash == null) || !(this.highResSplash.getFileSize() > 0)) && (this.isBlankString(this.highResSplashFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.highResSplash.required"));

                if (((this.highResActive == null) || !(this.highResActive.getFileSize() > 0)) && (this.isBlankString(this.highResActiveFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.highResActive.required"));

                if (((this.splashScreen == null) || !(this.splashScreen.getFileSize() > 0)) && (this.isBlankString(this.splashScreenFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.splashScreen.required"));

                if (((this.smallSplash == null) || !(this.smallSplash.getFileSize() > 0)) && (this.isBlankString(this.smallSplashFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.smallSplash.required"));

                if (((this.activeScreen1 == null) || !(this.activeScreen1.getFileSize() > 0)) && (this.isBlankString(this.activeScreen1FileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.activeScreen1.required"));

                if (((this.activeScreen2 == null) || !(this.activeScreen2.getFileSize() > 0)) && (this.isBlankString(this.activeScreen2FileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.activeScreen2.required"));

                if (((this.smlActiveScreen == null) || !(this.smlActiveScreen.getFileSize() > 0)) && (this.isBlankString(this.smlActiveScreenFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.smlActiveScreen.required"));

                if (((this.mediaStore == null) || !(this.mediaStore.getFileSize() > 0)) && (this.isBlankString(this.mediaStoreFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.mediaStore.required"));

                if (((this.dashboardScrImg == null) || !(this.dashboardScrImg.getFileSize() > 0)) && (this.isBlankString(this.dashboardScrImgFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.dashboardScrImg.required"));
            
                if (messagingDetailValidation==false){
					if (this.isBlankString(this.shortCode)){
						errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.shortcode.required"));
					}
					if (this.isBlankString(this.keyword)){
						errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.keyword.required"));
					}
		            if (!isDropDownSelected(this.aggregator)){
		            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.aggregator.required"));
		            }
                }
            }
            //LBS Validations
            if ((this.isLbs != null) && (this.isLbs.equals(AimsConstants.BREW_APP_CHECKBOX_IS_LBS[0])))
            {
                if (this.isBlankString(this.lbsAppType))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.lbstype_required"));

                if ((this.listSelectedGeoServices == null) || (!(this.listSelectedGeoServices.length > 0)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.lbsGeoService.required"));
            }


            if (appSubmitDate == null || appSubmitDate.after(validationDateForZonAutomation)
                    || this.aimsLifecyclePhaseId.longValue() == AimsConstants.SUBMISSION_ID.longValue()){

                //VALIDATION: application was submitted after validation date or old application is in SUBMITTED State.
                if (this.isBlankString(this.userGuideFileName)){
                    if (this.isBlankString(this.productDescription))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.productDescription.required"));

                    if (this.isBlankString(this.multiPlayer))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.multiPlayer.required"));

                    if (this.isBlankString(this.usingApplication))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.usingApplication.required"));

                    if (this.isBlankString(this.tipsAndTricks))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.tipsAndTricks.required"));

                    if (this.isBlankString(this.faq))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.faq.required"));

                    if (this.isBlankString(this.troubleshooting))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.troubleshooting.required"));

                    if (this.isBlankString(this.devCompanyDisclaimer))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.devCompanyDisclaimer.required"));

                    if (this.isBlankString(this.additionalInformation))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.additionalInformation.required"));
                }

            }

            //End   of Files (NULL) Validation

            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                if ((this.listSelectedDevices == null) || (!(this.listSelectedDevices.length > 0)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.listSelectedDevices.required"));

                //Start of Dates Section
                if (this.isBlankString(this.plannedDevStartDate))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.plannedDevStartDate.required"));

                if (this.isBlankString(this.plannedEntryIntoNstl))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.plannedEntryIntoNstl.required"));

                if (this.isBlankString(this.sellingPoints))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.sellingPoints.required"));

                if (this.isBlankString(this.networkUse))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.networkUse.required"));

                if (this.isBlankString(this.singleMultiPlayer))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.singleMultiPlayer.required"));

                if (this.isBlankString(this.prizes))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.prizes.required"));

                if (appSubmitDate == null || appSubmitDate.after(validationDateForZonAutomation)
                        || this.aimsLifecyclePhaseId.longValue() == AimsConstants.SUBMISSION_ID.longValue()){
                    //VALIDATION: application was submitted after validation date or old application is in SUBMITTED State.
                    if (this.isBlankString(this.ugcChat))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.ugcChat.required"));
                }

                if ((this.ifPrRelease == null) || (!(this.ifPrRelease.equals("Y"))))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.prRelease.accept"));
            }

            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                if (ApplicationHelper.checkAccess(request, taskName, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_BREW_RELATED_INFO))
                {
                    // Start of Dates
                    if (!this.isBlankString(this.plannedDevStartDate))
                        if (!this.isDate(this.plannedDevStartDate))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.plannedDevStartDate.date", this.plannedDevStartDate));

                    if (!this.isBlankString(this.plannedEntryIntoNstl))
                        if (!this.isDate(this.plannedEntryIntoNstl))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.plannedEntryIntoNstl.date", this.plannedEntryIntoNstl));

                    if (!this.isBlankString(this.plannedCompletionByNstl))
                        if (!this.isDate(this.plannedCompletionByNstl))
                            errors.add(
                                ActionErrors.GLOBAL_MESSAGE,
                                new ActionMessage("error.brew.app.plannedCompletionByNstl.date", this.plannedCompletionByNstl));

                    if (!this.isBlankString(this.plannedEntryIntoVzw))
                        if (!this.isDate(this.plannedEntryIntoVzw))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.plannedEntryIntoVzw.date", this.plannedEntryIntoVzw));

                    if (!this.isBlankString(this.plannedCompletionByVzw))
                        if (!this.isDate(this.plannedCompletionByVzw))
                            errors.add(
                                ActionErrors.GLOBAL_MESSAGE,
                                new ActionMessage("error.brew.app.plannedCompletionByVzw.date", this.plannedCompletionByVzw));

                    if (!this.isBlankString(this.deckLaunchDate))
                        if (!this.isDate(this.deckLaunchDate))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.deckLaunchDate.date", this.deckLaunchDate));
                    //End of Dates Section
                }

            }

        }

        return errors;

    }

    public void saveTempBrewFiles(HttpServletRequest request, String sessionId, String currUserId, ActionErrors errors)
    {
        log.debug("\n\nIn saveTempBrewFiles of BrewApplicationUpdateForm: \n\n");
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        MessageResources defaultBundle =(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
        String userGuideImageSize = defaultBundle.getMessage("file.image.brew.userGuide");
        TempFile tempFile = null;

        String _6MBFile = "6291456";
        String _30KBFile = "30720";
        String _6KBFile = "6144";
        String _150KBFile = "153600";

        try
        {

            if ((this.progGuide != null) && (this.progGuide.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.progGuide.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appProgGuide.fileSize.exceeded"));
                this.progGuide.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.progGuide, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.progGuideTempFileId = tempFile.getFileId();
                    this.progGuideFileName = tempFile.getFileName();
                }
            }

            if ((this.appTitleName != null) && (this.appTitleName.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.appTitleName.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appAppTitleName.fileSize.exceeded"));
                this.appTitleName.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appTitleName, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appTitleNameTempFileId = tempFile.getFileId();
                    this.appTitleNameFileName = tempFile.getFileName();
                }
            }

            if ((this.highResSplash != null) && (this.highResSplash.getFileSize() > 0) && !(this.isValidFileSize(_6MBFile, this.highResSplash.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.highResSplash.fileSize.exceeded"));
                this.highResSplash.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.highResSplash, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.highResSplashTempFileId = tempFile.getFileId();
                    this.highResSplashFileName = tempFile.getFileName();
                }
            }

            if ((this.highResActive != null) && (this.highResActive.getFileSize() > 0) && !(this.isValidFileSize(_6MBFile, this.highResActive.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.highResActive.fileSize.exceeded"));
                this.highResActive.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.highResActive, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.highResActiveTempFileId = tempFile.getFileId();
                    this.highResActiveFileName = tempFile.getFileName();
                }
            }

            if ((this.splashScreen != null) && (this.splashScreen.getFileSize() > 0) && !(this.isValidFileSize(_30KBFile, this.splashScreen.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.splashScreen.fileSize.exceeded"));
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

            if ((this.smallSplash != null) && (this.smallSplash.getFileSize() > 0) && !(this.isValidFileSize(_6KBFile, this.smallSplash.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.smallSplash.fileSize.exceeded"));
                this.smallSplash.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.smallSplash, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.smallSplashTempFileId = tempFile.getFileId();
                    this.smallSplashFileName = tempFile.getFileName();
                }
            }

            if ((this.activeScreen1 != null) && (this.activeScreen1.getFileSize() > 0) && !(this.isValidFileSize(_30KBFile, this.activeScreen1.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.activeScreen1.fileSize.exceeded"));
                this.activeScreen1.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.activeScreen1, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.activeScreen1TempFileId = tempFile.getFileId();
                    this.activeScreen1FileName = tempFile.getFileName();
                }
            }

            if ((this.activeScreen2 != null) && (this.activeScreen2.getFileSize() > 0) && !(this.isValidFileSize(_30KBFile, this.activeScreen2.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.activeScreen2.fileSize.exceeded"));
                this.activeScreen2.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.activeScreen2, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.activeScreen2TempFileId = tempFile.getFileId();
                    this.activeScreen2FileName = tempFile.getFileName();
                }
            }

            if ((this.smlActiveScreen != null) && (this.smlActiveScreen.getFileSize() > 0) && !(this.isValidFileSize(_6KBFile, this.smlActiveScreen.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.smlActiveScreen.fileSize.exceeded"));
                this.smlActiveScreen.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.smlActiveScreen, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.smlActiveScreenTempFileId = tempFile.getFileId();
                    this.smlActiveScreenFileName = tempFile.getFileName();
                }
            }

            if ((this.appActiionFlash != null) && (this.appActiionFlash.getFileSize() > 0) && !(this.isValidFileSize(_150KBFile, this.appActiionFlash.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appActiionFlash.fileSize.exceeded"));
                this.appActiionFlash.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appActiionFlash, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appActiionFlashTempFileId = tempFile.getFileId();
                    this.appActiionFlashFileName = tempFile.getFileName();
                }
            }

            if ((this.appGifAction != null) && (this.appGifAction.getFileSize() > 0) && !(this.isValidFileSize(_150KBFile, this.appGifAction.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.appGifAction.fileSize.exceeded"));
                this.appGifAction.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appGifAction, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appGifActionTempFileId = tempFile.getFileId();
                    this.appGifActionFileName = tempFile.getFileName();
                }
            }

            if ((this.mediaStore != null) && (this.mediaStore.getFileSize() > 0) && !(this.isValidFileSize(_6KBFile, this.mediaStore.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.mediaStore.fileSize.exceeded"));
                this.mediaStore.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.mediaStore, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.mediaStoreTempFileId = tempFile.getFileId();
                    this.mediaStoreFileName = tempFile.getFileName();
                }
            }
            if ((this.flashDemoMovie != null) && (this.flashDemoMovie.getFileSize() > 0))
            {
                tempFile = TempFilesManager.saveTempFile(this.flashDemoMovie, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.flashDemoMovieTempFileId = tempFile.getFileId();
                    this.flashDemoMovieFileName = tempFile.getFileName();
                }
            }

            if ((this.dashboardScrImg != null) && (this.dashboardScrImg.getFileSize() > 0))
            {
                tempFile = TempFilesManager.saveTempFile(this.dashboardScrImg, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.dashboardScrImgTempFileId = tempFile.getFileId();
                    this.dashboardScrImgFileName = tempFile.getFileName();
                }
            }

            if ((this.companyLogo != null)
                    && (this.companyLogo.getFileSize() > 0)
                    && !(this.isValidFileSize(userGuideImageSize, this.companyLogo.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.companyLogo.fileSize.exceeded"));
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

            if ((this.titleShot != null)
                    && (this.titleShot.getFileSize() > 0)
                    && !(this.isValidFileSize(userGuideImageSize, this.titleShot.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.brew.app.titleShot.fileSize.exceeded"));
                this.titleShot.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.titleShot, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.titleShotTempFileId = tempFile.getFileId();
                    this.titleShotFileName = tempFile.getFileName();
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempFiles() of ApplicationUpdateForm");
        }
        finally
        {
            log.debug("Finally called IN saveTempFiles() of ApplicationUpdateForm");
        }

    }
    public java.util.Collection getDeckList()
    {
        return deckList;
    }
    public void setDeckList(java.util.Collection deckList)
    {
        this.deckList = deckList;
    }

    public java.lang.Long getConceptId()
    {
        return conceptId;
    }

    public void setConceptId(java.lang.Long long1)
    {
        conceptId = long1;
    }

    public java.lang.String getConceptEvaluationDate()
    {
        return conceptEvaluationDate;
    }

    public java.lang.String getConceptTitle()
    {
        return conceptTitle;
    }

    public void setConceptEvaluationDate(java.lang.String string)
    {
        conceptEvaluationDate = string;
    }

    public void setConceptTitle(java.lang.String string)
    {
        conceptTitle = string;
    }

    public java.lang.String getIsConcept()
    {
        return isConcept;
    }

    public void setIsConcept(java.lang.String string)
    {
        isConcept = string;
    }

    public java.lang.String getSelectedDevicesAlertMessage()
    {
        return selectedDevicesAlertMessage;
    }

    public void setSelectedDevicesAlertMessage(java.lang.String string)
    {
        selectedDevicesAlertMessage = string;
    }

    public FormFile getProgGuide()
    {
        return progGuide;
    }

    public java.lang.String getProgGuideFileName()
    {
        return progGuideFileName;
    }

    public java.lang.Long getProgGuideTempFileId()
    {
        return progGuideTempFileId;
    }

    public void setProgGuide(FormFile file)
    {
        progGuide = file;
    }

    public void setProgGuideFileName(java.lang.String string)
    {
        progGuideFileName = string;
    }

    public void setProgGuideTempFileId(java.lang.Long long1)
    {
        progGuideTempFileId = long1;
    }

    public java.lang.String getPrizes()
    {
        return prizes;
    }

    public void setPrizes(java.lang.String string)
    {
        prizes = string;
    }

    /**
     * @return
     */
    public String getAppTypeFullName()
    {
        return appTypeFullName;
    }

    /**
     * @param string
     */
    public void setAppTypeFullName(String string)
    {
        appTypeFullName = string;
    }

    /**
     * @return
     */
    public java.lang.String getIsLbs()
    {
        return isLbs;
    }

    /**
     * @return
     */
    public java.lang.String getLbsAppType()
    {
        return lbsAppType;
    }

    /**
     * @return
     */
    public java.lang.String getLbsClientId()
    {
        return lbsClientId;
    }

    /**
     * @return
     */
    public java.lang.String getLbsSecretKey()
    {
        return lbsSecretKey;
    }

    /**
     * @param string
     */
    public void setIsLbs(java.lang.String string)
    {
        isLbs = string;
    }

    /**
     * @param string
     */
    public void setLbsAppType(java.lang.String string)
    {
        lbsAppType = string;
    }

    /**
     * @param string
     */
    public void setLbsClientId(java.lang.String string)
    {
        lbsClientId = string;
    }

    /**
     * @param string
     */
    public void setLbsSecretKey(java.lang.String string)
    {
        lbsSecretKey = string;
    }

    /**
     * @return
     */
    public java.util.Collection getAllGeoServices()
    {
        return allGeoServices;
    }

    /**
     * @return
     */
    public String[] getListSelectedGeoServices()
    {
        return listSelectedGeoServices;
    }

    /**
     * @param collection
     */
    public void setAllGeoServices(java.util.Collection collection)
    {
        allGeoServices = collection;
    }

    /**
     * @param strings
     */
    public void setListSelectedGeoServices(String[] strings)
    {
        listSelectedGeoServices = strings;
    }

    /**
     * @return
     */
    public String[] getListAvailableGeoServices()
    {
        return listAvailableGeoServices;
    }

    /**
     * @param strings
     */
    public void setListAvailableGeoServices(String[] strings)
    {
        listAvailableGeoServices = strings;
    }

    /**
     * @return
     */
    public java.lang.Long getLbsAutodeskPhaseId()
    {
        return lbsAutodeskPhaseId;
    }

    /**
     * @param long1
     */
    public void setLbsAutodeskPhaseId(java.lang.Long long1)
    {
        lbsAutodeskPhaseId = long1;
    }

    /**
     * @return
     */
    public java.lang.String getLbsAutodeskPhaseName()
    {
        return lbsAutodeskPhaseName;
    }

    /**
     * @param string
     */
    public void setLbsAutodeskPhaseName(java.lang.String string)
    {
        lbsAutodeskPhaseName = string;
    }

    public java.lang.String getDisclaimerText() {
        return disclaimerText;
    }

    public void setDisclaimerText(java.lang.String disclaimerText) {
        this.disclaimerText = disclaimerText;
    }

    public java.lang.String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(java.lang.String productDescription) {
        this.productDescription = productDescription;
    }

    public java.lang.String getMultiPlayer() {
        return multiPlayer;
    }

    public void setMultiPlayer(java.lang.String multiPlayer) {
        this.multiPlayer = multiPlayer;
    }

    public java.lang.String getUsingApplication() {
        return usingApplication;
    }

    public void setUsingApplication(java.lang.String usingApplication) {
        this.usingApplication = SafeHTML.makeSafe(usingApplication);
    }

    public java.lang.String getTipsAndTricks() {
        return tipsAndTricks;
    }

    public void setTipsAndTricks(java.lang.String tipsAndTricks) {
        this.tipsAndTricks = SafeHTML.makeSafe(tipsAndTricks);
    }

    public java.lang.String getFaq() {
        return faq;
    }

    public void setFaq(java.lang.String faq) {
        this.faq = SafeHTML.makeSafe(faq);
    }

    public java.lang.String getTroubleshooting() {
        return troubleshooting;
    }

    public void setTroubleshooting(java.lang.String troubleshooting) {
        this.troubleshooting = SafeHTML.makeSafe(troubleshooting);
    }

    public java.lang.String getDevCompanyDisclaimer() {
        return devCompanyDisclaimer;
    }

    public void setDevCompanyDisclaimer(java.lang.String devCompanyDisclaimer) {
        this.devCompanyDisclaimer = SafeHTML.makeSafe(devCompanyDisclaimer);
    }

    public java.lang.String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(java.lang.String additionalInformation) {
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

    public int getAirTimeLen() {
        return airTimeLen;
    }

    public void setAirTimeLen(int airTimeLen) {
        this.airTimeLen = airTimeLen;
    }

    public java.util.Collection getAllBrewContentRatings() {
        return allBrewContentRatings;
    }

    public void setAllBrewContentRatings(java.util.Collection allBrewContentRatings) {
        this.allBrewContentRatings = allBrewContentRatings;
    }

    public Long getContentRating() {
        return contentRating;
    }

    public void setContentRating(Long contentRating) {
        this.contentRating = contentRating;
    }

    public List getHistory() {
        return history;
    }

    public void setHistory(List history) {
        this.history = history;
    }

    public List getAllAggregators() {
        return allAggregators;
    }

    public void setAllAggregators(List allAggregators) {
        this.allAggregators = allAggregators;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getAggregator() {
        return aggregator;
    }

    public void setAggregator(Long aggregator) {
        this.aggregator = aggregator;
    }

    public FormFile getHighResSplash() {
        return highResSplash;
    }

    public void setHighResSplash(FormFile highResSplash) {
        this.highResSplash = highResSplash;
    }

    public FormFile getHighResActive() {
        return highResActive;
    }

    public void setHighResActive(FormFile highResActive) {
        this.highResActive = highResActive;
    }

    public FormFile getSplashScreen() {
        return splashScreen;
    }

    public void setSplashScreen(FormFile splashScreen) {
        this.splashScreen = splashScreen;
    }

    public FormFile getSmallSplash() {
        return smallSplash;
    }

    public void setSmallSplash(FormFile smallSplash) {
        this.smallSplash = smallSplash;
    }

    public FormFile getActiveScreen1() {
        return activeScreen1;
    }

    public void setActiveScreen1(FormFile activeScreen1) {
        this.activeScreen1 = activeScreen1;
    }

    public FormFile getActiveScreen2() {
        return activeScreen2;
    }

    public void setActiveScreen2(FormFile activeScreen2) {
        this.activeScreen2 = activeScreen2;
    }

    public FormFile getSmlActiveScreen() {
        return smlActiveScreen;
    }

    public void setSmlActiveScreen(FormFile smlActiveScreen) {
        this.smlActiveScreen = smlActiveScreen;
    }

    public FormFile getAppActiionFlash() {
        return appActiionFlash;
    }

    public void setAppActiionFlash(FormFile appActiionFlash) {
        this.appActiionFlash = appActiionFlash;
    }

    public FormFile getAppGifAction() {
        return appGifAction;
    }

    public void setAppGifAction(FormFile appGifAction) {
        this.appGifAction = appGifAction;
    }

    public FormFile getMediaStore() {
        return mediaStore;
    }

    public void setMediaStore(FormFile mediaStore) {
        this.mediaStore = mediaStore;
    }

    public java.lang.String getMediaStoreFileName() {
        return mediaStoreFileName;
    }

    public void setMediaStoreFileName(java.lang.String mediaStoreFileName) {
        this.mediaStoreFileName = mediaStoreFileName;
    }

    public java.lang.String getAppGifActionFileName() {
        return appGifActionFileName;
    }

    public void setAppGifActionFileName(java.lang.String appGifActionFileName) {
        this.appGifActionFileName = appGifActionFileName;
    }

    public java.lang.String getAppActiionFlashFileName() {
        return appActiionFlashFileName;
    }

    public void setAppActiionFlashFileName(java.lang.String appActiionFlashFileName) {
        this.appActiionFlashFileName = appActiionFlashFileName;
    }

    public java.lang.String getSmlActiveScreenFileName() {
        return smlActiveScreenFileName;
    }

    public void setSmlActiveScreenFileName(java.lang.String smlActiveScreenFileName) {
        this.smlActiveScreenFileName = smlActiveScreenFileName;
    }

    public java.lang.String getActiveScreen2FileName() {
        return activeScreen2FileName;
    }

    public void setActiveScreen2FileName(java.lang.String activeScreen2FileName) {
        this.activeScreen2FileName = activeScreen2FileName;
    }

    public java.lang.String getActiveScreen1FileName() {
        return activeScreen1FileName;
    }

    public void setActiveScreen1FileName(java.lang.String activeScreen1FileName) {
        this.activeScreen1FileName = activeScreen1FileName;
    }

    public java.lang.String getSmallSplashFileName() {
        return smallSplashFileName;
    }

    public void setSmallSplashFileName(java.lang.String smallSplashFileName) {
        this.smallSplashFileName = smallSplashFileName;
    }

    public java.lang.String getHighResActiveFileName() {
        return highResActiveFileName;
    }

    public void setHighResActiveFileName(java.lang.String highResActiveFileName) {
        this.highResActiveFileName = highResActiveFileName;
    }

    public java.lang.String getHighResSplashFileName() {
        return highResSplashFileName;
    }

    public void setHighResSplashFileName(java.lang.String highResSplashFileName) {
        this.highResSplashFileName = highResSplashFileName;
    }

    public java.lang.String getSplashScreenFileName() {
        return splashScreenFileName;
    }

    public void setSplashScreenFileName(java.lang.String splashScreenFileName) {
        this.splashScreenFileName = splashScreenFileName;
    }

    public java.lang.Long getHighResSplashTempFileId() {
        return highResSplashTempFileId;
    }

    public void setHighResSplashTempFileId(java.lang.Long highResSplashTempFileId) {
        this.highResSplashTempFileId = highResSplashTempFileId;
    }

    public java.lang.Long getHighResActiveTempFileId() {
        return highResActiveTempFileId;
    }

    public void setHighResActiveTempFileId(java.lang.Long highResActiveTempFileId) {
        this.highResActiveTempFileId = highResActiveTempFileId;
    }

    public java.lang.Long getSplashScreenTempFileId() {
        return splashScreenTempFileId;
    }

    public void setSplashScreenTempFileId(java.lang.Long splashScreenTempFileId) {
        this.splashScreenTempFileId = splashScreenTempFileId;
    }

    public java.lang.Long getSmallSplashTempFileId() {
        return smallSplashTempFileId;
    }

    public void setSmallSplashTempFileId(java.lang.Long smallSplashTempFileId) {
        this.smallSplashTempFileId = smallSplashTempFileId;
    }

    public java.lang.Long getActiveScreen1TempFileId() {
        return activeScreen1TempFileId;
    }

    public void setActiveScreen1TempFileId(java.lang.Long activeScreen1TempFileId) {
        this.activeScreen1TempFileId = activeScreen1TempFileId;
    }

    public java.lang.Long getActiveScreen2TempFileId() {
        return activeScreen2TempFileId;
    }

    public void setActiveScreen2TempFileId(java.lang.Long activeScreen2TempFileId) {
        this.activeScreen2TempFileId = activeScreen2TempFileId;
    }

    public java.lang.Long getSmlActiveScreenTempFileId() {
        return smlActiveScreenTempFileId;
    }

    public void setSmlActiveScreenTempFileId(
            java.lang.Long smlActiveScreenTempFileId) {
        this.smlActiveScreenTempFileId = smlActiveScreenTempFileId;
    }

    public java.lang.Long getAppActiionFlashTempFileId() {
        return appActiionFlashTempFileId;
    }

    public void setAppActiionFlashTempFileId(
            java.lang.Long appActiionFlashTempFileId) {
        this.appActiionFlashTempFileId = appActiionFlashTempFileId;
    }

    public java.lang.Long getAppGifActionTempFileId() {
        return appGifActionTempFileId;
    }

    public void setAppGifActionTempFileId(java.lang.Long appGifActionTempFileId) {
        this.appGifActionTempFileId = appGifActionTempFileId;
    }

    public java.lang.Long getMediaStoreTempFileId() {
        return mediaStoreTempFileId;
    }

    public void setMediaStoreTempFileId(java.lang.Long mediaStoreTempFileId) {
        this.mediaStoreTempFileId = mediaStoreTempFileId;
    }

    public java.lang.String[] getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(java.lang.String[] errorMessages) {
        this.errorMessages = errorMessages;
    }

    public java.lang.String getUserGuidePdfFileName() {
        return userGuidePdfFileName;
    }

    public void setUserGuidePdfFileName(java.lang.String userGuidePdfFileName) {
        this.userGuidePdfFileName = userGuidePdfFileName;
    }

    public java.lang.String getUgcChat() {
        return ugcChat;
    }

    public void setUgcChat(java.lang.String ugcChat) {
        this.ugcChat = ugcChat;
    }

    public java.lang.String getChatPrize() {
        return chatPrize;
    }

    public void setChatPrize(java.lang.String chatPrize) {
        this.chatPrize = chatPrize;
    }

    public FormFile getFlashDemoMovie() {
        return flashDemoMovie;
    }

    public void setFlashDemoMovie(FormFile flashDemoMovie) {
        this.flashDemoMovie = flashDemoMovie;
    }

    public FormFile getDashboardScrImg() {
        return dashboardScrImg;
    }

    public void setDashboardScrImg(FormFile dashboardScrImg) {
        this.dashboardScrImg = dashboardScrImg;
    }

    public java.lang.String getFlashDemoMovieFileName() {
        return flashDemoMovieFileName;
    }

    public void setFlashDemoMovieFileName(java.lang.String flashDemoMovieFileName) {
        this.flashDemoMovieFileName = flashDemoMovieFileName;
    }

    public java.lang.String getDashboardScrImgFileName() {
        return dashboardScrImgFileName;
    }

    public void setDashboardScrImgFileName(java.lang.String dashboardScrImgFileName) {
        this.dashboardScrImgFileName = dashboardScrImgFileName;
    }

    public java.lang.Long getFlashDemoMovieTempFileId() {
        return flashDemoMovieTempFileId;
    }

    public void setFlashDemoMovieTempFileId(java.lang.Long flashDemoMovieTempFileId) {
        this.flashDemoMovieTempFileId = flashDemoMovieTempFileId;
    }

    public java.lang.Long getDashboardScrImgTempFileId() {
        return dashboardScrImgTempFileId;
    }

    public void setDashboardScrImgTempFileId(
            java.lang.Long dashboardScrImgTempFileId) {
        this.dashboardScrImgTempFileId = dashboardScrImgTempFileId;
    }


}
