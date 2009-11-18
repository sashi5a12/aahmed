<%@	page import="org.apache.struts.validator.DynaValidatorForm"%>
<%@page import="com.netpace.aims.model.masters.AimsIndustryFocu"%>
<%@page import="java.util.Iterator"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<jsp:useBean id="allIndustryFocus" class="java.util.Collection"	scope="request"	/>

<html>


<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>

    <script language="JavaScript" src="js/aims.js"></script>
    <script language="JavaScript" src="js/calendar.js"></script>
    <script language="JavaScript" src="js/tjmlib.js"></script>





<script	language="javascript">
<!--

function newImage(arg) {
	if (document.images) {
		rslt = new Image();
		rslt.src = arg;
		return rslt;
	}
}

function changeImages()	{
	if (document.images	&& (preloadFlag	== true))	{
		for	(var i=0;	i<changeImages.arguments.length; i+=2) {
			document[changeImages.arguments[i]].src	=	changeImages.arguments[i+1];
		}
	}
}

var	preloadFlag	=	false;
function preloadImages() {
	if (document.images) {
		icon1_over = newImage("images/icon1-over.gif");
		icon2_over = newImage("images/icon2-over.gif");
		icon3_over = newImage("images/icon3-over.gif");
		icon4_over = newImage("images/icon4-over.gif");
		icon5_over = newImage("images/icon5-over.gif");
		icon6_over = newImage("images/icon6-over.gif");
		icon7_over = newImage("images/icon7-over.gif");
		icon8_over = newImage("images/icon8-over.gif");
		icon1_text = newImage("images/icon_doctype1.gif");
		icon2_text = newImage("images/icon_doctype2.gif");
		icon3_text = newImage("images/icon_doctype3.gif");
		icon4_text = newImage("images/icon_doctype4.gif");
		icon5_text = newImage("images/icon_doctype5.gif");
		icon6_text = newImage("images/icon_doctype6.gif");
		icon7_text = newImage("images/icon_doctype7.gif");
		icon8_text = newImage("images/icon_doctype8.gif");
		preloadFlag	=	true;
	}
}


preloadImages();

//-->
</script>
<DIV class="homeColumnTab lBox">

<div class="contentbox">
<html:messages id="message"	message="true" header="messages.header"	footer="messages.footer">
			<li><bean:write	name="message"/></li>
</html:messages>

<table width="100%"	height="0%"	 border="1"	cellpadding="0"	cellspacing="0">
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>Spotlight view</H1>
            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
   
