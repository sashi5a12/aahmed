package com.ch06.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@WebService(endpointInterface = "com.ch06.ejb.Fib")
public class FibEJB implements Fib {

    @Override
    public int fib(int n) {
        int fib = 1, prev = 0;
        for (int i = 2; i <= n; i++) {
            int temp = fib;
            fib += prev;
            prev = temp;
        }
        return fib;
    }

    @Override
    public List getFibs() {
        return new ArrayList();
    }

}
