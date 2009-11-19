<%@page import="com.netpace.vzdn.utils.ConfigEnvProperties"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    ConfigEnvProperties configEvnProps = ConfigEnvProperties.getInstance();    
    String authCookieName=configEvnProps.getProperty("sso.cookie.name");
    
%>

<%
	Cookie[] requestCookies = request.getCookies();
	boolean isUserAuthenticated=false;
	if (requestCookies != null && requestCookies.length > 0){
		for (int i = 0; i < requestCookies.length; i++) {
			Cookie cookie = requestCookies[i];
			if (cookie.getName().equals(authCookieName)) {
				isUserAuthenticated=true;
				break;
			}
		}
	}
	
	if (isUserAuthenticated){
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");	
		System.out.println("\n\n-------------------------User Info-------------------------------------------");	
		System.out.println("Welcome "+request.getHeader("firstName") + " "+ request.getHeader("lastName"));
		System.out.println("vzdn-user-type: "+request.getHeader("vzdn-user-type"));
		System.out.println("vzdn-user-roles: "+request.getHeader("vzdn-user-roles"));
		System.out.println("gtm_company_id: "+request.getHeader("gtm_company_id"));
		System.out.println("vzdn-partner-organization: "+request.getHeader("vzdn-partner-organization"));
		System.out.println("Time: "+sdf.format(new Date()));
		System.out.println("-----------------------------------------------------------------------------");
		%>
		<li class='navFirstLink'><b>Welcome</b>&nbsp;<%=request.getHeader("firstName")%>&nbsp;<%=request.getHeader("lastName")%></li> 
	<%}%>
	
 