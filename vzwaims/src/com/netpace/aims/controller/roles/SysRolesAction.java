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
import com.netpace.aims.bo.security.*;

import com.netpace.aims.util.*;
import com.netpace.aims.controller.BaseAction;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/sysRoles"                
 *                scope="request" 
 *				  name="SysRolesForm"
 *				  validate="false"
 *                input="/roles/sysRolesView.jsp"  
 * @struts.action-forward name="view" path="/roles/sysRolesView.jsp"
 * @struts.action-forward name="delete" path="/sysRoles.do?task=view" redirect="true"
 * @struts.action-exception key="masters.integrity.constraint.violation" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */
public class SysRolesAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(SysRolesAction.class.getName());
  
    /**
     * This method lets the user View or Delete a user account
     * It calls the Roles manager to get a list of applicable roles or passes
     * a role_id to delete from the database.
	 *
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

        String taskname =  request.getParameter("task");   
		HttpSession session = request.getSession(); 

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
		String user_type = user.getUserType();
		Long user_id = user.getUserId();


        String forward = "view";

        if (user_type.equalsIgnoreCase("A"))
        {
            throw new AimsSecurityException();
        } 
        
        log.debug("Task : " + taskname);
		

		if (taskname.equalsIgnoreCase("view"))
		{
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ROLES, 
                                                                AimsSecurityManager.SELECT))) 
            {
                throw new AimsSecurityException(); 
            }			
            Collection AimsSysRoles = AimsSysRolesManager.getSysRoles(user_id, user_type);	
			log.debug("The size of the set is " + AimsSysRoles.size());
			session.setAttribute("AimsSysRoles", AimsSysRoles);
		
			forward = "view";
		} 
		
		if (taskname.equalsIgnoreCase("delete"))
		{
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ROLES, 
                                                                AimsSecurityManager.DELETE))) 
            {
                throw new AimsSecurityException(); 
            }
			
            int delCount = AimsSysRolesManager.deleteSysRole(Integer.parseInt(request.getParameter("roleId")), user_name);

			forward = "delete";
		} 

		return mapping.findForward(forward);
    }
}
