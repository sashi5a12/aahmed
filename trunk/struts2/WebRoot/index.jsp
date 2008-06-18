<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  
  <body>
    <a href="helloworld/inputname.action" />Hello World Example</a><br>
    <a href="fordefault/defaultnamespace.action" />Default Namespace Example</a><br>
    <a href="validation/InputRegister.action"/>Validation Example</a>   How workflow interceptor (DefaultWorkflowInterceptor) and ActionSupport work together and provides the way of validation. ValidationWare Interface is use to store and retrieve errors. Workflow interceptor uses this interface to know about the error may occured for this request.<br/>
    <a href="javabean/InputRegister.action"/>Transfer of data using javabean properties method</a><br/>
    <a href="modeldriven/InputRegister.action"/>Transfer of data using ModelDriven method</a><br/>
    <a href="fileupload/AddFile.action">Add Single File</a>
  </body>
</html>
