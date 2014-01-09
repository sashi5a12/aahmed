package com.netpace.vic.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netpace.vic.service.PartnerService;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({
    @Property(name = "service.description", value = "Servlet to serve partner and product Images"),
    @Property(name = "service.vendor", value = "Netpace"),
    @Property(name = "sling.servlet.paths", value = {"/bin/getImage"
    })
})
public class ImageServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    protected final static Logger LOGGER = LoggerFactory.getLogger(ImageServlet.class);
    
    private static GregorianCalendar CACHE_EXPIRY_DATE = new GregorianCalendar();
    
    static {
        LOGGER.info("settting up initial expiry date for image resource");
        setCacheExpiryDate();
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        BundleContext bundleContext = FrameworkUtil.getBundle(ImageServlet.class).getBundleContext();
        ServiceReference factoryRef = bundleContext.getServiceReference(PartnerService.class.getName());
        PartnerService partnerService = (PartnerService) bundleContext.getService(factoryRef);

        Integer mediaId = Integer.valueOf(request.getParameter("mediaId"));
        LOGGER.info("Media Id = " + mediaId);

        byte[] thumb = partnerService.getImage(mediaId);

        String name = "Image";
        response.setContentType("image/jpeg");
        response.setContentLength(thumb.length);

        response.setHeader("Content-Disposition", "inline; filename=\"" + name + "\"");
        
        response.setDateHeader("Last-Modified", new Date().getTime());
                
        String modifiedSince=request.getHeader("if-modified-since");
        
        LOGGER.info("Last-Modified: "+request.getHeader("Last-Modified"));
        LOGGER.info("if-modified-since: "+modifiedSince);
        
        setExpiryHeader(response);
        if (modifiedSince!=null){
            response.setStatus(304);
            return;
        }
        
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new ByteArrayInputStream(thumb));
            output = new  BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[4012];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } catch (IOException e) {
            System.out.println("There are errors in reading/writing image stream "+ e.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ignore) {
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignore) {
                }
            }
        }

    }

    
    private void setExpiryHeader(SlingHttpServletResponse response) {
	GregorianCalendar now = new GregorianCalendar();
        GregorianCalendar nextUpdate = getNextUpdateTime(now);

        int maxAge = (int) ((nextUpdate.getTimeInMillis() - now.getTimeInMillis()) / 1000L);
        
        response.setHeader("Cache-Control", "public, max-age="+maxAge);
        response.setHeader("Expires", nextUpdate.getTime().toString());
    }
    
    private GregorianCalendar getNextUpdateTime(GregorianCalendar now) {
//        GregorianCalendar nextUpdate = new GregorianCalendar();
//        nextUpdate.setTime(now.getTime());
//        nextUpdate.set(Calendar.HOUR_OF_DAY, 12);
//        nextUpdate.set(Calendar.MINUTE, 0);
//        nextUpdate.set(Calendar.SECOND, 0);
//        nextUpdate.set(Calendar.MILLISECOND, 0);
        if (now.get(Calendar.DAY_OF_MONTH ) >= CACHE_EXPIRY_DATE.get(Calendar.DAY_OF_MONTH)) {
            LOGGER.info("Cache expired for this resource. setting up new cache. ");
            LOGGER.info("Old cache date was: "+CACHE_EXPIRY_DATE.getTime());
            setCacheExpiryDate();
            LOGGER.info("New cache date is: "+CACHE_EXPIRY_DATE.getTime());
        }
        return CACHE_EXPIRY_DATE;
    }
    
    private static void setCacheExpiryDate(){
        GregorianCalendar now = new GregorianCalendar();
        CACHE_EXPIRY_DATE.setTime(now.getTime());
        CACHE_EXPIRY_DATE.set(Calendar.HOUR_OF_DAY, 12);
        CACHE_EXPIRY_DATE.set(Calendar.MINUTE, 0);
        CACHE_EXPIRY_DATE.set(Calendar.SECOND, 0);
        CACHE_EXPIRY_DATE.set(Calendar.MILLISECOND, 0);
        CACHE_EXPIRY_DATE.add(Calendar.DAY_OF_YEAR, 7);
        LOGGER.info("setChacheExpiryDate: "+now.getTime());
    }
}
