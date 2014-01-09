<%@page import="com.netpace.device.services.ApplicationPropertiesService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%

ApplicationContext webAppContext = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
ApplicationPropertiesService appPropsService =(ApplicationPropertiesService) webAppContext.getBean("applicationPropertiesService");

try {
	appPropsService.probeHit();
	out.println("HTTP-200");
	response.setStatus(200);		
}
catch (Exception e){
	out.println("HTTP-500");
	response.setStatus(500);	
}

%>