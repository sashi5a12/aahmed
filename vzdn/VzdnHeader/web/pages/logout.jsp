<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.netpace.vzdn.header.GlobalNavProperties"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	GlobalNavProperties props = GlobalNavProperties.getInstance();

    String signoutLinks = props.getProperty("signout.links");
    String openssoSignOut = props.getProperty("opensso.signout.url");
    String vzdnsiteURL = props.getProperty("vzdnsite.url");
    
    String[] linksArray=signoutLinks.split(",");
%>    
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript">
		window.onload = gotoHome;
		function signout(url){
			call(url,'output');
		}
		//create the Cross-browser XMLHttpRequest object
		function call(pURL,pFunc) {    
			if (window.XMLHttpRequest) { // code for Mozilla, Safari, etc         
				xmlhttp=new XMLHttpRequest();        
				eval('xmlhttp.onreadystatechange='+pFunc+';');        
				xmlhttp.open("GET", pURL, true); // leave true for Gecko        
				xmlhttp.send(null);    
			} 
			else if (window.ActiveXObject) { //IE         
				xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');         
				if (xmlhttp) {            
					eval('xmlhttp.onreadystatechange='+pFunc+';');            
					xmlhttp.open('GET', pURL, false);            
					xmlhttp.send();        
				}
			}
		}	
		function output() {    
			if (xmlhttp.readyState==4) {         
				if (xmlhttp.status==200) {             
					//xmlhttp.responseText;
				}    
			}
		}
		function gotoHome(){
			window.location="<%=vzdnsiteURL%>";
		}
	</script>
  </head>
  
  <body>
	<% 
		if (linksArray != null && linksArray.length >0){
		for(int i=0; i<linksArray.length; i++){
			System.out.println("Signing out....... "+linksArray[i]);%>
				<IFRAME SRC="<%=linksArray[i]%>" WIDTH="0" HEIGHT="0" scrolling="no" style="display: none;"></IFRAME>
	    	<%}
	    }
	%>      
  </body>
</html>
