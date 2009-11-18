package com.netpace.aims.controller.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.application.AimsJmaSalesLeadManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.masters.AimsIndFocusManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsJmaSalesLead;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.masters.AimsIndustryFocu;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;


/**
 * This class takes care of action for display of update form of Enterprise Application.
 *
 * @struts.action path="/entAppSalesLeadUpdate"
 *                input="/application/entAppSalesLead.jsp"
 * 				  name="EntAppSalesLeadForm"	
 *                scope="request"
 *                validate="true"
 * @struts.action-forward name="view" path="/application/entAppSalesLead.jsp"

 * @author Arsalan Qureshi
 */
public class EntAppSalesLeadUpdateAction extends BaseAction {

	static Logger log= Logger.getLogger(EntAppSalesLeadUpdateAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		log.debug("EntAppSalesLeadSetupAction.ActionForward : Start");
		HttpSession session = request.getSession();
		AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
		String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
	    String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
	    String currentUsereEmail=((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getAimsContact().getEmailAddress();
	    Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
	    Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
	    Date currentDate=new Date();
	        
		
		EntAppSalesLeadForm frmSalesLead= (EntAppSalesLeadForm) form;
		String dateFormat = this.getResources(request).getMessage("date.format");
		String task_name = StringFuncs.NullValueReplacement(frmSalesLead.getTask());
		AimsJmaSalesLead salesLead=null;
		
		log.debug("task_name :"+task_name);
	
		/**
		 * This section cover CREATE and EDIT operation
		 */
		if(task_name.equals("create") || task_name.equals("edit"))
		{
			if(task_name.equals("create"))
			{
				//CHECK ACCESS
		        if (!(ApplicationHelper.checkAccess(request, AimsConstants.CREATE_TASK, AimsPrivilegesConstants.SUBMIT_JMA_SALES_LEAD)))
		            throw new AimsSecurityException();
		        
				salesLead=new AimsJmaSalesLead();
				salesLead.setCreateBy(currentUsereEmail);
				salesLead.setCreatedDate(currentDate);
				
				if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
				{
					salesLead.setJmaPartnerId(currentUserAllianceId);
					salesLead.setSalesLeadSubmittedBy(AimsConstants.SALES_LEAD_SUBMITTED_BY_PARTNER);
				}
				else if(currUserType.equals(AimsConstants.VZW_USERTYPE))
				{
					salesLead.setJmaPartnerId(frmSalesLead.getJmaPartnerId());
					salesLead.setSalesLeadSubmittedBy(AimsConstants.SALES_LEAD_SUBMITTED_BY_VERIZON);
				}
				
					
			}
			else if(task_name.equals("edit"))
			{
				//CHECK ACCESS
		        if (!(ApplicationHelper.checkAccess(request, AimsConstants.EDIT_TASK, AimsPrivilegesConstants.SUBMIT_JMA_SALES_LEAD)))
		            throw new AimsSecurityException();
		        
				salesLead = AimsJmaSalesLeadManager.getSalesLeadById(frmSalesLead.getSalesLeadId());
				
				//CHECK STATUS if status = win then user can't edit the solution
				if(salesLead.getSalesLeadStatus().equals(AimsConstants.SALES_LEAD_STATUS_WIN))
				{
					ActionErrors errors = new ActionErrors();
	                ActionError error = new ActionError("error.entApp.salesLead.cannot.edit");
	                errors.add(ActionErrors.GLOBAL_ERROR, error);
	                saveErrors(request, errors);
	                
	                return mapping.findForward("view");
				}
				salesLead.setLastUpdatedBy(currentUsereEmail);
				salesLead.setLastUpdatedDate(currentDate);
				if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
				{
					salesLead.setJmaPartnerId(currentUserAllianceId);
				}
				else if(currUserType.equals(AimsConstants.VZW_USERTYPE))
				{
					salesLead.setJmaPartnerId(frmSalesLead.getJmaPartnerId());
				}
				
			}
				
			//Setting values
			salesLead.setCustomerName(frmSalesLead.getCustomerName());	
			salesLead.setRegionId(frmSalesLead.getRegionId());
			salesLead.setSubRegionId(frmSalesLead.getSubRegionId());
			salesLead.setCity(frmSalesLead.getCity());
			salesLead.setState(frmSalesLead.getState());
			salesLead.setZipCode(frmSalesLead.getZipCode());
			salesLead.setIsNewVzwCustomer(frmSalesLead.getIsNewVzwCustomer());
			salesLead.setNewVzwCustomer(frmSalesLead.getNewVzwCustomer());
				
			salesLead.setSolutionName(frmSalesLead.getSolutionName());
			salesLead.setSolutionDescription(frmSalesLead.getSolutionDescription());
			salesLead.setVerticalId(frmSalesLead.getVerticalId());
			salesLead.setDeviceName(frmSalesLead.getDeviceName());
			salesLead.setNumberOfDevices(frmSalesLead.getNumberOfDevices());
			salesLead.setSolutionComments(frmSalesLead.getSolutionComments());
			salesLead.setSolutionTotalSales(frmSalesLead.getSolutionTotalSales());
				
			salesLead.setSalesRepFullName(frmSalesLead.getSalesRepFullName());
			salesLead.setSalesRepEmailAddress(frmSalesLead.getSalesRepEmailAddress());
			salesLead.setSalesRepPhoneNumber(frmSalesLead.getSalesRepPhoneNumber());
			salesLead.setSalesRepContactInformation(frmSalesLead.getSalesRepContactInformation());
				
			salesLead.setSalesLeadStatus(frmSalesLead.getSalesLeadStatus());
			salesLead.setCloseDate(Utility.convertToDate(frmSalesLead.getCloseDate(), dateFormat));
			
			//Saving sales lead
			try
			{
				AimsJmaSalesLeadManager.saveOrUpdate(salesLead);
				
				frmSalesLead.setSalesLeadId(salesLead.getSalesLeadId());
				frmSalesLead.setTask("edit");
				
				log.debug("Sales Lead savedsuccessfully.");
				ActionMessages messages = new ActionMessages();
		        ActionMessage message = new ActionMessage("message.entApp.salesLead.save.success");
		        messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		        saveMessages(request, messages);
		        
		        /**
		         * This section of code covers Notification
		         */
		     
		        try
		        {
		        	AimsEventLite aimsEvent=null;
		        	
		        	//Notification for JMA Sales Lead Closed, where close => salesLeadStatus=WIN
		        	if(salesLead.getSalesLeadStatus().equals(AimsConstants.SALES_LEAD_STATUS_WIN))
			        {
		        		if(currUserType.equals(AimsConstants.VZW_USERTYPE))
			        	{
				        	aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_SALES_LEAD_CLOSED_BY_ADMIN);
				        }
			        	else if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
			        	{
			        		aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_SALES_LEAD_CLOSED_BY_PARTNER);
			        	}
			        }
		        	 //Notification for create and update 
		        	else
		        	{
			        	if(currUserType.equals(AimsConstants.VZW_USERTYPE))
			        	{
				        	if(task_name.equals("create"))
				        		aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_SALES_LEAD_SUBMITTED_BY_ADMIN);
				        	else if(task_name.equals("edit"))
				        		aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_SALES_LEAD_UPDATED_BY_ADMIN);
			        	}
			        	else if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
			        	{
				        	if(task_name.equals("create"))
				        		aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_SALES_LEAD_SUBMITTED_BY_PARTNER);
				        	else if(task_name.equals("edit"))
				        		aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_SALES_LEAD_UPDATED_BY_PARTNER);
			        	}
		        	}
		        	AimsIndustryFocu infFocus= AimsIndFocusManager.getIndFocus(salesLead.getVerticalId().intValue());
		        	if (aimsEvent != null) {
		        		AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
		        		aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, new Date().toString());
		        		aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, AllianceManager.getAllianceCompanyName(salesLead.getJmaPartnerId()));
		        		aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VERTICAL_NAME, infFocus.getIndustryName());
		        		aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, salesLead.getJmaPartnerId().toString());

		        		aimsEvent.raiseEvent(aimsEventObject);
		        	}
		        	
		        }
		        catch(Exception ex)
		        {
		        	log.error("Exception occur, while sending notification for JMA Sales Lead",ex);
		        }
		        	        
		        //Journal Entry
		        StringBuffer journalText=new StringBuffer();
		        journalText.append("Sales Lead, ");
		        
		        if(task_name.equals("create"))
		        	journalText.append("Submitted ");
		        else if(task_name.equals("edit"))
		        	journalText.append("Updated ");
		        	
		        journalText.append("by "+currUser);
		        JMAApplicationHelper.journalEntry(journalText.toString(), AimsConstants.JOURNAL_TYPE_PRIVATE, salesLead.getJmaPartnerId(), null, currUser);
	        
			}
			catch (HibernateException h)
		    {
				log.error("Errro occur while saveing sales lead",h);
				
				ActionErrors errors = new ActionErrors();
	            ActionError error = new ActionError("error.generic.database");
	            errors.add(ActionErrors.GLOBAL_ERROR, error);
	            saveErrors(request, errors);
		    }
			
		}
		
		
		log.debug("EntAppSalesLeadSetupAction.ActionForward : End");
		return mapping.findForward("view");
	}

}
