package com.netpace.aims.controller.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.system.AimsEmailMessageManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.system.AimsEmailMessag;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/emailMessageViewDelete"
 *                scope="request"
 *	          name="EmailMessageForm"
 *                validate="false"
 *                input="/system/emailMessageViewDelete.jsp"
 *
 * @struts.action-forward name="view" path="/system/emailMessageViewDelete.jsp"
 * @struts.action-forward name="delete" path="/emailMessageViewDelete.do?task=view"
 * @struts.action-exception key="error.system.emailmessage.integrity" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @author Fawad Sikandar
 */

public class EmailMessageViewDeleteAction extends BaseAction
{

    static Logger log = Logger.getLogger(EmailMessageViewDeleteAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.EMAIL_MESSAGES);

        String taskname = request.getParameter("task");
        String dateFormat = this.getResources(request).getMessage("date.format");
        String totalEmailSent = null;
        String forward = "view";

        if (AimsConstants.VIEW_TASK.equalsIgnoreCase(taskname))
        {

            Collection collection = AimsEmailMessageManager.getCannedEmailMessageList();
            Collection aimsEmailMessages = new ArrayList();
            totalEmailSent = String.valueOf(collection.size());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                AimsEmailMessag aimsEmailMessage = (AimsEmailMessag) iter.next();

                EmailMessageForm emailMessageForm = new EmailMessageForm();
                emailMessageForm.setEmailMessageId(Utility.convertToString(aimsEmailMessage.getEmailMessageId()));
                emailMessageForm.setEmailTitle(aimsEmailMessage.getEmailTitle());
                emailMessageForm.setEmailText(aimsEmailMessage.getEmailText());
                emailMessageForm.setEmailDesc(aimsEmailMessage.getEmailDesc());
                emailMessageForm.setCreatedBy(aimsEmailMessage.getCreatedBy());
                emailMessageForm.setCreatedDate(Utility.convertToString(aimsEmailMessage.getLastUpdatedDate(), dateFormat));
                emailMessageForm.setLastEmailSentDate(Utility.convertToString(aimsEmailMessage.getLastUpdatedDate(), dateFormat));
                emailMessageForm.setTotalEmailSent(totalEmailSent);

                aimsEmailMessages.add(emailMessageForm);
            }
            request.getSession().setAttribute("aimsEmailMessages", aimsEmailMessages);
            return mapping.findForward(forward);
        }

        if (AimsConstants.DELETE_TASK.equalsIgnoreCase(taskname))
        {

            AimsEmailMessageManager.deleteEmailMessage(Integer.parseInt(request.getParameter("emailMessageId")));

            return mapping.findForward(taskname);
        }

        return mapping.findForward(forward);
    }
}
