package com.netpace.vzdn.webapp.vzdninterceptors;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.StrutsStatics;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CacheHandlingInterceptor extends AbstractInterceptor{

	
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		ActionContext context = invocation.getInvocationContext();
		
		HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);
		if(response!=null){
			response.setHeader("Cache-Control","no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires","-1");
			System.out.println("----------------no chahe set ");
		}
		return invocation.invoke();
	}
}
