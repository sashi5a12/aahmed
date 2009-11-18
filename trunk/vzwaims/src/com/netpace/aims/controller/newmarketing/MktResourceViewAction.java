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
import com.netpace.aims.model.newmarketing.AimsCreativeCreqFil;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * @struts.action path="/newMktResView"
 *                scope="request"
 *                input="/newmarketing/contentRequestCreate.jsp"
 *                name="MktContentReqForm"
 *                validate="false"
 * @struts.action-forward name="list" path="/newMktContentRequest.do"
 * @author Ahson Imtiaz
 */
public class MktResourceViewAction extends BaseAction
{

    static Logger log = Logger.getLogger(MktResourceViewAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        MktContentReqForm frm = (MktContentReqForm) form;

        if (frm.getTask() != null)
        {
            if (frm.getTask().equals("view") && frm.getFileId() != null && frm.getFileId().intValue() != 0)
            {

                response.setHeader("Cache-Control", "no-cache");
                InputStream inputStream = null;
                OutputStream outputStream = response.getOutputStream();

                AimsCreativeCreqFil acrf = ContentRequestManager.getContentRequestFile(frm.getFileId(), frm.getFileType());

                inputStream = acrf.getFileObject().getBinaryStream();

                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=" + acrf.getFileName());
                response.setContentType(acrf.getFileType());
                response.setContentLength((int) acrf.getFileObject().length());

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

                return mapping.getInputForward();
            }
        }
        return mapping.findForward("list");
    }
}
