<%@ page language="java" %>

<%@ page
        import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*,org.apache.struts.validator.DynaValidatorForm" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
    <div class="homeColumnTab lBox">

        <aims:getEditProfileTab selectionName="Notifications"/>
        <div> &nbsp; </div>

        <DIV class="headLeftCurveblk"></DIV>
        <H1>
            <bean:message key="EditProfileNotifForm.selectedNotifications"/>
        </H1>

        <DIV class="headRightCurveblk"></DIV>
        <DIV class="contentbox">
            <html:form action="/editprofilenotif" method="post">
                <input type="hidden" name="task" value="update">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                    <logic:present name="AllNotifications" scope="request">
                        <% boolean firstTime = true;%>
                        <logic:iterate id="notification" name="AllNotifications">
                            <tr>
                                <% if (firstTime) {%>
                                <th style="width:10px">
                                    <html:multibox property="eventsIds">
                                        <bean:write name="notification" property="notificationId"/>
                                    </html:multibox>
                                </th>
                                <th>
                                    <bean:write name="notification" property="notificationTitle"/>
                                </th>
                                <% firstTime = false;
                                } else {%>
                                <td style="width:10px">
                                    <html:multibox property="eventsIds">
                                        <bean:write name="notification" property="notificationId"/>
                                    </html:multibox>
                                </td>
                                <td>
                                    <bean:write name="notification" property="notificationTitle"/>
                                </td>
                                <%}%>
                            </tr>
                        </logic:iterate>
                    </logic:present>
                    <logic:notPresent name="AllNotifications" scope="request">
                        <tr>
                            <th>
                                <bean:message key="EditProfileNotifForm.notAvailable"/>
                            </th>
                        </tr>
                    </logic:notPresent>
                </table>

                <table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td>
                            <div class="redBtn" style="float:right;padding-right:10px" title="Submit">
                                <div>
                                    <div>
                                        <div onclick="document.forms[0].submit()">Submit</div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </html:form>
        </div>
    </div>
</div>
