<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<script language="javascript">

    function truncateLocalTextAreas()
    {
        if (typeof document.forms[0].deviceAlertMessage != "undefined")
            if (typeof document.forms[0].deviceAlertMessage.value != "undefined")
                TruncateText(document.forms[0].deviceAlertMessage, 100);
    }

</script>

<html:form action="/devicesInsertUpdate">
<html:hidden property="deviceId"/>
<html:hidden property="pid1Id"/>

<div id="contentBox" onmousemove="truncateLocalTextAreas();">
<%@ include file="/common/error.jsp" %>
<DIV class="homeColumnTab lBox">
<DIV class="headLeftCurveblk"></DIV>
<H1>
    <logic:match name="DevicesForm" property="taskPerform" scope="request" value="create">
        <html:hidden property="task" value="create"/>
        <bean:message key="DeviceForm.createHeading" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
    </logic:match>
    <logic:match name="DevicesForm" property="taskPerform" scope="request" value="update">
        <bean:message key="DeviceForm.updateHeading" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
        <html:hidden property="task" value="update"/>
    </logic:match>
</H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
<tr>
    <th width="25%"><strong>Phone Model <span class="requiredText">*</span>:</strong></th>
    <th width="75%">
        <html:text styleClass="inputField" property="deviceModel" size="30" maxlength="50"/>
    </th>
</tr>
<tr>
    <td width="25%"><strong>Phone Manufacturer :</strong></td>
    <td width="75%">
        <html:select property="deviceMfgBy" styleClass="selectField">
            <html:option value="Motorola">Motorola</html:option>
            <html:option value="Nokia">Nokia</html:option>
            <html:option value="LG">LG</html:option>
            <html:option value="Samsung">Samsung</html:option>
            <html:option value="Audiovox">Audiovox</html:option>
            <html:option value="Toshiba">Toshiba</html:option>
            <html:option value="Kyocera">Kyocera</html:option>
            <html:option value="Other">Other</html:option>
        </html:select>
    </td>
</tr>

<tr>
    <td colspan="2">&nbsp;</td>
</tr>

<tr>
    <td width="25%"></td>
    <td width="75%"></td>
</tr>

<tr>
    <td colspan="2">
        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td width=150 align="right">
                    <strong>VZW Platforms available</strong><br/>
                    <html:select styleClass="selectField" property="allplatforms" size="10" multiple="true">
                        <logic:iterate id="pf" name="AimsPlatforms" type="com.netpace.aims.model.core.AimsPlatform">
                            <html:option value="<%=pf.getPlatformId().toString()%>">
                                <bean:write name="pf" property="platformName"/>
                            </html:option>
                            <BR/>
                        </logic:iterate>
                    </html:select>
                </td>
                <td width=50 align="center" style="vertical-align:middle;">
                    <table cellpadding="0" cellspacing="0" border="0" height="100">
                        <tr>
                            <td align="center" valign="bottom">
                                <img src="images/greyRndArrow.gif" border="0" alt="Add"
                                     onClick="copyToList('allplatforms', 'platform')"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">
                                <img src="images/greyRndArrowL.gif" border="0" alt="Remove"
                                     onClick="removeAndCopy(document.DevicesForm.platform, document.DevicesForm.allplatforms);"/>
                            </td>
                        </tr>                            
                    </table>
                </td>
                <td width=150 align="left">
                    <strong>VZW Platforms supported</strong><br/>
                    <html:select styleClass="selectField" property="platform" size="10" multiple="true">
                        <logic:notEmpty name="DevicesForm" property="addedPlatforms" scope="request">
                            <logic:iterate id="pf" name="DevicesForm" property="addedPlatforms"
                                           type="com.netpace.aims.model.core.AimsPlatform">
                                <html:option value="<%=pf.getPlatformId().toString()%>">
                                    <bean:write name="pf" property="platformName"/>
                                </html:option>
                                <BR/>
                            </logic:iterate>
                        </logic:notEmpty>
                    </html:select>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td colspan="2">&nbsp;</td>
</tr>
<tr>
    <td width="25%"><strong>Manufacturer web site URL: (optional)</strong></td>
    <td width="75%">
        <html:text styleClass="inputField" property="mfgWebSiteUrl" size="30" maxlength="50"/>
    </td>
