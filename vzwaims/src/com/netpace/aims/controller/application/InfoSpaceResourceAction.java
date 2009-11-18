package com.netpace.aims.controller.application;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.webservices.InfospaceUtils;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.MiscUtils;

/**
 * This class is used to get resources for InfoSpace.
 *
 * @struts.action path="/infoSpaceResource"
 *                scope="request"
 * @struts.action-forward name="update" path="/application/brewApplicationUpdate.jsp"
 * @author Adnan Makda
 */
public class InfoSpaceResourceAction extends BaseAction
{

    static Logger log = Logger.getLogger(InfoSpaceResourceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {

        Object o_param;
        String app_id;
        String app_resource;
        String other_info;

        response.setHeader("Cache-Control", "no-cache");

        o_param = request.getParameter("app_id");
        if (o_param != null)
            app_id = request.getParameter("app_id");
        else
            app_id = "0";

        o_param = request.getParameter("app_res");
        if (o_param != null)
            app_resource = request.getParameter("app_res");
        else
            app_resource = "0";

        o_param = request.getParameter("other_info");
        if (o_param != null)
            other_info = request.getParameter("other_info");
        else
            other_info = "0";

        if (!(app_id.equals("0")) && !(app_resource.equals("0")) && !(other_info.equals("0")))
        {
            //Authenticating URL
            String initialInfo =
                MiscUtils.getBase64Digest(
                    InfospaceUtils.utf8decode(AimsConstants.INFOSPACE_KEY_DIGEST_FOR_URLS),
                    InfospaceUtils.utf8decode(app_id),
                    InfospaceUtils.utf8decode(app_resource));

            if (!other_info.equals(initialInfo))
            {
                StringBuffer emailSubject = new StringBuffer("Hacking Tried in InfoSpaceResourceAction, on .... ");
                if (request.getServerName() != null)
                    emailSubject.append(request.getServerName());

                try
                {
                    MailUtils.sendMail(
                        AimsConstants.EMAIL_EXCEPTION_ADMIN,
                        "exceptions@netpace.com",
                        emailSubject.toString(),
                        null,
                        MiscUtils.getRequestInfo(request));
                }
                catch (Exception ex0)
                {}
            }
            else
            {
                InputStream in = null;
                OutputStream ou = null;

                try
                {
                    ou = response.getOutputStream();
                    AimsTempFile aimsTempFile = null;

                    String[] objInfo = null;

                    if (app_resource.equals("WapProductLogo"))
                        objInfo = ManageApplicationsConstants.WAP_PRODUCT_LOGO_BLOB_OBJ_INFO;
                    if (app_resource.equals("WapProductIcon"))
                        objInfo = ManageApplicationsConstants.WAP_PRODUCT_ICON_BLOB_OBJ_INFO;
                    if (app_resource.equals("WapVendorLogo"))
                        objInfo = ManageApplicationsConstants.WAP_VENDOR_LOGO_BLOB_OBJ_INFO;

                    if (objInfo != null)
                        aimsTempFile = ApplicationsManagerHelper.getFile(new Long(app_id), null, objInfo);

                    if ((aimsTempFile == null) || (aimsTempFile.getTempFile() == null) || (aimsTempFile.getTempFileName() == null))
                        aimsTempFile = ApplicationsManagerHelper.getMiscImage(AimsConstants.MISC_IMAGE_NOT_AVAILABLE, null);

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
                catch (Exception e)
                {
                    System.out.println("Exception occured in InfoSpaceResourceAction");
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        if (in != null)
                            in.close();
                    }
                    catch (Exception ex2)
                    {}

                    try
                    {
                        if (ou != null)
                        {
                            ou.flush();
                            ou.close();
                        }
                    }
                    catch (Exception ex2)
                    {}
                }
            }
        }

        return null;
    }
}
