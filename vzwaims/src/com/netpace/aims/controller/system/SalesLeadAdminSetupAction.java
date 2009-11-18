package com.netpace.aims.controller.system;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceSalesLeadManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.alliance.SalesLeadForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for display of update form of Tools.
 *
 * @struts.action path="/SLAdminSetup"
 *                name="SalesLeadForm"
 *      	  validate="false"
 *                input="/system/SLAdminList.jsp"
 * @struts.action-forward name="update" path="/system/SLAdminUpdate.jsp"
 * @struts.action-forward name="view" path="/system/SLAdminView.jsp"
 * @struts.action-forward name="listview" path="/system/SLAdminList.jsp"
 * @author Ahson Imtiaz
 */
public class SalesLeadAdminSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(SalesLeadAdminSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_SALES_LEADS);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        SalesLeadForm salesLeadForm = (SalesLeadForm) form;
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
            salesLeadForm.setFilterField(StringFuncs.initializeStringGetParam(request.getParameter("filter_field"), "alliance_name"));
        }
        else
        {
            salesLeadForm.setFilterField(StringFuncs.initializeStringGetParam(salesLeadForm.getFilterField(), "alliance_name"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_text")).trim().length() > 0)
        {
            salesLeadForm.setFilterText(request.getParameter("filter_text"));
        }

        log.debug("salesLeadForm.getFilterField() : " + salesLeadForm.getFilterField());
        log.debug("salesLeadForm.getFilterText() : " + salesLeadForm.getFilterText());

        if (taskname.equalsIgnoreCase("view") || taskname.equalsIgnoreCase("edit"))
        {
            log.debug("\n\n\n  inside view or delete : " + taskname);

            String strSalesLeadId = request.getParameter("salesLeadId");
            if (strSalesLeadId != null)
            {
                java.lang.Long lngSalesLeadId = new java.lang.Long(strSalesLeadId);
                SalesLeadForm aaslf = AllianceSalesLeadManager.getAimsAllianceSalesLeadExt(lngSalesLeadId);

                Collection list = AllianceSalesLeadManager.getSubmittedSalesLeadApps(lngSalesLeadId);

                request.setAttribute("appList", list);

                if (aaslf != null)
                {
                    aaslf.reset(mapping, request);
                    request.setAttribute("SalesLeadForm", aaslf);

                    if (taskname.equalsIgnoreCase("edit"))
                        forward = "update";
                    else
                        forward = "view";

                    return mapping.findForward(forward);
                }
            }
        }
        else if (taskname.equalsIgnoreCase("delete"))
        {
            String strSalesLeadId = request.getParameter("salesLeadId");
            if (strSalesLeadId != null)
            {
                java.lang.Long lngSalesLeadId = new java.lang.Long(strSalesLeadId);
                AllianceSalesLeadManager.deleteAimsAllianceSalesLead(lngSalesLeadId);
            }

        }

        int rows = AllianceSalesLeadManager.getAimsAllianceSalesLeadCount();
        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

        if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
        {
            pageNo = 1;
            pmax = 1;
        }

        request.setAttribute(
            "AimsSalesLeads",
            AllianceSalesLeadManager.getAimsAllianceSalesLeadList(
                getFilterExpression(
                    StringFuncs.initializeStringGetParam(salesLeadForm.getFilterField(), ""),
                    StringFuncs.initializeStringGetParam(salesLeadForm.getFilterText(), "")),
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
                    expressionBuffer.append("		and trunc(aasl.submittedDate) = to_date('" + filter_text.trim() + "', 'MM/dd/yyyy')");
                }
                else
                {
                    expressionBuffer.append("		and aasl.submittedDate = to_date('12/31/9999', 'MM/dd/yyyy')");
                }

            }

        }

        return expressionBuffer.toString();
    }

}
