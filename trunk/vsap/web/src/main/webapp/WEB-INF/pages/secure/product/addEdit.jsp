<%@page import="com.netpace.device.utils.enums.ProductSubmissionType"%>
<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).CSS_TYPE" var="CSS_TYPE"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" var="JS_TYPE"/>

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).PRODUCT" var="PRODUCT"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).FINEUPLOAD" var="FINEUPLOAD"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JQUERY_UI" var="JQUERY_UI"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_COMMON" var="JS_COMMON"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_CHARACTERS_COUNTER" var="JS_CHARACTERS_COUNTER"/>

<tg:includestatic type="${JS_TYPE}" paramName="${JQUERY_UI}" />
<tg:includestatic type="${JS_TYPE}" paramName="${PRODUCT}" />
<tg:includestatic type="${JS_TYPE}" paramName="${FINEUPLOAD}" />
<tg:includestatic type="${JS_TYPE}" paramName="${JS_COMMON}" />
<tg:includestatic type="${JS_TYPE}" paramName="${JS_CHARACTERS_COUNTER}" />

<tg:includestatic type="${CSS_TYPE}" paramName="${JQUERY_UI}" />
<tg:includestatic type="${CSS_TYPE}" paramName="${FINEUPLOAD}" />

<form:form name="productForm" id="productForm" action="create.do" method="POST" commandName="productVO" >
 
<spring:hasBindErrors name="productVO">
    <div class="error">
        <ul>
            <form:errors path="*" element="li" delimiter="</li><li>" id="" />
        </ul>
    </div>
</spring:hasBindErrors>

<c:if test="${not empty SUCCESS_MESSAGE}">
	<div class="success"><p><fmt:message key="${SUCCESS_MESSAGE}" /></p></div>
</c:if>

<c:choose>
	<c:when test="${empty productVO.id}">
		<h1 class="redheading"><fmt:message key="productVO.create.title"/></h1>
	</c:when>
	<c:otherwise>
		<h1 class="redheading"><fmt:message key="productVO.edit.title"/></h1>	
	</c:otherwise>
</c:choose>

<div class="emptyBoxLayouts" style="padding:0px; margin-top:-10px; margin-bottom:10px;">
	<div class="threeColmnLayout">
		<div class="columnOne">
			<label class="inputlabel"><fmt:message key="companyForm.companyName"/></label><span class="label-value"><c:out value="${productVO.companyName}"></c:out></span>
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo" style="width:500px">
			<label class="inputlabel"><fmt:message key="productVO.workflow.status.label"/></label><span class="label-value"><c:out escapeXml="false" value="${productVO.worklflowStep}"/></span>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<c:if test="${productVO.productStatus ne 'Saved' }">
<div class="clearboth"></div>
<a href="#" class="bigTab"><span><span><fmt:message key="label.tab.one"/></span></span></a>
<a href="#" class="bigTabGray"><span><span><fmt:message key="label.tab.two"/></span></span></a> 
<div class="clearboth"></div> 
</c:if>
<div class="anchor-nav">
	<ul>
		<li><a href="#basicInfo"><fmt:message key="productVO.section.name.basicInfo"/></a></li>
		<li><a href="#productDetails"><fmt:message key="productVO.section.name.productDetails"/></a></li>
		<li id="hide_for_concept"><a href="#highlightedFeatures"><fmt:message key="productVO.section.name.highlightedFeatures"/></a></li>
		<li id="hide_for_concept"><a href="#productContact"><fmt:message key="productVO.section.name.productContact"/></a></li>
		<li><a href="#images"><fmt:message key="productVO.section.name.images"/></a></li>
		<li><a href="#video"><fmt:message key="productVO.section.name.video"/></a></li>
		<li><a href="#documents"><fmt:message key="productVO.section.name.documents"/></a></li>
	</ul>
