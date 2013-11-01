
package com.ch03;
public class FibException  extends Exception{
    private String detail;
    
    public FibException(String reason, String details){
        super(reason);
        this.detail = details;
    }
    
    public String getFaultInfo(){
        return detail;
    }
}
