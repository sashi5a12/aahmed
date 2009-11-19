<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ page import="com.netpace.vzdn.util.GlobalNavProperties"%>
<%@page import="com.netpace.vzdn.util.ConfigEnvProperties"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    GlobalNavProperties globalNavPropsForHeader = GlobalNavProperties.getInstance();
    ConfigEnvProperties configEvnProps = ConfigEnvProperties.getInstance();
    String headerServerUrl = globalNavPropsForHeader.getProperty("server.url");
    String headerContextName = globalNavPropsForHeader.getProperty("vzdncore.contextName");
    String headerCorePath = headerServerUrl+"/"+headerContextName;//vzdn core path

    String headerHomeUrl = globalNavPropsForHeader.getProperty("globalNav.home.url");
    String headerWirelessUrl = globalNavPropsForHeader.getProperty("globalNav.wirelessDev.url");
    String headerGoToUrl = globalNavPropsForHeader.getProperty("globalNav.goToMarket.url");
    String headerMyAccountUrl = globalNavPropsForHeader.getProperty("globalNav.myAccount.url");
    
    String headerSignInUrl = globalNavPropsForHeader.getProperty("signIn.url");
    String headerSignOutUrl = globalNavPropsForHeader.getProperty("signOut.url");

    String headerBlogUrl = globalNavPropsForHeader.getProperty("globalNav.support.blog.url");
    String headerForumUrl = globalNavPropsForHeader.getProperty("globalNav.support.forum.url");
    String headerFaqUrl = globalNavPropsForHeader.getProperty("globalNav.support.faq.url");
    String headerDevCareUrl = globalNavPropsForHeader.getProperty("globalNav.support.devCare.url");
    String headerCommonIssuesUrl = globalNavPropsForHeader.getProperty("globalNav.support.commonIssues.url");
    
    String authCookieName=configEvnProps.getProperty("opensso.cookie.name");
    
    String virtualServerURL= request.getParameter("ALFRESCO_VIRTUAL_SERVER_URL");
    
    System.out.println("VIRTUAL_SERVER_URL: "+virtualServerURL);
    
    if (virtualServerURL !=null && virtualServerURL.length() >0 && virtualServerURL.contains("www--sandbox")){
    	headerHomeUrl=virtualServerURL;
    	headerWirelessUrl=virtualServerURL+"/"+headerWirelessUrl;
    	headerGoToUrl=virtualServerURL+"/"+headerGoToUrl;
    	
    	headerMyAccountUrl=virtualServerURL;
    	headerBlogUrl=virtualServerURL;
    	headerForumUrl=virtualServerURL;
    }
    else {
    	headerWirelessUrl=headerServerUrl+"/"+headerWirelessUrl;
    	headerGoToUrl=headerServerUrl+"/"+headerGoToUrl;
    }
    
    System.out.println("headerHomeUrl: "+headerHomeUrl);
    System.out.println("headerWirelessUrl: "+headerWirelessUrl);
    System.out.println("headerGoToUrl: "+headerGoToUrl);
%>
<script type="text/javascript">
function searchSite() {

	mypara = document.getElementById("_hiddenForm");	
	
	myform = document.createElement("form");
	myform.setAttribute("action", "<%=headerCorePath%>/search?page=0");
	myform.setAttribute("method", "post");
	myform.setAttribute("id", "searchForm");
		
	queryField = document.createElement("INPUT");
	queryField.setAttribute("name", "q");
	queryField.setAttribute("value", document.getElementById('query').value);
		
	myform.appendChild(queryField);
	mypara.appendChild(myform);
	//alert(mypara.innerHTML);	
	document.forms['searchForm'].submit();

}
function onEnterSubmitFilter(e) {
	    if (e.which || e.keyCode) {
	        if ((e.which == 13) || (e.keyCode == 13)) {
	            searchSite();
	            return false;
	        }
	    }
	    return true;
}

function displayParamWelcomMessage(){
	var firstName='<c:out value="${param.firstName}"></c:out>';
	var lastName='<c:out value="${param.lastName}"></c:out>';
	var message="<li class='navFirstLink'><b>Welcome</b>&nbsp;"+ firstName +"&nbsp;"+lastName+"</li>";							
	message =message +"<li><a href='<%=headerSignOutUrl%>'>Sign out</a></li>";
	//alert(message);							
	document.getElementById('welcom_span').innerHTML=message;
}

