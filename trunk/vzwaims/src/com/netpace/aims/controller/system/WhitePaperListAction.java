package com.netpace.aims.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceWhitePaperManager;
import com.netpace.aims.controller.BaseAction;

/**
 * This class takes care of action for display of update form of Tools. Link from Main Login Page (Before Login)
 *
 * @struts.action path="/WPList"
 *                input="/system/whitePapers.jsp"
 *		  validate="false"
 * @struts.action-forward name="listview" path="/system/whitePapers.jsp"
 * @author Fawad Sikandar
 */
public class WhitePaperListAction extends BaseAction
{

    static Logger log = Logger.getLogger(WhitePaperListAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String forward = "listview";
        String status = "A";

        request.setAttribute("AimsWhitePapers", AllianceWhitePaperManager.getWhitePapersListByStatus(status));

        return mapping.findForward(forward);

    }
}
