package com.packtpub.ch11.advice;

import java.lang.reflect.Method;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

public class LogBeforeAdvice implements MethodBeforeAdvice {
	
	private static Logger logger = Logger.getLogger(LogBeforeAdvice.class.getName());

	public void before(Method method, Object[] args, Object target)throws Throwable {
		long start = System.currentTimeMillis();
		logger.log(Level.INFO, "Before Advice....."+method.getName() + " starts at " + start);
	}
}