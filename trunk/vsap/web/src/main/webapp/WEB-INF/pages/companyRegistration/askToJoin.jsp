<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Single Column Starts -->
<h1 class="redheading">Request to join Company</h1>
<!-- sucess message starts -->
<c:if test="${param.activationSuccessful}">
    <div class="success"><p>Your account has been activated.</p></div>
</c:if>
<!-- sucess message ends -->
<br>
<p>A company with the corporate domain you provided already exists within the system.  You can request to join this company by clicking the request to join button below.</p>
<br>
<br>
<p>If you choose not to join the company, you will not be able to submit products or access any company information within the portal.</p>

<div class="clearboth"></div>
    

<br>
<br>
<br>
<br>
<br>
<br>
<form:form name="frmasktojoin" action="asktojoin.do" method="post" >
    <input type="hidden" name="requestToJoin" value="">
    <a class="button mLeftBtn" href="javascript:;" onclick="javascript:togglePopupAskToJoinConfirm(SHOW_POPUP);" style="float:right;"><span class="gray">Cancel</span></a>
    <a class="button floatRight js-single-click-link" href="javascript:;" onclick="javascript:submitForm();"><span class="red">Request to join company</span></a>
</form:form>
<div class="clearboth"></div>

<!-- Single Column Ends -->
<!-- Registration Column Starts --><!-- Registration In Column Ends -->
<div class="clearboth"></div>
<c:import url="popupAskToJoinConfirmInc.jsp" />

<script type="text/javascript">
    function submitForm() {
        $('a.js-single-click-link').removeAttr("onclick");
        document.forms['frmasktojoin'].requestToJoin.value = 'true';
        document.forms['frmasktojoin'].submit();
    }
</script>