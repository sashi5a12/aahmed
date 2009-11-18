<%@	page language="java"  %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<script language="javascript">
    function submitPopupForm() {
        var buttonsRow = document.getElementById('buttonRow');
        if(buttonsRow) {
            buttonsRow.style.display='none';
        }
        document.forms[0].submit();
    }
</script>

<%-- if success then close this window, and refresh parent --%>
<logic:messagesPresent message="true">
        <script language="javascript">
            var parent = window.opener;
            //if this screen is opened in a pop up window
            if(parent && !parent.closed) {
                if(parent.document && parent.document.forms[0] && parent.document.forms[0].mportalAllianceName) {
                    parent.document.forms[0].mportalAllianceName.value = '<c:out value="${MportalAllianceUpdateForm.mportalAllianceName}"/>';
                }
                if(parent.refreshPage) {
                    parent.refreshPage();
                }
                self.close();
            }

        </script>
</logic:messagesPresent>

<%-- if there is any error, show here --%>

    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td width="465px"><div style="height:100%; "><%@ include file="/common/popUpError.jsp" %></div></td>
        </tr>
    </table>

<%-- width is 60px less than width of window called from parent window. (to support IE6) --%>

<DIV class="homeColumnTab" style="height:100%;width:465px;">
    <html:form action="/mportalAllianceUpdate.do?task=save" enctype="multipart/form-data">
        <html:hidden property="allianceId" />
        <html:hidden property="allianceName" />
        <html:hidden property="vendorId"  />

        <html:hidden property="task" value="save"  />

        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <logic:messagesNotPresent message="true">
                <logic:notPresent name="applicationAccessDenied">
                <%-- width of H1 is 20px less than width of parent div--%>
                <tr>
                    <td>
                        <DIV class="headLeftCurveblk"></DIV>
                        <H1 style="width:445px;">Edit MPortal Alliance Name</H1>
                        <DIV class="headRightCurveblk"></DIV>
                    </td>
                </tr>
                <tr><td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                        <tr>
                            <th><strong>Alliance Name&nbsp;:</strong></th>
                            <th><strong>&nbsp;</strong></th>
                        </tr>
                        <tr>
                            <td	valign="top">
                                <c:out value="${MportalAllianceUpdateForm.allianceName}"/>
                            </td>
                            <td	valign="top">&nbsp;</td>
                        </tr>
                        <tr><td colspan="2">&nbsp;</td></tr>
                        <tr>
                            <td><strong>Vendor ID&nbsp;:</strong></td>
                            <td><strong>&nbsp;</strong></td>
                        </tr>
                        <tr>
                            <td	valign="top" colspan="2">
                                <c:out value="${MportalAllianceUpdateForm.vendorId}"/>
                            </td>
                        </tr>
                        <tr><td colspan="2">&nbsp;</td></tr>

                        <tr>
                            <td><strong>MPortal Alliance Name&nbsp;<span class="requiredText">*</span>:</strong></td>
                            <td><strong>&nbsp;</strong></td>
                        </tr>
                        <tr>
                            <td	valign="top" colspan="2">
                                <html:text styleClass="inputField" property="mportalAllianceName" size="40" maxlength="250"/>
                            </td>
                        </tr>
                    </table>
                </td></tr>
                <tr id="buttonRow"><td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td colspan="2" height="25" align="right" valign="middle">
                                <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Save" title="Submit">
                                    <div>
                                        <div>
                                            <div onClick="javascript:submitPopupForm();">Save and Close</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="blackBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Cancel" title="Cancel">
                                    <div>
                                        <div><div onclick="javascript:window.close();">Cancel</div></div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td></tr>
                </logic:notPresent>
            </logic:messagesNotPresent>
        </table>
    </html:form>
</DIV>