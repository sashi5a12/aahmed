package com.netpace.aims.util;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class ErrorChecker extends DefaultHandler
{

    public ErrorChecker()
    {}

    public void error(SAXParseException e)
    {
        System.out.println("Parsing error:  " + e.getMessage());
        System.out.println("Error in xml on line " + e.getLineNumber());
        MailUtils.sendMailWithHandledExceptions(
            "vzwtech@netpace.com",
            "vzwtech@netpace.com",
            "XML Parsing Error while sending SCM, from Cisco DB",
            null,
            e.getMessage());
        System.exit(1);
    }

    public void warning(SAXParseException e)
    {
        System.out.println("Parsing problem:  " + e.getMessage());
        System.out.println("Error in xml on line " + e.getLineNumber());
        MailUtils.sendMailWithHandledExceptions(
            "vzwtech@netpace.com",
            "vzwtech@netpace.com",
            "XML Parsing Error while sending SCM, from Cisco DB",
            null,
            e.getMessage());
        System.exit(1);
    }

    public void fatalError(SAXParseException e)
    {
        System.out.println("Parsing error:  " + e.getMessage());
        System.out.println("Error in xml on line " + e.getLineNumber());
        MailUtils.sendMailWithHandledExceptions(
            "vzwtech@netpace.com",
            "vzwtech@netpace.com",
            "XML Parsing Error while sending SCM, from Cisco DB",
            null,
            e.getMessage());
        System.exit(1);
    }
}
