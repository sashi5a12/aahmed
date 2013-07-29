package com.soap.examples;
/*
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header>
        <mi:message-id xmlns:mi="http://www.Monson-Haefel.com/jwsbook/message-id" 
                        SOAP-ENV:actor="http://www.Monson-Haefel.com/logger" 
                        SOAP-ENV:mustUnderstand="0">
          e190c968f1843615:-3437a6ca:140278e70fa:-8000
        </mi:message-id>
    </SOAP-ENV:Header>
    <SOAP-ENV:Body>
        <mh:getBookPrice xmlns:mh="http://www.Monson-Haefel.com/jwsbook/BookQuote">
            <isbn>1234567</isbn>
        </mh:getBookPrice>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
 */
//Adding actor and mustunderstand attributes for header.

import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

public class Example_5 {
  public static void main(String args[]) throws SOAPException, IOException{
       //Create Soap Message.
        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage msg = msgFactory.createMessage();
        SOAPHeader header = msg.getSOAPHeader();
        
        //Create message-id header block
        SOAPHeaderElement msgIdHearder = (SOAPHeaderElement)header.addChildElement("message-id", "mi", "http://www.Monson-Haefel.com/jwsbook/message-id");
        msgIdHearder.setActor("http://www.Monson-Haefel.com/logger");
        msgIdHearder.setMustUnderstand(false);
        
        String uuid = new java.rmi.dgc.VMID().toString();
        msgIdHearder.addTextNode(uuid);
        
        
        
        //Create body block
        SOAPBody body = msg.getSOAPBody();
        SOAPElement getBookPrice = body.addChildElement("getBookPrice","mh","http://www.Monson-Haefel.com/jwsbook/BookQuote");
        SOAPElement isbn = getBookPrice.addChildElement("isbn");
        isbn.addTextNode("1234567");
        
        msg.writeTo(System.out);
  }  
}
