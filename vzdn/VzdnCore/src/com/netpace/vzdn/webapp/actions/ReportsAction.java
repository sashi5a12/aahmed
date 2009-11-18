package com.netpace.vzdn.webapp.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.components.ActionError;

import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.Report;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.security.VzdnSecurityException;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.IReportsService;
import com.netpace.vzdn.service.ISysRoleService;

public class ReportsAction extends BaseAction{

	private static Logger log = Logger.getLogger(ReportsAction.class);
	private IReportsService reportsService;
	private ISysRoleService roleService;
	private List<Report> reportList;
	private List<VzdnSysRoles> systemRoles;
	private List<String> assignedRoles;
	
	
	

	public List<String> getAssignedRoles() {
		return assignedRoles;
	}



	public void setAssignedRoles(List<String> assignedRoles) {
		this.assignedRoles = assignedRoles;
	}



	public List<Report> getReportList() {
		return reportList;
	}

	
	
	public void setRoleService(ISysRoleService roleService) {
		this.roleService = roleService;
	}



	public List<VzdnSysRoles> getSystemRoles() {
		return systemRoles;
	}



	public ISysRoleService getRoleService() {
		return roleService;
	}



	public void setReportsService(IReportsService reportsService) {
		this.reportsService = reportsService;
	}



	public IReportsService getReportsService() {
		return reportsService;
	}
	
	public String showReports(){
		VzdnUsers  loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		try {
			VzdnUsers currentUser = (VzdnUsers)getServletRequest().getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Set<VzdnSysRoles> roles =  currentUser.getRoles();
			List<Integer> privilegeIds = new ArrayList<Integer>();
			for(VzdnSysRoles role : roles){
				for(VzdnSysPrivileges  privlege: role.getPrivileges()){
					privilegeIds.add(privlege.getPrivilegeId());
				}
			}
			reportList = reportsService.getUserReports(privilegeIds);
			if(reportList==null || reportList.size()==0){
				log.debug("-----probelm here ..no sufficent roles ");
				this.addFieldError("", getText("reports.access.unavailable"));
				return ERROR;
			}
			
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.REPORT_VIEWER);

			
			
			
			return SUCCESS;
		} catch (Exception e) {
			log.error("Error in action:",e);
			e.printStackTrace();
			if(null != loggedInUser && null != pvlg)		
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			this.addFieldError("", getText("reports.access.unavailable"));
			return ERROR; 
			
		}
	}
	
	public String showRoleReport(){
		VzdnUsers  loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		VzdnSysPrivileges pvlg2 = null;
		
		try{
			List<Integer> typeIds = new ArrayList<Integer>();
			typeIds.add(VzdnConstants.VerizonUser);
			typeIds.add(VzdnConstants.DeveloperUser);
			typeIds.add(VzdnConstants.ROLE_BOTH);
			
			systemRoles = roleService.getRolesOnType(typeIds);
		
			Collections.sort(systemRoles);
		
		loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
		Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
		
		if (null == privileges)
			throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
		
		pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.ROLE_REPORT);
		pvlg2 = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.REPORT_VIEWER);

		return SUCCESS;
	}catch(Exception se){
		if(null != loggedInUser && (null != pvlg && pvlg2!=null))		
			log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
		
		//this.addActionError("report.access.unavailable");
		this.addFieldError("", getText("report.access.unavailable"));
		return ERROR; 
		
	}	
		
		
	}
	
	
	
	public String loadRoleReport(){
		
		VzdnUsers  loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		VzdnSysPrivileges pvlg2 = null;
		try{
			if(this.assignedRoles!=null && this.assignedRoles.size()>0){
				log.debug("User has selected one or more roles to display");
				for(String role : assignedRoles){
					log.debug("---role : "+role);
				}
			}else{
				log.debug("user has not selected roles");
				List<Integer> typeIds = new ArrayList<Integer>();
				typeIds.add(VzdnConstants.VerizonUser);
				typeIds.add(VzdnConstants.DeveloperUser);
				typeIds.add(VzdnConstants.ROLE_BOTH);
				assignedRoles=getAllRoleString(roleService.getRolesOnType(typeIds));
				
			}

			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.ROLE_REPORT);
			pvlg2 = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.REPORT_VIEWER);
			return SUCCESS;
	}catch(Exception se){
		if(null != loggedInUser && (null != pvlg && pvlg2!=null))		
			log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
		this.addFieldError("", getText("report.access.unavailable"));
		return ERROR; 
	
}	
	

	}
	
private List<String> getAllRoleString(List<VzdnSysRoles> sysroles){
		
		List<String > roles = new ArrayList<String>();
		for(VzdnSysRoles rol : sysroles){
			roles.add(rol.getRoleName());
		}
		return roles;
		
	}
	
}
