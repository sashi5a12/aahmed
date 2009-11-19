<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@ page import="com.businessobjects.samples.CRJavaHelper,
com.crystaldecisions.report.web.viewer.CrystalReportViewer,
com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
com.crystaldecisions.sdk.occa.report.application.ReportClientDocument,
com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
com.netpace.ldap.bean.LdapUser,java.util.ArrayList,java.util.List" %>
<%@page import="com.crystaldecisions.report.web.viewer.CrDocumentViewTypeEnum"%>


<%

	try {

		String reportName = "reports/ldapusersreport/ldapusersreport.rpt";
		session.removeAttribute(reportName);
		session.removeAttribute(reportName+"ReportSource");

		ReportClientDocument clientDoc = (ReportClientDocument) session.getAttribute(reportName);
		if (clientDoc == null) {
			// Report can be opened from the relative location specified in the CRConfig.xml, or the report location
			// tag can be removed to open the reports as Java resources or using an absolute path
			// (absolute path not recommended for Web applications).

			clientDoc = new ReportClientDocument();
			clientDoc.setReportAppServer(ReportClientDocument.inprocConnectionString);
			
			// Open report
			clientDoc.open(reportName, OpenReportOptions.openAsReadOnly.value());
		
			// ****** BEGIN SET RUNTIME DATABASE CREDENTIALS ****************  
			{
				// This option is not applicable for the report you have chosen
			}
			// ****** END SET RUNTIME DATABASE CREDENTIALS **************** 		
		
			
			// ****** BEGIN POPULATE WITH POJO SNIPPET ****************  
			{
				// **** POPULATE MAIN REPORT ****
				{

					 // Populate POJO data source
					 String className = "com.netpace.ldap.bean.LdapUser";
					 
					 // Look up existing table in the report to set the datasource for and obtain its alias.  This table must
					 // have the same schema as the Resultset that is being pushed in at runtime.  The table could be created
					 // from a Field Definition File, a Command Object, or regular database table.  As long the Resultset
					 // schema has the same field names and types, then the Resultset can be used as the datasource for the table.
					 String tableAlias = "LdapUser";

					 //Create a dataset based on the class com.netpace.ldap.bean.LdapUser
					 //If the class does not have a basic constructor with no parameters, make sure to adjust that manually
					 					 
					 List dataSet = (List) request.getAttribute("devoplerList");


					 //Push the resultset into the report (the POJO resultset will then be the runtime datasource of the report)
					 CRJavaHelper.passPOJO(clientDoc, dataSet, className, tableAlias, "");
				}


			}
			// ****** END POPULATE WITH POJO SNIPPET ****************		
			
					
			// ****** BEGIN CONNECT PARAMETERS SNIPPET ****************		
			     // This option is not applicable for the report you have chosen
			// ****** END CONNECT PARAMETERS SNIPPET ****************	
		

			// Store the report document in session
			session.setAttribute(reportName, clientDoc);

		}

				
		// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
		{
			
			// Create the CrystalReportViewer object
			CrystalReportViewer crystalReportPageViewer = new CrystalReportViewer();

			String reportSourceSessionKey = reportName+"ReportSource";
			Object reportSource = session.getAttribute(reportSourceSessionKey);
			
			reportSource = clientDoc.getReportSource();
			session.setAttribute(reportSourceSessionKey, reportSource);
		    
			crystalReportPageViewer.setReportSource(reportSource);
		    
			crystalReportPageViewer.setDocumentViewType(CrDocumentViewTypeEnum.webLayout);
			
			//crystalReportPageViewer.setHasPageNavigationButtons(false);
			crystalReportPageViewer.setHasToggleGroupTreeButton(false); 
			crystalReportPageViewer.setHasZoomFactorList(false); 
			crystalReportPageViewer.setHasDrilldownTabs(false);
			crystalReportPageViewer.setHasLogo(false);
			//crystalReportPageViewer.setHasToggleGroupTreeButton(false);
			//crystalReportPageViewer.setBestFitPage(false);
			//crystalReportPageViewer.setDisplayGroupTree(false);
			crystalReportPageViewer.setEnableDrillDown(false);
			crystalReportPageViewer.setHasToggleParameterPanelButton(false);
			crystalReportPageViewer.removeAfterRenderEventListener();
			//crystalReportPageViewer.setBestFitPage(true);
			//crystalReportPageViewer.setDisplayGroupTree(false); // displays left pane
			//crystalReportPageViewer.setDisplayPage(false);
			//crystalReportPageViewer.setHeight(500);
			crystalReportPageViewer.setLeft(70);
			//crystalReportPageViewer.setWidth(70);
			crystalReportPageViewer.setName("RegisteredDevelopersReport");
			
			crystalReportPageViewer.processHttpRequest(request, response, getServletContext(),response.getWriter()); 
	
			crystalReportPageViewer.dispose();	

		}
		// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		
	

	} catch (ReportSDKExceptionBase e) {
	    out.println(e);
	}
	
%>