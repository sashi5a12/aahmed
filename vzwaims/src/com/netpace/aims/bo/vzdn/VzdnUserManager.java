package com.netpace.aims.bo.vzdn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.roles.AimsSysRolesManager;
import com.netpace.aims.bo.security.InvalidLoginException;
import com.netpace.aims.controller.VzdnCredentials;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.security.AimsSysRole;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.StringFuncs;

public class VzdnUserManager {

	static Logger  log = Logger.getLogger(VzdnUserManager.class);

	public static AimsUser createUser(VzdnCredentials vzdnCredentials,  Long allianceId) throws Exception {

		AimsUser newCreatedAimsUser = null;
		log.debug("---creating the User in ZON");
		log.debug("---roles : "+vzdnCredentials.getVzdnManagerRoles());
		/*Collection sysRoles = AimsSysRolesManager.getAllSysRolesWOTrx(session);
		log.debug("---sys roles size : "+sysRoles.size());
		List roleIdsList = new ArrayList();
		Iterator it = sysRoles.iterator();
		AimsUser newCreatedAimsUser = null;
		while (it.hasNext()) {
			log.debug("---in while :???? ");
			AimsSysRole sysRole = (AimsSysRole) it.next();
			if (sysRole.getVzdnMappingRoleID() != null
					&& vzdnCredentials.getVzdnManagerRoles().contains(
							sysRole.getVzdnMappingRoleID().toString()))
				roleIdsList.add(sysRole.getRoleId().toString());
		}
		if (roleIdsList.size() > 0) {
			log.debug("zon related roles found in vzdn user");
			String[] roleArray = VzdnUserManager.getStringArray(roleIdsList);*/
		String[] roleArray = new String []{vzdnCredentials.getVzdnManagerRoles()};
			AimsAccountsManager.CreatUserRolesWithoutTransaction(vzdnCredentials.getUserName(),
					StringFuncs.asHex(MiscUtils.generate128bitkey()), AimsConstants.USER_STATUS_ACTIVE, vzdnCredentials.getFirstName(), vzdnCredentials.getLastName(),
					vzdnCredentials.getUserName(), vzdnCredentials.getTitle(), vzdnCredentials.getPhoneNumber(), vzdnCredentials.getMobileNumber(), vzdnCredentials.getFaxNumber(), vzdnCredentials.getUserName(), AimsConstants.ALLIANCE_USERTYPE, roleArray, allianceId);

			log.debug("User and roles created In ZON ");
			newCreatedAimsUser = AimsAccountsManager
					.validateUser(vzdnCredentials.getUserName());
		//}
		return newCreatedAimsUser;
	}

	public static String[] getStringArray(List vzdnRoles) {

		String[] result = new String[vzdnRoles.size()];
		for (int i = 0; i < vzdnRoles.size(); i++) {
			result[i] = vzdnRoles.get(i).toString();
			log.debug("+++++++inserting into array :"+vzdnRoles.get(i).toString());
		}

		return result;
	}
	
	public static String[] getStringArrayFromObject(Set userRoles) {
		String[] result = new String[userRoles.size()];
		Iterator it = userRoles.iterator();
		int i = 0 ;
		while(it.hasNext()){
			AimsSysRole role = (AimsSysRole) it.next();
			result[i] = role.getRoleId().toString(); 
			i++;
		}
		return result;
	}
	
	//
	