</div>
<form:hidden path="id" id="id"/>
<form:hidden path="contactId"/>
<form:hidden path="pageType"/>
<form:hidden path="validRequiredFields" id="validRequiredFields"/>
<form:hidden path="btnType" id="btnType"/>
<form:hidden path="productStatus"/>
<form:hidden path="companyName"/>
<form:hidden path="dbProductType"/>
<form:hidden path="worklflowStep"/>

<form:hidden path="out_front_view.mediaId" id="out_front_view.mediaId"/>
<form:hidden path="out_front_view.fileName" id="out_front_view.fileName"/>
<form:hidden path="out_front_view.fileType" id="out_front_view.fileType"/>
<form:hidden path="out_front_view.fileFullName" id="out_front_view.fileFullName"/>

<form:hidden path="out_angeled_view.mediaId" id="out_angeled_view.mediaId"/>
<form:hidden path="out_angeled_view.fileName" id="out_angeled_view.fileName"/>
<form:hidden path="out_angeled_view.fileType" id="out_angeled_view.fileType"/>
<form:hidden path="out_angeled_view.fileFullName" id="out_angeled_view.fileFullName"/>

<form:hidden path="out_another_object.mediaId" id="out_another_object.mediaId"/>
<form:hidden path="out_another_object.fileName" id="out_another_object.fileName"/>
<form:hidden path="out_another_object.fileType" id="out_another_object.fileType"/>
<form:hidden path="out_another_object.fileFullName" id="out_another_object.fileFullName"/>

<form:hidden path="out_other_view1.mediaId" id="out_other_view1.mediaId"/>
<form:hidden path="out_other_view1.fileName" id="out_other_view1.fileName"/>
<form:hidden path="out_other_view1.fileType" id="out_other_view1.fileType"/>
<form:hidden path="out_other_view1.fileFullName" id="out_other_view1.fileFullName"/>

<form:hidden path="out_other_view2.mediaId" id="out_other_view2.mediaId"/>
<form:hidden path="out_other_view2.fileName" id="out_other_view2.fileName"/>
<form:hidden path="out_other_view2.fileType" id="out_other_view2.fileType"/>
<form:hidden path="out_other_view2.fileFullName" id="out_other_view2.fileFullName"/>

<form:hidden path="out_other_view3.mediaId" id="out_other_view3.mediaId"/>
<form:hidden path="out_other_view3.fileName" id="out_other_view3.fileName"/>
<form:hidden path="out_other_view3.fileType" id="out_other_view3.fileType"/>
<form:hidden path="out_other_view3.fileFullName" id="out_other_view3.fileFullName"/>

<form:hidden path="out_other_view4.mediaId" id="out_other_view4.mediaId"/>
<form:hidden path="out_other_view4.fileName" id="out_other_view4.fileName"/>
<form:hidden path="out_other_view4.fileType" id="out_other_view4.fileType"/>
<form:hidden path="out_other_view4.fileFullName" id="out_other_view4.fileFullName"/>

<form:hidden path="in_front_view.mediaId" id="in_front_view.mediaId"/>
<form:hidden path="in_front_view.fileName" id="in_front_view.fileName"/>
<form:hidden path="in_front_view.fileType" id="in_front_view.fileType"/>
<form:hidden path="in_front_view.fileFullName" id="in_front_view.fileFullName"/>

<form:hidden path="in_angeled_view.mediaId" id="in_angeled_view.mediaId"/>
<form:hidden path="in_angeled_view.fileName" id="in_angeled_view.fileName"/>
<form:hidden path="in_angeled_view.fileType" id="in_angeled_view.fileType"/>
<form:hidden path="in_angeled_view.fileFullName" id="in_angeled_view.fileFullName"/>

<form:hidden path="in_another_object.mediaId" id="in_another_object.mediaId"/>
<form:hidden path="in_another_object.fileName" id="in_another_object.fileName"/>
<form:hidden path="in_another_object.fileType" id="in_another_object.fileType"/>
<form:hidden path="in_another_object.fileFullName" id="in_another_object.fileFullName"/>

