<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script language="javascript">
    function submit_click_event(selectedId, statusValue, docTypeValue, totalCount, selContractId)
    {
        document.forms[0].selDocType.value = docTypeValue;
        document.forms[0].selDocId.value = selectedId;
        document.forms[0].selContractId.value = selContractId;
        document.forms[0].task.value = "allianceClickThroughDetail";
    }
</script>


<%@ include file="/common/error.jsp" %>
<DIV class="homeColumnTab lBox">
    <html:form action="/allianceClickThroughContracts.do">

        <html:hidden property="task" value="view"/>
        <html:hidden property="selDocType"/>
        <html:hidden property="selDocId"/>
        <html:hidden property="selContractId"/>

        <DIV class="contentbox">
        <!-- DATA GRID START HERE -->
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr>
                <td><%@include file="allianceClickThroughContractsGrid.jsp"%></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
        </table>
        <!-- DATA GRID END HERE -->
        </DIV>
    </html:form>
</DIV>