<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*,com.netpace.aims.model.core.*,com.netpace.aims.controller.application.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:useBean id="DashboardApplicationUpdateForm" class="com.netpace.aims.controller.application.DashboardApplicationUpdateForm" scope="request" />

<%DashboardApplicationUpdateForm.setCurrentPage("page5");%>

<%@ include file="include/dashboardAppVariables.jsp"%>

<script language="javascript">	  
	function expand(num) {    	
    	for (var i=1; i<=6; i++){
    		var cell="row_"+i;
    		var imgCell="img_cell_"+i;
    		var imgCellText='<a onclick="expand('+i+'); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a>';
    		document.getElementById(imgCell).innerHTML=imgCellText;
    		document.getElementById(cell).style.display="none";
    	}
    	
    	var cell="cell_"+num;
    	var row="row_"+num;
    	var imgCell="img_cell_"+num;
    	var imgCellText='<a onclick="collapse('+num+'); return false;" href="#"><img src="images/arrow_down.gif" border="0" /></a>';
    	if (num == 1){
    		document.getElementById(cell).innerHTML=document.forms[0].usingApplication.value;
    	}
    	else if (num == 2){
    		document.getElementById(cell).innerHTML=document.forms[0].tipsAndTricks.value;
    	}
    	else if (num == 3){
    		document.getElementById(cell).innerHTML=document.forms[0].faq.value;
    	}
    	else if (num == 4){
    		document.getElementById(cell).innerHTML=document.forms[0].troubleshooting.value;
    	}
    	else if (num == 5){
    		document.getElementById(cell).innerHTML=document.forms[0].devCompanyDisclaimer.value;
    	}
    	else if (num == 6){
    		document.getElementById(cell).innerHTML=document.forms[0].additionalInformation.value;
    	}
    	
    	if (document.getElementById(cell).innerHTML.length>0){
    		document.getElementById(imgCell).innerHTML=imgCellText;
        	document.getElementById(row).style.display="";
        }
    }
    
	function collapse(num) {
    	var cell="cell_"+num;
    	var row="row_"+num;
    	var imgCell="img_cell_"+num;
    	var imgCellText='<a onclick="expand('+num+'); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a>';
    	document.getElementById(cell).innerHTML="&nbsp;";
    	document.getElementById(imgCell).innerHTML=imgCellText;
        document.getElementById(row).style.display="none";		
    }  
	<%@ include  file="include/dashboardJScript.jsp" %>
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%">
			<%@ include file="include/dashboardMessageHeader.jsp" %>
		</td>
	</tr>
</table>


<div id="contentBox" style="float: left">
	<div class="homeColumnTab">
		<%@ include file="include/dashboardViewTabs.jsp"%>
		<div class="contentbox">
			<html:form action="/dashboardApplicationUpdate" enctype="multipart/form-data">
			<%@ include file="include/dashboardAppHidden.jsp"%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="50%" valign="top">
						<table width="375" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="noPad">
									<div class="mmBox">
										<DIV class="headLeftCurveblk"></DIV>
										<H1>User Guide Details</H1>
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							<tr>
								<td style="padding: 0px">
								<table width="100%" border="0" bordercolor="black" cellspacing="0" cellpadding="5" class="GridGradient">
									<tr>
										<th>
											<strong>Company Logo:</strong><br/>					
															
											<logic:notEmpty name="DashboardApplicationUpdateForm" property="companyLogoFileName" scope="request">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=CompanyLogo&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="companyLogoFileName" /></a>
											</logic:notEmpty>									
										</th>
									</tr>
									<tr>
										<td>
											<strong>Channel Title:</strong><br/>
											<bean:write name="DashboardApplicationUpdateForm" property="title" />
										</td>
									</tr>
									<tr>
										<td>
											<strong>Title Image:</strong><br/>					
															
											<logic:notEmpty name="DashboardApplicationUpdateForm" property="titleImageFileName" scope="request">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TitleImage&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="titleImageFileName" /></a>
											</logic:notEmpty>									
										</td>
									</tr>															
									<tr>
										<td>
											<strong>Channel Description:</strong><br/>
											<html:textarea property="productDescription" rows="4" cols="50" styleClass="textareaField" readonly="true"></html:textarea>				                    
										</td>
									</tr>														
								</table>								
								</td>
							</tr>
						</table>
					</td>
					<td width="50%" valign=top>
						<table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="noPad">
									<div class="mmBox">
										<DIV class="headLeftCurveblk"></DIV>
										<H1>Other Details</H1>
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							<tr>
								<th>
									<strong><bean:message key="ManageApplicationForm.appScreenShot"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
								</th>
							</tr>
							
							<tr>
								<td>
									<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpegFileName" scope="request">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
											class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="screenJpegFileName" /></a>
									</logic:notEmpty>
									
									<br />
									<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg2FileName" scope="request">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
											 class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="screenJpeg2FileName" /></a>
									</logic:notEmpty>

									<br />
									<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg3FileName" scope="request">									
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
											class="a" target="_blank"> <bean:write name="DashboardApplicationUpdateForm" property="screenJpeg3FileName" /></a>
									</logic:notEmpty>

									<br />
									<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg4FileName" scope="request">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
											class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="screenJpeg4FileName" /></a>
									</logic:notEmpty>

									<br />
									<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg5FileName" scope="request">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
											class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="screenJpeg5FileName" /></a>
									</logic:notEmpty>								
								</td>
							</tr>							
						</table>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<table id="tbl_rteArea" width="100%" border="0" cellspacing="0" cellpadding="8">
							<tr>
								<td id="img_cell_1" width="3%"><a onclick="expand(1); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
								<td style="padding-left: 0px" width="30%"><strong>Using the Application:</strong></td>
								<td width="25%">&nbsp;</td>
								<td width="42%">&nbsp;<span id="field_1" ></span></td>
							</tr>
							<tr id="row_1" style="display: none"><td id="cell_1" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_2"><a onclick="expand(2); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
								<td style="padding-left: 0px"><strong>Tips and Tricks:</strong></td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_2" ></span></td>
							</tr>
							<tr id="row_2" style="display: none"><td id="cell_2" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_3"><a onclick="expand(3); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
								<td style="padding-left: 0px"><strong>FAQ:</strong></td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_3" ></span></td>
							</tr>
							<tr id="row_3" style="display: none"><td id="cell_3" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_4"><a onclick="expand(4); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
								<td style="padding-left: 0px"><strong>Troubleshooting:</strong></td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_4" ></span></td>
							</tr>
							<tr id="row_4" style="display: none"><td id="cell_4" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_5"><a onclick="expand(5); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
								<td style="padding-left: 0px"><strong>Development Company Disclaimer:</strong></td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_5" ></span></td>
							</tr>
							<tr id="row_5" style="display: none"><td id="cell_5" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_6"><a onclick="expand(6); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
								<td style="padding-left: 0px"><strong>Additional Information:</strong></td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_6" ></span></td>
							</tr>
							<tr id="row_6" style="display: none"><td id="cell_6" colspan="4" style="border: 1px solid black;"></td></tr>
						</table>
					</td>
				</tr>								
				<tr>
					<td width="100%">
						<%@ include file="include/dashboardAppViewButtons.jsp"%>
					</td>
				</tr>
			</table>
			</html:form>
		</div>
	</div>
</div>
