<%@ page language="java" %>


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html>
<head>
    <title>Email Additional Info</title>

    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

    <script language="JavaScript" type="text/JavaScript">
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
        //-->
    </script>

    <script language="JavaScript" src="js/aims2.js"></script>
    <script language="JavaScript" src="js/calendar2.js"></script>
    <script language="JavaScript" src="js/tjmlib2.js"></script>

    <link href="/aims/css/layout.css" rel="stylesheet" type="text/css"/>
    <link href="/aims/css/homepage.css" rel="stylesheet" type="text/css"/>
    <link href="/aims/css/button.css" rel="stylesheet" type="text/css"/>
    <link href="/aims/css/global.css" rel="stylesheet" type="text/css"/>
    <!--<link href="/aims/css/modules.css" rel="stylesheet" type="text/css"/>-->
    <%--<link href="/aims/css/sections.css" rel="stylesheet" type="text/css"/>--%>
    <link href="/aims/css/fonts.css" type="text/css" rel="stylesheet"/>

</head>

<body>

<script language="javascript">

    function truncateLocalTextAreas()
    {
        TruncateTextWithCount(document.forms[0].emailBody, 'textCountEmailBody', 3500);
    }

    function trackCountForTextAreas()
    {
        TrackCount(document.forms[0].emailBody, 'textCountEmailBody', 3500);
    }

</script>

<html:form action="/allianceMusicEmailUpdate" enctype="multipart/form-data">
<html:hidden property="task" value="save"/>

<div id="contentBox" style="float:left">
<DIV class="homeColumn lBox">
    <DIV class="headLeftCurveblk"></DIV>
    <H1 style="width:610px">Send Additional Information</H1>

    <DIV class="headRightCurveblk"></DIV>

    <DIV class="contentbox" style="width:630px;height:540px">
        <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
                <td>
                    <strong>From Email Addresses:</strong>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <html:text property="fromEmailAddress" size="100" maxlength="150" styleClass="inputField"/>
                </td>
            </tr>
        </table>

        <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
                <td>
                    <strong>Recipient Email Addresses: (Comma separated)</strong>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <html:text property="toEmailAddress" size="100" maxlength="500" styleClass="inputField"/>
                </td>
            </tr>
        </table>

        <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
                <td>
                    <strong>Subject:</strong>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <html:text property="emailSubject" size="100" maxlength="150" styleClass="inputField"/>
                </td>
            </tr>
        </table>

        <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
                <td>
                    <strong>Body:</strong>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <html:textarea property="emailBody" rows="10" cols="60"
                                   onkeyup="TrackCount(this,'textCountEmailBody',3500)"
                                   onkeypress="LimitText(this,3500)" styleClass="textareaField"></html:textarea>
                    <br/>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="vertical-align:top;padding:0">
                                <bean:message key="ManageApplicationForm.textarea.char.remaining"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                            </td>
                            <td><input type="text" name="textCountEmailBody" size="4" value="3500" disabled="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

        <table width="100%">
            <tr align="right">
                <td width="100%" height="25" align="right" valign="middle">
                    <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px; margin-bottom:10px"
                         id="Save" title="Save">
                        <div>
                            <div>
                                <div onclick="document.forms[0].task.value='save';document.forms[0].submit()">
                                    Save
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="blackBtn"
                         style="margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Send"
                         title="Send">
                        <div>
                            <div>
                                <div onclick="document.forms[0].task.value='send';document.forms[0].submit()">
                                    Send
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
        <%@ include file="/common/error.jsp" %>
    </div>
</div>
</div>


<script language="javascript">
    trackCountForTextAreas();
</script>

</html:form>
</body>
</html>
