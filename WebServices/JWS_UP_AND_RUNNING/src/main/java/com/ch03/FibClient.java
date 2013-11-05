package com.ch03;

import com.ch03.fibC.RabbitCounterService;

public class FibClient {
    public static void main (String[] args){
        RabbitCounterService service = new RabbitCounterService();
        com.ch03.fibC.RabbitCounter port = service.getRabbitCounterPort();
         try {
            int n = -999;
            System.out.println("fib(" + n + ") = " + port.countRabbits(n));
        }
        catch(Exception e) { e.printStackTrace(); System.err.println(e); }
    }
}
