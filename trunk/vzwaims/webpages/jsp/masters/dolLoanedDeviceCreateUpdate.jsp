<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/masters/dolLoanedDeviceCreateUpdate_body.jsp"/>

  <tiles:put name="headingText" >
      <logic:present name="AimsDeviceDetails" property="loanDeviceId" >
          <bean:message key="DeviceOnLoan.EditDeviceOnLoan" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
      </logic:present>
      <logic:notPresent name="AimsDeviceDetails" property="loanDeviceId">
        <bean:message key="DeviceOnLoan.NewDeviceOnLoan" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
      </logic:notPresent>
  </tiles:put>

</tiles:insert>