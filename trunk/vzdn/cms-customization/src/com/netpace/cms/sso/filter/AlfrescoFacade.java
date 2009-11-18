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

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationComponent;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.alfresco.wcm.util.WCMUtil;
import org.alfresco.wcm.webproject.WebProjectInfo;
import org.alfresco.wcm.webproject.WebProjectService;
import org.alfresco.web.app.servlet.AuthenticationHelper;
import org.alfresco.web.bean.LoginBean;
import org.alfresco.web.bean.repository.User;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * Facade for Alfresco operations, such as create user and groups
 * 
 * @author g.fernandes@sourcesense.com
 * 
 */
public class AlfrescoFacade {

	private static Log logger = LogFactory.getLog(AlfrescoFacade.class);

	public static final String ROLE_CONTENT_MANAGER=AlfrescoOpenSSOFilter.props.getProperty("cms.role.content.manager");
	public static final String ROLE_CONTENT_PUBLISHER=AlfrescoOpenSSOFilter.props.getProperty("cms.role.content.publisher");	
	public static final String ROLE_CONTENT_REVIEWER=AlfrescoOpenSSOFilter.props.getProperty("cms.role.content.reviewer");	
	public static final String ROLE_CONTENT_CONTRIBUTOR=AlfrescoOpenSSOFilter.props.getProperty("cms.role.content.contributor");	
	public static final String ROLE_SUPER_ADMIN=AlfrescoOpenSSOFilter.props.getProperty("cms.role.content.superAdmin");	
	public static final String WEB_SITE_NAME=AlfrescoOpenSSOFilter.props.getProperty("cms.website.name");	
	public static final String GROUP_CONTENT_MANAGER=WEB_SITE_NAME+"-ContentManager";
	public static final String GROUP_CONTENT_PUBLISHER=WEB_SITE_NAME+"-ContentPublisher";
	public static final String GROUP_CONTENT_REVIEWER=WEB_SITE_NAME+"-ContentReviewer";
	public static final String GROUP_CONTENT_CONTRIBUTOR=WEB_SITE_NAME+"-ContentContributor";	
	public static final String GROUP_SUPER_ADMIN="ALFRESCO_ADMINISTRATORS";
	public static final String PARAM_CMS_ROLE=AlfrescoOpenSSOFilter.props.getProperty("cms.opensso.roles");
	public static final String PARAM_CMS_USER_TYPE=AlfrescoOpenSSOFilter.props.getProperty("cms.opensso.user.type");
	public static final String OPENSSO_COOKIE_NAME=AlfrescoOpenSSOFilter.props.getProperty("cms.opensso.cookie.name");
	public static final String OPENSSO_FRIST_NAME=AlfrescoOpenSSOFilter.props.getProperty("cms.opensso.first.name");
	public static final String OPENSSO_LAST_NAME=AlfrescoOpenSSOFilter.props.getProperty("cms.opensso.last.name");
	
	private TransactionService transactionService;
	private NodeService nodeService;
	private AuthenticationComponent authComponent;
	private AuthenticationService authService;
	private PersonService personService;
	private PermissionService permissionService;
	private AuthenticationService authenticationService;
	private TransactionalHelper transactionalHelper;
	private AuthorityService authorityService;
    private WebProjectService wpService;
    

	public AlfrescoFacade(ServletContext servletContext) {
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ServiceRegistry serviceRegistry = (ServiceRegistry) ctx.getBean(ServiceRegistry.SERVICE_REGISTRY);
		transactionService = serviceRegistry.getTransactionService();
		nodeService = serviceRegistry.getNodeService();
		authComponent = (AuthenticationComponent) ctx.getBean("AuthenticationComponent");
		authService = (AuthenticationService) ctx.getBean("AuthenticationService");
		personService = (PersonService) ctx.getBean("personService");
		permissionService = (PermissionService) ctx.getBean("permissionService");
		authenticationService = (AuthenticationService) ctx.getBean("authenticationService");
		authorityService = (AuthorityService) ctx.getBean("authorityService");		
		wpService = serviceRegistry.getWebProjectService();
		transactionalHelper = new TransactionalHelper(transactionService);
		
	}

