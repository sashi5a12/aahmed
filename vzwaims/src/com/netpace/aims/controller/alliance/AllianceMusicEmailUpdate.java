package com.netpace.aims.controller.alliance;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.AllianceMusicInfoManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.system.AimsEmailMessag;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.StringFuncs;

/**
 * @struts.action path="/allianceMusicEmailUpdate"
 *                scope="request" 
 *                name="AllianceMusicEmailForm"
 *                validate="true"
 *                input="/alliance/allianceMusicEmail.jsp"
 * @struts.action-forward name="view" path="/alliance/allianceMusicEmail.jsp"
 * @author Adnan Makda
 */

public class AllianceMusicEmailUpdate extends BaseAction
{
    static Logger log = Logger.getLogger(AllianceMusicEmailUpdate.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));
        log.debug("Task : " + taskname);
        String forward = "view";
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_type = user.getUserType();

        if (user_type.equals(AimsConstants.ALLIANCE_USERTYPE))
            throw new AimsSecurityException();

        AllianceMusicEmailForm allianceMusicEmailForm = (AllianceMusicEmailForm) form;

        if (taskname.equalsIgnoreCase("send"))
        {
            String[] strArray = StringFuncs.tokenize(allianceMusicEmailForm.getToEmailAddress(), ",");

            for (int i = 0; i < strArray.length; i++)
            {
                System.out.println("EmailAddress : " + strArray[i]);
                MailUtils.sendMail(
                    strArray[i],
                    allianceMusicEmailForm.getFromEmailAddress(),
                    allianceMusicEmailForm.getEmailSubject(),
                    null,
                    allianceMusicEmailForm.getEmailBody());
            }

            ActionMessages messages = new ActionMessages();
            ActionMessage message = null;
            message = new ActionMessage("message.alliance_music_email_send.success");
            messages.add(ActionMessages.GLOBAL_MESSAGE, message);
            saveMessages(request, messages);
        }
        else if (taskname.equalsIgnoreCase("save"))
        {
            AimsEmailMessag emailMessage = null;
            emailMessage = AllianceMusicInfoManager.getAdditionalAggregatorInfoEmail();
            if (emailMessage != null)
            {
                emailMessage.setEmailTitle(allianceMusicEmailForm.getEmailSubject());
                emailMessage.setEmailText(allianceMusicEmailForm.getEmailBody());
                emailMessage.setFromAddress(allianceMusicEmailForm.getFromEmailAddress());
                emailMessage.setLastUpdatedBy(user.getUsername());
                emailMessage.setLastUpdatedDate(new Date());

                AllianceMusicInfoManager.saveAdditionalAggregatorInfoEmail(emailMessage);

                ActionMessages messages = new ActionMessages();
                ActionMessage message = null;
                message = new ActionMessage("message.alliance_music_email_save.success");
                messages.add(ActionMessages.GLOBAL_MESSAGE, message);
                saveMessages(request, messages);
            }
        }

        return mapping.findForward(forward);

    }
}
