package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/allianceBusInfoSetup"  
 *                name="AllianceBusInfoForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdate" path="/alliance/allianceBusinessInfoUpdate.jsp"
 * @struts.action-forward name="allianceView" path="/alliance/allianceBusinessInfoView.jsp"
 * @struts.action-forward name="vzwView" path="/alliance/vzwAllianceBusinessInfoView.jsp"
 * @author Rizwan Qazi
 */
public class AllianceBusInfoSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceBusInfoSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = request.getParameter("task");
        log.debug("start AllianceBusInfoSetupAction, task= "+taskname);
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);

        //Get Form
        AllianceBusInfoForm allianceBusInfoForm = (AllianceBusInfoForm) form;
        //Set Temp File Ids to Zero
        allianceBusInfoForm.setCompanyPresentationTempFileId(new Long(0));
        allianceBusInfoForm.setCompanyLogoTempFileId(new Long(0));
        allianceBusInfoForm.setProgGuideTempFileId(new Long(0));

        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        Long alliance_id = null;
        String forward = "";

        if (user_type.equalsIgnoreCase("A"))
        {

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.BUSINESS_INFORMATION, AimsSecurityManager.SELECT)))
            {
                throw new AimsSecurityException();
            }

            alliance_id = user.getAimsAllianc();
            if (taskname.equalsIgnoreCase("createForm"))
            {
                if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.BUSINESS_INFORMATION, AimsSecurityManager.INSERT))
                {
                    forward = "createUpdate";
                }
                else
                {
                    forward = "allianceView";
                }
            }

            if (taskname.equalsIgnoreCase("editForm"))
            {
                if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.BUSINESS_INFORMATION, AimsSecurityManager.UPDATE))
                {
                    forward = "createUpdate";
                }
                else
                {
                    forward = "allianceView";
                }
            }
        }//end alliance user

        if (user_type.equalsIgnoreCase("V"))
        {
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.SELECT)))
            {
                throw new AimsSecurityException();
            }
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
            forward = "vzwView";
        }
        log.debug("end AllianceBusInfoSetupAction, forward= "+forward);
        return mapping.findForward(forward);
    }
}
