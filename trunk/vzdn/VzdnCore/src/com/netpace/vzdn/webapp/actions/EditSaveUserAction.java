package com.netpace.vzdn.webapp.actions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.netpace.vzdn.exceptions.InvalidCSVFormatException;
import com.netpace.vzdn.exceptions.NoRolesFoundException;
import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnCredentials;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.model.enums.VzdnUserTypes;

import com.netpace.vzdn.security.VzdnSecurityException;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.ISysRoleService;
import com.netpace.vzdn.service.TypeService;
import com.netpace.vzdn.service.UserMgmtService;
import com.netpace.vzdn.service.UsersService;
import com.netpace.vzdn.service.opensso.OpenssoRestService;
import com.netpace.vzdn.util.CSVUtil;
import com.netpace.vzdn.util.StringFuncs;
import com.opensymphony.xwork2.Preparable;

public class EditSaveUserAction extends BaseAction implements Preparable{
	
	private static Logger log = Logger.getLogger(EditSaveUserAction.class);

	private UserMgmtService userMgmtService;

	private UsersService userService;
	
	private TypeService typesService;

	private ISysRoleService roleService;
	private Set<Integer> assignedRoles = new HashSet<Integer>();
	private Vector<String> hiddenRoles = new Vector<String>();
	private String hiddenRolesString;

	private List<VzdnSysRoles> availableRoles = new ArrayList<VzdnSysRoles>();
	
	private List<Integer> editableRoleIds = new ArrayList<Integer>();
	private List<VzdnSysRoles> editableRoles = new ArrayList<VzdnSysRoles>();
	
	private VzdnUsers user;
	private List<VzdnUsers> userlist;
	private String userTypeRadioChange = "";
	//private Set<Integer> editableRoles = new HashSet<Integer>();

	private List<VzdnTypes> userTypesList;

	public void prepare() {
		userTypesList = userService.getAllUserTypes();
	}

	public List<VzdnSysRoles> getAvailableRoles() {
		return availableRoles;
	}

	public void setAvailableRoles(List<VzdnSysRoles> availableRoles) {
		this.availableRoles = availableRoles;
	}

	public String getUserTypeRadioChange() {
		return userTypeRadioChange;
	}

	public void setUserTypeRadioChange(String userTypeRadioChange) {
		this.userTypeRadioChange = userTypeRadioChange;
	}

	public ISysRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(ISysRoleService roleService) {
		this.roleService = roleService;
	}

	public Set<Integer> getAssignedRoles() {
		return assignedRoles;
	}

	public void setAssignedRoles(Set<Integer> assignedRoles) {
		this.assignedRoles = assignedRoles;
	}

	public void setUserMgmtService(UserMgmtService userMgmtService) {
		this.userMgmtService = userMgmtService;
	}

	public VzdnUsers getUser() {
		return user;
	}

	public void setUser(VzdnUsers user) {
		this.user = user;
	}

