package com.netpace.vzdn.webapp.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Ikramullah Khan Base Action for all actions. extends Strut's
 *         ActionSupport for many nice and ready methods related to sessions are
 *         requests
 */
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private HttpServletResponse response;

	private HttpServletRequest request;
	
	public static final long serialVersionUID = 1;
	
	public HttpSession getSession(){
		return getServletRequest().getSession();
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}
	
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public HttpServletRequest getServletRequest(){
		return request;
	}
}
