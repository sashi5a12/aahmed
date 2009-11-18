package com.netpace.aims.controller.application;

import java.util.*;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppPhase;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.application.AimsWapApp;
import com.netpace.aims.model.application.AimsWapLicenseType;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.*;

/**
 * This class takes care of action for display of update form of BREW Application.
 *
 * @struts.action path="/wapApplicationUpdate"
 *                name="WapApplicationUpdateForm"
 *                scope="request"
 *                input="/application/wapApplicationUpdate.jsp"
 *        validate="true"
 * @struts.action-forward name="page1" path="/application/wapApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/wapApplicationUpdate2.jsp"
 * @struts.action-forward name="page3" path="/application/wapAppProcessInfo.jsp"
 * @struts.action-forward name="page4" path="/application/wapJournal.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="wapView" path="/application/wapApplicationView.jsp"
 * @struts.action-forward name="page4View" path="/application/wapJournalView.jsp"

 * @author Fawad Sikandar
 */
public class WapApplicationUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(WapApplicationUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        Long clonedFromAppId = null;
        Long oldStatus = new Long(0);
        HashMap oldValuesMap = null;
        HashMap testResultFileIdsInfo = new HashMap();
		String [] oldAppNetworkURLs = null;
        Set testingPhaseSet = null;
        String testTDStatus = null;
        String testPDStatus = null;
        String localLastUpdatedBy[] = null;
        String localLastUpdatedDate[] = null;
        Set testUpdatedJournalEntry = new HashSet();
        String forward = "view";
        String taskname = "";
        String submitType = "";
        String theRealXSDPath = getServlet().getServletContext().getRealPath(AimsConstants.INFOSPACE_XSD_URL);
        String hostName = request.getServerName();

        Object o_param = request.getParameter("task");
        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        o_param = request.getParameter("appSubmitType");
        if (o_param != null)
            submitType = request.getParameter("appSubmitType");

        log.debug("TASK is:  " + taskname);

        AimsApp aimsApp = null;
        AimsWapApp aimsWapApp = null;
        AimsContact aimsContact = null;
        HashMap appWap = null;

        //Get Form
        WapApplicationUpdateForm wapAppUpdForm = (WapApplicationUpdateForm) form;

        if (taskname.equalsIgnoreCase("create"))
        {
            aimsApp = new AimsApp();
            aimsWapApp = new AimsWapApp();
        }
        else if (taskname.equalsIgnoreCase("edit"))
        {
            try
            {
                appWap = WapApplicationManager.getWapApp(wapAppUpdForm.getAppsId(), currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }
            aimsApp = (AimsApp) appWap.get("AimsApp");
            aimsWapApp = (AimsWapApp) appWap.get("AimsWapApp");
            oldValuesMap = WapApplicationHelper.getValuesMapForVersion(aimsApp, aimsWapApp, dateFormat);
            oldStatus = aimsApp.getAimsLifecyclePhaseId();

            //get old network urls,
            if(aimsApp.getAppsId()!=null)
            {
                oldAppNetworkURLs = AimsApplicationsManager.getAppNetworkUrlValues( aimsApp.getAppsId() );
            }
        }

        //CHECK ACCESS
        if (!(ApplicationHelper.checkPlatformAccess(request, wapAppUpdForm.getOrignalTask(), AimsConstants.WAP_PLATFORM_ID)))
            throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("edit"))
            if (!(WapApplicationHelper.checkEditAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
                throw new AimsSecurityException();
        //END OF CHECK ACCESS

        if ((taskname.equalsIgnoreCase("page1"))
            || (taskname.equalsIgnoreCase("page2"))
            || (taskname.equalsIgnoreCase("page3"))
            || (taskname.equalsIgnoreCase("page4"))
            || (taskname.equalsIgnoreCase("submitpage4"))
            || (taskname.equalsIgnoreCase("submitpage4View")))
        {
            if (taskname.equalsIgnoreCase("page1"))
                forward = "page1";
            else if (taskname.equalsIgnoreCase("page2"))
                forward = "page2";
            else if (taskname.equalsIgnoreCase("page3"))
                forward = "page3";
            else if (taskname.equalsIgnoreCase("page4"))
                forward = "page4";
            else if (taskname.equalsIgnoreCase("submitpage4"))
                forward = "page4";
            else if (taskname.equalsIgnoreCase("submitpage4View"))
                forward = "page4View";

            if (taskname.equalsIgnoreCase("submitpage4"))
                wapAppUpdForm.setCurrentPage("page4");
            else
                wapAppUpdForm.setCurrentPage(taskname);

            wapAppUpdForm.setTask(wapAppUpdForm.getOrignalTask());

            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                if ((taskname.equalsIgnoreCase("submitpage4")) || (taskname.equalsIgnoreCase("submitpage4View")))
                {
                    AimsApplicationsManager.saveJournalEntry(
                        wapAppUpdForm.getAppsId(),
                        wapAppUpdForm.getJournalText(),
                        wapAppUpdForm.getJournalType(),
                        currUser);
                    Collection journalEntries = WapApplicationManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
                    wapAppUpdForm.setJournalCombinedText(WapApplicationHelper.getFormattedJournalEntries(journalEntries));
                }
            }
        }

        if ((submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (submitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)))
        {
            if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")))
            {
                if (taskname.equalsIgnoreCase("create"))
                {
                    aimsApp.setAimsAllianceId(currentUserAllianceId);
                    aimsApp.setAimsUserAppCreatedById(currUserId);
                    aimsApp.setIfOnHold("N");
                    aimsApp.setCreatedBy(currUser);
                    aimsApp.setCreatedDate(new Date());
                }

                aimsApp.setLastUpdatedBy(currUser);
                aimsApp.setLastUpdatedDate(new Date());
                aimsApp.setAimsPlatformId(AimsConstants.WAP_PLATFORM_ID);

                //set network usage (for urls)
                    if( (StringFuncs.isNullOrEmpty(wapAppUpdForm.getNetworkUsage()))
                        || (wapAppUpdForm.getNetworkUsage().equalsIgnoreCase(AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE)) )
                    {
                        //if network usage is disabled or null then set app urls to null
                        aimsApp.setNetworkUsage(AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE);
                        aimsApp.setAppNetworkURLs(null);
                    }
                    else if(wapAppUpdForm.getNetworkUsage().equalsIgnoreCase(AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE))
                    {
                        //if network usage is enabled then create List of AimsAppNetworkUrls
                        aimsApp.setNetworkUsage(wapAppUpdForm.getNetworkUsage());
                        List networkUrlsList = ApplicationHelper.convertAppNetworkUrlArrayToList(wapAppUpdForm.getApplicationURLs());
                        aimsApp.setAppNetworkURLs(networkUrlsList);
                    }
                    //export urls on edit screen
                    if(!taskname.equalsIgnoreCase("create") && !taskname.equalsIgnoreCase("clone"))
                    {
                        //check access for EXPORT_CONTENT_FILTER_URLS
                        request.setAttribute("exportUrlAllowed",
                                new Boolean((ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.EXPORT_CONTENT_FILTER_URLS))) );
                    }
                //end set network usage

                // Contact
				if ((wapAppUpdForm.getAimsContactId() != null) && (wapAppUpdForm.getAimsContactId().longValue() != 0))
					aimsApp.setAimsContactId(wapAppUpdForm.getAimsContactId());
				else if ((wapAppUpdForm.getAimsContactId() != null) && (wapAppUpdForm.getAimsContactId().longValue() == 0)) {
					aimsApp.setAimsContactId(null);
					aimsContact = new AimsContact();
					aimsContact.setFirstName(wapAppUpdForm.getContactFirstName());
					aimsContact.setLastName(wapAppUpdForm.getContactLastName());
					aimsContact.setEmailAddress(wapAppUpdForm.getContactEmail());
					aimsContact.setTitle(wapAppUpdForm.getContactTitle());
					aimsContact.setPhone(wapAppUpdForm.getContactPhone());
					aimsContact.setMobile(wapAppUpdForm.getContactMobile());
					aimsContact.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);
					aimsContact.setCreatedBy(currUser);
					aimsContact.setCreatedDate(new Date());
					aimsContact.setLastUpdatedBy(currUser);
					aimsContact.setLastUpdatedDate(new Date());
				} else
					aimsApp.setAimsContactId(null);
				
                // Check to see if the application can be edited (i.e. Phase ID is below 'Submitted DCR')
                if (!WapApplicationHelper.isStatusSubmittedDCRAndAbove(aimsApp.getAimsLifecyclePhaseId()))
                {
                    //Start of check to see if Product Code is Same and Product Version has incremented/changed, IF, App was "Rolled Back" to Pending DCR
                    if ((aimsWapApp.getRolledBackToPendingDcr() != null) && (aimsWapApp.getRolledBackToPendingDcr().equals("Y")))
                    {
                        if (wapAppUpdForm.getVersion().equals(aimsWapApp.getVersionBeforeRollback()))
                        {
                            AimsException aimsException = new AimsException("Error");
                            aimsException.addException(new RecordNotFoundException("error.wap.app.rollback.version"));
                            saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
                            return mapping.getInputForward();
                        }

                        if (!wapAppUpdForm.getVendorProductCode().equals(aimsWapApp.getVendorProductCode()))
                        {
                            AimsException aimsException = new AimsException("Error");
                            aimsException.addException(new RecordNotFoundException("error.wap.app.rollback.productCode"));
                            saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
                            return mapping.getInputForward();
                        }
                    }
                    //End of check to see if Product Code is Unique

                    aimsApp.setTitle(wapAppUpdForm.getTitle());
                    aimsApp.setVersion(wapAppUpdForm.getVersion());
                    aimsWapApp.setLongProductName(wapAppUpdForm.getLongProductName());
                    aimsWapApp.setContentType(wapAppUpdForm.getContentType());
                    aimsApp.setShortDesc(wapAppUpdForm.getShortDesc());
                    aimsApp.setLongDesc(wapAppUpdForm.getLongDesc());
                    aimsWapApp.setWapVersion(wapAppUpdForm.getWapVersion());

                    //no need of test url now, test url and demo url are same
                    //test url and demo url are same, copy demo url to test url
                    //aimsWapApp.setTestUrl(wapAppUpdForm.getTestUrl());
                    aimsWapApp.setTestUrl(wapAppUpdForm.getDemoUrl());
                    aimsWapApp.setDemoUrl(wapAppUpdForm.getDemoUrl());

                    aimsWapApp.setProductionUrl(wapAppUpdForm.getProductionUrl());
                    aimsWapApp.setVzwRetailPrice(wapAppUpdForm.getVzwRetailPrice());
                    aimsWapApp.setVendorProductCode(wapAppUpdForm.getVendorProductCode());
                    aimsWapApp.setVendorProductDisplay(wapAppUpdForm.getVendorProductDisplay());

                    //Start of check to see if Product Code is Unique
                    if ((aimsWapApp.getVendorProductCode() != null)
                        && (!WapApplicationManager
                            .checkUniqueVendorProductCode(aimsWapApp.getVendorProductCode(), aimsApp.getAimsAllianceId(), aimsApp.getAppsId())))
                    {
                        AimsException aimsException = new AimsException("Error");
                        aimsException.addException(new RecordNotFoundException("error.wap.app.vendorProductCode.unique"));
                        saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
                        return mapping.getInputForward();
                    }
                    //End of check to see if Product Code is Unique

                    //SubCategory
                    if ((wapAppUpdForm.getSubCategoryId1() != null) && (wapAppUpdForm.getSubCategoryId1().longValue() != 0))
                        aimsWapApp.setSubCategoryId1(wapAppUpdForm.getSubCategoryId1());
                    else
                        aimsWapApp.setSubCategoryId1(null);

                    //License Types
                    Set licenseTypeSet = new HashSet();
                    if (wapAppUpdForm.getListSelectedLicenseTypes() != null)
                    {
                        for (int i = 0; i < wapAppUpdForm.getListSelectedLicenseTypes().length; i++)
                        {
                            licenseTypeSet.add(
                                (AimsWapLicenseType) DBHelper.getInstance().load(AimsWapLicenseType.class, wapAppUpdForm.getListSelectedLicenseTypes()[i]));
                        }
                    }
                    aimsWapApp.setLicenseTypes(licenseTypeSet);                    

                    //The following properties can only be updated by an Alliance user and not the Verizon user
                    if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
                    {
                        //Start of check to see if alliance has been accepted
                        Object[] userValues = null;
                        String allianceStatus = null;
                        Collection AimsAlliance = null;

                        try
                        {
                            AimsAlliance = AllianceManager.getAllianceDetails(aimsApp.getAimsAllianceId(), "");
                        }
                        catch (Exception ex)
                        {
                            AimsException aimsException = new AimsException("Error");
                            aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
                            throw aimsException;
                        }

                        for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
                        {
                            userValues = (Object[]) iter.next();
                            allianceStatus = (String) userValues[3];
                        }

                        if (!(wapAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM)))
                        {
                            if (!(allianceStatus.equals(AimsConstants.ALLIANCE_STATUS_ACCEPTED)))
                            {
                                AimsException aimsException = new AimsException("Error");
                                aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
                                saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
                                return mapping.getInputForward();
                            }
                        }
                        //End of check to see if alliance has been accepted

                        aimsWapApp.setDescContentOffering(wapAppUpdForm.getDescContentOffering());
                        //vzw user can change demo url in submitted dcr state, moved setting demo url
                        //aimsWapApp.setDemoUrl(wapAppUpdForm.getDemoUrl());

                        aimsWapApp.setWebsiteUrl(wapAppUpdForm.getWebsiteUrl());
                        aimsApp.setIfPrRelease(wapAppUpdForm.getIfPrRelease());
                        aimsWapApp.setLaunchDate(Utility.convertToDate(wapAppUpdForm.getLaunchDate(), dateFormat));
                        aimsWapApp.setTestEffectiveDate(Utility.convertToDate(wapAppUpdForm.getTestEffectiveDate(), dateFormat));
                        aimsWapApp.setClonedFromId(wapAppUpdForm.getClonedFromId());
                        aimsWapApp.setClonedFromTitle(wapAppUpdForm.getClonedFromTitle());
                        

                    }

                    //The following properties can only be updated by an Verizon user and not the Alliance user                  
                    if (currUserType.equals(AimsConstants.VZW_USERTYPE))
                    {
                        if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_APPROVAL))
                        {
                            if ((wapAppUpdForm.getSubCategoryId2() != null) && (wapAppUpdForm.getSubCategoryId2().longValue() != 0))
                                aimsWapApp.setSubCategoryId2(wapAppUpdForm.getSubCategoryId2());
                            else
                                aimsWapApp.setSubCategoryId2(null);

                            if ((wapAppUpdForm.getSubCategoryId3() != null) && (wapAppUpdForm.getSubCategoryId3().longValue() != 0))
                                aimsWapApp.setSubCategoryId3(wapAppUpdForm.getSubCategoryId3());
                            else
                                aimsWapApp.setSubCategoryId3(null);

                            aimsWapApp.setLinkOrder1(wapAppUpdForm.getLinkOrder1());
                            aimsWapApp.setLinkOrder2(wapAppUpdForm.getLinkOrder2());
                            aimsWapApp.setLinkOrder3(wapAppUpdForm.getLinkOrder3());
                            aimsWapApp.setContentType(wapAppUpdForm.getContentType());
                            aimsWapApp.setInitialApprovalNotes(wapAppUpdForm.getInitialApprovalNotes());
                            aimsWapApp.setInitialApprovalAction(wapAppUpdForm.getInitialApprovalAction());
                            aimsWapApp.setVzwLiveDate(Utility.convertToDate(wapAppUpdForm.getVzwLiveDate(), dateFormat));
                        }

                        if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_BUSINESS_APPROVAL))
                        {
                            aimsWapApp.setBusinessApprovalNotes(wapAppUpdForm.getBusinessApprovalNotes());
                            aimsWapApp.setBusinessApprovalAction(wapAppUpdForm.getBusinessApprovalAction());
                        }

                        if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_MOVE_TO_PENDING_DCR))
                        {
                            aimsWapApp.setMoveToPendingDcr(wapAppUpdForm.getMoveToPendingDcr());
                            aimsWapApp.setVendorSplitPercentage(wapAppUpdForm.getVendorSplitPercentage());
                        }

                        if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_PENDING_DCR))
                        {
                            aimsWapApp.setPendingDcrNotes(wapAppUpdForm.getPendingDcrNotes());
                            aimsWapApp.setPageViewRate(wapAppUpdForm.getPageViewRate());
                            if ((wapAppUpdForm.getTaxCategoryCodeId() != null) && (wapAppUpdForm.getTaxCategoryCodeId().longValue() != 0))
                                aimsWapApp.setTaxCategoryCodeId(wapAppUpdForm.getTaxCategoryCodeId());
                            else
                                aimsWapApp.setTaxCategoryCodeId(null);
                        }

                    }
                }
                //End of Check to see if the application can be edited (i.e. Phase ID is below 'Submitted DCR')

                //The following properties can only be updated by an Verizon user and not the Alliance user                  
                if (currUserType.equals(AimsConstants.VZW_USERTYPE))
                {
                    if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_TESTING))
                    {
                        aimsWapApp.setVzwLiveDate(Utility.convertToDate(wapAppUpdForm.getVzwLiveDate(), dateFormat));

                        HashMap idMaps = new HashMap();
                        String[] testIds = wapAppUpdForm.getTestId();
                        localLastUpdatedBy = wapAppUpdForm.getTestUpdatedBy();
                        localLastUpdatedDate = wapAppUpdForm.getTestUpdatedDate();

                        //Associating TestIDs(e.g. 22,23,24 ...) to its index in the Array (e.g. 1,2,3...)
                        //This will help in fetching corresponding values from different Arrays like (Status, Comments etc.)
                        for (int index = 0; index < testIds.length; index++)
                        {
                            idMaps.put(testIds[index], (new Integer(index)).toString());
                        }

                        for (java.util.Iterator itr = aimsApp.getPhases().iterator(); itr.hasNext();)
                        {
                            boolean valueChanged = false;
                            AimsAppPhase aap = (AimsAppPhase) itr.next();
                            String testIdKey = aap.getAimsVzwTestingPhase().getTestingPhaseId().toString();
                            int iIndex = Integer.parseInt(idMaps.get(testIdKey).toString());

                            //Checking to see if any value (Status, Comments, etc...) is changed for this particular test
                            if ((!wapAppUpdForm.getTestStatus(iIndex).equals(StringFuncs.NullValueReplacement(aap.getStatus())))
                                || (!wapAppUpdForm.getTestComments(iIndex).equals(StringFuncs.NullValueReplacement(aap.getComments())))
                                || (!((wapAppUpdForm.getTestResultFileName())[iIndex]).equals(StringFuncs.NullValueReplacement(aap.getResultsFileName())))
                                || (!wapAppUpdForm
                                    .getTestedDate(iIndex)
                                    .equals(StringFuncs.NullValueReplacement(Utility.convertToString(aap.getTestedDate(), dateFormat)))))
                            {
                                valueChanged = true;
                            }

                            aap.setStatus(wapAppUpdForm.getTestStatus(iIndex));
                            aap.setComments(wapAppUpdForm.getTestComments(iIndex));
                            aap.setTestedDate(Utility.convertToDate(wapAppUpdForm.getTestedDate(iIndex), AimsConstants.DATE_FORMAT));
                            aap.setTempFileId(null);
                            aap.setResultsFileName((wapAppUpdForm.getTestResultFileName())[iIndex]);

                            //Saving Test Result File
                            if (!(wapAppUpdForm.getTestResultFileId())[iIndex].equals("0"))
                            {
                                Long[] compositeId = new Long[2];
                                compositeId[0] = aap.getAimsVzwTestingPhase().getTestingPhaseId();
                                compositeId[1] = aap.getAimsDevice().getDeviceId();
                                testResultFileIdsInfo.put((wapAppUpdForm.getTestResultFileId())[iIndex], compositeId);
                                valueChanged = true;
                            }

                            if (valueChanged)
                            {
                                localLastUpdatedBy[iIndex] = currUser;
                                localLastUpdatedDate[iIndex] = Utility.convertToString(new Date(), dateFormat);
                                aap.setLastUpdatedBy(currUser);
                                aap.setLastUpdatedDate(new Date());
                                testUpdatedJournalEntry.add(WapApplicationHelper.constructJournalEntryForUpdatedTest(aap));
                            }

                            //Setting the phase status of TD and PD
                            if (testIdKey.equals(AimsConstants.TESTING_WAP_TD_REVIEW_ID.toString()))
                                testTDStatus = aap.getStatus();
                            if (testIdKey.equals(AimsConstants.TESTING_WAP_PD_REVIEW_ID.toString()))
                                testPDStatus = aap.getStatus();
                        }
                        testingPhaseSet = aimsApp.getPhases();
                    }

                    if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_COMPLETION))
                    {
                        aimsWapApp.setMoveToCompletedInProduction(wapAppUpdForm.getMoveToCompletedInProduction());
                    }

                    if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_REMOVAL))
                    {
                        aimsWapApp.setMoveToSunset(wapAppUpdForm.getMoveToSunset());
                    }
                }

                //Making sure that the files cannot be uploaded if the status is 'SUBMITTED DCR' or above.  
                if ((currUserType.equals(AimsConstants.VZW_USERTYPE))
                    || (WapApplicationHelper.isStatusSubmittedDCRAndAbove(aimsApp.getAimsLifecyclePhaseId())))
                {
                    wapAppUpdForm.setProductLogoTempFileId(new Long(0));
                    wapAppUpdForm.setProductIconTempFileId(new Long(0));
                    wapAppUpdForm.setScreenJpegTempFileId(new Long(0));
                    wapAppUpdForm.setUserGuideTempFileId(new Long(0));
                    wapAppUpdForm.setFaqDocTempFileId(new Long(0));
                    wapAppUpdForm.setPresentationTempFileId(new Long(0));
                    wapAppUpdForm.setAppMediumLargeImageTempFileId(new Long(0));
                    wapAppUpdForm.setAppQVGAPotraitImageTempFileId(new Long(0));
                    wapAppUpdForm.setAppQVGALandscapeImageTempFileId(new Long(0));
                }

                //START OF CHANGING/UPDATING THE PHASE
                if (wapAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
                else if (wapAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                {
                    //check alliance has accepted atleast one active wap contract
                    if(!ApplicationHelper.validateAllianceContract(aimsApp.getAimsAllianceId(), AimsConstants.WAP_PLATFORM_ID, currUserType))
                    {
                        AimsException aimsException = new AimsException("Error");
                        aimsException.addException(new RecordNotFoundException("error.wap.app.contract.acceptance"));
                        saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
                        return mapping.getInputForward();
                    }

                    aimsApp.setSubmittedDate(new Date());
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUBMISSION_ID);
                    wapAppUpdForm.setSubmittedDate(Utility.convertToString(aimsApp.getSubmittedDate(), dateFormat));
                }
                else if (wapAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))
                {
                    //From Submit -> Initial Approval/Denied 
                    if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SUBMISSION_ID.longValue())
                        && (aimsWapApp.getInitialApprovalAction() != null)
                        && (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_APPROVAL)))
                    {
                        if (aimsWapApp.getInitialApprovalAction().equals(AimsConstants.WAP_APP_RADIO_INITIAL_APPROVE[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_INITIAL_APPROVAL_ID);
                        if (aimsWapApp.getInitialApprovalAction().equals(AimsConstants.WAP_APP_RADIO_INITIAL_DENY[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_INITIAL_DENIED_ID);
                    }

                    //From Initial Approval -> Business(Approval/Denied)/Pending ARM  
                    else if (
                        (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_INITIAL_APPROVAL_ID.longValue())
                            && (aimsWapApp.getBusinessApprovalAction() != null)
                            && (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_BUSINESS_APPROVAL)))
                    {
                        if (aimsWapApp.getBusinessApprovalAction().equals(AimsConstants.WAP_APP_RADIO_BUSINESS_OK[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID);
                        if (aimsWapApp.getBusinessApprovalAction().equals(AimsConstants.WAP_APP_RADIO_BUSINESS_ARM[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_PENDING_ARM_ID);
                        if (aimsWapApp.getBusinessApprovalAction().equals(AimsConstants.WAP_APP_RADIO_BUSINESS_DENY[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_BUSINESS_APPROVAL_DENIED_ID);
                    }

                    //From Pending ARM -> Business(Approval/Denied)  
                    else if (
                        (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_PENDING_ARM_ID.longValue())
                            && (aimsWapApp.getBusinessApprovalAction() != null)
                            && (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_BUSINESS_APPROVAL)))
                    {
                        if (aimsWapApp.getBusinessApprovalAction().equals(AimsConstants.WAP_APP_RADIO_BUSINESS_OK[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID);
                        if (aimsWapApp.getBusinessApprovalAction().equals(AimsConstants.WAP_APP_RADIO_BUSINESS_DENY[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_BUSINESS_APPROVAL_DENIED_ID);
                    }

                    //From Business Approval/Pending ARM -> Pending DCR   
                    else if (
                        ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID.longValue())
                            || (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_PENDING_ARM_ID.longValue()))
                            && (aimsWapApp.getMoveToPendingDcr() != null)
                            && (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_MOVE_TO_PENDING_DCR)))
                    {
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_PENDING_DCR_ID);
                        aimsWapApp.setPendingDcrVersion("1.0");
                    }

                    //From Pending DCR -> Submitted DCR   
                    else if (
                        (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_PENDING_DCR_ID.longValue())
                            && (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_PENDING_DCR)))
                    {
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_SUBMITTED_DCR_ID);
                    }

                    //From Submitted DCR -> Testing Passed / Testing Failed / Publication Ready
                    else if (
                        (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())
                            && (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_TESTING)))
                    {
                        if ((testTDStatus != null) && (testTDStatus.equals("P")))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_TESTING_PASSED_ID);
                        if ((testTDStatus != null) && (testTDStatus.equals("F")))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_TESTING_FAILED_ID);
                        if ((testPDStatus != null) && (testPDStatus.equals("P")))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_PUBLICATION_READY_ID);
                    }

                    //From Testing Passed -> Publication Ready
                    else if (
                        (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_TESTING_PASSED_ID.longValue())
                            && (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_TESTING)))
                    {
                        if ((testPDStatus != null) && (testPDStatus.equals("P")))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_PUBLICATION_READY_ID);
                    }

                    //From Publication Ready -> Completed - In Production 
                    else if (
                        (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_PUBLICATION_READY_ID.longValue())
                            && (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_COMPLETION))
                            && (aimsWapApp.getMoveToCompletedInProduction() != null))
                    {
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_COMPLETED_IN_PRODUCTION_ID);
                    }

                    //From Completed - In Production -> Sunset 
                    else if (
                        (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_COMPLETED_IN_PRODUCTION_ID.longValue())
                            && (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_REMOVAL))
                            && (aimsWapApp.getMoveToSunset() != null))
                    {
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUNSET_ID);
                    }
                }

                else if (
                    (wapAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                        || (wapAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM)))
                {}
                //END OF CHANGING/UPDATING THE PHASE

                //Check if the app is being cloned
                if ((wapAppUpdForm.getAppsId().longValue() != 0) && (aimsApp.getAppsId() == null))
                    clonedFromAppId = wapAppUpdForm.getAppsId();

                try
                {
                    WapApplicationManager.saveOrUpdateWapApplication(
                        aimsApp,
                        aimsWapApp,
                        aimsContact,
                        currUser,
                        currUserType,
                        wapAppUpdForm.getProductLogoTempFileId(),
                        wapAppUpdForm.getProductIconTempFileId(),
                        wapAppUpdForm.getScreenJpegTempFileId(),
                        wapAppUpdForm.getUserGuideTempFileId(),
                        wapAppUpdForm.getFaqDocTempFileId(),
                        wapAppUpdForm.getPresentationTempFileId(),
                        wapAppUpdForm.getAppMediumLargeImageTempFileId(),
                        wapAppUpdForm.getAppQVGAPotraitImageTempFileId(),
                        wapAppUpdForm.getAppQVGALandscapeImageTempFileId(),
                        testingPhaseSet,
                        testResultFileIdsInfo,
                        clonedFromAppId,
                        oldValuesMap,
                        oldStatus,
                        testUpdatedJournalEntry,
                        dateFormat);
                }
                catch (AimsException ae)
                {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                    return mapping.getInputForward();
                }

                //POST UPDATE TASKS

                wapAppUpdForm.setAppsId(aimsApp.getAppsId());
                wapAppUpdForm.setTask("edit");
                wapAppUpdForm.setOrignalTask("edit");
                wapAppUpdForm.setAimsLifecyclePhaseId(WapApplicationHelper.getFilteredApplicationStatus(aimsApp.getAimsLifecyclePhaseId(), currUserType));
                Long newStatus = aimsApp.getAimsLifecyclePhaseId();

                //Start of API Notifications to InfoSpace
                if ((aimsWapApp.getWapVersion().equals(AimsConstants.WAP_APP_VERSION_WAP_2_0[0]))
                    && (oldStatus != null)
                    && (oldStatus.longValue() != newStatus.longValue()))
                {
                    if (newStatus.longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())
                    {
                        WapApplicationHelper.sendDCRToInfoSpace(aimsApp, aimsWapApp, AimsConstants.PHASE_SUBMITTED_DCR_ID, theRealXSDPath, hostName, false);
                        if (aimsWapApp.getContentType().equals(AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0]))
                            WapApplicationHelper.sendDCRToSCM(aimsApp, aimsWapApp, AimsConstants.PHASE_SUBMITTED_DCR_ID, theRealXSDPath, hostName);
                    }

                    if (newStatus.longValue() == AimsConstants.SUNSET_ID.longValue())
                        WapApplicationHelper.sendDCRToInfoSpace(aimsApp, aimsWapApp, AimsConstants.SUNSET_ID, theRealXSDPath, hostName, false);
                }
                //End of API Notifications to InfoSpace

                if (currUserType.equals(AimsConstants.VZW_USERTYPE))
                {
                    if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_PENDING_DCR))
                    {
                        wapAppUpdForm.setPendingDcrVersion(aimsWapApp.getPendingDcrVersion());
                        wapAppUpdForm.setWapAppVersions(WapApplicationHelper.getWapVersionIds(aimsWapApp.getWapAppsId()));
                    }
                    if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_TESTING))
                    {
                        wapAppUpdForm.setTestUpdatedBy(localLastUpdatedBy);
                        wapAppUpdForm.setTestUpdatedDate(localLastUpdatedDate);
                    }
                }

                Collection journalEntries = null;
                try
                {
                    journalEntries = AimsApplicationsManager.getJournalEntries(aimsApp.getAppsId(), currentUserAllianceId);
                }
                catch (Exception ex)
                {
                    journalEntries = null;
                    log.debug("\n\nError in getting Journal Entries\n\n");
                }

                wapAppUpdForm.setJournalCombinedText(WapApplicationHelper.getFormattedJournalEntries(journalEntries));
                //Setting the Application Status for Page Header
                AimsLifecyclePhase aimsPhaseOfApplication =
                    (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, wapAppUpdForm.getAimsLifecyclePhaseId().toString());
                wapAppUpdForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
                //Set Temp File Ids to Zero
                wapAppUpdForm.setProductLogoTempFileId(new Long(0));
                wapAppUpdForm.setProductIconTempFileId(new Long(0));
                wapAppUpdForm.setScreenJpegTempFileId(new Long(0));
                wapAppUpdForm.setUserGuideTempFileId(new Long(0));
                wapAppUpdForm.setFaqDocTempFileId(new Long(0));
                wapAppUpdForm.setPresentationTempFileId(new Long(0));
                wapAppUpdForm.setAppMediumLargeImageTempFileId(new Long(0));//set app image medium
                wapAppUpdForm.setAppQVGAPotraitImageTempFileId(new Long(0));//set app image potrait
                wapAppUpdForm.setAppQVGALandscapeImageTempFileId(new Long(0));//set app image landscape
                if (wapAppUpdForm.getTestResultFileId() != null)
                {
                    Vector appPhaseTestFileIds = new Vector();
                    for (int index = 0; index < wapAppUpdForm.getTestResultFileId().length; index++)
                        appPhaseTestFileIds.add("0");
                    wapAppUpdForm.setTestResultFileId(StringFuncs.ConvertListToStringArray(appPhaseTestFileIds));
                }
                //End Of Set Temp File Ids to Zero

                if (((oldStatus != null) && (oldStatus.longValue() != newStatus.longValue()))
                    || ((oldStatus == null) && (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())))
                {
                    AimsEventLite aimsEvent = null;
                    if (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_STATUS_SUBMITTED);
                    if (newStatus.longValue() == AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID.longValue())
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_STATUS_BUSINESS_APPROVAL_GRANTED);
                    if (newStatus.longValue() == AimsConstants.PHASE_PENDING_DCR_ID.longValue())
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_STATUS_PENDING_DCR);
                    if (newStatus.longValue() == AimsConstants.PHASE_BUSINESS_APPROVAL_DENIED_ID.longValue())
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_STATUS_BUSINESS_APPROVAL_DENIED);
                    if (newStatus.longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())
                    {
                        if ((aimsWapApp.getRolledBackToPendingDcr() != null) && (aimsWapApp.getRolledBackToPendingDcr().equals("Y")))
                            aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_RESUBMITTED_STATUS_SUBMITTED_DCR);
                        else
                            aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_STATUS_SUBMITTED_DCR);
                    }
                    if (newStatus.longValue() == AimsConstants.PHASE_PUBLICATION_READY_ID.longValue())
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_STATUS_PUBLICATION_READY);
                    if (newStatus.longValue() == AimsConstants.SUNSET_ID.longValue())
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_STATUS_SUNSET);

                    //Special Case for Sending Notification 'Testing Passed'. It should be WAP 2.0   
                    if ((newStatus.longValue() == AimsConstants.PHASE_TESTING_PASSED_ID.longValue())
                        && (aimsWapApp.getWapVersion().equals(AimsConstants.WAP_APP_VERSION_WAP_2_0[0])))
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_STATUS_TESTING_PASSED);

                    if (aimsEvent != null)
                    {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());
                        AimsAllianc aimsAllianceOfApplication =
                            (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_CONTENT_TYPE,
                            MiscUtils.getWapAppContentType(aimsWapApp.getContentType()));
                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_PROJECTED_LIVE_DATE,
                            Utility.convertToString(aimsWapApp.getVzwLiveDate(), dateFormat));
                        aimsEvent.raiseEvent(aimsEventObject);
                    }
                }

				//check if status is not saved, and if app network urls changed, if yes then send notification
                if( (newStatus!=null) && (newStatus.longValue() != AimsConstants.SAVED_ID.longValue()) )
                {
                    boolean urlsChangedInSavedState = false;
                    String [] newAppNetworkURLs = wapAppUpdForm.getApplicationURLs();
                    //check if status is changed from saved to another status,
                    // and if application has network urls then set urlsChangedInSavedState flag to true
                    if(oldStatus != null && oldStatus.longValue() == AimsConstants.SAVED_ID.longValue())
                    {
                        if(newAppNetworkURLs!=null && newAppNetworkURLs.length>0)
                        {
                            urlsChangedInSavedState = true;
                        }
                    }
                    if( urlsChangedInSavedState ||
                            ApplicationHelper.checkAppNetworkUrlsChanged(oldAppNetworkURLs, newAppNetworkURLs))
                    {
                        log.debug("Application urls changed. appsId= "+aimsApp.getAppsId());
                        String appTitle = aimsApp.getTitle();
                        String allianceName = AllianceManager.getAllianceCompanyName(aimsApp.getAimsAllianceId());
                        allianceName = StringFuncs.NullValueReplacement(allianceName); //if no alliance found set to blank string

                        //send notification
                        AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_APPLICATION_URLS_CHANGED);
                        if (aimsEvent != null) {
                            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, allianceName);
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, appTitle);
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()));
                            aimsEvent.raiseEvent(aimsEventObject);
                        }
                    }
                }//end send notification if urls changed

                saveMessages(request, WapApplicationHelper.getMessagesOnUpdate(wapAppUpdForm.getAppSubmitType()));
                forward = wapAppUpdForm.getCurrentPage();
            }
        }

        return mapping.findForward(forward);
    }
}
