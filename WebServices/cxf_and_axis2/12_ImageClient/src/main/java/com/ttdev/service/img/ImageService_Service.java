package com.ttdev.service.img;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.8
 * 2013-12-23T14:03:57.272-08:00
 * Generated source version: 2.7.8
 * 
 */
@WebServiceClient(name = "ImageService", 
                  wsdlLocation = "src/main/resources/ImageService.wsdl",
                  targetNamespace = "urn:ttdev.com:service/img") 
public class ImageService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("urn:ttdev.com:service/img", "ImageService");
    public final static QName P1 = new QName("urn:ttdev.com:service/img", "p1");
    static {
    	URL url = null;
        try {
            url = new URL("file:src/main/resources/ImageService.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:src/main/resources/ImageService.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public ImageService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ImageService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ImageService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns ImageService
     */
    @WebEndpoint(name = "p1")
    public ImageService getP1() {
        return super.getPort(P1, ImageService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ImageService
     */
    @WebEndpoint(name = "p1")
    public ImageService getP1(WebServiceFeature... features) {
        return super.getPort(P1, ImageService.class, features);
    }

}
