package com.netpace.aims.bo.security;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.controller.login.LoginInfo;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.security.*;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.TreeSet;
import java.util.Collection;
import java.util.AbstractCollection;

/**
 * This class is responsible for all security related stuff.
 * It has static methods for validating a user and accessing its rights.
 * @author Shahnawaz Bagdadi
 */
public class AimsSecurityManager
{

    public static int SELECT = 1;
    public static int UPDATE = 2;
    public static int INSERT = 3;
    public static int DELETE = 4;

    static Logger log = Logger.getLogger(AimsSecurityManager.class.getName());

    /**
     *  This static method validates the user and returns a valid user object if the user is valid.
     *  If the username and password is invalid it throws a InvalidLoginException
     *  History:
     *  12/29/2003          Adnan Makda                 Added checkSectionAccess() method
     */

    public static AimsUser validateUser(String username, String password) throws InvalidLoginException, HibernateException
    {

        AimsUser user = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query =
                session.createQuery(
                    "select from com.netpace.aims.model.core.AimsUser as user where lower(user.username) = :username and user.password = :password and user.userAccountStatus = :useraccountstatus");
            query.setString("username", username.toLowerCase().trim());
            query.setString("password", password);
            query.setString("useraccountstatus", AimsConstants.USER_STATUS_ACTIVE);

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                user = (AimsUser) it.next();
                log.debug("User: " + user.toString());
            }

