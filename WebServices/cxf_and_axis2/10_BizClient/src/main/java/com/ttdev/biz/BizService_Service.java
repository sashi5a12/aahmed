package com.ttdev.biz;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.8
 * 2013-12-23T08:13:37.149-08:00
 * Generated source version: 2.7.8
 * 
 */
@WebServiceClient(name = "BizService", 
                  wsdlLocation = "src/main/resources/BizService.wsdl",
                  targetNamespace = "http://foo.com") 
public class BizService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://foo.com", "BizService");
    public final static QName BizServiceSOAP = new QName("http://foo.com", "BizServiceSOAP");
    static {
    	URL url = null;
        try {
            url = new URL("file:src/main/resources/BizService.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from ffile:src/main/resources/BizService.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public BizService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public BizService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BizService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns BizService
     */
    @WebEndpoint(name = "BizServiceSOAP")
    public BizService getBizServiceSOAP() {
        return super.getPort(BizServiceSOAP, BizService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BizService
     */
    @WebEndpoint(name = "BizServiceSOAP")
    public BizService getBizServiceSOAP(WebServiceFeature... features) {
        return super.getPort(BizServiceSOAP, BizService.class, features);
    }

}
