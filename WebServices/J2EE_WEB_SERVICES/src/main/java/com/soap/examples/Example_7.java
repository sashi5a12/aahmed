
package com.soap.examples;

//Buidling soap fault element

import java.io.IOException;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;


/*
<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Body>
        <soap:Fault>
            <faultcode>soap:Client</faultcode>
            <faultstring>The ISBN contains an invalid character(s)</faultstring>
            <faultactor>
                http://www.Monson-Haefel.omc/BookQuote_WebService
            </faultactor>
            <detail>
                <mh:InvalidIsbnFaultDetail
                    xmlns:mh="http://www.Monson-Haefel.com/jwsbook/BookQuote">
                    <offending-value>19318224-D</offending-value>
                    <conformance-rules>
                        The first nine characters must be digits. The last
                        character may be a digit or the letter 'X'. Case is not
                        important.
                    </conformance-rules>
                </mh:InvalidIsbnFaultDetail>
            </detail>
        </soap:Fault>
    </soap:Body>
</soap:Envelope>
 */
public class Example_7 {
    public static void main(String args[]) throws SOAPException, IOException{
        MessageFactory factory= MessageFactory.newInstance();
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        
        SOAPMessage msg = factory.createMessage();
        SOAPBody body = msg.getSOAPBody();
        SOAPFault fault = body.addFault();
        fault.setFaultCode(soapFactory.createName("client", "soap", SOAPConstants.URI_NS_SOAP_ENVELOPE));
        fault.setFaultString("The ISBN contains an invalid character(s)");
        fault.setFaultActor("http://www.Monson-Haefel.omc/BookQuote_WebService");
        
        Detail detail = fault.addDetail();
        
        
        Name errorName=soapFactory.createName("InvalidIsbnFaultDetail", "mh", "http://www.Monson-Haefel.com/jwsbook/BookQuote");
        DetailEntry detailEntry = detail.addDetailEntry(errorName);
        
        SOAPElement offendingValue=detailEntry.addChildElement("offending-value");
        offendingValue.addTextNode("123456");
        
        SOAPElement conformanceRules = detailEntry.addChildElement("conformance-rules");
        conformanceRules.addTextNode("The first nine characters must be digits. The last character may be a digit or the letter 'X'. Case is not important.");
        
        msg.writeTo(System.out);
    }
}
