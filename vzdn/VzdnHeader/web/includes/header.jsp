<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ page import="com.netpace.vzdn.header.GlobalNavProperties"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    GlobalNavProperties globalNavPropsForHeader = GlobalNavProperties.getInstance();

	String VERSION_MAJOR=globalNavPropsForHeader.getProperty("MojorVersion");   
   	String VERSION_MINOR=globalNavPropsForHeader.getProperty("MinorVersion");   
   	String VERSION_PATCH=globalNavPropsForHeader.getProperty("PatchVersion");   
   	String BUILD_NUMBER=globalNavPropsForHeader.getProperty("BuildNumber");   
   	String VERSION_SEPERATOR = "_" ;
	String RESOURCE_VERSION="?"+VERSION_MAJOR+VERSION_SEPERATOR+VERSION_MINOR+VERSION_SEPERATOR+VERSION_PATCH+VERSION_SEPERATOR+BUILD_NUMBER;

    String headerPath = globalNavPropsForHeader.getProperty("global.url");
    String vzndcorePath = globalNavPropsForHeader.getProperty("vzdncore.url");
    String vzdnsitePath = globalNavPropsForHeader.getProperty("vzdnsite.url");

    String headerHomeUrl = globalNavPropsForHeader.getProperty("globalNav.home.url");
    String headerWirelessUrl = globalNavPropsForHeader.getProperty("globalNav.wirelessDev.url");
    String headerGoToUrl = globalNavPropsForHeader.getProperty("globalNav.goToMarket.url");
    String headerMyAccountUrl = globalNavPropsForHeader.getProperty("globalNav.myAccount.url");
    String supportURL = globalNavPropsForHeader.getProperty("globalNav.support");
    
    String headerSignInUrl = globalNavPropsForHeader.getProperty("signIn.url");
    String headerSignOutUrl = globalNavPropsForHeader.getProperty("signOut.url");
    String module=request.getParameter("module");
    String queryString=request.getParameter("queryString");
    String firstName=request.getParameter("firstName"); 
    String lastName=request.getParameter("lastName");
    String loggedInUser=request.getParameter("LOGGED_IN_USER");
	
    String virtualServerURL= request.getParameter("ALFRESCO_VIRTUAL_SERVER_URL");
    System.out.println("module: "+module);
	System.out.println("loggedInUser: "+loggedInUser);
    System.out.println("firstName: "+firstName);
    System.out.println("lastName: "+lastName);
    
    System.out.println("------------------------------------------------------------------------------------");
    System.out.println("VIRTUAL_SERVER_URL: "+virtualServerURL);
    
    if (virtualServerURL !=null && virtualServerURL.length() >0 && virtualServerURL.contains("www--sandbox")){
    	headerHomeUrl=virtualServerURL;
    	headerWirelessUrl=virtualServerURL+"/"+headerWirelessUrl;
    	headerGoToUrl=virtualServerURL+"/"+headerGoToUrl;
    	
    	headerMyAccountUrl=virtualServerURL;
    	supportURL=virtualServerURL+"/"+supportURL;

    	headerSignInUrl = headerHomeUrl+"/index.jsp?signout=false";
    	headerSignOutUrl = headerHomeUrl+"/index.jsp?signout=true";
    	
    }
    else {
    	headerWirelessUrl=vzdnsitePath+"/"+headerWirelessUrl;
    	headerGoToUrl=vzdnsitePath+"/"+headerGoToUrl;
    	supportURL=vzdnsitePath+"/"+supportURL;
    }
    
    System.out.println("headerHomeUrl: "+headerHomeUrl);
    System.out.println("headerWirelessUrl: "+headerWirelessUrl);
    System.out.println("headerGoToUrl: "+headerGoToUrl);
    System.out.println("headerMyAccountUrl: "+headerMyAccountUrl);
    System.out.println("supportURL: "+supportURL);
    
    System.out.println("----------------------------------------------------------------------------------------");
%>

<!-- GLOBAL HEADER START HERE -->

