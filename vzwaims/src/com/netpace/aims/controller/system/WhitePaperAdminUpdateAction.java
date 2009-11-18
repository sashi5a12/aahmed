package com.netpace.aims.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceWhitePaperManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.alliance.WhitePaperForm;
import com.netpace.aims.model.alliance.AimsAllianceWhitePaper;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of update form of Tools.
 *
 * @struts.action path="/WPAdminUpdate"
 *                name="WhitePaperForm"
 *                scope="request"
 *                input="/WPAdminSetup.do"
 *				  validate="false"
 * @struts.action-forward name="update" path="/system/WPAdminUpdate.jsp"
 * @struts.action-forward name="listview" path="/WPAdminSetup.do"
 * @author Ahson Imtiaz
 */
public class WhitePaperAdminUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(WhitePaperAdminUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_WHITE_PAPERS);

        HttpSession session = request.getSession();
        String forward = "listview";
        String taskname = "";
        Object o_param;

        o_param = request.getParameter("task");

        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
        {
            taskname = "list";
        }

        if (taskname.equalsIgnoreCase("update"))
        {
            WhitePaperForm wPaperForm = (WhitePaperForm) form;
            log.debug("White Paper Id is : " + wPaperForm.getWhitePaperId().toString());
            AimsAllianceWhitePaper aawp = AllianceWhitePaperManager.getAimsAllianceWhitePaper(wPaperForm.getWhitePaperId());
            if (aawp != null)
            {
                log.debug("White Paper Status Modified . ");
                aawp.setStatus(wPaperForm.getWhitePaperStatus());
                AllianceWhitePaperManager.saveOrUpdate(aawp);
                log.debug("Record Saved for White Paper . ");
            }
        }

        //  		request.setAttribute("AimsWhitePapers",AllianceWhitePaperManager.getAimsAllianceWhitePapersList());
        forward = "listview";
        return mapping.findForward(forward);
    }
}
