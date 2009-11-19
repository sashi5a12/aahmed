<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.netpace.vzdn.utils.ConfigEnvProperties"%>

<%
	String VERSION_MAJOR="2";   
   	String VERSION_MINOR="0";   
   	String VERSION_PATCH="0";   
   	String VERSION_SEPERATOR = "_" ;
   	String BUILD_NUMBER="0";   
	String RESOURCE_VERSION="?"+VERSION_MAJOR+VERSION_SEPERATOR+VERSION_MINOR+VERSION_SEPERATOR+VERSION_PATCH+VERSION_SEPERATOR+BUILD_NUMBER;
	request.setAttribute("RESOURCE_VERSION",RESOURCE_VERSION);
	
    ConfigEnvProperties envProps = ConfigEnvProperties.getInstance();
    String serverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    String headerUrl = envProps.getProperty("header.url");
    String footerUrl = envProps.getProperty("footer.url");    
    String loggedInUser=(String)session.getAttribute("IS_USER_AUTHENTICATED");
    
    if (loggedInUser != null && loggedInUser.trim().length()>0 && request.getServerName().contains("www--sandbox")==false){
    	headerUrl=headerUrl+"?LOGGED_IN_USER=true";
    }
    else  if (loggedInUser != null && loggedInUser.trim().length()>0 && request.getServerName().contains("www--sandbox")){
    	headerUrl=headerUrl+"?firstName=Preview&lastName=Server";
    }
    
    request.setAttribute("header",headerUrl);
    request.setAttribute("footer",footerUrl);
    
    String hideMainContentText="To view this page, please sign in.";
    String signinRequiredText="To access this resource, please sign in.";     
    request.setAttribute("HIDE_MAIN_CONTENT",hideMainContentText);
    request.setAttribute("SIGNIN_REQUIRED_TEXT",signinRequiredText);
    //System.out.println("\n\n-------------------------VZDN Site URLs Start-------------------------------------------");
    //System.out.println("Header URL: "+headerUrl); 
    //System.out.println("Footer URL: "+footerUrl); 
    //System.out.println("Server URL: "+serverURL); 
	//System.out.println("-------------------------VZDN Site URLs End-------------------------------------------\n\n");
	
	if(request.getServerName().contains("www--sandbox")){
		request.setAttribute("ALFRESCO_VIRTUAL_SERVER_URL",serverURL);
	}
	
%>

<HEAD>

<TITLE><c:out value="${param.title}"></c:out></TITLE>
	
	<META content='<c:out value="${param.keywords}"></c:out>' name="Keywords"/>
	<META content="Verizon Wireless offers cell phones, pdas, devices, cell phone plans, data plans, and accessories with affordable, reliable wireless service for your personal needs." name="description"/>
	
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<!--<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="Expires" CONTENT="-1">
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">-->
	
	<LINK href="<%=request.getContextPath()%>/styles/layout.css<%=RESOURCE_VERSION%>" type="text/css" rel="stylesheet"/>
	<LINK href="<%=request.getContextPath()%>/styles/fonts.css<%=RESOURCE_VERSION%>" type="text/css" rel="stylesheet"/>
	<LINK href="<%=request.getContextPath()%>/styles/accessiblePrint.css<%=RESOURCE_VERSION%>" media="print" type="text/css" rel="stylesheet"/>
	<LINK href="<%=request.getContextPath()%>/styles/code_standards.css<%=RESOURCE_VERSION%>" rel="stylesheet" type="text/css" />

	<link href="<%=request.getContextPath()%>/scripts/jquery.jcarousel.css<%=RESOURCE_VERSION%>" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/styles/skins/tango/skin.css<%=RESOURCE_VERSION%>" rel="stylesheet" type="text/css" />
	
	<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/gn_engine.js<%=RESOURCE_VERSION%>"></SCRIPT>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/togglers.js<%=RESOURCE_VERSION%>"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/accordian.js<%=RESOURCE_VERSION%>"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.3.2.min.js<%=RESOURCE_VERSION%>"></script>
	<script>jQuery.noConflict();</script>
			
</HEAD>