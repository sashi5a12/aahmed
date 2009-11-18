<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%!
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
%>

<%@ include file="/common/error.jsp" %>

<DIV class="homeColumn lBox floatL">
    <DIV class="headLeftCurveblk"></DIV>
    <H1><bean:message key="ReconciliationCatalog.tableHeading" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
    <DIV class="headRightCurveblk"></DIV>

    <DIV class="contentbox">
        <html:form action="/reconcileCatalogFile">
            <table width="100%">
                <tr>
                    <td width="35%">
                        <strong>
                        <bean:message key="ReconciliationCatalog.reconcileDataFileDate"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
                    </td>
                    <td width=170>
                        <html:select property="brewNstlUploadId" size="1">
                            <html:option value="0">
                                <bean:message key="ManageApplicationForm.label.selectOne"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </html:option>
                            <logic:iterate id="entry" name="DataFiles"
                                           type="com.netpace.aims.model.application.AimsCatalog">
                                <html:option
                                        value="<%=entry.getCatalogId().toString()%>"><%=sdf.format(entry.getCatalogDateCreated())%>
                                </html:option>
                            </logic:iterate>
                        </html:select>
                    </td>
                    <td align="left">
                        <div class="redBtn" id="Go" title="Go">
                            <div><!---->
                                <div><div onclick="checkFunction(document.forms[0])">Go</div></div>
                            </div>
                        </div>
                        <%--<input type=image src="images/go_b.gif" border=0>--%>
                    </td>
                </tr>
            </table>
        </html:form>

        <html:form action="/reconcileCatalog">
            <table width="100%">
                <tr>
                    <td width="35%">
                        <strong>
                        <bean:message key="ReconciliationCatalog.reconciledDataFileDate"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
                    </td>
                    <td width=170>
                        <html:select property="brewNstlUploadId" size="1">
                            <html:option value="0">
                                <bean:message key="ManageApplicationForm.label.selectOne"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </html:option>
                            <logic:iterate id="entry" name="DataFilesReconciled"
                                           type="com.netpace.aims.model.application.AimsCatalog">
                                <html:option
                                        value="<%=entry.getCatalogId().toString()%>"><%=sdf.format(entry.getCatalogDateCreated())%>
                                </html:option>
                            </logic:iterate>
                        </html:select>
                    </td>
                    <td align="left">
                         <div class="redBtn" id="Go2" title="Go">
                            <div>
                                <div><div onclick="checkFunction(document.forms[1])">Go</div></div>
                            </div>
                        </div>
                        <!--<input type=image src="images/go_b.gif" border=0>-->
                    </td>
                </tr>
            </table>
        </html:form>
    </DIV>
</DIV>

<script type="text/javascript">
    function checkFunction(Form){
        if(Form.brewNstlUploadId.selectedIndex == 0){
            alert('Please select a date value from the dropdown box.')
        } else {
            Form.submit();
        }
    }
</script>