package com.netpace.aims.bo.alliance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.text.StringContent;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.alliance.AimsAllianceCarriers;
import com.netpace.aims.model.alliance.AimsAllianceDevTech;
import com.netpace.aims.model.alliance.AimsAllianceDevelopments;
import com.netpace.aims.model.alliance.AimsAllianceFinancing;
import com.netpace.aims.model.alliance.AimsAllianceIndFocus;
import com.netpace.aims.model.alliance.AimsAllianceTopIndFocus;
import com.netpace.aims.model.alliance.AimsAllianceVzwReasons;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsJMAAlliance;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsNotifOptInRecipientsLite;
import com.netpace.aims.model.masters.AimsCarriers;
import com.netpace.aims.model.masters.AimsFinancingOptions;
import com.netpace.aims.model.masters.AimsIndustryFocu;
import com.netpace.aims.model.security.AimsSysRole;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.DBErrorFinder;

public class JMAAllianceRegistrationManager {
	
	 static Logger log = Logger.getLogger(JMAAllianceRegistrationManager.class.getName());
	 
	 public static void saveOrUpdateAlliance(AimsUser aimsUser,AimsAllianc aimsAllianc,AimsContact aimsContact)throws AimsException, UniqueConstraintException, HibernateException
	 {


	        Session session = null;
	        Transaction tx = null;
	        Connection ConOra = null;
	        CallableStatement statement = null;
	        Long vendorId = new Long(0);
	        String currUserName = aimsUser.getUsername();
	   

	        Long allianceId = null;
	        
	        try
	        {
	            session = DBHelper.getInstance().getSession();
	            tx = session.beginTransaction();

	            //Check that the email does not already exists
	            Query userQry= session.createQuery("from AimsUser u where lower(u.username)=:email");            
	            userQry.setString("email", aimsUser.getUsername().toLowerCase());
	            List list=userQry.list();
	            if (list != null && list.size()>0){
	            	AimsException aimsException=new AimsException("Error");
	            	aimsException.addException(new UniqueConstraintException("error.alliance_registration.user_name.exists"));
	            	throw aimsException;
	            }
	            
	            
	            //Saving AimsContact
	            session.saveOrUpdate(aimsContact);
	            
	            //Saving AimsUser
	            session.saveOrUpdate(aimsUser);
	            
	            //Opting-in the user created to the 'ALLIANCE_ACCEPTED' Event (By Default).
	            AimsNotifOptInRecipientsLite notifOptIn = new AimsNotifOptInRecipientsLite();
	            notifOptIn.setUserId(aimsUser.getUserId());
	            notifOptIn.setNotificationId(new Long(AimsNotificationConstants.NOTIFICATION_ALLIANCE_ACCEPTED));
	            session.save(notifOptIn);

	            //Saving AimsAlliance
	            aimsAllianc.setAimsUserByAdminUserId(aimsUser.getUserId());
	            session.saveOrUpdate(aimsAllianc);

	            allianceId = aimsAllianc.getAllianceId();
	           
	            //Saving JMA Alliance
	            AimsJMAAlliance aimsJmaAlliance=new AimsJMAAlliance();
	            aimsJmaAlliance.setAllianceId(allianceId);
	            aimsJmaAlliance.setJmaAllianceId(allianceId);
	            aimsJmaAlliance.setCreatedDate(new java.util.Date());
	            aimsJmaAlliance.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
	            session.flush();
	            session.save(aimsJmaAlliance);

	            //Updating AimsUser
	            Set roleSet = new HashSet();
	            AimsSysRole sysRole = null;
	            Query query = session.createQuery("select from com.netpace.aims.model.security.AimsSysRole as sysrole where sysrole.roleName = :roleName");
	            query.setString("roleName", AimsConstants.ALLIANCEADMIN_ROLENAME);

	            for (Iterator it = query.iterate(); it.hasNext();)
	            {
	                sysRole = (AimsSysRole) it.next();
	            }
	            roleSet.add(sysRole);
	            //roleSet.add((AimsSysRole) session.load(AimsSysRole.class, new Long(2)));
	            
	            aimsUser.setRoles(roleSet); //Setting Roles in Users/Role Mapping Table
	            aimsUser.setAimsContact(aimsContact); //Setting Contacts Info in Users Table
	            aimsUser.setAimsAllianc(aimsAllianc.getAllianceId()); //Setting Alliance Info in Users Table
	            session.saveOrUpdate(aimsUser);
	            
	            //Setting vendor ID
	            ConOra = session.connection();
	            vendorId = AllianceManager.generateVendorId(aimsAllianc.getAllianceId(),ConOra);
	            aimsAllianc.setVendorId(vendorId);
	           
	            session.flush();  
	             tx.commit();

	        }

	        catch (JDBCException je)
	        {
	            if (tx != null)
	                tx.rollback();

	            /*String exMessage = je.getMessage();
	            UniqueConstraintException uce = new UniqueConstraintException();

	            for (int i = 0; i < AimsConstants.AIMS_ALLIANCE_REGISTRATION_UNIQUE_KEYS.length / 2; i++)
	            {
	                Object[] objs = { AimsConstants.AIMS_ALLIANCE_REGISTRATION_UNIQUE_KEYS[i * 2] };

	                if (exMessage.indexOf(AimsConstants.UNIQUE_CONSTRAINT_MESSAGE.format(objs)) > -1)
	                {
	                    uce.setMessageKey(AimsConstants.AIMS_ALLIANCE_REGISTRATION_UNIQUE_KEYS[(i * 2) + 1]);
	                    log.debug("UCE GETMESSAGEKEY: " + uce.getMessageKey());
	                    throw uce;
	                }
	            }*/
	            log.error("JMAAllianceRegistrationManager.saveOrUpdateAlliance: Duplicate company or user name found, rasing exception..");
	            je.printStackTrace();
	            AimsException aimsException = new AimsException("Error");
	            if (DBErrorFinder.searchUniqueConstraintErrorsWithSchemaName(je.getCause().toString(), AimsConstants.AIMS_ALLIANCE_REGISTRATION_UNIQUE_KEYS_DB, aimsException)) {	                
	            	throw aimsException;
	            }
	        } catch (AimsException aimsEx){
	        	aimsEx.printStackTrace();
		            if (tx != null)
		                tx.rollback();
	        	throw aimsEx;
	        
	        }
	        catch (HibernateException e)
	        {
	            if (tx != null)
	                tx.rollback();
	            e.printStackTrace();
	            throw e;
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	            if (tx != null)
	                tx.rollback();
	            throw new HibernateException(ex);
	        }

	        finally
	        {
	            session.close();
	            try
	            {
	                if (statement != null)
	                    statement.close();
	            }
	            catch (Exception ex)
	            {}
	            log.debug("Session closed in saveOrUpdateAlliance()");
	        }
	    
	 }


