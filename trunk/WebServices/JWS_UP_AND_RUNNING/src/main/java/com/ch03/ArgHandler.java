
package com.ch03;

import com.ch03.fibC.CountRabbits;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Payload;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

public class ArgHandler implements LogicalHandler<LogicalMessageContext>{

    private final Logger logger=Logger.getLogger("ArgLogger");
    private final boolean log_p = true; // set to false to turn off 

    // If outgoing message argument is negative, make non-negative.
    @Override
    public boolean handleMessage(LogicalMessageContext ctx) {
        Boolean outboud_p = (Boolean)ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if(outboud_p){
             if (log_p) logger.info("ArgHandler.handleMessage");
             LogicalMessage msg=ctx.getMessage();
            try {
                JAXBContext jaxb_ctx= JAXBContext.newInstance("com.ch03.fibC");
                Object payload = msg.getPayload(jaxb_ctx);
                if(payload instanceof JAXBElement){
                    Object obj = ((JAXBElement)payload).getValue();
                    CountRabbits cr = (CountRabbits)obj;
                    int n = cr.getArg0();
                    if (n<0){
                        cr.setArg0(Math.abs(n));
                        // Update the message by adding changed payload.
                        ((JAXBElement)payload).setValue(cr);
                        msg.setPayload(payload,jaxb_ctx);
                    }
                }
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(LogicalMessageContext c) {
        return true;
    }

    @Override
    public void close(MessageContext mc) {}
    
    
}
