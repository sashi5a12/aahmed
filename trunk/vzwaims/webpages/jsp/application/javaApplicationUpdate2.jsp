<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/application/javaApplicationUpdate2_body.jsp"/>
  <tiles:put name="headingText">
	<logic:match name="javaApplicationUpdateForm" property="task" scope="request" value="create">
		<logic:match name="javaApplicationUpdateForm" property="ring2App" scope="request" value="true">
			<bean:message   key="JavaApplicationForm.createOndeckApp"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</logic:match>
		<logic:match name="javaApplicationUpdateForm" property="ring3App" scope="request" value="true">
			<bean:message   key="JavaApplicationForm.createOffdeckApp"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</logic:match>
	</logic:match>
	<logic:match name="javaApplicationUpdateForm" property="task" scope="request" value="edit">
		<logic:match name="javaApplicationUpdateForm" property="ring2App" scope="request" value="true">
			<bean:message   key="JavaApplicationForm.editOndeckApp"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</logic:match>
		<logic:match name="javaApplicationUpdateForm" property="ring3App" scope="request" value="true">
			<bean:message   key="JavaApplicationForm.editOffdeckApp"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</logic:match>
	</logic:match> 
  </tiles:put>
</tiles:insert>  
 