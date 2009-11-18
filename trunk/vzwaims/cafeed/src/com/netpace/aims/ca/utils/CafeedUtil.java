package com.netpace.aims.ca.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

import com.netpace.aims.ca.constants.CafeedConstants;
import com.netpace.aims.ca.exceptions.InvalidArgumentsException;
import com.netpace.aims.ca.model.CachedSuite;
import com.netpace.aims.ca.model.DeveloperFeed;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/


public class CafeedUtil {
	
	private static Logger log = Logger.getLogger(CafeedUtil.class.getName());
	
	public static String createFile(Collection<DeveloperFeed> feeds, String pFileName){
		
		String separator = CafeedBundle.getInstance().getProperty("feed.separator");
		if (separator == null || "".equals(separator)){
			separator = CafeedConstants.DEFAULT_SEPARATOR;
		}
		
		CafeedCache cafeedCache = CafeedCache.getInstance(new CachedSuite());
		String fileNameFromCache = cafeedCache.getCachedSuite().getFileName();
		SimpleDateFormat formatter = new SimpleDateFormat(CafeedConstants.FEED_TIME_FORMAT);
	
		BufferedWriter bos = null;
		FileWriter fos = null;
		try
		{
			//String currentTime= getTimeInFormat(new Date(), "MMDDYYYY_HHMMSS");
			//String fileName = "Vzdn_Developer_Feed_"+currentTime+".txt";
			String fileName = pFileName;
			if(fileNameFromCache != null && !"".equals(fileNameFromCache)){
				fileName = fileNameFromCache;
			}
			File feedfile = new File(fileName);
			fos = new FileWriter(feedfile);
			bos = new BufferedWriter(fos);
			
			bos.write("12" + separator + "Record Type" + separator + "Alliance ID" + separator + "Alliance Name" + separator + "Contract ID" + separator + "Related Date");
			bos.write("\n");
			for(DeveloperFeed feed : feeds){
				String contractId = (String)(null == feed.getContractId() ? "" : feed.getContractId().toString());
				bos.write("21" + separator + "" + feed.getRecordType() + "" + separator + ""+feed.getVendorId()+ "" + separator + "" 
						+feed.getAllianceName()+ "" + separator + "" + contractId + "" + separator + ""+ formatter.format(feed.getAcceptRejectTime()));
				bos.write("\n");
			}
			//if(feeds.size()> 0) {
			bos.write("91" + separator + "");
			bos.write(new Integer(feeds.size()).toString());
			//}
			return fileName;
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
			return null;
		}				
		catch(IOException ioe){
			ioe.printStackTrace();
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally
		{
			try
			{
				bos.close();
				fos.close();
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}			
		}
		
	}
	
	public static void displayFeeds(Collection<DeveloperFeed> feeds){
		System.out.println("\nFinal Feed:\n\n");		
		String separator = CafeedBundle.getInstance().getProperty("feed.separator");
		SimpleDateFormat formatter = new SimpleDateFormat(CafeedConstants.FEED_TIME_FORMAT);
		if (separator == null || "".equals(separator)){
			separator = CafeedConstants.DEFAULT_SEPARATOR;
		}
		System.out.println("12" + separator + "Record Type" + separator +"Alliance ID" + separator +"Alliance Name" + separator +"Contract ID" + separator +"Related Date");
		for(DeveloperFeed feed : feeds){
			String contractId = (String)(null == feed.getContractId() ? "" : feed.getContractId().toString());
			System.out.println("21" + separator +"" + feed.getRecordType() + "" + separator +""+feed.getVendorId()+ "" + separator +"" 
					+feed.getAllianceName()+ "" + separator +"" + contractId + "" + separator +""+ formatter.format(feed.getAcceptRejectTime()));
		}
		System.out.print("91" + separator +"");
		System.out.println(feeds.size());
	}	

	public static ArrayList<DeveloperFeed> merge(Collection<DeveloperFeed> feedOneCollection, Collection<DeveloperFeed> feedTwoCollection){
		ArrayList<DeveloperFeed> mergedFeed = new ArrayList<DeveloperFeed>();
		
		for(DeveloperFeed feedOne : feedOneCollection){
			mergedFeed.add(feedOne);
		}		
		for(DeveloperFeed feedTwo : feedTwoCollection){
			mergedFeed.add(feedTwo);
		}
		
		return mergedFeed;
	}
	
	public static ArrayList<DeveloperFeed> exclude(Collection<DeveloperFeed> resultedIds, Collection<DeveloperFeed> excludes){
		ArrayList<DeveloperFeed> filtered = new ArrayList<DeveloperFeed>();
		
		boolean flag = true ;
		
		for(DeveloperFeed feed : resultedIds){
			for(DeveloperFeed exclude : excludes){
				if(
						feed.getContractId().intValue() == exclude.getContractId().intValue() &&
						feed.getAllianceId().intValue() == exclude.getAllianceId().intValue() 
				){
					flag = false ;
					break ;
				}
				else{
					flag = true ;
				}
			}
			if(flag){
				filtered.add(feed) ;
				flag = false ;
			}
		}		
		return filtered ;
	}
	
	public static Date goBackInTimeByDays(String inputDate, int backDaysValue){
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(CafeedConstants.TIME_FORMAT);
		    GregorianCalendar gc = new GregorianCalendar();
		    java.util.Date d = sdf.parse(inputDate);
		    gc.setTime(d);
		    int dayBefore = gc.get(Calendar.DAY_OF_YEAR);
		    gc.roll(Calendar.DAY_OF_YEAR, -1 * backDaysValue);
		    int dayAfter = gc.get(Calendar.DAY_OF_YEAR);
		    if(dayAfter > dayBefore) {
		        gc.roll(Calendar.YEAR, -1 * backDaysValue);
		    }
		    gc.get(Calendar.DATE);
		    java.util.Date backDate = gc.getTime();
		    return backDate;
		}
		catch(ParseException e){
			System.out.println("Invalid Date: Please follow the format" + CafeedConstants.TIME_FORMAT);
			return null;
		}
	}
	
