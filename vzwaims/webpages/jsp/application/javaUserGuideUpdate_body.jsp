<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ include  file="include/javaAppVariables.jsp" %>
<c:set value="${requestScope.javaApplicationUpdateForm}" var="form"></c:set>

<script language="javascript">
	var fieldNo;
		
	function truncateLocalTextAreas(){
		try {		
        	TruncateTextWithCount(document.forms[0].productDescription, 'textCountProDesc', 1000);
        } 
        catch(err){}
	}
	
	function RteTrackCount(lenObj, fieldObj, countFieldName, maxChars){
	    var countField = eval("fieldObj.form." + countFieldName);
		if( ! (countField == null)){	    
		    var cell_1=document.getElementById("cell_1");
		    cell_1.innerHTML=fieldObj.value;
		    if (cell_1.innerHTML.length > 0){
				textCount=0;
				lenObj.value=countNodeText(cell_1);
				var diff = maxChars - lenObj.value;
		    	countField.value = diff;			
			}
		}
	}
	
	function trackCountForTextAreas(){
    	TrackCount(document.forms[0].productDescription, 'textCountProDesc', 1000);
    	
    	RteTrackCount(document.forms[0].usingApplicationLen, document.forms[0].usingApplication, 'textCountUsingApplication', <%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>);
		
		RteTrackCount(document.forms[0].tipsAndTricksLen, document.forms[0].tipsAndTricks, 'textCountTipsAndTricks', <%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>);
		
		RteTrackCount(document.forms[0].faqLen, document.forms[0].faq, 'textCountFaq', <%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>);
	    
	    RteTrackCount(document.forms[0].troubleshootingLen, document.forms[0].troubleshooting, 'textCountTroubleshooting', <%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>);
    	
    	RteTrackCount(document.forms[0].devCompanyDisclaimerLen, document.forms[0].devCompanyDisclaimer, 'textCountDevCompanyDisclaimer', <%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>);
		
		RteTrackCount(document.forms[0].additionalInformationLen, document.forms[0].additionalInformation, 'textCountAdditionalInformation', <%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>);    	
	}
	
	function openRte(num){
		var url="/aims/javaUserGuideRte.do";
        var wind=window.open (url,"userGuideRte","resizable=1,width=850,height=600,scrollbars=1,screenX=100,left=100,screenY=30,top=20");        
        wind.focus();        
        fieldNo=num;
    }
    
    function getFieldNo(){
    	return fieldNo;
    }
    
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
	<%@ include  file="include/javaJScript.jsp" %>
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%">
			<%@ include file="include/javaMessageHeader.jsp" %>
		</td>
	</tr>
</table>


