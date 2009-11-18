<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<logic:present name="aimsContract">
    <div style="width:810px;">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">

            <tr>
                <td>
                    <DIV style="color:#000000;font-family:arial;font-size:1.8em;font-weight:bold;"><bean:write name="aimsContract" property="contractTitle" scope="request"/>&nbsp; (Ver: <bean:write name="aimsContract" property="version" scope="request"/>)&nbsp;</DIV>
                </td>
            </tr>
            <tr>
                <td width="100%" style="padding:10px 0px 10px 0px;">
                    <%--<div style="border: 1px solid #CCCCCC; border-collapse:collapse; padding:10px 20px 10px 20px;"></div>--%>
                    <!-- start contract html -->
                    <logic:notEmpty name="contractHTML" scope="request">
                        <bean:write name="contractHTML" filter="false" scope="request"/>
                    </logic:notEmpty>
                    <!-- end contract html -->
                </td>
            </tr>
            <tr><td>&nbsp;</td></tr>

            <%-- start contract document section --%>
            <tr>
                <td>
                    <DIV class="headLeftCurveblk"></DIV>
                    <DIV class="noLayout_h1_table_header">Contract Document</DIV>
                    <DIV class="headRightCurveblk"></DIV>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" cellspacing="0" cellpadding="0" class="noLayout_GridGradient">
                        <tr>
                            <td style="padding:5px;">
                                <a class="a" target="_blank"
                                   href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="aimsContract" property="contractId"/>'>
                                    <bean:write name="aimsContract" property="documentFileName"/>
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <%-- end contract document section--%>
        </table>
    </div>

</logic:present>
