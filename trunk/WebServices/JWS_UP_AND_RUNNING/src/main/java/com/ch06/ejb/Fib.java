package com.ch06.ejb;

import java.util.Collection;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
@Remote
public interface Fib {
    
    @WebMethod 
    int fib(int n);
    
    @WebMethod 
    Collection<Integer> getFibs();
}