<script type="text/javascript">
function searchSite() {

	mypara = document.getElementById("_hiddenForm");	
	
	myform = document.createElement("form");
	myform.setAttribute("action", "<%=vzndcorePath%>/search?page=0");
	myform.setAttribute("method", "post");
	myform.setAttribute("id", "searchForm");
		
	queryField = document.createElement("INPUT");
	queryField.setAttribute("name", "q");
	queryField.setAttribute("value", document.getElementById('query').value);
		
	myform.appendChild(queryField);
	mypara.appendChild(myform);
	//alert(mypara.innerHTML);	
	if (document.getElementById('_ajaxcall')){
		document.forms[0].submit();
	}
	else {
		document.forms['searchForm'].submit();
	}

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
function loadFirstAndLastName(){
	var mytime= "<%=System.currentTimeMillis()%>";
	var URL = '/secure/logged-in-header.jsp?time='+mytime;	
	//jQuery("#_ajax_welcome_msg").load(URL);
	jQuery.get(URL, function(data){		
		if (data.indexOf('Welcome') != -1){
			document.getElementById('navHeaderLinks').innerHTML=data+"<li><a href='<%=headerSignOutUrl%>'>Sign out</a></li>"
		}
	});
}
</script>
		
		<SCRIPT type="text/javascript" src="<%=headerPath+"/scripts/jquery-1.3.2.js"+RESOURCE_VERSION%>"></SCRIPT>
		<script>jQuery.noConflict();</script>
        <SCRIPT type="text/javascript">var gn_iframe = '<%=headerPath+"/scripts/blank.htm"+RESOURCE_VERSION%>';</SCRIPT>        
		<LINK href="<%=headerPath+"/styles/globalnav.css"+RESOURCE_VERSION%>" media="screen,projection" type="text/css" rel="stylesheet"/>
		<SCRIPT type="text/javascript" src="<%=headerPath+"/scripts/gn_engine.js"+RESOURCE_VERSION%>"></SCRIPT>
		
			<!-- HEADER START -->	
			<DIV id="nav">		
			<DIV id="navHeader">
				<DIV id="navMainnav">
					
					<UL id="navHeaderLinks">
						<c:choose>
							<c:when test="${not empty param.firstName and not empty param.lastName}">								
								<li class='navFirstLink'><b>Welcome</b>&nbsp;<c:out value="${param.firstName}"></c:out>&nbsp;<c:out value="${param.lastName}"></c:out></li>							
								<li><a href='<%=headerSignOutUrl%>'>Sign out</a></li>								
							</c:when>
							<c:when test="${empty param.LOGGED_IN_USER}">
								<li class='navFirstLink'><a href='<%=headerSignInUrl%>'>Register</a></li>
								<li><a href="<%=headerSignInUrl%>">Sign In</a></li>
							</c:when>
						</c:choose>
					</UL>						
					<span id="getSignOutURL" style="visibility: hidden; display: none;"><%=headerSignOutUrl%></span>											
					<DIV class="clear"></DIV>
					<div id="navLogoNew">
						<div><a href="<%=headerHomeUrl%>" target="_parent"><img src="<%=headerPath+"/images/logo.gif"+RESOURCE_VERSION%>" alt="Verizon Wireless" width= "110" height="65" border="0"/></a></div>
						<div class="navSection"><a href="<%=headerHomeUrl%>" target="_parent">Verizon Developer Community</a></div>
					</div>
					<div id="navSearchFormNew">
						<div id="_hiddenForm" style="display: none;"></div>						
						<fieldset>
							<BUTTON class="navinput_primary navfloat_right" onclick="searchSite();"><SPAN><SPAN><SPAN>Search</SPAN></SPAN></SPAN></BUTTON>
							<input id="query" alt="Search Input" onkeydown="onEnterSubmitFilter(event);" maxlength="100" name="query" class="navinputField navmR5" value="<c:out value='${param.queryString}'/>">
						</fieldset>						
                    </div>
					<DIV class="clear"></DIV>
				</DIV>
				<DIV id="navHeaderSpacer"></DIV>
			</DIV>
	
			
			<!-- HEADER END -->
            <!-- TOP NAVIGATION START -->
			<DIV id="navGn_outside">
				<div id="gn">
					<a name="nav"></a>
					<h2>
						<div class="gn_left_end"></div>
						<a href="<%=headerHomeUrl%>" target="_parent"><span id="gn_home">Home</span></a>
					</h2>					
					<div class="gn_dd"><ul></ul></div>
					<h2><a href="<%=headerWirelessUrl%>" target="_parent"><span id="gn_wirelessDev">Dev Center</span></a></h2>					
					<div class="gn_dd"><ul></ul></div>
					<h2><a href="<%=headerGoToUrl%>" target="_parent"><span id="gn_gotoMarket">Go to Market</span></a></h2>					
					<div class="gn_dd"><ul></ul></div>
					<h2><a href="<%=headerMyAccountUrl%>" target="_parent"><span id="gn_myAccount">My Account</span></a></h2>
					<div class="gn_dd"><ul></ul></div>
					<h2><a href="<%=supportURL%>" target="_parent"><span id="gn_myAccount">Support</span></a></h2>
					<div class="gn_dd"><ul></ul></div>					
				</div>
			</DIV>	
			</DIV>		
			<!-- TOP NAVIGATION END -->
            <SCRIPT src="<%=headerPath+"/scripts/nav.js"+RESOURCE_VERSION%>" type="text/javascript"></SCRIPT>
			
<%if (module!=null && (module.equals("forum") || module.equals("blog"))){%>
	<script>loadFirstAndLastName();</script>
<%}else if (queryString!=null && queryString.length()>0){%>
	<script>loadFirstAndLastName();</script>
<%}else if (loggedInUser!=null && (loggedInUser.equals("true"))){%>
	<script>loadFirstAndLastName();</script>	
<%} %>
            
<!-- GLOBAL HEADER END HERE -->            