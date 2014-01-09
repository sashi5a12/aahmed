<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Single Column Starts -->
<div class="content">
<h1 class="redheading">Request to join Company</h1>
<!-- sucess message starts -->
<div class="success"><p>Your account has been activated.</p></div>
<!-- sucess message ends -->
<p>A company with the corporate domain you provided already exists within the system. You can request to join this company by clicking the request to join button below.</p>     
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
</div>