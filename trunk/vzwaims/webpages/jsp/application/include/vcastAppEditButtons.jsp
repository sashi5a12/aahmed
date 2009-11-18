<tr><td	width="100%">&nbsp;</td></tr>
<tr><td	width="100%">
	<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td style="text-align:right; vertical-align:middle"	>
			
				<div id="divButtons">
					<%if (isVerizonUser) {%>

                        <%if (statusRejected) {%>
							<%--<input type="image"	name="AllAccept" <bean:message key="images.accept.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();"/>--%>
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllAccept" title="Accept">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
							</div>
							
                        <% } %>

                        <%if (statusSubmitted) {%>
							<%--<span class="imagebutton"><a href="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_REJECT_FORM%>';submitForm();document.forms[0].submit();">NOT ACCEPTED</a></span>--%>
                            <%--<input type="image"	name="AllAccept" <bean:message key="images.accept.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();"/>--%>							
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllAccept" title="Accept">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
							</div>							
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="NotAccepted" title="Not Accepted">
								<div><div><div onClick="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_REJECT_FORM%>';submitForm();document.forms[0].submit();">Not Accepted</div></div></div>
							</div>
							
                        <% } %>						
						<%--<input type="image"	name="AllSave" <bean:message key="images.save.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();"/>--%>
						<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllSave" title="Save">
							<div><div><div onClick="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM%>';submitForm();document.forms[0].submit();">Save</div></div></div>
						</div>
						
					<% } else {}%>
					
                    <%if (isAllianceUser) {%>
                        <%if (statusSaved) {%>
                        	<%--<span class="imagebutton"><a href="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM%>';submitForm();document.forms[0].submit();">SAVE</a></span>
							<input type="image"	name="AllSubmit" <bean:message key="images.submit.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SUBMIT_FORM%>';submitForm();"/>--%>
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllSubmit" title="Submit">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SUBMIT_FORM%>';document.forms[0].submit();">Submit</div></div></div>
							</div>							
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllSave" title="Save">
								<div><div><div onClick="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM%>';submitForm();document.forms[0].submit();">Save</div></div></div>
							</div>
                        <% } else {%>	
							<%--<input type="image"	name="SaveAgain" <bean:message key="images.save.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT%>';submitForm();"/>--%>
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="SaveAgain" title="Save">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT%>';submitForm();document.forms[0].submit();">Save</div></div></div>
							</div>							
                        <% } %>
					<% } else {}%>										                    

   					<%--<input type="image"	name="AllCancel" <bean:message key="images.cancel.button.lite"/> onClick="document.forms[0].action='<bean:message	key="ManageApplicationForm.manage.app.cancel.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';"/>--%>
					<div class="blackBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllCancel" title="Cancel">
						<div><div><div onClick="document.forms[0].action='<bean:message	key="ManageApplicationForm.manage.app.cancel.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';document.forms[0].submit();">Cancel</div></div></div>
					</div>

					
                    <%if (isAllianceUser) {%>
                        <%if (statusSaved) {%>
                            <br/><br/>(Before you click the "Submit" button, please ensure you've filled in the required information on the "Additional Info" tab.)
                        <% } %>  
                    <% } else {}%>
                                        
				</div>	
			</td>
		</tr>
	</table>
</td></tr>		