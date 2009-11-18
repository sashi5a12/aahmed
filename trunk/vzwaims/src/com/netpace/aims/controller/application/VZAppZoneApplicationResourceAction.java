package com.netpace.aims.controller.application;

import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 	This action handles VZAppZone Application Resources like devices binaries and preview files.
 *
 * @struts.action path="/vzAppZoneApplicationResource"
 *                scope="request"
 * @author Sajjad Raza
 */
public class VZAppZoneApplicationResourceAction extends BaseAction {
    static Logger log = Logger.getLogger(VZAppZoneApplicationResourceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String app_id;
        String app_resource;
        HttpSession session = request.getSession();

        response.setHeader("Cache-Control", "no-cache");

        log.debug("IN EXECUTE OF VZAppZoneApplicationResourceAction");

        app_id = StringFuncs.NulltoZeroStringReplacement(request.getParameter("app_id"));
        app_resource = StringFuncs.NullValueReplacement(request.getParameter("app_res"));

        log.debug("APP_ID= " + app_id);
        log.debug("APP_RESOURCE= " + app_resource);

        if (!StringFuncs.isNullOrEmpty(app_resource) )
        {
            AimsTempFile aimsTempFile = null;

            String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
            String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
            Long allianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();

            InputStream in = null;
            OutputStream ou = null;

            try
            {
                ou = response.getOutputStream();
                if (app_resource.equals("VZAppBinaryFile")) {
                    Long binaryId = new Long(StringFuncs.NullValueReplacement(request.getParameter("vzAppBinaryId")));
                    aimsTempFile =  VZAppZoneApplicationManager.getVZAppBinaryFileFromBinaries(new Long(app_id), allianceId, binaryId, "binaryFile");
                }
                else if (app_resource.equals("VZAppBinaryPreviewFile")) {
                    Long binaryId = new Long(StringFuncs.NullValueReplacement(request.getParameter("vzAppBinaryId")));
                    aimsTempFile =  VZAppZoneApplicationManager.getVZAppBinaryFileFromBinaries(new Long(app_id), allianceId, binaryId, "previewFile");
                }
                else if (app_resource.equals("VZAppBinaryDocumentFile")) {
                    Long binaryId = new Long(StringFuncs.NullValueReplacement(request.getParameter("vzAppBinaryId")));
                    aimsTempFile =  VZAppZoneApplicationManager.getVZAppBinaryFileFromBinaries(new Long(app_id), allianceId, binaryId, "documentFile");
                }
                else if(app_resource.equals("VZAppBaseResultFile")) {
                    Long binaryFirmwareId = new Long(StringFuncs.NullValueReplacement(request.getParameter("vzAppBinaryFirmwareId")));
                    aimsTempFile =  VZAppZoneApplicationManager.getVZAppBaseTestFile(new Long(app_id), allianceId, binaryFirmwareId, "baseResultsFile");

                }
                else if(app_resource.equals("VZAppOTAResultFile")) {
                    Long binaryFirmwareId = new Long(StringFuncs.NullValueReplacement(request.getParameter("vzAppBinaryFirmwareId")));
                    aimsTempFile =  VZAppZoneApplicationManager.getVZAppBaseTestFile(new Long(app_id), allianceId, binaryFirmwareId, "otaResultsFile");            
                }
                else if (app_resource.equals("TempFile")) {
                    Long tempFileId = new Long(StringFuncs.NullValueReplacement(request.getParameter("tempFileId")));
                    aimsTempFile = TempFilesManager.getTempFile(tempFileId, currUser);
                }

                if ((aimsTempFile == null) || (aimsTempFile.getTempFile() == null) || (aimsTempFile.getTempFileName() == null))
                    aimsTempFile = ApplicationsManagerHelper.getMiscImage(AimsConstants.MISC_IMAGE_NOT_AVAILABLE, currUserType);

                if (aimsTempFile != null)
                {
                    in = aimsTempFile.getTempFile().getBinaryStream();
                    response.reset();
                    response.setHeader("Content-Disposition", "attachment; filename=" + aimsTempFile.getTempFileName());
                    response.setContentType(aimsTempFile.getTempFileContentType());
                    response.setContentLength((int) aimsTempFile.getTempFile().length());
                    if (in != null)
                    {
                        int bytesRead = 0;
                        byte[] buffer = new byte[8192];
                        while ((bytesRead = in.read(buffer, 0, 8192)) != -1)
                            ou.write(buffer, 0, bytesRead);
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Exception occured in VZAppZoneApplicationResourceAction");
                e.printStackTrace();
            }
            finally {
                try
                {
                    if (in != null) {
                        in.close();
                        log.debug("InputStream closed in VZAppZoneApplicationResourceAction");
                    }
                }
                catch (Exception exIs) {
                    System.out.println("VZAppZoneApplicationResourceAction: Exception occured while closing input stream ");
                    exIs.printStackTrace();
                }

                try
                {
                    if (ou != null) {
                        ou.flush();
                        ou.close();
                        log.debug("OutputStream closed in VZAppZoneApplicationResourceAction");
                    }
                }
                catch (Exception exOp) {
                    System.out.println("VZAppZoneApplicationResourceAction: Exception occured while closing output stream ");
                    exOp.printStackTrace();
                }
            }//end finally
        }
        return null;
    }//end executes
}
