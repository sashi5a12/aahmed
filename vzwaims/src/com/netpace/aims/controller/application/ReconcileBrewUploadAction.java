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
import com.netpace.aims.model.application.AimsBrewNstlUpload;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of submission form of Brew NSTL Listing.
 *
 * @struts.action path="/reconcileBrewFileUpload"
 *                name="ReconcileBrewFileForm"
 *                scope="request"
 *                input="/application/reconcileBrewFUpload.jsp"
 *				  		validate="true"
 * @struts.action-forward name="select" path="/application/reconcileBrewSelect.jsp"
 * @struts.action-exception key="error.ReconcileBrewFileForm.dataParsingError" type="com.netpace.aims.bo.application.AimsInvalidDataException"
 * @struts.action-exception key="error.ReconcileBrewFileForm.dateError" type="com.netpace.aims.bo.application.AimsFileDateException"
 * @author Ahson Imtiaz
 */
public class ReconcileBrewUploadAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileBrewUploadAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception, AimsInvalidDataException
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.UPLOAD_NSTL_DATA);

        ReconcileBrewFileForm brewForm = (ReconcileBrewFileForm) form;
        AimsBrewNstlUpload brewUpload = ReconcileBrewManager.saveBrewNstlData(brewForm.getBrewFile(), brewForm.getCreatedDate());
        request.setAttribute("BrewUpload", brewUpload);
        request.setAttribute("BrewNstlExt", ReconcileBrewManager.matchBrewNstlData(brewUpload));
        request.setAttribute("AllianceAppDeviceExts", ReconcileBrewManager.getBrewAppsDevicesCombinations());
        return mapping.findForward("select");
    }
}