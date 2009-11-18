package com.netpace.aims.controller.alliance;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of Change Alliance Administrator
 * Form.
 * 
 * @struts.action path="/changeAllianceAdmin" 
 *                name="ChangeAllianceAdminForm"
 *                scope="request" 
 *                input="/allianceStatus.do?task=view"
 *                validate="false"
 * @struts.action-forward name="view" path="/alliance/changeAllianceAdmin.jsp"
 * @author Adnan Makda
 */
public class ChangeAllianceAdminSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(ChangeAllianceAdminSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws AimsSecurityException, Exception
    {

        // Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.CHANGE_ALLIANCE_ADMIN);

        HttpSession session = request.getSession();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currentUserType = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getUserType();

        String forward = "view";

        ChangeAllianceAdminForm changeAllianceAdminForm = (ChangeAllianceAdminForm) form;

        Collection AimsAlliance = AllianceManager.getAllianceDetails(currentUserAllianceId, currentUserType);
        Object[] userValues = null;

        for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
        {
            userValues = (Object[]) iter.next();

            changeAllianceAdminForm.setAllianceAdminName((String) userValues[14] + " " + (String) userValues[15]);
            changeAllianceAdminForm.setAllianceAdminEmailAddress((String) userValues[16]);
            changeAllianceAdminForm.setAllianceAdminPhone((String) userValues[17]);
        }

        /*
         AimsAllianc aimsAlliance = (AimsAllianc) DBHelper.getInstance().load(
         com.netpace.aims.model.core.AimsAllianc.class, currentUserAllianceId.toString());

         AimsUser aimAllianceAdminUser = (AimsUser) DBHelper.getInstance().load(
         com.netpace.aims.model.core.AimsUser.class, aimsAlliance.getAimsUserByAdminUserId().toString());

         AimsContact aimAllianceAdminContact = (AimsContact) DBHelper.getInstance().load(
         com.netpace.aims.model.core.AimsContact.class, aimAllianceAdminUser.getAimsContactId().toString());

         changeAllianceAdminForm.setAllianceAdminName(aimAllianceAdminContact.getFirstName() + " "
         + aimAllianceAdminContact.getLastName());
         changeAllianceAdminForm.setAllianceAdminEmailAddress(aimAllianceAdminContact.getEmailAddress());
         changeAllianceAdminForm.setAllianceAdminPhone(aimAllianceAdminContact.getPhone());
         */

        return mapping.findForward(forward);
    }
}
