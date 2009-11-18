<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/jsps/layout/header.jsp">
	<jsp:param name="title" value="${root.page_title}" />
</jsp:include>
<span id="for_title" style='display: none; visibility: hidden'>$TITLE_START$ ${root.page_title} $TITLE_END$</span>

<!-- BODY START --> 
  <div id=homepageWrapper > 
    <div id=bodyWrapper> 
      <div id=homepageContainer> 
        <!-- SPOTLIGHT START HERE --> 
        <div id="page_title"><p>${root.heading}</p></div> 
		<span id="id_spotlight" style="display: none;"></span>        
        <!--<div class="clear14"></div>--> 
        <div id="contentCol">
          <div class="rbox float_left mR10"> 
            <div class="rbox642" style=""> 
              <div id="togglers" class="tab_holder"> 
                <ul>
                  <#list root.tabs as t> 
                  <li><a href="#${t_index}" <#if t_index==0> class="active"</#if>><span><span><span>${t.name}</span></span></span></a></li> 
                  </#list> 
                </ul> 
              </div>               
              <#list root.tabs as t> 
              <div id="${t_index}" name="${t_index}" class="toggled <#if (t_index>0)>hidden</#if>"> 
                <div id="tabContainer"> 
                  <div class="tabcoL1"><span class="boldText">${t.col1}</span></div> 
                  <div class="tabcoL2"><span class="boldText">${t.col2}</span></div> 
                </div> 
              </div> 
              </#list>              
            </div> 
          </div> 
          <div class="box321 float_left"> 
            <#list root.pods as p>
            <!-- POD START HERE --> 
            <div class="box321"> 
              <div class="box2"> 
                <div class="boxTop box321"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLT.jpg" width="6" height="6"></div> 
                <div class="boxContent"> 
                  <!-- POD CONTENT START HERE --> 
                  <div class="boxContBM"> 
                    <div class="subHeadBlack">${p.title}</div> 
                    <div class="txtContent"><br />
                     
                      <#list p.links as l>
                      <a href="<%=request.getContextPath()%>${l.link}">${l.link_text}</a><#if l_has_next><br/><#else><br/><br/></#if> 
                      </#list> 
                      <img src="<%=request.getContextPath()%>/images/shared/elements/arrow_button.gif" width="6" height="7" align="absmiddle">&nbsp;<a href="${p.explore_link}">Explore</a></div> 
                  </div> 
                  <!-- POD CONTENT END HERE --> 
                </div> 
                <div class="boxBottom"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLB.jpg" width="6" height="6"></div> 
              </div> 
            </div> 
            <!-- POD END HERE --> 
            <#if p_has_next>
            <div class="clear14"></div>
            </#if> 
            </#list>
          </div> 
        </div> 
        <!-- VIDEO SLIDER START --> 
        <!-- VIDEO SLIDER END --> 
      </div> 
    </div> 
  </div> 
  <!-- BODY END -->
<jsp:include page="/jsps/layout/footer.jsp" />
<script type="text/javascript">
	var context='<%=request.getContextPath()%>';
	var txt_spotlight="${root.spotlight_html?replace("\"","'")}";	
	
	txt_spotlight=txt_spotlight.replace(/&quot;/gi, '"');
	txt_spotlight=txt_spotlight.replace(/&#39;/gi, "'");
	txt_spotlight=txt_spotlight.replace(/&amp;/gi, '&');
	txt_spotlight=txt_spotlight.replace(/&lt;/gi, '<');
	txt_spotlight=txt_spotlight.replace(/&gt;/gi, '>');
	txt_spotlight=txt_spotlight.replace(/&#92;/gi, '\\');
	
	document.getElementById('id_spotlight').innerHTML = txt_spotlight;
	document.getElementById('id_spotlight').style.display='';		
</script>