<div id="contentBox" style="float: left" onmousemove="truncateLocalTextAreas();">
	<div class="homeColumnTab">
		<%@ include file="include/javaTabs.jsp"%>
		<div class="contentbox">
			<html:form action="/javaApplicationUpdate" enctype="multipart/form-data">
			<html:hidden property="currentPage" value="page5"/>
			<%@ include file="include/javaAppHidden.jsp"%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="50%" valign="top" style="padding-right:5px;" >
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
								<th>
									<strong><bean:message key="JavaApplicationForm.companyLogo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
									<html:file size="30" styleClass="inputField" property="companyLogo"/><br/>
									<logic:notEmpty	name="javaApplicationUpdateForm" property="companyLogoFileName" scope="request">
										<logic:equal name="javaApplicationUpdateForm" property="companyLogoTempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=CompanyLogo&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="javaApplicationUpdateForm" property="companyLogoTempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="companyLogoTempFileId" />" class="a" target="_blank">
										</logic:notEqual>
												<bean:write	name="javaApplicationUpdateForm" property="companyLogoFileName"/>
											</a>
									</logic:notEmpty>									
								</th>
							</tr>
							<tr>
								<td>
									<strong><bean:message key="JavaApplicationForm.applicationName"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
									<html:text property="title" size="40" maxlength="200" styleClass="inputField"/>
								</td>
							</tr>

							<tr>
								<td>
									<strong>Title Image:(Size: 30KB, Format: JPG)&nbsp;<span class="requiredText">*</span>:</strong><br/>
									<html:file size="30" styleClass="inputField" property="titleImage"/>									
									<br/>
									
									
									<logic:notEmpty	name="javaApplicationUpdateForm" property="titleImageFileName" scope="request">
										<logic:equal name="javaApplicationUpdateForm" property="titleImageTempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TitleImage&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="javaApplicationUpdateForm" property="titleImageTempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="titleImageTempFileId" />" class="a" target="_blank">
										</logic:notEqual>
												<bean:write	name="javaApplicationUpdateForm" property="titleImageFileName"/>
											</a>
									</logic:notEmpty>									
								</td>
							</tr>	
							
																				
							<tr>
								<td>
									<strong><bean:message key="JavaApplicationForm.productDescription"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
									<html:textarea property="productDescription" rows="4" cols="50" styleClass="textareaField"
				                                   onkeyup="TrackCount(this,'textCountProDesc',1000)"
				                                   onkeypress="LimitText(this,1000)"></html:textarea>
				                    <br/>
				                    <table border="0" cellpadding="0" cellspacing="0">
				                        <tr>
				                            <td style="vertical-align:middle;padding:0">
				                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				                            </td>
				                            <td>
				                                <input class="inputFieldNoPad" type="text" name="textCountProDesc" size="4" value="1000" disabled="true"/>
				                            </td>
				                        </tr>
				                    </table>
								</td>
							</tr>							
						</table>
					</td>
					<td width="50%" valign="top" style="padding-left:5px;" >
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
									<strong><bean:message key="ManageApplicationForm.appScreenShot"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
									<span class="requiredText">*</span>:</strong>
								</th>
							</tr>
							
							<tr>
								<td>
								<html:file size="30" styleClass="inputField" property="screenJpeg"/><br/>
								<logic:notEmpty	name="javaApplicationUpdateForm"	property="screenJpegFileName" scope="request">
									<logic:equal name="javaApplicationUpdateForm" property="screenJpegTempFileId"	scope="request"	value="0">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
									</logic:equal>
									<logic:notEqual	name="javaApplicationUpdateForm"	property="screenJpegTempFileId" scope="request" value="0">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpegTempFileId" />" class="a" target="_blank">
									</logic:notEqual>
										<bean:write	name="javaApplicationUpdateForm"	property="screenJpegFileName"/>
										</a>
								</logic:notEmpty>					
			                  </td>
			                </tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField" property="screenJpeg2"/><br/>
									<logic:notEmpty	name="javaApplicationUpdateForm"	property="screenJpeg2FileName" scope="request">
										<logic:equal name="javaApplicationUpdateForm" property="screenJpeg2TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="javaApplicationUpdateForm"	property="screenJpeg2TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg2TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg2FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField"  property="screenJpeg3"/><br/>
									<logic:notEmpty	name="javaApplicationUpdateForm"	property="screenJpeg3FileName" scope="request">
										<logic:equal name="javaApplicationUpdateForm" property="screenJpeg3TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="javaApplicationUpdateForm"	property="screenJpeg3TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg3TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg3FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField" property="screenJpeg4"/><br/>
									<logic:notEmpty	name="javaApplicationUpdateForm"	property="screenJpeg4FileName" scope="request">
										<logic:equal name="javaApplicationUpdateForm" property="screenJpeg4TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="javaApplicationUpdateForm"	property="screenJpeg4TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg4TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg4FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField" property="screenJpeg5"/><br/>
									<logic:notEmpty	name="javaApplicationUpdateForm"	property="screenJpeg5FileName" scope="request">
										<logic:equal name="javaApplicationUpdateForm" property="screenJpeg5TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="javaApplicationUpdateForm"	property="screenJpeg5TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg5TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg5FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>							
						</table>
					</td>
				</tr>
			</table>



