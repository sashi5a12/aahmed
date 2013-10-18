package sample.ch02;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface TimeServer {
    
    @WebMethod
    public String getTimeAsString();
    
    @WebMethod
    public long getTimeAsLong();
}
