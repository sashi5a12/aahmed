package com.netpace.aims.controller.alliance;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Session;
//import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.util.*;


/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/provAppResource" 
 *                input="/alliance/allianceBusinessInfoUpdate.jsp"
 * @author Rizwan Qazi
 */

public class ProvAppResourceAction extends BaseAction 
{

	public ActionForward execute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
			
		Session session = DBHelper.getInstance().getSession();

		Connection ConOra = session.connection();
	
		InputStream inputStream = null;
		
		//File reportFile = new File(getServlet().getServletContext().getRealPath("/images/tenth.jasper"));
		
		File reportFile = new File(getServlet().getServletContext().getRealPath("/report/prov_apps.jasper"));
		
		

		//InputStream is = this.getClass().getClassLoader().getResourceAsStream("tenth.jasper");
		//byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), new java.util.HashMap(), new JREmptyDataSource());
		Integer appsId = new Integer(StringFuncs.initializeStringGetParam(request.getParameter("appsId"), "0"));
		Map parameters = new HashMap();
		parameters.put("APPSID", appsId);
		parameters.put("BaseDir", reportFile.getParentFile().getParentFile());
		
		byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, ConOra);
		


		OutputStream outputStream = response.getOutputStream();	
		if (bytes != null && bytes.length > 0)
		{
            //response.reset();
			response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=prov_app.pdf");		
			response.setContentLength(bytes.length);	
			for (int i = 0; i < bytes.length; i++ )
			{
				outputStream.write(bytes[i]);				
			}
			//outputStream.write(bytes, 0, bytes.length);
			
		
		}
		
	//response.reset();
	//response.setHeader("Content-Disposition", "attachment; filename=" + "not-available.pdf");
 
	
	
	//response.setContentType("application/pdf");
	//response.setContentLength(bytes.length);
	
	
	//outputStream.write(bytes, 0, bytes.length);
	
	
		
	try
	{
		outputStream.flush();
		outputStream.close();
	}
	catch(IOException iox)
	{
	
			System.out.println("Exception occured in ResourceAction while flushing stream");	
	}
	

	return null;
	}	
}