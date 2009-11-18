<%@	page language="java" import="com.netpace.aims.controller.newmarketing.ContactsDetailsExt, com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<%
String currentUserId = "";
boolean isVerizonUser = false, isAllianceUser = false;
%>

<%
currentUserId = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserId().toString();
isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE);
isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE);
%>

<script language="Javascript">
<!--
	function submitReq(iTask)
	{
		if (iTask == 1)
		{
			document.forms[0].task.value='acceptparts';
			document.forms[0].submit();
		}
		else if (iTask == 2)
		{
			document.forms[0].task.value='reject';
			document.forms[0].submit();
		}
		else if (iTask == 3)
		{
			document.forms[0].task.value='view';
			document.forms[0].submit();
		}
        else if (iTask == 4)
        {
            document.forms[0].task.value='alliance_acceptparts';
            document.forms[0].submit();
        }

	}
//-->
</script>
<% 
// String used to get prrdefined value for the page if error occurs
String optionSelected = null;
String userIdSelected = null;
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">
       	<span class="aimsmasterheading">
       		Content Management - 
       		Approve / Reject Content Requests
       	</span>               
       </td>
  </tr>
  <%@ include  file="/common/error.jsp" %>
  <tr> 
      <td width="20">&nbsp;</td>
      <td align="center" valign="middle"> 
			<html:form action="/newMktAdminContentRequest.do">
			<html:hidden property="crequestId"/>
			<html:hidden property="task"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td width="100%">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Content Request Briefs</td></tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Program Name
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Program Start Date
							:</td>
						</tr>
						
						<tr>
							<td class="text" valign="top">
								<bean:write name="ContentRequestDetail" property="programName"/>&nbsp;
							</td>
							<td class="text">
								<bean:write name="ContentRequestDetail" property="projectStartDate" formatKey="date.format" filter="true"/>&nbsp;
							</td>
						</tr>
						
					</table>	
				</td></tr>
				<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Contents Detail Requested for Provisioning</td></tr>
						<logic:iterate id="appDetail" name="ApplicationContacts" type="com.netpace.aims.controller.newmarketing.AppContactsDetailsExt">
						<tr>
							<td class="text">&nbsp;
								<span class="modFormFieldLbl"><bean:write name="appDetail" property="appTitle" /> (<bean:write name="appDetail" property="developerName" />) 
									<logic:present name="appDetail" property="contentUsagePersmission">
    									 [Permission - <logic:equal name="appDetail" property="contentUsagePersmission" value="G">General Usage</logic:equal><logic:equal name="appDetail" property="contentUsagePersmission" value="E">Event Based</logic:equal>]
    								</logic:present>
								</span> 
                                
                                <% if (isVerizonUser) { %>
                                    <logic:present name="appDetail" property="status"><span class="text">[Current Status: <bean:write name="appDetail" property="status" />]</span><input type="hidden" name="oldAction_<bean:write name="appDetail" property="applicationId" />" value="<bean:write name="appDetail" property="status" />"></logic:present>
                                <% } else if (isAllianceUser) { %>
                                    <logic:present name="appDetail" property="userStatus"><span class="text">[Current Status: <bean:write name="appDetail" property="userStatus" />]</span><input type="hidden" name="oldAction_<bean:write name="appDetail" property="applicationId" />" value="<bean:write name="appDetail" property="userStatus" />"></logic:present>
                                <% } else {} %>
                                
                                <input type="hidden" name="appsId" value="<bean:write name="appDetail" property="applicationId" />">
                                								
							</td>
						</tr>

                        <logic:present name="appDetail" property="allianceUserNameToApprove">
                            <logic:notEqual name="appDetail" property="allianceUserIdToApprove" value="<%=currentUserId%>">
                                <tr>
                                    <td class="text">
                                    &nbsp;Request for Approval from <bean:write name="appDetail" property="allianceUserNameToApprove"/> is <bean:write name="appDetail" property="userStatus"/>
                                    </td>
                                </tr>
                            </logic:notEqual>
                        </logic:present>            

						<tr>
							<td class="text">
                                
                                <% if (isVerizonUser) { %>

                                    <% optionSelected = request.getParameter("action_"+appDetail.getApplicationId());
                                       if (optionSelected == null) optionSelected = "N";
                                       userIdSelected = request.getParameter("userId_"+appDetail.getApplicationId());
                                       if (userIdSelected == null) userIdSelected = "";
                                    %>
                                     <input type="radio" name="action_<bean:write name="appDetail" property="applicationId" />" value="N" <%if ( optionSelected.equals("N")){%>checked<%}%>> No Action 
                            
                                     <logic:present name="appDetail" property="contentUsagePersmission">
                                         <logic:equal name="appDetail" property="contentUsagePersmission" value="G">
                                             <input type="radio" name="action_<bean:write name="appDetail" property="applicationId" />" value="A" <%if ( optionSelected.equals("A")){%>checked<%}%>> Accept 
                                         </logic:equal>
                                     </logic:present>   
                                
                                     <input type="radio" name="action_<bean:write name="appDetail" property="applicationId" />" value="D" <%if ( optionSelected.equals("D")){%>checked<%}%>> Reject <br/>
                                     <%
                                     if (appDetail.getStatus() == null || appDetail.getStatus().equals(com.netpace.aims.util.AimsConstants.MKT_APPLICATION_PENDING) ) {
                                     %>
                                     <logic:notEmpty  name="appDetail" property="allianceUsers">
                                     <input type="radio" name="action_<bean:write name="appDetail" property="applicationId" />" value="F" <%if ( optionSelected.equals("F")){%>checked<%}%>> Forward For Approval To :
                                         <select name="userId_<bean:write name="appDetail" property="applicationId" />">
                                         <logic:iterate id="contacts" name="appDetail" property="allianceUsers" type="ContactsDetailsExt">
                                                     <option value="<bean:write name="contacts" property="userId" />" <%if ( userIdSelected.equals(contacts.getUserId().toString())){%>selected<%}%>><bean:write name="contacts" property="fullNameTitle" /></option>
                                         </logic:iterate>
                                         </select>
                                     </logic:notEmpty>
                                     <%}%>

                                <% } else if (isAllianceUser) { %>

                                    <logic:present name="appDetail" property="allianceUserNameToApprove">
                                        <logic:equal name="appDetail" property="allianceUserIdToApprove" value="<%=currentUserId%>">
                                            <logic:equal name="appDetail" property="userStatus" value="PENDING">
                                                <% optionSelected = request.getParameter("action_"+appDetail.getApplicationId());
                                                   if (optionSelected == null) optionSelected = "N";
                                                   userIdSelected = request.getParameter("userId_"+appDetail.getApplicationId());
                                                   if (userIdSelected == null) userIdSelected = "";
                                                %>
                                                 <input type="radio" name="action_<bean:write name="appDetail" property="applicationId" />" value="N" <%if ( optionSelected.equals("N")){%>checked<%}%>> No Action 
                                                 <input type="radio" name="action_<bean:write name="appDetail" property="applicationId" />" value="A" <%if ( optionSelected.equals("A")){%>checked<%}%>> Accept 
                                                 <input type="radio" name="action_<bean:write name="appDetail" property="applicationId" />" value="D" <%if ( optionSelected.equals("D")){%>checked<%}%>> Reject <br/>
                                            </logic:equal>
                                        </logic:equal>
                                    </logic:present>            
                                                        
                                <% } else {} %>

							</td>
						</tr>
						<logic:notPresent name="appDetail" property="status">
						</logic:notPresent>
						</logic:iterate>
					</table>	
				</td></tr>
			
					
				<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%" align="right">
					<% if (isVerizonUser) { %>
                        <a href="javascript:submitReq(1);//"><img src="images/submit_b.gif" width="52" height="15" border="0"></a>&nbsp;
                        <a href="javascript:submitReq(2);//"><img src="images/reject_b.gif" width="52" height="15" border="0"></a>&nbsp;
                    <% } else if (isAllianceUser) { %>
                        <a href="javascript:submitReq(4);//"><img src="images/submit_b.gif" width="52" height="15" border="0"></a>&nbsp;
                    <% } else {} %>
                        
                    <a href="javascript:submitReq(3);//"><img src="images/cancel_b.gif" width="52" height="15" border="0"></a>
				</td></tr>
			</table>
		</td>
	</tr>
</table>
</html:form>