<%--  View for where RTE is disabled for java application    --%>

<%if ( isSumitted ) { %>

				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="8">
								<tr>
									<td width="63%">
										<table id="tbl_rteArea" width="100%" border="0" cellspacing="0" cellpadding="8">
											<tr>
												<td id="img_cell_1" width="3%"><a onclick="expand(1); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
												<td style="padding-left: 0px" width="45%" nowrap="nowrap"><strong>Using the Application:</strong></td>
												<td width="42%" nowrap="nowrap">&nbsp;</td>
												<td width="10%" nowrap="nowrap">&nbsp;<span id="field_1" ></span></td>
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
									
									<td>
										<strong>
											Although these fields are not required, additional information may expedite concept approval.
										</strong>
									</td>
								</tr>
							</table>						
						</td>
					</tr>			
				
				<%
				if ( isVerizonUser )
				{
				%>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td width="0" valign=top>
						<table width="100%" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="noPad" colspan="2" width="100%">
									<div class="lBox">
										<DIV class="headLeftCurveblk"></DIV>
										<H1><bean:message key="BrewApplicationForm.table.head.userGuideSnapshot" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${requestScope.javaApplicationUpdateForm.appsId gt 0}">
										<th width="8%">
											<img src="/aims/images/icon_pdf.gif" alt="User Guide" border="0"/>
										</th>
										<th width="80%">
											<a href="/aims/javaPdf.do?app_id=<bean:write name="javaApplicationUpdateForm" property="appsId"	/>"	class="a"	target="_blank">User Guide</a>
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
				<%} %>	
				<%  
				System.out.println("isRFI isRFI isRFI " + isRFI);
				System.out.println("isVerizonUser isVerizonUser isVerizonUser " + isVerizonUser);
				
				if(isRFI && !isVerizonUser) { %>
				<tr>
					<td colspan="2">
						<div class="lBox">
							<DIV class="headLeftCurveblk"></DIV>
							<H1>
								Comments
							</H1>

							<DIV class="headRightCurveblk"></DIV>
						</div>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="5"
							class="GridGradient">
							<tr>
								<td width=50%>
									<html:textarea property="comments" rows="4" cols="50" styleClass="textareaField" onkeypress="LimitText(this,500)"></html:textarea>
									<br />
									<span id="airTime" style="display: none"><c:out value="${requestScope.AirTimeText}" escapeXml="false"></c:out>
									</span>									
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<% } %>				
				<tr>
					<td width="100%">
						<table width="100%" cellpadding="0" cellspacing="0" border="0">
							<%@ include file="include/javaAppEditButtons.jsp"%>
							<tr><td><%@ include file="include/javaMessageFooter.jsp" %></td> </tr>
						</table>
					</td>				
				</tr>
				<tr>
					<td>
						<br/>
						<table cellspacing="0" cellpadding="0" border="0" width="100%">
							<tr>
								<td class="borderT">											
									<p class="bodySmallText">
										Note: Binaries are not submitted at this time.  Verizon must approve your concept prior to submitting binaries.  Verizon will notify you directly once your concept have been approved.
									
									</p>
								</td>
							</tr>							
						</table>
					</td>
				</tr>
			</table>
			
								
			


<%}else  { %>
<%--  View for where RTE is enableb for java application    --%>

			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<table  width="100%" border="0" cellspacing="0" cellpadding="8">
							<tr>
								<td width="63%">
									<table id="tbl_rteArea" width="100%" border="0" cellspacing="0" cellpadding="8">
										<tr>
											<td id="img_cell_1" width="3%"><a onclick="expand(1); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
											<td style="padding-left: 0px" width="45%" nowrap="nowrap">
												<strong>
													<a onclick="openRte('1'); return false;" href="#">Using the Application</a>&nbsp;:
												</strong>									
											</td>
											<td width="42%" nowrap="nowrap">
												<table border="0" cellpadding="0" cellspacing="0">
							                        <tr>
							                            <td style="vertical-align:middle;padding:0">
							                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
							                            </td>
							                            <td>
							                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountUsingApplication" size="4" value="<%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
							                            </td>
							                        </tr>
							                    </table>									
											</td>
											<td width="10%" nowrap="nowrap">&nbsp;<span id="field_1" ></span></td>
										</tr>
										<tr id="row_1" style="display: none"><td id="cell_1" colspan="4" style="border: 1px solid black;"></td></tr>
										<tr>
											<td id="img_cell_2"><a onclick="expand(2); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
											<td style="padding-left: 0px" nowrap="nowrap">									
												<strong>
													<a onclick="openRte('2'); return false;" href="#">Tips and Tricks</a>&nbsp;:
												</strong>
											</td>
											<td>
												<table border="0" cellpadding="0" cellspacing="0">
							                        <tr>
							                            <td style="vertical-align:middle;padding:0">
							                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
							                            </td>
							                            <td>
							                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountTipsAndTricks" size="4" value="<%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
							                            </td>
							                        </tr>
							                    </table>
											</td>
											<td nowrap="nowrap">&nbsp;<span id="field_2" ></span></td>
										</tr>
										<tr id="row_2" style="display: none"><td id="cell_2" colspan="4" style="border: 1px solid black;"></td></tr>
										<tr>
											<td id="img_cell_3"><a onclick="expand(3); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
											<td style="padding-left: 0px" nowrap="nowrap">
												<strong>
													<a onclick="openRte('3'); return false;" href="#">FAQ</a>&nbsp;:
												</strong>
											</td>
											<td>
												<table border="0" cellpadding="0" cellspacing="0">
							                        <tr>
							                            <td style="vertical-align:middle;padding:0">
							                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
							                            </td>
							                            <td>
							                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountFaq" size="4" value="<%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
							                            </td>
							                        </tr>
							                    </table>
											</td>
											<td nowrap="nowrap">&nbsp;<span id="field_3" ></span></td>
										</tr>
										<tr id="row_3" style="display: none"><td id="cell_3" colspan="4" style="border: 1px solid black;"></td></tr>
										<tr>
											<td id="img_cell_4"><a onclick="expand(4); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
											<td style="padding-left: 0px" nowrap="nowrap">
												<strong>
													<a onclick="openRte('4'); return false;" href="#">Troubleshooting</a>&nbsp;:
												</strong>
											</td>
											<td>
												<table border="0" cellpadding="0" cellspacing="0">
							                        <tr>
							                            <td style="vertical-align:middle;padding:0">
							                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
							                            </td>
							                            <td>
							                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountTroubleshooting" size="4" value="<%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
							                            </td>
							                        </tr>
							                    </table>
											</td>
											<td nowrap="nowrap">&nbsp;<span id="field_4" ></span></td>
										</tr>
										<tr id="row_4" style="display: none"><td id="cell_4" colspan="4" style="border: 1px solid black;"></td></tr>
										<tr>
											<td id="img_cell_5"><a onclick="expand(5); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
											<td style="padding-left: 0px" nowrap="nowrap">
												<strong>
													<a onclick="openRte('5'); return false;" href="#">Development Company Disclaimer</a>&nbsp;:
												</strong>
											</td>
											<td>
												<table border="0" cellpadding="0" cellspacing="0">
							                        <tr>
							                            <td style="vertical-align:middle;padding:0">
							                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
							                            </td>
							                            <td>
							                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountDevCompanyDisclaimer" size="4" value="<%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
							                            </td>
							                        </tr>
							                    </table>
											</td>
											<td nowrap="nowrap">&nbsp;<span id="field_5" ></span></td>
										</tr>
										<tr id="row_5" style="display: none"><td id="cell_5" colspan="4" style="border: 1px solid black;"></td></tr>
										<tr>
											<td id="img_cell_6"><a onclick="expand(6); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
											<td style="padding-left: 0px" nowrap="nowrap">
												<strong>
													<a onclick="openRte('6'); return false;" href="#">Additional Information</a>&nbsp;:
												</strong>
											</td>
											<td>
												<table border="0" cellpadding="0" cellspacing="0">
							                        <tr>
							                            <td style="vertical-align:middle;padding:0">
							                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
							                            </td>
							                            <td>
							                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountAdditionalInformation" size="4" value="<%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
							                            </td>
							                        </tr>
							                    </table>
											</td>
											<td nowrap="nowrap">&nbsp;<span id="field_6" ></span></td>
										</tr>
										<tr id="row_6" style="display: none"><td id="cell_6" colspan="4" style="border: 1px solid black;"></td></tr>
									</table>
								
								</td>
								<td>
									<strong>Although these fields are not required, additional information may expedite concept approval.</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<%
				if ( isVerizonUser )
				{
				%>				
					<c:choose>
						<c:when test="${requestScope.javaApplicationUpdateForm.appsId gt 0}">				
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
											<th width="8%">
												<img src="/aims/images/icon_pdf.gif" alt="User Guide" border="0"/>
											</th>
											<th width="80%">
												<a href="/aims/javaPdf.do?app_id=<bean:write name="javaApplicationUpdateForm" property="appsId"	/>"	class="a"	target="_blank">User Guide</a>
											</th>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									&nbsp;
								</td>
							</tr>
						</c:when>						
					</c:choose>				
				<%
				}
				%>
				<%  
				System.out.println("isRFI isRFI isRFI " + isRFI);
				System.out.println("isVerizonUser isVerizonUser isVerizonUser " + isVerizonUser);
				
				if(isRFI && !isVerizonUser) { %>
				<tr>
					<td colspan="2">
						<div class="lBox">
							<DIV class="headLeftCurveblk"></DIV>
							<H1>
								Comments
							</H1>

							<DIV class="headRightCurveblk"></DIV>
						</div>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="5"
							class="GridGradient">
							<tr>
								<td width=50%>
									<html:textarea property="comments" rows="4" cols="50" styleClass="textareaField" onkeypress="LimitText(this,500)"></html:textarea>
									<br />
									<span id="airTime" style="display: none"><c:out value="${requestScope.AirTimeText}" escapeXml="false"></c:out>
									</span>									
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<% } %>
				
				<tr>
					<td width="100%">
						<table width="100%" cellpadding="0" cellspacing="0" border="0">
							<%@ include file="include/javaAppEditButtons.jsp"%>
							<tr><td><%@ include file="include/javaMessageFooter.jsp" %></td> </tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<br/>
						<table cellspacing="0" cellpadding="0" border="0" width="100%">
							<tr>
								<td class="borderT">											
									<p class="bodySmallText">
										Note: Binaries are not submitted at this time.  Verizon must approve your concept prior to submitting binaries.  Verizon will notify you directly once your concept have been approved.
									
									</p>
								</td>
							</tr>							
						</table>
					</td>
				</tr>					
				
		</table>
	<%} %>	
<%--  END of user guides --%>
			
				<script>
				
				trackCountForTextAreas();
				
					<%
						if ( isLocked ) 
						{
					%>		
							function lockApplicationPage3() {			
								var frm = document.forms[0];
			                        frm.companyLogo.disabled = true;
			                        frm.title.disabled = true;
			                        frm.titleImage.disabled = true;
			                        frm.productDescription.disabled = true;
			                        frm.screenJpeg.disabled = true;
			                        frm.screenJpeg2.disabled = true;
			                        frm.screenJpeg3.disabled = true;
			                        frm.screenJpeg4.disabled = true;
			                        frm.screenJpeg5.disabled = true;			                        	                        
							}//end lockApplicationPage1
							lockApplicationPage3();
					<% 
						}
					%>	
				</script>
				<% 
					if (isLocked) 
					{
				%>
						<html:hidden property="title"/>    
						<html:hidden property="productDescription"/>
				<%
					}
				%>
			</html:form>
		</div>
	</div>
</div>