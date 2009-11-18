package com.netpace.vzdn.webapp.actions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import com.netpace.vzdn.model.VzdnCredentials;

import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import com.netpace.vzdn.model.VzdnMenus;
import com.netpace.vzdn.model.VzdnSubMenus;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.service.ISysRoleService;
import com.netpace.vzdn.service.TypeService;
import com.netpace.vzdn.service.UsersService;
import com.netpace.vzdn.util.CSVUtil;
import com.netpace.vzdn.exceptions.InvalidCSVFormatException;
import com.netpace.vzdn.exceptions.InvalidUserTypeException;
import com.netpace.vzdn.exceptions.NoRolesFoundException;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.global.VzdnConstants;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Ikramullah Khan Action class dealing with User login and session
 *         managemnt tasks.
 */
public class LoginAction extends BaseAction implements Preparable, LoginMarker{

	private static Logger log = Logger.getLogger(LoginAction.class);

	private static final long serialVersionUID = 1;
	private VzdnUsers loggedInUser;
	private UsersService userService;
	private ISysRoleService roleService;
	private Set<VzdnSysPrivileges> allPrivileges = new HashSet<VzdnSysPrivileges>();
	private List<VzdnUsers> allUsers;

	private String redirectURL;
	
	
	
	private TypeService typesService;

