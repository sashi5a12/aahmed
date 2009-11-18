package com.netpace.aims.controller.application;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.system.FirmwareManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.dataaccess.valueobjects.FirmwareInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppBaseTestVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppBinaryFirmwareInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppBinaryFirmwarePhaseInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppZoneOTATestVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppZoneProdInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppZoneStageInfoVO;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.application.AimsVZAppBinaries;
import com.netpace.aims.model.application.AimsVZAppBinaryFirmware;
import com.netpace.aims.model.application.AimsVZAppZoneApp;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.ConfigEnvProperties;
import net.sf.hibernate.HibernateException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.net.URLEncoder;


/**
 * This class takes care of action for display of update form of VZAppZone Application.
 *
 * @struts.action path="/vzAppZoneApplicationUpdate"
 *                name="VZAppZoneApplicationUpdateForm"
 *                scope="request"
 *                input="/application/vzAppZoneApplicationUpdate.jsp" 
 * @struts.action-forward name="page1" path="/application/vzAppZoneApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/vzAppZoneApplicationUpdate2.jsp"
 * @struts.action-forward name="page3" path="/application/vzAppZoneBinariesUpdate.jsp"
 * @struts.action-forward name="processingInfo" path="/application/vzAppZoneProcessingInfoUpdate.jsp"
 * @struts.action-forward name="vzAppZoneJournal" path="/application/vzAppZoneJournal.jsp"
 * @struts.action-forward name="vzAppZoneJournalView" path="/application/vzAppZoneJournalView.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @author Sajjad Raza
 */
