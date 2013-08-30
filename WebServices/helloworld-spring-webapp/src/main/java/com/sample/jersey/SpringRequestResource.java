package com.sample.jersey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Jersey2 Spring integration example. Demonstrate how to use Spring managed
 * JAX-RS resource class with request scope (+ Spring bean DI).
 *
 * @author Marko Asplund (marko.asplund at gmail.com)
 */
@Path("spring-hello")
@Component
public class SpringRequestResource {

    @Autowired
    private GreetingService greetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return greetingService.greet("world");
    }
}
