<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>


<%@ include file="/common/tile-def-popUp.jsp" %>
<tiles:insert beanName="popUpLayout" beanScope="request">
  <tiles:put name="body" value="/application/entAppSpotlightView_body.jsp"/>
  <tiles:put name="headingText">
	<bean:message key="EntApp.spotlightView.label"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
  </tiles:put>  
</tiles:insert>  
