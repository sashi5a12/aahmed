package com.netpace.aims.controller.application;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.bo.core.AimsTypeManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.dataaccess.valueobjects.VZAppBaseTestVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppBinaryFirmwareInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppZoneOTATestVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppZoneProdInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppZoneStageInfoVO;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.masters.AimsTaxCategoryCode;
import com.netpace.aims.model.application.AimsVZAppBinaries;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsAppSubCategory;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsTypes;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.AimsPrivilegesConstants;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * @struts.form name="VZAppZoneApplicationUpdateForm"
*/

public class VZAppZoneApplicationUpdateForm extends BaseValidatorForm {
    private static Logger log = Logger.getLogger(VZAppZoneApplicationUpdateForm.class.getName());

    private java.lang.String tempForRadioIssue; //This property has been added only as a workaround for an issue with Special Characters and Radio Buttons

    private java.lang.Long appsId;
    private java.lang.Long appOwnerAllianceId;//used to load alliance contacts

    private java.lang.String appTitle;
    private java.lang.String appTitleToView;
    private java.lang.String appVersion;

    private java.lang.String appShortDesc;
    private java.lang.String appLongDesc;

    private java.lang.String appCatalogName;
    private java.lang.String appProductCode;

    private java.lang.String goLiveDate;
    private java.lang.String expirationDate;
    private java.lang.Long contentType;
    private java.util.Collection allContentTypes;

    private java.lang.Long contentRating;
    private java.util.Collection allContentRatings;

    //Application Billing fields
    private java.lang.String subscriptionBillingMonthly;
    private java.lang.String subscriptionBillingPricePoint;
    private java.lang.String oneTimeBilling;
    private java.lang.String oneTimeBillingPricePoint;

    //Application Classification
    private java.util.Collection allCategories;
    private java.util.Collection allSubCategories;
    private java.lang.Long categoryId1;
    private java.lang.Long subCategoryId1;
    private java.lang.Long categoryId2;
    private java.lang.Long subCategoryId2;
    private java.lang.Long categoryId3;
    private java.lang.Long subCategoryId3;

    //marketingRelease
    private java.lang.String ifPrRelease;

    private java.lang.Long aimsLifecyclePhaseId;

    private java.lang.String task;
    private java.lang.String orignalTask;
    private java.lang.String appSubmitType;
    private java.lang.String currentPage;

    //Tab Header
    private java.lang.String applicationStatus;
    private java.lang.String allianceName;
    private java.lang.Long vendorId;

    private java.lang.String mportalAllianceName;

    private FormFile presentation;
    private java.lang.String presentationFileName;
    private java.lang.Long presentationTempFileId;

    private FormFile contentLandingScreenShot;
    private java.lang.String contentLandingScreenShotFileName;
    private java.lang.Long contentLandingScreenShotTempFileId;

    //clone
    private java.lang.Long clonedFromId;
    private java.lang.String clonedFromTitle;

    //Contact Info Fields
    private java.lang.Long aimsContactId;
    private java.util.Collection allContacts;
    private java.lang.String contactFirstName;
    private java.lang.String contactLastName;
    private java.lang.String contactTitle;
    private java.lang.String contactEmail;
    private java.lang.String contactPhone;
    private java.lang.String contactMobile;

    //additional info
    private java.lang.String communityChatUgc;
    private java.lang.String contentSweekstakes;

    //premium content info
    private java.lang.String subsVendorProductDisplay;
    private java.lang.String subsVZWRecommendedPrice;
    private java.lang.String subsVendorSplitPercentage;
    private java.lang.String oneTimeVendorProductDisplay;
    private java.lang.String oneTimeVZWRecommendedPrice;
    private java.lang.String oneTimeVendorSplitPercentage;

    //processing info
    // initial approval
    private java.lang.String scmVendorId;
    private java.lang.String vzwLiveDate;
    private java.lang.String initialApprovalAction;
    private java.lang.String initialApprovalNotes;
    private java.util.Collection allTaxCategoryCodes;
    private java.lang.Long taxCategoryCodeId;

    //Application Management
    private java.lang.String contentId;
    private java.lang.String isLocked;
    private java.lang.String isAppLocked;

    //Journal
    private java.lang.String journalType;
    private java.lang.String journalText;
    private java.lang.String journalCombinedText;

    //error messages
    private java.lang.String[] errorMessages;

    //sunset
    private java.lang.String moveToSunset;

    //Binaries
    private java.util.List vzAppBinaryFirmwareTestStatusList;
    private java.util.List vzAppBinaryFirmwareInfoList;
    private java.util.List vzAppBinaries;
    private java.lang.Long[] vzAppDeviceIds;
    private java.lang.Long[] vzAppFirmwareIds;

    private java.util.List allVZAppDeviceFirmwareList;

    private FormFile binaryFile;
    private java.lang.String binaryFileFileName;
    private java.lang.Long binaryFileTempFileId;
    private java.lang.Long binaryId;
    private java.lang.String binaryVersion;
    private java.lang.String binaryFileSize;
    private FormFile previewFile;
    private java.lang.String previewFileFileName;
    private java.lang.Long previewFileTempFileId;
    private FormFile documentFile;
    private java.lang.String documentFileFileName;
    private java.lang.Long documentFileTempFileId;

    //base test
    private java.util.List VZAppBinaryFirmwareInfoVOs;

    //Testing Phase
    private java.util.List VZAppBaseTests;
    private java.util.Map baseTestResultsTempFileMap;

    //OTA Tests
    private java.util.List VZAppZoneOTATests;
    private java.util.Map otaTestResultsTempFileMap;

    //Stage Info
    private java.util.List VZAppZoneStageInfoVOs;
    //Prod Info
    private java.util.List VZAppZoneProdInfoVOs;


    public void reset(ActionMapping mapping, HttpServletRequest request) {

        log.debug("======= RESET called	in VZAppZoneApplicationUpdateForm ");
        /*String taskName = StringFuncs.NullValueReplacement(request.getParameter("task"));
        String submitType = StringFuncs.NullValueReplacement(request.getParameter("appSubmitType"));
        Long appsId = null;*/

        try {
            this.allCategories = AimsApplicationsManager.getCategoriesByPlatform(AimsConstants.VZAPPZONE_PLATFORM_ID);
            this.allSubCategories = AimsApplicationsManager.getSubCategoriesByPlatform(AimsConstants.VZAPPZONE_PLATFORM_ID);
            this.allContentTypes = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_VZAPPZONE_CONTENT_TYPE_ID);
            this.allContentRatings = AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_VZAPPZONE_CONTENT_RATING_ID);

            this.allTaxCategoryCodes = WapApplicationManager.getTaxCategoryCodes();

