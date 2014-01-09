package com.netpace.device.listeners;

import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.utils.ServletContextUtil;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DeviceServletContextListener implements ServletContextListener {

    private static final Log log =
            LogFactory.getLog(DeviceServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Initialze Servlet Context");
        ServletContext context = sce.getServletContext();

        ApplicationContext webAppContext =
                WebApplicationContextUtils.getRequiredWebApplicationContext(
                context);

        ApplicationPropertiesService applicationProperties =
                (ApplicationPropertiesService) webAppContext.getBean(
                "applicationPropertiesService");

        ServletContextUtil.refreshContextAttributes(
                context, applicationProperties);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Servlet Context Destroyed");
    }
}
