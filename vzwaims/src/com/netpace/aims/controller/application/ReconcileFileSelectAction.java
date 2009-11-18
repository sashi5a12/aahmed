package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsInvalidDataException;
import com.netpace.aims.bo.application.ReconcileBrewManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of submission form of Brew NSTL Listing.
 *
 * @struts.action path="/reconcileBrewFileSelect"
 *                name="ReconcileBrewForm"
 *                scope="request"
 *				  		validate="false"
 * @struts.action-forward name="select" path="/application/reconcileBrewFileSelect.jsp"
  * @author Ahson Imtiaz
 */
public class ReconcileFileSelectAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileFileSelectAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception, AimsInvalidDataException
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.RECONCILE_NSTL_DATA);

        try
        {
            request.setAttribute("DataFiles", ReconcileBrewManager.getBrewNstlUploadToReconcile());
            request.setAttribute("DataFilesReconciled", ReconcileBrewManager.getBrewNstlUploadReconciled());
        }
        catch (Exception e)
        {
            log.debug("unable to get combo for Brew / Nstl uploaded records");
        }

        return mapping.findForward("select");
    }
}
