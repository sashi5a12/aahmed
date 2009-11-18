<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="headingText">Workflow Items List</tiles:put>
    <tiles:put name="body" value="/workflow/worklist_body.jsp"/>
    <tiles:put name="filterHolder" value="/workflow/worklistFilter.jsp"/>
</tiles:insert>