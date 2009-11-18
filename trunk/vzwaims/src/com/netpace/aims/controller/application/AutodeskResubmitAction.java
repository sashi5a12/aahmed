package com.netpace.aims.controller.application;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.BrewApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.webservices.InfospaceUtils;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsBrewApp;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.MiscUtils;

/**
 * This class is used to get resources for InfoSpace.
 *
 * @struts.action path="/autodeskResubmit"
 *                scope="request"
 * @struts.action-forward name="thankyou" path="/public/thankyou.jsp"
 * @author Adnan Makda
 */
public class AutodeskResubmitAction extends BaseAction
{

    static Logger log = Logger.getLogger(AutodeskResubmitAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        Object o_param;
        String app_id;
        String phase_id;
        String other_info;
        String xmlFolderPath = getServlet().getServletContext().getRealPath(AimsConstants.AUTODESK_XML_DIR);
        String hostName = request.getServerName();
        String forward = "thankyou";

        o_param = request.getParameter("app_id");
        if (o_param != null)
            app_id = request.getParameter("app_id");
        else
            app_id = "0";

        o_param = request.getParameter("p_id");
        if (o_param != null)
            phase_id = request.getParameter("p_id");
        else
            phase_id = "0";

        o_param = request.getParameter("other_info");
        if (o_param != null)
            other_info = request.getParameter("other_info");
        else
            other_info = "0";

        if (!(app_id.equals("0")) && !(phase_id.equals("0")) && !(other_info.equals("0")))
        {
            //Authenticating URL
            String initialInfo =
                MiscUtils.getBase64Digest(
                    InfospaceUtils.utf8decode(AimsConstants.AUTODESK_KEY_DIGEST_FOR_URLS),
                    InfospaceUtils.utf8decode(app_id),
                    InfospaceUtils.utf8decode(phase_id));

            if (!other_info.equals(initialInfo))
            {
                StringBuffer emailSubject = new StringBuffer("Hacking Tried in AutodeskResubmitAction, on .... ");
                if (request.getServerName() != null)
                    emailSubject.append(request.getServerName());

                MailUtils.sendMail(
                    AimsConstants.EMAIL_EXCEPTION_ADMIN,
                    "exceptions@netpace.com",
                    emailSubject.toString(),
                    null,
                    MiscUtils.getRequestInfo(request));
            }
            else
            {
                AimsApp aimsApp = null;
                AimsBrewApp aimsBrewApp = null;
                HashMap appBrew = null;

                try
                {
                    appBrew = BrewApplicationManager.getApp(new Long(app_id), null);
                }
                catch (AimsException ignoreEx)
                {
                    return null;
                }

                aimsApp = (AimsApp) appBrew.get("AimsApp");
                aimsBrewApp = (AimsBrewApp) appBrew.get("AimsBrewApp");

                BrewApplicationHelper.sendDCRToAutodesk(aimsApp, aimsBrewApp, new Long(phase_id), xmlFolderPath, hostName, false);
            }
        }

        return mapping.findForward(forward);
    }
}
