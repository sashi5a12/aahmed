<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<html>
<head>
<title>Difference</title>

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
        <logic:empty name="wapAppVersionInfo">
            <tr>
                <td>
                    <div class="errorMsg">
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
        <logic:notEmpty name="wapAppVersionInfo">
            <%--created by info, moved to top --%>
            <logic:notEmpty name="CreationInfo">
                <DIV><bean:write name="CreationInfo"/></DIV>
            </logic:notEmpty>

            <%-- heading starts --%>
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:write name="HeaderInfo"/></H1>
            <DIV class="headRightCurveblk"></DIV>
            <%-- heading ends --%>
            <DIV class="contentbox">
                <table width="100%" cellspacing="0" cellpadding="0" border="0">
                    <tr>
                        <td>
                            <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
                                <tr>
                                    <th>Field Name</th>
                                    <th>Old Value</th>
                                    <th>New Value</th>
                                </tr>
                                <logic:iterate id="wapAppVersion"   name="wapAppVersionInfo">
                                    <tr>
                                        <td align="left">
                                            <strong><bean:write name="wapAppVersion"   property="fieldName"/>&nbsp;</strong>
                                        </td>
                                        <td align="left">
                                            <bean:write name="wapAppVersion"   property="oldValue"/>&nbsp;
                                        </td>
                                        <td align="left">
                                            <bean:write name="wapAppVersion"   property="newValue"/>&nbsp;
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
</html>
