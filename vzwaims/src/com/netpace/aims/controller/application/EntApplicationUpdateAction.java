package com.netpace.aims.controller.application;

import net.sf.hibernate.HibernateException;

import org.apache.struts.action.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.events.*;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.events.*;
import com.netpace.aims.model.masters.AimsIndustryFocu;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;
import com.netpace.aims.controller.BaseAction;

/**
 * This class takes care of action for display of update form of Enterprise Application.
 *
 * @struts.action path="/entApplicationUpdate"
 *                name="EntApplicationUpdateForm"
 *                scope="request"
 *                input="/application/entApplicationUpdate.jsp"
 *				  validate="true"
 * @struts.action-forward name="page1" path="/application/entApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/entApplicationUpdate2.jsp"
 * @struts.action-forward name="page3" path="/application/entAppProcessInfo.jsp"
 * @struts.action-forward name="page4" path="/application/entJournal.jsp"
 * @struts.action-forward name="page5" path="/application/entApplicationUpdate3.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="entView" path="/application/entApplicationView.jsp"
 * @struts.action-forward name="journalUpdate" path="/application/journalUpdate.jsp"
 * @struts.action-forward name="pageEntBOBO" path="/application/entApplicationUpdateBOBO.jsp"
 * @struts.action-forward name="pageEntLBS" path="/application/entApplicationUpdateLBS.jsp"

 * @author Ahson Imtiaz
 */
