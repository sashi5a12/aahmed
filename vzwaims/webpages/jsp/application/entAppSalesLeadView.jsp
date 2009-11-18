<%@taglib uri="/WEB-INF/tiles.tld" prefix="tiles"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
 
 
<%@include file="/common/tile-def.jsp"%> 
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/application/entAppSalesLeadView_body.jsp"/>
  <tiles:put name="headingText">
  <logic:match name="EntAppSalesLeadViewForm"	property="view"	scope="request"	value="sent">
	<bean:message key="JMAApp.salesLeadSent"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
 </logic:match>
 
  <logic:match name="EntAppSalesLeadViewForm"	property="view"	scope="request"	value="received">
	<bean:message key="JMAApp.salesLeadReceived"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
 </logic:match>
 
 <tiles:put name="filterHolder" value="/application/entAppSalesLeadFilter.jsp"/> 
 
  </tiles:put> 
</tiles:insert>  
