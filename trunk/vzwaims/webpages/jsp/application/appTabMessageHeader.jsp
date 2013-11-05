<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="errorMessages" scope="request">
	<div class="errorMsg">
		<table cellpadding="0" cellspacing="0" border="0">
			<logic:iterate id="errorMessage" name="VZAppZoneApplicationUpdateForm" property="errorMessages">
				<tr>
					<td><h1><bean:write	name="errorMessage"/></h1></td>
				</tr>
			</logic:iterate>
		</table>
	</div>
</logic:notEmpty>
<logic:empty name="VZAppZoneApplicationUpdateForm" property="errorMessages">
    <%@ include  file="/common/error.jsp" %>
</logic:empty>