package com.netpace.aims.bo.alliance;

import java.util.Collection;
import java.util.Date;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.util.AimsConstants;

/**
 * This class is responsible for getting the BO for alliance contacts. 
 * @author Rizwan Qazi
 */
public class AllianceContactInfoManager
{

    static Logger log = Logger.getLogger(AllianceContactInfoManager.class.getName());

    /**
     *  This static method gets the company information details of the current alliance   
     */
    public static Collection getAllianceContactInfo(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		execContact.firstName, execContact.lastName, ")
                .append("		execContact.title, execContact.phone, ")
                .append("		execContact.emailAddress, execContact.fax, execContact.mobile,")
                .append("		busContact.firstName, busContact.lastName, ")
                .append("		busContact.title, busContact.phone, ")
                .append("		busContact.emailAddress, busContact.fax, busContact.mobile,")
                .append("		mktgContact.firstName, mktgContact.lastName, ")
                .append("		mktgContact.title, mktgContact.phone, ")
                .append("		mktgContact.emailAddress, mktgContact.fax, mktgContact.mobile,")
                .append("		techContact.firstName, techContact.lastName, ")
                .append("		techContact.title, techContact.phone, ")
                .append("		techContact.emailAddress, techContact.fax, techContact.mobile, ")
                .append("		alliance.execContact, alliance.vzwBusinessContact, ")
                .append("		alliance.mktgPrContact, alliance.techContact, alliance.companyName, ")
                .append("       alliance.escalationInstructions ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance,  ")
                .append("		com.netpace.aims.model.core.AimsContact execContact, ")
                .append("		com.netpace.aims.model.core.AimsContact busContact, ")
                .append("		com.netpace.aims.model.core.AimsContact mktgContact, ")
                .append("		com.netpace.aims.model.core.AimsContact techContact ")
                .append("where ")
                .append("		alliance.allianceId = :allianceId ")
                .append("		and alliance.execContact = execContact.contactId (+) ")
                .append("		and alliance.vzwBusinessContact = busContact.contactId (+) ")
                .append("		and alliance.mktgPrContact = mktgContact.contactId (+) ")
                .append("		and alliance.techContact = techContact.contactId (+) ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            log.debug("No of records returned: " + collection.size());

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

        return collection;
    }

    /**
    *  This static method updates the contact information for a given alliance.   
    */
    public static void UpdateAllianceContactInfo(
        Long allianceId,
        String execContactId,
        String busContactId,
        String mktgContactId,
        String techContactId,
        String execContactFirstName,
        String execContactLastName,
        String execContactTitle,
        String execContactPhone,
        String execContactEmailAddress,
        String execContactFax,
        String execContactMobile,
        String busContactFirstName,
        String busContactLastName,
        String busContactTitle,
        String busContactPhone,
        String busContactEmailAddress,
        String busContactFax,
        String busContactMobile,
        String mktgContactFirstName,
        String mktgContactLastName,
        String mktgContactTitle,
        String mktgContactPhone,
        String mktgContactEmailAddress,
        String mktgContactFax,
        String mktgContactMobile,
        String techContactFirstName,
        String techContactLastName,
        String techContactTitle,
        String techContactPhone,
        String techContactEmailAddress,
        String techContactFax,
        String techContactMobile,
        String escalationInstructions,
        String currUserName)
        throws AimsException, HibernateException
    {

        Session session = null;
        Transaction tx = null;
        AimsContact contact = null;

        log.debug("*** This is the execContactId " + execContactId);
        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            log.debug("*** This is the allianceId " + allianceId);
            AimsAllianc alliance = (AimsAllianc) session.load(AimsAllianc.class, allianceId);

            if (!(execContactId.equals("0")))
            {
                alliance.setExecContact(new Long(execContactId));
            }
            else
            {
                log.debug("*** Goes in else " + execContactId);
                contact = new AimsContact();
                contact.setFirstName(execContactFirstName);
                contact.setLastName(execContactLastName);
                contact.setTitle(execContactTitle);
                contact.setPhone(execContactPhone);
                contact.setEmailAddress(execContactEmailAddress);
                contact.setFax(execContactFax);
                contact.setMobile(execContactMobile);
                contact.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);
                contact.setCreatedBy(currUserName);
                contact.setCreatedDate(new Date());
                contact.setLastUpdatedBy(currUserName);
                contact.setLastUpdatedDate(new Date());

                session.save(contact);
                alliance.setExecContact(contact.getContactId());
                log.debug("*** gets new exec contact id " + contact.getContactId());
            }

            if (!busContactId.equals("0"))
            {
                alliance.setVzwBusinessContact(new Long(busContactId));
            }
            else
            {
                contact = new AimsContact();
                contact.setFirstName(busContactFirstName);
                contact.setLastName(busContactLastName);
                contact.setTitle(busContactTitle);
                contact.setPhone(busContactPhone);
                contact.setEmailAddress(busContactEmailAddress);
                contact.setFax(busContactFax);
                contact.setMobile(busContactMobile);
                contact.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);
                contact.setCreatedBy(currUserName);
                contact.setCreatedDate(new Date());
                contact.setLastUpdatedBy(currUserName);
                contact.setLastUpdatedDate(new Date());

                session.save(contact);
                alliance.setVzwBusinessContact(contact.getContactId());
            }

