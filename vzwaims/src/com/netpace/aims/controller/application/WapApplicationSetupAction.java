package com.netpace.aims.controller.application;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppPhase;
import com.netpace.aims.model.application.AimsAppSubCategory;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.application.AimsWapApp;
import com.netpace.aims.model.application.AimsWapLicenseType;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for display of update form of BREW Application.
 *
 * @struts.action path="/wapApplicationSetup"
 *                name="WapApplicationUpdateForm"
 *                scope="request"
 *                input="/applicationsViewDelete.do"
                  validate="false"
 * @struts.action-forward name="update" path="/application/wapApplicationUpdate.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="wapView" path="/application/wapApplicationView.jsp"
 * @struts.action-forward name="processInfoView" path="/application/wapAppProcessInfoView.jsp"
 * @struts.action-forward name="page4View" path="/application/wapJournalView.jsp"
 * @author Fawad Sikandar
 */
public class WapApplicationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(WapApplicationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String forward = "view";
        String taskname = "";
        String viewPageToView = "";
        Object o_param;
        AimsApp aimsApp = null;
        AimsWapApp aimsWapApp = null;
        HashMap appWap = null;
        Collection journalEntries = null;

        o_param = request.getParameter("task");
        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        o_param = request.getParameter("viewPageToView");
        if (o_param != null)
            viewPageToView = request.getParameter("viewPageToView");

        //On Hold Functionality
        if (currUserType.equals(AimsConstants.VZW_USERTYPE))
        {
            if (taskname.equalsIgnoreCase("onHold"))
            {
                try
                {
                    AimsApplicationsManager.updateOnHold(new Long(request.getParameter("appsId")), request.getParameter("onHold"), currUser);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    throw ex;
                }
                return mapping.getInputForward();
            }
        }

        //Getting Application Information And Journal Information
        if (!(taskname.equalsIgnoreCase("create")))
        {
            try
            {
                appWap = WapApplicationManager.getWapApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
                journalEntries = WapApplicationManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }

            aimsApp = (AimsApp) appWap.get("AimsApp");
            aimsWapApp = (AimsWapApp) appWap.get("AimsWapApp");
        }

        //CHECK ACCESS
        if (!(ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_WAP_APPS)))
            throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("edit"))
            if (!(WapApplicationHelper.checkEditAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
                throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("delete"))
        {
            if (!(WapApplicationHelper.checkDeleteAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
                throw new AimsSecurityException();
            else
            {
                try
                {
                    AimsApplicationsManager.deleteApp(new Long(request.getParameter("appsId")), currUser);
                }
                catch (AimsException ae)
                {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                }
                return mapping.getInputForward();
            }
        }
        //END OF CHECK ACCESS

        //Get Form
        WapApplicationUpdateForm wapAppUpdForm = (WapApplicationUpdateForm) form;

        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            wapAppUpdForm.setAppOwnerAllianceId(currentUserAllianceId);
        else
            wapAppUpdForm.setAppOwnerAllianceId(aimsApp.getAimsAllianceId());

        wapAppUpdForm.setAllContacts(AimsApplicationsManager.getContacts(wapAppUpdForm.getAppOwnerAllianceId()));

        if (taskname.equalsIgnoreCase("clone"))
            wapAppUpdForm.setTask("create");
        else
            wapAppUpdForm.setTask(taskname);

        wapAppUpdForm.setCurrentPage("page1");
        wapAppUpdForm.setOrignalTask(wapAppUpdForm.getTask());

        //Set Temp File Ids to Zero
        wapAppUpdForm.setProductLogoTempFileId(new Long(0));
        wapAppUpdForm.setProductIconTempFileId(new Long(0));
        wapAppUpdForm.setScreenJpegTempFileId(new Long(0));
        wapAppUpdForm.setUserGuideTempFileId(new Long(0));
        wapAppUpdForm.setFaqDocTempFileId(new Long(0));
        wapAppUpdForm.setPresentationTempFileId(new Long(0));
        wapAppUpdForm.setAppMediumLargeImageTempFileId(new Long(0));
        wapAppUpdForm.setAppQVGAPotraitImageTempFileId(new Long(0));
        wapAppUpdForm.setAppQVGALandscapeImageTempFileId(new Long(0));

        if (taskname.equalsIgnoreCase("create"))
        {
            wapAppUpdForm.setAppsId(new Long(0));
            wapAppUpdForm.setIfPrRelease("N");
            wapAppUpdForm.setWapVersion(AimsConstants.WAP_APP_VERSION_WAP_2_0[0]);
            wapAppUpdForm.setContentType(AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0]);
            wapAppUpdForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
            forward = "update";
        }
        else if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view")))
        {
            //Setting the Forwards
            if (taskname.equalsIgnoreCase("view"))
            {
                if (viewPageToView.equals(""))
                    forward = "wapView";
                else if (viewPageToView.equals("processingInfo"))
                    forward = "processInfoView";
                else if (viewPageToView.equals("journal"))
                    forward = "page4View";
            }
            else if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")))
                forward = "update";

            //End of Setting the Forwards

            if (taskname.equalsIgnoreCase("clone"))
            {
                aimsApp.setVersion(null);
                wapAppUpdForm.setTitle(null);
                wapAppUpdForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
                wapAppUpdForm.setClonedFromId(aimsApp.getAppsId());
                wapAppUpdForm.setClonedFromTitle(aimsApp.getTitle());
            }
            else
            {
                wapAppUpdForm.setTitle(aimsApp.getTitle());
                wapAppUpdForm.setAimsLifecyclePhaseId(WapApplicationHelper.getFilteredApplicationStatus(aimsApp.getAimsLifecyclePhaseId(), currUserType));
                wapAppUpdForm.setClonedFromId(aimsWapApp.getClonedFromId());
                wapAppUpdForm.setClonedFromTitle(aimsWapApp.getClonedFromTitle());
                wapAppUpdForm.setSubmittedDate(Utility.convertToString(aimsApp.getSubmittedDate(), dateFormat));
            }
            CommonProperties props=CommonProperties.getInstance();
            String dateStr=props.getProperty("wap.validationDate.forWAPUnderscore");
            Date validationDateForWAPUnderScore=Utility.convertToDate(dateStr, dateFormat);
            session.setAttribute(AimsConstants.VALIDATION_DATE_FOR_WAP_UNDERSCORE, validationDateForWAPUnderScore);
            
            wapAppUpdForm.setAppsId(aimsApp.getAppsId());
            wapAppUpdForm.setLongProductName(aimsWapApp.getLongProductName());
            wapAppUpdForm.setContentType(aimsWapApp.getContentType());
            wapAppUpdForm.setShortDesc(aimsApp.getShortDesc());
            wapAppUpdForm.setLongDesc(aimsApp.getLongDesc());
            wapAppUpdForm.setIfPrRelease(aimsApp.getIfPrRelease());            
            wapAppUpdForm.setDescContentOffering(aimsWapApp.getDescContentOffering());
            wapAppUpdForm.setWapVersion(aimsWapApp.getWapVersion());
            wapAppUpdForm.setDemoUrl(aimsWapApp.getDemoUrl());
            wapAppUpdForm.setTestUrl(aimsWapApp.getTestUrl());
            wapAppUpdForm.setProductionUrl(aimsWapApp.getProductionUrl());
            wapAppUpdForm.setWebsiteUrl(aimsWapApp.getWebsiteUrl());
            wapAppUpdForm.setLaunchDate(Utility.convertToString(aimsWapApp.getLaunchDate(), dateFormat));
            wapAppUpdForm.setTestEffectiveDate(Utility.convertToString(aimsWapApp.getTestEffectiveDate(), dateFormat));
            wapAppUpdForm.setVzwRetailPrice(aimsWapApp.getVzwRetailPrice());
            wapAppUpdForm.setVendorProductCode(aimsWapApp.getVendorProductCode());
            wapAppUpdForm.setRolledBackToPendingDcr(aimsWapApp.getRolledBackToPendingDcr());
            wapAppUpdForm.setVendorProductDisplay(aimsWapApp.getVendorProductDisplay());
            wapAppUpdForm.setVersion(aimsApp.getVersion());
            wapAppUpdForm.setAimsContactId(aimsApp.getAimsContactId());

            //set network usage of application
                wapAppUpdForm.setNetworkUsage(aimsApp.getNetworkUsage());
                //get AimsAppNetworkUrls
                wapAppUpdForm.setApplicationURLs(AimsApplicationsManager.getAppNetworkUrlValues(aimsApp.getAppsId()));
                //export urls if not create or clone
                if(!taskname.equalsIgnoreCase("create") && !taskname.equalsIgnoreCase("clone"))
                {
                    //check access for EXPORT_CONTENT_FILTER_URLS
                    request.setAttribute("exportUrlAllowed",
                        new Boolean((ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.EXPORT_CONTENT_FILTER_URLS))) );
                }
            //end set network usage of application

            
            if (aimsWapApp.getSubCategoryId1() != null)
            {
                AimsAppSubCategory subCategory =
                    (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, aimsWapApp.getSubCategoryId1().toString());
                wapAppUpdForm.setCategoryId1(subCategory.getAimsAppCategoryId());
                wapAppUpdForm.setSubCategoryId1(subCategory.getSubCategoryId());
            }


            if (aimsWapApp.getLicenseTypes() != null)
            {
                AimsWapLicenseType aimsWapLicenseType = null;
                Vector licenseTypeIds = new Vector();
                for (Iterator it = aimsWapApp.getLicenseTypes().iterator(); it.hasNext();)
                {
                    aimsWapLicenseType = (AimsWapLicenseType) it.next();
                    licenseTypeIds.add(aimsWapLicenseType.getLicenseTypeId().toString());
                }
                wapAppUpdForm.setListSelectedLicenseTypes(StringFuncs.ConvertListToStringArray(licenseTypeIds));
            }

            //Set File Names
            wapAppUpdForm.setProductLogoFileName(aimsWapApp.getProductLogoFileName());
            wapAppUpdForm.setProductIconFileName(aimsWapApp.getProductIconFileName());
            wapAppUpdForm.setScreenJpegFileName(aimsApp.getScreenJpegFileName());
            wapAppUpdForm.setUserGuideFileName(aimsApp.getUserGuideFileName());
            wapAppUpdForm.setFaqDocFileName(aimsApp.getFaqDocFileName());
            wapAppUpdForm.setPresentationFileName(aimsWapApp.getPresentationFileName());
            wapAppUpdForm.setAppMediumLargeImageFileName(aimsWapApp.getAppMediumLargeImageFileName());
            wapAppUpdForm.setAppQVGAPotraitImageFileName(aimsWapApp.getAppQVGAPotraitImageFileName());
            wapAppUpdForm.setAppQVGALandscapeImageFileName(aimsWapApp.getAppQVGALandscapeImageFileName());


            wapAppUpdForm.setJournalCombinedText(WapApplicationHelper.getFormattedJournalEntries(journalEntries));

            //Set VZW Specific Information
            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_APPROVAL))
                {
                    if (aimsWapApp.getSubCategoryId2() != null)
                    {
                        AimsAppSubCategory subCategory =
                            (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, aimsWapApp.getSubCategoryId2().toString());
                        wapAppUpdForm.setCategoryId2(subCategory.getAimsAppCategoryId());
                        wapAppUpdForm.setSubCategoryId2(subCategory.getSubCategoryId());
                    }
                    if (aimsWapApp.getSubCategoryId3() != null)
                    {
                        AimsAppSubCategory subCategory =
                            (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, aimsWapApp.getSubCategoryId3().toString());
                        wapAppUpdForm.setCategoryId3(subCategory.getAimsAppCategoryId());
                        wapAppUpdForm.setSubCategoryId3(subCategory.getSubCategoryId());
                    }
                    wapAppUpdForm.setLinkOrder1(aimsWapApp.getLinkOrder1());
                    wapAppUpdForm.setLinkOrder2(aimsWapApp.getLinkOrder2());
                    wapAppUpdForm.setLinkOrder3(aimsWapApp.getLinkOrder3());
                    wapAppUpdForm.setContentType(aimsWapApp.getContentType());
                    wapAppUpdForm.setInitialApprovalNotes(aimsWapApp.getInitialApprovalNotes());
                    wapAppUpdForm.setInitialApprovalAction(aimsWapApp.getInitialApprovalAction());
                }

                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_BUSINESS_APPROVAL))
                {
                    wapAppUpdForm.setBusinessApprovalNotes(aimsWapApp.getBusinessApprovalNotes());
                    wapAppUpdForm.setBusinessApprovalAction(aimsWapApp.getBusinessApprovalAction());
                }

                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_MOVE_TO_PENDING_DCR))
                {
                    wapAppUpdForm.setMoveToPendingDcr(aimsWapApp.getMoveToPendingDcr());
                    wapAppUpdForm.setVendorSplitPercentage(aimsWapApp.getVendorSplitPercentage());
                }

                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_PENDING_DCR))
                {
                    wapAppUpdForm.setPendingDcrNotes(aimsWapApp.getPendingDcrNotes());
                    wapAppUpdForm.setTaxCategoryCodeId(aimsWapApp.getTaxCategoryCodeId());
                    wapAppUpdForm.setPendingDcrVersion(aimsWapApp.getPendingDcrVersion());
                    wapAppUpdForm.setPageViewRate(aimsWapApp.getPageViewRate());
                    wapAppUpdForm.setWapAppVersions(WapApplicationHelper.getWapVersionIds(aimsWapApp.getWapAppsId()));
                }

                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_TESTING))
                {
                    if (aimsApp.getPhases() != null)
                    {
                        AimsAppPhase aimsAppPhase = null;
                        Vector appPhaseTestIds = new Vector();
                        Vector appPhaseTestNames = new Vector();
                        Vector appPhaseTestDates = new Vector();
                        Vector appPhaseTestStatus = new Vector();
                        Vector appPhaseTestFileNames = new Vector();
                        Vector appPhaseTestFileIds = new Vector();
                        Vector appPhaseTestComments = new Vector();
                        Vector appPhaseTestUpdateBys = new Vector();
                        Vector appPhaseTestUpdateDates = new Vector();

                        for (Iterator it = aimsApp.getPhases().iterator(); it.hasNext();)
                        {
                            aimsAppPhase = (AimsAppPhase) it.next();
                            appPhaseTestIds.add(StringFuncs.NullValueReplacement(aimsAppPhase.getAimsVzwTestingPhase().getTestingPhaseId().toString()));
                            appPhaseTestNames.add(StringFuncs.NullValueReplacement(aimsAppPhase.getAimsVzwTestingPhase().getTestingPhaseName()));
                            appPhaseTestDates.add(StringFuncs.NullValueReplacement(Utility.convertToString(aimsAppPhase.getTestedDate(), dateFormat)));
                            appPhaseTestStatus.add(StringFuncs.NullValueReplacement(aimsAppPhase.getStatus()));
                            appPhaseTestFileNames.add(StringFuncs.NullValueReplacement(aimsAppPhase.getResultsFileName()));
                            appPhaseTestFileIds.add("0");
                            appPhaseTestComments.add(StringFuncs.NullValueReplacement(aimsAppPhase.getComments()));
                            appPhaseTestUpdateBys.add(StringFuncs.NullValueReplacement(aimsAppPhase.getLastUpdatedBy()));
                            appPhaseTestUpdateDates.add(
                                StringFuncs.NullValueReplacement(Utility.convertToString(aimsAppPhase.getLastUpdatedDate(), dateFormat)));
                        }

                        wapAppUpdForm.setTestId(StringFuncs.ConvertListToStringArray(appPhaseTestIds));
                        wapAppUpdForm.setTestName(StringFuncs.ConvertListToStringArray(appPhaseTestNames));
                        wapAppUpdForm.setTestedDate2(StringFuncs.ConvertListToStringArray(appPhaseTestDates));
                        wapAppUpdForm.setTestStatus2(StringFuncs.ConvertListToStringArray(appPhaseTestStatus));
                        wapAppUpdForm.setTestResultFileName(StringFuncs.ConvertListToStringArray(appPhaseTestFileNames));
                        wapAppUpdForm.setTestResultFileId(StringFuncs.ConvertListToStringArray(appPhaseTestFileIds));
                        wapAppUpdForm.setTestComments2(StringFuncs.ConvertListToStringArray(appPhaseTestComments));
                        wapAppUpdForm.setTestUpdatedBy(StringFuncs.ConvertListToStringArray(appPhaseTestUpdateBys));
                        wapAppUpdForm.setTestUpdatedDate(StringFuncs.ConvertListToStringArray(appPhaseTestUpdateDates));
                    }
                }

                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_COMPLETION))
                {
                    wapAppUpdForm.setMoveToCompletedInProduction(aimsWapApp.getMoveToCompletedInProduction());
                }

                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_REMOVAL))
                {
                    wapAppUpdForm.setMoveToSunset(aimsWapApp.getMoveToSunset());
                }

                //VZW Live Date is visible in two sections: 'Initial Approval' & 'Testing'                
                if ((ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_APPROVAL))
                    || (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_TESTING)))
                {
                    wapAppUpdForm.setVzwLiveDate(Utility.convertToString(aimsWapApp.getVzwLiveDate(), dateFormat));
                }
            }
            //check whether manual image upload allowed for this application or not
            //wapFTPFlag is empty or equal to 'A' and not equal to 'Y'
            if( WapApplicationHelper.isManualImageUploadAllowed(aimsWapApp.getWapFtpFlag()) ) {
                request.setAttribute("manualImageUploadAllowed", new Boolean(true));
            }
        }//end if edit, clone or view

        //Setting the Alliance(Content Provider) Name for Page Header
        AimsAllianc aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, wapAppUpdForm.getAppOwnerAllianceId().toString());
        wapAppUpdForm.setAllianceName(aimsAllianceOfApplication.getCompanyName());
        wapAppUpdForm.setVendorId(aimsAllianceOfApplication.getVendorId());

        //Setting the Application Status for Page Header
        if ((wapAppUpdForm.getAppsId() != null) && (wapAppUpdForm.getAppsId().longValue() != 0))
        {
            AimsLifecyclePhase aimsPhaseOfApplication =
                (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, wapAppUpdForm.getAimsLifecyclePhaseId().toString());
            wapAppUpdForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
        }
        else
        {
            wapAppUpdForm.setApplicationStatus("NEW");
        }

        return mapping.findForward(forward);
    }

}
