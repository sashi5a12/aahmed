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
 * @struts.action path="/reconcileSaveCatalog"
 *                name="ReconcileBrewFileForm"
 *                scope="request"
 *                input="/application/reconcileCatalogUpload.jsp"
 *				  		validate="true"
 * @struts.action-forward name="select" path="/application/reconcileCatalogSelect.jsp"
 * @struts.action-exception key="error.ReconcileBrewFileForm.dataParsingError" type="com.netpace.aims.bo.application.AimsInvalidDataException"
 * @struts.action-exception key="error.ReconcileCatalog.dateError" type="com.netpace.aims.bo.application.AimsFileDateException"
 * @author Ahson Imtiaz
 */
public class ReconcileCatalogUploadAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileCatalogUploadAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception, AimsInvalidDataException
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.UPLOAD_CATALOG_DATA);

        ReconcileBrewFileForm brewForm = (ReconcileBrewFileForm) form;
        HttpSession session = request.getSession();
        // as same data using reconciliation brew class
        AimsCatalog catalog = ReconcileCatalogManager.saveCatalogData(brewForm.getBrewFile(), brewForm.getCreatedDate(), brewForm.getComments());
        request.setAttribute("Catalog", catalog);
        request.setAttribute("CatalogDataExt", ReconcileCatalogManager.matchCatalogData(catalog, session.getId()));
        //request.setAttribute("AllianceAppDeviceExts",ReconcileCatalogManager.getBrewAppsDevicesCombinations());
        return mapping.findForward("select");
    }
}
