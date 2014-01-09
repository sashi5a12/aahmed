<%-- 
    Document   : statics
    Created on : Jul 4, 2013, 1:10:26 PM
    Author     : Anwaar
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="src" required="true" type="java.lang.String" %>
<%@attribute name="width" required="false" type="java.lang.String" %>
<%@attribute name="height" required="false" type="java.lang.String" %>
<%@attribute name="border" required="false" type="java.lang.String" %>
<%@attribute name="alt" required="false" type="java.lang.String" %>
<%@attribute name="style" required="false" type="java.lang.String" %>
<%@attribute name="onclick" required="false" type="java.lang.String" %>

<img src="${applicationScope.STATICS_URL}${src}?v=${applicationScope.STATICS_VERSION}" <c:if test="${not empty fn:trim(width)}" >width="${width}"</c:if> <c:if test="${not empty fn:trim(height)}" >height="${height}"</c:if> <c:if test="${not empty fn:trim(border)}" >border="${border}"</c:if> <c:if test="${not empty fn:trim(style)}" >style="${style}"</c:if> <c:if test="${not empty fn:trim(alt)}" >alt="${alt}"</c:if> <c:if test="${not empty fn:trim(onclick)}" >onclick="${onclick}"</c:if> />
