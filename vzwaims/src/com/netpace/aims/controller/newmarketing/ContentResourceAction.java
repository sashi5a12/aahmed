package com.netpace.aims.controller.newmarketing;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.newmarketing.ContentRequestManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.newmarketing.AimsCreativeContentFiles;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * @struts.action path="/newContentResource"
 *                scope="request"
 *                input="/newmarketing/contentRequestCreate.jsp"
 *                name="MktContentReqForm"
 *                validate="false"
 * @struts.action-forward name="list" path="/newMktContentRequest.do"
 * @author Ahson Imtiaz
 */
public class ContentResourceAction extends BaseAction
{

    static Logger log = Logger.getLogger(ContentResourceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        MktContentReqForm frm = (MktContentReqForm) form;
        String fileId = request.getParameter("app_id");
        log.debug(" ********  File Id: " + fileId);
        String app_resource = request.getParameter("app_res");
        log.debug("********* APP_RESOURCE " + app_resource);

        response.setHeader("Cache-Control", "no-cache");
        InputStream inputStream = null;
        OutputStream outputStream = response.getOutputStream();
        AimsCreativeContentFiles acc = null;

        acc = ContentRequestManager.getContentFile(fileId, Utility.convertToString(user.getUserId()));

        if (app_resource.equals("ScreenJpeg1"))
        {

            inputStream = acc.getScreenJpeg1().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + acc.getScreenJpeg1FileName());
            response.setContentType(acc.getScreenJpeg1ContentType());
            response.setContentLength((int) acc.getScreenJpeg1().length());

        }
        if (app_resource.equals("ScreenJpeg2"))
        {

            inputStream = acc.getScreenJpeg2().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + acc.getScreenJpeg2FileName());
            response.setContentType(acc.getScreenJpeg2ContentType());
            response.setContentLength((int) acc.getScreenJpeg2().length());

        }

        if (app_resource.equals("ScreenJpeg3"))
        {
            inputStream = acc.getScreenJpeg3().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + acc.getScreenJpeg3FileName());
            response.setContentType(acc.getScreenJpeg3ContentType());
            response.setContentLength((int) acc.getScreenJpeg3().length());

        }

        if (app_resource.equals("ScreenJpeg4"))
        {
            inputStream = acc.getScreenJpeg4().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + acc.getScreenJpeg4FileName());
            response.setContentType(acc.getScreenJpeg4ContentType());
            response.setContentLength((int) acc.getScreenJpeg4().length());

        }

        if (app_resource.equals("ScreenJpeg5"))
        {
            inputStream = acc.getScreenJpeg5().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + acc.getScreenJpeg5FileName());
            response.setContentType(acc.getScreenJpeg5ContentType());
            response.setContentLength((int) acc.getScreenJpeg5().length());

        }

        if (app_resource.equals("ClrPubLogo"))
        {

            inputStream = acc.getPublisherLogo().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + acc.getPublisherLogoFileName());
            response.setContentType(acc.getPublisherLogoContentType());
            response.setContentLength((int) acc.getPublisherLogo().length());
        }

        if (app_resource.equals("AppTitleName"))
        {

            inputStream = acc.getAppLogoBwSmall().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + acc.getAppLogoBwSmallFileName());
            response.setContentType(acc.getAppLogoBwSmallContentType());
            response.setContentLength((int) acc.getAppLogoBwSmall().length());

        }

        if (app_resource.equals("SplashScreenEps"))
        {

            inputStream = acc.getSplashScreen().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + acc.getSplashScreenFileName());
            response.setContentType(acc.getScreenJpeg1ContentType());
            response.setContentLength((int) acc.getSplashScreen().length());

        }

        if (app_resource.equals("ActiveScreenEps"))
        {

            inputStream = acc.getActiveScreen().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + acc.getActiveScreenFileName());
            response.setContentType(acc.getActiveScreenContentType());
            response.setContentLength((int) acc.getActiveScreen().length());

        }

        if (inputStream != null)
        {
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1)
                outputStream.write(buffer, 0, bytesRead);
            inputStream.close();
        }

        outputStream.flush();
        outputStream.close();

        //				return mapping.getInputForward();
        return mapping.findForward("list");
    }
}
