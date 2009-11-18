<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ include  file="include/dashboardAppVariables.jsp" %>
<c:set value="${requestScope.DashboardApplicationUpdateForm}" var="form"></c:set>

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
	    var cell_1=document.getElementById("cell_1");
	    cell_1.innerHTML=fieldObj.value;
	    if (cell_1.innerHTML.length > 0){
			textCount=0;
			lenObj.value=countNodeText(cell_1);
			var diff = maxChars - lenObj.value;
	    	countField.value = diff;			
		}
	}
	
	function trackCountForTextAreas(){
    	TrackCount(document.forms[0].productDescription, 'textCountProDesc', 1000);
    	RteTrackCount(document.forms[0].usingApplicationLen, document.forms[0].usingApplication, 'textCountUsingApplication', <%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>);
    	RteTrackCount(document.forms[0].tipsAndTricksLen, document.forms[0].tipsAndTricks, 'textCountTipsAndTricks', <%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>);
    	RteTrackCount(document.forms[0].faqLen, document.forms[0].faq, 'textCountFaq', <%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>);
    	RteTrackCount(document.forms[0].troubleshootingLen, document.forms[0].troubleshooting, 'textCountTroubleshooting', <%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>);
    	RteTrackCount(document.forms[0].devCompanyDisclaimerLen, document.forms[0].devCompanyDisclaimer, 'textCountDevCompanyDisclaimer', <%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>);
    	RteTrackCount(document.forms[0].additionalInformationLen, document.forms[0].additionalInformation, 'textCountAdditionalInformation', <%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>);    	
	}
	
	function openRte(num){
        var url="/aims/dashboardUserGuideRte.do";
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
	<%@ include  file="include/dashboardJScript.jsp" %>
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%">
			<%@ include file="include/dashboardMessageHeader.jsp" %>
		</td>
	</tr>
</table>


<div id="contentBox" style="float: left" onmousemove="truncateLocalTextAreas();">
	<div class="homeColumnTab">
		<%@ include file="include/dashboardTabs.jsp"%>
		<div class="contentbox">
			<html:form action="/dashboardApplicationUpdate" enctype="multipart/form-data">
			<html:hidden property="currentPage" value="page5"/>
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
								<th>
									<strong>Company Logo:(Size: 30KB, Format: JPG)</strong><br/>
									<html:file size="30" styleClass="inputField" property="companyLogo"/><br/>
									<logic:notEmpty	name="DashboardApplicationUpdateForm" property="companyLogoFileName" scope="request">
										<logic:equal name="DashboardApplicationUpdateForm" property="companyLogoTempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=CompanyLogo&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="DashboardApplicationUpdateForm" property="companyLogoTempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="companyLogoTempFileId" />" class="a" target="_blank">
										</logic:notEqual>
												<bean:write	name="DashboardApplicationUpdateForm" property="companyLogoFileName"/>
											</a>
									</logic:notEmpty>									
								</th>
							</tr>
							<tr>
								<td>
									<strong>Channel Title&nbsp;<span class="requiredText">*</span>:</strong><br/>
									<html:text property="title" size="40" maxlength="200" styleClass="inputField"/>
								</td>
							</tr>
							<tr>
								<td>
									<strong>Title Image:(Size: 30KB, Format: JPG)</strong><br/>
									<html:file size="30" styleClass="inputField" property="titleImage"/><br/>
									<logic:notEmpty	name="DashboardApplicationUpdateForm" property="titleImageFileName" scope="request">
										<logic:equal name="DashboardApplicationUpdateForm" property="titleImageTempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TitleImage&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="DashboardApplicationUpdateForm" property="titleImageTempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="titleImageTempFileId" />" class="a" target="_blank">
										</logic:notEqual>
												<bean:write	name="DashboardApplicationUpdateForm" property="titleImageFileName"/>
											</a>
									</logic:notEmpty>									
								</td>
							</tr>														
							<tr>
								<td>
									<strong>Channel Description&nbsp;<span class="requiredText">*</span>:</strong><br/>
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
				                                <input class="inputFieldNoPad" type="text" name="textCountProDesc" size="1" value="1000" disabled="true"/>
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
									<strong><bean:message key="ManageApplicationForm.appScreenShot"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
									<span class="requiredText">*</span>:</strong>
								</th>
							</tr>
							
							<tr>
								<td>
								<html:file size="30" styleClass="inputField" property="screenJpeg"/><br/>
								<logic:notEmpty	name="DashboardApplicationUpdateForm"	property="screenJpegFileName" scope="request">
									<logic:equal name="DashboardApplicationUpdateForm" property="screenJpegTempFileId"	scope="request"	value="0">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
									</logic:equal>
									<logic:notEqual	name="DashboardApplicationUpdateForm"	property="screenJpegTempFileId" scope="request" value="0">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpegTempFileId" />" class="a" target="_blank">
									</logic:notEqual>
										<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpegFileName"/>
										</a>
								</logic:notEmpty>					
			                  </td>
			                </tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField" property="screenJpeg2"/><br/>
									<logic:notEmpty	name="DashboardApplicationUpdateForm"	property="screenJpeg2FileName" scope="request">
										<logic:equal name="DashboardApplicationUpdateForm" property="screenJpeg2TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="DashboardApplicationUpdateForm"	property="screenJpeg2TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg2TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg2FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField"  property="screenJpeg3"/><br/>
									<logic:notEmpty	name="DashboardApplicationUpdateForm"	property="screenJpeg3FileName" scope="request">
										<logic:equal name="DashboardApplicationUpdateForm" property="screenJpeg3TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="DashboardApplicationUpdateForm"	property="screenJpeg3TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg3TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg3FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField" property="screenJpeg4"/><br/>
									<logic:notEmpty	name="DashboardApplicationUpdateForm"	property="screenJpeg4FileName" scope="request">
										<logic:equal name="DashboardApplicationUpdateForm" property="screenJpeg4TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="DashboardApplicationUpdateForm"	property="screenJpeg4TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg4TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg4FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField" property="screenJpeg5"/><br/>
									<logic:notEmpty	name="DashboardApplicationUpdateForm"	property="screenJpeg5FileName" scope="request">
										<logic:equal name="DashboardApplicationUpdateForm" property="screenJpeg5TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="DashboardApplicationUpdateForm"	property="screenJpeg5TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg5TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg5FileName"/>
											</a>
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
								<td style="padding-left: 0px" width="30%">
									<strong>
										<a onclick="openRte('1'); return false;" href="#">Using the Application</a>&nbsp;:
									</strong>									
								</td>
								<td width="25%">
									<table border="0" cellpadding="0" cellspacing="0">
				                        <tr>
				                            <td style="vertical-align:middle;padding:0">
				                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				                            </td>
				                            <td>
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountUsingApplication" size="1" value="<%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>" disabled="true"/>
				                            </td>
				                        </tr>
				                    </table>									
								</td>
								<td width="42%">&nbsp;<span id="field_1" ></span></td>
							</tr>
							<tr id="row_1" style="display: none"><td id="cell_1" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_2"><a onclick="expand(2); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
								<td style="padding-left: 0px">									
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
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountTipsAndTricks" size="1" value="<%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>" disabled="true"/>
				                            </td>
				                        </tr>
				                    </table>
								</td>
								<td>&nbsp;<span id="field_2" ></span></td>
							</tr>
							<tr id="row_2" style="display: none"><td id="cell_2" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_3"><a onclick="expand(3); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>
								<td style="padding-left: 0px">
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
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountFaq" size="1" value="<%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>" disabled="true"/>
				                            </td>
				                        </tr>
				                    </table>
								</td>
								<td>&nbsp;<span id="field_3" ></span></td>
							</tr>
							<tr id="row_3" style="display: none"><td id="cell_3" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_4"><a onclick="expand(4); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
								<td style="padding-left: 0px">
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
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountTroubleshooting" size="1" value="<%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>" disabled="true"/>
				                            </td>
				                        </tr>
				                    </table>
								</td>
								<td>&nbsp;<span id="field_4" ></span></td>
							</tr>
							<tr id="row_4" style="display: none"><td id="cell_4" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_5"><a onclick="expand(5); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
								<td style="padding-left: 0px">
									<strong>
										<a onclick="openRte('5'); return false;" href="#">Development Company Disclaimer</a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>
									<table border="0" cellpadding="0" cellspacing="0">
				                        <tr>
				                            <td style="vertical-align:middle;padding:0">
				                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				                            </td>
				                            <td>
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountDevCompanyDisclaimer" size="1" value="<%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>" disabled="true"/>
				                            </td>
				                        </tr>
				                    </table>
								</td>
								<td>&nbsp;<span id="field_5" ></span></td>
							</tr>
							<tr id="row_5" style="display: none"><td id="cell_5" colspan="4" style="border: 1px solid black;"></td></tr>
							<tr>
								<td id="img_cell_6"><a onclick="expand(6); return false;" href="#"><img src="images/arrow_right.gif" border="0" /></a></td>							
								<td style="padding-left: 0px">
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
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountAdditionalInformation" size="1" value="<%=AimsConstants.DASHBOARD_USER_GUIDE_FIELDS_LENGTH%>" disabled="true"/>
				                            </td>
				                        </tr>
				                    </table>
								</td>
								<td>&nbsp;<span id="field_6" ></span></td>
							</tr>
							<tr id="row_6" style="display: none"><td id="cell_6" colspan="4" style="border: 1px solid black;"></td></tr>
						</table>
					</td>
				</tr>								
				<tr>
					<td width="100%">
						<table width="100%" cellpadding="0" cellspacing="0" border="0">
							<%@ include file="include/dashboardAppEditButtons.jsp"%>
							<tr><td><%@ include file="include/dashboardMessageFooter.jsp" %></td> </tr>
						</table>
					</td>
				</tr>
			</table>
			</html:form>
		</div>
	</div>
</div>
<script language="javascript">
	trackCountForTextAreas();
</script>