package com.netpace.device.resolvers;

import com.netpace.device.enums.EventType;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.notification.services.EventService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 *
 * @author Noorain
 */
public class NotifyExceptionResolver extends SimpleMappingExceptionResolver {

    private Log log = LogFactory.getLog(NotifyExceptionResolver.class);
    @Autowired
    private EventService eventService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;

    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception e) {
        log.error("*********************************************************");
        log.error("*************** An Exception has occured. ***************", e);
        log.error("*********************************************************");
        sendNotification(e);
        return super.doResolveException(request, response, handler, e);
    }

    public void sendNotification(Throwable e) {
        log.debug("********** Sending Exception Notification Email Start. **********");

        Map<String, String> params = new HashMap<String, String>();

        params.put(VAPConstants.PLACEHOLDER_EXCEPTION_STACK_TRACE,
                ExceptionUtils.getStackTrace(e));
        // Raise partner EXCEPTION_EMAIL_SENT Event
        eventService.raiseEvent(EventType.EXCEPTION_EMAIL.toString(),
                params, applicationPropertiesService.exceptionSendtoEmail(),
                null, null, null);
        
        log.debug("********** Sending Exception Notification Email End. **********");
    }
}
