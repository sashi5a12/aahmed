package com.ttdev.simple;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.8
 * 2013-12-21T09:32:43.398-08:00
 * Generated source version: 2.7.8
 * 
 */
@WebServiceClient(name = "SimpleService", 
                  wsdlLocation = "file:src/main/resources/SimpleService.wsdl",
                  targetNamespace = "http://ttdev.com/ss") 
public class SimpleService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://ttdev.com/ss", "SimpleService");
    public final static QName P1 = new QName("http://ttdev.com/ss", "p1");
    static {
    	 URL url = null;
         try {
             url = new URL("file:src/main/resources/SimpleService.wsdl");
         } catch (MalformedURLException e) {
             System.err.println("Can not initialize the default wsdl from file:src/main/resources/SimpleService.wsdl");
             // e.printStackTrace();
         }
         WSDL_LOCATION = url;
    }

    public SimpleService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SimpleService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SimpleService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns SimpleService
     */
    @WebEndpoint(name = "p1")
    public SimpleService getP1() {
        return super.getPort(P1, SimpleService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SimpleService
     */
    @WebEndpoint(name = "p1")
    public SimpleService getP1(WebServiceFeature... features) {
        return super.getPort(P1, SimpleService.class, features);
    }

}