	public static void updateUser(AimsUser user, VzdnCredentials vzdnCredentials, Session session)
			throws Exception {

		Collection sysRoles = AimsSysRolesManager.getAllSysRoles();
		List roleIdsList = new ArrayList();
		log.debug("---csvroles in loginaction content: "
				+ vzdnCredentials.getVzdnManagerRoles());
		log.debug("---sysRoles from db size  : " + sysRoles.size());
		Iterator it = sysRoles.iterator();
		while (it.hasNext()) {
			AimsSysRole sysRole = (AimsSysRole) it.next();
			if (sysRole.getVzdnMappingRoleID() != null
				&& vzdnCredentials.getVzdnManagerRoles().contains(sysRole.getVzdnMappingRoleID().toString())
				&& (
					(sysRole.getRoleType().equals(AimsConstants.ALLIANCEADMIN_ROLETYPE) && (vzdnCredentials.getVzdnUserType().equals("Developer")) )	
					||
					( (sysRole.getRoleType().equals(AimsConstants.VZWADMIN_ROLETYPE)|| sysRole.getRoleType().equals(AimsConstants.VZW_USERTYPE)) && (vzdnCredentials.getVzdnUserType().equals("Verizon")) )
					)
			)
				roleIdsList.add(sysRole.getRoleId().toString());
		}
		if (roleIdsList.size() > 0) {
			log.debug("zon related roles found in vzdn user");
			String[] roleArray = VzdnUserManager.getStringArray(roleIdsList);
			log.debug("////final role stering :"+roleArray.toString());
			
			AimsAccountsManager.UpdateUserRoles(user.getUserId().intValue(),
					user.getUsername(), user.getPassword(), user
							.getUserAccountStatus(), vzdnCredentials.getFirstName(), vzdnCredentials.getLastName(), user.getAimsContact()
							.getEmailAddress(), vzdnCredentials.getTitle(), vzdnCredentials.getPhoneNumber(), vzdnCredentials.getMobileNumber(), vzdnCredentials.getFaxNumber(), user.getUsername(),
					user.getUserType(), roleArray);
			log.debug("---roles updated in zon.");
		}
		
	}
	
	
	//mark
	public static AimsUser updateUser(AimsUser user,
			VzdnCredentials vzdnCredentials) throws Exception {
		try{
			Collection sysRoles = AimsSysRolesManager.getAllSysRoles();
			List roleIdsList = new ArrayList();
			log.debug("---csvroles in loginaction content: "+ vzdnCredentials.getVzdnManagerRoles());
			log.debug("---sysRoles from db size  : " + sysRoles.size());
			Iterator it = sysRoles.iterator();
			
			while (it.hasNext()) {
				AimsSysRole sysRole = (AimsSysRole) it.next();
				if (sysRole.getVzdnMappingRoleID() != null
						&&   (vzdnCredentials.getVzdnManagerRoles()!=null &&  vzdnCredentials.getVzdnManagerRoles().contains(sysRole.getVzdnMappingRoleID().toString())) 
						&& (
							(sysRole.getRoleType().equals(AimsConstants.ALLIANCEADMIN_ROLETYPE) && (vzdnCredentials.getVzdnUserType().equals("Developer")) )	
							||
							( (sysRole.getRoleType().equals(AimsConstants.VZWADMIN_ROLETYPE)|| sysRole.getRoleType().equals(AimsConstants.VZW_USERTYPE)) && (vzdnCredentials.getVzdnUserType().equals("Verizon")) )
							)
				)

					roleIdsList.add(sysRole.getRoleId().toString());
				
			}
			String[] roleArray;
				if(user.getUserType().equals(AimsConstants.ALLIANCE_USERTYPE)){
					log.debug("rewriting dev roles .");
					//List developerRoles = new ArrayList();
					//developerRoles.addAll(user.getRoles());
					roleArray = (user.getRoles()!=null && user.getRoles().size()>0)?VzdnUserManager.getStringArrayFromObject(user.getRoles()):new String[0];
					log.debug("dev roles length are _______________ : "+roleArray.length);
					if(roleArray.length>0){
						log.debug("dev roles are ______--------- : "+roleArray[0]);
						
					}
				}else
					roleArray = VzdnUserManager.getStringArray(roleIdsList);
				
				AimsAccountsManager.UpdateUserRoles(user
						.getUserId().intValue(), user.getUsername(), user
						.getPassword(), user.getUserAccountStatus(), vzdnCredentials.getFirstName(), vzdnCredentials.getLastName() , user.getAimsContact().getEmailAddress(),
						vzdnCredentials.getTitle(), vzdnCredentials.getPhoneNumber(), vzdnCredentials.getMobileNumber(),
						vzdnCredentials.getFaxNumber(), user.getUsername(), user
								.getUserType(), roleArray);
				log.debug("---roles updated in zon.");
			

			
			return AimsAccountsManager.validateUser(user.getUsername());
			
	}catch(Exception ex){
		ex.printStackTrace();
		/*if (tx!=null)
			tx.rollback();
		try{session.close();}catch(Exception ignore){}*/
		throw new Exception(ex);
	}
}
	