</tr>
<tr>
    <td width="25%"><strong>Status <span class="requiredText">*</span>:</strong></td>
    <td width="75%">
        <html:select property="status" styleClass="selectField">
            <html:option value="Active">Active</html:option>
            <html:option value="Inactive">Inactive</html:option>
        </html:select>
    </td>
</tr>
<tr>
    <td width="25%"><strong>Alert on Submit :</strong></td>
    <td width="75%">
        <html:radio property="deviceAlert" value="Y"/>
        Yes&nbsp;
        <html:radio property="deviceAlert" value="N"/>
        No&nbsp;
    </td>
</tr>
<tr>
    <td width="25%"><strong>LBS Supported :</strong></td>
    <td width="75%">
        <html:radio property="lbsSupported" value="Y"/>
        Yes&nbsp;
        <html:radio property="lbsSupported" value="N"/>
        No&nbsp;
    </td>
</tr>
<tr>
    <td width="25%"><strong>Alert Message :</strong></td>
    <td width="75%">
        <html:textarea styleClass="textareaField" property="deviceAlertMessage" onkeyup="LimitText(this,100)"
                       onkeypress="LimitText(this,100)" rows="4" cols="32"/>
    </td>
</tr>

<tr>
    <td width="25%"><strong>Primary PID <span class="requiredText">*</span>:</strong></td>
    <td width="75%">
        <html:text styleClass="inputField" property="primaryPID1" size="30" maxlength="50"/>
    </td>
</tr>
<tr>
    <td width="25%"><strong>Secondary PID 1 :</strong></td>
    <td width="75%">
        <html:text styleClass="inputField" property="secondaryPID11" size="30" maxlength="50"/>
    </td>
</tr>
<tr>
    <td width="25%"><strong>Secondary PID 2 :</strong></td>
    <td width="75%">
        <html:text styleClass="inputField" property="secondaryPID12" size="30" maxlength="50"/>
    </td>
</tr>
<tr>
    <td width="25%"><strong>Secondary PID 3 :</strong></td>
    <td width="75%">
        <html:text styleClass="inputField" property="secondaryPID13" size="30" maxlength="50"/>
    </td>
</tr>

<tr>
    <td width="25%"><strong>Asset Type <span class="requiredText" onmouseover="return Tip('Required for VZAppZone platform');">*</span>:</strong></td>
    <td width="75%">
        <html:select property="assetType" styleClass="selectField">
            <html:option value="0"><bean:message key="message.lable.selectOne"/></html:option>
            <html:optionsCollection property="allDeviceAssetTypes" label="typeValue" value="typeId"/>
        </html:select>
    </td>
</tr>
<tr>
    <td width="25%"><strong>MPortal Device Name <span class="requiredText" onmouseover="return Tip('Required for VZAppZone platform');">*</span>:</strong></td>
    <td width="75%">
        <html:text styleClass="inputField" property="mportalDeviceName" size="30" maxlength="50"/>
    </td>
</tr>


<tr>
    <td colspan="2">&nbsp;</td>
</tr>
<tr>
    <td width="100%" align="right" colspan="2">
        <logic:match name="DevicesForm" property="taskPerform" scope="request" value="create">
            <div class="redBtn" style="float:right; padding-right:10px" id="Create" title="Create">
                <div>
                    <div>
                        <div onclick="select_all(document.DevicesForm.platform);document.forms[0].submit();">Create
                        </div>
                    </div>
                </div>
            </div>
        </logic:match>
        <logic:match name="DevicesForm" property="taskPerform" scope="request" value="update">
            <div class="redBtn" style="float:right; padding-right:10px" id="Save" title="Save">
                <div>
                    <div>
                        <div onclick="select_all(document.DevicesForm.platform);document.forms[0].submit();">Save</div>
                    </div>
                </div>
            </div>
        </logic:match>
        <div class="blackBtn" style="float:right;padding-right:10px" id="Back" title="Back">
            <div>
                <div>
                    <div onclick="window.location='/aims/devices.do?task=view'">Back</div>
                </div>
            </div>
        </div>
    </td>
</tr>
</table>


</DIV>
</DIV>
</div>
</html:form>