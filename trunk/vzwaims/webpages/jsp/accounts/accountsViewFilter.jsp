<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script type="text/javascript">
    function submitFilter() {
        document.forms[2].filterField.value = document.forms[0].filterField.value;
        document.forms[2].filterText.value = document.forms[0].filterText.value;
        document.forms[2].submit();
    }
    function onEnterSubmitFilter(e) {
	    if (e.which || e.keyCode) {
	        if ((e.which == 13) || (e.keyCode == 13)) {
	            submitFilter();
	            return false;
	        }
	    }
	    return true;
	}            
</script>

<html:form action="/accounts.do?task=view" onsubmit="return false;">
    <c:if test="${empty isAccountManager}">
        <table cellspacing="0" cellpadding="0" border="0" width="100%">
            <tr>
                <td align="right" width="86%">
                    <html:text styleClass="inputField" property="filterText" size="20" onkeydown="onEnterSubmitFilter(event);"
                               onfocus="textFieldSearch(this, 'in')" onblur="textFieldSearch(this, 'out')"/>
                    <html:select styleClass="selectField" property="filterField" size="1">
                        <html:option value="email">Email</html:option>
                        <html:option value="firstName">First Name</html:option>
                        <html:option value="lastName">Last Name</html:option>
                        <html:option value="createdDate">Created Date</html:option>
                    </html:select>
                </td>
                <td align="right" width="14%">
                    <div class="blackBtn" style=" margin-left:5px;float:right; margin-top:3px" id="search"
                         title="Search">
                        <div>
                            <div>
                                <div onclick="submitFilter();">Search</div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </c:if>
</html:form>

<c:if test="${empty isAccountManager}">
	<script type="text/javascript">
	    textFieldSearch(document.forms[0].filterText, 'out');
	</script>
</c:if>