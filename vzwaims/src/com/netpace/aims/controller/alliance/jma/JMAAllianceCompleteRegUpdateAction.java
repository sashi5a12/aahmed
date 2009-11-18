package com.netpace.aims.controller.alliance.jma;

import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.ObjectNotFoundException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.JMAAllianceRegistrationManager;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.logincontent.AimsLoginContentManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.alliance.AllianceJournalEntryUpdateAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsJMAAlliance;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * @struts.action path="/jmaAllianceCompleteRegistrationUpdate"
 * 				  name="JMAAllianceCompleteRegistrationForm"
 * 				  scope="request"
 * 				   input="/alliance/jma/jmaAllianceCompleteRegistration.jsp"	
 * 				  validate="true"
 * @struts.action-forward name="submitted" path="/alliance/jma/jmaAllianceCompleteRegistration.jsp"
 * @struts.action-forward name="error" path="/alliance/jma/jmaAllianceCompleteRegistration.jsp"
 * @struts.action-forward name="save" path="/alliance/jma/jmaAllianceCompleteRegistration.jsp"					
 * @author aqureshi
 *
 */
public class JMAAllianceCompleteRegUpdateAction extends BaseAction {

	static Logger log = Logger.getLogger(JMAAllianceCompleteRegUpdateAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		JMAAllianceCompleteRegistrationForm completeInfoForm=(JMAAllianceCompleteRegistrationForm) form;
		HttpSession session = request.getSession();
		String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
		String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
		Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
		Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
		String taskname = request.getParameter("task");
		Long loginContentId = (Long)session.getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW);
		
		//Create JMA Alliance
		AimsJMAAlliance aimsJmaAlliance=null;
		AimsAllianc aimsAlliance=null;
		try{
			//try{
				aimsJmaAlliance=JMAAllianceRegistrationManager.getJMAAllianceById(currentUserAllianceId);
			/*}catch(ObjectNotFoundException objNotFound)
			{
				log.info("Code migration, converting Alliance into JMA alliance");
				aimsJmaAlliance=new AimsJMAAlliance();
				aimsJmaAlliance.setAllianceId(currentUserAllianceId);
				aimsJmaAlliance.setJmaAllianceId(currentUserAllianceId);
			}*/
			aimsAlliance= (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, currentUserAllianceId.toString());
		}catch (Exception ex)
        {
            ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("error.generic.database");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            saveErrors(request, errors);
            return (mapping.findForward("error"));
        }
		
		aimsJmaAlliance.setAllianceId(currentUserAllianceId);
		aimsJmaAlliance.setJmaAllianceId(currentUserAllianceId);
		aimsJmaAlliance.setCompanyShortDescription(completeInfoForm.getShortDescription());
		aimsJmaAlliance.setFaq(completeInfoForm.getFaq());
		aimsJmaAlliance.setReasonForRelationShip(completeInfoForm.getReasonOfReleation());
		aimsJmaAlliance.setListExistingContract(completeInfoForm.getContractAgreements());
		aimsJmaAlliance.setIsOptionToGoVZW(completeInfoForm.getIsOptionToGoWithVZW());
		aimsJmaAlliance.setOptionToGoVzw(completeInfoForm.getOptionToGoWithVZW());
		aimsJmaAlliance.setProductMenu(completeInfoForm.getProductMenu());
		aimsJmaAlliance.setIsSalesEngagements(completeInfoForm.getIsSalesEngaugementWithVZW());
		aimsJmaAlliance.setSalesEngagements(completeInfoForm.getSalesEngaugementWithVZW());
		aimsJmaAlliance.setIsAnyProductuseVzwVzNt(completeInfoForm.getIsProductUseVzwVzNt());
		aimsJmaAlliance.setIsProductCertifiedVzw(completeInfoForm.getIsProductCertifiedVZW());
		aimsJmaAlliance.setIsProcessODI(completeInfoForm.getIsProductCertifiedODI());
		aimsJmaAlliance.setBriefDescription(completeInfoForm.getBriefDescription());
		aimsJmaAlliance.setProducrInfo(completeInfoForm.getProductInformation());
		aimsJmaAlliance.setSolutionReside(completeInfoForm.getSolutionReside());
		aimsJmaAlliance.setIsRequiredLBS(completeInfoForm.getIsProductRequiredLBS());
		aimsJmaAlliance.setIsOfferBOBO(completeInfoForm.getIsOfferBOBOServices());
		aimsJmaAlliance.setLastUpdatedDate(new Date());
		aimsJmaAlliance.setLastUpdatedBy(currUser);
		
