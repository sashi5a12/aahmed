package com.netpace.vzdn.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class BaseFilter implements Filter{

	private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public FilterConfig getFilterConfig(){
       return filterConfig;
    }
    
    //Clean up resources
    public void destroy() {
       this.filterConfig = null;

    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }
    
    public void doFilterStart(ServletRequest request){
        HttpServletRequest req = (HttpServletRequest) request;
        String requestedURI = req.getRequestURI();
        System.out.println("Request URI : [ " + requestedURI + " ]");        
    }

}
