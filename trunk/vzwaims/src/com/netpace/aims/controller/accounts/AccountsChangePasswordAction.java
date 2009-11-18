package com.netpace.aims.controller.accounts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.AimsConstants;

/*****************************
    //commented for accounts cleanup, user can not change password
    //disabled xdoclet struts actions, forwards and forms
 struts.action path="/accountsChangePassword"
                 scope="request"
                 input="/accounts/accountsChangePassword.jsp"
                 name="AccountsChangePasswordForm"
                 validate="true"
 struts.action-forward name="update" path="/accountsSetup.do?task=editForm"
 author Adnan Ahmed
*****************************/

public class AccountsChangePasswordAction extends BaseAction {
    private static final Logger log = Logger.getLogger(AccountsChangePasswordAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String forward="update";
        AccountsChangePasswordForm changePwdForm=(AccountsChangePasswordForm)form;
        AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);

        Long accountId = Utility.ZeroValueReplacement(changePwdForm.getAccountId());
        AimsUser dbAccountUser = AimsAccountsManager.getAccount(accountId);

        if(dbAccountUser ==null) {
            throw new AimsSecurityException();
        }

        log.debug("AccountsChangePasswordAction: currentUserId= "+currUser.getUserId()+"\t dbAccountId= "+accountId);

        //check access, current user is allowed to change password
        /********* security check ***********/
        if(!AimsAccountsManager.checkCurrentUserAccessPrivilegeForUser(request, currUser, dbAccountUser)) {
            throw new AimsSecurityException();
        }
        /********* end security check ***********/

        AimsAccountsManager.changePassword(changePwdForm.getAccountId(), changePwdForm.getNewPwd());

        if(currUser.getUsername().equals(dbAccountUser.getUsername())) {
            //if current user is same as changed user, then set current user password
            currUser.setPassword(changePwdForm.getNewPwd());
        }

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.changePassword.success"));
        saveMessages(request, messages);
        request.setAttribute("accountId", changePwdForm.getAccountId());
        return mapping.findForward(forward);
    }
}
