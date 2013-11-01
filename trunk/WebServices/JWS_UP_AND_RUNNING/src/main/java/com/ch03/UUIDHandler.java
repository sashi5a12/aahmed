//Example 3-2. A handler that injects a SOAP header block
package com.ch03;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class UUIDHandler implements SOAPHandler<SOAPMessageContext>{
        
    private static final Logger logger = Logger.getLogger("ClientSideLogger");;
    private final boolean log_p=true; // set to false to turn off


    @Override
    public boolean handleMessage(SOAPMessageContext ctx) {
        if(log_p) logger.info("handleMessage");
        
        // Is this an outbound message, i.e., a request?
        Boolean isRequest = (Boolean)ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        // Manipulate the SOAP only if it's a request
        if (isRequest){
            try {
                // Generate a UUID and its associated timestamp to place in the message header.
                UUID uuid = UUID.randomUUID();
                
                SOAPMessage msg = ctx.getMessage();
                SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
                SOAPHeader hrd = env.getHeader();
                if (hrd==null) hrd=env.addHeader();
                
                QName qname = new QName("http://ch03.com", "uuid");
                SOAPHeaderElement elem = hrd.addHeaderElement(qname);
                
                // In SOAP 1.2, setting the actor is equivalent to setting the role.
                elem.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
                elem.setMustUnderstand(true);
                elem.addTextNode(uuid.toString());
                msg.saveChanges();
                
                // For tracking, write to standard output.
		msg.writeTo(System.out);

            } catch (SOAPException ex) {
                System.err.println(ex);
            } catch (IOException ex) {
                System.err.println(ex);
            }            
        }
        return true; // continue down the chain
    }

    @Override
    public Set<QName> getHeaders() {
        if (log_p) logger.info("getHeaders");
        return null;
    }

    @Override
    public boolean handleFault(SOAPMessageContext c) {
        if (log_p) logger.info("handleFault");
        try {
            c.getMessage().writeTo(System.out);
        } catch (SOAPException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return true;
    }

    @Override
    public void close(MessageContext mc) {
        if (log_p) logger.info("close");
    }
    
}
