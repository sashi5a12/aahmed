package com.netpace.notification.services;

import com.netpace.device.BaseServiceTest;
import com.netpace.device.enums.EventType;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class EventServiceTest extends BaseServiceTest {

    private static final Log log = LogFactory.getLog(EventServiceTest.class);
    
    @Resource(name = "eventService")
    private EventService eventService;
    
    @Resource(name = "emailQueueService")
    private EmailQueueService emailQueueService;
    
    @Test
    public void testAll()throws Exception{
        //testRaiseEvent();
        //testProcessQueuedMessages(5);
    }
    
    public void testRaiseEvent() throws Exception {
        
        String loggedInUserName = "super.admin";
        
        HashMap<String,String> params;
        
        String activationLink = "http://localhost:8080/device/secure/activation.do";
        String companyDomain = "netpace.com";
        String fullName = "User";
        String date = DateFormatUtils.format(new Date(), "MM/dd/yyyy");
        String resetLink = "http://localhost:8080/device/secure/activation.do";
        String username = "partneruser1";
        String invitationLink = "http://localhost:8080/device/secure/activation.do";
        String companyTitle = "Netpace Systems";
        String inviteeFullName = "Steve Jobs";
        
        params = new HashMap<String, String>();
        params.put("ACTIVATION_LINK", activationLink);
        params.put("COMPANY_DOMAIN", companyDomain);
        params.put("FULL_NAME", fullName);
        params.put("DATE", date);
        eventService.raiseEvent(EventType.USER_ACCOUNT_VALIDATION_EVENT.toString(), 
                params, "test@test.com,test2@test.com", null, null, loggedInUserName);
        
        params = new HashMap<String, String>();
        params.put("RESET_LINK", resetLink);
        params.put("FULL_NAME", fullName);
        params.put("DATE", date);
        eventService.raiseEvent(EventType.FORGOT_PASSWORD_EVENT.toString(), 
                params, "test@test.com,test2@test.com", null, null, loggedInUserName);

        params = new HashMap<String, String>();
        params.put("USERNAME", username);
        params.put("FULL_NAME", fullName);
        params.put("DATE", date);
        eventService.raiseEvent(EventType.FORGOT_USERNAME_EVENT.toString(), 
                params, "test@test.com,test2@test.com", null, null, loggedInUserName);

        params = new HashMap<String, String>();
        params.put("INVITATION_LINK", invitationLink);
        params.put("COMPANY_TITLE", companyTitle);
        params.put("COMPANY_DOMAIN", companyDomain);
        params.put("FULL_NAME", fullName);
        params.put("INVITEE_FULLNAME", inviteeFullName);
        params.put("DATE", date);
        eventService.raiseEvent(EventType.PARTNER_USER_INVITED_EVENT.toString(), 
                params, "test@test.com,test2@test.com", null, null, loggedInUserName);

        params = new HashMap<String, String>();
        params.put("INVITATION_LINK", invitationLink);
        params.put("FULL_NAME", fullName);
        params.put("INVITEE_FULLNAME", inviteeFullName);
        params.put("DATE", date);
        eventService.raiseEvent(EventType.VERIZON_USER_INVITED_EVENT.toString(), 
                params, "trafique@netpace.com", null, null, loggedInUserName);

    }
    
    public void testProcessQueuedMessages(Integer limit){
        emailQueueService.processQueuedEmails(limit);
    }
    
}
