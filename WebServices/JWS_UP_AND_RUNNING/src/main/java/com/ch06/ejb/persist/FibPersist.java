package com.ch06.ejb.persist;

import java.util.Collection;
import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
@Remote
public interface FibPersist {
    
    @WebMethod 
    int fib(int n);
    
    @WebMethod 
    Collection<Integer> getFibs();
}