	public static Date getDateForString(String inputDate){
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(CafeedConstants.TIME_FORMAT);
		    java.util.Date d = sdf.parse(inputDate);
		    return d;
		}
		catch(ParseException e){
			System.out.println("Invalid Date: Please follow the format" + CafeedConstants.TIME_FORMAT);
			return null;
		}
	}
	
	public static Date goBackInTimeByMinutes(Date inputDate, int backMinutesValue){
		return null;
	}
	
	public static String getTimeInFormat(Date time, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(CafeedConstants.FILE_NAME_TIME_FORMAT);		
		return formatter.format(time);
	}
	
	public static void verifyArguments(String[] args){
		try
		{
			CachedSuite cachedSuite = new CachedSuite();
			String startTime, endTime, fileName, scpDontSend, scpServer, scpUserName, scpPassword;			
			
			if(args.length>=1){				
				startTime = args[0];				
				if(startTime == null || "".equals(startTime) || !inRequiredFormat(startTime)){
					log.debug("Issue with start time: exiting");
					throw new InvalidArgumentsException("Issue with start time: exiting");
				}
				else{
					cachedSuite.setStartTime(startTime);
				}
			}
			
			if(args.length>=2){
				endTime = args[1];				
				
				if(endTime == null || "".equals(endTime) || !inRequiredFormat(endTime)){				 
					
					log.debug("Issue with End time: exiting");
					throw new InvalidArgumentsException("Issue with End-time: exiting");
				}
				else{
					cachedSuite.setEndTime(endTime);
				}
			}			
			
			if(args.length >= 3){
				fileName = args[2];
				cachedSuite.setFileName(fileName);
			}
			else{
				cachedSuite.setAutoGenFile(true);
			}			
			
			if(args.length >= 4){
				scpDontSend = args[3];				
				if(scpDontSend.toLowerCase().equals("yes") || scpDontSend.toLowerCase().equals("no")){
					cachedSuite.setScpServer(scpDontSend);
				}
				else{
					log.debug("Invalid dontsent(true|false) argument:" + scpDontSend);
					throw new InvalidArgumentsException("Invalid dontsent(true|false) argument:" + scpDontSend);
				}
			}
			else{
				scpDontSend = CafeedBundle.getInstance().getProperty("scp.server.dont.send");;
				cachedSuite.setScpServer(scpDontSend);
			}
			
			if(args.length >= 5){
				scpServer = args[4];
				cachedSuite.setScpServer(scpServer);
			}
			else{
				scpServer = CafeedBundle.getInstance().getProperty("scp.server.url");;
				cachedSuite.setScpServer(scpServer);
			}
			
			if(args.length >= 6){
				scpUserName = args[5];
				cachedSuite.setScpServerUserName(scpUserName);
			}
			else{
				scpUserName = CafeedBundle.getInstance().getProperty("scp.server.username");
				cachedSuite.setScpServerUserName(scpUserName);
			}
				
			
			if(args.length == 7){
				scpPassword = args[6];
				cachedSuite.setScpServerUserName(scpPassword);
			}
			else{
				scpPassword = CafeedBundle.getInstance().getProperty("scp.server.password");
				cachedSuite.setScpServerPassword(scpPassword);
			}				
		
			if(args.length < 2 && args.length > 7){
				log.debug("Invalid number or arguments ["+args.length+"] :: Existing");
				throw new InvalidArgumentsException("Invalid number or arguments ["+args.length+"] :: Existing");				
			}			
			
			//loading the read parameters in the cache.
			CafeedCache.getInstance(cachedSuite);				
		}
		catch(InvalidArgumentsException iae){
			System.out.println(iae.getMessage());
			System.out.println(CafeedConstants.ARGUMENTS_USAGE_MESSAGE);
			System.exit(1);
		}
	}
	
	public static boolean inRequiredFormat(String time){
		try{
			SimpleDateFormat formatter = new SimpleDateFormat(CafeedConstants.TIME_FORMAT);			
			formatter.parse(time);
			log.debug("Time format validated on start and end times:");			
			return true;
		}
		catch(ParseException pe){
			log.debug("Invalid start and/or end time format");
			log.info("return value: false");
			return false;
		}
	}
}
