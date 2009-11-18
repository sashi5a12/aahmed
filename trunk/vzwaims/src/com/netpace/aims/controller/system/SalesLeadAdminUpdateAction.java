package com.netpace.aims.controller.system;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.AllianceSalesLeadManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.alliance.SalesLeadForm;
import com.netpace.aims.model.alliance.AimsAllianceSalesLead;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for display of update form of Tools.
 *
 * @struts.action path="/SLAdminUpdate"
 *                name="SalesLeadForm"
 *                scope="request"
 *                input="/system/SLAdminUpdate.jsp"
 *				  validate="true"
 * @struts.action-forward name="update" path="/system/SLAdminUpdate.jsp"
 * @struts.action-forward name="listview" path="/SLAdminSetup.do"
 * @author Ahson Imtiaz
 */
public class SalesLeadAdminUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(SalesLeadAdminUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_SALES_LEADS);

        HttpSession session = request.getSession();
        SalesLeadForm salesForm = (SalesLeadForm) form;
        AimsUser oAimsUser = (AimsUser) (session.getAttribute(AimsConstants.AIMS_USER));
        String currUser = oAimsUser.getUsername();

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
            log.debug("Sales Lead Id is : " + salesForm.getSalesLeadId().toString());
            AimsAllianceSalesLead aasl = AllianceSalesLeadManager.getAimsAllianceSalesLead(salesForm.getSalesLeadId());
            if (aasl != null)
            {
                log.debug("Sales Lead Modified.");

                aasl.setCompanySolution(salesForm.getCompanySolution());

                aasl.setEstimatedSize(StringFuncs.returnLongOrNull(salesForm.getEstimatedSize()));

                aasl.setLeadQualification(salesForm.getLeadQualification());

                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                java.util.Date date = null;
                try
                {
                    date = df.parse(salesForm.getPurchaseDecision());

                }
                catch (Exception ex)
                {
                    // not reachable codition if validate is true
                }
                aasl.setPurchaseDecisionDate(date); //
                aasl.setSalesLeadDesc(salesForm.getSalesLeadDesc());
                aasl.setStatus(salesForm.getStatus());
                aasl.setAssignedTo(null);
                aasl.setComments(salesForm.getComments());

                aasl.setLastUpdatedBy(currUser);
                aasl.setLastUpdatedDate(new Date());

                AllianceSalesLeadManager.saveOrUpdate(aasl);

                ActionMessages amsgs = new ActionMessages();
                amsgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("salesLeadForm.record.updated"));
                saveMessages(request, amsgs);

                log.debug("Record modified for Sales Lead. ");
            }
        }

        request.setAttribute("AimsSalesLeads", AllianceSalesLeadManager.getAimsAllianceSalesLeadList());
        forward = "listview";
        return mapping.findForward(forward);
    }
}
