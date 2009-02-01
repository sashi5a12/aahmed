package test.optimizingfetching;

import java.util.Iterator;
import java.util.List;

import model.Bid;
import model.Item;

import org.hibernate.Session;

import dao.ItemDAO;

public class PrefetchBatchSize {

/*
 * Define batch-size="5" in Item.hbm.xml for bid collection	-OR-
 * Define batch-size="5" in Users.hbm.xml at class.
 */
	public static void main(String[] args) {
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
		List<Item> items=session.createCriteria(Item.class).list();
		items.get(0).getBids().size();
		/*for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
		}*/
		session.close();
	}

}
