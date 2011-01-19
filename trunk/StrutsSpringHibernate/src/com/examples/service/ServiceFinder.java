package com.examples.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServiceFinder {

	public static ApplicationContext getContext(HttpServletRequest httpRequest) {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(httpRequest.getSession().getServletContext());
	}
}
