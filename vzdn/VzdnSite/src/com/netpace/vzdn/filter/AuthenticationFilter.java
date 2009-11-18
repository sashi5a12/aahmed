package com.netpace.vzdn.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import com.netpace.vzdn.utils.ConfigEnvProperties;
import com.netpace.vzdn.utils.VzdnConstants;

public class AuthenticationFilter implements Filter {

	protected SSOTokenManager tokenManager;
	protected ConfigEnvProperties props;	
	public void init(FilterConfig config) throws ServletException {
		try {
			tokenManager = SSOTokenManager.getInstance();
			props=ConfigEnvProperties.getInstance();
		} catch (SSOException e) {
			System.out.println("Error obtained SSOTOkenManager instance");
		} catch (Exception e){}
	}
	
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession httpSession = httpRequest.getSession();
		String serverName=request.getServerName();
		String principal=null;
		SSOToken token = createTokenFrom(httpRequest);	
		if (token != null){
			principal = getPrincipal(token);
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Logged in principal....... "+principal);
			System.out.println("Session Id: "+httpSession.getId());
			if (principal != null && principal.length() > 0){			
				httpSession.setAttribute(VzdnConstants.IS_USER_AUTHENTICATED, principal);
			}
			System.out.println("Is New: "+httpSession.isNew());
		}
		else if (serverName.indexOf("sandbox") != -1 ){
			String requestedURI = httpRequest.getRequestURI();
			if (requestedURI.indexOf(".jsp") !=-1 || requestedURI.indexOf(".sjsp") != -1 || requestedURI.equalsIgnoreCase("/")){
				String signoutParam=httpRequest.getParameter("signout");
				String sessionSignout=(String)httpSession.getAttribute(VzdnConstants.IS_USER_SIGN_OUT);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Request for sandbox........");
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>requestURI: "+requestedURI);	
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>signoutParam: "+signoutParam);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sessionSignout: "+sessionSignout);
				
				if (signoutParam != null && signoutParam.length() > 0 && signoutParam.equalsIgnoreCase("true")){
					httpSession.removeAttribute(VzdnConstants.IS_USER_AUTHENTICATED);
					httpSession.setAttribute(VzdnConstants.IS_USER_SIGN_OUT, "true");
				}
				else if (signoutParam != null && signoutParam.length()>0 && signoutParam.equalsIgnoreCase("false")){
					httpSession.removeAttribute(VzdnConstants.IS_USER_SIGN_OUT);
					httpSession.setAttribute(VzdnConstants.IS_USER_AUTHENTICATED, "true");
				}
				else if (sessionSignout == null || sessionSignout.length()==0 ){
					httpSession.removeAttribute(VzdnConstants.IS_USER_SIGN_OUT);
					httpSession.setAttribute(VzdnConstants.IS_USER_AUTHENTICATED, "true");
				}
			}
		}
		else {
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Invalidating session..........");
			httpSession.invalidate();
		}
		
		chain.doFilter(request, response);
		
	}
		
	private String getPrincipal(SSOToken token) {
		String principal = null;
		try {
			principal = token.getProperty("UserId");
		} catch (SSOException e) {
			e.printStackTrace();
		}
		return principal;
	}
		
	private  SSOToken createTokenFrom(HttpServletRequest request) {
//		System.out.println("OpenSSOClientAdapter.createTokenFrom: Start");
		String serverName=request.getServerName();
		SSOToken token = null;
		try {
			if (tokenManager != null && serverName.indexOf("sandbox") == -1){
				Cookie[] requestCookies = request.getCookies();
				if (requestCookies != null){
					for (int i = 0; i < requestCookies.length; i++) {
						Cookie cookie = requestCookies[i];
						if (cookie.getName().equals(props.getProperty("sso.cookie.name"))) {
							token = tokenManager.createSSOToken(request);
							boolean sessionValid = tokenManager.isValidToken(token);
							if (sessionValid) {
								return token;
							}
						}
					}
				}
			}
		} catch (SSOException e) {
			e.printStackTrace();
		} 
//		System.out.println("OpenSSOClientAdapter.createTokenFrom: End");
		return token;
	}
	
	
}
