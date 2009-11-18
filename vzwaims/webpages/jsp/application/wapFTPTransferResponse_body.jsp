<%@	page language="java" %>

<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include  file="/common/error.jsp" %>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <input type="image"	name="close" <bean:message key="images.close.button.lite"/> onClick="window.close();"/>
            </td>
        </tr>
</table>