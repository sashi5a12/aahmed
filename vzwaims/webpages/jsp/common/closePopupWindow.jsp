<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Verizon Developer Community - Go to Market</title>
    <script type="text/javascript" language="javascript">
    	function closeWindow(){
    		var close='<c:out value="${requestScope.CLOSE}"/>';
    		var contactId='<c:out value="${requestScope.contactId}"/>';
    		var type='<c:out value="${requestScope.cType}"/>';
            var parentPageType='<c:out value="${requestScope.parentPageType}"/>';
            var parentPath='<c:out value="${requestScope.parentPath}"/>';
            if (close=='<%=AimsConstants.PAGE_TYPE_ALLIANCE_CONTACT_UPDATE%>'){
				//alert('close contact window and refresh alliance contact page.');
				opener.document.forms[0].action="<c:out value='${pageContext.request.contextPath}'/>/allianceContactInfoSetup.do?task=editForm&cType="+type+"&contactId="+contactId;
				opener.document.forms[0].submit();
			  	self.close();
			}
			else if (close=='CHANGE_ALL_ADMIN'){
				//alert('close contact window and refresh change alliance admin window.');			
				opener.document.forms[0].action="<c:out value='${pageContext.request.contextPath}'/>/changeAllianceAdmin.do?&adminUserId="+contactId;
				opener.document.forms[0].submit();
			  	self.close();				
			}
            else if(close=='<%=AimsConstants.PAGE_TYPE_LOGIN_ALLIANCE_CONTACT_UPDATE%>') {
                opener.document.forms[0].action="<c:out value='${pageContext.request.contextPath}'/>/"+parentPath+"&cType="+type+"&contactId="+contactId;
				opener.document.forms[0].submit();
			  	self.close();
            }
            else {
				self.close();
			}
    	}
    </script>
  </head>
  
  <body>
  	<script>closeWindow();</script>
  </body>
</html>
