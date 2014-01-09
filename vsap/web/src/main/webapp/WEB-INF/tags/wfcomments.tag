<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="companyId" required="true" type="java.lang.Integer" %>
<%@attribute name="productId" required="false" type="java.lang.Integer" %>
<%@attribute name="comments" required="true" type="java.util.List" %>
<%@attribute name="redirectUrl" required="true" type="java.lang.String" %>

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).EMAIL_TEXT_BOX_DEFAULT_TEXT" var="EMAIL_TEXT_BOX_DEFAULT_TEXT"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).COMMENT_DEFAULT_TEXT" var="COMMENT_DEFAULT_TEXT"/>

<div class="grayBoxLayouts">
    <label class="inputlabel redColor">Workflow History & Comments</label>
    <div class="clearboth"></div>

    <textarea name="textarea6" class="textfield font fullwidth" style="height:100px;" readonly="true"><c:forEach items="${comments}" var="comment"><fmt:formatDate value="${comment.createdDate}" type="date" pattern="MM/dd/yyyy" /> <fmt:formatDate value="${comment.createdDate}" type="date" pattern="HH:mm:ss" />: ${comment.commentText} by ${comment.createdBy}
</c:forEach> </textarea>
    <br/>
    <br/>
<form:form name="addCommentForm" action="${pageContext.request.contextPath}/secure/addComment.do" method="post" modelAttribute="commentVO">
    <input type="hidden" name="companyId" value="${companyId}"/>
    <input type="hidden" name="productId" value="${productId}"/>
    <input type="hidden" name="redirectUrl" value="${redirectUrl}"/>
    <textarea name="commentText" id="addCommentFormCommentText" class="textfield font wfCommentBox" style="height:33px; width:620px">Add new comment text here...</textarea>
    <a class="button floatRight marginTop20 js-single-click-link"  href="javascript:void(0)" onclick="addWorkflowComments()" ><span class="red">Add Comments</span></a>
</form:form>    
    <div class="clearboth"></div>
</div>
<br />
<br />

<script type="text/javascript">
    
    function addWorkflowComments() {
        var commentText = $.trim($('#addCommentFormCommentText').val());
        
        if( commentText == '' || commentText == '${EMAIL_TEXT_BOX_DEFAULT_TEXT}' 
            || commentText == '${COMMENT_DEFAULT_TEXT}' ){
            alert('Please provide comments');
            return;
        }
        
        $('a.js-single-click-link').removeAttr("onclick");
        document.forms['addCommentForm'].submit();
        return false;
    }
    
</script>
