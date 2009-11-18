<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>


<script	language="javascript">
<!--
	var childWindow;
	childWindow = null;
	
	function handleChild(dataId)
	{
		childWindow = window.open("reconcileCompAppsSelect.do?catalogDataId=" + dataId,"childAppSelect","menubar=no,location=no,resizable=no,toolbar=no,width=550,height=400,scrollbars=yes");
		if (childWindow.opener == null) childWindow.opener = self;
		childWindow.focus();
	}

//-->
</script>


<%-- the second one went to the master page.
     <bean:message key="ReconciliationCatalog.pageTitle"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <br/>
     <bean:message key="ReconciliationCatalog.tableHeading"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <br/>
--%>

<%@	include	 file="/common/error.jsp"	%>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">



<DIV class="headLeftCurveblk"></DIV>
<H1><bean:message key="ReconciliationCatalog.dataFileDate"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <bean:write name="Catalog" property="catalogDateCreated" formatKey="date.format" filter="true"/></H1>
<DIV class="headRightCurveblk"></DIV>


<DIV class="contentbox">

<html:form action="/reconcileCatalog">
        <input type="hidden" name="task" value="update" />
        <input type="hidden" name="choseCombineId" value="" />
        <input type="hidden" name="brewNstlUploadId" value=<bean:write name="Catalog" property="catalogId" /> />

    <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
        <tr align=center>
            <th rowspan="2"><strong><bean:message key="ReconciliationCatalog.catalogData"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>            
            <th colspan="5"><strong><bean:message key="ReconciliationCatalog.aimsData"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
        </tr>

        <tr align=center>            
            <%--
            <td ><strong ><bean:message key="ReconciliationCatalog.developer"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
            <td ><strong ><bean:message key="ReconciliationCatalog.application"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
            <td ><strong ><bean:message key="ReconciliationCatalog.handset"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
            <td ><strong ><bean:message key="ReconciliationCatalog.version"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
            --%>
            <th ><strong ><bean:message key="ReconciliationCatalog.alliance"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
            <th ><strong ><bean:message key="ReconciliationCatalog.application"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
            <th ><strong ><bean:message key="ReconciliationCatalog.device"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
            <th ><strong ><bean:message key="ReconciliationCatalog.version"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
            <th ><strong ><input type=checkbox checked disabled=true></strong></th>
        </tr>

        <%int iCount=0, iIndex=0;
        String[] aCheckboxes = request.getParameterValues("selectedCombo");
        String[] aDropDownValues = request.getParameterValues("reconcileCombineId");
        boolean bCheck = false;
        boolean bRPresent = aCheckboxes != null && aDropDownValues != null;
        String dropDownMatch = "";
        String strCombValue = null;
        %>
        <logic:iterate id="dEnteries" name="CatalogDataExt" type="com.netpace.aims.controller.application.BrewNstlDataExt" scope="request">
                <%
                    if ( bRPresent && iIndex < aCheckboxes.length && iCount == java.lang.Integer.parseInt(aCheckboxes[iIndex])  )
                    {bCheck = true; dropDownMatch = aDropDownValues[iCount]; iIndex++; } else bCheck=false;
                %>
            <tr>
                
            <td>
                <strong><bean:message key="ReconciliationCatalog.developer"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>:<br/>
                <bean:write name="dEnteries" property="developerName"/> <br/>
                <strong><bean:message key="ReconciliationCatalog.application"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>:<br/>
                <bean:write name="dEnteries" property="applicationName"/> <br/>
                <strong><bean:message key="ReconciliationCatalog.handset"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>:<br/>
                <bean:write name="dEnteries" property="handset"/> <br/>
                <strong><bean:message key="ReconciliationCatalog.version"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>:<br/>
                <bean:write name="dEnteries" property="version"/>
            </td>


            <logic:present name="dEnteries" property="linkedDetails">
                <td ><input type=hidden name="reconcileCombineId" value="<bean:write name="dEnteries" property="linkedDetails.combinedId" />"/><bean:write name="dEnteries" property="linkedDetails.companyName" /></td>
                <td ><bean:write name="dEnteries" property="linkedDetails.applicationName" /></td>
                <td ><bean:write name="dEnteries" property="linkedDetails.deviceModel" /></td>
                <td ><bean:write name="dEnteries" property="linkedDetails.version" /></td>
                <td ><input type=checkbox name="selectedCombo" value="<%=iCount%>" checked disabled/></td>
            </logic:present>

            <logic:notPresent name="dEnteries" property="linkedDetails">
                <logic:present name="dEnteries" property="brewAppsId"> <!-- Brew Apps Id present -->
                <td ><bean:write name="dEnteries" property="companyName" /></td>
                <td ><bean:write name="dEnteries" property="appTitle" /></td>
                <td >
                    <select name="reconcileCombineId" class="selectField" >
                    <logic:iterate id="device" name="dEnteries" property="supportedDevices" type="com.netpace.aims.model.application.AimsBrewAppDevice" >
                          <% strCombValue = dEnteries.getBrewAppsId().toString() + "-" + dEnteries.getDataEntryId().toString() + "-" + device.getDeviceId().toString();%>
                         <option value="<%=strCombValue%>" <%if (bCheck && strCombValue.equals(dropDownMatch)) out.write("selected");%>><bean:write name="device" property="aimsDevice.deviceModel" /></option>
                    </logic:iterate>
                    </select>
                </td>
                <td ><bean:write name="dEnteries" property="appVersion" /></td>
                <td ><input type=checkbox name="selectedCombo" value="<%=iCount%>" <%if (bCheck) {%>checked<%}%>></td>
                </logic:present>

                <!-- Brew Apps Present Ends -->
                    <logic:notPresent name="dEnteries" property="brewAppsId"> <!-- Brew Apps Id not Present -->
                        <logic:present name="dEnteries" property="definedMatch">
                            <td ><input type=hidden name="reconcileCombineId" value="<bean:write name="dEnteries" property="definedMatch.combinedId" />"/><bean:write name="dEnteries" property="definedMatch.companyName" /></td>
                            <td ><bean:write name="dEnteries" property="definedMatch.applicationName" /></td>
                            <td ><bean:write name="dEnteries" property="definedMatch.deviceModel" /></td>
                            <td ><bean:write name="dEnteries" property="definedMatch.version" /></td>
                            <td ><input type=checkbox name="selectedCombo" value="<%=iCount%>" <%if (bCheck) {%>checked<%}%>></td>
                        </logic:present>
                            <logic:notPresent name="dEnteries" property="definedMatch">
                            <!-- Possible Match Starts -->
                        <logic:present name="dEnteries" property="possibleMatch">
                            <td  colspan="4">
                                <select name="reconcileCombineId" class="inputField" style="width:400px">
                                    <logic:iterate id="apps" name="dEnteries" property="possibleMatch" type="com.netpace.aims.controller.application.AllianceApplicationDeviceExt">
                                          <% strCombValue = apps.getBrewAppsId().toString() + "-" + dEnteries.getDataEntryId().toString() + "-" + apps.getDeviceId().toString();%>
                                         <option value="<%=strCombValue%>" <%if (bCheck && strCombValue.equals(dropDownMatch)) out.write("selected");%>><bean:write name="apps" property="displayValue" /></option>
                                    </logic:iterate>
                                </select>
                            </td>
                            <td>
                                <input type=checkbox name="selectedCombo" value="<%=iCount%>" <%if (bCheck) {%>checked<%}%>>
                            </td>
                        </logic:present>

                    <logic:notPresent name="dEnteries" property="possibleMatch">
                        <td  colspan="5" >
                            <input type=hidden name="reconcileCombineId" value="0"/>
                            <bean:message key="ReconciliationCatalog.noDataMatch"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>,
                            <a href="javascript:handleChild(<bean:write name="dEnteries" property="dataEntryId" />);//">
                                <bean:message key="ReconciliationCatalog.manualSelection" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </a>
                        </td>
                    </logic:notPresent>
                    <!-- Possible Match Ends -->
                    </logic:notPresent>	<!-- defined match not present -->
                        </logic:notPresent> <!-- Brew Id not present-->
                    </logic:notPresent> <!-- Linked details not present ends -->
            </tr>

                <% iCount++; %>
        </logic:iterate>
    </table>

    <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
            <td align=left >&nbsp;
                <bean:message key="ReconciliationCatalog.totalApplications" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <%=iCount%>
            </td>
            <td align=right >
                <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Submit" title="Submit">
                    <div>
                        <div><div onclick="document.forms[0].submit()">Submit</div></div>
                    </div>
                </div>
                <!--<input type="image" src="images/submit_b.gif" border=0>-->
            </td>
        </tr>
    </table>

</html:form>

</div>
</div>
</div>
