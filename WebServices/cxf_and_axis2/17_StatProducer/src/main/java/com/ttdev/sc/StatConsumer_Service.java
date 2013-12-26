package com.ttdev.sc;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.8
 * 2013-12-26T09:58:13.820-08:00
 * Generated source version: 2.7.8
 * 
 */
@WebServiceClient(name = "StatConsumer", 
                  wsdlLocation = "src/main/resources/StatConsumer.wsdl",
                  targetNamespace = "http://ttdev.com/sc") 
public class StatConsumer_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://ttdev.com/sc", "StatConsumer");
    public final static QName P1 = new QName("http://ttdev.com/sc", "p1");
    static {
        URL url = StatConsumer_Service.class.getResource("src/main/resources/StatConsumer.wsdl");
        if (url == null) {
            url = StatConsumer_Service.class.getClassLoader().getResource("src/main/resources/StatConsumer.wsdl");
        } 
        if (url == null) {
            java.util.logging.Logger.getLogger(StatConsumer_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "src/main/resources/StatConsumer.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public StatConsumer_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public StatConsumer_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StatConsumer_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns StatConsumer
     */
    @WebEndpoint(name = "p1")
    public StatConsumer getP1() {
        return super.getPort(P1, StatConsumer.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StatConsumer
     */
    @WebEndpoint(name = "p1")
    public StatConsumer getP1(WebServiceFeature... features) {
        return super.getPort(P1, StatConsumer.class, features);
    }

}
