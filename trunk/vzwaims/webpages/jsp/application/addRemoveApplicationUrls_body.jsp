<%@ page import="com.netpace.aims.util.AimsConstants" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%int applicationURLIndex = 0;%>
<%-- if upload images successfully then close this window, and refresh parent --%>
<logic:messagesPresent message="true">
    <script language="javascript">
        var parent = window.opener;
        //if this screen is opened in a pop up window
        if (parent && !parent.closed) {
            parent.location.reload(true);
            window.close();
        }
    </script>
</logic:messagesPresent>

<script language="javascript">
    function submitFrm() {
        removeEmptyApplicationURLs(0);
        //document.forms[0].submit();
        return true;
    }
</script>

    <%@ include file="include/applicationUrlJScript.jsp" %>
    <%-- if there is any error, show here --%>
    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td width="465px"><div style="height:100%; "><%@ include file="/common/popUpError.jsp" %></div></td>
        </tr>        
    </table>

    <%-- width is 60px less than width of window called from parent window. (to support IE6) --%>
    <DIV class="homeColumnTab" style="height:100%;width:465px;">
        <html:form action="/addRemoveApplicationUrls.do?task=save" enctype="text/html" onsubmit="return submitFrm();">
            <logic:messagesNotPresent message="true">
                <html:hidden property="appsId"/>
                <html:hidden property="task" value="save"/>
                    <%-- width of H1 is 20px less than width of parent div--%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="100%">
                                <DIV class="headLeftCurveblk"></DIV>
                                <H1 style="width:445px;">Add/Remove Application URL's</H1>
                                <DIV class="headRightCurveblk"></DIV>
                            </td>
                        </tr>
                        <tr>
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                    <tr>
                                        <th width="100%">
                                            <div id="divApplicationURLLbl">
                                                <strong><bean:message key="ManageApplicationForm.applicationURLs" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                            </div>
                                        </th>
                                    </tr>

                                    <tr>
                                        <td width="100%">
                                            <table id="tblApplicationURLs" width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tbody>
                                                    <logic:equal name="AddRemoveApplicationUrlForm" property="networkUsage"
                                                                 value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
                                                        <logic:notEmpty name="AddRemoveApplicationUrlForm" property="applicationURLs">
                                                            <logic:iterate id="applicationURL" name="AddRemoveApplicationUrlForm"
                                                                           property="applicationURLs">
                                                                <tr id="<%=("row"+applicationURLIndex)%>" valign="top">
                                                                    <td vAlign="top">
                                                                        <input type="text" class="inputField" name="applicationURLs" size="60" maxlength="200"
                                                                               value="<%=applicationURL%>" style="margin-bottom:0px; width:375px;">&nbsp;<a href="javascript:removeApplicationURLRow('<%=("row"+(applicationURLIndex))%>', false);"><bean:message key='images.delete.icon'/></a>
                                                                        <%applicationURLIndex++;%>
                                                                    </td>
                                                                </tr>
                                                            </logic:iterate>
                                                        </logic:notEmpty>
                                                    </logic:equal>
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr><td width="100%">&nbsp;</td></tr>
                                    <tr>
                                        <td width="100%">
                                            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                                                <tr>
                                                    <td>
                                                        <div class="redBtn" style="float:right;margin-left:10px" id="saveAndClose" title="Save and Close">
                                                            <div>
                                                                <div>
                                                                    <div onClick="document.forms[0].submit()">Save and Close</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="blackBtn" style="float:right" id="add" title="Add">
                                                            <div>
                                                                <div>
                                                                    <div onClick="javascript:addApplicationURLRow('tblApplicationURLs', 'applicationURLs', '', true, false);">
                                                                        Add
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
            </logic:messagesNotPresent>
        </html:form>
    </DIV>
    <%-- end Div homeColumn--%>

    <script	language="javascript">
        lastRowId = <%=applicationURLIndex%>;
    </script>