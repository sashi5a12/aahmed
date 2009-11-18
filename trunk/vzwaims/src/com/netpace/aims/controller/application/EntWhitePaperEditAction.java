package com.netpace.aims.controller.application;

import org.apache.struts.action.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.application.EntAppsSpotlightsManager;
import com.netpace.aims.bo.core.*;
import com.netpace.aims.bo.security.*;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.*;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.application.EntAppsSpotLightForm;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/entWhitePaperEdit"                
 *                scope="request" 
 *				  name="EntAppsSpotLightForm"
 *				  validate="true"
 *                input="/application/whitePaperCreateUpdate.jsp"
 * @struts.action-forward name="view" path="/entAppsSpotlights.do?task=view&amp;spotLightTypeId=5" redirect="true"
 * @author Rizwan Qazi
 */

public class EntWhitePaperEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(EntWhitePaperEditAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));

        HttpSession session = request.getSession();
        EntAppsSpotLightForm allianceSpotLightForm = (EntAppsSpotLightForm) form;
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        ActionErrors errors = new ActionErrors();

        if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SPOTLIGHT_PAGE, AimsSecurityManager.UPDATE)))
        {
            throw new AimsSecurityException();
        }

        Long enterprise_apps_id = allianceSpotLightForm.getEnterpriseAppsId();
        String user_type = user.getUserType();
        String user_name = user.getUsername();
        Long user_id = user.getUserId();
        Long spotLightId = null;
        Long spotLightTypeId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("spotLightTypeId"), "0"));

        String forward = "view";

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("edit"))
        {
            spotLightId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("spotlightId"), "0"));

            try
            {

                EntAppsSpotlightsManager.UpdateSpotLight(
                    spotLightId,
                    allianceSpotLightForm.getSpotlightTypeId(),
                    allianceSpotLightForm.getSpotlightName(),
                    allianceSpotLightForm.getSpotlightDesc(),
                    allianceSpotLightForm.getSpotlightFile(),
                    user_name,
                    enterprise_apps_id);

                if ((allianceSpotLightForm.getSpotlightFile() != null) && (allianceSpotLightForm.getSpotlightFile().getFileSize() > 0))
                {
                    SpotlightEventHelper.raiseDocumentUploadEvent(
                        enterprise_apps_id,
                        allianceSpotLightForm.getSpotlightTypeId(),
                        allianceSpotLightForm.getSpotlightName(),
                        allianceSpotLightForm.getSpotlightFile().getFileName());
                }
            }
            catch (AimsException ae)
            {

                log.debug("ae.getMessageKey() in Action Class : " + ae.getMessageKey());
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));
                saveErrors(request, errors);
                return mapping.getInputForward();
            }

            forward = "view";

        }

        if (taskname.equalsIgnoreCase("create"))
        {
            try
            {
                EntAppsSpotlightsManager.CreateSpotLight(
                    allianceSpotLightForm.getSpotlightTypeId(),
                    allianceSpotLightForm.getSpotlightName(),
                    allianceSpotLightForm.getSpotlightDesc(),
                    allianceSpotLightForm.getSpotlightFile(),
                    user_name,
                    enterprise_apps_id);

                SpotlightEventHelper.raiseDocumentUploadEvent(
                    enterprise_apps_id,
                    allianceSpotLightForm.getSpotlightTypeId(),
                    allianceSpotLightForm.getSpotlightName(),
                    allianceSpotLightForm.getSpotlightFile().getFileName());
            }
            catch (AimsException ae)
            {
                log.debug("ae.getMessageKey() in Action Class : " + ae.getMessageKey());
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));
                saveErrors(request, errors);
                return mapping.getInputForward();
            }

            forward = "view";

        }

        return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=5&enterpriseAppsId=" + allianceSpotLightForm.getEnterpriseAppsId(), true);
    }
}
