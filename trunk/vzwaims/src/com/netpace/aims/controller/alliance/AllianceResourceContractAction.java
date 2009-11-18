package com.netpace.aims.controller.alliance;

import com.netpace.aims.bo.alliance.AllianceContractInfoManager;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Clob;

/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/resourceContractAction" 
 *                input="/alliance/allianceContractsInfoUpdate.jsp"
 * @author Rizwan Qazi
 */
public class AllianceResourceContractAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceResourceContractAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {

        String resourceName = StringFuncs.NullValueReplacement(request.getParameter("resource"));
        String resourceId = StringFuncs.NullValueReplacement(request.getParameter("resourceId"));
        String tempFileId = StringFuncs.NullValueReplacement(request.getParameter("tempFileId"));

        Long pkId = new Long("0");

        if (resourceId.length() > 0)
        {
            pkId = new Long(resourceId);
        }

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        Long allianceId= user.getAimsAllianc();
        String user_type = user.getUserType();

        response.setHeader("Cache-Control", "no-cache");

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try
        {
        	if (allianceId != null && AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user_type)){
                AimsContract aimsContract = ContractsManager.getAimsContract(pkId);
                if(aimsContract!=null) {
                    if(!StringFuncs.NullValueReplacement(aimsContract.getIsClickThroughContract()).equalsIgnoreCase("Y")) {
                        //if contract is not click through, then validate it from alliance contract
                        if (!AllianceContractInfoManager.validateContract(allianceId, pkId)) {
                            return null;
                        }
                    }
                }
                else {
                    //if aims contract not found
                    return null;
                }
            }

            outputStream = response.getOutputStream();
            Object[] resourceValues = null;

            if (resourceName.equals("tempFile"))
            {
                AimsTempFile aimsTempFile = TempFilesManager.getTempFile(new Long(tempFileId), user_name);
                if (aimsTempFile!=null)
                {
                    resourceValues = new Object[3];
                    resourceValues[0] = aimsTempFile.getTempFile();
                    resourceValues[1] = aimsTempFile.getTempFileName();
                    resourceValues[2] = aimsTempFile.getTempFileContentType();
                }
            }
            else
            {
                resourceValues = AllianceContractInfoManager.getAllianceResource(resourceName, pkId, user_type);
            }

            if(resourceValues!=null)
            {
                inputStream = ((Blob) resourceValues[0]).getBinaryStream();
                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=" + (String) resourceValues[1]);
                response.setContentType((String) resourceValues[2]);
                response.setContentLength((int) ((Blob) resourceValues[0]).length());
            }

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
            {ex2.printStackTrace();}

            try
            {
                if (outputStream != null)
                {
                    outputStream.flush();
                    outputStream.close();
                }
            }
            catch (Exception ex3)
            {ex3.printStackTrace();}
        }
        return null;
    }
}