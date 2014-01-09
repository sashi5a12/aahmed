package com.netpace.device.services.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVWriter;

import com.netpace.device.dao.MediaDao;
import com.netpace.device.dao.ReportDao;
import com.netpace.device.entities.VapMedia;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.EventType;
import com.netpace.device.enums.ReprotsNameEnum;
import com.netpace.device.exceptions.SendReportException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.ReportService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.notification.services.EventService;

@Service(value = "reportService")
public class ReportServiceImpl implements ReportService {

    private final static Log log = LogFactory.getLog(ReportServiceImpl.class);
   
    @Autowired
    private ReportDao reportDao;

   @Autowired
    private MediaDao mediaDao;
    
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    
    @Autowired
    private EventService eventService;
    
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void sendCSVReport(String reportType, String downloadLink){
		log.debug("ReportServiceImpl.sendProductsDetailCSV (Send Products Detail CSV File): Start -------------------"+new Date());	
		
		log.debug("Got request for report type: "+reportType);
		long mStartTime = new Date().getTime();
        
		try {
			
			if(reportType.equals(ReprotsNameEnum.PRODUCTS_DETAIL.name())){				
				List<Object[]> dbData=reportDao.getAllProductsDetailForCSV();
				writeCSV(dbData, reportType, "Product_Details_SA.csv", EventType.SEND_PRODUCTS_DETAIL_REPORT.toString(), downloadLink);
			}
			else if(reportType.equals(ReprotsNameEnum.PRODUCTS_TESTING_DETAIL.name())){
				List<Object[]> dbData=reportDao.getAllCompleteTestingProductsForCSV();				
				writeCSV(dbData, reportType, "Testing_Details_SA.csv", EventType.SEND_PRODUCTS_TESTING_DETAIL_REPORT.toString(), downloadLink);
			}
			else if(reportType.equals(ReprotsNameEnum.COMPANY_DETAIL.name())){
				List<Object[]> dbData=reportDao.getAllCompanyForCSV();				
				writeCSV(dbData, reportType, "Company_Details_SA.csv", EventType.SEND_COMPANY_DETAIL_REPORT.toString(), downloadLink);
			}
			else if(reportType.equals(ReprotsNameEnum.ALL_DEVELOPER_USERS.name())){
				List<Object[]> dbData=reportDao.getUsersForCSV(false);		
				writeCSV(dbData, reportType, "Registered_Partner_Report_Monthly.csv", EventType.SEND_DEVELOPERS_MONTHLY_REPORT.toString(), downloadLink);
			}
			else if(reportType.equals(ReprotsNameEnum.WEEKLY_DEVELOPER_USERS.name())){
				List<Object[]> dbData=reportDao.getUsersForCSV(true);		
				writeCSV(dbData, reportType, "Registered_Partner_Report_Weekly.csv", EventType.SEND_DEVELOPERS_WEEKLY_REPORT.toString(), downloadLink);
			}
			else {
	    		log.debug("Report parameter is not correct.");
			}
		} catch (SendReportException e) {
			throw e;
		}
		long mEndTime = new Date().getTime();
		log.debug("Method Total Execution time in second(s): "+TimeUnit.MILLISECONDS.toSeconds((mEndTime-mStartTime)) +" sec");
		log.debug("ReportServiceImpl.sendProductsDetailCSV (Send Products Detail CSV File): End -------------------"+new Date());
	}

	
	public void writeCSV(List<Object[]> dbData, String reportType, String fileName, String eventName, String downloadLink) {
		VapMedia media = new VapMedia();
		media.setFileName(fileName);
		media.setFileType("text/csv");
		media.setFileLength(0);
		media.populatedAuditFields("curl-job");
		
		mediaDao.add(media);
		
		String filePath=applicationPropertiesService.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, VAPConstants.ATTACHMENT_FILE_PATH);
		File file = new File(filePath + media.getMediaId() + ".csv");
		
