<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ include file="/common/tile-def.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/accounts/accountsCreateUpdateView_body.jsp"/>
    <tiles:put name="headingText">

        <logic:equal parameter="task" value="editFormView" >
            <bean:message key="AccountForm.viewUser" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        </logic:equal>

        <logic:equal parameter="task" value="editForm" >
            <bean:message key="AccountForm.viewUser" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        </logic:equal>

    </tiles:put>
</tiles:insert>