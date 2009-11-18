package com.netpace.aims.controller.newmarketing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.newmarketing.ApplicationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * @struts.action path="/newMktApplication"
 *                scope="request"
 *                input="/newmarketing/applicationList.jsp"
 *                name="MktApplicationFilterForm"
 *                validate="false"
 * @struts.action-forward name="list" path="/newmarketing/applicationList.jsp"
 * @author Ahson Imtiaz
 */

public class MktApplicationAction extends BaseAction
{

    static Logger log = Logger.getLogger(MktApplicationAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        int iPageLength = user.getRowsLength().intValue();
        StringBuffer sbFilter = new StringBuffer();

        MktApplicationFilterForm frm = (MktApplicationFilterForm) form;
        if (frm.getTask() != null && (frm.getTask().equals("list") || frm.getTask().equals("next") || frm.getTask().equals("previous")))
        {
            Integer pageNo = frm.getPageNo();
            if (pageNo == null)
                pageNo = new Integer(1);

            if (frm.getTask().equals("list"))
            {
                pageNo = new Integer(1);
            }
            else if (frm.getTask().equals("next"))
            {
                pageNo = new Integer(pageNo.intValue() + 1);
            }
            else if (frm.getTask().equals("previous"))
            {
                pageNo = new Integer(pageNo.intValue() - 1);
            }

            frm.setPageNo(pageNo);

            // Application alias app, brew application alias bapp, alliance alias alliance, deck alias deck
            // View AimsBrewApp, AimsApp, AimsDeck, AimsAllianc for field level details.
            if (frm.getFilterText() != null && frm.getFilterText().length() > 0)
            {
                String strField = frm.getFilterField();
                if (strField.equals("applicationTitle"))
                    sbFilter.append(" upper(app.title) like '%").append(Utility.replaceString(frm.getFilterText(), "'", "''").toUpperCase()).append("%' ");
                else if (strField.equals("companyName"))
                    sbFilter.append(" upper(alliance.companyName) like '%").append(Utility.replaceString(frm.getFilterText(), "'", "''").toUpperCase()).append(
                        "%' ");
                else if (strField.equals("deckName"))
                    sbFilter.append(" upper(deck.deckName) like '%").append(Utility.replaceString(frm.getFilterText(), "'", "''").toUpperCase()).append("%' ");
            }
            request.setAttribute("ApplicationList", ApplicationManager.getApplications(sbFilter.toString(), iPageLength, pageNo.intValue()));
            int iTotalRecords = ApplicationManager.getPageCount(sbFilter.toString());
            int iNoOfPage = (iTotalRecords / iPageLength) + (iTotalRecords % iPageLength > 0 ? 1 : 0);
            request.setAttribute("TotalPages", new Integer(iNoOfPage));
            return mapping.getInputForward();
        }

        frm.setPageNo(new Integer(1));
        request.setAttribute("ApplicationList", ApplicationManager.getApplications(null, iPageLength, 1));
        int iTotalRecords = ApplicationManager.getPageCount(null);
        int iNoOfPage = (iTotalRecords / iPageLength) + (iTotalRecords % iPageLength > 0 ? 1 : 0);
        request.setAttribute("TotalPages", new Integer(iNoOfPage));
        return mapping.getInputForward();
    }
}
