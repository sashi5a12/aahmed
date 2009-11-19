
<%@page import="com.crystaldecisions.sdk.occa.report.exportoptions.IPDFExportFormatOptions"%>
<%@page import="com.crystaldecisions.sdk.occa.report.exportoptions.IExportFormatOptions"%>
<%@page import="com.crystaldecisions.report.web.viewer.ReportExportControl"%>
<%@page import="com.crystaldecisions.sdk.occa.report.exportoptions.ExportOptions"%>
<%@page import="com.crystaldecisions.sdk.occa.report.exportoptions.RTFWordExportFormatOptions"%>
<%@page import="com.crystaldecisions.sdk.occa.report.exportoptions.PDFExportFormatOptions"%>
<%@page import="com.crystaldecisions.reports.reportdefinition.ReportExportOptions"%>
<%@page import="com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat"%>
<%@page import="com.crystaldecisions.report.web.viewer.CrDocumentViewTypeEnum"%>
<%@page import="java.util.List"%><%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %><%@ page import="com.businessobjects.samples.CRJavaHelper,com.crystaldecisions.report.web.viewer.CrystalReportViewer,com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,com.crystaldecisions.sdk.occa.report.application.ReportClientDocument,com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase" %><%
	// This sample code calls methods from the CRJavaHelper class, which 
	// contains examples of how to use the BusinessObjects APIs. You are free to 
	// modify and distribute the source code contained in the CRJavaHelper class. 

	try {

		String reportName = "reports/rolereport/rolereport.rpt";
		session.removeAttribute(reportName);
		session.removeAttribute(reportName+"ReportSource");
		ReportClientDocument clientDoc = (ReportClientDocument) session.getAttribute(reportName);
		
		
			if (clientDoc == null) {
			clientDoc = new ReportClientDocument();
			clientDoc.setReportAppServer(ReportClientDocument.inprocConnectionString);
			
			clientDoc.open(reportName, OpenReportOptions.openAsReadOnly.value());
			{
				String JNDIName = "java:comp/env/jdbc/vzdn";
				CRJavaHelper.changeDataSource(clientDoc, null, null, null, null, JNDIName);
			}
			{
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
			}
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
		
			
			
			
			//ExportOptions oExportOptions = new ExportOptions();
			//IPDFExportFormatOptions pdfexportformatoptions = new PDFExportFormatOptions();
			//oExportOptions.setFormatOptions(pdfexportformatoptions);
		    //oExportOptions.setExportFormatType(ReportExportFormat.PDF);
		    //ReportExportControl oReportExportControl = new ReportExportControl();
		    //oReportExportControl.setReportSource(reportSource);
		    //oReportExportControl.setExportOptions(oExportOptions);
		    //oReportExportControl.setExportAsAttachment(false);	
			

		    //oReportExportControl.processHttpRequest(request, response, application, null); 
		    
		    
		    
		    
		    
		    
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
			crystalReportPageViewer.setName("VerizonRolesPrivilegesReport");
			
			
			crystalReportPageViewer.processHttpRequest(request, response, getServletContext(),response.getWriter()); 
			crystalReportPageViewer.dispose();	
		}

	} catch (ReportSDKExceptionBase e) {
	    System.out.println(e.getMessage());
	}
	
%>