	public static AimsUser createUser(VzdnCredentials vzdnCredentials) throws Exception {
		try{
			log.debug("---creating the User in ZON");
			log.debug("---roles : "+vzdnCredentials.getVzdnManagerRoles());
			
			Collection sysRoles = AimsSysRolesManager.getAllSysRoles();
			log.debug("---sys roles size : "+vzdnCredentials.getVzdnManagerRoles());
			List roleIdsList = new ArrayList();
			Iterator it = sysRoles.iterator();
			AimsUser newCreatedAimsUser = null;
			while (it.hasNext()) {
				AimsSysRole sysRole = (AimsSysRole) it.next();
				if (sysRole.getVzdnMappingRoleID() != null
						&& vzdnCredentials.getVzdnManagerRoles().contains(sysRole.getVzdnMappingRoleID().toString())
						&& (
							(sysRole.getRoleType().equals(AimsConstants.ALLIANCEADMIN_ROLETYPE) && (vzdnCredentials.getVzdnUserType().equals("Developer")) )	
							||
							( (sysRole.getRoleType().equals(AimsConstants.VZWADMIN_ROLETYPE)|| sysRole.getRoleType().equals(AimsConstants.VZW_USERTYPE)) && (vzdnCredentials.getVzdnUserType().equals("Verizon")) )
							)
				)
					roleIdsList.add(sysRole.getRoleId().toString());
			}
			if (roleIdsList.size() > 0) {
				log.debug("zon related roles found in vzdn user");
				String[] roleArray = VzdnUserManager.getStringArray(roleIdsList);
				AimsAccountsManager.CreatUserRoles(vzdnCredentials.getUserName(),
						StringFuncs.asHex(MiscUtils.generate128bitkey()), AimsConstants.USER_STATUS_ACTIVE, vzdnCredentials.getFirstName(), vzdnCredentials.getLastName(), vzdnCredentials.getUserName(), vzdnCredentials.getTitle(), vzdnCredentials.getPhoneNumber(), vzdnCredentials.getMobileNumber(), vzdnCredentials.getFaxNumber(), vzdnCredentials.getUserName(), AimsConstants.VZW_USERTYPE, roleArray);
				log.debug("User and roles created In ZON ");
				newCreatedAimsUser = AimsAccountsManager
						.validateUser(vzdnCredentials.getUserName());
			}else{
				log.debug("zon related roles not found. creating user with no roles");
				String[] roleArray = new String [0];
				AimsAccountsManager.CreatUserRoles(vzdnCredentials.getUserName(),
						StringFuncs.asHex(MiscUtils.generate128bitkey()), AimsConstants.USER_STATUS_ACTIVE, vzdnCredentials.getFirstName(), vzdnCredentials.getLastName(), vzdnCredentials.getUserName(),vzdnCredentials.getTitle(), vzdnCredentials.getPhoneNumber(), vzdnCredentials.getMobileNumber(), vzdnCredentials.getFaxNumber() , vzdnCredentials.getUserName(), AimsConstants.VZW_USERTYPE, roleArray);
				log.debug("User without roles  created In ZON ");
				newCreatedAimsUser = AimsAccountsManager
						.validateUser(vzdnCredentials.getUserName());
				
			}
			
			return newCreatedAimsUser;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}
	
	
	public static AimsUser createVerizonUserWithMarkingPermDelete(VzdnCredentials vzdnCredentials,AimsUser user) throws Exception {
		try{
			log.debug("---creating the User in ZON");
			log.debug("---roles : "+vzdnCredentials.getVzdnManagerRoles());
			int count = VzdnUserManager.recursviceUserCheck(vzdnCredentials.getUserName());
			if(VzdnUserManager.recursviceUserCheck(vzdnCredentials.getUserName())>0){
				log.debug("---marking user :"+vzdnCredentials.getUserName()+"to "+vzdnCredentials.getUserName()+"del_00"+count);
				String updateUsername=vzdnCredentials.getUserName()+"del_00"+count;
				log.debug("---final username"+updateUsername);
				AimsAccountsManager.UpdateUserRolesWithPermanentDelete(user
						.getUserId().intValue(), updateUsername, user
						.getPassword(), user.getUserAccountStatus(), user.getAimsContact().getFirstName(), user.getAimsContact().getLastName() , user.getAimsContact().getEmailAddress(),
						user.getAimsContact().getTitle(), user.getAimsContact().getPhone(), user.getAimsContact().getMobile(),
						user.getAimsContact().getFax(), user.getUsername(), user
								.getUserType(), VzdnUserManager.getStringArrayFromObject(user.getRoles()),AimsConstants.VZDN_PERMANENT_DELETE);
			}
			
			Collection sysRoles = AimsSysRolesManager.getAllSysRoles();
			log.debug("---sys roles size : "+vzdnCredentials.getVzdnManagerRoles());
			List roleIdsList = new ArrayList();
			Iterator it = sysRoles.iterator();
			AimsUser newCreatedAimsUser = null;
			while (it.hasNext()) {
				AimsSysRole sysRole = (AimsSysRole) it.next();
				if (sysRole.getVzdnMappingRoleID() != null
						&& vzdnCredentials.getVzdnManagerRoles().contains(sysRole.getVzdnMappingRoleID().toString())
						&& (
							(sysRole.getRoleType().equals(AimsConstants.ALLIANCEADMIN_ROLETYPE) && (vzdnCredentials.getVzdnUserType().equals("Developer")) )	
							||
							( (sysRole.getRoleType().equals(AimsConstants.VZWADMIN_ROLETYPE)|| sysRole.getRoleType().equals(AimsConstants.VZW_USERTYPE)) && (vzdnCredentials.getVzdnUserType().equals("Verizon")) )
							)
				)
					roleIdsList.add(sysRole.getRoleId().toString());
			}
			if (roleIdsList.size() > 0) {
				log.debug("zon related roles found in vzdn user");
				String[] roleArray = VzdnUserManager.getStringArray(roleIdsList);
				AimsAccountsManager.CreatUserRoles(vzdnCredentials.getUserName(),
						StringFuncs.asHex(MiscUtils.generate128bitkey()), AimsConstants.USER_STATUS_ACTIVE, vzdnCredentials.getFirstName(), vzdnCredentials.getLastName(), vzdnCredentials.getUserName(), vzdnCredentials.getTitle(), vzdnCredentials.getPhoneNumber(), vzdnCredentials.getMobileNumber(), vzdnCredentials.getFaxNumber(), vzdnCredentials.getUserName(), AimsConstants.VZW_USERTYPE, roleArray);
				log.debug("User and roles created In ZON ");
				newCreatedAimsUser = AimsAccountsManager
						.validateUser(vzdnCredentials.getUserName());
			}else{
				log.debug("zon related roles not found. creating user with no roles");
				String[] roleArray = new String [0];
				AimsAccountsManager.CreatUserRoles(vzdnCredentials.getUserName(),
						StringFuncs.asHex(MiscUtils.generate128bitkey()), AimsConstants.USER_STATUS_ACTIVE, vzdnCredentials.getFirstName(), vzdnCredentials.getLastName(), vzdnCredentials.getUserName(),vzdnCredentials.getTitle(), vzdnCredentials.getPhoneNumber(), vzdnCredentials.getMobileNumber(), vzdnCredentials.getFaxNumber() , vzdnCredentials.getUserName(), AimsConstants.VZW_USERTYPE, roleArray);
				log.debug("User without roles  created In ZON ");
				newCreatedAimsUser = AimsAccountsManager
						.validateUser(vzdnCredentials.getUserName());
				
			}
			
			return newCreatedAimsUser;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}
	
	
	public static  int  recursviceUserCheck(String userName){
		boolean iterateMore=true;
		int count =1;
		String usertoSearch="";
		while(iterateMore){
			try {
				  usertoSearch=userName+"del_00"+count;	
				  AimsUser aimsUser = AimsAccountsManager.validateAnyUser(usertoSearch);
				  if(aimsUser==null){
					  iterateMore=false;
					  return count;
				  }
			}catch(Exception e) {
				iterateMore=false;
				return -1;
			}
			count++;
		}
		return count;
		
	}
	
}
