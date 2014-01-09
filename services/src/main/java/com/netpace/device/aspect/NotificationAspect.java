package com.netpace.device.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotificationAspect {
    
    private final static Log log = LogFactory.getLog(NotificationAspect.class);
    
    @Pointcut("@annotation(com.netpace.device.annotation.EnableNotification)")
    public void pointcutEnablingNotification(){}
    
    @Before("pointcutEnablingNotification()")
    public void beforeEnablingNotification(){
        log.debug("before");
    }
    
    @After("pointcutEnablingNotification()")
    public void afterEnablingNotification(){
        log.debug("after");
    }
    @AfterReturning("pointcutEnablingNotification()")
    public void afterReturningEnablingNotification(){
        log.debug("AfterReturning");
    }
    
    @AfterThrowing("pointcutEnablingNotification()")
    public void afterThrowingEnablingNotification(){
        System.out.println("AfterThrowing");
    }
}
