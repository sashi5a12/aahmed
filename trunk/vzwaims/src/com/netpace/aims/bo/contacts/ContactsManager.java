package com.netpace.aims.bo.contacts;

import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.util.StringFuncs;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;


public class ContactsManager {

    private static Logger log = Logger.getLogger(ContactsManager.class.getName());

    public static void saveOrUpdateAimsContact(AimsContact aimsContact) throws HibernateException, Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            ContactsManager.saveOrUpdateAimsContact(aimsContact, session);
            tx.commit();
        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
            throw he;
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
                log.debug("Session closed in ContactsManager.saveOrUpdateAimsContact(AimsContact aimsContact)");
            }
        }
    }//end saveOrUpdateAimsContact

    public static void saveOrUpdateAimsContact(AimsContact aimsContact, Session session) throws HibernateException, Exception {
        try {
            session.saveOrUpdate(aimsContact);
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            log.debug("Session will	be closed by the calling method of ContactsManager.saveOrUpdateAimsContact(AimsUserOffer userOffer, Session session)");
        }
    }//end saveOrUpdateAimsContact


    public static AimsContact getAimsContact(Long contactId, Long allianceId) throws HibernateException {

        Session session = null;
        Query query = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsContact aimsContact = null;
        List resultsList = null;
        try {
            session = DBHelper.getInstance().getSession();
                queryStringBuffer
                        .append("select aimsContact from com.netpace.aims.model.core.AimsContact aimsContact ")
                        .append("   where ")
                        .append("   aimsContact.contactId = :contactId ");
            if(allianceId!=null && allianceId.longValue()>0) {
                queryStringBuffer
                        .append("   and aimsContact.alliance.allianceId = :allianceId ");
            }

            query = session.createQuery(queryStringBuffer.toString());
            query.setLong("contactId", contactId.longValue());
            if(allianceId!=null && allianceId.longValue()>0) {
                query.setLong("allianceId", allianceId.longValue());
            }
            
            resultsList = query.list();
            if(resultsList!=null && resultsList.size()>0) {
                aimsContact = (AimsContact) resultsList.get(0);
            }
            return aimsContact;
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if (session != null) {
                session.close();
                log.debug("Session closed in ContactsManager.getAimsContact()");
            }
        }
    }//end getAimsContact

    public static AimsContact getAimsContactByEmail(String contactEmail, Long allianceId) throws HibernateException {

        Session session = null;
        Query query = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsContact aimsContact = null;
        List resultsList = null;
        try {
            session = DBHelper.getInstance().getSession();
                queryStringBuffer
                        .append("select aimsContact from com.netpace.aims.model.core.AimsContact aimsContact ")
                        .append("   where ")
                        .append("   aimsContact.emailAddress = :contactEmail ");
            if(allianceId!=null && allianceId.longValue()>0) {
                queryStringBuffer
                        .append("   and aimsContact.alliance.allianceId = :allianceId ");
            }

            query = session.createQuery(queryStringBuffer.toString());
            query.setString("contactEmail", contactEmail);
            if(allianceId!=null && allianceId.longValue()>0) {
                query.setLong("allianceId", allianceId.longValue());
            }

            resultsList = query.list();
            if(resultsList!=null && resultsList.size()>0) {
                aimsContact = (AimsContact) resultsList.get(0);
            }
            return aimsContact;
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if (session != null) {
                session.close();
                log.debug("Session closed in ContactsManager.getAimsContactByEmail()");
            }
        }
    }//end getAimsContactByEmail


    /**
     * If ignoreUserContacts is true, this method will return only alliance contacts not user contacts
     * @param allianceId
     * @param ignoreUserContacts    -   if true resultset will not include contacts that are tied to users
     * @param PAGE_LENGTH
     * @param pageNo
     * @param search_expression
     * @return
     * @throws HibernateException
     * @throws Exception
     */
    public static Collection getAllAllianceContacts(Long allianceId, boolean ignoreUserContacts, int PAGE_LENGTH, int pageNo, String search_expression) throws HibernateException, Exception{
        Session session = null;
        Query query = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        List resultsList = null;
        try {
            session = DBHelper.getInstance().getSession();
                queryStringBuffer
                        .append("select aimsContact from com.netpace.aims.model.core.AimsContact aimsContact ")
                        .append("   where ")
                        .append("   aimsContact.alliance.allianceId = :allianceId ");

                if(ignoreUserContacts) {
                queryStringBuffer
                        .append("     AND aimsContact.contactId NOT IN ")
                        .append("                       (SELECT aimsUser.aimsContactId ")
                        .append("                                      FROM com.netpace.aims.model.core.AimsUser aimsUser ")
                        .append("                                     WHERE aimsUser.aimsAllianc = :allianceId )");
                }

                if (search_expression.length() > 0) {
                    queryStringBuffer.append(search_expression);
                }
            queryStringBuffer
                        .append("   order by lower(aimsContact.firstName), lower(aimsContact.lastName) ");

            query = session.createQuery(queryStringBuffer.toString());

            query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (pageNo - 1));

            query.setLong("allianceId", allianceId.longValue());

            resultsList = query.list();

            return resultsList;
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
                log.debug("Session closed in ContactsManager.getAllAllianceContacts()");
            }
        }
    }//end getAllAllianceContacts

    public static Collection getAllAllianceContacts(Long allianceId) throws HibernateException, Exception{
        Session session = null;
        Query query = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        List resultsList = null;
        try {
            session = DBHelper.getInstance().getSession();
                queryStringBuffer
                        .append("select aimsContact from com.netpace.aims.model.core.AimsContact aimsContact ")
                        .append("   where ")
                        .append("   aimsContact.alliance.allianceId = :allianceId ");
                queryStringBuffer
                        .append("   order by lower(aimsContact.firstName), lower(aimsContact.lastName) ");

            query = session.createQuery(queryStringBuffer.toString());

            query.setLong("allianceId", allianceId.longValue());

            resultsList = query.list();
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
                log.debug("Session closed in ContactsManager.getAllAllianceContacts(Long allianceId)");
            }
        }
        return resultsList;
    }//end getAllAllianceContacts

    /**
     * If ignoreUserContacts is true, this method will return only alliance contacts not user contacts
     * @param allianceId
     * @param ignoreUserContacts    -   if true resultset will not include contacts that are tied to users
     * @param search_expression
     * @return
     * @throws HibernateException
     * @throws Exception
     */
    public static int getAllAllianceContactsCount(Long allianceId, boolean ignoreUserContacts, String search_expression) throws HibernateException, Exception{
        Session session = null;
        Query query = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        int allianceContactsCount = 0;

        try {
            session = DBHelper.getInstance().getSession();
                queryStringBuffer
                        .append("select count(*) from com.netpace.aims.model.core.AimsContact aimsContact ")
                        .append("   where ")
                        .append("   aimsContact.alliance.allianceId = :allianceId ");
                if(ignoreUserContacts) {
                queryStringBuffer
                        .append("     AND aimsContact.contactId NOT IN ")
                        .append("                       (SELECT aimsUser.aimsContactId ")
                        .append("                                      FROM com.netpace.aims.model.core.AimsUser aimsUser ")
                        .append("                                     WHERE aimsUser.aimsAllianc = :allianceId )");
                }
            if (search_expression.length() > 0) {
                  queryStringBuffer.append(search_expression);
            }
            queryStringBuffer
                        .append("   order by lower(aimsContact.firstName), lower(aimsContact.lastName) ");

            query = session.createQuery(queryStringBuffer.toString());
            query.setLong("allianceId", allianceId.longValue());

            allianceContactsCount = ( (Integer)query.iterate().next()).intValue();

            return allianceContactsCount;
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
                log.debug("Session closed in ContactsManager.getAllAllianceContactsCount()");
            }
        }
    }//end getAllAllianceContacts

    /**
     * This method returns all vzw contacts (both active and deleted vzw users) if userAccountStatus argument is null.
     * Otherwise it will return vzw contacts according to given status
     * @param userAccountStatus
     * @return
     * @throws HibernateException
     * @throws Exception
     */
    public static Collection getAllVZWContacts(String userAccountStatus) throws HibernateException, Exception {
        Collection collection = null;
        Session session = null;
        try {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();

            queryString
                .append("   select  distinct contact ")
                .append("   from    com.netpace.aims.model.core.AimsContact	contact ")
                .append("   where   contact.contactId in ")
                .append("       ( ")
                .append("           select  distinct user.aimsContact ")
                .append("           from    com.netpace.aims.model.core.AimsUser user ")
                .append("           where ");

                queryString.append("        user.aimsAllianc is	null ");
                queryString.append("        and user.userType = 'V' ");
            if(!StringFuncs.isNullOrEmpty(userAccountStatus)) {
                queryString.append("        and user.userAccountStatus = :userAccountStatus ");
            }
                queryString.append(" ) ");

            queryString.append("   order by lower(contact.firstName), lower(contact.lastName) ");

            Query query = session.createQuery(queryString.toString());

            if(!StringFuncs.isNullOrEmpty(userAccountStatus)) {
                query.setString("userAccountStatus", userAccountStatus);
            }

            collection = query.list();

            log.debug("No	of VZW contacts returned: " + collection.size());
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in ContactsManager.getAllVZWContacts()");
            }
        }
        return collection;
    }//end getAllVZWContacts
}
