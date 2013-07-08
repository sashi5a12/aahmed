
package com.sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "/helloWorld/{userName}")
public class HelloWorld {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return "Hello World!";
    }
    
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public String sayHelloHTML(){
//        return "<b>Hello World</b>";
//    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHello(@PathParam("userName") String userName){
        return "Hello <b>"+userName+"</b>";
    }
}
