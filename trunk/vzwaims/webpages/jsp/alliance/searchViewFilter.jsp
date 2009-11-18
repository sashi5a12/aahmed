<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:form action="/vzwAllianceSearch">
    <html:hidden property="task" value="in_search"/>

    <table>
        <tr>
            <td>
                <html:text property="filterText" size="20" styleClass="inputField"  onfocus="textFieldSearch(this, 'in')" onblur="textFieldSearch(this, 'out')"/>
            </td>
            <td>
                <bean:define id="filterCol" type="java.lang.String" name="VZWAllianceForm"
                             property="filterField"/>
                <html:select property="filterField" size="1" value="<%=filterCol%>" styleClass="selectField">
                    <html:option value="alliance_name">Alliance Name</html:option>
                    <html:option value="alliance_status">Alliance Status</html:option>
                    <html:option value="alliance_created_date">Created Date</html:option>
                    <html:option value="alliance_contract">Contract</html:option>
                    <html:option value="vzw_account_manager">VZW Account Manager</html:option>
                </html:select>
            </td>
            <td>
                <div class="blackBtn" id="Search" title="Search">
                    <div>
                        <div>
                            <div onclick="document.forms[0].submit()">Search</div>
                        </div>
                    </div>
                </div>
            </td>
            <!--<input type="image" src="images/go_b.gif" width="27" height="18">-->
        </tr>
    </table>

    <html:hidden property="sortField"/>
    <html:hidden property="allianceType"/>

</html:form>

<script type="text/javascript">
    textFieldSearch(document.forms[0].filterText, 'out');
</script>