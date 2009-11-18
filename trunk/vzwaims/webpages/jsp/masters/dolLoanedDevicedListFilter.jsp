<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%--<bean:message key="DeviceOnLoan.DeviceOnLoanFilter" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/> --%>
<%--<bean:message key="DeviceOnLoan.FilterBy" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>--%>
<%--<bean:message key="DeviceOnLoan.FilterText" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>--%>

<form method="post" action="devicesonloan.do">
    <input type="hidden" name="task" value="filterview">
    <input type="hidden" name="deviceId" value="<bean:write name="LoanedDeviceListForm" property="deviceId"/>"/>
    <table>
        <tr>
            <td>
                <input type="text" name="filterText" size="20"
                       value="<bean:write name="LoanedDeviceListForm" property="filterText"/>" class="inputField">
            </td>
            <td>
                <select name="filterField" size="1" class="inputField">
                    <option value="">
                        <bean:message key="DeviceOnLoan.PleaseSelect"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                    </option>
                    <option value="companyName"
                            <logic:equal name="LoanedDeviceListForm" property="filterField" value="companyName">selected
                            </logic:equal>
                            >Company Name
                    </option>
                    <option value="esnDec"
                            <logic:equal name="LoanedDeviceListForm" property="filterField" value="esnDec"> selected
                            </logic:equal>
                            >ESN-DEC
                    </option>
                    <option value="esn"
                            <logic:equal name="LoanedDeviceListForm" property="filterField" value="esn"> selected
                            </logic:equal>
                            >ESN-HEX
                    </option>
                    <option value="mtn"
                            <logic:equal name="LoanedDeviceListForm" property="filterField" value="mtn"> selected
                            </logic:equal>
                            >MTN
                    </option>
                </select>
            </td>
            <td valign="middle">
                <div class="redBtn" id="Go" title="Go">
                    <div>
                        <div>
                            <div onclick="document.forms[0].submit()">Go</div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <!--<input type="image" src="images/go_b.gif" width="27" height="18">-->
                <a href="/aims/devicesonloan.do?task=dolsearchoptions&deviceId=<bean:write name="LoanedDeviceListForm" property="deviceId"/>">
                    Advanced Search Options
                </a>
            </td>
        </tr>
    </table>
</form>


