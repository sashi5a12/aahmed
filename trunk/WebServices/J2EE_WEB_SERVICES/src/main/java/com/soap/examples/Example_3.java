
package com.soap.examples;

//Using SOAPFactory Class and Name Types.

import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;

public class Example_3 {
    public static void main (String args[]) throws SOAPException, IOException{
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        
        Name getBookPriceName = soapFactory.createName("getBookPrice", "mh", "http://www.Monson-Haefel.com/jwsbook/BookQuote");
        Name isbnName = soapFactory.createName("isbn");

        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage msg= msgFactory.createMessage();
        
        SOAPBody body = msg.getSOAPBody();
        
        SOAPBodyElement bodyElement=body.addBodyElement(getBookPriceName);
        bodyElement.addChildElement(isbnName).addTextNode("123455");
        
        msg.writeTo(System.out);
        
    }
}
