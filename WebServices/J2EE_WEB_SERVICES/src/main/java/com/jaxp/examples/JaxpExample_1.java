package com.jaxp.examples;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import java.io.IOException;

public class JaxpExample_1 {
  public static void main(String [] args) throws SAXException, IOException{
    String fileName = "C:\\Users\\aahmed.NETPACE\\Documents\\NetBeansProjects\\WebServices\\J2EE_WEB_SERVICES\\src\\main\\java\\com\\jaxp\\examples\\BookQuote.xml";

    XMLReader parser = XMLReaderFactory.createXMLReader();
    
    parser.parse(fileName);

    System.out.println("No problems parsing the document");
  }
}

