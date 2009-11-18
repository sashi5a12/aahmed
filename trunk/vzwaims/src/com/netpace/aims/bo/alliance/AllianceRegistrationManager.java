package com.netpace.aims.bo.alliance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.masters.AimsCarriers;
import com.netpace.aims.model.masters.AimsFinancingOptions;
import com.netpace.aims.model.masters.AimsVzwReasons;
import com.netpace.aims.model.alliance.*;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsNotifOptInRecipientsLite;
import com.netpace.aims.model.security.AimsSysRole;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;

/**
 * This class is responsible for getting the BO for devices.
 * It has static methods for getting the devices.
 * @author Rizwan Qazi
 */
public class AllianceRegistrationManager
{

    static Logger log = Logger.getLogger(AllianceRegistrationManager.class.getName());

    public static void saveOrUpdateAlliance(AimsUser aimsUser, AimsUser techUser,
    										AimsAllianc aimsAllianc, 
    										AimsContact aimsContact, AimsContact techContact,
                                            boolean sameUserTechContact,
                                            Long companyLogoTempFileId,
                                            Long companyPresentationTempFileId)

        throws UniqueConstraintException, HibernateException
    {
        Session session = null;
        Transaction tx = null;
        Connection ConOra = null;
        CallableStatement statement = null;
        Long vendorId = new Long(0);
        String currUserName = aimsUser.getUsername();
        String ALLIANCE_COMPANY_PRESENTATION_BLOB_DB_INFO[] = { "company_present", "aims_alliances", "alliance_id" };
        String ALLIANCE_COMPANY_LOGO_BLOB_DB_INFO[] = { "company_logo", "aims_alliances", "alliance_id" };


        Long allianceId = null;

        Set allianceCarriers = null;
        AimsAllianceCarriers aimsAllianceCarrier = null;

        Set allianceFinancingOptions = null;
        AimsAllianceFinancing aimsAllianceFinancing = null;

        Set allianceDevelopmentTechnologies = null;
        AimsAllianceDevTech aimsAllianceDevTech = null;

        Set allianceDevelopments = null;
        AimsAllianceDevelopments allianceDevelopment = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            //Check that the email does not already exists
            /*Query userQry= session.createQuery("from AimsUser u where lower(u.username)=:email");            
            userQry.setString("email", aimsUser.getUsername().toLowerCase());
            List list=userQry.list();
            if (list != null && list.size()>0){
            	UniqueConstraintException uce = new UniqueConstraintException();
            	uce.setMessageKey("error.email.UsernameAlreadyExist");
            	throw uce;
            }
            
            list=null;
            userQry.setString("email", techUser.getUsername().toLowerCase());
            list=userQry.list();
            if (list != null && list.size()>0){
            	UniqueConstraintException uce = new UniqueConstraintException();
            	uce.setMessageKey("error.email.TechUsernameAlreadyExist");
            	throw uce;
            }*/
            
            //Savign AimsContact
            session.saveOrUpdate(aimsContact);
            
            //Saving 24x7 Technical contact
            if(!sameUserTechContact) {
                //if username and techcontact email are not same then create contact
                session.saveOrUpdate(techContact);
            }

            //Saving AimsUser
            session.saveOrUpdate(aimsUser);
            
            //Saving 24x7 Technical User
            //session.saveOrUpdate(techUser);

            //Opting-in the user created to the 'ALLIANCE_ACCEPTED' Event (By Default).
//            AimsNotifOptInRecipientsLite notifOptIn = new AimsNotifOptInRecipientsLite();
//            notifOptIn.setUserId(aimsUser.getUserId());
//            notifOptIn.setNotificationId(new Long(AimsNotificationConstants.NOTIFICATION_ALLIANCE_ACCEPTED));
//            session.saveOrUpdate(notifOptIn);

            //Saving AimsAlliance
            aimsAllianc.setAimsUserByAdminUserId(aimsUser.getUserId());

            aimsContact.setAlliance(aimsAllianc);
            session.saveOrUpdate(aimsContact);

            if(!sameUserTechContact) {
                //if username and techcontact email are not same then contact id in alliance
                aimsAllianc.setTechContact(techContact.getContactId());
                session.saveOrUpdate(aimsAllianc);

                techContact.setAlliance(aimsAllianc);
                session.saveOrUpdate(techContact);
            }
            else {
                log.debug("Username and TechContact Email are same, setting user contact id in alliance");
                //else set user contact id in alliance
                aimsAllianc.setTechContact(aimsContact.getContactId());
                session.saveOrUpdate(aimsAllianc);
            }

            allianceId = aimsAllianc.getAllianceId();

            /*allianceCarriers = aimsAllianc.getAimsCarriers();
            Iterator carriersItr = allianceCarriers.iterator();
            while(carriersItr.hasNext()) {
                aimsAllianceCarrier = (AimsAllianceCarriers) carriersItr.next();
                aimsAllianceCarrier.setAllianceId(allianceId);
                session.save(aimsAllianceCarrier);
            }*/

            /*allianceFinancingOptions = aimsAllianc.getAimsFinancingOptions();
            Iterator financingItr = allianceFinancingOptions.iterator();
            while(financingItr.hasNext()) {
                aimsAllianceFinancing = (AimsAllianceFinancing) financingItr.next();
                aimsAllianceFinancing.setAllianceId(allianceId);
                session.save(aimsAllianceFinancing);
            }*/ 

/*            allianceDevelopmentTechnologies = aimsAllianc.getAimsDevelopmentTechnologies();
            Iterator allianceDevTechItr = allianceDevelopmentTechnologies.iterator();
            while(allianceDevTechItr.hasNext()) {
                aimsAllianceDevTech = (AimsAllianceDevTech) allianceDevTechItr.next();
                aimsAllianceDevTech.setAllianceId(allianceId);
                session.save(aimsAllianceDevTech);
            }
*/
            /*allianceDevelopments = aimsAllianc.getAimsDevelopments();
            Iterator allianceDevelopmentItr = allianceDevelopments.iterator();
            while(allianceDevelopmentItr.hasNext()) {
                allianceDevelopment = (AimsAllianceDevelopments) allianceDevelopmentItr.next();
                allianceDevelopment.setAllianceId(allianceId);
                session.save(allianceDevelopment);
            }*/

            aimsAllianc.setAimsCarriers(null);
            
            aimsAllianc.setAimsFinancingOptions(null);
            aimsAllianc.setAimsDevelopmentTechnologies(null);
            aimsAllianc.setAimsDevelopments(null);

            session.flush();

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
            
            Set secondaryRoleSet=new HashSet();
            AimsSysRole secondaryRole=new AimsSysRole();
            secondaryRole.setRoleId(AimsConstants.SECONDARY_USERS_ROLE_ID);
            secondaryRoleSet.add(secondaryRole);

            /******************** no need to save tech user, only tech contact will be saved ************/
            techUser.setRoles(secondaryRoleSet);
            techUser.setAimsContact(techContact);//todo code for same username and techcontact
            techUser.setAimsAllianc(aimsAllianc.getAllianceId());
            //session.saveOrUpdate(techUser);

            session.flush();
            log.debug("After flush called.");
            ConOra = session.connection();

            //Copying Images From Temp Table (if present)
            TempFilesManager.copyImageFromTemp(ConOra, companyLogoTempFileId, aimsAllianc.getAllianceId(), "system", ALLIANCE_COMPANY_LOGO_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                companyPresentationTempFileId,
                aimsAllianc.getAllianceId(),
                "system",
                ALLIANCE_COMPANY_PRESENTATION_BLOB_DB_INFO);

            tx.commit();


            //After Commit, Update the 'Vendor ID' via the sequence (SEQ_VENDOR_IDS)            
            statement = null;
            try
            {
                statement = ConOra.prepareCall("call AIMS_ALLIANCES_PKG.generate_vendor_id(?,?)");
                statement.setLong(1, aimsAllianc.getAllianceId().longValue());
                statement.registerOutParameter(2, java.sql.Types.INTEGER);
                statement.execute();
                vendorId = new Long(statement.getInt(2));
            }
            catch (Exception ex)
            {
                //TODO:Rizwan This should not happen. Common Function 
            }
            aimsAllianc.setVendorId(vendorId);
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
        catch (UniqueConstraintException uce){
        	uce.setMessageKey("error.alliance_registration.company_name.exists") ;
        	if (tx != null)
                tx.rollback();
        	throw uce;
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

    public static void saveOrUpdateMusicAlliance(
        AimsUser aimsUser,
        AimsAllianc aimsAllianc,
        AimsContact aimsContact,
        AimsAllianceMusic aimsAllianceMusic,
        Collection productTypeSet)
        throws UniqueConstraintException, HibernateException
    {

        Session session = null;
        Transaction tx = null;
        Connection ConOra = null;
        CallableStatement statement = null;
        Long vendorId = new Long(0);

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            //Check that the email does not already exists
            Query userQry= session.createQuery("from AimsUser u where lower(u.username)=:email");            
            userQry.setString("email", aimsUser.getUsername().toLowerCase());
            List list=userQry.list();
            if (list != null && list.size()>0){
            	UniqueConstraintException uce = new UniqueConstraintException();
            	uce.setMessageKey("error.email.UsernameAlreadyExist");
            	throw uce;
            }

            //Savign AimsContact
            session.saveOrUpdate(aimsContact);

            //Saving AimsUser
            session.saveOrUpdate(aimsUser);

            /*
            //Opting-in the user created to the 'ALLIANCE_ACCEPTED' Event (By Default).
            AimsNotifOptInRecipientsLite notifOptIn = new AimsNotifOptInRecipientsLite();
            notifOptIn.setUserId(aimsUser.getUserId());
            notifOptIn.setNotificationId(new Long(AimsNotificationConstants.NOTIFICATION_ALLIANCE_ACCEPTED));
            session.save(notifOptIn);
            */

            //Saving AimsAlliance
            aimsAllianc.setAimsUserByAdminUserId(aimsUser.getUserId());
            aimsAllianc.setIsVcastMusicAlliance("Y");
            aimsAllianc.setVzwBusinessContact(aimsContact.getContactId());
            session.saveOrUpdate(aimsAllianc);

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

            //aimsUser.setRoles(roleSet); //Setting Roles in Users/Role Mapping Table
            aimsUser.setAimsContact(aimsContact); //Setting Contacts Info in Users Table
            aimsUser.setAimsAllianc(aimsAllianc.getAllianceId()); //Setting Alliance Info in Users Table
            session.saveOrUpdate(aimsUser);

            aimsAllianceMusic.setAllianceId(aimsAllianc.getAllianceId());
            session.save(aimsAllianceMusic);

            //session.flush();

            for (Iterator it = productTypeSet.iterator(); it.hasNext();)
            {
                AimsAllianceMusicProdType aimsAllianceMusicProdType = (AimsAllianceMusicProdType) it.next();
                aimsAllianceMusicProdType.setAllianceId(aimsAllianc.getAllianceId());
                session.save(aimsAllianceMusicProdType);
            }

            //aimsAllianceMusic.setMusicProductTypeMap((Set) productTypeSet);
            //session.saveOrUpdate(aimsAllianceMusic);

            tx.commit();

            //After Commit, Update the 'Vendor ID' via the sequence (SEQ_VENDOR_IDS)
            ConOra = session.connection();
            statement = null;
            try
            {
                statement = ConOra.prepareCall("call AIMS_ALLIANCES_PKG.generate_vendor_id(?,?)");
                statement.setLong(1, aimsAllianc.getAllianceId().longValue());
                statement.registerOutParameter(2, java.sql.Types.INTEGER);
                statement.execute();
                vendorId = new Long(statement.getInt(2));
            }
            catch (Exception ex)
            {
                //TODO:Rizwan This should not happen. Common Function 
            }
            aimsAllianc.setVendorId(vendorId);
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
        catch (UniqueConstraintException uce){
        	throw uce;
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

}