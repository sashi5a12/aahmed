package com.netpace.aims.controller.application;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.apache.struts.Globals;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.application.AimsAppLite;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.util.Utility;

/**
 * @struts.form name="WapApplicationUpdateForm"
 */
public class WapApplicationUpdateForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(WapApplicationUpdateForm.class.getName());

    private java.lang.String tempForRadioIssue; //This property has been added only as a workaround for an issue with Special Characters and Radio Buttons
    private java.lang.String task;
    private java.lang.String appSubmitType;
    private java.lang.String currentPage;
    private java.lang.String orignalTask;

    //Tab Header
    private java.lang.String applicationStatus;
    private java.lang.String allianceName;
    private java.lang.Long vendorId;

    private java.lang.Long appsId;
    private java.lang.Long appOwnerAllianceId;
    private java.lang.String title;
    private java.lang.String version;
    private java.lang.String shortDesc;
    private java.lang.String longDesc;
    private java.lang.String ifPrRelease;
    private java.lang.String submittedDate;
    private java.lang.Long aimsUserAppCreatedById;
    private java.lang.Long aimsLifecyclePhaseId;
    private java.util.Collection allCategories;
    private java.util.Collection allSubCategories;

    private java.lang.String networkUsage;
    private java.lang.String[] applicationURLs;
    
    private java.lang.Long aimsContactId;
    private java.util.Collection allContacts;
    private java.lang.String contactFirstName;
    private java.lang.String contactLastName;
    private java.lang.String contactTitle;
    private java.lang.String contactEmail;
    private java.lang.String contactPhone;
    private java.lang.String contactMobile;

    private String launchDate;
    private java.lang.String longProductName;
    private java.lang.String descContentOffering;
    private java.lang.String contentType;
    private java.lang.String demoUrl;
    private java.lang.String testUrl;
    private java.lang.String productionUrl;
    private java.lang.String websiteUrl;
    private java.lang.Long categoryId1;
    private java.lang.Long categoryId2;
    private java.lang.Long categoryId3;
    private java.lang.Long subCategoryId1;
    private java.lang.Long subCategoryId2;
    private java.lang.Long subCategoryId3;
    private java.lang.String linkOrder1;
    private java.lang.String linkOrder2;
    private java.lang.String linkOrder3;
    private java.lang.String testEffectiveDate;
    private java.lang.String vendorProductCode;
    private java.lang.String vendorProductDisplay;
    private java.lang.String vzwRetailPrice;
    private java.lang.String pendingDcrNotes;
    private java.lang.String vendorSplitPercentage;
    private java.lang.String pendingDcrVersion;
    private java.lang.String wapVersion;

    private java.lang.String initialApprovalAction;
    private java.lang.String initialApprovalNotes;
    private java.lang.String businessApprovalAction;
    private java.lang.String businessApprovalNotes;
    private java.lang.String moveToPendingDcr;
    private java.lang.String moveToCompletedInProduction;
    private java.lang.String moveToSunset;

    private java.util.Collection allLicenseTypes;
    private String[] listSelectedLicenseTypes;

    private java.util.Collection allTaxCategoryCodes;
    private java.lang.Long taxCategoryCodeId;
    private java.lang.String vzwLiveDate;
    private java.lang.String pageViewRate;
    private java.lang.String rolledBackToPendingDcr;

    private Double[] wapAppVersions;

    //Files
    private FormFile productLogo;
    private FormFile productIcon;
    private FormFile userGuide;
    private FormFile screenJpeg;
    private FormFile faqDoc;
    private FormFile presentation;
    private FormFile appMediumLargeImage;//app image
    private FormFile appQVGAPotraitImage;//app image
    private FormFile appQVGALandscapeImage;//app image

    private java.lang.String productLogoFileName;
    private java.lang.String productIconFileName;
    private java.lang.String presentationFileName;
    private java.lang.String screenJpegFileName;
    private java.lang.String userGuideFileName;
    private java.lang.String faqDocFileName;
    private java.lang.String appMediumLargeImageFileName;
    private java.lang.String appQVGAPotraitImageFileName;
    private java.lang.String appQVGALandscapeImageFileName;


    private java.lang.Long productLogoTempFileId;
    private java.lang.Long productIconTempFileId;
    private java.lang.Long userGuideTempFileId;
    private java.lang.Long screenJpegTempFileId;
    private java.lang.Long faqDocTempFileId;
    private java.lang.Long presentationTempFileId;
    private java.lang.Long appMediumLargeImageTempFileId;
    private java.lang.Long appQVGAPotraitImageTempFileId;
    private java.lang.Long appQVGALandscapeImageTempFileId;


    //VZW   Application Management Parameters
    private java.lang.String[] testId;
    private java.lang.String[] testName;
    private java.lang.String[] testedDate;
    private java.lang.String[] testComments;
    private java.lang.String[] testStatus;
    private java.lang.String[] testResultFileId;
    private java.lang.String[] testResultFileName;
    private java.lang.String[] testUpdatedBy;
    private java.lang.String[] testUpdatedDate;

    private HashMap testDateSubmit;
    private HashMap testStatusSubmit;
    private HashMap testCommentsSubmit;
    private HashMap testResultFileSubmit;

    //Journal
    private java.lang.String journalType;
    private java.lang.String journalText;
    private java.lang.String journalCombinedText;

    //Cloning Related
    private java.lang.Long clonedFromId;
    private java.lang.String clonedFromTitle;

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        try
        {
            this.allCategories = AimsApplicationsManager.getCategoriesByPlatform(AimsConstants.WAP_PLATFORM_ID);
            this.allSubCategories = AimsApplicationsManager.getSubCategoriesByPlatform(AimsConstants.WAP_PLATFORM_ID);
            this.allLicenseTypes = WapApplicationManager.getLicenseTypes();
            this.allTaxCategoryCodes = WapApplicationManager.getTaxCategoryCodes();

            testDateSubmit = new HashMap();
            testStatusSubmit = new HashMap();
            testCommentsSubmit = new HashMap();
            testResultFileSubmit = new HashMap();

            request.setAttribute("WapApplicationUpdateForm", this);
        }
        catch (Exception ex)
        {
            log.debug("Exception in RESET: " + ex);
        }
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
        //bundle to to get values of filesize of wap image icons
        MessageResources defaultBundle =(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
        String appMediumLargeImageExt = "";
        String appQVGAPotraitImageExt = "";
        String appQVGALandscapeImageExt = "";
        String fileName = "";

        //This populating of contacts has been moved here from the Reset method.
        //The reason for this is that we need to know the Owner(Alliance) of Application, at time of populating.
        try
        {
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
                this.allContacts = AimsApplicationsManager.getContacts(currentUserAllianceId);
            else
                this.allContacts = AimsApplicationsManager.getContacts(this.appOwnerAllianceId);
        }
        catch (Exception ex)
        {}

        //Save Test Phases Information
        if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            if (ApplicationHelper.checkAccess(request, this.orignalTask, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_TESTING))
                saveCollections(currUserId, sessionId, fileSize, errors);

        //Save Files to AIMS_TEMP_FILES table
        saveTempWapFiles(currUserId, sessionId, fileSize, defaultBundle, errors);

        //Format Fields
        if (!this.isBlankString(this.vzwRetailPrice))
            if (this.isDecimal(this.vzwRetailPrice))
                this.vzwRetailPrice = (new BigDecimal(vzwRetailPrice).setScale(2, BigDecimal.ROUND_DOWN)).toString();

        if (!this.isBlankString(this.vendorSplitPercentage))
            if (this.isDecimal(this.vendorSplitPercentage))
                this.vendorSplitPercentage = (new BigDecimal(vendorSplitPercentage).setScale(2, BigDecimal.ROUND_DOWN)).toString();

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)))
        {

            //Check to see if data is compatible with DB fields
            validateToDBFields(currUserType, errors, request);

            if ((this.contentType != null)
                && (this.contentType.equals(AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0]))
                && (this.wapVersion != null)
                && (this.wapVersion.equals(AimsConstants.WAP_APP_VERSION_WAP_1_0[0])))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.standard.for.wap1"));

            if (this.isBlankString(this.title))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.shortProductName.required"));

            if (this.isBlankString(this.longProductName))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.longProductName.required"));

            if (this.isBlankString(this.shortDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appShortDesc.required"));

            if (this.isBlankString(this.longDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appLongDesc.required"));

            //Network usage validation
                if(!(this.isBlankString(this.networkUsage)) && this.networkUsage.equalsIgnoreCase(AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE))
                {
                    //application urls validation, if network usage is enabled then user must enter atleast one url
                    if(this.applicationURLs==null || this.applicationURLs.length==0)
                    {
                       errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.network.url.required"));                     
                    }
                    /************** Skip URL patterm validation
                        else
                        {
                            //URL Validation for valid URL pattern
                            for(int i=0; i<this.applicationURLs.length; i++)
                            {
                                if (!(this.isBlankString(this.applicationURLs[i])))
                                    if (!(this.isValidURL(this.applicationURLs[i])))
                                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.network.url.invalid"));
                            }
                        }
                    ***************/
                }
            //End Network usage validation

            //Email Validation
            if (!this.isDropDownSelected(this.aimsContactId))
            {
                if (!this.isBlankString(this.contactEmail))
                    if (!this.isValidEmail(this.contactEmail))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactEmail.email", this.contactEmail));
            }
            
            //Only For Alliance Users
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {

                //URL Validation
                if (!(this.isBlankString(this.demoUrl)))
                    if (!(this.isValidURL(this.demoUrl)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.demoUrl.invalid"));

                /* ****** no need to check testURL, as test url and demo url are same now
                    if (!(this.isBlankString(this.testUrl)))
                        if (!(this.isValidURL(this.testUrl)))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.testUrl.invalid"));
                */

                if (!(this.isBlankString(this.productionUrl)))
                    if (!(this.isValidURL(this.productionUrl)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productionUrl.invalid"));

                if (!(this.isBlankString(this.websiteUrl)))
                    if (!(this.isValidURL(this.websiteUrl)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.websiteUrl.invalid"));

                //Start of File Type Validations

                if (!(this.isBlankString(this.productLogoFileName)))
                    if (!(this.isValidFileType(this.productLogoFileName, AimsConstants.FILE_TYPE_IMAGE)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productLogo.file.type"));

                if (!(this.isBlankString(this.productIconFileName)))
                    if (!(this.isValidFileType(this.productIconFileName, AimsConstants.FILE_TYPE_IMAGE)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productIcon.file.type"));

                if (!(this.isBlankString(this.userGuideFileName)))
                    if (!(this.isValidFileType(this.userGuideFileName, AimsConstants.FILE_TYPE_DOC)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appUserGuide.file.type"));

                if (!(this.isBlankString(this.faqDocFileName)))
                    if (!(this.isValidFileType(this.faqDocFileName, AimsConstants.FILE_TYPE_DOC)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appFAQ.file.type"));

                if (!(this.isBlankString(this.screenJpegFileName)))
                    if (!(this.isValidFileType(this.screenJpegFileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.screenShot.file.type"));

                if (!(this.isBlankString(this.presentationFileName)))
                    if (!(this.isValidFileType(this.presentationFileName, AimsConstants.FILE_TYPE_PRESENTATION)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.presentation.file.type"));

                //medium image
                if (!(this.isBlankString(this.appMediumLargeImageFileName)))
                    if (!(this.isValidFileType(this.appMediumLargeImageFileName, AimsConstants.FILE_TYPE_WAP_FTP_IMAGES)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appMediumLargeImage.file.type"));
                //potrait
                if (!(this.isBlankString(this.appQVGAPotraitImageFileName)))
                    if (!(this.isValidFileType(this.appQVGAPotraitImageFileName, AimsConstants.FILE_TYPE_WAP_FTP_IMAGES)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGAPotraitImage.file.type"));
                //landscape
                if (!(this.isBlankString(this.appQVGALandscapeImageFileName)))
                    if (!(this.isValidFileType(this.appQVGALandscapeImageFileName, AimsConstants.FILE_TYPE_WAP_FTP_IMAGES)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGALandscapeImage.file.type"));

                //End of File Type Validations

                // Extension check, all images must have same file type
                    appMediumLargeImageExt = this.getFileExtension(this.appMediumLargeImage, this.appMediumLargeImageFileName);
                    appQVGAPotraitImageExt = this.getFileExtension(this.appQVGAPotraitImage, this.appQVGAPotraitImageFileName);
                    appQVGALandscapeImageExt = this.getFileExtension(this.appQVGALandscapeImage, this.appQVGALandscapeImageFileName);
                    if( (!appMediumLargeImageExt.equalsIgnoreCase(appQVGAPotraitImageExt))
                            || (!appMediumLargeImageExt.equalsIgnoreCase(appQVGALandscapeImageExt)))
                    {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.icons.same.fileType"));
                    }
                // end Extension check
            }
            
            //Start WAP underscore validation
            Date validationDateForWAPUnderScore=(Date)session.getAttribute(AimsConstants.VALIDATION_DATE_FOR_WAP_UNDERSCORE);
            Date appSubmitDate= Utility.convertToDate(this.submittedDate, "MM/dd/yyyy");

            if (appSubmitDate != null && appSubmitDate.before(validationDateForWAPUnderScore) 
            		&& this.aimsLifecyclePhaseId.longValue() != AimsConstants.SUBMISSION_ID.longValue())
            {            
            	//NO VALIDATION since application was submitted before validation date and is not SUBMITTED State.
            }
            else
            {
                if (!isBlankString(this.version) && this.version.indexOf('_') >=0){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.validate.underscore", "Product Version"));
                }
                if (!isBlankString(this.vendorProductCode) && this.vendorProductCode.indexOf('_') >= 0){
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.validate.underscore", "Vendor Product Code"));
                }
            }         
            //End underscore validation
        }

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)))
        {

            if (this.isBlankString(this.version))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.version.required"));

            if ((this.wapVersion != null) && (this.wapVersion.equals(AimsConstants.WAP_APP_VERSION_WAP_2_0[0])))
            {
                if (this.isBlankString(this.vendorProductCode))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.vendorProductCode.required"));
            }

            if ((this.contentType != null) && (this.contentType.equals(AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0])))
            {
                if ((this.listSelectedLicenseTypes == null) || (!(this.listSelectedLicenseTypes.length > 0)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.licenseType.required"));
                if (this.isBlankString(this.vzwRetailPrice))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.vzwRetailPrice.required"));
                if (this.isBlankString(this.vendorProductDisplay))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.vendorProductDisplay.required"));
            }

            //The following properties will be validated for an can only be updated by an Alliance user and not the Verizon user
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                if (this.isBlankString(this.contentType))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.contentType.required"));

                if (this.isBlankString(this.demoUrl))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.demoUrl.required"));

                //production url required on application submission
                if (this.isBlankString(this.productionUrl))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productionUrl.required"));

                if ((this.ifPrRelease == null) || (!(this.ifPrRelease.equals("Y"))))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.prRelease.accept"));

                if (this.isBlankString(this.launchDate))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.launchDate.required"));

                //Start   of Files (NULL) Validation
                if (((this.productLogo == null) || !(this.productLogo.getFileSize() > 0)) && (this.isBlankString(this.productLogoFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productLogo.required"));

                //medium
                if (((this.appMediumLargeImage == null) || !(this.appMediumLargeImage.getFileSize() > 0)) && (this.isBlankString(this.appMediumLargeImageFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appMediumLargeImage.required"));
                //potrait
                if (((this.appQVGAPotraitImage == null) || !(this.appQVGAPotraitImage.getFileSize() > 0)) && (this.isBlankString(this.appQVGAPotraitImageFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGAPotraitImage.required"));
                //landscape
                if (((this.appQVGALandscapeImage == null) || !(this.appQVGALandscapeImage.getFileSize() > 0)) && (this.isBlankString(this.appQVGALandscapeImageFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGALandscapeImage.required"));
                //End of Files (NULL) Validation
            }
            
            //Technical Contact
            if(!isDropDownSelected(this.aimsContactId)) {
                if( this.isBlankString(this.contactFirstName) || this.isBlankString(this.contactLastName)
                    || this.isBlankString(this.contactEmail) || this.isBlankString(this.contactTitle)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appTechContact.required"));
                }
            }//end techical contact

            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                if (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))
                {
                    if (ApplicationHelper.checkAccess(request, this.orignalTask, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_APPROVAL))
                        //&& (aimsLifecyclePhaseId.longValue() == AimsConstants.SUBMISSION_ID.longValue()))
                    {
                        if ((!this.isDropDownSelected(this.subCategoryId1))
                            && (!this.isDropDownSelected(this.subCategoryId2))
                            && (!this.isDropDownSelected(this.subCategoryId3)))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.subCategory.required"));
                        else if (
                            ((this.isDropDownSelected(this.subCategoryId1)) && (this.isBlankString(this.linkOrder1)))
                                || ((this.isDropDownSelected(this.subCategoryId2)) && (this.isBlankString(this.linkOrder2)))
                                || ((this.isDropDownSelected(this.subCategoryId3)) && (this.isBlankString(this.linkOrder3))))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.linkOrder.required"));
                    }

                    if ((ApplicationHelper.checkAccess(request, this.orignalTask, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_MOVE_TO_PENDING_DCR))
                        && ((aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID.longValue())
                            || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PENDING_ARM_ID.longValue())
                            || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PENDING_DCR_ID.longValue()))
                        && (moveToPendingDcr != null))
                    {

                        /* ****** no need to check testURL required, as test url and demo url are same now
                          if (this.isBlankString(this.testUrl))
                              errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.testUrl.required"));
                          else if (!(this.isValidURL(this.testUrl)))
                              errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.testUrl.invalid"));
                        */

                        //demo url is used instead of test url
                        if (this.isBlankString(this.demoUrl))
                              errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.demoUrl.required"));
                          else if (!(this.isValidURL(this.demoUrl)))
                              errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.demoUrl.invalid"));

                        if (this.isBlankString(this.productionUrl))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productionUrl.required"));
                        else if (!(this.isValidURL(this.productionUrl)))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productionUrl.invalid"));

                        //production and demo url check can be removed or moved out of this 'if' moveToPendingDcr check,
                        //as these 2 fields are required on application submission, and required for both vzw and alliance user 

                        if ((this.contentType != null) && (this.contentType.equals(AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0])))
                        {
                            if (this.isBlankString(this.vendorSplitPercentage))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.vendorSplitPercentage.required"));
                        }

                    }

                    if ((ApplicationHelper.checkAccess(request, this.orignalTask, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_PENDING_DCR))
                        && (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PENDING_DCR_ID.longValue()))
                    {
                        if ((this.contentType != null) && (this.contentType.equals(AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0])))
                        {
                            if (!this.isDropDownSelected(this.taxCategoryCodeId))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.taxCategoryCode.required"));
                        }

                        //check whether 3 images medium, potrait and landscape are present or not
                        if ( StringFuncs.isNullOrEmpty(this.appMediumLargeImageFileName) )
                        {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appMediumLargeImage.required"));
                        }
                        if ( StringFuncs.isNullOrEmpty(this.appQVGAPotraitImageFileName) )
                        {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGAPotraitImage.required"));
                        }
                        if ( StringFuncs.isNullOrEmpty(this.appQVGALandscapeImageFileName) )
                        {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGALandscapeImage.required"));
                        }

                        // Extension check, all images must have same file type
                            appMediumLargeImageExt = this.getFileExtension(this.appMediumLargeImage, this.appMediumLargeImageFileName);
                            appQVGAPotraitImageExt = this.getFileExtension(this.appQVGAPotraitImage, this.appQVGAPotraitImageFileName);
                            appQVGALandscapeImageExt = this.getFileExtension(this.appQVGALandscapeImage, this.appQVGALandscapeImageFileName);
                            if( (!appMediumLargeImageExt.equalsIgnoreCase(appQVGAPotraitImageExt))
                                    || (!appMediumLargeImageExt.equalsIgnoreCase(appQVGALandscapeImageExt)))
                            {
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.icons.same.fileType"));
                            }
                        // end Extension check

                    }
                }
            }
        }

        return errors;
    }

    public void validateToDBFields(String currUserType, ActionErrors errors, HttpServletRequest request)
    {
        if ((this.title != null) && (this.title.length() > 13))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.shortProductName.length"));

        if ((this.version != null) && (this.version.length() > 10))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.version.length"));

        if ((this.longProductName != null) && (this.longProductName.length() > 20))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.longProductName.length"));

        if ((this.vendorProductCode != null) && (this.vendorProductCode.length() > 20))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.vendorProductCode.length"));

        if ((this.shortDesc != null) && (this.shortDesc.length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appShortDesc.length"));

        if ((this.longDesc != null) && (this.longDesc.length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appLongDesc.length"));

        if ((this.descContentOffering != null) && (this.descContentOffering.length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.descContentOffering.length"));

        if ((this.demoUrl != null) && (this.demoUrl.length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.demoUrl.length"));

        /* ****** no need to check testURL, as test url and demo url are same now
            if ((this.testUrl != null) && (this.testUrl.length() > 200))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.testUrl.length"));
        */

        if ((this.productionUrl != null) && (this.productionUrl.length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productionUrl.length"));

        if ((this.websiteUrl != null) && (this.websiteUrl.length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.websiteUrl.length"));

        if ((this.contactFirstName != null) && (this.contactFirstName.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.contactFirstName.length"));

        if ((this.contactLastName != null) && (this.contactLastName.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.contactLastName.length"));

        if ((this.contactTitle != null) && (this.contactTitle.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.contactTitle.length"));

        if ((this.contactEmail != null) && (this.contactEmail.length() > 100))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.contactEmail.length"));

        if ((this.contactPhone != null) && (this.contactPhone.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.contactPhone.length"));

        if ((this.contactMobile != null) && (this.contactMobile.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.contactMobile.length"));

        if (!this.isBlankString(this.launchDate))
            if (!this.isDate(this.launchDate))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.launchDate.date", this.launchDate));

        if (!this.isBlankString(this.testEffectiveDate))
            if (!this.isDate(this.testEffectiveDate))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.testEffectiveDate.date", this.testEffectiveDate));

        if (!this.isBlankString(this.vzwRetailPrice))
            if (!this.isDecimalWithinRange(this.vzwRetailPrice, 0, 9999999.99))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.vzwRetailPrice.number"));

        if ((this.vendorProductDisplay != null) && (this.vendorProductDisplay.length() > 30))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.vendorProductDisplay.length"));

        if (currUserType.equals(AimsConstants.VZW_USERTYPE))
        {
            if (((!this.isBlankString(this.linkOrder1)) && (!isPositiveNumber(this.linkOrder1)))
                || ((!this.isBlankString(this.linkOrder2)) && (!isPositiveNumber(this.linkOrder2)))
                || ((!this.isBlankString(this.linkOrder3)) && (!isPositiveNumber(this.linkOrder3))))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.linkOrder.number"));

            if ((this.initialApprovalNotes != null) && (this.initialApprovalNotes.length() > 500))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.initialApprovalNotes.length"));

            if ((this.businessApprovalNotes != null) && (this.businessApprovalNotes.length() > 500))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.businessApprovalNotes.length"));

            if ((this.pendingDcrNotes != null) && (this.pendingDcrNotes.length() > 500))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.pendingDcrNotes.length"));

            if (!this.isBlankString(this.vendorSplitPercentage))
                if (!this.isDecimalWithinRange(this.vendorSplitPercentage, 0, 100))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.vendorSplitPercentage.number"));

            if (!this.isBlankString(this.vzwLiveDate))
                if (!this.isDate(this.vzwLiveDate))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.vzwLiveDate.date", this.vzwLiveDate));

            if ((this.pageViewRate != null) && (this.pageViewRate.length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.pageViewRate.length"));

            if (ApplicationHelper.checkAccess(request, this.orignalTask, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_TESTING))
            {
                for (int index = 0; index < testId.length; index++)
                {
                    if ((!this.isBlankString(this.testedDate[index])) && (!this.isDate(this.testedDate[index])))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.tested.date", this.testedDate[index]));

                    if ((this.testComments[index] != null) && (this.testComments[index].length() > 200))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.testComments.length"));
                }
            }
        }

    }

    private String getFileExtension(FormFile file, String fileName)
    {
        String ext = "";
        if(file!=null && !StringFuncs.isNullOrEmpty(file.getFileName()))
        {
            fileName = file.getFileName();
            if(fileName.lastIndexOf(".") != -1){
            	ext = fileName.substring(fileName.lastIndexOf("."));
            }
        }
        if(!StringFuncs.isNullOrEmpty(fileName))
        {
        	if(fileName.lastIndexOf(".") != -1){
        		ext = fileName.substring(fileName.lastIndexOf("."));
        	}
        }
        return ext;
    }


    public void saveCollections(String currUserId, String sessionId, String fileSize, ActionErrors errors)
    {
        for (int index = 0; index < testId.length; index++)
        {
            if (testDateSubmit.containsKey((new Integer(index)).toString()))
                this.testedDate[index] = (String) testDateSubmit.get((new Integer(index)).toString());

            if (testStatusSubmit.containsKey((new Integer(index)).toString()))
                this.testStatus[index] = (String) testStatusSubmit.get((new Integer(index)).toString());

            if (testCommentsSubmit.containsKey((new Integer(index)).toString()))
                this.testComments[index] = (String) testCommentsSubmit.get((new Integer(index)).toString());

            //Saving Files            
            if (testResultFileSubmit.containsKey((new Integer(index)).toString()))
            {
                FormFile uploadedFile = (FormFile) testResultFileSubmit.get((new Integer(index)).toString());
                TempFile tempFile = null;
                try
                {
                    if ((uploadedFile.getFileSize() > 0) && !(this.isValidFileSize(fileSize, uploadedFile.getFileSize())))
                    {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.testFile.fileSize.exceeded"));
                    }
                    else
                    {
                        tempFile = TempFilesManager.saveTempFile(uploadedFile, sessionId, currUserId);
                        if (tempFile != null)
                        {
                            this.testResultFileName[index] = tempFile.getFileName();
                            this.testResultFileId[index] = tempFile.getFileId().toString();
                        }
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    log.debug("Error while saving temp phase result file IN saveTempFiles() of WapApplicationUpdateForm");
                }
                finally
                {
                    log.debug("Finally called WHILE saving temp phase file of WapApplicationUpdateForm");
                }
            }
        }
    }

    public void saveTempWapFiles(String currUserId, String sessionId, String fileSize, MessageResources defaultBundle, ActionErrors errors)
    {
        TempFile tempFile = null;
        String mediumImageFileSize = defaultBundle.getMessage("file.wap.icon.medium.sizelimit");
        String potraitImageFileSize = defaultBundle.getMessage("file.wap.icon.potrait.sizelimit");
        String landscapeImageFileSize = defaultBundle.getMessage("file.wap.icon.landscape.sizelimit");

        try
        {
            if ((this.productLogo != null) && (this.productLogo.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.productLogo.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productLogo.fileSize.exceeded"));
                this.productLogo.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.productLogo, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.productLogoTempFileId = tempFile.getFileId();
                    this.productLogoFileName = tempFile.getFileName();
                }
            }

            if ((this.productIcon != null) && (this.productIcon.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.productIcon.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.productIcon.fileSize.exceeded"));
                this.productIcon.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.productIcon, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.productIconTempFileId = tempFile.getFileId();
                    this.productIconFileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg != null) && (this.screenJpeg.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.screenShot.fileSize.exceeded"));
                this.screenJpeg.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpegTempFileId = tempFile.getFileId();
                    this.screenJpegFileName = tempFile.getFileName();
                }
            }

            if (((this.userGuide != null) && (this.userGuide.getFileSize() > 0)) && !(this.isValidFileSize(fileSize, this.userGuide.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appUserGuide.fileSize.exceeded"));
                this.userGuide.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.userGuide, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.userGuideTempFileId = tempFile.getFileId();
                    this.userGuideFileName = tempFile.getFileName();
                }
            }

            if ((this.faqDoc != null) && (this.faqDoc.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.faqDoc.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appFAO.fileSize.exceeded"));
                this.faqDoc.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.faqDoc, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.faqDocTempFileId = tempFile.getFileId();
                    this.faqDocFileName = tempFile.getFileName();
                }
            }

            if ((this.presentation != null) && (this.presentation.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.presentation.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.presentation.fileSize.exceeded"));
                this.presentation.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.presentation, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.presentationTempFileId = tempFile.getFileId();
                    this.presentationFileName = tempFile.getFileName();
                }
            }

            //3 app images work
            //medium image
            if ((this.appMediumLargeImage != null) && (this.appMediumLargeImage.getFileSize() > 0) && !(this.isValidFileSize(mediumImageFileSize, this.appMediumLargeImage.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appMediumLargeImage.fileSize.exceeded"));
                this.appMediumLargeImage.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appMediumLargeImage, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appMediumLargeImageTempFileId = tempFile.getFileId();
                    this.appMediumLargeImageFileName = tempFile.getFileName();
                }
            }
            // potrait
            if ((this.appQVGAPotraitImage != null) && (this.appQVGAPotraitImage.getFileSize() > 0) && !(this.isValidFileSize(potraitImageFileSize, this.appQVGAPotraitImage.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGAPotraitImage.fileSize.exceeded"));
                this.appQVGAPotraitImage.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appQVGAPotraitImage, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appQVGAPotraitImageTempFileId = tempFile.getFileId();
                    this.appQVGAPotraitImageFileName = tempFile.getFileName();
                }
            }
            //landscape
            if ((this.appQVGALandscapeImage != null) && (this.appQVGALandscapeImage.getFileSize() > 0) && !(this.isValidFileSize(landscapeImageFileSize, this.appQVGALandscapeImage.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGALandscapeImage.fileSize.exceeded"));
                this.appQVGALandscapeImage.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.appQVGALandscapeImage, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.appQVGALandscapeImageTempFileId = tempFile.getFileId();
                    this.appQVGALandscapeImageFileName = tempFile.getFileName();
                }
            }
            //end 3 app images work

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempWapFiles() of WapApplicationUpdateForm");
        }
        finally
        {
            log.debug("Finally called IN saveTempWapFiles() of WapApplicationUpdateForm");
        }

    }

    public String getLaunchDate()
    {
        return launchDate;
    }
    public void setLaunchDate(String launchDate)
    {
        this.launchDate = launchDate;
    }

    public java.lang.Long getAimsContactId()
    {
        return aimsContactId;
    }

    public java.lang.Long getAimsLifecyclePhaseId()
    {
        return aimsLifecyclePhaseId;
    }

    public java.lang.Long getAimsUserAppCreatedById()
    {
        return aimsUserAppCreatedById;
    }

    public java.util.Collection getAllCategories()
    {
        return allCategories;
    }

    public java.util.Collection getAllContacts()
    {
        return allContacts;
    }

    public java.util.Collection getAllSubCategories()
    {
        return allSubCategories;
    }

    public java.lang.Long getAppsId()
    {
        return appsId;
    }

    public java.lang.String getAppSubmitType()
    {
        return appSubmitType;
    }

    public java.lang.String getContactEmail()
    {
        return contactEmail;
    }

    public java.lang.String getContactFirstName()
    {
        return contactFirstName;
    }

    public java.lang.String getContactLastName()
    {
        return contactLastName;
    }

    public java.lang.String getContactMobile()
    {
        return contactMobile;
    }

    public java.lang.String getContactPhone()
    {
        return contactPhone;
    }

    public java.lang.String getContactTitle()
    {
        return contactTitle;
    }

    public java.lang.String getCurrentPage()
    {
        return currentPage;
    }

    public FormFile getFaqDoc()
    {
        return faqDoc;
    }

    public java.lang.String getFaqDocFileName()
    {
        return faqDocFileName;
    }

    public java.lang.Long getFaqDocTempFileId()
    {
        return faqDocTempFileId;
    }

    public java.lang.String getIfPrRelease()
    {
        return ifPrRelease;
    }

    public java.lang.String getJournalCombinedText()
    {
        return journalCombinedText;
    }

    public java.lang.String getJournalText()
    {
        return journalText;
    }

    public java.lang.String getJournalType()
    {
        return journalType;
    }

    public java.lang.String getLongDesc()
    {
        return longDesc;
    }

    public FormFile getScreenJpeg()
    {
        return screenJpeg;
    }

    public java.lang.String getScreenJpegFileName()
    {
        return screenJpegFileName;
    }

    public java.lang.Long getScreenJpegTempFileId()
    {
        return screenJpegTempFileId;
    }

    public java.lang.String getShortDesc()
    {
        return shortDesc;
    }

    public java.lang.String getSubmittedDate()
    {
        return submittedDate;
    }

    public java.lang.String getTask()
    {
        return task;
    }

    public java.lang.String getTempForRadioIssue()
    {
        return tempForRadioIssue;
    }

    public java.lang.String[] getTestComments2()
    {
        return testComments;
    }

    public java.lang.String[] getTestedDate2()
    {
        return testedDate;
    }

    public java.lang.String[] getTestResultFileId()
    {
        return testResultFileId;
    }

    public java.lang.String[] getTestResultFileName()
    {
        return testResultFileName;
    }

    public java.lang.String[] getTestStatus2()
    {
        return testStatus;
    }

    public java.lang.String getTitle()
    {
        return title;
    }

    public FormFile getUserGuide()
    {
        return userGuide;
    }

    public java.lang.String getUserGuideFileName()
    {
        return userGuideFileName;
    }

    public java.lang.Long getUserGuideTempFileId()
    {
        return userGuideTempFileId;
    }

    public java.lang.String getVersion()
    {
        return version;
    }

    public void setAimsContactId(java.lang.Long long1)
    {
        aimsContactId = long1;
    }

    public void setAimsLifecyclePhaseId(java.lang.Long long1)
    {
        aimsLifecyclePhaseId = long1;
    }

    public void setAimsUserAppCreatedById(java.lang.Long long1)
    {
        aimsUserAppCreatedById = long1;
    }

    public void setAllCategories(java.util.Collection collection)
    {
        allCategories = collection;
    }

    public void setAllContacts(java.util.Collection collection)
    {
        allContacts = collection;
    }

    public void setAllSubCategories(java.util.Collection collection)
    {
        allSubCategories = collection;
    }

    public void setAppsId(java.lang.Long long1)
    {
        appsId = long1;
    }

    public void setAppSubmitType(java.lang.String string)
    {
        appSubmitType = string;
    }
    public void setContactEmail(java.lang.String string)
    {
        contactEmail = string;
    }
    public void setContactFirstName(java.lang.String string)
    {
        contactFirstName = string;
    }

    public void setContactLastName(java.lang.String string)
    {
        contactLastName = string;
    }

    public void setContactMobile(java.lang.String string)
    {
        contactMobile = string;
    }

    public void setContactPhone(java.lang.String string)
    {
        contactPhone = string;
    }

    public void setContactTitle(java.lang.String string)
    {
        contactTitle = string;
    }

    public void setCurrentPage(java.lang.String string)
    {
        currentPage = string;
    }

    public void setFaqDoc(FormFile file)
    {
        faqDoc = file;
    }

    public void setFaqDocFileName(java.lang.String string)
    {
        faqDocFileName = string;
    }
    public void setFaqDocTempFileId(java.lang.Long long1)
    {
        faqDocTempFileId = long1;
    }

    public void setIfPrRelease(java.lang.String string)
    {
        ifPrRelease = string;
    }
    public void setJournalCombinedText(java.lang.String string)
    {
        journalCombinedText = string;
    }

    public void setJournalText(java.lang.String string)
    {
        journalText = string;
    }

    public void setJournalType(java.lang.String string)
    {
        journalType = string;
    }

    public void setLongDesc(java.lang.String string)
    {
        longDesc = string;
    }

    public void setScreenJpeg(FormFile file)
    {
        screenJpeg = file;
    }

    public void setScreenJpegFileName(java.lang.String string)
    {
        screenJpegFileName = string;
    }

    public void setScreenJpegTempFileId(java.lang.Long long1)
    {
        screenJpegTempFileId = long1;
    }

    public void setShortDesc(java.lang.String string)
    {
        shortDesc = string;
    }

    public void setSubmittedDate(java.lang.String string)
    {
        submittedDate = string;
    }

    public void setTask(java.lang.String string)
    {
        task = string;
    }

    public void setTempForRadioIssue(java.lang.String string)
    {
        tempForRadioIssue = string;
    }

    public void setTestComments2(java.lang.String[] strings)
    {
        testComments = strings;
    }

    public void setTestedDate2(java.lang.String[] strings)
    {
        testedDate = strings;
    }

    public void setTestResultFileId(java.lang.String[] strings)
    {
        testResultFileId = strings;
    }

    public void setTestResultFileName(java.lang.String[] strings)
    {
        testResultFileName = strings;
    }

    public void setTestStatus2(java.lang.String[] strings)
    {
        testStatus = strings;
    }

    public void setTitle(java.lang.String string)
    {
        title = string;
    }

    public void setUserGuide(FormFile file)
    {
        userGuide = file;
    }

    public void setUserGuideFileName(java.lang.String string)
    {
        userGuideFileName = string;
    }

    public void setUserGuideTempFileId(java.lang.Long long1)
    {
        userGuideTempFileId = long1;
    }

    public void setVersion(java.lang.String string)
    {
        version = string;
    }

    public java.lang.String getContentType()
    {
        return contentType;
    }

    public java.lang.String getDemoUrl()
    {
        return demoUrl;
    }

    public java.lang.String getDescContentOffering()
    {
        return descContentOffering;
    }

    public java.lang.String getLinkOrder1()
    {
        return linkOrder1;
    }

    public java.lang.String getLinkOrder2()
    {
        return linkOrder2;
    }

    public java.lang.String getLinkOrder3()
    {
        return linkOrder3;
    }

    public java.lang.String getLongProductName()
    {
        return longProductName;
    }

    public java.lang.String getPresentationFileName()
    {
        return presentationFileName;
    }

    public java.lang.String getProductIconFileName()
    {
        return productIconFileName;
    }

    public java.lang.String getProductionUrl()
    {
        return productionUrl;
    }

    public java.lang.String getProductLogoFileName()
    {
        return productLogoFileName;
    }

    public java.lang.Long getSubCategoryId2()
    {
        return subCategoryId2;
    }

    public java.lang.Long getSubCategoryId3()
    {
        return subCategoryId3;
    }

    public java.lang.String getTestUrl()
    {
        return testUrl;
    }

    public java.lang.String getVendorProductCode()
    {
        return vendorProductCode;
    }

    public java.lang.String getVendorProductDisplay()
    {
        return vendorProductDisplay;
    }

    public java.lang.String getVzwRetailPrice()
    {
        return vzwRetailPrice;
    }

    public java.lang.String getWebsiteUrl()
    {
        return websiteUrl;
    }

    public void setContentType(java.lang.String string)
    {
        contentType = string;
    }

    public void setDemoUrl(java.lang.String string)
    {
        demoUrl = string;
    }

    public void setDescContentOffering(java.lang.String string)
    {
        descContentOffering = string;
    }

    public void setLinkOrder1(java.lang.String string)
    {
        linkOrder1 = string;
    }

    public void setLinkOrder2(java.lang.String string)
    {
        linkOrder2 = string;
    }

    public void setLinkOrder3(java.lang.String string)
    {
        linkOrder3 = string;
    }

    public void setLongProductName(java.lang.String string)
    {
        longProductName = string;
    }

    public void setPresentationFileName(java.lang.String string)
    {
        presentationFileName = string;
    }

    public void setProductIconFileName(java.lang.String string)
    {
        productIconFileName = string;
    }

    public void setProductionUrl(java.lang.String string)
    {
        productionUrl = string;
    }

    public void setProductLogoFileName(java.lang.String string)
    {
        productLogoFileName = string;
    }

    public void setSubCategoryId2(java.lang.Long long1)
    {
        subCategoryId2 = long1;
    }

    public void setSubCategoryId3(java.lang.Long long1)
    {
        subCategoryId3 = long1;
    }

    public void setTestUrl(java.lang.String string)
    {
        testUrl = string;
    }

    public void setVendorProductCode(java.lang.String string)
    {
        vendorProductCode = string;
    }

    public void setVendorProductDisplay(java.lang.String string)
    {
        vendorProductDisplay = string;
    }

    public void setVzwRetailPrice(java.lang.String string)
    {
        vzwRetailPrice = string;
    }

    public void setWebsiteUrl(java.lang.String string)
    {
        websiteUrl = string;
    }    

    public FormFile getPresentation()
    {
        return presentation;
    }

    public FormFile getProductIcon()
    {
        return productIcon;
    }

    public FormFile getProductLogo()
    {
        return productLogo;
    }

    public void setPresentation(FormFile file)
    {
        presentation = file;
    }

    public void setProductIcon(FormFile file)
    {
        productIcon = file;
    }

    public void setProductLogo(FormFile file)
    {
        productLogo = file;
    }

    public java.lang.Long getPresentationTempFileId()
    {
        return presentationTempFileId;
    }

    public java.lang.Long getProductIconTempFileId()
    {
        return productIconTempFileId;
    }

    public java.lang.Long getProductLogoTempFileId()
    {
        return productLogoTempFileId;
    }

    public void setPresentationTempFileId(java.lang.Long long1)
    {
        presentationTempFileId = long1;
    }

    public void setProductIconTempFileId(java.lang.Long long1)
    {
        productIconTempFileId = long1;
    }

    public void setProductLogoTempFileId(java.lang.Long long1)
    {
        productLogoTempFileId = long1;
    }

    public java.lang.String getTestEffectiveDate()
    {
        return testEffectiveDate;
    }

    public void setTestEffectiveDate(java.lang.String string)
    {
        testEffectiveDate = string;
    }

    public java.lang.String getAllianceName()
    {
        return allianceName;
    }

    public java.lang.String getApplicationStatus()
    {
        return applicationStatus;
    }

    public void setAllianceName(java.lang.String string)
    {
        allianceName = string;
    }

    public void setApplicationStatus(java.lang.String string)
    {
        applicationStatus = string;
    }

    public java.util.Collection getAllLicenseTypes()
    {
        return allLicenseTypes;
    }

    public String[] getListSelectedLicenseTypes()
    {
        return listSelectedLicenseTypes;
    }

    public void setAllLicenseTypes(java.util.Collection collection)
    {
        allLicenseTypes = collection;
    }

    public void setListSelectedLicenseTypes(String[] strings)
    {
        listSelectedLicenseTypes = strings;
    }

    public java.lang.Long getSubCategoryId1()
    {
        return subCategoryId1;
    }

    public void setSubCategoryId1(java.lang.Long long1)
    {
        subCategoryId1 = long1;
    }

    public java.lang.Long getCategoryId1()
    {
        return categoryId1;
    }

    public java.lang.Long getCategoryId2()
    {
        return categoryId2;
    }

    public java.lang.Long getCategoryId3()
    {
        return categoryId3;
    }

    public void setCategoryId1(java.lang.Long long1)
    {
        categoryId1 = long1;
    }

    public void setCategoryId2(java.lang.Long long1)
    {
        categoryId2 = long1;
    }

    public void setCategoryId3(java.lang.Long long1)
    {
        categoryId3 = long1;
    }

    public java.lang.String getBusinessApprovalAction()
    {
        return businessApprovalAction;
    }

    public java.lang.String getBusinessApprovalNotes()
    {
        return businessApprovalNotes;
    }

    public java.lang.String getInitialApprovalAction()
    {
        return initialApprovalAction;
    }

    public java.lang.String getInitialApprovalNotes()
    {
        return initialApprovalNotes;
    }

    public java.lang.String getMoveToPendingDcr()
    {
        return moveToPendingDcr;
    }

    public void setBusinessApprovalAction(java.lang.String string)
    {
        businessApprovalAction = string;
    }

    public void setBusinessApprovalNotes(java.lang.String string)
    {
        businessApprovalNotes = string;
    }

    public void setInitialApprovalAction(java.lang.String string)
    {
        initialApprovalAction = string;
    }

    public void setInitialApprovalNotes(java.lang.String string)
    {
        initialApprovalNotes = string;
    }

    public void setMoveToPendingDcr(java.lang.String string)
    {
        moveToPendingDcr = string;
    }

    public java.lang.String getOrignalTask()
    {
        return orignalTask;
    }

    public void setOrignalTask(java.lang.String string)
    {
        orignalTask = string;
    }

    public java.lang.String getPendingDcrNotes()
    {
        return pendingDcrNotes;
    }

    public java.lang.String getPendingDcrVersion()
    {
        return pendingDcrVersion;
    }

    public java.lang.String getVendorSplitPercentage()
    {
        return vendorSplitPercentage;
    }

    public void setPendingDcrNotes(java.lang.String string)
    {
        pendingDcrNotes = string;
    }

    public void setPendingDcrVersion(java.lang.String string)
    {
        pendingDcrVersion = string;
    }

    public void setVendorSplitPercentage(java.lang.String string)
    {
        vendorSplitPercentage = string;
    }

    public java.lang.String getWapVersion()
    {
        return wapVersion;
    }

    public void setWapVersion(java.lang.String string)
    {
        wapVersion = string;
    }

    public Double[] getWapAppVersions()
    {
        return wapAppVersions;
    }

    public void setWapAppVersions(Double[] doubles)
    {
        wapAppVersions = doubles;
    }

    public java.lang.String[] getTestName()
    {
        return testName;
    }

    public void setTestName(java.lang.String[] strings)
    {
        testName = strings;
    }

    public java.lang.String[] getTestId()
    {
        return testId;
    }

    public void setTestId(java.lang.String[] strings)
    {
        testId = strings;
    }

    public void setTestedDate(int index, java.lang.String strings)
    {
        testDateSubmit.put((new Integer(index)).toString(), StringFuncs.NullValueReplacement(strings));
    }
    public String getTestedDate(int index)
    {
        return testedDate[index];
    }

    public void setTestStatus(int index, java.lang.String strings)
    {
        testStatusSubmit.put((new Integer(index)).toString(), StringFuncs.NullValueReplacement(strings));
    }
    public String getTestStatus(int index)
    {
        return testStatus[index];
    }

    public void setTestComments(int index, java.lang.String strings)
    {
        testCommentsSubmit.put((new Integer(index)).toString(), StringFuncs.NullValueReplacement(strings));
    }
    public String getTestComments(int index)
    {
        return testComments[index];
    }

    public FormFile getTestResultFile(int index)
    {
        return null;
    }

    public void setTestResultFile(int index, FormFile testResultFile)
    {
        if (testResultFile != null)
            testResultFileSubmit.put((new Integer(index)).toString(), testResultFile);
    }

    public java.lang.String getMoveToSunset()
    {
        return moveToSunset;
    }

    public void setMoveToSunset(java.lang.String string)
    {
        moveToSunset = string;
    }

    public java.lang.String getMoveToCompletedInProduction()
    {
        return moveToCompletedInProduction;
    }

    public void setMoveToCompletedInProduction(java.lang.String string)
    {
        moveToCompletedInProduction = string;
    }

    public java.lang.String[] getTestUpdatedBy()
    {
        return testUpdatedBy;
    }

    public java.lang.String[] getTestUpdatedDate()
    {
        return testUpdatedDate;
    }

    public void setTestUpdatedBy(java.lang.String[] strings)
    {
        testUpdatedBy = strings;
    }

    public void setTestUpdatedDate(java.lang.String[] strings)
    {
        testUpdatedDate = strings;
    }

    public java.lang.Long getClonedFromId()
    {
        return clonedFromId;
    }

    public java.lang.String getClonedFromTitle()
    {
        return clonedFromTitle;
    }

    public void setClonedFromId(java.lang.Long long1)
    {
        clonedFromId = long1;
    }

    public void setClonedFromTitle(java.lang.String string)
    {
        clonedFromTitle = string;
    }

    public java.lang.Long getVendorId()
    {
        return vendorId;
    }

    public void setVendorId(java.lang.Long long1)
    {
        vendorId = long1;
    }

    public java.lang.Long getAppOwnerAllianceId()
    {
        return appOwnerAllianceId;
    }

    public void setAppOwnerAllianceId(java.lang.Long long1)
    {
        appOwnerAllianceId = long1;
    }

    public java.util.Collection getAllTaxCategoryCodes()
    {
        return allTaxCategoryCodes;
    }

    public void setAllTaxCategoryCodes(java.util.Collection collection)
    {
        allTaxCategoryCodes = collection;
    }

    public java.lang.Long getTaxCategoryCodeId()
    {
        return taxCategoryCodeId;
    }

    public void setTaxCategoryCodeId(java.lang.Long long1)
    {
        taxCategoryCodeId = long1;
    }

    public java.lang.String getVzwLiveDate()
    {
        return vzwLiveDate;
    }

    public void setVzwLiveDate(java.lang.String string)
    {
        vzwLiveDate = string;
    }

    public java.lang.String getPageViewRate()
    {
        return pageViewRate;
    }

    public void setPageViewRate(java.lang.String string)
    {
        pageViewRate = string;
    }

    public java.lang.String getRolledBackToPendingDcr()
    {
        return rolledBackToPendingDcr;
    }

    public void setRolledBackToPendingDcr(java.lang.String string)
    {
        rolledBackToPendingDcr = string;
    }

    public FormFile getAppMediumLargeImage() {
        return appMediumLargeImage;
    }

    public void setAppMediumLargeImage(FormFile appMediumLargeImage) {
        this.appMediumLargeImage = appMediumLargeImage;
    }

    public FormFile getAppQVGAPotraitImage() {
        return appQVGAPotraitImage;
    }

    public void setAppQVGAPotraitImage(FormFile appQVGAPotraitImage) {
        this.appQVGAPotraitImage = appQVGAPotraitImage;
    }

    public FormFile getAppQVGALandscapeImage() {
        return appQVGALandscapeImage;
    }

    public void setAppQVGALandscapeImage(FormFile appQVGALandscapeImage) {
        this.appQVGALandscapeImage = appQVGALandscapeImage;
    }

    public String getAppMediumLargeImageFileName() {
        return appMediumLargeImageFileName;
    }

    public void setAppMediumLargeImageFileName(String appMediumLargeImageFileName) {
        this.appMediumLargeImageFileName = appMediumLargeImageFileName;
    }

    public String getAppQVGAPotraitImageFileName() {
        return appQVGAPotraitImageFileName;
    }

    public void setAppQVGAPotraitImageFileName(String appQVGAPotraitImageFileName) {
        this.appQVGAPotraitImageFileName = appQVGAPotraitImageFileName;
    }

    public String getAppQVGALandscapeImageFileName() {
        return appQVGALandscapeImageFileName;
    }

    public void setAppQVGALandscapeImageFileName(String appQVGALandscapeImageFileName) {
        this.appQVGALandscapeImageFileName = appQVGALandscapeImageFileName;
    }

    public Long getAppMediumLargeImageTempFileId() {
        return appMediumLargeImageTempFileId;
    }

    public void setAppMediumLargeImageTempFileId(Long appMediumLargeImageTempFileId) {
        this.appMediumLargeImageTempFileId = appMediumLargeImageTempFileId;
    }

    public Long getAppQVGAPotraitImageTempFileId() {
        return appQVGAPotraitImageTempFileId;
    }

    public void setAppQVGAPotraitImageTempFileId(Long appQVGAPotraitImageTempFileId) {
        this.appQVGAPotraitImageTempFileId = appQVGAPotraitImageTempFileId;
    }

    public Long getAppQVGALandscapeImageTempFileId() {
        return appQVGALandscapeImageTempFileId;
    }

    public void setAppQVGALandscapeImageTempFileId(Long appQVGALandscapeImageTempFileId) {
        this.appQVGALandscapeImageTempFileId = appQVGALandscapeImageTempFileId;
    }

    public String getNetworkUsage() {
        return networkUsage;
    }

    public void setNetworkUsage(String networkUsage) {
        this.networkUsage = networkUsage;
    }

    public String[] getApplicationURLs() {
        return applicationURLs;
    }

    public void setApplicationURLs(String[] applicationURLs) {
        this.applicationURLs = applicationURLs;
    }
}
