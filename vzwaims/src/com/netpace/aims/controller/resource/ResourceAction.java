package com.netpace.aims.controller.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.resource.ResourceManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.resource.AimsResource;

/**
 * @struts.action path="/aimsResource"
 *                scope="request"
 * @struts.action-forward name="page1" path="/application/brewApplicationUpdate.jsp"
 * @author Adnan Ahmed
 */

public class ResourceAction extends BaseAction{
	private static final Logger log = Logger.getLogger(ResourceAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        InputStream in = null;
        Object o_param;
        AimsResource aimsResource=null;
        String resourceId;

        o_param = request.getParameter("resourceId");
        if (o_param != null)
            resourceId = request.getParameter("resourceId");
        else
            resourceId = "0"; 

        log.debug("resourceId" + resourceId);
        
        if (StringUtils.isNumeric(resourceId)){
        	aimsResource=ResourceManager.getResource(new Long(resourceId));
        }

        if (aimsResource != null)
        {
            in = aimsResource.getResourceFile().getBinaryStream();
            response.setHeader("Cache-Control", "no-cache");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + aimsResource.getFileName());
            response.setContentType(aimsResource.getResourceContentType());
            response.setContentLength((int) aimsResource.getResourceFile().length());

            log.debug("File length" + (int) aimsResource.getResourceFile().length());
            log.debug("Content Type"+aimsResource.getResourceContentType());
            log.debug("File Name"+aimsResource.getFileName());
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
                log.debug("Exception occured in ResourceAction while writing stream");
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
            log.debug("Exception occured in ResourceAction while flushing stream");
            System.out.println("Exception occured in ResourceAction while flushing stream");
        }

        return null;		
	}
}
