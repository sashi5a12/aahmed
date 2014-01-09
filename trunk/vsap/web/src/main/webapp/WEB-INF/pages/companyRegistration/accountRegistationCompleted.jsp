<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Single Column Starts -->
<h1 class="redheading">Request to join Company</h1>
<!-- sucess message starts -->
<c:if test="${activated}">
    <div class="success"><p>Your account has been activated.</p></div>
</c:if>
<!-- sucess message ends -->
<p>A company with the corporate domain you provided already exists within the system. You can request to join this company by clicking the request to join button below.</p>     
<br />
<br />
<p>If you choose not to join the company, you will not be able to submit products or access any company information within the portal.</p>  

<div class="clearboth"></div>

<br />
<br />
<br />
<br />
<br />
<br />
<a class="button mLeftBtn" href="${pageContext.request.contextPath}/registration.do?logout" style="float:right;"><span class="gray">Cancel</span></a>
<a class="button floatRight" href="${pageContext.request.contextPath}/company/joinCompany.do" ><span class="red">Request to join company</span></a>
<div class="clearboth"></div>    

<!-- Single Column Ends -->
<!-- Registration Column Starts --><!-- Registration In Column Ends -->
<div class="clearboth"></div>