package com.netpace.aims.bo.application;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.netpace.aims.controller.application.EntAppSpotLightBean;
import com.netpace.aims.controller.application.JMASolutionDetailBean;
import com.netpace.aims.controller.application.PartnerDetailBean;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsEntAppsSpotlight;

public class AimsEntAppSpotlightManager {
	   
	static Logger log = Logger.getLogger(AimsEntAppSpotlightManager.class.getName());
	
	 public static EntAppSpotLightBean getSpotlightInformation(Long solutionId, Long spotLightTypeId, boolean ifPublic) throws HibernateException, SQLException
	    {

	        Session session = null;
	        EntAppSpotLightBean spotLightInfo = new EntAppSpotLightBean();
	        PartnerDetailBean partnerDetail = new PartnerDetailBean();
	        JMASolutionDetailBean solutionDetail = new JMASolutionDetailBean();

	        Connection ConOra = null;
	        CallableStatement statement = null;
	        ResultSet results = null;
	        ArrayList arrayList = null;
	        ResultSet embeddedResults = null;

	        try
	        {
	            //Set	Paging Logic and execute Query
	            session = DBHelper.getInstance().getSession();
	            ConOra = session.connection();

	            statement = ConOra.prepareCall("call BDS_SPOTLIGHT.get_spotlight_page(?,?,?,?,?)");

	            statement.setLong(1, solutionId.longValue());
	            statement.setLong(2, spotLightTypeId.longValue());
	            if (ifPublic)
	                statement.setString(3, "T");
	            else
	                statement.setString(3, "F");
	            statement.registerOutParameter(4, OracleTypes.CURSOR);
	            statement.registerOutParameter(5, OracleTypes.CURSOR);

	            //execute the db statement
	            statement.execute();

	            results = (ResultSet) statement.getObject(4);
	            if (results != null)
	                results.setFetchSize(100);

	            while (results.next())
	            {

	                partnerDetail.setPartnerId(new Long(results.getLong(1)));
	                partnerDetail.setPartnerName(results.getString(2));
	                partnerDetail.setStreetAddress1(results.getString(3));
	                partnerDetail.setStreetAddress2(results.getString(4));
	                partnerDetail.setCity(results.getString(5));
	                partnerDetail.setState(results.getString(6));
	                partnerDetail.setZipCode(results.getString(7));
	                partnerDetail.setWebSiteUrl(results.getString(8));
	                partnerDetail.setNumFullTimeEmp(results.getString(9));
	                partnerDetail.setBusinessContactFirstName(results.getString(10));
	                partnerDetail.setBusinessContactLastName(results.getString(11));
	                partnerDetail.setBusinessContactPhone(results.getString(12));
	                partnerDetail.setBusinessContactEmailAddress(results.getString(13));
	                partnerDetail.setTechContactFirstName(results.getString(14));
	                partnerDetail.setTechContactLastName(results.getString(15));
	                partnerDetail.setTechContactPhone(results.getString(16));
	                partnerDetail.setTechContactEmailAddress(results.getString(17));
	            }

	            results.close();
	            results = null;
	            spotLightInfo.setPartnerDetail(partnerDetail);

	            results = (ResultSet) statement.getObject(5);
	            if (results != null)
	                results.setFetchSize(100);

	            while (results.next())
	            {
	                solutionDetail.setSolutionId(new Long(results.getLong(1)));
	                solutionDetail.setLongDesc(results.getString(2));
	                solutionDetail.setBdsName(results.getString(3));
	                solutionDetail.setAllianceSponsorFirstName(results.getString(4));
	                solutionDetail.setAllianceSponsorLastName(results.getString(5));
	                solutionDetail.setSolutionContactFirstName(results.getString(6));
	                solutionDetail.setSolutionContactLastName(results.getString(7));
	                solutionDetail.setSolutionContactPhone(results.getString(8));
	                solutionDetail.setSolutionContactEmailAddress(results.getString(9));
	                solutionDetail.setSpotLightMaxCreateDate(results.getDate(10));
	                solutionDetail.setSpotLightTypeName(results.getString(11));
	                solutionDetail.setQrgSpotLightId(new Long(results.getLong(12)));
	                solutionDetail.setSolutionContactTitle(results.getString(18));

	                //Adding Regions
	                embeddedResults = (ResultSet) results.getObject(13);
	                arrayList = new ArrayList();
	                for (int j = 0; embeddedResults.next(); j++)
	                {
	                    log.debug("\n\nTHE SOLUTION REGIONS: ");
	                    arrayList.add(new Long(embeddedResults.getLong(1)));
	                }

	                Long[] regionArray = new Long[arrayList.size()];

	                for (int k = 0; k < arrayList.size(); k++)
	                {
	                    regionArray[k] = (Long) arrayList.get(k);
	                }

	                embeddedResults.close();
	                embeddedResults = null;
	                solutionDetail.setRegions(regionArray);

	                //Adding Industry Focus
	                embeddedResults = (ResultSet) results.getObject(14);
	                arrayList = new ArrayList();
	                for (int j = 0; embeddedResults.next(); j++)
	                {
	                    log.debug("\n\nTHE IND FOCUS: ");
	                    arrayList.add(new Long(embeddedResults.getLong(1)));
	                }

	                Long[] indFocusArray = new Long[arrayList.size()];

	                for (int k = 0; k < arrayList.size(); k++)
	                {
	                    indFocusArray[k] = (Long) arrayList.get(k);
	                }

	                embeddedResults.close();
	                embeddedResults = null;
	                solutionDetail.setIndFocuses(indFocusArray);

	                //Adding Market Segments
	                embeddedResults = (ResultSet) results.getObject(15);
	                arrayList = new ArrayList();
	                for (int j = 0; embeddedResults.next(); j++)
	                {
	                    log.debug("\n\nTHE MKT SEGMENTS: ");
	                    arrayList.add(new Long(embeddedResults.getLong(1)));
	                }

	                Long[] mktSegmentArray = new Long[arrayList.size()];

	                for (int k = 0; k < arrayList.size(); k++)
	                {
	                    mktSegmentArray[k] = (Long) arrayList.get(k);
	                }

	                embeddedResults.close();
	                embeddedResults = null;
	                solutionDetail.setMktSegments(mktSegmentArray);

	                //Adding Spotlights
	                embeddedResults = (ResultSet) results.getObject(16);
	                arrayList = new ArrayList();
	                for (int j = 0; embeddedResults.next(); j++)
	                {
	                    log.debug("\n\nTHE SPOTLIGHTS: ");
	                    AimsEntAppsSpotlight bdsPartnerSpotlight = new AimsEntAppsSpotlight();
	                    bdsPartnerSpotlight.setSpotlightId(new Long(embeddedResults.getLong(1)));
	                    bdsPartnerSpotlight.setSpotlightName(embeddedResults.getString(2));
	                    bdsPartnerSpotlight.setSpotlightDesc(embeddedResults.getString(3));
	                    bdsPartnerSpotlight.setSpotlightFileFileName(embeddedResults.getString(4));
	                    bdsPartnerSpotlight.setCreatedDate(embeddedResults.getDate(5));
	                    bdsPartnerSpotlight.setSpotlightFileContentType(embeddedResults.getString(6));
	                    arrayList.add(bdsPartnerSpotlight);
	                }
	                embeddedResults.close();
	                embeddedResults = null;
	                solutionDetail.setSpotLights(arrayList);

	                //Adding Spotlight Counts
	                embeddedResults = (ResultSet) results.getObject(17);
	                Map spotLightCounts = new HashMap();
	                for (int j = 0; embeddedResults.next(); j++)
	                {
	                    //log.debug("\n\nTHE SPOTLIGHT COUNTS: ");
	                    spotLightCounts.put(new Long(embeddedResults.getLong(1)), new Long(embeddedResults.getLong(2)));
	                    //log.debug("SpotLight Type ID: " + embeddedResults.getLong(1));
	                    //log.debug("Count (Number of Documents): " + embeddedResults.getLong(2));
	                }
	                embeddedResults.close();
	                embeddedResults = null;
	                solutionDetail.setSpotLightCounts(spotLightCounts);
	            }

	            results.close();
	            results = null;

	            spotLightInfo.setSolutionDetail(solutionDetail);
	        }

	        catch (HibernateException e)
	        {
	            log.error("Exception while searching for solutions", e);
	            throw e;
	        }

	        finally
	        {
	            if (results != null)
	            {
	                results.close();
	                results = null;
	            }
	            if (statement != null)
	            {
	                statement.close();
	                statement = null;
	            }
	            if (ConOra != null)
	                ConOra.commit();

	            session.close();
	        }

	        return spotLightInfo;

	    }
	 
	 public static Collection getRegionsList() throws HibernateException
	    {
	        Collection collection = null;
	        Session session = null;
	        try
	        {
	            session = DBHelper.getInstance().getSession();
	            collection = session.find("from	com.netpace.aims.model.masters.AimsRegion as region order by region.regionName");
	            log.debug("No. of Regions : " + collection.size());
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

	    public static Collection getIndustryFocusList() throws HibernateException
	    {
	        Collection collection = null;
	        Session session = null;
	        try
	        {
	            session = DBHelper.getInstance().getSession();
	            collection = session.find("from	com.netpace.aims.model.masters.AimsIndustryFocu as indFocus order by indFocus.industryName");
	            log.debug("No. of Industry Focus : " + collection.size());
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

	    public static Collection getMarketSegmentsList() throws HibernateException
	    {
	        Collection collection = null;
	        Session session = null;
	        try
	        {
	            session = DBHelper.getInstance().getSession();
	            collection = session.find("from	com.netpace.aims.model.masters.BdsMarketSegment as mktSegment order by mktSegment.marketSegmentName");
	            log.debug("No. of Market Segments : " + collection.size());
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
