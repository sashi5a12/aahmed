<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%! java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy"); %>

<%@ include file="/common/error.jsp" %>


<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<html:form action="/reconcileCatalog">
<div class="btnTopLine">
<table align=center cellpadding="5" style="margin-top:5px">
    <tr>
        <td>
            <strong>
                <bean:message key="ReconciliationCatalog.dataFileDate"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </strong></td>
        <td>
            <html:select property="brewNstlUploadId" size="1">
                <logic:iterate id="entry" name="DataFiles"
                               type="com.netpace.aims.model.application.AimsCatalog">
                    <html:option
                            value="<%=entry.getCatalogId().toString()%>"><%=sdf.format(entry.getCatalogDateCreated())%>
                    </html:option>
                </logic:iterate>
            </html:select>
        </td>
        <td>
            <!--<input type=image src="images/go_b.gif" border=0>-->
            <div class="redBtn" title="Go">
                <div>
                    <div>
                        <div onclick="document.forms[0].submit()">Go</div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>
</div>

<DIV class="headLeftCurveblk"></DIV>
<H1><bean:message key="ReconciliationCatalog.tableHeadingHistory"
                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

<DIV class="headRightCurveblk"></DIV>


<DIV class="contentbox">
        <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
            <tr align=center>
                <th colspan=4>
                    <strong><bean:message key="ReconciliationCatalog.catalogData"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
                </th>
                <th colspan=5><strong><bean:message
                        key="ReconciliationCatalog.aimsData"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
            </tr>
            <tr align=center>
                <th><strong><bean:message
                        key="ReconciliationCatalog.developer"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                <th><strong><bean:message
                        key="ReconciliationCatalog.application"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                <th><strong><bean:message
                        key="ReconciliationCatalog.handset"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                <th><strong><bean:message
                        key="ReconciliationCatalog.version"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                <th><strong><bean:message
                        key="ReconciliationCatalog.alliance"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                <th><strong><bean:message
                        key="ReconciliationCatalog.application"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                <th><strong><bean:message
                        key="ReconciliationCatalog.device"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                <th><strong><bean:message
                        key="ReconciliationCatalog.version"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                <th><strong><input type=checkbox checked disabled=true></strong></th>
            </tr>
            <%int iCount = 0;%>
            <logic:iterate id="dEnteries" name="CatalogDataExt"
                           type="com.netpace.aims.controller.application.BrewNstlDataExt" scope="request">
                <tr>
                    <td><bean:write name="dEnteries" property="developerName"/></td>
                    <td><bean:write name="dEnteries" property="applicationName"/></td>
                    <td><bean:write name="dEnteries" property="handset"/></td>
                    <td><bean:write name="dEnteries" property="version"/></td>
                    <logic:present name="dEnteries" property="brewAppsId">
                        <td><bean:write name="dEnteries" property="companyName"/></td>
                        <td><bean:write name="dEnteries" property="appTitle"/></td>
                        <td><bean:write name="dEnteries" property="deviceModel"/></td>
                        <td><bean:write name="dEnteries" property="appVersion"/></td>
                        <td><input type=checkbox name="selectedCombo" value="<%=iCount++%>" checked
                                   disabled="true"></td>
                    </logic:present>
                    <logic:notPresent name="dEnteries" property="brewAppsId">
                        <td colspan="5">&nbsp;</td>
                    </logic:notPresent>
                </tr>
            </logic:iterate>
        </table>
</div>
</html:form>
</div>
</div>