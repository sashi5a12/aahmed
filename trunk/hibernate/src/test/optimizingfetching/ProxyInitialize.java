package test.optimizingfetching;

import model.Item;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import dao.ItemDAO;

public class ProxyInitialize {

	public static void main(String[] args) {
		ItemDAO dao=new ItemDAO();
		Session session=dao.getSession();
		Item item=(Item)session.load(Item.class, 2L);
		Hibernate.initialize(item.getBids());
		session.close();
		System.out.println(item.getBids().size());
	}

}
