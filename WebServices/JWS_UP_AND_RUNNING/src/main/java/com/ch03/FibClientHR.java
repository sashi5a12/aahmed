package com.ch03;

import com.ch03.fibC.RabbitCounterService;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class FibClientHR {

    public static void main(String args[]){
        RabbitCounterService service = new RabbitCounterService();
        service.setHandlerResolver(new ClientHandlerResolver());
        com.ch03.fibC.RabbitCounter port = service.getRabbitCounterPort();

        try {
           int n = 27;
           System.out.printf("fib(%d) = %d\n", n, port.countRabbits(n));
        }
        catch(Exception e) { e.printStackTrace();}
    }
   
}

class ClientHandlerResolver implements HandlerResolver{

    @Override
    public List<Handler> getHandlerChain(PortInfo pi) {
        List<Handler> handlers = new ArrayList<Handler>();
        handlers.add(new UUIDHandler());
        handlers.add(new ResponseHandler());
        return handlers;
    }
    
}