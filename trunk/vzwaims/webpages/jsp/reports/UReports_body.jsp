<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<jsp:useBean id="user_name" class="java.lang.String"	scope="request"/>
<jsp:useBean id="pass_word" class="java.lang.String"	scope="request"/>

<form name="reportForm" action="/vzwreports/pages/login.action" method="post">
    <input name="userName" type="hidden" value="<%=user_name%>">
    <input name="password" type="hidden" value="<%=pass_word%>">
</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			<span class="aimsmasterheading">
                Reports Functionality
            </span>
		</td>
	</tr>
	<%@ include  file="/common/error.jsp" %>
	<tr> 
		<td width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign=top">
						<table class="sectable" width="100%" height="100%">
							<tr class="sectitle"><td colspan="2" class="aimssecheading">Reports</td></tr>
							<tr class="disclaimerText"> 
							  <td>
							  The Reports feature is provided to Verizon Wireless to access and run various reports.
							  </td>
							</tr>							
						</table>
        			</td>
    			</tr>
            </table>           
		</td>
	</tr>
</table>
	
				
<script	language="javascript">
    document.reportForm.submit();
</script>