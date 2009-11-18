<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a" %>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r" %>

<h:outputText value="#{msg.no_tasks}" rendered="#{empty WorkflowBean.tasksToDo}" />

<a:richList id="tasks-todo-list" viewMode="details" value="#{WorkflowBean.tasksToDo}" var="r"
styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow"
altRowStyleClass="recordSetRowAlt" width="100%" pageSize="10"
initialSortColumn="created" initialSortDescending="true"
rendered="#{not empty WorkflowBean.tasksToDo}" refreshOnBind="true">

<%-- Primary column for details view mode --%>
<a:column id="col1" primary="true" width="200" style="padding:2px;text-align:left">
<f:facet name="header">
<a:sortLink id="col1-sort" label="#{msg.description}" value="bpm:description" mode="case-insensitive" styleClass="header"/>
</f:facet>
<f:facet name="small-icon">
<a:actionLink id="col1-act1" value="#{r['bpm:description']}" image="/images/icons/workflow_task.gif" showLink="false"
actionListener="#{WorkflowBean.setupTaskDialog}" action="dialog:manageTask">
<f:param name="id" value="#{r.id}" />
<f:param name="type" value="#{r.type}" />
</a:actionLink>
</f:facet>
<a:actionLink id="col1-act2" value="#{r['bpm:description']}" actionListener="#{WorkflowBean.setupTaskDialog}"
action="dialog:manageTask">
<f:param name="id" value="#{r.id}" />
<f:param name="type" value="#{r.type}" />
</a:actionLink>
</a:column>

<%-- Task type --%>
<a:column id="col2" style="padding:2px;text-align:left">
<f:facet name="header">
<a:sortLink id="col2-sort" label="#{msg.type}" value="name" mode="case-insensitive" styleClass="header"/>
</f:facet>
<h:outputText id="col2-txt" value="#{r.name}" />
</a:column>

<%-- Task id column --%>
<a:column id="col3" style="padding:2px;text-align:left">
<f:facet name="header">
<a:sortLink id="col3-sort" label="#{msg.id}" value="bpm:taskId" styleClass="header"/>
</f:facet>
<h:outputText id="col3-txt" value="#{r['bpm:taskId']}" />
</a:column>

<%-- Created Date column --%>
<a:column id="col4" style="padding:2px;text-align:left">
<f:facet name="header">
<a:sortLink id="col4-sort" label="#{msg.created}" value="created" styleClass="header"/>
</f:facet>
<h:outputText id="col4-txt" value="#{r.created}">
<a:convertXMLDate type="both" pattern="#{msg.date_time_pattern}" />
</h:outputText>
</a:column>
<%-- Due date column --%>
<a:column id="col5" style="padding:2px;text-align:left">
<f:facet name="header">
<a:sortLink id="col5-sort" label="#{msg.due_date}" value="bpm:dueDate" styleClass="header"/>
</f:facet>
<h:outputText id="col5-txt" value="#{r['bpm:dueDate']}">
<a:convertXMLDate type="both" pattern="#{msg.date_pattern}" />
</h:outputText>
</a:column>

<%-- Status column --%>

<%-- Priority column --%>

<%-- Actions column --%>

<a:dataPager styleClass="pager" />
</a:richList>
