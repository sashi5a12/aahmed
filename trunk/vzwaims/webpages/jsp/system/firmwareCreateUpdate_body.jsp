<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script language="javascript">
	function create(){		
		document.forms[0].task.value="create";
		document.forms[0].submit();
	}
	function sendMailToAlliance(){
		document.forms[0].task.value="sendMail";
		document.forms[0].submit();				
	}
	function update(){
		document.forms[0].task.value="edit";
		document.forms[0].submit();
	}
	function openPopup(){
		if (trim(document.forms[0].firmwareName.value).length >0){
			var url="/aims/system/firmwarePopup.jsp";
        	var wind=window.open (url,"confirm","resizable=0,width=550,height=240,scrollbars=0,screenX=380,left=380,screenY=240,top=240");        
        	wind.focus();
        }
        else {
        	create();
        }
	}
</script>

<%@ include file="/common/error.jsp"%>
<DIV class="contentbox">
	<html:form action="/firmwareCreateUpdate">
		<html:hidden property="task"/>
		<html:hidden property="firmwareId" />		
		<html:hidden property="sendMail" />		
		<DIV class="homeColumnTab lBox">
			<DIV class="headLeftCurveblk"></DIV>
			<H1>
				<c:choose>
					<c:when test="${requestScope.FirmwareForm.task eq 'create'}">
						Create Device Firmware
					</c:when>
					<c:otherwise>
						Update Device Firmware
					</c:otherwise>
				</c:choose>
			</H1>
			<DIV class="headRightCurveblk"></DIV>

			<DIV class="contentbox">
				<table width="100%" cellspacing="0" cellpadding="2" class="GridGradient" border="0">
					<tr>
						<td width="25%" class="gradient"><strong>Phone Model <span class="requiredText">*</span>:</strong></td>
						<td class="gradient">
							<html:select property="phoneModel" styleClass="selectField">
                      			<html:options collection="devicesList" property="deviceId" labelProperty="deviceModel"></html:options>
                    		</html:select>
						</td>
					</tr>					
					<tr>
						<td><strong>Firmware Name <span class="requiredText">*</span>:</strong></td>
						<td><html:text property="firmwareName" size="50" maxlength="250" styleClass="inputField" /></td>
					</tr>									
					<tr>
						<td><strong>Maintenance Release Number <span class="requiredText">*</span>:</strong></td>
						<td>
							<html:select property="mrNumber" styleClass="selectField">
								<html:option value="0">0</html:option>
								<html:option value="1">1</html:option>
								<html:option value="2">2</html:option>
								<html:option value="3">3</html:option>
								<html:option value="4">4</html:option>
								<html:option value="5">5</html:option>
								<html:option value="6">6</html:option>
								<html:option value="7">7</html:option>
								<html:option value="8">8</html:option>
								<html:option value="9">9</html:option>
							</html:select>
						</td>
					</tr>									
					<tr>
						<td><strong>Status <span class="requiredText">*</span>:</strong></td>
						<td><html:select property="status" styleClass="selectField">
								<html:option value="Active">Active</html:option>
								<html:option value="Inactive">Inactive</html:option>
							</html:select></td>
					</tr>									
					<tr>
						<td><strong>Description:</strong></td>
						<td><html:textarea property="description" rows="5" cols="60" styleClass="textareaField" /></td>
					</tr>									
				</table>

				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<c:choose>
								<c:when test="${requestScope.FirmwareForm.task eq 'create'}">
									<div class="redBtn" style="margin-left: 10px; float: right; margin-top: 3px" title="Create">
										<div>
											<div><div onClick="openPopup()">Create</div></div>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="redBtn" style="margin-left: 10px; float: right; margin-top: 3px" title="Save">
										<div>
											<div><div onClick="update()">Save</div></div>
										</div>
									</div>
									
									<div class="redBtn" style="margin-left: 10px; float: right; margin-top: 3px" title="Send Email To Alliance">
										<div>
											<div><div onClick="sendMailToAlliance()">Send Email To Alliance</div></div>
										</div>
									</div>								
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>

			</DIV>
		</DIV>

	</html:form>
</DIV>
