package com.netpace.aims.controller.tools;

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

import com.netpace.aims.bo.tools.AimsToolsManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.tools.AimsTool;
import com.netpace.aims.model.tools.AimsToolFiles;
import com.netpace.aims.util.AimsConstants;

/**
 * This class takes care of action for display of tools document.
 *
 * @struts.action path="/toolsResource"
 *                scope="request"
 * @struts.action-forward name="edit" path="/content/toolsUpdate.jsp"
 * @author Fawad Sikandar
 */
public class ToolsResourceAction extends BaseAction
{

    static Logger log = Logger.getLogger(ToolsResourceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        
        //Commented out on 03/30/2006 as Alliance Users were not able to see the 'Important Info' docs. 'TOOLS' is a Verizon Only privilege.
        //...Addtionally the method 'AimsToolsManager.getAimsTool(new Long(toolId), allianceId)', does the necessary security check.
        //checkAccess(request, AimsPrivilegesConstants.TOOLS);

        HttpSession session = request.getSession();
        Long allianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        InputStream in = null;
        Object o_param;
        AimsToolFiles toolFiles;
        String toolId;
        String toolType;
        String toolResource;

        o_param = request.getParameter("toolId");
        if (o_param != null)
            toolId = request.getParameter("toolId");
        else
            toolId = "0"; //to change

        o_param = request.getParameter("toolResource");
        if (o_param != null)
            toolResource = request.getParameter("toolResource");
        else
            toolResource = "0"; //to change

        log.debug("toolId" + toolId);
        log.debug("toolResource" + toolResource);

        if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            toolFiles = AimsToolsManager.getAimsToolFiles(new Long(toolId));
        else
            toolFiles = AimsToolsManager.getAimsTool(new Long(toolId), allianceId);

        if (toolResource.equals("toolFile"))
        {
            in = toolFiles.getToolFile().getBinaryStream();
            response.setHeader("Cache-Control", "no-cache");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + toolFiles.getFileName());
            response.setContentType(toolFiles.getToolFileContentType());
            response.setContentLength((int) toolFiles.getToolFile().length());

            log.debug("int" + (int) toolFiles.getToolFile().length());

            log.debug(toolFiles.getToolFileContentType());
            log.debug(toolFiles.getFileName());
        }

        OutputStream ou = response.getOutputStream();

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
                log.debug("Exception occured in ToolsResourceAction while writing stream");
                System.out.println("Exception occured in ToolsResourceAction while writing stream");
            }
            finally
            {
                in.close();
                log.debug("Input stream closed.");
            }
        }

        try
        {
            ou.flush();
            ou.close();
            log.debug("Output stream closed.");
        }
        catch (IOException iox)
        {
            log.debug("Exception occured in ToolsResourceAction while flushing stream");
            System.out.println("Exception occured in ToolsResourceAction while flushing stream");
        }

        return null;

    }
}
