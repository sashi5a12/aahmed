<%@page contentType="text/html"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<html>
  <head>
    <title><tiles:getAsString name="title"/></title>
    <script language="JavaScript" src="js/aims2.js"></script>
    <script language="JavaScript" src="js/calendar2.js"></script>
    <script language="JavaScript" src="js/tjmlib2.js"></script>


   <link href="/aims/css/global.css" rel="stylesheet" type="text/css" />
	<link href="/aims/css/modules.css" rel="stylesheet" type="text/css" />
	<link href="/aims/css/sections.css" rel="stylesheet" type="text/css" />
  </head>

  <body bgcolor="#ffffff" text="#000000" link="#023264" alink="#023264" vlink="#023264" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

  <table width="100%" border="0" width="100%" cellspacing="0">
      <tr>
        <td valign="top" align="left" width="100%" colspan="3">          
          <!-- BODY:BEGIN -->
          <tiles:insert attribute="body" />
          <!-- BODY:END   -->
        </td>
      </tr>
    </table>
  </body>
</html>