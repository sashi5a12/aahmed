package com.netpace.aims.controller;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.Action;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.model.security.AimsRolePrivilege;
import com.netpace.aims.model.security.AimsSection;
import com.netpace.aims.util.AimsConstants;
/**
 * This is the base class from which all Action will be derived.
 * This layer of abstraction is done to enable common tasks across all actions.
 * 
 * Add XDoclet tags for Action classes in your subclass , these tags will be read 
 * by XDoclet task during build time and added to the struts config appropriately.
 * @author Shahnawaz Bagdadi
 * @version 1.0
 * @see com.netpace.aims.controller.login.LoginAction
 */
public abstract class BaseAction extends Action
{

    public static int SELECT = 1;
    public static int UPDATE = 2;
    public static int INSERT = 3;
    public static int DELETE = 4;

    /**
     *  This method checks if the user has any rights to access this privilege.
     *  It throws a SecurityException if the user does not have the necessary rights.
     *  This method will be normally used to check the access to a particular action.
     *  @param request - the Httprequest object
     *  @param PRIVILEGE - the privilege key to be checked
     */
    public void checkAccess(HttpServletRequest request, String PRIVILEGE) throws AimsSecurityException
    {
        AimsSecurityManager.getPrivilege((Hashtable) request.getSession().getAttribute(AimsConstants.AIMS_PRIVILEGES), PRIVILEGE);
    }

    /**
     *  This method checks if the user has the specified rights to a particular privilige.
     *  If the user has no rights it throws a security exception, but if the user has some rights 
     *  but not the specified right then it returns false, else it returns true.
     *  @param request - the Httprequest object
     *  @param PRIVILEGE - the privilege key to be checked
     *  @param ACCESS_LEVEL - the access level to be checked. One of SELECT,UPDATE,INSERT or DELETE
     */
    public boolean checkAccess(HttpServletRequest request, String PRIVILEGE, int ACCESS_LEVEL) throws AimsSecurityException
    {
        AimsRolePrivilege role_privilege =
            AimsSecurityManager.getPrivilege((Hashtable) request.getSession().getAttribute(AimsConstants.AIMS_PRIVILEGES), PRIVILEGE);

        if ((ACCESS_LEVEL == SELECT) && (role_privilege.getIfSelectAllowed() != null) && (role_privilege.getIfSelectAllowed().trim().equals("Y")))
            return true;
        else if ((ACCESS_LEVEL == UPDATE) && (role_privilege.getIfUpdateAllowed() != null) && (role_privilege.getIfUpdateAllowed().trim().equals("Y")))
            return true;
        else if ((ACCESS_LEVEL == INSERT) && (role_privilege.getIfCreateAllowed() != null) && (role_privilege.getIfCreateAllowed().trim().equals("Y")))
            return true;
        else if ((ACCESS_LEVEL == DELETE) && (role_privilege.getIfDeleteAllowed() != null) && (role_privilege.getIfDeleteAllowed().trim().equals("Y")))
            return true;
        else
            return false;
    }

    /**
     *  This method checks if the user has the specified rights to a particular privilige.
     *  If the user has no rights it throws a security exception, but if the user has some rights 
     *  but not the specified right then it returns false, else it returns true.
     *  @param request - the Httprequest object
     *  @param PRIVILEGE - the privilege key to be checked
     *  @param SECTION - the section to be checked.
     *  @param ACCESS_LEVEL - the access level to be checked. One of SELECT,UPDATE,INSERT or DELETE
     */
    public boolean checkSectionAccess(HttpServletRequest request, String PRIVILEGE, String SECTION, int ACCESS_LEVEL) throws AimsSecurityException
    {
        AimsRolePrivilege role_privilege =
            AimsSecurityManager.getPrivilege((Hashtable) request.getSession().getAttribute(AimsConstants.AIMS_PRIVILEGES), PRIVILEGE);

        AimsSection section = AimsSecurityManager.getSection(role_privilege, SECTION);

        if (section == null)
            return false;

        if ((ACCESS_LEVEL == SELECT) && (role_privilege.getIfSelectAllowed().trim().equals("Y")))
            return true;
        else if ((ACCESS_LEVEL == UPDATE) && (role_privilege.getIfUpdateAllowed().trim().equals("Y")))
            return true;
        else if ((ACCESS_LEVEL == INSERT) && (role_privilege.getIfCreateAllowed().trim().equals("Y")))
            return true;
        else if ((ACCESS_LEVEL == DELETE) && (role_privilege.getIfDeleteAllowed().trim().equals("Y")))
            return true;
        else
            return false;
    }

}
