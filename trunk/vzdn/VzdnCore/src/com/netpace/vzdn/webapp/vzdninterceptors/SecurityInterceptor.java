/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package com.netpace.vzdn.webapp.vzdninterceptors;


import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.webapp.actions.LoginMarker;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;


/**
 * A struts2 interceptor for configuring specifics of the weblogger ui.
 */
public class SecurityInterceptor extends AbstractInterceptor 
        implements StrutsStatics {
    
    private static Log log = LogFactory.getLog(SecurityInterceptor.class);
    
    
    public String intercept(ActionInvocation invocation) throws Exception {
        
        log.debug("Entering Login Interceptor");
        
        final Object action = invocation.getAction();
        final ActionContext context = invocation.getInvocationContext();
        
        HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) context.get(HTTP_RESPONSE);        
        HttpSession session = request.getSession();        
        VzdnUsers currentUser = (VzdnUsers)session.getAttribute(VzdnConstants.LOGGED_IN_USER);
		String userNameFromCookie = getUserNameFromCookie(request, response);
		
		if(request.getServletPath().contains("deviceAnyWhere"))
			request.getSession().setAttribute("servletURL", request.getServletPath());
		log.debug("request URI is ==="+request.getServletPath());
		
		System.out.println("User Name from cookie=" + userNameFromCookie);
		
		if(null != currentUser){
			if(!userNameFromCookie.equals(currentUser.getUserName())){
				if(!(action instanceof LoginMarker))
					return VzdnConstants.RESULT_LOGIN_FIRST;
				else
					return invocation.invoke();
			}
			else
			{
				//Further OpenSSO related check must come here when we 
				//know how to find out if the user has a valid Opensso Session or not.
				//If the user is not logged in from opensso, he must simple get invocation.invoke()
				//otherwise, he must get VzdnConstants.RESULT_LOGIN_FIRST;
				log.debug("Invoking the normal  URL : "+request.getServletPath());
				return invocation.invoke();
			}
		}
		else
		if(!(action instanceof LoginMarker))
			return VzdnConstants.RESULT_LOGIN_FIRST;
		else
			return invocation.invoke();
       
    }
    
    
    
    public String getUserNameFromCookie(HttpServletRequest request, HttpServletResponse response){
   
    	try {
    		SSOTokenManager stm = SSOTokenManager.getInstance();
    		Cookie[] requestCookies = request.getCookies();
    		String userName = "";
    		for (int i = 0; i < requestCookies.length; i++) {
    			Cookie cookie = requestCookies[i];
    			if (cookie.getName().equals(VzdnConstants.OPENSSO_COOKIE)) {
					SSOToken st = stm.createSSOToken(request);
					userName = st.getPrincipal().getName();
					userName = userName.substring(userName.indexOf("=") + 1, userName.indexOf(",")).toLowerCase();
					System.out.println("got the cookie user name : " + userName);
					Cookie ssoCookie = new Cookie("loggedInUserInfo", userName);
					response.addCookie(ssoCookie);
					break;
    			}
    		}
    		return userName;
    	}
    	catch(Exception ex){
    		log.error("Some issue in SecurityInterceptor while reading user info from cookie" + ex.getMessage());
    		ex.printStackTrace();
    		return null;
    	}    	
    }
}
