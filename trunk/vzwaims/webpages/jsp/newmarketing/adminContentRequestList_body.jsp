<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.util.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%
boolean isVerizonUser = false, isAllianceUser = false;
%>

<%
isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE);
isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE);
%>

<jsp:useBean id="TotalPages"	class="java.lang.Integer"	scope="request"/>
<script language="Javascript">
<!--
	function submitReq(iTask)
	{
		if (iTask == 1)
		{
			document.forms[0].task.value='previous';
			document.forms[0].submit();
		}
		else if (iTask == 2)
		{
			document.forms[0].task.value='next';
			document.forms[0].submit();
		}
		else if (iTask == 3)
		{
			document.forms[0].task.value='list';
			document.forms[0].submit();
		}
		
	}
	
	function submitRequest(iTask, sortField)
	{
	document.forms[0].task.value='list';
	document.forms[0].sortField.value= sortField;
	document.forms[0].submit();
	
	}
	
	function deleteRequest(crequestId)
	{
		if(confirm('Are you sure delete the creative content request?'))
		{
			document.forms[0].task.value='delete';
			document.forms[0].crequestId.value = crequestId;
			document.forms[0].submit();
		}
	}
//-->
</script>
<html:form action="/newMktAdminContentRequest.do">
<html:hidden property="task"/>
<html:hidden property="pageNo"/>
<html:hidden property="sortField"/>
<input type="hidden" name="crequestId" value="0"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
	    <td width="20">&nbsp;</td>
		<td width="100%">
            <span	class="aimsmasterheading">Content Request Management - Content Requests List</span>
		</td>
	</tr>
    <!--tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">
            &nbsp;
       </td>
    </tr-->
    	<%@ include  file="/common/error.jsp" %>
    
		<tr>
		   <td colspan="2">
					<table class="sectable"	width="100%">
					<tr	class="sectitle"><td class="aimssecheading">Applications Filter</td></tr>
					<tr	bgcolor="#EBEBEB"	>
						<td	align="right">
							<table cellspacing="5" cellpadding="0" >
									<tr>
										<td	class="modFormFieldLbl">
											<bean:message key="ManageApplicationForm.view.filterBy"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:
										</td>
										<td	class="modFormFieldLbl">
											<html:select property="filterField"	size="1">
												<html:option value="programName">Program Name</html:option>
                                                <% if (isVerizonUser) { %>
                                                    <html:option value="status">Content Request Status</html:option>
                                                <% } else {} %>
											</html:select>
										</td>
										<td	class="modFormFieldLbl">
											<bean:message key="ManageApplicationForm.view.filterText"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:
										</td>
										<td	class="inputbox">
											<html:text	property="filterText"	size="20"	/>
										</td>
										<td	class="body" align="right">
											<a href="javascript:submitReq(3);//"><img src="images/go_b.gif" width="27"	height="18" border="0"></a>
										</td>
									</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
	 </tr>
	 
	<tr>
		<td	width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign="top">
						<table class="sectable" width="100%" height="100%">
							<tr class="sectitle"><td colspan="2" class="aimssecheading">Content Requests</td></tr>
                			<tr>
                    			<td colspan="2" align="center" valign="middle" bgcolor="#EBEBEB"> 
				          	        <table width="100%" border="0" cellspacing="0" cellpadding="0">		
			      			            <tr>
			      			      	        <td bgcolor="#EBEBEB">
			      		      		            <table width="100%" border="0" class="tabletop" cellspacing="0" cellpadding="2" >
			      	      			                <tr bgcolor="#BBBBBB">
                                                        <td class="firstcell" align="left">
                                                            Program Title
                                                        </td>
                                                        <td class="cell" align="left">
                                                            Project Start Date
                                                        </td>
                                                        <td class="cell" align="center">
                                                            Submitted Date
                                                        </td>
                                                        <% if (isVerizonUser) { %>
                                                            <td class="cell" align="left">
                                                                Status
                                                            </td>
                                                        <% } else {} %>
			                            	        </tr>
									   				<logic:iterate id="req" name="ContentRequestList" type="com.netpace.aims.model.newmarketing.AimsCreativeCrequest">
                                                        <tr>
                                                            <td class="firstcell" align="left"><a href="newMktAdminContentRequest.do?task=view&crequestId=<bean:write name="req" property="crequestId"/>"><bean:write name="req" property="programName" /></a>&nbsp;</td>
                                                            <td class="cell" align="left"><bean:write name="req" property="projectStartDate" formatKey="date.format" filter="true"/>&nbsp;</td>
                                                            <td class="cell" align="center">
                                                                <logic:present name="req" property="submittedDate"><bean:write name="req" property="submittedDate" formatKey="date.format" filter="true"/></logic:present>
                                                                <logic:notPresent name="req" property="submittedDate">N/A</logic:notPresent>&nbsp;
                                                            </td>
                                                            <% if (isVerizonUser) { %>
                                                                <td class="cell" align="left"><bean:write name="req" property="status"/>&nbsp;</td>
                                                            <% } else {} %>
                                                        </tr>
               										</logic:iterate>
               										<logic:empty name="ContentRequestList">
                                                        <tr>
                                                            <td class="firstcell" align="center" colspan="7">No Content Request Found.</td>
                                                        </tr>
               										</logic:empty>
                   								</table>
			      			                </td>
			      		                </tr>
			                        </table>
      	            			</td>
                			</tr>
				 <logic:notEmpty name="ContentRequestList">                			
    			<tr>
    			   <td colspan="2" align="right">
    			      <table border="0" cellspacing="0" cellpadding="0">
	               <tr><td><logic:greaterThan	name="MktAdmConReqForm" property="pageNo" value="1" >
						<a href='javascript:submitReq(1);//'><bean:message	key="images.previous.icon" /></logic:greaterThan></a></td> 
						<td>Page <bean:write name="app" name="MktAdmConReqForm" property="pageNo" /> of <%=TotalPages.toString()%></td>
	               <td><logic:lessThan	name="MktAdmConReqForm" property="pageNo" value="<%=TotalPages.toString()%>"	>
						<a href='javascript:submitReq(2);//'><bean:message	key="images.next.icon" /></a></logic:lessThan></td></tr>
						</table>
    			   </td>
    			</tr>
            </logic:notEmpty>
            			</table>
	    			</td>
    			</tr>
    			<tr>
    			   <td width="100%" align="right"><img src="images/spacer.gif" width="5" height="2"></td>
    			</tr>
			</table>
			 
		</td>
	</tr>
</table>
</html:form>