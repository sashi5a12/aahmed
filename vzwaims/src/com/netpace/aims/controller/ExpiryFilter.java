package com.netpace.aims.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 *  This is the filter class which validates the Aims User
 *
 *  @web.filter name="ExpiryFilter"
 *
 *  @web.filter-mapping url-pattern="/js/*"
 *
 */
public class ExpiryFilter implements Filter
{
	private static final Logger log = Logger.getLogger(ExpiryFilter.class.getName());
	private FilterConfig filterConfig;		
	
	public void destroy()
	{
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		if (httpRequest.getRequestURI() != null && httpRequest.getRequestURI().indexOf("fckeditor") == -1 ){			
			httpResponse.setDateHeader("Expires", -1);				
//			log.debug("js= "+ httpRequest.getRequestURI());
		}	
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}	
}