            this.baseTestResultsTempFileMap = new HashMap();
            this.otaTestResultsTempFileMap = new HashMap();
        }
        catch (HibernateException he) {
            he.printStackTrace();
        }//end catch
        log.debug("======= RESET ends in VZAppZoneApplicationUpdateForm ");
    } //end reset

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        log.debug("======= VZAppZoneApplicationUpdateForm.validate() starts");
        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();

        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String sessionId = session.getId();
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        //bundle to to get values of filesize of vzAppZone image icons
        MessageResources defaultBundle =(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);
        String dateFormat = defaultBundle.getMessage("date.format");

        //save tempFiles
        this.saveTempVZAppZoneFiles(currUserId, sessionId, fileSize, defaultBundle, errors);

        //This populating of contacts has been placed here instead of Reset method.
        //The reason for this is that we need to know the Owner(Alliance) of Application, at time of populating.
        try {
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
                this.allContacts = AimsApplicationsManager.getContacts(currentUserAllianceId);
            else
                this.allContacts = AimsApplicationsManager.getContacts(this.appOwnerAllianceId);
        }
        catch (Exception ex) {
            System.out.println("VZAppZoneApplicationUpdateForm.validate(): exception occured while getting all contacts");
            ex.printStackTrace();
        }

        if(this.appSubmitType!=null && !this.appSubmitType.equals("paging")) {
            //validation for save, submit form
            if(this.appSubmitType.equals(AimsConstants.AIMS_SAVE_FORM)
                    || this.appSubmitType.equals(AimsConstants.AIMS_SUBMIT_FORM)
                    || this.appSubmitType.equals(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT)
                    || this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM)
                    || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))) {

                boolean hasAccessInitialApproval = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_INITIAL_APPROVAL, AimsSecurityManager.UPDATE);

                //Check to see if data is compatible with DB fields
                validateToDBFields(currUserType, errors, request);

                //required fields for saving application
                if (this.isBlankString(this.appTitle))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.title.required"));

                if (this.isBlankString(this.appVersion))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.version.required"));

                if (this.isBlankString(this.appShortDesc))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appShortDesc.required"));

                if (this.isBlankString(this.appLongDesc))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appLongDesc.required"));
                //end required fields for saving application


                //Go Live Date < exp date
                if(!this.isBlankString(this.goLiveDate)) {
                    //if go live date is given, check it must be less than expiration date
                    if(!this.isBlankString(this.expirationDate) && this.isDate(this.goLiveDate) && this.isDate(this.expirationDate)) {
                        Date liveDate = Utility.convertToDate(this.goLiveDate, dateFormat);
                        Date expDate = Utility.convertToDate(this.expirationDate, dateFormat);
                        //if go live date is after or equals expiration date then show error
                        if(liveDate.after(expDate) || (liveDate.equals(expDate))) {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.expirationDate.greater"));
                        }
                    }
                }

                //Type or pattern validation
                if (!(this.isBlankString(this.contentLandingScreenShotFileName)))
                    if (!(this.isValidFileType(this.contentLandingScreenShotFileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.contentLandingScreenShot.file.type"));

                if (!(this.isBlankString(this.presentationFileName)))
                    if (!(this.isValidFileType(this.presentationFileName, AimsConstants.FILE_TYPE_PRESENTATION)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.presentation.file.type"));
                //Type or pattern validation ends

                //todo see old implementation, application can not be submitted, without giving a binary

                //------------------------------------------ validate binary file
                boolean binaryUploadedOrSelected = false;
                boolean noBinaryUploadErrors = true;
                boolean noVersionErrors = true;
                boolean oneBinaryAttachedForSubmit = false;
                String inputBinaryVersion = "";
                String inputBinaryFileFileName = "";
                try {
                    if((this.binaryFile!=null && this.binaryFile.getFileSize()>0)
                        || (!this.isBlankString(this.binaryFileFileName) && Utility.ZeroValueReplacement(this.binaryFileTempFileId).longValue()>0)) {

                        //binary file ext check
                        if (!(this.isBlankString(this.binaryFileFileName))) {
                            //only zip file will be allowed as binary file
                            if (!(this.isValidFileType(this.binaryFileFileName, AimsConstants.FILE_TYPE_ZIP))) {
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.binaryFile.file.type"));
                                noBinaryUploadErrors = false;
                            }
                        }

                        //preview ext check
                        if (!(this.isBlankString(this.previewFileFileName))) {
                            if (!(this.isValidFileType(this.previewFileFileName, AimsConstants.FILE_TYPE_PREVIEW_IMAGE))) {
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.previewFile.file.type"));
                                noBinaryUploadErrors = false;
                            }
                        }

                        //document ext check
                        if (!(this.isBlankString(this.documentFileFileName))) {
                            if (!(this.isValidFileType(this.documentFileFileName, AimsConstants.FILE_TYPE_ZIP))) {
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.documentFile.file.type"));
                                noBinaryUploadErrors = false;
                            }
                        }

                        //if binary file is given, version must be specified
                        if(this.isBlankString(this.binaryVersion)) {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.binaryVersion.required"));
                            noBinaryUploadErrors = false;
                            noVersionErrors = false;
                        }

                        //if document file is also required while uploading binary
                        if (((this.documentFile == null) || !(this.documentFile.getFileSize() > 0)) && (this.isBlankString(this.documentFileFileName))) {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.documentFile.file.required"));
                            noVersionErrors = false;
                        }

                        oneBinaryAttachedForSubmit = true;
                        binaryUploadedOrSelected = true;
                        inputBinaryVersion = this.binaryVersion;
                        inputBinaryFileFileName = this.binaryFileFileName;
                    }
                    else if(this.isDropDownSelected(this.binaryId) && !this.task.equals("removeBinary")) {
                        binaryUploadedOrSelected = true;
                        AimsVZAppBinaries vzAppBinary =
                            (AimsVZAppBinaries) DBHelper.getInstance().load(AimsVZAppBinaries.class, this.binaryId.toString());
                        if(vzAppBinary!=null) {
                            inputBinaryFileFileName = vzAppBinary.getBinaryFileFileName();
                            inputBinaryVersion = vzAppBinary.getBinaryVersion();
                            oneBinaryAttachedForSubmit = true;
                        }
                    }

                    //if binary file is given, version must be specified
                    if(!this.isBlankString(this.binaryVersion)) {
                        if(this.validateZeroVersion(this.binaryVersion)) {
                            System.out.println("--------- 0 found in version: "+this.binaryVersion);
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.binaryVersion.zero"));
                            noBinaryUploadErrors = false;
                            noVersionErrors = false;
                        }
                        else if(!VZAppZoneApplicationHelper.validateVersionPattern(this.binaryVersion)) {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.binaryVersion.pattern"));
                            noBinaryUploadErrors = false;
                            noVersionErrors = false;
                        }
                    }


                    if(binaryUploadedOrSelected) {
                        //else if binary validation OK, then validate firmwares
                        if(this.vzAppFirmwareIds!=null && this.vzAppFirmwareIds.length>0) {
                            //send ignoreProductionBinaryFirmwares parameter as false, to include all active binary firmwares
                            List vzAppBinaryFirmwareInfoList = VZAppZoneApplicationManager.getVZAppBinaryFirmwareInfoListByFirmwareIds(this.appsId,
                                                                                                        this.vzAppFirmwareIds,
                                                                                                        AimsConstants.ACTIVE_CHAR,
                                                                                                        "MR-", false, false);
                            VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
                            Long binaryFirmwareStatus = null;
                            if(vzAppBinaryFirmwareInfoList!=null && vzAppBinaryFirmwareInfoList.size()>0) {
                                for(int binaryFirmwareIndex=0; binaryFirmwareIndex<vzAppBinaryFirmwareInfoList.size(); binaryFirmwareIndex++) {
                                    binaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO)vzAppBinaryFirmwareInfoList.get(binaryFirmwareIndex);
                                    binaryFirmwareStatus = Utility.ZeroValueReplacement(binaryFirmwareInfo.getBinaryFirmwareStatus());
                                    if(binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_TEST_FAILED)
                                            || binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_OTA_TEST_FAILED)
                                            || binaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_PRODUCTION)) {
                                        //check version higher, same file name
                                        if(noVersionErrors && !VZAppZoneApplicationHelper.validateVersion(binaryFirmwareInfo.getBinaryVersion(), inputBinaryVersion)) {
                                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.binaryVersion.greater",
                                                    this.getDeviceNameFormatForErrorMessages(binaryFirmwareInfo.getPhoneModel(), binaryFirmwareInfo.getMrNumber()), binaryFirmwareInfo.getBinaryVersion()));
                                        }

                                        /**********
                                          //code commented to check same file name (in device upgrade module) no need to check same file name now
                                            if(!binaryFirmwareInfo.getBinaryFileFileName().equals(inputBinaryFileFileName)) {
                                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.binaryFile.match.name",
                                                        this.getDeviceNameFormatForErrorMessages(binaryFirmwareInfo.getPhoneModel(), binaryFirmwareInfo.getMrNumber()), binaryFirmwareInfo.getBinaryFileFileName()));
                                            }
                                        **********/

                                    }
                                    else {
                                        //if binary firmware phase is not failed/ota failed,
                                        //restrict user to upload binary for this firmware
                                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.firmware.duplicateBinary", this.getDeviceNameFormatForErrorMessages(binaryFirmwareInfo.getPhoneModel(), binaryFirmwareInfo.getMrNumber())));
                                    }
                                }
                            }
                        }//end if firmwareIds null
                        else {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.binary.firmware.required"));
                        }
                    }//end if binaryUploadedOrSelected
                    else if(this.vzAppFirmwareIds!=null && this.vzAppFirmwareIds.length>0 && noBinaryUploadErrors) {
                        //firmware ids selected without uploading/selecting binary file
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.firmware.noBinaryUploaded"));
                    }
                }
                catch (HibernateException he) {
                    he.printStackTrace();
                    log.error(he, he);
                }
                //------------------------------------------ end validate binary file

                //submit application required fields
                if(this.appSubmitType.equals(AimsConstants.AIMS_SUBMIT_FORM)
                    || this.appSubmitType.equals(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT)
                    || this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM)
                    || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)) ) {

                    /* commented, catalog name and product code, no need to show
                        if(this.isBlankString(this.appCatalogName))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.catalogName.required"));

                        if(this.isBlankString(this.appProductCode))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.productCode.required"));
                    */

                    //go live date os required on submission
                    if(this.isBlankString(this.goLiveDate)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.goLiveDate.required"));
                    }

                    if(!this.isDropDownSelected(this.contentRating)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.contentRating.required"));
                    }

                    if(!this.isBlankString(this.subscriptionBillingMonthly) && this.subscriptionBillingMonthly.equals("Y"))
                        if(this.isBlankString(this.subscriptionBillingPricePoint))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subscriptionBillingPricePoint.required"));

                    if(!this.isBlankString(this.oneTimeBilling) && this.oneTimeBilling.equals("Y"))
                        if(this.isBlankString(this.oneTimeBillingPricePoint))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.oneTimeBillingPricePoint.required"));

                    if ((this.communityChatUgc == null)
                            || ( !(this.communityChatUgc.equals("Y")) && !(this.communityChatUgc.equals("N")) )) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.communityChatUgc.required"));
                    }

                    if ((this.contentSweekstakes== null)
                            || ( !(this.contentSweekstakes.equals("Y")) && !(this.contentSweekstakes.equals("N")) )) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.contentSweekstakes.required"));
                    }

                    if(!this.isDropDownSelected(this.categoryId1))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appCategory.required"));

                    if(!this.isDropDownSelected(this.subCategoryId1))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appSubCategory.required"));

                    //check one binary attached
                     if(this.appSubmitType.equals(AimsConstants.AIMS_SUBMIT_FORM) && !oneBinaryAttachedForSubmit ) {
                         if(Utility.ZeroValueReplacement(this.appsId).longValue()>0 &&
                                 Utility.ZeroValueReplacement(this.aimsLifecyclePhaseId).equals(AimsConstants.SAVED_ID)) {
                            //if current status is saved, then check old binaries, if not found then flag error
                            try {
                                List activeBinaryFirmwares = VZAppZoneApplicationManager.getActiveAimsVZAppBinaryFirmwares(this.appsId);
                                if(activeBinaryFirmwares!=null && activeBinaryFirmwares.size()>0 ) {
                                    log.debug("No of Binaries found while submitting application: "+activeBinaryFirmwares.size());
                                }
                                else {
                                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.required"));
                                }
                             }
                             catch (HibernateException he) {
                                he.printStackTrace();
                                log.error(he, he);
                            }
                         }
                         else {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.required"));
                         }
                     }

                    //check intial approval required fields (after initial approval)
                    if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(this.aimsLifecyclePhaseId)) {
                        this.validateFieldsForInitialApproval(errors, hasAccessInitialApproval, currUserType);
                    }//end if equalOrAboveInitial approval

                    if ((this.ifPrRelease == null) || (!(this.ifPrRelease.equals("Y"))))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.prRelease.accept"));
                    //alliane user
                    if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)) {
                        //validation specific to alliance user
                    }
                    else if(currUserType.equals(AimsConstants.VZW_USERTYPE)) {
                        if(this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)) {
                            //last status submitted, and submit type is process, means processing initial approval, check initial approval required fields
                            if(this.aimsLifecyclePhaseId.equals(AimsConstants.SUBMISSION_ID)) {
                                this.validateFieldsForInitialApproval(errors, hasAccessInitialApproval, currUserType);
                            }
                        }//end processing validation

                    }//end vzw user validation
                }//end submit form, vzw save form, save after submit, vzw process
                //end submit application required fields

                this.validateDropDownValues(errors, hasAccessInitialApproval, currUserType);
            }
            this.setErrorMessages(null);
        }
        if(errors.size()>0) {
            this.setCurrentPage("page1");
        }
        log.debug("======= VZAppZoneApplicationUpdateForm.validate() ends. \terrors.size= "+errors.size());
        return errors;
    }//end validate

    public void validateToDBFields(String currUserType, ActionErrors errors, HttpServletRequest request) {
        if ((this.appTitle != null) && (this.appTitle.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.title.length"));

        if ((this.appVersion != null) && (this.appVersion.length() > 10))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.version.length"));

        if ((this.appCatalogName != null) && (this.appCatalogName.length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.catalogName.length"));

        if ((this.appProductCode != null) && (this.appProductCode.length() > 150))
           errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.productCode.length"));

        if ((this.appShortDesc != null) && (this.appShortDesc.length() > 200)) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appShortDesc.length"));
        }

        if ((this.appLongDesc != null) && (this.appLongDesc.length() > 500)) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appLongDesc.length"));
        }

        if (!this.isBlankString(this.goLiveDate))
            if (!this.isDate(this.goLiveDate))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.goLiveDate.date", this.goLiveDate));

        if (!this.isBlankString(this.expirationDate))
            if (!this.isDate(this.expirationDate))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.expirationDate.date", this.expirationDate));

        if (!this.isBlankString(this.subscriptionBillingPricePoint)) {
            if (!this.isDecimalWithinRange(this.subscriptionBillingPricePoint, 0, 9999999.99)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subscriptionBillingPricePoint.length"));
            }
            else if (this.isDecimalWithinRange(this.subscriptionBillingPricePoint, 0, 0.00)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subscriptionBillingPricePoint.length"));
            }
        }

        if (!this.isBlankString(this.oneTimeBillingPricePoint)) {
            if (!this.isDecimalWithinRange(this.oneTimeBillingPricePoint, 0, 9999999.99)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.oneTimeBillingPricePoint.length"));
            }
            else if (this.isDecimalWithinRange(this.oneTimeBillingPricePoint, 0, 0.00)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.oneTimeBillingPricePoint.zero"));
            }
        }

        if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {
            if (!this.isBlankString(this.subsVZWRecommendedPrice)) {
                if (!this.isDecimalWithinRange(this.subsVZWRecommendedPrice, 0, 9999999.99)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subsVZWRecommendedPrice.number"));
                }
                else if (this.isDecimalWithinRange(this.subsVZWRecommendedPrice, 0, 0.00)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subsVZWRecommendedPrice.zero"));
                }
            }

            if (!this.isBlankString(this.subsVendorSplitPercentage)) {
                if((this.subsVendorSplitPercentage.indexOf("+")!=-1) || (this.subsVendorSplitPercentage.indexOf("-")!=-1)
                        || (this.subsVendorSplitPercentage.indexOf("%")!=-1)
                        || (!this.isDecimalWithinRange(this.subsVendorSplitPercentage, 0, 100))) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subsVendorSplitPercentage.number"));
                }
                else if(this.isDecimalWithinRange(this.subsVendorSplitPercentage, 0, 0.00)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subsVendorSplitPercentage.zero"));
                }
            }

            if ((this.subsVendorProductDisplay != null) && (this.subsVendorProductDisplay.length() > 30))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subsVendorProductDisplay.length"));

            if (!this.isBlankString(this.oneTimeVZWRecommendedPrice)) {
                if (!this.isDecimalWithinRange(this.oneTimeVZWRecommendedPrice, 0, 9999999.99)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.oneTimeVZWRecommendedPrice.number"));
                }
                else if (this.isDecimalWithinRange(this.oneTimeVZWRecommendedPrice, 0, 0.00)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.oneTimeVZWRecommendedPrice.zero"));
                }
            }

            if (!this.isBlankString(this.oneTimeVendorSplitPercentage)) {
                if ( (this.oneTimeVendorSplitPercentage.indexOf("+")!=-1) || (this.oneTimeVendorSplitPercentage.indexOf("-")!=-1)
                        || (this.oneTimeVendorSplitPercentage.indexOf("%")!=-1)
                        || (!this.isDecimalWithinRange(this.oneTimeVendorSplitPercentage, 0, 100))) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.oneTimeVendorSplitPercentage.number"));
                }
                else if (this.isDecimalWithinRange(this.oneTimeVendorSplitPercentage, 0, 0.00)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.oneTimeVendorSplitPercentage.zero"));
                }
            }

            if ((this.oneTimeVendorProductDisplay != null) && (this.oneTimeVendorProductDisplay.length() > 30))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.oneTimeVendorProductDisplay.length"));


            if (!this.isBlankString(this.scmVendorId))
                if (!this.isPositiveNumber(this.scmVendorId))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.scmVendorId.number"));

            if ((this.initialApprovalNotes != null) && (this.initialApprovalNotes.length() > 500))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.initialApprovalNotes.length"));

            if (!this.isBlankString(this.vzwLiveDate))
                if (!this.isDate(this.vzwLiveDate))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.vzwLiveDate.date", this.vzwLiveDate));

            //check base test and ota test fields (after initial approval)
            if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(this.aimsLifecyclePhaseId)) {
                if( (this.VZAppBaseTests!= null && this.VZAppBaseTests.size()>0)
                        && (this.VZAppBinaryFirmwareInfoVOs!=null && this.VZAppBinaryFirmwareInfoVOs.size()==this.VZAppBaseTests.size())) {
                    VZAppBaseTestVO vzAppBaseTest = null;
                    VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;

                    for(int baseTestIndex = 0; baseTestIndex < this.VZAppBaseTests.size(); baseTestIndex++) {
                        vzAppBaseTest = (VZAppBaseTestVO) this.VZAppBaseTests.get(baseTestIndex);
                        binaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO)this.VZAppBinaryFirmwareInfoVOs.get(baseTestIndex);
                        //check Results file size not greater than 100MB
                        if(vzAppBaseTest!=null && Utility.ZeroValueReplacement(vzAppBaseTest.getBinaryFirmwareId()).longValue()>0) {//if device Phase exists
                            if (!this.isBlankString(vzAppBaseTest.getBaseTestedDate())) {
                                if (!this.isDate(vzAppBaseTest.getBaseTestedDate())) {
                                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.baseTest.testedDate.date",
                                                                                                vzAppBaseTest.getBaseTestedDate(),
                                                                                                this.getDeviceNameFormatForErrorMessages(binaryFirmwareInfo.getPhoneModel(), binaryFirmwareInfo.getMrNumber())));
                                }
                            }

                            if(!this.isBlankString(vzAppBaseTest.getBaseComments()) && vzAppBaseTest.getBaseComments().length()>4000) {
                                 errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.baseTest.comments.length",
                                         this.getDeviceNameFormatForErrorMessages(binaryFirmwareInfo.getPhoneModel(), binaryFirmwareInfo.getMrNumber())));
                            }
                        }
                    }
                }

                if(VZAppZoneApplicationHelper.isStatusEqualOrAboveTestPassed(this.aimsLifecyclePhaseId)) {
                    if( (this.VZAppZoneOTATests!= null && this.VZAppZoneOTATests.size()>0)
                        && (this.VZAppBinaryFirmwareInfoVOs!=null && this.VZAppBinaryFirmwareInfoVOs.size()==this.VZAppZoneOTATests.size())) {
                        VZAppZoneOTATestVO vzAppOTATest = null;
                        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;

                        for(int otaTestIndex = 0; otaTestIndex < this.VZAppZoneOTATests.size(); otaTestIndex++) {
                            binaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO)this.VZAppBinaryFirmwareInfoVOs.get(otaTestIndex);
                            vzAppOTATest = (VZAppZoneOTATestVO) this.VZAppZoneOTATests.get(otaTestIndex);
                            //check Results file size not greater than 100MB
                            if(vzAppOTATest !=null && Utility.ZeroValueReplacement(vzAppOTATest.getBinaryFirmwareId()).longValue()>0) {//if ota test exists
                                if (!this.isBlankString(vzAppOTATest.getOtaTestedDate())) {
                                    if (!this.isDate(vzAppOTATest.getOtaTestedDate())) {
                                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.otaTest.otaTestedDate.date",
                                                                                                    vzAppOTATest.getOtaTestedDate(),
                                                                                                    this.getDeviceNameFormatForErrorMessages(binaryFirmwareInfo.getPhoneModel(), binaryFirmwareInfo.getMrNumber())));
                                    }
                                }

                                if(!this.isBlankString(vzAppOTATest.getOtaComments()) && vzAppOTATest.getOtaComments().length()>4000) {
                                     errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.otaTest.comments.length",
                                             this.getDeviceNameFormatForErrorMessages(binaryFirmwareInfo.getPhoneModel(), binaryFirmwareInfo.getMrNumber())));
                                }
                            }
                        }
                    }
                }
            }//end testing section validation

            if ((this.contentId!= null) && (this.contentId.length() > 50))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.contentId.length"));
        }

    }//end validateToDBFields

    public void saveTempVZAppZoneFiles(String currUserId, String sessionId, String fileSize, MessageResources defaultBundle, ActionErrors errors) {
        TempFile tempFile = null;
        String maxPreviewFileSize = defaultBundle.getMessage("file.sizelimit.1MB");
        try {
            /* commented, no need to show  contentLandingScreenShot
                if ((this.contentLandingScreenShot != null) && (this.contentLandingScreenShot.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.contentLandingScreenShot.getFileSize()))) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.contentLandingScreenShot.fileSize.exceeded"));
                    this.contentLandingScreenShot.destroy();
                }
                else {
                    tempFile = TempFilesManager.saveTempFile(this.contentLandingScreenShot, sessionId, currUserId);
                    if (tempFile != null) {
                        this.contentLandingScreenShotTempFileId = tempFile.getFileId();
                        this.contentLandingScreenShotFileName= tempFile.getFileName();
                    }
                }
            */

            if ((this.presentation != null) && (this.presentation.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.presentation.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.presentation.fileSize.exceeded"));
                this.presentation.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.presentation, sessionId, currUserId);
                if (tempFile != null) {
                    this.presentationTempFileId = tempFile.getFileId();
                    this.presentationFileName = tempFile.getFileName();
                }
            }

            if ((this.binaryFile != null) && (this.binaryFile.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.binaryFile.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.binaryFile.fileSize.exceeded"));
                this.binaryFile.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.binaryFile, sessionId, currUserId);
                if (tempFile != null) {
                    this.binaryFileTempFileId = tempFile.getFileId();
                    this.binaryFileFileName = tempFile.getFileName();
                }
            }

            if ((this.previewFile != null) && (this.previewFile.getFileSize() > 0) && !(this.isValidFileSize(maxPreviewFileSize, this.previewFile.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.previewFile.fileSize.exceeded"));
                this.previewFile.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.previewFile, sessionId, currUserId);
                if (tempFile != null) {
                    this.previewFileTempFileId = tempFile.getFileId();
                    this.previewFileFileName = tempFile.getFileName();
                }
            }

            if ((this.documentFile != null) && (this.documentFile.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.documentFile.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.documentFile.fileSize.exceeded"));
                this.documentFile.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.documentFile, sessionId, currUserId);
                if (tempFile != null) {
                    this.documentFileTempFileId = tempFile.getFileId();
                    this.documentFileFileName = tempFile.getFileName();
                }
            }

            //device phase files
            this.saveVZAppBaseTestFiles(currUserId, sessionId, fileSize, defaultBundle, errors);
            this.saveVZAppOTATestFiles(currUserId, sessionId, fileSize, defaultBundle, errors);

        }//end try
        catch (Exception ex) {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempVZAppZoneFiles() of VZAppZoneApplicationUpdateForm");
        }
        finally {
            log.debug("Finally called IN saveTempVZAppZoneFiles() of VZAppZoneApplicationUpdateForm");
        }
    }//end saveTempVZAppZoneFiles

    private void saveVZAppBaseTestFiles(String currUserId, String sessionId, String fileSize, MessageResources defaultBundle, ActionErrors errors) {
        if((this.VZAppBaseTests!= null && this.VZAppBaseTests.size()>0)
                && (this.VZAppBinaryFirmwareInfoVOs!=null && this.VZAppBinaryFirmwareInfoVOs.size()==this.VZAppBaseTests.size())) {
            VZAppBaseTestVO vzAppBaseTest = null;
            VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
            String maxResultsFileSize = defaultBundle.getMessage("file.sizelimit");
            TempFile tempFile = null;
            try {
                for(int baseTestIndex = 0; baseTestIndex < this.VZAppBaseTests.size(); baseTestIndex++) {
                    vzAppBaseTest = (VZAppBaseTestVO) this.VZAppBaseTests.get(baseTestIndex);
                    binaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO)this.VZAppBinaryFirmwareInfoVOs.get(baseTestIndex);
                    //check Results file size not greater than 100MB
                    if(vzAppBaseTest!=null && Utility.ZeroValueReplacement(vzAppBaseTest.getBinaryFirmwareId()).longValue()>0) {//if device Phaseexists
                        if ((vzAppBaseTest.getBaseResultsFile() != null) && (vzAppBaseTest.getBaseResultsFile().getFileSize() > 0) && !(this.isValidFileSize(maxResultsFileSize, vzAppBaseTest.getBaseResultsFile().getFileSize()))) {
                            //errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.baseTest.resultsFile.fileSize.exceeded", vzAppBaseTest.getBaseResultsFile().getFileName()));
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.baseTest.resultsFile.fileSize.exceeded",
                                    this.getDeviceNameFormatForErrorMessages(binaryFirmwareInfo.getPhoneModel(), binaryFirmwareInfo.getMrNumber())));
                            vzAppBaseTest.getBaseResultsFile().destroy();
                        }
                        else {
                            //binary file
                            tempFile = TempFilesManager.saveTempFile(vzAppBaseTest.getBaseResultsFile(), sessionId, currUserId);
                            if (tempFile != null) {
                                vzAppBaseTest.setBaseResultsFileTempFileId(tempFile.getFileId());
                                vzAppBaseTest.setBaseResultsFileFileName(tempFile.getFileName());
                                vzAppBaseTest.setBaseResultsFileContentType(tempFile.getFileContentType());
                                tempFile = null;
                            }
                        }

                        //put temp file ids in map so that this will be used while copying temp files from temp table to binaryFirmware table while saving application
                        if(Utility.ZeroValueReplacement(vzAppBaseTest.getBaseResultsFileTempFileId()).longValue()>0) {
                            baseTestResultsTempFileMap.put(vzAppBaseTest.getBinaryFirmwareId()+"" , vzAppBaseTest.getBaseResultsFileTempFileId());
                        }
                    }
                }//end for deviceIndex
            }//end try
            catch (Exception ex) {
                log.error("Error while saving temp files IN saveVZAppBaseTestFiles() of VZAppZoneApplicationUpdateForm");
                ex.printStackTrace();
            }
            finally {
                log.debug("Finally called IN saveVZAppBaseTestFiles() of VZAppZoneApplicationUpdateForm");
            }
        }//end tests not null
    }//end saveVZAppBaseTestFiles

    private void saveVZAppOTATestFiles(String currUserId, String sessionId, String fileSize, MessageResources defaultBundle, ActionErrors errors) {
        if( (this.VZAppZoneOTATests!= null && this.VZAppZoneOTATests.size()>0
                && (this.VZAppBinaryFirmwareInfoVOs!=null && this.VZAppBinaryFirmwareInfoVOs.size()==this.VZAppZoneOTATests.size())) ) {
            VZAppZoneOTATestVO vzAppOTATest = null;
            VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
            String maxResultsFileSize = defaultBundle.getMessage("file.sizelimit");
            TempFile tempFile = null;
            try {
                for(int otaTestIndex = 0; otaTestIndex < this.VZAppZoneOTATests.size(); otaTestIndex++) {
                    vzAppOTATest = (VZAppZoneOTATestVO) this.VZAppZoneOTATests.get(otaTestIndex);
                    binaryFirmwareInfo = (VZAppBinaryFirmwareInfoVO)this.VZAppBinaryFirmwareInfoVOs.get(otaTestIndex);
                    //check Results file size not greater than 100MB
                    if(vzAppOTATest !=null && Utility.ZeroValueReplacement(vzAppOTATest.getBinaryFirmwareId()).longValue()>0) {//if device Phaseexists
                        if ((vzAppOTATest.getOtaResultsFile() != null) && (vzAppOTATest.getOtaResultsFile().getFileSize() > 0) && !(this.isValidFileSize(maxResultsFileSize, vzAppOTATest.getOtaResultsFile().getFileSize()))) {
                            //errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.otaTest.resultsFile.fileSize.exceeded", vzAppOTATest.getOtaResultsFile().getFileName()));
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.otaTest.resultsFile.fileSize.exceeded",
                                    this.getDeviceNameFormatForErrorMessages(binaryFirmwareInfo.getPhoneModel(), binaryFirmwareInfo.getMrNumber())));
                            vzAppOTATest.getOtaResultsFile().destroy();
                        }
                        else {
                            //binary file
                            tempFile = TempFilesManager.saveTempFile(vzAppOTATest.getOtaResultsFile(), sessionId, currUserId);
                            if (tempFile != null) {
                                vzAppOTATest.setOtaResultsFileTempFileId(tempFile.getFileId());
                                vzAppOTATest.setOtaResultsFileFileName(tempFile.getFileName());
                                vzAppOTATest.setOtaResultsFileContentType(tempFile.getFileContentType());
                                tempFile = null;
                            }
                        }

                        //put temp file ids in map so that this will be used while copying temp files from temp table to binaryFirmware table while saving application
                        if(Utility.ZeroValueReplacement(vzAppOTATest.getOtaResultsFileTempFileId()).longValue()>0) {
                            otaTestResultsTempFileMap.put(vzAppOTATest.getBinaryFirmwareId()+"" , vzAppOTATest.getOtaResultsFileTempFileId());
                        }
                    }
                }//end for deviceIndex
            }//end try
            catch (Exception ex) {
                log.error("Error while saving temp files IN saveVZAppOTATestFiles() of VZAppZoneApplicationUpdateForm");
                ex.printStackTrace();
            }
            finally {
                log.debug("Finally called IN saveVZAppOTATestFiles() of VZAppZoneApplicationUpdateForm");
            }
        }//end tests not null
    }//end saveVZAppOTATestFiles


    /**
     * Validate fields for initial approval / Under testing status
     * @param errors
     */
    private void validateDevicesForInitialApproval(ActionErrors errors) {
        //todo implement these rules, but these rules will not apply in phase3
        //binary file required for device
        //binary version can not be changed without uploading binaries
        //VZAppZoneDevices validation for initial approval
    }//end validateDevicesForInitialApproval

    private void validateFieldsForInitialApproval(ActionErrors errors, boolean hasAccessInitialApproval, String currUserType) {
        if(currUserType.equals(AimsConstants.VZW_USERTYPE)) {
            if(hasAccessInitialApproval){
                if(!this.isBlankString(this.subscriptionBillingMonthly) && this.subscriptionBillingMonthly.equals("Y"))
                    if(this.isBlankString(this.subsVZWRecommendedPrice))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subsVZWRecommendedPrice.required"));

                if(!this.isBlankString(this.oneTimeBilling) && this.oneTimeBilling.equals("Y"))
                    if(this.isBlankString(this.oneTimeVZWRecommendedPrice))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.oneTimeVZWRecommendedPrice.required"));

                /* commented, scmVendorId, no need to show
                    if(this.isBlankString(this.scmVendorId))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.scmVendorId.required"));
                */

                //tax category code reqd for initial approval
                if (!this.isDropDownSelected(this.taxCategoryCodeId))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzApp.app.taxCategoryCode.required"));
                //category and sub category required for initial approval
                //no need to validate category and sub category here, as it is already checked for vzappzone fields
                if(this.isBlankString(this.vzwLiveDate))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.vzwLiveDate.required"));

                if(!this.isDropDownSelected(this.contentType))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.contentType.required"));

                //if initial approval action is empty or not equal to A or D
                if(StringFuncs.isNullOrEmpty(this.initialApprovalAction) ||
                        (!this.initialApprovalAction.equals(AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_APPROVE[0])
                                && !this.initialApprovalAction.equals(AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_DENY[0]))) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.initialApprovalAction.required"));
                }

            }//end hasAccessInitialApproval
        }
        //validate devices
        this.validateDevicesForInitialApproval(errors);
    }//end validateFieldsForInitialApproval

    private void validateDropDownValues(ActionErrors errors, boolean hasAccessInitialApproval, String currUserType) {
        if(this.allContentRatings!=null && this.allContentRatings.size()>0) {
            if(this.isDropDownSelected(this.contentRating) && !this.validateContentRatingValues(this.contentRating)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.contentRating.value"));
            }
        }
        if(this.allCategories!=null && this.allCategories.size()>0) {
            if(this.isDropDownSelected(this.categoryId1) && !this.validateCategoryValues(this.categoryId1)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.category1.value"));
            }
        }
        if(this.allSubCategories!=null && this.allSubCategories.size()>0) {
            if(this.isDropDownSelected(this.categoryId1) && this.isDropDownSelected(this.subCategoryId1) &&
                    !this.validateSubCategoryValues(this.subCategoryId1, this.categoryId1)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subCategory1.value"));
            }
        }
        if(currUserType.equals(AimsConstants.VZW_USERTYPE)) {
            if(hasAccessInitialApproval) {
                if(this.allCategories!=null && this.allCategories.size()>0) {
                    if(this.isDropDownSelected(this.categoryId2) && !this.validateCategoryValues(this.categoryId2)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.category2.value"));
                    }

                    if(this.isDropDownSelected(this.categoryId3) && !this.validateCategoryValues(this.categoryId3)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.category3.value"));
                    }
                }

                if(this.allSubCategories!=null && this.allSubCategories.size()>0) {
                    if(this.isDropDownSelected(this.categoryId2) && this.isDropDownSelected(this.subCategoryId2) &&
                            !this.validateSubCategoryValues(this.subCategoryId2, this.categoryId2)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subCategory2.value"));
                    }
                    if(this.isDropDownSelected(this.categoryId3) && this.isDropDownSelected(this.subCategoryId3) &&
                            !this.validateSubCategoryValues(this.subCategoryId3, this.categoryId3)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.subCategory3.value"));
                    }
                }

                if(this.allContentTypes!=null && this.allContentTypes.size()>0) {
                    if(this.isDropDownSelected(this.contentType) && !this.validateContentTypeValues(this.contentType)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.contentType.value"));
                    }
                }

                if(this.allTaxCategoryCodes!=null && this.allTaxCategoryCodes.size()>0) {
                    if(this.isDropDownSelected(this.taxCategoryCodeId) && !this.validateTaxCategoryValues(this.taxCategoryCodeId)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.taxCategoryCode.value"));
                    }
                }
             }
         }

    }//end validateDropDownValues

    private boolean validateCategoryValues(Long categoryValue) {
        boolean categoryValidated = false;
        Iterator categoryItr = this.allCategories.iterator();
        while(categoryItr.hasNext()) {
            AimsAppCategory aimsCategory = (AimsAppCategory)categoryItr.next();
            if(aimsCategory.getCategoryId().equals(categoryValue)) {
                categoryValidated = true;
                break;
            }
        }
        return categoryValidated;
    }//end validateCategoryValues

    private boolean validateSubCategoryValues(Long subCategoryValue, Long categoryId) {
        boolean subCategoryValidated = false;
        Iterator subCategoryItr = this.allSubCategories.iterator();
        while(subCategoryItr.hasNext()) {
            AimsAppSubCategory aimsSubCategory = (AimsAppSubCategory)subCategoryItr.next();
            if(aimsSubCategory.getSubCategoryId().equals(subCategoryValue)
                    && aimsSubCategory.getAimsAppCategoryId().equals(categoryId)) {
                subCategoryValidated = true;
                break;
            }
        }
        return subCategoryValidated;
    }//end validateSubCategoryValues

    private boolean validateContentTypeValues(Long contentTypeValue) {
        boolean contentTypeValidated = false;
        Iterator contentTypeItr = this.allContentTypes.iterator();
        while(contentTypeItr.hasNext()) {
            AimsTypes aimsContentType = (AimsTypes)contentTypeItr.next();
            if(aimsContentType.getTypeId().equals(contentTypeValue)) {
                contentTypeValidated = true;
                break;
            }
        }
        return contentTypeValidated;
    }//end validateContentTypeValues

    private boolean validateContentRatingValues(Long contentRatingValue) {
        boolean contentRatingValidated = false;
        Iterator contentRatingItr = this.allContentRatings.iterator();
        while(contentRatingItr.hasNext()) {
            AimsTypes aimsContentRating = (AimsTypes)contentRatingItr.next();
            if(aimsContentRating.getTypeId().equals(contentRatingValue)) {
                contentRatingValidated = true;
                break;
            }
        }
        return contentRatingValidated;
    }//end validateContentTypeValues

    private boolean validateTaxCategoryValues(Long taxCategoryValue) {
        boolean taxcategoryValidated = false;
        Iterator taxtCategoryItr = this.allTaxCategoryCodes.iterator();
        while(taxtCategoryItr.hasNext()) {
            AimsTaxCategoryCode aimsTaxCategory = (AimsTaxCategoryCode)taxtCategoryItr.next();
            if(aimsTaxCategory.getTaxCategoryCodeId().equals(taxCategoryValue)) {
                taxcategoryValidated = true;
                break;
            }
        }
        return taxcategoryValidated;
    }//end validateTaxCategoryValues

    public Long getAppsId() {
        return appsId;
    }

    public void setAppsId(Long appsId) {
        this.appsId = appsId;
    }

    public Long getAppOwnerAllianceId() {
        return appOwnerAllianceId;
    }

    public void setAppOwnerAllianceId(Long appOwnerAllianceId) {
        this.appOwnerAllianceId = appOwnerAllianceId;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = StringFuncs.trim(appTitle);
    }

    public String getAppTitleToView() {
        return appTitleToView;
    }

    public void setAppTitleToView(String appTitleToView) {
        this.appTitleToView = appTitleToView;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = StringFuncs.trim(appVersion);
    }

    public String getAppShortDesc() {
        return appShortDesc;
    }

    public void setAppShortDesc(String appShortDesc) {
        this.appShortDesc = StringFuncs.trim(appShortDesc);
    }

    public String getAppLongDesc() {
        return appLongDesc;
    }

    public void setAppLongDesc(String appLongDesc) {
        this.appLongDesc = StringFuncs.trim(appLongDesc);
    }

    public String getAppCatalogName() {
        return appCatalogName;
    }

    public void setAppCatalogName(String appCatalogName) {
        this.appCatalogName = StringFuncs.trim(appCatalogName);
    }

    public String getAppProductCode() {
        return appProductCode;
    }

    public void setAppProductCode(String appProductCode) {
        this.appProductCode = StringFuncs.trim(appProductCode);
    }

    public String getGoLiveDate() {
        return goLiveDate;
    }

    public void setGoLiveDate(String goLiveDate) {
        this.goLiveDate = StringFuncs.trim(goLiveDate);
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = StringFuncs.trim(expirationDate);
    }

    public String getIfPrRelease() {
        return ifPrRelease;
    }

    public void setIfPrRelease(String ifPrRelease) {
        this.ifPrRelease = StringFuncs.trim(ifPrRelease);
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getAppSubmitType() {
        return appSubmitType;
    }

    public void setAppSubmitType(String appSubmitType) {
        this.appSubmitType = appSubmitType;
    }

    public String getSubscriptionBillingMonthly() {
        return subscriptionBillingMonthly;
    }

    public void setSubscriptionBillingMonthly(String subscriptionBillingMonthly) {
        this.subscriptionBillingMonthly = StringFuncs.trim(subscriptionBillingMonthly);
    }

    public String getSubscriptionBillingPricePoint() {
        return subscriptionBillingPricePoint;
    }

    public void setSubscriptionBillingPricePoint(String subscriptionBillingPricePoint) {
        this.subscriptionBillingPricePoint = StringFuncs.trim(subscriptionBillingPricePoint);
    }

    public String getOneTimeBilling() {
        return oneTimeBilling;
    }

    public void setOneTimeBilling(String oneTimeBilling) {
        this.oneTimeBilling = StringFuncs.trim(oneTimeBilling);
    }

    public String getOneTimeBillingPricePoint() {
        return oneTimeBillingPricePoint;
    }

    public void setOneTimeBillingPricePoint(String oneTimeBillingPricePoint) {
        this.oneTimeBillingPricePoint = StringFuncs.trim(oneTimeBillingPricePoint);
    }

    public Collection getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(Collection allCategories) {
        this.allCategories = allCategories;
    }

    public Collection getAllSubCategories() {
        return allSubCategories;
    }

    public void setAllSubCategories(Collection allSubCategories) {
        this.allSubCategories = allSubCategories;
    }

    public Long getCategoryId1() {
        return categoryId1;
    }

    public void setCategoryId1(Long categoryId1) {
        this.categoryId1 = categoryId1;
    }

    public Long getSubCategoryId1() {
        return subCategoryId1;
    }

    public void setSubCategoryId1(Long subCategoryId1) {
        this.subCategoryId1 = subCategoryId1;
    }

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Long getSubCategoryId2() {
        return subCategoryId2;
    }

    public void setSubCategoryId2(Long subCategoryId2) {
        this.subCategoryId2 = subCategoryId2;
    }

    public Long getCategoryId3() {
        return categoryId3;
    }

    public void setCategoryId3(Long categoryId3) {
        this.categoryId3 = categoryId3;
    }

    public Long getSubCategoryId3() {
        return subCategoryId3;
    }

    public void setSubCategoryId3(Long subCategoryId3) {
        this.subCategoryId3 = subCategoryId3;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public Long getAimsLifecyclePhaseId() {
        return aimsLifecyclePhaseId;
    }

    public void setAimsLifecyclePhaseId(Long aimsLifecyclePhaseId) {
        this.aimsLifecyclePhaseId = aimsLifecyclePhaseId;
    }


    public String getOrignalTask() {
        return orignalTask;
    }

    public void setOrignalTask(String orignalTask) {
        this.orignalTask = orignalTask;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getAllianceName() {
        return allianceName;
    }

    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getMportalAllianceName() {
        return mportalAllianceName;
    }

    public void setMportalAllianceName(String mportalAllianceName) {
        this.mportalAllianceName = mportalAllianceName;
    }

    public FormFile getPresentation() {
        return presentation;
    }

    public void setPresentation(FormFile presentation) {
        this.presentation = presentation;
    }

    public String getPresentationFileName() {
        return presentationFileName;
    }

    public void setPresentationFileName(String presentationFileName) {
        this.presentationFileName = presentationFileName;
    }

    public Long getPresentationTempFileId() {
        return presentationTempFileId;
    }

    public void setPresentationTempFileId(Long presentationTempFileId) {
        this.presentationTempFileId = presentationTempFileId;
    }

    public FormFile getContentLandingScreenShot() {
        return contentLandingScreenShot;
    }

    public void setContentLandingScreenShot(FormFile contentLandingScreenShot) {
        this.contentLandingScreenShot = contentLandingScreenShot;
    }

    public String getContentLandingScreenShotFileName() {
        return contentLandingScreenShotFileName;
    }

    public void setContentLandingScreenShotFileName(String contentLandingScreenShotFileName) {
        this.contentLandingScreenShotFileName = contentLandingScreenShotFileName;
    }

    public Long getContentLandingScreenShotTempFileId() {
        return contentLandingScreenShotTempFileId;
    }

    public void setContentLandingScreenShotTempFileId(Long contentLandingScreenShotTempFileId) {
        this.contentLandingScreenShotTempFileId = contentLandingScreenShotTempFileId;
    }

    public Long getClonedFromId() {
        return clonedFromId;
    }

    public void setClonedFromId(Long clonedFromId) {
        this.clonedFromId = clonedFromId;
    }

    public String getClonedFromTitle() {
        return clonedFromTitle;
    }

    public void setClonedFromTitle(String clonedFromTitle) {
        this.clonedFromTitle = clonedFromTitle;
    }

    public Long getAimsContactId() {
        return aimsContactId;
    }

    public void setAimsContactId(Long aimsContactId) {
        this.aimsContactId = aimsContactId;
    }

    public Collection getAllContacts() {
        return allContacts;
    }

    public void setAllContacts(Collection allContacts) {
        this.allContacts = allContacts;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getCommunityChatUgc() {
        return communityChatUgc;
    }

    public void setCommunityChatUgc(String communityChatUgc) {
        this.communityChatUgc = communityChatUgc;
    }

    public String getContentSweekstakes() {
        return contentSweekstakes;
    }

    public void setContentSweekstakes(String contentSweekstakes) {
        this.contentSweekstakes = contentSweekstakes;
    }

    public String getSubsVendorProductDisplay() {
        return subsVendorProductDisplay;
    }

    public void setSubsVendorProductDisplay(String subsVendorProductDisplay) {
        this.subsVendorProductDisplay = subsVendorProductDisplay;
    }

    public String getSubsVZWRecommendedPrice() {
        return subsVZWRecommendedPrice;
    }

    public void setSubsVZWRecommendedPrice(String subsVZWRecommendedPrice) {
        this.subsVZWRecommendedPrice = subsVZWRecommendedPrice;
    }

    public String getSubsVendorSplitPercentage() {
        return subsVendorSplitPercentage;
    }

    public void setSubsVendorSplitPercentage(String subsVendorSplitPercentage) {
        this.subsVendorSplitPercentage = subsVendorSplitPercentage;
    }

    public Long getContentType() {
        return contentType;
    }

    public void setContentType(Long contentType) {
        this.contentType = contentType;
    }

    public Collection getAllContentTypes() {
        return allContentTypes;
    }

    public void setAllContentTypes(Collection allContentTypes) {
        this.allContentTypes = allContentTypes;
    }

    public Long getContentRating() {
        return contentRating;
    }

    public void setContentRating(Long contentRating) {
        this.contentRating = contentRating;
    }

    public Collection getAllContentRatings() {
        return allContentRatings;
    }

    public void setAllContentRatings(Collection allContentRatings) {
        this.allContentRatings = allContentRatings;
    }

    public String getScmVendorId() {
        return scmVendorId;
    }

    public void setScmVendorId(String scmVendorId) {
        this.scmVendorId = StringFuncs.trim(scmVendorId);
    }

    public String getVzwLiveDate() {
        return vzwLiveDate;
    }

    public void setVzwLiveDate(String vzwLiveDate) {
        this.vzwLiveDate = StringFuncs.trim(vzwLiveDate);
    }

    public String getInitialApprovalAction() {
        return initialApprovalAction;
    }

    public void setInitialApprovalAction(String initialApprovalAction) {
        this.initialApprovalAction = initialApprovalAction;
    }

    public String getInitialApprovalNotes() {
        return initialApprovalNotes;
    }

    public void setInitialApprovalNotes(String initialApprovalNotes) {
        this.initialApprovalNotes = StringFuncs.trim(initialApprovalNotes);
    }

    public Collection getAllTaxCategoryCodes() {
        return allTaxCategoryCodes;
    }

    public void setAllTaxCategoryCodes(Collection allTaxCategoryCodes) {
        this.allTaxCategoryCodes = allTaxCategoryCodes;
    }

    public Long getTaxCategoryCodeId() {
        return taxCategoryCodeId;
    }

    public void setTaxCategoryCodeId(Long taxCategoryCodeId) {
        this.taxCategoryCodeId = taxCategoryCodeId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = StringFuncs.trim(contentId);
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
        this.journalText = journalText;
    }

    public String getJournalCombinedText() {
        return journalCombinedText;
    }

    public void setJournalCombinedText(String journalCombinedText) {
        this.journalCombinedText = journalCombinedText;
    }

    public String[] getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String[] errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getOneTimeVendorProductDisplay() {
        return oneTimeVendorProductDisplay;
    }

    public void setOneTimeVendorProductDisplay(String oneTimeVendorProductDisplay) {
        this.oneTimeVendorProductDisplay = oneTimeVendorProductDisplay;
    }

    public String getOneTimeVZWRecommendedPrice() {
        return oneTimeVZWRecommendedPrice;
    }

    public void setOneTimeVZWRecommendedPrice(String oneTimeVZWRecommendedPrice) {
        this.oneTimeVZWRecommendedPrice = oneTimeVZWRecommendedPrice;
    }

    public String getOneTimeVendorSplitPercentage() {
        return oneTimeVendorSplitPercentage;
    }

    public void setOneTimeVendorSplitPercentage(String oneTimeVendorSplitPercentage) {
        this.oneTimeVendorSplitPercentage = oneTimeVendorSplitPercentage;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public String getIsAppLocked() {
        return isAppLocked;
    }

    public void setIsAppLocked(String isAppLocked) {
        this.isAppLocked = isAppLocked;
    }

    public List getVZAppBinaryFirmwareInfoVOs() {
        return VZAppBinaryFirmwareInfoVOs;
    }

    public void setVZAppBinaryFirmwareInfoVOs(List VZAppBinaryFirmwareInfoVOs) {
        this.VZAppBinaryFirmwareInfoVOs = VZAppBinaryFirmwareInfoVOs;
    }

    public VZAppBinaryFirmwareInfoVO getVZAppBinaryFirmwareInfo(int index) {
        if(this.VZAppBinaryFirmwareInfoVOs == null) {
            this.VZAppBinaryFirmwareInfoVOs = new ArrayList();
        }
        while(index >= this.VZAppBinaryFirmwareInfoVOs.size()) {
            this.VZAppBinaryFirmwareInfoVOs.add(new VZAppBinaryFirmwareInfoVO());
        }
        return (VZAppBinaryFirmwareInfoVO)VZAppBinaryFirmwareInfoVOs.get(index);
    }

    public void setVZAppBinaryFirmwareInfo(int index, VZAppBinaryFirmwareInfoVO vzAppBinaryFirmwareInfo) {
        this.VZAppBinaryFirmwareInfoVOs.set(index, vzAppBinaryFirmwareInfo);
    }

    public List getVZAppBaseTests() {
        return VZAppBaseTests;
    }

    public void setVZAppBaseTests(List VZAppBaseTests) {
        this.VZAppBaseTests = VZAppBaseTests;
    }

    public VZAppBaseTestVO getVZAppBaseTest(int index) {
        if(this.VZAppBaseTests == null) {
            this.VZAppBaseTests = new ArrayList();
        }
        while(index >= this.VZAppBaseTests.size()) {
            this.VZAppBaseTests.add(new VZAppBaseTestVO());
        }
        return (VZAppBaseTestVO)VZAppBaseTests.get(index);
    }

    public void setVZAppBaseTest(int index, VZAppBaseTestVO vzAppBaseTest) {
        this.VZAppBaseTests.set(index, vzAppBaseTest);
    }

    public Map getBaseTestResultsTempFileMap() {
        return baseTestResultsTempFileMap;
    }

    public void setBaseTestResultsTempFileMap(Map baseTestResultsTempFileMap) {
        this.baseTestResultsTempFileMap = baseTestResultsTempFileMap;
    }

    public List getVZAppZoneOTATests() {
        return VZAppZoneOTATests;
    }

    public void setVZAppZoneOTATests(List VZAppZoneOTATests) {
        this.VZAppZoneOTATests = VZAppZoneOTATests;
    }

    public VZAppZoneOTATestVO getVZAppZoneOTATest(int index) {
        if(this.VZAppZoneOTATests == null) {
            this.VZAppZoneOTATests = new ArrayList();
        }
        while(index >= this.VZAppZoneOTATests.size()) {
            this.VZAppZoneOTATests.add(new VZAppZoneOTATestVO());
        }
        return (VZAppZoneOTATestVO)VZAppZoneOTATests.get(index);
    }

    public void setVZAppZoneOTATest(int index, VZAppZoneOTATestVO VZAppZoneOTATest) {
        this.VZAppZoneOTATests.set(index, VZAppZoneOTATest);
    }

    public Map getOtaTestResultsTempFileMap() {
        return otaTestResultsTempFileMap;
    }

    public void setOtaTestResultsTempFileMap(Map otaTestResultsTempFileMap) {
        this.otaTestResultsTempFileMap = otaTestResultsTempFileMap;
    }

    public List getVZAppZoneStageInfoVOs() {
        return VZAppZoneStageInfoVOs;
    }

    public void setVZAppZoneStageInfoVOs(List VZAppZoneStageInfoVOs) {
        this.VZAppZoneStageInfoVOs = VZAppZoneStageInfoVOs;
    }

    public VZAppZoneStageInfoVO getVZAppZoneStageInfo(int index) {
        if(this.VZAppZoneStageInfoVOs == null) {
            this.VZAppZoneStageInfoVOs = new ArrayList();
        }
        while(index >= this.VZAppZoneStageInfoVOs.size()) {
            this.VZAppZoneStageInfoVOs.add(new VZAppZoneStageInfoVO());
        }
        return (VZAppZoneStageInfoVO)VZAppZoneStageInfoVOs.get(index);
    }

    public void setVZAppZoneStageInfo(int index, VZAppZoneStageInfoVO vzAppZoneStageInfo) {
        this.VZAppZoneStageInfoVOs.set(index, vzAppZoneStageInfo);
    }

    public List getVZAppZoneProdInfoVOs() {
        return VZAppZoneProdInfoVOs;
    }

    public void setVZAppZoneProdInfoVOs(List VZAppZoneProdInfoVOs) {
        this.VZAppZoneProdInfoVOs = VZAppZoneProdInfoVOs;
    }

    public VZAppZoneProdInfoVO getVZAppZoneProdInfo(int index) {
        if(this.VZAppZoneProdInfoVOs == null) {
            this.VZAppZoneProdInfoVOs = new ArrayList();
        }
        while(index >= this.VZAppZoneProdInfoVOs.size()) {
            this.VZAppZoneProdInfoVOs.add(new VZAppZoneProdInfoVO());
        }
        return (VZAppZoneProdInfoVO)VZAppZoneProdInfoVOs.get(index);
    }

    public void setVzAppZoneProdInfo(int index, VZAppZoneProdInfoVO vzAppZoneProdInfoVO) {
        this.VZAppZoneProdInfoVOs.set(index, vzAppZoneProdInfoVO);
    }

    public String getMoveToSunset() {
        return moveToSunset;
    }

    public void setMoveToSunset(String moveToSunset) {
        this.moveToSunset = moveToSunset;
    }


    public FormFile getBinaryFile() {
        return binaryFile;
    }

    public void setBinaryFile(FormFile binaryFile) {
        this.binaryFile = binaryFile;
    }

    public String getBinaryFileFileName() {
        return binaryFileFileName;
    }

    public void setBinaryFileFileName(String binaryFileFileName) {
        this.binaryFileFileName = binaryFileFileName;
    }

    public Long getBinaryFileTempFileId() {
        return binaryFileTempFileId;
    }

    public void setBinaryFileTempFileId(Long binaryFileTempFileId) {
        this.binaryFileTempFileId = binaryFileTempFileId;
    }

    public Long getBinaryId() {
        return binaryId;
    }

    public void setBinaryId(Long binaryId) {
        this.binaryId = binaryId;
    }

    public String getBinaryVersion() {
        return binaryVersion;
    }

    public void setBinaryVersion(String binaryVersion) {
        this.binaryVersion = binaryVersion;
    }

    public String getBinaryFileSize() {
        return binaryFileSize;
    }

    public void setBinaryFileSize(String binaryFileSize) {
        this.binaryFileSize = binaryFileSize;
    }

    public FormFile getPreviewFile() {
        return previewFile;
    }

    public void setPreviewFile(FormFile previewFile) {
        this.previewFile = previewFile;
    }

    public String getPreviewFileFileName() {
        return previewFileFileName;
    }

    public void setPreviewFileFileName(String previewFileFileName) {
        this.previewFileFileName = previewFileFileName;
    }

    public Long getPreviewFileTempFileId() {
        return previewFileTempFileId;
    }

    public void setPreviewFileTempFileId(Long previewFileTempFileId) {
        this.previewFileTempFileId = previewFileTempFileId;
    }

    public FormFile getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(FormFile documentFile) {
        this.documentFile = documentFile;
    }

    public String getDocumentFileFileName() {
        return documentFileFileName;
    }

    public void setDocumentFileFileName(String documentFileFileName) {
        this.documentFileFileName = documentFileFileName;
    }

    public Long getDocumentFileTempFileId() {
        return documentFileTempFileId;
    }

    public void setDocumentFileTempFileId(Long documentFileTempFileId) {
        this.documentFileTempFileId = documentFileTempFileId;
    }

    public List getAllVZAppDeviceFirmwareList() {
        return allVZAppDeviceFirmwareList;
    }

    public void setAllVZAppDeviceFirmwareList(List allVZAppDeviceFirmwareList) {
        this.allVZAppDeviceFirmwareList = allVZAppDeviceFirmwareList;
    }

    public List getVzAppBinaryFirmwareTestStatusList() {
        return vzAppBinaryFirmwareTestStatusList;
    }

    public void setVzAppBinaryFirmwareTestStatusList(List vzAppBinaryFirmwareTestStatusList) {
        this.vzAppBinaryFirmwareTestStatusList = vzAppBinaryFirmwareTestStatusList;
    }

    public List getVzAppBinaryFirmwareInfoList() {
        return vzAppBinaryFirmwareInfoList;
    }

    public void setVzAppBinaryFirmwareInfoList(List vzAppBinaryFirmwareInfoList) {
        this.vzAppBinaryFirmwareInfoList = vzAppBinaryFirmwareInfoList;
    }

    public List getVzAppBinaries() {
        return vzAppBinaries;
    }

    public void setVzAppBinaries(List vzAppBinaries) {
        this.vzAppBinaries = vzAppBinaries;
    }

    public Long[] getVzAppFirmwareIds() {
        return vzAppFirmwareIds;
    }

    public void setVzAppFirmwareIds(Long[] vzAppFirmwareIds) {
        this.vzAppFirmwareIds = vzAppFirmwareIds;
    }

    public Long[] getVzAppDeviceIds() {
        return vzAppDeviceIds;
    }

    public void setVzAppDeviceIds(Long[] vzAppDeviceIds) {
        this.vzAppDeviceIds = vzAppDeviceIds;
    }

    private boolean validateZeroVersion(String version) {
        String patStr = "(0)+(\\.(0+))*";
        java.util.regex.Pattern versionPattern = java.util.regex.Pattern.compile(patStr);
        return versionPattern.matcher(version).matches();
    }

    private String getDeviceNameFormatForErrorMessages(String phoneModel, String mrNumber) {
        return phoneModel+" ("+mrNumber+")";
    }

    public String getTempForRadioIssue() {
        return tempForRadioIssue;
    }

    public void setTempForRadioIssue(String tempForRadioIssue) {
        this.tempForRadioIssue = tempForRadioIssue;
    }
}
