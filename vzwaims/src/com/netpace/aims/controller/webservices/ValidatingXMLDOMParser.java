package com.netpace.aims.controller.webservices;

import java.io.ByteArrayInputStream;

import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class ValidatingXMLDOMParser
{

    public static void main(String[] args)
    {
        try
        {
        	//validateSchemaDocURI("c:/tt.xsd", "c:/tt.xml");
        	validateJAXPValidator();
        }
      
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }     
    
    public static void validateJAXPValidator(String SchemaUrl, String xmlDocumentString) throws Exception
	 {  	try 
			{
   			//DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	 		DocumentBuilderFactory dbf = new org.apache.xerces.jaxp.DocumentBuilderFactoryImpl();
   			dbf.setNamespaceAware(true);
   			dbf.setValidating(true);
   			
   			dbf.setAttribute(
  				"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
  				"http://www.w3.org/2001/XMLSchema");
   			dbf.setAttribute(
  				"http://java.sun.com/xml/jaxp/properties/schemaSource",
				new java.io.File(SchemaUrl));
   			
               
   			DocumentBuilder db = dbf.newDocumentBuilder();
   			
   			Validator handler=new Validator();
   			db.setErrorHandler(handler);
   			
            if(handler.validationError==true)
            {
            	System.out.println("Reached validationError ...");
                 handler.saxParseException.printStackTrace(System.out);
            }
            else
            	System.out.println("XML Document is valid");   			
   			
   			Document doc = db.parse(new ByteArrayInputStream(xmlDocumentString.getBytes("UTF-8")));
   			
   			System.out.println("PARSING SUCCESS!!!");
			} 
		catch(Exception de) 
			{
			System.out.println("PARSING FAILURE!!!");
  			de.printStackTrace();
            throw de;
			}

	}    
    
    
    public static void validateJAXPValidator()
	 {  	try 
			{
    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			dbf.setNamespaceAware(true);
    			dbf.setValidating(true);
    			
    			dbf.setAttribute(
   				"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
   				"http://www.w3.org/2001/XMLSchema");
    			dbf.setAttribute(
   				"http://java.sun.com/xml/jaxp/properties/schemaSource",
   				"c:\\test2.xsd");
    			
                
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			
    			// Validator handler=new Validator();
    			 //db.setErrorHandler(handler);
    			String xmlSource = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
    				+	"<DeckChangeRequest>" +   "<VendorLoginID>800002</VendorLoginID>"
    				+  "<VendorName>800002</VendorName>"
    						+ "</DeckChangeRequest>";
    			
    			//Document doc = db.parse(new StringBufferInputStream(xmlSource));
    			
    			//Document doc = db.parse(new ByteArrayInputStream(xmlSource.getBytes("UTF-8")));
    			Document doc = db.parse("c:\\test2.xml");
    			
    			System.out.println("PARSING SUCCESS!!!");
			} 
		catch(Exception de) 
			{
			System.out.println("PARSING FAILURE!!!");
   			de.printStackTrace();
			}

	}
    
	
	
	public static void validateSchemaDocURI(String SchemaUrl, String XmlDocumentURI)
    {

         try 
         {

              DOMParser domParser = new DOMParser();
              System.out.println("Created object");
         
              domParser.setFeature("http://xml.org/sax/features/validation",true);
              domParser.setFeature("http://apache.org/xml/features/validation/schema",true);
              domParser.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
              domParser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation",SchemaUrl);
              //domParser.setDocumentSource(XMLDocumentSource);
              
              Validator handler=new Validator();
              domParser.setErrorHandler(handler);  
              System.out.println("Reached 1 ...");
              domParser.parse(XmlDocumentURI);
              System.out.println("Reached 2 ..."); 
           
              if(handler.validationError==true)
              {
              	System.out.println("Reached validationError ...");
                   handler.saxParseException.printStackTrace(System.out);
              }
              else
              	System.out.println("XML Document is valid");
              
              System.out.println("Reached DONE ...");
              System.out.println("<p>done the parse</p>");

         } 
         catch (Exception e) 
         {
              e.printStackTrace(System.out);
         }
    }    
	
	public static void validateSchema(String SchemaUrl, String XmlDocumentString)
     {

          try 
          {
 
               DOMParser domParser = new DOMParser();
               System.out.println("Created object");
          
               domParser.setFeature("http://xml.org/sax/features/validation",true);
               domParser.setFeature("http://apache.org/xml/features/validation/schema",true);
               domParser.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
               domParser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation",SchemaUrl);
               
               Validator handler=new Validator();
               domParser.setErrorHandler(handler);  
              
               //domParser.parse(new InputSource(new ByteArrayInputStream(XmlDocumentString.getBytes("UTF-8"))));
               domParser.parse(new InputSource(new ByteArrayInputStream(XmlDocumentString.getBytes("UTF-8"))));
               
               // getBytes(String charsetName)  "UTF-8"
               // parse(org.xml.sax.InputSource source)
               //InputSource(java.io.InputStream byteStream)
               //java.io.ByteArrayInputStream(byte[] buf) 
               
    
            
               if(handler.validationError==true)
               {
                    handler.saxParseException.printStackTrace(System.out);
               }
               else
               	System.out.println("validateSchema: XML Document is valid");
               
               System.out.println("validateSchema: Done the parser.");

          } 
          catch (Exception e) 
          {
               e.printStackTrace(System.out);
          }
     }
 
     private static class Validator extends DefaultHandler     
     {          
          public boolean  validationError = false;     
          public SAXParseException saxParseException=null;
       
          public void error(SAXParseException exception) throws SAXException     
          {
               validationError = true;     
               saxParseException=exception;
               throw saxParseException;
          }          
          public void fatalError(SAXParseException exception) throws SAXException          
          {          
               validationError = true;     
               saxParseException=exception;  
               throw saxParseException;
          }          
          public void warning(SAXParseException exception) throws SAXException          
          {          
          	  System.out.println("There is a warning in the validation of XML.");
          }     
     }

     
  
     

     


}
