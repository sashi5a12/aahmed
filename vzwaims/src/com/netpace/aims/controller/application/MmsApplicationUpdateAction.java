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
 * This class takes care of action for display of update form of MMS Application.
 *
 * @struts.action path="/mmsApplicationUpdate"  
 *                name="MmsApplicationUpdateForm" 
 *                scope="request"
 *                input="/application/mmsApplicationUpdate.jsp"   
 *				  			validate="true"
 * @struts.action-forward name="page1" path="/application/mmsApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/mmsApplicationUpdate2.jsp"
 * @struts.action-forward name="page3" path="/application/appProcessInfo.jsp"
 * @struts.action-forward name="page4" path="/application/journal.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="mmsView" path="/application/mmsApplicationView.jsp"
 * @struts.action-forward name="journalUpdate" path="/application/journalUpdate.jsp"
 * @author Adnan Makda
 */
public class MmsApplicationUpdateAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(MmsApplicationUpdateAction.class.getName());
  
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
				AimsMmsApp aimsMmsApp = null; 
				AimsContact aimsContact = new AimsContact();
				HashMap appMms = null;
				
				//Get Form
				MmsApplicationUpdateForm mmsAppUpdForm = (MmsApplicationUpdateForm) form;
									
				if (taskname.equalsIgnoreCase("create"))
				{
					aimsApp = new AimsApp();	
					aimsMmsApp = new AimsMmsApp();								
				}
				else if (taskname.equalsIgnoreCase("edit"))
				{
					try 
					{
	          appMms = MmsApplicationManager.getApp(mmsAppUpdForm.getAppsId(), currentUserAllianceId);
					}
					catch(AimsException ae)
					{
						saveErrors(request, DBErrorFinder.populateActionErrors(ae));					
						return mapping.getInputForward();
					}									
					aimsApp = (AimsApp) appMms.get("AimsApp");
					aimsMmsApp = (AimsMmsApp) appMms.get("AimsMmsApp");												
				}

				//Common Update Related Tasks				
				try
				{
					forward = ApplicationHelper.updateAction(request, taskname, mmsAppUpdForm, 
																									aimsApp, aimsContact, 
																									AimsConstants.MMS_PLATFORM_ID, dateFormat);
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
						aimsMmsApp.setContentProvider(mmsAppUpdForm.getContentProvider());
						aimsMmsApp.setProgramPromoInfo(mmsAppUpdForm.getProgramPromoInfo());
						aimsMmsApp.setTargetedLaunchDate(Utility.convertToDate(mmsAppUpdForm.getTargetedLaunchDate(),dateFormat));
						aimsMmsApp.setExpectedMsgTraffic(Utility.convertToLong(mmsAppUpdForm.getExpectedMsgTraffic()));
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
					if ( (mmsAppUpdForm.getAppsId().longValue() != 0) && (aimsApp.getAppsId() == null) )
						clonedFromAppId = mmsAppUpdForm.getAppsId();
							
					try
					{
						MmsApplicationManager.saveOrUpdateMmsApplication
										(aimsApp, aimsMmsApp, aimsContact, currUser, currUserType, 
											mmsAppUpdForm.getScreenJpegTempFileId(), mmsAppUpdForm.getFlashDemoTempFileId(), 
											mmsAppUpdForm.getUserGuideTempFileId(), mmsAppUpdForm.getSplashScreenEpsTempFileId(),
											mmsAppUpdForm.getActiveScreenEpsTempFileId(), mmsAppUpdForm.getFaqDocTempFileId(),
											mmsAppUpdForm.getTestPlanResultsTempFileId(), mmsAppUpdForm.getSampleContentTempFileId(),
											cHS, pHS, clonedFromAppId);
											
					}
	        catch(AimsException ae)
					{
						saveErrors(request, DBErrorFinder.populateActionErrors(ae));					
						return mapping.getInputForward();
					}
					
					//Post Update Tasks
					ActionMessages messages = ApplicationHelper.postUpdateAction(request, taskname, mmsAppUpdForm, aimsApp,
																												AimsConstants.MMS_PLATFORM_ID, dateFormat);
					
					mmsAppUpdForm.setSampleContentTempFileId(new Long(0));
					
					saveMessages( request, messages );
					forward = mmsAppUpdForm.getCurrentPage();																				
				} 

				return mapping.findForward(forward);
		
		
				
    }
}

			
