package com.netpace.aims.controller.system;

import java.text.SimpleDateFormat;

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
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for display of update form of Tools.
 *
 * @struts.action path="/WPAdminSetup"
 *                name="WhitePaperFilterForm"
 *                scope="request"
 *                input="/system/WPAdminList.jsp"
 *				  validate="true"
 * @struts.action-forward name="update" path="/system/WPAdminUpdate.jsp"
 * @struts.action-forward name="listview" path="/system/WPAdminList.jsp"
 * @author Ahson Imtiaz
 */
public class WhitePaperAdminSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(WhitePaperAdminSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_WHITE_PAPERS);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        WhitePaperFilterForm whitePaperFilterForm = (WhitePaperFilterForm) form;
        String forward = "listview";
        String taskname = "";
        Object o_param;
        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;
        pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);

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

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_field")).length() > 0)
        {
            whitePaperFilterForm.setFilterField(StringFuncs.initializeStringGetParam(request.getParameter("filter_field"), "alliance_name"));
        }
        else
        {
            whitePaperFilterForm.setFilterField(StringFuncs.initializeStringGetParam(whitePaperFilterForm.getFilterField(), "alliance_name"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_text")).trim().length() > 0)
        {
            whitePaperFilterForm.setFilterText(request.getParameter("filter_text"));
        }

        log.debug("whitePaperFilterForm.getFilterField() : " + whitePaperFilterForm.getFilterField());
        log.debug("whitePaperFilterForm.getFilterText() : " + whitePaperFilterForm.getFilterText());

        if (taskname.equalsIgnoreCase("view"))
        {
            String strWhitePaperId = request.getParameter("whitePaperId");
            if (strWhitePaperId != null)
            {
                java.lang.Long lngWhitePaperId = new java.lang.Long(strWhitePaperId);
                AimsAllianceWhitePaperExt aawpex = AllianceWhitePaperManager.getAimsAllianceWhitePaperExt(lngWhitePaperId);
                if (aawpex != null)
                {
                    WhitePaperForm wPaperForm = new WhitePaperForm();
                    wPaperForm.setWhitePaperId(aawpex.getWhitePaperId());
                    wPaperForm.setWhitePaperStatus(aawpex.getStatus());
                    request.setAttribute("AimsWhitePaper", aawpex);
                    request.setAttribute("WhitePaperForm", wPaperForm);
                    forward = "update";
                    return mapping.findForward(forward);
                }
            }
        }

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date date = null;
        WhitePaperFilterForm wPaperForm = (WhitePaperFilterForm) form;
        try
        {
            date = df.parse(wPaperForm.getSubmittedDate());

        }
        catch (Exception ex)
        {
            // no date should be included in criteria for filter as it is null
        }

        int rows =
            AllianceWhitePaperManager.getAimsAllianceWhitePapersCount(
                getFilterExpression(
                    StringFuncs.initializeStringGetParam(whitePaperFilterForm.getFilterField(), ""),
                    StringFuncs.initializeStringGetParam(whitePaperFilterForm.getFilterText(), "")));
        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

        if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
        {
            pageNo = 1;
            pmax = 1;
        }

        request.setAttribute(
            "AimsWhitePapers",
            AllianceWhitePaperManager.getAimsAllianceWhitePapersList(
                getFilterExpression(
                    StringFuncs.initializeStringGetParam(whitePaperFilterForm.getFilterField(), ""),
                    StringFuncs.initializeStringGetParam(whitePaperFilterForm.getFilterText(), "")),
                PAGE_LENGTH,
                pageNo));

        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));

        forward = "listview";
        log.debug("Forward String Before Forwarding: " + forward);
        return mapping.findForward(forward);
    }

    private String getFilterExpression(String filter_field, String filter_text)
    {

        StringBuffer expressionBuffer = new StringBuffer("");

        if (filter_text.trim().length() > 0)
        {
            if (filter_field.equalsIgnoreCase("alliance_name"))
            {
                expressionBuffer.append("		and upper(aalliance.companyName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }

            if (filter_field.equalsIgnoreCase("submitted_date"))
            {
                if (AimsUtils.isDate(filter_text.trim()))
                {
                    expressionBuffer.append("		and trunc(aawp.submittedDate) = to_date('" + filter_text.trim() + "', 'MM/dd/yyyy')");
                }
                else
                {
                    expressionBuffer.append("		and aawp.submittedDate = to_date('12/31/9999', 'MM/dd/yyyy')");
                }

            }

        }

        return expressionBuffer.toString();
    }

}
