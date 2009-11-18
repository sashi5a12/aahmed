package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsInvalidDataException;
import com.netpace.aims.bo.application.ReconcileCatalogManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of submission form of Brew NSTL Listing.
 *
 * @struts.action path="/reconcileCatalogFileSelect"
 *                name="ReconcileBrewForm"
 *                scope="request"
 *				  		validate="false"
 * @struts.action-forward name="select" path="/application/reconcileCatalogFileSelect.jsp"
  * @author Ahson Imtiaz
 */
public class ReconcileCatalogFileSelect extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileCatalogFileSelect.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception, AimsInvalidDataException
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.RECONCILE_CATALOG_DATA);

        try
        {
            request.setAttribute("DataFiles", ReconcileCatalogManager.getCatalogToReconcile());
            request.setAttribute("DataFilesReconciled", ReconcileCatalogManager.getCatalogReconciled());
        }
        catch (Exception e)
        {
            log.debug("unable to get combo for Catalog uploaded records");
        }

        return mapping.findForward("select");
    }
}
