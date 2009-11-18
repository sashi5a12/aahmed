<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>

<DIV class="homeColumn lBox">

<DIV class="headLeftCurveblk"></DIV>
   <H1>Device on Loan Data File Information</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">

<html:form action="/reconcileDeviceOnLoanFileUpload" enctype="multipart/form-data">

    <table width="100%">
        <tr>
            <td >
                <strong>Device <span class="requiredText">*</span>:</strong>
            </td>
            <td >
                <html:select property="deviceId" size="1" styleClass="selectField">
                    <html:option value="0">Please Select One...</html:option>
                    <html:optionsCollection property="allDevices" label="deviceModel" value="deviceId"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td >
                <strong><bean:message key="ReconciliationBrew.dataFile" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <span class="requiredText">*</span>:</strong>
            </td>
            <td >
                <html:file property="deviceOnLoanFile" styleClass="inputField"/>
            </td>
        </tr>
        <tr>
            <td colspan=2 align=right >
                <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Submit" title="Submit">
                    <div>
                        <div><div onclick="document.forms[0].submit()">Submit</div></div>
                    </div>
                </div>
                <!--<input type=image src="images/submit_b.gif" border=0>-->
            </td>
        </tr>
    </table>

</html:form>

</div>
</div>    

