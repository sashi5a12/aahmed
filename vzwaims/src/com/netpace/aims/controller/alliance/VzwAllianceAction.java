package com.netpace.aims.controller.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.VZWAllianceManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.ws.ServiceManager;

/**
 *
 *
 * @struts.action path="/vzwAlliance"                
 *                scope="request" 
 *				  name="VZWAllianceForm"
 *				  validate="true"
 *                input="/alliance/vzwAllianceViewDelete.jsp"  
 * @struts.action-forward name="view" path="/alliance/vzwAllianceViewDelete.jsp"
 * @struts.action-forward name="delete" path="/alliance/vzwAllianceViewDelete.jsp"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */
public class VzwAllianceAction extends BaseAction
{

    static Logger log = Logger.getLogger(VzwAllianceAction.class.getName());

    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES);

        if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.SELECT)))
        {
            throw new AimsSecurityException();
        }

        String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        Long user_id = user.getUserId();
        Collection AimsAlliance = null;
        Collection VZWAlliances = new ArrayList();
        VZWAllianceForm allianceForm = null;
        VZWAllianceForm vzwAllianceForm = (VZWAllianceForm) form;
        ActionErrors errors = new ActionErrors();

        int PAGE_LENGTH = user.getRowsLength().intValue();
        int pageNo = 1;

        try
        {
            pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);
        }
        catch (Exception e)
        {
            pageNo = 1;
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("all_type")).length() > 0)
        {
            vzwAllianceForm.setAllianceType(StringFuncs.initializeStringGetParam(request.getParameter("all_type"), "ALL"));
        }
        else
        {
            vzwAllianceForm.setAllianceType(StringFuncs.initializeStringGetParam(request.getParameter("all_type"), "ALL"));
        }

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

        Object[] userValues = null;

        String forward = "";

        log.debug("vzwAllianceForm.getFilterField() : " + vzwAllianceForm.getFilterField());
        log.debug("vzwAllianceForm.getFilterText() : " + vzwAllianceForm.getFilterText());

        if (task_name.equalsIgnoreCase("onhold"))
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

        if (task_name.equalsIgnoreCase("delete"))
        {
            try
            {
                if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.UPDATE))
                {
                	Long allianceId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
                	
                	AimsAllianc alliance = (AimsAllianc)DBHelper.getInstance().getSession().load(AimsAllianc.class, allianceId);
                	ServiceManager.deleteDeveloper( allianceId, alliance.getVendorId(), alliance.getCompanyName() );
                	
                    VZWAllianceManager.DeleteAlliance(allianceId, user_name);
                }
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.delete.success"));
                saveMessages(request, messages);
            }
            catch (AimsException ae)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));
                saveErrors(request, errors);
            }

        }

        int rows =
            VZWAllianceManager.getAllianceDetailsPageCount(
                StringFuncs.initializeStringGetParam(vzwAllianceForm.getSortField(), "1"),
                getFilterExpression(
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getFilterField(), ""),
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getFilterText(), "")),
                StringFuncs.initializeStringGetParam(vzwAllianceForm.getAllianceType(), "ALL"),
                user_id);
        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

        if (pageNo < 1)
            pageNo = 1;
        else if (pageNo > pmax)
            pageNo = pmax;

        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));

        AimsAlliance =
            VZWAllianceManager.getAllianceDetails(
                StringFuncs.initializeStringGetParam(vzwAllianceForm.getSortField(), "1"),
                getFilterExpression(
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getFilterField(), ""),
                    StringFuncs.initializeStringGetParam(vzwAllianceForm.getFilterText(), "")),
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
            allianceForm.setStatus(allianceForm.getAllianceStatus((String) userValues[3]));
            allianceForm.setVzwAccMgrFirstName((String) userValues[4]);
            allianceForm.setVzwAccMgrLastName((String) userValues[5]);
            allianceForm.setVzwAccMgrEmailAddress((String) userValues[6]);
            allianceForm.setVzwAccMgrPhone((String) userValues[7]);
            allianceForm.setAllianceAdminFirstName((String) userValues[8]);
            allianceForm.setAllianceAdminLastName((String) userValues[9]);
            allianceForm.setAllianceId((Long) userValues[10]);
            allianceForm.setIsOnHold(StringFuncs.initializeStringGetParam(((String) userValues[11]), "N"));
            allianceForm.setAllianceAdminEmailAddress((String) userValues[12]);

            VZWAlliances.add(allianceForm);

        }
        request.setAttribute("VZWAlliances", VZWAlliances);
        forward = "view";

        return mapping.findForward(forward);
    }

    private String getFilterExpression(String filter_field, String filter_text)
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

        return expressionBuffer.toString();
    }
}
