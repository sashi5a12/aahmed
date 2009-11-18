<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%-- Scripts drop-down selector --%>
<h:outputText value="#{msg.select_a_script}: " />
<h:selectOneMenu value="#{DialogManager.bean.script}">
<f:selectItems value="#{TemplateSupportBean.scriptFiles}" />
</h:selectOneMenu>