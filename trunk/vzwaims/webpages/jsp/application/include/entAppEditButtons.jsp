<%@page import="com.netpace.aims.util.AimsConstants"%>
<%@page import="com.netpace.aims.bo.security.AimsSecurityManager"%><tr><td	width="100%">&nbsp;</td></tr>
<tr><td	width="100%">
	<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
		<tr>
			<td style="text-align:right; vertical-align:middle"	>
				
				<div id="divButtons">
																								
					<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
						<%if(isEnterpriseApp){ %>
						<logic:notEmpty name="EntApplicationUpdateForm" scope="request">
							<%-- Display accept/reject button on pageEntBOBO --%>
							<logic:equal name="EntApplicationUpdateForm" property="currentPage" scope="request" value="pageEntBOBO">
								<logic:equal name="EntApplicationUpdateForm" property="isBoboAccept" scope="request" value="">
									<div class="redBtn" style=" margin-left:0px;float:left; margin-top:3px" id="RejectBobo" title="RejectBobo">
									<div><div><div onClick="if(confirm('<bean:message key="JMAApp.alert.rejectBOBO"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>')) {submitForm();document.forms[0].isBoboAccept.value='N';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_VZW_REJECT_BOBO %>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();}">Reject BOBO</div></div></div>
									</div>
									
									<div class="redBtn" style=" margin-left:10px;float:left; margin-top:3px" id="AcceptBobo" title="AcceptBobo">
									<div><div><div onClick="submitForm();document.forms[0].isBoboAccept.value='Y';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_VZW_ACCEPT_BOBO %>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Accept BOBO</div></div></div>
									</div>
								</logic:equal>
								
								<logic:equal name="EntApplicationUpdateForm" property="isBoboAccept" scope="request" value="Y">
									<div class="redBtn" style=" margin-left:0px;float:left; margin-top:3px" id="RevokeBobo" title="RevokeBobo">
									<div><div><div onClick="if(confirm('<bean:message key="JMAApp.alert.revokeBOBO"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>')) {submitForm();document.forms[0].isBoboAccept.value='N';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_VZW_REJECT_BOBO %>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();}">Revoke BOBO</div></div></div>
									</div>
								</logic:equal>
								
								<logic:equal name="EntApplicationUpdateForm" property="isBoboAccept" scope="request" value="N">
									<div class="redBtn" style=" margin-left:0px;float:left; margin-top:3px" id="AcceptBobo" title="AcceptBobo">
									<div><div><div onClick="submitForm();document.forms[0].isBoboAccept.value='Y';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_VZW_ACCEPT_BOBO %>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Accept BOBO</div></div></div>
									</div>
								</logic:equal>
							</logic:equal>
							
							<%-- Display accept/reject button on pageEntLBS --%>
							<logic:equal name="EntApplicationUpdateForm" property="currentPage" scope="request" value="pageEntLBS">
								<logic:equal name="EntApplicationUpdateForm" property="isLbsAccept" scope="request" value="">
									<div class="redBtn" style=" margin-left:0px;float:left; margin-top:3px" id="RejectLbs" title="RejectLbs">
									<div><div><div onClick="if(confirm('<bean:message key="JMAApp.alert.rejectLBS"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>')) {submitForm();document.forms[0].isLbsAccept.value='N';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_VZW_REJECT_LBS %>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();}">Reject LBS</div></div></div>
									</div>
									
									<div class="redBtn" style=" margin-left:10px;float:left; margin-top:3px" id="AcceptLbs" title="AcceptLbs">
									<div><div><div onClick="submitForm();document.forms[0].isLbsAccept.value='Y';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_VZW_ACCEPT_LBS %>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Accept LBS</div></div></div>
									</div>
								</logic:equal>
								
								<logic:equal name="EntApplicationUpdateForm" property="isLbsAccept" scope="request" value="Y">
									<div class="redBtn" style=" margin-left:0px;float:left; margin-top:3px" id="RevokeBobo" title="RevokeLbs">
									<div><div><div onClick="if(confirm('<bean:message key="JMAApp.alert.revokeLBS"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>')) {submitForm();document.forms[0].isLbsAccept.value='N';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_VZW_REJECT_LBS %>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();}">Revoke LBS</div></div></div>
									</div>
								</logic:equal>
								<logic:equal name="EntApplicationUpdateForm" property="isLbsAccept" scope="request" value="N">
									<div class="redBtn" style=" margin-left:0px;float:left; margin-top:3px" id="AcceptLbs" title="AcceptLbs">
									<div><div><div onClick="submitForm();document.forms[0].isLbsAccept.value='Y';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_VZW_ACCEPT_LBS %>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Accept LBS</div></div></div>
									</div>
								</logic:equal>
							</logic:equal>
						</logic:notEmpty>
						<br/>
						
						<%	if (AimsSecurityManager.checkAccess(request, "ACCEPT_JMA_PRODUCT", AimsSecurityManager.UPDATE)) { %>
					
						<logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.REJECTED_ID.toString()%>">
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllAccept" title="Accept">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
							</div>
						</logic:equal>
						<logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.ACCEPTANCE_ID.toString()%>">
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllReject" title="Reject">
								<div><div><div onClick="if(confirm('<bean:message key="JMAApp.alert.rejectAppliation"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>')) {document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_REJECT_FORM%>';submitForm();document.forms[0].submit();}">Reject</div></div></div>
							</div>
						</logic:equal>
						
						<%--For old Enterprise application --%>
						<logic:notEqual name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.REJECTED_ID.toString()%>">
							<logic:notEqual name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.ACCEPTANCE_ID.toString()%>">
								<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllAccept" title="Accept">
									<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
								</div>
								<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllReject" title="Reject">
									<div><div><div onClick="if(confirm('<bean:message key="JMAApp.alert.rejectAppliation"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>')) {document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_REJECT_FORM%>';submitForm();document.forms[0].submit();}">Reject</div></div></div>
								</div>
							</logic:notEqual>
						</logic:notEqual>
					<% } %>	
												
						<%--<input type="image"	name="AllSave" <bean:message key="images.save.button.lite"/> onClick="submitForm();document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();"/>--%>
						<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="SaveAgain" title="Save">
								<div><div><div onClick="submitForm();document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Save</div></div></div>
						</div>
						
						
						
						
						
						
						<%}else{ %>
							<logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.REJECTED_ID.toString()%>">
							<%--<input type="image"	name="AllAccept" <bean:message key="images.accept.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();"/>--%>
							<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllAccept" title="Accept">
								<div><div><div onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();document.forms[0].submit();">Accept</div></div></div>
							</div>
						</logic:equal>
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
						
						<%} %>
						
							
					<% } else {}%>
					
					<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE) ) {%>
						
						<logic:notEmpty name="EntApplicationUpdateForm" scope="request">
							<%-- Display accept/reject button on pageEntLBS --%>
							<logic:equal name="EntApplicationUpdateForm" property="currentPage" scope="request" value="pageEntLBS">
								<logic:equal name="EntApplicationUpdateForm" property="isLbsAccept" scope="request" value="Y">
									<logic:empty name="EntApplicationUpdateForm" property="isLbsAcceptByAlliance" scope="request" >
										<div class="redBtn" style=" margin-left:0px;float:left; margin-top:3px" id="RejectLbs" title="RejectLbs">
										<div><div><div onClick="if(confirm('<bean:message key="JMAApp.alert.rejectLBS"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>')) {submitForm();document.forms[0].isLbsAcceptByAlliance.value='N';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_USER_REJECT_LBS%>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();}">Reject LBS Contract</div></div></div>
										</div>
										
										<div class="redBtn" style=" margin-left:10px;float:left; margin-top:3px" id="AcceptLbs" title="AcceptLbs">
										<div><div><div onClick="submitForm();document.forms[0].isLbsAcceptByAlliance.value='Y';document.forms[0].acceptDeclinBoboLbs.value='<%=AimsConstants.AIMS_USER_ACCEPT_LBS%>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Accept LBS Contract</div></div></div>
										</div>
									</logic:empty>
								</logic:equal>
							</logic:equal>
						</logic:notEmpty>	
						<br/>
						
						<logic:notEmpty name="EntApplicationUpdateForm" scope="request">
							<%-- Display resubmit Button alogn with text box --%>
							<logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.REJECTED_ID.toString()%>">
								
								<div>
									<html:textarea styleClass="textareaField" name="industryVertical" property="resubmitSolutionText" rows="3" value="Resubmit message" cols="50" disabled="false"/>
								</div>
								<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Resubmit" title="Resubmit">
									<div><div><div onClick="submitForm();document.forms[0].resubmitSolution.value='<%=AimsConstants.AIMS_USER_RESUBMIT_SOLUTION%>';document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();document.forms[0].submit();">Resubmit</div></div></div>
								</div>
							</logic:equal>
						</logic:notEmpty>
						
						
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