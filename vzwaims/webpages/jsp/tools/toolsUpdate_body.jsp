<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:useBean id="task" class="java.lang.String" scope="request" />
<script type="text/javascript">
<!--
function contracts(elem,hide) {
	 len = elem.length;
	 for (var j = len - 1; j >= 0; j--) {
		 elem.options[j] = null;
	 }
	 opt_new = new Option('-------- None Selected ----------','-1');
	 elem.options[0] = opt_new;
	 document.getElementById('tbl_contracts').style.visibility=(hide)?"hidden":"visible";
}
function platforms(elem,disable){	
	for(i=0; i<elem.length; i++){
		elem[i].checked=false;
		elem[i].disabled=(disable)?true:false;
	}	
}
function enableDisableShareType(type){
	if (type=="1"){
		contracts(document.ToolsForm.contractList,true);
		platforms(document.ToolsForm.platformIds,true);
	}
	else if (type=="2"){
		contracts(document.ToolsForm.contractList,true);
		//platforms(document.ToolsForm.platformIds,false);
		for(i=0; i<document.ToolsForm.platformIds.length; i++){
			document.ToolsForm.platformIds[i].disabled=false;
		}		
	}
	else if (type=="3"){
		platforms(document.ToolsForm.platformIds,true);
		//contracts(document.ToolsForm.contractList,false);
		document.getElementById('tbl_contracts').style.visibility="visible";
	}
}
function selectShareType(type){
	if (type=="1"){
		document.ToolsForm.shareType[0].checked=true;
	}
	else if (type=="2"){
		document.ToolsForm.shareType[1].checked=true;
	}
	else if (type=="3"){
		document.ToolsForm.shareType[2].checked=true;
	}
}
function enableDisableDocType(type){
	if (type=="1"){
		document.ToolsForm.downloadURL.disabled=true;
		document.ToolsForm.toolFile.disabled=false;
	}
	else if (type=="2"){		
		document.ToolsForm.downloadURL.disabled=false;
		document.ToolsForm.toolFile.disabled=true;
		//document.getElementById('fileNameLoc').innerHTML="&nbsp;";
		document.getElementById('fileNameLoc').style.visibility="hidden";
		document.ToolsForm.displayFilenameId.value=0;
	}
}

