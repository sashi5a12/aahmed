package com.netpace.aims.ui;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;

import com.netpace.aims.model.reports.*;
import com.netpace.aims.util.StringFuncs;

/**
 * This class is responsible for displaying the Performance Report details.
 * @author Rizwan Qazi
 */

public class GetPerformanceReportTag extends BodyTagSupport {

    protected String attributeName;

    public void setAttributeName(String attributeName){
      this.attributeName = attributeName;
    }

    public String getAttributeName(){
      return this.attributeName;
    }

    public int doStartTag() throws JspException
    {


		HttpServletRequest  request  = (HttpServletRequest)  pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		AimsEmployee vzwEmployee = null;
		AimsEmpAlliance vzwEmpAlliance = null;
		
		AimsPerfReport perfReportBean = (AimsPerfReport) request.getAttribute("perfReportBean");
		
		int selVZWEmployeeId = StringFuncs.initializeIntGetParam(request.getParameter("user_id"), 0);
		String format_type = StringFuncs.NullValueReplacement(request.getParameter("format_type"));

		JspWriter out = pageContext.getOut();
		StringBuffer outBuffer = new StringBuffer();

		
		try {


				if (attributeName.equalsIgnoreCase("employee_name")) 
				{
					outBuffer.append(perfReportBean.getEmployeeName());
				}

				if (attributeName.equalsIgnoreCase("department_name")) 
				{
					outBuffer.append(StringFuncs.NullValueReplacement(perfReportBean.getDepartmentName()));
				}

				if (attributeName.equalsIgnoreCase("report_date")) 
				{
					outBuffer.append(perfReportBean.getReportDate());
				}

				if (attributeName.equalsIgnoreCase("vzw_emp_alliances")) 
				{
					Collection verizonEmpAlliances = perfReportBean.getVerizonEmpAlliances();

					String alliance_name = "";
					String alliance_date = "";
					String alliance_status = "";
					String alliance_cnt_submitted_apps = "";
					String alliance_cnt_live_apps = "";
					String platform_name = "";
					String app_name = "";
					String app_created_date = "";
					String app_status = "";

					String curr_alliance_name = "NOVALUE";
					String curr_alliance_date = "NOVALUE";
					String curr_alliance_status = "NOVALUE";
					String curr_alliance_cnt_submitted_apps = "NOVALUE";
					String curr_alliance_cnt_live_apps = "NOVALUE";
					String curr_platform_name = "NOVALUE";
					String curr_app_name = "NOVALUE";
					String curr_app_created_date = "NOVALUE";
					String curr_app_status = "NOVALUE";


					int cnt = 0;
					for (Iterator iter = verizonEmpAlliances.iterator(); iter.hasNext();) 
					{
						    cnt ++;
							if (cnt == 1)
							{
								outBuffer.append("<tr>")
										 .append("	<td colspan=2 class=\"text\" align=\"center\">")
										 .append("		<table width=\"100%\" class=\"tabletop\" cellspacing=\"0\" cellpadding=\"3\">") 
										 .append("			<tr bgcolor=\"#BBBBBB\">")
										 .append("				<td class=\"text\" align=\"left\"><strong>Alliance Name</strong></td>")
										 .append("				<td class=\"text\" height=\"26\" align=\"left\"><strong>Alliance Date Created</strong></td>")
										 .append("				<td class=\"text\" align=\"left\"><strong>Alliance Status</strong></td>")
										 .append("				<td class=\"text\" height=\"26\" align=\"left\"><strong># of Applications Submitted</strong></td>")
										 .append("				<td class=\"text\" height=\"26\" align=\"left\"><strong># of Live Applications</strong></td>")
										 .append("				<td class=\"text\" height=\"26\" align=\"left\"><strong>Platform</strong></td>")
										 .append("				<td class=\"text\" height=\"26\" align=\"left\"><strong>Application Name</strong></td>")
										 .append("				<td class=\"text\" height=\"26\" align=\"left\"><strong>Application Status</strong></td>")
										 .append("				<td class=\"text\" height=\"26\" align=\"left\"><strong>Application Status</strong></td>")
										 .append("			</tr>");
							}
							vzwEmpAlliance = (AimsEmpAlliance) iter.next();		
							
										alliance_name = vzwEmpAlliance.getAllianceName();
										alliance_date = vzwEmpAlliance.getAllianceCreatedDate();
										alliance_status = vzwEmpAlliance.getAllianceStatus();
										alliance_cnt_submitted_apps = vzwEmpAlliance.getCntSubmittedApps();
										alliance_cnt_live_apps = vzwEmpAlliance.getCntLiveApps();
										platform_name = vzwEmpAlliance.getPlatformName();
										app_name = vzwEmpAlliance.getAppName();
										app_created_date = vzwEmpAlliance.getAppCreatedDate();
										app_status = vzwEmpAlliance.getAppStatus();

						
								outBuffer.append("			<tr bgcolor=\"#EBEBEB\">");
							
							if (!(alliance_name.equalsIgnoreCase(curr_alliance_name)))
							{	
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(alliance_name).append("</td>");
							
								outBuffer.append("				<td class=\"text\" align=\"left\">").append(alliance_date).append("</td>");
	
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(alliance_status).append("</td>");
		
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"center\">").append(alliance_cnt_submitted_apps).append("</td>");
	
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"center\">").append(alliance_cnt_live_apps).append("</td>");

								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(platform_name).append("</td>");
	
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(app_name).append("</td>");
							
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(app_created_date).append("</td>");
							
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(app_status).append("</td>");
							
								outBuffer.append("			</tr>");
							}
							else 
							{
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">&nbsp;</td>");
							
								outBuffer.append("				<td class=\"text\" align=\"left\">&nbsp;</td>");
	
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">&nbsp;</td>");
		
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"center\">&nbsp;</td>");

								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"center\">&nbsp;</td>");

								
								if (!(platform_name.equalsIgnoreCase(curr_platform_name)))
								{	
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(platform_name).append("</td>");
								}
								else
								{
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">&nbsp;</td>");
								}
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(app_name).append("</td>");
							
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(app_created_date).append("</td>");
							
								outBuffer.append("				<td class=\"text\" height=\"26\" align=\"left\">").append(app_status).append("</td>");
							
								outBuffer.append("			</tr>");

							}

										curr_alliance_name = alliance_name;
										curr_alliance_date = alliance_date;
										curr_alliance_status = alliance_status;
										curr_alliance_cnt_submitted_apps = alliance_cnt_submitted_apps;
										curr_alliance_cnt_live_apps = alliance_cnt_live_apps;
										curr_platform_name = platform_name;
										curr_app_name = app_name;
										curr_app_created_date = app_created_date;
										curr_app_status = app_status;

					}


								outBuffer.append("		</table>")
										 .append("	</td>")
										 .append("</tr>");

							if ( (cnt > 1) && (!format_type.equalsIgnoreCase("excel")) )
							{								
								outBuffer.append("<tr>")
										 .append("	<td colspan=\"2\" class=\"text\" align=\"right\"><strong><a target=\"new\" href=\"/aims/perfReport.do?user_id=").append(selVZWEmployeeId).append("&format_type=excel\">Export to Excel</a></strong></td>")
										 .append("</tr>");
							}


				}

				if (attributeName.equalsIgnoreCase("vzw_employees")) 
				{
					Collection verizonEmployees = perfReportBean.getVerizonEmployees();
					
					for (Iterator iter = verizonEmployees.iterator(); iter.hasNext();) 
					{
							vzwEmployee = (AimsEmployee) iter.next();						

							outBuffer.append("<option value=\"")
								   .append(vzwEmployee.getEmployeeId())
								   .append("\"")
								   .append(StringFuncs.ifSelected(selVZWEmployeeId, vzwEmployee.getEmployeeId()))
								   .append(">")
								   .append(vzwEmployee.getEmployeeName())
								   .append("</option>")
								   .append("\n");
					}
				}

				out.println(outBuffer.toString());

			}

		 catch (IOException ioExc)
			{
				ioExc.printStackTrace(System.out);
				throw new JspException(ioExc.toString());
			}
/*
		  catch (Exception Exc)
			{
				Exc.printStackTrace(System.out);
				throw new JspException(Exc.toString());
			}
*/
			return SKIP_BODY;
    }


	public int doEndTag()
	{
		return EVAL_PAGE;
	}


	public void release()
	{		
		attributeName = null;
		super.release();
	}

}