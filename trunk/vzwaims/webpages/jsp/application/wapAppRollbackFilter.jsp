<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:form action="/rollbackWapApp">
    <html:hidden property="task" value="filterview"/>
    <html:hidden property="sortField"/>
    <html:hidden property="sortOrder"/>
    <table cellspacing="0" cellpadding="5" border="0">
        <tr>
            <td>
                <html:text property="filterText" size="20" styleClass="inputField" onfocus="textFieldSearch(this, 'in')" onblur="textFieldSearch(this, 'out')"/>
            </td>
            <td>
                <html:select property="filterField" size="1" styleClass="inputField">
                    <html:option value="4">
                        <bean:message key="ManageApplicationForm.view.applicationTitle"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    </html:option>
                    <html:option value="2">
                        <bean:message key="ManageApplicationForm.view.companyName"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    </html:option>
                </html:select>
            </td>

            <td align="right" valign="middle">
                <div class="blackBtn" style="float:right;" id="Search" title="Search">
                    <div>
                        <div>
                            <div onclick="document.forms[0].submit();">Search</div>
                        </div>
                    </div>
                </div>
                <%--<input type="image" src="images/go_b.gif" width="27" height="18">--%>
            </td>
        </tr>
    </table>
</html:form>

<script type="text/javascript">
    textFieldSearch(document.forms[0].filterText, 'out');
</script>