function displayWelcomMessage(){
		ajaxCall('<%=request.getContextPath()+"/loggedin/logged-in-header.jsp"%>','raw');
}
//create the Cross-browser XMLHttpRequest object
function ajaxCall(pURL,pFunc) {    
	if (window.XMLHttpRequest) { // code for Mozilla, Safari, etc         
		xmlhttp=new XMLHttpRequest();        
		eval('xmlhttp.onreadystatechange='+pFunc+';');        
		xmlhttp.open("POST", pURL, true); // leave true for Gecko        
		xmlhttp.send(null);    
	} 
	else if (window.ActiveXObject) { //IE         
		xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');         
		if (xmlhttp) {            
			eval('xmlhttp.onreadystatechange='+pFunc+';');            
			xmlhttp.open('POST', pURL, false);            
			xmlhttp.send();        
		}
	}
}	
function raw() {    
	if (xmlhttp.readyState==4) {         
		if (xmlhttp.status==200) {
			//alert(xmlhttp.responseText);             
			document.getElementById('welcom_span').innerHTML=xmlhttp.responseText;
			//document.getElementById('welcom_span').style.display='';        
		}    
	}
}	
</script>

        <%--<SCRIPT type=text/javascript>var gn_iframe = '<%=headerCorePath+"/scripts/blank.htm"%>';</SCRIPT>--%>        
		<LINK href="<%=headerCorePath+"/styles/globalnav.css"%>" media="screen,projection" type="text/css" rel="stylesheet"/>
		<SCRIPT type="text/javascript" src="<%=headerCorePath+"/scripts/gn_engine.js"%>"></SCRIPT>


			<!-- HEADER START -->	
			<DIV id="nav">		
			<DIV id="navHeader">
				<DIV id="navMainnav">
					
					<UL id="navHeaderLinks">
						<span id="welcom_span">							
							<li class='navFirstLink'><a href="<%=headerSignInUrl%>">Sign In</a></li>							
						</span>
					</UL>						
																
					<DIV class="clear"></DIV>
					<div id="navLogoNew">
						<div><a href="<%=headerHomeUrl%>" target="_parent"><img src="<%=headerCorePath+"/images/logo.gif"%>" alt="Verizon Wireless" width= "110" height="65" border="0"/></a></div>
						<div class="navSection"><a href="<%=headerHomeUrl%>">Verizon Developer Community</a></div>
					</div>
					<div id="navSearchFormNew">
						<div id="_hiddenForm" style="display: none;"></div>						
						<fieldset>
							<BUTTON class="navinput_primary navfloat_right" onclick="searchSite();"><SPAN><SPAN><SPAN>Search</SPAN></SPAN></SPAN></BUTTON>
							<input id="query" alt="Search Input" onkeydown="onEnterSubmitFilter(event);" maxlength="100" name="query" class="navinputField navmR5" value="<c:out value='${param.q}'></c:out>">
						</fieldset>						
                    </div>
					<DIV class="clear"></DIV>
				</DIV>
				<DIV id="navHeaderSpacer"></DIV>
			</DIV>
	
			
			            <!-- HEADER END -->
            <!-- _parent NAVIGATION START -->
			<DIV id="navGn_outside">
				<div id="gn">
					<a name="nav"></a>
					<h2>
						<div class="gn_left_end"></div>
						<a href="<%=headerHomeUrl%>" target="_parent"><span id="gn_home">Home</span></a>
					</h2>
					<div class="gn_dd"><ul></ul></div>
					<h2><a href="<%=headerWirelessUrl%>" target="_parent"><span id="gn_wirelessDev">Wireless Dev</span></a></h2>
					<div class="gn_dd"><ul></ul></div>
					<h2><a href="<%=headerGoToUrl%>" target="_parent"><span id="gn_gotoMarket">Go to Market</span></a></h2>
					<div class="gn_dd"><ul></ul></div>
					<h2><a href="<%=headerMyAccountUrl%>" target="_parent"><span id="gn_myAccount">My Account</span></a></h2>
					<div class="gn_dd"><ul></ul></div>
					<h2><a href="#" target="_parent"><span id="gn_support">Support</span></a></h2>
					<div class="gn_dd">
						<ul>
							<li><a href="<%=headerBlogUrl%>" target="_parent">Blog</a></li>
							<li><a href="<%=headerForumUrl%>" target="_parent">Forum</a></li>
							<li><a href="<%=headerFaqUrl%>" target="_parent">FAQ</a></li>
							<li><a href="<%=headerDevCareUrl%>" target="_parent">Development Care</a></li>
							<li><a href="<%=headerCommonIssuesUrl%>" target="_parent">Common Issues</a></li>
						</ul>
					</div>
				</div>
			</DIV>	
			</DIV>		
			<!-- TOP NAVIGATION END -->
            <SCRIPT src="<%=headerCorePath+"/scripts/nav.js"%>" type="text/javascript"></SCRIPT>

<c:choose>
	<c:when test="${not empty param.firstName and not empty param.lastName}"><script>displayParamWelcomMessage();</script></c:when>
	<c:otherwise><script>displayWelcomMessage();</script></c:otherwise>
</c:choose>