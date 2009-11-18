package	com.netpace.aims.bo.alliance;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.*;
import com.netpace.aims.model.*;
import com.netpace.aims.model.alliance.*;
import com.netpace.aims.controller.alliance.*;
import com.netpace.aims.controller.system.*;


import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import java.util.*;
import java.lang.Long;

import java.sql.Blob;
import java.sql.SQLException;
import oracle.sql.BLOB;
import com.netpace.aims.controller.devices.AimsAllianceExt;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.util.LobUtils;



/**
 * This	class is responsible for getting the details for White Papers.
 * It has static methods for getting the White Paper Details.
 * @author Ahson Imtiaz
 */
public class AllianceWhitePaperManager
{

	static Logger	log	=	Logger.getLogger(AllianceWhitePaperManager.class.getName());

	/*
	 *Collection will be returned having all White Papers in the system
	 **/
	public static Collection getAimsAllianceWhitePapers()	throws HibernateException
	{

		Session session = null;
		Collection collection = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		collection = session.find("from com.netpace.aims.model.alliance.AimsAllianceWhitePaper");

	  		log.debug("No of records returned: " + collection.size() );

		}
		catch(HibernateException e)
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


     /*
	 *Collection will be returned having all White Papers for administration view in the system
	 **/
	public static HashSet getAimsAllianceWhitePapersList()	throws HibernateException
	{

		Session session = null;
		HashSet hsAWhitePapers = new HashSet();

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		Query squery = session.createQuery("select aawp.whitePaperId, aawp.whitePaperName, aawp.whitePaperDesc, aawp.submittedDate, " +
      								  "aawp.status,  aalliance.companyName, auser.aimsContact.lastName , auser.aimsContact.firstName " +
      								  "from com.netpace.aims.model.alliance.AimsAllianceWhitePaper aawp, " +
      								  "com.netpace.aims.model.core.AimsAllianc aalliance, " +
      								  "com.netpace.aims.model.core.AimsUser auser " +
      								  "where  aawp.allianceId = aalliance.allianceId AND aawp.submittedBy = auser.userId order by aawp.submittedDate desc");

			Object [] listValues = null;
			for	(Iterator	it = squery.iterate();	it.hasNext();)
			{
				try {
					AimsAllianceWhitePaperExt aawpex = new AimsAllianceWhitePaperExt();
					listValues  = (Object []) it.next();
					aawpex.setWhitePaperId((Long)listValues[0]);
					aawpex.setWhitePaperName(listValues[1].toString());
					aawpex.setWhitePaperDesc(listValues[2].toString());
					aawpex.setSubmittedDate((java.util.Date)listValues[3]);
					aawpex.setStatus(listValues[4].toString());
					aawpex.setAllianceName(listValues[5].toString());
					aawpex.setSubmittedByName(listValues[6].toString() + " " + listValues[7].toString());
					hsAWhitePapers.add(aawpex);
				} catch (NullPointerException ex) {
					log.debug("Exception caught in AllianceWhitePaperManager.getAimsAllianceWhitePapersList() " + ex);
				}

			}

	  		log.debug("No of records returned: " + hsAWhitePapers.size() );

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			session.close();
		}

