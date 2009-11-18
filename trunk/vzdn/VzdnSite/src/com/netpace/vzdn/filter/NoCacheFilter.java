package com.netpace.vzdn.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCacheFilter extends BaseFilter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		doFilterStart(request);
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String cacheControl = getFilterConfig().getInitParameter("cacheControl");
		String expires = getFilterConfig().getInitParameter("Expires");
		res.setHeader("Cache-Control", cacheControl);
		res.setDateHeader("Expires", Long.parseLong(expires));
		filterChain.doFilter(request, response);
		return;
	}

}