</tr>
	<tr>
		<td	width="100%" valign="top">
			<table width="100%"	 border="1"	cellspacing="0"	cellpadding="0" class="GridGradient">
				<%@	include	 file="/common/error.jsp"	%>
				<tr>
					<td	 valign="top">
						<logic:empty name="spotLightInfo">
							<span	class="errorText">Sorry! No	Information	Found</span>
						</logic:empty>
						<logic:notEmpty	name="spotLightInfo">
							<bean:define id="spotLight"	name="spotLightInfo" scope="request"/>
							<bean:define id="partnerDetail"	name="spotLight" property="partnerDetail"	type="com.netpace.aims.controller.application.PartnerDetailBean"	/>
                            <bean:define id="solutionDetail" name="spotLight" property="solutionDetail"   type="com.netpace.aims.controller.application.JMASolutionDetailBean"   />
                            
                            <logic:empty name="solutionDetail"   property="bdsName">
                                <span   class="errorText">Sorry! No Information Found</span>
                            </logic:empty>
                            <logic:notEmpty name="solutionDetail"   property="bdsName">
                                <% AimsIndustryFocu	bdsIndustryFocus	=	null;	%>
    							
    
    							<table width="100%"	 border="0"	cellspacing="0"	cellpadding="0">
    									<tr>
    									<td width="30%">
    										<table width="100%"	 border="0"	cellpadding="7"	cellspacing="0"	class="GridGradient">
    											<logic:notEmpty	name="partnerDetail">
    											<tr	class="totalbottomcopy">
    												<td valign="top">
    												
    													<a target="_blank" href='/aims/resourceAction.do?resource=companyLogo&object=AimsAllianc&alliance_id=<bean:write	name="partnerDetail"	property="partnerId" />'>
    													<img src="/aims/resourceAction.do?resource=companyLogo&object=AimsAllianc&alliance_id=<bean:write	name="partnerDetail"	property="partnerId" />" border="0"	height="150" width="125">
    													</a>
    												 
    													<br/><br/>
    													<b><bean:write	name="partnerDetail" property="partnerName"	/></b>
    													<br/><bean:write name="partnerDetail"	property="streetAddress1"	/>
    													<br/><bean:write name="partnerDetail"	property="streetAddress2"	/>
    													<br/><bean:write name="partnerDetail"	property="city"	/>,&nbsp;<bean:write name="partnerDetail"	property="state" />&nbsp;<bean:write name="partnerDetail"	property="zipCode" />
    													<br/><a	target="_blank"	href="http://<bean:write name="partnerDetail"	property="webSiteUrl"	/>"><bean:write	name="partnerDetail"	property="webSiteUrl"	/></a>
    													<br/><br/>
    													
    													<bean:write name="solutionDetail" property="solutionContactFirstName" />&nbsp;<bean:write name="solutionDetail" property="solutionContactLastName" /><br/>
														<bean:write name="solutionDetail" property="solutionContactTitle" /><br/>
														<bean:write name="solutionDetail" property="solutionContactEmailAddress" /><br/>
														<bean:write name="solutionDetail" property="solutionContactPhone" /><br/>
    													
    													<br/><br/>
    													<logic:notEqual name="solutionDetail" property="qrgSpotLightId" value="0">
    														<a class="a" target="_blank" href='/aims/entAppsSLResourceAction.do?resource=spotlightFile&resourceId=<bean:write name="solutionDetail" property="qrgSpotLightId" />'><img src="images/download_qrg.gif" border="0" align="middle"></a>
                                                        </logic:notEqual>
                                                        <logic:equal name="solutionDetail" property="qrgSpotLightId" value="0">
							                            	<img src="images/download_qrg.gif" border="0" title="QRG not found." align="middle">
							                            </logic:equal>  
    													<br/><br/>
    													
    												</td>
    												<td	valign="top" width="70%" style="vertical-align: top;">
    												
    													<table width="100%" class="GridGradient">
	    											
                                                            <tr>
    															<td class="pageheading"><br>
                                                                    <Strong><bean:write name="solutionDetail"   property="bdsName" /></Strong><br/>                                                   
                                                                               
    															</td>
    														</tr>
    															<td>
    																<bean:write name="solutionDetail" property="longDesc" />
    															</td>
    														<tr>
    														</tr>
                                                            <tr>
                                                                <td>
                                                                    <table width="100%"  border="0" cellpadding="7" cellspacing="0" class="totalbottomcopyBold">
                                                                        <tr class="totalbottomcopy">
                                                                           
                                                                            <td valign="top" width="50%">
                                                                                <b>Targeted Verticals</b><br/>
                                                                                <% for (Iterator it =   allIndustryFocus.iterator(); it.hasNext();) {
                                                                                       bdsIndustryFocus   =   (AimsIndustryFocu)  it.next();
                                                                                %>
                                                                                    <logic:iterate id="formIndFocuses" name="solutionDetail" property="indFocuses">
                                                                                        <logic:equal name="formIndFocuses" value="<%=bdsIndustryFocus.getIndustryId().toString()%>">
                                                                                            <%=bdsIndustryFocus.getIndustryName()%></br>
                                                                                        </logic:equal>
                                                                                    </logic:iterate>
                                                                                <% } %> &nbsp;
                                                                            </td>
                                                                            <td valign="top" width="50%">
                                                                              &nbsp;
                                                                            </td>
                                                                        </tr>
                                                                    
                                                                </table>                                                            </td>
                                                            </tr>
    														<tr>
    															<td>
    																<img src="images/space.gif"	width=331	height=8 alt="">
    															</td>
    														</tr>
                                                        
    													</table>
    												</td>
    											</tr>											
    											</logic:notEmpty>
    										</table>
    									</td>
    								</tr>
    							</table>
                            </logic:notEmpty>        
						</logic:notEmpty>
					</td>
				</tr>
				
			
				<!--  Solution Material Part - Starts -->
				
		                        
    							</table>
				
				
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>Solution Materials</H1>
            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
   
</tr>				
	<!--  Solution Material Part - Starts -->
