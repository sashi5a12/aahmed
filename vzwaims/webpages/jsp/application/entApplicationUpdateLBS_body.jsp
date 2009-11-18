<%@ page language="java"
         import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*,java.util.*, com.netpace.aims.model.application.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%ApplicationUpdateForm.setCurrentPage("pageEntLBS");
 //boolean isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE);	
%>


<script language="javascript">
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
            <H1><bean:message key="ApplicationForm.table.head.lbs" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
        
          
           <tr>
               <th>
               
                    <strong><bean:message key="ManageApplicationForm.lbscontractField" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;:</strong>  
				<% if(isVerizonUser){%>
					<html:file size="30" styleClass="inputField" property="lbsContractFile"/><br/>
				<%} %>		
						<logic:notEmpty	name="EntApplicationUpdateForm"	property="lbsContractFileName" scope="request">

							<logic:equal name="EntApplicationUpdateForm" property="lbsContractTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=lbsContractFile&app_id=<bean:write	name="EntApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="EntApplicationUpdateForm"	property="lbsContractTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="EntApplicationUpdateForm"	property="lbsContractTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="EntApplicationUpdateForm"	property="lbsContractFileName"/>
								</a>
						
						
						<%// Display status of contract%>
						 (Status: 
						<logic:notEmpty name="EntApplicationUpdateForm" property="isLbsAcceptByAlliance">
          				<logic:equal name="EntApplicationUpdateForm" property="isLbsAcceptByAlliance" scope="request"	value="Y">
          					Accepted
						</logic:equal>
						<logic:equal name="EntApplicationUpdateForm" property="isLbsAcceptByAlliance" scope="request"	value="N">
							Rejected
						</logic:equal>	
          			</logic:notEmpty>
          			<logic:empty name="EntApplicationUpdateForm" property="isLbsAcceptByAlliance">
          				Pending
          			</logic:empty> )
          			
          			</logic:notEmpty> 
               </th>
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