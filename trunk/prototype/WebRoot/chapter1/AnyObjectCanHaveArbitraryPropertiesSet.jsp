<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'AnyObjectCanHaveArbitraryPropertiesSet.jsp' starting page</title>
    <script type="text/javascript" src="js/prototype-1.6.0.3.js"></script>
    <script type="text/javascript">
    	function test(){
			var object = new Object();
			object.foo = "foo";
			object.bar = "bar";
			for (var i in object) console.log(i);
			//-> foo
			//-> bar
			// Numbers have a native "toString" method
			var number = 5;
			console.log(number.toString()); //-> "5"
			Number.prototype.toString = function() {
				return String(this + 1);
			};
			console.log(number.toString()); //-> "6"			    	
    	}
    </script>
  </head>
  
  <body>
    <button onclick="test()">Click Here</button>
  </body>
</html>
