package com.netpace.aims.crons;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.type.LongType;
import net.sf.hibernate.type.StringType;
import net.sf.hibernate.type.Type;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.roles.AimsSysRolesManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.security.AimsSysRole;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.OpenssoRestServiceTmp;

public class MailLdapChangeUser {
	
	private static Logger log = Logger.getLogger(MailLdapChangeUser.class.getName());
	
    public static Collection getAccountsBytypeAndStatus(String user_type, String userAccountStatus) throws HibernateException {
    	log.debug("AimsAccountsManager.getAccountsBytypAndStatus Start:");
    	log.debug("Param user_type"+user_type);
    	log.debug("Param userAccountStatus"+userAccountStatus);
		Collection collection = null;
		Collection aimsAccounts = new ArrayList();
		Session session = null;
		Object[] userValues = null;
		StringBuffer queryStringBuffer = new StringBuffer();
		AimsUser user = null;
		AimsContact contact = null;
		Object[] parameterValues = null;
		Type[] parameterTypes = null;

		try {

			parameterValues = new Object[2];
			parameterTypes = new Type[2];

			parameterValues[0] = user_type;
			parameterValues[1] = userAccountStatus;

			parameterTypes[0] = new StringType();
			parameterTypes[1] = new StringType();
			
			queryStringBuffer.append("select ")
					.append("		user.userId, user.username, user.userAccountStatus, user.password, user.userType, ")
					.append("		contact.firstName, contact.lastName, contact.emailAddress  ")
					.append("from ")
					.append("		com.netpace.aims.model.core.AimsUser user  ")
					.append("		inner join user.aimsContact contact ")
					.append("where ")
					.append("		user.userType = :userType ")
					.append("		and user.userAccountStatus = :status")	
					.append("       and user.lastLoginDate is not null")
					.append("       and user.lastLoginDate >= '28-JUL-2009'");
			queryStringBuffer.append(" order by lower(user.username)");

			session = DBHelper.getInstance().getSession();

			collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);

			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				userValues = (Object[]) iter.next();
				user = new AimsUser();
				contact = new AimsContact();

				user.setUserId((Long) userValues[0]); // userId
				user.setUsername((String) userValues[1]); // userName
				user.setUserAccountStatus((String) userValues[2]); // userAccountStatus
				user.setPassword((String)userValues[3]);
				user.setUserType((String)userValues[4]);
				contact.setFirstName((String) userValues[5]); // firstName
				contact.setLastName((String) userValues[6]); // lastName
				contact.setEmailAddress((String)userValues[7]);
				user.setAimsContact(contact);
				aimsAccounts.add(user);
			}

