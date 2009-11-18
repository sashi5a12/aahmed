package com.netpace.vzdn.webapp.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.Report;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.IReportsService;
import com.netpace.vzdn.service.ISysRoleService;

public class LdapUsersReportAction extends BaseAction{

	private static Logger log = Logger.getLogger(LdapUsersReportAction.class);
	private String fromDate;
	private String toDate;
	private String sorting;
	private SortedMap<String,String> sortList = new TreeMap<String,String>();
	
	public String execute(){
		VzdnUsers  loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		VzdnSysPrivileges pvlg2 = null;
		try{//createTimestamp,mail,vzdn-title,givenName,sn,vzdn-dob,vzdn-country,vzdn-terms-conditions
			sortList.put("Created On", "Created On");
			sortList.put("Country", "Country");
			sortList.put("Date of Birth", "Date of Birth");
			sortList.put("Email", "Email");
			sortList.put("First Name", "First Name");
			sortList.put("Last Name", "Last Name");
			
			
			
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


	public String getSorting() {
		return sorting;
	}


	public void setSorting(String sorting) {
		this.sorting = sorting;
	}


	public SortedMap<String, String> getSortList() {
		return sortList;
	}


	public void setSortList(SortedMap<String, String> sortList) {
		this.sortList = sortList;
	}


	
	
	
	
}
