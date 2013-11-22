package com.ch06.ejb.persist.client;

import java.util.List;

public class ClientEJB {
    public static void main(String args[]){
        FibEJBService service = new FibEJBService();
        Fib port = service.getFibEJBPort();

        // Invoke service methods.
        System.out.println(port.fib(1));
        System.out.println(port.fib(11));
        System.out.println(port.getFibs());
        final int n=8;
        for (int i=1; i<n; i++) System.out.println("Fib(" + i + ") == "+port.fib(i));

        List fibs = port.getFibs();
        for (Object next : fibs) System.out.println(next);
    }
}
