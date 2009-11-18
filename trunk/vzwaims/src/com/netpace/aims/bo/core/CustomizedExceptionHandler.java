package com.netpace.aims.bo.core;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.MiscUtils;

/**
 * This	Exception	class	throws error
 * @author Adnan Makda
 * @version	1.0
 */
public class CustomizedExceptionHandler extends ExceptionHandler
{
    static Logger log = Logger.getLogger(CustomizedExceptionHandler.class.getName());

    public ActionForward execute(
        Exception ex,
        ExceptionConfig exConfig,
        ActionMapping mapping,
        ActionForm formInstance,
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException
    {

        // if	there's	already	errors in	the	request, don't process
        ActionForward forward = super.execute(ex, exConfig, mapping, formInstance, request, response);

        StringBuffer errTrace = new StringBuffer();

        HttpSession session = request.getSession();
        if (session != null)
        {
            errTrace.append("\nUSER INFORMATION:\n");
            AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
            if (user != null)
            {
                errTrace.append("User ID: " + user.getUserId() + "\n");
                errTrace.append("UserName: " + user.getUsername() + "\n");
            }
            else
            {
                return null;
            }
        }

        errTrace.append("\n\n\nERROR INFORMATION:\n");
        errTrace.append("Error Occured On: " + new Date() + "\n");
        errTrace.append("Error Message: " + ex.getMessage() + "\n\n");
        errTrace.append("Stack Trace:\n");
        errTrace.append(MiscUtils.getExceptionStackTraceInfo(ex));

        //REQUEST INFORMATION        
        errTrace.append(MiscUtils.getRequestInfo(request));

        //SESSION INFORMATION        
        errTrace.append(MiscUtils.getSessionInfo(session));

        //Composing of Email
        StringBuffer emailSubject = new StringBuffer("Exception on .... ");
        if (request.getServerName() != null)
            emailSubject.append(request.getServerName());

        try
        {
            MailUtils.sendMail(AimsConstants.EMAIL_EXCEPTION_ADMIN, "exceptions@netpace.com", emailSubject.toString(), null, errTrace.toString());
        }
        catch (SendFailedException sfEx)
        {
            System.out.println("SendFailedException in CustomizedExceptionHandler: ");
            sfEx.printStackTrace();
        }
        catch (MessagingException mEx)
        {
            System.out.println("MessagingException in CustomizedExceptionHandler: ");
            mEx.printStackTrace();
        }
        catch (Exception mailEx)
        {
            System.out.println("Exception in CustomizedExceptionHandler: ");
            mailEx.printStackTrace();
        }

        return forward;
    }

}