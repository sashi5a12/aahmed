package com.netpace.aims.controller.application;

import com.netpace.aims.bo.application.ApplicationInformation;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsWapApp;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 *
 * @struts.action path="/wapAppImageUpload"
 *                name="WapAppImageUploadForm"
 *                scope="request"
 *			      validate="true"
 *                input="/application/wapAppUploadImages.jsp"
 * @struts.action-forward name="edit" path="/application/wapAppUploadImages.jsp"
 * @author Sajjad Raza
 */
public class WapAppImageUploadAction extends BaseAction {
    private static Logger log = Logger.getLogger(WapAppImageUploadAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        WapAppImageUploadForm imgUploadForm = (WapAppImageUploadForm)form;

        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        Long currentUserAllianceId = user.getAimsAllianc();
        String currUserType = user.getUserType();

        HashMap appWap = null;
        AimsApp aimsApp = null;
        AimsWapApp aimsWapApp = null;
        Long appsID = null;

        String forward = "edit";
        String taskname = "";
        Object o_param;

        o_param = request.getParameter("task");

        if (o_param != null) {
            taskname = request.getParameter("task");
        }
        else {
            taskname = "edit";
        }

        ////// application setup load values from database
        try {
            appWap = WapApplicationManager.getWapApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
        }
        catch (AimsException ae) {
            request.setAttribute("applicationAccessDenied", new Boolean(true));
            saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            return mapping.findForward(forward);
        }

        aimsApp = (AimsApp) appWap.get("AimsApp");
        aimsWapApp = (AimsWapApp) appWap.get("AimsWapApp");
        appsID = aimsApp.getAppsId();
        imgUploadForm.setAppsId(appsID);

        if( !WapApplicationHelper.isManualImageUploadAllowed(aimsWapApp.getWapFtpFlag()) ) {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.upload.images.denied"));
            saveErrors(request, errors);
            return mapping.findForward(forward);
        }

        if (taskname.equalsIgnoreCase("edit")) {

            //set default temp ids to 0
            imgUploadForm.setAppMediumLargeImageTempFileId(new Long(0));
            imgUploadForm.setAppQVGAPotraitImageTempFileId(new Long(0));
            imgUploadForm.setAppQVGALandscapeImageTempFileId(new Long(0));

            imgUploadForm.setAppMediumLargeImageFileName(aimsWapApp.getAppMediumLargeImageFileName());
            imgUploadForm.setAppQVGAPotraitImageFileName(aimsWapApp.getAppQVGAPotraitImageFileName());
            imgUploadForm.setAppQVGALandscapeImageFileName(aimsWapApp.getAppQVGALandscapeImageFileName());
        }

        if (taskname.equalsIgnoreCase("save")) {

            WapApplicationManager.saveWapLogoImages(aimsApp, currUser,
                                                    imgUploadForm.getAppMediumLargeImageTempFileId(),
                                                    imgUploadForm.getAppQVGAPotraitImageTempFileId(),
                                                    imgUploadForm.getAppQVGALandscapeImageTempFileId());

            //Set Temp File Ids to Zero
            imgUploadForm.setAppMediumLargeImageTempFileId(new Long(0));//set app image medium
            imgUploadForm.setAppQVGAPotraitImageTempFileId(new Long(0));//set app image potrait
            imgUploadForm.setAppQVGALandscapeImageTempFileId(new Long(0));//set app image landscape

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.manage.app.image.saved"));
            saveMessages(request, messages);
            return mapping.findForward(forward);
        }
        return mapping.findForward(forward);
    }
}
