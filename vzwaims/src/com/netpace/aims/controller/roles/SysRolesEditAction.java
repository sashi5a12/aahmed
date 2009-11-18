package com.netpace.aims.controller.roles;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.core.*;

import com.netpace.aims.bo.roles.*;
import com.netpace.aims.bo.accounts.*;
import com.netpace.aims.bo.security.*;

import com.netpace.aims.util.*;

import com.netpace.aims.controller.BaseAction;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import org.apache.log4j.Logger;

/**
 * This class takes care of editing/creating of a Role.
 * If the role already exists it will throw a UniqueConstraintException
 *
 * @struts.action path="/sysRolesEdit"                
 *                scope="request" 
 *				  name="SysRolesForm"
 *				  validate="true"
 *                input="/roles/sysRolesCreateUpdate.jsp"  
 * @struts.action-forward name="view" path="/sysRoles.do?task=view" redirect="true"
 * @struts.action-exception key="masters.unique.constraint.violation" type="com.netpace.aims.bo.core.UniqueConstraintException"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.UniqueConstraintException
 */
public class SysRolesEditAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(SysRolesEditAction.class.getName());
  
    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
	 *
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

        String taskname =  request.getParameter("task");   
        int role_id =  StringFuncs.initializeIntGetParam(request.getParameter("roleId"), 0); 

		HttpSession session = request.getSession(); 
        SysRolesForm sysRoleForm = (SysRolesForm) form;

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);		
		
		Long alliance_id = user.getAimsAllianc();
        String user_type = user.getUserType();
        String user_name = user.getUsername();

        String forward = "view";

        if (user_type.equalsIgnoreCase("A"))
        {
            throw new AimsSecurityException();
        } 
        
        log.debug("Task : " + taskname);

		if (taskname.equalsIgnoreCase("edit"))
		{
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ROLES, 
                                                                AimsSecurityManager.UPDATE))) 
            {
                throw new AimsSecurityException(); 
            }

            AimsSysRolesManager.UpdateRolePrivilege
												  (		    
													Integer.parseInt(sysRoleForm.getRoleId()),														
													sysRoleForm.getRoleName(),
													sysRoleForm.getRoleDescription(),
													sysRoleForm.getRoleType(),                                                  
													user_name,
													user_type,							
													sysRoleForm.getSelects(),
													sysRoleForm.getCreates(),
													sysRoleForm.getUpdates(),
													sysRoleForm.getDeletes()
                                                   );		
			forward = "view";
		} 
	
		if (taskname.equalsIgnoreCase("create"))
		{

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ROLES, 
                                                                AimsSecurityManager.INSERT))) 
            {
                throw new AimsSecurityException(); 
            }			
            log.debug("This is the task : " + taskname);
		
            AimsSysRolesManager.CreateRolePrivilege
												  (		    																							
													sysRoleForm.getRoleName(),
													sysRoleForm.getRoleDescription(),
													"V",                                                  
													user_name,
													user_type,							
													sysRoleForm.getSelects(),
													sysRoleForm.getCreates(),
													sysRoleForm.getUpdates(),
													sysRoleForm.getDeletes()
                                                   );	
            
			forward = "view";			
		} 


		return mapping.findForward(forward);
    }
}
