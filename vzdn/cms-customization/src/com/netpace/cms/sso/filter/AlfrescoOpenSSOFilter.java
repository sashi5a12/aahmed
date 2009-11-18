/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netpace.cms.sso.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.alfresco.web.app.servlet.AuthenticationHelper;
import org.alfresco.web.bean.LoginBean;
import org.alfresco.web.bean.repository.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iplanet.sso.SSOToken;
import com.netpace.cms.sso.utils.ConfigEnvProperties;;

/**
 * Filter implementation that replace Alfresco's default AuthenticationFilter in
 * order to provide authentication in OpenSSO
 * 
 * @author g.fernandes@sourcesense.com
 * 
 */
public class AlfrescoOpenSSOFilter implements Filter {

	private static Log logger = LogFactory.getLog(AlfrescoOpenSSOFilter.class);
	
	private String openSSOServerURL;
	private String vzdnSiteURL;
	private OpenSSOClientAdapter openSSOClientAdapter;
	private AlfrescoFacade alfrescoFacade;
	private ServletContext servletContext;
	
	private static HashSet<String> USERS=new HashSet<String>();
	
	public static ConfigEnvProperties props;		
	
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession httpSession = httpRequest.getSession();
		
		SSOToken token = getOpenSSOClient().createTokenFrom(httpRequest);
		
		boolean isLoginRequest = isLoginRequest(httpRequest);
		boolean isLogoutRequest = isLogoutRequest(httpRequest);
		boolean isNormalRequest = (token!=null && !isLoginRequest && !isLogoutRequest);
		

		if(isLoginRequest) {
			if(logger.isDebugEnabled()){
				logger.debug("Is login request.");
			}		
			httpResponse.sendRedirect(buildURLForRedirect(request));
		}
		
		if(isLogoutRequest) {
			if(logger.isDebugEnabled()){
				logger.debug("Is logout request.");
			}
			doLogout(httpSession, token);
			httpResponse.sendRedirect(getVzdnSiteURL());
		}
		
