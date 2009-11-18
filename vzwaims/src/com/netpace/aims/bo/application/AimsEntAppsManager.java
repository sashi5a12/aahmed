package com.netpace.aims.bo.application;

import net.sf.hibernate.*;
import net.sf.hibernate.type.LongType;

import org.apache.log4j.Logger;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.*;

import oracle.jdbc.driver.*;
import oracle.sql.ArrayDescriptor;

import com.netpace.aims.model.*;
import com.netpace.aims.util.*;
import com.netpace.aims.bo.core.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.AimsIndustryFocu;
import com.netpace.aims.model.application.*;
import com.netpace.aims.controller.application.AimsAppsCaseStudyExt;
import com.netpace.aims.controller.application.EntAppPublishSolutionForm;
import com.netpace.aims.controller.application.EntAppPublishSolutionVO;

/**
 * Decsription here
 * @author Ahson Imtiaz
 */
public class AimsEntAppsManager
{

    static Logger log = Logger.getLogger(AimsEntAppsManager.class.getName());

    public static HashMap getApp(Long appsId, Long allianceId) throws AimsException, HibernateException
    {
        AimsApp aimsApp = null;
        Session session = null;
        HashMap appEnt = new HashMap();

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("       apps, entapps, category ")
                .append("   from ")
                .append("       com.netpace.aims.model.application.AimsApp	as apps, ")
                .append("       com.netpace.aims.model.application.AimsEnterpriseApp	as entapps, ")
                .append("       com.netpace.aims.model.application.AimsAppSubCategory	as subcategory, ")
                .append("       com.netpace.aims.model.application.AimsAppCategory	as category, ")
                .append("       com.netpace.aims.model.core.AimsAllianc as alliance ")
                .append("   where ")
                .append("       apps.appsId = entapps.enterpriseAppsId ")
                .append("       and	apps.aimsAppSubCategoryId = subcategory.subCategoryId (+) ")
                .append("       and	subcategory.aimsAppCategoryId = category.categoryId (+) ")
                .append("       and apps.aimsAllianceId = alliance.allianceId ")
                .append("       and	apps.appsId = :appsId ");

            //Set 'Alliance Id' if User is 'Alliance User' (allianceId=null).
            if (allianceId != null)
                queryStringBuffer.append("  and apps.aimsAllianceId = " + allianceId);

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", appsId.longValue());

            Object[] appValues = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                appValues = (Object[]) it.next();
                log.debug("AimsApp" + (AimsApp) appValues[0]);
                appEnt.put("AimsApp", appValues[0]); // AimsApp
                log.debug("AimsEntApp" + (AimsEnterpriseApp) appValues[1]);
                appEnt.put("AimsEntApp", appValues[1]); // AimsEntApp
                log.debug("AimsAppCategory" + (AimsAppCategory) appValues[2]);
                appEnt.put("AimsAppCategory", appValues[2]); // AimsAppCategory
            }
            if (appEnt.isEmpty())
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
            log.debug("SESSION CLOSED IN getApp()");
        }

        return appEnt;
    }

    /**/
    public static HashSet getCaseStudies(Long appsId) throws AimsException, HibernateException
    {
        AimsApp aimsApp = null;
        Session session = null;
        HashSet hsCaseStudies = new HashSet();

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("       casestudy ")
                .append("   from ")
                .append("       com.netpace.aims.model.application.AimsEnterpriseApp as entapp, ")
                .append("       com.netpace.aims.model.application.AimsAppsCaseStudy as casestudy ")
                .append("   where ")
                .append("       entapp = casestudy.aimsEnterpriseApp ")
                .append("       and entapp.enterpriseAppsId = :appsId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", appsId.longValue());

            Object[] appValues = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                AimsAppsCaseStudy aacs = (AimsAppsCaseStudy) it.next();
                AimsAppsCaseStudyExt aacsex = new AimsAppsCaseStudyExt(aacs);
                hsCaseStudies.add(aacsex);
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
            log.debug("SESSION CLOSED IN getApp()");
        }

        return hsCaseStudies;
    }

    /**/

    public static AimsApp getAimsApps(java.lang.Long lngAimsAppsId) throws HibernateException
    {
        Session session = null;
        AimsApp objAimsApp = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            objAimsApp = (AimsApp) session.find("from com.netpace.aims.model.application.AimsApp as aapps where aapps.appsId = " + lngAimsAppsId.toString()).get(0);
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

        return objAimsApp;
    }
    
    public static AimsEnterpriseApp getAimsEntApps(java.lang.Long lngAimsAppsId) throws HibernateException
    {
        Session session = null;
        AimsEnterpriseApp objAimsEntApp = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            objAimsEntApp = (AimsEnterpriseApp) session.find("from com.netpace.aims.model.application.AimsEnterpriseApp as aapps where aapps.enterpriseAppsId = " + lngAimsAppsId.toString()).get(0);
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

        return objAimsEntApp;
    }

    public static Collection getAimsPlatforms() throws HibernateException
    {
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            return session.find("from com.netpace.aims.model.core.AimsPlatform as aplatforms order by aplatforms.platformId");
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
    /* */

    public static Collection getAimsIndustryFocuses(java.lang.Long[] lngIFIds) throws HibernateException
    {
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            String strQuery = "from com.netpace.aims.model.masters.AimsIndustryFocu as afs ";

            for (int iCount = 0; iCount < lngIFIds.length; iCount++)
            {
                if (iCount == 0)
                    strQuery += " where afs.industryId = " + lngIFIds[iCount].toString();
                else
                    strQuery += " OR afs.industryId = " + lngIFIds[iCount].toString();
            }

            return session.find(strQuery);

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

    public static Collection getEntAppSubCategories(java.lang.Long[] lngEntAppSubCategoryIds) throws HibernateException
    {
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            String strQuery = "from com.netpace.aims.model.application.AimsAppSubCategory as subCategory ";

            for (int iCount = 0; iCount < lngEntAppSubCategoryIds.length; iCount++)
            {
                if (iCount == 0)
                    strQuery += " where subCategory.subCategoryId = " + lngEntAppSubCategoryIds[iCount].toString();
                else
                    strQuery += " OR subCategory.subCategoryId = " + lngEntAppSubCategoryIds[iCount].toString();
            }

            log.debug("*********** Regions Query: " + strQuery);
            return session.find(strQuery);

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

    public static Collection getAimsRegions() throws HibernateException
    {
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            return session.find("from com.netpace.aims.model.masters.AimsRegion as regions");
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

    public static Collection getAimsRegions(java.lang.Long[] lngRegionIds) throws HibernateException
    {
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            String strQuery = "from com.netpace.aims.model.masters.AimsRegion as regions ";

            for (int iCount = 0; iCount < lngRegionIds.length; iCount++)
            {
                if (iCount == 0)
                    strQuery += " where regions.regionId = " + lngRegionIds[iCount].toString();
                else
                    strQuery += " OR regions.regionId = " + lngRegionIds[iCount].toString();
            }

            log.debug("*********** Regions Query: " + strQuery);
            return session.find(strQuery);

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

    public static Collection getAimsSolutionComponents() throws HibernateException
    {
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            return session.find("from com.netpace.aims.model.application.AimsSolutionComponent as solComp");
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

    public static Collection getAimsSolutionComponents(java.lang.Long[] lngSolutionComponentIds) throws HibernateException
    {
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            String strQuery = "from com.netpace.aims.model.application.AimsSolutionComponent as solutionComponents ";

            for (int iCount = 0; iCount < lngSolutionComponentIds.length; iCount++)
            {
                if (iCount == 0)
                    strQuery += " where solutionComponents.solutionComponentId = " + lngSolutionComponentIds[iCount].toString();
                else
                    strQuery += " OR solutionComponents.solutionComponentId = " + lngSolutionComponentIds[iCount].toString();
            }

            log.debug("*********** SolutionComponents Query: " + strQuery);
            return session.find(strQuery);

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

    
    public static Collection getAimsEntAppsProductInfo() throws HibernateException
    {
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            return session.find("from com.netpace.aims.model.application.AimsEntAppProductInfo as productInfo");
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
    
    /* */

    public static void saveOrUpdate(
        AimsApp oAimsApp,
        AimsContact oAimsContact,
        AimsEnterpriseApp oAimsEnterpriseApp,
        String userId,
        String userType,
        Long ffFlashDemo,
        Long ffFAQs,
        Long ffUserGuide,
        Long ffTestPlan,
        Long ffPresentation,
        Long ffBOBO,
        Long ffLBS,
        Collection colAimsSolutionComponents,
        Collection colAimsRegions,
        Collection colAimsEntAppSubCategories,
        Collection colAimsIndFocuses,
        Collection colAimsCaseStudies,
        Set certificationSet,
        Set testingPhaseSet,
        Long clonedFromAppId,
        Long[] productInfo,
        Long[] marketSegment,
        Long[] bdsMktSeg)
        throws AimsException, HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;
        boolean newApp = false;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            //If Aims Contact Id provided zero (0), then create new Contact
            if ((oAimsApp.getAimsContactId() != null) && (oAimsApp.getAimsContactId().longValue() == 0))
            {
                //If All Non-Nullable Fields provided for Contact, then create new Contact
                if ((oAimsContact.getFirstName().length() > 0)
                    && (oAimsContact.getLastName().length() > 0)
                    && (oAimsContact.getEmailAddress().length() > 0)
                    && (oAimsContact.getTitle().length() > 0))
                    oAimsApp.setAimsContactId(AimsApplicationsManager.saveOrUpdateContact(oAimsContact));
                else
                    oAimsApp.setAimsContactId(null);
            }

            // Check is new Record to get inserted
            if (oAimsApp.getAppsId() == null)
            {
                session.saveOrUpdate(oAimsApp);
                oAimsEnterpriseApp.setEnterpriseAppsId(oAimsApp.getAppsId());
                session.save(oAimsEnterpriseApp);
                newApp = true;
            }
            else // Rather its for Update
                {
                session.saveOrUpdate(oAimsApp);
                oAimsEnterpriseApp.setEnterpriseAppsId(oAimsApp.getAppsId());
                session.update(oAimsEnterpriseApp);
                newApp = false;
            }

            //This section for ALLIANCE USERs Only
            //Now VZW user can also update solution, reference Bug 6319
            if (userType.equals(AimsConstants.ALLIANCE_USERTYPE) || userType.equals(AimsConstants.VZW_USERTYPE))
            {
                if (colAimsEntAppSubCategories != null && colAimsEntAppSubCategories.size() > 0)
                    oAimsEnterpriseApp.setAimsEntAppsSubCategories(new java.util.HashSet(colAimsEntAppSubCategories));

                if (colAimsRegions != null && colAimsRegions.size() > 0)
                    oAimsEnterpriseApp.setAimsEntAppsRegion(new java.util.HashSet(colAimsRegions));

                if (colAimsIndFocuses != null && colAimsIndFocuses.size() > 0)
                    oAimsEnterpriseApp.setAimsEntAppsIndFocus(new java.util.HashSet(colAimsIndFocuses));

                Set solutionComponentSet = oAimsEnterpriseApp.getAimsEntAppsSolComp();

                boolean bPresent = false;
                Set tempSet = new HashSet();

                if (solutionComponentSet != null)
                    log.debug("Orignal (solution components) solutionComponentSet.size(): " + solutionComponentSet.size());
                if (colAimsSolutionComponents != null)
                    log.debug("New (solution components) colAimsSolutionComponents.size(): " + colAimsSolutionComponents.size());

                if ((colAimsSolutionComponents != null) && (colAimsSolutionComponents.size() > 0))
                {
                    for (java.util.Iterator itr = colAimsSolutionComponents.iterator(); itr.hasNext();)
                    {
                        AimsEntAppSolComp aesc = (AimsEntAppSolComp) itr.next();
                        aesc.setEnterpriseAppsId(oAimsApp.getAppsId());

                        //Check to see if the entry already exists
                        bPresent = false;
                        if (solutionComponentSet != null)
                        {
                            for (Iterator itre = solutionComponentSet.iterator(); itre.hasNext();)
                            {
                                AimsEntAppSolComp aescOrig = (AimsEntAppSolComp) itre.next();
                                if (((AimsSolutionComponent) aescOrig.getAimsSolutionComponent())
                                    .getSolutionComponentId()
                                    .toString()
                                    .equals(((AimsSolutionComponent) aesc.getAimsSolutionComponent()).getSolutionComponentId().toString()))
                                {
                                    tempSet.add(aesc);
                                    session.update(aesc);
                                    bPresent = true;
                                    break;
                                }
                            }
                        }
                        //Add if not present
                        if (!(bPresent))
                        {
                            tempSet.add(aesc);
                            session.save(aesc);
                        }
                    }
                }

                if (tempSet != null)
                    log.debug("TO Delete (solution components) tempSet.size(): " + tempSet.size());

                //Check to see if existing entries in database does not contain deleted items				
                if ((solutionComponentSet != null) && (tempSet != null) && (tempSet.size() > 0))
                {
                    for (Iterator it = solutionComponentSet.iterator(); it.hasNext();)
                    {
                        AimsEntAppSolComp aesc = (AimsEntAppSolComp) it.next();
                        bPresent = false;
                        for (Iterator itr = tempSet.iterator(); itr.hasNext();)
                        {
                            AimsEntAppSolComp aescTemp = (AimsEntAppSolComp) itr.next();
                            if (((AimsSolutionComponent) aesc.getAimsSolutionComponent())
                                .getSolutionComponentId()
                                .toString()
                                .equals(((AimsSolutionComponent) aescTemp.getAimsSolutionComponent()).getSolutionComponentId().toString()))
                            {
                                bPresent = true;
                                break;
                            }
                        }
                        //Delete if not present
                        if (!(bPresent))
                            session.delete(aesc);
                    }
                }

                //Update product information
                AimsEntAppProductInfo productinfo=null;
                session.delete(
	                    "from com.netpace.aims.model.application.AimsEntAppProductInfo as productInfo where productInfo.enterpriseAppsId = :enterpriseAppsId",
	                    oAimsEnterpriseApp.getEnterpriseAppsId(),new LongType());
                if(productInfo!=null)
                {  
                	for (int i = 0; i < productInfo.length; i++)
                	{
                		productinfo=new AimsEntAppProductInfo();
                		productinfo.setEnterpriseAppsId(oAimsEnterpriseApp.getEnterpriseAppsId());
                		productinfo.setTypes((AimsTypes) session.load(AimsTypes.class,productInfo[i]));
                		session.save(productinfo);  
                	}
                	  
                }
              //Update Market Segment information
                AimsEntAppMarketSegmentInfo marketSeginfo=null;
                session.delete(
	                    "from com.netpace.aims.model.application.AimsEntAppMarketSegmentInfo as info where info.enterpriseAppsId = :enterpriseAppsId",
	                    oAimsEnterpriseApp.getEnterpriseAppsId(),new LongType());
                if(marketSegment!=null)
                {  
                	for (int i = 0; i < marketSegment.length; i++)
                	{
                		marketSeginfo=new AimsEntAppMarketSegmentInfo();
                		marketSeginfo.setEnterpriseAppsId(oAimsEnterpriseApp.getEnterpriseAppsId());
                		marketSeginfo.setTypes((AimsTypes) session.load(AimsTypes.class,marketSegment[i]));
                		session.save(marketSeginfo);  
                	}
                	  
                }
               
                /* CASE STUDIES PART STARTS*/
                /*Case Study will not save for JMA applicaion
                if (oAimsEnterpriseApp.getAimsAppsCaseStudies() != null)
                    for (java.util.Iterator itr = oAimsEnterpriseApp.getAimsAppsCaseStudies().iterator(); itr.hasNext();)
                    {
                        session.delete(itr.next());
                    }

                oAimsEnterpriseApp.setAimsAppsCaseStudies(null);
                session.flush();

                if (colAimsCaseStudies.size() > 0)
                {
                    java.util.HashSet hsCaseStudies = new java.util.HashSet();

                    for (java.util.Iterator itr = colAimsCaseStudies.iterator(); itr.hasNext();)
                    {
                        AimsAppsCaseStudyExt aacsex = (AimsAppsCaseStudyExt) itr.next();
                        AimsAppsCaseStudy aacs = new AimsAppsCaseStudy();
                        aacsex.copy2AimsAppsCaseStudy(aacs);
                        aacs.setAimsEnterpriseApp(oAimsEnterpriseApp);
                        session.save(aacs);
                        hsCaseStudies.add(aacs);
                    }

                    log.debug("Setting Case Studies " + hsCaseStudies.size());
                    oAimsEnterpriseApp.setAimsAppsCaseStudies(hsCaseStudies);
                }

                session.saveOrUpdate(oAimsEnterpriseApp);
                */
                /* CASE STUDIES ENDS*/
                
            }
            //End of section for ALLIANCE USERs Only

            //ApplicationsManagerHelper.addNewPhases(session, testingPhaseSet, oAimsApp, AimsConstants.ENTERPRISE_PLATFORM_ID, userType, null);

           // ApplicationsManagerHelper.saveTestPhases(session, testingPhaseSet, oAimsApp, userType);
            ApplicationsManagerHelper.saveCertifications(session, certificationSet, oAimsApp, userType);

            session.flush();

            /*
            *UPDATING DATABASE FOR IMAGES
            */

            java.sql.Connection ConOra = null;
            ConOra = session.connection();

            //Cloning Images
            if (clonedFromAppId != null)
                TempFilesManager.cloneImages(ConOra, clonedFromAppId, oAimsApp.getAppsId(), AimsConstants.ENTERPRISE_PLATFORM_ID);

            //Copying Images From Temp Table (if present)
            //TempFilesManager.copyImageFromTemp(ConOra, ffFlashDemo, oAimsApp.getAppsId(), userId, ManageApplicationsConstants.FLASH_DEMO_BLOB_DB_INFO);

            //TempFilesManager.copyImageFromTemp(ConOra, ffUserGuide, oAimsApp.getAppsId(), userId, ManageApplicationsConstants.USER_GUIDE_BLOB_DB_INFO);

            //TempFilesManager.copyImageFromTemp(ConOra, ffFAQs, oAimsApp.getAppsId(), userId, ManageApplicationsConstants.FAQ_DOC_BLOB_DB_INFO);

            //.copyImageFromTemp(ConOra, ffTestPlan, oAimsApp.getAppsId(), userId, ManageApplicationsConstants.TEST_PLAN_RESULTS_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, ffPresentation, oAimsApp.getAppsId(), userId, ManageApplicationsConstants.PRESENTATION_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, ffBOBO, oAimsApp.getAppsId(), userId, ManageApplicationsConstants.BOBO_LETTER_OF_AUTH_BLOB_DB_INFO);
            
            TempFilesManager.copyImageFromTemp(ConOra, ffLBS, oAimsApp.getAppsId(), userId, ManageApplicationsConstants.LBS_CONTRACT_FIELD_BLOB_DB_INFO);
            
            ApplicationsManagerHelper.saveTestingPhaseFiles(ConOra, testingPhaseSet, oAimsApp, userId, userType);

            ApplicationsManagerHelper.addJournalEntry(ConOra, oAimsApp, userId, userType, newApp);

            if (oAimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.ACCEPTANCE_ID.longValue())
            {
                addBdsEntries(ConOra, oAimsApp.getAppsId(), oAimsApp.getAimsAllianceId());
                
                if(userType.equals(AimsConstants.VZW_USERTYPE))
                		publishJmaSolution(ConOra, oAimsEnterpriseApp.getIsPublished(),oAimsEnterpriseApp.getIsFeatured(), oAimsApp.getAppsId(),bdsMktSeg);
            }

                        	
            session.flush();

            if (((oAimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SAVED_ID.longValue())
                || (oAimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SUBMISSION_ID.longValue())))
            {
                TempFilesManager.checkForEmptyBlobs(ConOra, oAimsApp.getAppsId());
            }

            session.flush();
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

                if (tx != null)
                    tx.rollback();

            AimsException aimsException = new AimsException("Error");
            //if (DBErrorFinder.searchUniqueConstraintErrors(je.getMessage(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS, aimsException))
            if (DBErrorFinder.searchUniqueConstraintErrorsWithSchemaName(je.getCause().toString(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS_DB, aimsException)) {
                throw aimsException;
            }
            else
            {
                je.printStackTrace();
                throw new HibernateException(je);
            }
        }

        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            log.debug("ROLLED BACK IN saveOrUpdateBrewApplication()");
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
            //Destroy All Form Files

            log.debug("SESSION CLOSED IN saveOrUpdate()");
        }

    }

    //Adds Journal Entry when	application	is updated
    public static void addBdsEntries(java.sql.Connection ConOra, Long appsId, Long allianceId) throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            /*	
            Database Procedure SnapShot	:					
            BDS_SOLUTIONS_PKG.add_bds_records_on_accept(?,?)
            	 
            Parameters:					 
            p_solution_id			 IN		number,
            p_partner_id			 IN		number
            */

            statement = ConOra.prepareCall("call BDS_SOLUTIONS_PKG.add_bds_records_on_accept(?,?)");
            statement.setLong(1, appsId.longValue());
            statement.setLong(2, allianceId.longValue());
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

    
    public static void publishJmaSolution(java.sql.Connection ConOra,String isbublish,String isFeatured, Long appsId, Long[] bdsMktSeg) throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            /*	
            Database Procedure SnapShot	:					
            AIMS_JMA_APP_PKG.publish_jma_app(?,?)
            	 
            Parameters:					 
            p_solution_id			 IN		number,
            p_is_published           IN char,
            p_is_mobile_professional IN char,
	   		p_is_soho                IN char,
	   		p_is_sme                 IN char,
            p_is_enterprise          IN char,
            p_is_featured          IN char 
            */

        	String mobileProfessional,soHo,sme,enterprise,featured;
        	mobileProfessional=soHo=sme=enterprise=featured="N";
        	
             /*
          	  * 	Segment                BDS_MARKET_SEGMENT
          	  * 	Mobile Professional          9
          	  *     SoHo                         10
          	  *     Services                     11
          	  *     Enterprise                   12
          	  */  
           	
        	if(bdsMktSeg!=null)
        	{
        		for(int i=0; i<bdsMktSeg.length;i++)
   			 {
   				 if(bdsMktSeg[i].equals(new Long(9)))
   					mobileProfessional="Y";
   				 else if(bdsMktSeg[i].equals(new Long (10)))
   					soHo="Y";
   				 else if(bdsMktSeg[i].equals(new Long(11)))
   					sme="Y";
   				 else if(bdsMktSeg[i].equals(new Long(12)))
   					enterprise="Y";
   			 }
        	}
        	
        	if(StringFuncs.NullValueReplacement(isFeatured).equals("Y"))
        		featured="Y";
     
            statement = ConOra.prepareCall("call AIMS_JMA_APP_PKG.publish_jma_app(?,?,?,?,?,?,?)");
            statement.setLong(1, appsId.longValue());
            statement.setString(2, isbublish);
            
            statement.setString(3, mobileProfessional);
            statement.setString(4, soHo);
            statement.setString(5, sme);
            statement.setString(6, enterprise);
            statement.setString(7, featured);
            
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
    
    public static void publishJmaSolution(java.sql.Connection ConOra, EntAppPublishSolutionVO vo) throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            /*	
            Database Procedure SnapShot	:					
            AIMS_JMA_APP_PKG.publish_jma_app(?,?)
            	 
            Parameters:					 
            p_solution_id			 IN		number,
            p_is_published           IN char,
            p_is_mobile_professional IN char,
	   		p_is_soho                IN char,
	   		p_is_sme                 IN char,
            p_is_enterprise          IN char 
            */

        	String mobileProfessional,soHo,sme,enterprise,publish,featured;
        	mobileProfessional=soHo=sme=enterprise=publish=featured="N";
        	
            if(StringFuncs.NullValueReplacement(vo.getIsMobileProfessional()).equals("Y"))
            	mobileProfessional="Y";
            if(StringFuncs.NullValueReplacement(vo.getIsSoho()).equals("Y"))
            	soHo="Y";
            if(StringFuncs.NullValueReplacement(vo.getIsSme()).equals("Y"))
            	sme="Y";
            if(StringFuncs.NullValueReplacement(vo.getIsEnterprise()).equals("Y"))
            	enterprise="Y";
            if(StringFuncs.NullValueReplacement(vo.getIsPublished()).equals("Y"))
            	publish="Y";
            if(StringFuncs.NullValueReplacement(vo.getIsFeatured()).equals("Y"))
            	featured="Y";
            
        
     
            statement = ConOra.prepareCall("call AIMS_JMA_APP_PKG.publish_jma_app(?,?,?,?,?,?,?)");
            statement.setLong(1, vo.getAppId().longValue());
            statement.setString(2, publish);
            
            statement.setString(3, mobileProfessional);
            statement.setString(4, soHo);
            statement.setString(5, sme);
            statement.setString(6, enterprise);
            statement.setString(7, featured);
            
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
    
    public static boolean checkAccessToSpotlight(String appsId)
    {
        Session session = null;
        java.sql.Connection ConOra = null;
        java.sql.CallableStatement statement = null;
        Long lngAppsId = null;
        boolean result = false;

        if (appsId == null)
            return false;
        else
            lngAppsId = new Long(appsId);

        try
        {
            session = DBHelper.getInstance().getSession();
            ConOra = session.connection();

            /*  
            Database Procedure SnapShot :                   
            AIMS_APPLICATIONS_PKG.check_access_to_spotlight_page(?,?)
                 
            Parameters:                  
            p_apps_id            IN     number,
            p_out_result         IN     varchar2
            */

            statement = ConOra.prepareCall("call AIMS_APPLICATIONS_PKG.check_access_to_spotlight_page(?,?)");
            statement.setLong(1, lngAppsId.longValue());
            statement.registerOutParameter(2, java.sql.Types.VARCHAR);
            statement.execute();

            if (((OracleCallableStatement) statement).getString(2).equals("Y"))
                result = true;

        }
        catch (Exception ex)
        {}
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
                session.close();
            }
            catch (Exception ex)
            {}
        }
        
        return result;        
    }
    
    /**
     * This method check that is application belongs to this alliance 
     * @param allianceId
     * @param appsId
     * @return
     * @throws HibernateException
     */
    public static boolean checkAccessToSpotlight(Long allianceId, Long appsId)throws HibernateException
    {
    	Session session=null;
    	AimsApp aimsApp=null;
    	boolean result = false;
    	try
    	{
    		session = DBHelper.getInstance().getSession();
            aimsApp = (AimsApp) session.find("from com.netpace.aims.model.application.AimsApp as aapps where aapps.appsId = " + appsId.toString()).get(0);
    	
            if(aimsApp.getAimsAllianceId().equals(allianceId))
            	result=true;
            
    	}catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
        	session.close();
        }

    	
    	return result;
    }
    
    public static Long[] getProductInformation(Long entAppId) throws HibernateException
    {
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Long [] longArray=null; 
        try
        {
        	session = DBHelper.getInstance().getSession();
			queryStringBuffer
					.append(" select productInfo.types.typeId ")
					.append(" 	from AimsEntAppProductInfo as productInfo ")
					.append(" where productInfo.enterpriseAppsId = :id ");

			Query query = session.createQuery(queryStringBuffer.toString());				
			query.setLong("id", entAppId.longValue());			
			
			
			List resultValues = query.list();
			
			if (resultValues != null && resultValues.size()>0){
				longArray=new Long[resultValues.size()];
				for (int i=0; i<resultValues.size(); i++){
					longArray[i]=new Long(resultValues.get(i).toString());
				}
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

        return longArray;
    }
    
    public static Long[] getMarketSegmentInfo(Long entAppId) throws HibernateException
    {
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Long [] longArray=null; 
        try
        {
        	session = DBHelper.getInstance().getSession();
			queryStringBuffer
					.append(" select info.types.typeId ")
					.append(" 	from AimsEntAppMarketSegmentInfo as info ")
					.append(" where info.enterpriseAppsId = :id ");

			Query query = session.createQuery(queryStringBuffer.toString());				
			query.setLong("id", entAppId.longValue());			
			
			
			List resultValues = query.list();
			
			if (resultValues != null && resultValues.size()>0){
				longArray=new Long[resultValues.size()];
				for (int i=0; i<resultValues.size(); i++){
					longArray[i]=new Long(resultValues.get(i).toString());
				}
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

        return longArray;
    }

    
    public static AimsIndustryFocu getIndustryFocus(Long id) throws HibernateException
    {
        Session session = null;
        AimsIndustryFocu objIndFoc = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            objIndFoc = (AimsIndustryFocu) session.find("from com.netpace.aims.model.masters.AimsIndustryFocu as obj where obj.industryId = " + id.toString()).get(0);
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

        return objIndFoc;
    }
    
    public static Collection getPublishSolutionRecord(int PAGE_LENGTH,int pageNo,String search_expression,String order_by) throws HibernateException
    {
    	log.debug("AimsEntAppsManager.getPublishSolutionRecord");
    	Collection collection = null;
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Long [] longArray=null; 
        try
        {
        	session = DBHelper.getInstance().getSession();
			queryStringBuffer
					.append(" select aa.allianceId, ")
					.append("        aa.companyName, ")
					.append("        aas.appsId, ")
					.append("        aas.title, ")
					.append("        entApp.isPublished, ")
					.append("        entApp.isFeatured ")
					//.append("         get_market_segment(aas.appsId),  ")
					//.append("          get_market_segment_id(aas.appsId)   ")
				
					.append(" 	from com.netpace.aims.model.core.AimsAllianc as aa , ")
					.append("        com.netpace.aims.model.application.AimsApp	as aas, ")
					.append("        com.netpace.aims.model.application.AimsEnterpriseApp	as entApp, ")
					
					.append(" where  aas.aimsPlatform = 5 and aas.aimsLifecyclePhaseId = 7 and ")
					.append("        aas.aimsAllianceId = aa.allianceId  ")
					.append("        and aas.appsId = entApp.enterpriseAppsId   ");
					
			if (search_expression.length() > 0)
			{
				queryStringBuffer.append(search_expression);
			}
					
			queryStringBuffer.append("        order by ")
							 .append(order_by);	
			  

			Query query = session.createQuery(queryStringBuffer.toString());
			query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (pageNo - 1));
			//Query query = session.createQuery(" begin ? := sp_get_stocks(18735); end; ");
			collection=query.list();
        
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
    
    public static int countPublishSolutionRecord(String search_expression,String order_by) throws HibernateException
    {
    	log.debug("countPublishSolutionRecord.getPublishSolutionRecord");
    	Collection collection = null;
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Long [] longArray=null; 
        try
        {
        	session = DBHelper.getInstance().getSession();
			queryStringBuffer
					.append(" select aa.allianceId, ")
					.append("        aa.companyName, ")
					.append("        aas.appsId, ")
					.append("        aas.title, ")
					.append("        entApp.isPublished, ")
					.append("        entApp.isFeatured ")
					.append(" 	from com.netpace.aims.model.core.AimsAllianc as aa , ")
					.append("        com.netpace.aims.model.application.AimsApp	as aas, ")
					.append("        com.netpace.aims.model.application.AimsEnterpriseApp	as entApp, ")
					
					.append(" where  aas.aimsPlatform = 5 and aas.aimsLifecyclePhaseId = 7 and ")
					.append("        aas.aimsAllianceId = aa.allianceId  ")
					.append("        and aas.appsId = entApp.enterpriseAppsId   ");
					
			if (search_expression.length() > 0)
			{
				queryStringBuffer.append(search_expression);
			}
					
			queryStringBuffer.append("        order by ")
							 .append(order_by);	
			 
			Query query = session.createQuery(queryStringBuffer.toString());
			collection=query.list();
        
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

        if(collection ==null)
        	return 0;
        else
        return collection.size();
    }
    
    
    public static void publishSolutions(
    	Long appId,
    	String isPublished, 
    	String isFeatured,
    	Long[] marketSegments,
    	Long[] bdsMarketSegment
    )throws HibernateException, Exception
    		
    {
    	 Session session = null;
         Transaction tx = null;

         try
         {
             session = DBHelper.getInstance().getSession();
             tx = session.beginTransaction();
             
             //Update isPublished and isFeatured fields 
             AimsEnterpriseApp entApp = (AimsEnterpriseApp) session.find("FROM com.netpace.aims.model.application.AimsEnterpriseApp as entApp WHERE entApp.enterpriseAppsId = "+appId.toString()).get(0);
             entApp.setIsPublished(isPublished);
             entApp.setIsFeatured(isFeatured);
             session.update(entApp);
             
             //Update Market Segment information
             AimsEntAppMarketSegmentInfo marketSeginfo=null;
             session.delete(
	                    "from com.netpace.aims.model.application.AimsEntAppMarketSegmentInfo as info where info.enterpriseAppsId = :enterpriseAppsId",
	                    appId,new LongType());
             if(marketSegments!=null)
             {  
             	for (int i = 0; i < marketSegments.length; i++)
             	{
             		marketSeginfo=new AimsEntAppMarketSegmentInfo();
             		marketSeginfo.setEnterpriseAppsId(appId);
             		marketSeginfo.setTypes((AimsTypes) session.load(AimsTypes.class,marketSegments[i]));
             		session.save(marketSeginfo);  
             	}
             	  
             }
             
             //Publish solution
             java.sql.Connection ConOra = null;
             ConOra = session.connection();
             publishJmaSolution(ConOra, isPublished,isFeatured, appId, bdsMarketSegment);
             
             session.flush();
             tx.commit();
         }
         catch (JDBCException je)
         {
        	  if (tx != null)
                  tx.rollback();
        	  
        	 je.printStackTrace();
             throw je; 
         }
         catch (HibernateException e)
         {
        	  if (tx != null)
                  tx.rollback();
        	  
             e.printStackTrace();
             throw e;
         }
        
         
         finally
         {
             session.close();
         }
    }
    public static void publishSolutions(EntAppPublishSolutionVO vo, Long[] marketSegments )throws HibernateException, Exception
        		
        {
        	 Session session = null;
             Transaction tx = null;

             try
             {
                 session = DBHelper.getInstance().getSession();
                 tx = session.beginTransaction();
                 
                 //Update isPublished and isFeatured fields 
                 AimsEnterpriseApp entApp = (AimsEnterpriseApp) session.find("FROM com.netpace.aims.model.application.AimsEnterpriseApp as entApp WHERE entApp.enterpriseAppsId = "+vo.getAppId().toString()).get(0);
                 entApp.setIsPublished(vo.getIsPublished());
                 entApp.setIsFeatured(vo.getIsFeatured());
                 session.update(entApp);
                 
                 //Update Market Segment information
                 AimsEntAppMarketSegmentInfo marketSeginfo=null;
                 session.delete(
    	                    "from com.netpace.aims.model.application.AimsEntAppMarketSegmentInfo as info where info.enterpriseAppsId = :enterpriseAppsId",
    	                    vo.getAppId(),new LongType());
                 if(marketSegments!=null)
                 {  
                 	for (int i = 0; i < marketSegments.length; i++)
                 	{
                 		marketSeginfo=new AimsEntAppMarketSegmentInfo();
                 		marketSeginfo.setEnterpriseAppsId(vo.getAppId());
                 		marketSeginfo.setTypes((AimsTypes) session.load(AimsTypes.class,marketSegments[i]));
                 		session.save(marketSeginfo);  
                 	}
                 	  
                 }
                 
                 //Publish solution
                 java.sql.Connection ConOra = null;
                 ConOra = session.connection();
                 publishJmaSolution(ConOra, vo);
                 
                 session.flush();
                 tx.commit();
             }
             catch (JDBCException je)
             {
            	  if (tx != null)
                      tx.rollback();
            	  
            	 je.printStackTrace();
                 throw je; 
             }
             catch (HibernateException e)
             {
            	  if (tx != null)
                      tx.rollback();
            	  
                 e.printStackTrace();
                 throw e;
             }
            
             
             finally
             {
                 session.close();
             }
        }
    
    public static int getFeaturedSolutionsCount() throws HibernateException
    {
    	log.debug("countPublishSolutionRecord.countIsFeaturedSolution");
    	int rows = 0;
    	List list = new ArrayList();
    	StringBuffer queryStringBuffer = new StringBuffer();
    	Session session = null;
        try
        {
        	session = DBHelper.getInstance().getSession();
        	queryStringBuffer
					.append(" select  ")
					.append("		count(*) ")
					.append(" from ")
					.append("		com.netpace.aims.model.application.AimsEnterpriseApp as entApp ")
					.append(" WHERE IS_FEATURED = 'Y' ");
        	
        	Query query = session.createQuery(queryStringBuffer.toString());
        	list = query.list();
    		if (list!=null && list.size()>0){
    			rows=new Integer(list.get(0).toString()).intValue();
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

       
        return rows;
    }
    
    public static int getFeaturedSolutionsCount(Long solutionId) throws HibernateException
    {

        int count = 0;
        Session session = null;
        Collection noOfFeatSolutions = new ArrayList();

        try
        {
        	session = DBHelper.getInstance().getSession();
            noOfFeatSolutions =
                session.find(
                    "select distinct entApp.enterpriseAppsId from com.netpace.aims.model.application.AimsEnterpriseApp as entApp where entApp.isFeatured = 'Y' "
                        + " and entApp.enterpriseAppsId not in ("
                        + solutionId
                        + ")");

            count = noOfFeatSolutions.size();

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
        return count;
    }
    
    public static int getFeaturedSolutionsCount(String solutionIds) throws HibernateException
    {

        int count = 0;
        Session session = null;
        Collection noOfFeatSolutions = new ArrayList();

        try
        {
        	session = DBHelper.getInstance().getSession();
            noOfFeatSolutions =
                session.find(
                    "select distinct entApp.enterpriseAppsId from com.netpace.aims.model.application.AimsEnterpriseApp as entApp where entApp.isFeatured = 'Y' "
                        + " and entApp.enterpriseAppsId not in ("
                        + solutionIds
                        + ")");

            count = noOfFeatSolutions.size();

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
        return count;
    }


    
}
 