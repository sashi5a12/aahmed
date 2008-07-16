<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s"  uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  	<h2><s:property value="message"/></h2>
  	<h3>Languages</h3>
  	<ul>
  		<li>
  			<s:url action="HelloWorld" id="url">
  				<s:param name="request_locale">en</s:param>
  			</s:url>
  			<s:a href="%{url}">English</s:a>
  		</li>
  		<li>
  			<s:url action="HelloWorld" id="url">
  				<s:param name="request_locale">ur</s:param>
  			</s:url>
  			<s:a href="%{url}">Urdu</s:a>
  		</li>
  	</ul>
  </body>
</html>
