<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
    <div class="homeColumnTab lBox">

        <html:form action="/changeAllianceAdminUpdate">

            <html:hidden property="allianceAdminName"/>
            <html:hidden property="allianceAdminEmailAddress"/>
            <html:hidden property="allianceAdminPhone"/>

            <div class="headLeftCurveblk"></div>
            <h1>Current Alliance Administrator</h1>
            <div class="headRightCurveblk"></div>

            <div class="contentbox">
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="GridGradient">
                    <tr>
                        <th><strong>Alliance Admin Name</strong></th>
                        <th>
                            <strong><bean:message key="AllianceForm.phoneNumber"
                                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>
                        </th>
                        <th>
                            <strong><bean:message key="AllianceForm.email"
                                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>
                        </th>
                    </tr>
                    <tr>
                        <td >
                            <bean:write name="ChangeAllianceAdminForm" property="allianceAdminName"/>
                            &nbsp;
                        </td>
                        <td >
                            <bean:write name="ChangeAllianceAdminForm" property="allianceAdminPhone"/>
                            &nbsp;
                        </td>
                        <td >
                            <bean:write name="ChangeAllianceAdminForm"
                                        property="allianceAdminEmailAddress"/>
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>

            <div>&nbsp;</div>
            <div>&nbsp;</div>
            <div class="headLeftCurveblk"></div>
            <h1>New Alliance Administrator</h1>

            <div class="headRightCurveblk"></div>
            <div class="contentbox">

                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="GridGradient">
                    <tr>
                        <th>
							The new Developer Administrator has the 'Primary User' role, allowing the ability to manage other users.<br/><br/>
							If a particular user does not appear in the drop-down list, please click on 'Manage Users' to edit.<br/><br/>
							On the "Manage Users" screen, new users can be invited to join the Verizon Developer Community. If the invited user joins VDC, the user will appear in the "Manage Users" drop-down list.
						</th>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                        <td>
                            <table cellpadding="2" cellspacing="0" border="0">
                                <tr>
                                    <td align="right" valign="middle">
                                        <html:select property="adminUserId" size="1" styleClass="selectField" >
                                            <html:option value="0">Select One...</html:option>                                            
                                            <logic:iterate id="accountManager" name="ChangeAllianceAdminForm"
                                                           property="allAdminUsers"
                                                           type="com.netpace.aims.model.core.AimsUser" scope="request">
                                                <html:option value="<%=accountManager.getUserId().toString()%>">
                                                    <%=accountManager.getAimsContact().getFirstName()%>
                                                    &nbsp;<%=accountManager.getAimsContact().getLastName()%>
                                                </html:option>
                                            </logic:iterate>
                                        </html:select>
                                    </td>
                                    <td>
                                        <div class="redBtn" >
                                            <div>
                                                <div>
                                                    <div onclick="document.forms[0].submit()">Submit</div>
                                                </div>
                                            </div>
                                        </div>
                                            <%--<html:image src="images/submit_b.gif"/>--%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>

            </div>
        </html:form>
    </div>
</div>