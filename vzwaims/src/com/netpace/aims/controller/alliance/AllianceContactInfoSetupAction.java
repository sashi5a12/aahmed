package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
 * @struts.action path="/allianceContactInfoSetup"  
 *                name="AllianceContactInfoForm"  
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdate" path="/alliance/allianceContactInfoUpdate.jsp"
 * @struts.action-forward name="allianceView" path="/alliance/allianceContactInfoView.jsp"
 * @struts.action-forward name="vzwCreateUpdate" path="/alliance/vzwAllianceContactInfoUpdate.jsp"
 * @struts.action-forward name="vzwView" path="/alliance/vzwAllianceContactInfoView.jsp"
 * @author Rizwan Qazi
 */
public class AllianceContactInfoSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceContactInfoSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String taskname = request.getParameter("task");
        log.debug("start AllianceContactInfoSetupAction, task= "+taskname);
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        Long alliance_id = null;
        String forward = "";
        AllianceContactInfoForm contactForm=(AllianceContactInfoForm)form;
        if (user_type.equalsIgnoreCase("A"))
        {

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CONTACT_INFORMATION, AimsSecurityManager.SELECT)))
            {
                throw new AimsSecurityException();
            }

            alliance_id = user.getAimsAllianc();
            if (taskname.equalsIgnoreCase("createForm"))
            {
                if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CONTACT_INFORMATION, AimsSecurityManager.INSERT))
                {
                    forward = "createUpdate";
                }
                else
                {
                    forward = "allianceView";
                }
            }

        }//end alliance user
        if (taskname.equalsIgnoreCase("editForm"))
        {
            if ( (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user_type) && AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CONTACT_INFORMATION, AimsSecurityManager.UPDATE))
            		|| (AimsConstants.VZW_USERTYPE.equalsIgnoreCase(user_type) && AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.UPDATE_ALLIANCE_CONTACT_INFO_BY_VZW, AimsSecurityManager.UPDATE)))
            {
            	String contactId=request.getParameter("contactId");
            	String type=request.getParameter("cType");
            	if (StringUtils.isNotEmpty(contactId) && StringUtils.isNotEmpty(type)){
            		if ("executive".equalsIgnoreCase(type)){
            			contactForm.setExecContactId(contactId);
            		}
            		else if ("business".equalsIgnoreCase(type)){
            			contactForm.setBusContactId(contactId);
            		}
            		else if ("marketing".equalsIgnoreCase(type)){
            			contactForm.setMktgContactId(contactId);
            		}
            		else if ("technical".equalsIgnoreCase(type)){
            			contactForm.setTechContactId(contactId);
            		}
            		
            	}
                if(AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user_type)) {
                    forward = "createUpdate";
                }
                else {
                    forward = "vzwCreateUpdate";
                }

            }
            else
            {
                if(AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user_type)) {
                    forward = "allianceView";
                }
                else {
                    forward = "vzwView";
                }
            }
        }
        if (user_type.equalsIgnoreCase("V"))
        {
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.SELECT)))
            {
                throw new AimsSecurityException();
            }
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
            forward = "vzwCreateUpdate";
        }
        log.debug("end AllianceContactInfoSetupAction, forward= "+forward);
        return mapping.findForward(forward);
    }
}
