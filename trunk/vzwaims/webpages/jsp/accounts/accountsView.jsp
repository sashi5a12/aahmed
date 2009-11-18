<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/accounts/accountsView_body.jsp"/>
  <tiles:put name="headingText">
	<c:choose>
		<c:when test="${empty isAccountManager}">
		  	<bean:message key="AccountForm.viewHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
		</c:when>
		<c:otherwise>
		  	<bean:message key="AccountForm.accountManagerHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
		</c:otherwise>
	</c:choose>
  </tiles:put>
  <tiles:put name="filterHolder" value="/accounts/accountsViewFilter.jsp"/>
</tiles:insert>  