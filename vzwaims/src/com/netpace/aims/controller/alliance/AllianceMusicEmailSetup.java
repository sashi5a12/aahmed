package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceMusicInfoManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.system.AimsEmailMessag;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * @struts.action path="/allianceMusicEmailSetup"
 *                name="AllianceMusicEmailForm"
 *                input="/alliance/allianceMusicInfoUpdate.jsp"
 *				  validate="false"
 * @struts.action-forward name="view" path="/alliance/allianceMusicEmail.jsp"
 * @author Adnan Makda
 */

public class AllianceMusicEmailSetup extends BaseAction
{
    static Logger log = Logger.getLogger(AllianceMusicEmailSetup.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        if (user.getUserType().equals(AimsConstants.ALLIANCE_USERTYPE))
            throw new AimsSecurityException();

        AllianceMusicEmailForm allianceMusicEmailForm = (AllianceMusicEmailForm) form;
        AimsEmailMessag emailMessage = AllianceMusicInfoManager.getAdditionalAggregatorInfoEmail();

        if (emailMessage != null)
        {
            allianceMusicEmailForm.setEmailSubject(emailMessage.getEmailTitle());
            allianceMusicEmailForm.setEmailBody(emailMessage.getEmailText());
            allianceMusicEmailForm.setFromEmailAddress(emailMessage.getFromAddress());
        }

        Long allianceId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("allianceId"), "0"));

        AimsAllianc aimsAlliance = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, allianceId.toString());
        AimsUser aimsUser = (AimsUser) DBHelper.getInstance().load(AimsUser.class, aimsAlliance.getAimsUserByAdminUserId().toString());

        allianceMusicEmailForm.setToEmailAddress(aimsUser.getUsername());

        return mapping.findForward("view");
    }
}
