<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <s:head/>
  </head>
  
  <body>
  	<s:form action="submit" >
  		<s:select name="selectResult" list="list" label="Select Demo" listKey="key" listValue="value">
  		</s:select>

  		<s:select multiple="true" size="5" name="multiSelectResult" list="list" label="Multi Select Demo" listKey="key" listValue="value">
  		</s:select>
  		
  		<s:checkboxlist name="checkboxResult" list="list" listKey="key" listValue="value" label="Checkbox Demo"></s:checkboxlist>
  		
  		<s:radio name="radioResult" list="list" listKey="key" listValue="value" label="Radio Demo"></s:radio>
  		
  		<!-- doubleListKey="cityId" doubleListValue="name" -->
  		<s:doubleselect name="country" 
  						list="countries" 
  						listKey="countryId" 
  						listValue="name" 
  						
  						doubleName="city" 
  						doubleList="cities" 
  						doubleListKey="cityId" 
  						doubleListValue="name"></s:doubleselect>
  		<s:submit/>
  	</s:form>
  </body>
</html>