            //TODO Remove this hack once the User->UserRoles->Roles->RolePribvileges brings in the desired data.
            //Getting the roles to set them back in correctly.
            if (user != null)
            {
                Set roles = user.getRoles();
                AimsSysRole tempRole = null;
                AimsSysRole role = null;
                HashSet newSet = new HashSet();
                Iterator rolesIter = roles.iterator();
                while (rolesIter.hasNext())
                {
                    role = (AimsSysRole) rolesIter.next();
                    try
                    {
                        tempRole = (AimsSysRole) DBHelper.getInstance().load(com.netpace.aims.model.security.AimsSysRole.class, role.getRoleId().toString());
                    }
                    catch (Exception ex)
                    {}
                    newSet.add(tempRole);
                }
                //Setting the correct set of Roles back in
                user.setRoles(newSet);
            }
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
        if (user == null)
            throw new InvalidLoginException();
        return user;
    }

    public static AimsUser getPassword(String username, String motherMaidenName) throws InvalidUserInfoException, HibernateException
    {

        AimsUser user = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query =
                session.createQuery(
                    "select from com.netpace.aims.model.core.AimsUser as user where lower(user.username) = :username and user.motherMaidenName = :motherMaidenName");
            query.setString("username", username.toLowerCase().trim());
            query.setString("motherMaidenName", motherMaidenName);

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                user = (AimsUser) it.next();
                log.debug("User: " + user.toString());
            }
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        if (user == null)
            throw new InvalidUserInfoException();
        return user;

    }

    /**
     *  This static method populates the sorted menu in the session.
     *  It uses all the info fromt the user object created in the session.
     */
    public static void createMenu(HttpSession session)
    {

        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        AimsSysRole role = null;
        Hashtable priveleges = new Hashtable();
        Set sections = null;
        Set role_privileges = null;
        AimsSysPrivilege privilege = null;
        AimsRolePrivilege role_privilege = null;
        TreeSet menu_items = new TreeSet(new MenuComparator());
        TreeSet sub_menu_items = new TreeSet(new SubMenuComparator());

        //System.out.println("*********** now getting userr role ********************");
        Set roles = user.getRoles();
        Iterator rolesIter = roles.iterator();
        
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        AimsAllianc alliance=null;
        Long allianceId=null;
        
        if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
	        allianceId=((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
	    	try {
				alliance =(AimsAllianc)DBHelper.getInstance().load(AimsAllianc.class, allianceId.toString());
			} catch (HibernateException e) {
				
				e.printStackTrace();
			}
        }

        while (rolesIter.hasNext())
        {
            role = (AimsSysRole) rolesIter.next();
            System.out.println("Role : " + role);
            role_privileges = role.getRolePrivileges();
            System.out.println("Role Priveleges size : " + role_privileges.size());
            Iterator prevIter = role_privileges.iterator();
            while (prevIter.hasNext())
            {
                role_privilege = (AimsRolePrivilege) prevIter.next();
                //System.out.println("*********** now getting privileges from role ********************");
                privilege = role_privilege.getPrivilege();

                //First check to see if it already exists
                if (priveleges.get(privilege.getPrivilegeKey().toString()) != null)
                {
                    log.debug("The Privilege Exists.");
                    AimsRolePrivilege tempRolePrivilege = (AimsRolePrivilege) priveleges.get(privilege.getPrivilegeKey().toString());
                    if ((tempRolePrivilege.getIfSelectAllowed() != null) && (tempRolePrivilege.getIfSelectAllowed().equals("Y")))
                        role_privilege.setIfSelectAllowed("Y");
                    if ((tempRolePrivilege.getIfCreateAllowed() != null) && (tempRolePrivilege.getIfCreateAllowed().equals("Y")))
                        role_privilege.setIfCreateAllowed("Y");
                    if ((tempRolePrivilege.getIfUpdateAllowed() != null) && (tempRolePrivilege.getIfUpdateAllowed().equals("Y")))
                        role_privilege.setIfUpdateAllowed("Y");
                    if ((tempRolePrivilege.getIfDeleteAllowed() != null) && (tempRolePrivilege.getIfDeleteAllowed().equals("Y")))
                        role_privilege.setIfDeleteAllowed("Y");
                }

                priveleges.put(privilege.getPrivilegeKey().toString(), role_privilege);

                //AimsMenu menu = privilege.getMenu();
                // Get the menu from privilege cache instead
                AimsMenu menu = PrivilegeManager.getInstance().getMenu(privilege.getPrivilegeId());

                if (menu != null)
                {
                	if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
                	{
                		if(menu.getMenuName().equals("Manage Sales Lead"))
                		{
                			log.debug("Manage Sales Lead -------------------------------");
                			if(alliance!=null)
    			            if(!(StringFuncs.NullValueReplacement(alliance.getIsJMAAlliance()).equals("Y")))
    			            {   	  	
    			            	log.info("For non Jma alliance we can't dispaly main menu: "+menu.getMenuName());
    			                continue;
    			            }
                		}
                	}
                    //System.out.println("Menu : " + menu);
                    menu_items.add(menu);
                }

                //AimsSubMenu submenu = privilege.getSubMenu();
                // get the submenu from privilege cache instead.
                AimsSubMenu submenu = PrivilegeManager.getInstance().getSubMenu(privilege.getPrivilegeId());

                if (submenu != null)
                {
                	if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
                    {
	                	if(submenu.getSubMenuName().equals("JMA Info") || submenu.getSubMenuName().equals("Submit Sales Lead") || 
	                	 submenu.getSubMenuName().equals("Sales Lead Sent") || submenu.getSubMenuName().equals("Sales Lead Received"))
	                	{
		                
	                		log.debug("Sub Menu---------------------------------------------:"+ submenu.getSubMenuName());	
		                	if(alliance!=null)
			                if(!(StringFuncs.NullValueReplacement(alliance.getIsJMAAlliance()).equals("Y")))
			                {   	
			                	log.info("For non Jma alliance we can't dispaly sub menu :"+ submenu.getSubMenuName());
			                    continue;
			                  }
		                    	
		                }
                    }
                    //System.out.println("subMenu : " + submenu);
                    menu_items.add(submenu.getAimsMenu());

                    // get the menu from submenu via cache.
                    sub_menu_items.add(submenu);
                }
            }
        }
        //debugSet(menu_items);
        //debugSet(sub_menu_items);
        session.setAttribute(AimsConstants.AIMS_MENU, menu_items);
        session.setAttribute(AimsConstants.AIMS_SUB_MENU, sub_menu_items);
        session.setAttribute(AimsConstants.AIMS_PRIVILEGES, priveleges);

        //if ((priveleges.size() == 1) && (((AimsSysRole)roles.iterator().next()).getRoleName().equalsIgnoreCase(AimsConstants.AIMS_PROV_APPS_PRIVILEGE)))     
        if ((priveleges.size() == 1) && (priveleges.containsKey(AimsConstants.AIMS_PROV_APPS_PRIVILEGE)))
        {
            session.setAttribute(AimsConstants.AIMS_ONLY_PROV_APPS, "Y");
        }

        setWelcomePage(session);
    }

    public static void setWelcomePage(HttpSession session)
    {

        try
        {
            Object[] values = null;
            AimsSysRole role = null;
            LoginInfo loginInfo = new LoginInfo();
            AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);

            // Role based checking

            Set roles = user.getRoles();
            log.debug("Role Size : " + roles.size());
            Iterator rolesIter = roles.iterator();
            while (rolesIter.hasNext())
            {
                role = (AimsSysRole) rolesIter.next();
                log.debug("Role Type : " + role.getRoleType());
                if (AimsConstants.ALLIANCEADMIN_ROLETYPE.equalsIgnoreCase(role.getRoleType()))
                {
                    loginInfo.setAllianceAdmin(AimsConstants.ALLIANCEADMIN_ROLETYPE);
                    loginInfo.setAllianceUser(null);
                    break;
                }
                else if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(role.getRoleType()))
                    loginInfo.setAllianceUser(AimsConstants.ALLIANCE_USERTYPE);
                else if (
                    AimsConstants.VZWADMIN_ROLETYPE.equalsIgnoreCase(role.getRoleType()) || (AimsConstants.VZW_USERTYPE.equalsIgnoreCase(role.getRoleType())))
                {
                    loginInfo.setVzwAdmin(AimsConstants.VZWADMIN_ROLETYPE);
                    loginInfo.setVzwUser(null);
                    break;
                }

            }

            // Alliance Summary for Alliance User

            Collection collection = AllianceManager.getAllianceDetails(user.getAimsAllianc(), user.getUserType());
            for (Iterator it = collection.iterator(); it.hasNext();)
            {
                values = (Object[]) it.next();
                loginInfo.setAllianceStatus(MiscUtils.getAllianceStatus(Utility.getObjectString(values[3])));
                loginInfo.setVzwManager(Utility.getObjectString(values[4]) + " " + Utility.getObjectString(values[5]));
            }

            // Applications Summary for Alliance User

            Collection aimsAppsForAllUser = getAppsByAllianceId(user.getAimsAllianc());
            loginInfo.setNoOfSubmittedApps(getAppCountByPhaseAndPlatformId(aimsAppsForAllUser, AimsConstants.SUBMISSION_ID, null));
            loginInfo.setNoOfSavedApps(getAppCountByPhaseAndPlatformId(aimsAppsForAllUser, AimsConstants.SAVED_ID, null));
            loginInfo.setNoOfAcceptedApps(getAppCountByPhaseAndPlatformId(aimsAppsForAllUser, AimsConstants.ACCEPTANCE_ID, null));
            loginInfo.setNoOfRejectedApps(getAppCountByPhaseAndPlatformId(aimsAppsForAllUser, AimsConstants.REJECTED_ID, null));
            // Alliance Summary for VZW User

            Collection aimsAlliances = getAlliances();
            loginInfo.setNoOfAlliances(String.valueOf(aimsAlliances.size()));
            loginInfo.setNoOfNewAlliances(getAllianceCountByStatus(aimsAlliances, AimsConstants.ALLIANCE_STATUS_SUBMITTED));
            loginInfo.setNoOfRejectedAlliances(getAllianceCountByStatus(aimsAlliances, AimsConstants.ALLIANCE_STATUS_REJECTED));

            // Applications Summary for VZW User

            Collection aimsApps = getApps();

            //BREW
            loginInfo.setNoOfBrewApps(getAppCountByPhaseAndPlatformId(aimsApps, null, AimsConstants.BREW_PLATFORM_ID));
            loginInfo.setNoOfBrewSubApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.SUBMISSION_ID, AimsConstants.BREW_PLATFORM_ID));
            loginInfo.setNoOfBrewEvaluatedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.EVALUATED_ID, AimsConstants.BREW_PLATFORM_ID));
            loginInfo.setNoOfBrewTestingApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.TESTING_ID, AimsConstants.BREW_PLATFORM_ID));
            loginInfo.setNoOfBrewAcceptedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.ACCEPTANCE_ID, AimsConstants.BREW_PLATFORM_ID));
            loginInfo.setNoOfBrewRejectedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.REJECTED_ID, AimsConstants.BREW_PLATFORM_ID));

            //SMS
            loginInfo.setNoOfSMSApps(getAppCountByPhaseAndPlatformId(aimsApps, null, AimsConstants.SMS_PLATFORM_ID));
            loginInfo.setNoOfSMSSubApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.SUBMISSION_ID, AimsConstants.SMS_PLATFORM_ID));
            loginInfo.setNoOfSMSEvaluatedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.EVALUATED_ID, AimsConstants.SMS_PLATFORM_ID));
            loginInfo.setNoOfSMSTestingApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.TESTING_ID, AimsConstants.SMS_PLATFORM_ID));
            loginInfo.setNoOfSMSAcceptedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.ACCEPTANCE_ID, AimsConstants.SMS_PLATFORM_ID));
            loginInfo.setNoOfSMSRejectedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.REJECTED_ID, AimsConstants.SMS_PLATFORM_ID));

            //MMS
            loginInfo.setNoOfMMSApps(getAppCountByPhaseAndPlatformId(aimsApps, null, AimsConstants.MMS_PLATFORM_ID));
            loginInfo.setNoOfMMSSubApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.SUBMISSION_ID, AimsConstants.MMS_PLATFORM_ID));
            loginInfo.setNoOfMMSEvaluatedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.EVALUATED_ID, AimsConstants.MMS_PLATFORM_ID));
            loginInfo.setNoOfMMSTestingApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.TESTING_ID, AimsConstants.MMS_PLATFORM_ID));
            loginInfo.setNoOfMMSAcceptedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.ACCEPTANCE_ID, AimsConstants.MMS_PLATFORM_ID));
            loginInfo.setNoOfMMSRejectedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.REJECTED_ID, AimsConstants.MMS_PLATFORM_ID));

            //WAP
            loginInfo.setNoOfWAPApps(getAppCountByPhaseAndPlatformId(aimsApps, null, AimsConstants.WAP_PLATFORM_ID));
            loginInfo.setNoOfWAPSubApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.SUBMISSION_ID, AimsConstants.WAP_PLATFORM_ID));
            loginInfo.setNoOfWAPEvaluatedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.EVALUATED_ID, AimsConstants.WAP_PLATFORM_ID));
            loginInfo.setNoOfWAPTestingApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.TESTING_ID, AimsConstants.WAP_PLATFORM_ID));
            loginInfo.setNoOfWAPAcceptedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.ACCEPTANCE_ID, AimsConstants.WAP_PLATFORM_ID));
            loginInfo.setNoOfWAPRejectedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.REJECTED_ID, AimsConstants.WAP_PLATFORM_ID));

            //Enterprise
            loginInfo.setNoOfEntApps(getAppCountByPhaseAndPlatformId(aimsApps, null, AimsConstants.ENTERPRISE_PLATFORM_ID));
            loginInfo.setNoOfEntSubApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.SUBMISSION_ID, AimsConstants.ENTERPRISE_PLATFORM_ID));
            loginInfo.setNoOfEntEvaluatedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.EVALUATED_ID, AimsConstants.ENTERPRISE_PLATFORM_ID));
            loginInfo.setNoOfEntTestingApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.TESTING_ID, AimsConstants.ENTERPRISE_PLATFORM_ID));
            loginInfo.setNoOfEntAcceptedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.ACCEPTANCE_ID, AimsConstants.ENTERPRISE_PLATFORM_ID));
            loginInfo.setNoOfEntRejectedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.REJECTED_ID, AimsConstants.ENTERPRISE_PLATFORM_ID));

            //VZAppZone
            loginInfo.setNoOfVZAppZoneApps(getAppCountByPhaseAndPlatformId(aimsApps, null, AimsConstants.VZAPPZONE_PLATFORM_ID));
            loginInfo.setNoOfVZAppZoneSubApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.SUBMISSION_ID, AimsConstants.VZAPPZONE_PLATFORM_ID));
            loginInfo.setNoOfVZAppZoneEvaluatedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.EVALUATED_ID, AimsConstants.VZAPPZONE_PLATFORM_ID));
            loginInfo.setNoOfVZAppZoneTestingApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.TESTING_ID, AimsConstants.VZAPPZONE_PLATFORM_ID));
            //if VZAppZone application is accepted, its status will be changed to under testing
            loginInfo.setNoOfVZAppZoneAcceptedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.TESTING_ID, AimsConstants.VZAPPZONE_PLATFORM_ID));
            loginInfo.setNoOfVZAppZoneRejectedApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.PHASE_INITIAL_DENIED_ID, AimsConstants.VZAPPZONE_PLATFORM_ID));

            //Dashboard
            loginInfo.setNoOfDashApps(getAppCountByPhaseAndPlatformId(aimsApps, null, AimsConstants.DASHBOARD_PLATFORM_ID));
            loginInfo.setNoOfDashSubApps(getAppCountByPhaseAndPlatformId(aimsApps, AimsConstants.SUBMISSION_ID, AimsConstants.DASHBOARD_PLATFORM_ID));

            //Total
            loginInfo.setTotalApps(
                calculateValues(
                    loginInfo.getNoOfBrewApps(),
                    loginInfo.getNoOfSMSApps(),
                    loginInfo.getNoOfMMSApps(),
                    loginInfo.getNoOfWAPApps(),
                    loginInfo.getNoOfEntApps(),
                    loginInfo.getNoOfVZAppZoneApps(),
                    loginInfo.getNoOfDashApps()));
            loginInfo.setTotalSubApps(
                calculateValues(
                    loginInfo.getNoOfBrewSubApps(),
                    loginInfo.getNoOfSMSSubApps(),
                    loginInfo.getNoOfMMSSubApps(),
                    loginInfo.getNoOfWAPSubApps(),
                    loginInfo.getNoOfEntSubApps(),
                    loginInfo.getNoOfVZAppZoneSubApps(),
                    loginInfo.getNoOfDashSubApps()));
            loginInfo.setTotalEvaluatedApps(
                calculateValues(
                    loginInfo.getNoOfBrewEvaluatedApps(),
                    loginInfo.getNoOfSMSEvaluatedApps(),
                    loginInfo.getNoOfMMSEvaluatedApps(),
                    loginInfo.getNoOfWAPEvaluatedApps(),
                    loginInfo.getNoOfEntEvaluatedApps(),
                    loginInfo.getNoOfVZAppZoneEvaluatedApps(),
                    "0"));
            loginInfo.setTotalTestedApps(
                calculateValues(
                    loginInfo.getNoOfBrewTestingApps(),
                    loginInfo.getNoOfSMSTestingApps(),
                    loginInfo.getNoOfMMSTestingApps(),
                    loginInfo.getNoOfWAPTestingApps(),
                    loginInfo.getNoOfEntTestingApps(),
                    loginInfo.getNoOfVZAppZoneEvaluatedApps(),
                    "0"));
            loginInfo.setTotalAcceptedApps(
                calculateValues(
                    loginInfo.getNoOfBrewAcceptedApps(),
                    loginInfo.getNoOfSMSAcceptedApps(),
                    loginInfo.getNoOfMMSAcceptedApps(),
                    loginInfo.getNoOfWAPAcceptedApps(),
                    loginInfo.getNoOfEntAcceptedApps(),
                    loginInfo.getNoOfVZAppZoneEvaluatedApps(),
                    "0"));
            loginInfo.setTotalRejectedApps(
                calculateValues(
                    loginInfo.getNoOfBrewRejectedApps(),
                    loginInfo.getNoOfSMSRejectedApps(),
                    loginInfo.getNoOfMMSRejectedApps(),
                    loginInfo.getNoOfWAPRejectedApps(),
                    loginInfo.getNoOfEntRejectedApps(),
                    loginInfo.getNoOfVZAppZoneEvaluatedApps(),
                    "0"));

            session.setAttribute("loginInfo", loginInfo);

        }
        catch (HibernateException ex)
        {
            // todo
        }
    }

    public static String calculateValues(String vaule1, String value2, String value3, String value4, String value5, String value6, String value7)
    {
        int total = Integer.parseInt(vaule1) + Integer.parseInt(value2) + Integer.parseInt(value3) + Integer.parseInt(value4) + Integer.parseInt(value5) + Integer.parseInt(value6)+ Integer.parseInt(value7);
        return String.valueOf(total);
    }

    public static String getAppCountByPhaseAndPlatformId(Collection collection, Long phaseId, Long platformId)
    {
        int noOfApp = 0;

        for (Iterator iter = collection.iterator(); iter.hasNext();)
        {
            Object[] item = (Object[]) iter.next();

            if ((item[0] != null) && (item[1] != null))
            {
                //If Alliance View
                if (platformId == null)
                {
                    if ((Utility.getObjectLong(item[0]).equals(phaseId)) && (AimsConstants.SAVED_ID.equals(phaseId)))
                        noOfApp++;
                    else if (AimsConstants.SUBMISSION_ID.equals(phaseId))
                    {
                        if (!(Utility.getObjectLong(item[0]).equals(AimsConstants.SAVED_ID)
                            || Utility.getObjectLong(item[0]).equals(AimsConstants.ACCEPTANCE_ID)
                            || Utility.getObjectLong(item[0]).equals(AimsConstants.SUNSET_ID)
                            || Utility.getObjectLong(item[0]).equals(AimsConstants.CONCEPT_REJECTED_ID)
                            || Utility.getObjectLong(item[0]).equals(AimsConstants.REJECTED_ID)))
                            noOfApp++;
                    }
                    else if (AimsConstants.ACCEPTANCE_ID.equals(phaseId))
                    {
                        if (Utility.getObjectLong(item[0]).equals(AimsConstants.ACCEPTANCE_ID)
                            || Utility.getObjectLong(item[0]).equals(AimsConstants.SUNSET_ID))
                            noOfApp++;
                    }
                    else if (AimsConstants.REJECTED_ID.equals(phaseId))
                    {
                        if (Utility.getObjectLong(item[0]).equals(AimsConstants.CONCEPT_REJECTED_ID)
                            || Utility.getObjectLong(item[0]).equals(AimsConstants.REJECTED_ID))
                            noOfApp++;
                    }
                }
                //Else VZW View (App Summary By Platforms)
                else if (Utility.getObjectLong(item[1]).equals(platformId))
                {
                    if (phaseId == null)
                        noOfApp++;
                    else if ((Utility.getObjectLong(item[0]).equals(phaseId)) && (AimsConstants.SUBMISSION_ID.equals(phaseId)))
                        noOfApp++;
                    else if ((Utility.getObjectLong(item[0]).equals(phaseId)) && (AimsConstants.EVALUATED_ID.equals(phaseId)))
                        noOfApp++;
                    else if ((Utility.getObjectLong(item[0]).equals(phaseId)) && (AimsConstants.TESTING_ID.equals(phaseId)))
                        noOfApp++;
                    else if (AimsConstants.ACCEPTANCE_ID.equals(phaseId))
                    {
                        if (Utility.getObjectLong(item[0]).equals(AimsConstants.ACCEPTANCE_ID)
                            || Utility.getObjectLong(item[0]).equals(AimsConstants.SUNSET_ID))
                            noOfApp++;
                    }
                    else if (AimsConstants.REJECTED_ID.equals(phaseId))
                    {
                        if (Utility.getObjectLong(item[0]).equals(AimsConstants.CONCEPT_REJECTED_ID)
                            || Utility.getObjectLong(item[0]).equals(AimsConstants.REJECTED_ID))
                            noOfApp++;
                    }
                }
            }

        }
        return String.valueOf(noOfApp);
    }

    public static Collection getApps() throws HibernateException
    {
        Collection aimsBrewApps = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();

            query.append(" select ").append(" app.aimsLifecyclePhaseId, ").append(" app.aimsPlatformId ").append(" from ").append(
                " com.netpace.aims.model.application.AimsApp as app ");

            aimsBrewApps = session.find(query.toString());
            log.debug("No of records returned: " + aimsBrewApps.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return aimsBrewApps;
    }

    public static String getAllianceCountByStatus(Collection collection, String status)
    {
        int noOfAlliances = 0;

        for (Iterator iter = collection.iterator(); iter.hasNext();)
        {
            if (Utility.getObjectString(iter.next()).equalsIgnoreCase(status))
            {
                noOfAlliances++;
            }
        }
        return String.valueOf(noOfAlliances);
    }

    public static Collection getAlliances() throws HibernateException
    {
        Collection aimsAlliances = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();

            query.append("select ").append(" alliance.status ").append(" from ").append(" com.netpace.aims.model.core.AimsAllianc alliance ");

            aimsAlliances = session.find(query.toString());
            log.debug("No of alliances : " + aimsAlliances.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return aimsAlliances;
    }

    public static Collection getAppsByAllianceId(Long allianceId) throws HibernateException
    {
        Collection aimsApps = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();

            query
                .append("select ")
                .append("       app.aimsLifecyclePhaseId, ")
                .append("       alliance.status ")
                .append("from ")
                .append("       com.netpace.aims.model.application.AimsApp as app, ")
                .append("       com.netpace.aims.model.core.AimsAllianc alliance ")
                .append("where ")
                .append("       alliance.allianceId = :allianceId ")
                .append("       and app.aimsAllianceId = alliance.allianceId ");

            aimsApps = session.find(query.toString(), allianceId, new LongType());
            log.debug("No of : " + aimsApps.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return aimsApps;
    }

    /**
     *  This method checks returns the privelege object of the user has acces to the said
     *  privilege.
     */
    public static AimsRolePrivilege getPrivilege(Hashtable priveleges, String privilegeId) throws AimsSecurityException
    {
        Object p_obj = priveleges.get(privilegeId);
        if (p_obj == null)
        {
            throw new AimsSecurityException();
        }
        else
            return (AimsRolePrivilege) p_obj;
    }

    public static AimsSection getSection(AimsRolePrivilege role_privilege, String SECTION)
    {
        AimsSysPrivilege privilege = role_privilege.getPrivilege();

        //Set sections = privilege.getSections();

        Set sections = PrivilegeManager.getInstance().getPrivilege(privilege.getPrivilegeId()).getSections();
        Iterator sectionIter = sections.iterator();
        AimsSection section = null;
        while (sectionIter.hasNext())
        {
            section = (AimsSection) sectionIter.next();
            if (section.getSectionKey().trim().equals(SECTION))
                return section;
        }
        return null;
    }

    /**
      *  Private method for debugging
      */
    private static void debugSet(AbstractCollection collection)
    {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
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
        AimsRolePrivilege role_privilege = null;

        try
        {
            role_privilege = AimsSecurityManager.getPrivilege((Hashtable) request.getSession().getAttribute(AimsConstants.AIMS_PRIVILEGES), PRIVILEGE);
        }
        catch (AimsSecurityException ex)
        {
            return false;
        }

        try
        {
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
        catch (Exception ex)
        {
            return false;
        }
    }

    /**
    *  This method checks if the user has the specified rights to a particular privilige.
    *  If the user has no rights it returns false, but if the user has some rights
    *  but not the specified right then it returns false, else it returns true.
    *  @param request - the Httprequest object
    *  @param PRIVILEGE - the privilege key to be checked
    *  @param SECTION - the section to be checked.
    *  @param ACCESS_LEVEL - the access level to be checked. One of SELECT,UPDATE,INSERT or DELETE
    */
    public static boolean checkSectionAccess(HttpServletRequest request, String PRIVILEGE, String SECTION, int ACCESS_LEVEL)
    {
        AimsRolePrivilege role_privilege = null;

        try
        {
            role_privilege = getPrivilege((Hashtable) request.getSession().getAttribute(AimsConstants.AIMS_PRIVILEGES), PRIVILEGE);
        }
        catch (AimsSecurityException ex)
        {
            return false;
        }

        AimsSection section = getSection(role_privilege, SECTION);

        if (section == null)
            return false;

        try
        {
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
        catch (Exception ex)
        {
            return false;
        }
    }

    public static void setLastLoginDate(Long userId) throws HibernateException {
		Session session = null;
		Transaction trx=null;
		try {
			log.debug("setLoginDate start");
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			AimsUser user=(AimsUser)session.load(AimsUser.class, userId);
			user.setLastLoginDate(new Date());
			session.update(user);
			trx.commit();
			log.debug("transaction commited");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (trx !=null){
				trx.rollback();
			}
			throw e;
		} finally {
			if(session !=null){
				session.close();
				log.debug("session is closed");
			}
		}
		log.debug("end");
	}

}