<%@	page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

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
</script>

<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="BrewApplicationUpdateForm" class="com.netpace.aims.controller.application.BrewApplicationUpdateForm" scope="request" />
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%@ include  file="/common/error.jsp" %>


<div id="contentBox" style="float: left">
	<div class="homeColumnTab">
		<%@ include  file="include/appViewTabs.jsp" %>
		<div class="contentbox">
			<html:form action="/brewApplicationUpdate.do"	enctype="multipart/form-data">
			<%@ include  file="include/appViewHidden.jsp" %>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td colspan="2" style="padding-bottom: 10px; padding-top: 10px">
					<bean:write name="BrewApplicationUpdateForm" property="disclaimerText" filter="false"/>
				</td></tr>
				<tr>
					<td width="50%" valign="top">
						<table width="375" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="noPad">
									<div class="mmBox">
										<DIV class="headLeftCurveblk"></DIV>
										<H1><bean:message key="BrewApplicationForm.table.head.userGuideDetails" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							<tr>
								<td style="padding: 0px">
									<table width="100%" border="0" bordercolor="black" cellspacing="0" cellpadding="5" class="GridGradient">							
									<tr>
										<th>
											<strong><bean:message key="BrewApplicationForm.companyLogo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong><br/>
											<logic:notEmpty	name="BrewApplicationUpdateForm" property="companyLogoFileName" scope="request">
		                                         <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=CompanyLogo&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
		                                             <bean:write	name="BrewApplicationUpdateForm" property="companyLogoFileName"/>
		                                         </a>
		                                    </logic:notEmpty>									
										</th>
									</tr>
									<tr>
										<td>
											<strong><bean:message key="ManageApplicationForm.appTitle"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
											<span class="requiredText">*</span>:</strong><br/>
											<bean:write name="BrewApplicationUpdateForm" property="title"/>
										</td>
									</tr>
									<tr>
										<td>
											<strong><bean:message key="BrewApplicationForm.titleShot"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong><br/>
											<logic:notEmpty	name="BrewApplicationUpdateForm" property="titleShotFileName" scope="request">										
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TitleShot&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">										
													<bean:write	name="BrewApplicationUpdateForm" property="titleShotFileName"/>
												</a>
											</logic:notEmpty>
										</td>
									</tr>
									<tr>
										<td>
											<strong><bean:message key="BrewApplicationForm.productDescription"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
											<html:textarea property="productDescription" rows="3" cols="50" styleClass="textareaField" readonly="true"/>				                    
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
										<H1><bean:message key="BrewApplicationForm.table.head.other.details" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							<tr>
								<th>
									<strong><bean:message key="ManageApplicationForm.appScreenShot"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
									<span class="requiredText">*</span>:</strong>
								</th>
							</tr>							
							<tr>
                               <td class="viewText">
                                   <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                       <bean:write	name="BrewApplicationUpdateForm" property="screenJpegFileName"/>
                                   </a>
                                   <logic:notEmpty name="BrewApplicationUpdateForm" property="screenJpeg2FileName">
                                       <br/>
                                       <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                           <bean:write	name="BrewApplicationUpdateForm" property="screenJpeg2FileName"/>
                                       </a>
                                   </logic:notEmpty>
                                   <logic:notEmpty name="BrewApplicationUpdateForm" property="screenJpeg3FileName">
                                       <br/>
                                       <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                           <bean:write	name="BrewApplicationUpdateForm" property="screenJpeg3FileName"/>
                                       </a>
                                   </logic:notEmpty>
                                   <logic:notEmpty name="BrewApplicationUpdateForm" property="screenJpeg4FileName">
                                       <br/>
                                       <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                           <bean:write	name="BrewApplicationUpdateForm" property="screenJpeg4FileName"/>
                                       </a>
                                   </logic:notEmpty>
                                   <logic:notEmpty name="BrewApplicationUpdateForm" property="screenJpeg5FileName">
                                       <br/>
                                       <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                           <bean:write	name="BrewApplicationUpdateForm" property="screenJpeg5FileName"/>
                                       </a>
                                   </logic:notEmpty>
                               </td>
                           </tr>						
							<tr>
								<td>
									<strong><bean:message key="BrewApplicationForm.multiplayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
									<span class="requiredText">*</span>:</strong>
								</td>
							</tr>
							<tr>
								<td>
			                        <logic:equal name="BrewApplicationUpdateForm" property="multiPlayer" value="Y">
			                            <bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			                        </logic:equal>
			                        <logic:equal name="BrewApplicationUpdateForm" property="multiPlayer" value="N">
			                            <bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			                        </logic:equal>&nbsp;
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
								<td style="padding-left: 0px" width="30%">
									<strong>
										<a onclick="openRte('1'); return false;" href="#"><bean:message key="BrewApplicationForm.usingApplication" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>									
								</td>
								<td width="25%">&nbsp;</td>
								<td width="42%">&nbsp;<span id="field_1" ></span></td>
							</tr>
							<tr id="row_1" style="display: none"><td id="cell_1" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_2"><a onclick="expand(2); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
								<td style="padding-left: 0px">									
									<strong>
										<a onclick="openRte('2'); return false;" href="#"><bean:message key="BrewApplicationForm.tipsAndTricks"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_2" ></span></td>
							</tr>
							<tr id="row_2" style="display: none"><td id="cell_2" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_3"><a onclick="expand(3); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
								<td style="padding-left: 0px">
									<strong>
										<a onclick="openRte('3'); return false;" href="#"><bean:message key="ManageApplicationForm.appFAQ"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_3" ></span></td>
							</tr>
							<tr id="row_3" style="display: none"><td id="cell_3" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_4"><a onclick="expand(4); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
								<td style="padding-left: 0px">
									<strong>
										<a onclick="openRte('4'); return false;" href="#"><bean:message key="BrewApplicationForm.troubleshooting" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_4" ></span></td>
							</tr>
							<tr id="row_4" style="display: none"><td id="cell_4" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_5"><a onclick="expand(5); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
								<td style="padding-left: 0px">
									<strong>
										<a onclick="openRte('5'); return false;" href="#"><bean:message key="BrewApplicationForm.devDisclaimer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_5" ></span></td>
							</tr>
							<tr id="row_5" style="display: none"><td id="cell_5" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_6"><a onclick="expand(6); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
								<td style="padding-left: 0px">
									<strong>
										<a onclick="openRte('6'); return false;" href="#"><bean:message key="BrewApplicationForm.additionalInfo" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;<span id="field_6" ></span></td>
							</tr>
							<tr id="row_6" style="display: none"><td id="cell_6" colspan="4" style="border: 1px solid black;"></td></tr>
						</table>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td width="50%" valign=top>
						<table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="noPad" colspan="2" width="100%">
									<div class="mmBox">
										<DIV class="headLeftCurveblk"></DIV>
										<H1><bean:message key="BrewApplicationForm.table.head.userGuideSnapshot" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${requestScope.BrewApplicationUpdateForm.appsId gt 0}">
										<th width="8%">
											<img src="/aims/images/icon_pdf.gif" alt="User Guide" border="0"/>
										</th>
										<th width="80%">
											<a href="/aims/brewPdf.do?app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId"	/>"	class="a"	target="_blank">User Guide</a>
										</th>										
									</c:when>
									<c:otherwise>
										<th width="80%" colspan="2">&nbsp;</th>									
									</c:otherwise>
								</c:choose>								
							</tr>
						</table>
					</td>
				</tr>						
				<tr>
            		<td width="100%">
	            		<table width="100%" cellpadding="0" cellspacing="0" border="0">
	                		<%@ include  file="include/appViewButtons.jsp" %>
	              		</table>
              		</td>
          		</tr>
			</table>
			</html:form>
		</div>
	</div>
</div>