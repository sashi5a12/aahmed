package com.netpace.aims.controller.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.alliance.VZWAllianceManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;

/**
 *
 *
 * @struts.action path="/vzwAllianceSearch"                
 *                scope="request" 
 *				  name="VZWAllianceForm"
 *				  validate="true"
 *                input="/alliance/vzwAllianceSearch.jsp"  
 * @struts.action-forward name="search" path="/alliance/vzwAllianceSearchViewDelete.jsp"
 * @struts.action-exception key="masters.integrity.constraint.violation" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */
public class VzwAllianceSearchAction extends BaseAction
{

    static Logger log = Logger.getLogger(VzwAllianceSearchAction.class.getName());

    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.SEARCH_ALLIANCES);

        String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
        String sub_task_name = StringFuncs.NullValueReplacement(request.getParameter("sub_task"));
        HttpSession session = request.getSession();

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String dateFormat = this.getResources(request).getMessage("date.format");
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        VZWAllianceForm allianceForm = null;
        VZWAllianceForm vzwAllianceForm = (VZWAllianceForm) form;
        String forward = "";
        Collection VZWAlliances = new ArrayList();
        Collection AimsAlliance = null;
        Object[] userValues = null;
        String inFilterExpression = null;
        String secondaryFilterExpression = null;
        ActionErrors errors = new ActionErrors();

        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;

        try
        {
            pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);
        }
        catch (Exception e)
        {
            pageNo = 1;
        }

        log.debug("The alliance type is " + request.getParameter("allianceType"));

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_field")).length() > 0)
        {
            vzwAllianceForm.setFilterField(StringFuncs.initializeStringGetParam(request.getParameter("filter_field"), "alliance_name"));
        }
        else
        {
            vzwAllianceForm.setFilterField(StringFuncs.initializeStringGetParam(vzwAllianceForm.getFilterField(), "alliance_name"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_text")).trim().length() > 0)
        {
            vzwAllianceForm.setFilterText(request.getParameter("filter_text"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("sort_field")).trim().length() > 0)
        {
            vzwAllianceForm.setSortField(request.getParameter("sort_field"));
        }

        if (sub_task_name.equalsIgnoreCase("onhold"))
        {

            if (StringFuncs.NullValueReplacement(request.getParameter("on_hold")).trim().length() > 0)
            {

                if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.UPDATE))
                {
                    VZWAllianceManager.UpdateAllianceOnHold(
                        new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0")),
                        StringFuncs.initializeStringGetParam(request.getParameter("on_hold"), "N"),
                        user_name);
                }
            }

        }

        if (sub_task_name.equalsIgnoreCase("delete"))
        {
            try
            {
                if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.UPDATE))
                {
                    VZWAllianceManager.DeleteAlliance(new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0")), user_name);
                }
            }
            catch (AimsException ae)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));
                saveErrors(request, errors);
            }

        }

        if (task_name.equalsIgnoreCase("search"))
        {

            if (StringFuncs.NullValueReplacement(request.getParameter("allianceType")).length() > 0)
            {
                vzwAllianceForm.setAllianceType(StringFuncs.initializeStringGetParam(request.getParameter("allianceType"), "ALL"));
            }
            else
            {
                vzwAllianceForm.setAllianceType(StringFuncs.initializeStringGetParam(request.getParameter("allianceType"), "ALL"));
            }

            inFilterExpression =
                getFilterExpression(
                    StringFuncs.NullValueReplacement(vzwAllianceForm.getCompanyName()).trim(),
                    StringFuncs.NullValueReplacement(vzwAllianceForm.getStatus()).trim(),
                    StringFuncs.NullValueReplacement(vzwAllianceForm.getVzwAccMgrFirstName()).trim(),
                    StringFuncs.NullValueReplacement(vzwAllianceForm.getVzwAccMgrLastName()).trim(),
                    StringFuncs.NullValueReplacement(vzwAllianceForm.getAllianceAdminFirstName()).trim(),
                    StringFuncs.NullValueReplacement(vzwAllianceForm.getAllianceAdminLastName()).trim(),
                    StringFuncs.NullValueReplacement(vzwAllianceForm.getContractId()).trim(),
                    StringFuncs.NullValueReplacement(vzwAllianceForm.getDateEstablishedFrom()).trim(),
                    StringFuncs.NullValueReplacement(vzwAllianceForm.getDateEstablishedTo()).trim());

            int rows =
                VZWAllianceManager.getAllianceDetailsPageCount(
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getSortField(), "1"),
                    inFilterExpression,
                    vzwAllianceForm.getAllianceType(),
                    user_id);

            int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

            AimsAlliance =
                VZWAllianceManager.getAllianceDetails(
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getSortField(), "1"),
                    inFilterExpression,
                    vzwAllianceForm.getAllianceType(),
                    PAGE_LENGTH,
                    pageNo,
                    user_id);

            for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
            {
                userValues = (Object[]) iter.next();

                allianceForm = new VZWAllianceForm();

                allianceForm.setCompanyName((String) userValues[0]);
                allianceForm.setCompanyLegalName((String) userValues[1]);
                allianceForm.setDateEstablished((Date) userValues[2]);
                allianceForm.setStatus(vzwAllianceForm.getAllianceStatus((String) userValues[3]));
                allianceForm.setVzwAccMgrFirstName((String) userValues[4]);
                allianceForm.setVzwAccMgrLastName((String) userValues[5]);
                allianceForm.setAllianceAdminFirstName((String) userValues[8]);
                allianceForm.setAllianceAdminLastName((String) userValues[9]);
                allianceForm.setAllianceId((Long) userValues[10]);
                allianceForm.setIsOnHold((String) userValues[11]);

                VZWAlliances.add(allianceForm);

            }

            session.setAttribute("in_filter_expression", inFilterExpression);
            session.setAttribute("alliance_type", vzwAllianceForm.getAllianceType());
            request.setAttribute("page_id", new Integer(pageNo));
            request.setAttribute("page_max", new Integer(pmax));

            request.setAttribute("VZWAlliances", VZWAlliances);

            forward = "search";
        }

        if (task_name.equalsIgnoreCase("in_search"))
        {
            inFilterExpression = (String) session.getAttribute("in_filter_expression");
            secondaryFilterExpression =
                getSecondaryFilterExpression(
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getFilterField(), ""),
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getFilterText(), ""));

            int rows =
                VZWAllianceManager.getAllianceDetailsPageCount(
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getSortField(), "1"),
                    inFilterExpression + secondaryFilterExpression,
                    (String) session.getAttribute("alliance_type"),
                    user_id);

            int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

            AimsAlliance =
                VZWAllianceManager.getAllianceDetails(
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getSortField(), "1"),
                    inFilterExpression + secondaryFilterExpression,
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getAllianceType(), "ALL"),
                    PAGE_LENGTH,
                    pageNo,
                    user_id);

            for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
            {
                userValues = (Object[]) iter.next();

                allianceForm = new VZWAllianceForm();

                allianceForm.setCompanyName((String) userValues[0]);
                allianceForm.setCompanyLegalName((String) userValues[1]);
                allianceForm.setDateEstablished((Date) userValues[2]);
                allianceForm.setStatus(vzwAllianceForm.getAllianceStatus((String) userValues[3]));
                allianceForm.setVzwAccMgrFirstName((String) userValues[4]);
                allianceForm.setVzwAccMgrLastName((String) userValues[5]);
                allianceForm.setAllianceAdminFirstName((String) userValues[8]);
                allianceForm.setAllianceAdminLastName((String) userValues[9]);
                allianceForm.setAllianceId((Long) userValues[10]);
                allianceForm.setIsOnHold((String) userValues[11]);

                VZWAlliances.add(allianceForm);

            }

            request.setAttribute("page_id", new Integer(pageNo));
            request.setAttribute("page_max", new Integer(pmax));

            request.setAttribute("VZWAlliances", VZWAlliances);

            forward = "search";
        }

        return mapping.findForward(forward);
    }

    private String getFilterExpression(
        String company_name,
        String status,
        String account_manager_first_name,
        String account_manager_last_name,
        String alliance_admin_first_name,
        String alliance_admin_last_name,
        String contract_id,
        String date_established_from,
        String date_established_to)
    {

        StringBuffer expressionBuffer = new StringBuffer("");

        if (company_name.length() > 0)
        {
            expressionBuffer.append("		and upper(alliance.companyName) like '%" + StringFuncs.sqlEscape(company_name.trim().toUpperCase()) + "%'");
        }

        if (status.length() > 0 && (!status.equals("0")))
        {
            expressionBuffer.append("		and upper(alliance.status) = '" + status.toUpperCase() + "'");
        }

        if (account_manager_first_name.length() > 0)
        {
            expressionBuffer.append(
                "		and upper(vzwAccountManager.firstName) like '%" + StringFuncs.sqlEscape(account_manager_first_name.toUpperCase()) + "%' ");
            //.append("		or upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(account_manager_name.toUpperCase()) + "%' ")
            //.append("		or upper(vzwAccountManager.firstName) || ' ' || upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(account_manager_name.toUpperCase()) + "%') ");							
        }

        if (account_manager_last_name.length() > 0)
        {
            expressionBuffer.append(
                "		and upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(account_manager_last_name.toUpperCase()) + "%' ");
            //.append("		or upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(account_manager_name.toUpperCase()) + "%' ")
            //.append("		or upper(vzwAccountManager.firstName) || ' ' || upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(account_manager_name.toUpperCase()) + "%') ");							
        }

        //allianceAdminContact.firstName

        if (alliance_admin_first_name.length() > 0)
        {
            expressionBuffer.append(
                "		and upper(allianceAdminContact.firstName) like '%" + StringFuncs.sqlEscape(alliance_admin_first_name.toUpperCase()) + "%' ");
            //.append("		or upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(account_manager_name.toUpperCase()) + "%' ")
            //.append("		or upper(vzwAccountManager.firstName) || ' ' || upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(account_manager_name.toUpperCase()) + "%') ");							
        }

        if (alliance_admin_last_name.length() > 0)
        {
            expressionBuffer.append(
                "		and upper(allianceAdminContact.lastName) like '%" + StringFuncs.sqlEscape(alliance_admin_last_name.toUpperCase()) + "%' ");
            //.append("		or upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(account_manager_name.toUpperCase()) + "%' ")
            //.append("		or upper(vzwAccountManager.firstName) || ' ' || upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(account_manager_name.toUpperCase()) + "%') ");							
        }

        if (contract_id.length() > 0 && (!contract_id.equals("0")))
        {
            expressionBuffer.append("		and allianceContract.contractId = " + contract_id);
        }

        if (date_established_from.length() > 0)
        {
            expressionBuffer.append("		and trunc(alliance.createdDate) >= to_date('" + date_established_from + "', 'MM/dd/yyyy')");
        }

        if (date_established_to.length() > 0)
        {
            expressionBuffer.append("		and trunc(alliance.createdDate) <= to_date('" + date_established_to + "', 'MM/dd/yyyy')");
        }

        log.debug("The filter expression is " + expressionBuffer.toString());

        return expressionBuffer.toString();
    }

    private String getSecondaryFilterExpression(String filter_field, String filter_text)
    {

        StringBuffer expressionBuffer = new StringBuffer("");

        if (filter_text.trim().length() > 0)
        {
            if (filter_field.equalsIgnoreCase("alliance_name"))
            {
                expressionBuffer.append("		and upper(alliance.companyName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }

            if (filter_field.equalsIgnoreCase("alliance_status"))
            {
                expressionBuffer.append(
                    "		and upper(alliance.status) in ("
                        + AimsUtils.getAllianceStatusString(StringFuncs.sqlEscape(filter_text.trim().toUpperCase()))
                        + ") ");
            }

            if (filter_field.equalsIgnoreCase("alliance_created_date"))
            {
                if (AimsUtils.isDate(filter_text.trim()))
                {
                    expressionBuffer.append("		and trunc(alliance.createdDate) = to_date('" + filter_text.trim() + "', 'MM/dd/yyyy')");
                }
                else
                {
                    expressionBuffer.append("		and alliance.createdDate = to_date('12/31/9999', 'MM/dd/yyyy')");
                }

            }

            if (filter_field.equalsIgnoreCase("alliance_contract"))
            {
                expressionBuffer.append("		and upper(contract.contractTitle)like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }

            if (filter_field.equalsIgnoreCase("vzw_account_manager"))
            {
                expressionBuffer.append(
                    "		and (upper(vzwAccountManager.firstName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%' ").append(
                    "		or upper(vzwAccountManager.lastName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%') ");
            }

        }

        log.debug("The secondary filter expression is " + expressionBuffer.toString());
        return expressionBuffer.toString();
    }

}
