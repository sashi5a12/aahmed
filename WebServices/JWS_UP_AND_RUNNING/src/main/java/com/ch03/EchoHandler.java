package com.ch03;

import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class EchoHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext ctx) {
        Boolean request = (Boolean)ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (!request) {
            System.out.println("*********************************************");
	    dump_map((Map)ctx, "");
            System.out.println("*********************************************");
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext c) {
        return true;
    }

    @Override
    public void close(MessageContext mc) {

    }

    private static void dump_map(Map map, String indent) {
        Set keys = map.keySet();
        for (Object key : keys) {
            System.out.println(indent + key + " ==> " + map.get(key));
            if (map.get(key) instanceof Map) {
                dump_map((Map) map.get(key), indent += "   ");
            }
        }
    }
}
