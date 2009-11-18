<%@	page language="java" import="com.netpace.aims.util.AimsConstants" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/tile-def-popUp.jsp" %>
<tiles:insert beanName="popUpLayout" beanScope="request">
  <logic:equal name="<%=AimsConstants.AIMS_USER%>" scope="session" property="userType" value="<%=AimsConstants.VZW_USERTYPE%>">
    <tiles:put name="body" value="<%=AimsConstants.POPUP_VZW_LOCATION%>"/>
  </logic:equal>
  <logic:equal name="<%=AimsConstants.AIMS_USER%>" scope="session" property="userType" value="<%=AimsConstants.ALLIANCE_USERTYPE%>">
    <tiles:put name="body" value="<%=AimsConstants.POPUP_ALLIANCE_LOCATION%>"/>
  </logic:equal>
</tiles:insert>  
