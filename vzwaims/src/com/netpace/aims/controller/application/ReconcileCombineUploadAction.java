package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.application.AimsFileDateException;
import com.netpace.aims.bo.application.AimsInvalidDataException;
import com.netpace.aims.bo.application.ReconcileBrewManager;
import com.netpace.aims.bo.application.ReconcileCatalogManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsBrewNstlUpload;
import com.netpace.aims.model.application.AimsCatalog;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of submission form of Brew NSTL Listing.
 *
 * @struts.action path="/reconcileCombineUpload"
 *                name="ReconcileBrewFileForm"
 *                scope="request"
 *                input="/application/reconcileCombineFUpload.jsp"
 *				  		validate="true"
 * @struts.action-forward name="done" path="/application/reconcileCombineUploaded.jsp"
 * @struts.action-exception key="error.ReconcileBrewFileForm.dataParsingError" type="com.netpace.aims.bo.application.AimsInvalidDataException"
 * @struts.action-exception key="error.ReconcileCatalog.dateError" type="com.netpace.aims.bo.application.AimsFileDateException"
 * @author Ahson Imtiaz
 */
public class ReconcileCombineUploadAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileCombineUploadAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception, AimsInvalidDataException
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.UPLOAD_NSTL_DATA);

        AimsBrewNstlUpload brewUpload = null;
        AimsCatalog catalog = null;
        ActionMessages amsgs = new ActionMessages();

        ReconcileBrewFileForm brewForm = (ReconcileBrewFileForm) form;

        try
        {
            try
            {
                brewUpload = ReconcileBrewManager.saveBrewNstlData(brewForm.getBrewFile(), brewForm.getCreatedDate());
                request.setAttribute("BrewUpload", brewUpload);
            }
            catch (AimsFileDateException e)
            {
                // Setting message to let user know that file already uploaded for date entered
                amsgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("reconcileCombine.brewFileAlreadyCompiled"));
                saveMessages(request, amsgs);
            }

            try
            {
                catalog = ReconcileCatalogManager.saveCatalogData(brewForm.getBrewFile(), brewForm.getCreatedDate(), brewForm.getComments());
                request.setAttribute("Catalog", catalog);

            }
            catch (AimsFileDateException e)
            {
                // Setting message to let user know that file already uploaded for date entered
                amsgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("reconcileCombine.catalogFileAlreadyCompiled"));
                saveMessages(request, amsgs);
            }
        }
        catch (Exception ex)
        {
            if (brewUpload != null)
            {
                ReconcileBrewManager.deleteBrewNstl(brewUpload.getBrewNstlId());
                request.setAttribute("BrewUpload", null);
            }
            if (catalog != null)
            {
                ReconcileCatalogManager.deleteCatalog(catalog.getCatalogId());
                request.setAttribute("Catalog", null);
            }
            throw ex;
        }
        return mapping.findForward("done");
    }
}
