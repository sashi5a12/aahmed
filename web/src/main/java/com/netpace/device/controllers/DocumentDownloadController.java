/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.controllers;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.utils.VAPConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Wakram
 */
@Controller
public class DocumentDownloadController {
    private static final Log log = LogFactory.getLog(DocumentDownloadController.class);
    
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;

    @RequestMapping(value = "/secure/documents/view.do", method = RequestMethod.GET)
    public ModelAndView viewDownloads(HttpServletRequest request) {
        log.debug("Documents Download View");
        ModelAndView mav = new ModelAndView("documents/viewdownloads.jsp");        

        return mav;
    }
    
    @RequestMapping(value = "/secure/documents/download.do", method = RequestMethod.GET)
    public void downloadDocument(HttpServletRequest request, 
                                @RequestParam(value = "title") String title, 
                                HttpServletResponse response) throws IOException {       
        
        if ( StringUtils.isEmpty(title) ) {
            response.sendRedirect(request.getContextPath() + "/error404.do");
            return;
        }
        
        log.debug("Document asked for download is : " + title);
        
        final String FILE_PATH=applicationPropertiesService.getApplicationPropertiesByTypeAndKey(ApplicationPropertyType.APPLICATION_PROPERTY, VAPConstants.ATTACHMENT_FILE_PATH).getValue();
        FileInputStream in=null;
        ServletOutputStream out=null;
        
        try {
            File file = new File(FILE_PATH + title);
            
            if ( file==null || !file.exists() ) {
                response.sendRedirect(request.getContextPath() + "/error404.do");
                return;
            }           
            
            response.setHeader("Content-Disposition","attachment; filename="+file.getName());
            Integer fileLength = NumberUtils.convertNumberToTargetClass(file.length(), Integer.class);
            response.setContentLength(fileLength);
	
            in = new FileInputStream(file);
            out = response.getOutputStream();
            
            byte[] outputByte = new byte[4096];
            while(in.read(outputByte, 0, 4096) != -1) {
                out.write(outputByte, 0, 4096);
            }
            out.flush();
            response.flushBuffer();
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
    }
}
