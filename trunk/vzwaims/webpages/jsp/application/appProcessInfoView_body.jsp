<%@	page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm"	scope="request"	/>

<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/appViewTabs.jsp" %>
    <div>&nbsp;</div>
    <html:form action="<%=ApplicationUpdateForm.getUpdateURL()%>"	enctype="multipart/form-data">
      <div class="contentbox">
        <%@ include  file="include/appViewHidden.jsp" %>
        <%
            boolean statusSubmitted = false;
            boolean statusEvaluated = false;
            boolean statusAssigned = false;
            boolean statusUnderTest = false;
            boolean statusAccepted = false;
            boolean statusRejected = false;
            boolean statusSunset = false;
            boolean statusBrewRejected = false;
            boolean statusUnderReview = false;            
         %>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.SUBMISSION_ID.toString()%>">
            <%statusSubmitted = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.EVALUATED_ID.toString()%>">
            <%statusEvaluated = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.ASSIGNED_ID.toString()%>">
            <%statusAssigned = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.TESTING_ID.toString()%>">
            <%statusUnderTest = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.ACCEPTANCE_ID.toString()%>">
            <%statusAccepted = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.REJECTED_ID.toString()%>">
            <%statusRejected = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.SUNSET_ID.toString()%>">
            <%statusSunset = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.PHASE_UNDER_REVIEW.toString()%>">
            <%statusUnderReview = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_REJECTED.toString()%>">
            <%statusBrewRejected = true;%>
        </logic:equal>
        
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <% if ( statusSubmitted || statusEvaluated || statusAssigned ||  statusUnderTest ||  statusAccepted || statusRejected || statusSunset || statusUnderReview || statusBrewRejected) { %>
            
            <%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_ASSIGN, AimsSecurityManager.SELECT)) {%>
                    <tr>
                        <td><div class="lBox">
                            <DIV class="headLeftCurveblk"></DIV>
                            <H1><bean:message	key="ApplicationForm.table.head.assign"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                            <DIV class="headRightCurveblk"></DIV>
                        </div></td>
                    </tr>
                    <tr>
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                            <tr>
                                <th>
                                    <strong>
                                    <bean:message	key="ApplicationForm.vzwContact"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <logic:iterate id="formContacts"	name="ApplicationUpdateForm"	property="allVzwContacts"	scope="request">
                                        <logic:notEmpty name="ApplicationUpdateForm"	property="aimsVzwAppsContactId">
                                            <logic:equal name="formContacts"	property="contactId" value="<%=ApplicationUpdateForm.getAimsVzwAppsContactId().toString()%>">
                                                <bean:write	name="formContacts" property="firstName"	/>&nbsp;<bean:write	name="formContacts" property="lastName"	/>
                                            </logic:equal>
                                        </logic:notEmpty>
                                    </logic:iterate>
                                </th>
                                <th>
                                    <strong>
                                    <bean:message	key="ApplicationForm.productGroup"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <logic:iterate id="formProductGroups"	name="ApplicationUpdateForm"	property="allProductGroups"	scope="request">
                                        <logic:notEmpty name="ApplicationUpdateForm"	property="aimsProductGroupId">
                                            <logic:equal name="formProductGroups"	property="productGroupId" value="<%=ApplicationUpdateForm.getAimsProductGroupId().toString()%>">
                                                <bean:write	name="formProductGroups" property="productGroupTitle"	/>
                                            </logic:equal>
                                        </logic:notEmpty>
                                    </logic:iterate>
                                </th>
                            </tr>
                            <logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.ENTERPRISE_PLATFORM_ID.toString()%>" scope="request"	>
                                <tr>
                                    <td colspan="2">
                                        <strong><bean:message	key="EntApplicationForm.allianceSponsor"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="viewText">
                                        <logic:notEmpty name="EntApplicationUpdateForm"	property="allianceSponsor">
                                            <logic:iterate id="formBdsContacts"	name="EntApplicationUpdateForm"	property="allBdsContacts"	type="com.netpace.aims.model.core.BdsContact" scope="request">
                                                <logic:equal name="EntApplicationUpdateForm"	property="allianceSponsor" value="<%=formBdsContacts.getContactId().toString()%>">
                                                    <bean:write	name="formBdsContacts" property="firstName"	/>&nbsp;<bean:write	name="formBdsContacts" property="lastName"	/>
                                                </logic:equal>
                                            </logic:iterate>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                            </logic:equal>
                          </table></td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
              <% } else {}%>
        <% } else {}%>
         <logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>" scope="request"	>
         	<%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_ARCHIVED_USER_GUIDE, AimsSecurityManager.UPDATE)) {%>
         	<tr>
               <td><div class="lBox">
                   <DIV class="headLeftCurveblk"></DIV>
                   <H1><bean:message key="ApplicationForm.table.head.app.files" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                   <DIV class="headRightCurveblk"></DIV>
                 </div></td>
             </tr>
             <tr>
               <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                   <tr>
			<th><strong><bean:message key="BrewApplicationForm.archivedUserGuide" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
		  </tr>				
			<tr>
				<td>
					<html:file  size="30" styleClass="inputField" property="userGuide" disabled="true"/><br/>
					<logic:notEmpty	name="BrewApplicationUpdateForm"	property="userGuideFileName" scope="request">
						<logic:equal name="BrewApplicationUpdateForm" property="userGuideTempFileId"	scope="request"	value="0">
							<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuide&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
						</logic:equal>
						<logic:notEqual	name="BrewApplicationUpdateForm"	property="userGuideTempFileId" scope="request" value="0">
							<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="userGuideTempFileId" />" class="a" target="_blank">
						</logic:notEqual>
							<bean:write	name="BrewApplicationUpdateForm"	property="userGuideFileName"/>
							</a>
					</logic:notEmpty>
				</td>
			</tr>
               </table></td>
             </tr>
             <%} %>
         </logic:equal>        
        <% if ( statusAssigned ||  statusUnderTest ||  statusAccepted || statusRejected || statusSunset ) { %>
            <logic:notEqual name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>" scope="request"	>
                <%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_PRIORITIZATION, AimsSecurityManager.SELECT)) {%>
                  <tr>
                    <td><div class="lBox">
                        <DIV class="headLeftCurveblk"></DIV>
                        <H1><bean:message	key="ApplicationForm.table.head.prioritize"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                        <DIV class="headRightCurveblk"></DIV>
                      </div></td>
                  </tr>
                  <tr>
                  <td><table width="100%" border="1" cellspacing="0" cellpadding="0" class="GridGradient">
                      <tr>
                        <th width="50%">
                            <strong>
                            <bean:message	key="ApplicationForm.appPriority"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                            <br/>
                            <logic:equal name="ApplicationUpdateForm" property="appPriority"	value="H">
                                <bean:message	key="ManageApplicationForm.radio.label.high"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </logic:equal>
                            <logic:equal name="ApplicationUpdateForm" property="appPriority"	value="L">
                                <bean:message	key="ManageApplicationForm.radio.label.low"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </logic:equal>
                        </th>
                        <th width="50%">
                            <strong>
                            <bean:message	key="ApplicationForm.prodDate"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                            <br/>
                            <bean:write	name="ApplicationUpdateForm" property="targetedProductionDate"	/>
                        </th>
                      </tr>
                    </table></td>
                  </tr>
                  <tr><td>&nbsp;</td></tr>
                <% } else {}%>
            </logic:notEqual>
        <% } else {}%>
        <logic:notEqual name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>" scope="request"	>
        <% if ( statusUnderTest ||  statusAccepted || statusRejected || statusSunset ) { %>
            <%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_TESTING, AimsSecurityManager.SELECT)) {%>
              <tr>
                <td><div class="lBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1><bean:message key="ApplicationForm.table.head.testing"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                    <DIV class="headRightCurveblk"></DIV>
                  </div></td>
              </tr>
              <tr>
              <td><table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
                  <tr>
                    <th><bean:message	key="ApplicationForm.testingName"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th><bean:message	key="ApplicationForm.testingDate"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th><bean:message	key="ApplicationForm.testingStatus"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th><bean:message	key="ApplicationForm.testingFile"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                  </tr>
                  <logic:notEmpty name="<%=ManageApplicationsConstants.SESSION_VAR_PHASES%>" scope="session">
                    <logic:iterate id="certs2" name="<%=ManageApplicationsConstants.SESSION_VAR_PHASES%>" scope="session" indexId="ctr" >
                        <tr>
                            <td align="left">
                                <bean:write name="certs2" property="aimsVzwTestingPhase.testingPhaseName" />
                            </td>
                            <td align="left">
                                <bean:write	name="ApplicationUpdateForm" property='<%= "testedDate[" + ctr + "]" %>'	/>
                            </td>
                            <td align="left">
                                <logic:equal name="ApplicationUpdateForm" property='<%= "testStatus[" + ctr + "]" %>'	value="P">
                                        <bean:message	key="ManageApplicationForm.radio.label.passed"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    </logic:equal>
                                    <logic:equal name="ApplicationUpdateForm" property='<%= "testStatus[" + ctr + "]" %>'	value="F">
                                        <bean:message	key="ManageApplicationForm.radio.label.failed"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    </logic:equal>
                                </td>
                            <td>
                                <logic:notEmpty	name="ApplicationUpdateForm"	property='<%= "testResultFileName[" + ctr + "]" %>' scope="request">
                                    <logic:equal name="ApplicationUpdateForm" property='<%= "testResultFileId[" + ctr + "]" %>' scope="request"	value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=resultFile&app_id=<bean:write	name="ApplicationUpdateForm"	property="appsId"	/>&composite_id=<bean:write	name="certs2"	property="aimsVzwTestingPhase.testingPhaseId"	/>" class="a"	target="_blank">
                                    </logic:equal>
                                    <logic:notEqual	name="ApplicationUpdateForm"	property='<%= "testResultFileId[" + ctr + "]" %>' scope="request" value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="ApplicationUpdateForm"	property='<%= "testResultFileId[" + ctr + "]" %>'  />" class="a" target="_blank">
                                    </logic:notEqual>
                                        <bean:write	name="ApplicationUpdateForm"	property='<%= "testResultFileName[" + ctr + "]" %>' />
                                    </a>
                                </logic:notEmpty>
                            </td>
                        </tr>
                        <tr>
                            <td align="left">&nbsp;</td>
                            <td colspan="3" align="left">
                                <html:textarea styleClass="textareaField" property='<%= "testComments[" + ctr + "]" %>' readonly="true" rows="4" cols="55"></html:textarea>
                            </td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
                </table></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
            <% } else {}%>
        <% } else {}%>
        </logic:notEqual>        
        <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <%@ include  file="include/appViewButtons.jsp" %>
              </table></td>
        </tr>
        </table>
      </div>
    </html:form>
  </div>
</div>
