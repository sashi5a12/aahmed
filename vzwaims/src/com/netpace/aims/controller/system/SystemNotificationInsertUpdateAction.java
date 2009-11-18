package com.netpace.aims.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.system.AimsSystemNotificationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care for Save/Update functionality
 * of the system notification form.
 *
 * @struts.action path="/systemNotificationInsertUpdate"
 *                scope="request"
 *	          name="SystemNotificationForm"
 *                validate="true"
 *                input="/systemNotificationSetup.do?validate=true"
 * @struts.action-forward name="view" path="/systemNotificationViewDelete.do?task=view" redirect="true"
 * @struts.action-exception key="error.system.systemNotification.duplicate" type="com.netpace.aims.bo.core.UniqueConstraintException"
 * @author Fawad Sikandar
 */

public class SystemNotificationInsertUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(SystemNotificationInsertUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_NOTIFICATIONS);

        String taskname = request.getParameter("task");
        String forward = "view";
        HttpSession session = request.getSession();
        SystemNotificationForm notificationForm = (SystemNotificationForm) form;

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname))
        {
            AimsSystemNotificationManager.saveNotification(
                notificationForm,
                ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))),
                notificationForm.getEmailAttachment());
            return mapping.findForward(forward);
        }

        if (AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
        {
            AimsSystemNotificationManager.UpdateNotification(
                notificationForm,
                ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))),
                notificationForm.getEmailAttachment());
            return mapping.findForward(forward);
        }

        return mapping.findForward(forward);
    }
}
