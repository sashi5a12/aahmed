<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/application/vcastApplicationUpdate2_body.jsp"/>
  <tiles:put name="headingText">
	<%--<bean:message key="VcastApplicationForm.welcome"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> - --%>
	<logic:match name="VcastApplicationUpdateForm"	property="task"	scope="request"	value="create">
		<bean:message	key="VcastApplicationForm.createApp"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</logic:match>
	<logic:match name="VcastApplicationUpdateForm"	property="task"	scope="request"	value="edit">
		<bean:message	key="VcastApplicationForm.editApp"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</logic:match>  
  </tiles:put>  
</tiles:insert>  
