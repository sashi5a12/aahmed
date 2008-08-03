<%@ page language="java"%>

<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><tiles:insertAttribute name="title" /></title>
  </head> 
  <body>
  <table border="1" width="600" cellspacing="5">
  	<tbody>
  		<tr><td colspan="2"><tiles:insertAttribute name="header" /></td></tr>
  		<tr>
  			<td width="200"><tiles:insertAttribute name="navigation" /></td>
  			<td width="400"><tiles:insertAttribute name="body" /></td>
  		</tr>
  		<tr><td colspan="2"><tiles:insertAttribute name="footer" /></td></tr>
  	</tbody>
  </table>
  </body>
</html>
