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

/**
 * @struts.action path="/newMktProvApps"
 *                scope="request"
 *                input="/newmarketing/provAppsList.jsp"
 *                name="MktProvAppsForm"
 *                validate="false"
 * @struts.action-forward name="list" path="/newmarketing/provAppsList.jsp"
 * @struts.action-forward name="view" path="/newmarketing/provApplicationView.jsp"
 * @author Ahson Imtiaz
 */
public class MktProvAppsAction extends BaseAction
{

    static Logger log = Logger.getLogger(MktProvAppsAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        int iPageLength = user.getRowsLength().intValue();
        StringBuffer sbFilter = new StringBuffer();

        MktProvAppsForm frm = (MktProvAppsForm) form;
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
            //
            //	    	request.setAttribute("ApplicationList",ApplicationManager.getProvApplications(sbFilter.toString(),iPageLength,pageNo.intValue(),user.getUserId()));
            //
            request.setAttribute("ApplicationList", ApplicationManager.getProvContents(sbFilter.toString(), iPageLength, pageNo.intValue(), user.getUserId()));
            int iTotalRecords = ApplicationManager.getProvRecordCount(sbFilter.toString(), user.getUserId());
            int iNoOfPage = (iTotalRecords / iPageLength) + (iTotalRecords % iPageLength > 0 ? 1 : 0);
            request.setAttribute("TotalPages", new Integer(iNoOfPage));
            return mapping.getInputForward();
        }
        else if (frm.getTask() != null && frm.getTask().equals("view"))
        {
            log.debug(" ******* Application Id: " + request.getParameter("applicationId"));
            request.setAttribute("AppDetails", ApplicationManager.getAcceptedContentDetail(new Long(request.getParameter("applicationId"))));
            return mapping.findForward("view");
        }

        frm.setPageNo(new Integer(1));
        request.setAttribute("ApplicationList", ApplicationManager.getProvApplications(null, iPageLength, 1, user.getUserId()));
        request.setAttribute("ApplicationList", ApplicationManager.getProvContents(null, iPageLength, 1, user.getUserId()));
        int iTotalRecords = ApplicationManager.getProvRecordCount(null, user.getUserId());
        log.debug(" THE TOTAL RECORD FOR LISTS ARE : " + iTotalRecords);
        int iNoOfPage = (iTotalRecords / iPageLength) + (iTotalRecords % iPageLength > 0 ? 1 : 0);
        request.setAttribute("TotalPages", new Integer(iNoOfPage));
        return mapping.getInputForward();
    }
}
