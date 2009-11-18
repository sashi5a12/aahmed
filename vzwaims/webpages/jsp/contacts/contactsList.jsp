<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/contacts/contactsList_body.jsp"/>
  <tiles:put name="headingText">
	  <bean:message key="ContactForm.viewHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
  </tiles:put>
  <tiles:put name="filterHolder" value="/contacts/contactsListFilter.jsp"/>
</tiles:insert>