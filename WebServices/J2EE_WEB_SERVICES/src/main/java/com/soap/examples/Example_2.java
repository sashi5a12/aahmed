package com.soap.examples;

//Create soap message from file. soap.xml

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class Example_2 {
    public static void main(String[] args) throws SOAPException, FileNotFoundException, IOException{
        
        File file=new File("/Users/aahmed/SkyDrive/eclipse-projects/WebServices/J2EE_WEB_SERVICES/src/main/java/com/soap/examples/soap.xml");
        
        System.out.println(file.getAbsoluteFile());
        FileInputStream soapFile=new FileInputStream(file);
        
        MessageFactory msgFactory = MessageFactory.newInstance();
        
        MimeHeaders mimeTypes = new MimeHeaders();
        mimeTypes.addHeader("Content-Type", "text/xml; charset=UTF-8");
        
        SOAPMessage msg = msgFactory.createMessage(mimeTypes, soapFile);
        
        soapFile.close();
        
        msg.writeTo(System.out);
    }
}
