package com.netpace.aims.bo.application;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.cfg.Configuration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.netpace.aims.bo.system.DisclaimerManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppFiles;
import com.netpace.aims.model.application.AimsJavaAppClob;
import com.netpace.aims.model.application.AimsJavaApps;
import com.netpace.aims.model.application.AimsJavaFiles;
import com.netpace.aims.model.system.AimsDisclaimers;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.Utility;

public class AimsJavaPdf {
	static Logger log = Logger.getLogger(AimsJavaPdf.class.getName());
	private static StyleSheet style=new StyleSheet();
	private static Font font = FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE);
	
	private static void addTitle(Document doc, String titleText, byte[] companyLogo, byte[] titleShot)throws IOException, BadElementException, DocumentException{
		float[] widths = { 62f, 164f, 62f };
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setSpacingAfter(10);

		PdfPCell cell=null; 
		
		cellImage(companyLogo, table, 2, "\n\n\n", 1);
		
		Paragraph title=new Paragraph(titleText,FontFactory.getFont(font.getFamilyname(),12,Font.BOLD));
		cell=new PdfPCell(title);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
		table.addCell(cell);

		cellImage(titleShot, table, 2, "\n\n\n", 1);
		
		doc.add(table);
		
		table = new PdfPTable(1);		
		table.setWidthPercentage(100);
		cell=new PdfPCell();
		cell.setBackgroundColor(Color.BLACK);
		cell.setFixedHeight(1);
		table.addCell(cell);
		
		doc.add(table);
	}
	
	private static void addAgreement(Document doc, String heading, String data)throws IOException,DocumentException{		
		style.loadTagStyle("p","align","center");
		writeData(doc, heading, data);
		style.loadTagStyle("p","align","justify");
	}
	
	private static void cellImage(byte[] imageBytes, PdfPTable table, int cellPadding, String textIfNull, int imageType) throws BadElementException {
		Image image = null;
		PdfPCell cell = null;

		if (imageBytes != null) {
			try {
				// If byte array is not a recognized as image format. E.g text file
				// with image extension
				image = Image.getInstance(imageBytes);
				if (imageType == 1){
					image.scaleAbsolute(60, 40);
				}
				else if (imageType == 2){
					image.scaleAbsolute(115, 150);
				}
				else {
					image.scalePercent(70);
				}
				
				cell = new PdfPCell(image);
			} catch (IOException e) {
				cell = new PdfPCell(new Paragraph("Invalid Image", FontFactory.getFont(font.getFamilyname(), 12, Font.BOLD)));
			}
		}
		else{
			cell = new PdfPCell(new Paragraph(textIfNull));
		}
		
		cell.setPadding(cellPadding);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

	}	
	private static void addScreenShots(Document doc, String heading, byte[] s1, byte[] s2, byte[] s3, byte[] s4, byte[] s5)throws BadElementException,DocumentException{
		
		Paragraph para=new Paragraph(heading,FontFactory.getFont(font.getFamilyname() ,10,Font.UNDERLINE|Font.BOLDITALIC));
		para.setSpacingAfter(12f);
		doc.add(para);
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(80);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
				
		PdfPCell cell;
		cellImage(s1, table, 1, "", 2);
		cellImage(s2, table, 1, "", 2);
		cellImage(s3, table, 1, "", 2);
		cellImage(s4, table, 1, "", 2);
		cellImage(s5, table, 1, "", 2);

		Paragraph empty=new Paragraph("",FontFactory.getFont(font.getFamilyname(),12,Font.BOLD));				
		cell = new PdfPCell(empty);
		cell.setPadding(1);		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		doc.add(table);
	}
	
	private static void writeData(Document doc, String heading, String data)throws IOException, DocumentException {
		
		String text = Utility.replace(data, "<p>&nbsp;</p>", "",Utility.REPLACE_ALL);
		text = Utility.replace(text, "</p>", "</p><br/>", Utility.REPLACE_ALL);
		text = Utility.replace(text, "</ul>", "</ul><br/>", Utility.REPLACE_ALL);
		text = Utility.replace(text, "</p><br/></td>", "</p></td>", Utility.REPLACE_ALL);
				
		Reader reader = new StringReader("<root>" + text + "</root>");
		
		try {
			if (heading !=null && heading.length()>0){
				Paragraph para=new Paragraph(heading,FontFactory.getFont(font.getFamilyname(),10,Font.UNDERLINE|Font.BOLDITALIC));
				para.setSpacingAfter(10f);
				doc.add(para);
			}
			
			ArrayList objects = HTMLWorker.parseToList(reader, style, null);
			for (int k = 0; k < objects.size(); ++k) {
				Element ele = (Element) objects.get(k);
				doc.add(ele);
			}
			
		} catch (IOException e) {
			log.error(e,e);
		} finally {
			reader.close();
		}
	}
	public static void getneratePdf(HashMap appMap, OutputStream out) throws Exception{
		if ( log.isDebugEnabled() )
			log.debug("AimJavaPdf.getneratePdf Start:");
		Document doc = null;

		try {
			doc = new Document(PageSize.LETTER);

			PdfWriter.getInstance(doc, out);
			doc.open();
					
			style.loadTagStyle("p","size","10px");
			style.loadTagStyle("p","align","justify");
			style.loadTagStyle("p","face", font.getFamilyname());
			style.loadTagStyle("p", "leading", "14f");
			style.loadTagStyle("a","color","blue");
			style.loadTagStyle("a", "u", "");
			style.loadTagStyle("ul", "face", font.getFamilyname()); 
			style.loadTagStyle("ul", "size", "10px"); 
			style.loadTagStyle("ul", "leading", "14f");	    
			style.loadTagStyle("li","align","justify");	    
			style.loadTagStyle("td", "cellpadding","2");
			style.loadTagStyle("td","face", font.getFamilyname());
			style.loadTagStyle("td","size", "10px");
//			style.loadTagStyle("th","", "");
			
					
			AimsDisclaimers disclaimer=DisclaimerManager.getDisclaimerById(ManageApplicationsConstants.USER_GUIDE_DISCLAIMER_ID);
			
			AimsApp aimsApp=(AimsApp) appMap.get("AimsApp");
			AimsAppFiles aimsAppFiles=(AimsAppFiles)appMap.get("AimsAppFiles");
			AimsJavaApps aimsJavaApps=(AimsJavaApps) appMap.get("AimsJavaApp");
			AimsJavaFiles aimsJavaFiles=(AimsJavaFiles) appMap.get("AimsJavaFiles");
			AimsJavaAppClob aimsJavaClobs=(AimsJavaAppClob) appMap.get("AimsJavaClobs");
			
			if (aimsApp != null){
				byte[] companyLogo=null;
				byte[] titleShot=null;
				String title =StringUtils.isNotEmpty(aimsApp.getTitle())?aimsApp.getTitle():"";
				if (aimsAppFiles != null){
					if (aimsJavaFiles.getCompanyLogo() != null){
						companyLogo=aimsJavaFiles.getCompanyLogo().getBytes(1, (int)aimsJavaFiles.getCompanyLogo().length());
					}
					if (aimsJavaFiles.getTitleImage() != null){
						titleShot=aimsJavaFiles.getTitleImage().getBytes(1, (int)aimsJavaFiles.getTitleImage().length());
					}
				}				
				addTitle(doc, title, companyLogo, titleShot);				
			}
			if (!disclaimer.getDisclaimerStr().startsWith("<p>")){
				disclaimer.setDisclaimerStr("<p>"+disclaimer.getDisclaimerStr()+"</p>");
			}
			AimsJavaPdf.addAgreement(doc, null, disclaimer.getDisclaimerStr());
			
			if (aimsJavaApps != null && StringUtils.isNotEmpty(aimsJavaApps.getProductDescription())){
				AimsJavaPdf.writeData(doc, "Product Description","<p>"+aimsJavaApps.getProductDescription()+"</p>");
			}
			
			if (aimsAppFiles != null){
				byte[] s1=null;
				byte[] s2=null;
				byte[] s3=null;
				byte[] s4=null;
				byte[] s5=null;
				boolean callFunction=false;
				
				if (aimsAppFiles.getScreenJpeg() != null){
					s1=aimsAppFiles.getScreenJpeg().getBytes(1, (int)aimsAppFiles.getScreenJpeg().length());
					callFunction=true;
				}
				if (aimsAppFiles.getScreenJpeg2() != null){
					s2=aimsAppFiles.getScreenJpeg2().getBytes(1, (int)aimsAppFiles.getScreenJpeg2().length());
					callFunction=true;
				}
				if (aimsAppFiles.getScreenJpeg3() != null){
					s3=aimsAppFiles.getScreenJpeg3().getBytes(1, (int)aimsAppFiles.getScreenJpeg3().length());
					callFunction=true;
				}
				if (aimsAppFiles.getScreenJpeg4() != null){
					s4=aimsAppFiles.getScreenJpeg4().getBytes(1, (int)aimsAppFiles.getScreenJpeg4().length());
					callFunction=true;
				}
				if (aimsAppFiles.getScreenJpeg5() != null){
					s5=aimsAppFiles.getScreenJpeg5().getBytes(1, (int)aimsAppFiles.getScreenJpeg5().length());
					callFunction=true;
				}
				
				if (callFunction){
					AimsJavaPdf.addScreenShots(doc, "Screenshots ", s1, s2, s3, s4, s5);
				}
			}
			
			if (aimsJavaClobs != null){
				if(aimsJavaClobs.getUsingApplication() != null){
					Clob clob = aimsJavaClobs.getUsingApplication();
					String str = clob.getSubString(1, (int)clob.length());
					if (StringUtils.isNotEmpty(str)){
						AimsJavaPdf.writeData(doc, "Using the Application", str);
					}
				}
				if(aimsJavaClobs.getTipsAndTricks() != null){
					Clob clob = aimsJavaClobs.getTipsAndTricks();
					String str = clob.getSubString(1, (int)clob.length());
					if (StringUtils.isNotEmpty(str)){
						AimsJavaPdf.writeData(doc, "Tips and Tricks", str);
					}
				}
				if(aimsJavaClobs.getFaq() != null){
					Clob clob = aimsJavaClobs.getFaq();
					String str = clob.getSubString(1, (int)clob.length());
					if (StringUtils.isNotEmpty(str)){
						AimsJavaPdf.writeData(doc, "FAQ", str);
					}
				}
				if(aimsJavaClobs.getTroubleshooting() != null){
					Clob clob = aimsJavaClobs.getTroubleshooting();
					String str = clob.getSubString(1, (int)clob.length());
					if (StringUtils.isNotEmpty(str)){
						AimsJavaPdf.writeData(doc, "Troubleshooting", str);
					}
				}
				if(aimsJavaClobs.getDevelopmentCompanyDisclaimer() != null){
					Clob clob = aimsJavaClobs.getDevelopmentCompanyDisclaimer();
					String str = clob.getSubString(1, (int)clob.length());
					if (StringUtils.isNotEmpty(str)){
						AimsJavaPdf.writeData(doc, "Development Company Disclaimer", str);
					}
				}
				if(aimsJavaClobs.getAdditionalInformation() != null){
					Clob clob = aimsJavaClobs.getAdditionalInformation();
					String str = clob.getSubString(1, (int)clob.length());
					if (StringUtils.isNotEmpty(str)){
						AimsJavaPdf.writeData(doc, "Additional Information", str);
					}
				}				
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			if (doc != null){
				doc.close();
			}
		}
		if ( log.isDebugEnabled() )
			log.debug("AimJavaPdf.getneratePdf End:");
	}
	
	public static void main(String args[]){
		DBHelper dbHelper=null;
		try {
			FileOutputStream os=new FileOutputStream("C:/User_Guide.pdf");
			ConfigEnvProperties props= ConfigEnvProperties.getInstance();
			Configuration conf =new Configuration();
			dbHelper=DBHelper.getInstance();
			conf.setProperty("hibernate.connection.url", props.getProperty("connection.url"));
			conf.setProperty("hibernate.connection.username", props.getProperty("connection.username"));
			conf.setProperty("hibernate.connection.password", props.getProperty("connection.password"));
			dbHelper.sessionFactory=conf.configure().buildSessionFactory();			
			HashMap  appMap=BrewApplicationManager.getUserGuideData(new Long(18558), new Long(14216));
			AimsJavaPdf.getneratePdf(appMap, os);
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
