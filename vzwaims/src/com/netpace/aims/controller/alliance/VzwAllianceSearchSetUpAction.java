package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 *
 *
 * @struts.action path="/vzwAllianceSearchSetUp"                
 *                scope="request" 
 *				  name="VZWAllianceForm"
 *				  validate="false"
 *                input="/alliance/vzwAllianceSearch.jsp"  
 * @struts.action-forward name="searchView" path="/alliance/vzwAllianceSearch.jsp"
 * @struts.action-exception key="masters.integrity.constraint.violation" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */
public class VzwAllianceSearchSetUpAction extends BaseAction
{

    static Logger log = Logger.getLogger(VzwAllianceSearchSetUpAction.class.getName());

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
        HttpSession session = request.getSession();

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String dateFormat = this.getResources(request).getMessage("date.format");
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        VZWAllianceForm searchForm = (VZWAllianceForm) form;
        String forward = "";

        if (task_name.equalsIgnoreCase("searchView"))
        {
            forward = "searchView";
            searchForm.setAllianceType("ALL");
        }

        return mapping.findForward(forward);
    }

}
