<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-logic" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="header" value="/public/login_header.jsp"/>
  <tiles:put name="menu" value="/common/blank.jsp"/>
  <tiles:put name="userbar" value="/common/blank-userbar.jsp"/>
  <tiles:put name="body_width" value="3" />
  <tiles:put name="body" value="/public/AdvDevInfo_body.jsp"/>
  
  <logic:present name="AIMS_USER" scope="session" >
   <tiles:put name="header" value="/common/header.jsp"/>
   <tiles:put name="userbar" value="/common/userbar.jsp"/>   
  </logic:present>
  
</tiles:insert>  
