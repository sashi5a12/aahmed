/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.ch05;

import java.io.IOException;
import java.util.Locale;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

/**
 *
 * @author aahmed
 */
public class SOAPFaultExample {
      public static void main(String args[]) throws SOAPException, IOException{
        
        //Creates a soap message that is compatible with soap-1.2 specification
        MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SOAPMessage msg = factory.createMessage();        
        SOAPBody body = msg.getSOAPBody();

        
        //Adding child elements to the soap fault.
        SOAPFault fault = body.addFault();
        QName faultName = new QName(SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE, "Sender");
        fault.setFaultCode(faultName);
        fault.addFaultReasonText("Invalid input XML", Locale.US);
        fault.setFaultRole("http://ws.learning.com/someactor");
        Detail detail = fault.addDetail();
        detail.setTextContent("Error in input data.");
                
        msg.writeTo(System.out);
    }
}
