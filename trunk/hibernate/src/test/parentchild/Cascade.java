package test.parentchild;

import java.util.Iterator;
import model.Bid;
import model.Item;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.BidDAO;
import dao.ItemDAO;

public class Cascade {
	public static void main(String args[]){
		ItemDAO dao=new ItemDAO();
		BidDAO bidDao=new BidDAO();
		Session session=dao.getSession();
		Transaction trx=session.beginTransaction();
		
		
		//Save parent/child--> cascade="save-update" in mapping file
		/*Item item=new Item("Item-1");
		item.addBid(new Bid());
		item.addBid(new Bid());
		dao.save(item);*/
		
		//Delete parent record with all children cascade="delete" in mapping file
		/*Item item=dao.findById(2L);
		dao.delete(item);*/
		
		//Delete child record when deleting from collection. cascade="delete-orphan"
		Item item=dao.findById(2L);
		item.getBids();
		Bid bid =(Bid) item.getBids().iterator().next();
		item.getBids().remove(bid);
		
		trx.commit();
	}

}
