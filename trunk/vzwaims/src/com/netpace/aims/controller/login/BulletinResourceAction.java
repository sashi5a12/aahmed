package com.netpace.aims.controller.login;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.security.AimsBulletinManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;


/**
 * 	This action handles bulletin resources.
 *
 * @struts.action path="/bulletinResource"
 *                input="/"
 * @author Sajjad Raza
 */
public class BulletinResourceAction extends BaseAction
{
    static Logger log = Logger.getLogger(BulletinResourceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Object[] resourceValues = null;
        Long resource_bulletinId  = null;//bulletin id of resource
        String bulletinResourceIdStr = request.getParameter("bulletinResourceId");
        Long session_bulletinId = (Long) request.getSession().getAttribute(AimsConstants.SESSION_BULLETIN_TO_READ);

        if( !(StringFuncs.isNullOrEmpty(bulletinResourceIdStr)) )
        {
            response.setHeader("Cache-Control", "no-cache");
            try
            {
                outputStream = response.getOutputStream();
                resourceValues = AimsBulletinManager.getBulletinResource(new Long(bulletinResourceIdStr));

                if(resourceValues != null)
                {
                    resource_bulletinId = (Long)resourceValues[0];
                    if( resource_bulletinId!=null && (resource_bulletinId.longValue() == session_bulletinId.longValue()))
                    {
                        inputStream = ((Blob) resourceValues[1]).getBinaryStream();

                        response.reset();
                        response.setHeader("Content-Disposition", "attachment; filename=" + (String) resourceValues[2]);
                        response.setContentType((String) resourceValues[3]);
                        response.setContentLength((int) ((Blob) resourceValues[1]).length());

                        if (inputStream != null)
                        {
                            int bytesRead = 0;
                            byte[] buffer = new byte[8192];
                            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1)
                            {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                        }
                    }//end if resource_bulletinId ==  session_bulletinId
                    else
                    {
                        //show error that bulletin id of resource is not matched with current bulletin
                        log.debug("BulletinResourceAction: bulletin id of resource is not matched with current bulletin. bulletinResourceId: "+bulletinResourceIdStr+"\t bulletinId: "+resource_bulletinId);
                    }
                }//end if resourceValues != null
                else
                {
                    log.debug("BulletinResourceAction: bulletinResourceId: "+bulletinResourceIdStr+" not found");
                }
            }
            catch (Exception e)
            {
                System.out.println("BulletinResourceAction: exception occured in BulletinResourceAction");
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (inputStream != null)
                    {
                        inputStream.close();
                    }
                }
                catch (Exception exIs)
                {
                    System.out.println("BulletinResourceAction: Exception occured while closing input stream ");
                    exIs.printStackTrace();
                }

                try
                {
                    if (outputStream != null)
                    {
                        outputStream.flush();
                        outputStream.close();
                    }
                }
                catch (Exception exOp)
                {
                    System.out.println("BulletinResourceAction: Exception occured while closing output stream ");
                    exOp.printStackTrace();
                }
            }
        }

        return null;
    }
}