	public List<VzdnUsers> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<VzdnUsers> userlist) {
		this.userlist = userlist;
	}

	public String list() throws VzdnSecurityException, NotLoggedInException{
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		try
		{
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_USERS);
			userlist = userMgmtService.getAllUsers();			
			return SUCCESS;
		} 
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			throw se;
		}
		catch (Exception ex) {
			log.error("problem with listing users: UserMgmtAction.list() method:\n" + ex);
			ex.printStackTrace();
			return SUCCESS;
		}
	}

	public String edit() throws VzdnSecurityException, NotLoggedInException{
		
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		try
		{
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_USERS);
		
			Integer id = Integer.valueOf(getServletRequest().getParameter("id"));
			user = userMgmtService.getUserById(id);			
			String openSSORoles = OpenssoRestService.getUserRoles(user);			
			Set<VzdnSysRoles> rolesSet = this.synchronizeRoles(openSSORoles);
			VzdnCredentials creds = OpenssoRestService.getUserAttributesFromSSO(user);
			
			user.setFirstName(creds.getFirstName());
			user.setLastName(creds.getLastName());
			user.setTitle(creds.getTitle());
			user.setFaxNumber(creds.getFax());
			user.setPhoneNumber(creds.getPhone());
			user.setMobileNumber(creds.getMobile());
			user.setGtmCompanyId(creds.getGtmCompanyId());
			user.setCountry(creds.getCountry());
			
			if (null!=rolesSet)
				user.setRoles(rolesSet);
			
			userMgmtService.updateUser(user);
			
			user = userMgmtService.getUserById(id);
	
			List<Integer> userTypes = new ArrayList<Integer>();
			List<VzdnSysRoles> allAvailableRoles = new ArrayList<VzdnSysRoles>();
			userTypes.add(VzdnUserTypes.Both.getValue());
			userTypes.add(user.getUserType().getTypeId());
			allAvailableRoles = this.loadAvailableRoles(userTypes);
			availableRoles = this.separateAvailableRoles(allAvailableRoles, user.getRoles());
			
			Collections.sort(availableRoles);
			Collections.sort(editableRoles);
			
			return SUCCESS;
		}
		
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			throw se;
		}
		
		catch (NumberFormatException e) {
			e.printStackTrace();
			return INPUT;
		}	
		catch(Exception e){
			this.addActionError("Issue with reading roles from opensso:");
			return INPUT;
		}
		
	}
	
	public Set<VzdnSysRoles> synchronizeRoles(String managerRoles) throws InvalidCSVFormatException, NoRolesFoundException, Exception{

		if( null == managerRoles)
			return null;
		
		Set<VzdnSysRoles> userRoles = CSVUtil.CSVToRolesSet(managerRoles);
		Set<VzdnSysRoles> extractedUserRoles = new HashSet<VzdnSysRoles>();
		
		int i=0;
		for(VzdnSysRoles role : userRoles){						
			VzdnSysRoles tempRole = roleService.getRolesOnMappingId(role.getRoleId());
			if(null!=tempRole){			
				extractedUserRoles.add(tempRole);
				//editableRoles.add(tempRole.getRoleId());
				
				if(tempRole.getVzdnTypes().getTypeId().intValue() == VzdnConstants.HIDDEN_ROLE)
					hiddenRoles.add(tempRole.getRoleId().toString());
				else
					editableRoles.add(tempRole);
			}
			
		}
		String[] hRoles = new String[hiddenRoles.size()];
		hiddenRoles.toArray(hRoles);
		hiddenRolesString = StringFuncs.ConvertArrToString(hRoles, ",");
		
		if(user.getUserType().getTypeId() == VzdnConstants.DeveloperUser)
		{
			VzdnSysRoles defaultRole = roleService.getRolesOnId(new Integer(VzdnConstants.DEFAULT_VZDN_CORE_ROLE));		
			extractedUserRoles.add(defaultRole);
		}
		
		return extractedUserRoles;		
	}

	public String toggleUserType() throws VzdnSecurityException, NotLoggedInException{
		
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		try
		{
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_USERS);
		
			VzdnUsers reFetchedUser = userMgmtService.getUserById(user.getUserId());
			if (user.getUserType().getTypeId().equals(VzdnUserTypes.Developer.getValue()))
				user.setManagerOrganization("");
			
			reFetchedUser.setManagerOrganization(user.getManagerOrganization());
			reFetchedUser.setUserType(user.getUserType());
			System.out.println("refetched user id :"
					+ reFetchedUser.getUserId());
			this.user = new VzdnUsers();
			user = reFetchedUser;

			List<Integer> userTypes = new ArrayList<Integer>();
			userTypes.add(VzdnUserTypes.Both.getValue());
			
			for(VzdnSysRoles role : reFetchedUser.getRoles()){
				if(role.getRoleId().intValue() != VzdnConstants.DEFAULT_VZDN_CORE_ROLE && role.getVzdnTypes().getTypeId().intValue() != VzdnConstants.HIDDEN_ROLE) 
					editableRoles.add(role);
			}
		
			if (userTypeRadioChange.equals("DtoV")) {
				userTypes.add(VzdnUserTypes.Verizon.getValue());
				availableRoles = this.loadAvailableRoles(userTypes);
				availableRoles = this.separateAvailableRoles(availableRoles, user.getRoles());				
				user.setRoles(reFetchedUser.getRoles());				
			
			} else if (userTypeRadioChange.equals("VtoD")) {
				System.out.println("--------------------------------VtoD");
				userTypes.add(VzdnUserTypes.Developer.getValue());
				availableRoles = this.loadAvailableRoles(userTypes);
				availableRoles = this.separateAvailableRoles(availableRoles, user.getRoles());
				
				editableRoles = takeBackVerizonRoles();
			}
			
			Collections.sort(availableRoles);
			Collections.sort(editableRoles);
			
		} 
		
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			throw se;
		}
		
		catch (Exception e) {
			log.error("Problem in SerMgmtAction.toggleUserType:\n" + e);
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}
	
	public List<VzdnSysRoles> takeBackVerizonRoles(){
		List<VzdnSysRoles> tempEditableRoles = new ArrayList<VzdnSysRoles>();
		for(VzdnSysRoles role : editableRoles){
			
			if(role.getVzdnTypes().getTypeId() == VzdnConstants.ROLE_DEVELOPER || 
			   role.getVzdnTypes().getTypeId() == VzdnConstants.ROLE_BOTH || 
			   role.getVzdnTypes().getTypeId() == VzdnConstants.HIDDEN_ROLE )
			{				
				
				tempEditableRoles.add(role);
			}
		}
		return tempEditableRoles;
	}

	public String editSave() throws VzdnSecurityException, NotLoggedInException{
		
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		try
		{
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_USERS);
		
			VzdnUsers newuser = userMgmtService.getUserById(this.user.getUserId());
			 
			Integer userTypeId = user.getUserType().getTypeId();			 
			VzdnTypes userType = typesService.getTypeById(userTypeId);
			newuser.setUserType(userType);
			
			Integer userStatusId = user.getStatusType().getTypeId();
			VzdnTypes userStatusType = typesService.getTypeById(userStatusId);
			newuser.setStatusType(userStatusType);
			
			newuser.setManagerOrganization(user.getManagerOrganization());
		
			Set<VzdnSysRoles> vzdnSysRoles = new HashSet<VzdnSysRoles>();
			Set<VzdnSysRoles> vzdnSysRolesForOpenSSO = new HashSet<VzdnSysRoles>();

			
			if(newuser.getUserType().getTypeId().intValue() == VzdnConstants.DeveloperUser){
				VzdnSysRoles defaultDeveloperRole = roleService.getRolesOnId(VzdnConstants.DEFAULT_VZDN_CORE_ROLE);
				vzdnSysRoles.add(defaultDeveloperRole);
			}
			
			
			List<Integer> userTypes = new ArrayList<Integer>();
			List<VzdnSysRoles> allAvailableRoles = new ArrayList<VzdnSysRoles>();
			userTypes.add(VzdnUserTypes.Both.getValue());
			userTypes.add(user.getUserType().getTypeId());
			allAvailableRoles = this.loadAvailableRoles(userTypes);
			availableRoles = this.separateAvailableRoles(allAvailableRoles, user.getRoles());

			
			 String[] hRoles = StringFuncs.tokenize(this.hiddenRolesString, ",");
			 
			
			for(int i=0; i<hRoles.length; i++){
				Integer nextValue = new Integer(hRoles[i]);
				assignedRoles.add(nextValue);
			}
			
			
			for (Integer in : assignedRoles) {
				
					VzdnSysRoles role = roleService.getRolesOnId(in);
					vzdnSysRoles.add(role);
					if(role.getRoleId().intValue() != VzdnConstants.DEFAULT_VZDN_CORE_ROLE)
						vzdnSysRolesForOpenSSO.add(role);
					System.out.println("role selected "+ role.getRoleName());
			}
			
			
			if(newuser.getUserType().getTypeId().intValue() == VzdnConstants.VerizonUser && assignedRoles.size() < 1){
				addFieldError("assignedRoles","A verizon user must have at least on role!");
				return INPUT;
			}
			
			newuser.setRoles(vzdnSysRoles);

			userMgmtService.updateUser(newuser);
			
			newuser.setRoles(vzdnSysRolesForOpenSSO);

			OpenssoRestService.updateUserRoles(newuser);
			getSession().setAttribute("successMessage", "User Updated Successfully!");
		} 
		
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			throw se;
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public static String request(URL url) throws Exception {
		System.out.println(url.toString());
		URLConnection conn = url.openConnection();
		BufferedReader dis = new BufferedReader(new InputStreamReader(conn
				.getInputStream()));
		StringBuffer buff = new StringBuffer();
		String inputLine;

		while ((inputLine = dis.readLine()) != null) {
			buff.append(inputLine).append("\n");
		}
		dis.close();
		return buff.toString();
	}

	public List<VzdnSysRoles> separateAvailableRoles(List<VzdnSysRoles> allAvailableRoles, Set<VzdnSysRoles> alreadyAssignedRoles) {
		//List<VzdnSysRoles> allRoles = roleService.findAllSysRoles();
		List<VzdnSysRoles> displayableRoles = new ArrayList<VzdnSysRoles>();

		for (VzdnSysRoles available : allAvailableRoles) {
			boolean roleAssigned = false;
			for (VzdnSysRoles assignedRole : alreadyAssignedRoles) {
				if (available.getRoleId().equals(assignedRole.getRoleId())) {
					roleAssigned = true;
					break;
				}				
			}
			if (roleAssigned == false) {
				displayableRoles.add(available);
			}
		}
		return displayableRoles;
	}

	private List<VzdnSysRoles> loadAvailableRoles(List<Integer> typeIds) {
		System.out.println("-----typeids size is " + typeIds.size());
		return roleService.getRolesOnType(typeIds);
	}

	public UsersService getUserService() {
		return userService;
	}

	public void setUserService(UsersService userService) {
		this.userService = userService;
	}

	public List<VzdnTypes> getUserTypesList() {
		return userTypesList;
	}

	public void setUserTypesList(List<VzdnTypes> userTypesList) {
		this.userTypesList = userTypesList;
	}

	
	public List<VzdnSysRoles> getEditableRoles() {
		return editableRoles;
	}

	public void setEditableRoles(List<VzdnSysRoles> editableRoles) {
		this.editableRoles = editableRoles;
	}

	public UserMgmtService getUserMgmtService() {
		return userMgmtService;
	}

	public TypeService getTypesService() {
		return typesService;
	}

	public void setTypesService(TypeService typesService) {
		this.typesService = typesService;
	}

	

	public String getHiddenRolesString() {
		return hiddenRolesString;
	}

	public void setHiddenRolesString(String hiddenRolesString) {
		this.hiddenRolesString = hiddenRolesString;
	}

	public Vector<String> getHiddenRoles() {
		return hiddenRoles;
	}

	public void setHiddenRoles(Vector<String> hiddenRoles) {
		this.hiddenRoles = hiddenRoles;
	}

	public List<Integer> getEditableRoleIds() {
		return editableRoleIds;
	}

	public void setEditableRoleIds(List<Integer> editableRoleIds) {
		this.editableRoleIds = editableRoleIds;
	}
}
