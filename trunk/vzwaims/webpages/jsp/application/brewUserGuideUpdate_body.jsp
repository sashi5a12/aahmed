<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />
<% boolean isConcept = false; %>
<% ApplicationUpdateForm.setCurrentPage("page7"); %>
<logic:equal name="BrewApplicationUpdateForm" property="isConcept" scope="request"   value="Y">
  <% isConcept = true; %>
</logic:equal>

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
    	RteTrackCount(document.forms[0].usingApplicationLen, document.forms[0].usingApplication, 'textCountUsingApplication', <%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>);
    	RteTrackCount(document.forms[0].tipsAndTricksLen, document.forms[0].tipsAndTricks, 'textCountTipsAndTricks', <%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>);
    	RteTrackCount(document.forms[0].faqLen, document.forms[0].faq, 'textCountFaq', <%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>);
    	RteTrackCount(document.forms[0].troubleshootingLen, document.forms[0].troubleshooting, 'textCountTroubleshooting', <%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>);
    	RteTrackCount(document.forms[0].devCompanyDisclaimerLen, document.forms[0].devCompanyDisclaimer, 'textCountDevCompanyDisclaimer', <%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>);
    	RteTrackCount(document.forms[0].additionalInformationLen, document.forms[0].additionalInformation, 'textCountAdditionalInformation', <%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>);    	
	}
	
	function openRte(num){
        var url="/aims/brewUserGuideRte.do";
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
	<%@ include  file="include/appJScript.jsp" %>
</script>

<%@ include file="include/brewMessageHeader.jsp" %>

<div id="contentBox" style="float: left" onmousemove="truncateLocalTextAreas();">
	<div class="homeColumnTab">
		<%@ include  file="include/appTabs.jsp" %>
		<div class="contentbox">
			<html:form action="/brewApplicationUpdate.do"	enctype="multipart/form-data">
			<%@ include  file="include/appHidden.jsp" %>
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
								<th>
									<strong><bean:message key="BrewApplicationForm.companyLogo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong><br/>
									<html:file size="30" styleClass="inputField" property="companyLogo"/><br/>
									<logic:notEmpty	name="BrewApplicationUpdateForm" property="companyLogoFileName" scope="request">
										<logic:equal name="BrewApplicationUpdateForm" property="companyLogoTempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=CompanyLogo&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="BrewApplicationUpdateForm" property="companyLogoTempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="companyLogoTempFileId" />" class="a" target="_blank">
										</logic:notEqual>
												<bean:write	name="BrewApplicationUpdateForm" property="companyLogoFileName"/>
											</a>
									</logic:notEmpty>									
								</th>
							</tr>
							<tr>
								<td>
									<strong><bean:message key="ManageApplicationForm.appTitle"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
									<span class="requiredText">*</span>:</strong><br/>
									<html:text property="title" size="40" maxlength="200" styleClass="inputField"/>
								</td>
							</tr>
							<tr>
								<td>
									<strong><bean:message key="BrewApplicationForm.titleShot"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong><br/>
									<html:file size="30" styleClass="inputField" property="titleShot"/><br/>
									<logic:notEmpty	name="BrewApplicationUpdateForm" property="titleShotFileName" scope="request">
										<logic:equal name="BrewApplicationUpdateForm" property="titleShotTempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TitleShot&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="BrewApplicationUpdateForm" property="titleShotTempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="titleShotTempFileId" />" class="a" target="_blank">
										</logic:notEqual>
												<bean:write	name="BrewApplicationUpdateForm" property="titleShotFileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<strong><bean:message key="BrewApplicationForm.productDescription"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
									<html:textarea property="productDescription" rows="3" cols="50" styleClass="textareaField"
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
								<td>
								<html:file size="30" styleClass="inputField" property="screenJpeg"/><br/>
								<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpegFileName" scope="request">
									<logic:equal name="BrewApplicationUpdateForm" property="screenJpegTempFileId"	scope="request"	value="0">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
									</logic:equal>
									<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpegTempFileId" scope="request" value="0">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpegTempFileId" />" class="a" target="_blank">
									</logic:notEqual>
										<bean:write	name="BrewApplicationUpdateForm"	property="screenJpegFileName"/>
										</a>
								</logic:notEmpty>					
			                  </td>
			                </tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField" property="screenJpeg2"/><br/>
									<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpeg2FileName" scope="request">
										<logic:equal name="BrewApplicationUpdateForm" property="screenJpeg2TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpeg2TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg2TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg2FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField"  property="screenJpeg3"/><br/>
									<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpeg3FileName" scope="request">
										<logic:equal name="BrewApplicationUpdateForm" property="screenJpeg3TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpeg3TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg3TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg3FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField" property="screenJpeg4"/><br/>
									<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpeg4FileName" scope="request">
										<logic:equal name="BrewApplicationUpdateForm" property="screenJpeg4TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpeg4TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg4TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg4FileName"/>
											</a>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td>
									<html:file size="30" styleClass="inputField" property="screenJpeg5"/><br/>
									<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpeg5FileName" scope="request">
										<logic:equal name="BrewApplicationUpdateForm" property="screenJpeg5TempFileId"	scope="request"	value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
										</logic:equal>
										<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpeg5TempFileId" scope="request" value="0">
											<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg5TempFileId" />" class="a" target="_blank">
										</logic:notEqual>
											<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg5FileName"/>
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
									<html:radio	property="multiPlayer" value="Y"/><bean:message key="ManageApplicationForm.radio.label.yes" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
									<html:radio	property="multiPlayer" value="N"/><bean:message key="ManageApplicationForm.radio.label.no"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
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
								<td width="25%">
									<table border="0" cellpadding="0" cellspacing="0">
				                        <tr>
				                            <td style="vertical-align:middle;padding:0">
				                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				                            </td>
				                            <td>
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountUsingApplication" size="1" value="<%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
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
										<a onclick="openRte('2'); return false;" href="#"><bean:message key="BrewApplicationForm.tipsAndTricks"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>
									<table border="0" cellpadding="0" cellspacing="0">
				                        <tr>
				                            <td style="vertical-align:middle;padding:0">
				                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				                            </td>
				                            <td>
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountTipsAndTricks" size="1" value="<%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
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
										<a onclick="openRte('3'); return false;" href="#"><bean:message key="ManageApplicationForm.appFAQ"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>
									<table border="0" cellpadding="0" cellspacing="0">
				                        <tr>
				                            <td style="vertical-align:middle;padding:0">
				                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				                            </td>
				                            <td>
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountFaq" size="1" value="<%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
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
										<a onclick="openRte('4'); return false;" href="#"><bean:message key="BrewApplicationForm.troubleshooting" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>
									<table border="0" cellpadding="0" cellspacing="0">
				                        <tr>
				                            <td style="vertical-align:middle;padding:0">
				                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				                            </td>
				                            <td>
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountTroubleshooting" size="1" value="<%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
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
										<a onclick="openRte('5'); return false;" href="#"><bean:message key="BrewApplicationForm.devDisclaimer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>
									<table border="0" cellpadding="0" cellspacing="0">
				                        <tr>
				                            <td style="vertical-align:middle;padding:0">
				                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				                            </td>
				                            <td>
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountDevCompanyDisclaimer" size="1" value="<%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
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
										<a onclick="openRte('6'); return false;" href="#"><bean:message key="BrewApplicationForm.additionalInfo" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>&nbsp;<span class="requiredText">*</span>:
									</strong>
								</td>
								<td>
									<table border="0" cellpadding="0" cellspacing="0">
				                        <tr>
				                            <td style="vertical-align:middle;padding:0">
				                                <bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				                            </td>
				                            <td>
				                                &nbsp;<input class="inputFieldNoPad" type="text" name="textCountAdditionalInformation" size="1" value="<%=AimsConstants.BREW_USER_GUIDE_FIELD_LEN%>" disabled="true"/>
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
							<%@ include  file="include/appEditButtons.jsp" %>
						</table>
						<%@ include file="include/brewMessageFooter.jsp" %>
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