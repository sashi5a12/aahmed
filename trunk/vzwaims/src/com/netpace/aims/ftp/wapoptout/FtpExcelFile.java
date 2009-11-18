package com.netpace.aims.ftp.wapoptout;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.wapoptout.AimsWapOptoutFtpLog;
import com.netpace.aims.util.AimsFtpUploader;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

public class FtpExcelFile {
	private static final Logger log = Logger.getLogger(FtpExcelFile.class.getName());
	
	public static List getRecordsForExcel()throws Exception{
		log.debug("FtpExcelFile.getRecordsForExcel: Start");
		Session session = null;
		List list=null;
		try {
			session = DBHelper.getInstance().getSession();
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT	o.submittalNumber, o.companyName, ");
			sb.append("			o.reqFirstName, o.reqLastName, o.reqEmailAddress, ");
			sb.append("			o.reqPhoneNumber, o.streetAddress, o.city, o.state, o.zipCode, ");
			sb.append("			o.country, u.bypassUrl, ");
			sb.append("			o.adminCompanyName, o.adminFirstName, o.adminLastName, ");
			sb.append("			o.adminPhoneNumber, o.adminEmailAddress ");
			sb.append("FROM AimsWapOptout as o, ");
			sb.append("			AimsWapOptoutUrls as u ");
			sb.append("WHERE o.wapOptoutId = u.aimsWapOptout.wapOptoutId ");
			sb.append("			AND (o.isDirty='Y') ");
			sb.append("			ORDER BY o.wapOptoutId, u.urlId");

			Query query=session.createQuery(sb.toString());
			list=query.list();
			log.debug("Records returns: "+list.size());
		} catch (HibernateException e) {
			log.error(e, e);
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
		log.debug("FtpExcelFile.getRecordsForExcel: End");
		return list;
	}
	
	public static File writeExcelFile(List records)throws Exception{ 
		log.debug("FtpExcelFile.writeExcelFile: Start");
        BufferedOutputStream bos=null;
        String localTempProp = ConfigEnvProperties.getInstance().getProperty("wapoptout.local.temp.dir");
        File localTempDir = new File(localTempProp);
        File excelFile = null;
        StringBuffer excelFileName = new StringBuffer("WAP_OPTOUT_");
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    	Timestamp timestamp=new Timestamp(Utility.convertToGMTMilli(new Date(), "yyyyMMddHHmm"));

        if(!localTempDir.exists()) {
            localTempDir.mkdir();
            log.debug("localTempDir created on path : "+localTempDir.getAbsolutePath());
        }

        //create zipFile Name: WAP_OPTOUT_YYYYMMDDHHMM.xls GMT Time
        excelFileName = excelFileName.append(dateFormat.format(timestamp)).append(".xls");
        excelFile = new File(localTempDir, excelFileName.toString());
        
        
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFFont vzwHeadingFont = workbook.createFont();
        vzwHeadingFont.setFontHeightInPoints((short) 24);
        vzwHeadingFont.setFontName(HSSFFont.FONT_ARIAL);
        vzwHeadingFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        HSSFCellStyle vzwHeadingStyle=workbook.createCellStyle();
        vzwHeadingStyle.setFont(vzwHeadingFont);
        vzwHeadingStyle.setWrapText(true);

		HSSFFont optoutHeadingFont = workbook.createFont();
        optoutHeadingFont.setFontHeightInPoints((short) 18);
        optoutHeadingFont.setFontName(HSSFFont.FONT_ARIAL);
        optoutHeadingFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        optoutHeadingFont.setColor(HSSFColor.GREY_50_PERCENT.index);
        
        HSSFCellStyle optoutHeadingStyle=workbook.createCellStyle();
        optoutHeadingStyle.setFont(optoutHeadingFont);
        optoutHeadingStyle.setWrapText(true);

		HSSFFont rowHeaderFont = workbook.createFont();
        rowHeaderFont.setFontHeightInPoints((short) 12);
        rowHeaderFont.setFontName(HSSFFont.FONT_ARIAL);
        rowHeaderFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        HSSFCellStyle rowHeaderStyle1=workbook.createCellStyle();
        rowHeaderStyle1.setFont(rowHeaderFont);
        rowHeaderStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        rowHeaderStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        HSSFCellStyle rowHeaderStyle2=workbook.createCellStyle();        
        rowHeaderStyle2.setFont(rowHeaderFont);
        rowHeaderStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        rowHeaderStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        rowHeaderStyle2.setWrapText(true);

		HSSFFont dataFont = workbook.createFont();
        dataFont.setFontHeightInPoints((short) 10);
        dataFont.setFontName(HSSFFont.FONT_ARIAL);

        HSSFCellStyle dataStyle=workbook.createCellStyle();
        dataStyle.setFont(dataFont);
        
        //Create Sheet and assign name.
        HSSFSheet sheet = workbook.createSheet("WAP");
         
        HSSFRow row=sheet.createRow((short)0);
        row.setHeight((short)(256*2.4));
        HSSFCellUtil.createCell(row, 0, "Verizon Wireless - The Zon",vzwHeadingStyle);
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 3));
        
