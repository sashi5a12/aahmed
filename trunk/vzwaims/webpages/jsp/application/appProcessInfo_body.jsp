<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%ApplicationUpdateForm.setCurrentPage("page3");%>


<script	language="javascript">
	<%@ include  file="include/appJScript.jsp" %>
	function callToggleDatePicker(eltName,formElt)
	{
		toggleDatePicker(eltName, document.forms[0].name + '.' + formElt);
	}
	
	function truncateLocalTextAreas()
	{
		if (typeof document.forms[0].anticipatedDaps != "undefined")
			if (typeof document.forms[0].anticipatedDaps.value != "undefined")
				TruncateText(document.forms[0].anticipatedDaps,500);

        for (var i=0; i<document.forms[0].elements.length; i++)
		{
		    if (document.forms[0].elements[i].name.indexOf("testComments") != -1)
                if (document.forms[0].elements[i].type.indexOf("textarea") != -1)
                    TruncateText(document.forms[0].elements[i],200);
        }
	}
	
</script>

<%System.gc();%>
<logic:present name="BrewApplicationUpdateForm">
	<%@ include file="include/brewMessageHeader.jsp" %>
</logic:present>
<logic:notPresent name="BrewApplicationUpdateForm">
	<%@ include  file="/common/error.jsp" %>
</logic:notPresent> 


