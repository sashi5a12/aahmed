package com.netpace.aims.bo.newmarketing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.newmarketing.MarketApplicationExt;
import com.netpace.aims.controller.newmarketing.ProvApplicationExt;
import com.netpace.aims.controller.newmarketing.ProvApplicationDetailsExt;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.Utility;

public class ApplicationManager {

	static Logger	log	=	Logger.getLogger(ApplicationManager.class.getName());
	/**
	 * Method accepts the query filter and append it to the listing query after an AND clause.
	 * */
	public static	Collection getApplications(String filterQuery,int	PAGE_LENGTH, int page_no)	throws HibernateException
	{
		Session	session	=	null;
		StringBuffer queryString = new StringBuffer();
		Object[] objValues;
		ArrayList objArray = new ArrayList();

		queryString.append("select app.appsId, app.title, alliance.companyName,  deck.deckName, bapp.deckLaunchDate ")
		           .append(" FROM com.netpace.aims.model.application.AimsBrewApp bapp, com.netpace.aims.model.application.AimsApp app, com.netpace.aims.model.system.AimsDeck deck, com.netpace.aims.model.core.AimsAllianc alliance ")
				   .append(" WHERE bapp.brewAppsId = app.appsId AND bapp.deckPlacement = deck.deckId ")
				   .append(" AND app.aimsAllianceId = alliance.allianceId ")
				   .append(" AND (app.aimsLifecyclePhaseId = ").append(AimsConstants.ACCEPTANCE_ID).append(" OR (app.aimsLifecyclePhaseId =").append(AimsConstants.TESTING_ID).append(" AND nvl(bapp.deckLaunchDate,sysdate) < to_date('").append(Utility.convertToString(new Date(),"MM-dd-yyyy" )).append("','MM-dd-yyyy')) OR app.aimsLifecyclePhaseId = ").append(AimsConstants.CERTIFICATION_ID).append(" ) ");
		if (filterQuery != null && filterQuery.length() > 0)
			queryString.append(" AND ").append(filterQuery);

		queryString.append(" ORDER BY app.title ");

		try
		{
			//Set	Paging Logic and execute Query
			session	=	DBHelper.getInstance().getSession();
			Query	query	=	session.createQuery(queryString.toString());
			query.setMaxResults(PAGE_LENGTH);
			query.setFirstResult(PAGE_LENGTH * (page_no-1));

			for ( Iterator it = query.iterate(); it.hasNext();){
				objValues = (Object[]) it.next();
				MarketApplicationExt appDetail = new MarketApplicationExt();
				appDetail.setApplicationId((Long)objValues[0]);
				appDetail.setAppTitle((String)objValues[1]);
				appDetail.setDeveloperName((String)objValues[2]);
				appDetail.setDeckPlacement((String)objValues[3]);
				appDetail.setLaunchDate((Date)objValues[4]);
				objArray.add(appDetail);
			}

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

		return objArray;
	}


	public static	int	getPageCount(	String filterQuery)	throws HibernateException
	{
		int	rows = 0;
		Collection collection	=	null;
		Session	session	=	null;
		StringBuffer queryString = new StringBuffer();

		try
		{
			session	=	DBHelper.getInstance().getSession();

			queryString.append("select app.appsId ")
	           .append(" FROM com.netpace.aims.model.application.AimsBrewApp bapp, com.netpace.aims.model.application.AimsApp app, com.netpace.aims.model.system.AimsDeck deck, com.netpace.aims.model.core.AimsAllianc alliance ")
			   .append(" WHERE bapp.brewAppsId = app.appsId AND bapp.deckPlacement = deck.deckId ")
			   .append(" AND app.aimsAllianceId = alliance.allianceId ")
			   .append(" AND (app.aimsLifecyclePhaseId = ").append(AimsConstants.ACCEPTANCE_ID).append(" OR (app.aimsLifecyclePhaseId =").append(AimsConstants.TESTING_ID).append(" AND nvl(bapp.deckLaunchDate,sysdate) < to_date('").append(Utility.convertToString(new Date(),"MM-dd-yyyy" )).append("','MM-dd-yyyy')) OR app.aimsLifecyclePhaseId = ").append(AimsConstants.CERTIFICATION_ID).append(" ) ");
			if (filterQuery != null && filterQuery.length() > 0)
					queryString.append(" AND ").append(filterQuery);

			collection = session.find(queryString.toString());
			log.debug("No	of apps	returned:	"	+	collection.size()	);
			rows = collection.size();
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
		return rows;

	}


	  /* */
	  public static Collection getDevices() throws HibernateException
	  {
		ArrayList collection = new ArrayList();
	   Session session = null;
	   StringBuffer sbQuery = new StringBuffer();

	   try
	   {
	      session = DBHelper.getInstance().getSession();

			sbQuery.append(" SELECT ad.deviceId, ad.deviceModel, ad.deviceMfgBy, ad.status ");
			sbQuery.append(" FROM ");
			sbQuery.append(" com.netpace.aims.model.masters.AimsDevic ad ");
			sbQuery.append(" WHERE ");
			sbQuery.append(" upper(ad.status) = 'ACTIVE' ");
			sbQuery.append(" ORDER BY ");
			sbQuery.append(" ad.deviceModel ");

	      Query	query	=	session.createQuery(sbQuery.toString());
	      Object[] objCols;

			for	(Iterator	it = query.iterate();	it.hasNext();)
			{
				com.netpace.aims.controller.devices.AimsLoanDevicExt loanDeviceExt = new com.netpace.aims.controller.devices.AimsLoanDevicExt();
				objCols = (Object []) it.next();
				loanDeviceExt.setDeviceId((java.lang.Long)objCols[0]);
				loanDeviceExt.setDeviceModel(Utility.getObjectString(objCols[1]) );
				loanDeviceExt.setDeviceMfgBy(Utility.getObjectString(objCols[2]) );
				loanDeviceExt.setStatus(Utility.getObjectString(objCols[3]));
				collection.add(loanDeviceExt);
	        }

		   log.debug("No of records returned for devices: " + collection.size() );

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

	  /* */
	  public static Collection getDecks() throws HibernateException
	  {

	   Session session = null;
	   StringBuffer sbQuery = new StringBuffer();

	   try
	   {
	      session = DBHelper.getInstance().getSession();

	      sbQuery.append(" FROM ");
	      sbQuery.append(" com.netpace.aims.model.system.AimsDeck de ");
	      sbQuery.append(" ORDER BY ");
	      sbQuery.append(" de.deckName ");

	      return session.find(sbQuery.toString());

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


	  /* */
	  public static MarketApplicationExt getApplicationDetail(Long applicationId) throws HibernateException, AimsException
	  {

	   Session session = null;
	   StringBuffer sbQuery = new StringBuffer();
	   MarketApplicationExt mae = new MarketApplicationExt();
	   int iRecords, iCounter = 0;
	   String[] deviceName = null;

	   try
	   {
	        session = DBHelper.getInstance().getSession();

	        /* Getting Application details */

	        sbQuery.append("select app.appsId, app.title, alliance.companyName,  deck.deckName, bapp.deckLaunchDate, app.shortDesc, app.longDesc, appfile.screenJpeg, appfile.screenJpeg2, appfile.screenJpeg3, appfile.screenJpeg4, appfile.screenJpeg5 ")
	           .append(" FROM com.netpace.aims.model.application.AimsBrewApp bapp, com.netpace.aims.model.application.AimsApp app, com.netpace.aims.model.system.AimsDeck deck, com.netpace.aims.model.core.AimsAllianc alliance, ")
			   .append(" com.netpace.aims.model.application.AimsAppFiles appfile")
			   .append(" WHERE bapp.brewAppsId = ")
			   .append(applicationId).append(" AND bapp.brewAppsId = app.appsId AND bapp.deckPlacement = deck.deckId ")
			   .append(" AND app.aimsAllianceId = alliance.allianceId  AND appfile.appsId = app.appsId ");

			Object[] objValues = null;
	        Query	query	=	session.createQuery(sbQuery.toString());
	        Iterator	it = query.iterate();
	        Object[] objFiles = new Object[5];

	        if(it.hasNext()) {
				objValues = (Object[]) it.next();
				mae.setApplicationId((Long)objValues[0]);
				mae.setAppTitle((String)objValues[1]);
				mae.setDeveloperName((String)objValues[2]);
				mae.setDeckPlacement((String)objValues[3]);
				mae.setLaunchDate((Date)objValues[4]);
				mae.setShortDesc((String)objValues[5]);
				mae.setLongDesc((String)objValues[6]);
				objFiles [0] = objValues[7];
				objFiles [1] = objValues[8];
				objFiles [2] = objValues[9];
				objFiles [3] = objValues[10];
				objFiles [4] = objValues[11];
				mae.setBlobImages(objFiles);
	        }
	        else {
	        	throw new AimsException("Record not found");
	        }

	        /* Getting Device Names for application */
	        sbQuery.delete(0,sbQuery.length()-1);
			sbQuery.append(" SELECT ad.deviceModel ");
			sbQuery.append(" FROM ");
			sbQuery.append(" com.netpace.aims.model.masters.AimsDevic ad ");
			sbQuery.append(" WHERE ");
			sbQuery.append(" ad.deviceId IN ( select badev.deviceId FROM com.netpace.aims.model.application.AimsBrewAppDevice badev WHERE badev.brewAppsId = ").append(applicationId).append(" ) ");
			sbQuery.append(" ORDER BY ");
			sbQuery.append(" ad.deviceModel ");

			query	=	session.createQuery(sbQuery.toString());
			iRecords = query.list().size();

			if (iRecords > 0 )
				deviceName = new String[iRecords];

			for	(it = query.iterate();	it.hasNext();)
				deviceName[iCounter++] = (String) it.next();

		    mae.setDeviceName(deviceName);

		    return mae;
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


          public static MarketApplicationExt getContentDetail(Long contentId) throws HibernateException, AimsException
                   {

                    Session session = null;
                    StringBuffer sbQuery = new StringBuffer();
                    MarketApplicationExt mae = new MarketApplicationExt();
                    int iRecords, iCounter = 0;
                    String[] deviceName = null;

                    try
                    {
                         session = DBHelper.getInstance().getSession();

                         /* Getting Application details */

                         sbQuery.append("select acc.creativeContentId, acc.contentTitle, acc.contentDescription, acc.applicationTitle, acc.expiryDate, accf.screenJpeg1, accf.screenJpeg2, accf.screenJpeg3, accf.screenJpeg4, accf.screenJpeg5 ")
                                .append(" FROM  com.netpace.aims.model.newmarketing.AimsCreativeContent acc,  ")
                                .append(" com.netpace.aims.model.newmarketing.AimsCreativeContentFiles accf")
                                .append(" WHERE acc.creativeContentId = ")
                                .append(contentId)
                                .append(" AND accf.creativeContentId= ")
                                .append(contentId);


                         log.debug(" ******* Query: " + sbQuery.toString() );

                           Object[] objValues = null;
                         Query	query	=	session.createQuery(sbQuery.toString());
                         Iterator	it = query.iterate();
                         Object[] objFiles = new Object[5];

                         if(it.hasNext()) {
                                         objValues = (Object[]) it.next();
                                         mae.setApplicationId((Long)objValues[0]);
                                         mae.setAppTitle((String)objValues[1]);
                                         mae.setDeveloperName((String)objValues[2]);
                                         mae.setDeckPlacement((String)objValues[3]);
                                         mae.setLaunchDate((Date)objValues[4]);
                                         objFiles [0] = objValues[5];
                                         objFiles [1] = objValues[6];
                                         objFiles [2] = objValues[7];
                                         objFiles [3] = objValues[8];
                                         objFiles [4] = objValues[9];
                                         mae.setBlobImages(objFiles);
                         }
                         else {
                                 throw new AimsException("Record not found");
                         }


                         log.debug(" ******* Try Block Ends " );
                             return mae;
                      }
                      catch(HibernateException e)
                      {
                        log.debug(" Exception : " + e.getMessage());
                       e.printStackTrace();
                       throw e;
                      }

                      finally
                      {
                       session.close();
                      }

                   }



		/**
		 * This methods returns the list of provisioned contents to the user
		 * Method accepts the query filter and append it to the listing query after an AND clause.
		 * */
		public static	Collection getProvApplications(String filterQuery, int	PAGE_LENGTH, int page_no, Long userId)	throws HibernateException
		{
			Session	session	=	null;
			StringBuffer queryString = new StringBuffer();
			Object[] objValues;
			ArrayList objArray = new ArrayList();

			queryString.append("select distinct app.appsId, app.title, alliance.companyName,  creq.crequestId, creq.approvalDate, creq.expiryDate, creq.programName ")
			           .append(" FROM com.netpace.aims.model.marketing.AimsContentReqApp creqapp, com.netpace.aims.model.marketing.AimsContentRequest creq, com.netpace.aims.model.application.AimsApp app, com.netpace.aims.model.core.AimsAllianc alliance ")
					   .append(" WHERE creq.userId = ").append(userId).append(" AND creqapp.crequestId = creq.crequestId AND creqapp.appsId = app.appsId  AND (creq.status = 'PARTIAL APPROVAL' OR creq.status = 'ACCEPTED' ) AND creqapp.status = 'ACCEPTED' ")
					   .append(" AND app.aimsAllianceId = alliance.allianceId ");
			if (filterQuery != null && filterQuery.length() > 0)
				queryString.append(" AND ").append(filterQuery);

			queryString.append(" ORDER BY creq.expiryDate ");

			try
			{
				//Set	Paging Logic and execute Query
				session	=	DBHelper.getInstance().getSession();
				Query	query	=	session.createQuery(queryString.toString());
				query.setMaxResults(PAGE_LENGTH);
				query.setFirstResult(PAGE_LENGTH * (page_no-1));

				for ( Iterator it = query.iterate(); it.hasNext();){
					objValues = (Object[]) it.next();
					ProvApplicationExt appDetail = new ProvApplicationExt();
					appDetail.setApplicationId((Long)objValues[0]);
					appDetail.setAppTitle((String)objValues[1]);
					appDetail.setDeveloperName((String)objValues[2]);
					appDetail.setCRequestId((Long)objValues[3]);
					appDetail.setApprovalDate((Date)objValues[4]);
					appDetail.setExpiryDate((Date)objValues[5]);
					appDetail.setProgramName((String)objValues[6]);
					objArray.add(appDetail);
				}

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
			log.debug("No	of apps	returnedx :	"	+	objArray.size()	);
			return objArray;
		}


                public static	Collection getProvContents(String filterQuery, int	PAGE_LENGTH, int page_no, Long userId)	throws HibernateException
                        {
                                Session	session	=	null;
                                StringBuffer queryString = new StringBuffer();
                                Object[] objValues;
                                ArrayList objArray = new ArrayList();

                                queryString.append("select distinct acc.creativeContentId, acc.contentTitle, aa.companyName, acc.approvalDate, acc.expiryDate, acc.status ")
                                           .append(" FROM com.netpace.aims.model.newmarketing.AimsCreativeContent acc, com.netpace.aims.model.core.AimsAllianc aa, com.netpace.aims.model.core.AimsUser au  ")
                                           .append(" WHERE acc.status= 'APPROVED' AND acc.userId= au.userId ")
                                           .append(" AND aa.allianceId = au.aimsAllianc ");



                                if (filterQuery != null && filterQuery.length() > 0)
                                        queryString.append(" AND ").append(filterQuery);

                                 queryString.append(" ORDER BY acc.expiryDate ");
                                 System.out.println(" Query : " +  queryString.toString());

                                try
                                {
                                        //Set	Paging Logic and execute Query
                                        session	=	DBHelper.getInstance().getSession();
                                        Query	query	=	session.createQuery(queryString.toString());
                                        query.setMaxResults(PAGE_LENGTH);
                                        query.setFirstResult(PAGE_LENGTH * (page_no-1));

                                        for ( Iterator it = query.iterate(); it.hasNext();){
                                                objValues = (Object[]) it.next();
                                                ProvApplicationExt appDetail = new ProvApplicationExt();
                                                appDetail.setApplicationId((Long)objValues[0]);
                                                appDetail.setAppTitle((String)objValues[1]);
                                                appDetail.setDeveloperName((String)objValues[2]);
                                                appDetail.setCRequestId((Long)objValues[0]);
                                                appDetail.setApprovalDate((Date)objValues[3]);
                                                appDetail.setExpiryDate((Date)objValues[4]);
                                                //appDetail.setProgramName((String)objValues[5]);
                                                objArray.add(appDetail);
                                        }

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
                                log.debug("No	of apps	returnedx :	"	+	objArray.size()	);
                                return objArray;
                        }




/**
 * 	Returns the total no of records for provisioned application
 * */
		public static	int	getProvRecordCount(	String filterQuery, Long userId)	throws HibernateException
		{
			int	rows = 0;
			Collection collection	=	null;
			Session	session	=	null;
			StringBuffer queryString = new StringBuffer();

			try
			{
				session	=	DBHelper.getInstance().getSession();


                                queryString.append("select distinct acc.creativeContentId, acc.contentTitle, aa.companyName, acc.approvalDate, acc.expiryDate, acc.status ")
                                    .append(" FROM com.netpace.aims.model.newmarketing.AimsCreativeContent acc, com.netpace.aims.model.core.AimsAllianc aa, com.netpace.aims.model.core.AimsUser au  ")
                                    .append(" WHERE acc.status= 'APPROVED' AND acc.userId= au.userId ")
                                    .append(" AND aa.allianceId = au.aimsAllianc ");

                                if (filterQuery != null && filterQuery.length() > 0)
                                  queryString.append(" AND ").append(filterQuery);

//
//				queryString.append("select distinct app.appsId, creq.crequestId ")
//		           .append(" FROM com.netpace.aims.model.marketing.AimsContentReqApp creqapp, com.netpace.aims.model.marketing.AimsContentRequest creq, com.netpace.aims.model.application.AimsApp app, com.netpace.aims.model.core.AimsAllianc alliance ")
//				   .append(" WHERE creq.userId = ").append(userId).append(" AND creqapp.crequestId = creq.crequestId AND creqapp.appsId = app.appsId  AND (creq.status = 'PARTIAL APPROVAL' OR creq.status = 'ACCEPTED' ) AND creqapp.status = 'ACCEPTED' ")
//				   .append(" AND app.aimsAllianceId = alliance.allianceId ");

//					if (filterQuery != null && filterQuery.length() > 0)
//					queryString.append(" AND ").append(filterQuery);

				collection = session.find(queryString.toString());
				log.debug("No	of apps	returnedz :	"	+	collection.size()	);
				rows = collection.size();
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
			return rows;

		}

		/**
		 * This methods returns the list of provisioned contents to the user
		 * Method accepts the query filter and append it to the listing query after an AND clause.
		 * */
		public static ProvApplicationDetailsExt getProvApplicationDetail(Long AppsId, Long userId)	throws HibernateException
		{
			Session	session	=	null;
			StringBuffer queryString = new StringBuffer();
			Object[] objValues;
			ArrayList objArray = new ArrayList();

			queryString.append("select app.appsId, app.title, app.version , alliance.companyName,  deck.deckName, bapp.deckLaunchDate ")
			   .append(" , app.shortDesc, app.longDesc, bapp.networkUse, bapp.singleMultiPlayer ")
			   .append(" , app.screenJpegFileName, app.screenJpeg2FileName, app.screenJpeg3FileName, app.screenJpeg4FileName, app.screenJpeg5FileName ")
			   .append(" , bapp.clrPubLogoFileName, app.splashScreenEpsFileName, app.activeScreenEpsFileName, bapp.appTitleNameFileName ")
                           .append(" FROM com.netpace.aims.model.application.AimsBrewApp bapp, com.netpace.aims.model.application.AimsApp app, com.netpace.aims.model.system.AimsDeck deck, com.netpace.aims.model.core.AimsAllianc alliance ")
			   .append(" WHERE bapp.brewAppsId = ").append(AppsId).append(" AND app.appsId = bapp.brewAppsId AND bapp.deckPlacement = deck.deckId ")
			   .append(" AND app.aimsAllianceId = alliance.allianceId ");

			try
			{
				//Set	Paging Logic and execute Query
				session	=	DBHelper.getInstance().getSession();
				Query	query	=	session.createQuery(queryString.toString());
				ProvApplicationDetailsExt appDetail = new ProvApplicationDetailsExt();

				Iterator it = query.iterate();
				if (it.hasNext()){
					objValues = (Object[]) it.next();
					appDetail.setApplicationId((Long)objValues[0]);
					appDetail.setAppTitle((String)objValues[1]);
					appDetail.setVersion((String)objValues[2]);
					appDetail.setDeveloperName((String)objValues[3]);
					appDetail.setDeckName((String)objValues[4]);
					appDetail.setShortDesc((String)objValues[6]);
					appDetail.setLongDesc((String)objValues[7]);
					appDetail.setNetworkUsage((String)objValues[8]);
					appDetail.setSingleMultiplayer((String)objValues[9]);
					appDetail.setScreenName1((String)objValues[10]);
					appDetail.setScreenName2((String)objValues[11]);
					appDetail.setScreenName3((String)objValues[12]);
					appDetail.setScreenName4((String)objValues[13]);
					appDetail.setScreenName5((String)objValues[14]);
					appDetail.setHiResPublisherLogoName((String)objValues[15]);
					appDetail.setSplashScreenName((String)objValues[16]);
					appDetail.setActiveScreenName((String)objValues[17]);
					appDetail.setAppTitleGraphicsName((String)objValues[18]);
				}

		        /* Getting Device Names for application */
				queryString.delete(0,queryString.length()-1);
				queryString.append(" SELECT ad.deviceModel ");
				queryString.append(" FROM ");
				queryString.append(" com.netpace.aims.model.masters.AimsDevic ad ");
				queryString.append(" WHERE ");
				queryString.append(" ad.deviceId IN ( select badev.deviceId FROM com.netpace.aims.model.application.AimsBrewAppDevice badev WHERE badev.brewAppsId = ").append(AppsId).append(" ) ");
				queryString.append(" ORDER BY ");
				queryString.append(" ad.deviceModel ");

				query	=	session.createQuery(queryString.toString());
				int iRecords = query.list().size();
				int iCounter=0;
				String[] deviceName = null;

				if (iRecords > 0 )
					deviceName = new String[iRecords];

				for	(it = query.iterate();	it.hasNext();)
					deviceName[iCounter++] = (String) it.next();

				appDetail.setDevicesNames(deviceName);
				return appDetail;
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



                /**
                 * This methods returns the list of provisioned contents to the user
                 * Method accepts the query filter and append it to the listing query after an AND clause.
                 * */
                public static ProvApplicationDetailsExt getAcceptedContentDetail(Long contentId) throws HibernateException,AimsException
                {
                        Session	session	=	null;
                        StringBuffer queryString = new StringBuffer();
                        Object[] objValues;
                        ArrayList objArray = new ArrayList();

//                        queryString.append("select app.appsId, app.title, app.version , alliance.companyName,  deck.deckName, bapp.deckLaunchDate ")
//                           .append(" , app.shortDesc, app.longDesc, bapp.networkUse, bapp.singleMultiPlayer ")
//                           .append(" , app.screenJpegFileName, app.screenJpeg2FileName, app.screenJpeg3FileName, app.screenJpeg4FileName, app.screenJpeg5FileName ")
//                           .append(" , bapp.clrPubLogoFileName, app.splashScreenEpsFileName, app.activeScreenEpsFileName, bapp.appTitleNameFileName ")
//                           .append(" FROM com.netpace.aims.model.application.AimsBrewApp bapp, com.netpace.aims.model.application.AimsApp app, com.netpace.aims.model.system.AimsDeck deck, com.netpace.aims.model.core.AimsAllianc alliance ")
//                           .append(" WHERE bapp.brewAppsId = ").append(AppsId).append(" AND app.appsId = bapp.brewAppsId AND bapp.deckPlacement = deck.deckId ")
//                           .append(" AND app.aimsAllianceId = alliance.allianceId ");


                       queryString.append("select acc.creativeContentId, acc.contentTitle, acc.contentDescription, acc.applicationTitle, acc.status, acc.screenJpeg1FileName, acc.screenJpeg2FileName, acc.screenJpeg3FileName, acc.screenJpeg4FileName, acc.screenJpeg5FileName, ")
                           .append(" acc.publisherLogoFileName, acc.splashScreenFileName, acc.activeScreenFileName ,acc.appTitleGraphicFileName")
                           .append(" FROM  com.netpace.aims.model.newmarketing.AimsCreativeContent acc ")
                           .append(" WHERE acc.creativeContentId = ")
                           .append(contentId);

                           log.debug(" ******* Query: " + queryString.toString() );

                        try
                        {
                                //Set	Paging Logic and execute Query
                                session	=	DBHelper.getInstance().getSession();
                                Query	query	=	session.createQuery(queryString.toString());
                                ProvApplicationDetailsExt appDetail = new ProvApplicationDetailsExt();

                                Iterator it = query.iterate();
                                if (it.hasNext()){
                                        objValues = (Object[]) it.next();
                                        appDetail.setApplicationId((Long)objValues[0]);
                                        appDetail.setAppTitle((String)objValues[1]);
                                        appDetail.setVersion((String)objValues[2]);
                                        appDetail.setShortDesc((String)objValues[3]);
                                        appDetail.setLongDesc((String)objValues[4]);
                                        appDetail.setScreenName1((String)objValues[5]);
                                        appDetail.setScreenName2((String)objValues[6]);
                                        appDetail.setScreenName3((String)objValues[7]);
                                        appDetail.setScreenName4((String)objValues[8]);
                                        appDetail.setScreenName5((String)objValues[9]);
                                        appDetail.setHiResPublisherLogoName((String)objValues[10]);
                                        appDetail.setSplashScreenName((String)objValues[11]);
                                        appDetail.setActiveScreenName((String)objValues[12]);
                                        appDetail.setAppTitleGraphicsName((String)objValues[13]);
                                }
                                else {
                                  throw new AimsException("Record not found");
                                }

                                return appDetail;
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




		  /* */
		  public static MarketApplicationExt getApplicationBrief(Long applicationId) throws HibernateException, AimsException
		  {

		   Session session = null;
		   StringBuffer sbQuery = new StringBuffer();
		   MarketApplicationExt mae = new MarketApplicationExt();
		   int iRecords, iCounter = 0;
		   String[] deviceName = null;

		   try
		   {
		        session = DBHelper.getInstance().getSession();

		        /* Getting Application brief and partially populating the MarketApplicationExt class */
		        sbQuery.append("select app.appsId, app.title, app.shortDesc, app.version ")
		           .append(" FROM com.netpace.aims.model.application.AimsBrewApp bapp, com.netpace.aims.model.application.AimsApp app, com.netpace.aims.model.core.AimsAllianc alliance ")
				   .append(" WHERE bapp.brewAppsId = ")
				   .append(applicationId).append(" AND bapp.brewAppsId = app.appsId  ")
				   .append(" AND app.aimsAllianceId = alliance.allianceId ");

				Object[] objValues = null;
		        Query	query	=	session.createQuery(sbQuery.toString());
		        Iterator	it = query.iterate();

		        if(it.hasNext()) {
					objValues = (Object[]) it.next();
					mae.setApplicationId((Long)objValues[0]);
					mae.setAppTitle((String)objValues[1]);
					mae.setShortDesc((String)objValues[2]);
					mae.setVersion((String)objValues[3]);
		        }
		        else {
		        	throw new AimsException("Record not found");
		        }

		        /* Getting Device Names for application */
		        sbQuery.delete(0,sbQuery.length()-1);
				sbQuery.append(" SELECT ad.deviceModel ");
				sbQuery.append(" FROM ");
				sbQuery.append(" com.netpace.aims.model.masters.AimsDevic ad ");
				sbQuery.append(" WHERE ");
				sbQuery.append(" ad.deviceId IN ( select badev.deviceId FROM com.netpace.aims.model.application.AimsBrewAppDevice badev WHERE badev.brewAppsId = ").append(applicationId).append(" ) ");
				sbQuery.append(" ORDER BY ");
				sbQuery.append(" ad.deviceModel ");

				query	=	session.createQuery(sbQuery.toString());
				iRecords = query.list().size();

				if (iRecords > 0 )
					deviceName = new String[iRecords];

				for	(it = query.iterate();	it.hasNext();)
					deviceName[iCounter++] = (String) it.next();

			    mae.setDeviceName(deviceName);

			    return mae;
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

// Class Ends
}
