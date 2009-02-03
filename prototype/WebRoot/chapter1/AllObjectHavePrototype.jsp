<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'AllObjectHavePrototype.jsp' starting page</title>
    <script type="text/javascript">
		Array.prototype.double = function() {
			var newArray = [];
			// inside this function, "this" refers to the array
			for (var i = 0, length = this.length; i < length; i++) {
				newArray.push(this[i]);
				newArray.push(this[i]);
			}
			return newArray;
		};
		
		function test(){
			var exampleArray = new Array(1, 2, 3);
			// inherits all the properties of Array.prototype
			alert(exampleArray.double());
		}
	</script>
  </head>
  
  <body>
    <button onclick="test()">Click Here</button>
  </body>
</html>
