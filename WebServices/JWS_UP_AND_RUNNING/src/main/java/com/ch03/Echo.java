package com.ch03;

import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService
@HandlerChain(file = "echo-handler.xml")
public class Echo {
    
    @Resource
    WebServiceContext ctx;
    
    @WebMethod
    public String echo(String message){
        MessageContext msgCtx = ctx.getMessageContext();
        
        Map requestHeader = (Map)msgCtx.get(MessageContext.HTTP_REQUEST_HEADERS);
        
        dumpMap((Map)msgCtx, "");
        String response = "Your message: " + message + "\n" + "Message request headers: ";
        return response;
    }
    
    private void dumpMap(Map map, String indent){
        Set keys = map.keySet();
        for (Object key: keys){
            System.out.println(indent + key + " ==> " +map.get(key));
            if (map.get(key) instanceof Map){
                dumpMap((Map) map.get(key), indent += "   ");
            }
        }
    }
}
