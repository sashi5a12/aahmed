package com.netpace.aims.controller.contracts;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceContractInfoManager;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/amendmentsResourceAction" 
 *                input="/contracts/amendmentsInfoView.jsp"
 * @author Rizwan Qazi
 */
public class AmendmentsResourceAction extends BaseAction
{

    static Logger log = Logger.getLogger(AmendmentsResourceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {

        //Throw AimsSecurityException() if No Privileges
        //checkAccess(request, AimsPrivilegesConstants.AMENDMENTS);

        String resourceName = StringFuncs.NullValueReplacement(request.getParameter("resource"));
        String resourceId = StringFuncs.NullValueReplacement(request.getParameter("resourceId"));
        Long pkId = new Long("0");

        if (resourceId.length() > 0)
        {
            pkId = new Long(resourceId);
        }

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_type = user.getUserType();
        Long allianceId= user.getAimsAllianc();
        response.setHeader("Cache-Control", "no-cache");

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try
        {
        	if (allianceId != null && AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user_type)){
        		if (AllianceContractInfoManager.validateAmendment(allianceId, pkId)==false){
        			return null;
        		}
        	}        	
            outputStream = response.getOutputStream();

            Object[] resourceValues = ContractsManager.getResource(resourceName, pkId, user_type);

            inputStream = ((Blob) resourceValues[0]).getBinaryStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + (String) resourceValues[1]);
            response.setContentType((String) resourceValues[2]);
            response.setContentLength((int) ((Blob) resourceValues[0]).length());

            if (inputStream != null)
            {
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1)
                    outputStream.write(buffer, 0, bytesRead);
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception occured in AllianceResourceContractAction");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (inputStream != null)
                    inputStream.close();
            }
            catch (Exception ex2)
            {}

            try
            {
                if (outputStream != null)
                {
                    outputStream.flush();
                    outputStream.close();
                }
            }
            catch (Exception ex2)
            {}
        }

        return null;
    }
}