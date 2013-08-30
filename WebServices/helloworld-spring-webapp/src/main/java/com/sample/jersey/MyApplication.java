package com.sample.jersey;

import com.sample.ch04.GradeManagerImpl;
import com.sample.ch04.RestDataManagerImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Spring HelloWorld Web Application configuration.
 *
 * @author Jakub Podlesak (jakub.podlesak at oracle.com)
 */
public class MyApplication extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public MyApplication() {
        register(RequestContextFilter.class);
        register(JerseyResource.class);
        register(SpringSingletonResource.class);
        register(SpringRequestResource.class);
        register(CustomExceptionMapper.class);

        register(GradeManagerImpl.class);
        register(RestDataManagerImpl.class);
    }
}
