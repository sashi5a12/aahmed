
package com.soap.examples;

//Sending soap message.

import java.io.FileInputStream;
import java.net.URL;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class Example_8 {
    public static void main(String [] args) throws SOAPException,
    java.io.IOException{

    // Build a SOAPMessage from a file
    MessageFactory msgFactory = MessageFactory.newInstance();
    MimeHeaders mimeHeaders = new MimeHeaders();
    mimeHeaders.addHeader("Content-Type","text/xml; charset=UTF-8");
    FileInputStream file = new FileInputStream("soap.xml");
    SOAPMessage requestMsg = msgFactory.createMessage(mimeHeaders, file);
    file.close();

    // Send the SOAP message to the BookQuote Web service
    SOAPConnectionFactory conFactry = SOAPConnectionFactory.newInstance();
    SOAPConnection connection = conFactry.createConnection();
    URL url = new URL(args[0]);
    SOAPMessage replyMsg =connection.call(requestMsg, url);

    // Print out the reply message
    replyMsg.writeTo(System.out);
  }

}
