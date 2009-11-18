<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ include file="/common/error.jsp" %>

<DIV class="homeColumn lBox floatL">
    <DIV class="headLeftCurveblk"></DIV>
    <H1><bean:message key="ReconciliationCatalog.dataFileInfo"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
    <DIV class="headRightCurveblk"></DIV>

    <DIV class="contentbox">
        <html:form action="/reconcileSaveCatalog" enctype="multipart/form-data">
            <table width="100%">
                <tr>
                    <td>
                        <strong>
                            <bean:message key="ReconciliationCatalog.dataFile"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </strong>
                    </td>
                    <td>
                        <html:file property="brewFile" styleClass="inputField"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <strong>
                            <bean:message key="ReconciliationCatalog.comments"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </strong>
                    </td>
                    <td>
                        <html:textarea property="comments" rows="5" cols="20" styleClass="textareaField"/>
                    </td>
                </tr>
                <tr>
                    <td colspan=2 align=right>
                        <div class="redBtn" style="float:right" id="Submit" title="Submit">
                            <div>
                                <div>
                                    <div onclick="document.forms[0].submit()">Submit</div>
                                </div>
                            </div>
                        </div>
                        <!--<input type=image src="images/submit_b.gif" border=0>-->
                    </td>
                </tr>
            </table>
        </html:form>

    </div>
</div>