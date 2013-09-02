/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.ch04.clients;

import com.sample.utils.Utility;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author aahmed
 */
public class SpringClient {

    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(Utility.GET_GRADE, String.class, "1");
        System.out.println("result: " + result);

        Map<String, String> vars = new HashMap<String, String>();
        vars.put("grade", "1");
        vars.put("subject", "Math");

        String result1 = restTemplate.getForObject(Utility.GET_SUBJECT, String.class, vars);
        System.out.println("result1: " + result1);

        Map<String, String> topics = new HashMap<String, String>();
        topics.put("grade", "1");
        topics.put("subject", "Math");
        topics.put("topic", "Mathematics and art");

        String result2 = restTemplate.getForObject(Utility.GET_TOPIC, String.class, topics);
        System.out.println("result2: " + result2);

    }
}
