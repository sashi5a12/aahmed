package com.netpace.aims.controller.application;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.netpace.aims.bo.application.AimsBrewPdf;
import com.netpace.aims.bo.application.BrewApplicationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/**
 * @struts.action path="/brewPdf"
 *                scope="request"
 * @struts.action-forward name="error" path="/application/brewPdfError.jsp" 
 */
public class BrewPdfAction extends BaseAction
{

    static Logger log = Logger.getLogger(BrewPdfAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {

        HttpSession session = request.getSession();
        MessageResources appResource = this.getResources(request);
        int pdfSize = Integer.parseInt(appResource.getMessage("file.brewPdf.sizelimit"));
        Object o_param;
        String app_id;
        String forward=null;
        
        o_param = request.getParameter("app_id");
        if (o_param != null)
            app_id = request.getParameter("app_id");
        else
            app_id = "0";
        
        log.debug("APP_ID" + app_id);

        if (!(app_id.equals("0")))
        {            

            Long allianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();            
            String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
            
            OutputStream ou = null;
            ByteArrayOutputStream baos = null;
            try
            {                
                baos = new ByteArrayOutputStream();
                HashMap  appMap=BrewApplicationManager.getUserGuideData(new Long(app_id), allianceId);
                if (!appMap.isEmpty()){
                	AimsBrewPdf.getneratePdf(appMap, baos);
            		ou = response.getOutputStream();
                	response.reset();
                	response.setHeader("Content-Disposition", "attachment; filename= User_Guide.pdf");
                    response.setHeader("Cache-Control", "no-cache");
                    response.setHeader("Expires", "0");
                	response.setHeader("Pragma", "public");
                	response.setContentType("application/pdf");
                	response.setContentLength(baos.size());
                	baos.writeTo(ou);
                }
            }
            catch (Exception e)
            {
                System.out.println("Exception occured in ApplicationResourceAction");
                e.printStackTrace();
            }
            finally
            {
                try{
                    if (baos != null){
                    	baos.close();
                    }
                }
                catch (Exception e){}
                
                try{
                    if (ou != null){
                        ou.flush();
                        ou.close();
                    }
                }
                catch (Exception e){}
                
            }
        }
        
        if (forward == null){
        	return null;
        }
        else {
        	return mapping.findForward(forward);
        }
        
    }
}
