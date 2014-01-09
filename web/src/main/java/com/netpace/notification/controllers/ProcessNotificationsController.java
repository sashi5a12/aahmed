package com.netpace.notification.controllers;

import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.notification.services.EmailQueueService;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProcessNotificationsController {
    
    private static final Log log = LogFactory.getLog(ProcessNotificationsController.class);
    @Autowired
    EmailQueueService emailQueueService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    
    @RequestMapping("/secure/process/notifications.do")
    public @ResponseBody String processNotifications(HttpServletRequest request, 
                @RequestParam(value="limit", required=false) Integer limit ) {
        String message;
        
        log.debug("Processing Notifications - Started");
        
        if(limit == null){
            limit = applicationPropertiesService.processNotificaitonsBatchLimit();
        }
        
        Integer successCount = emailQueueService.processQueuedEmails(limit);
        message = "Notifications sent successfully: "+successCount;        
        
        log.debug("Processing Notifications - Completed");
        
        return message;
    }
    
}
