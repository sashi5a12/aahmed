<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<tiles:definition id="classicLayout" page="/common/layout.jsp" >
    <tiles:put name="title" value="Verizon Developer Community - Go to Market"/>
    <tiles:put name="header" value="/common/header.jsp"/>
    <tiles:put name="menubar" value="/common/menubar-loggedIn.jsp"/>

    <tiles:put name="headingText" value="Welcome to Go to Market" />

    <tiles:put name="pageheading" value="/common/pageHeading.jsp"/>
    <tiles:put name="filterHolder" value="/common/empty.jsp"/>
    <tiles:put name="userbar" value="/common/userbar.jsp"/>
    <tiles:put name="footer" value="/common/footer.jsp"/>
    <tiles:put name="menu" value="/common/menu.jsp"/>

    <tiles:put name="body" value="/common/body-content.jsp"/>
</tiles:definition>