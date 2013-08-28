package com.sample.ch01;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SAXErrorHandler implements ErrorHandler {

    public void warning(SAXParseException e) throws SAXException {
        log("-----Warning----", e);
        throw (e);
    }

    public void error(SAXParseException e) throws SAXException {
        log("-----Error----", e);
        throw (e);
    }

    public void fatalError(SAXParseException e) throws SAXException {
        log("----Fatal Error----", e);
        throw (e);
    }

    private void log(String type, SAXParseException e) {
        System.out.println(type + ": " + e.getMessage());
        System.out.println("Line " + e.getLineNumber() + " Column " + e.getColumnNumber());
        System.out.println("System ID: " + e.getSystemId());
    }

}
