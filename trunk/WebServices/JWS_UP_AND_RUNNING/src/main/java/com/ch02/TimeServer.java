//Code first example.

package com.ch02;

import java.util.Date;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService(name = "AnnotatedTimeServer", serviceName = "RevisedTimeServer", targetNamespace = "http://ch02.com/" )
@SOAPBinding( parameterStyle = ParameterStyle.WRAPPED, style = Style.DOCUMENT, use = Use.LITERAL)
public class TimeServer {
    
    @WebMethod(operationName = "time_string")
    @WebResult(name = "ts_out", targetNamespace = "http://ch02.com/")
    public String getTimeAsString(@WebParam (name = "client_message", targetNamespace = "http://ch02.com/", mode = Mode.IN) String msg){
        return msg + " at " +new Date().toString();
    }
    
    @WebMethod(operationName = "time_elapsed")
    public long getTimeAsElapsed(){
        return new Date().getTime();
    }
    
    @WebMethod
    @Oneway
    public void acceptInput(String msg){
        System.out.println(msg);
    }
}
