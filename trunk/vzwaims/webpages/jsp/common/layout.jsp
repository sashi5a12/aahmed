<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE> <tiles:getAsString name="title"/> </TITLE>
<link REL="SHORTCUT ICON" HREF="/aims/images/favicon.ico"/>
<link rel="icon" type="image/ico" href="/aims/images/favicon.ico"/>
<META content="Verizon, Wireless, Cell, Cell Phone, Mobile, Cellular, Phone, Mobile Phone" name="Keywords"/>
<META content="Verizon Wireless offers cell phones, pdas, devices, cell phone plans, data plans, and accessories with affordable, reliable wireless service for your personal needs."
      name="description"/>
<META http-equiv=Content-Style-Type content=text/css/>
<META http-equiv=imagetoolbar content=no/>
<META content="MSHTML 6.00.2900.3243" name=GENERATOR/>
<LINK href="/aims/css/leftNav.css" type="text/css" rel="stylesheet" media="screen,print"/>
<LINK href="/aims/css/layout.css" type="text/css" rel="stylesheet" media="screen,print"/>
<LINK href="/aims/css/fonts.css" type="text/css" rel="stylesheet" media="screen,print"/>
<LINK href="/aims/css/homepage.css" type="text/css" rel="stylesheet" media="screen,print"/>
<link href="/aims/css/filter.css" type="text/css" rel="stylesheet" media="screen,print"/>
<link href="/aims/css/button.css" type="text/css" rel="stylesheet" media="screen,print"/>

<!-- <link href="/aims/css/modules.css"		type="text/css" rel="stylesheet" />
     <link href="/aims/css/sections.css"		type="text/css" rel="stylesheet" />-->

<SCRIPT type="text/javascript" src="/aims/js/filter.js"></script>
<SCRIPT type="text/javascript" src="/aims/js/gn_engine.js"></SCRIPT>
<SCRIPT type="text/javascript" src="/aims/js/leftNav.js"></SCRIPT>
<script type="text/javascript" src="/aims/js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="/aims/js/aims2.js"></script>
<script type="text/javascript" src="/aims/js/calendar2.js"></script>
<script type="text/javascript" src="/aims/js/tjmlib2.js"></script>
<script type="text/javascript" src="/aims/js/processingInfoPopup2.js"></script>
<script type="text/javascript" src="/aims/js/tooltip2.js"></script>

<script language="JavaScript" type="text/javascript">
    <!--
    function MM_preloadImages() { //v3.0
        var d = document;
        if (d.images) {
            if (!d.MM_p) d.MM_p = new Array();
            var i,j = d.MM_p.length,a = MM_preloadImages.arguments;
            for (i = 0; i < a.length; i++)
                if (a[i].indexOf("#") != 0) {
                    d.MM_p[j] = new Image;
                    d.MM_p[j++].src = a[i];
                }
        }
    }

    function MM_swapImgRestore() { //v3.0
        var i,x,a = document.MM_sr;
        for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++) x.src = x.oSrc;
    }

    function MM_findObj(n, d) { //v4.01
        var p,i,x;
        if (!d) d = document;
        if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
            d = parent.frames[n.substring(p + 1)].document;
            n = n.substring(0, p);
        }
        if (!(x = d[n]) && d.all) x = d.all[n];
        for (i = 0; !x && i < d.forms.length; i++) x = d.forms[i][n];
        for (i = 0; !x && d.layers && i < d.layers.length; i++) x = MM_findObj(n, d.layers[i].document);
        if (!x && d.getElementById) x = d.getElementById(n);
        return x;
    }

    function MM_swapImage() { //v3.0
        var i,j = 0,x,a = MM_swapImage.arguments;
        document.MM_sr = new Array;
        for (i = 0; i < (a.length - 2); i += 3)
            if ((x = MM_findObj(a[i])) != null) {
                document.MM_sr[j++] = x;
                if (!x.oSrc) x.oSrc = x.src;
                x.src = a[i + 2];
            }
    }

    function MM_openBrWindow(theURL, winName, features) { //v3.0
        window.open(theURL, winName, features).focus();
    }

    function MM_reloadPage(init) {  //reloads the window if Nav4 resized
        if (init == true) with (navigator) {
            if ((appName == "Netscape") && (parseInt(appVersion) == 4)) {
                document.MM_pgW = innerWidth;
                document.MM_pgH = innerHeight;
                onresize = MM_reloadPage;
            }
        }
        else if (innerWidth != document.MM_pgW || innerHeight != document.MM_pgH) location.reload();
    }
    MM_reloadPage(true);

    function MM_showHideLayers() { //v6.0
        var i,p,v,obj,args = MM_showHideLayers.arguments;
        for (i = 0; i < (args.length - 2); i += 3) if ((obj = MM_findObj(args[i])) != null) {
            v = args[i + 2];
            if (obj.style) {
                obj = obj.style;
                v = (v == 'show') ? 'visible' : (v == 'hide') ? 'hidden' : v;
            }
            obj.visibility = v;
        }
    }
    //-->