        row=sheet.createRow((short)1);
        row.setHeight((short)(256*2));
        HSSFCellUtil.createCell(row, 0, "WAP Optimization - Optout URLs",optoutHeadingStyle);
        sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 2));
        
        row = sheet.createRow((short) 3);
        HSSFCellUtil.createCell(row, 0, "Submittal Number", rowHeaderStyle2);
        sheet.setColumnWidth((short) 0, (short) (256 * 12));
        HSSFCellUtil.createCell(row, 1, "Company Name", rowHeaderStyle1);
        sheet.setColumnWidth((short) 1, (short) (256 * 30));
        HSSFCellUtil.createCell(row, 2, "First Name", rowHeaderStyle1);
        sheet.setColumnWidth((short) 2, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 3, "Last Name", rowHeaderStyle1);
        sheet.setColumnWidth((short) 3, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 4, "Requester Email Address", rowHeaderStyle2);
        sheet.setColumnWidth((short) 4, (short) (256 * 24));
        HSSFCellUtil.createCell(row, 5, "Phone Number", rowHeaderStyle2);
        sheet.setColumnWidth((short) 5, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 6, "Street Address", rowHeaderStyle1);
        sheet.setColumnWidth((short) 6, (short) (256 * 30));
        HSSFCellUtil.createCell(row, 7, "City", rowHeaderStyle1);
        sheet.setColumnWidth((short) 7, (short) (256 * 20));
        HSSFCellUtil.createCell(row, 8, "State Or Province", rowHeaderStyle2);
        sheet.setColumnWidth((short) 8, (short) (256 * 10));
        HSSFCellUtil.createCell(row, 9, "ZIP or Postal Code", rowHeaderStyle2);
        sheet.setColumnWidth((short) 9, (short) (256 * 10));
        HSSFCellUtil.createCell(row, 10, "Country", rowHeaderStyle1);
        sheet.setColumnWidth((short) 10, (short) (256 * 24));
        HSSFCellUtil.createCell(row, 11, "Bypass Requested URL", rowHeaderStyle2);
        sheet.setColumnWidth((short) 11, (short) (256 * 24));
        HSSFCellUtil.createCell(row, 12, "Domain Admin Company Name", rowHeaderStyle1);
        sheet.setColumnWidth((short) 12, (short) (256 * 35));        
        HSSFCellUtil.createCell(row, 13, "Domain Admin First Name", rowHeaderStyle1);
        sheet.setColumnWidth((short) 13, (short) (256 * 30));
        HSSFCellUtil.createCell(row, 14, "Domain Admin Last Name", rowHeaderStyle1);
        sheet.setColumnWidth((short) 14, (short) (256 * 30));
        HSSFCellUtil.createCell(row, 15, "Domain Admin Ph Num", rowHeaderStyle1);
        sheet.setColumnWidth((short) 15, (short) (256 * 29));
        HSSFCellUtil.createCell(row, 16, "Domain Admin Email", rowHeaderStyle1);
        sheet.setColumnWidth((short) 16, (short) (256 * 24));
        HSSFCellUtil.createCell(row, 17, "Credential Validated (Y/N)", rowHeaderStyle2);
        sheet.setColumnWidth((short) 17, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 18, "Date Credentials Validated", rowHeaderStyle2);
        sheet.setColumnWidth((short) 18, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 19, "Date Request Submitted to Novarra", rowHeaderStyle2);
        sheet.setColumnWidth((short) 19, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 20, "Date  Request Submitted to Motricity", rowHeaderStyle2);
        sheet.setColumnWidth((short) 20, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 21, "Date Novarra Fulfilled Request", rowHeaderStyle2);
        sheet.setColumnWidth((short) 21, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 22, "Date Motricity Fulfilled Request", rowHeaderStyle2);
        sheet.setColumnWidth((short) 22, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 23, "Date VZW Marketing Tested", rowHeaderStyle2);
        sheet.setColumnWidth((short) 23, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 24, "VZW Marketing Test Successful (Y/N)", rowHeaderStyle2);
        sheet.setColumnWidth((short) 24, (short) (256 * 14));
        HSSFCellUtil.createCell(row, 25, "Date Notification of Bypass Completion Sent to Requestor", rowHeaderStyle2);
        sheet.setColumnWidth((short) 25, (short) (256 * 14));
                
        for (int i=0; i<records.size(); i++){
        	row=sheet.createRow((short)i+4);
        	Object[] fields=(Object[])records.get(i);
        	for (int j=0; j<17; j++){
        		if (fields[j]==null  ){
        			HSSFCellUtil.createCell(row, j, "", dataStyle);
        		}
        		else {
        			/**
        			 * by mnauman to remove www. from bypassUrl in case if 
        			 * it is there 
        			 */
        			// since 11 contains by pass url
        			
        			String string = fields[j].toString();
					
        			if ( j == 11 && !StringFuncs.isNullOrEmpty(string)    
        					&&  string.startsWith("www.")) {

        				if (log.isInfoEnabled()) {
							log.info("  Removing www.  from "+string);
						}
        				
        				string = StringUtils.replace(string, "www.", "");
					}
        			HSSFCellUtil.createCell(row, j, string, dataStyle);
        		}
        	}
        }
        if(excelFile.exists()==false){        	
        	excelFile.createNewFile();
        	log.debug("Excel file: "+excelFile.getName());
        }
        try{
        	bos=new BufferedOutputStream(new FileOutputStream(excelFile));
        	workbook.write(bos);
        	bos.flush();
        } catch(IOException ioe){
        	log.error(ioe,ioe);
        	throw ioe;
        } finally {
        	if (bos != null){
        		bos.close();
        	}
        }
        log.debug("Excel file created at: "+excelFile.getAbsolutePath());
        log.debug("FtpExcelFile.writeExcelFile: End");
        return excelFile;
	}
	
	public static boolean createZipFile(File zipFile, File zipEntry) throws Exception {
		log.debug("FtpExcelFile.createZipFile: Start");
		byte[] buf = new byte[1024];
		boolean zipCreated = false;
		ZipOutputStream zipOut = null;
		BufferedInputStream bis = null;

		try {
			zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			// Compress the files
			try {
				bis = new BufferedInputStream(new FileInputStream(zipEntry));
				zipOut.putNextEntry(new ZipEntry(zipEntry.getName()));
				int len;
				while ((len = bis.read(buf)) > 0) {
					zipOut.write(buf, 0, len);
				}
			}// end try
			catch (IOException e) {
				System.out.println("Problem in adding zip entry");
				log.error(e, e);
				throw e;
			} 
			finally {
				// Complete the entry
				try {
					zipOut.closeEntry();
					bis.close();
				} catch (IOException ioe) {
					System.out.println("Error closing zip entry or input stream for image");
					ioe.printStackTrace();
				}
			}// end finally

			zipCreated = true;
			log.debug("zip file created " + zipFile.getAbsolutePath());
		} catch (FileNotFoundException e) {
			log.error(e, e);
			throw e;
		} finally {
			try {
				if (zipOut != null) {
					zipOut.close();
				}
			} catch (IOException e) {
				System.out.println("Error closing zip stream");
				log.error(e, e);
				System.out.println("deleting zip file: " + zipFile.delete());
				throw e;
			}
		}
		log.debug("FtpExcelFile.createZipFile: End");
		return zipCreated;
	}
	
	public static void processExcelFTPTransfer()throws Exception{
		log.debug("FtpExcelFile.processExcelFTPTransfer: Start");
		ConfigEnvProperties props= ConfigEnvProperties.getInstance();
		String ftpServerAddress= props.getProperty("wapoptout.ftp.server.address");
		String ftpUser= props.getProperty("wapoptout.ftp.user.name");
		String ftpPassword= props.getProperty("wapoptout.ftp.user.password");
		String ftpWorkingDirectory=	props.getProperty("wapoptout.ftp.working.dir");
		String localTempProp = props.getProperty("wapoptout.local.temp.dir");
		List recordsList=FtpExcelFile.getRecordsForExcel();
		if (recordsList != null && recordsList.size()>0){
			File excelFile=FtpExcelFile.writeExcelFile(recordsList);
			String zipFileName=excelFile.getName().substring(0,excelFile.getName().lastIndexOf("."))+".zip";
			File zipFile=new File(localTempProp,zipFileName);
			boolean zipCreated=FtpExcelFile.createZipFile(zipFile, excelFile);
			if (zipCreated){
				log.debug("Deleting excel file after zip creation "+excelFile.delete());
				boolean fileTransfered=AimsFtpUploader.uploadFile(zipFile, ftpServerAddress, ftpUser, ftpPassword, ftpWorkingDirectory);		
				if (fileTransfered){
					List submitalList=new ArrayList();
					StringBuffer sb=new StringBuffer();
					String submittalIds="";
					Session session = null;
					PreparedStatement prepStmt=null;
					for (int i=0; i<recordsList.size(); i++){
						Object[] fields=(Object[])recordsList.get(i);
						if(submitalList.contains(fields[0].toString()) == false){
							submitalList.add(fields[0].toString());
							sb.append(fields[0].toString()).append(",");
						}
					}
					submittalIds=Utility.replace(sb.toString(), ",", "", Utility.REPLACE_LAST_ONLY);
					log.debug("submitall number(s) transfered: "+submittalIds);
					try {				
						session = DBHelper.getInstance().getSession();
						AimsWapOptoutFtpLog ftpLog=new AimsWapOptoutFtpLog();
						ftpLog.setCreatedBy("system");
						ftpLog.setCreatedDate(new Date());
						ftpLog.setFtpFileDescription("Excel file name: "+zipFile.getName()+". Size of file: "+zipFile.length()+" with submittal number(s): "+submittalIds);
						ftpLog.setFtpFileName(zipFile.getName());
						Transaction trx=session.beginTransaction();
						Connection conn=session.connection();
						prepStmt=conn.prepareStatement("UPDATE aims_wap_optout w SET w.is_dirty = 'N' WHERE w.submittal_number IN ("+submittalIds+")");
						prepStmt.executeUpdate();
						session.save(ftpLog);
						trx.commit();
					} catch (HibernateException he) {
						log.error(he, he);
						throw he;
					} finally {
						if (session != null) {
							session.close();
						}
						if (prepStmt != null){
							prepStmt.close();
						}
					}
				}//FTP transfer check
			}//Zip creation check
		}
		log.debug("FtpExcelFile.processExcelFTPTransfer: End");
	}
	public static void main(String args[]){
		DBHelper dbHelper=null;
		try {
			ConfigEnvProperties props= ConfigEnvProperties.getInstance();
			Configuration conf =new Configuration();
			dbHelper=DBHelper.getInstance();
			conf.setProperty("hibernate.connection.url", props.getProperty("connection.url"));
			conf.setProperty("hibernate.connection.username", props.getProperty("connection.username"));
			conf.setProperty("hibernate.connection.password", props.getProperty("connection.password"));
			dbHelper.sessionFactory=conf.configure().buildSessionFactory();			
			FtpExcelFile.processExcelFTPTransfer();
		} catch (Exception e) {
			log.error(e,e);
			e.printStackTrace();
		} finally {
			if (dbHelper != null){
				try {
					dbHelper.sessionFactory.close();
					dbHelper=null;
				} catch (HibernateException he){
					log.debug("Error occured while closing the session factory");
					log.debug(he,he);
				}
			}
		}
	}
}
