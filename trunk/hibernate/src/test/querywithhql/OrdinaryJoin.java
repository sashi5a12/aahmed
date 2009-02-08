package test.querywithhql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import model.Bid;
import model.Item;

import org.hibernate.Session;

import dao.ItemDAO;

public class OrdinaryJoin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ItemDAO dao = new ItemDAO();
		Session session=dao.getSession();
//		List list=session.createQuery("from Item i join i.bids b").list();
//		List list=session.createQuery("select i from Item i join i.bids b where b.created < current_date()").list();
//		List list=session.createQuery("from Item i left join i.bids b with b.bidAmount > 1").list();
//		List list=session.createQuery("from Item i left join fetch i.bids").list();
//		List list=session.createQuery("from Item i join fetch i.bids b").list();
		List list=session.createQuery("select distinct i from Item i join fetch i.bids b").list();
		session.close();
		Set noDups=new LinkedHashSet(list);
		System.out.println(list.size());
		System.out.println(noDups.size());
		for (Object object : noDups) {
			Item item=(Item)object;
			System.out.println(item.getItemName() + " " +item.getBids().size());
		}
		/*Iterator pairs = list.iterator();
		List items=new ArrayList();
		while ( pairs.hasNext() ) {
			Object[] pair = (Object[]) pairs.next();
			Item item = (Item) pair[0];
			Bid bid = (Bid) pair[1];
			
			if (items.contains(item)==false){
				items.add(item);
				item.setBids(new ArrayList());
			}
			if (item.getBids().contains(bid)==false){
				item.getBids().add(bid);
			}
		}
		System.out.println(items.size());*/
	}

}