public class VZAppZoneApplicationUpdateAction extends BaseAction {
    private static Logger log = Logger.getLogger(VZAppZoneApplicationUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("============== start VZAppZoneApplicationUpdateAction");

        String forward = "view";

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        AimsAllianc aimsAllianceOfApplication = null;
        AimsContact currUserContact = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getAimsContact();

        VZAppZoneApplicationUpdateForm vzAppZoneUpdateForm = (VZAppZoneApplicationUpdateForm) form;

        log.debug("---- start printing vairables before execution");
        log.debug("task (request): "+request.getParameter("task"));
        if(vzAppZoneUpdateForm!=null) {
            log.debug("task: "+vzAppZoneUpdateForm.getTask());
            log.debug("orignalTask: "+vzAppZoneUpdateForm.getOrignalTask());
            log.debug("currentPage: "+vzAppZoneUpdateForm.getCurrentPage());
            log.debug("appSubmitType: "+vzAppZoneUpdateForm.getAppSubmitType());
            log.debug("appsId: "+vzAppZoneUpdateForm.getAppsId());
            log.debug("appTitleToView: "+vzAppZoneUpdateForm.getAppTitleToView());

            log.debug("isAppLocked: "+vzAppZoneUpdateForm.getIsAppLocked());//AimsApp locked value (from DB)
            log.debug("isLocked: "+vzAppZoneUpdateForm.getIsLocked());//Lock application checkbox

            log.debug("appOwnerAllianceId: "+vzAppZoneUpdateForm.getAppOwnerAllianceId());
            log.debug("clonedFromId: "+vzAppZoneUpdateForm.getClonedFromId());
            log.debug("clonedFromTitle: "+vzAppZoneUpdateForm.getClonedFromTitle());
            log.debug("aimsLifecyclePhaseId: "+vzAppZoneUpdateForm.getAimsLifecyclePhaseId());
            log.debug("applicationStatus: "+vzAppZoneUpdateForm.getApplicationStatus());
            log.debug("allianceName: "+vzAppZoneUpdateForm.getAllianceName());
            log.debug("vendorId: "+vzAppZoneUpdateForm.getVendorId());
            log.debug("appTitle: "+vzAppZoneUpdateForm.getAppTitle());
            log.debug("appVersion: "+vzAppZoneUpdateForm.getAppVersion());

            log.debug("categoryId1: "+vzAppZoneUpdateForm.getCategoryId1());
            log.debug("subCategoryId1: "+vzAppZoneUpdateForm.getSubCategoryId1());
            log.debug("subscriptionBillingMonthly: "+vzAppZoneUpdateForm.getSubscriptionBillingMonthly());
            log.debug("subscriptionBillingPricePoint: "+vzAppZoneUpdateForm.getSubscriptionBillingPricePoint());
            log.debug("oneTimeBilling: "+vzAppZoneUpdateForm.getOneTimeBilling());
            log.debug("oneTimeBillingPricePoint: "+vzAppZoneUpdateForm.getOneTimeBillingPricePoint());
            log.debug("mportalAllianceName: "+vzAppZoneUpdateForm.getMportalAllianceName());

            log.debug("moveToSunset: "+vzAppZoneUpdateForm.getMoveToSunset());

        }
        log.debug("---- end printing vairables before execution");

        String taskName = request.getParameter("task");
        String submitType = "";
        Long appsId = null;

        AimsApp aimsApp = null;
        AimsVZAppZoneApp vzAppZoneApp = null;

        HashMap vzAppZoneAppTable = null;
        Long oldStatus = new Long(0);
        Long clonedFromId = new Long(0);

        Collection journalEntries = null;//to show on Page

        boolean isLocked =false;
        boolean oneDeviceTestPassed = false;

        boolean processForm = false;

        List journalEntriesToSave = new ArrayList();

        AimsVZAppBinaries vzAppBinary = null;
        Map vzAppBinariesMap = null;
        Long selectedBinaryId = null;
        List newVZAppBinaryFirmwareList = null;
        List activeAimsVZAppBinaryFirmwareList = null;
        List firmwareInfoVOList = null;
        List binaryJournalEntriesList = null;

        Date currDate = new Date();

        boolean hasAccessInitialApproval = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_INITIAL_APPROVAL, AimsSecurityManager.UPDATE);
        boolean hasAccessBasicTesting = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_DEVICE_TESTING, AimsSecurityManager.UPDATE);
        boolean hasAccessApplicationManagement = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_APPLICATION_MANAGEMENT, AimsSecurityManager.UPDATE);
        boolean hasAccessOTATesting = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_OTA_TESTING, AimsSecurityManager.UPDATE);
        boolean hasAccessMoveToStaging = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_MOVE_TO_STAGING, AimsSecurityManager.UPDATE);
        boolean hasAccessMoveToProd = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_MOVE_TO_PRODUCTION, AimsSecurityManager.UPDATE);
        boolean hasAccessSunset = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_SUNSET, AimsSecurityManager.UPDATE);

        Map binaryFirmwareSectionsMap = null;
        List vzAppMoveToOTATestingList = null;
        List vzAppMoveToStagingList = null;
        List vzAppMoveToProdList = null;

        List changedBaseTestStatusList = null;
        List changedOTATestStatusList = null;

        List baseTestJournalEntriesList = null;
        List otaTestJournalEntriesList = null;
        List moveToServerJournalEntriesList = null;

        boolean isAppMovedFromSubmitToUnderTesting = false;        

        if(!StringFuncs.isNullOrEmpty(taskName)) {

            submitType = StringFuncs.NullValueReplacement(vzAppZoneUpdateForm.getAppSubmitType());
            appsId = new Long(StringFuncs.NulltoZeroStringReplacement(request.getParameter("appsId")));

            if (taskName.equals("create")) {
                aimsApp = new AimsApp();
                vzAppZoneApp = new AimsVZAppZoneApp();
            }
            else if(taskName.equals("edit") || taskName.equals("removeBinary")) {
                vzAppZoneAppTable = VZAppZoneApplicationManager.getVZAppZoneApp(appsId, currentUserAllianceId);
                aimsApp = (AimsApp) vzAppZoneAppTable.get("AimsApp");
                vzAppZoneApp = (AimsVZAppZoneApp) vzAppZoneAppTable.get("AimsVZAppZoneApp");
                oldStatus = aimsApp.getAimsLifecyclePhaseId();
                //get active aimsvzappbinaryfirmwares
                activeAimsVZAppBinaryFirmwareList = VZAppZoneApplicationManager.getAllActiveAimsVZAppBinaryFirmwaresList(appsId);

                if(taskName.equals("removeBinary")) {
                    vzAppZoneUpdateForm.setCurrentPage("page3");
                    vzAppZoneUpdateForm.setTask(vzAppZoneUpdateForm.getOrignalTask());

                    //if user is alliance and application is in saved state, then user can remove binary
                    if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE) && aimsApp.getAimsLifecyclePhaseId().equals(AimsConstants.SAVED_ID)) {
                        if(Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getBinaryId()).longValue()>0) {
                            VZAppZoneApplicationManager.deleteAimsVZAppBinaries(appsId, vzAppZoneUpdateForm.getBinaryId());
                        }
                        //rest binary file temp ids
                        this.resetFrmBinaryPageFields(vzAppZoneUpdateForm);
                    }
                    submitType = "";//reset submit type to skip other tasks
                    forward = "page3";
                    //return mapping.getInputForward();
                }
            }

            //tabs work
            if( submitType.equalsIgnoreCase("paging")) {
                if (taskName.equalsIgnoreCase("page1")) {
                    forward = "page1";  //Basic Info
                }
                else if (taskName.equalsIgnoreCase("page2")) {
                    forward = "page2";  //Additional Info
                }
                else if (taskName.equalsIgnoreCase("page3")) {
                    forward = "page3";  //Binaries Update
                }
                else if (taskName.equalsIgnoreCase("processingInfo")) {
                    forward = "processingInfo";  //processing info
                }
                else if((taskName.equalsIgnoreCase("vzAppZoneJournal"))
                        || (taskName.equalsIgnoreCase("submitVZAppZoneJournal"))
                        || (taskName.equalsIgnoreCase("submitVZAppZoneJournalView"))) {
                    if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_JOURNAL_ENTRIES, AimsSecurityManager.SELECT)) {
                        if (taskName.equalsIgnoreCase("vzAppZoneJournal")) {
                            forward = "vzAppZoneJournal";  //Journal
                            //set journal entries whenever journal tab called, no need to pass it through form or hidden variable
                            journalEntries = AimsApplicationsManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
                            vzAppZoneUpdateForm.setJournalCombinedText(ApplicationHelper.getFormattedJournalEntries(journalEntries));

                        }
                        else if (taskName.equalsIgnoreCase("submitVZAppZoneJournal")) {
                            forward = "vzAppZoneJournal";  //Journal update
                        }
                        else if (taskName.equalsIgnoreCase("submitVZAppZoneJournalView")) {
                            forward = "vzAppZoneJournalView";  //Journal update from View Page
                        }
                    }//end if checkAccess Journal
                    else {
                        throw new AimsSecurityException();
                    }
                }

                vzAppZoneUpdateForm.setCurrentPage(taskName);
                vzAppZoneUpdateForm.setTask(vzAppZoneUpdateForm.getOrignalTask());

                //save journal entries entered from UI, only VZW User can add journal entries from UI
                if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {
                    if ((taskName.equalsIgnoreCase("submitVZAppZoneJournal")) || (taskName.equalsIgnoreCase("submitVZAppZoneJournalView"))) {
                        if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_JOURNAL_ENTRIES, AimsSecurityManager.UPDATE)) {
                            String journalText = StringFuncs.NullValueReplacement(vzAppZoneUpdateForm.getJournalText()).trim();
                            String journalType = StringFuncs.NullValueReplacement(vzAppZoneUpdateForm.getJournalType()).trim();
                            if(StringFuncs.isNullOrEmpty(journalText)) {
                                ActionErrors errors = new ActionErrors();
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.journal.text.required"));
                                saveErrors(request, errors);
                                return mapping.findForward(forward);
                                //return mapping.getInputForward();
                            }
                            else if(StringFuncs.isNullOrEmpty(journalType)) {
                                ActionErrors errors = new ActionErrors();
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.journal.type.required"));
                                saveErrors(request, errors);
                                return mapping.findForward(forward);
                            }
                            else {
                                AimsApplicationsManager.saveJournalEntry(
                                    vzAppZoneUpdateForm.getAppsId(),
                                    journalText,
                                    journalType,
                                    currUser);
                                journalEntries = AimsApplicationsManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
                                vzAppZoneUpdateForm.setJournalCombinedText(ApplicationHelper.getFormattedJournalEntries(journalEntries));
                            }
                        }//end check access
                        else {
                            throw new AimsSecurityException();
                        }
                    }
                }//end save journal entries from UI
            }

            //end tabs

            if ((submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)) ) {

                if(taskName.equals("create") || taskName.equals("edit")) {

                    //for first time only
                    if(taskName.equals("create")) {
                        aimsApp.setAimsAllianceId(currentUserAllianceId);
                        aimsApp.setAimsUserAppCreatedById(currUserId);
                        aimsApp.setIfOnHold("N");
                        aimsApp.setCreatedBy(currUser);
                        aimsApp.setCreatedDate(currDate);
                    }

                    //set isLocked Variable, application is locked if isLocked field == Y
                    isLocked = (!StringFuncs.isNullOrEmpty(vzAppZoneApp.getIsLocked()) &&
                            vzAppZoneApp.getIsLocked().equalsIgnoreCase(AimsConstants.VZAPPZONE_APP_LOCK_APPLICATION[0]));

                    aimsApp.setLastUpdatedBy(currUser);
                    aimsApp.setLastUpdatedDate(currDate);
                    aimsApp.setAimsPlatformId(AimsConstants.VZAPPZONE_PLATFORM_ID);

                    //AimsApp Fields
                    //if application is locked, then user can not change basic and additional info fields
                    if(!isLocked) {
                        aimsApp.setTitle(vzAppZoneUpdateForm.getAppTitle());
                        aimsApp.setVersion(vzAppZoneUpdateForm.getAppVersion());
                        aimsApp.setShortDesc(vzAppZoneUpdateForm.getAppShortDesc());
                        aimsApp.setLongDesc(vzAppZoneUpdateForm.getAppLongDesc());
                        aimsApp.setIfPrRelease(vzAppZoneUpdateForm.getIfPrRelease());

                        //VZAppZone Fields
                        /* commented, catalog name and product code, no need to show
                            vzAppZoneApp.setAppCatalogName(vzAppZoneUpdateForm.getAppCatalogName());
                            vzAppZoneApp.setAppProductCode(vzAppZoneUpdateForm.getAppProductCode());
                        */
                        vzAppZoneApp.setAppGoLiveDate(Utility.convertToDate(vzAppZoneUpdateForm.getGoLiveDate(), dateFormat));
                        vzAppZoneApp.setAppExpirationDate(Utility.convertToDate(vzAppZoneUpdateForm.getExpirationDate(), dateFormat));
                                                
                        if(Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getContentRating()).longValue()>0) {
                        vzAppZoneApp.setContentRating(vzAppZoneUpdateForm.getContentRating());
                        }
                        else {
                            vzAppZoneApp.setContentRating(null);
                        }

                        vzAppZoneApp.setCommunityChatUgc(vzAppZoneUpdateForm.getCommunityChatUgc());
                        vzAppZoneApp.setContentSweekstakes(vzAppZoneUpdateForm.getContentSweekstakes());
                    }

                    //SubCategory1
                    if (Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getSubCategoryId1()).longValue()>0) {
                        vzAppZoneApp.setSubCategory1(vzAppZoneUpdateForm.getSubCategoryId1());
                    }
                    else {
                        vzAppZoneApp.setSubCategory1(null);
                    }
                    vzAppZoneApp.setSubsBilling(vzAppZoneUpdateForm.getSubscriptionBillingMonthly());
                    vzAppZoneApp.setSubsBillingPricepoint(vzAppZoneUpdateForm.getSubscriptionBillingPricePoint());
                    vzAppZoneApp.setOnetimeBilling(vzAppZoneUpdateForm.getOneTimeBilling());
                    vzAppZoneApp.setOnetimeBillingPricepoint(vzAppZoneUpdateForm.getOneTimeBillingPricePoint());


                        vzAppBinariesMap = this.populateVZAppBinariesMap(vzAppZoneUpdateForm, activeAimsVZAppBinaryFirmwareList, aimsApp.getAimsLifecyclePhaseId(), currDate,  currUser);
                        vzAppBinary = (AimsVZAppBinaries) vzAppBinariesMap.get("vzAppBinary");
                        selectedBinaryId = (Long) vzAppBinariesMap.get("selectedBinaryId");
                        newVZAppBinaryFirmwareList = (List) vzAppBinariesMap.get("newVZAppBinaryFirmwareList");
                        firmwareInfoVOList = (List) vzAppBinariesMap.get("firmwareInfoVOList");
                        binaryJournalEntriesList = (List)vzAppBinariesMap.get("binaryJournalEntriesList");
                        if(binaryJournalEntriesList!=null && binaryJournalEntriesList.size()>0) {
                            journalEntriesToSave.addAll(binaryJournalEntriesList);
                        }
                        //vzAppBinary, selectedBinaryId, newVZAppBinaryFirmwareList and firmwareInfoVOList will be used to send notifications


                    //The following properties can only be updated by an Alliance user and not the Verizon user
                    if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)) {
                        //START OF CHANGING/UPDATING THE PHASE for Alliance
                        if (vzAppZoneUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM)) {
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
                        }
                        else if (vzAppZoneUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM)) {
                            //check alliance has accepted atleast one active vzappzone contract
                            if(!this.validateAllianceContract(aimsApp.getAimsAllianceId(), currUserType)) {
                                ActionErrors errors = new ActionErrors();
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.contract.acceptance"));
                                saveErrors(request, errors);
                                //return mapping.getInputForward();
                                vzAppZoneUpdateForm.setCurrentPage("page1");
                                return mapping.findForward("page1");
                            }
                            aimsApp.setSubmittedDate(currDate);
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUBMISSION_ID);
                        }

                        //if application is locked then user can not change additional info fields
                        if(!isLocked) {
                            //Contact
                            if (Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getAimsContactId()).longValue() > 0) {
                                aimsApp.setAimsContactId(vzAppZoneUpdateForm.getAimsContactId());
                            }
                            else {
                                aimsApp.setAimsContactId(null);
                            }
                        }
                    }//end if alliance user
                    else if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {
                        //change values if admin user only
                        Long scmVendorId = StringFuncs.isNullOrEmpty(vzAppZoneUpdateForm.getScmVendorId()) ? null : new Long(vzAppZoneUpdateForm.getScmVendorId());
                        processForm = vzAppZoneUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM);
                        //update initial approval fields if user has access to this section
                        if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_INITIAL_APPROVAL, AimsSecurityManager.UPDATE)) {
                            vzAppZoneApp.setSubVZWRecommendedPrice(vzAppZoneUpdateForm.getSubsVZWRecommendedPrice());
                            vzAppZoneApp.setSubsVendorSplitPercent(vzAppZoneUpdateForm.getSubsVendorSplitPercentage());
                            vzAppZoneApp.setSubsVendorProdDisplay(vzAppZoneUpdateForm.getSubsVendorProductDisplay());
                            vzAppZoneApp.setOnetimeVZWRecommendedPrice(vzAppZoneUpdateForm.getOneTimeVZWRecommendedPrice());
                            vzAppZoneApp.setOnetimeVendorSplitPercent(vzAppZoneUpdateForm.getOneTimeVendorSplitPercentage());
                            vzAppZoneApp.setOnetimeVendorProdDisplay(vzAppZoneUpdateForm.getOneTimeVendorProductDisplay());

                            if (Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getSubCategoryId2()).longValue()>0) {
                                vzAppZoneApp.setSubCategory2(vzAppZoneUpdateForm.getSubCategoryId2());
                            }
                            else {
                                vzAppZoneApp.setSubCategory2(null);
                            }
                            if (Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getSubCategoryId3()).longValue()>0) {
                                vzAppZoneApp.setSubCategory3(vzAppZoneUpdateForm.getSubCategoryId3());
                            }
                            else {
                                vzAppZoneApp.setSubCategory3(null);
                            }
                            /* commented, scmVendorId, not used now
                                vzAppZoneApp.setScmVendorId(scmVendorId);
                            */
                            vzAppZoneApp.setInitialApprovalNotes(vzAppZoneUpdateForm.getInitialApprovalNotes());
                            vzAppZoneApp.setVzwLiveDate(Utility.convertToDate(vzAppZoneUpdateForm.getVzwLiveDate(), dateFormat));
                            //if tax category code provided then save it, otherwise set it to null
                            if(Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getTaxCategoryCodeId()).longValue()>0) {
                                vzAppZoneApp.setTaxCategoryCodeId(vzAppZoneUpdateForm.getTaxCategoryCodeId());
                            }
                            else {
                                vzAppZoneApp.setTaxCategoryCodeId(null);
                            }
                            //if contnet type is proviced then save it, otherwise set it to null
                            if(Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getContentType()).longValue()>0) {
                                vzAppZoneApp.setContentType(vzAppZoneUpdateForm.getContentType());
                            }
                            else {
                                vzAppZoneApp.setContentType(null);
                            }

                        }

                        //Testing, save form fields
                        binaryFirmwareSectionsMap = this.populateVZAppBinaryFirmwaresSections(request, vzAppZoneUpdateForm,
                                                                activeAimsVZAppBinaryFirmwareList,
                                                                Utility.ZeroValueReplacement(oldStatus),
                                                                processForm, dateFormat, currUser, currDate);
                        changedBaseTestStatusList = (List)binaryFirmwareSectionsMap.get("changedBaseTestStatusList");
                        changedOTATestStatusList = (List)binaryFirmwareSectionsMap.get("changedOTATestStatusList");
                        vzAppMoveToOTATestingList = (List)binaryFirmwareSectionsMap.get("vzAppMoveToOTATestingList");
                        vzAppMoveToStagingList = (List)binaryFirmwareSectionsMap.get("vzAppMoveToStagingList");
                        vzAppMoveToProdList = (List)binaryFirmwareSectionsMap.get("vzAppMoveToProdList");
                        baseTestJournalEntriesList = (List)binaryFirmwareSectionsMap.get("baseTestJournalEntriesList");
                        otaTestJournalEntriesList = (List)binaryFirmwareSectionsMap.get("otaTestJournalEntriesList");
                        moveToServerJournalEntriesList = (List)binaryFirmwareSectionsMap.get("moveToServerJournalEntriesList");

                        if(baseTestJournalEntriesList!=null && baseTestJournalEntriesList.size()>0) {
                            journalEntriesToSave.addAll(baseTestJournalEntriesList);
                        }
                        if(otaTestJournalEntriesList!=null && otaTestJournalEntriesList.size()>0) {
                            journalEntriesToSave.addAll(otaTestJournalEntriesList);
                        }
                        if(moveToServerJournalEntriesList!=null && moveToServerJournalEntriesList.size()>0) {
                            journalEntriesToSave.addAll(moveToServerJournalEntriesList);
                        }

                        //start changing phases
                        if(processForm) {
                            //From Submit -> Initial Approval/Denied
                            if(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_INITIAL_APPROVAL, AimsSecurityManager.UPDATE)) {
                                if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SUBMISSION_ID.longValue())) {
                                    String initialApprovalAction = StringFuncs.NullValueReplacement(vzAppZoneUpdateForm.getInitialApprovalAction());
                                    vzAppZoneApp.setInitialApprovalAction(initialApprovalAction);
                                    if (initialApprovalAction.equals(AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_APPROVE[0])) {
                                        log.debug("...... moving application to Under Testing. appsId: "+appsId);
                                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.TESTING_ID);
                                        //set status and under testing date of all active aimsVZAppBinaryFirmwares
                                        if(activeAimsVZAppBinaryFirmwareList!=null && activeAimsVZAppBinaryFirmwareList.size()>0) {
                                            //todo no need of this null check, as app must have vzappbinaryfirmwares at this stage
                                            AimsVZAppBinaryFirmware aimsBinaryFirmwareForUnderTesting = null;
                                            for(int underTestingIndex=0; underTestingIndex<activeAimsVZAppBinaryFirmwareList.size(); underTestingIndex++) {
                                                aimsBinaryFirmwareForUnderTesting = (AimsVZAppBinaryFirmware)activeAimsVZAppBinaryFirmwareList.get(underTestingIndex);
                                                aimsBinaryFirmwareForUnderTesting.setUnderTestingDate(currDate);
                                                aimsBinaryFirmwareForUnderTesting.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_UNDER_TESTING);
                                                aimsBinaryFirmwareForUnderTesting.setLastUpdatedBy(currUser);
                                                aimsBinaryFirmwareForUnderTesting.setLastUpdatedDate(currDate);
                                            }
                                        }
                                        //change new firmwares to under testing
                                        if(newVZAppBinaryFirmwareList!=null && newVZAppBinaryFirmwareList.size()>0) {
                                            AimsVZAppBinaryFirmware newAimsBinaryFirmwareForUnderTesting = null;
                                            for(int newUnderTestingIndex =0; newUnderTestingIndex <newVZAppBinaryFirmwareList.size(); newUnderTestingIndex++) {
                                                newAimsBinaryFirmwareForUnderTesting = (AimsVZAppBinaryFirmware)newVZAppBinaryFirmwareList.get(newUnderTestingIndex);
                                                newAimsBinaryFirmwareForUnderTesting.setUnderTestingDate(currDate);
                                                newAimsBinaryFirmwareForUnderTesting.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_UNDER_TESTING);
                                            }
                                        }

                                        //set flag to send notification
                                        isAppMovedFromSubmitToUnderTesting = true;
                                    }//end initial approve
                                    if (initialApprovalAction.equals(AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_DENY[0])) {
                                        log.debug("...... moving application to Initial Denied. appsId: "+appsId);
                                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_INITIAL_DENIED_ID);
                                    }//end initial denied
                                }
                            }//end initial approval
                            //from Under Testing -> Testing Passed
                            if(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_DEVICE_TESTING, AimsSecurityManager.UPDATE)) {
                                if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.TESTING_ID.longValue())) {
                                    //if atleast one device passes test, change status to testing passed
                                    oneDeviceTestPassed = this.checkOneBaseTestPassed(vzAppZoneUpdateForm.getVZAppBaseTests());
                                    if(oneDeviceTestPassed) {
                                        log.debug("...... moving application to Test Passed. appsId: "+appsId);
                                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_TEST_PASSED_ID);
                                    }
                                }
                            }//end testing passed
                            //from OTA Testing -> OTA Test Passed
                            if(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_OTA_TESTING, AimsSecurityManager.UPDATE)) {
                                boolean oneOTATestPassed = false;
                                if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_TEST_PASSED_ID.longValue())) {
                                    //if atleast one ota test passes test, change status to testing passed
                                    oneOTATestPassed = this.checkOneOTATestPassed(vzAppZoneUpdateForm.getVZAppZoneOTATests());
                                    if(oneOTATestPassed) {
                                        log.debug("...... moving application to OTA Test Passed. appsId: "+appsId);
                                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_OTA_TEST_PASSED_ID);
                                    }
                                }
                            }//end ota Test passed

                            //from ota test passed -> production
                            if(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_MOVE_TO_PRODUCTION, AimsSecurityManager.UPDATE)) {
                                if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_OTA_TEST_PASSED_ID.longValue())) {
                                    if(vzAppMoveToProdList!=null && vzAppMoveToProdList.size()>0) {
                                        log.debug("...... moving application to Production. appsId: "+appsId);
                                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_IN_PRODUCTION_ID);
                                    }
                                }
                            }
                            //from production -> sunset
                            if(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_SUNSET, AimsSecurityManager.UPDATE)) {
                                if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_IN_PRODUCTION_ID.longValue())) {
                                    if(!StringFuncs.isNullOrEmpty(vzAppZoneUpdateForm.getMoveToSunset())
                                            && vzAppZoneUpdateForm.getMoveToSunset().equals(AimsConstants.VZAPPZONE_APP_CHECKBOX_SUNSET[0])) {
                                        log.debug("...... moving application to Sunset. appsId: "+appsId);
                                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUNSET_ID);
                                    }
                                }
                            }//end sunset
                        }
                        //end changing phases

                        //move to staging section
                        //update move to staging section fields if user has access to this section
                        if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_APPLICATION_MANAGEMENT, AimsSecurityManager.UPDATE)
                                && !isAppMovedFromSubmitToUnderTesting
                                && VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(oldStatus)) {
                            vzAppZoneApp.setIsLocked(vzAppZoneUpdateForm.getIsLocked());
                            /* commented, contentId
                                vzAppZoneApp.setContentId(vzAppZoneUpdateForm.getContentId());
                            */
                        }

                        if(isAppMovedFromSubmitToUnderTesting) {
                            //lock application when it is approved,
                            log.debug("...... Application is apprvoed, setting lock application flag to Y. appsId: "+appsId);
                            vzAppZoneApp.setIsLocked(AimsConstants.VZAPPZONE_APP_LOCK_APPLICATION[0]);
                        }

                    }//end if verizon user

                    if(Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getClonedFromId()).longValue()>0) {
                        clonedFromId = vzAppZoneUpdateForm.getClonedFromId();
                    }
                    try {
                    VZAppZoneApplicationManager.saveOrUpdateVZAppZoneApplication(aimsApp,
                                                                                    vzAppZoneApp,
                                                                                    currUser,
                                                                                    currUserType,
                                                                                    oldStatus,
                                                                                    clonedFromId,
                                                                                    vzAppZoneUpdateForm.getPresentationTempFileId(),
                                                                                    null,
                                                                                    vzAppBinary,
                                                                                    selectedBinaryId,
                                                                                    vzAppZoneUpdateForm.getBinaryFileTempFileId(),
                                                                                    vzAppZoneUpdateForm.getPreviewFileTempFileId(),
                                                                                    vzAppZoneUpdateForm.getDocumentFileTempFileId(),
                                                                                    newVZAppBinaryFirmwareList,
                                                                                    activeAimsVZAppBinaryFirmwareList,
                                                                                    vzAppZoneUpdateForm.getBaseTestResultsTempFileMap(),
                                                                                    vzAppZoneUpdateForm.getOtaTestResultsTempFileMap(),
                                                                                    journalEntriesToSave);

                    }
                    catch (AimsException ae) {
                        saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                        //forward to page1 i.e., basic info
                        vzAppZoneUpdateForm.setCurrentPage("page1");
                        forward = "page1";
                        return mapping.findForward(forward);
                    }

                    aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

                    if (processForm && currUserType.equals(AimsConstants.VZW_USERTYPE)) {

                        /********** send XML for OTA, Staging and Production    *********/


                        if(vzAppMoveToOTATestingList!=null && vzAppMoveToOTATestingList.size()>0) {

                            log.debug("....... Sending XML for OTA devices ... size: "+vzAppMoveToOTATestingList.size());

                            this.sendMPortalXML(aimsApp, vzAppZoneApp, aimsAllianceOfApplication, vzAppMoveToOTATestingList, VZAppZoneApplicationHelper.VZAPPZONE_TYPE_OTA);

                            this.sendNotificationsForXMLMoved(aimsApp, aimsAllianceOfApplication, vzAppMoveToOTATestingList,
                                    VZAppZoneApplicationHelper.VZAPPZONE_TYPE_OTA, currDate);
                        }


                        if(vzAppMoveToStagingList !=null && vzAppMoveToStagingList.size()>0) {
                            log.debug("....... Sending XML to Stage ... size: "+vzAppMoveToStagingList.size());

                            this.sendMPortalXML(aimsApp, vzAppZoneApp, aimsAllianceOfApplication, vzAppMoveToStagingList, VZAppZoneApplicationHelper.VZAPPZONE_TYPE_STAGE);

                        }//end send Stage info xml

                        if(vzAppMoveToProdList !=null && vzAppMoveToProdList.size()>0) {
                            log.debug("....... Sending XML for to Production ... size: "+vzAppMoveToProdList.size());

                            this.sendMPortalXML(aimsApp, vzAppZoneApp, aimsAllianceOfApplication, vzAppMoveToProdList, VZAppZoneApplicationHelper.VZAPPZONE_TYPE_PROD);

                            this.sendNotificationsForXMLMoved(aimsApp, aimsAllianceOfApplication, vzAppMoveToProdList,
                                    VZAppZoneApplicationHelper.VZAPPZONE_TYPE_PROD, currDate);

                        }//end send prod info xml
                        /********** End send XML for OTA, Staging and Production    *********/

                        //send Test failure notification when device test fails
                        if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(aimsApp.getAimsLifecyclePhaseId())) {
                            if(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_DEVICE_TESTING, AimsSecurityManager.UPDATE)) {
                                this.sendNotificationForBaseTestStatus(changedBaseTestStatusList,
                                                                        aimsAllianceOfApplication.getCompanyName(),
                                                                        aimsApp.getAimsAllianceId(),
                                                                        aimsApp.getAppsId(),
                                                                        aimsApp.getTitle(),
                                                                        aimsApp.getVersion());
                            }

                            if(VZAppZoneApplicationHelper.isStatusEqualOrAboveTestPassed(aimsApp.getAimsLifecyclePhaseId())) {
                                if(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_OTA_TESTING, AimsSecurityManager.UPDATE)) {
                                    this.sendNotificationForOTATestStatus(changedOTATestStatusList,
                                                                        aimsAllianceOfApplication.getCompanyName(),
                                                                        aimsApp.getAimsAllianceId(),
                                                                        aimsApp.getAppsId(),
                                                                        aimsApp.getTitle(),
                                                                        aimsApp.getVersion());
                                }
                            }
                        }
                    }//end send xml

                    //for both VZW Admin and alliance user
                    if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(aimsApp.getAimsLifecyclePhaseId())) {

                        //send notification for binary file upload
                        if(isAppMovedFromSubmitToUnderTesting
                                || (vzAppZoneUpdateForm.getVzAppFirmwareIds()!=null && vzAppZoneUpdateForm.getVzAppFirmwareIds().length>0)) {

                            List vzAppBinaryFirmwareInfoList = new ArrayList();//List of List containg BinaryFirmwareInfoVOs
                            List vzAppBinariesList = new ArrayList();//List of AimsVZAppBinaries

                            String secretKey = CommonProperties.getInstance().getProperty("vzappzone.secretKey.password");
                            String intertekPaymentURL = ConfigEnvProperties.getInstance().getProperty("vzappzone.alliance.intertek.payment.url");
                            Long appAllianceAdminUserId = aimsAllianceOfApplication.getAimsUserByAdminUserId();

                            AimsContact allianceAdminContact = null;
                            if(Utility.ZeroValueReplacement(appAllianceAdminUserId).longValue()>0) {
                                allianceAdminContact = ((AimsUser)DBHelper.getInstance().load(AimsUser.class, appAllianceAdminUserId.toString())).getAimsContact();
                            }

                            if(isAppMovedFromSubmitToUnderTesting) {
                                //send notification for all binaries-firmwares if app moved from submitted-->under testing
                                log.debug("Application moved from submitted-->under testing, sending notifications for all binary firmwares of application. appsId:  "+appsId);
                                Map vzAppBinaryFirmwareInfoMap = VZAppZoneApplicationManager.getVZAppBinaryFirmwareInfoMapWithBinaries(appsId, AimsConstants.ACTIVE_CHAR, "MR-", false);
                                vzAppBinaryFirmwareInfoList = (List)vzAppBinaryFirmwareInfoMap.get("vzAppBinaryFirmwareInfoList");
                                vzAppBinariesList = (List)vzAppBinaryFirmwareInfoMap.get("vzAppBinariesList");

                            }
                            else if(vzAppZoneUpdateForm.getVzAppFirmwareIds()!=null && vzAppZoneUpdateForm.getVzAppFirmwareIds().length>0) {
                                //send notification for new binaries-firmwares if app is in under testing
                                log.debug("binary file uploaded/selected for firmwares, when application is in undertesting or above state: appsId: "+appsId);
                                List vzAppBinaryFirmwareInfoVOList = null;
                                AimsVZAppBinaries vzAppSelectedBinary = null;
                                //find binary file
                                if(vzAppBinary!=null) {//binary uploaded
                                    vzAppSelectedBinary = vzAppBinary;
                                }
                                else if(Utility.ZeroValueReplacement(selectedBinaryId).longValue()>0) {
                                    vzAppSelectedBinary =
                                        (AimsVZAppBinaries) DBHelper.getInstance().load(AimsVZAppBinaries.class, selectedBinaryId.toString());
                                }
                                //send ignoreProductionBinaryFirmwares parameter as true, to ignore binary firmwares in production
                                vzAppBinaryFirmwareInfoVOList = VZAppZoneApplicationManager.getVZAppBinaryFirmwareInfoListByFirmwareIds(appsId,
                                                                                                        vzAppZoneUpdateForm.getVzAppFirmwareIds(),
                                                                                                        AimsConstants.ACTIVE_CHAR,
                                                                                                        "MR-", false, true);
                                vzAppBinaryFirmwareInfoList.add(vzAppBinaryFirmwareInfoVOList);
                                vzAppBinariesList.add(vzAppSelectedBinary);
                            }
                            if((vzAppBinariesList!=null && vzAppBinariesList.size()>0)
                                    && (vzAppBinaryFirmwareInfoList!=null && vzAppBinaryFirmwareInfoList.size()>0)
                                    && vzAppBinariesList.size() == vzAppBinaryFirmwareInfoList.size()) {
                                //each element of vzAppBinaryFirmwareInfoVOList is a list of VZAppBinaryFirmwareInfoVO
                                //and at element of vzAppBinariesList of same index is AimsVZAppBinaries of all firmwares in list of VZAppBinaryFirmwareInfoVO
                                for(int binaryIdx=0; binaryIdx<vzAppBinaryFirmwareInfoList.size(); binaryIdx++) {
                                    //iterate first to send notifications
                                    List vzAppBinaryFirmwareInfoVOList = (List)vzAppBinaryFirmwareInfoList.get(binaryIdx);
                                    AimsVZAppBinaries vzAppSelectedBinary = (AimsVZAppBinaries)vzAppBinariesList.get(binaryIdx);
                                    this.sendNotificationsForBinaries(aimsApp,
                                                                        aimsAllianceOfApplication,
                                                                        vzAppBinaryFirmwareInfoVOList,
                                                                        vzAppSelectedBinary,
                                                                        secretKey, intertekPaymentURL,
                                                                        currDate);
                                }
                                for(int binaryXMLIdx =0; binaryXMLIdx <vzAppBinaryFirmwareInfoList.size(); binaryXMLIdx++) {
                                    //iterate again to send xml
                                    List vzAppBinaryFirmwareInfoVOList = (List)vzAppBinaryFirmwareInfoList.get(binaryXMLIdx);
                                    AimsVZAppBinaries vzAppSelectedBinary = (AimsVZAppBinaries)vzAppBinariesList.get(binaryXMLIdx);
                                    log.debug("== send xml for binary Upload for binary: "+vzAppSelectedBinary.getBinaryId());
                                    VZAppZoneApplicationHelper.sendXmlToIntertek(aimsAllianceOfApplication, allianceAdminContact, aimsApp, vzAppBinaryFirmwareInfoVOList, vzAppSelectedBinary, false);
                                }
                            }
                        }

                    }

                    // Post update tasks
                    vzAppZoneUpdateForm.setAppsId(aimsApp.getAppsId());
                    vzAppZoneUpdateForm.setTask("edit");
                    vzAppZoneUpdateForm.setOrignalTask("edit");
                    vzAppZoneUpdateForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
                    vzAppZoneUpdateForm.setIsLocked(StringFuncs.NullValueReplacement(vzAppZoneApp.getIsLocked()));//set isLocked field
                    vzAppZoneUpdateForm.setIsAppLocked(StringFuncs.NullValueReplacement(vzAppZoneApp.getIsLocked()));//set status for lock
                    Long newStatus = aimsApp.getAimsLifecyclePhaseId();

                    //Setting the Application Status for Page Header
                    AimsLifecyclePhase aimsPhaseOfApplication =
                        (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, vzAppZoneUpdateForm.getAimsLifecyclePhaseId().toString());
                    vzAppZoneUpdateForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());

                    //Set Temp File Ids to Zero
                    vzAppZoneUpdateForm.setPresentationTempFileId(new Long(0));
                    vzAppZoneUpdateForm.setContentLandingScreenShotTempFileId(new Long(0));
                    vzAppZoneUpdateForm.setAppTitleToView(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                            StringFuncs.NullValueReplacement(aimsApp.getTitle())));

                    //rest binary file temp ids
                    this.resetFrmBinaryPageFields(vzAppZoneUpdateForm);


                    if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {
                        if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(aimsApp.getAimsLifecyclePhaseId())) {
                            List vzAppBinaryFirmwarePhaseList = VZAppZoneApplicationManager.getVZAppBinaryFirmwarePhaseInfoList(appsId, AimsConstants.ACTIVE_CHAR, "MR ", true);

                             VZAppZoneApplicationHelper.setupVZAppSections(vzAppZoneUpdateForm,
                                                                    vzAppBinaryFirmwarePhaseList,
                                                                    aimsApp, vzAppZoneApp,
                                                                    hasAccessBasicTesting, hasAccessApplicationManagement,
                                                                    hasAccessOTATesting, hasAccessMoveToStaging,
                                                                    hasAccessMoveToProd, hasAccessSunset);

                        }//end if undertesting
                    }//end if vzw user

                    //send notifications for Application Status Change
                    this.sendNotificationForApplicationStatus(oldStatus,
                                                                newStatus,
                                                                aimsApp.getAimsAllianceId(),
                                                                aimsAllianceOfApplication.getCompanyName(),
                                                                aimsApp.getAppsId(),
                                                                aimsApp.getTitle(),
                                                                aimsApp.getVersion(),
                                                                vzAppZoneApp.getInitialApprovalNotes(),
                                                                vzAppZoneApp.getOnetimeVZWRecommendedPrice());

                    saveMessages(request, this.getMessagesOnUpdate(submitType));
                    forward = vzAppZoneUpdateForm.getCurrentPage();
                }
            }
        }

        log.debug("---- start printing vairables before current page check");
        if(vzAppZoneUpdateForm!=null) {
            log.debug("task: "+vzAppZoneUpdateForm.getTask());
            log.debug("orignalTask: "+vzAppZoneUpdateForm.getOrignalTask());
            log.debug("currentPage: "+vzAppZoneUpdateForm.getCurrentPage());
            log.debug("appSubmitType: "+vzAppZoneUpdateForm.getAppSubmitType());
            log.debug("appsId: "+vzAppZoneUpdateForm.getAppsId());
        }
        else {
            log.debug("vzAppZoneUpdateForm is null");
        }
        log.debug("---- end printing vairables before current page check");

        if (StringUtils.isNotEmpty(vzAppZoneUpdateForm.getCurrentPage()) && vzAppZoneUpdateForm.getCurrentPage().equalsIgnoreCase("page3")) {
            if ((submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM)) && aimsApp!=null) {
                appsId = aimsApp.getAppsId();
            }

            vzAppZoneUpdateForm.setVzAppBinaryFirmwareTestStatusList(VZAppZoneApplicationManager.getVZAppBinaryFirmwareTestInfoList(appsId, AimsConstants.ACTIVE_CHAR, "MR-", true));

            //binaryFirmwareInfoList will contain ellipse strings, but vzAppBinariesList will contain string with original length
            Map vzAppBinaryFirmwareInfoMap = VZAppZoneApplicationManager.getVZAppBinaryFirmwareInfoMapWithBinaries(appsId, AimsConstants.ACTIVE_CHAR, "MR-", true);
            vzAppZoneUpdateForm.setAllVZAppDeviceFirmwareList(FirmwareManager.getFirmwaresList(AimsConstants.ACTIVE));
            vzAppZoneUpdateForm.setVzAppBinaryFirmwareInfoList((List)vzAppBinaryFirmwareInfoMap.get("vzAppBinaryFirmwareInfoList"));
            vzAppZoneUpdateForm.setVzAppBinaries((List)vzAppBinaryFirmwareInfoMap.get("vzAppBinariesList"));
        }

        log.debug("---- start printing vairables before forwarding");
        if(vzAppZoneUpdateForm!=null) {
            log.debug("task: "+vzAppZoneUpdateForm.getTask());
            log.debug("orignalTask: "+vzAppZoneUpdateForm.getOrignalTask());
            log.debug("currentPage: "+vzAppZoneUpdateForm.getCurrentPage());
            log.debug("appSubmitType: "+vzAppZoneUpdateForm.getAppSubmitType());
            log.debug("appsId: "+vzAppZoneUpdateForm.getAppsId());
        }
        else {
            log.debug("vzAppZoneUpdateForm is null");
        }
        log.debug("---- end printing vairables before forwarding");

        if (StringUtils.isEmpty(vzAppZoneUpdateForm.getCurrentPage())){
        	log.debug("Found CurrentPage attribute null. Sending User on Application Listing page.");
        }
        log.debug("============== end VZAppZoneApplicationUpdateAction forward: "+forward);
        return mapping.findForward(forward);
    }

    private void resetFrmBinaryPageFields(VZAppZoneApplicationUpdateForm vzAppZoneUpdateForm) {
        vzAppZoneUpdateForm.setBinaryFileTempFileId(new Long(0));
        vzAppZoneUpdateForm.setPreviewFileTempFileId(new Long(0));
        vzAppZoneUpdateForm.setDocumentFileTempFileId(new Long(0));
        vzAppZoneUpdateForm.setVzAppDeviceIds(null);
        vzAppZoneUpdateForm.setVzAppFirmwareIds(null);
        vzAppZoneUpdateForm.setBinaryVersion("");
        vzAppZoneUpdateForm.setBinaryFileSize("");
        vzAppZoneUpdateForm.setBinaryId(new Long(0));
        vzAppZoneUpdateForm.setErrorMessages(null);
    }

    /**
     * This method returns true if alliance has accepted atleast one 'active' VZAppZone Contract
     * @param aimsAllianceId
     * @param currUserType
     * @return
     * @throws HibernateException
     */
    private boolean validateAllianceContract(Long aimsAllianceId, String currUserType) throws HibernateException {
        boolean vzAppZoneContractPresent = false;
        Collection allianceContracts = null;
        try {
            allianceContracts = AllianceManager.getAllianceAcceptedActiveContractsByPlatform(aimsAllianceId, AimsConstants.VZAPPZONE_PLATFORM_ID, currUserType);
            if(allianceContracts!=null && allianceContracts.size()>0) {
                vzAppZoneContractPresent = true;
            }
        }//end try
        catch (HibernateException he) {
            System.out.println("VZAppZoneApplicationUpdateAction.validateAllianceContract: exception occured while getting alliance accepted contracts.");
            he.printStackTrace();
            throw he;
        }
        return vzAppZoneContractPresent;
    }

    /**
     * Returns messages on update
     * @param appSubmitType
     * @return
     */
    private ActionMessages getMessagesOnUpdate(String appSubmitType) {
        ActionMessages messages = new ActionMessages();
        ActionMessage message = null;

        if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            message = new ActionMessage("message.manage.app.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            message = new ActionMessage("message.manage.app.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            message = new ActionMessage("message.manage.app.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))
            message = new ActionMessage("message.manage.app.processed");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            message = new ActionMessage("message.vzAppZone.manage.app.submitted");

        messages.add(ActionMessages.GLOBAL_MESSAGE, message);

        return messages;
    }

    /**
     * returns this map
     * AimsVZAppBinaries    -   vzAppBinary
     * Long                 -   selectedBinaryId
     * List                 -   newVZAppBinaryFirmwareList
     * List                 -   firmwareInfoVOList
     * List                 -   binaryJournalEntriesList
     * this method also sets isActive Field to N in gien activeAimsVZAppBinaryFirmwareList, if firmware ids match in activeAimsVZAppBinaryFirmwareList
     * @param vzAppZoneUpdateForm
     * @param activeAimsVZAppBinaryFirmwareList
     * @param applicationStatus
     * @param currDate
     * @param currUser
     * @return
     */
    private Map populateVZAppBinariesMap(VZAppZoneApplicationUpdateForm vzAppZoneUpdateForm,
                                         List activeAimsVZAppBinaryFirmwareList,
                                         Long applicationStatus,
                                         Date currDate, String currUser) throws HibernateException {
        AimsVZAppBinaries vzAppBinary = null;
        boolean binaryUploadedOrSelected = false;
        Long selectedBinaryId = null;
        Long[] vzAppFirmwareIds = vzAppZoneUpdateForm.getVzAppFirmwareIds();

        List newVZAppBinaryFirmwareList = null;
        List firmwareInfoVOList = null;
        List binaryJournalEntriesList = null;
        Map vzAppBinariesMap = new HashMap();

        String inputBinaryFileFileName = "";
        String inputBinaryVersion = "";

        Long binaryFileSizeInBytes = null;
        Long previewFileSizeInBytes = null;
        Long documentFileSizeInBytes = null;

        if(Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getBinaryFileTempFileId()).longValue()>0) {
            inputBinaryFileFileName = vzAppZoneUpdateForm.getBinaryFileFileName();
            inputBinaryVersion = vzAppZoneUpdateForm.getBinaryVersion();
            //if binary file given
            //populate binary
            vzAppBinary = new AimsVZAppBinaries();
            vzAppBinary.setBinaryVersion(vzAppZoneUpdateForm.getBinaryVersion());
            vzAppBinary.setBinaryFileSize(vzAppZoneUpdateForm.getBinaryFileSize());
            //temporary use, this filename will not be stored in database,
            // file name will be copied from temp file, setting file name here will help sending notifications
            vzAppBinary.setBinaryFileFileName(inputBinaryFileFileName);

            //set file size
            binaryFileSizeInBytes = TempFilesManager.getTempFileSize(vzAppZoneUpdateForm.getBinaryFileTempFileId(), currUser);
            if(binaryFileSizeInBytes != null) {
                vzAppBinary.setBinaryFileSizeInBytes(binaryFileSizeInBytes);
            }
            if(Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getPreviewFileTempFileId()).longValue()>0) {
                previewFileSizeInBytes = TempFilesManager.getTempFileSize(vzAppZoneUpdateForm.getPreviewFileTempFileId(), currUser);
                if(previewFileSizeInBytes != null) {
                    vzAppBinary.setPreviewFileSizeInBytes(previewFileSizeInBytes);
                }
            }
            if(Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getDocumentFileTempFileId()).longValue()>0) {
                documentFileSizeInBytes = TempFilesManager.getTempFileSize(vzAppZoneUpdateForm.getDocumentFileTempFileId(), currUser);
                if(documentFileSizeInBytes != null) {
                    vzAppBinary.setDocumentFileSizeInBytes(documentFileSizeInBytes);
                }
            }
            vzAppBinary.setCreatedBy(currUser);
            vzAppBinary.setCreatedDate(currDate);
            vzAppBinary.setLastUpdatedBy(currUser);
            vzAppBinary.setLastUpdatedDate(currDate);

            binaryUploadedOrSelected = true;
            log.debug("VZAppZoneApplicationUpdateAction: binary file uploaded for firmwares");
        }
        else if(Utility.ZeroValueReplacement(vzAppZoneUpdateForm.getBinaryId()).longValue()>0) {
            binaryUploadedOrSelected = true;
            selectedBinaryId = vzAppZoneUpdateForm.getBinaryId();

            AimsVZAppBinaries vzAppSelectedBinary =
                (AimsVZAppBinaries) DBHelper.getInstance().load(AimsVZAppBinaries.class, selectedBinaryId.toString());
            if(vzAppSelectedBinary!=null) {
                inputBinaryFileFileName = vzAppSelectedBinary.getBinaryFileFileName();
                inputBinaryVersion = vzAppSelectedBinary.getBinaryVersion();
            }

            log.debug("VZAppZoneApplicationUpdateAction: binary id selected for firmwares");
        }
        if(binaryUploadedOrSelected) {
            AimsVZAppBinaryFirmware binaryFirmwareToInActive = null;
            //make binary firmware
            if(vzAppFirmwareIds !=null && vzAppFirmwareIds.length>0) {
                newVZAppBinaryFirmwareList = new ArrayList();
                AimsVZAppBinaryFirmware binaryFirmware = null;
                for(int binaryFirmwareIndex=0; binaryFirmwareIndex<vzAppFirmwareIds.length; binaryFirmwareIndex++) {
                    binaryFirmware = new AimsVZAppBinaryFirmware();
                    binaryFirmware.setFirmwareId(vzAppFirmwareIds[binaryFirmwareIndex]);
                    binaryFirmware.setCreatedBy(currUser);
                    binaryFirmware.setCreatedDate(currDate);
                    binaryFirmware.setLastUpdatedBy(currUser);
                    binaryFirmware.setLastUpdatedDate(currDate);

                    binaryFirmware.setIsPaid("N");
                    binaryFirmware.setIsActive(AimsConstants.ACTIVE_CHAR);
                    if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(applicationStatus)) {
                        binaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_UNDER_TESTING);
                        //application is in under testing status, set under testing date to current date
                        binaryFirmware.setUnderTestingDate(currDate);
                    }
                    else {
                        binaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_SUBMITTED);
                        binaryFirmware.setUnderTestingDate(null);
                    }
                    newVZAppBinaryFirmwareList.add(binaryFirmware);

                    //if device is test/OTA test failed, then set isActive to N, so only one active binary firmware exists for this device
                    //if device is in production, then do not change isActive Flag, so multiple active binary firmware exist for this device
                    //iterate all active binary firmwares and match firmware ids
                    if(activeAimsVZAppBinaryFirmwareList!=null && activeAimsVZAppBinaryFirmwareList.size()>0) {
                        for(int isActiveIndex=0; isActiveIndex<activeAimsVZAppBinaryFirmwareList.size(); isActiveIndex++) {
                            binaryFirmwareToInActive = (AimsVZAppBinaryFirmware)activeAimsVZAppBinaryFirmwareList.get(isActiveIndex);
                            //if match, set its isActive to N
                            if(binaryFirmwareToInActive.getFirmwareId().equals(vzAppFirmwareIds[binaryFirmwareIndex])) {
                                //if device is not in production then set isactive to N
                                if(!binaryFirmwareToInActive.getStatus().equals(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_PRODUCTION)) {
                                    binaryFirmwareToInActive.setIsActive(AimsConstants.INACTIVE_CHAR);

                                    //set ota deleted flag to Y, to send delete message of this device, when ota xml is sent to mportal
                                    if(binaryFirmwareToInActive.getStatus().equals(AimsConstants.VZAPPZONE_BINARY_STATUS_OTA_TEST_FAILED)) {
                                        binaryFirmwareToInActive.setOtaDeleted(AimsConstants.YES_CHAR);
                                    }

                                    binaryFirmwareToInActive.setLastUpdatedBy(currUser);
                                    binaryFirmwareToInActive.setLastUpdatedDate(currDate);
                                }
                                else if(binaryFirmwareToInActive.getStatus().equals(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_PRODUCTION)) {
                                    //for duplicate device, if device is in production, do not set isActive to N
                                    //but set otaDeleted to N and otaMoved to Y
                                    log.debug("----------- "+binaryFirmwareToInActive.getBinaryFirmwareId()+" setting old device in production OTA_DELETED flag to N");
                                    binaryFirmwareToInActive.setOtaDeleted(AimsConstants.NO_CHAR);

                                    log.debug("----------- "+binaryFirmwareToInActive.getBinaryFirmwareId()+" setting old device in production OTA_MOVED flag to Y");
                                    binaryFirmwareToInActive.setOtaMoved(AimsConstants.YES_CHAR);
                                    binaryFirmwareToInActive.setLastUpdatedBy(AimsConstants.RECORD_UPDATED_BY_SYSTEM);
                                    binaryFirmwareToInActive.setLastUpdatedDate(currDate);

                                }
                                //break;
                            }
                        }
                    }
                }//end for binaryFirmwareIndex
                firmwareInfoVOList = FirmwareManager.getFirmwareInfoVOListByFirmwareIds(vzAppFirmwareIds, AimsConstants.ACTIVE);
                //construct journal entry if application is approved
                if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(applicationStatus) &&
                        firmwareInfoVOList!=null && firmwareInfoVOList.size()>0) {
                    binaryJournalEntriesList = new ArrayList();
                    for(int firmwareIndex=0; firmwareIndex<firmwareInfoVOList.size(); firmwareIndex++) {
                        FirmwareInfoVO firmwareInfo = (FirmwareInfoVO)firmwareInfoVOList.get(firmwareIndex);
                        binaryJournalEntriesList.add(VZAppZoneApplicationHelper.constructJournalEntryForBinaryUpload(
                                                                inputBinaryFileFileName, inputBinaryVersion, firmwareInfo, currUser));
                    }
                }
            }
        }//end binaryUploadedOrSelected
        vzAppBinariesMap.put("vzAppBinary", vzAppBinary);
        vzAppBinariesMap.put("selectedBinaryId", selectedBinaryId);
        vzAppBinariesMap.put("newVZAppBinaryFirmwareList", newVZAppBinaryFirmwareList);
        vzAppBinariesMap.put("firmwareInfoVOList", firmwareInfoVOList);
        vzAppBinariesMap.put("binaryJournalEntriesList", binaryJournalEntriesList);
        return vzAppBinariesMap;
    }//end populateVZAppBinariesMap


    private Map populateVZAppBinaryFirmwaresSections(HttpServletRequest request,
                                                     VZAppZoneApplicationUpdateForm vzAppZoneUpdateForm,
                                                     List activeAimsVZAppBinaryFirmwareList,
                                                     Long currentAppStatus,
                                                     boolean processForm, String dateFormat,
                                                     String currUser, Date currDate) throws HibernateException {
        Map vzAppBinaryFirmwareSectionsMap = new HashMap();
        List firmwareInfoList = null;
        AimsVZAppBinaryFirmware activeBinaryFirmware = null;
        Long activeBinaryFirmwareStatus = null;

        List frmVZAppBinaryFirmwareInfoVOs = vzAppZoneUpdateForm.getVZAppBinaryFirmwareInfoVOs();
        List frmVZAppBaseTests = vzAppZoneUpdateForm.getVZAppBaseTests();
        List frmVZAppOTATests = vzAppZoneUpdateForm.getVZAppZoneOTATests();
        List frmVZAppStageInfoVOs = vzAppZoneUpdateForm.getVZAppZoneStageInfoVOs();
        List frmVZAppProdInfoVOs = vzAppZoneUpdateForm.getVZAppZoneProdInfoVOs();

        List changedBaseTestStatusList = new ArrayList();
        List changedOTATestStatusList = new ArrayList();

        List vzAppMoveToOTATestingList = new ArrayList();
        List vzAppMoveToStagingList = new ArrayList();
        List vzAppMoveToProdList = new ArrayList();

        FirmwareInfoVO firmwareInfo = null;

        VZAppBaseTestVO frmBaseTest = null;
        VZAppZoneOTATestVO frmOTATest = null;
        VZAppZoneStageInfoVO frmStageInfo = null;
        VZAppZoneProdInfoVO frmProdInfo = null;

        List firmwareIdsInProduction = new ArrayList();

        Map aimsBinaryFirmwaresInProductionMap = new HashMap(); //firmwareId, AimsVZAppBinaryFirmware

        boolean activeAimsVZAppBinaryFirmwareFound = (activeAimsVZAppBinaryFirmwareList!=null && activeAimsVZAppBinaryFirmwareList.size()>0);
        boolean binaryFirmwareInfoFound = (frmVZAppBinaryFirmwareInfoVOs!=null && frmVZAppBinaryFirmwareInfoVOs.size()>0);
        boolean baseTestsFound = (frmVZAppBaseTests!=null && frmVZAppBaseTests.size()>0);
        boolean otaTestsFound = (frmVZAppOTATests!=null && frmVZAppOTATests.size()>0);
        boolean stageInfoFound = (frmVZAppStageInfoVOs!=null && frmVZAppStageInfoVOs.size()>0);
        boolean prodInfoFound = (frmVZAppProdInfoVOs!=null && frmVZAppProdInfoVOs.size()>0);

        boolean isEqualOrAboveInitialApproval = (VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(currentAppStatus)
                                                    && !currentAppStatus.equals(AimsConstants.PHASE_INITIAL_DENIED_ID));
        boolean isEqualOrAboveTestingPassed = VZAppZoneApplicationHelper.isStatusEqualOrAboveTestPassed(currentAppStatus);
        boolean isEqualOrAboveOTATestPassed = VZAppZoneApplicationHelper.isStatusEqualOrAboveOTATestPassed(currentAppStatus);


        boolean hasAccessBaseTesting = (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_DEVICE_TESTING, AimsSecurityManager.UPDATE));
        boolean hasAccessOTATesting = (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_OTA_TESTING, AimsSecurityManager.UPDATE));
        boolean hasAccessMoveToStaging = (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_MOVE_TO_STAGING, AimsSecurityManager.UPDATE));
        boolean hasAccessMoveToProd = (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_MOVE_TO_PRODUCTION, AimsSecurityManager.UPDATE));

        List baseTestJournalEntriesList = new ArrayList();
        List otaTestJournalEntriesList = new ArrayList();
        List moveToServerJournalEntriesList = new ArrayList();

        if(activeAimsVZAppBinaryFirmwareFound && binaryFirmwareInfoFound) {
            Long[] firmwareIds = VZAppZoneApplicationHelper.getFirmwareIdsFromAimsVZAppBinaryFirmwares(activeAimsVZAppBinaryFirmwareList);
            if(firmwareIds!=null && firmwareIds.length>0) {
                //get firmware info list, this list will be used in sending notifications and journal entries for binary fimware info
                //send status as null to get all firmwares, both active and inactive,
                //firmwareInfoList = FirmwareManager.getFirmwareInfoVOListByFirmwareIds(firmwareIds, AimsConstants.ACTIVE);
                firmwareInfoList = FirmwareManager.getFirmwareInfoVOListByFirmwareIds(firmwareIds, null);

            }
            //iterate activeBinaryFirmwares and set section fields
            for(int index=0; index<activeAimsVZAppBinaryFirmwareList.size(); index++) {
                activeBinaryFirmware = (AimsVZAppBinaryFirmware)activeAimsVZAppBinaryFirmwareList.get(index);
                activeBinaryFirmwareStatus = Utility.ZeroValueReplacement(activeBinaryFirmware.getStatus());
                //get corresponding firmware info
                firmwareInfo = VZAppZoneApplicationHelper.getFirmwareInfoVOFromList(firmwareInfoList, activeBinaryFirmware.getFirmwareId());
                if(firmwareInfo!=null) {
                    //add if current item is in production
                    if(activeBinaryFirmwareStatus.equals(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_PRODUCTION)) {
                        firmwareIdsInProduction.add(activeBinaryFirmware.getFirmwareId());
                        aimsBinaryFirmwaresInProductionMap.put(activeBinaryFirmware.getFirmwareId().toString(), activeBinaryFirmware);
                    }

                    if(isEqualOrAboveInitialApproval) {
                        //2nd check i.e., size check here do nothing, but this check guarantees that number of base tests are not altered
                        if(hasAccessBaseTesting && baseTestsFound && (frmVZAppBinaryFirmwareInfoVOs.size() == frmVZAppBaseTests.size())) {
                            //if this binaryFirmware is in under testing, then change its info
                            if(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveTesting(activeBinaryFirmwareStatus)
                                    && !VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveTestPassed(activeBinaryFirmwareStatus)) {
                                String oldBaseTestStatus = StringFuncs.NullValueReplacement(activeBinaryFirmware.getBaseTestStatus());
                                String oldBaseTestedDate = StringFuncs.NullValueReplacement(Utility.convertToString(activeBinaryFirmware.getBaseTestedDate(), dateFormat));
                                String oldBaseComments = StringFuncs.NullValueReplacement(activeBinaryFirmware.getBaseComments());

                                frmBaseTest = (VZAppBaseTestVO)frmVZAppBaseTests.get(index);
                                if(!StringFuncs.isNullOrEmpty(frmBaseTest.getBaseTestedDate())) {
                                    activeBinaryFirmware.setBaseTestedDate(Utility.convertToDate(frmBaseTest.getBaseTestedDate(), dateFormat));
                                }
                                else {
                                    activeBinaryFirmware.setBaseTestedDate(null);
                                }
                                activeBinaryFirmware.setBaseTestStatus(frmBaseTest.getBaseTestStatus());
                                activeBinaryFirmware.setBaseComments(frmBaseTest.getBaseComments());
                                if(processForm) {
                                    //change status according to test status
                                    String baseTestStatus = StringFuncs.NullValueReplacement(frmBaseTest.getBaseTestStatus());
                                    String baseTestStatusValue = "";

                                    if(baseTestStatus .equals(AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0])) {
                                        activeBinaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_TEST_PASSED);
                                        baseTestStatusValue = AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[1];
                                    }
                                    else if(baseTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[0])) {
                                        activeBinaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_TEST_FAILED);
                                        baseTestStatusValue = AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[1];
                                    }
                                    if(Utility.ZeroValueReplacement(activeBinaryFirmware.getStatus()).longValue()>0 &&
                                            !activeBinaryFirmwareStatus.equals(activeBinaryFirmware.getStatus())) {
                                        baseTestJournalEntriesList.add(VZAppZoneApplicationHelper.constructJournalEntryForUpdatedTest(firmwareInfo, baseTestStatus, currUser));
                                        log.debug("----------- "+activeBinaryFirmware.getBinaryFirmwareId()+" status changed to "+activeBinaryFirmware.getStatus());
                                        try {
                                            if(!StringFuncs.isNullOrEmpty(baseTestStatusValue)) {
                                                VZAppBinaryFirmwarePhaseInfoVO changedBinaryFirmware = new VZAppBinaryFirmwarePhaseInfoVO();
                                                BeanUtils.copyProperties(changedBinaryFirmware, activeBinaryFirmware);
                                                BeanUtils.copyProperties(changedBinaryFirmware, firmwareInfo);

                                                //set baseTested date according to date format
                                                changedBinaryFirmware.setBaseTestedDate(Utility.convertToString(activeBinaryFirmware.getBaseTestedDate(), dateFormat));

                                                changedBinaryFirmware.setBinaryFirmwareStatusValue(baseTestStatusValue);
                                                changedBinaryFirmware.setBinaryFirmwareStatus(activeBinaryFirmware.getStatus());
                                                changedBaseTestStatusList.add(changedBinaryFirmware);
                                            }
                                        }
                                        catch(Exception e) {
                                            System.out.println("Exception occured while making changedBinaryFirmware in base test");
                                            e.printStackTrace();
                                        }
                                    }

                                }//end if process form
                                //check if values changed
                                if( !(oldBaseTestedDate.equals(StringFuncs.NullValueReplacement(frmBaseTest.getBaseTestedDate())) )
                                    || !(oldBaseTestStatus.equals(StringFuncs.NullValueReplacement(frmBaseTest.getBaseTestStatus())) )
                                    || !(oldBaseComments.equals(StringFuncs.NullValueReplacement(frmBaseTest.getBaseComments())) )
                                    || (Utility.ZeroValueReplacement(frmBaseTest.getBaseResultsFileTempFileId()).longValue()>0)) {
                                    activeBinaryFirmware.setLastUpdatedBy(currUser);
                                    activeBinaryFirmware.setLastUpdatedDate(currDate);
                                }

                            }
                        }//end if base test

                        if(isEqualOrAboveTestingPassed) {
                            if(hasAccessOTATesting && otaTestsFound && (frmVZAppBinaryFirmwareInfoVOs.size() == frmVZAppOTATests.size())) {
                                //if this binaryFirmware is in OTA stage
                                if(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveTestPassed(activeBinaryFirmwareStatus)
                                        && !VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(activeBinaryFirmwareStatus)) {
                                    String oldOTATestStatus = StringFuncs.NullValueReplacement(activeBinaryFirmware.getOtaTestStatus());
                                    String oldOTATestedDate = StringFuncs.NullValueReplacement(Utility.convertToString(activeBinaryFirmware.getOtaTestedDate(), dateFormat));
                                    String oldOTAComments = StringFuncs.NullValueReplacement(activeBinaryFirmware.getOtaComments());

                                    frmOTATest = (VZAppZoneOTATestVO)frmVZAppOTATests.get(index);
                                    if(!StringFuncs.isNullOrEmpty(frmOTATest.getOtaTestedDate())) {
                                        activeBinaryFirmware.setOtaTestedDate(Utility.convertToDate(frmOTATest.getOtaTestedDate(), dateFormat));
                                    }
                                    else {
                                        //activeBinaryFirmware.setOtaTestedDate(Utility.convertToDate(frmOTATest.getOtaTestedDate(), dateFormat));
                                        activeBinaryFirmware.setOtaTestedDate(null);//set ota tested date to null, if not set from form
                                    }

                                    activeBinaryFirmware.setOtaTestStatus(frmOTATest.getOtaTestStatus());
                                    activeBinaryFirmware.setOtaComments(frmOTATest.getOtaComments());
                                    if(processForm) {
                                        //change status according to test status
                                        String otaTestStatus = StringFuncs.NullValueReplacement(frmOTATest.getOtaTestStatus());
                                        String otaTestStatusValue = "";
                                        if(otaTestStatus .equals(AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[0])) {
                                            activeBinaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_OTA_TEST_PASSED);
                                            otaTestStatusValue = AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[1];
                                        }
                                        else if(otaTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[0])) {
                                            activeBinaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_OTA_TEST_FAILED);
                                            otaTestStatusValue = AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[1];

                                            /******************* no need to set ota deleted to N
                                                //set otaMoved of old device in production to Y (Device upgrade)
                                                if(firmwareIdsInProduction.contains(activeBinaryFirmware.getFirmwareId())) {
                                                    AimsVZAppBinaryFirmware aimsBinaryFirmwareInProduction = (AimsVZAppBinaryFirmware)aimsBinaryFirmwaresInProductionMap.get(activeBinaryFirmware.getFirmwareId().toString());
                                                    if(aimsBinaryFirmwareInProduction!=null
                                                            && aimsBinaryFirmwareInProduction.getBinaryFirmwareId().longValue()!=activeBinaryFirmware.getBinaryFirmwareId().longValue()) {

                                                        log.debug("----------- "+aimsBinaryFirmwareInProduction.getBinaryFirmwareId()+"\t "+firmwareInfo.getPhoneModel()+"("+firmwareInfo.getMrNumber()+") setting OTA_DELETED flag to N");
                                                        aimsBinaryFirmwareInProduction.setOtaDeleted(AimsConstants.NO_CHAR);

                                                        log.debug("----------- "+aimsBinaryFirmwareInProduction.getBinaryFirmwareId()+"\t "+firmwareInfo.getPhoneModel()+"("+firmwareInfo.getMrNumber()+") setting OTA_MOVED flag to Y");
                                                        aimsBinaryFirmwareInProduction.setOtaMoved(AimsConstants.YES_CHAR);
                                                        aimsBinaryFirmwareInProduction.setLastUpdatedBy(AimsConstants.RECORD_UPDATED_BY_SYSTEM);
                                                        aimsBinaryFirmwareInProduction.setLastUpdatedDate(currDate);

                                                    }
                                                }//end
                                             **********/
                                        }

                                        if(Utility.ZeroValueReplacement(activeBinaryFirmware.getStatus()).longValue()>0 &&
                                                !activeBinaryFirmwareStatus.equals(activeBinaryFirmware.getStatus())) {
                                            otaTestJournalEntriesList.add(VZAppZoneApplicationHelper.constructJournalEntryForUpdatedOTATest(firmwareInfo, otaTestStatus, currUser));
                                            log.debug("----------- "+activeBinaryFirmware.getBinaryFirmwareId()+" ota status changed to "+activeBinaryFirmware.getStatus());

                                            try {
                                                if(!StringFuncs.isNullOrEmpty(otaTestStatusValue)) {
                                                    VZAppBinaryFirmwarePhaseInfoVO changedBinaryFirmwareForOTA = new VZAppBinaryFirmwarePhaseInfoVO();
                                                    BeanUtils.copyProperties(changedBinaryFirmwareForOTA, activeBinaryFirmware);
                                                    BeanUtils.copyProperties(changedBinaryFirmwareForOTA, firmwareInfo);

                                                    //set baseTested date according to date format
                                                    changedBinaryFirmwareForOTA.setOtaTestedDate(Utility.convertToString(activeBinaryFirmware.getOtaTestedDate(), dateFormat));

                                                    changedBinaryFirmwareForOTA.setBinaryFirmwareStatusValue(otaTestStatusValue);
                                                    changedBinaryFirmwareForOTA.setBinaryFirmwareStatus(activeBinaryFirmware.getStatus());
                                                    changedOTATestStatusList.add(changedBinaryFirmwareForOTA);
                                                }
                                            }
                                            catch(Exception e) {
                                                System.out.println("Exception occured while making changedBinaryFirmwareForOTA in ota test");
                                                e.printStackTrace();
                                            }
                                        }

                                        //make list of tests for  'move to OTA'
                                        if(StringFuncs.NullValueReplacement(frmOTATest.getMoveToOTATesting()).equals(AimsConstants.YES_CHAR)
                                                && (!VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(activeBinaryFirmware.getStatus())
                                                        && !activeBinaryFirmware.getStatus().equals(AimsConstants.VZAPPZONE_BINARY_STATUS_OTA_TEST_FAILED))) {
                                            try {
                                                VZAppBinaryFirmwarePhaseInfoVO changedBinaryFirmwareForMoveToOTA = new VZAppBinaryFirmwarePhaseInfoVO();
                                                BeanUtils.copyProperties(changedBinaryFirmwareForMoveToOTA, activeBinaryFirmware);
                                                BeanUtils.copyProperties(changedBinaryFirmwareForMoveToOTA, firmwareInfo);
                                                vzAppMoveToOTATestingList.add(changedBinaryFirmwareForMoveToOTA);
                                            }
                                            catch(Exception e) {
                                                System.out.println("Exception occured while making changedBinaryFirmwareForMoveToOTA in ota test");
                                                e.printStackTrace();
                                            }

                                            //if binaryFirmware is ota test passed or failed, dont change status to ota testing
                                            /*if(!VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(activeBinaryFirmware.getStatus())
                                                    && !activeBinaryFirmware.getStatus().equals(AimsConstants.VZAPPZONE_BINARY_STATUS_OTA_TEST_FAILED)) {
                                                activeBinaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_OTA_TESTING);
                                            }*/

                                            activeBinaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_OTA_TESTING);

                                            activeBinaryFirmware.setOtaMoved(AimsConstants.YES_CHAR);
                                            activeBinaryFirmware.setOtaMovedDate(currDate);
                                            activeBinaryFirmware.setLastUpdatedBy(currUser);
                                            activeBinaryFirmware.setLastUpdatedDate(currDate);
                                            moveToServerJournalEntriesList.add(VZAppZoneApplicationHelper.constructJournalEntryForMoveToOTATesting(firmwareInfo, currUser));
                                            log.debug("----------- "+activeBinaryFirmware.getBinaryFirmwareId()+" moved to OTA Testing");

                                            //set otaDeletedFlag if duplicate device
                                            //for duplicate device, set when new device is moved to OTA, set otaDeleted of oldDevice to Y,
                                            //by doing this, old device will be included in mPortal XML, with delete action
                                            if(firmwareIdsInProduction.contains(activeBinaryFirmware.getFirmwareId())) {
                                                AimsVZAppBinaryFirmware aimsBinaryFirmwareInProduction = (AimsVZAppBinaryFirmware)aimsBinaryFirmwaresInProductionMap.get(activeBinaryFirmware.getFirmwareId().toString());
                                                if(aimsBinaryFirmwareInProduction!=null
                                                        && aimsBinaryFirmwareInProduction.getBinaryFirmwareId().longValue()!=activeBinaryFirmware.getBinaryFirmwareId().longValue()
                                                        && !StringFuncs.NullValueReplacement(aimsBinaryFirmwareInProduction.getOtaDeleted()).equals(AimsConstants.YES_CHAR)) {
                                                    log.debug("----------- "+aimsBinaryFirmwareInProduction.getBinaryFirmwareId()+"\t "+firmwareInfo.getPhoneModel()+"("+firmwareInfo.getMrNumber()+") setting OTA_DELETED flag to Y");
                                                    aimsBinaryFirmwareInProduction.setOtaDeleted(AimsConstants.YES_CHAR);
                                                    aimsBinaryFirmwareInProduction.setLastUpdatedBy(AimsConstants.RECORD_UPDATED_BY_SYSTEM);
                                                    aimsBinaryFirmwareInProduction.setLastUpdatedDate(currDate);
                                                }
                                            }

                                        }//end move to ota
                                    }//end if process form
                                    //check if values changed
                                    if( !(oldOTATestedDate.equals(StringFuncs.NullValueReplacement(frmOTATest.getOtaTestedDate())) )
                                        || !(oldOTATestStatus.equals(StringFuncs.NullValueReplacement(frmOTATest.getOtaTestStatus())) )
                                        || !(oldOTAComments.equals(StringFuncs.NullValueReplacement(frmOTATest.getOtaComments())) )
                                        || (Utility.ZeroValueReplacement(frmOTATest.getOtaResultsFileTempFileId()).longValue()>0)) {
                                        activeBinaryFirmware.setLastUpdatedBy(currUser);
                                        activeBinaryFirmware.setLastUpdatedDate(currDate);
                                    }

                                }
                            }//end if ota tests

                            if(isEqualOrAboveOTATestPassed) {
                                if(hasAccessMoveToStaging && stageInfoFound && (frmVZAppBinaryFirmwareInfoVOs.size() == frmVZAppStageInfoVOs.size())) {
                                    //if this binaryFirmware is in staging
                                    if(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(activeBinaryFirmwareStatus)) {
                                        frmStageInfo = (VZAppZoneStageInfoVO)frmVZAppStageInfoVOs.get(index);
                                        if(processForm) {
                                            //make list of tests for  'move to staging'
                                            if(StringFuncs.NullValueReplacement(frmStageInfo.getMoveToStaging()).equals(AimsConstants.YES_CHAR)) {
                                                try {
                                                    VZAppBinaryFirmwarePhaseInfoVO changedBinaryFirmwareForMoveToStage = new VZAppBinaryFirmwarePhaseInfoVO();
                                                    BeanUtils.copyProperties(changedBinaryFirmwareForMoveToStage, activeBinaryFirmware);
                                                    BeanUtils.copyProperties(changedBinaryFirmwareForMoveToStage, firmwareInfo);
                                                    vzAppMoveToStagingList.add(changedBinaryFirmwareForMoveToStage);
                                                }
                                                catch(Exception e) {
                                                    System.out.println("Exception occured while making changedBinaryFirmwareForMoveStage");
                                                    e.printStackTrace();
                                                }

                                                //no need to set in staging status
                                                //activeBinaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_STAGING);
                                                activeBinaryFirmware.setStageMoved(AimsConstants.YES_CHAR);
                                                activeBinaryFirmware.setStageMovedDate(currDate);
                                                activeBinaryFirmware.setLastUpdatedBy(currUser);
                                                activeBinaryFirmware.setLastUpdatedDate(currDate);
                                                moveToServerJournalEntriesList.add(VZAppZoneApplicationHelper.constructJournalEntryForMovedToStaging(firmwareInfo, currUser));
                                                log.debug("----------- "+activeBinaryFirmware.getBinaryFirmwareId()+" moved to Staging");

                                                //set stageDeletedFlag if duplicate device
                                                //for duplicate device, set when new device is moved to Stage, set stageDeleted of oldDevice to Y,
                                                //by doing this, old device will be included in mPortal XML, with delete action
                                                if(firmwareIdsInProduction.contains(activeBinaryFirmware.getFirmwareId())) {
                                                    AimsVZAppBinaryFirmware aimsBinaryFirmwareInProduction = (AimsVZAppBinaryFirmware)aimsBinaryFirmwaresInProductionMap.get(activeBinaryFirmware.getFirmwareId().toString());
                                                    if(aimsBinaryFirmwareInProduction!=null
                                                            && aimsBinaryFirmwareInProduction.getBinaryFirmwareId().longValue()!=activeBinaryFirmware.getBinaryFirmwareId().longValue()
                                                            && !StringFuncs.NullValueReplacement(aimsBinaryFirmwareInProduction.getStageDeleted()).equals(AimsConstants.YES_CHAR)) {
                                                        log.debug("----------- "+aimsBinaryFirmwareInProduction.getBinaryFirmwareId()+"\t "+firmwareInfo.getPhoneModel()+"("+firmwareInfo.getMrNumber()+") setting STAGE_DELETED flag to Y");
                                                        aimsBinaryFirmwareInProduction.setStageDeleted(AimsConstants.YES_CHAR);
                                                        aimsBinaryFirmwareInProduction.setLastUpdatedBy(AimsConstants.RECORD_UPDATED_BY_SYSTEM);
                                                        aimsBinaryFirmwareInProduction.setLastUpdatedDate(currDate);
                                                    }
                                                }
                                            }//end move to stage
                                        }//end if process form
                                    }
                                }//end if stage info

                                if(hasAccessMoveToProd && prodInfoFound && (frmVZAppBinaryFirmwareInfoVOs.size() == frmVZAppProdInfoVOs.size())) {
                                    //if this binaryFirmware is in staging
                                    if(VZAppZoneApplicationHelper.isBinaryStatusEqualOrAboveOTATestPassed(activeBinaryFirmwareStatus)) {
                                        frmProdInfo = (VZAppZoneProdInfoVO)frmVZAppProdInfoVOs.get(index);
                                        if(processForm) {
                                            //make list of tests for  'move to Prod'
                                            if(StringFuncs.NullValueReplacement(frmProdInfo.getMoveToProd()).equals(AimsConstants.YES_CHAR)) {
                                                try {
                                                    VZAppBinaryFirmwarePhaseInfoVO changedBinaryFirmwareForMoveToProd = new VZAppBinaryFirmwarePhaseInfoVO();
                                                    BeanUtils.copyProperties(changedBinaryFirmwareForMoveToProd, activeBinaryFirmware);
                                                    BeanUtils.copyProperties(changedBinaryFirmwareForMoveToProd, firmwareInfo);
                                                    vzAppMoveToProdList.add(changedBinaryFirmwareForMoveToProd);
                                                }
                                                catch(Exception e) {
                                                    System.out.println("Exception occured while making changedBinaryFirmwareForMoveToProd");
                                                    e.printStackTrace();
                                                }

                                                //todo instead of adding binaryfirmwareid in moveto list, make an object on binaryfirmwareinfovo and add it to send xml info
                                                //vzAppMoveToProdList.add(activeBinaryFirmware.getBinaryFirmwareId());
                                                activeBinaryFirmware.setStatus(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_PRODUCTION);
                                                activeBinaryFirmware.setProdMoved(AimsConstants.YES_CHAR);
                                                activeBinaryFirmware.setProdMovedDate(currDate);
                                                activeBinaryFirmware.setLastUpdatedBy(currUser);
                                                activeBinaryFirmware.setLastUpdatedDate(currDate);
                                                moveToServerJournalEntriesList.add(VZAppZoneApplicationHelper.constructJournalEntryForMovedToProduction(firmwareInfo, currUser));
                                                log.debug("----------- "+activeBinaryFirmware.getBinaryFirmwareId()+" moved to Production");

                                                //set prodDeletedFlag if duplicate device
                                                //for duplicate device, set when new device is moved to Stage, set prodDeleted of oldDevice to Y,
                                                //by doing this, old device will be included in mPortal XML, with delete action
                                                if(firmwareIdsInProduction.contains(activeBinaryFirmware.getFirmwareId())) {
                                                    AimsVZAppBinaryFirmware aimsBinaryFirmwareInProduction = (AimsVZAppBinaryFirmware)aimsBinaryFirmwaresInProductionMap.get(activeBinaryFirmware.getFirmwareId().toString());
                                                    if(aimsBinaryFirmwareInProduction!=null
                                                            && aimsBinaryFirmwareInProduction.getBinaryFirmwareId().longValue()!=activeBinaryFirmware.getBinaryFirmwareId().longValue()
                                                            && !StringFuncs.NullValueReplacement(aimsBinaryFirmwareInProduction.getProdDeleted()).equals(AimsConstants.YES_CHAR)) {
                                                        log.debug("----------- "+aimsBinaryFirmwareInProduction.getBinaryFirmwareId()+"\t "+firmwareInfo.getPhoneModel()+"("+firmwareInfo.getMrNumber()+") setting PROD_DELETED flag to Y, and making it inactive");
                                                        aimsBinaryFirmwareInProduction.setProdDeleted(AimsConstants.YES_CHAR);

                                                        //set inActive flag of old device to Y, as new device has completed its cycle and moved to production
                                                        //after this only one device (new device) will be active
                                                        log.debug("----------- setting "+aimsBinaryFirmwareInProduction.getBinaryFirmwareId()+"\t "+firmwareInfo.getPhoneModel()+"("+firmwareInfo.getMrNumber()+") isActive to N");
                                                        aimsBinaryFirmwareInProduction.setIsActive(AimsConstants.NO_CHAR);

                                                        aimsBinaryFirmwareInProduction.setLastUpdatedBy(AimsConstants.RECORD_UPDATED_BY_SYSTEM);
                                                        aimsBinaryFirmwareInProduction.setLastUpdatedDate(currDate);
                                                    }
                                                }

                                            }//end move to prod
                                        }//end if process form
                                    }
                                }//end if Prod info
                            }//end isEqualOrAboveOTATestPassed
                        }//end isEqualOrAboveTestingPassed
                    }//end isEqualOrAboveIntialApproval
                }//end if firmwareInfo
            }//end for index
        }
        vzAppBinaryFirmwareSectionsMap.put("changedBaseTestStatusList", changedBaseTestStatusList);
        vzAppBinaryFirmwareSectionsMap.put("changedOTATestStatusList", changedOTATestStatusList);
        vzAppBinaryFirmwareSectionsMap.put("vzAppMoveToOTATestingList", vzAppMoveToOTATestingList);
        vzAppBinaryFirmwareSectionsMap.put("vzAppMoveToStagingList", vzAppMoveToStagingList);
        vzAppBinaryFirmwareSectionsMap.put("vzAppMoveToProdList", vzAppMoveToProdList);
        vzAppBinaryFirmwareSectionsMap.put("baseTestJournalEntriesList", baseTestJournalEntriesList);
        vzAppBinaryFirmwareSectionsMap.put("otaTestJournalEntriesList", otaTestJournalEntriesList);
        vzAppBinaryFirmwareSectionsMap.put("moveToServerJournalEntriesList", moveToServerJournalEntriesList);
        return vzAppBinaryFirmwareSectionsMap;
    }

    /**
     *
     * @param frmVZAppZoneBaseTests
     * @return
     */
    private boolean checkOneBaseTestPassed(List frmVZAppZoneBaseTests) {
        VZAppBaseTestVO vzAppBaseTest = null;
        boolean oneDevicePassed = false;
        if(frmVZAppZoneBaseTests!=null && frmVZAppZoneBaseTests.size()>0) {
            for(int devicePhaseIndex=0; devicePhaseIndex<frmVZAppZoneBaseTests.size(); devicePhaseIndex++) {
                vzAppBaseTest = (VZAppBaseTestVO) frmVZAppZoneBaseTests.get(devicePhaseIndex);
                if(vzAppBaseTest != null) {
                    if(StringFuncs.NullValueReplacement(vzAppBaseTest.getBaseTestStatus()).equals(AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0])) {
                        oneDevicePassed = true;
                        break;
                    }
                }
            }//end for
        }
        return oneDevicePassed;
    }

    private boolean checkOneOTATestPassed(List frmVZAppZoneOTATestsList) {
        VZAppZoneOTATestVO vzAppOTATest = null;
        boolean oneOTATestPassed = false;
        if(frmVZAppZoneOTATestsList!=null && frmVZAppZoneOTATestsList.size()>0) {
            for(int otaTestIndex =0; otaTestIndex <frmVZAppZoneOTATestsList.size(); otaTestIndex++) {
                vzAppOTATest = (VZAppZoneOTATestVO) frmVZAppZoneOTATestsList.get(otaTestIndex);
                if(vzAppOTATest != null) {
                    if(StringFuncs.NullValueReplacement(vzAppOTATest.getOtaTestStatus()).equals(AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[0])) {
                        oneOTATestPassed = true;
                        break;
                    }
                }
            }//end for
        }
        return oneOTATestPassed;
    }//end checkOneOTATestPassed

    private void sendNotificationForBaseTestStatus(List changedBaseTestStatusList,
                                                     String appAllianceName,
                                                     Long appAllianceId,
                                                     Long appsId,
                                                     String appTitle,
                                                     String appVersion) {
        VZAppBinaryFirmwarePhaseInfoVO binaryFirmwarePhaseInfo = null;

        String newTestStatus = "";
        String deviceName = "";
        AimsEventLite aimsEvent = null;
        if(changedBaseTestStatusList !=null && changedBaseTestStatusList.size()>0) {
            for(int baseTestIndex =0; baseTestIndex <changedBaseTestStatusList.size(); baseTestIndex++) {
                binaryFirmwarePhaseInfo = (VZAppBinaryFirmwarePhaseInfoVO) changedBaseTestStatusList.get(baseTestIndex);
                if(binaryFirmwarePhaseInfo != null) {
                    newTestStatus = binaryFirmwarePhaseInfo.getBaseTestStatus();
                    deviceName = this.generateDeviceNameWithFirmwareInfo(binaryFirmwarePhaseInfo.getPhoneModel(), binaryFirmwarePhaseInfo.getFirmwareName(), binaryFirmwarePhaseInfo.getMrNumber());

                    if(newTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[0])) {
                        log.debug("===== Test failed for Device: "+deviceName+"\t Application Title: "+appTitle+"\t Raising event for Device Test Failure");
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_TEST_FAILURE);
                    }
                    else if(newTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0])) {
                        log.debug("===== Test Passed for Device: "+deviceName+"\t Application Title: "+appTitle+"\t Raising event for Device Test Passed");
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_DEVICE_TEST_PASSED);
                    }
                    if (aimsEvent != null) {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, appAllianceId.toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, appsId.toString());

                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, StringFuncs.NullValueReplacement(appAllianceName));
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_TESTED_DATE, StringFuncs.NullValueReplacement(binaryFirmwarePhaseInfo.getBaseTestedDate()));
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, StringFuncs.NullValueReplacement(appTitle));
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, StringFuncs.NullValueReplacement(appVersion));
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DEVICE_NAME, deviceName);
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMMENTS, StringFuncs.NullValueReplacement(binaryFirmwarePhaseInfo.getBaseComments()));
                        aimsEvent.raiseEvent(aimsEventObject);
                    }//end event
                }
            }//end for
        }
    }//end sendNotificationForBaseTestStatus

    private void sendNotificationForOTATestStatus(List changedOTATestStatusList,
                                                     String appAllianceName,
                                                     Long appAllianceId,
                                                     Long appsId,
                                                     String appTitle,
                                                     String appVersion) {
        VZAppBinaryFirmwarePhaseInfoVO binaryFirmwarePhaseInfo = null;

        String newTestStatus = "";
        String deviceName = "";
        AimsEventLite aimsEvent = null;
        if(changedOTATestStatusList !=null && changedOTATestStatusList.size()>0) {
            for(int otaPhaseIndex =0; otaPhaseIndex <changedOTATestStatusList.size(); otaPhaseIndex++) {
                binaryFirmwarePhaseInfo = (VZAppBinaryFirmwarePhaseInfoVO) changedOTATestStatusList.get(otaPhaseIndex);
                if(binaryFirmwarePhaseInfo != null) {
                    newTestStatus = binaryFirmwarePhaseInfo.getOtaTestStatus();
                    deviceName = this.generateDeviceNameWithFirmwareInfo(binaryFirmwarePhaseInfo.getPhoneModel(), binaryFirmwarePhaseInfo.getFirmwareName(), binaryFirmwarePhaseInfo.getMrNumber());

                    if(newTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[0])) {
                        log.debug("===== OTA Test failed for Device: "+deviceName+"\t Application Title: "+appTitle+"\t Raising event for Device Test Failure");
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_DEVICE_OTA_TEST_FAILED);
                    }
                    else if(newTestStatus.equals(AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[0])) {
                        log.debug("===== OTA Test Passed for Device: "+deviceName+"\t Application Title: "+appTitle+"\t Raising event for Device Test Passed");
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_DEVICE_OTA_TEST_PASSED);
                    }
                    if (aimsEvent != null) {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, appAllianceId.toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, appsId.toString());

                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, StringFuncs.NullValueReplacement(appAllianceName));
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_TESTED_DATE, StringFuncs.NullValueReplacement(binaryFirmwarePhaseInfo.getOtaTestedDate()));
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, StringFuncs.NullValueReplacement(appTitle));
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, StringFuncs.NullValueReplacement(appVersion));
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DEVICE_NAME, deviceName);
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMMENTS, StringFuncs.NullValueReplacement(binaryFirmwarePhaseInfo.getOtaComments()));
                        aimsEvent.raiseEvent(aimsEventObject);
                    }//end event
                }
            }//end for
        }
    }//end sendNotificationForOTATestStatus

    /**
     * send notification whenever status of application is changed
     * @param oldStatus
     * @param newStatus
     * @param appAllianceId
     * @param appAllianceName
     * @param appsId
     * @param appTitle
     * @param appVersion
     * @param initialApprovalNotes
     * @param price
     */
    private void sendNotificationForApplicationStatus(Long oldStatus,
                                                      Long newStatus,
                                                      Long appAllianceId,
                                                      String appAllianceName,
                                                      Long appsId,
                                                      String appTitle,
                                                      String appVersion,
                                                      String initialApprovalNotes,
                                                      String price) {
        AimsEventLite aimsEvent = null;
        boolean initialApprovalOrDenied = false;

        boolean applicationMovedToUnderTesting = false;

        oldStatus = Utility.ZeroValueReplacement(oldStatus);
        newStatus = Utility.ZeroValueReplacement(newStatus);

        if((oldStatus.longValue()>0 && newStatus.longValue()>0)
                || (oldStatus.longValue()==0 && (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue()))) {
            //if status is different
            if(oldStatus.longValue() != newStatus.longValue()) {
                if (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue()) {
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_APP_STATUS_SUBMITTED);
                    log.debug("===== Application Status Changed to submitted. Sending notification for "+appTitle);
                }
                else if (newStatus.longValue() == AimsConstants.PHASE_INITIAL_DENIED_ID.longValue()) {
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_APP_STATUS_INITIAL_APPROVAL_DENIED);
                    log.debug("===== Application Status Changed to Initial Denied. Sending notification for "+appTitle);
                    initialApprovalOrDenied = true;
                }
                else if (newStatus.longValue() == AimsConstants.TESTING_ID.longValue()) {
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_APP_STATUS_INITIAL_APPROVAL_GRANTED);
                    log.debug("===== Application Status Changed to Under Testing (Initial Approval). Sending notification for "+appTitle);
                    initialApprovalOrDenied = true;
                    applicationMovedToUnderTesting = true;
                }
                else if (newStatus.longValue() == AimsConstants.PHASE_TEST_PASSED_ID.longValue()) {
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_APP_STATUS_TEST_PASSED);
                    log.debug("===== Application Status Changed to Test Passed. Sending notification for "+appTitle);
                }
                else if (newStatus.longValue() == AimsConstants.PHASE_OTA_TEST_PASSED_ID.longValue()) {
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_APP_STATUS_OTA_TEST_PASSED);
                    log.debug("===== Application Status Changed to OTA Test Passed. Sending notification for "+appTitle);
                }
                else if (newStatus.longValue() == AimsConstants.PHASE_IN_PRODUCTION_ID.longValue()) {
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_APP_STATUS_IN_PRODUCTION);
                    log.debug("===== Application Status Changed to Production. Sending notification for "+appTitle);
                }
                else if (newStatus.longValue() == AimsConstants.SUNSET_ID.longValue()) {
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_APP_STATUS_SUNSET);
                    log.debug("===== Application Status Changed to Sunset. Sending notification for "+appTitle);
                }

                if (aimsEvent != null) {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, appAllianceId.toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, appsId.toString());

                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, appAllianceName);
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, appTitle);
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, appVersion);

                    //set price place holder for undertesting notification
                    if(applicationMovedToUnderTesting) {
                        //if price given then send price, otherwise send FREE as price value 
                        if(StringFuncs.isNullOrEmpty(price)) {
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_PRICE, "FREE");
                        }
                        else {
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_PRICE, price);
                        }

                    }

                    //set initial approval notes as comments for initial approval or denied
                    if(initialApprovalOrDenied) {
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMMENTS,
                                StringFuncs.NullValueReplacement(initialApprovalNotes));
                    }

                    aimsEvent.raiseEvent(aimsEventObject);
                }

            }
        }
    }//end sendNotificationForApplicationStatus


    private void sendNotificationsForBinaries(AimsApp aimsApp,
                                              AimsAllianc aimsAllianceOfApplication,
                                              List vzAppBinaryFirmwareInfoVOList,
                                              AimsVZAppBinaries aimsVZAppSelectedBinary,
                                              String secretKey, String intertekPaymentURL,
                                              Date currDate) {

        AimsEventLite aimsEvent = null;

        String key = "";

        if(vzAppBinaryFirmwareInfoVOList !=null && vzAppBinaryFirmwareInfoVOList.size()>0 && aimsVZAppSelectedBinary!=null) {
            log.debug("--------- Sending notification start --------- size: "+vzAppBinaryFirmwareInfoVOList.size());

            aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_DEVICE_BINARY_FILE_UPLOADED);
            //if event found send notifications
            if(aimsEvent != null) {
                AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();

                //generate intertek payment url
                 try
                {
                        key = MiscUtils.getBase64Digest(aimsAllianceOfApplication.getVendorId().toString(), aimsAllianceOfApplication.getAllianceId().toString(), secretKey);
                        key = URLEncoder.encode(key,"UTF-8");
                }
                catch (Exception exKey)
                {
                        key = "ERROR";
                }

                intertekPaymentURL = intertekPaymentURL+"?key="+key+"&vendor_id="+aimsAllianceOfApplication.getVendorId();
                log.debug("sendNotificationsForBinaries: Intertek Payment URL= "+intertekPaymentURL);

                aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsAllianceOfApplication.getAllianceId().toString());
                aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAllianceOfApplication.getVendorId());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ID, aimsAllianceOfApplication.getAllianceId());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ADDRESS, StringFuncs.NullValueReplacement(aimsAllianceOfApplication.getStreetAddress1()));

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APPLICATION_ID, aimsApp.getAppsId());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, aimsApp.getVersion());

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DEVICE_UUID,
                        VZAppZoneApplicationHelper.getFormattedDeviceUUIDs(vzAppBinaryFirmwareInfoVOList));

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DEVICE_NAME,
                        VZAppZoneApplicationHelper.getFormattedDeviceMRNumbers(vzAppBinaryFirmwareInfoVOList));

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FIRMWARE_NAME, "");
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MAINTENANCE_RELEASE_NUMBER, "");

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_BINARY_ID, StringFuncs.NullValueReplacement(aimsVZAppSelectedBinary.getBinaryId()));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FILE_NAME, StringFuncs.NullValueReplacement(aimsVZAppSelectedBinary.getBinaryFileFileName()));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FILE_VERSION, StringFuncs.NullValueReplacement(aimsVZAppSelectedBinary.getBinaryVersion()));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, currDate);
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_INTERTEK_PAYMENT_URL, StringFuncs.NullValueReplacement(intertekPaymentURL));

                aimsEvent.raiseEvent(aimsEventObject);


            }//end for binaryFirmware
        }//end if list
        else {
            log.debug("VZAppZoneApplicationUpdateAction.sendNotificationsForBinaries: ERROR in sending notification for binary upload, either aimsVZAppSelectedBinary or vzAppBinaryFirmwareInfoVOList is null");
        }
        log.debug("--------- end sending binary notification ---------");
    }//end sendNotificationsForBinaries

    private void sendNotificationsForXMLMoved(AimsApp aimsApp,
                                              AimsAllianc aimsAllianceOfApplication,
                                              List vzAppMoveToServerList,
                                              String xmlType,
                                              Date currDate) throws HibernateException {
        Map binaryFirmwareInfoMapForXML = null;
        Long[] binaryFirmwareIdsArr = VZAppZoneApplicationHelper.getBinaryFirmwareIdsFromBinaryFirmwarePhasesList(vzAppMoveToServerList);
        if(binaryFirmwareIdsArr!=null && binaryFirmwareIdsArr.length>0) {

            binaryFirmwareInfoMapForXML = VZAppZoneApplicationManager.getVZAppBinaryFirmwareInfoMapByIds(
                                                                                    aimsApp.getAppsId(),
                                                                                    AimsConstants.ACTIVE_CHAR,
                                                                                    binaryFirmwareIdsArr,
                                                                                    null,
                                                                                    "MR",
                                                                                    false);
            if(binaryFirmwareInfoMapForXML!=null) {
                List vzAppBinaryFirmwareInfoList = (List)binaryFirmwareInfoMapForXML.get("vzAppBinaryFirmwareInfoList");
                List binariesList = (List)binaryFirmwareInfoMapForXML.get("vzAppBinariesList");

                if(vzAppBinaryFirmwareInfoList!=null && binariesList!=null && binariesList.size()==vzAppBinaryFirmwareInfoList.size() && binariesList.size()>0) {
                    for(int binaryIdx=0; binaryIdx<binariesList.size(); binaryIdx++) {
                        AimsVZAppBinaries aimsVZAppBinary = (AimsVZAppBinaries)binariesList.get(binaryIdx);
                        List vzAppBinaryFirmwareInfoVOList = (List)vzAppBinaryFirmwareInfoList.get(binaryIdx);
                        this.sendNotificationsForXMLMoved(aimsApp, aimsAllianceOfApplication, vzAppBinaryFirmwareInfoVOList, aimsVZAppBinary, xmlType, currDate);
                    }
                }
                else {
                    log.debug("ERROR in VZAppZoneApplicationHelper.sendNotificationsForXMLMoved: size mismatch b/w vzAppBinaryFirmwareInfoList and vzAppBinariesList ");
                }
            }
            else {
                log.debug("ERROR in VZAppZoneApplicationUpdateAction.sendNotificationsForXMLMoved: binaryFirmwareInfoMapForXML not found against binaryIds, xmlType: "+xmlType);
            }
        }
        else {
            log.debug("ERROR in VZAppZoneApplicationUpdateAction.sendNotificationsForXMLMoved: binaryFirmwareInfoMapForXML not found against binaryIds, while sending notification for Prod");
        }
    }//end sendNotificationsForXMLMoved

    private void sendNotificationsForXMLMoved(AimsApp aimsApp,
                                              AimsAllianc aimsAllianceOfApplication,
                                              List vzAppBinaryFirmwareInfoVOList,
                                              AimsVZAppBinaries aimsVZAppBinary,
                                              String xmlType,
                                              Date currDate)  {

        AimsEventLite aimsEvent = null;

        if(vzAppBinaryFirmwareInfoVOList !=null && vzAppBinaryFirmwareInfoVOList.size()>0 && aimsVZAppBinary!=null) {
            log.debug("--------- Sending notification for XML moved start --------- size: "+vzAppBinaryFirmwareInfoVOList.size()+"\t xmlType: "+xmlType);

            if(xmlType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_OTA)) {
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_DEVICE_IN_OTA_TESTING);
            }
            else if(xmlType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_PROD)) {
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_DEVICE_IN_PRODUCTION);
            }

            //if event found send notifications
            if(aimsEvent != null) {
                AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();

                aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsAllianceOfApplication.getAllianceId().toString());
                aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAllianceOfApplication.getVendorId());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ID, aimsAllianceOfApplication.getAllianceId());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ADDRESS, StringFuncs.NullValueReplacement(aimsAllianceOfApplication.getStreetAddress1()));

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APPLICATION_ID, aimsApp.getAppsId());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, aimsApp.getVersion());

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DEVICE_UUID,
                        VZAppZoneApplicationHelper.getFormattedDeviceUUIDs(vzAppBinaryFirmwareInfoVOList));

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DEVICE_NAME,
                        VZAppZoneApplicationHelper.getFormattedDeviceMRNumbers(vzAppBinaryFirmwareInfoVOList));

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FIRMWARE_NAME, "");
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MAINTENANCE_RELEASE_NUMBER, "");

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_BINARY_ID, StringFuncs.NullValueReplacement(aimsVZAppBinary.getBinaryId()));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FILE_NAME, StringFuncs.NullValueReplacement(aimsVZAppBinary.getBinaryFileFileName()));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FILE_VERSION, StringFuncs.NullValueReplacement(aimsVZAppBinary.getBinaryVersion()));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, currDate);

                aimsEvent.raiseEvent(aimsEventObject);


            }//end for binaryFirmware
        }//end if list
        else {
            log.debug("VZAppZoneApplicationUpdateAction.sendNotificationsForXMLMoved: ERROR in sending notification for xml moved, either aimsVZAppSelectedBinary or vzAppBinaryFirmwareInfoVOList is null");
        }

        log.debug("--------- end sending notification for XML moved  ---------");
    }//end sendNotificationsForXML

    private void sendMPortalXML(AimsApp aimsApp, AimsVZAppZoneApp vzAppZoneApp, AimsAllianc aimsAllianceOfApplication, List vzAppMoveToSeverList, String xmlType) throws Exception {
        Long[] binaryIdsArr = VZAppZoneApplicationHelper.getBinaryIdsArrFromBinaryFirmwareInfos(vzAppMoveToSeverList);
        VZAppZoneApplicationHelper.sendMPortalXML(aimsApp, vzAppZoneApp, aimsAllianceOfApplication, binaryIdsArr, xmlType);
    }//end sendMPortalXML

    private String generateDeviceNameWithFirmwareInfo(String phoneModel, String firmwareName, String mrNumber) {
        return phoneModel+" ("+firmwareName+") ("+mrNumber+")";
    }
}
