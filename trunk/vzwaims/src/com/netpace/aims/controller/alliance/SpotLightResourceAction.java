package com.netpace.aims.controller.alliance;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.*;

import java.io.*;
import java.util.*;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.controller.*;

import com.netpace.aims.bo.core.*;
import com.netpace.aims.bo.alliance.*;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.alliance.*;

import com.netpace.aims.util.*;

import net.sf.hibernate.*;

import java.sql.Blob;
import java.sql.SQLException;

import oracle.sql.*;

/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/spotLightResourceAction" 
 *                input="/alliance/allianceCaseStudiesView.jsp"
 * @author Rizwan Qazi
 */
public class SpotLightResourceAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(SpotLightResourceAction.class.getName());
  
	public ActionForward execute(ActionMapping mapping,
								ActionForm form,
								HttpServletRequest request,
								HttpServletResponse response) throws Exception 
    {

        String taskName =  StringFuncs.NullValueReplacement(request.getParameter("task")); 
		String resourceName = StringFuncs.NullValueReplacement(request.getParameter("resource"));
		String objectName = StringFuncs.NullValueReplacement(request.getParameter("object"));
		String resourceId = StringFuncs.NullValueReplacement(request.getParameter("resourceId"));
		Long   pkId = new Long("0");

		if (resourceId.length() > 0)
		{
			pkId = new Long(resourceId);
		}


		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);		
		Long alliance_id = user.getAimsAllianc();
        String user_type = user.getUserType();
        String user_name = user.getUsername();
		
		response.setHeader("Cache-Control", "no-cache");

		InputStream inputStream = null;
		OutputStream outputStream = response.getOutputStream();

		Object [] resourceValues =  AllianceSpotlightsManager.getSpotLightResource(resourceName, pkId, user_type);

		inputStream = ((Blob)resourceValues[0]).getBinaryStream();
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=" + (String)resourceValues[1]);
		response.setContentType((String)resourceValues[2]);
		response.setContentLength((int)((Blob)resourceValues[0]).length());    
        		
				
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

		return mapping.getInputForward();
    }
}