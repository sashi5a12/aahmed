package test.optimizingfetching;

import java.util.*;

import model.*;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import dao.ItemDAO;

public class SelectFetchStrategy {
	
	public static void main(String args[]){
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
		
//		List<Item> items=session.createCriteria(Item.class).list();
//		Item item=(Item)session.load(Item.class, 88L);
		Item item=(Item)session.get(Item.class, 88L);
		Hibernate.initialize(item.getBids());
//		System.out.println(item.getItemName());
		
//		List<Item> items=session.createQuery("from Item where itemId in (2,88,89) order by itemId").list();
		/*for (int i=0; i<items.size(); i++){
			System.out.println(items.get(i).getSeller().getEmail());
		}*/
//		item.getBids().get(0);
		session.close();
		System.out.println(item.getBids().size());
//		for (Iterator<Bid> iterator = item.getBids().iterator(); iterator.hasNext();) {
//			System.out.println(iterator.next().getBidAmount());
//		}
//		System.out.println(items.get(0).getBids().size());
	}
}