	protected void setAuthenticatedUser(HttpServletRequest req, final HttpSession httpSess, final String userName) {
		authComponent.setCurrentUser(userName);
		transactionalHelper.doInTransaction(new Transactionable() {
			public Object execute() {
				User user;
				NodeRef homeSpaceRef = null;
				user = new User(userName, authService.getCurrentTicket(), personService.getPerson(userName));
				homeSpaceRef = (NodeRef) nodeService.getProperty(personService.getPerson(userName), ContentModel.PROP_HOMEFOLDER);
				user.setHomeSpaceId(homeSpaceRef.getId());
				populateSession(httpSess, user);
				return null;
			}
		});
	}

	protected void populateSession(HttpSession httpSess, User user) {
		httpSess.setAttribute(AuthenticationHelper.AUTHENTICATION_USER, user);
		httpSess.setAttribute(LoginBean.LOGIN_EXTERNAL_AUTH, Boolean.TRUE);
	}

	public void createUser(final String username, final String email, final String firstName, final String lastName) {
		transactionalHelper.doInTransaction(new Transactionable() {
			public Object execute() {
				authenticationService.createAuthentication(username, username.toCharArray());
				HashMap<QName, Serializable> properties = new HashMap<QName, Serializable>();
				properties.put(ContentModel.PROP_USERNAME, username);
				properties.put(ContentModel.PROP_FIRSTNAME, firstName);
				properties.put(ContentModel.PROP_LASTNAME, lastName);
				properties.put(ContentModel.PROP_EMAIL, getNullSafe(email));
				properties.put(ContentModel.PROP_PASSWORD, "Netp@ce!23");
				NodeRef newPerson = personService.createPerson(properties);
				permissionService.setPermission(newPerson, username, permissionService.getAllPermission(), true);
				authenticationService.setAuthenticationEnabled(username, true);
				return null;
			}

			private String getNullSafe(String email) {
				return (email == null || email.isEmpty()) ? username.concat("@") : email;
			}
		});
	}

	public Boolean existUser(final String username) {
		return (Boolean) transactionalHelper.doInTransaction(new Transactionable() {
			public Object execute() {
				return personService.personExists(username);
			}
		});
	}

