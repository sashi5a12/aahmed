<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<logic:notEmpty name="javaApplicationUpdateForm" property="errorMessages" scope="request">
	<div class="errorMsg">
		<table cellpadding="0" cellspacing="0" border="0">
			<logic:iterate id="errorMessage" name="javaApplicationUpdateForm" property="errorMessages">
				<tr>
					<td><h1><bean:write	name="errorMessage"/></h1></td>
				</tr>
			</logic:iterate>
		</table>
	</div>
</logic:notEmpty>
<logic:empty name="javaApplicationUpdateForm" property="errorMessages">
    <%@ include  file="/common/error.jsp" %>
</logic:empty>