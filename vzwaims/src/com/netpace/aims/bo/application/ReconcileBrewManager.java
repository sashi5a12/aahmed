package	com.netpace.aims.bo.application;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.*;
import com.netpace.aims.model.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.controller.application.*;
import com.netpace.aims.util.*;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.*;
import org.apache.struts.upload.FormFile;

import java.util.*;
import java.text.*;
import java.lang.*;

import com.netpace.aims.model.core.*;

 
/**
 * This	class is responsible for getting the details for Reconcile Brew Data.
 * It has static methods for getting the Reconcile Data Details.
 * @author Ahson Imtiaz
 */
public class ReconcileBrewManager
{
		
	static Logger	log	=	Logger.getLogger(ReconcileBrewManager.class.getName());

	/*
	 *Collection will be returned having all * in the system
	 **/
	public static AimsBrewNstlUpload saveBrewNstlData(FormFile ffBrewNstlData, Date dteCreated)	
	throws HibernateException,Exception,AimsInvalidDataException,AimsFileDateException
	{		 		
		
		Session session = null;
		AimsBrewNstlData brewData = null;
		AimsBrewNstlUpload brewUpload = new AimsBrewNstlUpload();
		
		recordPresentForDate(dteCreated);
		brewUpload.setFileName(ffBrewNstlData.getFileName());
		brewUpload.setDataDateStamp(dteCreated);
		brewUpload.setIsReconciled("N");
		
	   POIFSFileSystem fs      =
              new POIFSFileSystem(ffBrewNstlData.getInputStream());
      HSSFWorkbook wb = new HSSFWorkbook(fs);
		
		log.debug("File Opened");		
		
		HSSFSheet sheet = wb.getSheet("Accepted Apps");
    	HSSFRow row = null;
    	HSSFCell cell = null;
    	HSSFCell cellData = null;
      
      if (sheet == null ) throw new AimsInvalidDataException();
      
      int iLastRowNum = sheet.getLastRowNum();
		ArrayList collection = new ArrayList(iLastRowNum+1);
      int iCurrentRow = 2;
      
      SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy kk:mm:ss");
      
      try 
      {
	      while (iCurrentRow <= iLastRowNum )
	      {
				 row = sheet.getRow(iCurrentRow);
				 
				 if (row == null) {iCurrentRow++; continue;}
				 
				 brewData = new AimsBrewNstlData();
				 
      		// Getting Developer
   			 cell = row.getCell((short)3);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	brewData.setDeveloperName(cell.getStringCellValue());
				 }
				 else {iCurrentRow++; /*log.debug("Skipped Row");*/continue;}
				 
      		// Getting Application Name
		 		 cell = row.getCell((short)4);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	brewData.setApplicationName(cell.getStringCellValue());
				 }
				 else {iCurrentRow++; /*log.debug("Skipped Row");*/continue;}
				 
      		// Getting Handset
		 		 cell = row.getCell((short)5);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				 		brewData.setHandset( new Integer((int)cell.getNumericCellValue()).toString() );
				 	else
				 		brewData.setHandset(cell.getStringCellValue());
				 }
      		// Getting PartNum
		 		 cell = row.getCell((short)6);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	brewData.setPartnum(cell.getStringCellValue());
				 }
      		// Getting Version
		 		 cell = row.getCell((short)7);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				 		brewData.setVersion( new Integer((int)cell.getNumericCellValue()).toString() );
				 	else
				 		brewData.setVersion(cell.getStringCellValue());
				 }
      		// Getting Platform ID
		 		 cell = row.getCell((short)8);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				 		brewData.setPlatformId( new Integer((int)cell.getNumericCellValue()).toString() );
				 	else
				 		brewData.setPlatformId(cell.getStringCellValue());
				 }
      		
      		// Getting to WC Date
      		try {
		 		 cell = row.getCell((short)12);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	brewData.setToWcDate( df.parse(cell.getStringCellValue()) );
				 }
				} catch (Exception e) {log.debug("WC Date not in proper format skipping the field for row :" + iCurrentRow);}
				 
      		// Getting BDS Acceptance Date
		 		 cell = row.getCell((short)13);
				 if (cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	brewData.setBdsAcceptanceDate( df.parse(cell.getStringCellValue()) );
				 }
      		// Getting GIN
		 		 cell = row.getCell((short)22);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				 		brewData.setGinNumber( new Integer((int)cell.getNumericCellValue()).toString() );
				 	else
				 		brewData.setGinNumber(cell.getStringCellValue());
				 }
				
				// Add the class to ArrayList 
				collection.add(brewData);
				
				// Row Increament
				log.debug("Row Added");
      		iCurrentRow ++;
      	}
      } catch (Exception e) {
      	e.printStackTrace();
      	throw new AimsInvalidDataException();
      }
      
      //brewUpload.setAimsBrewNstlDatas(new HashSet(collection));
	    Transaction	tx = null;      
	    
	    try
	    { 
	     	session = DBHelper.getInstance().getSession();
	     	tx = session.beginTransaction();
      	session.save(brewUpload);
      	log.debug("saved ***** ");
	  		log.debug("No of records returned: " + collection.size() );
	  		session.flush();
	  		for (int iCount= 0 ; iCount < collection.size() ; iCount++)
	  		{
	  			brewData = (AimsBrewNstlData) collection.get(iCount);
	  			brewData.setAimsBrewNstlUpload(brewUpload);
	  			//log.debug("Now saving " + iCount);
	  			session.save(brewData);
	  		}
	  		tx.commit();
		 }
		catch(HibernateException e)
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

	    return brewUpload;

	}
	
	
	/**/
	/*
	 *Collection will be returned having all * in the system
	 **/
	public static Collection matchBrewNstlData(AimsBrewNstlUpload brewUpload)	throws HibernateException,Exception
	{		 		
		
		Session session = null;
		BrewNstlDataExt brewData = null;
		ArrayList collection = new ArrayList();
		Long lngBrewAppsId, lngDeviceId;
		Object objects[];
		StringBuffer sbSubQuery = null;
		String[] splitResult = null;
		String strAllianceNamePart, strTitlePart;
		Object objValues[] = null;
				
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		StringBuffer sbQuery = 
	  		new StringBuffer("select bnData.developerName, bnData.applicationName, bnData.handset " );
	  		sbQuery.append(", bnData.version, alliance.companyName, app.title, app.version, bnData.brewNstlId ");
	  		sbQuery.append(", brewapp.brewAppsId , bnData.dataEntryId, bnData.brewAppsId , bnData.deviceId ");
	  		sbQuery.append(" from com.netpace.aims.model.application.AimsBrewNstlData bnData, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsBrewApp brewapp, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsApp app, ");
	  		sbQuery.append("com.netpace.aims.model.core.AimsAllianc alliance, ");
	  		sbQuery.append("where ");
	  		sbQuery.append("bnData.applicationName = app.title(+) AND ");
	  		sbQuery.append("bnData.version = app.version(+) AND ");
	  		sbQuery.append("app.appsId = brewapp.brewAppsId(+) AND ");
	  		sbQuery.append("app.aimsAllianceId = alliance.allianceId(+) AND ");
	  		sbQuery.append("bnData.brewNstlId = " + brewUpload.getBrewNstlId().toString() + " ORDER BY bnData.developerName, bnData.applicationName, bnData.handset, bnData.version ");
	  		Query	query	= session.createQuery(sbQuery.toString());
  		
			for	(Iterator	it = query.iterate();	it.hasNext();)
			{
				brewData = new BrewNstlDataExt();
				objects = (Object[])it.next();
				brewData.setDeveloperName( Utility.getObjectString(objects[0]) );
				brewData.setApplicationName( Utility.getObjectString(objects[1]));
				brewData.setHandset(Utility.getObjectString(objects[2]));
				brewData.setVersion(Utility.getObjectString(objects[3]));
				brewData.setCompanyName(Utility.getObjectString(objects[4]));
				brewData.setAppTitle(Utility.getObjectString(objects[5]));
				brewData.setAppVersion(Utility.getObjectString(objects[6]));
				brewData.setBrewNstlId((Long)objects[7]);
				brewData.setBrewAppsId((Long)objects[8]);
				brewData.setDataEntryId((Long)objects[9]);
				lngBrewAppsId = (Long)objects[10];
				lngDeviceId = (Long)objects[11];
				
				if (lngBrewAppsId != null && lngDeviceId != null && lngBrewAppsId.intValue() != 0 && lngDeviceId.intValue() != 0) {
					
					sbQuery = new StringBuffer();
					sbQuery.append(" SELECT alliance.companyName, app.title, device.deviceModel ,app.version ");
					sbQuery.append(" FROM ");
			  		sbQuery.append(" com.netpace.aims.model.application.AimsApp app, ");
			  		sbQuery.append(" com.netpace.aims.model.core.AimsAllianc alliance, ");
			  		sbQuery.append(" com.netpace.aims.model.masters.AimsDevic device ");
			  		sbQuery.append(" WHERE device.deviceId = ").append(lngDeviceId);
			  		sbQuery.append(" AND app.appsId = ").append(lngBrewAppsId);
			  		sbQuery.append(" AND app.aimsAllianceId = alliance.allianceId ");
			  		Query subQuery = session.createQuery(sbQuery.toString());
			  		
			  		Iterator itSub = subQuery.iterate();
			  		BrewNstlDataLinkedDetails bndl = new BrewNstlDataLinkedDetails();
			  		
			  		if (itSub.hasNext()) {
			  			objects = (Object[]) itSub.next();
			  			bndl.setCompanyName(objects[0].toString() );
			  			bndl.setApplicationName(objects[1].toString());
			  			bndl.setDeviceModel(objects[2].toString());
			  			bndl.setVersion(objects[3].toString());
			  		}
			  		
			  		brewData.setLinkedDetails(bndl);
	  		
				}else if (brewData.getBrewAppsId() != null && brewData.getBrewAppsId().intValue() != 0){
					brewData.setSupportedDevices((Collection)session.find("from com.netpace.aims.model.application.AimsBrewAppDevice bappdevice where bappdevice.brewAppsId = " + brewData.getBrewAppsId().toString() ));
				}
				else {
					if (brewData.getDeveloperName() != null && brewData.getApplicationName() != null)
					{
						splitResult = brewData.getDeveloperName().split("\\b");
						if (splitResult.length > 1) strAllianceNamePart = splitResult[1]; else strAllianceNamePart = null;
						splitResult = brewData.getApplicationName().split("\\b"); 
						if (splitResult.length > 1) strTitlePart = splitResult[1]; else strTitlePart = null;
						
						if ( strAllianceNamePart != null && strTitlePart != null )
						{
							sbSubQuery = new StringBuffer();
							sbSubQuery.append(" SELECT brewapp.brewAppsId, dev.deviceId, dev.deviceModel, alliance.companyName, ");
							sbSubQuery.append(" apps.title, apps.version FROM ");
					  		sbSubQuery.append(" com.netpace.aims.model.application.AimsBrewApp brewapp, ");
					  		sbSubQuery.append(" com.netpace.aims.model.application.AimsApp apps, ");
					  		sbSubQuery.append(" com.netpace.aims.model.core.AimsAllianc alliance, ");
					  		sbSubQuery.append(" com.netpace.aims.model.application.AimsBrewAppDevice brewdev, ");
					  		sbSubQuery.append(" com.netpace.aims.model.masters.AimsDevic dev ");
							sbSubQuery.append(" WHERE " );
							sbSubQuery.append(" brewapp.brewAppsId = apps.appsId AND ");
							sbSubQuery.append(" brewdev.brewAppsId = brewapp.brewAppsId AND ");
							sbSubQuery.append(" dev.deviceId = brewdev.deviceId AND ");
							sbSubQuery.append(" apps.aimsAllianceId = alliance.allianceId AND ");
							sbSubQuery.append(" alliance.companyName like '%").append(strAllianceNamePart).append("%' AND ");
							sbSubQuery.append(" apps.title like '%").append(strTitlePart).append("%' ORDER BY alliance.companyName, dev.deviceModel , apps.version ");

							Query subQuery = session.createQuery(sbSubQuery.toString());
							ArrayList alist = new ArrayList();
							for	(Iterator	itsub = subQuery.iterate();	itsub.hasNext();)
							{
								objValues = (Object[]) itsub.next();
								AllianceApplicationDeviceExt aade = new AllianceApplicationDeviceExt();
								aade.setBrewAppsId((Long) objValues[0]);
								aade.setDeviceId((Long) objValues[1]);
								aade.setDeviceModel(Utility.getObjectString(objValues[2]));
								aade.setCompanyName(Utility.getObjectString(objValues[3]));
								aade.setTitle(Utility.getObjectString(objValues[4]));
								aade.setVersion(Utility.getObjectString(objValues[5]));
								alist.add(aade);
							}
							brewData.setPossibleMatch(alist);
						}
					}
				}
				collection.add(brewData);
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

	    return collection;

	}
	
	
	/**/
	/*
	 *Collection will be returned having all * in the system
	 **/
	public static Collection getBrewAppsDevicesCombinations() throws HibernateException,Exception
	{		 		
		
		Session session = null;
		AllianceApplicationDeviceExt aadext = null;
		ArrayList collection = new ArrayList();
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		StringBuffer sbQuery = 
	  		new StringBuffer("select brewapp.brewAppsId, alliance.companyName, app.title, "); //, device.deviceModel
	  		sbQuery.append(" bappdevice.aimsDevice.deviceModel, app.version, bappdevice.aimsDevice.deviceId ");
	  		sbQuery.append("from com.netpace.aims.model.application.AimsBrewApp brewapp, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsApp app, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsBrewAppDevice bappdevice, ");
	  		sbQuery.append("com.netpace.aims.model.core.AimsAllianc alliance ");
	  		sbQuery.append("where ");
	  		sbQuery.append("app.appsId = brewapp.brewAppsId AND ");
	  		sbQuery.append("app.aimsAllianceId = alliance.allianceId AND ");
	  		sbQuery.append("brewapp.brewAppsId = bappdevice.brewAppsId ");
	  		sbQuery.append(" order by alliance.companyName, app.title, bappdevice.aimsDevice.deviceModel ");

	  		Query	query	= session.createQuery(sbQuery.toString());
	  		int iCount=0;
	  		
			for	(Iterator	it = query.iterate();	it.hasNext();)
			{
				Object objects[] = (Object[])it.next();
				aadext = new AllianceApplicationDeviceExt();
				aadext.setBrewAppsId((Long)objects[0]);
				aadext.setCompanyName(Utility.getObjectString(objects[1]));
				aadext.setTitle(Utility.getObjectString(objects[2]));
				aadext.setDeviceModel(Utility.getObjectString(objects[3]));
				aadext.setVersion(Utility.getObjectString(objects[4]));
				aadext.setDeviceId((Long)objects[5]);
				collection.add(aadext);
				//log.debug("i : " + iCount++);
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

	    return collection;

	}

/* */

	/**/
	/*
	 *Collection will be returned having all * in the system
	 **/
	public static Collection getBrewNstlUploadReconciled()	throws HibernateException,Exception
	{		 		
		
		Session session = null;
		Collection collection = null;

	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		collection = session.find("from com.netpace.aims.model.application.AimsBrewNstlUpload bnData where bnData.isReconciled = 'Y'  ORDER BY bnData.dataDateStamp");
	  		
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

	/**/
	/*
	 *Collection will be returned having all * in the system
	 **/
	public static Collection getBrewNstlUploadToReconcile()	throws HibernateException,Exception
	{		 		
		
		Session session = null;
		Collection collection = null;

	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		collection = session.find("from com.netpace.aims.model.application.AimsBrewNstlUpload bnData ORDER BY bnData.dataDateStamp "); // where bnData.isReconciled = 'N'
	  		
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
	/**/
	/*
	 *
	 **/
	public static Collection recordPresentForDate(Date dteFile)	throws HibernateException,AimsFileDateException
	{		 		
		
		Session session = null;
		Collection collection = null;

	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		collection = session.find("from com.netpace.aims.model.application.AimsBrewNstlUpload bnData where bnData.dataDateStamp = :dateValue", dteFile, new DateType());
	  		
	  		if (!collection.isEmpty())
	  		{
	  			throw new AimsFileDateException();
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

	    return collection;

	}

/** Function to relate the data entry with the respective brew and device application
*/

	public static void associateEntries(Integer[] aSelIndex, String[] aEnteries, Long brewNstlId)	throws HibernateException
	{		 		
		
		Session session = null;
		Collection collection = null;
		Transaction tx= null;

	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
		  	tx= session.beginTransaction();			
		  	
		  	AimsBrewNstlUpload brewUpload = (AimsBrewNstlUpload) session.find("from com.netpace.aims.model.application.AimsBrewNstlUpload bnData where bnData.brewNstlId = " + brewNstlId.toString()).get(0);		  	
		  	
	   	for (int iCount= 0 ; aSelIndex != null && iCount < aSelIndex.length ; iCount++)
	   	{
	   		StringTokenizer stnz = new StringTokenizer(aEnteries[aSelIndex[iCount].intValue()],"-");
	   		String strBrewAppsId  = stnz.nextToken();
	   		String strDataEntryId = stnz.nextToken();
	   		String strDeviceId    = stnz.nextToken();
	   		
		  		Query query = session.createQuery("select bnData, brewAppDevice from com.netpace.aims.model.application.AimsBrewNstlData bnData, " + 
		  									" com.netpace.aims.model.application.AimsBrewAppDevice brewAppDevice " +
		  									" where bnData.dataEntryId = " + strDataEntryId + " AND brewAppDevice.brewAppsId = " + strBrewAppsId + 
		  									" AND brewAppDevice.deviceId = " + strDeviceId );
		  		
		  		Iterator it = query.iterate();
		  		Object[] objects = null;
		  		
		  		if ( it.hasNext() )
		  		{
		  			objects = (Object[]) it.next();
		  			AimsBrewNstlData abnd = (AimsBrewNstlData) objects[0];
		  			AimsBrewAppDevice abad = (AimsBrewAppDevice) objects[1];

		  			abad.setBdsAcceptanceDate(abnd.getBdsAcceptanceDate());
		  			abad.setPartNumber(abnd.getPartnum());
		  			abad.setGinNumber(abnd.getGinNumber());
		  			abnd.setBrewAppsId(new Long(strBrewAppsId));
		  			abnd.setDeviceId(new Long(strDeviceId));
		  			session.update(abnd);
		  			session.update(abad);
		  		}
	   	}
	   	brewUpload.setReconciledDate(new Date());
	   	brewUpload.setIsReconciled("Y");
	   	session.saveOrUpdate(brewUpload);
		  	tx.commit();
	  		
		 }
		catch(HibernateException e)
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

		return;
	}

	/**/
	/*
	 *
	 **/
	public static AimsBrewNstlUpload getAimsBrewNstlUpload(Long brewNstlId)	throws HibernateException
	{		 		
		
		Session session = null;
		Collection collection = null;

	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		return (AimsBrewNstlUpload) session.find("from com.netpace.aims.model.application.AimsBrewNstlUpload bnData where bnData.brewNstlId = " + brewNstlId.toString()).get(0);
	  		
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

	/**/
	/*
	 *Collection will be returned having all * in the system
	 **/
	public static Collection getAssociatedBrewNstlData(Long brewNstlId)	throws HibernateException,Exception
	{		 		
		
		Session session = null;
		BrewNstlDataExt brewData = null;
		ArrayList collection = new ArrayList();
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		StringBuffer sbQuery = 
	  		new StringBuffer("select bnData.developerName, bnData.applicationName, bnData.handset " );
	  		sbQuery.append(", bnData.version, alliance.companyName, app.title, app.version, bnData.brewNstlId "); 
	  		sbQuery.append(", brewapp.brewAppsId , bnData.dataEntryId, device.deviceModel ");
	  		sbQuery.append(" from com.netpace.aims.model.application.AimsBrewNstlData bnData, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsBrewApp brewapp, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsBrewAppDevice brewappdev, ");
	  		sbQuery.append("com.netpace.aims.model.masters.AimsDevic device, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsApp app, ");
	  		sbQuery.append("com.netpace.aims.model.core.AimsAllianc alliance ");
	  		sbQuery.append("where ");
	  		sbQuery.append("bnData.brewAppsId = brewappdev.brewAppsId(+) AND ");
	  		sbQuery.append("bnData.deviceId = brewappdev.deviceId(+) AND ");
	  		sbQuery.append("brewappdev.brewAppsId = brewapp.brewAppsId(+) AND ");
	  		sbQuery.append("brewappdev.deviceId = device.deviceId(+) AND ");
	  		sbQuery.append("brewapp.brewAppsId = app.appsId(+) AND ");
	  		sbQuery.append("app.aimsAllianceId = alliance.allianceId(+) AND ");
	  		sbQuery.append("bnData.brewNstlId = " + brewNstlId.toString() + " ORDER BY bnData.developerName, bnData.applicationName ");
	  		Query	query	= session.createQuery(sbQuery.toString());
  		
			for	(Iterator	it = query.iterate();	it.hasNext();)
			{
				brewData = new BrewNstlDataExt();
				Object objects[] = (Object[])it.next();
				brewData.setDeveloperName( Utility.getObjectString(objects[0]) );
				brewData.setApplicationName( Utility.getObjectString(objects[1]));
				brewData.setHandset(Utility.getObjectString(objects[2]));
				brewData.setVersion(Utility.getObjectString(objects[3]));
				brewData.setCompanyName(Utility.getObjectString(objects[4]));
				brewData.setAppTitle(Utility.getObjectString(objects[5]));
				brewData.setAppVersion(Utility.getObjectString(objects[6]));
				brewData.setBrewNstlId((Long)objects[7]);
				brewData.setBrewAppsId((Long)objects[8]);
				brewData.setDataEntryId((Long)objects[9]);
				brewData.setDeviceModel(Utility.getObjectString(objects[10]));
				collection.add(brewData);
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

	    return collection;

	}

	/**/
	/*
	 * Delete the specific catalog data from the database
	 **/
	public static void deleteBrewNstl(Long brewNstlId)	throws HibernateException,Exception
	{
		Session     session = null;
		Transaction tx      = null;
		
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	   	tx= session.beginTransaction();
	     	session.delete("from com.netpace.aims.model.application.AimsBrewNstlUpload brew where brew.brewNstlId = " + brewNstlId.toString());
	     	tx.commit();
		 }
		catch(HibernateException e)
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

// Class Ends
	
}