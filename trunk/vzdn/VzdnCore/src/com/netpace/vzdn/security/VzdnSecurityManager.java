package com.netpace.vzdn.security;


import java.util.Hashtable;

import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnSysPrivileges;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is responsible for all security related stuff.
 * It has static methods for validating a user and accessing its rights.
 * @author Shahnawaz Bagdadi
 */
public class VzdnSecurityManager
{

    public static int SELECT = 1;
    public static int UPDATE = 2;
    public static int INSERT = 3;
    public static int DELETE = 4;

    static Logger log = Logger.getLogger(VzdnSecurityManager.class.getName());
    
 
    public static VzdnSysPrivileges getPrivilege(Hashtable<String,VzdnSysPrivileges> priveleges, String privilegeId) throws VzdnSecurityException
    {
        Object p_obj = priveleges.get(privilegeId);
        if (p_obj == null)
        {
            throw new VzdnSecurityException();
        }
        else
            return (VzdnSysPrivileges) p_obj;
    }


    /**
     *  This method checks if the user has the specified rights to a particular privilige.
     *  If the user has no rights it returns false, but if the user has some rights
     *  but not the specified right then it returns false, else it returns true.
     *  @param request - the Httprequest object
     *  @param PRIVILEGE - the privilege key to be checked
     *  @param ACCESS_LEVEL - the access level to be checked. One of SELECT,UPDATE,INSERT or DELETE
     */
    public static boolean checkAccess(HttpServletRequest request, String PRIVILEGE, int ACCESS_LEVEL)
    {
    	VzdnSysPrivileges role_privilege = null;

        try
        {
            role_privilege = VzdnSecurityManager.getPrivilege((Hashtable<String,VzdnSysPrivileges>) request.getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES), PRIVILEGE);
            return true;
        }
        catch (VzdnSecurityException ex)
        {
            return false;
        }

     
    }

   

}