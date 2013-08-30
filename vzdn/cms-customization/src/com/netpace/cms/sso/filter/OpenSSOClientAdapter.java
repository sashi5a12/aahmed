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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import com.sun.identity.idm.IdRepoException;
import com.sun.identity.idm.IdUtils;

/**
 * Adapter of OpenSSO client SDK
 * 
 * @author g.fernandes@sourcesense.com
 * 
 */
public class OpenSSOClientAdapter {

	private static Log logger = LogFactory.getLog(OpenSSOClientAdapter.class);
	
	public static final String ATTR_UID = "uid";
	public static final String ATTR_LAST_NAME = "sn";
	public static final String ATTR_FULL_NAME = "cn";
	public static final String ATTR_EMAIL = "mail";
	public static final String ATTR_HOME_ADDRESS = "postaladdress";
	public static final String ATTR_TELEFONE = "telephonenumber";
	public static final String ATTR_GROUPS = "memberof";

	protected SSOTokenManager tokenManager;

	public OpenSSOClientAdapter() {
		try {
			tokenManager = SSOTokenManager.getInstance();
		} catch (SSOException e) {
			logger.error("Error obtained SSOTOkenManager instance");
		}
	}

	/**
	 * Tries to create an SSOToken based on the HTTP request
	 * 
	 * @param request
	 * @return The token or null if session not valid
	 */
	public  SSOToken createTokenFrom(HttpServletRequest request) {
		System.out.println("OpenSSOClientAdapter.createTokenFrom: Start");
		if(logger.isDebugEnabled()){
			logger.debug("OpenSSOClientAdapter.createTokenFrom: Start");
		}		
		SSOToken token = null;
		try {
//			token = tokenManager.createSSOToken(request);
			Cookie[] requestCookies = request.getCookies();
			for (int i = 0; i < requestCookies.length; i++) {
				Cookie cookie = requestCookies[i];
				String ssoTokenId = java.net.URLDecoder.decode(cookie.getValue(), "UTF-8");
				if (cookie.getName().equals(AlfrescoFacade.OPENSSO_COOKIE_NAME)) {
					token = tokenManager.createSSOToken(ssoTokenId);
					if(logger.isDebugEnabled()){
						logger.debug("token: "+token);
					}
					boolean sessionValid = tokenManager.isValidToken(token);
					if (sessionValid) {
						return token;
					}
				}
			}			
		} catch (SSOException e) {
			logger.info("Request does not contain a valid session");
			logger.error(e,e);
		} catch (UnsupportedEncodingException e) {			
			logger.error(e,e);
		}
		if(logger.isDebugEnabled()){
			logger.debug("OpenSSOClientAdapter.createTokenFrom: End");
		}
		System.out.println("OpenSSOClientAdapter.createTokenFrom: End");
		return token;
	}

	protected List<String> extractGroupNameFromFQGroup(Set<String> cngroups) {
		ArrayList<String> groups = new ArrayList<String>();
		for (String group : cngroups) {
			String commonName = group.substring(group.indexOf("=") + 1, group.indexOf(","));
			groups.add(commonName);
		}
		if (groups.size() == 0) {
			return null;
		}
		return groups;
	}

	/*public List<String> getGroups(SSOToken token) {
		try {
			Set<String> cngroups = IdUtils.getIdentity(token).getAttribute(ATTR_GROUPS);
			return extractGroupNameFromFQGroup(cngroups);

		} catch (SSOException e) {
			e.printStackTrace();
		} catch (IdRepoException e) {
			e.printStackTrace();
		}
		return null;
	}*/

	public String getUserAttribute(String attribute, SSOToken token) {
		String attributeValue = null;
		try {
			Set<String> attributeValues = (Set<String>) IdUtils.getIdentity(token).getAttribute(attribute);
			if (!attributeValues.isEmpty()) {
				attributeValue = attributeValues.toArray()[0].toString();
			}
		} catch (SSOException e) {
			e.printStackTrace();
		} catch (IdRepoException e) {
			e.printStackTrace();
		}
		return attributeValue;
	}

	public String getPrincipal(SSOToken token) {
		String principal = null;
		try {
			principal = token.getProperty("UserId").toLowerCase();
		} catch (SSOException e) {
			e.printStackTrace();
		}
		return principal;
	}

	public void destroyToken(SSOToken token) {
		try {
			tokenManager.destroyToken(token);
		} catch (SSOException e) {
			e.printStackTrace();
		}
	}
}