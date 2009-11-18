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

import com.netpace.aims.bo.system.AimsSystemNotificationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.system.AimsEmailMessag;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care for view/delete functionality of system
 * notificaiton form.
 *
 * @struts.action path="/systemNotificationViewDelete"
 *                scope="request"
 *	          name="SystemNotificationForm"
 *                validate="false"
 *                input="/systemNotificationViewDelete.do?task=view"
 * @struts.action-forward name="view" path="/system/systemNotificationViewDelete.jsp"
 * @struts.action-forward name="delete" path="/systemNotificationViewDelete.do?task=view"
 * @struts.action-exception key="error.system.systemNotification.integrity" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @author Fawad Sikandar
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */

public class SystemNotificationViewDeleteAction extends BaseAction
{

    static Logger log = Logger.getLogger(SystemNotificationViewDeleteAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_NOTIFICATIONS);

        String taskname = request.getParameter("task");
        String forward = "view";

        if (AimsConstants.VIEW_TASK.equalsIgnoreCase(taskname))
        {
            Collection collection = AimsSystemNotificationManager.getSystemEmailMessageList();
            Collection aimsSystemNotifications = new ArrayList();

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                AimsEmailMessag aimsEmailMessage = (AimsEmailMessag) iter.next();

                SystemNotificationForm notificationForm = new SystemNotificationForm();
                notificationForm.setSystemNotificationId(Utility.convertToString(aimsEmailMessage.getEmailMessageId()));
                notificationForm.setSubject(aimsEmailMessage.getEmailTitle());
                notificationForm.setDescription(aimsEmailMessage.getEmailDesc());
                notificationForm.setContent(aimsEmailMessage.getEmailText());

                aimsSystemNotifications.add(notificationForm);
            }

            request.setAttribute("aimsSystemNotifications", aimsSystemNotifications);

            return mapping.findForward(forward);
        }

        if (AimsConstants.DELETE_TASK.equalsIgnoreCase(taskname))
        {

            AimsSystemNotificationManager.deleteSystemNotification(Integer.parseInt(request.getParameter("systemNotificationId")));

            return mapping.findForward(taskname);
        }

        return mapping.findForward(forward);
    }

}