<form:hidden path="screen_shot1.mediaId" id="screen_shot1.mediaId"/>
<form:hidden path="screen_shot1.fileName" id="screen_shot1.fileName"/>
<form:hidden path="screen_shot1.fileType" id="screen_shot1.fileType"/>
<form:hidden path="screen_shot1.fileFullName" id="screen_shot1.fileFullName"/>

<form:hidden path="screen_shot2.mediaId" id="screen_shot2.mediaId"/>
<form:hidden path="screen_shot2.fileName" id="screen_shot2.fileName"/>
<form:hidden path="screen_shot2.fileType" id="screen_shot2.fileType"/>
<form:hidden path="screen_shot2.fileFullName" id="screen_shot2.fileFullName"/>

<form:hidden path="screen_shot3.mediaId" id="screen_shot3.mediaId"/>
<form:hidden path="screen_shot3.fileName" id="screen_shot3.fileName"/>
<form:hidden path="screen_shot3.fileType" id="screen_shot3.fileType"/>
<form:hidden path="screen_shot3.fileFullName" id="screen_shot3.fileFullName"/>

<form:hidden path="lifestyle_image1.mediaId" id="lifestyle_image1.mediaId"/>
<form:hidden path="lifestyle_image1.fileName" id="lifestyle_image1.fileName"/>
<form:hidden path="lifestyle_image1.fileType" id="lifestyle_image1.fileType"/>
<form:hidden path="lifestyle_image1.fileFullName" id="lifestyle_image1.fileFullName"/>

<form:hidden path="lifestyle_image2.mediaId" id="lifestyle_image2.mediaId"/>
<form:hidden path="lifestyle_image2.fileName" id="lifestyle_image2.fileName"/>
<form:hidden path="lifestyle_image2.fileType" id="lifestyle_image2.fileType"/>
<form:hidden path="lifestyle_image2.fileFullName" id="lifestyle_image2.fileFullName"/>

<form:hidden path="lifestyle_image3.mediaId" id="lifestyle_image3.mediaId"/>
<form:hidden path="lifestyle_image3.fileName" id="lifestyle_image3.fileName"/>
<form:hidden path="lifestyle_image3.fileType" id="lifestyle_image3.fileType"/>
<form:hidden path="lifestyle_image3.fileFullName" id="lifestyle_image3.fileFullName"/>

<form:hidden path="phone_splash_screen.mediaId" id="phone_splash_screen.mediaId"/>
<form:hidden path="phone_splash_screen.fileName" id="phone_splash_screen.fileName"/>
<form:hidden path="phone_splash_screen.fileType" id="phone_splash_screen.fileType"/>
<form:hidden path="phone_splash_screen.fileFullName" id="phone_splash_screen.fileFullName"/>

<form:hidden path="tablet_splash_screen.mediaId" id="tablet_splash_screen.mediaId"/>
<form:hidden path="tablet_splash_screen.fileName" id="tablet_splash_screen.fileName"/>
<form:hidden path="tablet_splash_screen.fileType" id="tablet_splash_screen.fileType"/>
<form:hidden path="tablet_splash_screen.fileFullName" id="tablet_splash_screen.fileFullName"/>

<form:hidden path="application_icon.mediaId" id="application_icon.mediaId"/>
<form:hidden path="application_icon.fileName" id="application_icon.fileName"/>
<form:hidden path="application_icon.fileType" id="application_icon.fileType"/>
<form:hidden path="application_icon.fileFullName" id="application_icon.fileFullName"/>

<form:hidden path="phone_in_app_screen.mediaId" id="phone_in_app_screen.mediaId"/>
<form:hidden path="phone_in_app_screen.fileName" id="phone_in_app_screen.fileName"/>
<form:hidden path="phone_in_app_screen.fileType" id="phone_in_app_screen.fileType"/>
<form:hidden path="phone_in_app_screen.fileFullName" id="phone_in_app_screen.fileFullName"/>