</script>
</HEAD>

<BODY>
<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td width="100%">
	<DIV id="wideLayout">
	<!-- PAGE BODY START HERE -->
	<!-- HEADER:BEGIN -->
	<tiles:insert attribute="header"/>
	<!-- HEADER:END -->

	<!-- MENUBAR:BEGIN -->
	<tiles:insert attribute="menubar"/>
	<!-- MENUBAR:END   -->

	<!-- BODY START HERE -->
	<DIV id="pageWrapper">
		<DIV id="bodyWrapper">
			<DIV id="homepageContainer">
				<!-- PAGE HEADING START HERE -->
				<DIV id="marquee">
					<DIV class="pageHead">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left: 10px">
							<tr>
								<td width="50%" height="32" class="pageHeading">
									<tiles:getAsString name="headingText"/>
								</td>
								<td width="50%" align="right">
									<tiles:insert attribute="filterHolder"/>
								</td>
							</tr>
						</table>
					</DIV>
				</DIV>
				<!-- PAGE HEADING END HERE -->
				<table cellpadding="0" cellspacing="10" border="0">
					<tr>
						<td valign="top">
							<!-- LEFT PANEL START HERE -->
							<div class="leftPan">
								<!-- USERBAR:BEGIN -->
								<tiles:insert attribute="userbar"/>
								<!-- USERBAR:END -->

								<!-- MENU:BEGIN -->
								<tiles:insert attribute='menu'/>
								<!-- MENU:END -->
							</div>
							<!-- LEFT PANEL END HERE -->
						</td>
						<td valign="top">
							<!-- BODY:BEGIN -->
							<tiles:insert attribute='body'/>
							<!-- BODY:END   -->
						</td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>

							<div id="processingInfoPopupDiv" style="visibility:hidden; z-index:550">
							
								
								<table width="370px" height="105px">
									<tr>
										<td>
											<DIV class="homeColumn expandableBox">
												<table width="100%" cellspacing="0" cellpadding="0">
													<tr>
														<td class="headLeftCurveblkTDWidth">
															<DIV class="headLeftCurveblk"></DIV>
														</td>
														<td>
															<H1>                                        
																Please wait while your information is being processed...                                        
															</H1>                            
														</td>
														<td class="headRightCurveblkTDWidth">
															<DIV class="headRightCurveblk"></DIV>
														</td>
													</tr>
												</table>
												<DIV class="loginbox">
													<DIV class="marginB" align="center"><img src="/aims/images/waitInfoProcess.gif" border="0"></DIV>                                    
												</DIV>
											</DIV>             
										</td>
									</tr>
								</table>
								
							</div>

							<DIV class="classZonHelpWindow" id="ZonHelpWindow" name="ZonHelpWindow"
								 style="LEFT: 200px; WIDTH: 200px; TOP: 100px; z-index:500">
								<DIV class="classZonHelpWindowText" id="ZonHelpWindowText" name="ZonHelpWindowText"
									 style="HEIGHT: 100px" onclick="closeZonHelpWindow();">
								   Go to Market Help
								</DIV>
							</DIV>

							<iframe id="ZonPopupDivShim" src="javascript:false;" scrolling="no" frameborder="0"
									style="position:absolute; top:0px; left:0px; display:none;"></iframe>
						</td>
					</tr>
				</table>
			</DIV>
		</DIV>
	</DIV>
	<!-- BODY END HERE -->

	<!-- FOOTER:BEGIN -->
		<tiles:insert attribute="footer"/>
	<!-- FOOTER:END -->

	<!-- PAGE BODY END HERE -->
	</DIV>
	<%-- end widelayout--%>
</td></tr></table>
</BODY>
</HTML>
<!--not needing elements-->
<%--<tiles:insert attribute="blankspace"/>--%>