<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">

<jsp:include page="/jsps/layout/header.jsp" >
	<jsp:param name="title" value="${root.page_title}" />
</jsp:include>

<BODY>
<DIV id="wideLayout">
	<!-- Begin Header Container -->
	<div id="headerContainer">
		<!-- BEGIN GLOBAL NAVIGATION -->		
		<c:import url="<%=(String)request.getAttribute("header")%>"></c:import>
        <!-- GLOBAL NAVIGATION END -->
    </div>
    <!-- End Header Container -->
    <span id="for_title" style='display: none; visibility: hidden'>$TITLE_START$ ${root.page_title} $TITLE_END$</span>	

	  <!-- BODY START --> 
	  <div id=homepageWrapper > 
	    <div id=bodyWrapper> 
	      <div id=homepageContainer><a name=content></a> 
	        <div> 
	          <div id="page_title2"> 
	            <p> Device Filter</p> 
	          </div> 
	        </div> 
	        <div class="pL16"> 
	          <H3 class=head_tblhead>Choose a device you want to develop for.</H3> 
	        </div> 
	        <div class="clear14"></div> 
	        <div id="contentCol"> 
	          <div class="box190 float_left mR9"> 
	            <!-- POD START HERE --> 
	            <div class="box190"> 
	              <div class="box2"> 
	                <div class="boxTop box190"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLT.jpg" width="6" height="6"></div> 
	                <div class="boxContent"> 
	                  <!-- POD CONTENT START HERE --> 
	                  <div class="pR10"> 
	                    <div class="subHeadBlack">Phone/Device Filter</div> 
	                    <div class="txtContent"> <br /> 
	                      <!-- ACCORDIAN MENU START HERE --> 
	                      <div class="columnFiltered"> 
	                        
	                        <#list root.filters as f>
	                        <p id="${f_index}_expande" style="display:none;" class="accorStyle"><a href="javascript: toggleMenu(${f_index});" class="dwrLink viewMoreLink boldLink"><b>${f.main_menu}</b></a></p> 
	                        <p id="${f_index}_close" style="display:block;" class="accorStyle"><a href="javascript: toggleMenu(${f_index});" class="dwrLink closeExpandedLink boldLink"><b>${f.main_menu}</b></a></p> 
	                        
	                        <ul class="linkList" id="${f_index}" style="display:block; margin-top:5px !important;">
	                          <#list f.sub_menus as sm> 
	                          <li><a href="<%=request.getContextPath()%>${sm.link}" class="dwrLink" onClick="displayDevices(this);return false;">${sm.name}</a></li>
	                          </#list> 
	                        </ul> 
	                        </#list>
	                        
	                      </div> 
	                      <!-- ACCORDIAN MENU END HERE --> 
	                      <div class="clear14"></div> 
	                    </div> 
	                  </div> 
	                  <!-- POD CONTENT END HERE --> 
	                </div> 
	                <div class="boxBottom"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLB.jpg" width="6" height="6"></div> 
	              </div> 
	            </div> 
	            <!-- POD END HERE --> 
	          </div> 
	          
	          <span id="device_placement"></span>
	        </div> 
	      </div> 
	    </div> 
	  </div> 
	  <!-- BODY END --> 
	<!-- BEGIN FOOTER -->
    <c:import url="<%=(String)request.getAttribute("footer")%>" ></c:import>
    <!-- FOOTER END -->

</DIV>

</BODY>
</HTML>
<script type="text/javascript">
	function displayDevices(obj){
		//alert(obj);
		getFile(obj,'raw');
	}
	//create the Cross-browser XMLHttpRequest object
	function getFile(pURL,pFunc) {    
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
	function raw() {    
		if (xmlhttp.readyState==4) {         
			if (xmlhttp.status==200) {             
				document.getElementById('device_placement').innerHTML=xmlhttp.responseText;        
			}    
		}
	}	
</script>
