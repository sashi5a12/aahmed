package com.netpace.aims.controller.application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.controller.alliance.AllianceContractForm;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsEnterpriseApp;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsJMAAlliance;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.masters.BdsMarketSegment;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;

public class JMAApplicationHelper {
	
	 public static boolean validateAllianceContract(Long aimsAllianceId, String currUserType) throws HibernateException {
	        boolean contractPresent = false;
	        Collection allianceContracts = null;
	        try {
	            allianceContracts = AllianceManager.getAllianceAcceptedActiveContractsByPlatform(aimsAllianceId, AimsConstants.ENTERPRISE_PLATFORM_ID, currUserType);
	            if(allianceContracts!=null && allianceContracts.size()>0) {
	            	contractPresent = true;
	            }
	        }//end try
	        catch (HibernateException he) {
	            System.out.println("EntApplicationUpdateAction.validateAllianceContract: exception occured while getting alliance accepted contracts.");
	            he.printStackTrace();
	            throw he;
	        }
	        return contractPresent;
	    }
	 
	 /**
	  * This method check that is alliance has zero contract 
	  * @param aimsAllianceId
	  * @param currUserType
	  * @return true in case of no contract
	  * @throws HibernateException
	  */
	 public static boolean validateAllianceHasNoContract(Long aimsAllianceId, String currUserType) throws HibernateException {
	        boolean contractPresent = false;
	        Collection allianceContracts = null;
	        try {
	            allianceContracts = AllianceManager.getAllianceAcceptedActiveContractsByPlatform(aimsAllianceId, AimsConstants.ENTERPRISE_PLATFORM_ID, currUserType);
	            if(allianceContracts!=null && allianceContracts.size()==0) {
	            	contractPresent = true;
	            }
	        }//end try
	        catch (HibernateException he) {
	            System.out.println("EntApplicationUpdateAction.validateAllianceContract: exception occured while getting alliance accepted contracts.");
	            he.printStackTrace();
	            throw he;
	        }
	        return contractPresent;
	    }
	 public static boolean validateAllianceBOBOContract(Long aimsAllianceId, String currUserType) throws HibernateException {
	        boolean contractPresent = false;
	        Collection allianceContracts = null;
	        try {
	            allianceContracts = AllianceManager.getAllianceAcceptedActiveBOBOContractsByPlatform(aimsAllianceId, AimsConstants.ENTERPRISE_PLATFORM_ID, currUserType);
	            if(allianceContracts!=null && allianceContracts.size()>0) {
	            	contractPresent = true;
	            }
	        }//end try
	        catch (HibernateException he) {
	            System.out.println("EntApplicationUpdateAction.validateAllianceBOBOContract: exception occured while getting alliance accepted contracts.");
	            he.printStackTrace();
	            throw he;
	        }
	        return contractPresent;
	    }
	 public static Collection getAllianceBOBOContract(Long aimsAllianceId, String currUserType) throws HibernateException {
	     
	        Collection allianceContracts = null;
	        try {
	            allianceContracts = AllianceManager.getAllianceAcceptedActiveBOBOContractsByPlatform(aimsAllianceId, AimsConstants.ENTERPRISE_PLATFORM_ID, currUserType);
	          
	        }//end try
	        catch (HibernateException he) {
	            System.out.println("EntApplicationUpdateAction.validateAllianceBOBOContract: exception occured while getting alliance accepted contracts.");
	            he.printStackTrace();
	            throw he;
	        }
	        return allianceContracts;
	    }
	 
