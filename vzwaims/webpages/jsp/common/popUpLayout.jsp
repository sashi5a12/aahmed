<%@page contentType="text/html"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<html>
<head>
<title>
<tiles:getAsString name="title"/>
</title>
<LINK href="/aims/css/button.css" type=text/css rel=stylesheet media="screen,print">
<LINK href="/aims/css/leftNav.css" type=text/css rel=stylesheet media="screen,print">
<LINK href="/aims/css/layout.css" type=text/css rel=stylesheet media="screen,print">
<LINK href="/aims/css/fonts.css" type=text/css rel=stylesheet media="screen,print">
<LINK href="/aims/css/homepage.css" type=text/css rel=stylesheet media="screen,print">
<SCRIPT type="text/javascript" src="/aims/js/filter.js"></script>
<SCRIPT type="text/javascript" src="/aims/js/gn_engine.js" ></SCRIPT>
<SCRIPT type="text/javascript" src="/aims/js/leftNav.js" ></SCRIPT>
<script type="text/javascript" src="/aims/js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="/aims/js/aims2.js"></script>
<script type="text/javascript" src="/aims/js/calendar2.js"></script>
<script type="text/javascript" src="/aims/js/tjmlib2.js"></script>
<script type="text/javascript" src="/aims/js/processingInfoPopup2.js"></script>
<script type="text/javascript" src="/aims/js/tooltip2.js"></script>
<style type="text/css">
    @media print {
      .noprint { display: none; }
    }
</style>
</head>
<body style="BACKGROUND: #FFFFFF;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="49%" ><IMG id="popupLogoNew" alt="Verizon Wireless" src="/aims/images/logo_vzw.gif"/></td>
          <td width="51%" align="right" class='noprint'><img src="/aims/images/icon-close2.gif" width="17" height="17" align="absmiddle" /> <a class="a" href="javascript:window.close();">Close</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td><IMG height="10" alt="" src="/aims/images/spacer.gif" width="1"></td>
  </tr>
  <tr>
    <td background="/aims/images/footer.gif"><IMG height="5" alt="" src="/aims/images/footer.gif" width="1"></td>
  </tr>
  <tr id="spacerRow">
    <td><IMG height="20" alt="" src="/aims/images/spacer.gif" width="1"></td>
  </tr>
  <tr>
    <td><table align="center" width="100%" border="0" cellspacing="0" cellpadding="0" style="FLOAT: left; margin-left:10px; margin-right:10px; margin-bottom:10px;">
        <tr>
          <td><!-- BODY:BEGIN -->
            <tiles:insert attribute="body" />
            <!-- BODY:END   -->
          </td>
        </tr>
      </table></td>
  </tr>
  <tr>
  	<td width="100%">
		<div class="btnTopLine"></div>
	</td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
  </tr>
</table>
</body>
</html>