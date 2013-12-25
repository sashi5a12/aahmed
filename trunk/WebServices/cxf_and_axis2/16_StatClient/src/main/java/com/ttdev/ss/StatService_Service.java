package com.ttdev.ss;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.8
 * 2013-12-24T10:07:49.803-08:00
 * Generated source version: 2.7.8
 * 
 */
@WebServiceClient(name = "StatService", 
                  wsdlLocation = "file:src/main/resources/StatService.wsdl",
                  targetNamespace = "http://ttdev.com/ss") 
public class StatService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://ttdev.com/ss", "StatService");
    public final static QName P1 = new QName("http://ttdev.com/ss", "p1");
    static {
    	URL url = null;
        try {
            url = new URL("file:src/main/resources/StatService.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:src/main/resources/StatService.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public StatService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public StatService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StatService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns StatService
     */
    @WebEndpoint(name = "p1")
    public StatService getP1() {
        return super.getPort(P1, StatService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StatService
     */
    @WebEndpoint(name = "p1")
    public StatService getP1(WebServiceFeature... features) {
        return super.getPort(P1, StatService.class, features);
    }

}
