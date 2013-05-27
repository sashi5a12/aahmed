package com.packtpub.ch11.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PerformanceInterceptor implements MethodInterceptor {
	private static Logger logger = Logger.getLogger(PerformanceInterceptor.class.getName());

	public Object invoke(MethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			Object result = invocation.proceed();
			return result;
		} 
		finally {
			long end = System.currentTimeMillis();
			long timeMs = end - start;
			logger.log(Level.INFO, "AroundAdvice......... Method: " 
								+ invocation.getMethod().getName() 
								+ " took: " 
								+ timeMs+ "ms.");
		}
	}

}
