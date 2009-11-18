package com.netpace.vzdn.webapp.actions;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.netpace.ldap.SearchUsers;
import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.Report;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.IReportsService;
import com.netpace.vzdn.service.ISysRoleService;

public class LoadLdapUsersReportAction extends BaseAction{

	private static Logger log = Logger.getLogger(LoadLdapUsersReportAction.class);
	private String fromDate;
	private String toDate;
	private String sorting;
	
	
	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}


	public String execute(){
		VzdnUsers  loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		VzdnSysPrivileges pvlg2 = null;
		try{
			SearchUsers search = new SearchUsers();
			 
			 List devList = search.searchUsers( fromDate, toDate  , getSortOption(sorting));
			 
			 log.debug( "Developers searched:" + devList.size() );
			 
			 getServletRequest().setAttribute( "devoplerList" , devList );
			 
			 log.debug( "Set developers list in request." );
			 
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.DEV_REPORT);
			pvlg2 = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.REPORT_VIEWER);
			return SUCCESS;
	}catch(Exception se){
		if(null != loggedInUser && (null != pvlg && pvlg2!=null))			
			log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
		this.addFieldError("", getText("report.access.unavailable"));
		return ERROR; 
	
	}	
	}

	
	
	private String getSortOption(String value){
		
		if(value.equals("Created On"))
				return "createTimestamp";
		if(value.equals("Country"))
			return "vzdn-country";
		if(value.equals("Date of Birth"))
			return "vzdn-dob";
		if(value.equals("Email"))
			return "mail";
		if(value.equals("First Name"))
			return "givenName";
		
			return "sn";
	}
	

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}


	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}


	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}


	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
}
