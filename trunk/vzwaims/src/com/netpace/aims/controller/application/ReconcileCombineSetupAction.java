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
 * This class takes care of action for display file upload form for BREW/NSTL and Catalog data file.
 *
 * @struts.action path="/reconcileCombineSetup"
 *                name="ReconcileBrewFileForm"
 *                scope="request"
 *                input="/application/reconcileCatalogUpload.jsp"
 *				  		validate="false"
 * @struts.action-forward name="upload" path="/application/reconcileCombineFUpload.jsp"
 * @author Ahson Imtiaz
 */
public class ReconcileCombineSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileCombineSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.UPLOAD_NSTL_DATA);

        return mapping.findForward("upload");
    }
}
