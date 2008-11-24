<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>order_indexed.jsp</title>
  </head>
  
  <body>
<html:form action="/SaveOrderIndexed" >
    <table width="100%" border="1">
        <tr>
            <th width="25%">Product ID</th>
            <th width="60%">Name</th>
            <th width="15%">Quantity</th>
        </tr>
        <logic:iterate id="orderItem" name="OrderIndexedForm" property="orderList">
            <html:hidden name="orderItem" property="productId" indexed="true" />
            <tr>
                <td><bean:write name="orderItem" property="productId" /></td>
                <td><bean:write name="orderItem" property="productName" /></td>
                <td><html:text name="orderItem" property="quantity" size="30" indexed="true" /></td>
            </tr>
        	</logic:iterate>
            
            <tr>
                <td>Product-6</td>
                <td>Name-6</td>
                <td>
                	<input type="text" name="orderItem[5].quantity" size="30" value="51">
                	<input type="hidden" name="orderItem[5].productId" value="Name-6">
				</td>
            </tr>
            <tr>
                <td>Product-7</td>
                <td>Name-7</td>
                <td>
                	<input type="text" name="orderItem[6].quantity" size="30" value="61">
                	<input type="hidden" name="orderItem[6].productId" value="Name-7">
				</td>
            </tr>
        
    </table>
    <html:submit>Save</html:submit>
</html:form>


  </body>
</html:html>