            if (!mktgContactId.equals("0"))
            {
                alliance.setMktgPrContact(new Long(mktgContactId));
            }
            else
            {
                contact = new AimsContact();
                contact.setFirstName(mktgContactFirstName);
                contact.setLastName(mktgContactLastName);
                contact.setTitle(mktgContactTitle);
                contact.setPhone(mktgContactPhone);
                contact.setEmailAddress(mktgContactEmailAddress);
                contact.setFax(mktgContactFax);
                contact.setMobile(mktgContactMobile);
                contact.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);
                contact.setCreatedBy(currUserName);
                contact.setCreatedDate(new Date());
                contact.setLastUpdatedBy(currUserName);
                contact.setLastUpdatedDate(new Date());

                session.save(contact);
                alliance.setMktgPrContact(contact.getContactId());
            }

            if (!techContactId.equals("0"))
            {
                alliance.setTechContact(new Long(techContactId));
            }
            else
            {
                contact = new AimsContact();
                contact.setFirstName(techContactFirstName);
                contact.setLastName(techContactLastName);
                contact.setTitle(techContactTitle);
                contact.setPhone(techContactPhone);
                contact.setEmailAddress(techContactEmailAddress);
                contact.setFax(techContactFax);
                contact.setMobile(techContactMobile);
                contact.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);
                contact.setCreatedBy(currUserName);
                contact.setCreatedDate(new Date());
                contact.setLastUpdatedBy(currUserName);
                contact.setLastUpdatedDate(new Date());

                session.save(contact);
                alliance.setTechContact(contact.getContactId());
            }

            alliance.setEscalationInstructions(escalationInstructions);
            alliance.setLastUpdatedDate(new Date());
            alliance.setLastUpdatedBy(currUserName);

            session.update(alliance);

            tx.commit();
        }

        catch (JDBCException je)
        {
            if (tx!=null)
                tx.rollback();

            String exMessage = je.getMessage();
            AimsException ae = new AimsException();

            for (int i = 0; i < AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS.length / 2; i++)
            {
                Object[] objs = { AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS[i * 2] };

                if (exMessage.indexOf(AimsConstants.UNIQUE_CONSTRAINT_MESSAGE.format(objs)) > -1)
                {
                    ae.setMessageKey(AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS[(i * 2) + 1]);

                    log.debug("ae GETMESSAGEKEY: " + ae.getMessageKey());
                    throw ae;
                }
                else
                {
                    je.printStackTrace();
                    throw new HibernateException(je);
                }
            }

        }

        catch (HibernateException e)
        {

            if (tx != null)
            {

                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }

        finally
        {
            session.close();
        }

    }

    /**
    *  This static method only updates the contact information for a given alliance.
    */
    public static void UpdateAllianceContactInfo(
            Long allianceId,
            String execContactId,
            String busContactId,
            String mktgContactId,
            String techContactId,
            String currUserName)
        throws AimsException, HibernateException
    {

        Session session = null;
        Transaction tx = null;

        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            log.debug("*** This is the allianceId " + allianceId);
            AimsAllianc alliance = (AimsAllianc) session.load(AimsAllianc.class, allianceId);

            if (!(execContactId.equals("0")))
            {
                alliance.setExecContact(new Long(execContactId));
            }

            if (!busContactId.equals("0"))
            {
                alliance.setVzwBusinessContact(new Long(busContactId));
            }


            if (!mktgContactId.equals("0"))
            {
                alliance.setMktgPrContact(new Long(mktgContactId));
            }

            if (!techContactId.equals("0"))
            {
                alliance.setTechContact(new Long(techContactId));
            }

            alliance.setLastUpdatedDate(new Date());
            alliance.setLastUpdatedBy(currUserName);

            session.update(alliance);

            tx.commit();
        }

        catch (JDBCException je)
        {
            if (tx!=null)
                tx.rollback();

            String exMessage = je.getMessage();
            AimsException ae = new AimsException();

            for (int i = 0; i < AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS.length / 2; i++)
            {
                Object[] objs = { AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS[i * 2] };

                if (exMessage.indexOf(AimsConstants.UNIQUE_CONSTRAINT_MESSAGE.format(objs)) > -1)
                {
                    ae.setMessageKey(AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS[(i * 2) + 1]);

                    log.debug("ae GETMESSAGEKEY: " + ae.getMessageKey());
                    throw ae;
                }
                else
                {
                    je.printStackTrace();
                    throw new HibernateException(je);
                }
            }

        }

        catch (HibernateException e)
        {

            if (tx != null)
            {

                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }

        finally
        {
            session.close();
        }

    }

    public static Collection getContacts(Long allianceId) throws HibernateException
    {

        Collection collection = null;
        Object[] userValues = null;

        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            /*queryStringBuffer
                .append("	select ")
                .append("		distinct contact ")
                .append(" from ")
                .append("		com.netpace.aims.model.core.AimsContact contact ")
                .append(" where		")
                .append("		contact.contactId in ( ")
                .append("								select ")
                .append(" 								user.aimsContact.contactId ")
                .append(" 							from ")
                .append("									com.netpace.aims.model.core.AimsUser user ")
                .append(" 							where ")
                .append("									user.aimsAllianc = :allianceId ")
                .append("									and user.userAccountStatus = 'ACTIVE' ")
                .append("	                          ) ");
            */
            queryStringBuffer
                .append("	select ")
                .append("		contact ")
                .append(" from ")
                .append("		com.netpace.aims.model.core.AimsContact contact ")
                .append(" where		")
                .append("		contact.alliance.allianceId = :allianceId ");

            collection = session.find(queryStringBuffer.toString(), allianceId, new LongType());

            log.debug("No of records returned: " + collection.size());
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

        return collection;
    }

}