public class EntApplicationUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(EntApplicationUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        Long clonedFromAppId = null;
        Long oldStatus = null;
        Long newStatus = null;
        Long[] bdsMktSeg=null;
        boolean saveOnSubmitDueToNoContract=false;
        java.util.Set sOldIndFocus = null;

        String forward = "view";
        String taskname = "";
        Object o_param;
        String lbsBoboJournalEntry=null;
        String isPublishOld = "";
		String isPublishNew = "";

        o_param = request.getParameter("task");

        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        AimsApp aimsApp = null;
        AimsEnterpriseApp aimsEntApp = null;
        AimsContact aimsContact = new AimsContact();
        HashMap appEnt = null;

        //Get Form
        EntApplicationUpdateForm entAppUpdForm = (EntApplicationUpdateForm) form;

        if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")))
        {
        	//Check for contract expires
        	if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)&& (!taskname.equalsIgnoreCase("clone"))){
        		//If have no contract
        		if((JMAApplicationHelper.validateAllianceHasNoContract(currentUserAllianceId, currUserType))
	        			&&(StringFuncs.NullValueReplacement(entAppUpdForm.appSubmitType)).equals(AimsConstants.AIMS_SUBMIT_FORM))
	        	{
        			log.info("JMA Alliance have no contract, so user can not submit the solution, how erver he can save it.");
        			saveOnSubmitDueToNoContract=true;
        			entAppUpdForm.appSubmitType=AimsConstants.AIMS_SAVE_FORM;	
	        	}
        		
        		else if((!JMAApplicationHelper.validateAllianceContract(currentUserAllianceId, currUserType))
	        			&&((StringFuncs.NullValueReplacement(entAppUpdForm.appSubmitType)).equals(AimsConstants.AIMS_SUBMIT_FORM) 
	        					|| (StringFuncs.NullValueReplacement(entAppUpdForm.appSubmitType)).equals(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT)))
	        	{
	        		 /*ActionErrors errors = new ActionErrors();
	                 ActionError error = new ActionError("error.jma.active.accepted.contract");
	                 errors.add(ActionErrors.GLOBAL_ERROR, error);
	                 saveErrors(request, errors);*/
	                 forward="page1";
	                 log.debug("Forwarding to: " + forward);
	                 request.setAttribute("noActiveacceptedContract", "true");
	                 return mapping.findForward(forward);
	        	}
        	}
            if (taskname.equalsIgnoreCase("create"))
            {
                aimsApp = new AimsApp();
                aimsEntApp = new AimsEnterpriseApp();
            }
            else if (taskname.equalsIgnoreCase("edit"))
            {
                try
                {
                    appEnt = AimsEntAppsManager.getApp(entAppUpdForm.getAppsId(), currentUserAllianceId);
                }
                catch (AimsException ae)
                {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                    return mapping.getInputForward();
                }
                aimsApp = (AimsApp) appEnt.get("AimsApp");
                aimsEntApp = (AimsEnterpriseApp) appEnt.get("AimsEntApp");
                
                //User can not edit solution if solution is in submitted state
                if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
                {
	                if(aimsApp.getAimsLifecyclePhaseId().equals(AimsConstants.SUBMISSION_ID) )
	                {
	                	ActionErrors errors = new ActionErrors();
		                ActionError error = new ActionError("error.jma.edit.submittedSolution");
		                errors.add(ActionErrors.GLOBAL_ERROR, error);
		                saveErrors(request, errors);
		                return mapping.findForward(forward);
	                }
                } 
            }
            oldStatus = aimsApp.getAimsLifecyclePhaseId();
        }

        //Common Update Related Tasks				
        try
        {
            forward = ApplicationHelper.updateAction(request, taskname, entAppUpdForm, aimsApp, aimsContact, AimsConstants.ENTERPRISE_PLATFORM_ID, dateFormat);
        }
        catch (AimsException ae)
        {
            saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            return mapping.getInputForward();
        }
     
        //BOBO Contract
        if(forward.equals("pageEntBOBO") || StringFuncs.NullValueReplacement(entAppUpdForm.getCurrentPage()).equals("pageEntBOBO"))
        {
	        log.debug("Setting BOBO contract in the form bean");
	    	try{
	    		AimsApp tempApp=AimsEntAppsManager.getAimsApps(entAppUpdForm.getAppsId());
	    		entAppUpdForm.setBoboContract(JMAApplicationHelper.getAllianceBOBOContract(tempApp.getAimsAllianceId(), currUserType));
	    	}catch(Exception ex)
	    	{
	    		log.error("EntApplicationUpdateAction: error occure while getting BOBO contract");
	    		log.error("Error cooure", ex);
	    	}
        }

        if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")))
        {
            log.debug("APPS_ID after creation of object : " + aimsApp.getAppsId());
            Collection aimsCStudies = entAppUpdForm.getAllCaseStudies();
            Collection aimsRegions = null;
            Collection aimsIndFocuses = null;
            Collection aimsSolutionComponents = null;
            Collection aimsEntAppSubCategories = null;

            //The following properties can only be updated by an Alliance user and not the Verizon user
            //Now following properties can be Verizon user, reference Bug 6319
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)||currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                aimsEntApp.setFortuneCustomers(entAppUpdForm.getFortuneCustomers());
                //aimsEntApp.setCustomerBenefits(entAppUpdForm.getCustomerBenefits());
                //aimsEntApp.setOtherIndFocusValue(entAppUpdForm.getOtherIndFocusValue());
                aimsEntApp.setNumAllUsers(Utility.convertToLong(entAppUpdForm.getTotalEndUsers()));
                aimsEntApp.setNumWirelessUsers(Utility.convertToLong(entAppUpdForm.getNoOfUsersAccess()));
                aimsEntApp.setPlatformDepMode(entAppUpdForm.getPlatformDepMode());
                aimsEntApp.setCustSupportPhone(entAppUpdForm.getCustSupportPhone());
                aimsEntApp.setCustSupportEmail(entAppUpdForm.getCustSupportEmail());
                aimsEntApp.setCustSupportHours(entAppUpdForm.getCustSupportHours());
                
                aimsEntApp.setDevices(entAppUpdForm.getDevices());
                aimsEntApp.setProductExclusiveToVzw(entAppUpdForm.getProductExclusiveToVzw());
                aimsEntApp.setProductInformation(entAppUpdForm.getProductInformation());
                aimsEntApp.setBriefDescription(entAppUpdForm.getBriefDescription());
                aimsEntApp.setAdditionalInformation(entAppUpdForm.getAdditionalInformation());
                aimsEntApp.setIsProductExeclusiveToVZW(entAppUpdForm.getIsProductExeclusiveToVZW());
                aimsEntApp.setIsGoExclusiveWithVZW(entAppUpdForm.getIsGoExclusiveWithVZW());
                aimsEntApp.setIsProductUseVzwVzNt(entAppUpdForm.getIsProductUseVzwVzNt());
                aimsEntApp.setIsProductCertifiedVZW(entAppUpdForm.getIsProductCertifiedVZW());
                aimsEntApp.setIsProductCertifiedODIProcess(entAppUpdForm.getIsProductCertifiedODIProcess());
                aimsEntApp.setIsProductRequiedLBS(entAppUpdForm.getIsProductRequiedLBS());
                aimsEntApp.setIsOfferBoboServices(entAppUpdForm.getIsOfferBoboServices());
               
                if(entAppUpdForm.getIsInterestedInBOBO()!=null && entAppUpdForm.getIsInterestedInBOBO().equals("Y")){
                	aimsEntApp.setIsInterestedInBOBO(entAppUpdForm.getIsInterestedInBOBO());
                }
                else{
                	aimsEntApp.setIsInterestedInBOBO("N");
                }
               
                if(entAppUpdForm.getIsInterestedInLBS()!=null && entAppUpdForm.getIsInterestedInLBS().equals("Y")){
                	aimsEntApp.setIsInterestedInLBS(entAppUpdForm.getIsInterestedInLBS());
                }
                else{
                	aimsEntApp.setIsInterestedInLBS("N");
                }
                
                if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))//Only JMA Partner can accept/reject
                {
	                //Code for accept reject LBS 
	                String acceptDeclineLBS=StringFuncs.NullValueReplacement(entAppUpdForm.getAcceptDeclinBoboLbs());
	                lbsBoboJournalEntry=acceptDeclineLBS;//for journal entry
	                if(acceptDeclineLBS.equals(AimsConstants.AIMS_USER_ACCEPT_LBS)){
	                	aimsEntApp.setIsLbsAcceptByAlliance("Y");
	                }
	                if(acceptDeclineLBS.equals(AimsConstants.AIMS_USER_REJECT_LBS)){
	                	aimsEntApp.setIsLbsAcceptByAlliance("N");
	                }
                }
                //code for resubmit solution
                String resumbitSolution= StringFuncs.NullValueReplacement(entAppUpdForm.getResubmitSolution());
                if(resumbitSolution.equals(AimsConstants.AIMS_USER_RESUBMIT_SOLUTION) && aimsApp.getAimsLifecyclePhaseId().equals(AimsConstants.REJECTED_ID)){
                	aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUBMISSION_ID);
                }
                    

                if (entAppUpdForm.getRegionId() != null)
                    aimsRegions = AimsEntAppsManager.getAimsRegions(entAppUpdForm.getRegionId());

                //if (entAppUpdForm.getEntAppSubCategoryId() != null)
                //    aimsEntAppSubCategories = AimsEntAppsManager.getEntAppSubCategories(entAppUpdForm.getEntAppSubCategoryId());

                if (entAppUpdForm.getIndustryFocusId() != null)
                    aimsIndFocuses = AimsEntAppsManager.getAimsIndustryFocuses(entAppUpdForm.getIndustryFocusId());

                //if (entAppUpdForm.getSolutionComponentId() != null)
                 //   aimsSolutionComponents = (Set) request.getSession().getAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP);
            }

            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
            	/*
                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_ASSIGN)) //JMA application not allowed to save it
                {
                    if (entAppUpdForm.getAllianceSponsor().longValue() == 0)
                        aimsEntApp.setAllianceSponsor(null);
                    else
                        aimsEntApp.setAllianceSponsor(entAppUpdForm.getAllianceSponsor());
                }*/
                    
                String appSubmitType=StringFuncs.NullValueReplacement(entAppUpdForm.getAcceptDeclinBoboLbs());
                String currentUsereEmail=((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getAimsContact().getEmailAddress();
                Date acceptDeclineDate=new Date();
                lbsBoboJournalEntry=appSubmitType;//for journal entry
                   
                if(appSubmitType.equals(AimsConstants.AIMS_VZW_ACCEPT_BOBO)){
                	if(JMAApplicationHelper.validateAllianceBOBOContract(aimsApp.getAimsAllianceId(), currUserType)){
	                    aimsEntApp.setIsBoboAccept("Y");
	                    aimsEntApp.setBoboAcceptDeclineDate(acceptDeclineDate);
	                    aimsEntApp.setBoboAcceptDeclineByEmailId(currentUsereEmail);
                	}
                    else{
                    	ActionErrors errors = new ActionErrors();
		                ActionError error = new ActionError("error.entApp.acceptBobob.noActiveBoboContract");
		                errors.add(ActionErrors.GLOBAL_ERROR, error);
		                saveErrors(request, errors);
		                log.info("EntApplicationUpdateAction: JMA partnert dont have active accepted contracr.");
		                entAppUpdForm.setIsBoboAccept("N");
		                return mapping.findForward("pageEntBOBO");
                    }
                }
                else if(appSubmitType.equals(AimsConstants.AIMS_VZW_REJECT_BOBO)){
             
                    aimsEntApp.setIsBoboAccept("N");
                    aimsEntApp.setBoboAcceptDeclineDate(acceptDeclineDate);
                    aimsEntApp.setBoboAcceptDeclineByEmailId(currentUsereEmail);
                }
                else if(appSubmitType.equals(AimsConstants.AIMS_VZW_ACCEPT_LBS)){
                	aimsEntApp.setIsLbsAcceptByAlliance("");
                    aimsEntApp.setIsLbsAccept("Y");
                    aimsEntApp.setLbsAcceptDeclineByEmailId(currentUsereEmail);
                    aimsEntApp.setLbsAcceptDeclineDate(acceptDeclineDate);
                }
                else if(appSubmitType.equals(AimsConstants.AIMS_VZW_REJECT_LBS)){
                	aimsEntApp.setIsLbsAcceptByAlliance("");
                     aimsEntApp.setIsLbsAccept("N");
                     aimsEntApp.setLbsAcceptDeclineByEmailId(currentUsereEmail);
                    aimsEntApp.setLbsAcceptDeclineDate(acceptDeclineDate);
                }
               
                //User can update 'published' and 'featured', If application is accepted
                Long phaseId=entAppUpdForm.getAimsLifecyclePhaseId();
                if(phaseId!=null && phaseId.equals(AimsConstants.ACCEPTANCE_ID))
                {
                	isPublishOld=StringFuncs.NullValueReplacement(aimsEntApp.getIsPublished());
                	isPublishNew = StringFuncs.NullValueReplacement(entAppUpdForm.getIsPublished());
                	aimsEntApp.setIsPublished(entAppUpdForm.getIsPublished());
                	aimsEntApp.setIsFeatured(entAppUpdForm.getIsFeatured());
                }
                
                bdsMktSeg=JMAApplicationHelper.getBdsMarketSegmentId(entAppUpdForm.getEntMarketSegInfo());
            }

            java.util.Set cHS = null;
            java.util.Set pHS = null;

            /*
            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_TESTING))
                    if ((java.util.Set) session.getAttribute(ManageApplicationsConstants.SESSION_VAR_PHASES) != null)
                        pHS = (java.util.Set) session.getAttribute(ManageApplicationsConstants.SESSION_VAR_PHASES);
             */
            //Check if the app is being cloned
            if ((entAppUpdForm.getAppsId().longValue() != 0) && (aimsApp.getAppsId() == null))
                clonedFromAppId = entAppUpdForm.getAppsId();
            
           sOldIndFocus=aimsEntApp.getAimsEntAppsIndFocus();


            try
            {
                AimsEntAppsManager.saveOrUpdate(
                    aimsApp,
                    aimsContact,
                    aimsEntApp,
                    currUser,
                    currUserType,
                    entAppUpdForm.getFlashDemoTempFileId(),
                    entAppUpdForm.getFaqDocTempFileId(),
                    entAppUpdForm.getUserGuideTempFileId(),
                    entAppUpdForm.getTestPlanResultsTempFileId(),
                    entAppUpdForm.getPresentationTempFileId(),
                    entAppUpdForm.getBoboLetterOfAuthTempFileId(),
                    entAppUpdForm.getLbsContractTempFileId(),
                    aimsSolutionComponents,
                    aimsRegions,
                    aimsEntAppSubCategories,
                    aimsIndFocuses,
                    aimsCStudies,
                    cHS,
                    pHS,
                    clonedFromAppId,
                    entAppUpdForm.getEntProductInfo(),
                    entAppUpdForm.getEntMarketSegInfo(),
                    bdsMktSeg);
                
                JMAApplicationHelper.journalEntry(lbsBoboJournalEntry, aimsApp.getAppsId(),((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername(),currUserType); 
                if(!isPublishOld.equals("Y") && isPublishNew.equals("Y"))
				{
					 StringBuffer journalText=new StringBuffer();
					 journalText.append("Application ")
					 			.append("\"").append(aimsApp.getTitle()).append("\"").append(" ")
					 			.append("Published by "+currUser);
					 
					 log.debug("Journal Entry :"+journalText.toString());
					 JMAApplicationHelper.journalEntry(journalText.toString(), AimsConstants.JOURNAL_TYPE_PRIVATE, aimsApp.getAimsAllianceId(), aimsApp.getAppsId(), currUser);
					
				}
                if(isPublishOld.equals("Y") && isPublishNew.equals("N"))
				{
					 StringBuffer journalText=new StringBuffer();
					 journalText.append("Application ")
					 			.append("\"").append(aimsApp.getTitle()).append("\"").append(" ")
					 			.append("Unpublished by "+currUser);
					 
					 log.debug("Journal Entry :"+journalText.toString());
					 JMAApplicationHelper.journalEntry(journalText.toString(), AimsConstants.JOURNAL_TYPE_PRIVATE, aimsApp.getAimsAllianceId(), aimsApp.getAppsId(), currUser);
					
				}
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }

            //Post Update Tasks
            ActionMessages messages=new ActionMessages(); 
            //If save on submit request, Bug# 6332
            if(saveOnSubmitDueToNoContract)
            {
            	ActionMessage message = null;
            	message = new ActionMessage("message.jmaAlliance.hasnoContract.jmaApp.save.success");
            	messages.add(ActionMessages.GLOBAL_MESSAGE, message);
            }
            else
            {
             messages =
                ApplicationHelper.postUpdateAction(request, taskname, entAppUpdForm, aimsApp, AimsConstants.ENTERPRISE_PLATFORM_ID, dateFormat);
            }

            newStatus = entAppUpdForm.getAimsLifecyclePhaseId();

            if(!(StringFuncs.NullValueReplacement(entAppUpdForm.getResubmitSolution()).equals(AimsConstants.AIMS_USER_RESUBMIT_SOLUTION)))//if not resubmit solution request
            if (((oldStatus != null) && (oldStatus.longValue() != newStatus.longValue()))
                || ((oldStatus == null) && (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())))
            {
                AimsEventLite aimsEvent = null;

                if (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_APP_STATUS_SUBMITTED);

                /*else if (newStatus.longValue() == AimsConstants.ASSIGNED_ID.longValue())
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_APP_STATUS_ASSIGNED);

                else if (newStatus.longValue() == AimsConstants.TESTING_ID.longValue())
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_APP_STATUS_UNDER_TESTING);
				*/
                else if (newStatus.longValue() == AimsConstants.ACCEPTANCE_ID.longValue())
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_APP_STATUS_ACCEPTED);

                else if (newStatus.longValue() == AimsConstants.REJECTED_ID.longValue())
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_APP_STATUS_REJECTED);

                else
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_APP_STATUS_CHANGE);

                if (aimsEvent != null)
                {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

                    /*
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_USER_IDS, currUserId.toString());
                    
                    OR
                    
                    Vector userIds = new Vector();
                    userIds.add(currUserId.toString());
                    userIds.add("4624");
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_USER_IDS, userIds.toString().substring(1,userIds.toString().length()-1));
                    */

                    AimsAllianc aimsAllianceOfApplication =
                        (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, aimsApp.getAimsAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());

                    AimsUser aimAllianceAdminUser =
                        (AimsUser) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsUser.class,
                            aimsAllianceOfApplication.getAimsUserByAdminUserId().toString());
                    AimsContact aimAllianceAdminContact =
                        (AimsContact) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsContact.class,
                            aimAllianceAdminUser.getAimsContactId().toString());
                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                        aimAllianceAdminContact.getFirstName() + " " + aimAllianceAdminContact.getLastName());

                    if (aimsApp.getAimsVzwAppsContactId() != null)
                    {
                        AimsContact aimsVzwAppsContact =
                            (AimsContact) DBHelper.getInstance().load(
                                com.netpace.aims.model.core.AimsContact.class,
                                aimsApp.getAimsVzwAppsContactId().toString());

                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_APPLICATION_ASSIGNEE,
                            aimsVzwAppsContact.getFirstName() + " " + aimsVzwAppsContact.getLastName());
                    }

                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());

                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_APP_STATUS_NEW,
                        ((AimsLifecyclePhase) DBHelper.getInstance().load(com.netpace.aims.model.application.AimsLifecyclePhase.class, newStatus.toString()))
                            .getPhaseName());

                    if ((aimsApp.getVersion() != null) && (!StringFuncs.isEmpty(aimsApp.getVersion())))
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, aimsApp.getVersion());

                    if (oldStatus != null)
                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_APP_STATUS_OLD,
                            ((AimsLifecyclePhase) DBHelper
                                .getInstance()
                                .load(com.netpace.aims.model.application.AimsLifecyclePhase.class, oldStatus.toString()))
                                .getPhaseName());

                    aimsEvent.raiseEvent(aimsEventObject);
                }
            }
            
            //sending notification if partner resubmit solution
            if ((currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)) && (StringFuncs.NullValueReplacement(entAppUpdForm.getResubmitSolution()).equals(AimsConstants.AIMS_USER_RESUBMIT_SOLUTION)))
            {
            	try
            	{
	            	AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_PARTNER_RESUBMIT_SOLUTION);
	            	if(aimsEvent!=null)
	            	{
	            		  AimsAllianc aimsAllianceOfApplication =
	                          (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, aimsApp.getAimsAllianceId().toString());
						AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
						aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
						aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
						aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE,aimsApp.getTitle());
		
						//must set these properties
						aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID,aimsApp.getAimsAllianceId()+"");
						aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId()+"");
		
						aimsEvent.raiseEvent(aimsEventObject);
	            	}
            	}
            	catch(Exception ex)
            	{
            		log.error("EntApplicationUpdateAction: error occure while sending resubmit notification ");
            	}
            	
            	//
            	 saveMessages(request, messages);
            	 return mapping.findForward("view");
            }
            
            //Send Notification for industry focused changed.
            Long [] oldIndFocusIds=null;
            if (sOldIndFocus != null)
            {
            	oldIndFocusIds = new Long[sOldIndFocus.size()];
                int iCount = 0;
                for (Iterator it = sOldIndFocus.iterator(); it.hasNext();)
                {
                	oldIndFocusIds[iCount++] = ((AimsIndustryFocu) it.next()).getIndustryId();
                }
            }
            this.sendEmailForIndustryFocusChanged(oldIndFocusIds,entAppUpdForm.getIndustryFocusId(),aimsApp,session); 
            
            entAppUpdForm.setPresentationTempFileId(new Long(0));
            entAppUpdForm.setBoboLetterOfAuthTempFileId(new Long(0));
            entAppUpdForm.setLbsContractTempFileId(new Long(0));
            if(JMAApplicationHelper.displayTabBOBO(currUserType, aimsApp, aimsEntApp)){
            	entAppUpdForm.setDisplayTabBOBO("Y");
            }
            else{
            	entAppUpdForm.setDisplayTabBOBO("N");
            }
            
            if(JMAApplicationHelper.displayTabLBS(currUserType, aimsEntApp)){
            	entAppUpdForm.setDisplayTabLBS("Y");
            }
            else{
            	entAppUpdForm.setDisplayTabLBS("N");
            }
            //Need to update form bean for LBS and BOBO updated in DB
            entAppUpdForm.setIsBoboAccept(aimsEntApp.getIsBoboAccept());
            entAppUpdForm.setIsLbsAccept(aimsEntApp.getIsLbsAccept());
            entAppUpdForm.setIsLbsAcceptByAlliance(aimsEntApp.getIsLbsAcceptByAlliance());
            
            //Setting the Application Status for Page Header
            if ((aimsApp.getAppsId() != null) && (aimsApp.getAppsId().longValue() != 0)) {
                AimsLifecyclePhase aimsPhaseOfApplication =
                    (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, aimsApp.getAimsLifecyclePhaseId().toString());
                entAppUpdForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
            }
            else {
            	entAppUpdForm.setApplicationStatus("NEW");
            }
            
            saveMessages(request, messages);
            forward = entAppUpdForm.getCurrentPage();
        }

        log.debug("Forwarding to: " + forward);
        return mapping.findForward(forward);
    }
    
    private void sendEmailForIndustryFocusChanged(Long[] oldIndFocusIds,Long[] newIndFocusIds,AimsApp aimsApp,HttpSession session )
    {
    	AimsIndustryFocu indFocus=null;
    	try
    	{ 
    		if(oldIndFocusIds!=null && newIndFocusIds!=null)
    		{
    			for(int i=0;i<oldIndFocusIds.length;i++)
    			{
    				if(bruteFource(oldIndFocusIds[i],newIndFocusIds))
    				{
    					indFocus=AimsEntAppsManager.getIndustryFocus(oldIndFocusIds[i]);
    					sendVerticalChangerNotificatio(false, indFocus, aimsApp);
    				}
    			
    			}
    		}
    		
    		//
    		if(oldIndFocusIds!=null && newIndFocusIds!=null)
    		{
    			for(int i=0;i<newIndFocusIds.length;i++)
    			{
    				if(bruteFource(newIndFocusIds[i], oldIndFocusIds))
    				{
    					indFocus=AimsEntAppsManager.getIndustryFocus(newIndFocusIds[i]);
    					sendVerticalChangerNotificatio(true, indFocus, aimsApp);
    				}
    			}
    		}
    		
    	}
    	catch(Exception ex)
    	{
    		log.error("Error occur", ex);
    	}
    }
    
    /**
     * Check vertical changed
     * @param id
     * @param ids
     * @return return TRUE if id is not exist in ids else false
     */
    private boolean bruteFource(Long id,Long[] ids)
    {
    	boolean flag=true;
    	if(id!=null && ids!=null)
    	{
    		for(int i=0;i<ids.length;i++)
    		{
    			if(id.equals(ids[i]))
    			{
    				flag=false;//vertical not changed
    				break;
    			}
    		}
    	}
    	return flag;
    }
    

    private void sendVerticalChangerNotificatio(boolean checked,AimsIndustryFocu indFocus, AimsApp aimsApp)
    {
    	try
    	{
    		AimsEventLite aimsEvent =null;
    		if(checked)
    			aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_APP_VERTICAL_CHECKED);
    		else
    			aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_APP_VERTICAL_UNCHECKED);
    		
    		if (aimsEvent != null) {
    			AimsAllianc aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId() .toString());
				
    			AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
    			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT,  (new Date()).toString());
    			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
    			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
    			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VERTICAL_NAME, indFocus.getIndustryName());
    			
    			//must set these properties
    			aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId() .toString());
    		    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());
    		    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ADHOC_EMAILS, indFocus.getEmailAddress());

    			aimsEvent.raiseEvent(aimsEventObject);
    		}
    		
    	}
    	catch(Exception ex)
    	{
    		log.error("Exception occure while sending notification for vertical changed", ex);
    	}
    }
    
   
}