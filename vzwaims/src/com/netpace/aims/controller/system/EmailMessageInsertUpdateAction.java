package com.netpace.aims.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.system.AimsEmailMessageManager;
import com.netpace.aims.bo.system.SystemConstants;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.system.AimsEmailMessag;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/emailMessageInsertUpdate"
 *                scope="request"
 *	          name="EmailMessageForm"
 *                validate="true"
 *                input="/system/emailMessageUpdate.jsp"
 * @struts.action-forward name="view" path="/emailMessageViewDelete.do?task=view" redirect="true"
 * @struts.action-exception key="error.system.emailTitle.duplicate" type="com.netpace.aims.bo.core.UniqueConstraintException"
 * @author Fawad Sikandar
 */

public class EmailMessageInsertUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(EmailMessageInsertUpdateAction.class.getName());

    /**
     * This method validates the user.
     * It calls the Security manager with the user name and password and gets a
     * User object if the user is valid. It then moves the user to his logged in page.
     *
     * If the username and password does not match it throws
     * a InvalidUserException.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.EMAIL_MESSAGES);

        String taskname = request.getParameter("task");
        String forward = "view";
        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        EmailMessageForm emailMessageForm = (EmailMessageForm) form;

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname) || AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
        {

            AimsEmailMessag aimsEmailMessage = new AimsEmailMessag();
            aimsEmailMessage.setEmailMessageId(Utility.convertToLong(emailMessageForm.getEmailMessageId()));
            aimsEmailMessage.setEmailCategory(SystemConstants.Email_Message_Categories[0]);
            aimsEmailMessage.setEmailTitle(emailMessageForm.getEmailTitle());
            aimsEmailMessage.setEmailDesc(emailMessageForm.getEmailDesc());
            aimsEmailMessage.setEmailText(emailMessageForm.getEmailText());

            if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname))
            {
                aimsEmailMessage.setCreatedBy(currUser);
                aimsEmailMessage.setCreatedDate(SystemConstants.currentDate);
            }
            if (AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
            {
                aimsEmailMessage.setCreatedBy(emailMessageForm.getCreatedBy());
                aimsEmailMessage.setCreatedDate(Utility.convertToDate(emailMessageForm.getCreatedDate(), dateFormat));
            }

            aimsEmailMessage.setLastUpdatedBy(currUser);
            aimsEmailMessage.setLastUpdatedDate(SystemConstants.currentDate);
            AimsEmailMessageManager.saveOrUpdateEmailMessage(aimsEmailMessage);

            return mapping.findForward(forward);
        }
        return mapping.findForward(forward);
    }
}
