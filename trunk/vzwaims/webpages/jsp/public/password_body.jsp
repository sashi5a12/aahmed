<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<%@ include file="/common/error.jsp" %>

<DIV class="homeColumn lBox floatL">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>Your password</H1>
        <DIV class="headRightCurveblk"></DIV>

        <DIV class="contentbox">
        <strong><bean:write name="responseMsg"/></strong>
        <br/>
        <br/>
         <br/>
        Please <a class="a" href="loginSetup.do">login</a> with valid username and password <br/><br/>
        Or try <a class="a" href="/aims/forgotPasswordSetup.do">retrieving</a> your password again.
    </DIV>
</DIV>
