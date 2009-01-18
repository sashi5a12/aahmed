package test.onetomany;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.ItemDAO;

import model.*;

public class Bag {
	public static void main(String args[]){
		
		/*Item item=new Item("Item-2");
		List images=new ArrayList();
		
		images.add("Image-1");
		images.add("Image-2");
				
		item.setImages(images);
		item.addBid(new Bid());
		item.addBid(new Bid());*/
		
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
//		dao.save(item);
		
		Item item=(Item)session.createQuery("from Item i where i.itemId=3").list().get(0);
		trx.commit();
		session.close();
		
		System.out.println(item.getBids());
		System.out.println(item.getImages());
	}
}
