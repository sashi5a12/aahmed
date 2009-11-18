package	com.netpace.aims.bo.application;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.*;
import com.netpace.aims.model.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.controller.application.*;
import com.netpace.aims.controller.devices.AimsAllianceExt;
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
 * This	class is responsible for getting the details for Reconcile Catalog Data.
 * It has static methods for getting the Reconcile Data Details.
 * @author Ahson Imtiaz
 */
public class ReconcileCatalogManager
{
		
	static Logger	log	=	Logger.getLogger(ReconcileCatalogManager.class.getName());
	static final String strAppStatusFilter = " (" + AimsConstants.SUNSET_ID.toString() + ", " + AimsConstants.REJECTED_ID.toString() + ", " + AimsConstants.SAVED_ID.toString() + ") " ;
	/*
	 *Collection will be returned having all * in the system
	 **/
	public static AimsCatalog saveCatalogData(FormFile ffCatalogData, Date dteCreated, String strComments)	
	throws HibernateException,Exception,AimsInvalidDataException,AimsFileDateException
	{		 		

		Session session = null;
		AimsCatalogsData catalogData = null;
		AimsCatalog catalog = new AimsCatalog();
		String strGINLine = null,strGIN = null;
		
		recordPresentForDate(dteCreated);
		catalog.setCatalogFileName(ffCatalogData.getFileName());
		catalog.setCatalogDateCreated(dteCreated);
		catalog.setIsReconciled("N");
		catalog.setComments(strComments);
		
	   POIFSFileSystem fs      =
              new POIFSFileSystem(ffCatalogData.getInputStream());
      HSSFWorkbook wb = new HSSFWorkbook(fs);
		
		log.debug("File Opened");		
		
		HSSFSheet sheet = wb.getSheet("Live GIN");
    	HSSFRow row = null;
    	HSSFCell cell = null;
    	HSSFCell cellData = null;
      
      if (sheet == null ) throw new AimsInvalidDataException();
      
      row = sheet.getRow(2);
      cell = row.getCell((short)2);
      
      try {
	      if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
			{
		 		strGINLine = cell.getStringCellValue();
		 		strGIN = strGINLine.substring(strGINLine.lastIndexOf("GIN")+3).trim();
		 		java.lang.Integer.parseInt(strGIN);
	      }
	      else throw new AimsInvalidDataException();
	   }
	   catch (Exception e)
	   {
	   	log.debug("Error : GIN Number not found/valid in excel sheet.");
	   	throw new AimsInvalidDataException();
	   }
      
      catalog.setCatalogGin(strGIN);
      
      int iLastRowNum = sheet.getLastRowNum();
		ArrayList collection = new ArrayList(iLastRowNum+1);
      int iCurrentRow = 3;
      
      SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy kk:mm:ss");
      
      try 
      {
	      while (iCurrentRow <= iLastRowNum )
	      {
				 row = sheet.getRow(iCurrentRow);
				 
				 if (row == null) {iCurrentRow++; continue;}
				 
				 catalogData = new AimsCatalogsData();
				
				// Getting Category 
   			 cell = row.getCell((short)2);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	catalogData.setApplicationCategory(cell.getStringCellValue());
				 }
				 else {iCurrentRow++; continue;}
				
      		// Getting Developer
   			 cell = row.getCell((short)3);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	catalogData.setDeveloperName(cell.getStringCellValue());
				 }
				 else {iCurrentRow++; continue;}
				 
      		// Getting Application Name
		 		 cell = row.getCell((short)4);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	catalogData.setApplicationName(cell.getStringCellValue());
				 }
				 else {iCurrentRow++; continue;}
				 
