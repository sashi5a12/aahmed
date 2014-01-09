package com.netpace.vic.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netpace.vic.dto.Partner;
import com.netpace.vic.dto.UserApplication;

public class DTOConverter {
	protected static final Logger LOGGER = LoggerFactory.getLogger(DTOConverter.class);	
	public static List<Partner> getPartnerList(ResultSet rs){
		List<Partner> partnerList = new ArrayList<Partner>();
		try {
			while(rs != null && rs.next()){
				Partner partner = new Partner();
				partner.setPartnerId(rs.getInt("partner_id"));
				partner.setPartnerName(rs.getString("partner_name"));
				partner.setFeatured(rs.getString("featured"));
				partner.setUrl(rs.getString("url"));
				partner.setMediaId(rs.getInt("media_id"));
				partnerList.add(partner);
				
			}
		} catch(SQLException se) {
			LOGGER.info(se.toString(),se);
		} catch(Exception e) {
        	LOGGER.info(e.toString(),e);
        } 
				
		return partnerList;
	}
	
	public static Partner getPartnerDTO(ResultSet rs){
		Partner partner  = new Partner();
		try {
			while(rs != null && rs.next()){
				partner.setPartnerId(rs.getInt("partner_id"));
				partner.setPartnerName(rs.getString("partner_name"));
				partner.setLongDescription(rs.getString("long_desc"));
				partner.setShortDescription(rs.getString("short_desc"));
				partner.setUrl(rs.getString("url"));
				partner.setMediaId(rs.getInt("media_id"));
								
			}
		} catch(SQLException se) {
			LOGGER.error(se.toString(),se);
		} catch(Exception e) {
        	LOGGER.error(e.toString(),e);
        } 
				
		return partner;
	}
	
	public static String getHTMLContent(UserApplication userApplication){
		String content = VICConstants.VZWV_EMAIL_CONTENT_HTML;
		
		content = StringUtils.replace(content,"$companyName",userApplication.getCompanyName());
		content = StringUtils.replace(content,"$website",userApplication.getWebsite());
		content = StringUtils.replace(content,"$stageOfCompany",userApplication.getStageOfCompany());
		content = StringUtils.replace(content,"$numberOfEmployees",userApplication.getNumberOfEmployees());
		content = StringUtils.replace(content,"$revenueGeneration",userApplication.getRevenueGeneration());
		content = StringUtils.replace(content,"$reasonForEnagaging",userApplication.getReasonForEnagaging());
		content = StringUtils.replace(content,"$productIdea",userApplication.getProductIdea());
		content = StringUtils.replace(content,"$projectDevelopmentStage",userApplication.getProjectDevelopmentStage());
		content = StringUtils.replace(content,"$connectivityUtilization",userApplication.getConnectivityUtilization());
		content = StringUtils.replace(content,"$productDataSpeed",getDelimitedData(userApplication.getProductDataSpeed()));
		content = StringUtils.replace(content,"$programImmediateHelp",getDelimitedData(userApplication.getProgramImmediateHelp()));
		content = StringUtils.replace(content,"$verticalIndustry",userApplication.getVerticalIndustry());
		content = StringUtils.replace(content,"$existingPartnerships",userApplication.getExistingPartnerships());
		content = StringUtils.replace(content,"$typeOfFiniancing",getDelimitedData(userApplication.getTypeOfFiniancing()));
		content = StringUtils.replace(content,"$projectBusinessModel",userApplication.getProjectBusinessModel());
		content = StringUtils.replace(content,"$targetCustomers",getDelimitedData(userApplication.getTargetCustomers()));
		content = StringUtils.replace(content,"$productUsage",userApplication.getProductUsage());
		content = StringUtils.replace(content,"$existingSalesChannels",userApplication.getExistingSalesChannels());
		content = StringUtils.replace(content,"$companiesWorkingSameSpace",userApplication.getCompaniesWorkingSameSpace());
		content = StringUtils.replace(content,"$oneYearMarketSales",userApplication.getOneYearMarketSales());
		content = StringUtils.replace(content,"$contactName",userApplication.getContactName());
		content = StringUtils.replace(content,"$contactPosition",userApplication.getContactPosition());
		content = StringUtils.replace(content,"$contactEmail",userApplication.getContactEmail());
		content = StringUtils.replace(content,"$contactPhone",userApplication.getContactPhone());
		content = StringUtils.replace(content,"$contactSalesforceIdentifier",userApplication.getContactSalesforceIdentifier());
		content = StringUtils.replace(content,"$contactVerizonSalesRep",userApplication.getContactVerizonSalesRep());
				
		return content;
	}
	
	public static String getPlainTextContent(UserApplication userApplication){
		String content = VICConstants.VZWV_EMAIL_CONTENT_TEXT;
		
		content = StringUtils.replace(content,"$companyName",userApplication.getCompanyName());
		content = StringUtils.replace(content,"$website",userApplication.getWebsite());
		content = StringUtils.replace(content,"$stageOfCompany",userApplication.getStageOfCompany());
		content = StringUtils.replace(content,"$numberOfEmployees",userApplication.getNumberOfEmployees());
		content = StringUtils.replace(content,"$revenueGeneration",userApplication.getRevenueGeneration());
		content = StringUtils.replace(content,"$reasonForEnagaging",userApplication.getReasonForEnagaging());
		content = StringUtils.replace(content,"$productIdea",userApplication.getProductIdea());
		content = StringUtils.replace(content,"$projectDevelopmentStage",userApplication.getProjectDevelopmentStage());
		content = StringUtils.replace(content,"$connectivityUtilization",userApplication.getConnectivityUtilization());
		content = StringUtils.replace(content,"$productDataSpeed",userApplication.getProductDataSpeed());
		content = StringUtils.replace(content,"$programImmediateHelp",userApplication.getProgramImmediateHelp());
		content = StringUtils.replace(content,"$verticalIndustry",userApplication.getVerticalIndustry());
		content = StringUtils.replace(content,"$existingPartnerships",userApplication.getExistingPartnerships());
		content = StringUtils.replace(content,"$typeOfFiniancing",userApplication.getTypeOfFiniancing());
		content = StringUtils.replace(content,"$projectBusinessModel",userApplication.getProjectBusinessModel());
		content = StringUtils.replace(content,"$targetCustomers",userApplication.getTargetCustomers());
		content = StringUtils.replace(content,"$productUsage",userApplication.getProductUsage());
		content = StringUtils.replace(content,"$existingSalesChannels",userApplication.getExistingSalesChannels());
		content = StringUtils.replace(content,"$companiesWorkingSameSpace",userApplication.getCompaniesWorkingSameSpace());
		content = StringUtils.replace(content,"$oneYearMarketSales",userApplication.getOneYearMarketSales());
		content = StringUtils.replace(content,"$contactName",userApplication.getContactName());
		content = StringUtils.replace(content,"$contactPosition",userApplication.getContactPosition());
		content = StringUtils.replace(content,"$contactEmail",userApplication.getContactEmail());
		content = StringUtils.replace(content,"$contactPhone",userApplication.getContactPhone());
		content = StringUtils.replace(content,"$contactSalesforceIdentifier",userApplication.getContactSalesforceIdentifier());
		content = StringUtils.replace(content,"$contactVerizonSalesRep",userApplication.getContactVerizonSalesRep());
				
		return content;
	}
	
	private static String getDelimitedData(String str){
		StringBuffer sb = new StringBuffer();
		String data[] = str.split("##");
		
		for(String s : data){
			sb.append(s).append("<br>");
		}
		
		return sb.toString();
	}
	
}
