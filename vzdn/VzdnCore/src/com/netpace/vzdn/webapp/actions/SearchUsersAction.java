package com.netpace.vzdn.webapp.actions;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;


import org.apache.log4j.Logger;

import com.netpace.vzdn.model.Searchable;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.security.VzdnSecurityException;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.UsersService;
import com.netpace.vzdn.service.TypeService;

import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;

import com.opensymphony.xwork2.Preparable;

/**
 * @author Ikramullah Khan 
 * 
 */
public class SearchUsersAction extends BaseAction implements Preparable {

	private static Logger log = Logger.getLogger(SearchUsersAction.class);

	private static final long serialVersionUID = 1;
	private UsersService userService;
	private TypeService typesService;
	private List<VzdnUsers> userlist;
	
	private VzdnUsers userToSearch = new VzdnUsers();
	private Searchable searchOn = new Searchable();
	
	private List<VzdnTypes> userTypesList;
	private List<Searchable> searchers;
	

	public void prepare(){
		searchers = new ArrayList<Searchable>();
		//searchers.add(new Searchable(1, "User Type"));
		searchers.add(new Searchable(2, "User Name"));
		searchers.add(new Searchable(3, "First Name"));
		searchers.add(new Searchable(4, "Last Name"));
	}
	
	

	public void setUserTypesList(List<VzdnTypes> userTypesList) {
		this.userTypesList = userTypesList;
	}

	public SearchUsersAction() {
	}

	public UsersService getUserService() {
		return userService;
	}

	public void setUserService(UsersService userService) {
		this.userService = userService;
	}

	public String search() throws VzdnSecurityException, NotLoggedInException{
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		try
		{
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			
			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_USERS);
			userlist = userService.search(searchOn);
			Collections.sort(userlist);
			return SUCCESS;
		}
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)		
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			
			se.printStackTrace();
			throw se;
		}		
	}

	public VzdnUsers getUserToSearch() {
		return userToSearch;
	}

	public void setUserToSearch(VzdnUsers userToSearch) {
		this.userToSearch = userToSearch;
	}

	public List<VzdnUsers> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<VzdnUsers> userlist) {
		this.userlist = userlist;
	}


	public List<VzdnTypes> getUserTypesList() {
		return userTypesList;
	}

	public TypeService getTypesService() {
		return typesService;
	}



	public void setTypesService(TypeService typesService) {
		this.typesService = typesService;
	}

	public Searchable getSearchOn() {
		return searchOn;
	}


	public void setSearchOn(Searchable searchOn) {
		this.searchOn = searchOn;
	}


	public List<Searchable> getSearchers() {
		return searchers;
	}

	public void setSearchers(List<Searchable> searchers) {
		this.searchers = searchers;
	}

}