	public void createOrUpdateRoles(final String principal, final String roleId){
		if (StringUtils.isEmpty(principal) || StringUtils.isEmpty(roleId)){
			return;
		}
		transactionalHelper.doInTransaction(new Transactionable() {
			public Object execute() {
				if(logger.isDebugEnabled()){
					logger.debug("AlfrescoFacade.createOrUpdateRoles: Start");
				}		
				
		        Map<String, String> userGroupRoles = new HashMap<String, String>();
				
				if (!authorityService.authorityExists("GROUP_".concat(GROUP_CONTENT_MANAGER))){
					authorityService.createAuthority( AuthorityType.GROUP, null, GROUP_CONTENT_MANAGER);
				}
				if (!authorityService.authorityExists("GROUP_".concat(GROUP_CONTENT_PUBLISHER))){
					authorityService.createAuthority( AuthorityType.GROUP, null, GROUP_CONTENT_PUBLISHER);
				}
				if (!authorityService.authorityExists("GROUP_".concat(GROUP_CONTENT_REVIEWER))){
					authorityService.createAuthority( AuthorityType.GROUP, null, GROUP_CONTENT_REVIEWER);
				}
				if (!authorityService.authorityExists("GROUP_".concat(GROUP_CONTENT_CONTRIBUTOR))){
					authorityService.createAuthority( AuthorityType.GROUP, null, GROUP_CONTENT_CONTRIBUTOR);
				}

				authorityService.removeAuthority("GROUP_".concat(GROUP_CONTENT_MANAGER), principal);
				authorityService.removeAuthority("GROUP_".concat(GROUP_CONTENT_PUBLISHER), principal);
				authorityService.removeAuthority("GROUP_".concat(GROUP_CONTENT_REVIEWER), principal);
				authorityService.removeAuthority("GROUP_".concat(GROUP_CONTENT_CONTRIBUTOR), principal);
				authorityService.removeAuthority("GROUP_".concat(GROUP_SUPER_ADMIN), principal);
				
				//New Role is content_manager 
				if (roleId.equals(ROLE_CONTENT_MANAGER)){
					authorityService.addAuthority("GROUP_".concat(GROUP_CONTENT_MANAGER), principal);
					userGroupRoles.put(principal, WCMUtil.ROLE_CONTENT_MANAGER);
					
					if(logger.isDebugEnabled()){
						logger.debug("Added as a Content Manager");
					}
				}
				//New Role is content_publisher 
				else if (roleId.equals(ROLE_CONTENT_PUBLISHER)){
					authorityService.addAuthority("GROUP_".concat(GROUP_CONTENT_PUBLISHER), principal);
					userGroupRoles.put(principal, WCMUtil.ROLE_CONTENT_PUBLISHER);
					
					if(logger.isDebugEnabled()){
						logger.debug("Added as a Content Publisher");
					}					
				}
				//New Role is content_reviewer 
				else if (roleId.equals(ROLE_CONTENT_REVIEWER)){
					authorityService.addAuthority("GROUP_".concat(GROUP_CONTENT_REVIEWER), principal);
					userGroupRoles.put(principal, WCMUtil.ROLE_CONTENT_REVIEWER);
					
					if(logger.isDebugEnabled()){
						logger.debug("Added as a Content Reviewer");
					}					
				}
				//New Role is content_contributor 
				else if (roleId.equals(ROLE_CONTENT_CONTRIBUTOR)){
					authorityService.addAuthority("GROUP_".concat(GROUP_CONTENT_CONTRIBUTOR), principal);
					userGroupRoles.put(principal, WCMUtil.ROLE_CONTENT_CONTRIBUTOR);
					
					if(logger.isDebugEnabled()){
						logger.debug("Added as a Content Contributor");
					}					
				}
				else if (roleId.equals(ROLE_SUPER_ADMIN)){
					authorityService.addAuthority("GROUP_".concat(GROUP_SUPER_ADMIN), principal);
					userGroupRoles.put(principal, WCMUtil.ROLE_CONTENT_MANAGER);
					
					if(logger.isDebugEnabled()){
						logger.debug("SUPER ADMIN.....Added as a Content Manager");
					}
				}
				
				AuthenticationUtil.setFullyAuthenticatedUser("admin");
				List<WebProjectInfo> infos=wpService.listWebProjects();
				NodeRef webNodeRef=null;
				for (WebProjectInfo webProjectInfo : infos) {
					logger.debug("Project Name: "+webProjectInfo.getName());
					if (webProjectInfo.getName().toLowerCase().indexOf(WEB_SITE_NAME.toLowerCase()) != -1){
						logger.debug("Got Name: "+webProjectInfo.getName());
						webNodeRef=webProjectInfo.getNodeRef();
					}
					
				}
								
				if (webNodeRef != null){
					logger.debug(userGroupRoles);
					wpService.inviteWebUsersGroups(webNodeRef, userGroupRoles, true);
					if(logger.isDebugEnabled()){
						logger.debug("User Invited.....");
					}
				}
				if(logger.isDebugEnabled()){
					logger.debug("AlfrescoFacade.createOrUpdateRoles: End");
				}
				return null;
			}
		});		
	}
	public void authenticateAsGuest(final HttpSession session) {
		transactionalHelper.doInTransaction(new Transactionable() {
			public Object execute() {
				authenticationService.authenticateAsGuest();
				NodeRef guestRef = personService.getPerson(PermissionService.GUEST_AUTHORITY);
				User user = new User(PermissionService.GUEST_AUTHORITY, authenticationService.getCurrentTicket(), guestRef);
				NodeRef guestHomeRef = (NodeRef) nodeService.getProperty(guestRef, ContentModel.PROP_HOMEFOLDER);
				user.setHomeSpaceId(guestHomeRef.getId());
				session.setAttribute(AuthenticationHelper.AUTHENTICATION_USER, user);
				return null;
			}
		});
	}
}