	    return hsAWhitePapers;

	}


               /*
                *Collection will be returned having all White Papers for administration view in the system
                **/
               public static Collection getWhitePapersListByStatus(String status, int PAGE_LENGTH, int pageNo)	throws HibernateException
               {

                       Session session = null;
                       Collection whitePapers = new ArrayList();

                   try
                   {
                             session = DBHelper.getInstance().getSession();
                             Query squery = session.createQuery("select aawp.whitePaperId, aawp.whitePaperName, aawp.whitePaperFilename " +
                                                                               "from com.netpace.aims.model.alliance.AimsAllianceWhitePaper aawp " +
                                                                               "where  aawp.status like '%" + status + "%' order by aawp.submittedDate");


                              squery.setMaxResults(PAGE_LENGTH);
                              squery.setFirstResult(PAGE_LENGTH * (pageNo - 1));

                              Object[] listValues = null;
                               for	(Iterator	it = squery.iterate();	it.hasNext();)
                               {
                                       try {
                                               AimsAllianceWhitePaperExt aawpex = new AimsAllianceWhitePaperExt();
                                               listValues  = (Object []) it.next();
                                               aawpex.setWhitePaperId((Long)listValues[0]);
                                               aawpex.setWhitePaperName(listValues[1].toString());
                                               aawpex.setWhitePaperFilename(listValues[2].toString());
                                               whitePapers.add(aawpex);
                                       } catch (NullPointerException ex) {
                                               log.debug("Exception caught in AllianceWhitePaperManager.getWhitePapersListByStatus() " + ex);
                                       }

                               }

                                 log.debug("No of records returned: " + whitePapers.size() );

                       }
                       catch(HibernateException e)
                       {
                               e.printStackTrace();
                               throw e;
                       }
                       finally
                       {
                               session.close();
                       }

                   return whitePapers;

               }

               /*
                            *Collection will be returned having all White Papers for administration view in the system
                            **/
                           public static Collection getWhitePapersListByStatus(String status)	throws HibernateException
                           {

                                   Session session = null;
                                   Collection whitePapers = new ArrayList();

                               try
                               {
                                         session = DBHelper.getInstance().getSession();
                                         Query squery = session.createQuery("select aawp.whitePaperId, aawp.whitePaperName, aawp.whitePaperDesc, aawp.whitePaperFilename, aawp.whitePaperFileType " +
                                                                                           "from com.netpace.aims.model.alliance.AimsAllianceWhitePaper aawp " +
                                                                                           "where  aawp.status like '%" + status + "%' order by aawp.submittedDate");



                                          Object[] listValues = null;
                                           for	(Iterator	it = squery.iterate();	it.hasNext();)
                                           {
                                                   try {
                                                           AimsAllianceWhitePaperExt aawpex = new AimsAllianceWhitePaperExt();
                                                           listValues  = (Object []) it.next();
                                                           aawpex.setWhitePaperId((Long)listValues[0]);
                                                           aawpex.setWhitePaperName(listValues[1].toString());
                                                           aawpex.setWhitePaperDesc(listValues[2].toString());
                                                           aawpex.setWhitePaperFilename(listValues[3].toString());
                                                           aawpex.setWhitePaperFileType((String)listValues[4].toString());
                                                           whitePapers.add(aawpex);
                                                   } catch (NullPointerException ex) {
                                                           log.debug("Exception caught in AllianceWhitePaperManager.getWhitePapersListByStatus() " + ex);
                                                   }

                                           }

                                             log.debug("No of records returned: " + whitePapers.size() );

                                   }
                                   catch(HibernateException e)
                                   {
                                           e.printStackTrace();
                                           throw e;
                                   }
                                   finally
                                   {
                                           session.close();
                                   }

                               return whitePapers;

                           }


               public static int getAimsAllianceWhitePapersCount(String search_expression)	throws HibernateException
               {

                   int rows = 0;
                   Session session = null;
                   StringBuffer sbQuery = new StringBuffer();
                   try
                   {

                     session = DBHelper.getInstance().getSession();
                     sbQuery.append("select count(*) ");
                     sbQuery.append("from com.netpace.aims.model.alliance.AimsAllianceWhitePaper aawp, ");
                     sbQuery.append("com.netpace.aims.model.core.AimsAllianc aalliance, ");
                     sbQuery.append("com.netpace.aims.model.core.AimsUser auser ");
                     sbQuery.append("where  aawp.allianceId = aalliance.allianceId AND aawp.submittedBy = auser.userId ");

                     if (search_expression.length() > 0)
                     {
                       sbQuery.append(search_expression);
                     }

                     sbQuery.append(" order by aawp.submittedDate ");
                     Query squery = session.createQuery(sbQuery.toString());



                       rows = ( (Integer)squery.iterate().next()).intValue();
                       log.debug("Rows Counted : "  + rows );

                       }
                       catch(HibernateException e)
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


                 public static int getWhitePapersCountByStatus(String status)	throws HibernateException
                                {

                                    int rows = 0;
                                    Session session = null;
                                    StringBuffer sbQuery = new StringBuffer();
                                    try
                                    {

                                      session = DBHelper.getInstance().getSession();
                                      Query query = session.createQuery("select count(*) " +
                                                                        "from com.netpace.aims.model.alliance.AimsAllianceWhitePaper aawp " +
                                                                        "where  aawp.status like '%" + status + "%' order by aawp.submittedDate");

                                        rows = ( (Integer)query.iterate().next()).intValue();
                                        log.debug("Rows Counted : "  + rows );

                                        }
                                        catch(HibernateException e)
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



     /*
	 * Collection will be returned having all White Papers for administration view in the system
	 * Having Filter Criteria Support
	 **/
	public static Collection getAimsAllianceWhitePapersList(String search_expression, int PAGE_LENGTH, int pageNo)	throws HibernateException
	{

		Session session = null;
		Collection hsAWhitePapers = new ArrayList();
		StringBuffer sbQuery = new StringBuffer();

	    try
	    {
	      	session = DBHelper.getInstance().getSession();

      		sbQuery.append("select aawp.whitePaperId, aawp.whitePaperName, aawp.whitePaperDesc, aawp.submittedDate, ");
      		sbQuery.append("aawp.status,  aalliance.companyName, auser.aimsContact.lastName , auser.aimsContact.firstName ");
      		sbQuery.append("from com.netpace.aims.model.alliance.AimsAllianceWhitePaper aawp, ");
      		sbQuery.append("com.netpace.aims.model.core.AimsAllianc aalliance, ");
      		sbQuery.append("com.netpace.aims.model.core.AimsUser auser ");
      		sbQuery.append("where  aawp.allianceId = aalliance.allianceId AND aawp.submittedBy = auser.userId ");

                      if (search_expression.length() > 0)
                      {
                        sbQuery.append(search_expression);
                      }


                sbQuery.append(" order by aawp.submittedDate desc");
		Query squery = session.createQuery(sbQuery.toString());


                      squery.setMaxResults(PAGE_LENGTH);
                      squery.setFirstResult(PAGE_LENGTH * (pageNo - 1));

			Object [] listValues = null;
			for	(Iterator	it = squery.iterate();	it.hasNext();)
			{
				try {
					AimsAllianceWhitePaperExt aawpex = new AimsAllianceWhitePaperExt();
					listValues  = (Object []) it.next();
					aawpex.setWhitePaperId((Long)listValues[0]);
					aawpex.setWhitePaperName(listValues[1].toString());
					aawpex.setWhitePaperDesc(listValues[2].toString());
					aawpex.setSubmittedDate((java.util.Date)listValues[3]);
					aawpex.setStatus(listValues[4].toString());
					aawpex.setAllianceName(listValues[5].toString());
					aawpex.setSubmittedByName(listValues[6].toString() + " " + listValues[7].toString());
					hsAWhitePapers.add(aawpex);
				} catch (NullPointerException ex) {
					log.debug("Exception caught in AllianceWhitePaperManager.getAimsAllianceWhitePapersList() " + ex);
				}

			}

	  		log.debug("No of records returned: " + hsAWhitePapers.size() );

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			session.close();
		}

	    return hsAWhitePapers;

	}

	/*
	 *Collection will be returned specific White Paper detail from the system
	 **/
	public static AimsAllianceWhitePaper getAimsAllianceWhitePaper(java.lang.Long lngWhitePaperId)	throws HibernateException
	{

		Session session = null;
		Collection collection = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		return (AimsAllianceWhitePaper) session.find("from com.netpace.aims.model.alliance.AimsAllianceWhitePaper aawp WHERE aawp.whitePaperId = " + lngWhitePaperId.toString()).get(0);

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception ex) {
			log.debug("Exception caught in AllianceWhitePaperManager.getAimsAllianceWhitePaper(Long) " + ex);
			return null;
		}
		finally
		{
			session.close();
		}

	}


	/*
	 *Collection will be returned specific White Paper detail from the system
	 **/
	public static AimsAllianceWhitePaperExt getAimsAllianceWhitePaperExt(java.lang.Long lngWhitePaperId)	throws HibernateException
	{

		Session session = null;
		Collection collection = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		Query squery = session.createQuery("select aawp.whitePaperId, aawp.whitePaperName, aawp.whitePaperDesc, aawp.submittedDate, " +
      								  "aawp.status,  aalliance.companyName, auser.aimsContact.lastName , auser.aimsContact.firstName, aawp.whitePaperFilename " +
      								  "from com.netpace.aims.model.alliance.AimsAllianceWhitePaper aawp, " +
      								  "com.netpace.aims.model.core.AimsAllianc aalliance, " +
      								  "com.netpace.aims.model.core.AimsUser auser " +
      								  "where  aawp.allianceId = aalliance.allianceId AND aawp.submittedBy = auser.userId " +
      								  "AND aawp.whitePaperId = " + lngWhitePaperId.toString());

			Object [] listValues = null;
			for	(Iterator	it = squery.iterate();	it.hasNext();)
			{
				try {
				AimsAllianceWhitePaperExt aawpex = new AimsAllianceWhitePaperExt();
				listValues  = (Object []) it.next();
				aawpex.setWhitePaperId((Long)listValues[0]);
				aawpex.setWhitePaperName(listValues[1].toString());
				aawpex.setWhitePaperDesc(listValues[2].toString());
				aawpex.setSubmittedDate((java.util.Date)listValues[3]);
				aawpex.setStatus(listValues[4].toString());
				aawpex.setAllianceName(listValues[5].toString());
				aawpex.setSubmittedByName(listValues[6].toString() + " " + listValues[7].toString());
				aawpex.setWhitePaperFilename(listValues[8].toString());
				return aawpex;
				} catch (NullPointerException ex) {
					log.debug("Exception caught in AllianceWhitePaperManager.getAimsAllianceWhitePaperExt(Long) " + ex);
				}

			}

			return null;

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			session.close();
		}

	}
  /*
   *
   *  */
  public static int deleteAimsAllianceWhitePaper(java.lang.Long wPaperId) throws HibernateException
  {

	int delCount = 0;
    Session session = null;
    try
    {

     session = DBHelper.getInstance().getSession();
	  Transaction tx = session.beginTransaction();
	  delCount = session.delete("from com.netpace.aims.model.alliance.AimsAllianceWhitePaper as wpapers where wpapers.whitePaperId = :wpaperId",
									wPaperId, new LongType());

	  tx.commit();
      log.debug("Rows affected by deletion: " + delCount);

     }
     catch(HibernateException e)
     {
      e.printStackTrace();
      throw e;
     }
     finally
     {
      session.close();
     }

    return delCount;
  }


  /*
   ***/

  	public static java.lang.Long saveOrUpdate(AimsAllianceWhitePaper aWPaper)	throws HibernateException, AimsWhitePaperTitleException
	{

		Session	session	=	null;
		java.lang.Long whitePaperId = null;
		Transaction	tx = null;

		Long tempWhitePaperId = aWPaper.getWhitePaperId();
		if (tempWhitePaperId == null) tempWhitePaperId = new Long("0");
		hasWhitePaperNameAlreadyUsed(aWPaper.getWhitePaperName(), tempWhitePaperId );

		try
		{
			session	=	DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(aWPaper);
			whitePaperId = aWPaper.getWhitePaperId();

			tx.commit();
		}

		catch(HibernateException	e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw	e;

		}
		finally
		{
			session.close();
		}

		return whitePaperId;
	}


  /*
   ***/

  	public static java.lang.Long saveOrUpdate(AimsAllianceWhitePaper aWPaper, FormFile ffWhitePaper)	throws HibernateException, AimsWhitePaperTitleException, Exception
	{

		Session	session	=	null;
		java.lang.Long whitePaperId = null;
		Transaction	tx = null;

		Long tempWhitePaperId = aWPaper.getWhitePaperId();
		if (tempWhitePaperId == null) tempWhitePaperId = new Long("0");
		hasWhitePaperNameAlreadyUsed(aWPaper.getWhitePaperName(), tempWhitePaperId );

		try
		{
			session	=	DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			byte[] buffer = new byte[1];
    		buffer[0] = 1;
    		long bytesWrote = 0;

    		aWPaper.setWhitePaperFilename(ffWhitePaper.getFileName());
			aWPaper.setWhitePaperFileType(ffWhitePaper.getContentType());
			aWPaper.setWhitePaperFile(Hibernate.createBlob(buffer));

			session.saveOrUpdate(aWPaper);
			session.flush();

    		session.refresh(aWPaper, LockMode.UPGRADE);
    		bytesWrote = LobUtils.writeToOraBlob((BLOB) aWPaper.getWhitePaperFile(), ffWhitePaper.getInputStream());
    		session.flush();

			whitePaperId = aWPaper.getWhitePaperId();
			tx.commit();
		}

		catch(Exception	e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw	e;

		}
		finally
		{
			session.close();
		}

		return whitePaperId;
	}

	/*
	 *Collection will be returned having all Alliances in the system
	 **/
	public static Collection getAimsAlliances()	throws HibernateException
	{

		Session session = null;
		Collection collection = null;
                Collection alliances = new ArrayList();
                AimsAllianc alliance = null;
                Object [] listValues = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		collection = session.find("select alliance.allianceId, alliance.companyName  from com.netpace.aims.model.core.AimsAllianc alliance order by alliance.companyName ");

	  		log.debug("No of records returned for Alliances: " + collection.size() );

                  for (Iterator it = collection.iterator(); it.hasNext();)
                  {
                     alliance =  new AimsAllianc();
                     listValues = (Object[]) it.next();
                     alliance.setAllianceId((Long)listValues[0]);
                     alliance.setCompanyName(listValues[1].toString());
                     alliances.add(alliance);
                  }



		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			session.close();
		}

	    return alliances;

	}



    /**
     *  This static method validates the uniqueness of the white paper titles.
     *  Otherwise throw AimsWhitePaperTitleException.
     */

      public static boolean hasWhitePaperNameAlreadyUsed(String whitePaperName, java.lang.Long whitePaperId ) throws AimsWhitePaperTitleException, HibernateException
      {

        Session session = null;

        try
        {
          session = DBHelper.getInstance().getSession();
          Query query = session.createQuery("select from com.netpace.aims.model.alliance.AimsAllianceWhitePaper as wpaper where upper(wpaper.whitePaperName) = :whitePaperName AND wpaper.whitePaperId <> :whitePaperId");
          query.setString("whitePaperName", whitePaperName.toUpperCase());
          query.setString("whitePaperId",whitePaperId.toString());

             for (Iterator it = query.iterate(); it.hasNext();)
               {
                     throw new AimsWhitePaperTitleException();
               }

         }
         catch(HibernateException e)
         {
          e.printStackTrace();
          throw e;
         }
         finally
         {
          session.close();
         }

        return false;
      }

/* Class Ends */
}