
package com.soap.examples;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


//Adding the soap using DOM api.
public class Example_9 {
    public static void main (String args[]) throws ParserConfigurationException, SAXException, IOException, SOAPException{
        
        // Read an XML document from a file into a DOM tree using JAXP.
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document soapRoot = docBuilder.parse(new File("/Users/aahmed/SkyDrive/eclipse-projects/WebServices/J2EE_WEB_SERVICES/src/main/java/com/soap/examples/soap.xml"));
        
        // Create a SAAJ SOAPMessage object. Get the SOAPBody and SOAPPart.
        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage msg = msgFactory.createMessage();
        SOAPPart soapPart = msg.getSOAPPart();
        SOAPBody body = soapPart.getEnvelope().getBody();
        
        // Import the root element and append it to the body of the SOAP message.
        //Node elementCopy = soapPart.importNode(soapRoot.getDocumentElement(), true);
        //body.appendChild(elementCopy);
        
         // Append the root element of the XML doc to the body of the SOAP message.
         body.addDocument(soapRoot);
        
        msg.writeTo(System.out);
        
    }
}
