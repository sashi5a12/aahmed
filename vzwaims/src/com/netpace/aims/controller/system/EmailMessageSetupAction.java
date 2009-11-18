package com.netpace.aims.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.system.AimsEmailMessageManager;
import com.netpace.aims.bo.system.AimsSystemManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.system.AimsEmailMessag;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/emailMessageSetup"
 *                scope="request"
 *	          name="EmailMessageForm"
 *                validate="false"
 *                input="/system/emailMessageViewDelete.jsp"
 * @struts.action-forward name="Update" path="/system/emailMessageUpdate.jsp"
 * @struts.action-forward name="DetailView" path="/system/emailMessageDetailView.jsp"
 * @author Fawad Sikandar
 */

public class EmailMessageSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(EmailMessageSetupAction.class.getName());

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
        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        EmailMessageForm aimsEmailMessage = (EmailMessageForm) form;
        String forward = "Update";

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname))
        {
            session.setAttribute("placeHolderList", AimsSystemManager.getEmailTemplatePHSList());
            return mapping.findForward(forward);
        }

        if (AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname) || "DetailView".equalsIgnoreCase(taskname))
        {

            AimsEmailMessag emailMessage = AimsEmailMessageManager.getEmailMessage(Integer.parseInt(request.getParameter("emailMessageId")));
            aimsEmailMessage.setEmailMessageId(Utility.convertToString(emailMessage.getEmailMessageId()));
            aimsEmailMessage.setEmailTitle(emailMessage.getEmailTitle());
            aimsEmailMessage.setEmailDesc(emailMessage.getEmailDesc());
            aimsEmailMessage.setEmailText(emailMessage.getEmailText());
            aimsEmailMessage.setCreatedBy(emailMessage.getCreatedBy());
            aimsEmailMessage.setCreatedDate(Utility.convertToString(emailMessage.getCreatedDate(), dateFormat));
            aimsEmailMessage.setTask(taskname);
            session.setAttribute("aimsEmailMessage", aimsEmailMessage);

            if ("DetailView".equalsIgnoreCase(taskname))
            {
                return mapping.findForward("DetailView");
            }

            return mapping.findForward(forward);

        }
        return mapping.findForward(forward);
    }
}
