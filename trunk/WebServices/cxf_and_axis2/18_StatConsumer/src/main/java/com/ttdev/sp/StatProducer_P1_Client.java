
package com.ttdev.sp;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;

/**
 * This class was generated by Apache CXF 2.2.5
 * Mon Dec 21 15:53:54 HKT 2009
 * Generated source version: 2.2.5
 * 
 */

public final class StatProducer_P1_Client {

    private static final QName SERVICE_NAME = new QName("http://ttdev.com/sp", "StatProducer");

    private StatProducer_P1_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = StatProducer_Service.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        StatProducer_Service ss = new StatProducer_Service(wsdlURL, SERVICE_NAME);
        StatProducer port = ss.getP1();  
        
        {
        System.out.println("Invoking getStatistics...");
        java.lang.String _getStatistics_param = "";
        java.lang.String _getStatistics_replyTo = "";
        port.getStatistics(_getStatistics_param, _getStatistics_replyTo);


        }

        System.exit(0);
    }

}