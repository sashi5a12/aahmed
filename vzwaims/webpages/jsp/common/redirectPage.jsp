<%@page contentType="text/html"%>
<%@ page import="com.netpace.aims.util.StringFuncs"%>
<%
    String message = request.getParameter("message");
    message = StringFuncs.isNullOrEmpty(message) ? "Please wait while your information is being processed..." : message;
%>
<html>
    <head>
        <link href="/aims/css/sections.css" rel="stylesheet" type="text/css" />
        <script language="JavaScript">              
            function processAfterLoad() {
                window.location.href='<%=request.getParameter("gotoURL")%>';
            }
        </script>
    </head>
<body bgcolor="#FFE084" onload="javascript:setTimeout('processAfterLoad()',1000);">
    <table height="100%" width="100%" cellspacing="0" cellpadding="0" align="center" border="0">
        <tr>
            <td align="center">
                <table width="375px" height="100px" bgcolor="#FFE084" cellspacing="4">
                    <tr>
                        <td align="center" valign="middle" class="modFormFieldLbl">
                            <!--*****EDIT THIS MESSAGE*****-->
                            <img src="/aims/images/the.gif" border="0"><img src="/aims/images/zon.gif" border="0"><br/>
                            <%=message%>&nbsp;&nbsp;<img src="/aims/images/wait_icon.gif" border="0">
                            <!--*****EDIT THE ABOVE MESSAGE*****-->
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>