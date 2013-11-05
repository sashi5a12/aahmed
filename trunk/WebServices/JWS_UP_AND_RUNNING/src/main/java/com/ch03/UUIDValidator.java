package com.ch03;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

public class UUIDValidator implements SOAPHandler<SOAPMessageContext>{
    private final boolean trace = true;
    private static final int UUIDvariant = 2; // layout
    private static final int UUIDversion = 4; // version
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext c) {
        Boolean request_p = (Boolean)c.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if(!request_p){
            try {
                SOAPMessage msg = c.getMessage();                
                SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
                SOAPHeader hrd = env.getHeader();
                 
                msg.writeTo(System.out);
                System.out.println();
                // Ensure that the SOAP message has a header.
                if (hrd == null) generateSoapFault(msg, "No message header.");
                
                // Get UUID value from header block if it's there
                Iterator it = hrd.extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);
                if (it == null || !it.hasNext()) {
                    generateSoapFault(msg, "No header block for next actor.");
                }

                Node node = (Node)it.next();
                String value = (node==null)?null:node.getValue();
                if (value == null) {
                    generateSoapFault(msg, "No UUID in header block.");
                }
                
                UUID uuid = UUID.fromString(value);
                if(uuid.variant() != UUIDvariant || uuid.version()!=UUIDversion)
                    generateSoapFault(msg, "Bad UUID variant or version.");

                if (trace) {
                    msg.writeTo(System.out);
                }
            } catch (SOAPException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        return true;
    }

    private void generateSoapFault(SOAPMessage msg, String reason) {
        SOAPBody body;
        try {
            body = msg.getSOAPPart().getEnvelope().getBody();
            SOAPFault fault = body.addFault();
            fault.setFaultString(reason);
            // wrapper for a SOAP 1.1 or SOAP 1.2 fault
            throw new SOAPFaultException(fault);

        } catch (SOAPException ex) {
           ex.printStackTrace();
        }
    }
    @Override
    public boolean handleFault(SOAPMessageContext c) {
        return true;
    }

    @Override
    public void close(MessageContext mc) {
        
    }
    
}
