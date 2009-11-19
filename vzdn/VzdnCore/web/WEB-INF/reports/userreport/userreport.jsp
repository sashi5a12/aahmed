
<%@page import="com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat"%>
<%@page import="com.crystaldecisions.report.web.viewer.CrDocumentViewTypeEnum"%>
<%@page import="java.util.List"%><%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %><%@ page import="com.businessobjects.samples.CRJavaHelper,
com.crystaldecisions.report.web.viewer.CrystalReportViewer,
com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
com.crystaldecisions.sdk.occa.report.application.ReportClientDocument,
com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase" %><%
	// This sample code calls methods from the CRJavaHelper class, which 
	// contains examples of how to use the BusinessObjects APIs. You are free to 
	// modify and distribute the source code contained in the CRJavaHelper class. 

	try {
		
		String reportName = "reports/userreport/userreport.rpt";
		session.removeAttribute(reportName);
		session.removeAttribute(reportName+"ReportSource");
		ReportClientDocument clientDoc = (ReportClientDocument) session.getAttribute(reportName);
		

		if (clientDoc == null) {
			clientDoc = new ReportClientDocument();
			clientDoc.setReportAppServer(ReportClientDocument.inprocConnectionString);
			clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);
			{
				String JNDIName = "java:comp/env/jdbc/vzdn";
				CRJavaHelper.changeDataSource(clientDoc, null, null, null, null, JNDIName);
			}
			{
				// STRING MULTI-VALUE PARAMETER.  
				if(request.getAttribute("assignedRoles")!=null){
					List<String> selectedRoles = (List<String>)request.getAttribute("assignedRoles");
					if(selectedRoles.size()>0){
						for(String role : selectedRoles){
							String stringValue1 = role;	// TODO: Fill in value
							CRJavaHelper.addDiscreteParameterValue(clientDoc, "", "rolenames", stringValue1);
						}
					}
				}else{
					CRJavaHelper.addDiscreteParameterValue(clientDoc, "", "rolenames", "ALL");	
				}
				
				if(request.getAttribute("userName")!=null && !request.getAttribute("userName").equals("") ){
					String userName = (String)request.getAttribute("userName");
					System.out.println("---------in jsp report user name is : "+userName);
					CRJavaHelper.addDiscreteParameterValue(clientDoc, "", "username", userName);
				}else{
					CRJavaHelper.addDiscreteParameterValue(clientDoc, "", "username", "ALL");	
				}
				String sortfield = (String)request.getAttribute("sorting");
				CRJavaHelper.addDiscreteParameterValue(clientDoc, "", "SortField", sortfield);
				
				
			}
			
			
			session.setAttribute(reportName, clientDoc);
		}

				
		// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
		{
			// Create the CrystalReportViewer object
			CrystalReportViewer crystalReportPageViewer = new CrystalReportViewer();

			String reportSourceSessionKey = reportName+"ReportSource";
			Object reportSource = session.getAttribute(reportSourceSessionKey);
			if (reportSource == null){
				reportSource = clientDoc.getReportSource();
				session.setAttribute(reportSourceSessionKey, reportSource);
			}
			//	set the reportsource property of the viewer	
			crystalReportPageViewer.setReportSource(reportSource);

			// Apply the viewer preference attributes
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
			crystalReportPageViewer.setLeft(70);
			crystalReportPageViewer.setName("VerizonUserByRoleReport");
			
			
			
			
			crystalReportPageViewer.processHttpRequest(request, response, getServletContext(), null); 
			crystalReportPageViewer.dispose();	
			//crystalReportPageViewer.proces
			

		}
		// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		
			
		
		
		

	} catch (ReportSDKExceptionBase e) {
	    out.println(e);
	}
	
%>