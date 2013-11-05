package com.ch03;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class ResponseHandler implements SOAPHandler<SOAPMessageContext>{

    private static final String LoggerName = "ClientSideLogger";
    private Logger logger;
    private final boolean log_p = true; // set to false to turn off 

    public ResponseHandler() {
        logger = Logger.getLogger(LoggerName);
    }

    public boolean handleMessage(SOAPMessageContext ctx) {
        if (log_p) {
            logger.info("ResponseHandler::handleMessage");
        }

        // Is this an outbound message, i.e., a request?
        Boolean request_p = (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // Manipulate the SOAP only if it's a response
        if (!request_p) {
            try {
                SOAPMessage msg = ctx.getMessage();
                msg.writeTo(System.out);
                System.out.println();
            } catch (SOAPException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return true; // continue down the chain
    }

    public boolean handleFault(SOAPMessageContext ctx) {
        return true;
    }

    public Set<QName> getHeaders() {
        if (log_p) {
            logger.info("ResponseHandler:getHeaders");
        }
        return null;
    }

    public void close(MessageContext messageContext) {
        if (log_p) {
            logger.info("ResponseHandler:close");
        }
    }

}
