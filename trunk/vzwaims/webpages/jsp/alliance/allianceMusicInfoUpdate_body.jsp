<%@ page language="java" %>

<%@ page
        import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.alliance.*, com.netpace.aims.bo.security.*  " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<html:form action="/allianceMusicInfoEdit" enctype="multipart/form-data">
<bean:define id="alliance_id" type="java.lang.Long" name="AllianceMusicInfoForm" property="allianceId"/>
<html:hidden property="task" value="edit"/>
<html:hidden property="allianceId"/>
<html:hidden property="companyName"/>

<%@ include file="/common/error.jsp" %>

<%--<div id="contentBox" style="float:left">--%>
<DIV class="homeColumnTab lBox">

 
    <aims:getVZWAllianceTab attributeName="VCAST Music" allianceId="<%=alliance_id.toString()%>"/>

<div style="padding-bottom:10px;padding-top:10px">
    <strong>Company Name:
        <bean:write name="AllianceMusicInfoForm" property="companyName"/>
    </strong>
</div>


<DIV class="headLeftCurveblk"></DIV>
<H1> Which Products are you interested in supplying content for? </H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
        <tr>
            <th> &nbsp; </th>
            <th align="center"><b>Product<br/>Type</b></th>
            <th align="center"><b>Size of<br/>Total<br/>Catalog</b></th>
            <th align="center"><b>Size of<br/>Catalog<br/>proposed<br/>for Mobile</b></th>
            <th align="center"><b>Annual<br/>Non-Mobile Income<br>for Catalog<br/>(Most Recent Year)</b></th>
            <th align="center"><b>Annual Mobile<br>Income for Catalog<br/>(Most Recent Year)</b></th>
            <th align="center"><b>Top<br/>Selling<br/>Artists</b></th>
        </tr>
        <logic:iterate id="producttypes" name="AllianceMusicInfoForm" property="allProductTypes"
                       type="com.netpace.aims.controller.alliance.AllianceMusicRegistrationProductBean">
            <tr>
                <td valign="top">
                    <html:multibox property="productId">
                        <bean:write name="producttypes" property="productId"/>
                    </html:multibox>
                </td>
                <td valign="top">
                    <bean:write name="producttypes" filter="false" property="productName"/>
                    <input type="hidden" name="productTypeId"
                           value='<bean:write name="producttypes" property="productId" />'/>
                    <input type="hidden" name="productName"
                           value='<bean:write name="producttypes" property="productName" />'/>
                </td>
                <td valign="top" align="center">
                    <input type="text" class="inputField" size="15" maxlength="50" name="sizeTotalCatalog"
                           value='<bean:write name="producttypes" property="sizeTotalCatalog" />'/></td>
                <td valign="top" align="center">
                    <input type="text" class="inputField" size="15" maxlength="50" name="sizeMobileCatalog"
                           value='<bean:write name="producttypes" property="sizeMobileCatalog" />'/></td>
                <td valign="top" align="center">
                    <input type="text" class="inputField" size="17" maxlength="50" name="incomeNonMobile"
                           value='<bean:write name="producttypes" property="incomeNonMobile" />'/></td>
                <td valign="top" align="center">
                    <input type="text" class="inputField" size="17" maxlength="50" name="incomeMobile"
                           value='<bean:write name="producttypes" property="incomeMobile" />'/></td>
                <td valign="top" align="center"><textarea name="topSellingArtists" rows="2" cols="25"
                                                          class="textareaField" onkeyup="LimitText(this,1000)"
                                                          onkeypress="LimitText(this,1000)"><bean:write name="producttypes" property="topSellingArtists"/></textarea>
                </td>
            </tr>
        </logic:iterate>
    </table>
</DIV>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1> Content Rights </H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th>
                <strong>
                    Do you currently have rights fully cleared and documented for all content you propose for mobile?
                    <span class="requiredText">*</span>
                </strong>
            </th>
        </tr>
        <tr>
            <td valign="top">
                <html:radio property="haveRightsCleared" value="Y"/>
                Yes&nbsp;
                <html:radio property="haveRightsCleared" value="N"/>
                No
            </td>
        </tr>
        <tr>
            <td>
                <strong>Do you have exclusive rights to ALL of the proposed content?<span class="requiredText">*</span></strong>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <html:radio property="haveExclusiveRights" value="Y"/>
                Yes&nbsp;
                <html:radio property="haveExclusiveRights" value="N"/>
                No
            </td>
        </tr>
        <tr>
            <td>
                <strong>If not, what IS exclusive?</strong>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <html:textarea property="whatIsExclusive" onkeyup="LimitText(this,3000)"
                               onkeypress="LimitText(this,3000)"
                               rows="3" cols="80" styleClass="textareaField"></html:textarea>
            </td>
        </tr>
    </table>
</DIV>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1> Additional Information </H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
            <td valign="top" width="50%">
                <table width="100%" cellpadding="5" cellspacing="0" border="0" class="GridGradient">
                    <tr>
                        <th>
                            <strong>Are you currently delivering your content<br/>to us through a content provider / aggregator?
                            <span class="requiredText">*</span></strong>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <html:radio property="contentThruAggregator" value="Y"/>
                            Yes&nbsp;
                            <html:radio property="contentThruAggregator" value="N"/>
                            No
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong>Who are your current digital distribution partners<br/>(mobile or otherwise)?
                                <span class="requiredText">*</span> <br/></strong>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:textarea property="currentDistributionPartners"
                                           onkeyup="LimitText(this,3000)" onkeypress="LimitText(this,3000)" rows="4" cols="40"
                                           styleClass="textareaField" />
                        </td>
                    </tr>
                </table>
            </td>
            <td valign="top">
                <table width="100%" cellpadding="5" cellspacing="0" border="0" class="GridGradient">
                    <tr>
                        <th>
                            <strong>What is your annual revenue for the <br/>content sales you are proposing?</strong>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <html:text property="annualRevenue" size="55" maxlength="100" styleClass="inputField"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                           <strong>Additional Information<span class="requiredText">*</span></strong>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:textarea
                                property="additionalInformation"
                                onkeyup="LimitText(this,3000)"
                                onkeypress="LimitText(this,3000)" rows="5" cols="40"
                                styleClass="textareaField" />

                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</DIV>

<table width="100%">
    <tr>
        <td align="right" valign="middle">

            <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px"
                 id="Submit" title="Submit">
                <div>
                    <div>
                        <div onclick="document.forms[0].task.value='edit';document.forms[0].submit()">Submit</div>
                    </div>
                </div>
            </div>

            <div class="blackBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px"
                 id="E-mail Additional Information" title="E-mail Additional Information">
                <div>
                    <div>
                        <div onclick="window.open('/aims/allianceMusicEmailSetup.do?allianceId=<%=alliance_id.toString()%>', 'sendEmail' , 'status=no, directories=no, resizable=no, menubar=no,location=no,toolbar=no,width=630,height=550,scrollbars=no')">
                            E-mail Additional Information
                        </div>
                    </div>
                </div>
            </div>
                <%--<input type="image" src="images/submit_b.gif" border="0" onClick=";"/>--%>
        </td>
    </tr>
</table>

</div>


</html:form>