		if (isNormalRequest) {
			
			if(logger.isDebugEnabled()){
				logger.debug("Is Normal Request");
			}					
			
			String principal = getOpenSSOClient().getPrincipal(token);
			String originalPrincipal = principal;
			
			if (USERS.contains(principal) == true){
				logger.debug("Special User.... "+principal);
				principal = principal.split("@")[0];
//				principal += ".";
			}
			
			if(logger.isDebugEnabled()){
				logger.debug("principal: "+principal);
			}
			
			User user=(User)httpSession.getAttribute(AuthenticationHelper.AUTHENTICATION_USER);
			if (user==null ) {
				String roleId="";
				String userRoles = (String) httpRequest.getHeader(AlfrescoFacade.PARAM_CMS_ROLE);
	
				logger.debug("Roles from OpenSSO = "+userRoles);
				if (userRoles != null && userRoles.trim().length() >0 ){
					
					if (userRoles.contains(AlfrescoFacade.ROLE_SUPER_ADMIN)){
						roleId=AlfrescoFacade.ROLE_SUPER_ADMIN;
					}
					else if (userRoles.contains(AlfrescoFacade.ROLE_CONTENT_MANAGER)){
						roleId=AlfrescoFacade.ROLE_CONTENT_MANAGER;
					}
					else if (userRoles.contains(AlfrescoFacade.ROLE_CONTENT_PUBLISHER)){
						roleId=AlfrescoFacade.ROLE_CONTENT_PUBLISHER;
					}
					else if (userRoles.contains(AlfrescoFacade.ROLE_CONTENT_REVIEWER)){
						roleId=AlfrescoFacade.ROLE_CONTENT_REVIEWER;
					}
					else if (userRoles.contains(AlfrescoFacade.ROLE_CONTENT_CONTRIBUTOR)){
						roleId=AlfrescoFacade.ROLE_CONTENT_CONTRIBUTOR;
					}					
					else {
						if(logger.isDebugEnabled()){
							logger.debug("User does not have appropriate role so go back on login page.");
						}
//						doLogout(httpSession, token);
						httpSession.invalidate();
						httpResponse.sendRedirect(getVzdnSiteURL());
						return;
					}
				}		
				
				if (!getAlfrescoFacade().existUser(principal)) {
					if(logger.isDebugEnabled()){
						logger.debug("Going to create user in CMS");
					}
					
	//				String email = getOpenSSOClient().getUserAttribute(OpenSSOClientAdapter.ATTR_EMAIL, token);
	//				String fullName = getOpenSSOClient().getUserAttribute(OpenSSOClientAdapter.ATTR_FULL_NAME, token);
	//				String firstName = getOpenSSOClient().getUserAttribute(OpenSSOClientAdapter.ATTR_LAST_NAME, token);
					String email = originalPrincipal;
					String firstName=httpRequest.getHeader(AlfrescoFacade.OPENSSO_FRIST_NAME);
					String lastName=httpRequest.getHeader(AlfrescoFacade.OPENSSO_LAST_NAME);
					if(logger.isDebugEnabled()){
						logger.debug("Principle: "+principal);
						logger.debug("Email: "+email);
						logger.debug("FirstName: "+firstName);
						logger.debug("LastName: "+lastName);
					}					
					getAlfrescoFacade().createUser(principal, email, firstName, lastName);
					
					if(logger.isDebugEnabled()){
						logger.debug("User created successfully.");
					}
				}

				getAlfrescoFacade().createOrUpdateRoles(principal, roleId);
			}
			
			getAlfrescoFacade().setAuthenticatedUser(httpRequest, httpSession, principal);
			chain.doFilter(request, response);
		} 
		
	}


	private void doLogout(HttpSession httpSession, SSOToken token) {
		getOpenSSOClient().destroyToken(token);
		httpSession.invalidate();
	}
	
	private boolean isLoginRequest(HttpServletRequest request) {
		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String  parameter = (String ) parameterNames.nextElement();
			String[] string = request.getParameterValues(parameter);
			for (int i = 0; i < string.length; i++) {
				if(string[i]!=null && string[i].contains(":login")) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isLogoutRequest(HttpServletRequest request) {
		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String  parameter = (String ) parameterNames.nextElement();
			String[] string = request.getParameterValues(parameter);
			for (int i = 0; i < string.length; i++) {
				if(string[i]!=null && string[i].contains(":logout")) {
					return true;
				}
			}
		}
		return false;
	}

	public void init(FilterConfig config) throws ServletException {
		props=ConfigEnvProperties.getInstance();
		openSSOServerURL = props.getProperty("opensso.url");
		vzdnSiteURL=props.getProperty("vzdnsite.url");
		servletContext = config.getServletContext();
				
		USERS.add("ricardo.clements@verizonwireless.com");
		USERS.add("michael.salmon@verizonwireless.com");
		USERS.add("lawrence.rau@verizonwireless.com");
		USERS.add("larry.voss@verizonwireless.com");
		USERS.add("jonathan.firestone@verizonwireless.com");
		USERS.add("diana.lewis@verizonwireless.com");
		USERS.add("dan@qual-smart.com");
		USERS.add("dalena.good@verizonwireless.com");
		USERS.add("brian.higgins@verizonwireless.com");		

	}

	protected String getOpenSSOLoginURL() {
		return getOpenSSOServerURL() + "/UI/Login";
	}

	protected String buildURLForRedirect(ServletRequest request) {
		String serverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String alfrescoContext = ((HttpServletRequest) request).getContextPath();
		System.out.println("ServerURL: "+serverURL);
		return getOpenSSOLoginURL().concat("?goto=").concat(serverURL).concat(alfrescoContext);
	}

	public String getOpenSSOServerURL() {
		return openSSOServerURL;
	}
	
	public String getVzdnSiteURL() {
		return vzdnSiteURL;
	}

	public OpenSSOClientAdapter getOpenSSOClient() {
		if (openSSOClientAdapter == null) {
			openSSOClientAdapter = new OpenSSOClientAdapter();
		}
		return openSSOClientAdapter;
	}

	public AlfrescoFacade getAlfrescoFacade() {
		if (alfrescoFacade == null) {
			alfrescoFacade = new AlfrescoFacade(servletContext);
		}
		return alfrescoFacade;
	}

	public void setAlfrescoFacade(AlfrescoFacade alfrescoFacade) {
		this.alfrescoFacade = alfrescoFacade;
	}

	public void setOpenSSOClient(OpenSSOClientAdapter openSSOClientAdapter) {
		this.openSSOClientAdapter = openSSOClientAdapter;

	}

}
