package com.netpace.aims.controller.accounts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.alliance.VZWAllianceManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/accounts"
 *                scope="request"
 *		  name="AccountForm"
 *		  validate="false"
 *                input="/accounts/accountsView.jsp"
 * @struts.action-forward name="view" path="/accounts/accountsView.jsp"
 * @struts.action-forward name="delete" path="/accounts.do?task=view" redirect="true"
 * @struts.action-exception key="masters.integrity.constraint.violation" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */
public class AccountsAction extends BaseAction
{

    static Logger log = Logger.getLogger(AccountsAction.class.getName());

    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = request.getParameter("task");
        HttpSession session = request.getSession();

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        AccountForm accountForm = (AccountForm) form;
        Long user_id = user.getUserId();
        Long alliance_id = user.getAimsAllianc();
        Collection AimsAccounts = null;
        int PAGE_LENGTH = user.getRowsLength().intValue(); //Integer.parseInt(this.getResources(request).getMessage("records.pageLength"));
        int pageNo, rows, pmax = 1;

        try
        {
            pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);
        }
        catch (Exception e)
        {
            pageNo = 1;
        }

        String forward = "";
        log.debug("Task : " + taskname);

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_field")).length() > 0)
        {
            accountForm.setFilterField(StringFuncs.initializeStringGetParam(request.getParameter("filter_field"), "email"));
        }
        else
        {
            accountForm.setFilterField(StringFuncs.initializeStringGetParam(accountForm.getFilterField(), "email"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_text")).trim().length() > 0)
        {
            accountForm.setFilterText(request.getParameter("filter_text"));
        }


        log.debug("accountForm.getFilterField() : " + accountForm.getFilterField());
        log.debug("accountForm.getFilterText() : " + accountForm.getFilterText());

        if (taskname.equalsIgnoreCase("view"))
            if (taskname.equalsIgnoreCase("view"))
            {
                if (user_type.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE))
                {
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, AimsSecurityManager.SELECT)))
                    {
                        throw new AimsSecurityException();
                    }

                    rows =
                        AimsAccountsManager.getAccountsCount(
                            getFilterExpression(
                                StringFuncs.initializeStringGetParam(accountForm.getFilterField(), ""),
                                StringFuncs.initializeStringGetParam(accountForm.getFilterText(), "")),
                            user_id,
                            user_type,
                            alliance_id, accountForm.getSelectedFilterValue(), true);

                    pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

                    if (pageNo < 1)
                        pageNo = 1;
                    else if (pageNo > pmax)
                        pageNo = pmax;

                    AimsAccounts =
                        AimsAccountsManager.getAccounts(
                            getFilterExpression(
                                StringFuncs.initializeStringGetParam(accountForm.getFilterField(), ""),
                                StringFuncs.initializeStringGetParam(accountForm.getFilterText(), "")),
                            user_id,
                            user_type,
                            alliance_id,
                            PAGE_LENGTH,
                            pageNo, accountForm.getSelectedFilterValue(), true);

                    log.debug("The size of the set is " + AimsAccounts.size());
                    request.setAttribute("AimsAccounts", AimsAccounts);

                    request.setAttribute("page_id", new Integer(pageNo));
                    request.setAttribute("page_max", new Integer(pmax));
                    
                }
                /*****************************
                    //commented for vzw accounts cleanup
                    else
                    {
                        this.vzwAccounts(request,
                                        accountForm,
                                        user_id,
                                        user_type,
                                        pageNo,
                                        PAGE_LENGTH,
                                        false);
                    }
                *****************************/
                forward = "view";
            }
        /*****************************
            //commented for vzw accounts cleanup, user can not delete accounts
            if (taskname.equalsIgnoreCase("delete"))
            {
                if (user_type.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE))
                {
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, AimsSecurityManager.DELETE)))
                    {
                        throw new AimsSecurityException();
                    }
                }
                else
                {
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.USERS, AimsSecurityManager.DELETE)))
                    {
                        throw new AimsSecurityException();
                    }
                }

                AimsAccountsManager.deleteAccount(Integer.parseInt(request.getParameter("accountId")), user_name);
                forward = "delete";
            }
        *****************************/
                
        if ("listAccountManager".equalsIgnoreCase(taskname)) {        	
        	this.accountManagerAccounts(request, user_type, accountForm, user_id, PAGE_LENGTH, pageNo);
			forward = "view";
		}
        else if("addAccountManager".equalsIgnoreCase(taskname)){
        	if (StringUtils.isEmpty(accountForm.getAccountManager()) || "0".equalsIgnoreCase(accountForm.getAccountManager())){
				ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError("error.required.AccountManager");
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
        	}
        	else {
        		AimsAccountsManager.setAccountManager(new Long(accountForm.getAccountManager()), true);
        		ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.create.AccountManager"));			
                saveMessages(request, messages);
        	}
        	this.accountManagerAccounts(request, user_type, accountForm, user_id, PAGE_LENGTH, pageNo);
        	forward = "view";
        }
        else if ("delAccountManager".equalsIgnoreCase(taskname)){
        	String accountId=request.getParameter("accountId");
        	if (StringUtils.isNotEmpty(accountId)){
        		Collection alliances=VZWAllianceManager.getVzwAccountManager(new Long(accountId));
        		if (alliances!=null && alliances.size()>0){
        			ActionErrors errors = new ActionErrors();
                    ActionError error = new ActionError("error.delete.AccountManager");
                    errors.add(ActionErrors.GLOBAL_ERROR, error);
                    saveErrors(request, errors);
        		}
        		else {
	        		AimsAccountsManager.setAccountManager(new Long(accountId), false);
	        		ActionMessages messages = new ActionMessages();
	                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.delete.success"));			
	                saveMessages(request, messages);
        		}
        	}
        	this.accountManagerAccounts(request, user_type, accountForm, user_id, PAGE_LENGTH, pageNo);
        	forward= "view";
        }
        if (StringUtils.isNotEmpty(taskname) && 
        		("listAccountManager".equalsIgnoreCase(taskname) ||
        				"addAccountManager".equalsIgnoreCase(taskname) || 
        				"delAccountManager".equalsIgnoreCase(taskname))){

        	request.setAttribute("task", "listAccountManager");
        }
        else {
        	request.setAttribute("task", "view");
        	this.setFilterString(request, accountForm);	
        }
        
        return mapping.findForward(forward);
    }

	private String getFilterExpression(String filter_field, String filter_text)
    {

        StringBuffer expressionBuffer = new StringBuffer("");

        if (filter_text.trim().length() > 0)
        {
            if (filter_field.equalsIgnoreCase("email"))
            {
                expressionBuffer.append("and upper(user.username) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }
            if (filter_field.equalsIgnoreCase("firstName"))
            {
                expressionBuffer.append("and upper(contact.firstName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }
            if (filter_field.equalsIgnoreCase("lastName"))
            {
                expressionBuffer.append("and upper(contact.lastName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }
            if (filter_field.equalsIgnoreCase("createdDate"))
            {
                if (AimsUtils.isDate(filter_text.trim()))
                {
                    expressionBuffer.append("and trunc(user.createdDate) = to_date('" + filter_text.trim() + "', 'MM/dd/yyyy')");
                }
                else
                {
                    expressionBuffer.append("and user.createdDate = to_date('12/31/9999', 'MM/dd/yyyy')");
                }
            }
        }

        return expressionBuffer.toString();
    }
    private void setFilterString(HttpServletRequest request, AccountForm accountForm) {
        StringBuffer value = new StringBuffer();
		HashMap accountStatusMap = new HashMap();
		accountStatusMap.put(AimsConstants.USER_STATUS_ACTIVE, AimsConstants.FILTER_LABEL_ACTIVE);
		accountStatusMap.put(AimsConstants.USER_STATUS_DELETED, AimsConstants.FILTER_LABEL_DELETED);

        for (int i=0; i<accountForm.getSelectedFilterValue().length; i++) {
        	if (accountForm.getSelectedFilterValue()[i].equalsIgnoreCase("SHOW_ALL")){
        		value.append("Showing All");
        		break;
        	}
        	else if (i != (accountForm.getSelectedFilterValue().length-1)){
        		value.append(accountStatusMap.get(accountForm.getSelectedFilterValue()[i])).append(", ");
        	}
        	else {
        		value.append(accountStatusMap.get(accountForm.getSelectedFilterValue()[i]));
        	}
        }
        request.setAttribute("filterValue", value.toString());
    } 
    

        private void vzwAccounts(HttpServletRequest request,
                                AccountForm accountForm,
                                Long user_id,
                                String user_type,
                                int pageNo,
                                int PAGE_LENGTH,
                                boolean isAccountManager) throws Exception{

            int rows, pmax = 1;
            Collection aimsAccounts=null;

            rows =
                AimsAccountsManager.getVZWAccountsCount(
                                                        getFilterExpression(
                                                                StringFuncs.initializeStringGetParam(accountForm.getFilterField(), ""),
                                                                StringFuncs.initializeStringGetParam(accountForm.getFilterText(), "")),
                                                        user_id,
                                                        user_type,
                                                        accountForm.getSelectedFilterValue(),
                                                        isAccountManager
                                                        );
            pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

            if (pageNo < 1)
                pageNo = 1;
            else if (pageNo > pmax)
                pageNo = pmax;

            aimsAccounts =
                AimsAccountsManager.getVZWAccounts(
                                                    getFilterExpression(
                                                            StringFuncs.initializeStringGetParam(accountForm.getFilterField(), ""),
                                                            StringFuncs.initializeStringGetParam(accountForm.getFilterText(), "")),
                                                    user_id,
                                                    user_type,
                                                    PAGE_LENGTH,
                                                    pageNo,
                                                    accountForm.getSelectedFilterValue(),
                                                    isAccountManager
                                                    );
            log.debug("The size of the set is " + aimsAccounts.size());
            request.setAttribute("AimsAccounts", aimsAccounts);
            request.setAttribute("page_id", new Integer(pageNo));
            request.setAttribute("page_max", new Integer(pmax));
        }//end vzwAccounts

    private void accountManagerAccounts(HttpServletRequest request, 
    								String user_type, 
    								AccountForm accountForm, 
    								Long user_id, 
    								int PAGE_LENGTH, 
    								int pageNo) throws Exception, HibernateException {
		accountForm.setSelectedFilterValue(new String[]{AimsConstants.USER_STATUS_ACTIVE,
														AimsConstants.USER_STATUS_DELETED}
											);
        if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_ACCOUNT_MANAGERS, AimsSecurityManager.SELECT)))
        {
            throw new AimsSecurityException();
        }

        this.vzwAccounts(request,
                        accountForm,
                        user_id,
                        user_type,
                        pageNo,
                        PAGE_LENGTH,
                        true);

        Collection activeVzwAccounts=AimsAccountsManager.getAccountsByStatus(
																			user_id, 
																			AimsConstants.VZW_USERTYPE, 
																			AimsConstants.USER_STATUS_ACTIVE,
																			false                                                                           
                                                                            );
		if (activeVzwAccounts!=null && activeVzwAccounts.size()>0){
			Iterator accountItr=activeVzwAccounts.iterator();
			while(accountItr.hasNext()){
				StringBuffer sBuffer=new StringBuffer();
				AimsUser activeUser=(AimsUser)accountItr.next();
				sBuffer.append(activeUser.getUsername())
						.append(" (").append(activeUser.getAimsContact().getFirstName())
						.append(" ").append(activeUser.getAimsContact().getLastName())
						.append(")");
				activeUser.setUsername(sBuffer.toString());
			}
		}
		request.setAttribute("activeVzwAccounts", activeVzwAccounts);
		request.setAttribute("isAccountManager", "true");
	}
}
