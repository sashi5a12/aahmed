package com.netpace.aims.controller.system;

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
import com.netpace.aims.model.events.AimsEmailMessagesAtt;
import com.netpace.aims.model.events.AimsEventNotificationLite;
import com.netpace.aims.model.events.AimsNotificationRole;
import com.netpace.aims.model.system.AimsEmailMessag;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.Utility;

/**
 * This class takes care for Create/Update functionality
 * of the system notification form.
 *
 * @struts.action path="/systemNotificationSetup"
 *                scope="request"
 *	          name="SystemNotificationForm"
 *                validate="false"
 *                input="/system/systemNotificationUpdate.jsp"
 * @struts.action-forward name="Update" path="/system/systemNotificationUpdate.jsp"
 * @author Fawad Sikandar
 */

public class SystemNotificationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(SystemNotificationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_NOTIFICATIONS);

        String taskname = request.getParameter("task");

        String dateFormat = this.getResources(request).getMessage("date.format");
        SystemNotificationForm notificationForm = (SystemNotificationForm) form;
        String forward = "Update";
        if (null == taskname)
            taskname = notificationForm.getTask();

        log.debug(" Task is : .... " + taskname);
        log.debug(" validate : ... " + request.getParameter("validate"));

        notificationForm.setEventList(AimsSystemNotificationManager.getEventList());
        notificationForm.setRoles(AimsSystemNotificationManager.getSysRoles());

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname))
        {
            notificationForm.setTask(AimsConstants.CREATE_TASK);
            return mapping.findForward(forward);
        }
        if (AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname) && !("true".equalsIgnoreCase(request.getParameter("validate"))))
        {
            notificationForm.setTask(AimsConstants.EDIT_TASK);
            AimsEmailMessag aimsEmailMessage = AimsSystemNotificationManager.getEmailMessage(Integer.parseInt(request.getParameter("systemNotificationId")));
            notificationForm.setSystemNotificationId(Utility.convertToString(aimsEmailMessage.getEmailMessageId()));
            notificationForm.setSubject(aimsEmailMessage.getEmailTitle());
            Collection attachments = AimsSystemNotificationManager.getMessageAttachmentsName(aimsEmailMessage.getEmailMessageId());
            Iterator attIter = attachments.iterator();
            AimsEmailMessagesAtt aimsEmailMsgAtt = null;

            while (attIter.hasNext())
            {
                aimsEmailMsgAtt = (AimsEmailMessagesAtt) attIter.next();
                notificationForm.setEmailAttachmentName(aimsEmailMsgAtt.getAttFileName());
            }

            notificationForm.setDescription(aimsEmailMessage.getEmailDesc());
            notificationForm.setContent(aimsEmailMessage.getEmailText());

            AimsEventNotificationLite eventNotification =
                AimsSystemNotificationManager.getEventNotificationByMessageId(Integer.parseInt(request.getParameter("systemNotificationId")));

            notificationForm.setVzwAccountManager(MiscUtils.convertToYesNo(eventNotification.getIncludeVzwAccountManager()));
            notificationForm.setAppManager(MiscUtils.convertToYesNo(eventNotification.getIncludeVzwAppsContact()));
            notificationForm.setEventId(Utility.convertToString(eventNotification.getEventId()));

            Collection notificationRecipirnts = AimsSystemNotificationManager.getAdHocRecipientsByNotifId(eventNotification.getNotificationId().toString());

            notificationForm.setRecipientEmail(MiscUtils.appendEmailAddressByDelimiter(notificationRecipirnts, ","));
            Collection set = eventNotification.getNotificationRoles();

            if (set != null && !(set.isEmpty()))
            {

                int i = 0;
                Iterator iter = set.iterator();
                Long[] array = new Long[set.size()];

                while (iter.hasNext())
                {
                    array[i++] = ((AimsNotificationRole) iter.next()).getAimsSysRole().getRoleId();
                }
                notificationForm.setRoleIds(array);
            }
            notificationForm.setCreatedBy(aimsEmailMessage.getCreatedBy());
            notificationForm.setCreatedDate(Utility.convertToString(aimsEmailMessage.getCreatedDate(), dateFormat));

            return mapping.findForward(forward);
        }

        return mapping.findForward(forward);
    }
}
