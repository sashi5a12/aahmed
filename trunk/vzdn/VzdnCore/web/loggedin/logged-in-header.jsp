<%@page import="com.netpace.vzdn.util.GlobalNavProperties"%>
<%@page import="com.netpace.vzdn.util.ConfigEnvProperties"%>
<%
    GlobalNavProperties globalNavPropsForHeader = GlobalNavProperties.getInstance();
    ConfigEnvProperties configEvnProps = ConfigEnvProperties.getInstance();
    String headerServerUrl = globalNavPropsForHeader.getProperty("server.url");
    String headerContextName = globalNavPropsForHeader.getProperty("vzdncore.contextName");
    String headerCorePath = headerServerUrl+"/"+headerContextName;//vzdn core path
    
    String headerSignInUrl = headerCorePath+"/"+globalNavPropsForHeader.getProperty("signIn.url");
    String headerSignOutUrl = globalNavPropsForHeader.getProperty("signOut.url");
    
    String authCookieName=configEvnProps.getProperty("opensso.cookie.name");
    
%>

<%
	Cookie[] requestCookies = request.getCookies();
	boolean isUserAuthenticated=false;
	System.out.println("<<<<<<<<<<<<<<<<<<<<"+requestCookies+">>>>>>>>>>>>>>>>>>>>");
	if (requestCookies != null && requestCookies.length > 0){
		for (int i = 0; i < requestCookies.length; i++) {
			Cookie cookie = requestCookies[i];
			if (cookie.getName().equals(authCookieName)) {
				isUserAuthenticated=true;
				break;
			}
		}
	}
%>
<%if (isUserAuthenticated){ %>
	<%System.out.println("Authenticated.......................");%>
	<li class="navFirstLink"><b>Welcome</b> <%=request.getHeader("firstName")%>&nbsp;<%=request.getHeader("lastName") %></li>							
	<li><a href="<%=headerSignOutUrl%>">Sign out</a></li>							
<%}else { System.out.println("Non-Authenticated.......................");%>
	<li class='navFirstLink'><a href="<%=headerSignInUrl%>">Sign In</a></li>
<%}%>
 