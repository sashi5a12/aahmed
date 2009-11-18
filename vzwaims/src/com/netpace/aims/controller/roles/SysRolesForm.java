package com.netpace.aims.controller.roles;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.*;

import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.security.*;

import com.netpace.aims.bo.accounts.*;
import com.netpace.aims.bo.roles.*;

import java.util.*;

/**
 * @struts.form name="SysRolesForm"
 */
public class SysRolesForm extends BaseValidatorForm {

    /** identifier field */
    private java.lang.String roleId;

    /** persistent field */
    private java.lang.String roleName;

    /** nullable persistent field */
    private java.lang.String roleDescription;

    /** persistent field */
    private java.lang.String roleType;

    /** nullable persistent field */
    private java.lang.String createdBy;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** nullable persistent field */
    private java.lang.String lastUpdatedBy;

    /** nullable persistent field */
    private java.util.Date lastUpdatedDate;

    /** persistent field */
    private Set rolePrivileges;

    private String [] selects = {}; 

    private String [] creates = {}; 

    private String [] updates = {}; 

    private String [] deletes = {}; 


    public java.lang.String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(java.lang.String roleId) {
        this.roleId = roleId;
    }

    public java.lang.String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(java.lang.String roleName) {
        this.roleName = roleName;
    }

    public java.lang.String getRoleDescription() {
        return this.roleDescription;
    }

    public void setRoleDescription(java.lang.String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public java.lang.String getRoleType() {
        return this.roleType;
    }

    public void setRoleType(java.lang.String roleType) {
        this.roleType = roleType;
    }

    public java.lang.String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    public java.util.Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public java.lang.String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(java.lang.String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public java.util.Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(java.util.Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public java.util.Set getRolePrivileges() {
        return this.rolePrivileges;
    }

    public void setRolePrivileges(java.util.Set rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }

    public String [] getSelects() { 
      return this.selects; 
    } 

    public void setSelects(String [] selects) { 
      this.selects = selects; 
    }

    public String [] getCreates() { 
      return this.creates; 
    } 

    public void setCreates(String [] creates) { 
      this.creates = creates; 
    }

    public String [] getUpdates() { 
      return this.updates; 
    } 

    public void setUpdates(String [] updates) { 
      this.updates = updates; 
    }


    public String [] getDeletes() { 
      return this.deletes; 
    } 

    public void setDeletes(String [] deletes) { 
      this.deletes = deletes; 
    }


	public void reset (ActionMapping mapping, HttpServletRequest request)
	{
        System.out.println("The RESET is called in AccountForm!");
	
        Collection AimsSysPrivileges = null;
		Collection AimsRolePrivileges = null;
        HttpSession session = request.getSession(); 
        Session hibernateSession = null;

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();

		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        AimsSysRole AimsEditRole = null;

        AimsRolePrivilege currPrivelege = null;
        Long currPrivelegeId = null;
        
        if (task_name.equalsIgnoreCase("create") )
        {			

            try
            {
                 AimsSysPrivileges = AimsAccountsManager.getUserTypeSysPrivileges(user_id, user_type);
            }
            catch (HibernateException he)
            {
                System.out.println("A hibernate exception occured!");
            }
			
			request.setAttribute("AimsSysPrivileges", AimsSysPrivileges);


        }

        if (task_name.equalsIgnoreCase("edit") )
        {			

            try
            {
                 AimsSysPrivileges = AimsAccountsManager.getUserTypeSysPrivileges(user_id, user_type);
            }
            catch (HibernateException he)
            {
                System.out.println("A hibernate exception occured!");
            }
			
			request.setAttribute("AimsSysPrivileges", AimsSysPrivileges);


        }

        if (task_name.equalsIgnoreCase("createForm") )
        {			

            try
            {
                 AimsSysPrivileges = AimsAccountsManager.getUserTypeSysPrivileges(user_id, user_type);
            }
            catch (HibernateException he)
            {
                System.out.println("A hibernate exception occured!");
            }
			
			request.setAttribute("AimsSysPrivileges", AimsSysPrivileges);


        }

        if (task_name.equalsIgnoreCase("editForm") || task_name.equalsIgnoreCase("editFormView"))
        {			

            try
            {
                AimsEditRole = AimsSysRolesManager.getSysRole(new Long(request.getParameter("roleId")));
                AimsSysPrivileges = AimsSysRolesManager.getRoleAvailableSysPrivileges(AimsEditRole.getRoleType());
				AimsRolePrivileges = AimsSysRolesManager.getRoleAssignedPrivileges(new Long(request.getParameter("roleId")));
				System.out.println("AimsRolePrivileges.size() " + AimsRolePrivileges.size());
               
            }
            catch (HibernateException he)
            {
                System.out.println("A hibernate exception occured!");
            }
			
            this.setRoleId(AimsEditRole.getRoleId().toString());
			this.setRoleName(AimsEditRole.getRoleName());
            this.setRoleDescription(AimsEditRole.getRoleDescription());
            this.setRoleType(AimsEditRole.getRoleType());
			
			

            if (AimsRolePrivileges != null)
            {
                ArrayList selectArrayList = new ArrayList();
                ArrayList createArrayList = new ArrayList();
                ArrayList updateArrayList = new ArrayList();
                ArrayList deleteArrayList = new ArrayList();

                for (Iterator iter = AimsRolePrivileges.iterator(); iter.hasNext();) 
                {
                    currPrivelege = (AimsRolePrivilege)iter.next();
                    currPrivelegeId = currPrivelege.getPrivilege().getPrivilegeId();

                    if (currPrivelege.getIfSelectAllowed() != null)
                    {
                        selectArrayList.add(currPrivelegeId.toString());
                    }

                    if (currPrivelege.getIfCreateAllowed() != null)
                    {
                        createArrayList.add(currPrivelegeId.toString());
                    }

                    if (currPrivelege.getIfUpdateAllowed() != null)
                    {
                        updateArrayList.add(currPrivelegeId.toString());
                    }

                    if (currPrivelege.getIfDeleteAllowed() != null)
                    {
                        deleteArrayList.add(currPrivelegeId.toString());
                    }
                }

                String [] selectArr = StringFuncs.ConvertListToStringArray(selectArrayList);
                String [] createArr = StringFuncs.ConvertListToStringArray(createArrayList);
                String [] updateArr = StringFuncs.ConvertListToStringArray(updateArrayList);
                String [] deleteArr = StringFuncs.ConvertListToStringArray(deleteArrayList);


                this.setSelects(selectArr);
                this.setCreates(createArr);
                this.setUpdates(updateArr);
                this.setDeletes(deleteArr);
            }

			request.setAttribute("AimsSysPrivileges", AimsSysPrivileges);
      

        }
 
	}

}
