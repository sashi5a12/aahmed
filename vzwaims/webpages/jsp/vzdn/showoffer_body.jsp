<%@ page language="java" %>

<%@ page import="com.netpace.aims.controller.contracts.* " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab xlBox">

<html:form action="/checkOffer">
    <DIV class="headLeftCurveblk"></DIV>
    <H1>User Offer</H1>
    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">

        <html:hidden property="offerId"/>
        <html:hidden property="allianceId"/>
        <html:hidden property="formOfferStatus"/>

        <html:hidden property="userName"/>
        <html:hidden property="managerRoles"/>
        <html:hidden property="userType"/>
        <html:hidden property="requestURI"/>
        <html:hidden property="firstName"/>
        <html:hidden property="lastName"/>
        <html:hidden property="phone"/>
        <html:hidden property="fax"/>
        <html:hidden property="mobile"/>
        <html:hidden property="title"/>
        
        <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">

        <tr>
            <th width="50%"><strong>Offer To&nbsp;:</strong></th>
            <th width="50%"><strong>Offer From&nbsp;:</strong></th>
        </tr>
        <tr>
            <td valign="top">
                <bean:write name="CheckOfferForm" property="offerTo"/>
            </td>
            <td>
                <bean:write name="CheckOfferForm" property="offerFrom"/>
            </td>
        </tr>
        <tr>
            <td><strong>Offer Status&nbsp;:</strong></td>
            <td><strong>Alliance&nbsp;:</strong></td>
        </tr>
        <tr>
            <td valign="top">
                <bean:write name="CheckOfferForm" property="status"/>
            </td>
            <td>    
                <bean:write name="CheckOfferForm" property="allianceName"/>
            </td>
            
        </tr>
        
        
        
    </table>
        <table width="100%" cellpadding="0" cellspacing="0" border="0" >
            <tr align="right">
                <td>
                    <div class="divButtons" style="float:right;">
                        <div class="blackBtn" style="float:left;" id="Reject" title="Reject">
                            <div>
                                <div>
                                    <div onclick="javascript:submitOfferForm('Rejected');">
                                        Reject
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="redBtn" style="float:left;" id="Accept" title="Accept">
                            <div>
                                <div>
                                    <div onclick="javascript:submitOfferForm('Accepted');">
                                        Accept
                                    </div>
                                </div>
                            </div>
                    </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div>&nbsp;</div>
</html:form>

</DIV>
</div>

<script type="text/javascript">

	function submitOfferForm(val){
		
		document.CheckOfferForm.formOfferStatus.value=val;
		document.CheckOfferForm.submit();
	
	}


</script>
