/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaxp.examples;

import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import java.io.IOException;

public class JaxpExample_2 {

    public static void main(String[] args)
            throws SAXException, IOException, Exception {
        String contentHanderClassName = args[0];
        String fileName = args[1];

        ContentHandler contentHandler = (ContentHandler) Class.forName(contentHanderClassName).newInstance();

        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(contentHandler);
        parser.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
        parser.parse(fileName);
    }
}