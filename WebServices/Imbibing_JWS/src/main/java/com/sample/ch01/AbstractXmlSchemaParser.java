/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.ch01;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class AbstractXmlSchemaParser extends DefaultHandler {

    private StringBuilder currentTagContent = new StringBuilder();
    private boolean nil;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentTagContent.append(new String(ch, start, length));
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attr) {
        // detect nil tags
        System.out.println("---- uri ---" + uri + "-- localName --" + localName + "---- qName ----" + qName);
        System.out.println("---- qName ----" + qName);
        nil = Boolean.parseBoolean(attr.getValue("xsi:nil"));
        System.out.println(nil);
        startTag(qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String currentTag = currentTagContent.toString().trim();
        currentTagContent.setLength(0);
        if (nil) {
            nil = false;
        } else {
            // leave field in DTO set to null
            endTag(qName, currentTag);
        }
    }

    protected abstract void startTag(String name);

    protected abstract void endTag(String name, String tagContent);
}