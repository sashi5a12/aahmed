<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- POD START HERE --> 
<div class="box784 float_left"> 
    <div class="box2"> 
      <div class="boxTop box784"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLT.jpg" width="6" height="6"></div> 
      <div class="boxContent"> 
        <!-- POD CONTENT START HERE --> 
        <div class="pR10"> 
          <div class="clear10"></div> 
          <div id="content"> 
            <#list root.devices as d>
            <div class="listProPod" style="float:left;"> 
              <div><img src="<%=request.getContextPath()%>${d.deviceImage}" width="75" height="148" alt="${d.deviceImageAlt}"></div> 
              <div class="proDetail"><a href="<%=request.getContextPath()%>" class="smallLink">${d.deviceDisplayName}</a><br />
                <a href="<%=request.getContextPath()%>${d.link}" class="smallLink">View Details</a></div> 
            </div> 
            </#list>
            <div class="clear14"></div> 
          </div> 
        </div> 
        <!-- POD CONTENT END HERE --> 
      </div> 
      <div class="boxBottom"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLB.jpg" width="6" height="6"></div> 
    </div> 
  </div>
<!-- POD END HERE -->