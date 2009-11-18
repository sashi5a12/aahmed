package com.netpace.aims.controller.alliance;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceBusInfoManager;
import com.netpace.aims.bo.alliance.AllianceManagerHelper;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/resourceAction" 
 *                input="/alliance/allianceBusinessInfoUpdate.jsp"
 * @author Rizwan Qazi
 */
public class ResourceAction extends BaseAction
{

    static Logger log = Logger.getLogger(ResourceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskName = StringFuncs.NullValueReplacement(request.getParameter("task"));
        String resourceName = StringFuncs.NullValueReplacement(request.getParameter("resource"));
        String objectName = StringFuncs.NullValueReplacement(request.getParameter("object"));
        String tempFileId = StringFuncs.NullValueReplacement(request.getParameter("tempFileId"));

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);

        String user_type = user.getUserType();
        String user_name = user.getUsername();
        Long alliance_id = null;
        Object[] resourceValues = null;
        AimsTempFile aimsTempFile = null;

        if (user_type.equalsIgnoreCase("A"))
        {
            alliance_id = user.getAimsAllianc();
        }

        if (user_type.equalsIgnoreCase("V"))
        {
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
        }

        log.debug("alliance_id in RESOURCE ACTION " + alliance_id);

        response.setHeader("Cache-Control", "no-cache");

        InputStream inputStream = null;
        OutputStream outputStream = response.getOutputStream();

        if (resourceName.equals("tempFile"))
            aimsTempFile = TempFilesManager.getTempFile(new Long(tempFileId), user_name);
        else
            resourceValues = AllianceBusInfoManager.getAllianceResource(resourceName, alliance_id, user_type);

        if (aimsTempFile!=null)
        {
            resourceValues = new Object[3];
            resourceValues[0] = aimsTempFile.getTempFile();
            resourceValues[1] = aimsTempFile.getTempFileName();
            resourceValues[2] = aimsTempFile.getTempFileContentType();
        }
        
        if ((resourceValues[0] == null) || (((int) ((Blob) resourceValues[0]).length()) <= 0))
        {
            resourceValues = AllianceManagerHelper.getMiscImage("not-available.jpg", user_type);
        }

        inputStream = ((Blob) resourceValues[0]).getBinaryStream();
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + (String) resourceValues[1]);
        response.setContentType((String) resourceValues[2]);
        response.setContentLength((int) ((Blob) resourceValues[0]).length());

        if (inputStream != null)
        {
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            try
            {
                while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1)
                    outputStream.write(buffer, 0, bytesRead);
            }
            catch (IOException iox)
            {
                log.debug("Exception occured in ResourceAction while writing stream");
                System.out.println("Exception occured in ResourceAction while writing stream");
            }
            finally
            {
                inputStream.close();
                log.debug("Input stream closed.");
            }
        }

        try
        {
            outputStream.flush();
            outputStream.close();
            log.debug("Output stream closed.");
        }
        catch (IOException iox)
        {
            log.debug("Exception occured in ResourceAction while flushing stream");
            System.out.println("Exception occured in ResourceAction while flushing stream");
        }

        /*
        		return null;
        		}
        		if (inputStream != null)
        		{
        			int bytesRead = 0;
        			byte[] buffer = new byte[8192];
        			while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1)
        				outputStream.write (buffer, 0, bytesRead);
        			inputStream.close();
        		}
        				
        		outputStream.flush();
        		outputStream.close();		
        */
        return null;
    }
}