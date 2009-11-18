package com.netpace.aims.controller.roles;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.bo.security.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.util.*;



/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/sysRolesSetup"  
 *                name="SysRolesForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdate" path="/roles/sysRolesCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateView" path="/roles/sysRolesCreateUpdateView.jsp"
 * @author Rizwan Qazi
 */
public class SysRolesSetupAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(SysRolesSetupAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        String taskname =  request.getParameter("task");   
        String forward = "createUpdate";

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);		
		
		Long alliance_id = user.getAimsAllianc();
        String user_type = user.getUserType();
        String user_name = user.getUsername();

        if (user_type.equalsIgnoreCase("A"))
        {
            throw new AimsSecurityException();
        }

        if (taskname.equalsIgnoreCase("createForm"))
		{	
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ROLES, 
                                                                AimsSecurityManager.INSERT))) 
            {
                throw new AimsSecurityException(); 
            }			
            forward = "createUpdate";           
		} 


		if (taskname.equalsIgnoreCase("editForm"))
		{
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ROLES, 
                                                                AimsSecurityManager.UPDATE))) 
            {
                throw new AimsSecurityException(); 
            }			
            forward = "createUpdate"; 			
		} 

		if (taskname.equalsIgnoreCase("editFormView"))
		{
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ROLES, 
                                                                AimsSecurityManager.SELECT))) 
            {
                throw new AimsSecurityException(); 
            }			
            forward = "createUpdateView"; 			
		} 

		return mapping.findForward(forward);
    }
}
