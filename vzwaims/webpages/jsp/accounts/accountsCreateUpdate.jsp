<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/accounts/accountsCreateUpdate_body.jsp"/>

    <tiles:put name="headingText">
        <bean:parameter id="taskForThisPage" name="task" value="Nopes"/>
        <logic:match name="taskForThisPage" scope="page" value="create">
            <bean:message key="AccountForm.createHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        </logic:match>
        <logic:match name="taskForThisPage" scope="page" value="edit">
            <bean:message key="AccountForm.editHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        </logic:match>
    </tiles:put>

</tiles:insert>