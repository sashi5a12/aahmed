package test.optimizingfetching;

import model.Item;

import org.hibernate.Session;

import dao.ItemDAO;

public class LazyDefaultFetchPlan {
	public static void main(String args[]){
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
		Item item=(Item)session.load(Item.class, 3L);
		session.close();
		
		//below line will throw exception because proxy was not initialized
		System.out.println(item.getItemName()); 
	}
}
