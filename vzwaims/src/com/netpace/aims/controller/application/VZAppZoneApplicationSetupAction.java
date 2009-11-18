package com.netpace.aims.controller.application;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppSubCategory;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.application.AimsVZAppZoneApp;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class takes care of action for display of update form of VZAppZone Application.
 *
 * @struts.action path="/vzAppZoneApplicationSetup"
 *                name="VZAppZoneApplicationUpdateForm"
 *                scope="request"
 *                input="/applicationsViewDelete.do"
                  validate="false"
 * @struts.action-forward name="update" path="/application/vzAppZoneApplicationUpdate.jsp"
 * @struts.action-forward name="viewList" path="/applicationsViewDelete.do"
 * @struts.action-forward name="viewVZAppZoneApp" path="/application/vzAppZoneApplicationView.jsp"
 * @struts.action-forward name="binariesView" path="/application/vzAppZoneApplicationBinariesView.jsp"
 * @struts.action-forward name="processingInfoView" path="/application/vzAppZoneProcessingInfoView.jsp"
 * @struts.action-forward name="vzAppZoneJournalView" path="/application/vzAppZoneJournalView.jsp"
 * @author Sajjad Raza
 */
public class VZAppZoneApplicationSetupAction extends BaseAction {
    private static Logger log = Logger.getLogger(VZAppZoneApplicationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String forward = "viewList";
        String taskname = StringFuncs.NulltoZeroStringReplacement(request.getParameter("task"));
        String pageToView = StringFuncs.NullValueReplacement(request.getParameter("viewPageToView"));
        VZAppZoneApplicationUpdateForm vzAppZoneApplicationForm = null;
        vzAppZoneApplicationForm = (VZAppZoneApplicationUpdateForm) form;

        String dateFormat = this.getResources(request).getMessage("date.format");
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();

        Long appsId = null;

        AimsApp aimsApp = null;
        AimsVZAppZoneApp vzAppZoneApp = null;
        HashMap vzAppZoneAppTable = null;

        Collection journalEntries = null;

        //CHECK Platform ACCESS
        if (!(ApplicationHelper.checkPlatformAccess(request, taskname, AimsConstants.VZAPPZONE_PLATFORM_ID)))
            throw new AimsSecurityException();
        //END OF CHECK ACCESS

        //Getting Application Information
        if (!(taskname.equals("create")))
        {
            appsId = new Long(StringFuncs.NulltoZeroStringReplacement(request.getParameter("appsId")));
            try
            {
                //vzAppZoneAppTable = VZAppZoneApplicationManager.getVZAppZoneApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
                vzAppZoneAppTable = VZAppZoneApplicationManager.getVZAppZoneApp(appsId, currentUserAllianceId);
                //get journal entries
                journalEntries = AimsApplicationsManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);

            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }

            aimsApp = (AimsApp) vzAppZoneAppTable.get("AimsApp");
            vzAppZoneApp = (AimsVZAppZoneApp) vzAppZoneAppTable.get("AimsVZAppZoneApp");
        }

        //delete application
        if (taskname.equalsIgnoreCase("delete")) {
            if (!(ApplicationHelper.checkDeleteAccess(currUserType, aimsApp.getAimsLifecyclePhaseId()))) {
                throw new AimsSecurityException();
            }
            else {
                try {
                    AimsApplicationsManager.deleteApp(new Long(request.getParameter("appsId")), currUser);
                    //TODO set delete message
                }
                catch (AimsException ae) {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                }
                //return mapping.getInputForward();
                return mapping.findForward("viewList");
            }
        }//end delete application

