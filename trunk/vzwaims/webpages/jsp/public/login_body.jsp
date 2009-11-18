<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>

<DIV class="homeColumn xlBox floatL">
    <DIV class="headLeftCurveblk"></DIV>
    <H1>Login</H1>
    <DIV class="headRightCurveblk"></DIV>

    <DIV class="contentbox">
        <html:form action="/login">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table width="300px" border="0" cellspacing="0">
                            <tr>
                                <td><strong>Email Address <span class="requiredText">*</span></strong></td>
                                <td style="padding-left:10px"><strong>Password <span class="requiredText">*</span></strong></td>
                            </tr>
                            <tr>
                                <td>
                                    <html:text property="userName" size="25" styleClass="inputField" onkeydown="onEnterSubmitForm(event, document.forms[0])"/>
                                </td>
                                <td style="padding-left:10px">
                                    <html:password property="password" size="25" styleClass="inputField" onkeydown="onEnterSubmitForm(event, document.forms[0])"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="left">                        
                        <a class="a" href="/aims/allianceRegistrationSetup.do?task=createForm">Register</a>
                    </td>
                    <td>
                        <%--<input type="image" name="AllSubmit" src="/aims/images/login_b.gif"/>--%>
                        <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;">
                            <div>
                                <div>
                                    <div onclick="document.forms.LoginForm.submit()">Submit</div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </html:form>
    </DIV>
</DIV>