<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/appTabs.jsp" %>
    <div>&nbsp;</div>
    <html:form action="<%=ApplicationUpdateForm.getUpdateURL()%>"	enctype="multipart/form-data">
      <div class="contentbox">
        <%@ include  file="include/appHidden.jsp" %>
        <%
            boolean statusSubmitted = false;
            boolean statusEvaluated = false;
            boolean statusUnderReview = false;
            boolean statusAssigned = false;
            boolean statusUnderTest = false;
            boolean statusAccepted = false;
            boolean statusRejected = false;
            boolean statusSunset = false;
            boolean statusConceptAccepted = false;
            boolean statusConceptRejected = false;
            boolean statusBrewRejected = false;
         %>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.SUBMISSION_ID.toString()%>">
            <%statusSubmitted = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.EVALUATED_ID.toString()%>">
            <%statusEvaluated = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.PHASE_UNDER_REVIEW.toString()%>">
            <%statusUnderReview = true;%>
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
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.CONCEPT_ACCEPTED_ID.toString()%>">
            <%statusConceptAccepted = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.CONCEPT_REJECTED_ID.toString()%>">
            <%statusConceptRejected = true;%>
        </logic:equal>
        <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_REJECTED.toString()%>">
            <%statusBrewRejected = true;%>
        </logic:equal>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">            								
            <% if ( statusConceptAccepted || statusConceptRejected || statusSubmitted || statusEvaluated || statusUnderReview || statusAssigned ||  statusUnderTest ||  statusAccepted || statusRejected || statusSunset || statusBrewRejected) { %>                
                <%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_ASSIGN, AimsSecurityManager.UPDATE)) {%>
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
                                    <strong><bean:message	key="ApplicationForm.vzwContact"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <html:select styleClass="selectField" property="aimsVzwAppsContactId"	size="1" >
                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                        <logic:iterate id="formContacts" name="ApplicationUpdateForm"	property="allVzwContacts" type="com.netpace.aims.model.core.AimsContact"	scope="request">
                                            <html:option value="<%=formContacts.getContactId().toString()%>"><%=formContacts.getFirstName()%>	<%=formContacts.getLastName()%></html:option>
                                        </logic:iterate>
                                    </html:select>
                                </th>
                                <th>
                                    <strong><bean:message	key="ApplicationForm.productGroup"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <html:select styleClass="selectField" property="aimsProductGroupId"	size="1" >
                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                        <html:optionsCollection	property="allProductGroups" label="productGroupTitle"	 value="productGroupId"/>
                                    </html:select>
                                </th>
                            </tr>
                            <logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.ENTERPRISE_PLATFORM_ID.toString()%>" scope="request"	>
                                <tr>
                                    <td colspan="2">
                                        <strong><bean:message	key="EntApplicationForm.allianceSponsor"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <html:select styleClass="selectField" property="allianceSponsor"	size="1" >
                                            <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                            <logic:iterate id="formBdsContacts" name="EntApplicationUpdateForm"	property="allBdsContacts" type="com.netpace.aims.model.core.BdsContact"	scope="request">
                                                <html:option value="<%=formBdsContacts.getContactId().toString()%>"><%=formBdsContacts.getFirstName()%>	<%=formBdsContacts.getLastName()%></html:option>
                                            </logic:iterate>
                                        </html:select>
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
                    <%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_PRIORITIZATION, AimsSecurityManager.UPDATE)) {%>
                      <tr>
                        <td><div class="lBox">
                            <DIV class="headLeftCurveblk"></DIV>
                            <H1><bean:message	key="ApplicationForm.table.head.prioritize"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                            <DIV class="headRightCurveblk"></DIV>
                          </div></td>
                      </tr>
                      <tr>
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                          <tr>
                            <td class="gradient" width="50%">
                                <strong><bean:message	key="ApplicationForm.appPriority"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                <br/>
                                <html:radio	property="appPriority" value="H"/><bean:message	key="ManageApplicationForm.radio.label.high"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                                <html:radio	property="appPriority" value="L"/><bean:message	key="ManageApplicationForm.radio.label.low"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </td>
                            <td class="gradient" width="50%">
                                <strong><bean:message	key="ApplicationForm.prodDate"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:<strong>
                                <br/>
                                <html:text styleClass="inputField"	property="targetedProductionDate"	size="15"/>
                                <img onclick="callToggleDatePicker('daysOfMonth400','targetedProductionDate')" name="daysOfMonth400Pos" id="daysOfMonth400Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth400"/>
                            </td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr><td>&nbsp;</td></tr>
                    <% } else {}%>
                </logic:notEqual>
            <% } else {}%>
            
            <logic:notEqual name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>" scope="request"	>
            <% if ( statusUnderTest ||  statusAccepted || statusRejected || statusSunset ) { %>
                <%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_TESTING, AimsSecurityManager.UPDATE)) {%>
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
                              <td align="left"><bean:write name="certs2" property="aimsVzwTestingPhase.testingPhaseName" /></td>
                              <td align="center">
                                <html:text styleClass="inputField"	property='<%= "testedDate[" + ctr + "]" %>' size="15" /><img onclick="callToggleDatePicker('daysOfMonth<%=ctr%>','testedDate[<%=ctr%>]')" name="daysOfMonth<%=ctr%>Pos" id="daysOfMonth<%=ctr%>Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth<%=ctr%>"/>
                              </td>
                              <td align="center">
                                    <html:radio	property='<%= "testStatus[" + ctr + "]" %>' value="P"/><bean:message	key="ManageApplicationForm.radio.label.passed"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                                    <html:radio	property='<%= "testStatus[" + ctr + "]" %>' value="F"/><bean:message	key="ManageApplicationForm.radio.label.failed"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                                </td>
                              <td>
                                <html:file styleClass="inputField" size="20" property='<%= "testResultFile[" + ctr + "]" %>'/><br/>
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
                                    <html:textarea styleClass="textareaField" property='<%= "testComments[" + ctr + "]" %>' onkeyup="LimitText(this,200)" onkeypress="LimitText(this,200)" rows="4" cols="45"></html:textarea>
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
                    <%@ include  file="include/appEditButtons.jsp" %>
                  </table>
				  <logic:present name="BrewApplicationUpdateForm">
					<%@ include file="include/brewMessageFooter.jsp" %>
				  </logic:present>                   
                </td>
            </tr>
        </table>
      </div>
    </html:form>
  </div>
</div>
