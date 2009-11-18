<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
<head>
    <title>Device Test Results History</title>

    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

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

    <LINK href="/aims/css/leftNav.css" type="text/css" rel="stylesheet"/>
    <LINK href="/aims/css/layout.css" type="text/css" rel="stylesheet"/>
    <LINK href="/aims/css/fonts.css" type="text/css" rel="stylesheet"/>
    <LINK href="/aims/css/homepage.css" type="text/css" rel="stylesheet"/>
    <link href="/aims/css/filter.css" type="text/css" rel="stylesheet"/>
    <link href="/aims/css/button.css" type="text/css" rel="stylesheet"/>


</head>


<body style="BACKGROUND: #FFFFFF;">
    <%-- no need to include error page, as errors are being displayed manually--%>
    <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr><td>&nbsp;</td></tr>
        <html:messages id="message"	message="true" header="messages.header"	footer="messages.footer">
            <tr>
                <td>
                    <div class="alertMsg">
                        <li><bean:write	name="message"/></li><br/>
                    </div>
                </td>
            </tr>
        </html:messages>
        <logic:empty name="vzAppBinaryFirmwareList">
            <tr>
                <td>
                    <div class="errorMsg xlBox">
                        <h1>Sorry! No Information Found</h1>
                    </div>
                </td>
            </tr>
        </logic:empty>
    </table>

    <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
            <td><%@ include file="/common/error.jsp" %></td>
        </tr>
    </table>

    <DIV class="homeColumnTab xlBox">
        <logic:notEmpty name="vzAppBinaryFirmwareList">
            <%-- heading starts --%>
            <DIV class="headLeftCurveblk"></DIV>
            <H1>VZAppZone Test History for <c:out value="${vzAppBinaryFirmwareList[0].phoneModel}"/></H1>
            <DIV class="headRightCurveblk"></DIV>
            <%-- heading ends --%>
            <DIV class="contentbox">
                <table width="100%" cellspacing="0" cellpadding="0" border="0">
                    <tr>
                        <td>
                            <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
                                <tr>
                                    <th>Binary Name</th>
                                    <th>Version</th>
                                    <th>Tested Date</th>
									<th>Test Status</th>
									<th>Testing Comments</th>
									<th>Test Results</th>
                                    <th>OTA Tested Date</th>
									<th>OTA Test Status</th>
									<th>OTA Comments</th>
									<th>OTA Results</th>
                                </tr>
                                <logic:iterate id="vzAppBinaryFirmwareInfo"   name="vzAppBinaryFirmwareList" indexId="idx" scope="request">
                                    <tr>
                                        <td align="left">
                                            <logic:notEmpty	name="vzAppBinaryFirmwareInfo"	property="binaryFileFileName">
                                                <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryFile&vzAppBinaryId=<c:out value="${vzAppBinaryFirmwareInfo.binaryId}"/>&app_id=<c:out value="${vzAppBinaryFirmwareInfo.vzAppZoneAppsId}"/>" class="a"	target="_blank">
                                                    <c:out value="${vzAppBinaryFirmwareInfo.binaryFileFileName}" escapeXml="false"/>
                                                </a>
                                            </logic:notEmpty>
                                            &nbsp;
                                        </td>
                                        <td align="left">
                                            <c:out value="${vzAppBinaryFirmwareInfo.binaryVersion}" escapeXml="false"/>&nbsp;
                                        </td>
                                        <td align="left">
                                            <c:out value="${vzAppBinaryFirmwareInfo.baseTestedDate}"/>&nbsp;
                                        </td>
										<td align="left">
                                            <logic:notEmpty name="vzAppBinaryFirmwareInfo" property="baseTestStatus">
                                                <logic:equal name="vzAppBinaryFirmwareInfo" property="baseTestStatus" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0]%>">
                                                    <%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[1]%>
                                                </logic:equal>
                                                <logic:equal name="vzAppBinaryFirmwareInfo" property="baseTestStatus" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[0]%>">
                                                    <%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[1]%>
                                                </logic:equal>
                                            </logic:notEmpty>
                                            &nbsp;
                                        </td>
										<td align="left">
                                            <textarea class="inputField" name="comments<%=idx%>"
                                                                  onkeyup="LimitText(this,200)"
                                                                  onkeypress="LimitText(this,200)"
                                                                  rows="3" cols="25" readonly="true"><c:out value="${vzAppBinaryFirmwareInfo.baseComments}"/></textarea>
                                        </td>
										<td align="left">
                                            <logic:notEmpty name="vzAppBinaryFirmwareInfo" property="baseResultsFileFileName">
                                                <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBaseResultFile&vzAppBinaryFirmwareId=<c:out value="${vzAppBinaryFirmwareInfo.binaryFirmwareId}"/>&app_id=<c:out value="${vzAppBinaryFirmwareInfo.vzAppZoneAppsId}"/>"	class="a"	target="_blank">
                                                    <c:out value="${vzAppBinaryFirmwareInfo.baseResultsFileFileName}" escapeXml="false"/>
                                                </a>
                                            </logic:notEmpty>
                                            &nbsp;
                                        </td>

                                        <td align="left">
                                            <c:out value="${vzAppBinaryFirmwareInfo.otaTestedDate}"/>&nbsp;
                                        </td>
										<td align="left">
                                            <logic:notEmpty name="vzAppBinaryFirmwareInfo" property="otaTestStatus">
                                                <logic:equal name="vzAppBinaryFirmwareInfo" property="otaTestStatus" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0]%>">
                                                    <%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[1]%>
                                                </logic:equal>
                                                <logic:equal name="vzAppBinaryFirmwareInfo" property="otaTestStatus" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[0]%>">
                                                    <%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[1]%>
                                                </logic:equal>
                                            </logic:notEmpty>
                                            &nbsp;
                                        </td>
										<td align="left">
                                            <textarea class="inputField" name="otaComments<%=idx%>"
                                                                  onkeyup="LimitText(this,200)"
                                                                  onkeypress="LimitText(this,200)"
                                                                  rows="3" cols="25" readonly="true"><c:out value="${vzAppBinaryFirmwareInfo.otaComments}"/></textarea>
                                        </td>
										<td align="left">
                                            <logic:notEmpty name="vzAppBinaryFirmwareInfo" property="otaResultsFileFileName">
                                                <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppOTAResultFile&vzAppBinaryFirmwareId=<c:out value="${vzAppBinaryFirmwareInfo.binaryFirmwareId}"/>&app_id=<c:out value="${vzAppBinaryFirmwareInfo.vzAppZoneAppsId}"/>"	class="a"	target="_blank">
                                                    <c:out value="${vzAppBinaryFirmwareInfo.otaResultsFileFileName}" escapeXml="false"/>
                                                </a>
                                            </logic:notEmpty>
                                            &nbsp;
                                        </td>
                                    </tr>
                                </logic:iterate>
                            </table>
                        </td>
                    </tr>
                </table>
            </DIV>
            <%-- end div contentBox--%>
        </logic:notEmpty>
    </DIV>
</body>
<script language="javascript" type="text/javascript" src="/aims/js/wz_tooltip.js"></script>
</html>