		//Creating user for 24X7 Technical support
		AimsUser techUser = new AimsUser();
        techUser.setUsername(completeInfoForm.getTechEmail());
        techUser.setPassword(completeInfoForm.getTechPassword());
        techUser.setUserType(AimsConstants.ALLIANCE_USERTYPE);
        techUser.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
        techUser.setUserAccountStatus(AimsConstants.USER_STATUS_ACTIVE);
        techUser.setCreatedDate(new Date());
        techUser.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
        techUser.setLastUpdatedDate(new Date());
        techUser.setRowsLength(new java.lang.Long(10L));
        techUser.setNotificationType(AimsNotificationConstants.NOTIFICATION_EMAIL_TYPE);
        
        //Creating  contact for 24X7 Technical support user
        AimsContact techContact = new AimsContact();
        techContact.setType("UC");
        techContact.setEmailAddress(completeInfoForm.getTechEmail());
        techContact.setFirstName(completeInfoForm.getTechFirstName());
        techContact.setLastName(completeInfoForm.getTechLastName());
        techContact.setTitle(completeInfoForm.getTechTitle());
        techContact.setPhone(completeInfoForm.getTechPhone());
        techContact.setMobile(completeInfoForm.getTechMobile());
        techContact.setFax(completeInfoForm.getTechFax());
        techContact.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
        techContact.setCreatedDate(new Date());
        techContact.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
        techContact.setLastUpdatedDate(new Date());
        
        //Creating user for 24X7 Sales support
		AimsUser salesUser = new AimsUser();
		salesUser.setUsername(completeInfoForm.getSalesEmail());
		salesUser.setPassword(completeInfoForm.getTechPassword());
		salesUser.setUserType(AimsConstants.ALLIANCE_USERTYPE);
		salesUser.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
		salesUser.setUserAccountStatus(AimsConstants.USER_STATUS_ACTIVE);
		salesUser.setCreatedDate(new Date());
		salesUser.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
		salesUser.setLastUpdatedDate(new Date());
		salesUser.setRowsLength(new java.lang.Long(10L));
		salesUser.setNotificationType(AimsNotificationConstants.NOTIFICATION_EMAIL_TYPE);
        
