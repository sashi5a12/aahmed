package com.netpace.device.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netpace.device.services.ReportService;

@Controller
@RequestMapping("/report")
public class CSVReportController {

    private static final Log log = LogFactory.getLog(CSVReportController.class);
    
    @Autowired
    ReportService reportService;
    
    @RequestMapping(value = "/send.do", method = RequestMethod.GET)
    public @ResponseBody String sendReport(HttpServletRequest request, @RequestParam(value = "reportType") String reportType){
    	log.debug("CSVReportController.sendReport (Report): Start -------------------");
    	log.debug("System sent request for report: "+reportType);
    	int port=request.getServerPort();
    	String portStr="";
    	if(port!=80 && port !=443){
    		portStr=":"+port;
    	}
    	String csvDownloadUrl="/secure/download.do?type=report&id=";
    	
    	String downloadUrl;
		try {
			downloadUrl = request.getScheme() + "://" + request.getServerName()+portStr+request.getContextPath()+"/signin.do?goto="+URLEncoder.encode(csvDownloadUrl, "UTF-8");
			log.debug("Download URL: "+downloadUrl);
	    	reportService.sendCSVReport(reportType, downloadUrl);
	    	log.debug("CSVReportController.sendReport (Report): End -------------------");
	    	
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
		}
		return "{\"report\":\"Generated\"}";
    }
    
}