	public String processLogin() {
		
		try {
			
			getSession().removeAttribute(VzdnConstants.LOGGED_IN_USER);
			getSession().removeAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);			
			VzdnCredentials vzdnCredentials = getUserInfoFromCookie();

			// if user is found in the cookie
			if (null != vzdnCredentials.getUserName()) {
				log.info("Got the User Name=" + vzdnCredentials.getUserName());
				log.info("Got the User Roles=" + vzdnCredentials.getManagerRoles());

				// check if the user already exists in the system, if yes, make
				// sure the roles he brings
				// from opensso are synchronized with the database.
				try {
					loggedInUser = userService.getUserByUserName(vzdnCredentials.getUserName());
					System.out.println("user name="+ loggedInUser.getUserName());
					Set<VzdnSysRoles> userRoles = new HashSet<VzdnSysRoles>();					
					userRoles = synchronizeRoles(vzdnCredentials.getManagerRoles());					
					loggedInUser.setRoles(userRoles);

					loggedInUser.setFirstName(vzdnCredentials.getFirstName());
					loggedInUser.setLastName(vzdnCredentials.getLastName());
					loggedInUser.setGtmCompanyId(vzdnCredentials.getGtmCompanyId());
					loggedInUser.setTitle(vzdnCredentials.getTitle());
					loggedInUser.setPhoneNumber(vzdnCredentials.getPhone());
					loggedInUser.setFaxNumber(vzdnCredentials.getFax());
					loggedInUser.setMobileNumber(vzdnCredentials.getMobile());
					loggedInUser.setCountry(vzdnCredentials.getCountry());
					loggedInUser.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
					//loggedInUser.setUserType(typesService.)
					
					userService.updateUser(loggedInUser);
				}
				// If the user does not exist, create new user with the given
				// roles
				catch (UserNotFoundException unfe) {
					log
							.info("User does not exist in the system, creating one.");
					createNewUser(vzdnCredentials);
					log.info("New user created successfully");
				} catch (InvalidCSVFormatException invalidCsv) {
					log.info("The given CSV format in invalid");
					addActionError("Invalid Role Data: See the CSV for this user in OpenSSO:");
					return INPUT;
				} catch (NoRolesFoundException nrfe) {
					log.info("This user has no roles:");
					addActionError("Problem with User Credentials: Please Contact your System Administrator");
					return INPUT;
				} catch (RoleNotFoundException rnfe) {
					log.info("One of the roles with mentioned mapping id does not exist");
					addActionError("Problem with User Credentials: Please Contact your System Administrator");
					return INPUT;
				}
		
				Set<VzdnSysRoles> userRoles = loggedInUser.getRoles();
				getSession().setAttribute("userRoles", userRoles);
				loggedInUser.setRoles(userRoles);

				Set<VzdnSubMenus> myInfoMenus, communityMenus, gotoMarketMenus, managementMenus;

				myInfoMenus = getSubMenusOnMenuId(userRoles,VzdnConstants.MyInfoMenus);
				communityMenus = getSubMenusOnMenuId(userRoles,VzdnConstants.CommunityMenus);
				gotoMarketMenus = getSubMenusOnMenuId(userRoles,VzdnConstants.GoToMarketMenus);
				managementMenus = getSubMenusOnMenuId(userRoles,VzdnConstants.ManagementMenu);

				loggedInUser.setMyInfoMenus(myInfoMenus);
				loggedInUser.setCommunityMenus(communityMenus);
				loggedInUser.setGotoMarketMenus(gotoMarketMenus);
				loggedInUser.setManagementMenus(managementMenus);

				int pageSize = loggedInUser.getPageSize();

				if (pageSize == 0) {
					ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
					pageSize = Integer.parseInt(rb.getString("default.page.size"));
					loggedInUser.setPageSize(pageSize);
				}
				getSession().setAttribute(VzdnConstants.LOGGED_IN_USER, loggedInUser);
				
				Hashtable<String, VzdnSysPrivileges> userPrivileges = getPrivilegesFromRoles(userRoles);
				getSession().setAttribute(VzdnConstants.VZDN_USER_PRIVILEGES, userPrivileges);
				//deviceAnyWhere.action
				if( getServletRequest().getSession().getAttribute("servletURL")!=null && getServletRequest().getSession().getAttribute("servletURL").toString().contains("deviceAnyWhere.action") ){
					log.debug("In login action --> Redireting to "+getServletRequest().getServletPath());
					redirectURL=getServletRequest().getSession().getAttribute("servletURL").toString();
					getServletRequest().getSession().removeAttribute("servletURL");
					return "redirect";
				}else{
					return SUCCESS;
				}
				
				
				
			}
			return INPUT;

		} catch (NoRolesFoundException invalid) {
			log.error(invalid);
			addActionError(invalid.toString());
			return INPUT;
		} catch (RoleNotFoundException rnfe) {
			log.error("One of the roles in the CSV is not valid: Make sure all the roles in the CSV are already part of the vzdn system"
							+ rnfe);
			addActionError("Problem with user's crdentials. Contact your System Admin");
			return INPUT;
		} catch (InvalidCSVFormatException invalid) {
			log.error("Invalid Credentials" + invalid);
			addActionError("Invalid Credentials");
			return INPUT;
		} catch (Exception e) {
			log.error("Some Problem with processLogin() method" + e.toString());
			e.printStackTrace();
			addActionError("Problem with processing login: Try again");
			return INPUT;
		}
	}
	
	public Hashtable<String, VzdnSysPrivileges> getPrivilegesFromRoles(Set<VzdnSysRoles> roles){
		Hashtable<String, VzdnSysPrivileges> userPrivileges = new Hashtable<String, VzdnSysPrivileges>();
		for(VzdnSysRoles role : roles){
			Set<VzdnSysPrivileges> pvlgs = role.getPrivileges();
			for(VzdnSysPrivileges pvlg : pvlgs){
				userPrivileges.put(pvlg.getPrivilegeKey(), pvlg);
			}
		}
		return userPrivileges;
	}

	public Set<VzdnSysRoles> synchronizeRoles(String managerRoles)
			throws InvalidCSVFormatException, NoRolesFoundException, Exception {
		
		Set<VzdnSysRoles> userRoles = new HashSet<VzdnSysRoles>();
		if(null != managerRoles){
			userRoles = CSVUtil.CSVToRolesSet(managerRoles);
		}
		Set<VzdnSysRoles> extractedUserRoles = new HashSet<VzdnSysRoles>();

		for (VzdnSysRoles role : userRoles) {
			VzdnSysRoles tempRole = roleService.getRolesOnMappingId(role
					.getRoleId());
			if (null != tempRole
					&& tempRole.getRoleId().intValue() != VzdnConstants.DEFAULT_VZDN_CORE_ROLE)
				extractedUserRoles.add(tempRole);
		}
		
		if(loggedInUser.getUserType().getTypeId() == VzdnConstants.VerizonUser && extractedUserRoles.size() == 0){
			throw new NoRolesFoundException("Verizon user must have at least one role in ldap");
		}
		
		if(loggedInUser.getUserType().getTypeId() == VzdnConstants.DeveloperUser){
			VzdnSysRoles defaultVzdnCoreRole = roleService.getRolesOnId(new Integer(VzdnConstants.DEFAULT_VZDN_CORE_ROLE));
			extractedUserRoles.add(defaultVzdnCoreRole);
			System.out.println("\n\nDefault User Added="+ defaultVzdnCoreRole.getRoleName());
			System.out.println("\n\nSize of tempRoles=" + extractedUserRoles.size());
		}
		
		return extractedUserRoles;
	}
	
	public VzdnCredentials getUserInfoFromCookie()
			throws InvalidUserTypeException {

		VzdnCredentials tempCredentials = new VzdnCredentials();
		log.debug("User information at the time of login:");
		try {
			SSOTokenManager stm = SSOTokenManager.getInstance();
			Cookie[] requestCookies = getServletRequest().getCookies();
			String userName = "";
			for (int i = 0; i < requestCookies.length; i++) {
				Cookie cookie = requestCookies[i];
				if (cookie.getName().equals("iPlanetDirectoryPro")) {
					SSOToken st = stm.createSSOToken(getServletRequest());
					userName = st.getPrincipal().getName();
					userName = userName.substring(userName.indexOf("=") + 1,
							userName.indexOf(",")).toLowerCase();
					System.out
							.println("got the cookie user name : " + userName);
					Cookie ssoCookie = new Cookie("loggedInUserInfo", userName);
					getServletResponse().addCookie(ssoCookie);
					tempCredentials.setUserName(userName);
					log.debug("userName="+userName);
				}

			}

			// picking first name
			String firstName = (String) getServletRequest().getHeader(
					VzdnConstants.OPENSSO_ATTRIBUTE_FIRST_NAME_RH);
			
			log.debug("firstName="+firstName);
			
			if (null != firstName && !firstName.isEmpty()) 
					tempCredentials.setFirstName(firstName);

			// picking last name
			String lastName = (String) getServletRequest().getHeader(
					VzdnConstants.OPENSSO_ATTRIBUTE_LAST_NAME_RH);
			
			log.debug("lastName="+lastName);
			
			if (null != lastName && !lastName.isEmpty()) 
						tempCredentials.setLastName(lastName);			

			// picking roles
			System.out.println("manager-roles="
					+ getServletRequest().getHeader(
							VzdnConstants.OPENSSO_ATTRIBUTE_USER_ROLES));
			
			

			String roleSet = (String) getServletRequest().getHeader(
					VzdnConstants.OPENSSO_ATTRIBUTE_USER_ROLES);						

			tempCredentials.setManagerRoles(roleSet);
			
			log.debug("user roles="+roleSet);

			// picking user-type
			String userTypeSet = (String) getServletRequest().getHeader(
					VzdnConstants.OPENSSO_ATTRIBUTE_USER_TYPE);
			
			log.debug("user type="+userTypeSet);

			System.out.println("\n\n\nuserTypeSet=" + userTypeSet + "\n\n\n");
			
			if ("Verizon".equals(userTypeSet)) {
				tempCredentials.setUserTypeId(VzdnConstants.VerizonUser);
			} 
			else if ("Developer".equals(userTypeSet) || null == userTypeSet || "".equals(userTypeSet)) {
				tempCredentials.setUserTypeId(VzdnConstants.DeveloperUser);
			}			
			
			System.out.println("\n\n\nUser Type set =" + tempCredentials.getUserTypeId() + "\n\n\n");
			
			//picking GTM-Company-ID
			String gtmCompanyId = (String) getServletRequest().getHeader(VzdnConstants.OPENSSO_ATTRIBUTE_COMP_ID_RH);
			tempCredentials.setGtmCompanyId(gtmCompanyId);
			
			log.debug("gtm company id="+userTypeSet);
					
			String title = (String) getServletRequest().getHeader(VzdnConstants.OPENSSO_ATTRIBUTE_USER_TITLE);
			tempCredentials.setTitle(title);
			
			log.debug("title="+title);
			
			String phoneNumber = (String) getServletRequest().getHeader(VzdnConstants.OPENSSO_ATTRIBUTE_USER_PHONE);
			tempCredentials.setPhone(phoneNumber);
			
			log.debug("phoneNumber="+phoneNumber);
			
			String faxNumber = (String) getServletRequest().getHeader(VzdnConstants.OPENSSO_ATTRIBUTE_USER_FAX);
			tempCredentials.setFax(faxNumber);
			
			log.debug("faxNumber="+faxNumber);

			String mobileNumber = (String) getServletRequest().getHeader(VzdnConstants.OPENSSO_ATTRIBUTE_USER_MOBILE);
			tempCredentials.setMobile(mobileNumber);
			
			log.debug("mobileNumber="+mobileNumber);
			
			//picking Country
			String country = (String) getServletRequest().getHeader(
					VzdnConstants.OPENSSO_ATTRIBUTE_USER_COUNTRY);
			
			log.debug("country="+country);
			
			tempCredentials.setCountry(country);
		
			return tempCredentials;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return tempCredentials;
		}
	}

	private void createNewUser(VzdnCredentials vzdnCredentials)
			throws InvalidCSVFormatException, Exception {
		loggedInUser = new VzdnUsers();
		
		List<Integer> typeIds = new ArrayList<Integer>();
		
		if(null != vzdnCredentials.getManagerRoles()){
			typeIds = CSVUtil.CSVToRolesList(vzdnCredentials.getManagerRoles());
		}

		Set<VzdnSysRoles> tempRoles = new HashSet<VzdnSysRoles>();

		for (Integer typeId : typeIds) {
			VzdnSysRoles tempRole = roleService.getRolesOnMappingId(typeId);

			if (null != tempRole
					&& tempRole.getRoleId().intValue() != VzdnConstants.DEFAULT_VZDN_CORE_ROLE) {
				tempRoles.add(tempRole);
				System.out.println("\n\n -=-=-=-=-=- role added for the user+" + tempRole.getRoleDescription());
			}
		}
		
		if (vzdnCredentials.getUserTypeId() == VzdnConstants.VerizonUser && tempRoles.size() == 0) {
			throw new NoRolesFoundException("Verizon User Must have at least one Role:");
		}

		// Every user must have a default role whenever he logs in to VZDN Core
		if (vzdnCredentials.getUserTypeId() == VzdnConstants.DeveloperUser) {
			System.out
					.println("\n\nSize of tempRoles Before adding default ... ="
							+ tempRoles.size());
			VzdnSysRoles defaultVzdnCoreRole = roleService
					.getRolesOnId(new Integer(
							VzdnConstants.DEFAULT_VZDN_CORE_ROLE));
			tempRoles.add(defaultVzdnCoreRole);
			System.out.println("\n\nDefault User Added="
					+ defaultVzdnCoreRole.getRoleName());
			System.out
					.println("\n\nSize of tempRoles after adding default ... ="
							+ tempRoles.size());
		}

		loggedInUser.setUserName(vzdnCredentials.getUserName());
		loggedInUser.setFirstName(vzdnCredentials.getFirstName());
		loggedInUser.setLastName(vzdnCredentials.getLastName());
		loggedInUser.setTitle(vzdnCredentials.getTitle());
		loggedInUser.setPassword(vzdnCredentials.getPassword());
		loggedInUser.setRoles(tempRoles);
		
		loggedInUser.setGtmCompanyId(vzdnCredentials.getGtmCompanyId());
		loggedInUser.setPhoneNumber(vzdnCredentials.getPhone());
		loggedInUser.setFaxNumber(vzdnCredentials.getFax());
		loggedInUser.setMobileNumber(vzdnCredentials.getMobile());
		loggedInUser.setCountry(vzdnCredentials.getCountry());
		
			

		VzdnTypes userStatus = typesService.getTypeById(VzdnConstants.ACTIVE_STATUS);
		loggedInUser.setStatusType(userStatus);

		VzdnTypes userType = new VzdnTypes();
		userType.setTypeId(new Integer(vzdnCredentials.getUserTypeId()));
		loggedInUser.setUserType(userType);

		loggedInUser.setCreatedBy("vzdn-core");
		loggedInUser.setCreatedDate(new Timestamp(System.currentTimeMillis()));

		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		int pageSize = Integer.parseInt(rb.getString("default.page.size"));
		loggedInUser.setPageSize(pageSize);

		userService.createNewUser(loggedInUser);
	}

	public Set<VzdnSubMenus> getSubMenusOnMenuId(Set<VzdnSysRoles> roles,
			int menuId) {

		Set<VzdnSubMenus> subMenuSet = new TreeSet<VzdnSubMenus>();

		for (VzdnSysRoles role : roles) {
			System.out.println(role.getRoleDescription());
			Set<VzdnSysPrivileges> tempPrivileges = role.getPrivileges();
			for (VzdnSysPrivileges privilege : tempPrivileges) {
				allPrivileges.add(privilege);
				VzdnSubMenus subMenu = privilege.getSubMenu();
				if (null != subMenu) {
					VzdnMenus mainMenu = subMenu.getVzdnMenus();
					if (null != mainMenu) {
						Integer mainMenuId = mainMenu.getMenuId();
						if (null != mainMenuId) {
							int menuIntValue = mainMenuId.intValue();
							if (menuIntValue == menuId)
								subMenuSet.add(subMenu);
						}
					}
				}
			}
		}
		return subMenuSet;
	}

	public String logout() {

		try {
			SSOTokenManager stm = SSOTokenManager.getInstance();
			Cookie[] requestCookies = getServletRequest().getCookies();
			String userName = "";
			for (int i = 0; i < requestCookies.length; i++) {
				Cookie cookie = requestCookies[i];
				if (cookie.getName().equals("iPlanetDirectoryPro")) {
					cookie.setMaxAge(0);
					getServletResponse().addCookie(cookie);
				}
			}
			getSession().invalidate();
			return SUCCESS;
			
		} catch (SSOException exception) {
			log.error("Could not remove the cookie or invalidate the session"+ exception);		
			return SUCCESS;
		}
	}

	public TypeService getTypesService() {
		return typesService;
	}

	public void setTypesService(TypeService typesService) {
		this.typesService = typesService;
	}

	public UsersService getUserService() {
		return userService;
	}

	public void setUserService(UsersService userService) {
		this.userService = userService;
	}

	public void prepare() {
		allUsers = userService.getAllUsers();
	}

	public LoginAction() {
	}

	public String showAllUsers() {
		return SUCCESS;
	}

	public VzdnUsers getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(VzdnUsers loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public List<VzdnUsers> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<VzdnUsers> allUsers) {
		this.allUsers = allUsers;
	}

	public ISysRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(ISysRoleService roleService) {
		this.roleService = roleService;
	}

	public String getRedirectURL() {
		return redirectURL;
	}
}