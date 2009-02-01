package test.optimizingfetching;

import java.util.List;

import model.Item;

import org.hibernate.Session;

import dao.ItemDAO;

public class PrefetchSubselect {

	public static void main(String[] args) {
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
//		List<Item> items=session.createCriteria(Item.class).list();
//		List<Item> items=session.createQuery("from Item where itemId in (2,88)").list();
//		Item item=(Item)session.load(Item.class, 2L);
		Item item=(Item)session.get(Item.class, 2L);
		
		item.getBids().size();
//		items.get(0).getBids().size();
		/*for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
		}*/
		session.close();
//		System.out.println(items.size());
	}

}
