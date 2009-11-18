package com.netpace.aims.bo.alliance;

import java.sql.CallableStatement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.alliance.AimsAllianceMusic;
import com.netpace.aims.model.alliance.AimsAllianceMusicProdType;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.system.AimsEmailMessag;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;

/**
 * This class is responsible for getting the BO for Business Info of alliances.
 * @author Rizwan Qazi
 */
public class AllianceMusicInfoManager
{

    static Logger log = Logger.getLogger(AllianceMusicInfoManager.class.getName());

    public static HashMap getAllianceMusicInfo(Long allianceId) throws AimsException, HibernateException
    {
        Session session = null;
        HashMap musicAlliance = new HashMap();

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("       alliance, allianceMusic ")
                .append("   from ")
                .append("       com.netpace.aims.model.core.AimsAllianc as alliance, ")
                .append("       com.netpace.aims.model.alliance.AimsAllianceMusic as allianceMusic ")
                .append("   where ")
                .append("       alliance.allianceId = allianceMusic.allianceId ")
                .append("       and alliance.isVcastMusicAlliance = 'Y' ")
                .append("       and alliance.allianceId = :allianceId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("allianceId", allianceId.longValue());

            Object[] allianceValues = null;

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                allianceValues = (Object[]) it.next();
                musicAlliance.put("AimsAllianc", (AimsAllianc) allianceValues[0]);
                musicAlliance.put("AimsAllianceMusic", (AimsAllianceMusic) allianceValues[1]);
            }

            if (musicAlliance.isEmpty())
            {
                AimsException aimsException = new AimsException("Error");
                aimsException.addException(new RecordNotFoundException("error.security"));
                throw aimsException;
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

        return musicAlliance;
    }

    public static AimsEmailMessag getAdditionalAggregatorInfoEmail() throws AimsException, HibernateException
    {
        AimsEmailMessag emailMessage = null;
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("       emailMessage ")
                .append("   from ")
                .append("       com.netpace.aims.model.system.AimsEmailMessag as emailMessage ")
                .append("   where ")
                .append("       emailMessage.emailCategory = :emailCategory ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setString("emailCategory", AimsNotificationConstants.EMAIL_CATEGORY_VCAST_MUSIC_AGGREGATOR_INFO);

            System.out.println("Query: " + query.toString());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                emailMessage = (AimsEmailMessag) it.next();
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

        return emailMessage;
    }

    public static void saveAdditionalAggregatorInfoEmail(AimsEmailMessag emailMessage) throws HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(emailMessage);
            tx.commit();
        }

        catch (JDBCException je)
        {
            if (tx != null)
                tx.rollback();

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
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
            throw ex;
        }

        finally
        {
            session.close();
        }

    }

    public static void saveOrUpdateMusicAlliance(AimsAllianceMusic aimsAllianceMusic, Collection productTypeSet, Collection productTypeNewSet)
        throws UniqueConstraintException, HibernateException
    {

        Session session = null;
        Transaction tx = null;
        CallableStatement statement = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            session.saveOrUpdate(aimsAllianceMusic);

            for (Iterator it = productTypeSet.iterator(); it.hasNext();)
            {
                AimsAllianceMusicProdType aimsAllianceMusicProdType = (AimsAllianceMusicProdType) it.next();
                session.saveOrUpdate(aimsAllianceMusicProdType);
            }

            for (Iterator it = productTypeNewSet.iterator(); it.hasNext();)
            {
                AimsAllianceMusicProdType aimsAllianceMusicProdType = (AimsAllianceMusicProdType) it.next();
                session.save(aimsAllianceMusicProdType);
            }

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

    public static Collection getAllianceMusicProductTypes(Long allianceId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();
            queryStringBuffer.append(" select   musicProdType ");
            queryStringBuffer.append(" from     com.netpace.aims.model.alliance.AimsAllianceMusicProdType musicProdType ");
            queryStringBuffer.append(" where    musicProdType.allianceId = :allianceId ");
            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("allianceId", allianceId.longValue());
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

    public static Collection getMusicProductTypes() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append(" from com.netpace.aims.model.alliance.AimsMusicProductType as musicProductType ");
            query.append(" where musicProductType.isActive = 'Y' order by sort_order ");
            collection = session.find(query.toString());
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