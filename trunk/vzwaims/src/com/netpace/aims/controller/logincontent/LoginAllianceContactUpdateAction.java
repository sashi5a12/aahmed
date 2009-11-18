package com.netpace.aims.controller.logincontent;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.Utility;
import com.netpace.aims.bo.logincontent.AimsLoginContentManager;
import com.netpace.aims.bo.alliance.AllianceContactInfoManager;
import com.netpace.aims.model.core.AimsUser;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Vector;

/**
 * This class takes care of Alliance contact update when alliance logs in (login content configued)
 * @struts.action path="/loginAllianceContactUpdateAction"
 *                scope="request"
 *				  name="LoginAllianceContactUpdateForm"
 *				  validate="true"
 *                input="/logincontent/loginAllianceContactUpdate.jsp"
 * @struts.action-forward name="edit" path="/logincontent/loginAllianceContactUpdate.jsp"
 * @struts.action-forward name="success" path="/userHome.do"
 * @author Sajjad Raza
 */

public class LoginAllianceContactUpdateAction extends BaseAction
{

    private static Logger log = Logger.getLogger(LoginAllianceContactUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        log.debug("LoginAllianceContactUpdateAction starts ... ");
        String forward = "edit";
        String task = StringFuncs.NullValueReplacement(request.getParameter("task"));
        Vector loginContentIds = null;
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
        Long alliance_id = user.getAimsAllianc();

        Long loginContentId = (Long)session.getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW);
        LoginAllianceContactUpdateForm allianceContactInfoForm = (LoginAllianceContactUpdateForm) form;

        ActionForward newForward = null;
        ActionErrors errors = null;

        if(Utility.ZeroValueReplacement(loginContentId).longValue()>0) {
            if(task.equals("editForm")) {
                allianceContactInfoForm.setParentPageType(AimsConstants.PAGE_TYPE_LOGIN_ALLIANCE_CONTACT_UPDATE);
                allianceContactInfoForm.setParentPath("loginAllianceContactUpdateAction.do?task=editForm");

                String contactId=request.getParameter("contactId");
                String type=request.getParameter("cType");
                	if (StringUtils.isNotEmpty(contactId) && StringUtils.isNotEmpty(type)){
                		if ("executive".equalsIgnoreCase(type)){
                			allianceContactInfoForm.setExecContactId(contactId);
                		}
                		else if ("business".equalsIgnoreCase(type)){
                			allianceContactInfoForm.setBusContactId(contactId);
                		}
                		else if ("marketing".equalsIgnoreCase(type)){
                			allianceContactInfoForm.setMktgContactId(contactId);
                		}
                		else if ("technical".equalsIgnoreCase(type)){
                			allianceContactInfoForm.setTechContactId(contactId);
                		}
                	}
                forward = "edit";
            }
            if(task.equals("update")) {
                try {
                    //update contact info
                    AllianceContactInfoManager.UpdateAllianceContactInfo(
                        alliance_id,
                        allianceContactInfoForm.getExecContactId(),
                        allianceContactInfoForm.getBusContactId(),
                        allianceContactInfoForm.getMktgContactId(),
                        allianceContactInfoForm.getTechContactId(),
                        user_name);

                    AimsLoginContentManager.updateLoginContentAllianceAck(loginContentId, alliance_id, user_name);
                    session.removeAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW);
                    loginContentIds = (Vector)session.getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_IDS_TO_SHOW);
                    newForward = AimsLoginContentManager.setNextLoginContent(request, loginContentIds);
                    if(newForward == null) {
                        forward = "success";
                    }
                }
                catch(Exception e) {
                    return mapping.getInputForward();
                }
            }//end if update
        }//end not zero
        else {
            //login content not present in session, forward to user home
            forward = "success";
        }
        log.debug("LoginAllianceContactUpdateAction ends ... forward: "+forward);
        return newForward==null ? mapping.findForward(forward) : newForward;
    }
}
