package com.netpace.aims.controller.application;

import org.apache.struts.util.MessageResources;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import net.sf.hibernate.JDBCException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.application.*;
import com.netpace.aims.controller.BaseAction;


/**
 * This class takes care of action for display of update form of SMS Application.
 *
 * @struts.action path="/smsApplicationUpdate"  
 *                name="SmsApplicationUpdateForm" 
 *                scope="request"
 *                input="/application/smsApplicationUpdate.jsp"   
 *				  			validate="true"
 * @struts.action-forward name="page1" path="/application/smsApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/smsApplicationUpdate2.jsp"
 * @struts.action-forward name="page3" path="/application/appProcessInfo.jsp"
 * @struts.action-forward name="page4" path="/application/journal.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="smsView" path="/application/smsApplicationView.jsp"
 * @struts.action-forward name="journalUpdate" path="/application/journalUpdate.jsp"
 * @author Adnan Makda
 */
public class SmsApplicationUpdateAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(SmsApplicationUpdateAction.class.getName());
  
		public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {
				
				HttpSession session = request.getSession(); 
				String dateFormat = this.getResources(request).getMessage("date.format");
				String currUser = ((AimsUser)(session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
				String currUserType = ((AimsUser)(session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
				Long currUserId = ((AimsUser)(session.getAttribute(AimsConstants.AIMS_USER))).getUserId();				
				Long currentUserAllianceId = ((AimsUser)session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
				Long clonedFromAppId = null;
				
        String forward = "view";
        boolean errorFound = false;
				String taskname="";

				Object o_param = request.getParameter("task"); 
      	if (o_param != null )
      	{
	      	taskname =  request.getParameter("task");   
					request.setAttribute("task", (String)o_param);   					
      	}
      	else
      		return mapping.findForward(forward);
        
        log.debug("TASK is:  " + taskname);


				AimsApp aimsApp = null; 
				AimsSmsApp aimsSmsApp = null; 
				AimsContact aimsContact = new AimsContact();
				HashMap appSms = null;
				
				//Get Form
				SmsApplicationUpdateForm smsAppUpdForm = (SmsApplicationUpdateForm) form;
									
				if (taskname.equalsIgnoreCase("create"))
				{
					aimsApp = new AimsApp();	
					aimsSmsApp = new AimsSmsApp();								
				}
				else if (taskname.equalsIgnoreCase("edit"))
				{
					try 
					{
           	appSms = SmsApplicationManager.getApp(smsAppUpdForm.getAppsId(), currentUserAllianceId);
					}
					catch(AimsException ae)
					{
						saveErrors(request, DBErrorFinder.populateActionErrors(ae));					
						return mapping.getInputForward();
					}									
					aimsApp = (AimsApp) appSms.get("AimsApp");
					aimsSmsApp = (AimsSmsApp) appSms.get("AimsSmsApp");												
				}

				//Common Update Related Tasks				
				
				try
				{
					forward = ApplicationHelper.updateAction(request, taskname, smsAppUpdForm, 
																									aimsApp, aimsContact, 
																									AimsConstants.SMS_PLATFORM_ID, dateFormat);
				}
				catch(AimsException ae)
				{
					saveErrors(request, DBErrorFinder.populateActionErrors(ae));					
					return mapping.getInputForward();
				}					 				
 					 				
        
        if ( (taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")) )
				{	
					//The following properties can only be updated by an Alliance user and not the Verizon user
					if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
					{
						aimsSmsApp.setCampaignClassification(smsAppUpdForm.getCampaignClassification());
						aimsSmsApp.setCarrierSupport(smsAppUpdForm.getCarrierSupport());
						aimsSmsApp.setIfVzwDirectConn(smsAppUpdForm.getIfVzwDirectConn());
						aimsSmsApp.setConnType(smsAppUpdForm.getConnType());
						aimsSmsApp.setContentProvider(smsAppUpdForm.getContentProvider());
						//DUPLICATE://aimsSmsApp.setCampaignDesc(smsAppUpdForm.getCampaignDesc());
						aimsSmsApp.setCampaignPromoInfo(smsAppUpdForm.getCampaignPromoInfo());
						aimsSmsApp.setShortCodesReqd(smsAppUpdForm.getShortCodesReqd());
						
						aimsSmsApp.setWholesalePrice(Utility.convertToBigDecimal(smsAppUpdForm.getWholesalePrice()));
						aimsSmsApp.setRetailPrice(Utility.convertToBigDecimal(smsAppUpdForm.getRetailPrice()));
						aimsSmsApp.setAvgMsgsPerSec(Utility.convertToBigDecimal(smsAppUpdForm.getAvgMsgsPerSec()));
						aimsSmsApp.setPeakMsgsPerSec(Utility.convertToBigDecimal(smsAppUpdForm.getPeakMsgsPerSec()));
						
						aimsSmsApp.setCampaignStartDate(Utility.convertToDate(smsAppUpdForm.getCampaignStartDate(), dateFormat));
						aimsSmsApp.setCampaignEndDate(Utility.convertToDate(smsAppUpdForm.getCampaignEndDate(), dateFormat));
					}
		
					java.util.Set cHS = null;
					java.util.Set pHS = null;
					
					/*
					if ((java.util.Set)session.getAttribute(ManageApplicationsConstants.SESSION_VAR_CERTIFICATIONS) != null)
						cHS = (java.util.Set)session.getAttribute(ManageApplicationsConstants.SESSION_VAR_CERTIFICATIONS);
					*/
					
					if (currUserType.equals(AimsConstants.VZW_USERTYPE))			
						if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_TESTING)) 	
							if ((java.util.Set)session.getAttribute(ManageApplicationsConstants.SESSION_VAR_PHASES) != null)
								pHS = (java.util.Set)session.getAttribute(ManageApplicationsConstants.SESSION_VAR_PHASES);
					
					//Check if the app is being cloned
					if ( (smsAppUpdForm.getAppsId().longValue() != 0) && (aimsApp.getAppsId() == null) )
						clonedFromAppId = smsAppUpdForm.getAppsId();
						
					try
					{
						SmsApplicationManager.saveOrUpdateSmsApplication
										(aimsApp, aimsSmsApp, aimsContact, currUser, currUserType, 
											smsAppUpdForm.getUserGuideTempFileId(), smsAppUpdForm.getFaqDocTempFileId(),
											smsAppUpdForm.getTestPlanResultsTempFileId(), smsAppUpdForm.getMessageFlowTempFileId(),
											cHS, pHS, clonedFromAppId);
					}
	        catch(AimsException ae)
					{
						saveErrors(request, DBErrorFinder.populateActionErrors(ae));					
						return mapping.getInputForward();
					}						
					
					//Post Update Tasks
					ActionMessages messages = ApplicationHelper.postUpdateAction(request, taskname, smsAppUpdForm, aimsApp,
																												AimsConstants.SMS_PLATFORM_ID, dateFormat);
					
					smsAppUpdForm.setMessageFlowTempFileId(new Long(0));
					
					saveMessages( request, messages );
					forward = smsAppUpdForm.getCurrentPage();														
				} 

				return mapping.findForward(forward);
    }
}

			