			log.debug("No of records returned in getAccountsBytypeAndStatus: "+ collection.size());

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.debug("AimsAccountsManager.getAccountsBytypAndStatus End:");
		return aimsAccounts;
	}
    
    public static Collection getAssignedRoles(Long user_id) throws HibernateException {
		Collection collection = null;
		Collection AimsSysRoles = new ArrayList();
		Session session = null;

		Object[] sysRoleValues = null;
		StringBuffer queryStringBuffer = new StringBuffer();

		AimsSysRole sysRole = null;

		try {

			session = DBHelper.getInstance().getSession();

			queryStringBuffer
				.append("select ")
				.append("		role.roleId, role.roleName, role.vzdnMappingRoleID ")
				.append("from ")
				.append("		com.netpace.aims.model.core.AimsUser user ")
				.append("		inner join user.roles role ")
				.append("where ")
				.append("		user.userId = :user_id ")
				.append("order by role.roleId ");

			collection = session.find(queryStringBuffer.toString(), user_id, new LongType());

			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				sysRoleValues = (Object[]) iter.next();

				sysRole = new AimsSysRole();

				sysRole.setRoleId((Long) sysRoleValues[0]);
				sysRole.setRoleName((String) sysRoleValues[1]);
				sysRole.setVzdnMappingRoleID((Long) sysRoleValues[2]);

				AimsSysRoles.add(sysRole);
			}
			log.debug("No of records returned: " + collection.size());
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

		return AimsSysRoles;
	}    
	
	public static void main(String[] args) {
		log.debug("MailLdapChangeUser Start");
		long startTime = System.currentTimeMillis();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		DBHelper dbHelper = null;
		try {					
			ConfigEnvProperties props = ConfigEnvProperties.getInstance();
			Configuration conf = new Configuration();
			
			final String mailTo = "aahmed@netpace.com,mshiraz@netpace.com,amakda@netpace.com";

			String opensso_userRoles = props.getProperty("opensso.vzdn.user.roles");
			String opensso_userType = props.getProperty("opensso.vzdn.user.type");
			String opensso_title = props.getProperty("opensso.vzdn.title");
			String opensso_mobileNumber = props.getProperty("opensso.vzdn.mobile.number");
			String opensso_phoneNumber = props.getProperty("opensso.vzdn.phone.number");
			String opensso_faxNumber = props.getProperty("opensso.vzdn.fax.number");			
			String opensso_firstName_attr="givenname";
			String opensso_lastName_attr="sn";
			String opensso_user_status="inetUserStatus";
			
			dbHelper = DBHelper.getInstance();

			conf.setProperty("hibernate.connection.url", props.getProperty("connection.url"));
			conf.setProperty("hibernate.connection.username", props.getProperty("connection.username"));
			conf.setProperty("hibernate.connection.password", props.getProperty("connection.password"));
			dbHelper.sessionFactory = conf.configure().buildSessionFactory();
			
			Collection<AimsUser> verizonUsers= MailLdapChangeUser.getAccountsBytypeAndStatus(AimsConstants.VZW_USERTYPE, AimsConstants.USER_STATUS_ACTIVE);
			Collection sysRoles = AimsSysRolesManager.getAllSysRoles();
			StringBuffer mailBodyBuffer=new StringBuffer();
			Map<String, String> roleMapping=new HashMap<String, String>();
			
			for (Iterator itr=sysRoles.iterator(); itr.hasNext();){
				AimsSysRole role=(AimsSysRole)itr.next();
				roleMapping.put(role.getVzdnMappingRoleID().toString(), role.getRoleId().toString());
			}

			mailBodyBuffer.append("Insert into AIMS_USER_ROLES (USER_ID, ROLE_ID) Values (?, ?)\t\n");
			mailBodyBuffer.append("Delete from AIMS_USER_ROLES where USER_ID=? and ROLE_ID=?");
			mailBodyBuffer.append("\n\n");
			Iterator<AimsUser> itr=verizonUsers.iterator();
			
			while (itr.hasNext()){
				AimsUser user= itr.next();				
				log.debug("Starting Process for username= "+user.getUsername());
				try {
					//Get user info from ldap
					Map values=OpenssoRestServiceTmp.read(user.getUsername().toLowerCase(), new String[]{opensso_userRoles,opensso_userType,opensso_firstName_attr,opensso_lastName_attr,opensso_title,opensso_mobileNumber,opensso_phoneNumber,opensso_faxNumber,opensso_user_status});
					
					//Get zon user roles
					Collection<AimsSysRole> gtmUserAssignedRoles=MailLdapChangeUser.getAssignedRoles(user.getUserId());
					
					//Create zon user roles sorted string
					String gtmRolesStr=new String();
					for (Iterator<AimsSysRole> i=gtmUserAssignedRoles.iterator(); i.hasNext();){
						AimsSysRole r=i.next();
						if (i.hasNext()){
							gtmRolesStr = gtmRolesStr + r.getRoleId() + ",";
						}
						else{
							gtmRolesStr = gtmRolesStr + r.getRoleId();
						}
					}
				
					//Create ldap user roles sorted string 
					String opensso_roles_value=(String)values.get(opensso_userRoles);
					String ldapRolesStr=new String();
					if (StringUtils.isNotEmpty(opensso_roles_value) && ((String)values.get(opensso_userType)).equalsIgnoreCase("Verizon")){
						String[] rolesArray=opensso_roles_value.split(",");
						
						List<Integer> gtmRoles=new ArrayList<Integer>();
						for (int i=0; i<rolesArray.length; i++){
							if (Integer.parseInt(rolesArray[i]) >= 2000 && Integer.parseInt(rolesArray[i]) < 3000){
								if (roleMapping.containsKey(rolesArray[i]))
									gtmRoles.add( new Integer(roleMapping.get(rolesArray[i])));
							}
						}
						
						int[] rolesList=new int[gtmRoles.size()];
						for (int i=0; i<gtmRoles.size(); i++){
							rolesList[i]=gtmRoles.get(i).intValue();
						}				
						/*
						int[] rolesList=new int[rolesArray.length];
						for (int i=0; i<rolesArray.length; i++){
							rolesList[i]=Integer.parseInt(rolesArray[i]);
						}
						*/
						Arrays.sort(rolesList);
						
						for (int i = 0; i < rolesList.length; i++){
			                if (i != rolesList.length - 1){
			                	ldapRolesStr = ldapRolesStr + rolesList[i] + ",";
			                }
			                else{
			                	ldapRolesStr = ldapRolesStr + rolesList[i];
			                }
			            }						
					}
					else {
						ldapRolesStr = opensso_roles_value;
					}
					
					if (ldapRolesStr.equalsIgnoreCase(gtmRolesStr) == false){
						mailBodyBuffer.append("GTM User Id:\t").append(user.getUserId()).append("\n");
						mailBodyBuffer.append("User Name:\t"+user.getUsername()).append("\n");
						mailBodyBuffer.append("LDAP User Type:\t"+(String)values.get(opensso_userType)).append("\n");
						mailBodyBuffer.append("GTM User Type:\t"+user.getUserType()).append("\n");						
						mailBodyBuffer.append("LDAP Roles:\t"+ldapRolesStr).append("\n");
						mailBodyBuffer.append("GTM  Roles:\t"+gtmRolesStr).append("\n");
						mailBodyBuffer.append("****************************************************************************").append("\n");						
					}
										
				}catch (IOException ioe){
					log.error(user.getUsername()+" User not found in LDAP...");
				}
			}	

			if (mailBodyBuffer.length()>0){
				MailUtils.sendMail(mailTo, AimsNotificationConstants.FROM_ADDRESS, "LDAP and GTM Users Roles Comparison", null, mailBodyBuffer.toString());
			}
			log.debug("*********************************************************************************************");
			log.debug(mailBodyBuffer.toString());
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			if (dbHelper != null) {
				try {
					dbHelper.sessionFactory.close();
					dbHelper = null;
				} catch (HibernateException he) {
					log.debug("Error occured while closing the session factory");
					log.debug(he, he);
				}
			}
		}
		long endTime = System.currentTimeMillis();
		long elapsed = endTime - startTime;
		log.debug("Process completed in ============> "+dateFormat.format(new Date(elapsed)));
		log.debug("MailLdapChangeUser End");		
	}
}
