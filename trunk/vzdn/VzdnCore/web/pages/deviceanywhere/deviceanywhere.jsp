<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
 <%@ include file="../../../commons/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>
<script type="text/javascript">
function performAct(val){
	window.open(val);
}
performAct('<%=(String)request.getAttribute("deviceAnyWhereUrl")%>');
</script>  
<s:form name="deviceAnyWhereForm" method="post" theme="simple">
</s:form>

<script type="text/javascript">
function performAct1(){
	document.deviceAnyWhereForm.action="home.action";
	document.deviceAnyWhereForm.submit();	
}
performAct1();
</script>  