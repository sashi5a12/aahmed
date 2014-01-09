package com.netpace.device.filters;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestExecutionTime implements HandlerInterceptor{
    private static final Log log = LogFactory.getLog(RequestExecutionTime.class);

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Date date = new Date();
    	log.debug(request.getRequestURL()+ " Start Time-------------------"+date);
    	long requestStartTime = date.getTime();
    	request.setAttribute("REQUEST_START_TIME", requestStartTime);
    	return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
	
		Date date = new Date();
		long requestStartTime=(Long)request.getAttribute("REQUEST_START_TIME");
		long requestEndTime = date.getTime();
		log.debug("Total Execution time in second(s): "+TimeUnit.MILLISECONDS.toSeconds((requestEndTime-requestStartTime)) +" sec");
		log.debug(request.getRequestURL()+ " End Time-------------------"+date);

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}


	

}
