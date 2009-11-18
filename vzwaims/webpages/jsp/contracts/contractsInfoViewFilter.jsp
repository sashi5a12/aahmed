<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script type="text/javascript">
    function submitFilter() {
        document.forms[1].filterField.value = document.forms[0].filterField.value;
        document.forms[1].filterText.value = document.forms[0].filterText.value;
        document.forms[1].submit();
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

<html:form action="/contracts.do" onsubmit="return false;">
    <table cellspacing="0" cellpadding="0" border="0" width="100%">
        <tr>
            <td align="right" width="86%">
                <html:text styleClass="inputField" property="filterText" size="20" onkeydown="onEnterSubmitFilter(event);"
                           onfocus="textFieldSearch(this, 'in')" onblur="textFieldSearch(this, 'out')"/>
                <html:select styleClass="selectField" property="filterField" size="1">
                    <html:option value="contractTitle">Title</html:option>
                    <html:option value="contractVersion">Version</html:option>
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
</html:form>

<script type="text/javascript">
    textFieldSearch(document.forms[0].filterText, 'out');
</script>

