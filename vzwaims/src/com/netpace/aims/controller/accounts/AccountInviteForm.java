package com.netpace.aims.controller.accounts;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @struts.form	name="AccountInviteForm"
 */
public class AccountInviteForm extends BaseValidatorForm {

    private String task;
    private String userEmail;

    public void reset(ActionMapping mapping, HttpServletRequest request) {

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors	=	new	ActionErrors();
        AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String currUserName = currUser.getUsername();

        if(this.task!=null && this.task.equalsIgnoreCase("edit")) {
            if ( isBlankString(this.userEmail) ) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.inviteUser.required.userEmail"));
            }
            else {
                if(this.userEmail.equals(currUserName)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.inviteUser.currentUser.userEmail"));
                }
                if (!isValidEmail(this.userEmail)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.inviteUser.invalid.userEmail"));
                }
                if(this.userEmail.length()>100) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.inviteUser.length.userEmail"));
                }
            }
        }
        return errors;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
