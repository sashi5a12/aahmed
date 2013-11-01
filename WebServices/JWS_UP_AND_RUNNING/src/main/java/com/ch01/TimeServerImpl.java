//Example 1-2. Service Implementation Bean for the TimeServer

package com.ch01;

import java.util.Date;
import javax.jws.WebService;

/**
 * The @WebService property endpointInterface links the SIB (this class) 
 * to the SEI (com.ch01.TimeServer). 
 * Note that the method implementations are not annotated as @WebMethods.
 */
@WebService(endpointInterface = "com.ch01.TimeServer")
public class TimeServerImpl implements TimeServer {

    @Override
    public String getTimeAsString() {
        return new Date().toString();
    }

    @Override
    public long getTimeAsElapsed() {
        return new Date().getTime();
    }
}

