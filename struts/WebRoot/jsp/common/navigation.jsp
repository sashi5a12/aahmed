
<%@ page language="java"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

Navigation page
<br />
<html:link action="welcome"> Goto welcome page </html:link>
<br />
<html:link action="bookList">Goto book list page </html:link> 
<br />
<html:link action="item?method=allItems">All items</html:link> 
<br />
<html:link action="admin/admin?method=allAdmin">All Admin</html:link> 
<br />
<br />