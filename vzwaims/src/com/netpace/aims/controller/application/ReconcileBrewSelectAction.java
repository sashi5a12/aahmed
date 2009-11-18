package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.ReconcileBrewManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of submission form of Brew NSTL Listing.
 *
 * @struts.action path="/reconcileBrew"
 *                name="ReconcileBrewForm"
 *                scope="request"
 *						input="/application/reconcileBrewConfirmed.jsp"
 *				  		validate="false"
 * @struts.action-forward name="confirmed" path="/application/reconcileBrewConfirmed.jsp"
 * @author Ahson Imtiaz
 */
public class ReconcileBrewSelectAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileBrewSelectAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.RECONCILE_NSTL_DATA);

        ReconcileBrewForm brewForm = (ReconcileBrewForm) form;
        String task = request.getParameter("task");

        if (task == null)
        {
            task = "view";
        }

        if (task.equalsIgnoreCase("update"))
        {
            Integer[] aSelIndex = brewForm.getSelectedCombo();
            String[] aEnteries = brewForm.getReconcileCombineId();

            ReconcileBrewManager.associateEntries(aSelIndex, aEnteries, brewForm.getBrewNstlUploadId());
        }

        request.setAttribute("DataFiles", ReconcileBrewManager.getBrewNstlUploadReconciled());
        request.setAttribute("BrewNstlExt", ReconcileBrewManager.getAssociatedBrewNstlData(brewForm.getBrewNstlUploadId()));

        return mapping.findForward("confirmed");
    }
}
