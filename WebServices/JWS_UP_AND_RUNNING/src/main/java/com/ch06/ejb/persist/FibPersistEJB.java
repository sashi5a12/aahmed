package com.ch06.ejb.persist;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@WebService(endpointInterface = "com.ch06.ejb.persist.FibPersistEJB")
public class FibPersistEJB implements FibPersist {

    @PersistenceContext(unitName = "FibServicePU")
    private EntityManager em;

    @Override
    public int fib(int n) {
        System.out.println("Number: "+n);
        System.out.println(em);
        // Computed already? If so, return.
        FibNum fn = em.find(FibNum.class, n); // read from database
        System.out.println("After database hit: "+fn);
        if (fn != null) {
            return fn.getFib();
        }

        int f = compute_fib(Math.abs(n));
        fn = new FibNum();
        fn.setN(n);
        fn.setFib(f);
        em.persist(fn); // write to database
        System.out.println("Value saved in database.");
        return f;
    }

    @Override
    public List getFibs() {
        Query query = em.createNativeQuery("select * from FibNum");
        
        // fib_nums is a list of pairs: N and Fibonacci(N)
        List fib_nums = query.getResultList(); // read from database
        List results = new ArrayList();
        for (Object next : fib_nums) {
            List list = (List) next;
            for (Object n : list) {
                results.add(n);
            }
        }
        return results; // N, fib(N), K, fib(K),...
    }

    private int compute_fib(int n) {
        int f = 1, prev = 0;
        for (int i = 2; i <= n; i++) {
            int temp = f;
            f += prev;
            prev = temp;
        }
	return f;
    }

}
