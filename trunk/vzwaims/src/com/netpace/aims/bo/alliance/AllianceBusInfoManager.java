package com.netpace.aims.bo.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;
import oracle.jdbc.driver.OracleCallableStatement;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.alliance.AimsAllianceDevTech;
import com.netpace.aims.model.alliance.AimsAllianceLinesOfBusiness;
import com.netpace.aims.model.alliance.AimsAllianceRole;
import com.netpace.aims.model.alliance.AimsAllianceFinancing;
import com.netpace.aims.model.alliance.AimsAllianceDevelopments;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class is responsible for getting the BO for Business Info of alliances.
 * @author Rizwan Qazi
 */
public class AllianceBusInfoManager
{

    static Logger log = Logger.getLogger(AllianceBusInfoManager.class.getName());

    /**
     *  This static method gets the company information details of the current alliance   
     */
    public static Collection getAllianceBusInfo(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		alliance.companyName, ")
                .append("		alliance.dateEstablished, ")
                .append("		alliance.numFullTimeEmp, ")
                .append("		alliance.numPartTimeEmp, ")
                .append("		alliance.numInsideSalesEmp, ")
                .append("		alliance.numOutSalesEmp, ")
                .append("		alliance.numRetailEmp, ")
                .append("		alliance.numTeleMktgEmp, ")
                .append("		alliance.numTechnicianEmp, ")
                .append("		alliance.numWarehouseEmp, ")
                .append("		alliance.numSuppClericalEmp, ")
                .append("		alliance.numOtherEmp, ")
                .append("		alliance.companyCountryOfOrigin, ")
                .append("		alliance.prodAndServicesDesc, ")
                .append("		alliance.competetiveAdvantages, ")
                .append("		alliance.companyPresentationFileName, ")
                .append("		alliance.companyPresentationContentType, ")
                .append("		alliance.companyLogoFileName, ")
                .append("		alliance.companyLogoContentType, ")
                .append("		alliance.programGuideFileName, ")
                .append("		alliance.programGuideContentType, ")
                .append("		alliance.employeesRange, ")
                .append("		alliance.partnerBrand, ")
                .append("		alliance.outsourceDevPublisherName ")
                .append(" from ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance  ")
                .append(" where ")
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
     *  This static method gets the lines of business not assigned to the current alliance.
     */
    public static Collection getAvailableLinesOfBusiness(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsLinesOfBusiness aimsLinesOfBusiness = null;
        Collection AllianceLineOfBusiness = new ArrayList();

        try
        {
            queryStringBuffer
                .append(" select ")
                .append("		linesOfBusinessAvailable.lineOfBusinessId, linesOfBusinessAvailable.lineOfBusinessName ")
                .append(" from ")
                .append("		com.netpace.aims.model.masters.AimsLinesOfBusiness linesOfBusinessAvailable ")
                .append(" where ")
                .append("		linesOfBusinessAvailable.lineOfBusinessId  ")
                .append("		not in ( ")
                .append("			select ")
                .append("				linesOfBusinessAssigned.lineOfBusinessId ")
                .append("			from ")
                .append("				com.netpace.aims.model.alliance.AimsAllianceLinesOfBusiness allianceLinesOfBusiness, ")
                .append("				com.netpace.aims.model.masters.AimsLinesOfBusiness linesOfBusinessAssigned ")
                .append("			where ")
                .append("				allianceLinesOfBusiness.allianceId = :allianceId ")
                .append("				and allianceLinesOfBusiness.lineOfBusiness.lineOfBusinessId = linesOfBusinessAssigned.lineOfBusinessId ")
                .append("				) ")
                .append(" order by  ")
                .append("		linesOfBusinessAvailable.lineOfBusinessName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsLinesOfBusiness = new AimsLinesOfBusiness();

                userValues = (Object[]) iter.next();

                aimsLinesOfBusiness.setLineOfBusinessId((Long) userValues[0]);
                aimsLinesOfBusiness.setLineOfBusinessName((String) userValues[1]);

                AllianceLineOfBusiness.add(aimsLinesOfBusiness);
            }

            log.debug("No of records returned: " + AllianceLineOfBusiness.size());

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

        return AllianceLineOfBusiness;
    }

    /**
     *  This static method gets the lines of business not assigned to the current alliance.
     */
    public static Collection getAvailableLinesOfBusinessFromArray(String[] assignedArrLinesOfBusiness) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsLinesOfBusiness aimsLinesOfBusiness = null;
        Collection AllianceLineOfBusiness = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		linesOfBusinessAvailable.lineOfBusinessId, linesOfBusinessAvailable.lineOfBusinessName ")
                .append(" from ")
                .append("		com.netpace.aims.model.masters.AimsLinesOfBusiness linesOfBusinessAvailable ")
                .append(" where ")
                .append("		linesOfBusinessAvailable.lineOfBusinessId  ")
                .append("		not in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrLinesOfBusiness, ","), "0"))
                .append("				) ")
                .append(" order by  ")
                .append("		linesOfBusinessAvailable.lineOfBusinessName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsLinesOfBusiness = new AimsLinesOfBusiness();

                userValues = (Object[]) iter.next();

                aimsLinesOfBusiness.setLineOfBusinessId((Long) userValues[0]);
                aimsLinesOfBusiness.setLineOfBusinessName((String) userValues[1]);

                AllianceLineOfBusiness.add(aimsLinesOfBusiness);
            }

            log.debug("No of records returned: " + AllianceLineOfBusiness.size());

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

        return AllianceLineOfBusiness;
    }

    /**
     *  This static method gets the lines of business for the current alliance.
     */
    public static Collection getAllianceLinesOfBusiness(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsLinesOfBusiness aimsLinesOfBusiness = null;
        Collection AllianceLineOfBusiness = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		linesOfBusinessAssigned.lineOfBusinessId, linesOfBusinessAssigned.lineOfBusinessName ")
                .append(" from ")
                .append("		com.netpace.aims.model.alliance.AimsAllianceLinesOfBusiness allianceLinesOfBusiness, ")
                .append("		com.netpace.aims.model.masters.AimsLinesOfBusiness linesOfBusinessAssigned ")
                .append(" where ")
                .append("		allianceLinesOfBusiness.allianceId = :allianceId  ")
                .append("		and allianceLinesOfBusiness.lineOfBusiness.lineOfBusinessId = linesOfBusinessAssigned.lineOfBusinessId ")
                .append(" order by  ")
                .append("		linesOfBusinessAssigned.lineOfBusinessName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsLinesOfBusiness = new AimsLinesOfBusiness();

                userValues = (Object[]) iter.next();

                aimsLinesOfBusiness.setLineOfBusinessId((Long) userValues[0]);
                aimsLinesOfBusiness.setLineOfBusinessName((String) userValues[1]);

                AllianceLineOfBusiness.add(aimsLinesOfBusiness);
            }

            log.debug("No of records returned: " + AllianceLineOfBusiness.size());

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

        return AllianceLineOfBusiness;
    }

    /**
     *  This static method gets the lines of business for the current alliance.
     */
    public static Collection getAllianceLinesOfBusinessFromArray(String[] assignedArrLinesOfBusiness) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsLinesOfBusiness aimsLinesOfBusiness = null;
        Collection AllianceLineOfBusiness = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		linesOfBusinessAvailable.lineOfBusinessId, linesOfBusinessAvailable.lineOfBusinessName ")
                .append(" from ")
                .append("		com.netpace.aims.model.masters.AimsLinesOfBusiness linesOfBusinessAvailable ")
                .append(" where ")
                .append("		linesOfBusinessAvailable.lineOfBusinessId  ")
                .append("		     in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrLinesOfBusiness, ","), "0"))
                .append("				) ")
                .append(" order by  ")
                .append("		linesOfBusinessAvailable.lineOfBusinessName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsLinesOfBusiness = new AimsLinesOfBusiness();

                userValues = (Object[]) iter.next();

                aimsLinesOfBusiness.setLineOfBusinessId((Long) userValues[0]);
                aimsLinesOfBusiness.setLineOfBusinessName((String) userValues[1]);

                AllianceLineOfBusiness.add(aimsLinesOfBusiness);
            }

            log.debug("No of records returned: " + AllianceLineOfBusiness.size());

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

        return AllianceLineOfBusiness;
    }

    /**
     * This static method gets the dev technologies not assigned to the current alliance.
     */
    public static Collection getAvailableDevTechnologies(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsDevTechnology aimsDevTechnology = null;
        Collection AllianceDevTechnologies = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		devTechnologyAvailable.devTechnologyId, devTechnologyAvailable.devTechnologyName ")
                .append(" from ")
                .append("		com.netpace.aims.model.masters.AimsDevTechnology devTechnologyAvailable ")
                .append(" where ")
                .append("		devTechnologyAvailable.devTechnologyId  ")
                .append("		not in ( ")
                .append("			select ")
                .append("				devTechnologyAssigned.devTechnologyId ")
                .append("			from ")
                .append("				com.netpace.aims.model.alliance.AimsAllianceDevTech allianceDevTechnology, ")
                .append("				com.netpace.aims.model.masters.AimsDevTechnology devTechnologyAssigned ")
                .append("			where ")
                .append("				allianceDevTechnology.allianceId = :allianceId ")
                .append("				and allianceDevTechnology.devTechnology.devTechnologyId = devTechnologyAssigned.devTechnologyId ")
                .append("				) ")
                .append(" order by  ")
                .append("		devTechnologyAvailable.devTechnologyName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsDevTechnology = new AimsDevTechnology();
                userValues = (Object[]) iter.next();
                aimsDevTechnology.setDevTechnologyId((Long) userValues[0]);
                aimsDevTechnology.setDevTechnologyName((String) userValues[1]);
                AllianceDevTechnologies.add(aimsDevTechnology);
            }

            log.debug("No of records returned: " + AllianceDevTechnologies.size());

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

        return AllianceDevTechnologies;
    }

    /**
     * This static method gets the dev technologies not assigned to the current alliance.
     */
    public static Collection getAvailableDevTechnologiesFromArray(String[] assignedArrDevTechnologies) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsDevTechnology aimsDevTechnology = null;
        Collection AllianceDevTechnologies = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		devTechnologyAvailable.devTechnologyId, devTechnologyAvailable.devTechnologyName ")
                .append(" from ")
                .append("		com.netpace.aims.model.masters.AimsDevTechnology devTechnologyAvailable ")
                .append(" where ")
                .append("		devTechnologyAvailable.devTechnologyId  ")
                .append("		not in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrDevTechnologies, ","), "0"))
                .append("				) ")
                .append(" order by  ")
                .append("		devTechnologyAvailable.devTechnologyName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsDevTechnology = new AimsDevTechnology();

                userValues = (Object[]) iter.next();

                aimsDevTechnology.setDevTechnologyId((Long) userValues[0]);
                aimsDevTechnology.setDevTechnologyName((String) userValues[1]);

                AllianceDevTechnologies.add(aimsDevTechnology);
            }

            log.debug("No of records returned: " + AllianceDevTechnologies.size());

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

        return AllianceDevTechnologies;
    }

    /**
     *  This static method gets the dev technologies for the current alliance.
     */
    public static Collection getAllianceDevTechnologies(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsDevTechnology aimsDevTechnology = null;
        Collection AllianceDevTechnologies = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		devTechnologyAssigned.devTechnologyId, devTechnologyAssigned.devTechnologyName ")
                .append(" from ")
                .append("		com.netpace.aims.model.alliance.AimsAllianceDevTech allianceDevTechnology, ")
                .append("		com.netpace.aims.model.masters.AimsDevTechnology devTechnologyAssigned ")
                .append(" where ")
                .append("		allianceDevTechnology.allianceId = :allianceId  ")
                .append("		and allianceDevTechnology.devTechnology.devTechnologyId = devTechnologyAssigned.devTechnologyId ")
                .append(" order by  ")
                .append("		devTechnologyAssigned.devTechnologyName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsDevTechnology = new AimsDevTechnology();

                userValues = (Object[]) iter.next();

                aimsDevTechnology.setDevTechnologyId((Long) userValues[0]);
                aimsDevTechnology.setDevTechnologyName((String) userValues[1]);

                AllianceDevTechnologies.add(aimsDevTechnology);
            }

            log.debug("No of records returned: " + AllianceDevTechnologies.size());

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

        return AllianceDevTechnologies;
    }

    /**
     *  This static method gets the dev technolog Ids for the current alliance.
     */
    public static String[] getAllianceDevTechnologyIds(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Collection allianceDevTechnologIdList = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		devTechnologyAssigned.devTechnologyId, devTechnologyAssigned.devTechnologyName ")
                .append(" from ")
                .append("		com.netpace.aims.model.alliance.AimsAllianceDevTech allianceDevTechnology, ")
                .append("		com.netpace.aims.model.masters.AimsDevTechnology devTechnologyAssigned ")
                .append(" where ")
                .append("		allianceDevTechnology.allianceId = :allianceId  ")
                .append("		and allianceDevTechnology.devTechnology.devTechnologyId = devTechnologyAssigned.devTechnologyId ")
                .append(" order by  ")
                .append("		devTechnologyAssigned.devTechnologyName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {

                userValues = (Object[]) iter.next();
                allianceDevTechnologIdList.add(((Long)userValues[0]).toString());

            }

            log.debug("No of records returned: " + allianceDevTechnologIdList.size());

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

        return (String [] )allianceDevTechnologIdList.toArray(new String[0]);
    }

    /**
     *  This static method gets the developments for the current alliance.
     */
    public static Collection getAllianceDevelopments(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Collection allianceDevelopmentsList = new ArrayList();
        AimsDevelopments aimsDevelopment = null;

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		aimsDevelopment.developmentId, aimsDevelopment.developmentType ")
                .append(" from ")
                .append("		com.netpace.aims.model.alliance.AimsAllianceDevelopments allianceDevelopment, ")
                .append("		com.netpace.aims.model.masters.AimsDevelopments aimsDevelopment ")
                .append(" where ")
                .append("		allianceDevelopment.allianceId = :allianceId  ")
                .append("		and allianceDevelopment.development.developmentId = aimsDevelopment.developmentId ")
                .append(" order by  ")
                .append("		aimsDevelopment.developmentType  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsDevelopment = new AimsDevelopments();
                userValues = (Object[]) iter.next();
                aimsDevelopment.setDevelopmentId((Long)userValues[0]);
                aimsDevelopment.setDevelopmentType((String)userValues[1]);
                allianceDevelopmentsList.add(aimsDevelopment);

            }

            log.debug("No of records returned: " + allianceDevelopmentsList.size());

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

        return allianceDevelopmentsList;
    }

    /**
     *  This static method gets the development Ids for the current alliance.
     */
    public static Long[] getAllianceDevelopmentIds(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Collection allianceDevelopmentsIdList = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		aimsDevelopment.developmentId, aimsDevelopment.developmentType ")
                .append(" from ")
                .append("		com.netpace.aims.model.alliance.AimsAllianceDevelopments allianceDevelopment, ")
                .append("		com.netpace.aims.model.masters.AimsDevelopments aimsDevelopment ")
                .append(" where ")
                .append("		allianceDevelopment.allianceId = :allianceId  ")
                .append("		and allianceDevelopment.development.developmentId = aimsDevelopment.developmentId ")
                .append(" order by  ")
                .append("		aimsDevelopment.developmentType  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {

                userValues = (Object[]) iter.next();
                allianceDevelopmentsIdList.add((Long)userValues[0]);

            }

            log.debug("No of records returned: " + allianceDevelopmentsIdList.size());

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

        return (Long [] )allianceDevelopmentsIdList.toArray(new Long[0]);
    }

    /**
     *  This static method gets the dev technologies for the current alliance.
     */
    public static Collection getAllianceDevTechnologiesFromArray(String[] assignedArrDevTechnologies) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsDevTechnology aimsDevTechnology = null;
        Collection AllianceDevTechnologies = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		devTechnologyAvailable.devTechnologyId, devTechnologyAvailable.devTechnologyName ")
                .append(" from ")
                .append("		com.netpace.aims.model.masters.AimsDevTechnology devTechnologyAvailable ")
                .append(" where ")
                .append("		devTechnologyAvailable.devTechnologyId  ")
                .append("		 in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrDevTechnologies, ","), "0"))
                .append("			) ")
                .append(" order by  ")
                .append("		devTechnologyAvailable.devTechnologyName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsDevTechnology = new AimsDevTechnology();

                userValues = (Object[]) iter.next();

                aimsDevTechnology.setDevTechnologyId((Long) userValues[0]);
                aimsDevTechnology.setDevTechnologyName((String) userValues[1]);

                AllianceDevTechnologies.add(aimsDevTechnology);
            }

            log.debug("No of records returned: " + AllianceDevTechnologies.size());

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

        return AllianceDevTechnologies;
    }

    /**
     * This static method gets the roles of alliance not assigned to the current alliance.
     */
    public static Collection getAvailableRoles(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsRolesOfAlliance aimsRolesOfAlliance = null;
        Collection AllianceRolesOfAlliance = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		roleAvailable.roleId, roleAvailable.roleName ")
                .append(" from ")
                .append("		com.netpace.aims.model.masters.AimsRolesOfAlliance roleAvailable ")
                .append(" where ")
                .append("		roleAvailable.roleId  ")
                .append("		not in ( ")
                .append("			select ")
                .append("				roleAssigned.roleId ")
                .append("			from ")
                .append("				com.netpace.aims.model.alliance.AimsAllianceRole allianceRole, ")
                .append("				com.netpace.aims.model.masters.AimsRolesOfAlliance roleAssigned ")
                .append("			where ")
                .append("				allianceRole.allianceId = :allianceId ")
                .append("				and allianceRole.roleOfAlliance.roleId = roleAssigned.roleId ")
                .append("				) ")
                .append(" order by  ")
                .append("		roleAvailable.roleName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsRolesOfAlliance = new AimsRolesOfAlliance();

                userValues = (Object[]) iter.next();

                aimsRolesOfAlliance.setRoleId((Long) userValues[0]);
                aimsRolesOfAlliance.setRoleName((String) userValues[1]);

                AllianceRolesOfAlliance.add(aimsRolesOfAlliance);
            }

            log.debug("No of records returned: " + AllianceRolesOfAlliance.size());

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

        return AllianceRolesOfAlliance;
    }

    /**
     * This static method gets the select roles from an array of role_ids.
     */
    public static Collection getAvailableRolesFromArray(String[] assignedArrRolesOfAlliance) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsRolesOfAlliance aimsRolesOfAlliance = null;
        Collection AllianceRolesOfAlliance = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		roleAvailable.roleId, roleAvailable.roleName ")
                .append(" from ")
                .append("		com.netpace.aims.model.masters.AimsRolesOfAlliance roleAvailable ")
                .append(" where ")
                .append("		roleAvailable.roleId  ")
                .append("		not in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrRolesOfAlliance, ","), "0"))
                .append("				) ")
                .append(" order by  ")
                .append("		roleAvailable.roleName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsRolesOfAlliance = new AimsRolesOfAlliance();

                userValues = (Object[]) iter.next();

                aimsRolesOfAlliance.setRoleId((Long) userValues[0]);
                aimsRolesOfAlliance.setRoleName((String) userValues[1]);

                AllianceRolesOfAlliance.add(aimsRolesOfAlliance);
            }

            log.debug("No of records returned: " + AllianceRolesOfAlliance.size());

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

        return AllianceRolesOfAlliance;
    }

    /**
     * This static method gets the roles of alliance assigned to the current alliance.
     */
    public static Collection getAllianceRoles(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsRolesOfAlliance aimsRolesOfAlliance = null;
        Collection AllianceRolesOfAlliance = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		roleAssigned.roleId, roleAssigned.roleName ")
                .append(" from ")
                .append("		com.netpace.aims.model.alliance.AimsAllianceRole allianceRole, ")
                .append("		com.netpace.aims.model.masters.AimsRolesOfAlliance roleAssigned ")
                .append(" where ")
                .append("		allianceRole.allianceId = :allianceId ")
                .append("		and allianceRole.roleOfAlliance.roleId = roleAssigned.roleId ")
                .append(" order by  ")
                .append("		roleAssigned.roleName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsRolesOfAlliance = new AimsRolesOfAlliance();

                userValues = (Object[]) iter.next();

                aimsRolesOfAlliance.setRoleId((Long) userValues[0]);
                aimsRolesOfAlliance.setRoleName((String) userValues[1]);

                AllianceRolesOfAlliance.add(aimsRolesOfAlliance);
            }

            log.debug("No of records returned: " + AllianceRolesOfAlliance.size());

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

        return AllianceRolesOfAlliance;
    }

    /**
     * This static method gets the roles of alliance assigned to the current alliance.
     */
    public static Collection getAllianceRolesFromArray(String[] assignedArrRolesOfAlliance) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsRolesOfAlliance aimsRolesOfAlliance = null;
        Collection AllianceRolesOfAlliance = new ArrayList();

        try
        {

            queryStringBuffer
                .append(" select ")
                .append("		roleAvailable.roleId, roleAvailable.roleName ")
                .append(" from ")
                .append("		com.netpace.aims.model.masters.AimsRolesOfAlliance roleAvailable ")
                .append(" where ")
                .append("		roleAvailable.roleId  ")
                .append("		 in ( ")
                .append(StringFuncs.initializeStringGetParam(StringFuncs.ConvertArrToString(assignedArrRolesOfAlliance, ","), "0"))
                .append("			) ")
                .append(" order by  ")
                .append("		roleAvailable.roleName  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsRolesOfAlliance = new AimsRolesOfAlliance();

                userValues = (Object[]) iter.next();

                aimsRolesOfAlliance.setRoleId((Long) userValues[0]);
                aimsRolesOfAlliance.setRoleName((String) userValues[1]);

                AllianceRolesOfAlliance.add(aimsRolesOfAlliance);
            }

            log.debug("No of records returned: " + AllianceRolesOfAlliance.size());

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

        return AllianceRolesOfAlliance;
    }

     /**
     *  This static method gets the AllianceFinancingIds for the current alliance.
     */
    public static Long[] getAllianceFinancingOptionIds(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        Collection allianceFinancingOptionIdList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsFinancingOption.financingOptionsId, aimsFinancingOption.financingOptions ")
                .append("from ")
                .append("       com.netpace.aims.model.alliance.AimsAllianceFinancing aimsAllianceFinancing, ")
                .append("       com.netpace.aims.model.masters.AimsFinancingOptions aimsFinancingOption ")
                .append("where ")
                .append("       aimsAllianceFinancing.allianceId = :allianceId ")
                .append("       and aimsAllianceFinancing.finance.financingOptionsId = aimsFinancingOption.financingOptionsId ")
                .append("order by  ")
                .append("       aimsFinancingOption.financingOptionsId  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                userValues = (Object[]) iter.next();
                allianceFinancingOptionIdList.add((Long)userValues[0]);
            }

            log.debug("No of records returned: " + allianceFinancingOptionIdList.size());

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

        return (Long[])allianceFinancingOptionIdList.toArray(new Long[0]);
    }

    /**
     *  This static method gets the AllianceFinancing for the current alliance.
     */
    public static Collection getAllianceFinancingOptions(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsFinancingOptions aimsFinancingOption = null;

        Collection allianceFinancingOptionList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsFinancingOption.financingOptionsId, aimsFinancingOption.financingOptions ")
                .append("from ")
                .append("       com.netpace.aims.model.alliance.AimsAllianceFinancing aimsAllianceFinancing, ")
                .append("       com.netpace.aims.model.masters.AimsFinancingOptions aimsFinancingOption ")
                .append("where ")
                .append("       aimsAllianceFinancing.allianceId = :allianceId ")
                .append("       and aimsAllianceFinancing.finance.financingOptionsId = aimsFinancingOption.financingOptionsId ")
                .append("order by  ")
                .append("       aimsFinancingOption.financingOptionsId  ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                userValues = (Object[]) iter.next();
                aimsFinancingOption = new AimsFinancingOptions();
                aimsFinancingOption.setFinancingOptionsId((Long)userValues[0]);
                aimsFinancingOption.setFinancingOptions((String)userValues[1]);
                allianceFinancingOptionList.add(aimsFinancingOption);
            }

            log.debug("No of records returned: " + allianceFinancingOptionList.size());

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

        return allianceFinancingOptionList;
    }


    /**
     *  This static method gets the industry focus from an array on Ind Focus ids.
     */
    public static Object[] getAllianceResource(String resourceName, Long alliance_id, String user_type) throws HibernateException
    {
        Session session = null;
        Collection collection = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Object[] resourceValues = null;

        log.debug("resourceName in AllBusInfoManager " + resourceName);
        log.debug("alliance_id in AllBusInfoManager " + alliance_id);
        log.debug("user_type in AllBusInfoManager " + user_type);

        try
        {

            queryStringBuffer.append("select ");

            if (resourceName.equalsIgnoreCase("companyLogo"))
            {
                queryStringBuffer.append(" alliance.companyLogo, alliance.companyLogoFileName, alliance.companyLogoContentType ");
            }

            if (resourceName.equalsIgnoreCase("companyPresentation"))
            {
                queryStringBuffer.append(" alliance.companyPresentation, alliance.companyPresentationFileName, alliance.companyPresentationContentType ");
            }

            if (resourceName.equalsIgnoreCase("progGuide"))
            {
                queryStringBuffer.append(" alliance.programGuide, alliance.programGuideFileName, alliance.programGuideContentType ");
            }

            queryStringBuffer.append(" from com.netpace.aims.model.core.AimsAllianc alliance ").append("where alliance.allianceId = :allianceId ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                resourceValues = (Object[]) iter.next();
            }

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

        return resourceValues;
    }
    
    public static Object[] getJMAAllianceResource(String resourceName, Long alliance_id, String user_type) throws HibernateException
    {
        Session session = null;
        Collection collection = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Object[] resourceValues = null;

        log.debug("resourceName in AllBusInfoManager " + resourceName);
        log.debug("alliance_id in AllBusInfoManager " + alliance_id);
        log.debug("user_type in AllBusInfoManager " + user_type);

        try
        {

            queryStringBuffer.append("select ");

            if (resourceName.equalsIgnoreCase("winOpportunities"))
            {
                queryStringBuffer.append(" alliance.winOpportunities, alliance.winOpportunitiesFileName, alliance.winOpportunitiesContentType ");
            }

            if (resourceName.equalsIgnoreCase("prodPresentation"))
            {
                queryStringBuffer.append(" alliance.prodPresentation, alliance.prodPresentationFileName, alliance.prodPresentationContentType ");
            }

           
            queryStringBuffer.append(" from com.netpace.aims.model.core.AimsJMAAllianceFiles alliance ").append("where alliance.allianceId = :allianceId ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                resourceValues = (Object[]) iter.next();
            }

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

        return resourceValues;
    }

    /**
    *  This static method updates a given alliance's business info.   
    */
    public static void UpdateAllianceBusInfo(
        Long allianceId,
        String companyName,
        String dateEstablished,
        String fullTimeEmployees,
        String partTimeEmployees,
        String insideSalesEmployees,
        String outsideSalesEmployees,
        String retailSalesEmployees,
        String teleMarketingEmployees,
        String technicianEmployees,
        String warehouseEmployees,
        String supportingEmployees,
        String otherEmployees,
        String countryOfOrigin,
        String prodServicesDesc,
        String competitiveAdvantages,
        String partner,
        Long companyPresentationTempFileId,
        Long companyLogoTempFileId,
        Long progGuideTempFileId,
        String[] assignedArrRolesOfAlliance,
        String[] assignedArrDevTechnologies,
        String[] assignedArrLinesOfBusiness,
        String currUserName,
        String employeesRange,
        Long[] allianceFinancingIds,
        Long[] allianceDevelopmentIds,
        String outsourceDevelopmentPublisherName)
        throws AimsException, HibernateException
    {

        Session session = null;
        Transaction tx = null;
        AimsAllianceRole allianceRole = null;
        AimsAllianceDevTech allianceDevTech = null;
        AimsAllianceLinesOfBusiness allianceLinesOfBusiness = null;
        int delCount = 0;
        String ALLIANCE_COMPANY_PRESENTATION_BLOB_DB_INFO[] = { "company_present", "aims_alliances", "alliance_id" };
        String ALLIANCE_COMPANY_LOGO_BLOB_DB_INFO[] = { "company_logo", "aims_alliances", "alliance_id" };
        String ALLIANCE_PROG_GUIDE_BLOB_DB_INFO[] = { "prog_guide", "aims_alliances", "alliance_id" };

        AimsAllianceFinancing allianceFinancing = null;
        AimsFinancingOptions aimsFinancingOption = null;

        AimsDevelopments aimsDevelopment = null;
        AimsAllianceDevelopments allianceDevelopment = null;

        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            AimsAllianc alliance = (AimsAllianc) session.load(AimsAllianc.class, allianceId);

            alliance.setCompanyName(companyName);
            alliance.setDateEstablished(Utility.convertToDate(dateEstablished, AimsConstants.DATE_FORMAT));

            alliance.setNumFullTimeEmp("0");
            alliance.setNumPartTimeEmp(new Long(0));
            alliance.setNumInsideSalesEmp(new Long(0));
            alliance.setNumOutSalesEmp(new Long(0));
            alliance.setNumRetailEmp(new Long(0));
            alliance.setNumTeleMktgEmp(new Long(0));
            alliance.setNumTechnicianEmp(new Long(0));
            alliance.setNumWarehouseEmp(new Long(0));
            alliance.setNumSuppClericalEmp(new Long(0));
            alliance.setNumOtherEmp(new Long(0));

            alliance.setCompanyCountryOfOrigin(countryOfOrigin);
            alliance.setProdAndServicesDesc(prodServicesDesc);
            alliance.setCompetetiveAdvantages(competitiveAdvantages);
            alliance.setPartnerBrand(partner);
            alliance.setLastUpdatedBy(currUserName);
            alliance.setLastUpdatedDate(new Date());
            alliance.setEmployeesRange(employeesRange);
            alliance.setOutsourceDevPublisherName(outsourceDevelopmentPublisherName);

            session.update(alliance);
            session.flush();

            /*
                UPDATING   DATABASE FOR IMAGES
            */

            java.sql.Connection ConOra = null;
            ConOra = session.connection();

            //Copying Images From Temp Table (if present)     
            TempFilesManager.copyImageFromTemp(
                ConOra,
                companyPresentationTempFileId,
                alliance.getAllianceId(),
                currUserName,
                ALLIANCE_COMPANY_PRESENTATION_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, companyLogoTempFileId, alliance.getAllianceId(), currUserName, ALLIANCE_COMPANY_LOGO_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, progGuideTempFileId, alliance.getAllianceId(), currUserName, ALLIANCE_PROG_GUIDE_BLOB_DB_INFO);

            session.flush();

            delCount =
                session.delete(
                    "from com.netpace.aims.model.alliance.AimsAllianceRole as allianceRole where allianceRole.allianceId = :allianceId",
                    allianceId,
                    new LongType());

            for (int i = 0; i < assignedArrRolesOfAlliance.length; i++)
            {
                allianceRole = new AimsAllianceRole();
                allianceRole.setAllianceId(allianceId);
                allianceRole.setRoleOfAlliance((AimsRolesOfAlliance) session.load(AimsRolesOfAlliance.class, new Long(assignedArrRolesOfAlliance[i])));
                session.save(allianceRole);
            }

            delCount =
                session.delete(
                    "from com.netpace.aims.model.alliance.AimsAllianceDevTech as allianceDevTech where allianceDevTech.allianceId = :allianceId",
                    allianceId,
                    new LongType());

            if(assignedArrDevTechnologies!=null && assignedArrDevTechnologies.length>0) {
                for (int i = 0; i < assignedArrDevTechnologies.length; i++)
                {
                    allianceDevTech = new AimsAllianceDevTech();
                    allianceDevTech.setAllianceId(allianceId);
                    allianceDevTech.setDevTechnology((AimsDevTechnology) session.load(AimsDevTechnology.class, new Long(assignedArrDevTechnologies[i])));
                    session.save(allianceDevTech);
                }
            }

            delCount =
                session.delete(
                    "from com.netpace.aims.model.alliance.AimsAllianceLinesOfBusiness as allianceLinesOfBusiness where allianceLinesOfBusiness.allianceId = :allianceId",
                    allianceId,
                    new LongType());

            if(assignedArrLinesOfBusiness!=null && assignedArrLinesOfBusiness.length>0) {
                for (int i = 0; i < assignedArrLinesOfBusiness.length; i++)
                {
                    allianceLinesOfBusiness = new AimsAllianceLinesOfBusiness();
                    allianceLinesOfBusiness.setAllianceId(allianceId);
                    allianceLinesOfBusiness.setLineOfBusiness(
                        (AimsLinesOfBusiness) session.load(AimsLinesOfBusiness.class, new Long(assignedArrLinesOfBusiness[i])));
                    session.save(allianceLinesOfBusiness);
                }
            }

            //delete previous AimsAllianceFinancing
            delCount =
                session.delete(
                    "from com.netpace.aims.model.alliance.AimsAllianceFinancing as allianceFinancing where allianceFinancing.allianceId = :allianceId",
                    allianceId,
                    new LongType());

            if(allianceFinancingIds!=null && allianceFinancingIds.length>0)
            {
                for (int i = 0; i < allianceFinancingIds.length; i++)
                {
                    allianceFinancing = new AimsAllianceFinancing();
                    aimsFinancingOption = new AimsFinancingOptions();
                    allianceFinancing.setAllianceId(allianceId);
                    aimsFinancingOption.setFinancingOptionsId(allianceFinancingIds[i]);
                    allianceFinancing.setFinance(aimsFinancingOption);
                    session.save(allianceFinancing);
                }
            }

            //delete previous AimsAllianceFinancing
            delCount =
                session.delete(
                    "from com.netpace.aims.model.alliance.AimsAllianceDevelopments as aimsAllianceDevelopment where aimsAllianceDevelopment.allianceId = :allianceId",
                    allianceId,
                    new LongType());

            if(allianceDevelopmentIds!=null && allianceDevelopmentIds.length>0)
            {
                for (int i = 0; i < allianceDevelopmentIds.length; i++)
                {
                    allianceDevelopment = new AimsAllianceDevelopments();
                    aimsDevelopment = new AimsDevelopments();
                    allianceDevelopment.setAllianceId(allianceId);
                    aimsDevelopment.setDevelopmentId(allianceDevelopmentIds[i]);
                    allianceDevelopment.setDevelopment (aimsDevelopment);
                    session.save(allianceDevelopment);
                }
            }


            checkForEmptyBlobs(session.connection(), allianceId);

            tx.commit();
        }

        catch (AimsException ax)
        {
            if (tx != null)
                tx.rollback();
            throw ax;
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

        catch (Exception ex)
        {
            if (tx != null)
            {

                tx.rollback();
            }
            ex.printStackTrace();
        }

        finally
        {
            session.close();
        }
    }

    public static void checkForEmptyBlobs(java.sql.Connection ConOra, Long allianceId) throws AimsException, Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            statement = ConOra.prepareCall("call AIMS_ALLIANCES_PKG.check_uploaded_files(?,?)");
            statement.setLong(1, allianceId.longValue());
            statement.registerOutParameter(2, java.sql.Types.VARCHAR);
            statement.execute();

            String resultString = ((OracleCallableStatement) statement).getString(2);

            if ((resultString != null) && (resultString.length() > 0))
            {
                AimsException aimsException = new AimsException();
                aimsException.setMessageKey("error.uploading.files");

                Object[] args = { resultString };
                aimsException.setMessageArgs(args);

                System.out.println("AM: Exception in checkForEmptyBlobs() for allianceId: " + allianceId + ". Problem Columns are: " + resultString);
                throw aimsException;
            }
        }
        catch (AimsException ax)
        {
            throw ax;
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
     *  This static method gets the all Development Technologies available
     */
    public static Collection getAllDevelopmentTechnologies() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsDevTechnology devTechnology = null;
        Collection allDevelopmentTechnologies = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsDevTech.devTechnologyId, aimsDevTech.devTechnologyName ")
                .append("from ")
                .append("       com.netpace.aims.model.masters.AimsDevTechnology aimsDevTech ")
                .append("order by  ")
                .append("      aimsDevTech.devTechnologyName  ");
            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                devTechnology = new AimsDevTechnology();

                userValues = (Object[]) iter.next();

                devTechnology.setDevTechnologyId((Long) userValues[0]);
                devTechnology.setDevTechnologyName((String) userValues[1]);

                allDevelopmentTechnologies.add(devTechnology);
            }

            log.debug("No of records returned: " + allDevelopmentTechnologies.size());

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

        return allDevelopmentTechnologies;
    }

    /**
     *  This static method gets the all developments available
     */
    public static Collection getAllDevelopments() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsDevelopments development = null;
        Collection allDevelopmentsList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       development.developmentId, development.developmentType ")
                .append("from ")
                .append("       com.netpace.aims.model.masters.AimsDevelopments development ")
                .append("order by  ")
                .append("      development.developmentType ");
            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                development = new AimsDevelopments();

                userValues = (Object[]) iter.next();

                development.setDevelopmentId((Long) userValues[0]);
                development.setDevelopmentType((String) userValues[1]);

                allDevelopmentsList.add(development);
            }

            log.debug("No of records returned: " + allDevelopmentsList .size());

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

        return allDevelopmentsList ;
    }

    /**
     *  This static method gets the all AimsFinancingOptions available
     */
    public static Collection getAllAimsFinancingOptions() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsFinancingOptions aimsFinancingOption = null;
        Collection allAimsCarriersList = new ArrayList();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("       aimsFinancingOption.financingOptionsId, aimsFinancingOption.financingOptions ")
                .append("from ")
                .append("       com.netpace.aims.model.masters.AimsFinancingOptions aimsFinancingOption ")
                .append("order by  ")
                .append("      aimsFinancingOption.financingOptionsId ");
            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsFinancingOption = new AimsFinancingOptions();

                userValues = (Object[]) iter.next();

                aimsFinancingOption.setFinancingOptionsId((Long) userValues[0]);
                aimsFinancingOption.setFinancingOptions((String) userValues[1]);

                allAimsCarriersList.add(aimsFinancingOption);
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

}