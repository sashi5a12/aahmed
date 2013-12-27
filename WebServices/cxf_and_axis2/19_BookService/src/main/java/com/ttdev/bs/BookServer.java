package com.ttdev.bs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

public class BookServer {

	public static void main(String[] args) throws InterruptedException, IOException {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(BookResource.class);
		sf.setAddress("http://localhost:8081/bs");
		sf.create();
		System.out.println("Started");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (;;){
			System.out.println("Enter command: u--update. q--quit");
			String cmd = br.readLine();
			if(cmd.equals("u")){
				BookDB.instance.getBook1234().setLastModified(new Date());
			}
			else if(cmd.equals("q")){
				System.exit(0);
			}
		}
	}

}
