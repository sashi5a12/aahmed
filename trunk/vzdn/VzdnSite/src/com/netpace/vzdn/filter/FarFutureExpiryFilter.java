package com.netpace.vzdn.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class FarFutureExpiryFilter extends BaseFilter{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		doFilterStart(request);
		HttpServletResponse res = (HttpServletResponse) response;
		String expiresHeader = getFilterConfig().getInitParameter("expires");
		Long expiryDays = Long.parseLong(expiresHeader);
		expiryDays = System.currentTimeMillis()+ (expiryDays * 60 * 60 * 24 * 1000);
		res.setDateHeader("Expires", expiryDays);
		filterChain.doFilter(request, response);
		return;
	}
}
