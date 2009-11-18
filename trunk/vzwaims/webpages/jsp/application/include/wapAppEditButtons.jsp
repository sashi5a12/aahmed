<tr><td	width="100%">&nbsp;</td></tr>
<tr><td	width="100%">
	<div id="divButtons">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" >				
					<div style="float:right">
						<%--<input type="image"	name="AllCancel" <bean:message key="images.cancel.button.lite"/> onClick="document.forms[0].action='<bean:message	key="ManageApplicationForm.manage.app.cancel.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';"/>--%>
						<div class="blackBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllCancel" title="Cancel">
							<div><div><div onClick="document.forms[0].action='<bean:message	key="ManageApplicationForm.manage.app.cancel.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';document.forms[0].submit();">Cancel</div></div></div>
						</div>
						
						<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE) ) {%>
							<logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.SAVED_ID.toString()%>">
								<%--<span class="imagebutton"><a href="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM%>';submitForm();document.forms[0].submit();">SAVE AS DRAFT</a></span>--%>
								<%--<input type="image"	name="AllSubmit" <bean:message key="images.submit.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SUBMIT_FORM%>';submitForm();"/>--%>
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="SaveDraft" title="Save As Draft">
									<div><div><div onClick="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM%>';submitForm();document.forms[0].submit();">Save As Draft</div></div></div>
								</div>							
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllSubmit" title="Submit">
									<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SUBMIT_FORM%>';submitForm();document.forms[0].submit();">Submit</div></div></div>
								</div>							                            
							</logic:equal>	
							<logic:notEqual name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.SAVED_ID.toString()%>">
								<%--<input type="image"	name="SaveAgain" <bean:message key="images.save.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT%>';submitForm();"/>--%>
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="SaveAgain" title="Save">
									<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT%>';submitForm();document.forms[0].submit();">Save</div></div></div>
								</div>							
							</logic:notEqual>	
						<% } else {}%>
						
						
						<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
							<%--<input type="image"	name="AllSave" <bean:message key="images.save.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();"/>--%>
							<%--<span class="imagebutton"><a href="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_PROCESS_FORM%>';submitForm();document.forms[0].submit();">PROCESS</a></span>--%>
							<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllSave" title="Save">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Save</div></div></div>
							</div>							
							<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllProcess" title="Process">
								<div><div><div onClick="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_PROCESS_FORM%>';submitForm();document.forms[0].submit();">Process</div></div></div>
							</div>							
								
							<logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.TESTING_ID.toString()%>">
								<%--<input type="image"	name="AllReject" <bean:message key="images.reject.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_REJECT_FORM%>';submitForm();"/>--%>							
								<%--<input type="image"	name="AllAccept" <bean:message key="images.accept.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();"/>--%>
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllReject" title="Reject">
									<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_REJECT_FORM%>';submitForm();document.forms[0].submit();">Reject</div></div></div>
								</div>							
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllAccept" title="Accept">
									<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
								</div>							
															
							</logic:equal>						
							<logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.REJECTED_ID.toString()%>">						
								<%--<input type="image"	name="AllAccept" <bean:message key="images.accept.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();"/>--%>
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllAccept" title="Accept">
									<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
								</div>							
							</logic:equal>
						<% } else {}%> 
					</div>                                                           
				</td>
			</tr>
			<tr>
				<td style="text-align:right">
				<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE) ) {%>
					<logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"    value="<%=AimsConstants.SAVED_ID.toString()%>">
						(Before you click the "Submit" button, please ensure you've filled in the required information on the "Additional Info" tab.)
					</logic:equal>  
				<% } else {}%>
				</td>						
			</tr>
		</table>
	</div>		
</td></tr>		