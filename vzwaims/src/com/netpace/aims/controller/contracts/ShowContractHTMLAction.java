package com.netpace.aims.controller.contracts;

import com.netpace.aims.bo.alliance.AllianceContractInfoManager;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class takes care of action to show contract html.
 *
 * @struts.action path="/showContractHTMLAction"
 *                scope="request"
 * @struts.action-forward name="showContractHTML" path="/contracts/showContractHTML.jsp"
 * @author Sajjad Raza
 */
public class ShowContractHTMLAction extends BaseAction {

    static Logger log = Logger.getLogger(ShowContractHTMLAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String forward = "showContractHTML";

        HttpSession session = request.getSession();

        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        String currUserType = user.getUserType();

        Long contractId = new Long(StringFuncs.initializeIntGetParam(request.getParameter("contractId"), 0));

        String contractHTML = "";

        boolean showHTML = false;

        //only verizon users can view contract html
        if (currUserType.equalsIgnoreCase(AimsConstants.VZW_USERTYPE)) {
            //if current user is vzw
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTRACTS, AimsSecurityManager.SELECT))) {
                throw new AimsSecurityException();
            }
            //if user is allowed to view contract html, set showHTML flag to true
            showHTML = true;
        }
        else {
            throw new AimsSecurityException();
        }

        if(showHTML) {
            log.debug("user is allowed to view contract html, getting html for contract: "+contractId);
            contractHTML = AllianceContractInfoManager.getAllianceContractHTML(contractId, currUserType);
            request.setAttribute("contractHTML", contractHTML);
            request.setAttribute("aimsContract", ContractsManager.getAimsContract(contractId));
        }

        return mapping.findForward(forward);
    }
}