package com.netpace.aims.controller.alliance;

import org.apache.struts.action.*;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.security.*;

import com.netpace.aims.controller.BaseAction;


/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/allianceCompInfoSetup"  
 *                name="AllianceCompInfoForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdate" path="/alliance/allianceCompanyInfoUpdate1.jsp"
 * @struts.action-forward name="allianceView" path="/alliance/allianceCompanyInfoView.jsp"
 * @struts.action-forward name="vzwView" path="/alliance/vzwAllianceCompanyInfoView.jsp"
 * @author Rizwan Qazi
 */
public class AllianceCompInfoSetupAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceCompInfoSetupAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        String taskname =  request.getParameter("task");
        log.debug("start AllianceCompInfoSetupAction, task= "+taskname);
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = null;
		String forward = "";

            if (user_type.equalsIgnoreCase("A"))
            {		
                
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.COMPANY_INFORMATION, 
                                                                    AimsSecurityManager.SELECT))) 
                {
                    throw new AimsSecurityException(); 
                }
                
                alliance_id = user.getAimsAllianc();
                if (taskname.equalsIgnoreCase("createForm"))
                {	
                    
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.COMPANY_INFORMATION, 
                                                                    AimsSecurityManager.INSERT))) 
                    {
                        throw new AimsSecurityException(); 
                    }              
                    
                    forward = "createUpdate";           
                } 

                if (taskname.equalsIgnoreCase("editForm"))
                {
                    
                    if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.COMPANY_INFORMATION, 
                                                                    AimsSecurityManager.UPDATE)) 
                    {
                        forward = "createUpdate";
                    }  
                    else
                    {
                        forward = "allianceView";
                    }                     
                     			
                }                 
            }//end alliance user

            if (user_type.equalsIgnoreCase("V"))
            {		
                
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, 
                                                                    AimsSecurityManager.SELECT))) 
                {
                    throw new AimsSecurityException(); 
                }
                alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
                forward = "vzwView";
            } 
		    log.debug("end AllianceCompInfoSetupAction, forward= "+forward);
			return mapping.findForward(forward);
    }
}
