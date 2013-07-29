package com.soap.examples;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/*
<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope
 xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
 xmlns:mh="http://www.Monson-Haefel.com/jwsbook/BookQuote">
   <soap:Body>
      <mh:getBookPrice>
          <isbn>0321146182</isbn>
      </mh:getBookPrice>
   </soap:Body>
</soap:Envelope>
 */

//Create soap message from scratch.
public class Example_1 {
    public static void main(String args[]){
        try {
            MessageFactory msgFactory=MessageFactory.newInstance();
            SOAPMessage msg = msgFactory.createMessage();
            msg.getSOAPHeader().detachNode();
            
            SOAPBody body=msg.getSOAPBody();
            SOAPElement getBookPrice = body.addChildElement("getBookPrice", "mh", "http://www.Monson-Haefel.com/jwsbook/BookQuote");
            SOAPElement isbn=getBookPrice.addChildElement("isbn");
            isbn.addTextNode("12345678");
            
            body.setEncodingStyle(SOAPConstants.URI_NS_SOAP_ENCODING);
            msg.writeTo(System.out);
        } catch (SOAPException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
