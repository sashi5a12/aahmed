package com.netpace.aims.controller.accounts;

import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;

import com.netpace.aims.bo.security.*;
import com.netpace.aims.bo.masters.*;

import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.bo.accounts.*;
import com.netpace.aims.bo.core.UniqueConstraintException;

import com.netpace.aims.util.*;

import com.netpace.aims.controller.BaseAction;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/accountsEdit"                
 *                scope="request" 
 *				  name="AccountForm"
 *				  validate="false"
 *                input="/accounts/accountsCreateUpdate.jsp"  
 * @struts.action-forward name="view" path="/accounts.do?task=view" redirect="true"
 * @struts.action-forward name="createUpdate" path="/accounts/accountsCreateUpdate.jsp"
 * @struts.action-forward name="error" path="/accounts/accountsCreateUpdate.jsp" 
 * @struts.action-forward name="errorPopup" path="/accounts/accountsCreateUpdatePopup.jsp" 
 * @struts.action-forward name="closePopup" path="/common/closePopupWindow.jsp"
 * @struts.action-exception key="masters.integrity.constraint.violation" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @struts.action-exception key="error.email.UsernameAlreadyExist" type="com.netpace.aims.bo.core.UniqueConstraintException"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */
public class AccountsEditAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AccountsEditAction.class.getName());
  
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

        String isPopup=request.getParameter("isPopup");
        String type=request.getParameter("cType");

        String parentPageType = StringFuncs.NullValueReplacement(request.getParameter("parentPageType"));
        String parentPath = StringFuncs.NullValueReplacement(request.getParameter("parentPath"));

        HttpSession session = request.getSession();
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);						
        String user_type = user.getUserType();
        String user_name = user.getUsername();
        
        Long alliance_id = new Long(0);
        if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user_type))
        {
        	alliance_id = user.getAimsAllianc();        	
        }
        /*****************************
            //commented for vzw accounts cleanup
            else
            {
                alliance_id = new Long(StringFuncs.initializeIntGetParam(request.getParameter("alliance_id"), 0));
                request.setAttribute("alliance_id", alliance_id);
            }
        *****************************/
    
        AccountForm accountForm = (AccountForm) form;
    	ActionErrors errors=new ActionErrors();
    	errors.clear();
    	errors=accountForm.validate(mapping, request);
    	if (StringUtils.isNotEmpty(type)){
    		request.setAttribute("cType", type);
    	}    	
    	if(StringUtils.isNotEmpty(isPopup)){
			request.setAttribute("isPopup", isPopup);
    	}

        if(StringUtils.isNotEmpty(parentPageType)) {
            request.setAttribute("parentPageType", parentPageType);
        }

        if(StringUtils.isNotEmpty(parentPath)) {
            request.setAttribute("parentPath", parentPath);
        }

        if(errors.isEmpty()==false){
    		super.saveErrors(request, errors);
    		if (StringUtils.isNotEmpty(isPopup) && "true".equalsIgnoreCase(isPopup)){
    			return mapping.findForward("errorPopup");
    		}
    		else {
    			return mapping.findForward("error");
    		}
    	}
        String taskname =  request.getParameter("task");   
        int user_id =  StringFuncs.initializeIntGetParam(request.getParameter("userId"), 0); 
        
        String forward = "view";
        
        log.debug("Task : " + taskname);

        Long aimsRoleId = accountForm.getAimsRoleId();
	 			
		if (aimsRoleId == null)
        {
            aimsRoleId = new Long(0);
        }


		if (user_type.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE))
        {
            if (taskname.equalsIgnoreCase("edit"))
            {
                AimsContact dbAccountContact = null;
                AimsUser dbAccount = AimsAccountsManager.getAccount(new Long(user_id));
                boolean revokeOffersByUser = false;

                if(dbAccount==null) {
                    throw new AimsSecurityException();
                }

                dbAccountContact = dbAccount.getAimsContact();
                if(dbAccountContact == null)
                {
                    throw new AimsSecurityException();
                }

                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, 
																			AimsSecurityManager.UPDATE))) 
                {
                    throw new AimsSecurityException(); 
                }

                if(dbAccount.getUserAccountStatus().equals(AimsConstants.USER_STATUS_ACTIVE) &&
                        accountForm.getUserAccountStatus().equals(AimsConstants.USER_STATUS_DELETED)) {
                    revokeOffersByUser = true;
                }

                //do not change aims contact values, change status and roles only
                AimsAccountsManager.UpdateUserRolePrivilege (
																		user_id,
																		accountForm.getEmailAddress(),
																		dbAccount.getPassword(),
																		accountForm.getUserAccountStatus(),
																		dbAccountContact.getFirstName(),
																		dbAccountContact.getLastName(),
																		dbAccountContact.getEmailAddress(),
																		dbAccountContact.getTitle(),
																		dbAccountContact.getPhone(),
																		dbAccountContact.getMobile(),
																		dbAccountContact.getFax(),
																		user_name,
																		user_type,
																		alliance_id,
																		aimsRoleId,
                                                                        revokeOffersByUser
                                                            );
            
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.edit.success"));
                saveMessages(request, messages);
                        
                forward = "createUpdate";
            }//end edit
        }

        /****************************
            //commented for accounts cleanup, user can not create accounts(users),
            //they can invite users by using invite user section

            if (taskname.equalsIgnoreCase("create"))
            {
                //Alliance Users
                if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user_type))
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, AimsSecurityManager.INSERT))){
                        throw new AimsSecurityException();
                    }

                //VZW Users
                if (AimsConstants.VZW_USERTYPE.equalsIgnoreCase(user_type))
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CREATE_ALLIANCE_USER_BY_VZW, AimsSecurityManager.INSERT))){
                        throw new AimsSecurityException();
                    }

                try {
                    AimsUser aimsUser=AimsAccountsManager.saveOrUpdateUserRolePrivilege(
                                                                                accountForm.getEmailAddress(),
                                                                                accountForm.getUserPassword(),
                                                                                accountForm.getUserAccountStatus(),
                                                                                accountForm.getFirstName(),
                                                                                accountForm.getLastName(),
                                                                                accountForm.getEmailAddress(),
                                                                                accountForm.getTitle(),
                                                                                accountForm.getPhone(),
                                                                                accountForm.getMobile(),
                                                                                accountForm.getFax(),
                                                                                user_name,
                                                                                "A",
                                                                                alliance_id,
                                                                                aimsRoleId
                                                                    );

                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.create.success"));
                    saveMessages(request, messages);
                    if (StringUtils.isNotEmpty(isPopup) && "true".equalsIgnoreCase(isPopup)){
                        if (StringUtils.isEmpty(type)){
                            request.setAttribute("CLOSE", "CHANGE_ALL_ADMIN");
                            request.setAttribute("contactId", aimsUser.getUserId().toString());
                        }
                        else {
                            request.setAttribute("CLOSE", "CLOSE_CONTACT");
                            request.setAttribute("contactId",aimsUser.getAimsContact().getContactId().toString());
                        }

                        //if parentPageType is alliance contact info update (login content), change close value
                        if(parentPageType.equals(AimsConstants.PAGE_TYPE_LOGIN_ALLIANCE_CONTACT_UPDATE)) {
                            request.setAttribute("CLOSE", AimsConstants.PAGE_TYPE_LOGIN_ALLIANCE_CONTACT_UPDATE);
                            request.setAttribute("contactId",aimsUser.getAimsContact().getContactId().toString());
                        }

                        forward = "closePopup";
                    }
                    else {
                        forward = "view";
                    }
                }
                catch (UniqueConstraintException uce){
                    if (StringUtils.isNotEmpty(isPopup)){
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.email.UsernameAlreadyExist"));
                        saveErrors(request, errors);
                        forward="errorPopup";
                    }
                    else {
                        throw new UniqueConstraintException();
                    }
                }
            }//end create
        *****************************/
    
        return mapping.findForward(forward);
    }
}
