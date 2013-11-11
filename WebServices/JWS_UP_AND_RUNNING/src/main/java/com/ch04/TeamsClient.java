package com.ch04;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TeamsClient {
    public static final String END_POINT="http://localhost:7007/teams";
    
    public static void main(String args[]){
        new TeamsClient().send_requests();
    }
    
    public void send_requests(){
        
        //Get request
        HttpURLConnection conn = get_connection(END_POINT,"GET");
        try {
            conn.connect();
            print_and_parse(conn,false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private HttpURLConnection get_connection(String endPoint, String verb){
        HttpURLConnection conn=null;
        try {
            URL url = new URL(endPoint);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod(verb);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return conn;
    }
    
    private void print_and_parse(HttpURLConnection conn, boolean parse){
        String xml = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String next = null;
            while ((next = reader.readLine())!=null){
                xml += next;
            }
            System.out.println("The raw xml:\n"+xml);
            
            if(parse){
                SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
                parser.parse(new ByteArrayInputStream(xml.getBytes()), new SaxParserHandler());
            }                  
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
    }
}

class SaxParserHandler extends DefaultHandler {
    char[] buffer = new char[1024];
    int n = 0;
    
    @Override
    public void startElement(String uri, String lname, String qname, Attributes attributes){
        clear_buffer();
    }
    
    @Override
    public void characters(char[] data, int start, int length){
        System.arraycopy(data, start, buffer, 0, length);
        n += length;
    }
    
    public void endElement(String uri, String lname, String qname){
        if(Character.isUpperCase(buffer[0])){
            System.out.println(new String(buffer));
        }
        clear_buffer();
    }
    private void clear_buffer(){
        Arrays.fill(buffer, '\0');
        n=0;
    }
}
