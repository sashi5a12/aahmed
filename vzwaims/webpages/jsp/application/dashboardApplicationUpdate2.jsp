<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/application/dashboardApplicationUpdate2_body.jsp"/>
  <tiles:put name="headingText">
	<logic:match name="DashboardApplicationUpdateForm"	property="task"	scope="request"	value="create">
		<bean:message	key="DashboardApplicationForm.createApp"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</logic:match>
	<logic:match name="DashboardApplicationUpdateForm"	property="task"	scope="request"	value="edit">
		<bean:message	key="DashboardApplicationForm.editApp"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</logic:match> 
  </tiles:put>
</tiles:insert>  