<tr>
<td>

				<table width="100%"	 border="0"	cellpadding="7"	cellspacing="0"	>
                                              
                                                <tr>
                                                    <td valign="middle" colspan=2   class="totalbottomcopy">
                                                        <img src="images/space.gif" width=67 height=25 alt=""   name="icontext">
                                                    </td>
                                                    <td>
                                                        <img src="images/left_arrow.gif" width=12   height=36>
                                                    </td>
                                                    <logic:iterate id="spotLightCount" name="solutionDetail" property="spotLightCounts">
                                                        <logic:equal name="spotLightCount" property="key" value="1">                                                            
                                                            <td align="right" valign="middle">
                                                                <logic:equal name="spotLightCount" property="value" value="0">
                                                                    <img name="icon1" src="images/icon1-gray.gif"   width="27" height="27" border="0"/>
                                                                </logic:equal>
                                                                <logic:notEqual name="spotLightCount" property="value" value="0">
                                                                    <a href="/aims/entAppSetupSpotlight.do?task=view&spotLightTypeId=1&solutionId=<bean:write name="solutionDetail" property="solutionId" />"
                                                                        onMouseOver="changeImages('icon1', 'images/icon1-over.gif'); changeImages('icontext', 'images/icon_doctype1.gif'); return true;"
                                                                        onMouseOut="changeImages('icon1', 'images/icon1<logic:equal name="spotLightTypeId" scope="request" value="1">-over</logic:equal>.gif'); changeImages('icontext', 'images/space.gif'); return true;">
                                                                        <img name="icon1" src="images/icon1<logic:equal name="spotLightTypeId" scope="request" value="1">-over</logic:equal>.gif"   width=27 height=27 border=0/>
                                                                    </a>
                                                                </logic:notEqual>
                                                            </td>
                                                        </logic:equal>
                                                    </logic:iterate>    
                                                    
                                                    <logic:iterate id="spotLightCount" name="solutionDetail" property="spotLightCounts">
                                                        <logic:equal name="spotLightCount" property="key" value="2">
                                                            <td align="right"   valign="middle">
                                                                <logic:equal name="spotLightCount" property="value" value="0">
                                                                    <img name="icon1" src="images/icon4-gray.gif"   width=27 height=27 border=0/>
                                                                </logic:equal>
                                                                <logic:notEqual name="spotLightCount" property="value" value="0">
                                                                    <a href="/aims/entAppSetupSpotlight.do?task=view&spotLightTypeId=2&solutionId=<bean:write   name="solutionDetail" property="solutionId" />"
                                                                        onMouseOver="changeImages('icon4', 'images/icon4-over.gif'); changeImages('icontext',   'images/icon_doctype4.gif'); return true;"
                                                                        onMouseOut="changeImages('icon4',   'images/icon4<logic:equal   name="spotLightTypeId" scope="request" value="2">-over</logic:equal>.gif'); changeImages('icontext', 'images/space.gif');   return true;">
                                                                        <img name="icon4"   src="images/icon4<logic:equal   name="spotLightTypeId" scope="request" value="2">-over</logic:equal>.gif"   width=27 height=27 border=0/>
                                                                    </a>
                                                                </logic:notEqual>
                                                            </td>
                                                        </logic:equal>
                                                    </logic:iterate>
                                                    
                                                    <logic:iterate id="spotLightCount" name="solutionDetail" property="spotLightCounts">
                                                        <logic:equal name="spotLightCount" property="key" value="5">
                                                            <td align="right"   valign="middle">
                                                                <logic:equal name="spotLightCount" property="value" value="0">
                                                                    <img name="icon1" src="images/icon6-gray.gif"   width=27 height=27 border=0/>
                                                                </logic:equal>
                                                                <logic:notEqual name="spotLightCount" property="value" value="0">
                                                                    <a href="/aims/entAppSetupSpotlight.do?task=view&spotLightTypeId=5&solutionId=<bean:write   name="solutionDetail" property="solutionId" />"
                                                                        onMouseOver="changeImages('icon6', 'images/icon6-over.gif'); changeImages('icontext',   'images/icon_doctype6.gif'); return true;"
                                                                        onMouseOut="changeImages('icon6',   'images/icon6<logic:equal   name="spotLightTypeId" scope="request" value="5">-over</logic:equal>.gif'); changeImages('icontext', 'images/space.gif');   return true;">
                                                                        <img name="icon6"   src="images/icon6<logic:equal   name="spotLightTypeId" scope="request" value="5">-over</logic:equal>.gif"   width=27 height=27 border=0/>
                                                                    </a>
                                                                </logic:notEqual>
                                                            </td>
                                                        </logic:equal>
                                                    </logic:iterate>
                                                    <logic:iterate id="spotLightCount" name="solutionDetail" property="spotLightCounts">
                                                        <logic:equal name="spotLightCount" property="key" value="6">
                                                            <td align="right"   valign="middle">
                                                                <logic:equal name="spotLightCount" property="value" value="0">
                                                                    <img name="icon1" src="images/icon7-gray.gif"   width=27 height=27 border=0/>
                                                                </logic:equal>
                                                                <logic:notEqual name="spotLightCount" property="value" value="0">
                                                                    <a href="/aims/entAppSetupSpotlight.do?task=view&spotLightTypeId=6&solutionId=<bean:write   name="solutionDetail" property="solutionId" />"
                                                                        onMouseOver="changeImages('icon7', 'images/icon7-over.gif'); changeImages('icontext',   'images/icon_doctype7.gif'); return true;"
                                                                        onMouseOut="changeImages('icon7',   'images/icon7<logic:equal   name="spotLightTypeId" scope="request" value="6">-over</logic:equal>.gif'); changeImages('icontext', 'images/space.gif');   return true;">
                                                                        <img name="icon7"   src="images/icon7<logic:equal   name="spotLightTypeId" scope="request" value="6">-over</logic:equal>.gif"   width=27 height=27 border=0/>
                                                                    </a>
                                                                </logic:notEqual>
                                                            </td>
                                                        </logic:equal>
                                                    </logic:iterate>
                                                    
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <img src="images/space.gif" width=21 height=8   alt="">
                                                    </td>
                                                    <td colspan=10  align="left" valign="top">
                                                        <table width="100%" border="0" cellpadding="0">
                                                            <tr>
                                                                <td align="left" valign="top"   bgcolor="#b8b8b8">
                                                                    <table width="100%" border="0" cellpadding="0">
                                                                        <tr>
                                                                            <td align="left" valign="top"   bgcolor="#FFFFFF">
                                                                                <table width="100%" border="0" cellpadding="1" bgcolor="#FFFFFF" >
                                          </tr>
                                                                                    <logic:empty    name="solutionDetail" property="spotLights" >
                                                                                    <tr>
                                                                                        <td>
                                                                                            <span class="errorText">No Files</span>
                                                                                        </td>
                                                                                    </tr>                                                                                                               
                                                                                    </logic:empty>
                                                                                    <logic:notEmpty name="solutionDetail" property="spotLights" >
                                                                                    <tr>
                                                                                        <td colspan="-3" align="left"   valign="top">
                                                                                            <table width="100%" cellpadding="2" cellspacing="0" border="1" bgcolor="#FFFFFF" class="Grid2">
                                                                                          
                                                                                          <tr>
                                                                                          <td align="left"    valign="top" colspan="4"  class="pageheading">
                                                                                           
                                                                                            <strong><bean:write name="solutionDetail"   property="spotLightTypeName" /></strong>&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                            <logic:notEmpty name="solutionDetail" property="spotLights" >
                                                                                                <span   class="totalbottomcopy">(last   addition:   <bean:write name="solutionDetail"   property="spotLightMaxCreateDate"   formatKey="date.format" filter="true"   />)</span>
                                                                                            </logic:notEmpty>
                                                                                        	
                                                                                        </td>
                                                                                      </tr>
                                                                                      
						                                                           	
																					

                                                                                                <tr>
                                                                                                    <th width="10" ><strong>File Name</strong></th>
                                                                                                    <th width="70" ><strong>Description</strong></th>
                                                                                                    <th width="10"><strong>Date   Added</strong></th>
                                                                                                    <th align="right" width="10" ><strong>File Type</strong></th>
                                                                                                </tr>
                                                                                                <tr>
                                                                                                    <td align="left"    valign="top" colspan="3"><img   src="images/grayspace.gif" width="280" height="1"></td>
                                                                                                </tr>
                                                                                                <logic:iterate id="spotLight"   name="solutionDetail" property="spotLights" >
                                                                                                <tr>
                                                                                                    <td><a class="a"   target="_blank" href='/aims/entAppsSLResourceAction.do?resource=spotlightFile&resourceId=<bean:write name="spotLight"    property="spotlightId" />' ><bean:write name="spotLight"    property="spotlightName" /></a></td>
                                                                                                    <td><bean:write name="spotLight"    property="spotlightDesc" /></td>
                                                                                                    <td><bean:write name="spotLight"   property="createdDate" formatKey="date.format" filter="true" /></td>
                                                                                                    <td><bean:write name="spotLight"    property="spotlightFileContentType" /></td>
                                                                                                </tr>
                                                                                              
                                                                                                </logic:iterate>
                                                                                            </table>
                                                                                        </td>
                                                                                    </tr>
                                                                                    </logic:notEmpty>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>											
    										</table>	
</td>
</tr>
	<!-- Solution Material Part - Ends -->					
								
</table>


</div>
</DIV>

</html>
