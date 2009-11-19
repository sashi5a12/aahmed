<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.netpace.vzdn.util.ConfigEnvProperties"%>
<%@ include file="/commons/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0045)http://www.verizonwireless.com/b2c/index.html -->
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">
<HEAD>

<TITLE><decorator:title/></TITLE>

<META content="Verizon, Wireless, Cell, Cell Phone, Mobile, Cellular, Phone, Mobile Phone" name=Keywords>
<META content="Verizon Wireless offers cell phones, pdas, devices, cell phone plans, data plans, and accessories with affordable, reliable wireless service for your personal needs." name=description>
<META http-equiv=Content-Type content="text/html;charset=UTF-8">
<META http-equiv=Content-Style-Type content=text/css>
<META http-equiv=imagetoolbar content=no>
<LINK href="styles/layout.css" type=text/css rel=stylesheet>
<LINK href="styles/fonts.css" type=text/css rel=stylesheet>
<LINK href="styles/displaytag.css" type=text/css rel=stylesheet>
<LINK media=print href="styles/accessiblePrint.css" type=text/css rel=stylesheet>
<SCRIPT src="scripts/hbxVariables.js" type=text/javascript> </SCRIPT>
<SCRIPT src="scripts/hbxFunctions.js" type=text/javascript> </SCRIPT>
<SCRIPT type=text/javaScript>
	 var webserver="";
	  var appServer="";
	  var vzserve="";
	  var bizServer="";
	  var loginForm="";
	  var logoutURL="";
	  var secureServer="";
	  var spanishLink ='';
	  var tabCookie ='';
	  var loginState ='';
	</SCRIPT>
<META content="MSHTML 6.00.2900.5726" name=GENERATOR>

<decorator:head/>
</HEAD>
<%
    ConfigEnvProperties envProps = ConfigEnvProperties.getInstance();
    String headerUrl = envProps.getProperty("header.url");
    String footerUrl = envProps.getProperty("footer.url");
%>
<BODY>
<DIV id="wideLayout">
	<!-- Begin Header Container -->
	<div id="headerContainer">
		<!-- BEGIN GLOBAL NAVIGATION -->
		<c:import url="<%=headerUrl%>" >
			<c:param name="firstName" value="<%=request.getHeader("firstName")%>" />
			<c:param name="lastName" value="<%=request.getHeader("lastName")%>" />
			<c:param name="queryString" value="${requestScope.queryString}" />
		</c:import>
		<%--<%@include file="/includes/header.jsp"%>--%>

        <!-- GLOBAL NAVIGATION END -->
    </div>
    <!-- End Header Container -->

	<div id="homepageWrapper">
		<div id="bodyWrapper">
			<div id="homepageContainer"> 
				
				<div>
					<div id="page_title2">
						<decorator:getProperty property="meta.mainTitle" />
					</div>
				</div>
				
				<div id="contentCol">
					<!-- BODY START -->
					<decorator:body />
					<!-- BODY END -->
				</div>	
			</div>
		</div>	</div>

    <!-- BEGIN FOOTER -->
     <c:import url="<%=footerUrl%>" ></c:import> 
    
    <%--<%@include file="/includes/footer.jsp"%>--%>
    
    <!-- FOOTER END -->

</DIV>

</BODY>
</HTML>