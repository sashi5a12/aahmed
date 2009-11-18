package com.netpace.aims.controller.reports;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import org.apache.log4j.Logger;

import oracle.jdbc.driver.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.*;

import java.io.*;
import java.sql.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.netpace.aims.util.AimsConstants;

import com.netpace.aims.model.reports.AimsPerfReport;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.bo.reports.AimsPerfReportManager;
import com.netpace.aims.util.StringFuncs;

import com.netpace.aims.controller.BaseAction;

/**
 * This class takes care of getting the data for the Staff Performance Report.
 *
 * @struts.action path="/perfReport"                
 *                scope="request" 
 *                input="/reports/report.jsp"               
 * @struts.action-forward name="view" path="/reports/staffPerfReport.jsp"
 * @struts.action-forward name="excel" path="/reports/staffPerfReportExcel.jsp"
 * @author Rizwan Qazi
 */

public class PerfReportAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(PerfReportAction.class.getName());
  
    /**
     * This method gets the data.
     * It calls the Performance Report manager with the userid and gets a 
     * Report BO.
     * 
	 *
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

        int user_id =  StringFuncs.initializeIntGetParam(request.getParameter("user_id"), 0); 

		String format_type = StringFuncs.NullValueReplacement(request.getParameter("format_type"));
		String forward = "view";
		
		if (format_type.equals("excel"))
		{
			forward = "excel";
		}

		else
		{
			//int curr_user_id = ((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserId();
			forward = "view";       
		}
			AimsPerfReport perfReportBean = AimsPerfReportManager.getReport(user_id); 
			log.debug("user_id : " + user_id);
			request.setAttribute("perfReportBean", perfReportBean);		
				

		return mapping.findForward(forward);
    }
}
