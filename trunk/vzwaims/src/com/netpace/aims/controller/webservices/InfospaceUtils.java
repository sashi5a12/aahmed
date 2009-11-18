package com.netpace.aims.controller.webservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

public class InfospaceUtils 
{
public static byte[] generateNonce(String password) 
{
     Random RANDOM = null;
     byte [] nonceB = null;
 
     RANDOM = new Random(password.hashCode() + new Date().getTime());

     try 
    {
         String nonce = Long.toString(RANDOM.nextLong());
         nonceB = nonce.getBytes();
    } 
    catch (Exception ignore) 
    {
         //throw new RuntimeException(e);
    }
    return nonceB;
}
  
	public static String generateCreatedTimestamp(GregorianCalendar currentTime) {
  
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");		
    df.setTimeZone( TimeZone.getTimeZone("GMT+0:00"));
    Date time = new Date(currentTime.getTimeInMillis());
    return df.format(time);
    
    }
    
	public static String generateExpiredTimestamp(GregorianCalendar currentTime) {  
		
    currentTime.add(GregorianCalendar.MINUTE,5);    
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");		
    df.setTimeZone( TimeZone.getTimeZone("GMT+0:00"));
    Date time = new Date(currentTime.getTimeInMillis());
    return df.format(time);
    }    
    
	public static GregorianCalendar generateCurrentTime() {
    GregorianCalendar currentTime = new GregorianCalendar ();      
    return currentTime;
    }    
	  
	public static byte[] utf8decode(String input) {		
		byte[] ret = null;
		try {
			ret = input.getBytes("UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

   public static String escapeXML(String s) {
    StringBuffer str = new StringBuffer();
    int len = (s != null) ? s.length() : 0;
    for (int i=0; i<len; i++) {
       char ch = s.charAt(i);
       switch (ch) {
       case '<': str.append("&lt;"); break;
       case '>': str.append("&gt;"); break;
       case '&': str.append("&amp;"); break;
       case '"': str.append("&quot;"); break;
       case '\'': str.append("&apos;"); break;
       default: str.append(ch);
     }
    }
    return str.toString();
  }
    
}