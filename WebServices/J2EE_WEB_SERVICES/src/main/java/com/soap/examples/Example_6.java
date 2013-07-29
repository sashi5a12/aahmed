package com.soap.examples;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

//use SOAPFactory to create individua header block or any element
public class Example_6 {

    public static void main(String args[]) throws SOAPException {
        // Create SOAPMessage
        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage message = msgFactory.createMessage();
        SOAPHeader header = message.getSOAPHeader();

        // Create message-id header block
        SOAPElement msgId = MessageIDHeader.createHeaderBlock();
        SOAPHeaderElement msgId_header =
                (SOAPHeaderElement) header.addChildElement(msgId);
        msgId_header.setActor("http://www.Monson-Haefel.com/logger");
    }

    static class MessageIDHeader {

        static SOAPElement createHeaderBlock() throws SOAPException {

            SOAPFactory factory = SOAPFactory.newInstance();
            SOAPElement msgId = factory.createElement("message-id", "mi", "http://www.Monson-Haefel.com/jwsbook/message-id");

            String messageid = new java.rmi.dgc.VMID().toString();

            msgId.addTextNode(messageid);
            return msgId;
        }
    }
}
