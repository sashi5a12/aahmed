package com.netpace.device.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.entities.VapProductAttachment;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.ProductService;
import com.netpace.device.utils.VAPConstants;


@Controller
public class DownloadMediaController {
	private static final Log log = LogFactory.getLog(DownloadMediaController.class);
	
	@Autowired
    private ApplicationPropertiesService applicationPropertiesService;
	
	@Autowired 
	ProductService productService;
	
	@RequestMapping(value = "/secure/download.do", method = RequestMethod.GET)
	public void download(HttpServletRequest request, 
			@RequestParam(value = "type") String type,
			@RequestParam(value = "id") Integer mediaId,
			HttpServletResponse response) {
		
		final String FILE_PATH=applicationPropertiesService.getApplicationPropertiesByTypeAndKey(ApplicationPropertyType.APPLICATION_PROPERTY, VAPConstants.ATTACHMENT_FILE_PATH).getValue();
		
		log.debug("DownloadMediaController.download:  (download File) ----------------- Start");
		
		log.debug("p_mediaId: "+mediaId);
		log.debug("p_type: "+type);
		FileInputStream in=null;
		ServletOutputStream out=null;
		
		try {
			VapProductAttachment att=new VapProductAttachment();
			att=productService.getAttachment(0, mediaId);

			if (att !=null && att.getMedia() != null){				
				File file = new File(FILE_PATH + mediaId + "." + VAPUtils.getFileExtension(att.getMedia().getFileName()));
				
//				response.setContentType(att.getMedia().getFileType()); 
				response.setContentType("application/octet-stream");
		        response.setContentLength(att.getMedia().getFileLength());
		        response.setHeader("Content-Disposition","attachment; filename="+att.getMedia().getFileName());
		        
		        in = new FileInputStream(file);
		        out = response.getOutputStream();
		        
		        byte[] outputByte = new byte[4096];
		        while(in.read(outputByte, 0, 4096) != -1) {
		        	out.write(outputByte, 0, 4096);
		        }		      
		        out.flush();
		        response.flushBuffer();
			}

		} catch (JpaObjectRetrievalFailureException e) {
			log.debug(e);
		} catch (IOException e) {
			log.debug(e);
		} catch (Exception e) {
			log.debug(e);
		} finally {
			if (in !=null){
				try {
					in.close();
				} catch (IOException e) {
					log.debug(e);
				}
			}
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					log.debug(e);
				}
			}
		}
		
		log.debug("DownloadMediaController.download:  (download File) ----------------- End");
	}
}
