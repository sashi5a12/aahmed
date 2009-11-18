<tr><td	width="100%">&nbsp;</td></tr>
<tr><td	width="100%">
	<div id="divButtons">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" >				
					<div style="float:right">
						<div class="blackBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllCancel" title="Cancel">
							<div><div><div onClick="document.forms[0].action='<bean:message	key="ManageApplicationForm.manage.app.cancel.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';document.forms[0].submit();">Cancel</div></div></div>
						</div>
						
						<%if (isAllianceUser) {%>
							<%if (statusSaved) {%>
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="SaveDraft" title="Save As Draft">
									<div><div><div onClick="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM%>';submitForm();document.forms[0].submit();">Save As Draft</div></div></div>
								</div>							
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllSubmit" title="Submit">
									<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SUBMIT_FORM%>';javascript:if (isOKWithDevices()) { submitForm(); document.forms[0].submit();} else { return false; }">Submit</div></div></div>									
								</div>
							<%} %>
							<%if (!statusSaved) {%>
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="SaveAgain" title="Save">
									<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT%>';submitForm();document.forms[0].submit();">Save</div></div></div>
								</div>					
							<%} %>
						<% } else {}%>
												
						<%if (isVerizonUser) {%>
							<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllSave" title="Save">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Save</div></div></div>
							</div>
							<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllProcess" title="Process">
								<div><div><div onClick="javascript:document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_PROCESS_FORM%>';submitForm();document.forms[0].submit();">Process</div></div></div>
							</div>															
						<% } else {}%> 
						
					</div>
				</td>
			</tr>			
		</table>
	</div>		
</td></tr>