package com.netpace.vic.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({
    @Property(name = "service.description", value = "Servlet to start application"),
    @Property(name = "service.vendor", value = "Netpace"),
    @Property(name = "sling.servlet.paths", value = {"/bin/startApp"
    })
})
public class StartApplication extends SlingAllMethodsServlet {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("StartApplication.doPost========================Start");
        HttpSession session=request.getSession();
        if (!request.getSession().isNew()){
            session=request.getSession(true);            
        }
        session.setAttribute("START_APP", "true");
        //request.getRequestDispatcher("http://172.25.25.67:4502/content/vic/en/apply.html").forward(request, response);
        response.sendRedirect("/content/vic/startApp/applyNow.html");
    }
}
