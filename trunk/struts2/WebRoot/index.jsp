<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags" prefix="s" %>
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
    <a href="fileupload/AddFile.action">Add Single File</a><br/>
    <a href="interceptor/show.action">Interceptor Map to action</a><br/>
    <a href="secure/profile.action">Show Profile (Without Login)</a><br/>
    <a href="login/login.action">Login</a><br/>
    <a href="conversion/ArrayDataTransfer.action">Array Data Transfer</a><br/>
    <a href="conversion/ListDataTransfer.action">List Data Transfer</a><br/>
    <a href="conversion/UserDataTransfer.action">User Data Transfer</a><br/>
    <a href="tagdemo/TagDemo.action">Tag Demo</a><br/>
    <a href="htmltags/setup.action">Html Tag Demo</a><br/>
    <a href="<s:url action="validationFramework/Registration"/>">Validation Framework</a> 
  </body>
</html>
