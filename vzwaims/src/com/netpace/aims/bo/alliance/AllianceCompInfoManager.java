package com.netpace.aims.bo.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.Query;
import net.sf.hibernate.type.LongType;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.alliance.AllianceApplicationForm;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.alliance.*;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.model.masters.AimsIndustryFocu;
import com.netpace.aims.model.masters.AimsRegion;
import com.netpace.aims.model.masters.AimsVzwReasons;
import com.netpace.aims.model.masters.AimsCarriers;
import com.netpace.aims.model.core.AimsCountries;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class is responsible for getting the BO for alliances.
 * It has static methods for getting the different details of the alliances.
 * @author Rizwan Qazi
 */
public class AllianceCompInfoManager
{

    static Logger log = Logger.getLogger(AllianceCompInfoManager.class.getName());

    /**
     *  This static method gets the company information details of the current alliance   
     */
    public static Collection getAllianceCompInfo(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		alliance.companyName, alliance.companyLegalName, ")
                .append("		alliance.stateOfInc, alliance.authRepName, ")
                .append("		alliance.streetAddress1, alliance.steetAddress2, ")
                .append("		alliance.city, alliance.state, ")
                .append("		alliance.zipCode, alliance.country, ")
                .append("		alliance.webSiteUrl, alliance.prevYearRevenues, ")
                .append("		alliance.dbNumber, alliance.isFinanceInfoPublic, ")
                .append("		alliance.vzwRelationshipReasons, alliance.vzwCurrentContracts, ")
                .append("		alliance.otherCarrierAlliances, alliance.commentsOtherCarrierAlliances, ")
                .append("		alliance.otherIndustryFocus, alliance.vendorId, alliance.status, ")
                .append("		alliance.remitTo, alliance.remitAddress1, alliance.remitAddress2, ")
                .append("		alliance.remitCity, alliance.remitState, alliance.remitPostalCode, alliance.remitCountry ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance ")
                .append("where ")
                .append("		alliance.allianceId = :allianceId ");

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
     *  This static method gets the industry focus available (not assigned) to the current alliance.
     */
    public static Collection getAvailableIndFocus(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsIndustryFocu aimsIndustryFocus = null;
        Collection AvailableIndFocus = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		indFocusAvailable.industryId, indFocusAvailable.industryName ")
                .append("from ")
                .append("		com.netpace.aims.model.masters.AimsIndustryFocu indFocusAvailable ")
                .append("where ")
                .append("		indFocusAvailable.industryId  ")
                .append("		not in ( ")
                .append("			select ")
                .append("				indFocusAssigned.industryId ")
                .append("			from ")
                .append("				com.netpace.aims.model.alliance.AimsAllianceIndFocus allianceIndFocus, ")
                .append("				com.netpace.aims.model.masters.AimsIndustryFocu indFocusAssigned ")
                .append("			where ")
                .append("				allianceIndFocus.allianceId = :allianceId ")
                .append("				and allianceIndFocus.industryFocus.industryId = indFocusAssigned.industryId ")
                .append("				) ")
                .append("order by  ")
                .append("		indFocusAvailable.industryName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsIndustryFocus = new AimsIndustryFocu();

                userValues = (Object[]) iter.next();

                aimsIndustryFocus.setIndustryId((Long) userValues[0]);
                aimsIndustryFocus.setIndustryName((String) userValues[1]);

                AvailableIndFocus.add(aimsIndustryFocus);
            }

            log.debug("No of records returned: " + AvailableIndFocus.size());

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

        return AvailableIndFocus;
    }

    /**
     *  This static method gets the platforms available (not assigned) to the current alliance.
     */
    public static Collection getAvailablePlatforms(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsPlatform aimsPlatform = null;
        Collection AvailablePlatform = new ArrayList();

        try
        {
            queryStringBuffer
                .append("select ")
                .append("       platformAvailable.platformId, platformAvailable.platformName ")
                .append("from ")
                .append("       com.netpace.aims.model.core.AimsPlatform platformAvailable ")
                .append("where ")
                .append("       platformAvailable.platformId  ")
                .append("       not in ( ")
                .append("           select ")
                .append("               platformAssigned.platformId ")
                .append("           from ")
                .append("               com.netpace.aims.model.alliance.AimsAlliancePlatform alliancePlatform, ")
                .append("               com.netpace.aims.model.core.AimsPlatform platformAssigned ")
                .append("           where ")
                .append("               alliancePlatform.allianceId = :allianceId ")
                .append("               and alliancePlatform.platforms.platformId = platformAssigned.platformId ")
                .append("               ) ")
                .append("order by  ")
                .append("       platformAvailable.platformName  ");


            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsPlatform = new AimsPlatform();

                userValues = (Object[]) iter.next();

                aimsPlatform.setPlatformId((Long) userValues[0]);
                aimsPlatform.setPlatformName((String) userValues[1]);

                AvailablePlatform.add(aimsPlatform);
            }

            log.debug("No of records returned: " + AvailablePlatform.size());

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

        return AvailablePlatform;
    }

    /**
     *  This static method gets the all platforms available
     */
    public static Collection getAllAvailablePlatforms() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsPlatform aimsPlatform = null;
        Collection AvailablePlatform = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       platformAvailable.platformId, platformAvailable.platformName ")
                .append("from ")
                .append("       com.netpace.aims.model.core.AimsPlatform platformAvailable ")
                .append("order by  ")
                .append("       platformAvailable.platformName  ");
            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString()); 

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsPlatform = new AimsPlatform();