	 public static boolean displayTabLBS(String userType, AimsEnterpriseApp aimsEntApp) {
		 boolean flag=false;
		 
		 if(userType!=null && aimsEntApp!=null)
		 {
			 if(userType.equals(AimsConstants.VZW_USERTYPE))
			 {
				 if(aimsEntApp.getIsInterestedInLBS()!=null && aimsEntApp.getIsInterestedInLBS().equals("Y")){
					 flag=true;
	         	}
	         	
			 }
			 else  if(userType.equals(AimsConstants.ALLIANCE_USERTYPE)){
				 if((aimsEntApp.getIsInterestedInLBS()!=null && aimsEntApp.getIsInterestedInLBS().equals("Y"))
	         			&& (aimsEntApp.getIsLbsAccept()!=null && aimsEntApp.getIsLbsAccept().equals("Y"))){
	         		flag=true;
	         	}
				 
			 }
		 }
		 
		 return flag;
	 }
	 
	 
	 public static boolean displayTabLBS(String userType, String lngAimsAppsId) {
		 boolean flag=false;
		 AimsEnterpriseApp aimsEntApp=null;
		 
		 try
         {
			 aimsEntApp = AimsEntAppsManager.getAimsEntApps(new Long(lngAimsAppsId));
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
		 
		 if(userType!=null && aimsEntApp!=null)
		 {
			 if(userType.equals(AimsConstants.VZW_USERTYPE))
			 {
				 if(aimsEntApp.getIsInterestedInLBS()!=null && aimsEntApp.getIsInterestedInLBS().equals("Y")){
					 flag=true;
	         	}
	         	
			 }
			 else  if(userType.equals(AimsConstants.ALLIANCE_USERTYPE)){
				 if((aimsEntApp.getIsInterestedInLBS()!=null && aimsEntApp.getIsInterestedInLBS().equals("Y"))
	         			&& (aimsEntApp.getIsLbsAccept()!=null && aimsEntApp.getIsLbsAccept().equals("Y"))){
	         		flag=true;
	         	}
				 
			 }
		 }
		 
		 return flag;
	 }

	 public static boolean displayTabBOBO(String userType,AimsApp aimsApp, AimsEnterpriseApp aimsEntApp) {
		 boolean flag=false;
		 
		 if(userType!=null && aimsEntApp!=null && aimsApp!=null)
		 {
			 try
			 {	
				 if(userType.equals(AimsConstants.VZW_USERTYPE)) 
				 {
					if(aimsEntApp.getIsInterestedInBOBO()!=null && aimsEntApp.getIsInterestedInBOBO().equals("Y")){
					  flag=true;
			         }
			         	
				 } 
				 else  if(userType.equals(AimsConstants.ALLIANCE_USERTYPE) && validateAllianceBOBOContract(aimsApp.getAimsAllianceId(), userType)){
					if((aimsEntApp.getIsInterestedInBOBO()!=null && aimsEntApp.getIsInterestedInBOBO().equals("Y"))
			         		&& (aimsEntApp.getIsBoboAccept()!=null && aimsEntApp.getIsBoboAccept().equals("Y"))){
			         	flag=true;
			         }
				 }
				 
			 } 
			 catch (HibernateException e)
			 {
				 e.printStackTrace();
			 }
		 }
		 
		 return flag;
	 }
	 
	 public static boolean displayTabBOBO(String userType,String lngAimsAppsId) {
		 boolean flag=false;
		 AimsApp aimsApp=null;
		 AimsEnterpriseApp aimsEntApp=null;
		 
		 try
         {
			 Long appId = new Long(lngAimsAppsId); 
			 aimsApp = AimsEntAppsManager.getAimsApps(appId);
			 aimsEntApp = AimsEntAppsManager.getAimsEntApps(appId);
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
             
		 
		 if(userType!=null && aimsEntApp!=null && aimsApp!=null)
		 {
			 try
			 {
				if(userType.equals(AimsConstants.VZW_USERTYPE)) 
				{
					if(aimsEntApp.getIsInterestedInBOBO()!=null && aimsEntApp.getIsInterestedInBOBO().equals("Y")){
						 flag=true;
			        } 	
				}
				else  if(userType.equals(AimsConstants.ALLIANCE_USERTYPE) && validateAllianceBOBOContract(aimsApp.getAimsAllianceId(), userType)){
					if((aimsEntApp.getIsInterestedInBOBO()!=null && aimsEntApp.getIsInterestedInBOBO().equals("Y"))
			         		&& (aimsEntApp.getIsBoboAccept()!=null && aimsEntApp.getIsBoboAccept().equals("Y"))){
			         	flag=true;
					}
						 
				}
				 
			 }
			 catch (HibernateException e)
			 {
				 e.printStackTrace();
			 }	 
		 }
		 
		 return flag;
	 }

	 public static String getYesNo(String value)
	 {
		 if(value!=null && value.equals("Y"))
			 return "Y";
		 else
			 return "N";
	 }
	 
	 public static boolean wordCountCheck(String words,int length)
	 {
		 boolean flag=true;
		 if(words!=null)
		 {
			 int tempLength=words.split(" ").length;
			 if(tempLength>length)
				 flag=false;
		 }
		 
		 
		 return flag;
	 }
	 
	 public static void journalEntry(String journalEntryFor,Long appsId,String user,String userType)
	 {
		 if(journalEntryFor!=null && !journalEntryFor.equals(""))
		 {
			 String value="";
			 if(journalEntryFor.equals(AimsConstants.AIMS_USER_ACCEPT_LBS))
				 value="LBS Accept By ";
			 else  if(journalEntryFor.equals(AimsConstants.AIMS_USER_REJECT_LBS))
				 value="LBS Reject By ";
			 else  if(journalEntryFor.equals(AimsConstants.AIMS_VZW_ACCEPT_BOBO))
				 value="BOBO Accept By ";
			 else if(journalEntryFor.equals(AimsConstants.AIMS_VZW_REJECT_BOBO))
				 value="BOBO reject By ";
			 else if(journalEntryFor.equals(AimsConstants.AIMS_VZW_ACCEPT_LBS))
				 value="LBS Accept By ";
			 else if(journalEntryFor.equals(AimsConstants.AIMS_VZW_REJECT_LBS))
				 value="LBS Reject By ";
			 else 
				 value=null;
			
				
			if(value!=null)
			{
				String type=AimsConstants.JOURNAL_TYPE_PRIVATE;
				if(StringFuncs.NullValueHTMLReplacement(userType).equals(AimsConstants.ALLIANCE_USERTYPE))
					type=AimsConstants.JOURNAL_TYPE_PUBLIC;
				
				value= value + user;
				 try {
					AimsApplicationsManager.saveJournalEntry(
							 appsId,
							 value,
							 type,
					         "system");
					
					if(type.equals(AimsConstants.JOURNAL_TYPE_PUBLIC))
					{
						//Send notification
						sendNotificationForpublicJournalEntry(appsId);
					}
				} catch (Exception e) {
					System.out.println("JMAApplicationHelper.journalEntry exception occure");
					e.printStackTrace();
				}
			}
		 }
	 }
	 
	 public static void journalEntry(String journalText,String journalType, Long allianceId, Long appsId,String createdBy)throws Exception
	 {
		 
		 journalText=StringFuncs.NullValueReplacement(journalText);
		 try 
		 {	
			 AimsJournalEntry je=new AimsJournalEntry();
			 
			 je.setAimsAllianceId(allianceId);
			 je.setAimsAppId(appsId);
			 je.setJournalType(journalType);
			 je.setJournalText(journalText);
			 je.setCreatedBy(createdBy);
			 je.setCreatedDate(new Date());
			 
			AimsApplicationsManager.saveOrUpdateJournalEntries(je);
							
			
		} catch (Exception e) {
			System.out.println("JMAApplicationHelper.journalEntry exception occure");
			e.printStackTrace();
			throw e;
		}
			
		 
	 }
	 
	 public static void sendNotificationForpublicJournalEntry(Long appsId)
	 {
		 try
		 {
			 AimsApp aimsApp = AimsEntAppsManager.getAimsApps(appsId);
			 AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_PUBLIC_JOURNAL_ENTRY);
				
			 if (aimsEvent != null) {
			 	AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
			 	aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
			 	aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());

			 	//must set these properties
			 	aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId() .toString());
			    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

			 	aimsEvent.raiseEvent(aimsEventObject);
			 }
			 
		 }
		 catch(Exception ex)
		 {
			 System.out.println("JMAApplicationHelper.sendNotificationForpublicJournalEntry exception occure");
			 ex.printStackTrace();
		 }
	 }
	 /**
	  * 	Segment              AIMS_TYPE  BDS_MARKET_SEGMENT
	  * 	Mobile Professional      161          9
	  *     SoHo                     162          10
	  *     Services                 163          11
	  *     Enterprise               164          12
	  *           
	  * @param marketSegment
	  * @return
	  */
	 public static Long[] getBdsMarketSegmentId(Long[] marketSegment)
	 {
		 Long[] bdsMktSeg=null;
		  
		 if(marketSegment!=null)
		 {
			 bdsMktSeg =new Long [marketSegment.length];
			 for(int i=0; i<marketSegment.length;i++)
			 {
				 if(marketSegment[i].equals(new Long(161)))
					 bdsMktSeg[i]=new Long(9);
				 else if(marketSegment[i].equals(new Long (162)))
					 bdsMktSeg[i]=new Long(10);
				 else if(marketSegment[i].equals(new Long(163)))
					 bdsMktSeg[i]=new Long(11);
				 else if(marketSegment[i].equals(new Long(164)))
					 bdsMktSeg[i]=new Long(12);
			 }
		 }
		 else
			 bdsMktSeg = new Long[0];
		 
		 return bdsMktSeg;
	 }
	 
	 public static Long[] getMarketSegmentId(EntAppPublishSolutionVO vo)
	 {
		 Long[] mktSegId=null;
		 ArrayList list=new ArrayList();
		 
		 if(StringFuncs.NullValueReplacement(vo.getIsMobileProfessional()).equals("Y"))
			 list.add(new Long(161));
		 if(StringFuncs.NullValueReplacement(vo.getIsSoho()).equals("Y"))
			 list.add(new Long(162));
		 if(StringFuncs.NullValueReplacement(vo.getIsSme()).equals("Y"))
			 list.add(new Long(163));
		 if(StringFuncs.NullValueReplacement(vo.getIsEnterprise()).equals("Y"))
			 list.add(new Long(164));
		 
		 mktSegId=new Long[list.size()];
		 for(int i=0; i<list.size(); i++)
		 {
			 mktSegId[i]=(Long) list.get(i);
		 }
			 
		 return mktSegId;
	 }
	 
	 public static boolean displaySporlight(String lngAimsAppsId) {
		 boolean flag=false;
		 AimsEnterpriseApp aimsEntApp=null;
		 
		 try
         {
			 Long appId = new Long(lngAimsAppsId); 
			 aimsEntApp = AimsEntAppsManager.getAimsEntApps(appId);
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
             
		 
		 if(aimsEntApp!=null)
		 {
			 try
			 {
				 if(StringFuncs.NullValueReplacement(aimsEntApp.getIsPublished()).equals("Y"))
					 flag=true;
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }	 
		 }
		 
		 return flag;
	 }
}
