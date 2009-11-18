package com.netpace.aims.controller.application;

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.application.JavaApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.AimsTypeManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.system.AimsDeckManager;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.util.SafeHTML;

/**
 * @struts.form name="javaApplicationUpdateForm"
 */
public class JavaApplicationUpdateForm extends ApplicationUpdateForm
{
    static Logger log = Logger.getLogger(JavaApplicationUpdateForm.class.getName());

    private java.lang.Long cloneFromAppId;
    private java.lang.Long javaAppsId;
    private java.util.Collection allJavaContentRatings;
    private java.lang.Long contentRating;
    private String infoURL;
    private String enterpriseApp;
    private String enterpriseId;
    private String appKeyword;
        
    private java.util.Collection allJavaContracts;
    private java.lang.Long javaAppContractId;

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
    private Long companyLogoTempFileId;
    private Long titleImageTempFileId;

    private Long ringNumber;
    private Boolean ring2App;
    private Boolean ring3App;

    private java.lang.Long contentType;
    private java.util.Collection allContentTypes;

    private java.lang.String projectedLiveDate;

    private java.lang.Long appCategory1;
    private java.lang.Long appSubCategory1;
    private java.lang.Long appCategory2;
    private java.lang.Long appSubCategory2;

    protected java.lang.String comments;

    private java.util.Collection allTaxCategoryCodes;
    private java.lang.Long aimsTaxCategoryCodeId;

    private List workItemsList;
    protected java.lang.String actionComments;

    private java.lang.Long stepToValidate;

    // Variables pertaining to work item
    private String wiTitle;
    private String wiStartDate;
    private Vector wiActions;
    private Long wiWorkitemId;
    private Long selectedAction;
    private Boolean showExecute;
    private Boolean lockContentRating;


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

    private java.lang.String appSize;

    private FormFile progGuide;
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

    private String progGuideFileName;
    private java.lang.String userGuidePdfFileName;
    private java.lang.String flashDemoMovieFileName;
    private java.lang.String dashboardScrImgFileName;

    private java.lang.Long progGuideTempFileId;
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

    private java.lang.String allianceName;
    private java.lang.Long vendorId;

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


    private List history;

    private String shortCode;
    private String keyword;


    private String originalTask;

    private java.lang.Long ringId;

    private java.lang.String[] errorMessages;

    // extra i think

    private String contentZipFileFileName;
    private String contentZipFileTempFileId;


    private String isNewContentZipFileUploaded;

    private String initialApprovalNotes ;
    private String initialApprovalAction ;
    private String contentZipFileNotes ;
    private String contentZipFileAction ;
    private String moveToProduction ;
    private String remove ;


    public String getInitialApprovalNotes() {
        return initialApprovalNotes;
    }

    public void setInitialApprovalNotes(String initialApprovalNotes) {
        this.initialApprovalNotes = initialApprovalNotes;
    }

    public java.lang.Long getJavaAppsId() {
        return javaAppsId;
    }

    public void setJavaAppsId(java.lang.Long javaAppsId) {
        this.javaAppsId = javaAppsId;
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
        if ( log.isDebugEnabled() )
            log.debug("\n\nIn Reset of JavaApplicationUpdateForm: \n\n");

        try
        {
            super.reset(mapping, request, AimsConstants.JAVA_PLATFORM_ID);
            this.deckList = AimsDeckManager.getDeckList();

            this.allJavaContentRatings = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_JAVA_CONTENT_RATING_ID);
            this.allContentTypes = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_JAVA_CONTENT_TYPE_ID);
            this.allTaxCategoryCodes = JavaApplicationManager.getTaxCategoryCodes();            
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

        if ( StringUtils.isEmpty(this.appSubmitType) )
        	return errors;
        
        Long currentUserAllianceId = null;
        if ( this.aimsAllianceId!=null )
            currentUserAllianceId = this.aimsAllianceId;
        else
            currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();

        if(currUserType.equalsIgnoreCase(AimsConstants.VZW_USERTYPE)) {
            try {
                //load contracts for vzw user only (to save unnecessary calls to load contracts)
                log.debug("loading all java contracts....");
                this.setAllJavaContracts(ContractsManager.getContractsByPlatform(AimsConstants.JAVA_PLATFORM_ID, AimsConstants.CONTRACT_STATUS_ACTIVE));
            } catch (HibernateException e) {
                log.error(e,e);
            }
        }

