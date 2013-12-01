package com.sample.ch05;

import java.io.IOException;
import java.util.Locale;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

public class SOAPHeaderExample {
    
    public static void main(String args[]) throws SOAPException, IOException{
        
        //Creates a soap message that is compatible with soap-1.2 specification
        MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SOAPMessage msg = factory.createMessage();
        SOAPHeader header = msg.getSOAPHeader();
        
        //adding an element to a sap header; called as header block.
        QName qName = new QName("http://saaj.ws.learning.com/", "transaction", "ws");
        SOAPHeaderElement hElement = header.addHeaderElement(qName);
        hElement.setAttribute("xmlns:ws", "http://wsbook.learning.com/transaction/");
        hElement.setMustUnderstand(true);
        hElement.setRelay(true);
        hElement.setTextContent("567");
        hElement.setRole("http://www.w3.org/2003/05/envelope/role/next");
        
        //adding another header block to a soap header
        QName logQName = new QName("http://saaj.ws.learning.com/", "logger", "log");
        SOAPHeaderElement logHeaderElement = header.addHeaderElement(logQName);
        logHeaderElement.setAttribute("xmlns:log", "http://wsbook.learning.com/logger/");
        logHeaderElement.setRole("http://www.w3.org/2003/05/envelope/role/next");
                
        msg.writeTo(System.out);
    }
}
