package com.netpace.aims.controller.webservices;

import java.net.URL;
import java.util.Vector;

import javax.mail.BodyPart;


import org.apache.soap.Constants;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.encoding.soapenc.BeanSerializer;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;

import org.apache.soap.util.xml.QName;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Apache2DotNet {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = 
           new URL("http://vzwdemoappmgr.infospace.com/vzwhandler/default.asmx");

      SOAPMappingRegistry smr = new SOAPMappingRegistry();

      BeanSerializer beanSer = new BeanSerializer();

 
      smr.mapTypes(Constants.NS_URI_SOAP_ENC,
new QName("", 
                 "SubmitDocumentResponse"),
                 SubmitDocumentResponse.class, beanSer, beanSer);
    
      Call call = new Call();
      call.setSOAPMappingRegistry(smr);
      call.setTargetObjectURI("http://www.infospace.com/vzw/import");
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);


      
      try {
         call.setMethodName("SubmitDocument");
         Response resp = call.invoke(url, 
               "http://www.infospace.com/vzw/import/SubmitDocument");

         if (resp.generatedFault()) {
            Fault fault = resp.getFault();
            String code = fault.getFaultCode();
            String desc = fault.getFaultString();
            System.out.println(code + ": " + desc);

            Vector v = fault.getDetailEntries();
            int cnt = v.size();
            for (int i = 0; i < cnt; i++) {
               Element n = (Element)v.elementAt(i);
               Node nd = n.getFirstChild();
               System.out.println(n.getNodeName() + ": " + 
                                                nd.getNodeValue());   
            }   
         }
         else { 
         	
         	BodyPart b = resp.getBodyPart(1);         	
            Parameter ret = resp.getReturnValue();
            SubmitDocumentResponse value = (SubmitDocumentResponse)ret.getValue();
            System.out.println(value.getCallingUrl());
            System.out.println(value.getErrorMsg());
         }
      }
      catch (SOAPException e) {
         System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
      }
   }
}
