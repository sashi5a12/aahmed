package com.netpace.aims.controller.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.profile.AimsProfileManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/*****************************
    //commented for accounts cleanup, user can not change password
    //disabled xdoclet struts actions, forwards and forms

     struts.action path="/profileChangePassword"                
                     scope="request"
                     input="/profile/profileChangePassword.jsp"
                     name="ProfileChangePasswordForm"
                     validate="true"
     struts.action-forward name="update" path="/editprofile.do"
     author Adnan Ahmed
*****************************/

public class ProfileChangePasswordAction extends BaseAction {
	private static final Logger log = Logger.getLogger(ProfileChangePasswordAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		String forward="update";
		ProfileChangePasswordForm changePwdForm=(ProfileChangePasswordForm)form;
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		
		try {
			AimsProfileManager.changePassword(user.getUserId(), changePwdForm.getOldPwd(), changePwdForm.getNewPwd());

            //set password for user stored in session
            user.setPassword(changePwdForm.getNewPwd());

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.changePassword.success"));			
            saveMessages(request, messages);
		} catch (AimsException e) {
			ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionMessage(e.getMessageKey()));
            saveErrors(request, errors);
            return mapping.getInputForward();
		}
		return mapping.findForward(forward);
	}
}
