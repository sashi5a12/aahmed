<%@ page language="java"
         import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*,java.util.*, com.netpace.aims.model.application.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%ApplicationUpdateForm.setCurrentPage("pageEntBOBO");%>

<script language="javascript">
function disableForVzw()
{
}
	<%@ include  file="include/appJScript.jsp" %>
</script>

<%@ include file="/common/error.jsp" %>
<div id="contentBox" style="float:left" onmousemove="">
<DIV class="homeColumnTab ">
<%@ include file="include/entAppTabs.jsp" %>
<html:form action="/entApplicationUpdate.do" enctype="multipart/form-data">
<div class="contentbox">
<%@ include file="include/entAppHidden.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>
		<br/>
	</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ApplicationForm.table.head.bobo" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <th>
                    <logic:notEmpty name="EntApplicationUpdateForm" property="boboContract">
				        <strong><bean:message key="ManageApplicationForm.boboContractlink" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:&nbsp;</strong>
                    	<br/>
				        <logic:iterate id="boboCon" name="EntApplicationUpdateForm" property="boboContract">
				            <a class="a" target="_blank"
					           href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="boboCon" property="contractId"/>'>
					           <bean:write name="boboCon" property="contractTitle"/>
					        </a>
					       <br/> 
				        </logic:iterate>
			    	</logic:notEmpty>
                    
               </th>
          </tr>
         
          <tr>
          	<td>
          		<strong><bean:message key="ManageApplicationForm.boboAuthorizatonLetter" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:&nbsp;</strong>
				<% if(isVerizonUser){%>
					<html:file size="30" styleClass="inputField" property="boboLetterOfAuthFile"/><br/>
				<% } %>	
						<logic:notEmpty	name="EntApplicationUpdateForm"	property="boboLetterOfAuthFileName" scope="request">

							<logic:equal name="EntApplicationUpdateForm" property="boboLetterOfAuthTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=boboLetterOfAuthFile&app_id=<bean:write	name="EntApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="EntApplicationUpdateForm"	property="boboLetterOfAuthTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="EntApplicationUpdateForm"	property="boboLetterOfAuthTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="EntApplicationUpdateForm"	property="boboLetterOfAuthFileName"/>
								</a>
						</logic:notEmpty>
			</td>
          </tr>
         </table>
     </td>          
</tr>

<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<%@ include  file="include/entAppEditButtons.jsp" %>
							<script	language="javascript">
								//displayContactsInformation();
								<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
									disableForVzw();
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