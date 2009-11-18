package com.netpace.aims.controller.accounts;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*****************************
    //commented for vzw accounts cleanup,
    //disabled xdoclet struts actions, forwards and forms
  This class takes care of action for the login of the aims user.
  If the user is invalid it throws a InvalidUserException which

  struts.action path="/accountsVZWEdit"
                 scope="request"
 				  name="AccountVZWForm"
 				  validate="true"
                 input="/accounts/accountsCreateUpdateVZW.jsp"
  struts.action-forward name="view" path="/accounts.do?task=view"
  struts.action-exception key="masters.integrity.constraint.violation" type="com.netpace.aims.bo.core.IntegrityConstraintException"
  struts.action-exception key="error.email.UsernameAlreadyExist" type="com.netpace.aims.bo.core.UniqueConstraintException"
  author Rizwan Qazi
  see com.netpace.aims.bo.core.IntegrityConstraintException
*****************************/

public class AccountsVZWEditAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AccountsVZWEditAction.class.getName());
  
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
        int user_id =  StringFuncs.initializeIntGetParam(request.getParameter("userId"), 0); 

		HttpSession session = request.getSession(); 
        AccountVZWForm accountForm = (AccountVZWForm) form;

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		
		
		Long alliance_id = user.getAimsAllianc();
        String user_type = user.getUserType();
        String user_name = user.getUsername();

        String forward = "view";
        
        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("edit"))
		{
            AimsUser dbAccount = AimsAccountsManager.getAccount(new Long(user_id));
            String dbAccountPassword = "password";
            if(dbAccount==null) {
                throw new AimsSecurityException();
            }
            dbAccountPassword = dbAccount.getPassword();

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.USERS, 
                                                                AimsSecurityManager.UPDATE))) 
            {
                throw new AimsSecurityException(); 
            }


             /*
            if the user beeing updated is the same is the one using the system then
             change the users info too
         */
            AimsUser au = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
            if(au.getUsername().equals(accountForm.getEmailAddress())) {
            AccountVZWForm frm = (AccountVZWForm) form;
                //au.setPassword(frm.getUserPassword());//do not set password
                au.setMotherMaidenName(frm.getMotherMaidenName());
                au.getAimsContact().setLastName(frm.getLastName());
                au.getAimsContact().setFirstName(frm.getFirstName());
                au.getAimsContact().setTitle(frm.getTitle());
                au.getAimsContact().setPhone(frm.getPhone());
                au.getAimsContact().setFax(frm.getFax());
                au.getAimsContact().setMobile(frm.getMobile());
            }

            AimsAccountsManager.UpdateUserRoles	(
													user_id,
													//accountForm.getUsername(),
													accountForm.getEmailAddress(),
													dbAccountPassword, //accountForm.getUserPassword(),
													accountForm.getUserAccountStatus(),
													accountForm.getFirstName(),
													accountForm.getLastName(),
													accountForm.getEmailAddress(),
													accountForm.getTitle(),
													accountForm.getPhone(),
													accountForm.getMobile(),
													accountForm.getFax(),
													user_name,
													user_type,
													accountForm.getSelectedRoles()
												 );


			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.edit.success"));
			saveMessages(request, messages);

            forward = "view";
		}

		if (taskname.equalsIgnoreCase("create"))
		{

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.USERS,
                                                                AimsSecurityManager.INSERT)))
            {
                throw new AimsSecurityException();
            }

			AimsAccountsManager.CreatUserRoles	(
													//accountForm.getUsername(),
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
													user_type,															
													accountForm.getSelectedRoles()
												 );



			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.create.success"));			
			saveMessages(request, messages);
		                
			forward = "view";			
		} 


		return mapping.findForward(forward);
    }
}
