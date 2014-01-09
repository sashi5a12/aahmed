package com.netpace.vic.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HtmlResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

/** Servlet used to test HtmlResponse escaping */
@Component(immediate=true, metatype=false)
@Service(value=javax.servlet.Servlet.class)
@Properties({
    @Property(name="service.description", value="Paths Test Servlet"),
    @Property(name="service.vendor", value="The Apache Software Foundation"),
    @Property(name="sling.servlet.paths", value={
            "/bin/HtmlResponseServlet" 
    })
})
@SuppressWarnings("serial")
public class HtmlResponseServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
    throws ServletException,IOException {
        final HtmlResponse hr = new HtmlResponse();
        // Specific status to help recognize this servlet in tests
        final int status = HttpServletResponse.SC_GATEWAY_TIMEOUT;
        hr.setStatus(status, getClass().getName() + ": GET always fails with status " + status);
        hr.setLocation("Location: some <script>");
        hr.setTitle(getClass().getName() + ": fake response to test <escaping>");
        hr.send(response, true);
    }

	
}