<form:hidden path="tablet_in_app_screen.mediaId" id="tablet_in_app_screen.mediaId"/>
<form:hidden path="tablet_in_app_screen.fileName" id="tablet_in_app_screen.fileName"/>
<form:hidden path="tablet_in_app_screen.fileType" id="tablet_in_app_screen.fileType"/>
<form:hidden path="tablet_in_app_screen.fileFullName" id="tablet_in_app_screen.fileFullName"/>

<form:hidden path="product_video.mediaId" id="product_video.mediaId"/>
<form:hidden path="product_video.fileName" id="product_video.fileName"/>
<form:hidden path="product_video.fileType" id="product_video.fileType"/>
<form:hidden path="product_video.fileFullName" id="product_video.fileFullName"/>

<form:hidden path="phone_app_video.mediaId" id="phone_app_video.mediaId"/>
<form:hidden path="phone_app_video.fileName" id="phone_app_video.fileName"/>
<form:hidden path="phone_app_video.fileType" id="phone_app_video.fileType"/>
<form:hidden path="phone_app_video.fileFullName" id="phone_app_video.fileFullName"/>

<form:hidden path="tablet_app_video.mediaId" id="tablet_app_video.mediaId"/>
<form:hidden path="tablet_app_video.fileName" id="tablet_app_video.fileName"/>
<form:hidden path="tablet_app_video.fileType" id="tablet_app_video.fileType"/>
<form:hidden path="tablet_app_video.fileFullName" id="tablet_app_video.fileFullName"/>

<form:hidden path="product_copy_doc.mediaId" id="product_copy_doc.mediaId"/>
<form:hidden path="product_copy_doc.fileName" id="product_copy_doc.fileName"/>
<form:hidden path="product_copy_doc.fileType" id="product_copy_doc.fileType"/>
<form:hidden path="product_copy_doc.fileFullName" id="product_copy_doc.fileFullName"/>

<form:hidden path="app_copy_doc.mediaId" id="app_copy_doc.mediaId"/>
<form:hidden path="app_copy_doc.fileName" id="app_copy_doc.fileName"/>
<form:hidden path="app_copy_doc.fileType" id="app_copy_doc.fileType"/>
<form:hidden path="app_copy_doc.fileFullName" id="app_copy_doc.fileFullName"/>

<form:hidden path="launch_presentation_video.mediaId" id="launch_presentation_video.mediaId"/>
<form:hidden path="launch_presentation_video.fileName" id="launch_presentation_video.fileName"/>
<form:hidden path="launch_presentation_video.fileType" id="launch_presentation_video.fileType"/>
<form:hidden path="launch_presentation_video.fileFullName" id="launch_presentation_video.fileFullName"/>

<div class="grayBoxLayouts">
	<div class="twoColmnLayout width500">
		<div class="columnOne">
			<label class="inputlabel width400"><fmt:message key="productVO.productType"/></label> 
			<c:choose>
				<c:when test="${productVO.productStatus eq 'Saved' }">
					<form:select cssClass="selct" path="productType" id="productType">
						<form:options items="${populatedFormElements['productList']}" />
					</form:select>
				</c:when>
				<c:when test="${productVO.productStatus eq 'Submitted' and productVO.dbProductType eq 'Concept (no physical product available at this time)'}">
					<form:select cssClass="selct" path="productType" id="productType">
						<form:options items="${populatedFormElements['productList']}" />
					</form:select>
				</c:when>
				<c:otherwise>
					<span class="label-value"><c:out value="${productVO.productType}"></c:out></span>		
					<form:hidden path="productType" id="productType"/>		
				</c:otherwise>
			</c:choose>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<br />

<c:import url="basicInformationInc.jsp"/>
<c:import url="productDetailInc.jsp"/>
<div id="hide_for_concept">
	<c:import url="highlightFeaturesInc.jsp"/>