        //used to load alliance contacts, get alliance id to load contacts of that alliance
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)) {
            vzAppZoneApplicationForm.setAppOwnerAllianceId(currentUserAllianceId);
        }
        else {
            vzAppZoneApplicationForm.setAppOwnerAllianceId(aimsApp.getAimsAllianceId());
        }
        vzAppZoneApplicationForm.setAllContacts(AimsApplicationsManager.getContacts(vzAppZoneApplicationForm.getAppOwnerAllianceId()));

        if (taskname.equalsIgnoreCase("clone")) {
            vzAppZoneApplicationForm.setTask("create");
        }
        else {
            vzAppZoneApplicationForm.setTask(taskname);
        }

        vzAppZoneApplicationForm.setCurrentPage("page1");
        vzAppZoneApplicationForm.setOrignalTask(vzAppZoneApplicationForm.getTask());

        //set temp file ids
        vzAppZoneApplicationForm.setContentLandingScreenShotTempFileId(new Long(0));
        vzAppZoneApplicationForm.setPresentationTempFileId(new Long(0));

        if(taskname.equals("create")) {
            vzAppZoneApplicationForm.setAppsId(new Long(0));
            vzAppZoneApplicationForm.setIfPrRelease("N");
            vzAppZoneApplicationForm.setOneTimeBilling("N");
            vzAppZoneApplicationForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
        }
        else if(taskname.equals("edit") || taskname.equals("clone") || taskname.equals("view")) {

            if(taskname.equals("clone")) {
                vzAppZoneApplicationForm.setAppTitle(null);
                vzAppZoneApplicationForm.setAppVersion(null);
                vzAppZoneApplicationForm.setAppsId(new Long(0));
                vzAppZoneApplicationForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
                vzAppZoneApplicationForm.setClonedFromId(aimsApp.getAppsId());
                vzAppZoneApplicationForm.setClonedFromTitle(aimsApp.getTitle());
            }//end if clone
            else {
                vzAppZoneApplicationForm.setAppsId(aimsApp.getAppsId());
                vzAppZoneApplicationForm.setAppTitle(aimsApp.getTitle());
                vzAppZoneApplicationForm.setAppTitleToView(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                         StringFuncs.NullValueReplacement(aimsApp.getTitle())));
                vzAppZoneApplicationForm.setAppVersion(aimsApp.getVersion());
                //set phaseId
                vzAppZoneApplicationForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
                vzAppZoneApplicationForm.setIsLocked(StringFuncs.NullValueReplacement(vzAppZoneApp.getIsLocked()));//set isLocked field
                vzAppZoneApplicationForm.setIsAppLocked(StringFuncs.NullValueReplacement(vzAppZoneApp.getIsLocked()));//set status for locked
            }
            //set form values
            vzAppZoneApplicationForm.setAppCatalogName(vzAppZoneApp.getAppCatalogName());
            vzAppZoneApplicationForm.setAppProductCode(vzAppZoneApp.getAppProductCode());
            vzAppZoneApplicationForm.setAppShortDesc(aimsApp.getShortDesc());
            vzAppZoneApplicationForm.setAppLongDesc(aimsApp.getLongDesc());
            vzAppZoneApplicationForm.setGoLiveDate(Utility.convertToString(vzAppZoneApp.getAppGoLiveDate(), dateFormat));
            vzAppZoneApplicationForm.setExpirationDate(Utility.convertToString(vzAppZoneApp.getAppExpirationDate(), dateFormat));
            vzAppZoneApplicationForm.setContentRating(vzAppZoneApp.getContentRating());
            vzAppZoneApplicationForm.setSubscriptionBillingMonthly(vzAppZoneApp.getSubsBilling());
            vzAppZoneApplicationForm.setSubscriptionBillingPricePoint(vzAppZoneApp.getSubsBillingPricepoint());
            vzAppZoneApplicationForm.setOneTimeBilling(vzAppZoneApp.getOnetimeBilling());
            vzAppZoneApplicationForm.setOneTimeBillingPricePoint(vzAppZoneApp.getOnetimeBillingPricepoint());
            vzAppZoneApplicationForm.setIfPrRelease(aimsApp.getIfPrRelease());

            vzAppZoneApplicationForm.setContentLandingScreenShotFileName(vzAppZoneApp.getAppLandingPageFileName());
            vzAppZoneApplicationForm.setPresentationFileName(vzAppZoneApp.getAppPresentationFileName());

            //addtional info section
            vzAppZoneApplicationForm.setCommunityChatUgc(vzAppZoneApp.getCommunityChatUgc());
            vzAppZoneApplicationForm.setContentSweekstakes(vzAppZoneApp.getContentSweekstakes());

            //set category1 and subcategory1
            if(vzAppZoneApp.getSubCategory1() != null && vzAppZoneApp.getSubCategory1().longValue()!=0) {
                AimsAppSubCategory subCategory1 =
                    (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, vzAppZoneApp.getSubCategory1().toString());
                if(subCategory1 != null) {
                    vzAppZoneApplicationForm.setCategoryId1(subCategory1.getAimsAppCategoryId());
                    vzAppZoneApplicationForm.setSubCategoryId1(subCategory1.getSubCategoryId());
                }
            }//end category

            vzAppZoneApplicationForm.setAimsContactId(aimsApp.getAimsContactId());

            //Binaryes
            //todo set vzappzone binaries here if any
            //end clone

            vzAppZoneApplicationForm.setJournalCombinedText(ApplicationHelper.getFormattedJournalEntries(journalEntries));

            //Set VZW Specific Information
            if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {

                //if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_INITIAL_APPROVAL)) {
                    vzAppZoneApplicationForm.setSubsVZWRecommendedPrice(vzAppZoneApp.getSubVZWRecommendedPrice());
                    vzAppZoneApplicationForm.setSubsVendorSplitPercentage(vzAppZoneApp.getSubsVendorSplitPercent());
                    vzAppZoneApplicationForm.setSubsVendorProductDisplay(vzAppZoneApp.getSubsVendorProdDisplay());
                    vzAppZoneApplicationForm.setOneTimeVZWRecommendedPrice(vzAppZoneApp.getOnetimeVZWRecommendedPrice());
                    vzAppZoneApplicationForm.setOneTimeVendorSplitPercentage(vzAppZoneApp.getOnetimeVendorSplitPercent());
                    vzAppZoneApplicationForm.setOneTimeVendorProductDisplay(vzAppZoneApp.getOnetimeVendorProdDisplay());
                    //set category2 and subcategory2
                    if(Utility.ZeroValueReplacement(vzAppZoneApp.getSubCategory2()).longValue()>0) {
                        AimsAppSubCategory subCategory2 =
                            (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, vzAppZoneApp.getSubCategory2().toString());
                        if(subCategory2 != null) {
                            vzAppZoneApplicationForm.setCategoryId2(subCategory2.getAimsAppCategoryId());
                            vzAppZoneApplicationForm.setSubCategoryId2(subCategory2.getSubCategoryId());
                        }
                    }//end category
                    //set category3 and subcategory3
                    if(Utility.ZeroValueReplacement(vzAppZoneApp.getSubCategory3()).longValue()>0) {
                        AimsAppSubCategory subCategory3 =
                            (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, vzAppZoneApp.getSubCategory3().toString());
                        if(subCategory3 != null) {
                            vzAppZoneApplicationForm.setCategoryId3(subCategory3.getAimsAppCategoryId());
                            vzAppZoneApplicationForm.setSubCategoryId3(subCategory3.getSubCategoryId());
                        }
                    }//end category
                    vzAppZoneApplicationForm.setScmVendorId(StringFuncs.NullValueReplacement(vzAppZoneApp.getScmVendorId()));
                    vzAppZoneApplicationForm.setVzwLiveDate(Utility.convertToString(vzAppZoneApp.getVzwLiveDate(), dateFormat));
                    vzAppZoneApplicationForm.setTaxCategoryCodeId(vzAppZoneApp.getTaxCategoryCodeId());
                    vzAppZoneApplicationForm.setContentType(vzAppZoneApp.getContentType());
                    vzAppZoneApplicationForm.setInitialApprovalNotes(vzAppZoneApp.getInitialApprovalNotes());
                    vzAppZoneApplicationForm.setInitialApprovalAction(vzAppZoneApp.getInitialApprovalAction());
                //}


                if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(aimsApp.getAimsLifecyclePhaseId())) {
                    List vzAppBinaryFirmwarePhaseList = VZAppZoneApplicationManager.getVZAppBinaryFirmwarePhaseInfoList(appsId, AimsConstants.ACTIVE_CHAR, "MR ", true);


                    boolean hasAccessBasicTesting = ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_DEVICE_TESTING);
                    boolean hasAccessApplicationManagement = ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_APPLICATION_MANAGEMENT);
                    boolean hasAccessOTATesting = ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_OTA_TESTING);
                    boolean hasAccessMoveToStaging = ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_MOVE_TO_STAGING);
                    boolean hasAccessMoveToProd = ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_MOVE_TO_PRODUCTION);
                    boolean hasAccessSunset = ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_SUNSET);

                    VZAppZoneApplicationHelper.setupVZAppSections(vzAppZoneApplicationForm,
                                                                    vzAppBinaryFirmwarePhaseList,
                                                                    aimsApp, vzAppZoneApp,
                                                                    hasAccessBasicTesting, hasAccessApplicationManagement,
                                                                    hasAccessOTATesting, hasAccessMoveToStaging,
                                                                    hasAccessMoveToProd, hasAccessSunset);

                    if(VZAppZoneApplicationHelper.isStatusEqualOrAboveTestPassed(aimsApp.getAimsLifecyclePhaseId())) {
                        if (hasAccessApplicationManagement) {
                            vzAppZoneApplicationForm.setContentId(vzAppZoneApp.getContentId());
                        }
                    }

                    //sunset, if application is moved to sunset, then set sunset flag
                    if(VZAppZoneApplicationHelper.isStatusEqualSunset(aimsApp.getAimsLifecyclePhaseId())) {
                        if(hasAccessSunset) {
                            vzAppZoneApplicationForm.setMoveToSunset(AimsConstants.VZAPPZONE_APP_CHECKBOX_SUNSET[0]);
                        }
                    }
                }//end if undertesting
            }//end if VZW user
        }//end if edit, clone, view

        //Setting the Alliance(Content Provider) Name for Page Header
        AimsAllianc aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, vzAppZoneApplicationForm.getAppOwnerAllianceId().toString());
        vzAppZoneApplicationForm.setAllianceName(aimsAllianceOfApplication.getCompanyName());
        vzAppZoneApplicationForm.setVendorId(aimsAllianceOfApplication.getVendorId());

        if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {
            vzAppZoneApplicationForm.setMportalAllianceName(aimsAllianceOfApplication.getMportalAllianceName());
        }

        //Setting the Application Status for Page Header
        if ((vzAppZoneApplicationForm.getAppsId() != null) && (vzAppZoneApplicationForm.getAppsId().longValue() != 0)) {
            AimsLifecyclePhase aimsPhaseOfApplication =
                (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, vzAppZoneApplicationForm.getAimsLifecyclePhaseId().toString());
            vzAppZoneApplicationForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
        }
        else {
            vzAppZoneApplicationForm.setApplicationStatus("NEW");
        }

        //set forward
        if(taskname.equals("create") || taskname.equals("edit") || taskname.equals("clone")) {
            forward = "update";
        }
        else if(taskname.equals("view")) {
            if(pageToView.equals("")) {
                forward = "viewVZAppZoneApp";
            }
            else if(pageToView.equals("binariesView")) {
                vzAppZoneApplicationForm.setVzAppBinaryFirmwareTestStatusList(VZAppZoneApplicationManager.getVZAppBinaryFirmwareTestInfoList(appsId, AimsConstants.ACTIVE_CHAR, "MR-", true));
                //binaryFirmwareInfoList will contain ellipse strings, but vzAppBinariesList will contain string with original length
                //Map vzAppBinaryFirmwareInfoMap = VZAppZoneApplicationManager.getVZAppBinaryFirmwareInfoMap(appsId, AimsConstants.ACTIVE_CHAR);
                Map vzAppBinaryFirmwareInfoMap = VZAppZoneApplicationManager.getVZAppBinaryFirmwareInfoMapWithBinaries(appsId, AimsConstants.ACTIVE_CHAR, "MR-", true);
                vzAppZoneApplicationForm.setVzAppBinaryFirmwareInfoList((List)vzAppBinaryFirmwareInfoMap.get("vzAppBinaryFirmwareInfoList"));
                forward = "binariesView";
            }
            else if(pageToView.equals("processingInfo")) {
                forward = "processingInfoView";
            }
            else if(pageToView.equals("journal")) {
                forward = "vzAppZoneJournalView";
                if (!AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_JOURNAL_ENTRIES, AimsSecurityManager.SELECT)) {
                    throw new AimsSecurityException();
                }
            }
        }

        return mapping.findForward(forward);
    }

}
