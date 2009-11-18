package com.netpace.aims.bo.security;

import java.util.*;
import org.apache.log4j.Logger;

import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.security.*;
import com.netpace.aims.model.*;
import java.util.Iterator;
import java.util.*;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;


/**
 * Title: Privilege Manager
 * Description: This class caches the privileges for a user
 * Copyright: Copyright (c) 2002 Netpace Inc.
 * Company: Netpace Inc.
 * @author Shahnawaz Bagdadi
 * @version 1.0
 * @date - Wed 11 Feb, 2004
 */

public class PrivilegeManager
{

    private static PrivilegeManager instance = null;
    private Hashtable menus = null;
    private Hashtable submenus = null;
    private Hashtable privileges = null;

    private static Logger log = Logger.getLogger(PrivilegeManager.class.getName());

    /**
     *  Private constructor for the PrivilegeManager
     */
    private PrivilegeManager()
    {
      loadMenus();
      //loadSubMenus();
    }


    /**
     *  This method loads the menus for all the priveleges.
     */
    public void loadMenus()
    {
      Session session = null;
      menus = new Hashtable();
      submenus = new Hashtable();
      privileges = new Hashtable();
      try
      {
        session = DBHelper.getInstance().getSession();
        Query query = session.createQuery("select from com.netpace.aims.model.security.AimsSystemPrivilege as sysprivilege where sysprivilege.menuId is not null");
        AimsSystemPrivilege syspriv = null;
        for (Iterator it = query.iterate(); it.hasNext();)
        {
          syspriv = (AimsSystemPrivilege) it.next();
          privileges.put(syspriv.getPrivilegeId(),syspriv);
          //log.debug("syspriv ID : " + syspriv.getPrivilegeId() );
          menus.put(syspriv.getPrivilegeId(),syspriv.getMenu());
        }
        
        // get sub menus
        Query squery = session.createQuery("select from com.netpace.aims.model.security.AimsSystemPrivilege as sysprivilege where sysprivilege.subMenuId is not null");
        for (Iterator its = squery.iterate(); its.hasNext();)
        {
          syspriv = (AimsSystemPrivilege) its.next();
          privileges.put(syspriv.getPrivilegeId(),syspriv);
          //log.debug("Sub Menu syspriv ID : " + syspriv.getPrivilegeId() );
          submenus.put(syspriv.getPrivilegeId(),syspriv.getSubMenu());
        }

        // get privileges not assigned to menu or sub menu
        Query nquery = session.createQuery("select from com.netpace.aims.model.security.AimsSystemPrivilege as sysprivilege where sysprivilege.subMenuId is null and sysprivilege.menuId is null");
        for (Iterator itn = nquery.iterate(); itn.hasNext();)
        {
          syspriv = (AimsSystemPrivilege) itn.next();
          privileges.put(syspriv.getPrivilegeId(),syspriv);
          //log.debug("Sub Menu syspriv ID : " + syspriv.getPrivilegeId() );
        }
        
       }
       catch(HibernateException e)
       {
        e.printStackTrace();
        //throw e;
       }
       finally
       {
        try
        {
         session.close();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
       }
     }
      


   /**
    * This method returns the cached Menu object
    */
   public AimsMenu getMenu(Long privilegeId)
   {
      return (AimsMenu) menus.get(privilegeId);
   }
  

   /**
    * This method returns the cached Menu object
    */
   public AimsSubMenu getSubMenu(Long privilegeId)
   {
      return (AimsSubMenu) submenus.get(privilegeId);
   }


   /**
    * This method returns the cached Menu object
    */
   public AimsSystemPrivilege getPrivilege(Long privilegeId)
   {
      return (AimsSystemPrivilege) privileges.get(privilegeId);
   }


    /**
     *  This method returns the singleton instance of the PrivilegeManager.
     *  Care has been taken to make this method thread safe.
     */
    public static PrivilegeManager getInstance()
    {
        if (instance == null)
        {
            synchronized(PrivilegeManager.class)
            {
                if (instance == null)
                {
                    instance = new PrivilegeManager();
                }
            }
        }
        return instance;
    }

}
