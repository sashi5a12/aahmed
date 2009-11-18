<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>

<DIV class="homeColumn xlBox">
    <DIV class="headLeftCurveblk"></DIV>
    <H1 >Your password</H1>
    <DIV class="headRightCurveblk"></DIV>

    <DIV class="contentbox">

        <html:form action="/forgotPassword">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="2">
                    <table width="300px" border="0" cellspacing="0">
                        <tr>
                            <td ><strong>Email Address <span class="requiredText">*</span></strong></td>
                            <td style="padding-left:10px"><strong>Mother's Maiden Name <span class="requiredText">*</span></strong></td>
                        </tr>
                        <tr>
                            <td>
                                <html:text property="userName" size="25" styleClass="inputField"/>
                            </td>
                            <td style="padding-left:10px">
                                <html:text property="motherMaidenName" size="25" styleClass="inputField"/>
                            </td>
                        </tr>
                    </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        If you do not have an Alliance with Verizon Wireless, please
                        <a class="a" href="/aims/allianceRegistrationSetup.do?task=createForm">Register</a>
                        now!
                    </td>
                    <td>
                        <div class="redBtn" style="float:right;margin-left:50px; margin-top:10px;">
                            <div>
                                <div>
                                    <div onclick="document.forms.ForgotPasswordForm.submit()">Submit</div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </html:form>
    </DIV>
</DIV>
