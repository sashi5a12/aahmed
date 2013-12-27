package com.ttdev.bs;

import org.apache.cxf.jaxrs.client.WebClient;

public class BookClient {
	public static void main(String args[]){
		WebClient client = WebClient.create("http://localhost:8081/bs");
		client.path("books/1234");
		Book book = client.get(Book.class);
		System.out.println(book.getTitle());
	}
}
