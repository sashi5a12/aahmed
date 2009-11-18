package com.netpace.aims.controller.accounts;

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
 * @struts.action path="/accountsSetup"  
 *                name="AccountForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdatePopup" path="/accounts/accountsCreateUpdatePopup.jsp"
 * @struts.action-forward name="createUpdate" path="/accounts/accountsCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateView" path="/accounts/accountsCreateUpdateView.jsp"
 * @author Rizwan Qazi
 */
public class AccountsSetupAction extends BaseAction 
{
    /*****************************
        //commented for vzw accounts cleanup,
        //disabled xdoclet struts actions, forwards and forms
        struts.action-forward name="createUpdateVZW" path="/accounts/accountsCreateUpdateVZW.jsp"
        struts.action-forward name="createUpdateVZWView" path="/accounts/accountsCreateUpdateVZWView.jsp"
    *****************************/
    
    static Logger log = Logger.getLogger(AccountsSetupAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));   
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		String forward = "";

        Boolean accessNotAllowed = (Boolean)request.getAttribute("accessNotAllowed");
        if(accessNotAllowed!=null && accessNotAllowed.booleanValue()) {
            //if access not allowed (checked in form reset method while setting form values)
            throw new AimsSecurityException();
        }

        if ((user_type.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE)))
		{			
			if (taskname.equalsIgnoreCase("createForm"))
			{	
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, 
                                                                    AimsSecurityManager.INSERT))) 
                {
                    throw new AimsSecurityException(); 
                }				
                forward = "createUpdate";           
			} 


			if (taskname.equalsIgnoreCase("editForm"))
			{
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, 
                                                                    AimsSecurityManager.UPDATE))) 
                {
                    throw new AimsSecurityException(); 
                }					
                forward = "createUpdate"; 			
			} 


			if (taskname.equalsIgnoreCase("editFormView"))
			{
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, 
                                                                    AimsSecurityManager.SELECT))) 
                {
                    throw new AimsSecurityException(); 
                }				
                
                forward = "createUpdateView"; 			
			} 

		} 
        /*****************************
            //commented for vzw accounts cleanup
            else
            {
                if (taskname.equalsIgnoreCase("createForm"))
                {
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.USERS,
                                                                        AimsSecurityManager.INSERT)))
                    {
                        throw new AimsSecurityException();
                    }
                    forward = "createUpdateVZW";
                }


                if (taskname.equalsIgnoreCase("editForm"))
                {
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.USERS,
                                                                        AimsSecurityManager.UPDATE)))
                    {
                        throw new AimsSecurityException();
                    }
                    forward = "createUpdateVZW";
                }

                if (taskname.equalsIgnoreCase("editFormView"))
                {
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.USERS,
                                                                        AimsSecurityManager.SELECT)))
                    {
                        throw new AimsSecurityException();
                    }
                    forward = "createUpdateVZWView";
                }
            }//end vzw
        *****************************/

        if (taskname.equalsIgnoreCase("createFormPopup")) {
			
			//Alliance Users
			if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user_type))
				if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, AimsSecurityManager.INSERT))){
					throw new AimsSecurityExceptionForPopup(); 
				}

			//VZW Users
			if (AimsConstants.VZW_USERTYPE.equalsIgnoreCase(user_type))
				if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CREATE_ALLIANCE_USER_BY_VZW, AimsSecurityManager.INSERT))){
					throw new AimsSecurityExceptionForPopup(); 
				}
			request.setAttribute("alliance_id", request.getParameter("alliance_id"));
            request.setAttribute("isPopup", request.getParameter("isPopup"));
            request.setAttribute("cType", request.getParameter("cType"));
            request.setAttribute("parentPageType", request.getParameter("parentPageType"));
            request.setAttribute("parentPath", request.getParameter("parentPath"));
            forward = "createUpdatePopup";           
		}


		return mapping.findForward(forward);
    }
}
