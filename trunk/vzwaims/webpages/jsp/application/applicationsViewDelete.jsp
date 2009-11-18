<%@ page language="java" import="com.netpace.aims.bo.application.*" %>

<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/application/applicationsViewDelete_body.jsp"/>
    <tiles:put name="headingText">

        <logic:equal name="app_type" scope="request" value="<%=ManageApplicationsConstants.APP_TYPE_ALL_APPS%>">
            <bean:message key="ManageApplicationForm.view.allApplications"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>
        <logic:equal name="app_type" scope="request" value="<%=ManageApplicationsConstants.APP_TYPE_MY_APPS%>">
            <bean:message key="ManageApplicationForm.view.myApplications"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>
        <logic:equal name="app_type" scope="request" value="<%=ManageApplicationsConstants.APP_TYPE_NEW_APPS%>">
            <bean:message key="ManageApplicationForm.view.newApplications"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>
        <logic:equal name="app_type" scope="request" value="<%=ManageApplicationsConstants.APP_TYPE_TEST_APPS%>">
            <bean:message key="ManageApplicationForm.view.testApplications"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>
        <logic:equal name="app_type" scope="request" value="<%=ManageApplicationsConstants.APP_TYPE_ARCHIVE_APPS%>">
            <bean:message key="ManageApplicationForm.view.archiveApplications"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>

        <!--app_type=new_brew_apps-->
        <logic:equal name="app_type" scope="request" value="<%=ManageApplicationsConstants.APP_TYPE_NEW_BREW_APPS%>">
            <bean:message key="ManageApplicationForm.list.newBrewApp"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>

        <!--app_type=new_brew_concepts-->
        <logic:equal name="app_type" scope="request"
                     value="<%=ManageApplicationsConstants.APP_TYPE_NEW_BREW_CONCEPTS%>">
            <bean:message key="ManageApplicationForm.list.newBrewConcepts"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>

        <!--app_type=new_ent_apps-->
        <logic:equal name="app_type" scope="request"
                     value="<%=ManageApplicationsConstants.APP_TYPE_NEW_ENTERPRISE_APPS%>">
            <bean:message key="ManageApplicationForm.list.newEntApp"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>

        <!--app_type=new_mms_apps-->
        <logic:equal name="app_type" scope="request" value="<%=ManageApplicationsConstants.APP_TYPE_NEW_MMS_APPS%>">
            <bean:message key="ManageApplicationForm.list.newMmsApp"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>

        <!--app_type=new_sms_apps-->
        <logic:equal name="app_type" scope="request" value="<%=ManageApplicationsConstants.APP_TYPE_NEW_SMS_APPS%>">
            <bean:message key="ManageApplicationForm.list.newSmsApp"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>

        <!--app_type=new_wap_apps-->
        <logic:equal name="app_type" scope="request" value="<%=ManageApplicationsConstants.APP_TYPE_NEW_WAP_APPS%>">
            <bean:message key="ManageApplicationForm.list.newWapApp"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>

        <!--app_type=new_vcast_video_apps-->
        <logic:equal name="app_type" scope="request"
                     value="<%=ManageApplicationsConstants.APP_TYPE_NEW_VCAST_VIDEO_APPS%>">
            <bean:message key="ManageApplicationForm.list.newVcastApp"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>

        <logic:equal name="app_type" scope="request"
                     value="<%=ManageApplicationsConstants.APP_TYPE_SAVED_APPS%>">
            <bean:message key="ManageApplicationForm.list.savedApps"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </logic:equal>

    </tiles:put>
    <tiles:put name="filterHolder" value="/application/applicationsViewDeleteFilter.jsp"/>
</tiles:insert>  
