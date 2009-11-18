package com.netpace.aims.controller.webservices;

import java.net.*;
import java.io.*;

public class ReadURL {
    public static void main(String[] args) throws Exception {
	URL yahoo = new URL("http://rsa.cricinfo.com/link_to_database/NEW/LIVE/scores-summary-global-desktop.html");
	BufferedReader in = new BufferedReader(
				new InputStreamReader(
				yahoo.openStream()));

	StringBuffer noHtmlBuffer = new StringBuffer();
	String inputLine;
	

	while ((inputLine = in.readLine()) != null)
	{
		//noHtmlString = inputLine.replaceAll("\\<.*?\\>","");
	    if (inputLine.trim().length() > 0)
	    {
	    	noHtmlBuffer.append(inputLine);
	    }
	    
	}
	System.out.println(noHtmlBuffer.toString().replaceAll("\\<.*?\\>",""));
	in.close();
    }
}
