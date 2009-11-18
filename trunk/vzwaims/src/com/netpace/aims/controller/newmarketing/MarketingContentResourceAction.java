package com.netpace.aims.controller.newmarketing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.newmarketing.MarketingContentManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/marketingContentResource"
 *                scope="request"
 * @struts.action-forward name="update" path="/application/brewApplicationUpdate.jsp"
 * @author Adnan Makda
 */
public class MarketingContentResourceAction extends BaseAction
{

    static Logger log = Logger.getLogger(MarketingContentResourceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        //Privileges Being Checked Below

        HttpSession session = request.getSession();
        Object o_param;
        String content_id;
        String content_resource;
        String forward = "update";

        response.setHeader("Cache-Control", "no-cache");
        OutputStream ou = response.getOutputStream();

        log.debug("IN EXECUTE OF RESOURCE");

        o_param = request.getParameter("content_id");
        if (o_param != null)
            content_id = request.getParameter("content_id");
        else
            content_id = "0";

        o_param = request.getParameter("content_res");
        if (o_param != null)
            content_resource = request.getParameter("content_res");
        else
            content_resource = "0";

        log.debug("CONTENT_ID" + content_id);
        log.debug("CONTENT_RESOURCE" + content_resource);

        if (!(content_id.equals("0")) && !(content_resource.equals("0")))
        {

            InputStream in = null;
            AimsTempFile aimsTempFile = null;

            Long allianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
            String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
            String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

            String objInfo = null;
            boolean showTempFile = false;

            if (content_resource.equals("PublisherLogo"))
                objInfo = "publisherLogo";
            if (content_resource.equals("AppTitleGraphic"))
                objInfo = "appTitleGraphic";
            if (content_resource.equals("SplashScreen"))
                objInfo = "splashScreen";
            if (content_resource.equals("ActiveScreen"))
                objInfo = "activeScreen";
            if (content_resource.equals("ScreenJpeg1"))
                objInfo = "screenJpeg1";
            if (content_resource.equals("ScreenJpeg2"))
                objInfo = "screenJpeg2";
            if (content_resource.equals("ScreenJpeg3"))
                objInfo = "screenJpeg3";
            if (content_resource.equals("ScreenJpeg4"))
                objInfo = "screenJpeg4";
            if (content_resource.equals("ScreenJpeg5"))
                objInfo = "screenJpeg5";
            if (content_resource.equals("VideoFile"))
                objInfo = "videoFile";
            if (content_resource.equals("AppLogoBwSmall"))
                objInfo = "appLogoBwSmall";
            if (content_resource.equals("AppLogoBwLarge"))
                objInfo = "appLogoBwLarge";
            if (content_resource.equals("AppLogoColorSmall"))
                objInfo = "appLogoClrsmall";
            if (content_resource.equals("AppLogoColorLarge"))
                objInfo = "appLogoClrlarge";

            if (content_resource.equals("TempFile"))
                showTempFile = true;

            if ((MarketingContentHelper.checkAccess(request, "view", AimsPrivilegesConstants.CP_MARKETING_CONTENT)))
            {
                if (objInfo != null)
                    aimsTempFile = MarketingContentManager.getResourceFile(new Long(content_id), allianceId, objInfo);
                else if (showTempFile)
                    aimsTempFile = TempFilesManager.getTempFile(new Long(content_id), currUser);
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
                    try
                    {
                        while ((bytesRead = in.read(buffer, 0, 8192)) != -1)
                            ou.write(buffer, 0, bytesRead);
                    }
                    catch (IOException iox)
                    {
                        log.debug("Exception occured in MarketingContentResourceAction while writing stream");
                        System.out.println("Exception occured in MarketingContentResourceAction while writing stream");
                    }
                    finally
                    {
                        in.close();
                        log.debug("Input stream closed.");
                    }
                }
            }
        }

        //exit
        try
        {
            ou.flush();
            ou.close();
            log.debug("Output stream closed.");
        }
        catch (IOException iox)
        {
            log.debug("Exception occured in MarketingContentResourceAction while flushing stream");
            System.out.println("Exception occured in MarketingContentResourceAction while flushing stream");
        }

        return null;
    }
}