        //Creating  contact for 24X7 Sales support user
        AimsContact salesContact = new AimsContact();
        salesContact.setType("UC");
        salesContact.setEmailAddress(completeInfoForm.getSalesEmail());
        salesContact.setFirstName(completeInfoForm.getSalesFirstName());
        salesContact.setLastName(completeInfoForm.getSalesLastName());
        salesContact.setTitle(completeInfoForm.getSalesTitle());
        salesContact.setPhone(completeInfoForm.getSalesPhone());
        salesContact.setMobile(completeInfoForm.getSalesMobile());
        salesContact.setFax(completeInfoForm.getSalesFax());
        salesContact.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
        salesContact.setCreatedDate(new Date());
        salesContact.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
        salesContact.setLastUpdatedDate(new Date());
		try
		{		
			boolean saveTechContact=false;
			boolean saveSalesContact=false;
			boolean isSubmit=false;
			boolean isResubmit=false;
			
			if(taskname!=null && taskname.equals("submit")) {
				isSubmit=true;
			}
			if(taskname!=null && taskname.equals("resubmit")){
				isResubmit=true;
			}
			if(isSubmit || isResubmit)
			{
				if(!StringFuncs.NullValueReplacement(aimsAlliance.getIsJmaInfoComplete()).equals("Y"))
				{
					aimsJmaAlliance.setJmaInfoCompletionDate(new Date());
				}
			}
				
			if(completeInfoForm.getIsTechincalInfo()!= null && completeInfoForm.getIsTechincalInfo().equals("Y"))
				saveTechContact=true;
			if(completeInfoForm.getIsSalesSupport()!=null && completeInfoForm.getIsSalesSupport().equals("Y"))
				saveSalesContact=true;
			
			JMAAllianceRegistrationManager.saveOrUpdateJmaAlliance(isSubmit,isResubmit,aimsJmaAlliance,
					currentUserAllianceId,saveTechContact, saveSalesContact,techContact, salesContact,
					completeInfoForm.getAlliancesWithOtherCarriers(),completeInfoForm.getAllianceWithOtherCarriers(),
					completeInfoForm.getAllianceIndustryVerticals(),completeInfoForm.getAllianceTopIndustryVerticals(),
					completeInfoForm.getProductPresentationTempFileId(),completeInfoForm.getWinOpportunityTempFileId(),currUser);
			
			//Action to perform after save/submit
			completeInfoForm.setProductPresentationTempFileId(new Long(0));
			completeInfoForm.setWinOpportunityTempFileId(new Long(0));
			
			if(isResubmit)
			{
				log.info("Application resubmitted by JMA Alliance");
				completeInfoForm.setStatus(AimsConstants.ALLIANCE_STATUS_SUBMITTED);
				//Journal entry
				AimsJournalEntry aimsJournalEntry = new AimsJournalEntry();
				aimsJournalEntry.setJournalText(completeInfoForm.getResubmitDescription());
				aimsJournalEntry.setJournalType("PR");
				aimsJournalEntry.setCreatedBy(currUser);
	 	        aimsJournalEntry.setCreatedDate(new Date());         	        
	 	        aimsJournalEntry.setAimsAllianceId(currentUserAllianceId);
	  	
		        try
				{						
					AimsApplicationsManager.saveOrUpdateJournalEntries(aimsJournalEntry);						
				}
	            catch(Exception ex)
				{						
					log.debug("Exception in AllianceJournalEntryUpdateAction");
				}
	
			}
			
			try
			{
				log.debug("Sending notification for submittind second registration from");
				AimsEventLite aimsEvent=null;
				if(isSubmit)	
					aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_ALLIANCE_REGISTRATION_SUBMITTED);
				else if(isResubmit)
					aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_ALLIANCE_RESUBMITTED);
				
				if (aimsEvent != null) {
					AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMPANY_NAME, aimsAlliance.getCompanyName());	
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ID, aimsAlliance.getAllianceId().toString());	
					aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsAlliance.getAllianceId().toString());
					aimsEvent.raiseEvent(aimsEventObject);
				}
				
			}
			catch(Exception ex)
			{
				log.error("Exception occure while submitting second registram form for JMA",ex);
			}
			
			if(isSubmit) {				
				session.removeAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW);//remove current id from session
                Vector loginContentIds = (Vector)session.getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_IDS_TO_SHOW);//get next or remaining ids
                ActionForward nextForward = null; 
                nextForward = AimsLoginContentManager.setNextLoginContent(request, loginContentIds);
                if(nextForward != null) {
                	return nextForward;
                }
				//todo set next login content
			}
		
			
		} catch (UniqueConstraintException uce)
        {
            ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError(uce.getMessageKey());
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            saveErrors(request, errors);
            return (mapping.findForward("error"));
        }
        catch (Exception ex)
        {
            ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("error.generic.database");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            saveErrors(request, errors);
            return (mapping.findForward("error"));
        }
        
        // Redirect to 
        if(taskname!=null && taskname.equals("save"))
        {
        	ActionMessages messages = new ActionMessages();
            ActionMessage message = new ActionMessage("message.jma_alliance_registration.save");
            messages.add(ActionMessages.GLOBAL_MESSAGE, message);
            saveMessages(request, messages);
        	return mapping.findForward("save");
        }
        ActionMessages messages = new ActionMessages();
        ActionMessage message = new ActionMessage("message.jma_alliance_registration.success2");
        messages.add(ActionMessages.GLOBAL_MESSAGE, message);
        saveMessages(request, messages);
        
        
		return mapping.findForward("submitted");
	}
	
	
	
	

}
