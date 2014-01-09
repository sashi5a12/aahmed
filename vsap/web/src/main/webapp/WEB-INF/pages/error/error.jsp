<%@page isErrorPage="true" import="org.apache.commons.logging.*" contentType="text/html" %>
<%
    Log log = LogFactory.getLog(this.getClass().getName());
    log.error("Exception: ", exception);
%>
<div class="content">
    <div class="system-error">
        <h1>Error</h1>
        <h2>Unknown error occurred!</h2>
        <h3>Please contact administrator.</h3>
    </div>
</div>
