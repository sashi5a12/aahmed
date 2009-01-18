package test.parentchild;

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
		/*Item item=dao.findById(new Long(1));
		dao.delete(item);*/
		
		//Delete child record when deleting from collection. cascade="delete-orphan"
		/*Item item=dao.findById(2L);
		Bid bid =item.getBids().iterator().next();
		item.getBids().remove(bid);*/
		
		trx.commit();
	}

}
