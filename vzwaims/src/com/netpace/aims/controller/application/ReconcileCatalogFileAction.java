package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsInvalidDataException;
import com.netpace.aims.bo.application.ReconcileCatalogManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsCatalog;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of submission form of Brew NSTL Listing.
 *
 * @struts.action path="/reconcileCatalogFile"
 *                name="ReconcileBrewForm"
 *                scope="request"
 *                input="/application/reconcileCatalogFileSelect.jsp"
 *				  		validate="true"
 * @struts.action-forward name="select" path="/application/reconcileCatalogSelect.jsp"
 * @struts.action-exception key="error.ReconcileBrewFileForm.dataParsingError" type="com.netpace.aims.bo.application.AimsInvalidDataException"
 * @struts.action-exception key="error.ReconcileCatalog.dateError" type="com.netpace.aims.bo.application.AimsFileDateException"
 * @author Ahson Imtiaz
 */
public class ReconcileCatalogFileAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileCatalogFileAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception, AimsInvalidDataException
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.RECONCILE_CATALOG_DATA);

        ReconcileBrewForm brewForm = (ReconcileBrewForm) form;
        HttpSession session = request.getSession();
        ReconcileCatalogManager.deleteTempData(session.getId());
        AimsCatalog catalog = ReconcileCatalogManager.getAimsCatalog(brewForm.getBrewNstlUploadId() /*Has catalog Id*/
        );
        request.setAttribute("Catalog", catalog);
        request.setAttribute("CatalogDataExt", ReconcileCatalogManager.matchCatalogData(catalog, session.getId()));
        //request.setAttribute("AllianceAppDeviceExts",ReconcileCatalogManager.getBrewAppsDevicesCombinations());
        return mapping.findForward("select");
    }
}
