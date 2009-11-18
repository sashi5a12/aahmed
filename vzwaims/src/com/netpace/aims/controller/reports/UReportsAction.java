package com.netpace.aims.controller.reports;

import org.apache.struts.action.*;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Set;
import java.util.Vector;
import java.util.Date;
import com.netpace.aims.controller.BaseAction;

import com.netpace.aims.model.core.*;
import com.netpace.aims.util.*;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/UReports"
 *                scope="request"
 *                input="/reports/UReports.jsp"
 * @struts.action-forward name="view" path="/reports/UReports.jsp"
 * @struts.action-exception key="error.login.InvalidUser" type="com.netpace.aims.bo.security.InvalidLoginException"
 * @author Fawad Sikandar
 * @see com.netpace.aims.bo.security.InvalidLoginException
 */


public class UReportsAction extends BaseAction
{

 static Logger log = Logger.getLogger(UReportsAction.class.getName());

 public static String PRIVILEGE = "UREPORTS";

 public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {


      //check if the user has the necessary rights
      checkAccess(request,PRIVILEGE);

      String taskname = request.getParameter("task");

      AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER); 
      request.setAttribute("user_name", user.getUsername());
      request.setAttribute("pass_word", user.getPassword());
      

      return mapping.findForward("view");
    }

}