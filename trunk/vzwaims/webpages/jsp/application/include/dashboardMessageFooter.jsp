<%@ page import="com.netpace.aims.util.StringFuncs"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<logic:messagesPresent>
    <html:messages id="error">
        <html:hidden property="errorMessages" value="<%=StringFuncs.NullValueReplacement((String)pageContext.getAttribute("error"))%>"/>
    </html:messages>
</logic:messagesPresent>
<logic:messagesNotPresent>
    <logic:notEmpty name="DashboardApplicationUpdateForm" property="errorMessages" scope="request">
         <logic:iterate id="error" name="DashboardApplicationUpdateForm" property="errorMessages">
             <html:hidden property="errorMessages" value="<%=StringFuncs.NullValueReplacement((String)pageContext.getAttribute("error"))%>"/>
         </logic:iterate>
    </logic:notEmpty>
</logic:messagesNotPresent>