        /******* do not load alliance contracts
            try {
                this.setAllJavaContracts(JavaApplicationManager.getContracts(currentUserAllianceId));
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        *******/

        if (!"paging".equalsIgnoreCase(this.appSubmitType)){
            this.setErrorMessages(null);

            validateToDBFields(currUserType,errors,request);
            if (!errors.isEmpty()){
                this.setCurrentPage("page1");
                JavaApplicationHelper.prePopulateForm(this);
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
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.title.required"));

            if ( this.isBlankString(this.version) )
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.version.required"));
            }
            else if ( ! ((Pattern.compile("\\d((\\d)*(\\.)*)*")).matcher(this.version.trim())).matches()  )
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.version.format"));
            }

            if (this.isBlankString(this.shortDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.shortDesc.required"));

            if (this.isBlankString(this.longDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.longDesc.required"));

            if(!isBlankString(clrPubLogoFileName) && !isValidFileType(clrPubLogoFileName, AimsConstants.FILE_TYPE_GIF_JPG_EPS))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.java.app.appClrPubLogo.file.type"));

            if(!isBlankString(appTitleNameFileName) && !isValidFileType(appTitleNameFileName, AimsConstants.FILE_TYPE_GIF_JPG_EPS))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.java.app.appAppTitleName.file.type"));

            if(!isBlankString(splashScreenEpsFileName) && !isValidFileType(splashScreenEpsFileName, AimsConstants.FILE_TYPE_GIF_JPG_EPS))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.java.app.appSplashScreen.file.type"));

            if(!isBlankString(activeScreenEpsFileName) && !isValidFileType(activeScreenEpsFileName, AimsConstants.FILE_TYPE_GIF_JPG_EPS))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.java.app.appActiveScreen.file.type"));

            if(!isBlankString(screenJpegFileName) && !isValidFileType(screenJpegFileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.java.app.appScreenShot.file.type"));

            if(!isBlankString(screenJpeg2FileName) && !isValidFileType(screenJpeg2FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.java.app.appScreenShot.file.type"));

            if(!isBlankString(screenJpeg3FileName) && !isValidFileType(screenJpeg3FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.java.app.appScreenShot.file.type"));

            if(!isBlankString(screenJpeg4FileName) && !isValidFileType(screenJpeg4FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.java.app.appScreenShot.file.type"));

            if(!isBlankString(screenJpeg5FileName) && !isValidFileType(screenJpeg5FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.java.app.appScreenShot.file.type"));

            if(!isBlankString(faqDocFileName) && !isValidFileType(faqDocFileName, AimsConstants.FILE_TYPE_DOC))
                errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.manage.app.appFAQ.file.type"));

            if (!(this.isBlankString(this.companyLogoFileName)))
                if (!(this.isValidFileType(this.companyLogoFileName, AimsConstants.FILE_TYPE_JPEG)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.companyLogo.file.type"));

            if (!(this.isBlankString(this.titleImageFileName)))
                if (!(this.isValidFileType(this.titleImageFileName, AimsConstants.FILE_TYPE_JPEG)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.titleImage.file.type"));

            // Email Validation
            if (!this.isDropDownSelected(this.aimsContactId)) {
                if (!this.isBlankString(this.contactEmail))
                    if (!this.isValidEmail(this.contactEmail))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactEmail.email", this.contactEmail));
            }

        }

        /*
         * check uniqueness of the keyword when the application is submitted 
         */        
        if ( 
        		this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM)
        		|| this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM)	
        	)
        {
        	if (this.isBlankString(this.appKeyword))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.appkeywords.required"));
        	else if ( ! Pattern.compile("^[A-Za-z0-9-]+$").matcher(this.appKeyword).matches() )
        		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.keyword.format"));
        	else
        	{
        		try
        		{        			
        			if ( !( JavaApplicationManager.getApplicationWithKeyword(this.appKeyword, this.appsId) == null ) )
        				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.appkeywords.exists"));
        		}
        		catch (HibernateException e)
        		{
        			e.printStackTrace();
        			new AimsException(e);
        		}
        	}
        }
        else if (                
                this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT) ||                
                this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)
            )
        {
        	if (this.isBlankString(this.appKeyword))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.appkeywords.required"));
        	else if ( ! Pattern.compile("^[A-Za-z0-9-]+$").matcher(this.appKeyword).matches() )
        		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.keyword.format"));
        }
        
        CommonProperties commonProps = CommonProperties.getInstance();
        if ( (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_EXECUTE_ACTION))
                && this.aimsLifecyclePhaseId.equals(AimsConstants.PHASE_JAVA_SUBMITTED) && !selectedAction.toString().equalsIgnoreCase("203") && !selectedAction.toString().equalsIgnoreCase("202") )
        {
        	if (!this.isDropDownSelected(this.contentType))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contentType.required"));

            if (this.isBlankString(this.projectedLiveDate))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.projectedLiveDate.required"));

            if (!this.isBlankString(this.projectedLiveDate))
            	if (!this.isDate(this.projectedLiveDate))
            		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.vzwLiveDate.date", this.projectedLiveDate));
            
            if (this.enterpriseApp.equals(AimsConstants.YES_CHAR) && this.isBlankString(this.enterpriseId) )
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.enterpriseId.required"));
            
            if (this.isBlankString(this.appKeyword))
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.appkeywords.required"));
            else if ( ! Pattern.compile("^[A-Za-z0-9-]+$").matcher(this.appKeyword).matches() )
            	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.keyword.format"));
            else
            {
                try
                {
                    if ( !( JavaApplicationManager.getApplicationWithKeyword(this.appKeyword, this.appsId) == null ) )
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.appkeywords.exists"));
                }
                catch (HibernateException e)
                {
                    e.printStackTrace();
                    new AimsException(e);
                }
            }
        }

        if ( (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_EXECUTE_ACTION))
                && ( this.aimsLifecyclePhaseId.equals(AimsConstants.PHASE_JAVA_CONTENT_APPROVED) || this.aimsLifecyclePhaseId.equals(AimsConstants.PHASE_JAVA_LEGAL_APPROVED) ) && !selectedAction.toString().equalsIgnoreCase("503") && !selectedAction.toString().equalsIgnoreCase("502") )
        {
            if (!this.isDropDownSelected(this.contentRating))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contentRating.required"));
        }

        if ( (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_EXECUTE_ACTION))
                && ( this.aimsLifecyclePhaseId.equals(AimsConstants.PHASE_JAVA_PENDING_TAX_APPROVAL) ) && !selectedAction.toString().equalsIgnoreCase("702") && !selectedAction.toString().equalsIgnoreCase("703") )
        {
            if (!this.isDropDownSelected(this.aimsTaxCategoryCodeId))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.taxCategory.required"));
        }

        if ( (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_EXECUTE_ACTION)) )
        {
            if (!this.isDropDownSelected(this.selectedAction))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.selectedAction.required"));
        }
        
        if (
                this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM) ||
                this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT) ||
                this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM) ||
                this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)
            )
        {


            if (((this.screenJpeg == null) || !(this.screenJpeg.getFileSize() > 0)) && (this.isBlankString(this.screenJpegFileName)))
                if (((this.screenJpeg2 == null) || !(this.screenJpeg2.getFileSize() > 0)) && (this.isBlankString(this.screenJpeg2FileName)))
                    if (((this.screenJpeg3 == null) || !(this.screenJpeg3.getFileSize() > 0)) && (this.isBlankString(this.screenJpeg3FileName)))
                        if (((this.screenJpeg4 == null) || !(this.screenJpeg4.getFileSize() > 0)) && (this.isBlankString(this.screenJpeg4FileName)))
                            if (((this.screenJpeg5 == null) || !(this.screenJpeg5.getFileSize() > 0)) && (this.isBlankString(this.screenJpeg5FileName)))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.appScreenShot.required"));

            if ( AimsConstants.CONTRACT_RING_3_ID.equals(this.ringNumber) )
            {
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
            }           

            if (!this.isDropDownSelected(this.contentRating))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contentRating.required"));

            /*
            if (!this.isDropDownSelected(this.javaAppContractId))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contract.required"));
            */
            if (!this.isDropDownSelected(this.aimsAppCategoryId))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.category.required"));

            if (!this.isDropDownSelected(this.aimsAppSubCategoryId))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.subCategory.required"));

            if ( AimsConstants.CONTRACT_RING_2_ID.equals(this.ringNumber) )
            {
                if (this.isBlankString(this.enterpriseApp))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.enterpriseApp.required"));

                if (  !  (this.isBlankString(this.ifPrRelease)
                        ||	!( this.ifPrRelease.equals(AimsConstants.NO_CHAR )))
                    ){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.ifPrRelease.required"));
                }
                if (this.isBlankString(this.infoURL)){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.infourl.required"));
                }

                if (!isBlankString(this.infoURL) && ! this.isValidWebURL(this.infoURL) )
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.infourl.invalid"));

                if (((this.splashScreenEps == null) || !(this.splashScreenEps.getFileSize() > 0)) && (this.isBlankString(this.splashScreenEpsFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.appSplashScreen.required"));
            }

            if (((this.companyLogo == null) || !(this.companyLogo.getFileSize() > 0)) && (this.isBlankString(this.companyLogoFileName)))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.companyLogo.required"));

            if (((this.titleImage == null) || !(this.titleImage.getFileSize() > 0)) && (this.isBlankString(this.titleImageFileName)))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.titleImage.required"));

            if (this.isBlankString(this.productDescription))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.productDescription.required"));

            /*
            if (this.isBlankString(this.usingApplication))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.usingApplication.required"));

            if (this.isBlankString(this.tipsAndTricks))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.tipsAndTricks.required"));

            if (this.isBlankString(this.faq))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.faq.required"));

            if (this.isBlankString(this.troubleshooting))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.troubleshooting.required"));

            if (this.isBlankString(this.devCompanyDisclaimer))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.devCompanyDisclaimer.required"));

            if (this.isBlankString(this.additionalInformation))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.additionalInformation.required"));
             */
            if (aimsLifecyclePhaseId!=null
                    && !aimsLifecyclePhaseId.equals(AimsConstants.SUBMISSION_ID)
                    && !aimsLifecyclePhaseId.equals(AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL)){

            }
        }

        if ("paging".equalsIgnoreCase(this.appSubmitType) && "submitpage4".equalsIgnoreCase(this.task)){
            if (this.isBlankString(this.journalText))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.journalText.required"));
            else if (this.journalText.length() >2000) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.journal.text.length"));
            }
            if (this.isBlankString(this.journalType))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.journalType.required"));
        }

        if (!errors.isEmpty()){
            this.setCurrentPage("page1");
            JavaApplicationHelper.prePopulateForm(this);
        }
        return errors;

    }

    public void validateToDBFields(String currUserType, ActionErrors errors,HttpServletRequest request) {

        if (!isBlankString(this.title) && (this.title.trim().length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.title.length"));

        if (!isBlankString(this.version) && (this.version.trim().length() > 300))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.version.length"));

        if (!isBlankString(this.shortDesc) && (this.shortDesc.trim().length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appShortDesc.length"));

        if (!isBlankString(this.longDesc) && (this.longDesc.trim().length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appLongDesc.length"));

        if (!isBlankString(this.appKeyword) && (this.appKeyword.trim().length() > 40))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.appKeyword.length"));


        if (!isBlankString(this.infoURL) && (this.infoURL.trim().length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.infourl.length"));

        if ((this.clrPubLogo != null) && (this.clrPubLogo.getFileSize() > 0) && this.clrPubLogo.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.clrPubLogo.length"));
            this.clrPubLogo.destroy();
        }

        if ((this.titleImage != null) && (this.titleImage.getFileSize() > 0) && this.titleImage.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.titleImage.length"));
            this.titleImage.destroy();
        }
        if ((this.appTitleName != null) && (this.appTitleName.getFileSize() > 0) && this.appTitleName.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.appTitleName.length"));
            this.appTitleName.destroy();
        }

        if ((this.splashScreenEps != null) && (this.splashScreenEps.getFileSize() > 0) && this.splashScreenEps.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.splashScreenEps.length"));
            this.splashScreenEps.destroy();
        }

        if ((this.activeScreenEps != null) && (this.activeScreenEps.getFileSize() > 0) && this.activeScreenEps.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.activeScreenEps.length"));
            this.activeScreenEps.destroy();
        }

        if ((this.screenJpeg != null) && (this.screenJpeg.getFileSize() > 0) && this.screenJpeg.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.screenJpeg.length"));
            this.screenJpeg.destroy();
        }

        if ((this.screenJpeg2 != null) && (this.screenJpeg2.getFileSize() > 0) && this.screenJpeg2.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.screenJpeg.length"));
            this.screenJpeg2.destroy();
        }

        if ((this.screenJpeg3 != null) && (this.screenJpeg3.getFileSize() > 0) && this.screenJpeg3.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.screenJpeg.length"));
            this.screenJpeg3.destroy();
        }

        if ((this.screenJpeg4 != null) && (this.screenJpeg4.getFileSize() > 0) && this.screenJpeg4.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.screenJpeg.length"));
            this.screenJpeg4.destroy();
        }

        if ((this.screenJpeg5 != null) && (this.screenJpeg5.getFileSize() > 0) && this.screenJpeg5.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.screenJpeg.length"));
            this.screenJpeg5.destroy();
        }

        if ((this.faqDoc != null) && (this.faqDoc.getFileSize() > 0) && this.faqDoc.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.faqDoc.length"));
            this.faqDoc.destroy();
        }

        if (((this.userGuide != null) && (this.userGuide.getFileSize() > 0)) && this.userGuide.getFileName().length() > 150) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.userGuide.length"));
            this.userGuide.destroy();
        }

        if (!this.isDropDownSelected(this.aimsContactId)) {
            if (!isBlankString(this.contactFirstName) && (this.contactFirstName.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contactFirstName.length"));

            if (!isBlankString(this.contactLastName) && (this.contactLastName.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contactLastName.length"));

            if (!isBlankString(this.contactTitle) && (this.contactTitle.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contactTitle.length"));

            if (!isBlankString(this.contactEmail) && (this.contactEmail.trim().length() > 100))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contactEmail.length"));

            if (!isBlankString(this.contactPhone) && (this.contactPhone.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contactPhone.length"));

            if (!isBlankString(this.contactMobile) && (this.contactMobile.trim().length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.contactMobile.length"));
        }

        if (!this.isBlankString(this.productDescription)&& this.productDescription.length() > 1000)
             errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.productDescription.length"));

        if (this.usingApplicationLen > AimsConstants.JAVA_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.usingApplication.length"));

        if (this.tipsAndTricksLen > AimsConstants.JAVA_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.tipsAndTricks.length"));

        if (this.faqLen > AimsConstants.JAVA_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.faq.length"));

        if (this.troubleshootingLen > AimsConstants.JAVA_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.troubleshooting.length"));

        if (this.devCompanyDisclaimerLen > AimsConstants.JAVA_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.devCompanyDisclaimer.length"));

        if (this.additionalInformationLen > AimsConstants.JAVA_USER_GUIDE_FIELDS_LENGTH)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.java.app.additionalInformation.length"));

    }

    public void saveTempFiles(HttpServletRequest request, String sessionId, String currUserId, ActionErrors errors)
    {
        if ( log.isDebugEnabled() )
            log.debug("Java.saveTempFiles Start:");
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

        if ( log.isDebugEnabled() )
        {
            log.debug("Image File Size: " + imageFileSize);
            log.debug("Doc File Size: " + docFileSize);
            log.debug("File Size: "+fileSize);
        }


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
        if ( log.isDebugEnabled() )
            log.debug("JavaApplicaitonUpdater.saveTempFiles End:");
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

    public java.util.Collection getAllJavaContentRatings() {
        return allJavaContentRatings;
    }

    public void setAllJavaContentRatings(java.util.Collection allJavaContentRatings) {
        this.allJavaContentRatings = allJavaContentRatings;
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

    public String getInfoURL() {
        return infoURL;
    }

    public void setInfoURL(String infoURL) {
        this.infoURL = infoURL;
    }

    public String getAppKeyword() {
        return appKeyword;
    }

    public void setAppKeyword(String appKeyword) {
        this.appKeyword = appKeyword;
    }

    public java.util.Collection getAllJavaContracts() {
        return allJavaContracts;
    }

    public void setAllJavaContracts(java.util.Collection allJavaContracts) {
        this.allJavaContracts = allJavaContracts;
    }

    public java.lang.Long getJavaAppContractId() {
        return javaAppContractId;
    }

    public void setJavaAppContractId(java.lang.Long javaAppContractId) {
        this.javaAppContractId = javaAppContractId;
    }

    public String getOriginalTask() {
        return originalTask;
    }

    public void setOriginalTask(String originalTask) {
        this.originalTask = originalTask;
    }

    public java.lang.Long getRingId() {
        return ringId;
    }

    public void setRingId(java.lang.Long ringId) {
        this.ringId = ringId;
    }

    public java.lang.String getAllianceName() {
        return allianceName;
    }

    public void setAllianceName(java.lang.String allianceName) {
        this.allianceName = allianceName;
    }

    public String getClrPubLogoFileName() {
        return clrPubLogoFileName;
    }

    public void setClrPubLogoFileName(String clrPubLogoFileName) {
        this.clrPubLogoFileName = clrPubLogoFileName;
    }

    public String getContentZipFileFileName() {
        return contentZipFileFileName;
    }

    public void setContentZipFileFileName(String contentZipFileFileName) {
        this.contentZipFileFileName = contentZipFileFileName;
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

    public String getContentZipFileTempFileId() {
        return contentZipFileTempFileId;
    }

    public void setContentZipFileTempFileId(String contentZipFileTempFileId) {
        this.contentZipFileTempFileId = contentZipFileTempFileId;
    }

    public Long getTitleImageTempFileId() {
        return titleImageTempFileId;
    }

    public void setTitleImageTempFileId(Long titleImageTempFileId) {
        this.titleImageTempFileId = titleImageTempFileId;
    }

    public String getIsNewContentZipFileUploaded() {
        return isNewContentZipFileUploaded;
    }

    public void setIsNewContentZipFileUploaded(String isNewContentZipFileUploaded) {
        this.isNewContentZipFileUploaded = isNewContentZipFileUploaded;
    }

    private String isContentZipFileLock;

    public String getIsContentZipFileLock() {
        return isContentZipFileLock;
    }

    public void setIsContentZipFileLock(String isContentZipFileLock) {
        this.isContentZipFileLock = isContentZipFileLock;
    }

    public FormFile getClrPubLogo() {
        return clrPubLogo;
    }

    public void setClrPubLogo(FormFile clrPubLogo) {
        this.clrPubLogo = clrPubLogo;
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

    public FormFile getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(FormFile titleImage) {
        this.titleImage = titleImage;
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

    public String getEnterpriseApp() {
        return enterpriseApp;
    }

    public void setEnterpriseApp(String enterpriseApp) {
        this.enterpriseApp = enterpriseApp;
    }

    public java.lang.String getDevCompanyDisclaimer() {
        return devCompanyDisclaimer;
    }

    public void setDevCompanyDisclaimer(java.lang.String devCompanyDisclaimer) {
        this.devCompanyDisclaimer = SafeHTML.makeSafe(devCompanyDisclaimer);
    }

    public String getInitialApprovalAction() {
        return initialApprovalAction;
    }

    public void setInitialApprovalAction(String initialApprovalAction) {
        this.initialApprovalAction = initialApprovalAction;
    }

    public String getContentZipFileNotes() {
        return contentZipFileNotes;
    }

    public void setContentZipFileNotes(String contentZipFileNotes) {
        this.contentZipFileNotes = contentZipFileNotes;
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

        public Long getRingNumber() {
        return ringNumber;
    }

    public void setRingNumber(Long ringNumber) {
        this.ringNumber = ringNumber;
    }

    public Boolean getRing2App() {
        return ring2App;
    }

    public void setRing2App(Boolean ring2App) {
        this.ring2App = ring2App;
    }

    public Boolean getRing3App() {
        return ring3App;
    }

    public void setRing3App(Boolean ring3App) {
        this.ring3App = ring3App;
    }

    public java.lang.Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(java.lang.Long vendorId) {
        this.vendorId = vendorId;
    }

    public java.util.Collection getAllContentTypes() {
        return allContentTypes;
    }

    public void setAllContentTypes(java.util.Collection allContentTypes) {
        this.allContentTypes = allContentTypes;
    }

    public java.lang.Long getContentType() {
        return contentType;
    }

    public void setContentType(java.lang.Long contentType) {
        this.contentType = contentType;
    }

    public java.lang.String getProjectedLiveDate() {
        return projectedLiveDate;
    }

    public void setProjectedLiveDate(java.lang.String projectedLiveDate) {
        this.projectedLiveDate = projectedLiveDate;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public java.lang.Long getAppCategory1() {
        return appCategory1;
    }

    public void setAppCategory1(java.lang.Long appCategory1) {
        this.appCategory1 = appCategory1;
    }

    public java.lang.Long getAppCategory2() {
        return appCategory2;
    }

    public void setAppCategory2(java.lang.Long appCategory2) {
        this.appCategory2 = appCategory2;
    }

    public java.lang.Long getAppSubCategory1() {
        return appSubCategory1;
    }

    public void setAppSubCategory1(java.lang.Long appSubCategory1) {
        this.appSubCategory1 = appSubCategory1;
    }

    public java.lang.Long getAppSubCategory2() {
        return appSubCategory2;
    }

    public void setAppSubCategory2(java.lang.Long appSubCategory2) {
        this.appSubCategory2 = appSubCategory2;
    }

    public java.lang.String getComments() {
        return comments;
    }

    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }

    public java.util.Collection getAllTaxCategoryCodes() {
        return allTaxCategoryCodes;
    }

    public void setAllTaxCategoryCodes(java.util.Collection allTaxCategoryCodes) {
        this.allTaxCategoryCodes = allTaxCategoryCodes;
    }

    public java.lang.Long getAimsTaxCategoryCodeId() {
        return aimsTaxCategoryCodeId;
    }

    public void setAimsTaxCategoryCodeId(java.lang.Long aimsTaxCategoryCodeId) {
        this.aimsTaxCategoryCodeId = aimsTaxCategoryCodeId;
    }

    public JavaApplicationUpdateForm() {
        this.setIfPrRelease(AimsConstants.NO_CHAR);
    }

    public List getWorkItemsList() {
        return workItemsList;
    }

    public void setWorkItemsList(List workItemsList) {
        this.workItemsList = workItemsList;
    }

    public java.lang.String getActionComments() {
        return actionComments;
    }

    public void setActionComments(java.lang.String actionComments) {
        this.actionComments = actionComments;
    }

    public java.lang.Long getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(java.lang.Long selectedAction) {
        this.selectedAction = selectedAction;
    }

    public java.lang.Long getStepToValidate() {
        return stepToValidate;
    }

    public void setStepToValidate(java.lang.Long stepToValidate) {
        this.stepToValidate = stepToValidate;
    }

    public Vector getWiActions() {
        return wiActions;
    }

    public void setWiActions(Vector wiActions) {
        this.wiActions = wiActions;
    }

    public String getWiStartDate() {
        return wiStartDate;
    }

    public void setWiStartDate(String wiStartDate) {
        this.wiStartDate = wiStartDate;
    }

    public String getWiTitle() {
        return wiTitle;
    }

    public void setWiTitle(String wiTitle) {
        this.wiTitle = wiTitle;
    }

    public Long getWiWorkitemId() {
        return wiWorkitemId;
    }

    public void setWiWorkitemId(Long wiWorkitemId) {
        this.wiWorkitemId = wiWorkitemId;
    }

    public Boolean getShowExecute() {
        return showExecute;
    }

    public void setShowExecute(Boolean showExecute) {
        this.showExecute = showExecute;
    }

    public Boolean getLockContentRating() {
        return lockContentRating;
    }

    public void setLockContentRating(Boolean lockContentRating) {
        this.lockContentRating = lockContentRating;
    }

	public java.lang.Long getCloneFromAppId() {
		return cloneFromAppId;
	}

	public void setCloneFromAppId(java.lang.Long cloneFromAppId) {
		this.cloneFromAppId = cloneFromAppId;
	}    
}