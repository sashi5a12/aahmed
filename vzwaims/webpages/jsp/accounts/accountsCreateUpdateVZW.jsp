<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/accounts/accountsCreateUpdateVZW_body.jsp"/>

    <tiles:put name="headingText" >
        <logic:match parameter="task" value="create">
            Create User
        </logic:match>
        <logic:match parameter="task" scope="page" value="editForm">
            Edit User
        </logic:match>
    </tiles:put>

</tiles:insert>