</div>
<div id="hide_for_concept">
	<c:import url="productContactInc.jsp"/>
</div>
<c:import url="productImagesInc.jsp"/>
<c:import url="appImagesInc.jsp"/>
<c:import url="videosInc.jsp"/>
<c:import url="documentsInc.jsp"/>
</form:form>
<a class="button floatRight marginTop20 marginLeft10" href="#" id='btn_cancel'><span class="gray"><fmt:message key="btn.cancel"/></span></a>
<c:choose>
	<c:when test="${productVO.pageType eq 'NEW_PRODUCT' }">
		<a class="button floatRight marginTop20 marginLeft10" href="#" id='btn_save'><span class="red"><fmt:message key="btn.save"/></span></a>	
	</c:when>
	<c:when test="${productVO.pageType eq 'UPDATE_PRODUCT' }">
		<a class="button floatRight marginTop20 marginLeft10" href="#" id='btn_update'><span class="red"><fmt:message key="btn.update"/></span></a>
	</c:when>
</c:choose>
	<c:if test="${productVO.productStatus eq 'Saved'}">
		<a class="button floatRight marginTop20" href="#" id='btn_submit'><span class="red"><fmt:message key="btn.submit"/></span></a>
	</c:if>
<script type="text/javascript">
	$("#btn_save, #btn_update, #btn_submit").click(function(e) {
        e.preventDefault();
        if($('#id').val()){
        	$('form').attr('action', 'update.do');
        }
        
        if($(this).attr('id')=='btn_save'){
        	$('#btnType').val("<%=VAPConstants.BTN_SAVE%>");
        	$('#validRequiredFields').val('false');
        }
        else if($(this).attr('id')=='btn_update'){
        	$('#btnType').val("<%=VAPConstants.BTN_UPDATE%>");
        	$('#validRequiredFields').val('false');
        }
        else if($(this).attr('id')=='btn_submit'){
        	$('#btnType').val("<%=VAPConstants.BTN_SUBMIT%>");
        	$('#validRequiredFields').val('true');
        }

       	$('form').submit();
        return false;
    });
    $("#btn_cancel").click(function(e) {
        e.preventDefault();
        window.location='list.do';
        return false;
    });   
    $("#productType").change(function(e){
    	
    	$("div#hide_for_concept").toggle();
    	$("li#hide_for_concept").toggle();
    	$("div#attachments_require_for_product").toggle();
    	//$("li a[href^='#productContact']").toggle();
    });
    $("a.bigTabGray").click(function(e) {
        e.preventDefault();
        window.location='processInfoEdit.do?productId='+$('#id').val();
        return false;
    });   
    $($('input:checkbox[name=supportedDevices]')).click(function (e){
		checkSupportedDevices();    	
    }); 
    $(function() {
		$("#availabilityDate").datepicker();
		if ($("#productType").val()=="<%=ProductSubmissionType.Concept.getLabel()%>"){
			$("#productType").trigger("change");
		}
		checkSupportedDevices();
	});
	function checkSupportedDevices(){
		var showAppIcon=false;
		$("input:checkbox[name=supportedDevices]").each(function() {
    		var value=$(this).val();    		
			if(this.checked && value=='Phone'){
	    		$("#redstar_phone").show();
	    		showAppIcon=true;
	    	}
	    	else if(!this.checked && value=='Phone'){
	    		$("#redstar_phone").hide();
	    	}
	    	else if(this.checked && value=='Tablet'){
	    		$("#redstar_splash").show();
	    		showAppIcon=true;
	    	}
	    	else if(!this.checked && value=='Tablet'){
	    		$("#redstar_splash").hide();
	    	}	    	
		});
		
    	if (showAppIcon){
    		$("#redstar_appIcon").show();
    	}
    	else {
    		$("#redstar_appIcon").hide();
    	}
	}
</script>