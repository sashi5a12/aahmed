package test.onetomany;

import java.util.ArrayList;

import model.Bid;
import model.Item;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.ItemDAO;

public class List {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Item item=new Item("Item-3");
		java.util.List images=new ArrayList();
		
		images.add("Image-1");
		images.add("Image-2");
				
		item.setImages(images);
		item.addBid(new Bid());
		item.addBid(new Bid());
		
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		dao.save(item);
		
		trx.commit();
		session.close();
			
	}

}
