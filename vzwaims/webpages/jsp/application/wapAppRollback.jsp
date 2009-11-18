<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
     <tiles:put name="body" value="/application/wapAppRollback_body.jsp"/>
     <tiles:put name="headingText" value="Rollback Submitted DCR Applications"/>
     <tiles:put name="filterHolder" value="/application/wapAppRollbackFilter.jsp" />
</tiles:insert>
