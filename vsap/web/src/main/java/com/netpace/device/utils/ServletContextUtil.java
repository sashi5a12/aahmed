package com.netpace.device.utils;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.services.ApplicationPropertiesService;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Noorain
 */
public class ServletContextUtil {

    private static final Log log = LogFactory.getLog(ServletContextUtil.class);

    public static void refreshContextAttributes(ServletContext context,
            ApplicationPropertiesService applicationProperties) {

        log.info("-----------------------------------------------------------");

        String staticsURL = applicationProperties.staticsURL();
        String staticsVersion = applicationProperties.staticsVersion();
        
        String headerURL = applicationProperties.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "header.url");
        String footerURL = applicationProperties.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "footer.url");

        log.info(ServletContextAttributes.STATICS_URL + " = [" + staticsURL + "]");
        log.info(ServletContextAttributes.STATICS_VERSION + " = [" + staticsVersion + "]");
        context.setAttribute(ServletContextAttributes.STATICS_URL, staticsURL);
        context.setAttribute(ServletContextAttributes.STATICS_VERSION, staticsVersion);
        
        log.info(ServletContextAttributes.HEADER_URL + " = [" + headerURL + "]");
        log.info(ServletContextAttributes.FOOTER_URL + " = [" + footerURL + "]");
        context.setAttribute(ServletContextAttributes.HEADER_URL, headerURL);
        context.setAttribute(ServletContextAttributes.FOOTER_URL, footerURL);        
        
        String noReplyFromAddress = applicationProperties.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "vap.notification.email.from");
        context.setAttribute(ServletContextAttributes.NO_REPLY_FROM_ADDRESS, noReplyFromAddress);
        
        String certAgreementHelpEmail = applicationProperties.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "cert.agreement.help.email");
        context.setAttribute(ServletContextAttributes.CERT_AGREEMENT_HELP_EMAIL, certAgreementHelpEmail);
                
        String submenuSupportLink = applicationProperties.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "submenu.support.link");
        context.setAttribute(ServletContextAttributes.SUBMENU_SUPPORT_LINK, submenuSupportLink);

        Integer workitemDelayInDays = applicationProperties.workitemDelayInDays();
        context.setAttribute(ServletContextAttributes.WORKITEM_DELAY_IN_DAYS, workitemDelayInDays);
    }
}