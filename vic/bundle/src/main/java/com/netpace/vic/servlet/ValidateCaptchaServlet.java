package com.netpace.vic.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import nl.captcha.Captcha;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generates captcha image to tell whether its user is a human or a computer.
 */
@Component(immediate=true, metatype=false)
@Service(value=javax.servlet.Servlet.class)
@Properties({
    @Property(name="service.description", value="Servlet to validate captcha"),
    @Property(name="service.vendor", value="Netpace"),
    @Property(name="sling.servlet.paths", value={"/bin/validateCaptcha" 
    })
})

public class ValidateCaptchaServlet extends SlingAllMethodsServlet {
    private static final long serialVersionUID = 1L;
    
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp)
            throws ServletException, IOException {
    
    	String userCaptcha = req.getParameter("captchaText");
    	boolean validCaptcha = false;
    	
    	try {
			Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);
			req.setCharacterEncoding("UTF-8"); // Do this so we can capture	// non-Latin chars
			if (captcha.getAnswer().equalsIgnoreCase(userCaptcha)) {
				validCaptcha = true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	    	 
    	resp.setContentType("application/json");
    	PrintWriter out = resp.getWriter();
    	 
    	String jsonObject = "{ \"validCaptcha\": \"" + validCaptcha +"\"} ";
    	out.print(jsonObject);
    	out.flush();
    	
    }
}
