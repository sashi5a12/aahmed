<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/roles/sysRolesCreateUpdate_body.jsp"/>

    <tiles:put name="headingText">
        <bean:parameter id="taskForThisPage" name="task" value="Nopes"/>
        <logic:match name="taskForThisPage" scope="page" value="create">
            Create Role
        </logic:match>
        <logic:match name="taskForThisPage" scope="page" value="edit">
            Edit Role
        </logic:match>
    </tiles:put>

</tiles:insert>