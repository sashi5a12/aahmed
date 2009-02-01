package test.optimizingfetching;

import model.Item;

import org.hibernate.Session;

import dao.ItemDAO;

public class LazyDefaultFetchPlan {
	public static void main(String args[]){
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
		Item item=(Item)session.load(Item.class, 2L);

		//uncomment this line will initialize the proxy.
		item.getItemName();
		item.getBids().get(0);
		session.close();
				
		//below line will throw exception because proxy was not initialized
//		System.out.println(item.getItemName());
//		System.out.println(item.getBids().get(0));
		System.out.println(item.getBids().get(1));
	}
}
