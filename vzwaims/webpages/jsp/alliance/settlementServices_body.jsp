<%@ page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>


<logic:messagesPresent>	
	<div class="errorMsg">
		<table cellpadding="0" cellspacing="0" border="0">
			<html:messages id="error"	header="errors.header" footer="errors.footer">
				<tr>
					<td><h1><bean:write	name="error"/></h1></td>
				</tr>
			</html:messages>
		</table>
	</div>
</logic:messagesPresent>
<logic:messagesPresent message="true">
	<div class="alertMsg">
		<html:messages id="message"	message="true" header="messages.header"	footer="messages.footer">	
			<li><bean:write	name="message"/></li><br/>
		</html:messages>
	</div>
</logic:messagesPresent>