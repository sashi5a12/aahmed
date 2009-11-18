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
</script>


<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left" onmousemove="">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/entAppTabs.jsp" %>
    <div>&nbsp;</div>
    <html:form action="<%=ApplicationUpdateForm.getUpdateURL()%>"	enctype="multipart/form-data">
      <div class="contentbox">
        <%@ include  file="include/entAppHidden.jsp" %>
     
<table width="100%" border="0" cellspacing="0" cellpadding="0">

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
                              
                            </tr>
            
                          </table></td>
                    </tr>
                    
                    <%-- Display only if application is in accepted state--%>
                   <logic:equal name="EntApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request" value="7">
                   				
                    <tr><td>&nbsp;</td></tr>
                    
                    <tr>
                        <td><div class="lBox">
                            <DIV class="headLeftCurveblk"></DIV>
                            <H1><bean:message	key="ApplicationForm.table.head.other.details"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                            <DIV class="headRightCurveblk"></DIV>
                        </div></td>
                    </tr>
                    
                    <tr>
                    	<td>	
                         	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                         	<tr>
		                        <th>
		                            <strong><bean:message key="EntApplicationForm.ispublished"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
		                            <br/>
		                            <html:radio property="isPublished" value="Y"/>Yes
		                            <html:radio property="isPublished" value="N"/>No
		                         </th>
                           </tr>
                            
                            <tr><td>&nbsp;</td></tr>
                            
                         	<tr>
								<td>
									<html:checkbox property="isFeatured" value="Y" />
									<strong><bean:message key="EntApplicationForm.isFeatured"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong><br/> 
								</td>
							</tr>
							</table>
							
                    	</td>
                    </tr>
                  </logic:equal>  
                    
                    
                    <tr><td>&nbsp;</td></tr>
              <% } else {}%>		
<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<%@ include  file="include/entAppEditButtons.jsp" %>
							<script	language="javascript">
								//displayContactsInformation();
								<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
									//disableForVzw();
								<% } else {}%>
						    </script>

							<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
                                <html:hidden property="aimsContactId"	/>
                                <html:hidden property="appDeployments"	/>
                                <html:hidden property="fortuneCustomers"	/>
                                <html:hidden property="customerBenefits"    />
                                
                                
                                <html:hidden property="otherIndFocusValue"	/>
                                <html:hidden property="solutionComponentId"	/>
				  	        <% } else {}%>
						</td>
					</tr>
				</table>
	</td>
</tr>
   
</table>
      </div>
    </html:form>
  </DIV>
 </div>
  

