package com.netpace.aims.controller.application;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsAppSubCategory;
import com.netpace.aims.model.application.AimsEntAppSolComp;
import com.netpace.aims.model.application.AimsEnterpriseApp;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.application.AimsSolutionComponent;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.masters.AimsIndustryFocu;
import com.netpace.aims.model.masters.AimsRegion;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for display of update form of Enterprise Application.
 *
 * @struts.action path="/entApplicationSetup"
 *                name="EntApplicationUpdateForm"
 *                scope="request"
 *                input="/applicationsViewDelete.do"
 *				  validate="false"
 * @struts.action-forward name="update" path="/application/entApplicationUpdate.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="entView" path="/application/entApplicationView.jsp"
 * @struts.action-forward name="processInfoView" path="/application/entAppProcessInfoView.jsp"
 * @struts.action-forward name="journalUpdate" path="/application/journalUpdate.jsp"
 * @author Ahson Imtiaz
 */
public class EntApplicationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(EntApplicationSetupAction.class.getName());

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

        if (taskname.equalsIgnoreCase("onHold"))
        {
            try
            {
                AimsApplicationsManager.updateOnHold(new Long(request.getParameter("appsId")), request.getParameter("onHold"), currUser);
            }
            catch (Exception ex)
            {
                //Log or display error
                //saveErrors(request, DBErrorFinder.populateActionErrors(ae));						
            }
            return mapping.getInputForward();
        }

        AimsApp aimsApp = null;
        AimsEnterpriseApp aimsEntApp = null;
        AimsAppCategory aimsAppCategory = null;
        HashMap appEnt = null;

        //Get Application Information
        if (!(taskname.equalsIgnoreCase("create")))
        {
            try
            {
                appEnt = AimsEntAppsManager.getApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }
            aimsApp = (AimsApp) appEnt.get("AimsApp");
            aimsEntApp = (AimsEnterpriseApp) appEnt.get("AimsEntApp");
            aimsAppCategory = (AimsAppCategory) appEnt.get("AimsAppCategory");
            
        }

        //Get Form
        EntApplicationUpdateForm entAppUpdForm = (EntApplicationUpdateForm) form;

        //Common Setup Related Tasks
        ApplicationHelper.setupAction(request, taskname, entAppUpdForm, aimsApp, aimsAppCategory, AimsConstants.ENTERPRISE_PLATFORM_ID, dateFormat);

        entAppUpdForm.setSetupURL(
            this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.enterprise.app.setup.url"));
        entAppUpdForm.setUpdateURL(
            this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.enterprise.app.update.url"));

        //Set Temp File Ids to Zero
        entAppUpdForm.setPresentationTempFileId(new Long(0));
        entAppUpdForm.setBoboLetterOfAuthTempFileId(new Long(0));
        entAppUpdForm.setLbsContractTempFileId(new Long(0));

        //Start of Delete related Task
        if (taskname.equalsIgnoreCase("delete"))
        {
        	 if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
             {
	        	//If have no active accepted contract
	        	if(!JMAApplicationHelper.validateAllianceContract(currentUserAllianceId, currUserType)){
	        		throw new AimsSecurityException();
	        	}
             }
            try
            {
                AimsApplicationsManager.deleteApp(new Long(request.getParameter("appsId")), currUser);
                AimsEventLite aimsEvent = null;
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_APP_DELETED);
                if (aimsEvent != null)
                {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

                    //Here we are explicitly sending UserID of Application Assignee (to the stored proc to be mailed). 
                    //This is because our stored procedure cannot fetch the Application Assignee by the 'APPS_ID' as the 'APPS_ID' is already deleted.
                    if (aimsApp.getAimsVzwAppsContactId() != null)
                    {
                        Long otherUserId = AimsApplicationsManager.getUserIdForContactId(aimsApp.getAimsVzwAppsContactId());
                        if (otherUserId != null)
                            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_USER_IDS, otherUserId.toString());
                    }

                    AimsAllianc aimsAllianceForEvent =
                        (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

                    AimsUser aimAllianceAdminUser =
                        (AimsUser) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsUser.class,
                            aimsAllianceForEvent.getAimsUserByAdminUserId().toString());

                    AimsContact aimAllianceAdminContact =
                        (AimsContact) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsContact.class,
                            aimAllianceAdminUser.getAimsContactId().toString());

                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceForEvent.getCompanyName());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                        aimAllianceAdminContact.getFirstName() + " " + aimAllianceAdminContact.getLastName());

                    if ((aimsApp.getVersion() != null) && (!StringFuncs.isEmpty(aimsApp.getVersion())))
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, aimsApp.getVersion());

                    if (aimsApp.getAimsLifecyclePhaseId() != null)
                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_APP_STATUS_OLD,
                            ((AimsLifecyclePhase) DBHelper
                                .getInstance()
                                .load(com.netpace.aims.model.application.AimsLifecyclePhase.class, aimsApp.getAimsLifecyclePhaseId().toString()))
                                .getPhaseName());

                    aimsEvent.raiseEvent(aimsEventObject);
                }
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            }
            return mapping.getInputForward();
        }
        //End of Delete related Task

        if (taskname.equalsIgnoreCase("create"))
        {
            entAppUpdForm.setAllCaseStudies(new java.util.HashSet());
            session.setAttribute("AIMS_APP_CASE_STUDIES", entAppUpdForm.getAllCaseStudies());

            session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_REGIONS_LIST);
            entAppUpdForm.setRegionId(null);

            session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_IND_FOCUS_LIST);
            entAppUpdForm.setIndustryFocusId(null);

            session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP_ID_ONLY);
            entAppUpdForm.setSolutionComponentId(null);

            session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_ENT_APP_SUB_CAT);
            entAppUpdForm.setEntAppSubCategoryId(null);

            session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP);
            entAppUpdForm.setSolCompDescWhole(null);

            //entAppUpdForm.setIsBoboAccept("N");
            //entAppUpdForm.setIsLbsAccept("N");
           // entAppUpdForm.setIsLbsAcceptByAlliance("N");
            entAppUpdForm.setApplicationStatus("New");
            forward = "update";
        }

        if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view")))
        {
            entAppUpdForm.setAllCaseStudies(new java.util.HashSet());
            session.setAttribute("AIMS_APP_CASE_STUDIES", entAppUpdForm.getAllCaseStudies());

            entAppUpdForm.setAllianceSponsor(aimsEntApp.getAllianceSponsor());
            entAppUpdForm.setCustSupportPhone(aimsEntApp.getCustSupportPhone());
            entAppUpdForm.setNoOfUsersAccess(Utility.convertToString(aimsEntApp.getNumWirelessUsers()));
            entAppUpdForm.setTotalEndUsers(Utility.convertToString(aimsEntApp.getNumAllUsers()));
            entAppUpdForm.setPlatformDepMode(aimsEntApp.getPlatformDepMode());
            entAppUpdForm.setCustSupportEmail(aimsEntApp.getCustSupportEmail());
            entAppUpdForm.setCustSupportHours(aimsEntApp.getCustSupportHours());
            entAppUpdForm.setFortuneCustomers(aimsEntApp.getFortuneCustomers());
            entAppUpdForm.setCustomerBenefits(aimsEntApp.getCustomerBenefits());
            entAppUpdForm.setOtherIndFocusValue(aimsEntApp.getOtherIndFocusValue());

            entAppUpdForm.setPresentationFileName(aimsEntApp.getPresentationFileName());
            entAppUpdForm.setBoboLetterOfAuthFileName(aimsEntApp.getBoboLetterOfAuthFileName());
            
            entAppUpdForm.setDevices(aimsEntApp.getDevices());
            entAppUpdForm.setProductExclusiveToVzw(aimsEntApp.getProductExclusiveToVzw() );
            entAppUpdForm.setProductInformation(aimsEntApp.getProductInformation());
            entAppUpdForm.setBriefDescription(aimsEntApp.getBriefDescription());
            entAppUpdForm.setAdditionalInformation(aimsEntApp.getAdditionalInformation());
            entAppUpdForm.setBoboLetterOfAuthFileName(aimsEntApp.getBoboLetterOfAuthFileName());
            entAppUpdForm.setLbsContractFileName(aimsEntApp.getLbsContractFileName());
            //entAppUpdForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
            
            
            entAppUpdForm.setIsGoExclusiveWithVZW(aimsEntApp.getIsGoExclusiveWithVZW());
            entAppUpdForm.setIsProductExeclusiveToVZW(aimsEntApp.getIsProductExeclusiveToVZW());
            entAppUpdForm.setIsProductUseVzwVzNt(aimsEntApp.getIsProductUseVzwVzNt());
            entAppUpdForm.setIsProductCertifiedVZW(aimsEntApp.getIsProductCertifiedVZW());
            entAppUpdForm.setIsProductCertifiedODIProcess(aimsEntApp.getIsProductCertifiedODIProcess());
            entAppUpdForm.setIsProductRequiedLBS(aimsEntApp.getIsProductRequiedLBS());
            entAppUpdForm.setIsOfferBoboServices(aimsEntApp.getIsOfferBoboServices());
            entAppUpdForm.setIsInterestedInLBS(aimsEntApp.getIsInterestedInLBS());
            entAppUpdForm.setIsInterestedInBOBO(aimsEntApp.getIsInterestedInBOBO());
            
        
            //For cloning don't display BOB and LBS, BOBO and LBS are not part of cloning
            if(taskname.equalsIgnoreCase("clone"))
            {
            	entAppUpdForm.setIsBoboAccept("");
            	entAppUpdForm.setIsLbsAccept("");
            	entAppUpdForm.setIsLbsAcceptByAlliance("");
            	entAppUpdForm.setIsPublished("");
 	            entAppUpdForm.setIsFeatured("");
            	entAppUpdForm.setDisplayTabBOBO("N");
            	entAppUpdForm.setDisplayTabLBS("N");
            	entAppUpdForm.setApplicationStatus("NEW");
            }
            else
            {
	            entAppUpdForm.setIsBoboAccept(aimsEntApp.getIsBoboAccept());
	            entAppUpdForm.setIsLbsAccept(aimsEntApp.getIsLbsAccept());
	            
	            if(JMAApplicationHelper.displayTabBOBO(currUserType, aimsApp, aimsEntApp))
	            	entAppUpdForm.setDisplayTabBOBO("Y");
	            else
	            	entAppUpdForm.setDisplayTabBOBO("N");
	            
	            if(JMAApplicationHelper.displayTabLBS(currUserType, aimsEntApp))
	            	entAppUpdForm.setDisplayTabLBS("Y");
	            else
	            	entAppUpdForm.setDisplayTabLBS("N");
	           
	     
	            entAppUpdForm.setIsLbsAcceptByAlliance(aimsEntApp.getIsLbsAcceptByAlliance());
	            entAppUpdForm.setIsPublished(aimsEntApp.getIsPublished());
	            entAppUpdForm.setIsFeatured(aimsEntApp.getIsFeatured());
	            
	          //Setting the Application Status for Page Header
	            if ((aimsApp.getAppsId() != null) && (aimsApp.getAppsId().longValue() != 0)) {
	                AimsLifecyclePhase aimsPhaseOfApplication =
	                    (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, aimsApp.getAimsLifecyclePhaseId().toString());
	                entAppUpdForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
	            }
	            else {
	            	entAppUpdForm.setApplicationStatus("NEW");
	            }
            }
            Long[] lngVV = null;
            java.util.Set sTemp = null;

            //Solution Components
            lngVV = null;

            sTemp = aimsEntApp.getAimsEntAppsSolComp();
            if (sTemp != null)
            {
                lngVV = new Long[sTemp.size()];
                int iCount = 0;
                for (Iterator it = sTemp.iterator(); it.hasNext();)
                {
                    lngVV[iCount++] = ((AimsEntAppSolComp) it.next()).getSolutionComponentId();
                }
            }

            entAppUpdForm.setSolutionComponentId(lngVV);
            if (lngVV != null)
                session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP_ID_ONLY, lngVV);
            else
                session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP_ID_ONLY);

            //Solution Components with Description
            Collection allSolutionComponents = AimsEntAppsManager.getAimsSolutionComponents();
            Set solutionComponentSet = aimsEntApp.getAimsEntAppsSolComp();

            if (allSolutionComponents != null)
            {

                String[] solCompName = new String[allSolutionComponents.size()];
                Long[] solCompId = new Long[allSolutionComponents.size()];
                String[] solCompDesc = new String[allSolutionComponents.size()];

                int index = 0;
                for (java.util.Iterator itr = allSolutionComponents.iterator(); itr.hasNext();)
                {
                    AimsSolutionComponent asc = (AimsSolutionComponent) itr.next();
                    solCompName[index] = asc.getSolutionComponentName();
                    solCompId[index] = asc.getSolutionComponentId();
                    if ((solutionComponentSet != null) && (solutionComponentSet.size() > 0))
                    {
                        for (java.util.Iterator itre = solutionComponentSet.iterator(); itre.hasNext();)
                        {
                            AimsEntAppSolComp aesc = (AimsEntAppSolComp) itre.next();
                            if (aesc.getAimsSolutionComponent().equals(asc))
                                solCompDesc[index] = aesc.getSolutionDescription();
                        }
                    }
                    index++;
                }
                entAppUpdForm.setSolCompNameWhole(solCompName);
                entAppUpdForm.setSolCompIdWhole(solCompId);
                entAppUpdForm.setSolCompDescWhole(solCompDesc);
            }
            session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP, aimsEntApp.getAimsEntAppsSolComp());

            //Ent App Sub Categories
            lngVV = null;

            sTemp = aimsEntApp.getAimsEntAppsSubCategories();
            if (sTemp != null)
            {
                lngVV = new Long[sTemp.size()];
                int iCount = 0;
                for (Iterator it = sTemp.iterator(); it.hasNext();)
                {
                    lngVV[iCount++] = ((AimsAppSubCategory) it.next()).getSubCategoryId();
                }
            }

            entAppUpdForm.setEntAppSubCategoryId(lngVV);
            if (lngVV != null)
                session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_ENT_APP_SUB_CAT, lngVV);
            else
                session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_ENT_APP_SUB_CAT);

            //Regions
            lngVV = null;

            sTemp = aimsEntApp.getAimsEntAppsRegion();
            if (sTemp != null)
            {
                lngVV = new Long[sTemp.size()];
                int iCount = 0;
                for (Iterator it = sTemp.iterator(); it.hasNext();)
                {
                    lngVV[iCount++] = ((AimsRegion) it.next()).getRegionId();
                }
            }

            entAppUpdForm.setRegionId(lngVV);
            if (lngVV != null)
                session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_REGIONS_LIST, lngVV);
            else
                session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_REGIONS_LIST);

            //Industry Focuses
            lngVV = null;

            sTemp = aimsEntApp.getAimsEntAppsIndFocus();
            if (sTemp != null)
            {
                lngVV = new Long[sTemp.size()];
                int iCount = 0;
                for (Iterator it = sTemp.iterator(); it.hasNext();)
                {
                    lngVV[iCount++] = ((AimsIndustryFocu) it.next()).getIndustryId();
                }
            }

            entAppUpdForm.setIndustryFocusId(lngVV);
            if (lngVV != null)
                session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_IND_FOCUS_LIST, lngVV);
            else
                session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_IND_FOCUS_LIST);

            //Product Information
            entAppUpdForm.setEntProductInfo(AimsEntAppsManager.getProductInformation(aimsEntApp.getEnterpriseAppsId()));
            //Market Segment Information
            entAppUpdForm.setEntMarketSegInfo(AimsEntAppsManager.getMarketSegmentInfo(aimsEntApp.getEnterpriseAppsId()));  
            
            //Case Studies					
            entAppUpdForm.setAllCaseStudies(AimsEntAppsManager.getCaseStudies(aimsApp.getAppsId()));
            session.setAttribute("AIMS_APP_CASE_STUDIES", entAppUpdForm.getAllCaseStudies());

            if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")))
            {
                forward = "update";
            }
            else if (taskname.equalsIgnoreCase("view"))
            {
                if (viewPageToView.equals(""))
                    forward = "entView";
                else if (viewPageToView.equals("processingInfo"))
                {
                    entAppUpdForm.setCurrentPage("processingInfo");
                    forward = "processInfoView";
                }
            }
        }

        log.debug("Forward String Before Forwarding: " + forward);
        return mapping.findForward(forward);
    }
}