                userValues = (Object[]) iter.next();

                aimsPlatform.setPlatformId((Long) userValues[0]);
                aimsPlatform.setPlatformName((String) userValues[1]);

                AvailablePlatform.add(aimsPlatform);
            }

            log.debug("No of records returned: " + AvailablePlatform.size());

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

        return AvailablePlatform;
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
     *  This static method gets the platforms for the current alliance.
     */
    public static Collection getAlliancePlatforms(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsPlatform aimsPlatform = null;
        Collection AlliancePlatform = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       platform.platformId, platform.platformName ")
                .append("from ")
                .append("       com.netpace.aims.model.alliance.AimsAlliancePlatform alliancePlatform, ")
                .append("       com.netpace.aims.model.core.AimsPlatform platform ")
                .append("where ")
                .append("       alliancePlatform.allianceId = :allianceId ")
                .append("       and alliancePlatform.platforms.platformId = platform.platformId ")
                .append("order by  ")
                .append("       platform.platformName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsPlatform = new AimsPlatform();

                userValues = (Object[]) iter.next();

                aimsPlatform.setPlatformId((Long) userValues[0]);
                aimsPlatform.setPlatformName((String) userValues[1]);

                AlliancePlatform.add(aimsPlatform);
            }

            log.debug("No of records returned: " + AlliancePlatform.size());

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

        return AlliancePlatform;
    }

    /**
     *  This static method gets the platforms for the current alliance.
     */
    public static String[] getAlliancePlatformIds(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Collection AlliancePlatformIdList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       platform.platformId, platform.platformName ")
                .append("from ")
                .append("       com.netpace.aims.model.alliance.AimsAlliancePlatform alliancePlatform, ")
                .append("       com.netpace.aims.model.core.AimsPlatform platform ")
                .append("where ")
                .append("       alliancePlatform.allianceId = :allianceId ")
                .append("       and alliancePlatform.platforms.platformId = platform.platformId ")
                .append("order by  ")
                .append("       platform.platformName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {

                userValues = (Object[]) iter.next();

                AlliancePlatformIdList.add(((Long)userValues[0]).toString());
            }

            log.debug("No of records returned: " + AlliancePlatformIdList.size());

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

        return (String [])AlliancePlatformIdList.toArray(new String[0]);
    }


    /**
     *  This static method gets the VZWReasons for the current alliance.
     */
    public static Collection getAllianceReasonsForRelationshipWithVzw(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsVzwReasons aimsVzwReason = null;

        Collection allianceVzwReasons = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsVzwReasons.reasonsId, aimsVzwReasons.reasonsOption ")
                .append("from ")
                .append("       com.netpace.aims.model.alliance.AimsAllianceVzwReasons aimsAllianceVzwReasons, ")
                .append("       com.netpace.aims.model.masters.AimsVzwReasons aimsVzwReasons ")
                .append("where ")
                .append("       aimsAllianceVzwReasons.allianceId = :allianceId ")
                .append("       and aimsAllianceVzwReasons.vzwReasons.reasonsId = aimsVzwReasons.reasonsId ")
                .append("order by  ")
                .append("       aimsVzwReasons.sortOrder  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsVzwReason = new AimsVzwReasons();
                userValues = (Object[]) iter.next();
                aimsVzwReason.setReasonParentId((Long)userValues[0]);
                aimsVzwReason.setReasonsOption((String)userValues[1]);
                allianceVzwReasons.add(aimsVzwReason);
            }

            log.debug("No of records returned: " + allianceVzwReasons.size());

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

        return allianceVzwReasons;
    }
    /**
     *  This static method gets the VZWReasons IDS for the current alliance.
     */
    public static Long[] getAllianceReasonsForRelationshipWithVzwIds(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        //AimsPlatform aimsPlatform = null;

        Collection allianceVzwReasons = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsVzwReasons.reasonsId, aimsVzwReasons.reasonsOption ")
                .append("from ")
                .append("       com.netpace.aims.model.alliance.AimsAllianceVzwReasons aimsAllianceVzwReasons, ")
                .append("       com.netpace.aims.model.masters.AimsVzwReasons aimsVzwReasons ")
                .append("where ")
                .append("       aimsAllianceVzwReasons.allianceId = :allianceId ")
                .append("       and aimsAllianceVzwReasons.vzwReasons.reasonsId = aimsVzwReasons.reasonsId ")
                .append("order by  ")
                .append("       aimsVzwReasons.sortOrder  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                //aimsPlatform = new AimsPlatform();

                userValues = (Object[]) iter.next();

                //aimsPlatform.setPlatformId((Long) userValues[0]);
                //aimsPlatform.setPlatformName((String) userValues[1]);
                allianceVzwReasons.add((Long)userValues[0]);

                //AlliancePlatform.add(aimsPlatform);
            }

            log.debug("No of records returned: " + allianceVzwReasons.size());

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

        //return allianceVzwReasons;
        return (Long[])allianceVzwReasons.toArray(new Long[0]);
    }

    /**
     *  This static method gets the AllianceCarrierIds for the current alliance.
     */
    public static Long[] getAlliancesWithOtherCarriersIds(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        Collection allianceCarriersList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsCarriers.carrierId, aimsCarriers.carrierName ")
                .append("from ")
                .append("       com.netpace.aims.model.alliance.AimsAllianceCarriers aimsAllianceCarriers, ")
                .append("       com.netpace.aims.model.masters.AimsCarriers aimsCarriers ")
                .append("where ")
                .append("       aimsAllianceCarriers.allianceId = :allianceId ")
                .append("       and aimsAllianceCarriers.carriers.carrierId = aimsCarriers.carrierId ")
                .append("order by  ")
                .append("       aimsCarriers.sortOrder  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {


                userValues = (Object[]) iter.next();


                allianceCarriersList.add((Long)userValues[0]);


            }

            log.debug("No of records returned: " + allianceCarriersList.size());

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

        return (Long[])allianceCarriersList.toArray(new Long[0]);
    }

    /**
     *  This static method gets the AllianceCarriers for the current alliance.
     */
    public static Collection getAlliancesWithOtherCarriers(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsCarriers aimsCarrier = null;
        Collection allianceCarriersList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsCarriers.carrierId, aimsCarriers.carrierName ")
                .append("from ")
                .append("       com.netpace.aims.model.alliance.AimsAllianceCarriers aimsAllianceCarriers, ")
                .append("       com.netpace.aims.model.masters.AimsCarriers aimsCarriers ")
                .append("where ")
                .append("       aimsAllianceCarriers.allianceId = :allianceId ")
                .append("       and aimsAllianceCarriers.carriers.carrierId = aimsCarriers.carrierId ")
                .append("order by  ")
                .append("       aimsCarriers.sortOrder  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsCarrier = new AimsCarriers();
                userValues = (Object[]) iter.next();

                aimsCarrier.setCarrierId((Long)userValues[0]);
                aimsCarrier.setCarrierName((String)userValues[1]);
                allianceCarriersList.add(aimsCarrier);
            }

            log.debug("No of records returned: " + allianceCarriersList.size());

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

        return allianceCarriersList;
    }

    /**
     *  This static method gets the industry focus from an array on Ind Focus ids.
     */
    public static Collection getAvailableIndFocusFromArray(String[] assignedArrIndustryFocus) throws HibernateException
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
                .append("		com.netpace.aims.model.masters.AimsIndustryFocu indFocus ")
                .append("where ")
                .append("		indFocus.industryId not in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrIndustryFocus, ","), "0"))
                .append("							   ) ")
                .append("order by  ")
                .append("		indFocus.industryName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

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
            if (session != null)
                session.close();
        }

        return AllianceIndFocus;
    }

    /**
     *  This static method gets the platforms available (not assigned) to the current alliance.
     */
    public static Collection getAvailablePlatformsFromArray(String[] assignedArrPlatforms) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsPlatform aimsPlatform = null;
        Collection AvailablePlatforms = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       platform.platformId, platform.platformName ")
                .append("from ")
                .append("       com.netpace.aims.model.core.AimsPlatform platform ")
                .append("where ")
                .append("       platform.platformId not in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrPlatforms, ","), "0"))
                .append("                          ) ")
                .append("order by  ")
                .append("       platform.platformName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsPlatform = new AimsPlatform();

                userValues = (Object[]) iter.next();

                aimsPlatform.setPlatformId((Long) userValues[0]);
                aimsPlatform.setPlatformName((String) userValues[1]);

                AvailablePlatforms.add(aimsPlatform);
            }

            log.debug("No of records returned: " + AvailablePlatforms.size());

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

        return AvailablePlatforms;
    }

    /**
     *  This static method gets the platforms for the current alliance.
     */
    public static Collection getAssignedPlatformsFromArray(String[] assignedArrPlatforms) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsPlatform aimsPlatform = null;
        Collection AlliancePlatform = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       platform.platformId, platform.platformName ")
                .append("from ")
                .append("       com.netpace.aims.model.core.AimsPlatform platform ")
                .append("where ")
                .append("       platform.platformId in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrPlatforms, ","), "0"))
                .append("                          ) ")
                .append("order by  ")
                .append("       platform.platformName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsPlatform = new AimsPlatform();

                userValues = (Object[]) iter.next();

                aimsPlatform.setPlatformId((Long) userValues[0]);
                aimsPlatform.setPlatformName((String) userValues[1]);

                AlliancePlatform.add(aimsPlatform);
            }

            log.debug("No of records returned: " + AlliancePlatform.size());

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

        return AlliancePlatform;
    }

    /**
     *  This static method gets the industry focus from an array on Ind Focus ids.
     */
    public static Collection getAssignedIndFocusFromArray(String[] assignedArrIndustryFocus) throws HibernateException
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
                .append("		com.netpace.aims.model.masters.AimsIndustryFocu indFocus ")
                .append("where ")
                .append("		indFocus.industryId in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrIndustryFocus, ","), "0"))
                .append("							   ) ")
                .append("order by  ")
                .append("		indFocus.industryName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

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
            if (session != null)
                session.close();
        }

        return AllianceIndFocus;
    }

    /**
     *  This static method gets the regions for the current alliance.
     */
    public static Collection getAllianceRegions(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsRegion aimsRegion = null;
        Collection AllianceRegion = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		region.regionId, region.regionName ")
                .append("from ")
                .append("		com.netpace.aims.model.alliance.AimsAllianceRegion allianceRegion, ")
                .append("		com.netpace.aims.model.masters.AimsRegion region ")
                .append("where ")
                .append("		allianceRegion.allianceId = :allianceId ")
                .append("		and allianceRegion.region.regionId = region.regionId ")
                .append("order by  ")
                .append("		region.regionName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsRegion = new AimsRegion();

                userValues = (Object[]) iter.next();

                aimsRegion.setRegionId((Long) userValues[0]);
                aimsRegion.setRegionName((String) userValues[1]);

                AllianceRegion.add(aimsRegion);
            }

            log.debug("No of records returned: " + AllianceRegion.size());

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

        return AllianceRegion;
    }

    /**
     *  This static method gets the regions available (not assigned) to the current alliance.
     */
    public static Collection getAvailableRegions(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsRegion aimsRegion = null;
        Collection AvailableRegions = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		regionAvailable.regionId, regionAvailable.regionName ")
                .append("from ")
                .append("		com.netpace.aims.model.masters.AimsRegion regionAvailable ")
                .append("where ")
                .append("		regionAvailable.regionId  ")
                .append("		not in ( ")
                .append("			select ")
                .append("				regionAssigned.regionId ")
                .append("			from ")
                .append("				com.netpace.aims.model.alliance.AimsAllianceRegion allianceRegion, ")
                .append("				com.netpace.aims.model.masters.AimsRegion regionAssigned ")
                .append("			where ")
                .append("				allianceRegion.allianceId = :allianceId ")
                .append("				and allianceRegion.region.regionId = regionAssigned.regionId ")
                .append("				) ")
                .append("order by  ")
                .append("		regionAvailable.regionName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsRegion = new AimsRegion();

                userValues = (Object[]) iter.next();

                aimsRegion.setRegionId((Long) userValues[0]);
                aimsRegion.setRegionName((String) userValues[1]);

                AvailableRegions.add(aimsRegion);
            }

            log.debug("No of records returned: " + AvailableRegions.size());

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

        return AvailableRegions;
    }

    /**
     *  This static method gets the regions for the current alliance.
     */
    public static Collection getAllianceRegionsFromArray(String[] assignedArrRegions) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsRegion aimsRegion = null;
        Collection AllianceRegion = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		region.regionId, region.regionName ")
                .append("from ")
                .append("		com.netpace.aims.model.masters.AimsRegion region ")
                .append("where ")
                .append("		region.regionId in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrRegions, ","), "0"))
                .append("						   ) ")
                .append("order by  ")
                .append("		region.regionName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsRegion = new AimsRegion();

                userValues = (Object[]) iter.next();

                aimsRegion.setRegionId((Long) userValues[0]);
                aimsRegion.setRegionName((String) userValues[1]);

                AllianceRegion.add(aimsRegion);
            }

            log.debug("No of records returned: " + AllianceRegion.size());

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

        return AllianceRegion;
    }

    /**
     *  This static method gets the regions available (not assigned) to the current alliance.
     */
    public static Collection getAvailableRegionsFromArray(String[] assignedArrRegions) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsRegion aimsRegion = null;
        Collection AvailableRegions = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		region.regionId, region.regionName ")
                .append("from ")
                .append("		com.netpace.aims.model.masters.AimsRegion region ")
                .append("where ")
                .append("		region.regionId not in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrRegions, ","), "0"))
                .append("						   ) ")
                .append("order by  ")
                .append("		region.regionName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsRegion = new AimsRegion();

                userValues = (Object[]) iter.next();

                aimsRegion.setRegionId((Long) userValues[0]);
                aimsRegion.setRegionName((String) userValues[1]);

                AvailableRegions.add(aimsRegion);
            }

            log.debug("No of records returned: " + AvailableRegions.size());

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

        return AvailableRegions;
    }

    /**
     *  This static method gets the application details in the database which are available
     *  to the current alliance.
     */
    public static Collection getAllianceApplications(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Collection AllianceApplications = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AllianceApplicationForm allianceApplicationForm = null;

        try
        {

            queryStringBuffer
                .append("select  ")
                .append("		application.appsId, ")
                .append("		application.title, ")
                .append("		application.version, ")
                .append("		platform.platformName, ")
                .append("		phase.phaseName, ")
                .append("		contact.firstName, ")
                .append("		contact.lastName, ")
                .append("		application.createdDate ")
                .append("from ")
                .append("		com.netpace.aims.model.application.AimsApp application, ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance, ")
                .append("		com.netpace.aims.model.application.AimsLifecyclePhase phase, ")
                .append("		com.netpace.aims.model.core.AimsPlatform platform, ")
                .append("		com.netpace.aims.model.core.AimsContact contact, ")
                .append("		com.netpace.aims.model.core.AimsUser user ")
                .append("where ")
                .append("		alliance.allianceId = :alliance_id ")
                .append("		and application.aimsAllianceId = alliance.allianceId ")
                .append("		and application.aimsPlatformId = platform.platformId ")
                .append("		and application.aimsLifecyclePhaseId = phase.phaseId ")
                .append("		and application.aimsUserAppCreatedById = user.userId  ")
                .append("		and user.aimsContact.contactId = contact.contactId  ")
                .append("order by ")
                .append("		platform.platformName, ")
                .append("		application.title ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                allianceApplicationForm = new AllianceApplicationForm();

                userValues = (Object[]) iter.next();

                allianceApplicationForm.setAppsId((Long) userValues[0]);
                allianceApplicationForm.setAppTitle((String) userValues[1]);
                allianceApplicationForm.setAppVersion((String) userValues[2]);
                allianceApplicationForm.setAppPlatformName((String) userValues[3]);
                allianceApplicationForm.setAppPphaseName((String) userValues[4]);
                allianceApplicationForm.setSubmittedFirstName((String) userValues[5]);
                allianceApplicationForm.setSubmittedLastName((String) userValues[6]);
                allianceApplicationForm.setSubmittedDate((java.util.Date) userValues[7]);

                AllianceApplications.add(allianceApplicationForm);

            }

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

        return AllianceApplications;
    }

    /**
    *  This static method updates a given alliance with its associated industry focus.   
    */
    public static void UpdateAllianceCompInfo(
        Long allianceId,
        String companyName,
        String companyLegalName,
        String stateOfInc,
        String authRepName,
        String streetAddress1,
        String city,
        String state,
        String country,
        String zipCode,
        String webSiteUrl,
        String prevYearRevenues,
        String dbNumber,
        String isFinanceInfoPublic,
        String reasonsForRelationshipWithVZW,
        String existingContractsWithVZW,
        String allianceWithOtherCarriers,
        String commentsAllianceWithOtherCarriers,
        String otherIndustryFocus,
        String[] assignedIndFocusIds,
        String[] assignedRegionIds,
        String[] assignedPlatformIds,
        String currUserName,
        Long[] allianceCarrierIds,
        String remitTo,
        String remitAddress1,
        String remitAddress2,
        String remitCity,
        String remitCountry,
        String remitPostalCode,
        String remitState
       )
        throws AimsException, HibernateException
    {

        Session session = null;
        Transaction tx = null;
        AimsAllianceIndFocus allianceIndFocus = null;
        AimsAllianceRegion allianceRegion = null;
        AimsAlliancePlatform alliancePlatform = null;

        AimsAllianceVzwReasons aimsAllianceVzwReason = null;
        AimsVzwReasons aimsVzwReason = null;
        AimsAllianceCarriers aimsAllianceCarrier = null;
        AimsCarriers aimsCarrier = null;

        int delCount = 0;

        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            AimsAllianc alliance = (AimsAllianc) session.load(AimsAllianc.class, allianceId);

            if ((alliance.getStatus() != null) && (alliance.getStatus().equals(AimsConstants.ALLIANCE_STATUS_SUBMITTED)))
            {
                alliance.setCompanyName(companyName);
                alliance.setCompanyLegalName(companyLegalName);
            }

            alliance.setStateOfInc(stateOfInc);
            alliance.setAuthRepName(authRepName);
            alliance.setStreetAddress1(streetAddress1);
            alliance.setCity(city);
            alliance.setState(state);
            alliance.setZipCode(zipCode);
            alliance.setCountry(country);
            alliance.setWebSiteUrl(webSiteUrl);
            alliance.setPrevYearRevenues(prevYearRevenues);

            alliance.setDbNumber(dbNumber);
            alliance.setIsFinanceInfoPublic(isFinanceInfoPublic);
            alliance.setVzwRelationshipReasons(reasonsForRelationshipWithVZW);
            alliance.setVzwCurrentContracts(existingContractsWithVZW);
            alliance.setOtherCarrierAlliances(allianceWithOtherCarriers);

            alliance.setCommentsOtherCarrierAlliances(commentsAllianceWithOtherCarriers);
            alliance.setOtherIndustryFocus(otherIndustryFocus);

            alliance.setLastUpdatedBy(currUserName);
            alliance.setLastUpdatedDate(new Date());

            alliance.setRemitTo(remitTo);
            alliance.setRemitAddress1(remitAddress1);
            alliance.setRemitAddress2(remitAddress2);
            alliance.setRemitCity(remitCity);
            alliance.setRemitCountry(remitCountry);
            alliance.setRemitPostalCode(remitPostalCode);
            alliance.setRemitState(remitState);
            
            session.update(alliance);

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
                    allianceIndFocus.setIndustryFocus((AimsIndustryFocu) session.load(AimsIndustryFocu.class, new Long(assignedIndFocusIds[i])));
                    session.save(allianceIndFocus);
                }
            }

            delCount =
                session.delete(
                    "from com.netpace.aims.model.alliance.AimsAllianceRegion as allianceRegion where allianceRegion.allianceId = :allianceId",
                    allianceId,
                    new LongType());

            for (int i = 0; i < assignedRegionIds.length; i++)
            {
                allianceRegion = new AimsAllianceRegion();
                allianceRegion.setAllianceId(allianceId);
                allianceRegion.setRegion((AimsRegion) session.load(AimsRegion.class, new Long(assignedRegionIds[i])));
                session.save(allianceRegion);
            }

            if(assignedPlatformIds!=null && assignedPlatformIds.length>0)
            {
                delCount =
                    session.delete(
                        "from com.netpace.aims.model.alliance.AimsAlliancePlatform as platform where platform.allianceId = :allianceId",
                        allianceId,
                        new LongType());

                for (int i = 0; i < assignedPlatformIds.length; i++)
                {
                    alliancePlatform = new AimsAlliancePlatform();
                    alliancePlatform.setAllianceId(allianceId);
                    alliancePlatform.setPlatforms((AimsPlatform) session.load(AimsPlatform.class, new Long(assignedPlatformIds[i])));
                    session.save(alliancePlatform);
                }
            }            

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

            tx.commit();
        }

        catch (JDBCException je)
        {
            if (tx != null)
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
     *  This static method gets the all Aims Carriers available
     */
    public static Collection getAllAimsCarriers() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsCarriers aimsCarrier = null;
        Collection allAimsCarriersList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsCarrier.carrierId, aimsCarrier.carrierName ")
                .append("from ")
                .append("       com.netpace.aims.model.masters.AimsCarriers aimsCarrier ")
                .append("order by  ")
                .append("      aimsCarrier.sortOrder ");
            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsCarrier = new AimsCarriers();

                userValues = (Object[]) iter.next();

                aimsCarrier.setCarrierId((Long) userValues[0]);
                aimsCarrier.setCarrierName((String) userValues[1]);

                allAimsCarriersList.add(aimsCarrier);
            }

            log.debug("No of records returned: " + allAimsCarriersList .size());

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

        return allAimsCarriersList ;
    }

    /**
     *  This static method gets the all Aims Vzw Reasons available
     */
    public static Collection getAllVzwReasons() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsVzwReasons aimsVzwReason = null;
        Collection allAimsVzwReasonsList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsVzwReason.reasonsId, aimsVzwReason.reasonsOption, aimsVzwReason.reasonParentId ")
                .append("from ")
                .append("       com.netpace.aims.model.masters.AimsVzwReasons aimsVzwReason ")
                .append("order by  ")
                .append("      aimsVzwReason.sortOrder ");
            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsVzwReason = new AimsVzwReasons();

                userValues = (Object[]) iter.next();

                aimsVzwReason.setReasonsId((Long) userValues[0]);
                aimsVzwReason.setReasonsOption((String) userValues[1]);
                aimsVzwReason.setReasonParentId((Long) userValues[2]);

                allAimsVzwReasonsList.add(aimsVzwReason);
            }

            log.debug("No of records returned: " + allAimsVzwReasonsList .size());

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

        return allAimsVzwReasonsList ;
    }
    /**
     *  This static method gets Aims Vzw Reasons by ReasonIds
     */
    public static Collection getVzwReasonsByReasonId(Long[] reasonIds) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsVzwReasons aimsVzwReason = null;
        Collection allAimsVzwReasonsList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsVzwReason.reasonsId, aimsVzwReason.reasonsOption, aimsVzwReason.reasonParentId ")
                .append("from ")
                .append("       com.netpace.aims.model.masters.AimsVzwReasons aimsVzwReason ")
                .append("where ")
                .append("		aimsVzwReason.reasonsId in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertLongArrToString(reasonIds, ","), "0"))
                .append("						   ) ")
                .append("order by  ")
                .append("      aimsVzwReason.sortOrder ");
            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsVzwReason = new AimsVzwReasons();

                userValues = (Object[]) iter.next();

                aimsVzwReason.setReasonsId((Long) userValues[0]);
                aimsVzwReason.setReasonsOption((String) userValues[1]);
                aimsVzwReason.setReasonParentId((Long) userValues[2]);

                allAimsVzwReasonsList.add(aimsVzwReason);
            }

            log.debug("No of records returned: " + allAimsVzwReasonsList .size());

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

        return allAimsVzwReasonsList ;
    }
    
    /**
     *  This static method gets Aims Vzw Reasons Options by ReasonIds
     */
    public static String[] getVzwReasonOptionsByReasonIds(Long[] reasonIds) throws HibernateException
    {
        //Collection collection = null;
        Session session = null;
        //Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Collection vzwReasonOptionsList = null;

        try
        {
            queryStringBuffer
                .append("select ")
                .append("       aimsVzwReason.reasonsOption ")
                .append("from ")
                .append("       com.netpace.aims.model.masters.AimsVzwReasons aimsVzwReason ")
                .append("where ")
                .append("		aimsVzwReason.reasonsId in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertLongArrToString(reasonIds, ","), "0"))
                .append("						   ) ")
                .append("order by  ")
                .append("      aimsVzwReason.sortOrder ");
            session = DBHelper.getInstance().getSession();

            vzwReasonOptionsList = session.find(queryStringBuffer.toString());

            log.debug("No of records returned: " + vzwReasonOptionsList.size());

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

        return (String [])vzwReasonOptionsList.toArray(new String[0]) ;
    } //end getVzwReasonOptionsByReasonIds


    /**
     *  This static method gets the all Country Names available which are active i.e., activeYn = Y
     */
    public static Collection getAllCountries() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsCountries aimsCountry = null;
        Collection allAimsCountriesList = new ArrayList();

        Query query = null;

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsCountry.countryId, aimsCountry.countryName  ")
                .append("from ")
                .append("       com.netpace.aims.model.core.AimsCountries aimsCountry ")
                .append("       where UPPER(aimsCountry.activeYn) = :activeYn ")
                .append("order by  ")
                .append("      aimsCountry.countryId ");
            
            session = DBHelper.getInstance().getSession();

            query = session.createQuery(queryStringBuffer.toString());
            query.setString("activeYn", AimsConstants.YES_CHAR);
            collection = query.list();

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
            	aimsCountry = new AimsCountries();

                userValues = (Object[]) iter.next();

                aimsCountry.setCountryId((Long) userValues[0]);
                aimsCountry.setCountryName((String) userValues[1]);                

                allAimsCountriesList.add(aimsCountry);
            }

            log.debug("No of records returned in getAllCountries(): " + allAimsCountriesList .size());

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

        return allAimsCountriesList ;
    }    

}