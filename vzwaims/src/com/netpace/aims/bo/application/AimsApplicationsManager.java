package com.netpace.aims.bo.application;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.connection.UserSuppliedConnectionProvider;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.contacts.ContactsManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsAppLite;
import com.netpace.aims.model.application.AimsCertification;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.application.AimsAppNetworkUrls;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This	class	is responsible for getting the BO	for	Applications.
 * It	has	static methods for getting the Applications.
 * @author Adnan Makda
 */
public class AimsApplicationsManager
{

    static Logger log = Logger.getLogger(AimsApplicationsManager.class.getName());

    // NEW Function to Fetch Apps
    public static Collection getApps(
        String appTitle,
        Long platformId,
        Long phaseId,
        Long categoryId,
        Long subCategoryId,
        String brewAppType,
        String selectedDeviceIds,
        String permittedPlatformIds,
        String queryType,
        int pageNo,
        int PAGE_LENGTH,
        String userType,
        String sortField,
        String filterField,
        String filterText,
        Long allianceId,
        Long userId,
        Long contactId,
        StringBuffer totalRecords,
        String filterStatuses,
        String filterPlatforms,
        String sortOrder)
        throws AimsException, Exception
    {
        Session session = null;
        Transaction tx = null;
        java.sql.Connection ConOra = null;
        java.sql.CallableStatement statement = null;
        Collection apps = new ArrayList();
        ResultSet results = null;

        try
        {
            /*    
            Database Procedure SnapShot   :
            AIMS_APPS_LIST_PKG.get_results_cursor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                   
            Parameters:
            p_app_title                     IN varchar2,            -- full/partial title of application
            p_platform_id                   IN number,              -- platform id of the application
            p_phase_id                      IN number,              -- phase id of the application
            p_category_id                   IN number,              -- category id of the application
            p_sub_category_id               IN number,              -- sub category id of the application
            p_selected_devices              IN varchar2,            -- comma seperated list of device ids
            p_brew_app_type                 IN varchar2,            -- New VZW App, Concept, etc.
            p_permitted_platforms           IN varchar2,            -- comma seperated list permitted platform ids
            p_alliance_id                   IN number,              -- alliance id of the application user
            p_user_id                       IN number,              -- user id of the application user
            p_contact_id                    IN number,              -- vzw contact id of the application
            p_query_type                    IN varchar2,            -- for egs. NEW BREW APP, SAVED APPS, SUBMITTED APPS, etc
            p_page_no_needed                IN number,              -- page number requested
            p_num_per_page                  IN number,              -- number of records per page
            p_filter_text                   IN varchar2,            -- filtering text
            p_filter_field                  IN varchar2,            -- filtering field
            p_sort_field                    IN varchar2,            -- sort field
            p_user_type                     IN varchar2,            -- type of user
			p_filter_statuses				IN varchar2,			-- comma seperated list of selected statuses            
			p_filter_platforms				IN varchar2,			-- comma seperated list of selected platforms
			p_sort_order					IN varchar2,			-- sorting order either asc or desc			            
            p_out_total_records            OUT number,              -- total number of records in selection set
            p_out_result                   OUT TYPES.cursor_type    -- Result cursor
            */

            if ((brewAppType != null) && (brewAppType.equals("0")))
                brewAppType = null;

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();

            statement = ConOra.prepareCall("call AIMS_APPS_LIST_PKG.get_results_cursor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, appTitle);
            statement.setLong(2, platformId.longValue());
            statement.setLong(3, phaseId.longValue());
            statement.setLong(4, categoryId.longValue());
            statement.setLong(5, subCategoryId.longValue());
            statement.setString(6, selectedDeviceIds);
            statement.setString(7, brewAppType);
            statement.setString(8, permittedPlatformIds);
            statement.setLong(9, Utility.ZeroValueReplacement(allianceId).longValue());
            statement.setLong(10, Utility.ZeroValueReplacement(userId).longValue());
            statement.setLong(11, Utility.ZeroValueReplacement(contactId).longValue());
            statement.setString(12, queryType);
            statement.setInt(13, pageNo);
            statement.setInt(14, PAGE_LENGTH);
            statement.setString(15, filterText);
            statement.setString(16, filterField);
            statement.setString(17, sortField);
            statement.setString(18, userType);
            statement.setString(19, filterPlatforms);
            statement.setString(20, filterStatuses);
            statement.setString(21, sortOrder);
            statement.registerOutParameter(22, OracleTypes.NUMBER);
            statement.registerOutParameter(23, OracleTypes.CURSOR);
            statement.execute();

            System.out.println("Total Records from new query: " + statement.getInt(22));
            totalRecords.append(statement.getInt(22));

            results = (ResultSet) statement.getObject(23);

            if (results != null)
                results.setFetchSize(100);

            ApplicationInformation appInfo = null;
            while (results.next())
            {
                appInfo = new ApplicationInformation();
                appInfo.setAppsId(new Long(results.getLong(1))); // appsId
                appInfo.setCompanyName(results.getString(2)); // companyName
                appInfo.setPlatformName(results.getString(3)); // platformName
                appInfo.setTitle(  (String)results.getString(4)  ); // title                

                appInfo.setVersion(results.getString(5)); // version
                appInfo.setCategoryName(results.getString(6)); // categoryName
                appInfo.setCreatedDate((Date) results.getDate(7)); // createdDate
                appInfo.setPhaseId(new Long(results.getLong(8))); // phaseId
                appInfo.setPhaseName(results.getString(9)); // phaseName
                appInfo.setPlatformId(new Long(results.getLong(10))); // platformId
                appInfo.setIfOnHold(results.getString(11)); // ifOnHold
                appInfo.setAllianceId(new Long(results.getLong(12))); // allianceId
                appInfo.setBrewAppType(results.getString(13)); // brewAppType
                if (results.getString(14)!=null && "Y".equalsIgnoreCase(results.getString(14))){
                	appInfo.setIsLbs(true); // isLBS	
                }
                else {
                	appInfo.setIsLbs(false);
                }
                
                appInfo.setUrlSetupAction(ApplicationsManagerHelper.getUrlSetupAction(appInfo.getPlatformId()));
                apps.add(appInfo);
            }

            tx.commit();
        }

        catch (Exception ex)
        {
            if (tx != null)
                tx.rollback();

            ex.printStackTrace();
            throw ex;
        }

        finally
        {
            if (statement != null)
                statement.close();
            session.close();
        }

        return apps;
    }

    /*
        public static Collection getApplications(StringBuffer queryStringBuffer, int PAGE_LENGTH, int page_no) throws HibernateException
        {
            Session session = null;
            Collection collection = null;
    
            try
            {
                //Set	Paging Logic and execute Query	
                session = DBHelper.getInstance().getSession();
                Query query = session.createQuery(queryStringBuffer.toString());
                query.setMaxResults(PAGE_LENGTH);
                query.setFirstResult(PAGE_LENGTH * (page_no - 1));
                collection = query.list();
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
    */

    /*
        public static int getPageCount(StringBuffer queryStringBuffer) throws HibernateException
        {
            int rows = 0;
            Collection collection = null;
            Session session = null;
    
            try
            {
                session = DBHelper.getInstance().getSession();
                //rows = ( (Integer) session.iterate(queryStringBuffer.toString()).next()	).intValue();
                collection = session.find(queryStringBuffer.toString());
                log.debug("No	of apps	returned:	" + collection.size());
                rows = collection.size();
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
            return rows;
    
        }
    */

    /**
     *	This static	method gets	the	contracts	in the database	
     */
    public static Collection getContracts() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            collection = session.find("from	com.netpace.aims.model.core.AimsContract as	contracts	order	by contract_title");

            log.debug("No	of contracts returned: " + collection.size());

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

    public static Collection getProductGroups() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            collection = session.find("from	com.netpace.aims.model.application.AimsProductGroup	as productGroup	order	by product_group_title ");

            log.debug("No	of product groups	returned:	" + collection.size());

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

    public static Collection getCertifications(Long platformId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString
                .append("	from com.netpace.aims.model.application.AimsCertification	certification	")
                .append(" where certification.aimsPlatformId	=	")
                .append(platformId.toString())
                .append(" order by	certification.certificationName	");

            collection = session.find(queryString.toString());
            //collection = session.find("from	com.netpace.aims.model.application.AimsCertification as	certification	order	by certification_name	");

            log.debug("No	of certifications	returned:	" + collection.size());

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

    public static Collection getAppNetworkUrls(Long appsId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString
                .append("	from com.netpace.aims.model.application.AimsAppNetworkUrls	appNetworkURL	")
                .append(" where appNetworkURL.aimsAppId	=	")
                .append(appsId.toString())
                .append(" order by	appNetworkURL.appNetworkUrlId	");

            collection = session.find(queryString.toString());

            log.debug("No	of AimsAppNetworkUrls	returned:	" + collection.size());

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

    public static String[] getAppNetworkUrlValues(Long appsId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        String [] appNetworkUrlValues = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString
                .append("	select 	appNetworkURL.appNetworkUrl ")
                .append("	from com.netpace.aims.model.application.AimsAppNetworkUrls	appNetworkURL	")
                .append(" where appNetworkURL.aimsAppId	=	")
                .append(appsId.toString())
                .append(" order by	appNetworkURL.appNetworkUrlId	");

            collection = session.find(queryString.toString());
            log.debug("No	of AimsAppNetworkUrls	returned:	" + collection.size());
            if(collection!=null && collection.size()>0)
            {
                appNetworkUrlValues = (String [])collection.toArray(new String[collection.size()]);
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
        return appNetworkUrlValues;
    }

    public static Collection getTestingPhases(Long platformId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString
                .append("	from com.netpace.aims.model.application.AimsVzwTestingPhase	testingPhase ")
                .append(" where testingPhase.aimsPlatformId = ")
                .append(platformId.toString())
                .append(" order by	testingPhase.testingPhaseName	");

            collection = session.find(queryString.toString());

            log.debug("No	of testing phases	returned:	" + collection.size());

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

    public static AimsCertification getCertification(Long certificationId) throws HibernateException
    {

        AimsCertification certification = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query =
                session.createQuery(
                    "select	from com.netpace.aims.model.application.AimsCertification	as certification where certification.certificationId = :certificationId");
            query.setLong("certificationId", certificationId.longValue());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                certification = (AimsCertification) it.next();
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

        return certification;
    }
    /*
     * This method is used by the 'Application Search' interface
     * to hide some statuses from the Alliance User
     */
    public static Collection getPhases(String currUserType) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();
            queryStringBuffer.append(" from com.netpace.aims.model.application.AimsLifecyclePhase phases ");
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                queryStringBuffer.append(" where phases.phaseId != " + AimsConstants.PHASE_INITIAL_APPROVAL_ID.toString());
                queryStringBuffer.append(" and  phases.phaseId != " + AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID.toString());
                queryStringBuffer.append(" and  phases.phaseId != " + AimsConstants.PHASE_PENDING_DCR_ID.toString());
                queryStringBuffer.append(" and  phases.phaseId != " + AimsConstants.PHASE_PENDING_ARM_ID.toString());
                queryStringBuffer.append(" and  phases.phaseId != " + AimsConstants.PHASE_TESTING_PASSED_ID.toString());
                queryStringBuffer.append(" and  phases.phaseId != " + AimsConstants.PHASE_TESTING_FAILED_ID.toString());
                queryStringBuffer.append(" and  phases.phaseId != " + AimsConstants.PHASE_PUBLICATION_READY_ID.toString());
            }
            queryStringBuffer.append(" order by phase_name ");
            Query query = session.createQuery(queryStringBuffer.toString());
            collection = query.list();
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

    public static Collection getPhases() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            collection = session.find("from com.netpace.aims.model.application.AimsLifecyclePhase   as phases   order   by phase_name");

            log.debug("No   of phases   returned:   " + collection.size());

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
     *	START	OF CATEGORIES	RELATED	METHODS
     */

    public static Collection getCategories() throws HibernateException
    {

        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString.append("	from com.netpace.aims.model.application.AimsAppCategory	category ");

            collection = session.find(queryString.toString());

            log.debug("No	of records returned: " + collection.size());
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

    public static Collection getCategories(Long platformId) throws HibernateException
    {

        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString.append("	from com.netpace.aims.model.application.AimsAppCategory	category ").append(" where category.aimsPlatformId = ").append(
                platformId.toString());

            collection = session.find(queryString.toString());

            log.debug("No	of records returned: " + collection.size());
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

    public static Collection getSubCategories() throws HibernateException
    {

        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString.append("	from com.netpace.aims.model.application.AimsAppSubCategory subCategory ");

            collection = session.find(queryString.toString());

            log.debug("No	of records returned: " + collection.size());
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

    public static Collection getSubCategoriesByCategory(Long categoryId) throws HibernateException
    {

        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString.append("	from com.netpace.aims.model.application.AimsAppSubCategory subCategory ").append(
                " where subCategory.aimsAppCategoryId = ").append(
                categoryId.toString());

            collection = session.find(queryString.toString());

            log.debug("No	of records returned: " + collection.size());
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

    public static Collection getCategoriesByPlatform(Long platformId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();
            queryStringBuffer.append(" from com.netpace.aims.model.application.AimsAppCategory category where category.aimsPlatformId = :platformId");
            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("platformId", platformId.longValue());
            collection = query.list();
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

    public static Collection getSubCategoriesByPlatform(Long platformId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();
            queryStringBuffer
                .append(" select ")
                .append("       subCategory ")
                .append(" from ")
                .append("       com.netpace.aims.model.application.AimsAppCategory category, ")
                .append("       com.netpace.aims.model.application.AimsAppSubCategory subCategory ")
                .append(" where ")
                .append("       category.aimsPlatformId = :platformId ")
                .append("       and category.categoryId = subCategory.aimsAppCategoryId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("platformId", platformId.longValue());
            collection = query.list();
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
     *	END	OF CATEGORIES	RELATED	METHODS
     */

    /**
     *	START	OF CONTACTS	RELATED	METHODS
     */

    /*public static Collection getContacts(Long allianceId) throws HibernateException
    {

        Collection collection = null;
        Session session = null;
        try
        {
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

            if (allianceId != null)
            {
                queryString
                    .append("               user.aimsAllianc = :allianceId ")
                    .append("               and user.userType = 'A' ")
                    .append("   ) ")
                    .append("   or          contact.contactId in ")
                    .append("   ( ")
                    .append("               select  distinct app.aimsContactId ")
                    .append("               from    com.netpace.aims.model.application.AimsApp app ")
                    .append("				where  app.aimsAllianceId = :allianceId ")
                    .append("   ) ");
            }
            else
            {
                queryString.append("        user.aimsAllianc is	null ");
                queryString.append("        and user.userType = 'V' ");
                queryString.append(" ) ");
            }

            queryString.append("    order by contact.firstName, contact.lastName ");

            Query query = session.createQuery(queryString.toString());

            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            collection = query.list();

            //collection = session.find(queryString.toString());

            log.debug("No	of records returned: " + collection.size());
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
    }*/

    /**
     * This method calls ContactsManager internally,
     * this method is used in applications to show contacts (Additional info, tech contact etc) 
     * @param allianceId
     * @return
     * @throws HibernateException
     * @throws Exception
     */
    public static Collection getContacts(Long allianceId) throws HibernateException, Exception
    {
        Collection allianceContacts = null;
        try {
            allianceContacts = ContactsManager.getAllAllianceContacts(allianceId);
        }
        catch(HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return allianceContacts;
    }
    
    public static Collection getContactsByStatus(Long allianceId, String userAccountStatus) throws HibernateException
    {

        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();

            queryString
                .append("   select  distinct contact ")
                .append("   from    com.netpace.aims.model.core.AimsContact	contact ")
                .append("   where   contact.contactId in ")
                .append("       ( ")
                .append("           select  distinct user.aimsContact ")
                .append("           from    com.netpace.aims.model.core.AimsUser user ")
                .append("           where ")
                .append("				user.userAccountStatus = :userAccountStatus")
                .append(" 				and ");
            
            if (allianceId != null)
            {
                queryString
                    .append("               user.aimsAllianc = :allianceId ")
                    .append("               and user.userType = 'A' ")
                    .append("   ) ")
                    .append("   or          contact.contactId in ")
                    .append("   ( ")
                    .append("               select  distinct app.aimsContactId ")
                    .append("               from    com.netpace.aims.model.application.AimsApp app ")
                    .append("				where  app.aimsAllianceId = :allianceId ")
                    .append("   ) ");
            }
            else
            {
                queryString.append("        user.aimsAllianc is	null ");
                queryString.append("        and user.userType = 'V' ");
                queryString.append(" ) ");
            }
            
            queryString.append("    order by contact.firstName, contact.lastName ");

            Query query = session.createQuery(queryString.toString());
            
            query.setString("userAccountStatus", userAccountStatus);
            if (allianceId != null) {
                query.setLong("allianceId", allianceId.longValue());
            }
            if(!StringFuncs.isNullOrEmpty(userAccountStatus)) {
            	collection = query.list();            	
            }
            else {
            	log.debug("AimsApplicationsManager.getContactsByStatus(): user account status is empty");
            }

            //collection = session.find(queryString.toString());
            
            log.debug("AimsApplicationsManager.getContactsByStatus(): No of records returned: " + collection.size());
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

    public static AimsContact getContact(Long contactId) throws HibernateException
    {

        AimsContact contact = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query =
                session.createQuery("select	from com.netpace.aims.model.core.AimsContact as	contact	where	contact.contactId	=	:contactId");
            query.setLong("contactId", contactId.longValue());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                contact = (AimsContact) it.next();
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

        return contact;
    }

    public static Long saveOrUpdateContact(AimsContact aimsContact) throws HibernateException
    {

        Session session = null;
        Long contactId = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            DBHelper.getInstance().save(aimsContact);
            contactId = aimsContact.getContactId();
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

        return contactId;
    }

    /**
     *	END	OF CONTACTS	RELATED	METHODS
     */

    /**
     *	START	OF JOURNAL RELATED METHODS
     */

    public static Collection getJournalEntries(Long applicationId, Long allianceId) throws HibernateException
    {

        Collection collection = null;
        Session session = null;
        try
        {

            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("	select journalEntry		")
                .append("	from	")
                .append("					com.netpace.aims.model.application.AimsJournalEntry	as journalEntry,		")
                .append("					com.netpace.aims.model.application.AimsAppLite as app		")
                .append("	where	")
                .append("					journalEntry.aimsAppId = app.appsId ")
                .append("					and journalEntry.aimsAppId = " + applicationId);

            //Set	additional criteria if User	is 'Alliance User' (allianceId != null).	
            if (allianceId != null)
            {
                queryStringBuffer.append("				and	app.aimsAllianceId = " + allianceId).append(
                    "				and	journalEntry.journalType = '" + AimsConstants.JOURNAL_TYPE_PUBLIC + "' ");
            }

            queryStringBuffer.append("	order	by	journalEntry.createdDate desc	 ");
            collection = session.find(queryStringBuffer.toString());

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

    public static void saveOrUpdateJournalEntries(AimsJournalEntry journalEntry) throws HibernateException
    {

        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            DBHelper.getInstance().save(journalEntry);
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

    }

    public static void saveJournalEntries(List journalEntries) throws HibernateException
    {
    	
    	Session session = null;
    	
    	try
    	{
    		session = DBHelper.getInstance().getSession();
    		for (int i=0; i<journalEntries.size(); i++){
    			AimsJournalEntry je=(AimsJournalEntry)journalEntries.get(i);
    			DBHelper.getInstance().save(je);
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
    	
    }

    /**
     *	END	OF JOURNAL RELATED METHODS
     */
    /*
    public static	void updateOnHold(Long appsId, String	onHold)	throws HibernateException	
    {
    AimsAppLite	appLite	=	null;
    Session	session	=	null;
    try
    {
    	session	=	DBHelper.getInstance().getSession();
    	Query	query	=	session.createQuery("select	from com.netpace.aims.model.application.AimsAppLite	as appLite where appLite.appsId = :appsId");
    	query.setLong("appsId", appsId.longValue());
    
    	for	(Iterator	it = query.iterate();	it.hasNext();) 
    	{
    		appLite	=	(AimsAppLite)	it.next();
    	}
    	
    	if (onHold.equals("Y"))
    		appLite.setIfOnHold("Y");
    	else
    		appLite.setIfOnHold("N");
    	
    	DBHelper.getInstance().save(appLite);	
    				
    }
    catch(HibernateException e)
    {
    	e.printStackTrace();
    	throw	e;
    }
    finally
    {
    	session.close();
    }		
    }
    /*
    /* */

    public static void updateOnHold(Long appsId, String onHold, String user) throws HibernateException
    {
        AimsAppLite appLite = null;
        Session session = null;
        String strHoldText = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery("select	from com.netpace.aims.model.application.AimsAppLite	as appLite where appLite.appsId = :appsId");
            query.setLong("appsId", appsId.longValue());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                appLite = (AimsAppLite) it.next();
            }

            if (onHold.equals("Y"))
            {
                appLite.setIfOnHold("Y");
                strHoldText = "On Hold";
            }
            else
            {
                appLite.setIfOnHold("N");
                strHoldText = "Off Hold";
            }

            DBHelper.getInstance().save(appLite);

            AimsJournalEntry journalEntry = new AimsJournalEntry();
            journalEntry.setJournalText("Application is " + strHoldText + " Updated by " + user);
            journalEntry.setJournalType(AimsConstants.JOURNAL_TYPE_PRIVATE);
            journalEntry.setCreatedBy("system");
            journalEntry.setCreatedDate(new Date());
            journalEntry.setAimsAppId(appsId);
            DBHelper.getInstance().save(journalEntry);

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
    }

    //This function is used, from the Jsps, to display the Alliance Name of the owner of the application
    public static String getAllianceNameOfApplication(Long applicationId)
    {

        String allianceName = "";
        Session session = null;
        try
        {

            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("	select alliance.companyName ")
                .append("	from	")
                .append("					com.netpace.aims.model.core.AimsAllianc	as alliance,		")
                .append("					com.netpace.aims.model.application.AimsAppLite as app		")
                .append("	where	")
                .append("					app.aimsAllianceId = alliance.allianceId ")
                .append("			and	app.appsId = :applicationId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("applicationId", applicationId.longValue());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                allianceName = (String) it.next();
            }

        }
        catch (Exception ex)
        {
            allianceName = "";
        }
        finally
        {
            try
            {
                session.close();
            }
            catch (Exception ex)
            {}
        }

        return allianceName;
    }

    public static Long getAllianceIdOfApplication(Long applicationId)
    {
        Long allianceId = null;
        try
        {
            AimsAppLite aimsApp = (AimsAppLite) DBHelper.getInstance().load(com.netpace.aims.model.application.AimsAppLite.class, applicationId.toString());
            allianceId = aimsApp.getAimsAllianceId();
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
        }

        return allianceId;
    }

    //Get Alliance Sponsors From BDS Contacts
    public static Collection getAllianceSponsors() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();

            queryString.append("		select	distinct contact ").append("	from		com.netpace.aims.model.core.BdsContact	contact	").append(
                "	order by contact.firstName, contact.lastName ");

            collection = session.find(queryString.toString());

            log.debug("No	of alliance sponsors	returned:	" + collection.size());

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

    //Function to Delete App
    public static int deleteApp(Long appsId, String user) throws AimsException, Exception
    {
        int delCount = 0;
        Session session = null;
        Transaction tx = null;
        java.sql.Connection ConOra = null;
        java.sql.CallableStatement statement = null;

        try
        {
            /*	
            Database Procedure SnapShot	:
            AIMS_UTILS.delete_app_with_relations(?,?)
            		 
            Parameters:
            p_apps_id			   IN		number,
            p_curr_user_name IN		varchar2
            */

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();

            statement = ConOra.prepareCall("call AIMS_UTILS.delete_app_with_relations(?,?)");
            statement.setLong(1, appsId.longValue());
            statement.setString(2, user);
            statement.execute();

            tx.commit();
        }

        catch (Exception ex)
        {
            AimsException aimsException = new AimsException("Error");

            if (tx != null)
                tx.rollback();

            if (DBErrorFinder.isIntegrityConstraintError(ex.getMessage(), aimsException))
            {
                throw aimsException;
            }
            else
            {
                ex.printStackTrace();
                throw ex;
            }
        }

        finally
        {
            if (statement != null)
                statement.close();
            session.close();
            log.debug("SESSION CLOSED IN deleteApp()");
        }

        return delCount;
    }

    //This function is used to get the updated PHASE_ID (Status), that might
    //	have been updated by the Trigger.
    public static Long getPhaseIdAfterUpdate(Long appId)
    {
        Long phaseId = new Long(1);
        Session session = null;
        try
        {

            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("	select app.aimsLifecyclePhaseId ")
                .append("	from	")
                .append("					com.netpace.aims.model.application.AimsAppLite as app		")
                .append("	where	")
                .append("					app.appsId = :appId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appId", appId.longValue());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                phaseId = (Long) it.next();
            }

        }
        catch (Exception ex)
        {}
        finally
        {
            try
            {
                session.close();
            }
            catch (Exception ex)
            {}
        }

        return phaseId;
    }

    public static Long getUserIdForContactId(Long contactId)
    {
        Long userId = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();
            queryStringBuffer
                .append("   select user.userId ")
                .append("   from    ")
                .append("       com.netpace.aims.model.core.AimsUser as user ")
                .append("   where   ")
                .append("       user.aimsContactId = :contactId ");
            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("contactId", contactId.longValue());
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                userId = (Long) it.next();
            }
        }
        catch (Exception ex)
        {}
        finally
        {
            try
            {
                session.close();
            }
            catch (Exception ex)
            {}
        }
        return userId;
    }

    /*
     * This method saves the Journal Entry into the database. It encapsulates the whole transaction
     */
    public static void saveJournalEntry(Long appsId, String journalEntry, String journalType, String username) throws Exception
    {
        Session session = null;
        Transaction tx = null;
        java.sql.Connection ConOra = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();
            addSpecificJournalEntry(ConOra, appsId, journalEntry, journalType, username);
            tx.commit();
        }

        catch (Exception ex)
        {
            if (tx != null)
                tx.rollback();
            throw ex;
        }

        finally
        {
            session.close();
        }
    }

    /*
     * This method calls the Stored Procedure 'AIMS_UTILS.add_journal_entry(?,?,?,?,?)' which saves the Journal Entry.
     */
    public static void addSpecificJournalEntry(java.sql.Connection ConOra, Long appsId, String journalText, String journalType, String username)
        throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            /*  
            Database Procedure SnapShot :
            
            AIMS_UTILS.add_journal_entry(?,?,?,?,?)
            
            Parameters:
            
            p_apps_id            IN     number,
            p_alliance_id    IN     number,                      
            p_journal_text IN       varchar2,
            p_journal_type IN       varchar2,
            p_created_by     IN     varchar2
            
            */

            statement = ConOra.prepareCall("call AIMS_UTILS.add_journal_entry(?,?,?,?,?)");
            statement.setLong(1, appsId.longValue());
            statement.setLong(2, 0);
            statement.setString(3, journalText);
            statement.setString(4, journalType);
            statement.setString(5, username);
            statement.execute();
        }

        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            if (statement != null)
                statement.close();
        }

    }

    /**
     * Set network usage value with given value. Used to enable or disable network usage
     * @param appsId
     * @param networkUsageValue
     * @throws SQLException
     * @throws HibernateException
     */
    public static int saveAimsAppNetworkUsage(Long appsId, String networkUsageValue) throws SQLException, HibernateException
    {
        Session session = null;
        Connection conOra = null;
        PreparedStatement pStatement = null;
        String query = "update AIMS_APPS SET NETWORK_USAGE = '"+networkUsageValue+"' where APPS_ID= ?";
        int rowsUpdated = 0;

        try
        {
            session = DBHelper.getInstance().getSession();
            conOra = session.connection();
            pStatement  = conOra.prepareStatement(query);
            pStatement .setLong(1, appsId.longValue());
            rowsUpdated = pStatement.executeUpdate();

            conOra.commit();
        }
        catch (SQLException sqle)
        {
            conOra.rollback();
            sqle.printStackTrace();
        }
        finally
        {
            if (pStatement!=null)
            {
                    pStatement.close();
            }
            session.close();
            log.debug("session closed in saveAimsAppNetworkUsage()");
        }
        return rowsUpdated;
    }

}