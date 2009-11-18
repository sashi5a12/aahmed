package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.ReconcileCatalogManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsCatalog;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of submission form of Catalog Listing.
 *
 * @struts.action path="/reconcileCatalog"
 *                name="ReconcileBrewForm"
 *                scope="request"
 *						input="/application/reconcileCatalogConfirmed.jsp"
 *				  		validate="false"
 * @struts.action-forward name="confirmed" path="/application/reconcileCatalogConfirmed.jsp"
 * @struts.action-forward name="refresh" path="/application/reconcileCatalogSelect.jsp"
 * @author Ahson Imtiaz
 */
public class ReconcileCatalogSelectAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileCatalogSelectAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.RECONCILE_CATALOG_DATA);

        ReconcileBrewForm brewForm = (ReconcileBrewForm) form;
        String task = request.getParameter("task");
        HttpSession session = request.getSession();
        if (task == null)
        {
            task = "view";
        }

        if (task.equalsIgnoreCase("updateSelection"))
        {
            log.debug(" Combine Id is :" + brewForm.getChoseCombineId());
            ReconcileCatalogManager.saveUserSelection(brewForm.getChoseCombineId(), session.getId());
            AimsCatalog catalog = ReconcileCatalogManager.getAimsCatalog(brewForm.getBrewNstlUploadId() /*Has catalog Id*/
            );
            request.setAttribute("Catalog", catalog);
            request.setAttribute("CatalogDataExt", ReconcileCatalogManager.matchCatalogData(catalog, session.getId()));
            return mapping.findForward("refresh");
        }
        else if (task.equalsIgnoreCase("update"))
        {
            Integer[] aSelIndex = brewForm.getSelectedCombo();
            String[] aEnteries = brewForm.getReconcileCombineId();

            ReconcileCatalogManager.associateEntries(aSelIndex, aEnteries, brewForm.getBrewNstlUploadId() /* It has the catalog Id*/
            );
        }

        request.setAttribute("DataFiles", ReconcileCatalogManager.getCatalogReconciled());
        request.setAttribute("CatalogDataExt", ReconcileCatalogManager.getAssociatedCatalogData(brewForm.getBrewNstlUploadId() /* it has the catalog Id*/
        ));

        return mapping.findForward("confirmed");
    }
}