      		// Getting Handset
		 		 cell = row.getCell((short)5);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				 		catalogData.setHandset( new Integer((int)cell.getNumericCellValue()).toString() );
				 	else
				 		catalogData.setHandset(cell.getStringCellValue());
				 }
      		// Getting PartNum
		 		 cell = row.getCell((short)6);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	catalogData.setPartNumber(cell.getStringCellValue());
				 }
				 
      		// Getting Version
		 		 cell = row.getCell((short)7);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				 		catalogData.setVersion( new Integer((int)cell.getNumericCellValue()).toString() );
				 	else
				 		catalogData.setVersion(cell.getStringCellValue());
				 }

      		// Getting Platform ID
		 		 cell = row.getCell((short)8);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				 		catalogData.setPlatformId( new Integer((int)cell.getNumericCellValue()).toString() );
				 	else
				 		catalogData.setPlatformId(cell.getStringCellValue());
				 }
				 
      		// Getting Notes
		 		 cell = row.getCell((short)9);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	catalogData.setNotes(cell.getStringCellValue());
				 }
				 
      		// Getting Days In Queue (Numeric)
		 		 /*cell = row.getCell((short)10);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				 		catalogData.setDaysInQueue( new Long(new Double(cell.getNumericCellValue()).longValue()) );
				 	else
				 		catalogData.setDaysInQueue(new Long(cell.getStringCellValue()));
				 }*/
				 
      		// Getting to WC Date
      		try {
		 		 cell = row.getCell((short)11);
				 if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	//log.debug("******** : " + cell.getDateCellValue());
				 	catalogData.setToWcDate( cell.getDateCellValue() );
				 }
				} catch (Exception e) {log.debug("WC Date not in proper format skipping the field for row :" + iCurrentRow);}
				 
      		// Getting BDS Acceptance Date
		 		 cell = row.getCell((short)12);
				 if (cell.getCellType() != HSSFCell.CELL_TYPE_BLANK )
				 {
				 	if( cell.getCellType() == HSSFCell.CELL_TYPE_STRING )
				 		catalogData.setBdsAcceptanceDate( df.parse(cell.getStringCellValue()) );
				 	else
				 		catalogData.setBdsAcceptanceDate( cell.getDateCellValue() );
				 }
				
				// Add the class to ArrayList 
				collection.add(catalogData);
				
				// Row Increament
				//log.debug("Row Added");
      		iCurrentRow++;
      	}
      } catch (Exception e) {
      	e.printStackTrace();
      	throw new AimsInvalidDataException();
      }
      
	    Transaction	tx = null;      
	    
	    try
	    { 
	     	session = DBHelper.getInstance().getSession();
	     	tx = session.beginTransaction();
      	session.save(catalog);
      	log.debug("saved ***** ");
	  		log.debug("No of records for catalog : " + collection.size() );
	  		session.flush();
	  		for (int iCount= 0 ; iCount < collection.size() ; iCount++)
	  		{
	  			catalogData = (AimsCatalogsData) collection.get(iCount);
	  			catalogData.setAimsCatalog(catalog);
	  			//log.debug("Now saving " + iCount);
	  			session.save(catalogData);
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

	    return catalog;

	}
	
	
	/**/
	/*
	 *Collection will be returned having all * in the system
	 **/
	public static Collection matchCatalogData(AimsCatalog catalog, String strSessionId)	throws HibernateException,Exception
	{		 		
		
		Session session = null;
		BrewNstlDataExt brewData = null;
		ArrayList collection = new ArrayList();
		StringBuffer sbSubQuery = null;
		String[] splitResult = null;
		String strAllianceNamePart, strTitlePart;
		Object objValues[] = null, objects[] = null;
		Query subQuery = null;
		Long lngLinkBrewAppsId, lngLinkDeviceId;
		Iterator itSub;
		
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		StringBuffer sbQuery = 
	  		new StringBuffer("select cData.developerName, cData.applicationName, cData.handset " );
	  		sbQuery.append(", cData.version, alliance.companyName, app.title, app.version, cData.catalogId "); //, device.deviceModel
	  		sbQuery.append(", brewapp.brewAppsId , cData.catalogDataId , match.catalogDataId , cData.brewAppsId, cData.deviceId ");
	  		sbQuery.append(" from com.netpace.aims.model.application.AimsCatalogsData cData,  ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsBrewApp brewapp, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsApp app, ");
	  		sbQuery.append("com.netpace.aims.model.core.AimsAllianc alliance, ");
 			sbQuery.append("com.netpace.aims.model.application.AimsTempCatalogDataMatch match ");
	  		sbQuery.append("where ");
	  		sbQuery.append("cData.applicationName = app.title(+) AND ");
	  		sbQuery.append("cData.version = app.version(+) AND ");
	  		//sbQuery.append("bnData.developerName = alliance.companyName(+) AND ");
	  		sbQuery.append("app.appsId = brewapp.brewAppsId(+) AND ");
	  		sbQuery.append("app.aimsAllianceId = alliance.allianceId(+) AND ");
	  		sbQuery.append(" cData.catalogId = ").append(catalog.getCatalogId().toString()).append(" AND "); 
	  		sbQuery.append(" cData.catalogDataId = match.comp_id.catalogDataId(+) AND "); 
	  		sbQuery.append(" app.aimsLifecyclePhaseId(+) NOT IN ").append(strAppStatusFilter).append(" AND ");
	  		sbQuery.append(" match.comp_id.sessionId(+) = '").append(strSessionId).append("'"); 
	  		sbQuery.append(" ORDER BY cData.developerName, cData.applicationName, cData.handset, cData.version ");
	  		Query	query	= session.createQuery(sbQuery.toString());
  		
			for	(Iterator	it = query.iterate();	it.hasNext();)
			{
				// Using same class as brew since most of the data is similar
				brewData = new BrewNstlDataExt();
				objects = (Object[])it.next();
				brewData.setDeveloperName( Utility.getObjectString(objects[0]) );
				brewData.setApplicationName( Utility.getObjectString(objects[1]));
				brewData.setHandset(Utility.getObjectString(objects[2]));
				brewData.setVersion(Utility.getObjectString(objects[3]));
				brewData.setCompanyName(Utility.getObjectString(objects[4]));
				brewData.setAppTitle(Utility.getObjectString(objects[5]));
				brewData.setAppVersion(Utility.getObjectString(objects[6]));
				brewData.setBrewNstlId((Long)objects[7]);  // Has the Catalog Id
				brewData.setBrewAppsId((Long)objects[8]); 
				brewData.setDataEntryId((Long)objects[9]); // Has the CatalogData Id
				lngLinkBrewAppsId = (Long) objects[11]; // Brew Apps Id if record already reconciled
				lngLinkDeviceId = (Long) objects[12]; // Device Id if record already reconciled
				
				// 1- We check either record is already reconcilied if yes fill the linked details,
				// 2- We check either complete match is found w.r.t to alliance name and application name if yes fill the supported devices applicable to entry
				// 3- We check have used already specified the match.
				// 4- We pick the start of company name and start of application name and search for the likely match.
				
					if (lngLinkBrewAppsId != null && lngLinkDeviceId != null && lngLinkBrewAppsId.intValue() != 0 && lngLinkDeviceId.intValue() != 0)
					{
						
						sbSubQuery = new StringBuffer();
						sbSubQuery.append(" SELECT alliance.companyName, app.title, device.deviceModel ,app.version ");
						sbSubQuery.append(" FROM ");
				  		sbSubQuery.append(" com.netpace.aims.model.application.AimsApp app, ");
				  		sbSubQuery.append(" com.netpace.aims.model.core.AimsAllianc alliance, ");
				  		sbSubQuery.append(" com.netpace.aims.model.masters.AimsDevic device ");
				  		sbSubQuery.append(" WHERE device.deviceId = ").append(lngLinkDeviceId);
				  		sbSubQuery.append(" AND app.appsId = ").append(lngLinkBrewAppsId);
				  		sbSubQuery.append(" AND app.aimsAllianceId = alliance.allianceId ");
				  		
				  		subQuery = session.createQuery(sbSubQuery.toString());
				  		
				  		itSub = subQuery.iterate();
				  		BrewNstlDataLinkedDetails bndl = new BrewNstlDataLinkedDetails();
				  		
				  		if (itSub.hasNext()) {
				  			objValues = (Object[]) itSub.next();
				  			bndl.setCompanyName(objValues[0].toString() );
				  			bndl.setApplicationName(objValues[1].toString());
				  			bndl.setDeviceModel(objValues[2].toString());
				  			bndl.setVersion(objValues[3].toString());
				  			//log.debug(" Application Name - " +  objValues[0].toString() + " Device Model " + objValues[2].toString());
				  		}
				  		
				  		brewData.setLinkedDetails(bndl);
						
					}
					else if (brewData.getBrewAppsId() != null && brewData.getBrewAppsId().intValue() != 0){
								brewData.setSupportedDevices((Collection)session.find("from com.netpace.aims.model.application.AimsBrewAppDevice bappdevice where bappdevice.brewAppsId = " + brewData.getBrewAppsId().toString() ));
				   }
					else if (objects[10] != null )
					{
								sbSubQuery = new StringBuffer();
								sbSubQuery.append(" SELECT apps.appsId, dev.deviceId, dev.deviceModel, alliance.companyName, match.catalogDataId , apps.version , apps.title ");
						  		sbSubQuery.append(" FROM com.netpace.aims.model.application.AimsApp apps, ");
						  		sbSubQuery.append(" com.netpace.aims.model.core.AimsAllianc alliance, ");
						  		sbSubQuery.append(" com.netpace.aims.model.masters.AimsDevic dev, ");
						  		sbSubQuery.append(" com.netpace.aims.model.application.AimsTempCatalogDataMatch match ");
						  		sbSubQuery.append(" WHERE apps.appsId = match.appsId AND ");
						  		sbSubQuery.append(" apps.appsId = match.appsId AND ");
						  		sbSubQuery.append(" apps.aimsAllianceId = alliance.allianceId AND ");
						  		sbSubQuery.append(" dev.deviceId = match.deviceId AND ");
						  		sbSubQuery.append(" match.catalogDataId =").append(brewData.getDataEntryId().toString()).append(" AND ");
						  		sbSubQuery.append(" match.comp_id.sessionId = '").append(strSessionId).append("' ");
						  		
								subQuery = session.createQuery(sbSubQuery.toString());
								
								BrewNstlDataLinkedDetails bndl = null;
								
			  					itSub = subQuery.iterate();
			  					if (itSub.hasNext()) {
			  						objValues = (Object[]) itSub.next();
			  						bndl = new BrewNstlDataLinkedDetails();
			  						bndl.setCompanyName(objValues[3].toString() );
			  						bndl.setApplicationName(objValues[6].toString());
			  						bndl.setDeviceModel(objValues[2].toString());
			  						bndl.setVersion(objValues[5].toString());
			  						bndl.setCombinedId(objValues[0].toString() + "-" + objValues[4].toString() + "-" + objValues[1].toString());
			  					}
			  		
			  					brewData.setDefinedMatch(bndl);
			  		
					}
					else if (brewData.getDeveloperName() != null && brewData.getApplicationName() != null)
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
							sbSubQuery.append(" apps.aimsLifecyclePhaseId(+) NOT IN ").append(strAppStatusFilter).append(" AND ");
							sbSubQuery.append(" alliance.companyName like '%").append(strAllianceNamePart).append("%' AND ");
							sbSubQuery.append(" apps.title like '%").append(strTitlePart).append("%' ORDER BY alliance.companyName, dev.deviceModel , apps.version ");
							
							subQuery = session.createQuery(sbSubQuery.toString());
							ArrayList alist = new ArrayList();
							for	(itSub = subQuery.iterate();	itSub.hasNext();)
							{
								objValues = (Object[]) itSub.next();
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
	  		sbQuery.append("brewapp.brewAppsId = bappdevice.brewAppsId ORDER BY alliance.companyName, app.title, bappdevice.aimsDevice.deviceModel, app.version");

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
	public static Collection getCatalogReconciled()	throws HibernateException,Exception
	{		 		
		
		Session session = null;
		Collection collection = null;

	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		collection = session.find("from com.netpace.aims.model.application.AimsCatalog cData where cData.isReconciled = 'Y' ORDER BY cData.catalogDateCreated ");
	  		
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
	public static Collection getCatalogToReconcile()	throws HibernateException,Exception
	{		 		
		
		Session session = null;
		Collection collection = null;

	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		collection = session.find("from com.netpace.aims.model.application.AimsCatalog cData  ORDER BY cData.catalogDateCreated "); //where cData.isReconciled = 'N'
	  		
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
	  		
	  		collection = session.find("from com.netpace.aims.model.application.AimsCatalog cData where cData.catalogDateCreated = :dateValue", dteFile, new DateType());
	  		
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

	public static void associateEntries(Integer[] aSelIndex, String[] aEnteries, Long catalogId)	throws HibernateException
	{		 		
		
		Session session = null;
		Collection collection = null;
		Transaction tx= null;

	   try
	   { 
	     	session = DBHelper.getInstance().getSession();
		  	tx= session.beginTransaction();			
		  	
		  	AimsCatalog catalog = (AimsCatalog) session.find("from com.netpace.aims.model.application.AimsCatalog catalog where catalog.catalogId = " + catalogId.toString()).get(0);
		  	
	   	for (int iCount= 0 ; aSelIndex != null && iCount < aSelIndex.length ; iCount++)
	   	{
	   		//log.debug(" sel chk was : " + aSelIndex[iCount]);
	   		//log.debug(" sel value was : " + aEnteries[aSelIndex[iCount].intValue()]);
	   		StringTokenizer stnz = new StringTokenizer(aEnteries[aSelIndex[iCount].intValue()],"-");
	   		String strBrewAppsId    = stnz.nextToken();
	   		String strCatalogDataId = stnz.nextToken();
	   		String strDeviceId      = stnz.nextToken();
	   		
		  		Query query = session.createQuery("select cData, brewAppDevice from com.netpace.aims.model.application.AimsCatalogsData cData, " + 
		  									" com.netpace.aims.model.application.AimsBrewAppDevice brewAppDevice, " +
		  									" where cData.catalogDataId = " + strCatalogDataId + " AND brewAppDevice.brewAppsId = " + strBrewAppsId + 
		  									" AND brewAppDevice.deviceId = " + strDeviceId );
		  		
		  		Iterator it = query.iterate();
		  		Object[] objects = null;
		  		
		  		if ( it.hasNext() )
		  		{
		  			objects = (Object[]) it.next();
		  			AimsCatalogsData abnd = (AimsCatalogsData) objects[0];
		  			AimsBrewAppDevice abad = (AimsBrewAppDevice) objects[1];
					
		  			abad.setBdsAcceptanceDate(abnd.getBdsAcceptanceDate());
		  			//abad.setPlatformId(abnd.getPlatformId);
		  			abad.setPartNumber(abnd.getPartNumber());
		  			abad.setGinNumber(abnd.getAimsCatalog().getCatalogGin());
		  			abad.setProductionDate(abnd.getAimsCatalog().getCatalogDateCreated());
		  			abnd.setBrewAppsId( new Long(strBrewAppsId));
		  			abnd.setDeviceId(new Long(strDeviceId));
		  			session.update(abnd);
		  			session.update(abad);
		  		}

		  		try {
					//AimsApp aApp = (AimsApp) session.find("from com.netpace.aims.model.application.AimsApp as aapps where aapps.appsId = " + strBrewAppsId).get(0);
					//aApp.setAimsLifecyclePhaseId(AimsConstants.ACCEPTANCE_ID);
					//session.update(aApp);
					session.connection().createStatement().executeUpdate("update AIMS_APPS SET Phase_Id = " + AimsConstants.ACCEPTANCE_ID.toString() + " where APPS_ID = " + strBrewAppsId);
				} catch (Exception ex){log.debug("Error ************ Unable to update application lifecycle to accepted ****** ");ex.printStackTrace();}
	   	}
	   	catalog.setReconciledDate(new Date());
	   	catalog.setIsReconciled("Y");
	   	session.saveOrUpdate(catalog);
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
	public static AimsCatalog getAimsCatalog(Long catalogId)	throws HibernateException
	{		 		
		
		Session session = null;
		Collection collection = null;

	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		return (AimsCatalog) session.find("from com.netpace.aims.model.application.AimsCatalog catalog where catalog.catalogId = " + catalogId.toString()).get(0);
	  		
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
	public static Collection getAssociatedCatalogData(Long catalogId)	throws HibernateException,Exception
	{		 		
		
		Session session = null;
		BrewNstlDataExt brewData = null;
		ArrayList collection = new ArrayList();
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	  		
	  		StringBuffer sbQuery = 
	  		new StringBuffer("select cData.developerName, cData.applicationName, cData.handset " );
	  		sbQuery.append(", cData.version, alliance.companyName, app.title, app.version, cData.catalogId "); //, device.deviceModel
	  		sbQuery.append(", brewapp.brewAppsId , cData.catalogDataId, device.deviceModel ");
	  		sbQuery.append(" from com.netpace.aims.model.application.AimsCatalogsData cData, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsBrewApp brewapp, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsBrewAppDevice brewappdev, ");
	  		sbQuery.append("com.netpace.aims.model.masters.AimsDevic device, ");
	  		sbQuery.append("com.netpace.aims.model.application.AimsApp app, ");
	  		sbQuery.append("com.netpace.aims.model.core.AimsAllianc alliance ");
	  		sbQuery.append("where ");
	  		sbQuery.append("cData.brewAppsId = brewappdev.brewAppsId(+) AND ");
	  		sbQuery.append("cData.deviceId = brewappdev.deviceId(+) AND ");
	  		sbQuery.append("brewappdev.brewAppsId = brewapp.brewAppsId(+) AND ");
	  		sbQuery.append("brewappdev.deviceId = device.deviceId(+) AND ");
	  		sbQuery.append("brewapp.brewAppsId = app.appsId(+) AND ");
	  		sbQuery.append("app.aimsAllianceId = alliance.allianceId(+) AND ");
	  		sbQuery.append("cData.catalogId = " + catalogId.toString() + " ORDER BY cData.developerName, cData.applicationName ");
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
				brewData.setBrewNstlId((Long)objects[7]);  // Has the CatalogId
				brewData.setBrewAppsId((Long)objects[8]);
				brewData.setDataEntryId((Long)objects[9]); // Has the catalogDataId
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
	public static void deleteCatalog(Long catalogId)	throws HibernateException,Exception
	{
		Session     session = null;
		Transaction tx      = null;
		
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	   	tx= session.beginTransaction();  	
	     	session.delete("from com.netpace.aims.model.application.AimsCatalog catalog where catalog.catalogId = " + catalogId.toString());
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
	
	/**
	 *
	 * 
	 **/
	public static Collection getAllAlliances()	throws HibernateException,Exception
	{
		Session     session = null;
		ArrayList	alist = new ArrayList();
		Object[]    objValues = null;
	   
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	     	
	     	Query query = session.createQuery(" SELECT distinct alliance.allianceId, alliance.companyName "  + 
						     					  " FROM com.netpace.aims.model.core.AimsAllianc alliance, " + 
						     					  " com.netpace.aims.model.core.AimsContract ac, " +
						     					  " com.netpace.aims.model.core.AimsAllianceContract aacon " +
						     					  " where alliance.allianceId = aacon.allianceId " +
						     					  " AND aacon.contractId = ac.contractId " +
						     					  " AND alliance.status = '" + AimsConstants.ALLIANCE_STATUS_ACCEPTED + "' " +
						     					  " AND aacon.status = '" + AimsConstants.CONTRACT_ACCEPTED + "' " +
						     					  " AND ac.platformId = " + AimsConstants.BREW_PLATFORM_ID +
						     					  " order by alliance.companyName ");
			
			for (Iterator it = query.iterate() ; it.hasNext() ; )			     					  
			{
				objValues = (Object [])it.next();
				AimsAllianceExt aaext = new AimsAllianceExt();
				aaext.setAllianceId((Long)objValues[0]);
				aaext.setCompanyName((String)objValues[1]);
				alist.add(aaext);
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
		
		return alist;
	}
	
  /**
  */
  public static Collection getAllianceApps (Long allianceId) throws HibernateException
  {    
    Session session = null;
    ArrayList alist = new ArrayList();
    Object[]    objValues = null;
    
    try
    {         
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery(" SELECT app.appsId, app.title, dev.deviceId, dev.deviceModel, app.version, app.aimsLifecyclePhaseId " +
      									" FROM com.netpace.aims.model.application.AimsApp app," +
      									" com.netpace.aims.model.application.AimsBrewAppDevice badev, " +
      									" com.netpace.aims.model.masters.AimsDevic dev " +
      									" WHERE dev.deviceId = badev.deviceId " +
      									" AND app.aimsAllianceId = " + allianceId.toString() + 
      									" AND app.aimsPlatformId = " + AimsConstants.BREW_PLATFORM_ID.toString() + 
											" AND app.aimsLifecyclePhaseId(+) NOT IN " + strAppStatusFilter +
      									//" AND app.aimsLifecyclePhaseId = " + AimsConstants.TESTING_ID.toString() + 
      									" AND app.appsId = badev.brewAppsId  order by app.title, app.version, dev.deviceModel ");
      									
			for (Iterator it = query.iterate() ; it.hasNext() ; )			     					  
			{
				objValues = (Object [])it.next();
				AllianceApplicationDeviceExt aadext = new AllianceApplicationDeviceExt();
				aadext.setBrewAppsId((Long)objValues[0]);
				aadext.setTitle((String)objValues[1]);
				aadext.setDeviceId((Long)objValues[2]);
				aadext.setDeviceModel((String)objValues[3]);
				aadext.setVersion((String)objValues[4]);
				aadext.setAppStatus((Long)objValues[5]);
				alist.add(aadext);
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

    return alist;
  }  
  
  /**
  */
  public static void saveUserSelection(String strCombineId , String strSessionId) throws HibernateException
  {    
    Session session = null;
    Transaction tx      = null;
    String[] Ids = null;
    
    Ids = strCombineId.split("-");    
    
    if (Ids.length != 3 ) return;
    
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	   	tx= session.beginTransaction();  	
	   	AimsTempCatalogDataMatchPK atpk = new AimsTempCatalogDataMatchPK();
	   	AimsTempCatalogDataMatch entry = new AimsTempCatalogDataMatch();
	   	atpk.setSessionId(strSessionId);
	   	atpk.setCatalogDataId(new Long(Ids[1]));
	   	entry.setAppsId(new Long(Ids[0]));
	   	entry.setDeviceId(new Long(Ids[2]));
	   	entry.setComp_id(atpk);
	     	try {
	     		session.save(entry);
	     	} catch (Exception ex){session.saveOrUpdate(entry);}
	     	
	     	tx.commit();
		 }
		catch(HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
  }
  
  /**
  */
  public static void deleteTempData(String strSessionId)
  {    
		Session     session = null;
		Transaction tx      = null;
		
		log.debug("Going to delete temp data from the temp catalog tables ********* ");
	   try
	    { 
	     	session = DBHelper.getInstance().getSession();
	   	tx= session.beginTransaction();  	
	     	session.delete("from com.netpace.aims.model.application.AimsTempCatalogDataMatch catalog where catalog.comp_id.sessionId = '" + strSessionId + "' ");
	     	tx.commit();
		 }
		catch(HibernateException e)
		{
			try {
			if (tx != null)
				tx.rollback(); 
			} catch (Exception ex) {}
			e.printStackTrace();
		}
		finally
		{
			try {
			session.close();
			} catch (Exception e) {}
		}
  }
// Class Ends
	
}            