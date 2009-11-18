package com.netpace.aims.controller.system;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.netpace.aims.bo.system.*;
import com.netpace.aims.controller.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.util.*;

/**
 * This class takes care of action for getting attachment of email.
 *
 * @struts.action path="/emailAttResource"
 *                input="/common/logged-in.jsp"
 * @author Fawad Sikandar
 */
public class EmailAttResourceAction extends BaseAction
{

    static Logger log = Logger.getLogger(EmailAttResourceAction.class.getName());

	public ActionForward execute(ActionMapping mapping,
								ActionForm form,
								HttpServletRequest request,
								HttpServletResponse response) throws Exception
    {

		String resourceId = StringFuncs.NullValueReplacement(request.getParameter("systemNotificationId"));
		Long   msgAttId = new Long("0");

		if (resourceId.length() > 0)
		{
			msgAttId = new Long(resourceId);
		}

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);

		response.setHeader("Cache-Control", "no-cache");

		InputStream inputStream = null;


		Object [] resourceValues =  AimsSystemNotificationManager.getMessageAttachment(new Long(resourceId));

		inputStream = ((Blob)resourceValues[0]).getBinaryStream();
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=" + (String)resourceValues[1]);
		response.setContentType((String)resourceValues[2]);
		response.setContentLength((int)((Blob)resourceValues[0]).length());
                log.debug(" Attachment Size : " +  ((int)((Blob)resourceValues[0]).length()) );
		OutputStream outputStream = response.getOutputStream();


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
