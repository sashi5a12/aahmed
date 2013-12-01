package com.sample.ch04;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;
import java.util.List;

public class AccessManagerImpl implements AccessManager {

    public void postUserName(String userName) {
        //Implement the logic here
    }

    public void postUserData(String userName, String password) {
        System.out.println("----- userName ----" + userName + "---- password -----" + password);
        //Implement the logic here
    }

    public void postUserData(MultivaluedMap<String, String> formParams) {
        //Implement the logic here.
        for (String key : formParams.keySet()) {
            System.out.println("---- value is ----" + formParams.get(key));
        }

        //Or use this. instead of the above loop.
        for (Map.Entry<String, List<String>> entry : formParams.entrySet()) {
            System.out.println("---- value is ----" + entry.getValue());
        }
    }

}
