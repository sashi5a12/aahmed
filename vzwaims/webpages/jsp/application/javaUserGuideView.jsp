<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/application/javaUserGuideView_body.jsp"/>
    <tiles:put name="headingText">
            		<logic:match name="javaApplicationUpdateForm" property="ring2App" scope="request" value="true">
			<bean:message   key="JavaApplicationForm.viewOndeckApp"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</logic:match>
		<logic:match name="javaApplicationUpdateForm" property="ring3App" scope="request" value="true">
			<bean:message   key="JavaApplicationForm.viewOffdeckApp"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</logic:match>
    </tiles:put>
</tiles:insert>  
