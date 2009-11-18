package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of submission form of Brew NSTL Listing.
 *
 * @struts.action path="/BrewNSTLUpload"
 *                name="ReconcileBrewFileForm"
 *                scope="request"
 *                input="/application/reconcileBrewFUpload.jsp"
 *				  		validate="false"
 * @struts.action-forward name="upload" path="/application/reconcileBrewFUpload.jsp"
 * @author Ahson Imtiaz
 */
public class ReconcileBrewSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileBrewSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.UPLOAD_NSTL_DATA);

        //log.debug("whitePaperSetup.do Forwarding: " + forward);
        return mapping.findForward("upload");
    }
}
