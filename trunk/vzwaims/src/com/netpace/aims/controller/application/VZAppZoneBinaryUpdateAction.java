package com.netpace.aims.controller.application;

import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsVZAppBinaries;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 * @struts.action path="/vzAppZoneBinaryUpdate"
 *                name="VZAppZoneBinaryUpdateForm"
 *                scope="request"
 *			      validate="true"
 *                input="/application/vzAppZoneBinaryUpdate.jsp"
 * @struts.action-forward name="edit" path="/application/vzAppZoneBinaryUpdate.jsp"
 * @author Sajjad Raza
 */

public class VZAppZoneBinaryUpdateAction extends BaseAction {
    private static Logger log = Logger.getLogger(VZAppZoneBinaryUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.debug("======= VZAppZoneBinaryUpdateAction starts");
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        VZAppZoneBinaryUpdateForm binaryUpdateForm = (VZAppZoneBinaryUpdateForm)form;

        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        Long currentUserAllianceId = user.getAimsAllianc();
        String currUserType = user.getUserType();

        Long appsId = null;
        Long binaryId = null;
        AimsVZAppBinaries aimsBinary = null;

        boolean binaryInfoChanged = false;
        Long documentFileSizeInBytes = null;

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

        //CHECK Platform ACCESS
        if (!(ApplicationHelper.checkPlatformAccess(request, "edit", AimsConstants.VZAPPZONE_PLATFORM_ID))) {
            throw new AimsSecurityException();
        }

        appsId = new Long(StringFuncs.NulltoZeroStringReplacement(request.getParameter("appsId")));
        binaryId = new Long(StringFuncs.NulltoZeroStringReplacement(request.getParameter("binaryId")));

        ////// binary setup load values from database
        try {
            aimsBinary = VZAppZoneApplicationManager.getAimsVZAppBinaryByBinaryId(appsId,binaryId,currentUserAllianceId);
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw new AimsSecurityException();
        }

        if(aimsBinary==null) {
            throw new AimsSecurityException();
        }

        binaryUpdateForm.setAppsId(appsId);
        binaryUpdateForm.setBinaryId(binaryId);

        if (taskname.equalsIgnoreCase("edit")) {
            binaryUpdateForm.setBinaryFileFileName(aimsBinary.getBinaryFileFileName());
            binaryUpdateForm.setBinaryFileSize(aimsBinary.getBinaryFileSize());
            binaryUpdateForm.setBinaryVersion(aimsBinary.getBinaryVersion());
            binaryUpdateForm.setDocumentFileFileName(aimsBinary.getDocumentFileFileName());
            binaryUpdateForm.setDocumentFileTempFileId(new Long(0));
            binaryUpdateForm.setPreviewFileFileName(aimsBinary.getPreviewFileFileName());

        }

        if (taskname.equalsIgnoreCase("save")) {

            if(Utility.ZeroValueReplacement(binaryUpdateForm.getDocumentFileTempFileId()).longValue()>0) {
                binaryInfoChanged = true;

                documentFileSizeInBytes = TempFilesManager.getTempFileSize(binaryUpdateForm.getDocumentFileTempFileId(), currUser);
                if(documentFileSizeInBytes != null) {
                    log.debug("VZAppZoneBinaryUpdateAction: setting document file size of binary= "+documentFileSizeInBytes);
                    aimsBinary.setDocumentFileSizeInBytes(documentFileSizeInBytes);
                }

                log.debug("VZAppZoneBinaryUpdateAction: Binary Document File uploaded by user");
            }
            
            if(binaryInfoChanged) {
                aimsBinary.setLastUpdatedBy(currUser);
                aimsBinary.setLastUpdatedDate(new Date());
            }
            VZAppZoneApplicationManager.saveBinaryFields(currUser, aimsBinary, binaryUpdateForm.getDocumentFileTempFileId());
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.vzAppZone.manage.app.binary.saved"));
            saveMessages(request, messages);
        }

        log.debug("======= VZAppZoneBinaryUpdateAction ends \tforward: "+forward);

        return mapping.findForward(forward);
    }
}
