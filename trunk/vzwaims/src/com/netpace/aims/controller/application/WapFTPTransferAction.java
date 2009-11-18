package com.netpace.aims.controller.application;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsWapApp;
import com.netpace.aims.util.*;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 *
 * @struts.action path="/wapFTPTransfer"
 *                scope="request"
 * @struts.action-forward name="wapFTPTransferResponse" path="/application/wapFTPTransferResponse.jsp"
 * @author Sajjad Raza
 */
public class WapFTPTransferAction extends BaseAction {
    private static Logger log = Logger.getLogger(WapFTPTransferAction.class.getName());


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                throws Exception {

        String forward = "wapFTPTransferResponse";
        String appsID_param = request.getParameter("appsId");
        HttpSession session = request.getSession();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();

        Long appsID = null;
        HashMap appWap = null;
        AimsApp aimsApp = null;
        AimsWapApp aimsWapApp = null;

        //paths
        //ftp images
        String confZipDir = ConfigEnvProperties.getInstance().getProperty("wap.images.local.temp.dir");
        String localTempZipDir = getServlet().getServletContext().getRealPath(confZipDir);

        boolean processed = false;

        if((AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_PENDING_DCR, AimsSecurityManager.UPDATE))
                && (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_FTP_IMAGE_MANUAL, AimsSecurityManager.UPDATE))) {
            if(!StringFuncs.isNullOrEmpty(appsID_param)) {
                try {
                    appsID = new Long(appsID_param);
                    //get aims application
                    appWap = WapApplicationManager.getWapApp(new Long(appsID_param), null);
                    aimsApp = (AimsApp) appWap.get("AimsApp");
                    aimsWapApp = (AimsWapApp) appWap.get("AimsWapApp");

                    //if application phase id is not in (SubmittedDcr || TestingPassed  || PublicationReady || CompletedInProduction)
                    //then stop further processing and generate error and return
                    if(!this.checkAppStatusToAllowFTP(aimsApp.getAimsLifecyclePhaseId())) {
                        ActionErrors errors = new ActionErrors();
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.ftp.notallowed"));
                        saveErrors(request, errors);
                        return mapping.findForward(forward);
                    }

                    processed = WapApplicationHelper.processWapFTPTransfer(appsID, currUser, aimsApp, aimsWapApp);
                    if(processed) {
                        ActionMessages messages = new ActionMessages();
                        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.wap.app.images.ftp.success"));
                        saveMessages(request, messages);
                    }
                }
                catch(AimsException ae) {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                }
                catch(HibernateException he) {

                }
            }//end if appid null
        }
        else {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.ftp.transfer.access"));
            saveErrors(request, errors);
        }

        return mapping.findForward(forward);
    }

    private boolean checkAppStatusToAllowFTP(Long aimsLifecyclePhaseId) {
        boolean allowedFTP = false;
        boolean statusSubmittedDcr = false;
        boolean statusTestingPassed = false;
        boolean statusPublicationReady = false;
        boolean statusCompletedInProduction = false;

        statusSubmittedDcr = aimsLifecyclePhaseId.equals(AimsConstants.PHASE_SUBMITTED_DCR_ID);
        statusTestingPassed = aimsLifecyclePhaseId.equals(AimsConstants.PHASE_TESTING_PASSED_ID);
        statusPublicationReady = aimsLifecyclePhaseId.equals(AimsConstants.PHASE_PUBLICATION_READY_ID);
        statusCompletedInProduction = aimsLifecyclePhaseId.equals(AimsConstants.PHASE_COMPLETED_IN_PRODUCTION_ID);

        //if current phaseid is one of these, then allow ftp images
        allowedFTP = (statusSubmittedDcr || statusTestingPassed  || statusPublicationReady || statusCompletedInProduction);

        return allowedFTP;
    }

}
