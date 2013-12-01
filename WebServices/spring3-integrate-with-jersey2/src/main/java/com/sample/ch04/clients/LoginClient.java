/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.ch04.clients;

import com.sample.utils.Utility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author aahmed
 */
public class LoginClient {

    public static void main(String args[]) throws MalformedURLException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("userName", "adnan");
        map.add("password", "ahmed");
        
        restTemplate.postForLocation("http://localhost:9090/jersey/services/loginservice/userName/adnan/password/ahmed", map, String.class);
        
    }
}