//-->
</script>
<%@ include file="/common/error.jsp"%>
<DIV class="contentbox">
	<html:form action="/toolUpdate" enctype="multipart/form-data" onsubmit="statusPage();">
		<DIV class="homeColumnTab lBox">
			<DIV class="headLeftCurveblk"></DIV>
			<H1>
				<logic:match name="ToolsForm" property="taskPerform" scope="request" value="create">
					<bean:message key="ToolsForm.welcomeCreate" />
					<html:hidden property="taskPerform" value="create" />
				</logic:match>
				<logic:match name="ToolsForm" property="taskPerform" scope="request" value="update">
					<bean:message key="ToolsForm.welcomeUpdate" />
					<html:hidden property="taskPerform" value="update" />
				</logic:match>
				<html:hidden property="toolId" />
				<html:hidden property="displayFilenameId" />
			</H1>
			<DIV class="headRightCurveblk"></DIV>

			<DIV class="contentbox">
				<table width="100%" cellspacing="0" cellpadding="3" class="GridGradient" border="0" bordercolor="black" style="border: 0px solid">
					<tr>
						<th>
							<strong><bean:message key="ToolsForm.toolName" />&nbsp;<span class="requiredText">*</span>:</strong>
						</th>
						<th valign="top">
							<html:text property="toolName" size="50" maxlength="50" styleClass="inputField" />
						</th>
					</tr>
					<tr>
						<td>
							<strong><bean:message key="ToolsForm.toolDescription" />&nbsp;<span class="requiredText">*</span>:</strong>
						</td>
						<td valign="top">
							<html:textarea property="description" rows="7" cols="50" />
						</td>
					</tr>
					<tr>
						<td>
							<html:radio property="docType" value="1" onclick="enableDisableDocType('1')" />&nbsp;<strong><bean:message key="ToolsForm.filename" />&nbsp;<span class="requiredText">*</span>:</strong>
						</td>
						<td valign="top">
							<html:file property="toolFile" styleClass="inputField" size="35" />
							<html:hidden property="displayFilename"/>
							<span id="fileNameLoc">
							<logic:notEmpty	name="ToolsForm"	property="displayFilename" scope="request">								
								<br />
								<logic:equal name="ToolsForm" property="displayFilenameId"	scope="request"	value="0">
								<a href="toolsResource.do?toolResource=toolFile&toolId=<bean:write name="ToolsForm" property="toolId" />"
									class="a" target="_blank">
								</logic:equal>
								<logic:notEqual	name="ToolsForm" property="displayFilenameId" scope="request" value="0">
									<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="ToolsForm"	property="displayFilenameId" />" class="a" target="_blank">
								</logic:notEqual>
										<bean:write	name="ToolsForm"	property="displayFilename"/>
									</a>
							</logic:notEmpty>
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding-bottom: 0px; padding-top: 0px"><strong>&nbsp;&nbsp;&nbsp;--OR--</strong> </td>
					</tr>
					<tr>
						<td>
							<html:radio property="docType" value="2" onclick="enableDisableDocType('2')" />&nbsp;<strong><bean:message key="ToolsForm.url" /><span class="requiredText">*</span>:</strong>
						</td>
						<td valign="top">
							<html:text property="downloadURL" size="35" maxlength="50" styleClass="inputField" />
						</td>
					</tr>
					<tr>
						<td>
							<strong><bean:message key="ToolsForm.category" />&nbsp;<span class="requiredText">*</span>:</strong>
						</td>
						<td valign="top">
							<table width="100%" cellspacing="0" cellpadding="0" border="0" bordercolor="black">
								<tr>
								<c:forEach items="${ToolsForm.allAppCategories}" var="category" varStatus="status">
									<td width="50%" nowrap="nowrap" style="padding: 0px">
										<html:multibox property="categoriesIds">
											<c:out value="${category.categoryId}"/>
										</html:multibox>
											<c:out value="${category.categoryName}"/>
									</td>
									<c:if test="${status.count mod 2 eq 0}">
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<strong>Please select one&nbsp;<span class="requiredText">*</span>:</strong>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 0px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" bordercolor="black" style="border: 0px solid">
								<tr>
									<td width="3%" style="padding: 5px"><html:radio property="shareType" value="1" onclick="enableDisableShareType('1')" /></td>								
									<td style="padding-left: 0px"><strong>Share with all alliances regardless of platform(s) or contract(s)</strong></td>								
								</tr>							
								<tr>
									<td width="3%" style="padding: 5px"><html:radio property="shareType" value="2" onclick="enableDisableShareType('2')" /></td>								
									<td style="padding-left: 0px"><strong>Share with all alliances with any accepted contract for the following platform(s)</strong></td>								
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td style="padding: 0px">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" bordercolor="black">
											<tr>
												<td style="vertical-align: top; padding: 0px" width="25%">
													<strong>Platform Supported&nbsp;<span class="requiredText">*</span>:</strong>
												</td>
												<td style="padding: 0px">
													<table width="75%" cellspacing="0" cellpadding="0" border="0" bordercolor="black">
														<tr>
														<c:forEach items="${ToolsForm.allPlatforms}" var="platform" varStatus="status">
															<td nowrap="nowrap" style="padding: 0px">
																<html:multibox property="platformIds" onclick="">
																	<c:out value="${platform.platformId}"/>
																</html:multibox>
																	<c:out value="${platform.platformName}"/>
															</td>
															<c:if test="${status.count mod 3 eq 0}">
																</tr>
															</c:if>
														</c:forEach>
													</table>	
												</td>
											</tr>
										</table>										
									</td>								
								</tr>
								<tr>
									<td width="3%" style="padding: 5px"><html:radio property="shareType" value="3" onclick="enableDisableShareType('3')" /></td>								
									<td style="padding-left: 0px"><strong>Share with all alliances for any of the following accepted contract(s)</strong></td>								
								</tr>							
								<tr>
									<td colspan="2" style="padding: 0px">
										<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left" bordercolor="black" style="border: 0px solid">
											<tr>
												<td width="4%" style="padding: 5px">&nbsp;</td>
												<td align="left" style="padding: 0px" width="40%">
													<strong><bean:message key="ToolsForm.contractAvailable" /></strong>
													<br />
													<html:select property="allContracts" size="10" multiple="true" styleClass="selectField">
														<logic:iterate id="contract" name="ToolsForm" property="allContracts" type="com.netpace.aims.model.core.AimsContract">
															<html:option value="<%=contract.getContractId().toString()%>">
																<bean:write name="contract" property="contractTitle" />
															</html:option>
															<BR />
														</logic:iterate>
													</html:select>
												</td>
												<td width="50">
													<table cellpadding="0" cellspacing="0" border="0" height="100" id="tbl_contracts" >
														<tr>
															<td style="vertical-align: middle; text-align: center;">
																<img src="images/greyRndArrow.gif" border="0" onClick="copyToList('allContracts', 'contractList')" />
															</td>
														</tr>
														<tr style="vertical-align: middle; text-align: center;">
															<td>
																<img src="images/greyRndArrowL.gif" border="0" onClick="removeAndCopy(document.ToolsForm.contractList , document.ToolsForm.allContracts, '1')" />
															</td>
														</tr>
													</table>
												</td>
												<td align="left" style="padding: 0px">
													<strong>Contracts Accepted</strong>
													<br />
													<html:select name="ToolsForm" property="contractList" size="10" multiple="true" styleClass="selectField">
														<logic:notEmpty name="ToolsForm" property="addedContracts" scope="request">
															<logic:iterate id="contract" name="ToolsForm" property="addedContracts" type="com.netpace.aims.model.core.AimsContract">
																<html:option value="<%=contract.getContractId().toString()%>">
																	<bean:write name="contract" property="contractTitle" />
																</html:option>
																<BR />
															</logic:iterate>
														</logic:notEmpty>
													</html:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>	
							</table>
						</td>
					</tr>
				</table>

				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<div class="redBtn"
								style="margin-left: 10px; float: right; margin-top: 3px"
								id="Save" title="Save">
								<div>
									<div><div onClick="select_all(document.ToolsForm.contractList);document.forms[0].submit();">Save</div></div>
								</div>
							</div>

							<div class="blackBtn"
								style="margin-left: 10px; float: right; margin-top: 3px"
								id="Back" title="Back">
								<div>
									<div><div onClick="window.location='toolSetup.do'">Back</div></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</DIV>
		</DIV>

	</html:form>
</DIV>
<script>
enableDisableShareType('<bean:write name="ToolsForm" property="shareType"/>');
enableDisableDocType('<bean:write name="ToolsForm" property="docType"/>')
</script>