	 public static Collection getAllIndustyVerticals() throws HibernateException
	    {
	        Collection collection = null;
	        Session session = null;
	        Object[] userValues = null;
	        StringBuffer queryStringBuffer = new StringBuffer();
	        //AimsFinancingOptions aimsFinancingOption = null;
	        AimsIndustryFocu aimsIndustryFocu=null;
	        Collection allindFoucList = new ArrayList();

	        try
	        {

	            queryStringBuffer
	                .append("select ")
	                .append("       aimsIndustryFocu.industryId, aimsIndustryFocu.industryName,aimsIndustryFocu.emailAddress ")
	                .append("from ")
	                .append("       com.netpace.aims.model.masters.AimsIndustryFocu aimsIndustryFocu ")
	                .append("order by  ")
	                .append("      aimsIndustryFocu.industryId ");
	            session = DBHelper.getInstance().getSession();

	            collection = session.find(queryStringBuffer.toString());

	            for (Iterator iter = collection.iterator(); iter.hasNext();)
	            {
	            	aimsIndustryFocu = new AimsIndustryFocu();

	                userValues = (Object[]) iter.next();

	                aimsIndustryFocu.setIndustryId((Long) userValues[0]);
	                aimsIndustryFocu.setIndustryName((String) userValues[1]);
	                aimsIndustryFocu.setEmailAddress((String) userValues[2]);
	                allindFoucList.add(aimsIndustryFocu);
	            }

	            log.debug("No of records returned: " + allindFoucList .size());

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

	        return allindFoucList ;
	    }

	 public static void saveOrUpdateJmaAlliance(boolean isSubmit,boolean isResubmit,AimsJMAAlliance aimsJmaAlliance,
			Long allianceId,
			boolean saveTechContact, 
			boolean saveSalesContact,
			AimsContact techContact, 
			AimsContact salesContact,
			Long[] allianceCarrierIds,
			String commentsAllianceWithOtherCarriers,
			Long[] assignedIndFocusIds,
			Long[] assignedTopIndFocusIds,
			Long productPresentaionTempFileId,
			Long winOpportunitiesTempFileId,
			String userId
			) throws UniqueConstraintException,
			HibernateException
	 {



	        Session session = null;
	        Transaction tx = null;
	        Connection ConOra = null;
	        CallableStatement statement = null;
	        Long vendorId = new Long(0);
	        String ALLIANCE_PRODUCT_PRESENTATION_BLOB_DB_INFO[] = { "prod_presentation", "aims_alliance_jma", "alliance_id" };
	        String ALLIANCE_WIN_OPPORTUNITIES_BLOB_DB_INFO[] = { "win_opportunities", "aims_alliance_jma", "alliance_id" };
	   	
	      

	        //Long allianceId = null;
	        
	        try
	        {
	            session = DBHelper.getInstance().getSession();
	            tx = session.beginTransaction();
	           
	            //Saving AimsContact
	            session.saveOrUpdate(aimsJmaAlliance);
	           
	            session.flush();
	            
	            /*
	             * Code for save alliance carriers
	             */
	            AimsAllianceCarriers aimsAllianceCarrier = null;
	            AimsCarriers aimsCarrier = null;
	            int delCount = 0;
	            if(allianceCarrierIds!=null && allianceCarrierIds.length>0)
	            {
	                //delete previous AimsAllianceCarriers
	                delCount =
	                    session.delete(
	                        "from com.netpace.aims.model.alliance.AimsAllianceCarriers as allianceCarriers where allianceCarriers.allianceId = :allianceId",
	                        allianceId,
	                        new LongType());

	                for (int i = 0; i < allianceCarrierIds.length; i++)
	                {
	                    aimsAllianceCarrier = new AimsAllianceCarriers();
	                    aimsCarrier = new AimsCarriers();
	                    aimsAllianceCarrier.setAllianceId(allianceId);
	                    aimsCarrier.setCarrierId(allianceCarrierIds[i]);
	                    aimsAllianceCarrier.setCarriers(aimsCarrier);
	                    session.save(aimsAllianceCarrier);
	                }
	            }
	            AimsAllianc alliance = (AimsAllianc) session.load(AimsAllianc.class, allianceId);
	            alliance.setOtherCarrierAlliances(commentsAllianceWithOtherCarriers);
	            //If submit request than update complete flag in DB
	            if(isSubmit || isResubmit)
	            	alliance.setIsJmaInfoComplete("Y");
	            else
	            	alliance.setIsJmaInfoComplete("N");
	            
	            
	            /**
	             *Code for saving industry verticals 
	             */
	            AimsAllianceIndFocus allianceIndFocus = null;
	            delCount =
	                session.delete(
	                    "from com.netpace.aims.model.alliance.AimsAllianceIndFocus as allianceIndFocus where allianceIndFocus.allianceId = :allianceId",
	                    allianceId,
	                    new LongType());

	            if (assignedIndFocusIds != null)
	            {
	                for (int i = 0; i < assignedIndFocusIds.length; i++)
	                {
	                    allianceIndFocus = new AimsAllianceIndFocus();
	                    allianceIndFocus.setAllianceId(allianceId);
	                    allianceIndFocus.setIndustryFocus((AimsIndustryFocu) session.load(AimsIndustryFocu.class, assignedIndFocusIds[i]));
	                    session.save(allianceIndFocus);
	                }
	            }
	            
	            /**
	             *Code for saving Top industry verticals 
	             */
	            AimsAllianceTopIndFocus allianceTopIndFocus = null;
	            delCount =
	                session.delete(
	                    "from com.netpace.aims.model.alliance.AimsAllianceTopIndFocus as allianceTopIndFocus where allianceTopIndFocus.allianceId = :allianceId",
	                    allianceId,
	                    new LongType());

	            if (assignedTopIndFocusIds != null)
	            {
	                for (int i = 0; i < assignedTopIndFocusIds.length; i++)
	                {
	                	allianceTopIndFocus = new AimsAllianceTopIndFocus();
	                	allianceTopIndFocus.setAllianceId(allianceId);
	                    allianceTopIndFocus.setIndustryFocus((AimsIndustryFocu) session.load(AimsIndustryFocu.class, assignedTopIndFocusIds[i]));
	                    session.save(allianceTopIndFocus);
	                }
	            }
	            
	            /**
	             * Saving product presentation and win opportunities in DB 
	             */
	            
	            ConOra = session.connection();
	            //Copying Images From Temp Table (if present)
	             TempFilesManager.copyImageFromTemp(ConOra,productPresentaionTempFileId,allianceId, userId,ALLIANCE_PRODUCT_PRESENTATION_BLOB_DB_INFO);
	             TempFilesManager.copyImageFromTemp(ConOra,winOpportunitiesTempFileId,allianceId, userId,ALLIANCE_WIN_OPPORTUNITIES_BLOB_DB_INFO);
		            
		         
	            
	            
	            //Save if not exist
	            if(saveTechContact)
	            {
	            	if(alliance.getTechContact()==null)
	            	{
			            session.saveOrUpdate(techContact);
			            alliance.setTechContact(techContact.getContactId());
	            	}
	            	else
	            	{
	            		techContact.setContactId(alliance.getTechContact());
	            		session.update(techContact);
	            	}
		       
	            }
	            
	            if(saveSalesContact)
	            {
	            	if(alliance.getAimsContactBySalesContactId()==null)
	            	{
	            		session.saveOrUpdate(salesContact);
	            		alliance.setAimsContactBySalesContactId(salesContact.getContactId());
	            	}
	            	else
	            	{
	            		salesContact.setContactId(alliance.getAimsContactBySalesContactId());
	            		session.update(salesContact);
	            		
	            	}
	            		
		        }
	        
	            //Is resubmit the change the status of alliance to submitted
	            if(isResubmit)
	            	alliance.setStatus(AimsConstants.ALLIANCE_STATUS_SUBMITTED);
	            
	            session.update(alliance);
	            tx.commit();

	        }

	        catch (JDBCException je)
	        {
	            if (tx != null)
	                tx.rollback();

	            String exMessage = je.getMessage();
	            UniqueConstraintException uce = new UniqueConstraintException();

	            for (int i = 0; i < AimsConstants.AIMS_ALLIANCE_REGISTRATION_UNIQUE_KEYS.length / 2; i++)
	            {
	                Object[] objs = { AimsConstants.AIMS_ALLIANCE_REGISTRATION_UNIQUE_KEYS[i * 2] };

	                if (exMessage.indexOf(AimsConstants.UNIQUE_CONSTRAINT_MESSAGE.format(objs)) > -1)
	                {
	                    uce.setMessageKey(AimsConstants.AIMS_ALLIANCE_REGISTRATION_UNIQUE_KEYS[(i * 2) + 1]);
	                    log.debug("UCE GETMESSAGEKEY: " + uce.getMessageKey());
	                    throw uce;
	                }
	            }

	            je.printStackTrace();
	            throw new HibernateException(je);
	        }
	        catch (HibernateException e)
	        {
	            if (tx != null)
	                tx.rollback();
	            e.printStackTrace();
	            throw e;
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	            if (tx != null)
	                tx.rollback();
	            throw new HibernateException(ex);
	        }

	        finally
	        {
	            session.close();
	            try
	            {
	                if (statement != null)
	                    statement.close();
	            }
	            catch (Exception ex)
	            {}
	        }
	    
	 
	 }
	 
	 public static AimsJMAAlliance getJMAAllianceById(Long allinaceId)throws HibernateException {
		 
		 AimsJMAAlliance jmaAlliance=null;
		 try {
			jmaAlliance= (AimsJMAAlliance) DBHelper.getInstance().load(AimsJMAAlliance.class, allinaceId.toString());
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		}
		 
		 return jmaAlliance;
	 }
	 
	 /**
	     *  This static method gets the industry focus for the current alliance.
	     */
	    public static Collection getAllianceIndFocus(Long alliance_id, String user_type) throws HibernateException
	    {
	        Collection collection = null;
	        Session session = null;
	        Object[] userValues = null;
	        StringBuffer queryStringBuffer = new StringBuffer();
	        AimsIndustryFocu aimsIndustryFocus = null;
	        Collection AllianceIndFocus = new ArrayList();

	        try
	        {

	            queryStringBuffer
	                .append("select ")
	                .append("		indFocus.industryId, indFocus.industryName ")
	                .append("from ")
	                .append("		com.netpace.aims.model.alliance.AimsAllianceIndFocus allianceIndFocus, ")
	                .append("		com.netpace.aims.model.masters.AimsIndustryFocu indFocus ")
	                .append("where ")
	                .append("		allianceIndFocus.allianceId = :allianceId ")
	                .append("		and allianceIndFocus.industryFocus.industryId = indFocus.industryId ")
	                .append("order by  ")
	                .append("		indFocus.industryName  ");

	            session = DBHelper.getInstance().getSession();

	            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

	            for (Iterator iter = collection.iterator(); iter.hasNext();)
	            {
	                aimsIndustryFocus = new AimsIndustryFocu();

	                userValues = (Object[]) iter.next();

	                aimsIndustryFocus.setIndustryId((Long) userValues[0]);
	                aimsIndustryFocus.setIndustryName((String) userValues[1]);

	                AllianceIndFocus.add(aimsIndustryFocus);
	            }

	            log.debug("No of records returned: " + AllianceIndFocus.size());

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

	        return AllianceIndFocus;
	    }
	    
	    /**
	     *  This static method gets the industry IDs, for the current alliance.
	     */
	    public static Long[] getAllianceIndFocus(Long alliance_id) throws HibernateException
	    {
	        Session session = null;
	        StringBuffer queryStringBuffer = new StringBuffer();
	        Long [] indFocous=null; 
	        try
	        {
	        	session = DBHelper.getInstance().getSession();
				queryStringBuffer
						.append(" select i.industryFocus.industryId ")
						.append(" 	from AimsAllianceIndFocus as i ")
						.append(" where i.allianceId = :id ");

				Query query = session.createQuery(queryStringBuffer.toString());				
				query.setLong("id", alliance_id.longValue());			
				
				
				List resultValues = query.list();
				
				if (resultValues != null && resultValues.size()>0){
					indFocous=new Long[resultValues.size()];
					for (int i=0; i<resultValues.size(); i++){
						indFocous[i]=new Long(resultValues.get(i).toString());
					}
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

	        return indFocous;
	    }
	    
	    /**
	     *  This static method gets the Top industry IDs for the current alliance.
	     */
	    public static Long[] getAllianceTopIndFocus(Long alliance_id) throws HibernateException
	    {
	        Session session = null;
	        StringBuffer queryStringBuffer = new StringBuffer();
	        Long [] indFocous=null; 
	        try
	        {
	        	session = DBHelper.getInstance().getSession();
				queryStringBuffer
						.append(" select i.industryFocus.industryId ")
						.append(" 	from AimsAllianceTopIndFocus as i ")
						.append(" where i.allianceId = :id ");

				Query query = session.createQuery(queryStringBuffer.toString());
				query.setLong("id", alliance_id.longValue());			
				
				
				List resultValues = query.list();
				
				if (resultValues != null && resultValues.size()>0){
					indFocous=new Long[resultValues.size()];
					for (int i=0; i<resultValues.size(); i++){
						indFocous[i]=new Long(resultValues.get(i).toString());
					}
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

	        return indFocous;
	    }
	    
	    public static Long[] getAllianceCarreirs(Long alliance_id) throws HibernateException
	    {
	        Session session = null;
	        StringBuffer queryStringBuffer = new StringBuffer();
	        Long [] carriers=null; 
	        try
	        {
	        	session = DBHelper.getInstance().getSession();
				queryStringBuffer
						.append(" select c.carriers.carrierId ")
						.append(" 	from AimsAllianceCarriers as c ")
						.append(" where c.allianceId = :id ");

				Query query = session.createQuery(queryStringBuffer.toString());
				query.setLong("id", alliance_id.longValue());			
				
				
				List resultValues = query.list();
				
				if (resultValues != null && resultValues.size()>0){
					carriers=new Long[resultValues.size()];
					for (int i=0; i<resultValues.size(); i++){
						carriers[i]=new Long(resultValues.get(i).toString());
					}
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

	        return carriers;
	    }
	 

}
