package com.ttdev.bs;

public class BookDB {
	private Book book1234;
	
	public BookDB() {
		this.book1234 = new Book("1234","Java Programing");
	}

	public Book getBook1234() {
		return book1234;
	}

	public void setBook1234(Book book1234) {
		this.book1234 = book1234;
	}
	
	public static BookDB instance = new BookDB();
}
