<tr><td	width="100%">&nbsp;</td></tr>
<tr><td	width="100%">
	<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
		<tr>
			<td style="text-align:right; vertical-align:middle"	>
				
				<div id="divButtons">
																								
					<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
						
						<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>" scope="request"	>
							<logic:present parameter="zonAutoBeforeLive">
								<logic:match value="true" parameter="zonAutoBeforeLive" scope="request">	
									<logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.REJECTED_ID.toString()%>">
										<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllAccept" title="Accept">
											<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
										</div>
									</logic:equal>
								</logic:match>
							</logic:present>
						</logic:equal>
						<logic:notEqual name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>" scope="request"	>
							<logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.REJECTED_ID.toString()%>">
								<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllAccept" title="Accept">
									<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
								</div>
							</logic:equal>
						</logic:notEqual>
						<logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.TESTING_ID.toString()%>">
							<%--<input type="image"	name="AllReject" <bean:message key="images.reject.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_REJECT_FORM%>';submitForm();"/>
							<input type="image"	name="AllAccept" <bean:message key="images.accept.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();"/>--%>
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllReject" title="Reject">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_REJECT_FORM%>';submitForm();document.forms[0].submit();">Reject</div></div></div>
							</div>
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllAccept" title="Accept">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
							</div>
						</logic:equal>						
						<%--<input type="image"	name="AllSave" <bean:message key="images.save.button.lite"/> onClick="submitForm();document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();"/>--%>
						<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="SaveAgain" title="Save">
								<div><div><div onClick="submitForm();document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Save</div></div></div>
						</div>	
							
					<% } else {}%>
					
					<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE) ) {%>
						<logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.SAVED_ID.toString()%>">
							<%--<input type="image"	name="AllSave" <bean:message key="images.save.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM%>';submitForm();"/>
							<input type="image"	name="AllSubmit" <bean:message key="images.submit.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SUBMIT_FORM%>';javascript:if (isOKWithDevices()) { submitForm(); } else { return false; }"/>--%>
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllSubmit" title="Submit">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SUBMIT_FORM%>';javascript:if (isOKWithDevices()) { submitForm(); document.forms[0].submit();} else { return false; }">Submit</div></div></div>
							</div>
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllSave" title="Save">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM%>';submitForm();document.forms[0].submit();">Save</div></div></div>
							</div>							
						</logic:equal>	
						<logic:notEqual name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.SAVED_ID.toString()%>">
							<%--<input type="image"	name="SaveAgain" <bean:message key="images.save.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT%>';submitForm();"/>--%>
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="SaveAgain" title="Save">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT%>';submitForm();document.forms[0].submit();">Save</div></div></div>
							</div>
						</logic:notEqual>	
					<% } else {}%>					
					<%--<input type="image"	name="AllCancel" <bean:message key="images.cancel.button.lite"/> onClick="document.forms[0].action='<bean:message	key="ManageApplicationForm.manage.app.cancel.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';"/>--%>
					
					<div class="blackBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllCancel" title="Cancel">
						<div><div><div onclick="document.forms[0].action='<bean:message	key="ManageApplicationForm.manage.app.cancel.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';document.forms[0].submit();">Cancel</div></div></div>
					</div>

				</div>	
			</td>
		</tr>
	</table>
</td></tr>		