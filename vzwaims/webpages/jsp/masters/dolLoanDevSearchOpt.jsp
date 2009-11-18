<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/masters/dolLoanDevSearchOpt_body.jsp"/>

   <tiles:put name="headingText" >
       <bean:message key="DeviceOnLoan.SearchDeviceOnLoan"	bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/> For <bean:write name="AimsLoanDeviceExtz" property="deviceModel" />
   </tiles:put>

</tiles:insert>