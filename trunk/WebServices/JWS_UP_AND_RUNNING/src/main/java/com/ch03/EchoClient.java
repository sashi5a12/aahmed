package com.ch03;

import com.ch03.echoC.EchoService;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

public class EchoClient {

    public static void main(String args[]) {
        EchoService service = new EchoService();
        com.ch03.echoC.Echo port = service.getEchoPort();
        Map<String, Object> reqCtx = ((BindingProvider) port).getRequestContext();

        args = new String[2];
        args[0] = "http://localhost:7004";
        args[1] = "echo";

        // Endpoint address becomes: http://localhost:9797/echo        
        reqCtx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, args[0] + "/" + args[1]);

        // SOAP action becomes: echo
        reqCtx.put(BindingProvider.SOAPACTION_USE_PROPERTY, args[1]);

        // Add some application-specific HTTP headers
        Map<String, List<String>> myHeader = new HashMap<String, List<String>>();
        myHeader.put("Accept-Encoding", Collections.singletonList("gzip"));
        myHeader.put("Greeting", Collections.singletonList("Hello! World"));

        // Insert customized headers into HTTP message headers
        reqCtx.put(MessageContext.HTTP_REQUEST_HEADERS, myHeader);
        dump_map(reqCtx, "");
        
        System.out.println("\n\nRequest above, response below\n\n");
        
        String response = port.echo("Have a nice day!");
        
        Map<String, Object> res_ctx = ((BindingProvider) port).getResponseContext();
        dump_map(res_ctx, "");

        Object response_code = res_ctx.get(MessageContext.HTTP_RESPONSE_CODE);
        
        System.out.println(response_code);
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
