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
        <%@ include  file="include/appViewHidden.jsp" %>
      <div id="contentBox">
      
      <table cellpadding="0" cellspacing="0" width="100%">
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
                            
                            </tr>
                            
                          </table></td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
              <% } else {}%>
              <tr>
	            <td width="100%">
	                <%@ include  file="include/appViewButtons.jsp" %>
	            </td>
             </tr>
                
     </table>
      </div> 
    </html:form>
 </DIV>   
</div>
