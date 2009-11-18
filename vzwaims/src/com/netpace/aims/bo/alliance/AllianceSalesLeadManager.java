package	com.netpace.aims.bo.alliance;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.*;
import com.netpace.aims.model.*;
import com.netpace.aims.model.alliance.*;
import com.netpace.aims.controller.alliance.*;
import com.netpace.aims.controller.system.*;
import com.netpace.aims.util.*;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import java.util.*;
import java.lang.Long;

import java.sql.Blob;
import java.sql.SQLException;
import oracle.sql.BLOB;
import com.netpace.aims.model.application.AimsApp;
import java.util.ArrayList;



/**
 * This	class is responsible for getting the details for Sales Lead.
 * It has static methods for getting the Sales Lead Details.
 * @author Ahson Imtiaz
 */
public class AllianceSalesLeadManager
{

	static Logger	log	=	Logger.getLogger(AllianceSalesLeadManager.class.getName());

	/*
	 *Collection will be returned having all Sales Lead in the system
	 **/
	public static Collection getAimsAllianceWhitePapers()	throws HibernateException
	{

		Session session = null;
		Collection collection = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		collection = session.find("from com.netpace.aims.model.alliance.AimsAllianceSalesLead");

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


        public static Collection checkAllianceStatus(Long allianceId, String status, Long aimsLifecyclePhaseId, Long aimsPlatformId, String allianceContractStatus)throws HibernateException
        {
          Session session = null;
          Object[] listValues = null;
          AimsApp aimsApp = null;
          Collection list  = new ArrayList();
          StringBuffer buffer = new StringBuffer();

            try
            {
                      session = DBHelper.getInstance().getSession();
                       buffer.append("select distinct app.appsId, app.title from com.netpace.aims.model.core.AimsAllianc alliance, ")
                             .append("com.netpace.aims.model.application.AimsApp app,  ")
                             .append("com.netpace.aims.model.core.AimsAllianceContract allcontract ")
                             .append("where alliance.allianceId = :allianceId AND ")
                             .append("alliance.status = :status AND ")
                             .append("app.aimsAllianceId = alliance.allianceId AND ")
                             .append("app.aimsAllianceId = alliance.allianceId AND ")
                             .append("app.aimsLifecyclePhaseId = :aimsLifecyclePhaseId ")
                             .append("AND alliance.allianceId = allcontract.allianceId AND ")
                             .append("allcontract.status = :allianceContractStatus ");

                       if ( aimsPlatformId != null )
                         buffer.append(" AND app.aimsPlatformId = :aimsPlatformId");


					   buffer.append(" order by upper(app.title) ");
                       Query query = session.createQuery(buffer.toString());
                       query.setLong("allianceId",allianceId.longValue());
                       query.setString("status", status);
                       query.setLong("aimsLifecyclePhaseId", aimsLifecyclePhaseId.longValue());
                       query.setString("allianceContractStatus",allianceContractStatus);

                       if ( aimsPlatformId != null )
                         query.setLong("aimsPlatformId", aimsPlatformId.longValue());

                       for (Iterator	it = query.iterate();	it.hasNext();)
                       {
                            aimsApp = new AimsApp();
                            listValues = (Object[]) it.next();
                            aimsApp.setAppsId((Long)listValues[0]);
                            aimsApp.setTitle((String)listValues[1]);
                            list.add(aimsApp);
                       }

                       log.debug("No of alliances : " + list.size() );

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

            return list;

        }


        public static Collection getSubmittedSalesLeadApps(Long salesLeadId)throws HibernateException
        {
          Session session = null;
          Object[] listValues = null;
          AimsApp aimsApp = null;
          Collection list  = new ArrayList();
          StringBuffer buffer = new StringBuffer();

            try
            {
                      session = DBHelper.getInstance().getSession();
                       buffer.append("select app.appsId, app.title from  ")
                             .append("com.netpace.aims.model.application.AimsApp app,  ")
                             .append("com.netpace.aims.model.alliance.AimsAllianceSalesLeadApps aslApps ")
                             .append("where aslApps.allianceSalesLead.salesLeadId = :salesLeadId AND ")
                             .append("aslApps.appsId = app.appsId ")
                             .append("order by app.title ");
            

                 Query query = session.createQuery(buffer.toString());
                       query.setLong("salesLeadId",salesLeadId.longValue());
    

                       for (Iterator	it = query.iterate();	it.hasNext();)
                       {
                            aimsApp = new AimsApp();
                            listValues = (Object[]) it.next();
                            aimsApp.setAppsId((Long)listValues[0]);
                            aimsApp.setTitle((String)listValues[1]);
                            list.add(aimsApp);
                       }

                       log.debug("No of salesLeadApps : " + list.size() );

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

            return list;

        }
     /*
	 * HashSet will be returned having all Sales Leads for administration view in the system
	 **/
	public static Collection getAimsAllianceSalesLeadList()	throws HibernateException
	{

		Session session = null;
		ArrayList hsASalesLead = new ArrayList();

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		Query squery = session.createQuery("select aasl.salesLeadId, aasl.salesLeadDesc, aasl.status, aasl.submittedDate, " +
      								  "aasl.companySolution, aasl.leadQualification, aasl.estimatedSize, " +
      								  "aasl.purchaseDecisionDate, aalliance.companyName, auser.aimsContact.lastName , auser.aimsContact.firstName, " +
      								  "aasl.comments, acontact.lastName, acontact.firstName " +
      								  "from com.netpace.aims.model.alliance.AimsAllianceSalesLead aasl, " +
      								  "com.netpace.aims.model.core.AimsAllianc aalliance, " +
      								  "com.netpace.aims.model.core.AimsUser auser, " +
      								  "com.netpace.aims.model.core.AimsContact acontact " +
      								  "where  aasl.allianceId = aalliance.allianceId AND aasl.submittedBy = auser.userId " +
      								  "AND acontact.contactId(+) = aasl.assignedTo  order by aasl.submittedDate desc");

			Object [] listValues = null;
			for	(Iterator	it = squery.iterate();	it.hasNext();)
			{
				try {
					SalesLeadForm aasl = new SalesLeadForm();
					listValues  = (Object []) it.next();
					aasl.setSalesLeadId((Long)listValues[0]);
					aasl.setSalesLeadDesc(listValues[1].toString());
					aasl.setStatus(listValues[2].toString());
					aasl.setSubmittedDate(Utility.convertToString(((Date) listValues[3]), AimsConstants.DATE_FORMAT));
					//aasl.setSubmittedDate((java.util.Date)listValues[3]);
					aasl.setCompanySolution(listValues[4].toString());
					aasl.setLeadQualification(listValues[5].toString());
					aasl.setEstimatedSize(StringFuncs.initializeLongGetParam((Long)listValues[6], ""));
					//aasl.setEstimatedSize((String)listValues[6]);
					aasl.setPurchaseDecisionDate((java.util.Date)listValues[7]);
					aasl.setAllianceName(listValues[8].toString());
					aasl.setSubmittedBy(listValues[10].toString() + " " + listValues[9].toString());
					if (listValues[11] != null)
						aasl.setComments(listValues[11].toString());
					else
						aasl.setComments(new String());

					if (listValues[12] != null && listValues[13] != null)
						aasl.setAssignedToName(listValues[12].toString() + " " + listValues[13].toString());
					else
						aasl.setAssignedToName("None");

					hsASalesLead.add(aasl);

				} catch (NullPointerException ex) {
					log.debug("Exception caught in AllianceSalesLeadManager.getAimsAllianceSalesLeadList() " + ex);
				}

			}

	  		log.debug("No of records returned: " + hsASalesLead.size() );

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

	    return hsASalesLead;

	}


        /*
                * HashSet will be returned having all Sales Leads for administration view in the system
                **/
               public static Collection getAimsAllianceSalesLeadList(String search_expression, int PAGE_LENGTH, int pageNo)	throws HibernateException
               {

                       Session session = null;
                       ArrayList hsASalesLead = new ArrayList();
                       StringBuffer query = new StringBuffer();

                   try
                   {
                             session = DBHelper.getInstance().getSession();

                             query.append("select aasl.salesLeadId, aasl.salesLeadDesc, aasl.status, aasl.submittedDate, " +
                                                                               "aasl.companySolution, aasl.leadQualification, aasl.estimatedSize, " +
                                                                               "aasl.purchaseDecisionDate, aalliance.companyName, auser.aimsContact.lastName , auser.aimsContact.firstName, " +
                                                                               "aasl.comments, acontact.lastName, acontact.firstName, auser.aimsContact.emailAddress " +
                                                                               "from com.netpace.aims.model.alliance.AimsAllianceSalesLead aasl, " +
                                                                               "com.netpace.aims.model.core.AimsAllianc aalliance, " +
                                                                               "com.netpace.aims.model.core.AimsUser auser, " +
                                                                               "com.netpace.aims.model.core.AimsContact acontact " +
                                                                               "where 1=1 ");

                                                    if (search_expression.length() > 0)
                                                     {
                                                     query.append(search_expression);
                                                     }


                                                     query.append(" AND aasl.allianceId = aalliance.allianceId AND aasl.submittedBy = auser.userId " +
                                                                   "AND acontact.contactId(+) = aasl.assignedTo  order by aasl.submittedDate desc");

                              Query squery = session.createQuery(query.toString());
                              squery.setMaxResults(PAGE_LENGTH);
                              squery.setFirstResult(PAGE_LENGTH * (pageNo - 1));

                               Object [] listValues = null;
                               for	(Iterator	it = squery.iterate();	it.hasNext();)
                               {
                                       try {
                                               SalesLeadForm aasl = new SalesLeadForm();
                                               listValues  = (Object []) it.next();
                                               aasl.setSalesLeadId((Long)listValues[0]);
                                               aasl.setSalesLeadDesc(listValues[1].toString());
                                               aasl.setStatus(listValues[2].toString());
											   aasl.setSubmittedDate(Utility.convertToString(((Date) listValues[3]), AimsConstants.DATE_FORMAT));											   
                                               //aasl.setSubmittedDate((java.util.Date)listValues[3]);
                                               aasl.setCompanySolution((String)listValues[4]);
                                               aasl.setLeadQualification(listValues[5].toString());
                                               aasl.setEstimatedSize(StringFuncs.initializeLongGetParam((Long)listValues[6], ""));
                                               aasl.setPurchaseDecisionDate((java.util.Date)listValues[7]);
                                               aasl.setAllianceName(listValues[8].toString());
                                               aasl.setSubmittedBy(listValues[10].toString() + " " + listValues[9].toString());
                                               if (listValues[11] != null)
                                                       aasl.setComments(listValues[11].toString());
                                               else
                                                       aasl.setComments(new String());

                                               if (listValues[12] != null && listValues[13] != null)
                                                       aasl.setAssignedToName(listValues[12].toString() + " " + listValues[13].toString());
                                               else
                                                       aasl.setAssignedToName("None");

											   aasl.setSubmittedByEmailAddress((String)listValues[14]);
                                               hsASalesLead.add(aasl);

                                       } catch (NullPointerException ex) {
                                               log.debug("Exception caught in AllianceSalesLeadManager.getAimsAllianceSalesLeadList() " + ex);
                                       }

                               }

                                 log.debug("No of records returned: " + hsASalesLead.size() );

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

                   return hsASalesLead;

               }




public static int getAimsAllianceSalesLeadCount()	throws HibernateException
{

    int rows = 0;
    Session session = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    try
    {

        queryStringBuffer.append("select  ")
                                         .append("		count(*) ")
                                         .append("from ")
                                         .append("		com.netpace.aims.model.alliance.AimsAllianceSalesLead aasl");

                session = DBHelper.getInstance().getSession();
        rows = ( (Integer) session.iterate(queryStringBuffer.toString()).next()	).intValue();
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
	 *AimsAllianceSalesLead class will be returned specific Sales Lead detail from the system
	 **/
	public static AimsAllianceSalesLead getAimsAllianceSalesLead(java.lang.Long lngSalesLeadId)	throws HibernateException
	{

		Session session = null;
		Collection collection = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		return (AimsAllianceSalesLead) session.find("from com.netpace.aims.model.alliance.AimsAllianceSalesLead aasl WHERE aasl.salesLeadId = " + lngSalesLeadId.toString()).get(0);

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception ex) {
			log.debug("Exception caught in AllianceSalesLeadManager.getAimsAllianceSalesLead(Long) " + ex);
			return null;
		}
		finally
		{
			session.close();
		}

	}


	/*
	 * AimsSalesLeadForm will be returned specific White Paper detail from the system
	 * To avoid replication we are using the SalesLeadForm
	 **/

	public static SalesLeadForm getAimsAllianceSalesLeadExt(Long lngSalesLeadId )	throws HibernateException
	{

		Session session = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();

      		Query squery = session.createQuery("select aasl.salesLeadId, aasl.salesLeadDesc, aasl.status, aasl.submittedDate, " +
      								  "aasl.companySolution, aasl.leadQualification, aasl.estimatedSize, " +
      								  "aasl.purchaseDecisionDate, aalliance.companyName, auser.aimsContact.lastName , auser.aimsContact.firstName, " +
      								  "aasl.comments, acontact.lastName, acontact.firstName, acontact.contactId, auser.aimsContact.emailAddress,auser.aimsContact.phone " +
      								  "from com.netpace.aims.model.alliance.AimsAllianceSalesLead aasl, " +
      								  "com.netpace.aims.model.core.AimsAllianc aalliance, " +
      								  "com.netpace.aims.model.core.AimsUser auser, " +
      								  "com.netpace.aims.model.core.AimsContact acontact " +
      								  "where  aasl.allianceId = aalliance.allianceId AND aasl.submittedBy = auser.userId " +
      								  "AND acontact.contactId(+) = aasl.assignedTo AND aasl.salesLeadId = " + lngSalesLeadId.toString() + " order by aasl.submittedDate");

			Object [] listValues = null;
			for	(Iterator	it = squery.iterate();	it.hasNext();)
			{
				try {
					SalesLeadForm aasl = new SalesLeadForm();
					listValues  = (Object []) it.next();
					aasl.setSalesLeadId((Long)listValues[0]);
					aasl.setSalesLeadDesc((String)listValues[1]);
					aasl.setStatus((String)listValues[2]);
					aasl.setSubmittedDate(Utility.convertToString(((Date) listValues[3]), AimsConstants.DATE_FORMAT));											   
					//aasl.setSubmittedDate((java.util.Date)listValues[3]);
					aasl.setCompanySolution((String)listValues[4]);
					aasl.setLeadQualification((String)listValues[5]);
					aasl.setEstimatedSize(StringFuncs.initializeLongGetParam((Long)listValues[6], ""));					
					aasl.setPurchaseDecisionDate((java.util.Date)listValues[7]);
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
					aasl.setPurchaseDecision(sdf.format(aasl.getPurchaseDecisionDate()));
					aasl.setAllianceName((String)listValues[8]);
					aasl.setSubmittedBy((String)listValues[10] + " " + (String)listValues[9]);
					aasl.setSubmittedByEmailAddress((String)listValues[15]);
					aasl.setSubmittedByPhone((String)listValues[16]);

					System.out.println("The SUBMITTED BY NAME IS : " + listValues[10].toString() + " " + listValues[9].toString());
					if (listValues[11] != null)
						aasl.setComments(listValues[11].toString());
					else
						aasl.setComments(new String());

					if (listValues[12] != null && listValues[13] != null)
						aasl.setAssignedToName(listValues[12].toString() + " " + listValues[13].toString());
					else
						aasl.setAssignedToName("None");

					if (listValues[14] != null)
					    aasl.setAssignedTo((Long)listValues[14]);
					else
					    aasl.setAssignedTo(new Long("0"));

					return aasl;

				} catch (NullPointerException ex) {
					log.debug("Exception caught in AllianceSalesLeadManager.getAimsAllianceSalesLeadExt(Long) " + ex);
				}

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

		return null;

	}

  /*
   *
   *  */
  public static int deleteAimsAllianceSalesLead(java.lang.Long lngSalesLeadId) throws HibernateException
  {

	int delCount = 0;
    Session session = null;
    try
    {

     session = DBHelper.getInstance().getSession();
	  Transaction tx = session.beginTransaction();
	  delCount = session.delete("from com.netpace.aims.model.alliance.AimsAllianceSalesLead as aasl where aasl.salesLeadId = :salesLeadId",
									lngSalesLeadId, new LongType());

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

  	public static java.lang.Long saveOrUpdate(AimsAllianceSalesLead aSalesLead)	throws HibernateException
	{

		Session	session	=	null;
		java.lang.Long salesLeadId = null;
		Transaction	tx = null;


		try
		{
			session	=	DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(aSalesLead);
			salesLeadId = aSalesLead.getSalesLeadId();

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

		return salesLeadId;
	}


	/*
	 *Collection will be returned having all solutions in the system (for future use)
	 **/
	public static Collection getAssingedTos()	throws HibernateException
	{

		Session session = null;
		Collection collection = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		collection = session.find("from com.netpace.aims.model.core.AimsUser auser where auser.userType = 'V' and auser.userAccountStatus = 'ACTIVE'");

	  		log.debug("No of records returned for Assigned To: " + collection.size() );

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
	 *Collection will be returned having all solutions in the system (for future use)
	 **/
/*	public static Collection getSolutions()	throws HibernateException
	{

		Session session = null;
		Collection collection = null;

	    try
	    {
	      	session = DBHelper.getInstance().getSession();
      		collection = session.find("from com.netpace.aims.model. ");

	  		log.debug("No of records returned for Solutions: " + collection.size() );

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
*/

/* Class Ends */
}