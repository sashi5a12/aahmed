package com.netpace.aims.controller.alliance.jma;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.alliance.JMAAllianceRegistrationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.vzdn.VzdnUserManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.BaseActionForm;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.OpenssoRestService;
import com.netpace.aims.util.StringFuncs;

/**
 * @struts.action path="/jmaAllianceRegistrationUpdate"
 * 				  name="JMAAllianceRegistrationForm"
 *                scope="request"
 *                input="/alliance/jma/jmaAllianceRegistrationUpdate.jsp"
 *                validate="true"
 * @struts.action-forward name="view" path="/public/index.jsp"
 * @struts.action-forward name="error" path="/alliance/jma/jmaAllianceRegistrationUpdate.jsp"                 	
 * @author aqureshi
 *
 */

public class JMAAllianceInsertUpdateAction extends BaseAction {

	static Logger log = Logger.getLogger(JMAAllianceInsertUpdateAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		String taskname = request.getParameter("task");
		String forward = "view";
		
		if (taskname.equalsIgnoreCase("create"))
		{
			JMAAllianceRegistrationForm jmaRegistrationForm= (JMAAllianceRegistrationForm) form;
			
			
			AimsUser existingUser = AimsAccountsManager.validateAnyUser(jmaRegistrationForm.getEmail());
			if(existingUser!=null && !existingUser.getUserAccountStatus().equals(AimsConstants.USER_STATUS_ACTIVE)){
				
				int count = VzdnUserManager.recursviceUserCheck(existingUser.getUsername());
            	String updateUserName=existingUser.getUsername()+"del_00"+count;
            	AimsAccountsManager.UpdateUserRolesWithPermanentDelete
            	(existingUser.getUserId().intValue(),
            			updateUserName, existingUser.getPassword(),
            			existingUser.getUserAccountStatus(),
            			existingUser.getAimsContact().getFirstName(),
            			existingUser.getAimsContact().getLastName(),
            			existingUser.getAimsContact().getEmailAddress(),
            			existingUser.getAimsContact().getTitle(),
            			existingUser.getAimsContact().getPhone(),
            			existingUser.getAimsContact().getMobile(),
            			existingUser.getAimsContact().getFax(),
            			existingUser.getUsername(), existingUser.getUserType(), VzdnUserManager.getStringArrayFromObject(existingUser.getRoles()), AimsConstants.VZDN_PERMANENT_DELETE);
			}
			
			
			
			//Creating aims user
			AimsUser aimsUser = new AimsUser();
			aimsUser.setUsername(jmaRegistrationForm.getEmail());
	        aimsUser.setPassword(StringFuncs.asHex(MiscUtils.generate128bitkey()));
	        aimsUser.setUserType(AimsConstants.ALLIANCE_USERTYPE);
	        aimsUser.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
	        aimsUser.setUserAccountStatus(AimsConstants.USER_STATUS_ACTIVE);
	        aimsUser.setCreatedDate(new Date());
	        aimsUser.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
	        aimsUser.setLastUpdatedDate(new Date());
	        aimsUser.setRowsLength(new java.lang.Long(10L));
	        aimsUser.setNotificationType(AimsNotificationConstants.NOTIFICATION_EMAIL_TYPE);
	        
	        //Creating JMA alliance
	        AimsAllianc aimsAllianc = new AimsAllianc();
			aimsAllianc.setIsJMAAlliance("Y");
			aimsAllianc.setIsJmaInfoComplete("N");
	        aimsAllianc.setCompanyName(jmaRegistrationForm.getCompanyName());
	        aimsAllianc.setWebSiteUrl(jmaRegistrationForm.getWebSiteUrl());
	        aimsAllianc.setStreetAddress1(jmaRegistrationForm.getCompanyAddress());
	        aimsAllianc.setState(jmaRegistrationForm.getStateProvince());
	        aimsAllianc.setZipCode(jmaRegistrationForm.getZipCode().toString());
	        aimsAllianc.setCountry(jmaRegistrationForm.getCountry());
	        aimsAllianc.setCompanyCountryOfOrigin(jmaRegistrationForm.getCountry());
	        aimsAllianc.setCity(jmaRegistrationForm.getCity());
	        aimsAllianc.setYearCompanyFounded(jmaRegistrationForm.getYearFounded());
	        aimsAllianc.setCompanyLegalName(jmaRegistrationForm.getCompanyName());
	        aimsAllianc.setAuthRepName(jmaRegistrationForm.getFirstName() + " " +jmaRegistrationForm.getLastName());
	        aimsAllianc.setNumFullTimeEmp(AimsConstants.NON_NULLABLE_FIELD_NUMERIC);
	        aimsAllianc.setStatus(AimsConstants.ALLIANCE_STATUS_SUBMITTED);
	        aimsAllianc.setIsOnHold("N");
	        aimsAllianc.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
	        aimsAllianc.setCreatedDate(new Date());
	        aimsAllianc.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
	        aimsAllianc.setLastUpdatedDate(new Date());
	        
	        //Creating contact
	        AimsContact aimsContact = new AimsContact();
            aimsContact.setType("UC");
            aimsContact.setFirstName(jmaRegistrationForm.getFirstName());
            aimsContact.setLastName(jmaRegistrationForm.getLastName());
            aimsContact.setTitle(jmaRegistrationForm.getTitle());
            aimsContact.setEmailAddress(jmaRegistrationForm.getEmail());
            aimsContact.setPhone(jmaRegistrationForm.getPhone());
            aimsContact.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsContact.setCreatedDate(new Date());
            aimsContact.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            aimsContact.setLastUpdatedDate(new Date());
			
            //Saving JMA alliance in database
            try
            {
            	JMAAllianceRegistrationManager.saveOrUpdateAlliance(aimsUser, aimsAllianc, aimsContact);
            
            	OpenssoRestService.updateUserCompany(aimsUser, aimsAllianc);
            	
            	//Notification code come here
            	/*try
    			{
    				log.debug("Sending notification for JMA Alliance submitted");
    				AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_ALLIANCE_REGISTRATION_SUBMITTED);
    				
    				if (aimsEvent != null) {
    					AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
    					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
    					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMPANY_NAME, aimsAllianc.getCompanyName());	
    					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ID, aimsAllianc.getAllianceId().toString());
    					aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsAllianc.getAllianceId().toString());
    					aimsEvent.raiseEvent(aimsEventObject);
    				}
    				
    			}
    			catch(Exception ex)
    			{
    				log.error("Exception occure while sending notification for JMA Alliance submitted",ex);
    			}*/
            }
            catch (AimsException ae) {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.findForward("error");
            }            
            catch (Exception ex)
            {
                ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError("error.generic.database");
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));
            }

            
            ActionMessages messages = new ActionMessages();
            ActionMessage message = new ActionMessage("message.jma_alliance_registration.success1");
            messages.add(ActionMessages.GLOBAL_MESSAGE, message);
            saveMessages(request, messages);
            request.setAttribute("aimsUser",aimsUser);
            //Automatically log in the user
            //return mapping.findForward(forward);
            return new ActionForward("/login.do?userName=" + aimsUser.getUsername() + "&password=" + aimsUser.getPassword());
       
			
		}
		
		return mapping.findForward(forward);
	}
	
	

}
