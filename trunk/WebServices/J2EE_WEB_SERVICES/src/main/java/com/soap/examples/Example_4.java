package com.soap.examples;

import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/*
<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope
    xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:mi="http://www.Monson-Haefel.com/jwsbook/message-id"
    xmlns:proc="http://www.Monson-Haefel.com/jwsbook/processed-by">
    <soap:Header>
        <mi:message-id soap:actor="http://www.Monson-Haefel.com/logger" >
            11d1def534ea:b1c5fa:f3bfb4dcd7:-8000
        </mi:message-id>
        <proc:processed-by>
            <node>
                <time-in-millis>1013694680000</time-in-millis>
                <identity>http://www.customer.com</identity>
            </node>
        </proc:processed-by>
    </soap:Header>
    <soap:Body>
        <!-- Application-specific data goes here -->
        <mh:getBookPrice>
            <isbn>0321146182</isbn>
        </mh:getBookPrice>
    </soap:Body>
</soap:Envelope>
 */
public class Example_4 {
    public static void main(String args[]) throws SOAPException, IOException{
        
        //Create Soap Message.
        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage msg = msgFactory.createMessage();
        SOAPElement header = msg.getSOAPHeader();
        
        //Create message-id header block
        SOAPElement msgIdHearder = header.addChildElement("message-id", "mi", "http://www.Monson-Haefel.com/jwsbook/message-id");
        
        String uuid = new java.rmi.dgc.VMID().toString();
        msgIdHearder.addTextNode(uuid);
        
        //Create processed-by block
        SOAPElement procHeader = header.addChildElement("processed-by","proc","http://www.Monson-Haefel.com/jwsbook/processed-by");
        
        SOAPElement node = procHeader.addChildElement("node");
        SOAPElement time = node.addChildElement("time-in-millis");
        long millis = System.currentTimeMillis();
        time.addTextNode(millis+"");
        SOAPElement identity=node.addChildElement("identity");
        identity.addTextNode("Example_4");
        
        //Create body block
        SOAPElement body = msg.getSOAPBody();
        SOAPElement getBookPrice = body.addChildElement("getBookPrice","mh","http://www.Monson-Haefel.com/jwsbook/BookQuote");
        SOAPElement isbn = getBookPrice.addChildElement("isbn");
        isbn.addTextNode("1234567");
        
        msg.writeTo(System.out);
    }
}
