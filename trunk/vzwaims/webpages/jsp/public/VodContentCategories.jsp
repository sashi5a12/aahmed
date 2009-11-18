<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ include file="/common/tile-def.jsp" %>

<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="menu" value="/public/landingpages/landing-vod-muCatg.jsp"/>
    <logic:notPresent name="AIMS_USER" scope="session">
        <tiles:put name="menubar" value="/public/menubar.jsp"/>
    </logic:notPresent>
    <tiles:put name="body" value="/public/vodContentCategories_body.jsp"/>
    <tiles:put name="userbar" value="/common/userbar.jsp"/>
    <tiles:put name="headingText" value="V CAST Video On-Demand" />
</tiles:insert>

<%--
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-logic" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="header" value="/public/login_header.jsp"/>
  <tiles:put name="menu" value="/common/blank.jsp"/>
  <tiles:put name="userbar" value="/common/blank-userbar.jsp"/>
  <tiles:put name="body_width" value="3" />
  <tiles:put name="body" value="/public/vodContentCategories_body.jsp"/>
    
  <logic:present name="AIMS_USER" scope="session" >
   <tiles:put name="header" value="/common/header.jsp"/>
   <tiles:put name="userbar" value="/common/userbar.jsp"/>   
  </logic:present>
  
</tiles:insert>  
--%>
