<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <head>
    <title>My JSP 'AddFile.jsp' starting page</title>
  </head>
  
  <body>
    <s:form action="FileUpload" method="post" enctype="multipart/form-data">
    	<s:file name="image" label="Add File"></s:file>
    	<s:submit value="Add"></s:submit>
    </s:form>
  </body>
</html>
