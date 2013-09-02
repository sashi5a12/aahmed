/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.ch04.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author aahmed
 */
public class URLClient {

    public static void main(String args[]) throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/jersey/services/gradeservice/grades");
        URLConnection con = url.openConnection();
        con.setDoOutput(true);

        InputStreamReader isr = new InputStreamReader(con.getInputStream());
        BufferedReader br = new BufferedReader(isr);

        String response;
        while ((response = br.readLine()) != null) {
            System.out.println(response);
        }
        br.close();
    }
}
