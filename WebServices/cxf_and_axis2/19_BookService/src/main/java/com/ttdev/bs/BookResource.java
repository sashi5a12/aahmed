package com.ttdev.bs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("books/{isbn}")
public class BookResource {

	@GET
	@Produces("text/xml")
	public Response getDetails(@Context Request request, @PathParam("isbn") String isbn){
		BookDB db = BookDB.instance;
		if(isbn.equals("1234")){
			System.out.println("got request");
			Book book = db.getBook1234();
			
			
			ResponseBuilder builder = request.evaluatePreconditions(getVersion(book));
			if(builder != null){				
				setExpiry(builder);
			}
			else {
				BookState st = getBookState(book);
				builder = Response.ok(st);
				builder.lastModified(getVersion(book));
				setExpiry(builder);
			}
			return builder.build();
		}
		return null;
	}


	private void setExpiry(ResponseBuilder builder) {
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar nextUpdate = getNextUpdateTime(now);
		
		int maxAge = (int)((nextUpdate.getTimeInMillis()-now.getTimeInMillis())/1000L);
		
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(maxAge);
		
		builder.cacheControl(cacheControl);
		builder.expires(nextUpdate.getTime());
	}
	
	
	private GregorianCalendar getNextUpdateTime(GregorianCalendar now){
		GregorianCalendar nextUpdate = new GregorianCalendar();
		nextUpdate.setTime(now.getTime());
		nextUpdate.set(Calendar.HOUR_OF_DAY, 10);
		nextUpdate.set(Calendar.MINUTE, 0);
		nextUpdate.set(Calendar.SECOND, 0);
		nextUpdate.set(Calendar.MILLISECOND, 0);
		if(now.get(Calendar.HOUR_OF_DAY) >= 10){
			nextUpdate.add(Calendar.DAY_OF_YEAR, 1);
		}
		return nextUpdate;
	}
	
	private BookState getBookState(Book book){
		BookState st = new BookState();
		st.setIsbn(book.getIsbn());
		st.setTitle(book.getTitle());
		return st;
	}
	
	private Date getVersion(Book book){
		Date lastModified = book.getLastModified();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(lastModified);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
