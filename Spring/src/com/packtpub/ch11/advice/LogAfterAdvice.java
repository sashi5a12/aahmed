package com.packtpub.ch11.advice;

import java.lang.reflect.Method;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

public class LogAfterAdvice implements AfterReturningAdvice{
	private static Logger logger = Logger.getLogger(LogAfterAdvice.class.getName());
	
	public void afterReturning(Object object, Method method, Object[] args, Object target) throws Throwable{
		long end = System.currentTimeMillis();
        logger.log(Level.INFO, "After Advice......."+method.getName()+ " ends at " + end);
	}
}
