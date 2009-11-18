package com.netpace.aims.controller.system;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceWhitePaperManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.alliance.AimsAllianceWhitePaper;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of getting files stored in white paper.
 *
 * @struts.action path="/whitePaperResource"
 *                scope="request"
 * @struts.action-forward name="update" path="/system/WPAdminUpdate.jsp"
 * @author Ahson Imtiaz
 */
public class WhitePaperResourceAction extends BaseAction
{

    static Logger log = Logger.getLogger(WhitePaperResourceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //TODO: Create a seperate Whitepaper_Resource file, to fetch data for WhitePaperListAction(/WPList.do) on Landing Page 

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_WHITE_PAPERS);

        HttpSession session = request.getSession();

        Object o_param;
        String whitePaperId;
        String forward = "update";

        response.setHeader("Cache-Control", "no-cache");
        OutputStream ou = response.getOutputStream();

        log.debug("In Getting white paper file");

        o_param = request.getParameter("whitePaperId");
        if (o_param != null)
            whitePaperId = request.getParameter("whitePaperId");
        else
            whitePaperId = "0";

        Long lngWPaperId = null;

        try
        {
            lngWPaperId = new Long(whitePaperId);
        }
        catch (Exception ex)
        {
            lngWPaperId = new Long("0");
        }

        if (lngWPaperId.intValue() != 0)
        {
            AimsAllianceWhitePaper aawp = AllianceWhitePaperManager.getAimsAllianceWhitePaper(lngWPaperId);
            InputStream in = null;
            in = aawp.getWhitePaperFile().getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + aawp.getWhitePaperFilename());
            response.setContentType(aawp.getWhitePaperFileType());
            response.setContentLength((int) aawp.getWhitePaperFile().length());

            if (in != null)
            {
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = in.read(buffer, 0, 8192)) != -1)
                    ou.write(buffer, 0, bytesRead);
                in.close();
            }

        }

        //exit
        ou.flush();
        ou.close();

        return mapping.findForward("update");

    }
}
