<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/tools/toolView_body.jsp"/>

   <tiles:put name="headingText" >
       <bean:message key="ToolsForm.welcomeScreen" />
   </tiles:put>

</tiles:insert>