		FileWriter fileWriter=null;
		CSVWriter csvWriter = null;
		try {
			fileWriter = new FileWriter(file);
			csvWriter = new CSVWriter(fileWriter);
			
			if(reportType.equals(ReprotsNameEnum.PRODUCTS_DETAIL.name())){
				String[] header= new String[16];
				header[0]="Partner Name";	
				header[1]="Product Name";
				header[2]="Submission Type";	
				header[3]="Description";
				header[4]="Model #";
				header[5]="Supplier Products Part #";	
				header[6]="Product Category";
				header[7]="Workflow Phase - Status";	
				header[8]="App Submission Date";
				header[9]="App Submission Time";	
				header[10]="Device Marketing Approval date"; 	
				header[11]="Export Compliance Approval date"; 	
				header[12]="Requirements Groups Approval date";	
				header[13]="Device Compliance Start Testing Approval Date";
				header[14]="Device Compliance End Testing Approval Date";	
				header[15]="Device Marketing Approval date";
				csvWriter.writeNext(header);
			}
			else if(reportType.equals(ReprotsNameEnum.PRODUCTS_TESTING_DETAIL.name())){
				String[] header= new String[8];
				header[0]="Partner Name";	
				header[1]="Product Name";
				header[2]="Description";
				header[3]="Model #";
				header[4]="Supplier Products Part #";	
				header[5]="Product Category";
				header[6]="Device Compliance Start Testing Approval Date";
				header[7]="Device Compliance End Testing Approval Date";	
				csvWriter.writeNext(header);
			}
			else if(reportType.equals(ReprotsNameEnum.COMPANY_DETAIL.name())){
				String[] header= new String[13];
				header[0]="Partner Name";	
				header[1]="Workflow Phase -Status";
				header[2]="Created Date";
				header[3]="Main Company Street Address";
				header[4]="City/Town";	
				header[5]="State/Province";
				header[6]="Zip Code/Postal Code";
				header[7]="Country";	
				header[8]="Main Point of Contact";
				header[9]="MPOC email";
				header[10]="MPOC Phone Number";
				header[11]="Sales Contact";
				header[12]="Sales Contact Phone";
				csvWriter.writeNext(header);
			}
			else if(reportType.equals(ReprotsNameEnum.ALL_DEVELOPER_USERS.name()) || reportType.equals(ReprotsNameEnum.WEEKLY_DEVELOPER_USERS.name()) ){
				String[] header= new String[14];
				header[0]="Partner ID";	
				header[1]="Partner Name";	
				header[2]="Partner Street Address";	
				header[3]="Partner Address City";
				header[4]="Partner Address State/Province";	
				header[5]="Partner Address ZIP or Postal Code";	
				header[6]="Partner Address Country";	
				header[7]="User Full Name";	
				header[8]="User Street Address";	
				header[9]="User Address City";	
				header[10]="User Address State/Province";	
				header[11]="User Address ZIP or Postal Code";	
				header[12]="User Address Country";	
				header[13]="Status";
				csvWriter.writeNext(header);
			}
			
			if(dbData!=null && dbData.size()>0){
				for (Object[] data: dbData){
					String[] strArr=new String[data.length];
					
					for (int i=0; i<strArr.length; i++){
						
						if(reportType.equals(ReprotsNameEnum.COMPANY_DETAIL) && i==1 && StringUtils.isEmpty((String)data[i])){
							strArr[i]="Approved"; 
						}
						else {
							strArr[i]=(String)data[i];
						}
					}
					csvWriter.writeNext(strArr);
				}
//				csvWriter.writeAll(dbData);
			}
			
                        csvWriter.flush();
                        fileWriter.flush();
                        log.debug("Total Records found in bytes: "+dbData.size());
                        log.debug("file wrote on path: " + file.getAbsolutePath());
                        log.debug("File size: " + file.length());
                        media.setFileLength((int)file.length());
                                
			Map<String, String> params= new HashMap<String, String>();
			downloadLink=downloadLink+media.getMediaId();
			
			log.debug("Download link sent in mail: "+downloadLink);
			params.put(VAPConstants.PLACEHOLDER_REPORT_DOWNLOAD_LINK, downloadLink);
	        eventService.raiseEvent(eventName, params, null, null, null, null);
		} 
		catch (IOException e) {
			log.error(e,e);
			throw new SendReportException("error.send.report");
		} 
		finally {
			if (fileWriter!=null){
				try {
					fileWriter.close();
				} catch (IOException e) {
					log.debug(e,e);
					throw new SendReportException("error.send.report");
				}
			}
			if(csvWriter!=null){
				try {
					csvWriter.close();
				} catch (IOException e) {
					log.error(e,e);
					throw new SendReportException("error.send.report");
				}
			}			
		}
	}
}
