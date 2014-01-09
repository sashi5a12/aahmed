<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>


<spring:eval expression="T(com.netpace.device.utils.VAPConstants).CSS_TYPE" var="CSS_TYPE" />
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" var="JS_TYPE" />

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).PRODUCT" var="PRODUCT" />
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).FINEUPLOAD" var="FINEUPLOAD" />
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JQUERY_UI" var="JQUERY_UI" />
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).ROLE_PARTNER_USER" var="ROLE_PARTNER_USER" />

<tg:includestatic type="${JS_TYPE}" paramName="${PRODUCT}" />
<tg:includestatic type="${JS_TYPE}" paramName="${FINEUPLOAD}" />

<tg:includestatic type="${CSS_TYPE}" paramName="${JQUERY_UI}" />
<tg:includestatic type="${CSS_TYPE}" paramName="${FINEUPLOAD}" />

<style>
	.qq-upload-list {
		text-align: left;
	}
	.qq-upload-button {
	    background: none repeat scroll 0 0 #EEEEEE;
	    border-bottom: 0px solid #DDDDDD;
	    color: #333333;
	    display: block;
	    padding: 0px 0;
	    text-align: LEFT;    	
	}	
	
	li.alert-success{
		background-color: #EEEEEE;
	}
	li.alert-error {
		background-color: #F2DEDE;
	}
	.alert-error .qq-upload-failed-text {
		display: inline;
	}
	.qq-upload-list li {
		background-color: #EEEEEE;
	}

        workflowButton{}
</style>
        
<spring:eval expression="T(com.netpace.device.utils.VAPSecurityManager).getAuthenticationToken()" var="loggedInUser"/>

<h1 class="redheading" >Partner Processing</h1>
<div class="emptyBoxLayouts" style="padding:0px; margin-top:-10px; margin-bottom:10px;">
    <div class="threeColmnLayout">
        <div class="columnOne">
            <label class="inputlabel">Company Name: </label>
            <span class="label-value">${partnerProcessVO.company.name}</span>
            <div class="clearboth"></div>      
        </div>
        <div class="columnTwo" style="width:500px">
            <sec:authorize ifNotGranted="${ROLE_PARTNER_USER}">
                <label class="inputlabel"> Workflow Phase - Status: </label>
                <span class="label-value">${partnerProcessVO.company.workFlowSteps}</span>
            </sec:authorize>
            <div class="clearboth"></div>                                
        </div>
        <div class="clearboth"></div>
    </div>
</div>

<a href="${pageContext.request.contextPath}/secure/company.do?companyid=${partnerProcessVO.company.id}" class="bigTabGray"><span><span>Partner Info</span></span></a>
<a href="javascript:void(0);" class="bigTab"><span><span>Workflow Processing</span></span></a>
<div class="clearboth"></div> 
<br />

<tg:wfworkitems companyId="${partnerProcessVO.company.id}" workitems="${partnerProcessVO.workitems}" offlineCertNdaId="${partnerProcessVO.company.offlineCertNdaId}" offlineCertNdaName="${partnerProcessVO.company.offlineCertNdaName}" loggedInUserRoles="${loggedInUser.roles}"/>

<tg:wfcomments companyId="${partnerProcessVO.company.id}" comments="${partnerProcessVO.comments}" redirectUrl="/secure/company/process.do" />

<div class="clearboth"></div>
<script type="text/javascript">
    var defaultEmailText='<%=VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT%>';
    var defaultCommentText='<%=VAPConstants.COMMENT_DEFAULT_TEXT%>';

    $('div.grayBoxLayouts textarea.wfCommentBox').focus(function(e) {
        if ($(this).val() == defaultEmailText || $(this).val() == defaultCommentText) {
            $(this).val("");
        }
    }); 
    
    $('div.grayBoxLayouts textarea.wfCommentBox').blur(function(e) {
        if ($(this).val() == null || $(this).val() == '') {
            $(this).val(defaultCommentText);
        }
    });

    $('div.grayBoxLayouts textarea').focus(function(e) {
        if ($(this).val() == defaultEmailText || $(this).val() == defaultCommentText) {
            $(this).val("");
        }
    }); 
    
    $('div.grayBoxLayouts textarea').blur(function(e) {
        if ($(this).val() == null || $(this).val() == '') {
            $(this).val(defaultEmailText);
        }
    });
</script> 