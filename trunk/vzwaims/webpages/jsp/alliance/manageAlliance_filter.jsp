<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<table cellspacing="5" cellpadding="0">
    <html:form action="/vzwAlliance">
        <html:hidden property="task" value="filterview"/>
        <input type="hidden" name="all_type" value="<bean:write name="VZWAllianceForm" property="allianceType"/>"/>
        <tr>
            <td valign="middle">
                <html:text property="filterText" size="20" styleClass="inputField" onfocus="textFieldSearch(this, 'in')"
                           onblur="textFieldSearch(this, 'out')"/>
            </td>
            <td valign="middle">
                <bean:define id="filterCol" type="java.lang.String" name="VZWAllianceForm" property="filterField"/>
                <html:select property="filterField" size="1" value="<%=filterCol%>" styleClass="selectField">
                    <html:option value="alliance_name">Alliance Name</html:option>
                    <html:option value="alliance_status">Alliance Status</html:option>
                    <html:option value="alliance_created_date">Created Date</html:option>
                    <html:option value="alliance_contract">Contract</html:option>
                    <html:option value="vzw_account_manager">VZW Account Manager</html:option>
                </html:select>
            </td>
            <td align="right">
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
        <html:hidden property="sortField"/>
        <html:hidden property="allianceType"/>
    </html:form>
</table>

<script type="text/javascript">
    textFieldSearch(document.forms[0].filterText, 'out');
</script>