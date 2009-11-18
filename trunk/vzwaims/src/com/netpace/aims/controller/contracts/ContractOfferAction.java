package com.netpace.aims.controller.contracts;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.contracts.ContractOfferManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/contractOffer"                
 *                scope="request" 
 *				  name="ContractOfferForm"
 *				  validate="false"
 *                input="/contracts/contractsOfferListView.jsp"  
 * @struts.action-forward name="view" path="/contracts/contractsOfferListView.jsp" 
 * @author Rizwan Qazi
 */
public class ContractOfferAction extends BaseAction
{

    static Logger log = Logger.getLogger(ContractOfferAction.class.getName());

    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.OFFER_CONTRACTS);

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));

        Object[] userValues = null;
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        Long alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));

        Collection AimsAlliance = AllianceManager.getAllianceDetails(alliance_id, user_type);

        for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
        {
            userValues = (Object[]) iter.next();
        }

        String alliance_status = StringFuncs.NullValueReplacement((String) userValues[3]);

        if (!(alliance_status.equals("A")))
        {
            throw new AimsSecurityException();
        }

        ContractOfferForm contractForm = (ContractOfferForm) form;
        contractForm.setAllianceId(alliance_id.toString());
        Collection AvailableAllianceContracts = null;

        String forward = "";

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("view"))
        {

            AvailableAllianceContracts = ContractOfferManager.getAllianceAvailableContracts(alliance_id, AimsConstants.CONTRACT_STATUS_ACTIVE);
            request.setAttribute("AvailableAllianceContracts", AvailableAllianceContracts);
            forward = "view";
        }

        return mapping.findForward(forward);
    }
}
